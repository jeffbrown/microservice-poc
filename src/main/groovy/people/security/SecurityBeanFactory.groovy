package people.security

import groovy.transform.CompileStatic
import io.micronaut.context.annotation.Factory
import org.pac4j.core.credentials.password.PasswordEncoder as Pac4jPasswordEncoder
import org.pac4j.core.credentials.password.SpringSecurityPasswordEncoder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder as SpringSecurityCryptoPasswordEncoder

import javax.inject.Singleton

@Factory
@CompileStatic
class SecurityBeanFactory {

    @Singleton
    Pac4jPasswordEncoder createPac4jPasswordEncoder(SpringSecurityCryptoPasswordEncoder cryptoEncoder) {
        new SpringSecurityPasswordEncoder(cryptoEncoder)
    }

    @Singleton
    SpringSecurityCryptoPasswordEncoder createCryptoEncoder() {
        new BCryptPasswordEncoder()
    }
}
