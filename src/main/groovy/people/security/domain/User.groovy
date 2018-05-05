package people.security.domain

import grails.gorm.annotation.Entity
import io.micronaut.security.authentication.providers.UserState

@Entity
class User implements UserState {
    String username
    String password
    boolean enabled = true
    boolean accountExpired = false
    boolean accountLocked = false
    boolean passwordExpired = false

    static constraints = {
        username blank: false, unique: true
        password blank: false
    }

    static mapping = {
        table 'users'
    }
}
