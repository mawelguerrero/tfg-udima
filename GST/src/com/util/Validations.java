package com.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.forms.DataBean;

/**
 * Clase para la validación de los diferentes campos
 * 
 * @author Manuel Guerrero del Pino
 * @version 1.0
 */

public class Validations {

	private static Logger _logger = LoggerFactory.getLogger(Validations.class);

	private static String LETTERS = "áéíóúÁÉÍÓÚabcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
	private static String DIGITS = "0123456789";
	private static String ALFANUMERICO = LETTERS + DIGITS;
	private static String PUNTUACION = ".-?¿!¡,";
	private static String SPECIALLETTERS = LETTERS + PUNTUACION;
	private static String SPECIALALFANUMERICO = ALFANUMERICO + PUNTUACION;
	private static String MAIL_VALID = LETTERS + DIGITS + "._@-";
	private static String COMA = ",";

	// Validación del campo nombre para el buscador de LIDA
	private static String NOMBRE = LETTERS + "-ª'.&,";

	public static boolean validatePlatform(String platform) {
		platform = platform.replace(' ', '*');
		if (validateNumber(platform)) {
			return true;
		} else {
			String primeraPlataforma = platform.substring(0, platform
					.indexOf(COMA));
			String segundaPlataforma = platform.substring(platform
					.indexOf(COMA) + 1, platform.length());
			if (validateNumber(primeraPlataforma)
					&& validateNumber(segundaPlataforma)) {
				return true;
			}
		}
		return false;
	}

	public static boolean validateDate(String date, String format) {
		_logger.debug("Validando fecha '" + date + "' con formato '" + format
				+ "'");

		SimpleDateFormat sdf = new SimpleDateFormat(format);

		sdf.setLenient(false);
		try {
			java.util.Date d = sdf.parse(date.trim());
		} catch (Exception ex) {
			return false;
		}

		if (format.trim().equals("dd/mm/yyyy")
				|| format.trim().equals("dd/mm/yyyy HH:mm:ss")) {

			if (format.trim().equals("dd/mm/yyyy HH:mm:ss")) {
				return comprobarFechaHHMMSS(date.trim().substring(0, 11));
			} else {
				return comprobarFecha(date.trim());
			}

		}

		return true;

	}

	public static boolean esFechaMayorActual(String strDate, int numAnnos,
			int numMeses, int numDias) {

		Calendar calFec = null;
		Calendar calFecHoy = null;
		StringTokenizer strFecha = new StringTokenizer(strDate, "/");
		try {
			int dia = Integer.parseInt(strFecha.nextToken());
			int mes = Integer.parseInt(strFecha.nextToken());
			int anho = Integer.parseInt(strFecha.nextToken());

			calFec = Calendar.getInstance();
			calFec.set(anho, (mes - 1), dia);

			calFecHoy = Calendar.getInstance();
			calFecHoy.add(Calendar.YEAR, numAnnos);
			calFecHoy.add(Calendar.MONTH, numMeses);
			calFecHoy.add(Calendar.DATE, numDias);

		} catch (Exception ex) {
			return false;
		}

		return (calFec.after(calFecHoy));
	}

	public static boolean esFechaMenorActual(String strDate) {

		Calendar calFecHoy = Calendar.getInstance();
		Calendar calFec = null;
		StringTokenizer strFecha = new StringTokenizer(strDate, "/");
		try {
			int dia = Integer.parseInt(strFecha.nextToken());
			int mes = Integer.parseInt(strFecha.nextToken());
			int anho = Integer.parseInt(strFecha.nextToken());

			calFec = Calendar.getInstance();
			// Convierte la Fecha de String (dd/mm/yyyy) a Calendar
			// Al mes se le resta uno, porque para Calendar January=0
			calFec.set(anho, (mes - 1), dia);
		} catch (Exception ex) {
			return false;
		}
		return calFecHoy.after(calFec);
	}

