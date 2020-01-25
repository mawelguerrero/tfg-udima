/*
 * LoggerFactory.java
 *
 * Created on 26 de julio de 2002, 13:02
 */

package com.util;

import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Clase para la configuración de los logs de la aplicación
 * 
 * @author Manuel Guerrero del Pino
 * @version 1.0
 */

public class LoggerFactory {

	private static LoggerFactory instance = null;

	protected String configFile = "/log4j.properties";

	private LoggerFactory() {
		try {
			Properties props = new Properties();
			props.load(getClass().getResourceAsStream(configFile));
			PropertyConfigurator.configure(props);
		} catch (Exception e) {
			BasicConfigurator.configure();
			try {
				Logger.getLogger(LoggerFactory.class).error(
						"No se encuentra el fichero de configuración de log4j '"
								+ configFile + "'", e);
			} catch (Exception ex) {
			}
		}
	}

	public synchronized static Logger getLogger(Class theClass) {
		if (instance == null)
			instance = new LoggerFactory();
		return instance.createLogger(theClass.getName());
	}

	public synchronized static Logger getLogger(String className) {
		if (instance == null)
			instance = new LoggerFactory();
		return instance.createLogger(className);
	}

	private Logger createLogger(String className) {
		return Logger.getLogger(className);
	}

}
