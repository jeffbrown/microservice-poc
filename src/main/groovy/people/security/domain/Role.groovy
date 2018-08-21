package people.security.domain

import grails.gorm.annotation.Entity
import org.grails.datastore.gorm.GormEntity

@Entity
class Role implements GormEntity<Role> {
    String authority

    static constraints = {
        authority unique: true
    }
}
