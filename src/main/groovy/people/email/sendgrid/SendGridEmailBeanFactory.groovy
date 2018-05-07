package people.email.sendgrid

import com.sendgrid.SendGrid
import groovy.transform.CompileStatic
import io.micronaut.context.annotation.Factory

import javax.inject.Singleton

@Factory
@CompileStatic
class SendGridEmailBeanFactory {

    @Singleton
    SendGrid sendGrid() {
        final String sendgridApiKey = System.getenv 'SENDGRID_API_KEY'
        new SendGrid(sendgridApiKey)
    }
}
