package com.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;

/**
 * Clase que define las constantes del proyecto
 * 
 * @author Manuel Guerrero del Pino
 * @version 1.0
 */

public class Constantes {
	private Constantes() {
	}

	private static Logger _logger = LoggerFactory.getLogger(Constantes.class);

	// Constantes para calendario de fechas
	public static final String CAL_START = "01/01/1998";
	public static final String CAL_FINAL; // Se inicia en el static de la
	// clase

	// Titulo de la aplicacion
	public static final String APP_NAME = "Gestión UDIMA Center";

	public static String SES_USER_KEY = "_SESSIONUSER_";
	public static String USER_KEY = "1";
	public static final String PARAM_MENU = "menu";
	public static final String PARAM_ACCESO = "tipo";
	
	// SQL
	public static final String SQL_WHERE = "WHERE ";
	public static final String SQL_AND = "AND ";

	// Inicializador estático
	static {
		// Fecha tope para calendarios. Dia actual + 2 anos
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.roll(Calendar.YEAR, 2);
		CAL_FINAL = sdf.format(cal.getTime());
	}
}
