package people.security

import groovy.transform.CompileStatic
import io.micronaut.security.authentication.providers.UserFetcher
import io.micronaut.security.authentication.providers.UserState
import io.reactivex.Flowable
import org.reactivestreams.Publisher

import javax.inject.Singleton

@CompileStatic
@Singleton
class DummyPeopleUserFetcher implements UserFetcher {

    protected Map<String, UserState> userStateMap = [:]

    DummyPeopleUserFetcher() {
        userStateMap.admin = new DummyUserState('admin')
        userStateMap.user = new DummyUserState('user')
    }

    @Override
    Publisher<UserState> findByUsername(String username) {
        Flowable.just userStateMap[username]
    }

    private static class DummyUserState implements UserState {
        String username
        String password
        boolean enabled = true
        boolean accountExpired = false
        boolean accountLocked = false
        boolean passwordExpired = false

        DummyUserState(String username) {
            this.password = "password"
            this.username = username
        }
    }
}
