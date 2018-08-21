package people

import groovy.transform.CompileStatic
import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.security.Secured
import io.micronaut.security.rules.SecurityRule
import io.micronaut.validation.Validated

import javax.inject.Inject
import javax.validation.constraints.Min
import javax.validation.constraints.Pattern

@Controller('/people')
@CompileStatic
@Validated
@Secured(SecurityRule.IS_ANONYMOUS)
class PersonController {

    @Inject
    PersonService personService

    @Get('/')
    List<Person> list() {
        personService.list()
    }

    @Get('/{id}')
    Person person(long id) {
        personService.get id
    }

    @Secured('ROLE_ADMIN')
    @Post('/')
    HttpResponse save(@Pattern(regexp = '^[A-Z].*') String firstName,
                      @Pattern(regexp = '^[A-Z].*') String lastName,
                      @Min(0l) int age) {
        Person person = personService.savePerson(firstName, lastName, age)
        HttpResponse.status(HttpStatus.CREATED)
                .header(HttpHeaders.LOCATION, locationHeader(person.id))
    }

    @Get('/enabled')
    List<Person> enabledPeople() {
        personService.listEnabled()
    }

    @Get('/disabled')
    List<Person> disabledPeople() {
        personService.listDisabled()
    }

    @Secured('ROLE_USER')
    @Put('/{id}/enable')
    HttpResponse enable(long id) {
        Number usersUpdated = personService.enable(id)
        if(!usersUpdated) {
            if(!personService.countById(id)) {
                return HttpResponse.status(HttpStatus.NOT_FOUND)
            }
            return HttpResponse.status(HttpStatus.BAD_REQUEST)
        }
        HttpResponse.status(HttpStatus.OK)
                .header(HttpHeaders.LOCATION, locationHeader(id))
    }

    @Secured('ROLE_USER')
    @Put('/{id}/disable')
    HttpResponse disable(long id) {
        Number usersUpdated = personService.disable(id)
        if(!usersUpdated) {
            if(!personService.countById(id)) {
                return HttpResponse.status(HttpStatus.NOT_FOUND)
            }
            return HttpResponse.status(HttpStatus.BAD_REQUEST)
        }
        HttpResponse.status(HttpStatus.OK)
                .header(HttpHeaders.LOCATION, locationHeader(id))
    }

    protected String locationHeader(long id) {
        "/people/$id"
    }
}
