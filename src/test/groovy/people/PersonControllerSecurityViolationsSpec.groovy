package people

import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException

class PersonControllerSecurityViolationsSpec extends AbstractServerSpec {

    void 'test creating a person with non admin credentials'() {
        when:
        String token = getJwtToken 'user', 'password'
        personClient.createPerson 'Geddy', 'Lee', 64, "Bearer $token"

        then:
        HttpClientResponseException ex = thrown()
        ex.status == HttpStatus.FORBIDDEN
    }

    void 'test creating a person without credentials'() {
        when:
        personClient.createPerson 'Geddy', 'Lee', 64, ''

        then:
        HttpClientResponseException ex = thrown()
        ex.status == HttpStatus.UNAUTHORIZED
    }

    void 'test disabling a person without credentials'() {
        when:
        personClient.disable 999, ''

        then:
        HttpClientResponseException ex = thrown()
        ex.status == HttpStatus.UNAUTHORIZED
    }
}
