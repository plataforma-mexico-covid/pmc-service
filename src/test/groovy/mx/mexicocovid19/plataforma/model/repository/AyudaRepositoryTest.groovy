package mx.mexicocovid19.plataforma.model.repository

import mx.mexicocovid19.plataforma.model.entity.EstatusAyuda
import mx.mexicocovid19.plataforma.model.entity.OrigenAyuda
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Profile
import spock.lang.Specification
import spock.lang.Unroll

@SpringBootTest
@Profile(["mailoff", "localdb"])
class AyudaRepositoryTest extends Specification {

    @Autowired
    private AyudaRepository ayudaRepository

    @Unroll("Escenario numero #index se buscan ayudas tipo: #origenAyuda, a menos de :kilometers km de :latitude / :longitude")
    def "Validar consulta de ayudas por distancia y origen de ayuda"() {
        expect:
        total == ayudaRepository.findByAllInsideOfKilometersByOrigenAyuda(latitude, longitude, kilometers, origenAyuda).size()

        where:
        index   | origenAyuda           | kilometers    | latitude  | longitude  | total
        0       | OrigenAyuda.SOLICITA  | 1             | 19.429386 | -99.158080 | 1
        1       | OrigenAyuda.SOLICITA  | 1             | 19.421728 | -99.148887 | 0
        2       | OrigenAyuda.OFRECE    | 1             | 19.429386 | -99.158080 | 0
    }

    @Unroll("Escenario numero #index se buscan ayudas a menos de :kilometers km de :latitude / :longitude")
    def "Validar consulta de ayudas por distancia "() {
        expect:
        total == ayudaRepository.findByAllInsideOfKilometers(latitude, longitude, kilometers).size()

        where:
        index   | kilometers    | latitude  | longitude  | total
        0       | 1             | 19.429386 | -99.158080 | 1
        1       | 1             | 19.421728 | -99.148887 | 0
    }

    @Unroll("Escenario numero #index se buscan ayudas a menos de :kilometers km de :latitude / :longitude")
    def "Validar consulta de ayudas por filtro "() {
        expect:
        total == ayudaRepository.findByFilter(estatusAyuda, origenAyuda, search).size()

        where:
        index   | estatusAyuda              | origenAyuda           | search        | total
        0       | EstatusAyuda.PENDIENTE    | OrigenAyuda.SOLICITA  | ""            | 3
        1       | EstatusAyuda.NUEVA        | OrigenAyuda.OFRECE    | ""            | 0
        2       | EstatusAyuda.PENDIENTE    | OrigenAyuda.OFRECE    | ""            | 0
        3       | EstatusAyuda.NUEVA        | OrigenAyuda.SOLICITA  | "hemodi"      | 5
        4       | null                      | null                  | "hemodi"      | 6
        5       | null                      | null                  | "Apoyo Legal" | 1
        6       | EstatusAyuda.PENDIENTE    | null                  | ""            | 3
        99      | null                      | null                  | ""            | 12
    }
}
