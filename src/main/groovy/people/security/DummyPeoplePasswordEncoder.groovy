package people.security

import groovy.transform.CompileStatic
import io.micronaut.security.authentication.providers.PasswordEncoder

import javax.inject.Singleton

@CompileStatic
@Singleton
class DummyPeoplePasswordEncoder implements PasswordEncoder {

    @Override
    String encode(String rawPassword) {
        rawPassword
    }

    @Override
    boolean matches(String rawPassword, String encodedPassword) {
        rawPassword == encodedPassword
    }
}
