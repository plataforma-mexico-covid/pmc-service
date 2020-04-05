package mx.mexicocovid19.plataforma.controller

import mx.mexicocovid19.plataforma.PlataformaApp
import mx.mexicocovid19.plataforma.config.security.JwtTokenUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.mobile.device.DevicePlatform
import org.springframework.mobile.device.DeviceType
import org.springframework.mobile.device.LiteDevice
import spock.lang.Specification

@SpringBootTest(classes = PlataformaApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Profile(["mailoff", "localdb"])
class AddressRestControllerTest extends Specification {
    @LocalServerPort
    private int port

    @Autowired
    private TestRestTemplate restTemplate

    @Autowired
    private JwtTokenUtil jwtTokenUtil

    def "HashMap accepts null key"() {
        given:
        def uri = "http://localhost:" + port + "/api/v1/private/address/province"
        String token = jwtTokenUtil.generateToken("admin@pmc.mx", "Admin Admin", new LiteDevice(DeviceType.MOBILE, DevicePlatform.ANDROID), null)
        HttpHeaders headers = new HttpHeaders()
        headers.add("X-Auth-Token", token)

        when:
        ResponseEntity<List> result = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), List.class)

        then:
        result.statusCode == HttpStatus.OK
        result.getBody().size() == 6
    }
}
