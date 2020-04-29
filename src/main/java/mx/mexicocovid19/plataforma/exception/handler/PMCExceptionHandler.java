package mx.mexicocovid19.plataforma.exception.handler;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.google.gson.Gson;

import lombok.extern.log4j.Log4j2;
import mx.mexicocovid19.plataforma.exception.PMCException;
import mx.mexicocovid19.plataforma.model.exception.DefaultErrorList;
import mx.mexicocovid19.plataforma.model.exception.Notification;
import mx.mexicocovid19.plataforma.util.ErrorEnum;

@Log4j2
@ControllerAdvice
public class PMCExceptionHandler implements Serializable{
	

	private static final long serialVersionUID = -3903839705643975593L;

	/**
	 * Handle activation exception.
	 *
	 * @param ex El objeto: ex
	 * @return Objeto response entity
	 */
	@ExceptionHandler(PMCException.class)
	ResponseEntity<DefaultErrorList> handleActivationException(PMCException ex) {
		Notification notification = new Notification(ex.getCode(), ex.getMessage(), "ERROR");
		log.error(ex.getMessage());
		if ( ex.getErrorEnum() == ErrorEnum.ERR_MAX_AYUDA ) {
			return new ResponseEntity<>(new DefaultErrorList(notification), HttpStatus.TOO_MANY_REQUESTS);
		} else {
			return new ResponseEntity<>(new DefaultErrorList(notification), HttpStatus.BAD_REQUEST);
		}
	}


	/**
	 * Handle generic exception.
	 *
	 * @param ex El objeto: ex
	 * @return Objeto response entity
	 */
	@ExceptionHandler(Exception.class)
	ResponseEntity<DefaultErrorList> handleGenericException(Exception ex) {
		Notification notification = new Notification(ErrorEnum.ERR_GENERICO.getCode(), ex.getMessage(), ErrorEnum.ERR_GENERICO.getLevel());
		String jsonError = new Gson().toJson(notification);
		log.error(jsonError);
		return new ResponseEntity<>(new DefaultErrorList(notification), HttpStatus.INTERNAL_SERVER_ERROR);
	}


	/**
	 * Esta funcion nos permitira gestionar todas las excepciones que se generen
	 * debido a un error de I/O
	 * 
	 * @param e       {@link IOException} excepcion generada
	 * @param request {@link HttpServletRequest} request donde se genero la
	 *                excepcion para verificar que no haya sido por un corte de cliente
	 * @return
	 */
	@ExceptionHandler(IOException.class)
	ResponseEntity<DefaultErrorList> handleIOException(IOException e, HttpServletRequest request) {
		/**
		 * Comparamos que el cliente haya cortado la conexion, si es el caso no
		 * devolvemos nada en caso contrario debemos devolver un Internal Error 500 por
		 * un error de IO
		 */
		log.error(e.getMessage());
		if (StringUtils.containsIgnoreCase(ExceptionUtils.getRootCauseMessage(e), "Broken pipe")) { // (2)
			return null; // (2) socket is closed, cannot return any response
		} else {
			return new ResponseEntity<>(new DefaultErrorList(new Notification(ErrorEnum.ERR_GENERICO)),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}