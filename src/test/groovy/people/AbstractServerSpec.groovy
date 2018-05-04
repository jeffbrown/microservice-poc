package people

import io.micronaut.context.ApplicationContext
import io.micronaut.http.client.HttpClient
import io.micronaut.runtime.server.EmbeddedServer
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

abstract class AbstractServerSpec extends Specification implements SpecRequestHelper {

    @Shared
    @AutoCleanup
    EmbeddedServer embeddedServer =
            ApplicationContext.run EmbeddedServer

    @Shared
    @AutoCleanup
    HttpClient client = HttpClient.create embeddedServer.URL

}
