package people.security.domain

import grails.gorm.annotation.Entity

@Entity
class Role {
    String authority

    static constraints = {
        authority unique: true
    }
}
