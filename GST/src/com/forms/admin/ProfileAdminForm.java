package com.forms.admin;

import org.apache.struts.action.ActionForm;

/**
 * Clase que extiende de la clase ActionForm.
 * 
 * Esta clase es el formulario para la administración de perfiles.
 * 
 * @author Manuel Guerrero del Pino
 * @version 1.0
 */

public class ProfileAdminForm extends ActionForm {

	private String pageEvent;
	private String profile;
	private String idProfile;
	private String profileTable;
	private String name;
	private String lista_disponibles;
	private String[] lista_seleccionados;
	private String profileSelectedTable;
	private String[] seleccion;
	private String profileAvailableTable;
	private String tipoProfile;
	private boolean isModificable;

	public ProfileAdminForm() {
		// reset();
	}

	public String getPageEvent() {
		return pageEvent;
	}

	public void setPageEvent(String pageEvent) {
		this.pageEvent = pageEvent;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getIdProfile() {
		return idProfile;
	}

	public void setIdProfile(String idProfile) {
		this.idProfile = idProfile;
	}

	public String getProfileTable() {
		return profileTable;
	}

	public void setProfileTable(String profileTable) {
		this.profileTable = profileTable;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLista_disponibles() {
		return lista_disponibles;
	}

	public void setLista_disponibles(String lista_disponibles) {
		this.lista_disponibles = lista_disponibles;
	}

	public String[] getLista_seleccionados() {
		return lista_seleccionados;
	}

	public void setLista_seleccionados(String[] lista_seleccionados) {
		this.lista_seleccionados = lista_seleccionados;
	}

	public String getProfileSelectedTable() {
		return profileSelectedTable;
	}

	public void setProfileSelectedTable(String profileSelectedTable) {
		this.profileSelectedTable = profileSelectedTable;
	}

	public String[] getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(String[] seleccion) {
		this.seleccion = seleccion;
	}

	public String getProfileAvailableTable() {
		return profileAvailableTable;
	}

	public String getTipoProfile() {
		return tipoProfile;
	}

	public void setProfileAvailableTable(String profileAvailableTable) {
		this.profileAvailableTable = profileAvailableTable;
	}

	public void setTipoProfile(String tipoProfile) {
		this.tipoProfile = tipoProfile;
	}

	public boolean getIsModificable() {
		return isModificable;
	}

	public void setIsModificable(boolean isModificable) {
		this.isModificable = isModificable;
	}

	public void reset() {
		this.setTipoProfile("");
		this.setProfile("");
		this.setIdProfile("");
		this.setProfileTable("");
		this.setName("");
		this.setLista_disponibles("");
		this.setProfileSelectedTable("");
		this.setLista_seleccionados(null);
		this.setSeleccion(null);
		this.setIsModificable(false);
	}

} // ProfileAdminForm
