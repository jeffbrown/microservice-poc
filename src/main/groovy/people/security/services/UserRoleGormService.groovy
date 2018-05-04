package people.security.services

import grails.gorm.services.Query
import grails.gorm.services.Service
import people.security.domain.Role
import people.security.domain.User
import people.security.domain.UserRole

@Service(UserRole)
interface UserRoleGormService {

    UserRole save(User user, Role role)

    UserRole find(User user, Role role)

    void delete(Serializable id)

    @Query("""select $r.authority
    from ${UserRole ur}
    inner join ${User u = ur.user}
    inner join ${Role r = ur.role}
    where $u.username = $username""")
    List<String> findAllAuthoritiesByUsername(String username)
}
