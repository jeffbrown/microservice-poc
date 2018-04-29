package people

import groovy.transform.CompileStatic
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put

import javax.inject.Inject

@Controller('/people')
@CompileStatic
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

    @Post('/')
    Person save(String firstName, String lastName, int age) {
        personService.savePerson firstName, lastName, age
    }

    @Get('/enabled')
    List<Person> enabledPeople() {
        personService.listEnabled()
    }

    @Get('/disabled')
    List<Person> disabledPeople() {
        personService.listDisabled()
    }

    @Put('/{id}/enable')
    Person enable(long id) {
        personService.enable id
    }

    @Put('/{id}/disable')
    Person disable(long id) {
        personService.disable id
    }
}
