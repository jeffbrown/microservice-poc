package people

import groovy.json.JsonSlurper
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.MutableHttpRequest
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient

@CompileStatic
trait SpecRequestHelper {

    JsonSlurper jsonSlurper = new JsonSlurper()

    HttpResponse executeGetRequest(String url) {
        client.toBlocking()
                .exchange(HttpRequest.GET(url), Argument.of(String))
    }

    HttpResponse executePostRequest(String url, String jsonBody, String token) {
        MutableHttpRequest<String> postRequest = createPostRequest(url, jsonBody)
        postRequest.header('Authorization', "Bearer $token")
        def blocking = client.toBlocking()
        blocking.exchange(postRequest, Argument.of(String))
    }

    HttpResponse executePostRequest(String url, String jsonBody) {
        MutableHttpRequest<String> postRequest = createPostRequest(url, jsonBody)
        client.toBlocking().exchange(postRequest, Argument.of(String))
    }

    HttpResponse executePutRequest(String url, String jsonBody, String token) {
        MutableHttpRequest<String> putRequest = createPutRequest(url, jsonBody)
        putRequest.header('Authorization', "Bearer $token")
        client.toBlocking().exchange(putRequest, Argument.of(String))
    }

    HttpResponse executePutRequest(String url, String jsonBody) {
        MutableHttpRequest<String> putRequest = createPutRequest(url, jsonBody)
        client.toBlocking().exchange(putRequest, Argument.of(String))
    }

    MutableHttpRequest<String> createPostRequest(String url, String jsonBody) {
        HttpRequest.POST(url, jsonBody).contentType(MediaType.APPLICATION_JSON)
    }

    MutableHttpRequest<String> createPutRequest(String url, String jsonBody) {
        HttpRequest.PUT(url, jsonBody).contentType(MediaType.APPLICATION_JSON)
    }

    @CompileDynamic
    String getJwtToken(String userName, String password) {
        def jsonBodyString = "{\"username\":\"${userName}\",\"password\":\"${password}\"}"
        def responseString = client.toBlocking().retrieve(
                HttpRequest.POST('/login', jsonBodyString)
                        .contentType(MediaType.APPLICATION_JSON))
        def json = jsonSlurper.parseText responseString

        json.accessToken
    }

    def parseJson(String response) {
        jsonSlurper.parseText response
    }

    abstract HttpClient getClient()
}
