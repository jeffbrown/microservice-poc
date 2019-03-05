package people.email

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule

import javax.inject.Inject

@Controller('/report')
@Secured(SecurityRule.IS_ANONYMOUS)
class ReportController {

    @Inject
    ReportService reportService

    @Post("/people")
    @Secured('ROLE_ADMIN')
    HttpResponse people() {
        reportService.emailReport()
        HttpResponse.ok()
    }
}
