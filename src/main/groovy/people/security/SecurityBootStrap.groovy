package people.security

import groovy.transform.CompileStatic
import io.micronaut.context.event.ApplicationEventListener
import io.micronaut.runtime.server.event.ServerStartupEvent
import people.security.services.RegisterService

import javax.inject.Singleton

@Singleton
@CompileStatic
class SecurityBootStrap implements ApplicationEventListener<ServerStartupEvent> {

    final RegisterService registerService

    SecurityBootStrap(RegisterService registerService) {
        this.registerService = registerService
    }

    @Override
    void onApplicationEvent(ServerStartupEvent event) {
        registerService.register 'admin', 'password', ['ROLE_ADMIN', 'ROLE_USER']
        registerService.register 'user', 'password', ['ROLE_USER']
    }
}
