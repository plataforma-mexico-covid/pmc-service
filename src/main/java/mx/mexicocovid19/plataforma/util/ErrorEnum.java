package mx.mexicocovid19.plataforma.util;

public enum ErrorEnum {

	/** Error general para la clase del manejo de excepciones */
	ERR_GENERICO("ERR-000", "Error generico", "", ErrorConst.ERROR, ""),
	
	/** Errores de servicio y logica de negocio */
	ERR_LENGUAJE_SOEZ("ERR-001", "Uso de lengaje soez e inapropiado", "", ErrorConst.WARNING, ""),
	ERR_MAX_AYUDA("ERR-002", "Ha rebasado el numero maximo de ayuda, intenta pasada la hora.", "", ErrorConst.WARNING, ""),
	ERR_UNAVAILABLE_ACCOUNT("ERR-003", "", "Este correo '%s' ya fue utilizado por otra cuenta.", ErrorConst.WARNING, ""),
	
	ERR_INVALID_PASSWORD("ERR-004",
			"Password invalido, minimo 1 mayuscula, minimo 1 numero, minimo 8 caracters y caracteres especiales -*_#@().!%&",
			"", ErrorConst.ERROR, ""),
	
	ERR_REGISTRO_CIUDADANO("ERR-005", "Es necesario especificar .", "", ErrorConst.WARNING, ""),
	
	ERR_RECUPERACION_PASSWORD("ERR-006", "Proceso de recuperacion", "", ErrorConst.ERROR, ""),

	ERR_CONFIRMAR_EMAIL("ERR-007", "Proceso de confirmacion de email", "", ErrorConst.ERROR, ""),
	
	/** Errores de autorizacion */	
	ERR_AUTH_TOKEN_REQUERIDO("ERR-AUTH-001", "Token requerido", "", ErrorConst.ERROR, "");

	/** The code. */
	/**guarda el codigo de error o exitoso*/
    private final String code;

    /** The message. */
    /**guarda el mesaje del error */
    private final String message;

    /** The description. */
    /**guarda la descripcion relacionada al error*/
    private final String description;

    /** The level. */
    /**guarda el nivel en el que ocurre la excepcion*/
    private final String level;

    /** The more info. */
    /**almacena los datos adicionales*/
    private final String moreInfo;


	/**
	 * Nueva instancia de error enum.
	 *
	 * @param code        objeto: code
	 * @param message     objeto: message
	 * @param description objeto: description
	 * @param level       objeto: level
	 * @param moreInfo    objeto: more info
	 */
	ErrorEnum(final String code, final String message, final String description, final String level,
			final String moreInfo) {
		this.code = code;
		this.message = message;
		this.description = description;
		this.level = level;
		this.moreInfo = moreInfo;
	}

	/**
	 * Obtiene el objeto: code.
	 *
	 * @return el valor: code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Obtiene el objeto: message.
	 *
	 * @return el valor: message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Obtiene el objeto: description.
	 *
	 * @return el valor: description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Obtiene el objeto: level.
	 *
	 * @return el valor: level
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * Obtiene el objeto: more info.
	 *
	 * @return el valor: more info
	 */
	public String getMoreInfo() {
		return moreInfo;
	}

}
