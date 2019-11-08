package people

import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client
import io.micronaut.security.token.jwt.render.AccessRefreshToken

@Client('/login')
interface LoginClient {

    @Post('/')
    AccessRefreshToken login(String username, String password)
}
