package people.security.services

import grails.gorm.services.Service
import people.security.domain.User

@Service(User)
interface UserGormService {

    User save(String username, String password)

    User findByUsername(String username)

    User findById(Serializable id)

    void delete(Serializable id)
}
