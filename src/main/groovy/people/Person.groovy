package people

import grails.gorm.annotation.Entity

@Entity
class Person {
    String firstName
    String lastName
    int age
    boolean enabled = true
}
