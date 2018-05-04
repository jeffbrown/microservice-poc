package people

import groovy.json.JsonSlurper
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import io.micronaut.http.HttpRequest
import io.micronaut.http.MediaType
import io.micronaut.http.MutableHttpRequest
import io.micronaut.http.client.HttpClient

@CompileStatic
trait SpecRequestHelper {

    JsonSlurper jsonSlurper = new JsonSlurper()

    String executeGetRequest(String url) {
        client.toBlocking()
                .retrieve(HttpRequest.GET(url))
    }

    String executePostRequest(String url, String jsonBody, String token) {
        MutableHttpRequest<String> postRequest = createPostRequest(url, jsonBody)
        postRequest.header('Authorization', "Bearer $token")
        client.toBlocking().retrieve(postRequest)
    }

    String executePostRequest(String url, String jsonBody) {
        MutableHttpRequest<String> postRequest = createPostRequest(url, jsonBody)
        client.toBlocking().retrieve(postRequest)
    }

    String executePutRequest(String url, String jsonBody, String token) {
        MutableHttpRequest<String> putRequest = createPutRequest(url, jsonBody)
        putRequest.header('Authorization', "Bearer $token")
        client.toBlocking().retrieve(putRequest)
    }

    String executePutRequest(String url, String jsonBody) {
        MutableHttpRequest<String> putRequest = createPutRequest(url, jsonBody)
        client.toBlocking().retrieve(putRequest)
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
