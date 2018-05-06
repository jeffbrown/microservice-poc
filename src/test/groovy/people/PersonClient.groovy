package people

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.http.client.Client

@Client('/people')
interface PersonClient {

    @Post('/')
    HttpResponse createPerson(String firstName, String lastName, int age, @Header('Authorization') String token)

    @Get('/')
    List<Person> list()

    @Get('/enabled')
    List<Person> listEnabled()

    @Get('/disabled')
    List<Person> listDisabled()

    @Put('/{id}/enable')
    HttpResponse enable(long id, @Header('Authorization') String token)

    @Put('/{id}/disable')
    HttpResponse disable(long id, @Header('Authorization') String token)

    @Get('/{id}')
    HttpResponse get(long id)
}
