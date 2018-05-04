package people.security.services

import groovy.transform.CompileStatic
import io.micronaut.security.authentication.providers.PasswordEncoder
import org.pac4j.core.credentials.password.PasswordEncoder as Pac4jPasswordEncoder

import javax.inject.Singleton

@Singleton
@CompileStatic
class BCryptPasswordEncoderService implements PasswordEncoder {
    final Pac4jPasswordEncoder passwordEncoder

    BCryptPasswordEncoderService(Pac4jPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder
    }

    @Override
    String encode(String rawPassword) {
        passwordEncoder.encode rawPassword
    }

    @Override
    boolean matches(String rawPassword, String encodedPassword) {
        passwordEncoder.matches rawPassword, encodedPassword
    }
}
