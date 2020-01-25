package com.dto;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.util.LoggerFactory;

/**
 * Objeto usuario
 * 
 * @author Manuel Guerrero del Pino
 * @version 1.0
 */

public class UserDTO implements java.io.Serializable {

	private static Logger _logger = LoggerFactory.getLogger(UserDTO.class);

	private ArrayList _roles = null;
	private String _login = null;
	private String _name = null;
	private String _type = null;
	private String _email = null;
	private String password = null;
	private String idUser = null;


	// Login del usuario
	public void setLogin(String login) {
		_login = login;
	}

	public String getIdUser() {
		return idUser;
	}

	public void setidUser(String idUser) {
		this.idUser = idUser;
	}

	public String getLogin() {
		return _login;
	}

	// Nombre del usuario
	public void setName(String name) {
		_name = name;
	}

	public String getName() {
		return _name;
	}

	// Tipo del usuario
	public void setType(String type) {
		_type = type;
	}

	public String getType() {
		return _type;
	}

	// Email del usuario
	public void setEmail(String email) {
		_email = email;
	}

	public String getEmail() {
		return _email;
	}

	// Roles
	public void setRoles(ArrayList roles) {
		_roles = roles;
	}

	public ArrayList getRoles() {
		return _roles;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	// Fin métodos de la interfaz

	public String toString() {
		return "Login=" + _login + ", Nombre=" + _name;

	}

}
