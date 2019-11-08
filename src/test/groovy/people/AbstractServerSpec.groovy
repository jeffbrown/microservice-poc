package people

import io.micronaut.context.ApplicationContext
import io.micronaut.http.client.HttpClient
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.security.token.jwt.render.AccessRefreshToken
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

    @Shared
    PersonClient personClient = embeddedServer.applicationContext.getBean PersonClient

    @Shared
    LoginClient loginClient = embeddedServer.applicationContext.getBean LoginClient

    protected String getJwtToken(String userName, String password) {
        loginClient.login(userName, password).accessToken
    }
}
