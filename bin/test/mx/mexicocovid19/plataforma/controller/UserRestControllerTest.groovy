package mx.mexicocovid19.plataforma.controller

import groovy.json.JsonSlurper
import mx.mexicocovid19.plataforma.PlataformaApp
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification
import spock.lang.Unroll

@SpringBootTest(classes = PlataformaApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Profile(["mailoff", "localdb"])
class UserRestControllerTest extends Specification {
    @LocalServerPort
    private int port

    @Autowired
    private TestRestTemplate restTemplate

    @Unroll("Escenario numero #index se intenta registrar un usuario y se espera statusCode #status")
    def "Validar registro de usuarios"() {
        expect:
        def uri = "http://localhost:" + port + "/api/v1/public/users"
        def request = new File("src/test/resources/json/" + filename).text
        def requestMap = new JsonSlurper().parseText(request)
        def requestEntity = new HttpEntity(requestMap);

        ResponseEntity<Void> result = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, Void.class)
        status == result.statusCode

        where:
        index   | filename              					| status
        0       | "in-create-user.json" 					| HttpStatus.CREATED
        1       | "in-create-user-invalid-password.json" 	| HttpStatus.BAD_REQUEST
		2       | "in-create-user-sin-info-contacto.json" 	| HttpStatus.BAD_REQUEST
    }
	
}
