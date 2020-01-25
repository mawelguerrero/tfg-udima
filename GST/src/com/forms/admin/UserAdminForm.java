package com.forms.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.util.Validations;

/**
 * Clase que extiende de la clase ActionForm.
 * 
 * Esta clase es el formulario para la administración de usuarios.
 * 
 * @author Manuel Guerrero del Pino
 * @version 1.0
 */

public class UserAdminForm extends ActionForm {
	private String id_usuario;
	private String lista_perfiles_disponibles;
	private String[] lista_perfiles_seleccionados;
	private String login;
	private String mail;
	private String name;
	private String[] lista_seleccionados;
	private String[] seleccion;
	private String pageEvent;
	private String password;
	private boolean isModificable = true;
	private String perfilesSelectedTable;
	private boolean iniciarPassword;
	private String operation;
	private int periodicidad;
	private String nombre;
	private String perfilesAvailableTable;

	public String[] getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(String[] seleccion) {
		this.seleccion = seleccion;
	}

	public int getPeriodicidad() {
		return periodicidad;
	}

	public String[] getLista_seleccionados() {
		return lista_seleccionados;
	}

	public void setLista_seleccionados(String[] lista_seleccionados) {
		this.lista_seleccionados = lista_seleccionados;
	}

	public void setIniciarPassword(boolean iniciarPassword) {
		this.iniciarPassword = iniciarPassword;
	}

	public boolean isIniciarPassword() {
		return iniciarPassword;
	}

	public String getId_usuario() {
		return id_usuario;
	}

	public String getPerfilesSelectedTable() {
		return perfilesSelectedTable;
	}

	public void setPerfilesSelectedTable(String perfilesSelectedTable) {
		this.perfilesSelectedTable = perfilesSelectedTable;
	}

	public void setIsModificable(boolean isModificable) {
		this.isModificable = isModificable;
	}

	public void setId_usuario(String id_usuario) {
		this.id_usuario = id_usuario;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPageEvent(String pageEvent) {
		this.pageEvent = pageEvent;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setLista_perfiles_seleccionados(
			String[] lista_perfiles_seleccionados) {
		this.lista_perfiles_seleccionados = lista_perfiles_seleccionados;
	}

	public void setLista_perfiles_disponibles(String lista_perfiles_disponibles) {
		this.lista_perfiles_disponibles = lista_perfiles_disponibles;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public boolean getIsModificable() {
		return isModificable;
	}

	public String getLista_perfiles_disponibles() {
		return lista_perfiles_disponibles;
	}

	public String[] getLista_perfiles_seleccionados() {
		return lista_perfiles_seleccionados;
	}

	public String getLogin() {
		return login;
	}

	public String getMail() {
		return mail;
	}

	public String getName() {
		return name;
	}

	public String getPageEvent() {
		return pageEvent;
	}

	public String getPassword() {
		return password;
	}

	public String getOperation() {
		return operation;
	}

	public String getNombre() {
		return nombre;
	}

	public void setPeriodicidad(int periodicidad) {
		this.periodicidad = periodicidad;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ActionErrors validate(ActionMapping actionMapping,
			HttpServletRequest httpServletRequest) {
		ActionErrors errors = null;

		// Comprobación eMail correcto
		if (!this.getMail().equals("") && !Validations.validateMail(getMail())) {
			errors = new ActionErrors();
			ActionError error = new ActionError("validation.errorEmail");
			errors.add("errorMessage", error);
		}
		return errors;

	}

	public void reset() {
		setLogin("");
		setMail("");
		setName("");
		setNombre("");
		setNombre("");
		setPassword("");
		this.setLista_perfiles_disponibles("");
		this.setLista_perfiles_seleccionados(null);
		this.setLista_seleccionados(null);

	}

	public void reset(ActionMapping actionMapping,
			HttpServletRequest servletRequest) {
		this.setLogin("");
		this.setPeriodicidad(0);
		this.setPassword("");
		this.setName("-1");
		this.setMail("");
		this.setIsModificable(true);
	}

	public String getPerfilesAvailableTable() {
		return perfilesAvailableTable;
	}

	public void setPerfilesAvailableTable(String perfilesAvailableTable) {
		this.perfilesAvailableTable = perfilesAvailableTable;
	}

}
