package mx.mexicocovid19.plataforma.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public final class DateUtil {
	
	
	/**
	 * Nueva instancia DateUtil.
	 */
	private DateUtil() {
		throw new UnsupportedOperationException("No instanciar la clase");
	}

	
	/** {@link String} DATE_FORMAT formato para la salida de SimpleDateFormat. */
	private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss:SSS'Z'";

	/** {@link String} TIMEZONE_MEXICO Zona horaria de mexico centro. */
	public static final String TIMEZONE_MEXICO = "America/Mexico_City";
	
	
    public static String formatDTO(final LocalDateTime date){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return df.format(date);
    }

    public static LocalDateTime parseDTO(final String date){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return LocalDateTime.parse(date, df);
    }

	public static boolean isExpired(final Date expirationDate) {
		return LocalDateTime.now().isAfter(convertToLocalDateTimeViaMilisecond(expirationDate));
	}

	public static LocalDateTime convertToLocalDateTimeViaMilisecond(final Date dateToConvert) {
    	return Instant.ofEpochMilli(dateToConvert.getTime())
				.atZone(ZoneId.of(TIMEZONE_MEXICO))
				.toLocalDateTime();
	}
    
	/**
	 * This function allows get date in format ISO 8601.
	 *
	 * @return {@link String} - Date in format ISO 8601
	 */
	public static String getDateIso() {
		/**
		 * se crea la fecha tomada desde el sistema
		 */
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf;
		sdf = new SimpleDateFormat(DATE_FORMAT);
		/**
		 * se configura el timezone a la ciudad de Mexico
		 */
		sdf.setTimeZone(TimeZone.getTimeZone(TIMEZONE_MEXICO));
		return sdf.format(date);
	}

	/**
	 * Obtener el objeto: date isos.
	 *
	 * @return El objeto: date isos
	 */
	public static Date getDateIsos() {
		Date date;
		try {
			/**
			 * se crea una fecha con el ISO
			 */
			date = new SimpleDateFormat(DATE_FORMAT).parse(getDateIso());
		} catch (ParseException e) {
			Utils.printError(e, Utils.class.getName(), e.getMessage());
			date = new Date();
		}
		return date;
	}
}
