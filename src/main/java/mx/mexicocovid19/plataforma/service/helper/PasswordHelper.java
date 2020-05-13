package mx.mexicocovid19.plataforma.service.helper;

public final class PasswordHelper {

	private final static String PASSWORD_REGEX_VALIDATION = ""
			+ "^(?=.*[0-9])"
			+ "(?=.*[a-z])"
			+ "(?=.*[A-Z])"
			+ "[a-zA-Z0-9!@#\\$%^&*]"
			+ "(?=\\S+$).{7,15}$";

	/**
	 * DEFAULT CONSTRUCTOR.
	 */
	private PasswordHelper() {
		throw new UnsupportedOperationException("METHOD NOT AVAILABLE");
	}

	/**
	 * 
	 * Este metodo evalua si el texto contiene palabras soeces
	 * 
	 * @param password {@link String} - Password a evular.
	 * @return {@link boolean} - Resultado boleano, True si el password es valido o no.
	 */
	public static boolean passwordIsValid(String password) {
		boolean result = false;
		/**
		 * Evalua el password contra el regex definido
		 */
		if (password.matches(PASSWORD_REGEX_VALIDATION)) {
			result = true;
		}
		return result;
	}

}
