package people

import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client

@Client('/login')
interface LoginClient {

    @Post('/')
    Map login(String username, String password)
}
