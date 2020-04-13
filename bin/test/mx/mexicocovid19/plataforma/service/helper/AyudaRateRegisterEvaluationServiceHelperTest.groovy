package mx.mexicocovid19.plataforma.service

import mx.mexicocovid19.plataforma.service.helper.AyudaRateRegisterEvaluationServiceHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Profile
import spock.lang.Specification
import spock.lang.Unroll

@SpringBootTest
@Profile(["mailoff", "localdb"])
class AyudaRateRegisterEvaluationServiceHelperTest extends Specification {

    @Autowired
    private AyudaRateRegisterEvaluationServiceHelper service

    @Unroll("Escenario #index prueba helper para evaluar el numero de peticiones de '#input' ")
    def "Validar consulta de ayudas por distancia y origen de ayuda"() {
        expect:
        output == service.isMaximumRequestsPerHourExceeded(input)

        where:
        index 	| input 		| output
        0 		| "mra_capri"   | false
        1 		| "mra_capri_2"	| false
        2 		| "mra_capri_2"	| false
        3 		| "mra_capri_2"	| true
        4 		| "mra_capri"   | false
        5 		| "mra_capri"   | true
    }
}
