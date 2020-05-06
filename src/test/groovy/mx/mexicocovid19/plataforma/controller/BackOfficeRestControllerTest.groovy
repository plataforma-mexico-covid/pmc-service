package mx.mexicocovid19.plataforma.controller

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

import mx.mexicocovid19.plataforma.PlataformaApp
import mx.mexicocovid19.plataforma.config.security.JwtTokenUtil
import spock.lang.Specification
import spock.lang.Unroll

@SpringBootTest(classes = PlataformaApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Profile(["mailoff", "localdb"])
class BackOfficeRestControllerTest extends Specification {
	@LocalServerPort
	private int port

	@Autowired
	private TestRestTemplate restTemplate
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil

	@Unroll("Escenario numero #index para consultar ayudas por #estatusAyuda y se espera statusCode #status")
	def "Consulta de AYUDAS por ESTADO de ATENCION"() {
		expect:
		
		String token = jwtTokenUtil.generateToken("admin@pmc.mx", "p4Ssword", new LiteDevice(DeviceType.MOBILE, DevicePlatform.ANDROID), null)
		HttpHeaders headers = new HttpHeaders()
		headers.add("X-Auth-Token", token)
		def requestEntity = new HttpEntity(headers);
		
		def uri = "http://localhost:" + port + "/api/v1/private/backoffice/ayuda/estatus?estatus=" + estatusAyuda
		ResponseEntity<List> result = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, List.class)
		status == result.statusCode
		size == result.getBody().size()

		where:
		index	|	estatusAyuda	|	status			|	size		
		0		|	"NUEVA"			|	HttpStatus.OK	|	9
	}
	
}
