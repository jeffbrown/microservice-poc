package people

import grails.gorm.annotation.Entity
import org.grails.datastore.gorm.GormEntity

@Entity
class Person implements GormEntity<Person> {
    Long id
    String firstName
    String lastName
    int age
    boolean enabled = true
}
