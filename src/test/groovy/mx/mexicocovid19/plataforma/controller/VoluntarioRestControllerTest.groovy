package mx.mexicocovid19.plataforma.controller

import groovy.json.JsonSlurper
import mx.mexicocovid19.plataforma.PlataformaApp
import mx.mexicocovid19.plataforma.config.security.JwtTokenUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.context.annotation.Profile
import org.springframework.http.*
import org.springframework.mobile.device.DevicePlatform
import org.springframework.mobile.device.DeviceType
import org.springframework.mobile.device.LiteDevice
import spock.lang.Specification
import spock.lang.Unroll

@SpringBootTest(classes = PlataformaApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Profile(["mailoff", "localdb"])
class VoluntarioRestControllerTest extends Specification {
    @LocalServerPort
    private int port

    @Autowired
    private TestRestTemplate restTemplate
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil

    @Unroll("Escenario numero #index e intenta registrar voluntario y se espera statusCode #status")
    def "Validar registro de voluntario"() {
        expect:
        
		String token = jwtTokenUtil.generateToken("citizen_dos@pmc.mx", "p4Ssword", new LiteDevice(DeviceType.MOBILE, DevicePlatform.ANDROID), null)
        HttpHeaders headers = new HttpHeaders()
        headers.add("X-Auth-Token", token)
        
        def uri = "http://localhost:" + port + "/api/v1/private/voluntario"
        def request = new File("src/test/resources/json/" + filename).text
        def requestMap = new JsonSlurper().parseText(request)
        def requestEntity = new HttpEntity(requestMap, headers);
        


        ResponseEntity<Void> result = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, Void.class)
        status == result.statusCode

        where:
        index   | filename              			| status
        0       | "in-create-voluntario.json" 		| HttpStatus.OK
        1       | "in-create-voluntario-fail.json"	| HttpStatus.INTERNAL_SERVER_ERROR
    }
    
}
