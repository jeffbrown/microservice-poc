package people

import grails.gorm.services.Query
import grails.gorm.services.Service
import grails.gorm.services.Where
import groovy.transform.CompileStatic

@Service(Person)
@CompileStatic
interface PersonService {
    int count()

    Person get(long id)

    Person savePerson(String firstName,
                      String lastName,
                      int age)

    Person savePerson(String firstName,
                      String lastName,
                      int age,
                      boolean enabled)

    List<Person> list()

    @Where({ enabled == true })
    List<Person> listEnabled()

    @Where({ enabled == false })
    List<Person> listDisabled()

    Person save(Person person)

    @Query("update ${Person person} set ${person.enabled} = false where person.id = $id")
    Number disable(Long id)

    @Query("update ${Person person} set ${person.enabled} = true where person.id = $id")
    Number enable(Long id)
}
