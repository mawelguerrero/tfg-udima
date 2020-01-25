package com.util;

import java.sql.Connection;

import org.apache.log4j.Logger;

import com.dto.UserDTO;

/**
 * Clase para establecer la conexi�n con la Base de Datos
 * 
 * @author Manuel Guerrero del Pino
 * @version 1.0
 */

public class AccesoBBDD {

	private static Logger _logger = LoggerFactory.getLogger(UserDTO.class);

	private AccesoBBDD(){
		
	}
	public static Connection getConnection() {
		_logger.info("AccesoBBDD.getConnection() conectando a la bbdd");

		Connection conn = null;
		String url = "";
		String user = "";
		String password = "";

		// Datos de Conexi�n
		url = "jdbc:mysql://localhost/udimagest";
		user = "root";
		password = "manuel";
		
		try {
			// Usamos una conexi�n normal de base de datos
			Class.forName("com.mysql.jdbc.Driver");
			conn = java.sql.DriverManager.getConnection(url, user, password);
		} catch (Exception ex) {
			_logger.error("Error obteniendo conexi�n de BD");
		}
		return conn;
	}

}
