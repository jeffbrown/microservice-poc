package people

import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import spock.lang.Shared
import spock.lang.Stepwise

@Stepwise
class PersonControllerSpec extends AbstractServerSpec {

    @Shared
    long geddyId

    @Shared
    long alexId

    @Shared
    long neilId

    void 'test creating people'() {
        when:
        String token = getJwtToken 'admin', 'password'
        HttpResponse response = personClient.createPerson 'Geddy', 'Lee', 64, "Bearer $token"
        def json = response.body()
        geddyId = json.id

        then:
        response.status == HttpStatus.CREATED
        json.firstName == 'Geddy'
        json.lastName == 'Lee'
        json.age == 64
        json.enabled == true

        when:
        response = personClient.createPerson 'Alex', 'Lifeson', 64, "Bearer $token"
        json = response.body()
        alexId = json.id

        then:
        response.status == HttpStatus.CREATED
        json.firstName == 'Alex'
        json.lastName == 'Lifeson'
        json.age == 64
        json.enabled == true

        when:
        response = personClient.createPerson 'Neil', 'Peart', 65, "Bearer $token"
        json = response.body()
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
        def people = personClient.list()

        then:
        people.size() == 3
        people.find { it.firstName == 'Geddy' && it.lastName == 'Lee' }
        people.find { it.firstName == 'Alex' && it.lastName == 'Lifeson' }
        people.find { it.firstName == 'Neil' && it.lastName == 'Peart' }
    }

    void 'test list enabled people'() {
        when:
        def people = personClient.listEnabled()

        then:
        people.size() == 3
        people.find { it.firstName == 'Geddy' && it.lastName == 'Lee' }
        people.find { it.firstName == 'Alex' && it.lastName == 'Lifeson' }
        people.find { it.firstName == 'Neil' && it.lastName == 'Peart' }
    }

    void 'test list disabled people'() {
        when:
        def people = personClient.listDisabled()

        then:
        people.size() == 0
    }

    void 'test disabling a person'() {
        when:
        String token = getJwtToken 'admin', 'password'
        Map person = personClient.disable(neilId, "Bearer $token").body()

        then:
        person.firstName == 'Neil'
        person.lastName == 'Peart'
        person.enabled == false
    }

    void 'test list enabled people after disabling someone'() {
        when:
        def people = personClient.listEnabled()

        then:
        people.size() == 2
        people.find { it.firstName == 'Geddy' && it.lastName == 'Lee' }
        people.find { it.firstName == 'Alex' && it.lastName == 'Lifeson' }
    }

    void 'test list disabled people after disabling someone'() {
        when:
        def people = personClient.listDisabled()

        then:
        people.size() == 1
        people.find { it.firstName == 'Neil' && it.lastName == 'Peart' }
    }

    void 'test retrieving individual people'() {
        when:
        Map person = personClient.get(geddyId).body()

        then:
        person.firstName == 'Geddy'
        person.lastName == 'Lee'
        person.enabled == true

        when:
        person = personClient.get(alexId).body()

        then:
        person.firstName == 'Alex'
        person.lastName == 'Lifeson'
        person.enabled == true

        when:
        person = personClient.get(neilId).body()

        then:
        person.firstName == 'Neil'
        person.lastName == 'Peart'
        person.enabled == false
    }

    void 'test retrieving a person that does not exist'() {
        when:
        HttpResponse httpResponse = personClient.get 9999

        then:
        httpResponse.status == HttpStatus.NOT_FOUND
    }

    void 'test disabling an already disabled person'() {
        when:
        String token = getJwtToken 'admin', 'password'
        personClient.disable neilId, "Bearer $token"

        then:
        HttpClientResponseException ex = thrown()
        ex.status == HttpStatus.BAD_REQUEST
    }

    void 'test enabling an already enabled person'() {
        when:
        String token = getJwtToken 'admin', 'password'
        personClient.enable geddyId, "Bearer $token"

        then:
        HttpClientResponseException ex = thrown()
        ex.status == HttpStatus.BAD_REQUEST
    }

    void 'test enabling a non-existent person'() {
        when:
        String token = getJwtToken 'admin', 'password'
        HttpResponse httpResponse = personClient.enable 999, "Bearer $token"

        then:
        httpResponse.status == HttpStatus.NOT_FOUND
    }

    void 'test disabling a non-existent person'() {
        when:
        String token = getJwtToken 'admin', 'password'
        HttpResponse httpResponse = personClient.disable 999, "Bearer $token"

        then:
        httpResponse.status == HttpStatus.NOT_FOUND
    }

    void 'test creating person with a negative age'() {
        when:
        String token = getJwtToken 'admin', 'password'
        personClient.createPerson 'First', 'Last', -1, "Bearer $token"

        then:
        HttpClientResponseException ex = thrown()
        ex.status == HttpStatus.BAD_REQUEST
        ex.message == 'age: must be greater than or equal to 0'
    }

    void 'test creating person with a lower case first name'() {
        when:
        String token = getJwtToken 'admin', 'password'
        personClient.createPerson 'first', 'Last', -1, "Bearer $token"

        then:
        HttpClientResponseException ex = thrown()
        ex.status == HttpStatus.BAD_REQUEST
    }

    void 'test creating person with a lower case last name'() {
        when:
        String token = getJwtToken 'admin', 'password'
        personClient.createPerson 'First', 'last', -1, "Bearer $token"

        then:
        HttpClientResponseException ex = thrown()
        ex.status == HttpStatus.BAD_REQUEST
    }

    void 'test enabling a person'() {
        when:
        String token = getJwtToken 'admin', 'password'
        Map person = personClient.enable(neilId, "Bearer $token").body()

        then:
        person.firstName == 'Neil'
        person.lastName == 'Peart'
        person.enabled == true
    }
}
