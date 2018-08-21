package people.email

import groovy.text.SimpleTemplateEngine
import groovy.text.Template
import groovy.text.TemplateEngine
import groovy.transform.CompileStatic
import people.Person
import people.PersonService

import javax.inject.Singleton

@CompileStatic
@Singleton
class ReportService {

    protected final EmailSender emailSender
    protected final PersonService personService

    ReportService(EmailSender emailSender,
                  PersonService personService) {
        this.emailSender = emailSender
        this.personService = personService
    }

    void emailReport() {
        emailSender.sendEmail 'brownj@objectcomputing.com',
                'brownj@objectcomputing.com',
                'Person Report',
                generateEmailBody()
    }

    protected String generateEmailBody() {
        List<Person> disabledPeople = personService.listDisabled()
        List<Person> enabledPeople = personService.listEnabled()

        Map<String, List<Person>> model = [
                disabledPeople: disabledPeople,
                enabledPeople : enabledPeople
        ]

        String text = '''
Enabled People (<%= enabledPeople.size() %>):


<% 
enabledPeople.each { person ->
    println "${person.lastName}, ${person.firstName}"
    println ''
}
%>


Disabled People (<%= disabledPeople.size() %>):


<% 
disabledPeople.each { person ->
    println "${person.lastName}, ${person.firstName}"
    println ''
}
%>

'''
        TemplateEngine engine = new SimpleTemplateEngine()
        Template template = engine.createTemplate text
        template.make model
    }
}
