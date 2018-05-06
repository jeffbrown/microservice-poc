package people

import groovy.json.JsonSlurper
import io.micronaut.context.ApplicationContext
import io.micronaut.http.HttpRequest
import io.micronaut.http.MediaType
import io.micronaut.http.client.HttpClient
import io.micronaut.runtime.server.EmbeddedServer
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

abstract class AbstractServerSpec extends Specification {

    @Shared
    @AutoCleanup
    EmbeddedServer embeddedServer =
            ApplicationContext.run EmbeddedServer

    @Shared
    @AutoCleanup
    HttpClient client = HttpClient.create embeddedServer.URL

    @Shared PersonClient personClient = embeddedServer.applicationContext.getBean PersonClient

    @Shared
    JsonSlurper jsonSlurper = new JsonSlurper()

    protected String getJwtToken(String userName, String password) {
        def jsonBodyString = "{\"username\":\"${userName}\",\"password\":\"${password}\"}"
        def responseString = client.toBlocking().retrieve(
                HttpRequest.POST('/login', jsonBodyString)
                        .contentType(MediaType.APPLICATION_JSON))
        def json = jsonSlurper.parseText responseString

        json.accessToken
    }

}
