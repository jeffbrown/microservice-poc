package people.security.domain

import grails.gorm.annotation.Entity
import groovy.transform.CompileStatic
import org.grails.datastore.gorm.GormEntity

@Entity
@CompileStatic
class UserRole implements GormEntity<UserRole> {
    User user
    Role role
}
