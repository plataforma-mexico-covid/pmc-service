package mx.mexicocovid19.plataforma.util;

import java.util.List;

import org.owasp.encoder.Encode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import lombok.extern.slf4j.Slf4j;
import mx.mexicocovid19.plataforma.exception.PMCException;
import mx.mexicocovid19.plataforma.model.exception.DefaultErrorList;
import mx.mexicocovid19.plataforma.model.exception.Notification;

@Slf4j
public final class Utils {

	/** constant string ERROR_TAG. */
	private static final String ERROR_TAG = "ERROR FOUND || ";

	/**
	 * Nueva instancia utils.
	 */
	private Utils() {
		throw new UnsupportedOperationException("No instanciar la clase");
	}

	public static String truncateDescription(final String description, int limit){
		if (description == null)
			return "";
		if(description.length() > limit){
			return description.substring(0, limit-4) + "...";
		}
		return description;
	}

	/**
	 * Prints the error binding.
	 *
	 * @param bindingResult the binding result
	 */
	public static void printErrorBinding(BindingResult bindingResult) {
		List<ObjectError> list = bindingResult.getAllErrors();
		/**
		 * se procede a iterar el stack de error para poder imprimir que datos son
		 * invalidos
		 */
		if (!list.isEmpty()) {
			for (Object object : list) {
				if (object instanceof FieldError) {
					FieldError fieldError = (FieldError) object;
					log.error("Binding Name [{}]", Encode.forJava(fieldError.getObjectName()));
					log.error("Property not valid [{}]", Encode.forJava(fieldError.getField()));
					log.error("Value not Valid [{}]", validNull(fieldError.getRejectedValue()));
					log.error("Message [{}]", Encode.forJava(fieldError.getDefaultMessage()));
					log.error("Code [{}]", Encode.forJava(fieldError.getCode()));
				} else if (object instanceof ObjectError) {
					ObjectError objectError = (ObjectError) object;
					log.error("Binding Name [{}]", Encode.forJava(objectError.getObjectName()));
					log.error("Message [{}]", Encode.forJava(objectError.getDefaultMessage()));
					log.error("Code [{}]", Encode.forJava(objectError.getCode()));
				}
			}
		}
	}

	/**
	 * Funcion que permite imprimir una excepcion generada en el log
	 *
	 * @param exception - {@link Throwable} excepcion generada
	 * @param nameClass - {@link String} nombre de la clase donde se genero la
	 *                  execpcion
	 * @param msg       - {@link String} Mensaje que sera enviado al log para ser
	 *                  identificado
	 */
	public static void printError(Throwable exception, String nameClass, String msg) {
		if (exception != null) {
			boolean isFind = false;
			/**
			 * se procede a iterar sobre el stack y buscar el error generado
			 */
			for (StackTraceElement ste : exception.getStackTrace()) {
				if (nameClass.equals(ste.getClassName())) {
					String msgError = "";
					msgError = exception.getMessage();
					log.error("{}{}||{}||{}||{}||{}", Encode.forJava(ERROR_TAG), Encode.forJava(ste.getClassName()),
							Encode.forJava(ste.getMethodName()), Encode.forJava(String.valueOf(ste.getLineNumber())),
							Encode.forJava(msgError), Encode.forJava(msg), exception);
					isFind = true;
					break;
				}
			}
			/**
			 * se valida si se encontro la clase que genero el error
			 */
			if (!isFind) {
				log.error("{}{}||{}", Encode.forJava(ERROR_TAG), Encode.forJava(nameClass), Encode.forJava(msg),
						exception);
			}
		} else {
			/**
			 * En caso contrario se imprime una excepcion generica
			 */
			log.error("{}{}||{}", Encode.forJava(ERROR_TAG), Encode.forJava(nameClass), Encode.forJava(msg), exception);
		}
	}

	/**
	 * Valid null.
	 *
	 * @param object the object
	 * @return Objeto string
	 */
	public static String validNull(Object object) {
		if (object == null) {
			return "null";
		}
		return Encode.forJava(object.toString());
	}

	/**
	 * Validar nulo.
	 *
	 * @param dato the dato
	 * @return Objeto string
	 */
	public static String validarNulo(String dato) {
		/**
		 * se valida si es nulo o si esta vacio
		 */
		if (dato == null || dato.isEmpty()) {
			return "";
		}
		return dato;
	}

	/**
	 * Esta funcion permite validar si un objecto es del tipo de clase referencia
	 * 
	 * @param refClazz {@link Object} Tipo de clase con la cual se validara si es
	 *                 igual al objeto
	 * @param obj      {@link Object} objecto se sera validado si es igual a la
	 *                 clase
	 * @return {@link Boolean} estatus de la validacion
	 */
	public static <T> boolean isEqualObj(T refClazz, Object obj) {
		if (refClazz.getClass() != obj.getClass()) {
			return false;
		}
		try {
			Class<?> clazz = Class.forName(refClazz.getClass().getName());
			return clazz.equals(obj);
		} catch (ClassNotFoundException e) {
			Utils.printError(e, Utils.class.getName(), "No se ha podido crear el objeto");
			return false;
		}
	}
	
	
	/**
	 * Esta funcion permite crear el error de respuesta a partir de una excepcion y
	 * enviarla tambien al log.
	 *
	 * @param exception {@link PMCException} excepcion que genero el error
	 * @param nameClass {@link String} nombre de la clase que genero el error
	 * @param status    {@link HttpStatus} estatus que se configurar como codigo
	 *                  http
	 * @return response {@link ResponseEntity<Object>} respuesta generada
	 */
	public static ResponseEntity<Object> createErrorResponse(PMCException exception, String nameClass,
			HttpStatus status) {
		
		Notification notification = new Notification(exception.getErrorEnum());
		/**
		 * se configura el http code y el cuerpo del error
		 */
		return new ResponseEntity<>(new DefaultErrorList(notification), status);
	}
}
