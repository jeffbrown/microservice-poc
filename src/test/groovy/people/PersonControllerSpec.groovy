package people

import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import spock.lang.Shared
import spock.lang.Stepwise

@Stepwise
class PersonControllerSpec extends AbstractServerSpec {

    @Shared
    String geddyId

    @Shared
    String alexId

    @Shared
    String neilId

    void 'test creating people'() {
        when:
        String token = getJwtToken 'admin', 'password'
        String jsonBody = '{"firstName":"Geddy","lastName":"Lee","age":64}'
        String url = '/people'
        HttpResponse response = executePostRequest url, jsonBody, token
        def json = parseJson response.body()
        geddyId = json.id

        then:
        response.status == HttpStatus.CREATED
        json.firstName == 'Geddy'
        json.lastName == 'Lee'
        json.age == 64
        json.enabled == true

        when:
        jsonBody = '{"firstName":"Alex","lastName":"Lifeson","age":64}'
        response = executePostRequest url, jsonBody, token
        json = parseJson response.body()
        alexId = json.id

        then:
        response.status == HttpStatus.CREATED
        json.firstName == 'Alex'
        json.lastName == 'Lifeson'
        json.age == 64
        json.enabled == true

        when:
        jsonBody = '{"firstName":"Neil","lastName":"Peart","age":65}'
        response = executePostRequest url, jsonBody, token
        json = parseJson response.body()
        neilId = json.id

        then:
        response.status == HttpStatus.CREATED
        json.firstName == 'Neil'
        json.lastName == 'Peart'
        json.age == 65
        json.enabled == true
    }

    void 'test list people'() {
        when:
        String url = '/people'
        HttpResponse results = executeGetRequest url
        def json = parseJson results.body()

        then:
        json.size() == 3
        json.find { it.firstName == 'Geddy' && it.lastName == 'Lee' }
        json.find { it.firstName == 'Alex' && it.lastName == 'Lifeson' }
        json.find { it.firstName == 'Neil' && it.lastName == 'Peart' }
    }

    void 'test list enabled people'() {
        when:
        HttpResponse results = executeGetRequest '/people/enabled'
        def json = parseJson results.body()

        then:
        json.size() == 3
        json.find { it.firstName == 'Geddy' && it.lastName == 'Lee' }
        json.find { it.firstName == 'Alex' && it.lastName == 'Lifeson' }
        json.find { it.firstName == 'Neil' && it.lastName == 'Peart' }
    }

    void 'test list disabled people'() {
        when:
        HttpResponse results = executeGetRequest '/people/disabled'
        def json = parseJson results.body()

        then:
        json.size() == 0
    }

    void 'test disabling a person'() {
        when:
        String token = getJwtToken 'admin', 'password'
        String url = "/people/$neilId/disable"
        HttpResponse response = executePutRequest url, '', token
        def json = parseJson response.body()

        then:
        json.firstName == 'Neil'
        json.lastName == 'Peart'
        json.enabled == false

    }
    void 'test list enabled people after disabling someone'() {
        when:
        HttpResponse results = executeGetRequest '/people/enabled'
        def json = parseJson results.body()

        then:
        json.size() == 2
        json.find { it.firstName == 'Geddy' && it.lastName == 'Lee' }
        json.find { it.firstName == 'Alex' && it.lastName == 'Lifeson' }
    }

    void 'test list disabled people after disabling someone'() {
        when:
        HttpResponse results = executeGetRequest '/people/disabled'
        def json = parseJson results.body()

        then:
        json.size() == 1
        json.find { it.firstName == 'Neil' && it.lastName == 'Peart' }
    }

    void 'test retrieving individual people'() {
        when:
        HttpResponse results = executeGetRequest "/people/$geddyId"
        def json = parseJson results.body()

        then:
        json.firstName == 'Geddy'
        json.lastName == 'Lee'
        json.enabled == true

        when:
        results = executeGetRequest "/people/$alexId"
        json = parseJson results.body()

        then:
        json.firstName == 'Alex'
        json.lastName == 'Lifeson'
        json.enabled == true

        when:
        results = executeGetRequest "/people/$neilId"
        json = parseJson results.body()

        then:
        json.firstName == 'Neil'
        json.lastName == 'Peart'
        json.enabled == false
    }

    void 'test retrieving a person that does not exist'() {
        when:
        executeGetRequest "/people/99999"

        then:
        HttpClientResponseException ex = thrown()
        ex.status == HttpStatus.NOT_FOUND
    }

    void 'test disabling an already disabled person'() {
        when:
        String token = getJwtToken 'admin', 'password'
        String url = "/people/$neilId/disable"
        executePutRequest url, '', token

        then:
        HttpClientResponseException ex = thrown()
        ex.status == HttpStatus.BAD_REQUEST
    }

    void 'test enabling an already enabled person'() {
        when:
        String token = getJwtToken 'admin', 'password'
        String url = "/people/$geddyId/enable"
        executePutRequest url, '', token

        then:
        HttpClientResponseException ex = thrown()
        ex.status == HttpStatus.BAD_REQUEST
    }

    void 'test enabling a non-existent person'() {
        when:
        String token = getJwtToken 'admin', 'password'
        String url = '/people/999/enable'
        executePutRequest url, '', token

        then:
        HttpClientResponseException ex = thrown()
        ex.status == HttpStatus.NOT_FOUND
    }

    void 'test disabling a non-existent person'() {
        when:
        String token = getJwtToken 'admin', 'password'
        String url = '/people/999/disable'
        executePutRequest url, '', token

        then:
        HttpClientResponseException ex = thrown()
        ex.status == HttpStatus.NOT_FOUND
    }

    void 'test creating person with a negative age'() {
        when:
        String token = getJwtToken 'admin', 'password'
        String jsonBody = '{"firstName":"First","lastName":"Last","age":-1}'
        String url = '/people'
        executePostRequest url, jsonBody, token

        then:
        HttpClientResponseException ex = thrown()
        ex.status == HttpStatus.BAD_REQUEST
        ex.message == 'age: must be greater than or equal to 0'
    }

    void 'test creating person with a lower case first name'() {
        when:
        String token = getJwtToken 'admin', 'password'
        String jsonBody = '{"firstName":"johnny","lastName":"Winter","age":70}'
        String url = '/people'
        executePostRequest url, jsonBody, token

        then:
        HttpClientResponseException ex = thrown()
        ex.status == HttpStatus.BAD_REQUEST
    }

    void 'test creating person with a lower case last name'() {
        when:
        String token = getJwtToken 'admin', 'password'
        String jsonBody = '{"firstName":"Johnny","lastName":"winter","age":70}'
        String url = '/people'
        executePostRequest url, jsonBody, token

        then:
        HttpClientResponseException ex = thrown()
        ex.status == HttpStatus.BAD_REQUEST
    }

    void 'test enabling a person'() {
        when:
        String token = getJwtToken 'admin', 'password'
        String url = "/people/$neilId/enable"
        HttpResponse response = executePutRequest url, '', token
        def json = parseJson response.body()

        then:
        json.firstName == 'Neil'
        json.lastName == 'Peart'
        json.enabled == true
    }
}
