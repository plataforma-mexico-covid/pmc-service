package mx.mexicocovid19.plataforma.service.helper

import spock.lang.Specification
import spock.lang.Unroll

class PasswordHelperTest extends Specification {

    @Unroll("Escenario numero #index a validar el passord: '#input' -  #output")
    def "Validacion de password"() {
        expect:
        output == PasswordHelper.passwordIsValid(input)

        where:
        index 	| input 						| output
        0 		| "mypassword"   				| false
        1 		| "00000000"          			| false
        2 		| "AlphaRomeo4c"      			| false
        3 		| "fiatlinea2014"     			| false
        4 		| "F@rd1co"           			| false
		5 		| "F@rd1coSports"				| true
		6 		| "Suzuki@lpha2016"				| true
		7 		| "!vwvento2015"				| false
		8 		| "!@#%^&*Aa1"					| true
		9 		| "myDream1@\$5"				| true
		10 		| "Hello World@001"				| false
    }
}
