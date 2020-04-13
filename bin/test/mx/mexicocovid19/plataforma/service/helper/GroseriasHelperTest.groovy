package mx.mexicocovid19.plataforma.service.helper

import spock.lang.Specification
import spock.lang.Unroll

class GroseriasHelperTest extends Specification {

    @Unroll("Escenario numero #index texto a validar: '#input', contiene groserias? #output")
    def "Validar que un texto no contenga groserias"() {
        expect:
        output == GroseriasHelper.evaluarTexto(input)

        where:
        index | input | output
        0 | "Necesito ayuda para ir a mi cita del doctor"   				| false
        1 | "Vola de pendejo dejen de hacer esto"           				| true
        2 | "CABRON ponganse a hacer algo"                  				| true
        3 | "Vola de pendejos dejen de hacer esto"          				| true
        4 | "CABRONES ponganse a hacer algo"                				| true
		5 | "Nomas quieren robar a la gente, pinches lacras!"				| true
		6 | "Orale putos, prueben bien no sean irresponsables"				| true
		7 | "Esto que esta pasando es una mierda, ayudenos"					| true
    }
}
