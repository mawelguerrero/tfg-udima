package com.util;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * Clase de utilidad para los logs de la aplicación
 * 
 * @author Manuel Guerrero del Pino
 * @version 1.0
 */

public class LogUtils {

	public static final int DEBUG = 1;
	public static final int WARN = 2;
	public static final int ERROR = 3;
	public static final int FATAL = 4;

	public static void logAction(Logger logger, int level,
			ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse res) {

		// Información del mapping
		String mappingInfo = "";
		if (mapping != null) {
			mappingInfo = mappingInfo + "Mapping name=" + mapping.getName();
		} else {
			mappingInfo = "Mapping=null";
		}

		// Información del form
		String formInfo = "";
		if (form != null) {
			formInfo = formInfo + "Form name=" + form.toString();
		} else {
			formInfo = "Form=null";
		}

		// Información del request
		String requestInfo = "";
		if (req != null) {
			String key = null;
			String value = null;

			requestInfo += "PARAMETERS=";
			Enumeration names = req.getParameterNames();
			while (names.hasMoreElements()) {
				key = (String) names.nextElement();
				value = (String) req.getParameter(key);
				requestInfo = requestInfo + key + "=" + value + "#";
			}

			requestInfo += "\nATTRIBUTES=";
			names = req.getAttributeNames();
			while (names.hasMoreElements()) {
				key = (String) names.nextElement();
				value = (String) req.getParameter(key);
				requestInfo = requestInfo + key + "=" + value + "#";
			}

		}

		String logMessage = "\nEjecutando action \n" + "----------------- \n"
				+ mappingInfo + "\n" + formInfo + "\n" + requestInfo;

		switch (level) {
		case DEBUG:
			logger.debug(logMessage);
			break;
		case WARN:
			logger.warn(logMessage);
			break;
		case ERROR:
			logger.error(logMessage);
			break;
		case FATAL:
			logger.fatal(logMessage);
			break;
		default:
			logger.error("LogUtils: nivel de log desconocido");
			break;
		}

	}

}
