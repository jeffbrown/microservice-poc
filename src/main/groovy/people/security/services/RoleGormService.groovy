package people.security.services

import grails.gorm.services.Service
import people.security.domain.Role

@Service(Role)
interface RoleGormService {
    Role save(String authority)

    Role find(String authority)

    void delete(Serializable id)
}
