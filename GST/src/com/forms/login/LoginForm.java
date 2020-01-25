package com.forms.login;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.util.LoggerFactory;

/**
 * Clase que extiende de la clase ActionForm.
 * 
 * Esta clase es el formulario para el logado de la apliacación.
 * 
 * @author Manuel Guerrero del Pino
 * @version 1.0
 */

public class LoginForm extends ActionForm {

	private static Logger logger = LoggerFactory.getLogger(LoginForm.class);

	// Código de plataforma
	private String clientCode;

	// Evento de pagina
	private String pageEvent;

	// Password
	private String password;

	// Usuario
	private String user;

	public void reset() {
		this.setClientCode("");
		this.setUser("");
		this.setPassword("");
	}

	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPageEvent() {
		return pageEvent;
	}

	public void setPageEvent(String pageEvent) {
		this.pageEvent = pageEvent;
	}

	// Validación del login
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {

		ActionErrors errors = new ActionErrors();

		logger.debug("Pendiente Añadir validaciones");

		return errors;

	}

	public LoginForm() {
		this.reset();
	}

}
