package people

import groovy.json.JsonSlurper
import io.micronaut.context.ApplicationContext
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.runtime.server.EmbeddedServer
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise

@Stepwise
class PersonControllerSpec extends Specification {

    @Shared
    @AutoCleanup
    EmbeddedServer embeddedServer =
            ApplicationContext.run(EmbeddedServer)

    @Shared
    @AutoCleanup
            client = HttpClient.create(embeddedServer.URL)

    @Shared
            jsonSlurper = new JsonSlurper()

    @Shared
            geddyId
    @Shared
            alexId
    @Shared
            neilId

    void 'test creating people'() {
        when:
        def response = client.toBlocking().retrieve(
                HttpRequest.POST('/people', '{"firstName":"Geddy","lastName":"Lee","age":64}')
                        .contentType(MediaType.APPLICATION_JSON))
        def json = jsonSlurper.parseText response
        geddyId = json.id

        then:
        json.firstName == 'Geddy'
        json.lastName == 'Lee'
        json.age == 64
        json.enabled == true

        when:
        response = client.toBlocking().retrieve(
                HttpRequest.POST('/people', '{"firstName":"Alex","lastName":"Lifeson","age":64}')
                        .contentType(MediaType.APPLICATION_JSON))
        json = jsonSlurper.parseText response
        alexId = json.id

        then:
        json.firstName == 'Alex'
        json.lastName == 'Lifeson'
        json.age == 64
        json.enabled == true

        when:
        response = client.toBlocking().retrieve(
                HttpRequest.POST('/people', '{"firstName":"Neil","lastName":"Peart","age":65}')
                        .contentType(MediaType.APPLICATION_JSON))
        json = jsonSlurper.parseText response
        neilId = json.id

        then:
        json.firstName == 'Neil'
        json.lastName == 'Peart'
        json.age == 65
        json.enabled == true
    }

    void 'test list people'() {
        when:
        def results = client.toBlocking()
                .retrieve(HttpRequest.GET('/people'))
        def json = jsonSlurper.parseText results

        then:
        json.size() == 3
        json.find { it.firstName == 'Geddy' && it.lastName == 'Lee' }
        json.find { it.firstName == 'Alex' && it.lastName == 'Lifeson' }
        json.find { it.firstName == 'Neil' && it.lastName == 'Peart' }
    }

    void 'test list enabled people'() {
        when:
        def results = client.toBlocking()
                .retrieve(HttpRequest.GET('/people/enabled'))
        def json = jsonSlurper.parseText results

        then:
        json.size() == 3
        json.find { it.firstName == 'Geddy' && it.lastName == 'Lee' }
        json.find { it.firstName == 'Alex' && it.lastName == 'Lifeson' }
        json.find { it.firstName == 'Neil' && it.lastName == 'Peart' }
    }

    void 'test list disabled people'() {
        when:
        def results = client.toBlocking()
                .retrieve(HttpRequest.GET('/people/disabled'))
        def json = jsonSlurper.parseText results

        then:
        json.size() == 0
    }

    void 'test disabling a person'() {
        when:
        def response = client.toBlocking().retrieve(
                HttpRequest.PUT("/people/$neilId/disable", ''))
        def json = jsonSlurper.parseText response

        then:
        json.firstName == 'Neil'
        json.lastName == 'Peart'
        json.enabled == false

    }

    void 'test list enabled people after disabling someone'() {
        when:
        def results = client.toBlocking()
                .retrieve(HttpRequest.GET('/people/enabled'))
        def json = jsonSlurper.parseText results

        then:
        json.size() == 2
        json.find { it.firstName == 'Geddy' && it.lastName == 'Lee' }
        json.find { it.firstName == 'Alex' && it.lastName == 'Lifeson' }
    }

    void 'test list disabled people after disabling someone'() {
        when:
        def results = client.toBlocking()
                .retrieve(HttpRequest.GET('/people/disabled'))
        def json = jsonSlurper.parseText results

        then:
        json.size() == 1
        json.find { it.firstName == 'Neil' && it.lastName == 'Peart' }
    }

    void 'test retrieving individual people'() {
        when:
        def results = client.toBlocking()
                .retrieve(HttpRequest.GET("/people/$geddyId"))
        def json = jsonSlurper.parseText results

        then:
        json.firstName == 'Geddy'
        json.lastName == 'Lee'
        json.enabled == true

        when:
        results = client.toBlocking()
                .retrieve(HttpRequest.GET("/people/$alexId"))
        json = jsonSlurper.parseText results

        then:
        json.firstName == 'Alex'
        json.lastName == 'Lifeson'
        json.enabled == true

        when:
        results = client.toBlocking()
                .retrieve(HttpRequest.GET("/people/$neilId"))
        json = jsonSlurper.parseText results

        then:
        json.firstName == 'Neil'
        json.lastName == 'Peart'
        json.enabled == false
    }

    void 'test retrieving a person that does not exist'() {
        when:
        client.toBlocking()
                .retrieve(HttpRequest.GET("/people/99999"))

        then:
        HttpClientResponseException ex = thrown()
        ex.status == HttpStatus.NOT_FOUND
    }
}
