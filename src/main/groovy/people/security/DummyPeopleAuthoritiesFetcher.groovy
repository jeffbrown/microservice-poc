package people.security

import groovy.transform.CompileStatic
import io.micronaut.security.authentication.providers.AuthoritiesFetcher
import io.reactivex.Flowable
import org.reactivestreams.Publisher

import javax.inject.Singleton

@Singleton
@CompileStatic
class DummyPeopleAuthoritiesFetcher implements AuthoritiesFetcher {

    protected Map<String, List<String>> userAuthorities = [:]

    DummyPeopleAuthoritiesFetcher() {
        userAuthorities.user = ['ROLE_USER']
        userAuthorities.admin = ['ROLE_USER', 'ROLE_ADMIN']
    }

    @Override
    Publisher<List<String>> findAuthoritiesByUsername(String username) {
        Flowable.just userAuthorities[username]
    }
}
