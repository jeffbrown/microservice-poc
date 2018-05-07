package people.email.sendgrid

import com.sendgrid.SendGrid
import people.email.EmailSender

import javax.inject.Singleton

@Singleton
class SendGridEmailSenderService implements EmailSender {

    protected final SendGrid sendGrid

    SendGridEmailSenderService(SendGrid sendGrid) {
        this.sendGrid = sendGrid
    }

    @Override
    void sendEmail(String recipientEmail,
                   String senderEmail,
                   String subject,
                   String body) {
        SendGrid.Email email = new SendGrid.Email()
        email.addTo recipientEmail
        email.from = senderEmail
        email.subject = subject
        email.text = body

        sendGrid.send email
    }
}
