package people

import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException

class PersonControllerSecurityViolationsSpec extends AbstractServerSpec {

    void 'test creating a person with non admin credentials'() {
        when:
        String token = getJwtToken 'user', 'password'
        String jsonBody = '{"firstName":"Geddy","lastName":"Lee","age":64}'
        String url = '/people'
        executePostRequest url, jsonBody, token

        then:
        HttpClientResponseException ex = thrown()
        ex.status == HttpStatus.FORBIDDEN
    }

    void 'test creating a person without credentials'() {
        when:
        String jsonBody = '{"firstName":"Geddy","lastName":"Lee","age":64}'
        String url = '/people'
        executePostRequest url, jsonBody

        then:
        HttpClientResponseException ex = thrown()
        ex.status == HttpStatus.UNAUTHORIZED
    }

    void 'test disabling a person without credentials'() {
        when:
        executePutRequest '/people/999/disable', ''

        then:
        HttpClientResponseException ex = thrown()
        ex.status == HttpStatus.UNAUTHORIZED
    }
}
