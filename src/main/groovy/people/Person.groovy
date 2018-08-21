package people

import grails.gorm.annotation.Entity
import org.grails.datastore.gorm.GormEntity

@Entity
class Person implements GormEntity<Person> {
    String firstName
    String lastName
    int age
    boolean enabled = true
}