	public static boolean validateCIF(String elCIF) {

		_logger.debug("Validando CIF '" + elCIF + "'");

		try {
			boolean resul = false;
			String tempCIF = elCIF.toUpperCase(); // pasar a mayúsculas
			char letrasValidas[] = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
					'K', 'L', 'M', 'N', 'P', 'Q', 'S' };

			if (tempCIF.length() != 9) {
				// Son 9 dígitos?
				_logger.debug("CIF distinto de 9 letras");
				return false;
			} else {
				char letra = tempCIF.charAt(0);
				for (int cont = 0; cont < letrasValidas.length; cont++) {
					if (letra == letrasValidas[cont]) {
						resul = true;
					}
				}
				if (!resul) {
					_logger.debug("CIF no empieza por letra válida");
					return false;
				}
			}

			int v1[] = { 0, 2, 4, 6, 8, 1, 3, 5, 7, 9 };
			char letrasControl[] = { 'J', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
					'H', 'I' };
			int temp = 0;
			int temp1;

			for (int i = 2; i <= 6; i += 2) {
				temp = temp + v1[Integer.parseInt(tempCIF.substring(i - 1, i))];
				temp = temp + Integer.parseInt(tempCIF.substring(i, i + 1));
			}
			;

			temp = temp + v1[Integer.parseInt(tempCIF.substring(7, 8))];

			temp = (10 - (temp % 10));

			String control = "";
			if (temp == 10) {
				control = "0";
			} else {
				control = Integer.toString(temp);
			}

			if (tempCIF.substring(8).equals(control)
					|| tempCIF.charAt(8) == letrasControl[Integer
							.parseInt(control)]) {
				return true;
			} else {
				_logger.debug("Dígito de control no válido");
				return false;
			}

		} catch (Exception e) {
			return false;
		}
	}

	public static boolean validateNIE(String theNIE) {

		_logger.debug("Validando NIE '" + theNIE + "'");

		if (theNIE == null || theNIE.equals("")) {
			return false;
		}
		String letra = theNIE.substring(0, 1).toUpperCase();

		if (!letra.equals("X") || theNIE.length() < 2) {
			_logger.debug("NIE no empieza por X");
			return false;
		} else {
			return validateNIF(theNIE.substring(1));
		}
	}

	public static boolean validatePASSPORT(String thePASSPORT) {

		_logger.debug("Validando PASSPORT '" + thePASSPORT + "'");

		return true;
	}

	public static boolean validateSpecialTexto(String texto) {
		if (texto.trim().equals("."))
			return false;
		for (int i = 0; i < texto.length(); i++) {
			if (SPECIALLETTERS.indexOf(texto.charAt(i)) == -1
					&& texto.charAt(i) != ' ')
				return false;
		}
		return true;
	}

	public static boolean validateAlfaNumerico(String texto) {
		for (int i = 0; i < texto.length(); i++) {
			if (ALFANUMERICO.indexOf(texto.charAt(i)) == -1
					&& texto.charAt(i) != ' ')
				return false;
		}
		return true;
	}

	public static boolean validateSpecialAlfaNumerico(String texto) {
		for (int i = 0; i < texto.length(); i++) {
			if (SPECIALALFANUMERICO.indexOf(texto.charAt(i)) == -1
					&& texto.charAt(i) != ' ')
				return false;
		}
		return true;
	}

	public static boolean validateNIF(String theNIF) {

		_logger.debug("Validando NIF '" + theNIF + "'");

		try {

			int numeros = Integer.parseInt(theNIF.substring(0,
					theNIF.length() - 1));
			String letra = theNIF.substring(theNIF.length() - 1).toUpperCase();
			String[] ctrlLetters = new String[] { "T", "R", "W", "A", "G", "M",
					"Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S", "Q", "V",
					"H", "L", "C", "K", "E" };
			int index = numeros - ((numeros / 23) * 23);
			if (!ctrlLetters[index].equals(letra)) {
				_logger.debug("NIF incorrecto");
				return false;
			}
			return true;

		} catch (Exception e) {
			return false;
		}
	}

	public static boolean validateAccount(String accountNumber) {

		_logger.debug("Validando número de cuenta '" + accountNumber + "'");

		if (accountNumber.length() != 20) {
			_logger.debug("El número de cuenta no tiene 20 caracteres");
			return false;
		}

		try {
			Double.parseDouble(accountNumber);
		} catch (Exception ex) {
			_logger.debug("El número de cuenta no es numérico");
			return false;
		}

		if (accountNumber.equals("00000000000000000000")) {
			_logger.debug("Cuenta con todos ceros inválida");
			return false;
		}

		// Cargamos el array
		int[] theArray = new int[accountNumber.length()];
		for (int c = 0; c < accountNumber.length(); c++) {
			theArray[c] = Integer.parseInt(accountNumber.substring(c, c + 1));
		}

		int calculo1 = theArray[0] * 4 + theArray[1] * 8 + theArray[2] * 5
				+ theArray[3] * 10 + theArray[4] * 9 + theArray[5] * 7
				+ theArray[6] * 3 + theArray[7] * 6;
		int calculo2 = calculo1 / 11;
		int calculo3 = calculo2 * 11;
		int calculo4 = calculo1 - calculo3;
		int digito1 = 11 - calculo4;
		int calculoa = theArray[10] + theArray[11] * 2 + theArray[12] * 4
				+ theArray[13] * 8 + theArray[14] * 5 + theArray[15] * 10
				+ theArray[16] * 9 + theArray[17] * 7 + theArray[18] * 3
				+ theArray[19] * 6;
		int calculob = calculoa / 11;
		int calculoc = calculob * 11;
		int calculod = calculoa - calculoc;
		int digito2 = 11 - calculod;

		if (digito1 == 10) {
			digito1 = 1;
		}
		if (digito1 == 11) {
			digito1 = 0;
		}
		if (digito2 == 10) {
			digito2 = 1;
		}
		if (digito2 == 11) {
			digito2 = 0;
		}

		_logger.debug("DC calculado=" + digito1 + digito2);

		if (!((theArray[8] == digito1) && (theArray[9] == digito2))) {
			_logger.debug("Error DC de la cuenta=" + theArray[8] + theArray[9]);
			return false;
		}
		return true;
	}

	public static boolean validateZIP(String zip, String state) {
		_logger.debug(state == null ? "Validando C.P '" + zip + "'"
				: "Validando C.P '" + zip + "' Provincia='" + state + "'");

		try {
			Integer.parseInt(zip);
		} catch (Exception ex) {
			_logger.debug("El C.P. no es numérico");
			return false;
		}

		if (zip.length() != 5) {
			_logger.debug("El C.P. no tiene 5 letras");
			return false;
		}

		if (state != null) {
			// Si se indica código de provincia
			if (state.length() == 1)
				state = "0" + state;

			String aux = zip.substring(0, state.length());
			if (!state.equals(aux)) {
				_logger.debug("El C.P. no coincide con el código de provincia");
				return false;
			}
		}

		return true;
	}

	public static boolean validateNumber(String number) {
		_logger.debug("Validando número '" + number + "'");

		try {
			Double.parseDouble(number);
		} catch (Exception ex) {
			return false;
		}

		return true;
	}

	public static boolean validateRequired(String value) {
		_logger.debug("Validando obligatorio '" + value + "'");

		if (value == null || "".equals(value)) {
			return false;
		}

		return true;
	}

	public static boolean validateTelephone(String tlf) {
		_logger.debug("Validando número de telefono '" + tlf + "'");

		// validar que sea numerico
		try {
			Double.parseDouble(tlf);
		} catch (Exception ex) {
			return false;
		}

		// validar longitud
		if (tlf.length() != 9)
			return false;

		// validar digitos
		// Cargamos el array
		int[] theArray = new int[tlf.length()];
		for (int c = 0; c < tlf.length(); c++) {
			theArray[c] = Integer.parseInt(tlf.substring(c, c + 1));
		}
		if (theArray[0] != 9 && theArray[0] != 8 && theArray[0] != 6) {
			return false;
		}

		return true;
	}

	public static boolean validateTelephoneMovil(String tlf) {
		_logger.debug("Validando número de telefono (movil) '" + tlf + "'");

		// validar que sea numerico
		try {
			Double.parseDouble(tlf);
		} catch (Exception ex) {
			return false;
		}

		// validar longitud
		if (tlf.length() != 9)
			return false;

		// validar digitos
		// Cargamos el array
		int[] theArray = new int[tlf.length()];
		for (int c = 0; c < tlf.length(); c++) {
			theArray[c] = Integer.parseInt(tlf.substring(c, c + 1));
		}
		if (theArray[0] != 6) {
			return false;
		}

		return true;
	}

	public static boolean validateTelephoneNumber(String tlf) {
		_logger.debug("Validando número de telefono '" + tlf + "'");

		// validar que sea numerico
		try {
			Double.parseDouble(tlf);
		} catch (Exception ex) {
			return false;
		}

		// validar longitud
		if (tlf.length() != 9)
			return false;

		// validar digitos
		// Cargamos el array
		int[] theArray = new int[tlf.length()];
		for (int c = 0; c < tlf.length(); c++) {
			theArray[c] = Integer.parseInt(tlf.substring(c, c + 1));
		}
		if (theArray[0] != 9 && theArray[0] != 8) {
			return false;
		} else if (theArray[1] <= 0 || theArray[1] >= 9) {
			return false;
		}

		return true;
	}

	public static boolean validateMail(String email) {
		if (email.length() > 0) {
			if (email.length() < 5) {
				return false;
			}
			for (int i = 0; i < email.length(); i++) {
				if (MAIL_VALID.indexOf(email.charAt(i)) == -1)
					return false;
			}
			if (email.indexOf("@") == -1 || email.indexOf(".") == -1
					|| email.indexOf(" ") != -1
					|| email.charAt(email.length() - 1) == '.'
					|| email.charAt(email.length() - 1) == '@') {
				return false;
			}
		}
		return true;
	}

	public static boolean comprobarFecha(String fecha) {

		StringTokenizer strFecha = new StringTokenizer(fecha, "/");

		try {

			String strDia = strFecha.nextToken();
			String strMes = strFecha.nextToken();
			String strYear = strFecha.nextToken();

			if (strDia.length() != 2)
				return false;
			if (strMes.length() != 2)
				return false;
			if (strYear.length() != 4)
				return false;

			int dia = Integer.parseInt(strDia);
			int mes = Integer.parseInt(strMes);
			int aux_year = Integer.parseInt(strYear);

			if (mes < 1 || mes > 12) {
				return false;
			}
			switch (mes) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				if (dia < 1 || dia > 31) {
					return false;
				}
				break;
			case 4:
			case 6:
			case 9:
			case 11:
				if (dia < 1 || dia > 30) {
					return false;
				}

				break;

			case 2:

				if (dia < 1) {
					return false;
				} else if (dia > 28) {
					// Comprueba si es bisiesto
					if (dia == 29) {
						GregorianCalendar calendar = new GregorianCalendar();

						if (!calendar.isLeapYear(aux_year)) {
							return false;
						}
					} else {
						return false;
					}

				}

				break;

			}

			return true;

		} catch (Exception e) {
			return false;
		}

	}

	public static boolean comprobarFechaHHMMSS(String fecha) {

		StringTokenizer strFecha = new StringTokenizer(fecha, "/");

		try {

			String strDia = strFecha.nextToken();
			String strMes = strFecha.nextToken();
			String strYear = strFecha.nextToken();

			if (strDia.length() != 2)
				return false;
			if (strMes.length() != 2)
				return false;
			if (strYear.length() != 4)
				return false;

			int dia = Integer.parseInt(strDia);
			int mes = Integer.parseInt(strMes);
			int aux_year = Integer.parseInt(fecha.substring(6, 10));

			if (mes < 1 || mes > 12) {
				return false;
			}
			switch (mes) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				if (dia < 1 || dia > 31) {
					return false;
				}
				break;
			case 4:
			case 6:
			case 9:
			case 11:
				if (dia < 1 || dia > 30) {
					return false;
				}

				break;

			case 2:

				if (dia < 1) {
					return false;
				} else if (dia > 28) {
					// Comprueba si es bisiesto
					if (dia == 29) {
						GregorianCalendar calendar = new GregorianCalendar();

						if (!calendar.isLeapYear(aux_year)) {
							return false;
						}
					} else {
						return false;
					}

				}

				break;

			}

			return true;

		} catch (Exception e) {
			return false;
		}

	}

	public static ArrayList procesarCadenasSeparador(String sCadCod,
			String sCadDesc) {

		ArrayList oArraList = new ArrayList();

		StringTokenizer oTokCod = new StringTokenizer(sCadCod, ",");
		StringTokenizer oTokDesc = new StringTokenizer(sCadDesc, ",");
		while (oTokCod.hasMoreTokens()) {
			String sCodTipo = oTokCod.nextToken();
			String sDescTipo = oTokDesc.nextToken();
			if ((sCodTipo != null) && (!sCodTipo.equalsIgnoreCase(""))
					&& (sDescTipo != null) && (!sDescTipo.equalsIgnoreCase(""))) {
				oArraList.add(new DataBean(sCodTipo, sDescTipo));
			}
		}
		return oArraList;
	}

	public static boolean validarPeriodicidad(String strDate,
			String sPeriodicidad) {

		boolean bRetorno = true;
		if ((strDate == null)) {
			bRetorno = false;
		} else {
			Calendar calFecHoy = Calendar.getInstance();
			Calendar calFec = null;
			StringTokenizer strFecha = new StringTokenizer(strDate, "/");
			try {
				// Convierte la Fecha de String (dd/mm/yyyy) a Calendar
				int dia = Integer.parseInt(strFecha.nextToken());
				int mes = Integer.parseInt(strFecha.nextToken());
				int anho = Integer.parseInt(strFecha.nextToken());

				// Al mes se le resta uno, porque para Calendar January=0
				mes--; // (mes-Integer.parseInt(sPeriodicidad))

				calFec = Calendar.getInstance();
				calFec.set(anho, (mes + Integer.parseInt(sPeriodicidad)), dia);
			} catch (Exception ex) {
				return false;
			}

			if (!calFecHoy.equals(calFec)) {
				bRetorno = calFecHoy.after(calFec);
			}
		}
		return bRetorno;
	}

	public static String obtenerFechaActual() {
		String sFecha = "";
		java.util.Date oDate = new java.util.Date();
		java.text.SimpleDateFormat fmtDia = new java.text.SimpleDateFormat(
				"EEEE d", new Locale("es", "ES"));
		java.text.SimpleDateFormat fmtMes = new java.text.SimpleDateFormat(
				"MMMMM", new Locale("es", "ES"));
		java.text.SimpleDateFormat fmtAnio = new java.text.SimpleDateFormat(
				"yyyy ", new Locale("es", "ES"));
		String aux = null;
		String dia = fmtDia.format(oDate);
		aux = dia.substring(0, 1);
		aux = aux.toUpperCase() + dia.substring(1, dia.length());
		dia = aux;
		String mes = fmtMes.format(oDate);
		aux = null;
		aux = mes.substring(0, 1);
		aux = aux.toUpperCase() + mes.substring(1, mes.length());
		mes = aux;
		sFecha = dia + " de " + mes + " de " + fmtAnio.format(oDate);
		oDate = null;
		fmtDia = null;
		fmtMes = null;
		fmtAnio = null;
		dia = null;
		mes = null;
		aux = null;

		return sFecha;
	}

	public static boolean validateTextoNombre(String texto) {
		for (int i = 0; i < texto.length(); i++) {
			if (NOMBRE.indexOf(texto.charAt(i)) == -1 && texto.charAt(i) != ' ')
				return false;
		}
		return true;
	}

}
