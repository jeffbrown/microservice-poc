package people

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.http.client.annotation.Client

@Client('/people')
interface PersonClient {

    @Post('/')
    HttpResponse createPerson(String firstName, String lastName, int age, @Header String authorization)

    @Get('/')
    List<Person> list()

    @Get('/enabled')
    List<Person> listEnabled()

    @Get('/disabled')
    List<Person> listDisabled()

    @Put('/{id}/enable')
    HttpResponse enable(long id, @Header String authorization)

    @Put('/{id}/disable')
    HttpResponse disable(long id, @Header String authorization)

    @Get('/{id}')
    HttpResponse get(long id)
}
