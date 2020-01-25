package com.forms.login;


import org.apache.struts.action.ActionForm;

/**
 * Clase que extiende de la clase ActionForm. 
 * 
 * Esta clase es el formulario para la página inicial.
 * 
 * @author Manuel Guerrero del Pino
 * @version 1.0
 */

public class HomeForm extends ActionForm {

	public void reset() {
		userName = "";
	}

	// Nombre de usuario
	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
