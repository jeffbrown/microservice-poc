package people.security.domain

import grails.gorm.annotation.Entity
import groovy.transform.CompileStatic

@Entity
@CompileStatic
class UserRole {
    User user
    Role role
}
