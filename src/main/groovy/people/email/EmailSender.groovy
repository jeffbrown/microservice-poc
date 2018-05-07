package people.email

interface EmailSender {
    void sendEmail(String recipientEmail,
                   String senderEmail,
                   String subject,
                   String body)
}
