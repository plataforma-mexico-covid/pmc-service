package mx.mexicocovid19.plataforma.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Profile
import spock.lang.Specification
import spock.lang.Unroll

@SpringBootTest
@Profile(["mailoff", "localdb"])
class DefaultAyudaServiceTest extends Specification {

    @Autowired
    private AyudaService ayudaService

    @Unroll("Escenario numero #index se buscan ayudas tipo: #origenAyuda, a menos de :kilometers km de :latitude / :longitude")
    def "Validar consulta de ayudas por distancia y origen de ayuda"() {
        expect:
        total == ayudaService.readAyudas(origenAyuda, longitude, latitude, kilometers).size()

        where:
        index   | origenAyuda   | kilometers    | latitude  | longitude  | total
        0       | "SOLICITA"    | 1             | 19.429386 | -99.158080 | 1
        1       | "SOLICITA"    | 1             | 19.421728 | -99.148887 | 0
        2       | "OFRECE"      | 1             | 19.429386 | -99.158080 | 1
        3       | "AMBOS"       | 1             | 19.429386 | -99.158080 | 2
        4       | "AMBOS"       | 1             | 19.421728 | -99.148887 | 0
    }
    
    
    @Unroll("Escenario numero #index se buscan ayudas tipo: #estatusAyuda")
    def "Validar consulta de ayudas por estatus de ayuda"() {
        expect:
        total == ayudaService.readAyudasByEstatusAyuda(estatusAyuda).size()

        where:
        index   | estatusAyuda	| total
        0       | "NUEVA"		| 10
    }
}
