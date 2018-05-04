package people.security

import groovy.transform.CompileStatic
import io.micronaut.context.annotation.Factory
import org.pac4j.core.credentials.password.PasswordEncoder
import org.pac4j.core.credentials.password.SpringSecurityPasswordEncoder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

import javax.inject.Singleton

@Factory
@CompileStatic
class SecurityBeanFactory {

    @Singleton
    PasswordEncoder createPac4jPasswordEncoder() {
        new SpringSecurityPasswordEncoder(new BCryptPasswordEncoder())
    }
}
