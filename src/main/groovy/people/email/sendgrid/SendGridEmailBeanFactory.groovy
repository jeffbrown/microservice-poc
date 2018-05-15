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
        final String sendgridApiKey = 'http://metadata.google.internal/computeMetadata/v1/project/attributes/SENDGRID_API_KEY'.toURL().getText(requestProperties: ['Metadata-Flavor': 'Google'])
        new SendGrid(sendgridApiKey)
    }
}
