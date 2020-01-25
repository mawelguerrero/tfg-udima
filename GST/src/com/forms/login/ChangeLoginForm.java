package com.forms.login;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;

import com.util.LoggerFactory;

/**
 * Clase que extiende de la clase ActionForm.
 * 
 * Esta clase es el formulario para el cambio de contraseña.
 * 
 * @author Manuel Guerrero del Pino
 * @version 1.0
 */

public class ChangeLoginForm extends ActionForm {

	private static Logger logger = LoggerFactory
			.getLogger(ChangeLoginForm.class);

	private String user;

	private String password;

	private String passwordForm;

	private String newPassword;

	private String confirmNewPassword;

	private String clientCode;

	private String pageEvent;

	public void reset() {
		this.setClientCode("");
		this.setUser("");
		this.setPasswordForm("");
		this.setConfirmNewPassword("");
		this.setNewPassword("");
		this.setPageEvent(null);

	}

	public void limpiarContrasenas() {
		this.setPasswordForm("");
		this.setConfirmNewPassword("");
		this.setNewPassword("");
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

	public String getNewPassword() {
		return newPassword;
	}

	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}

	public String getPageEvent() {
		return pageEvent;
	}

	public String getPasswordForm() {
		return passwordForm;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}

	public void setPageEvent(String pageEvent) {
		this.pageEvent = pageEvent;
	}

	public void setPasswordForm(String passwordForm) {
		this.passwordForm = passwordForm;
	}

	public ChangeLoginForm() {
		this.reset();
	}

}
