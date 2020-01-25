package com.forms.admin;

import org.apache.struts.action.ActionForm;

import com.dto.CentralDTO;

/**
 * Clase que extiende de la clase ActionForm.
 * 
 * Esta clase es el formulario para la administración de centralitas.
 * 
 * @author Manuel Guerrero del Pino
 * @version 1.0
 */

public class CentralAdminForm extends ActionForm {

	private String pageEvent;

	private CentralDTO central;

	private String id;
	private String lista_disponibles;
	private String[] lista_seleccionados;
	private String centralSelectedTable;
	private String centralAvailableTable;

	public void init() {
		id = "";
		lista_disponibles = "";
		centralSelectedTable = "";
		centralAvailableTable = "";
		this.central = new CentralDTO();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPageEvent() {
		return pageEvent;
	}

	public void setPageEvent(String pageEvent) {
		this.pageEvent = pageEvent;
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

	public String getCentralSelectedTable() {
		return centralSelectedTable;
	}

	public void setCentralSelectedTable(String centralSelectedTable) {
		this.centralSelectedTable = centralSelectedTable;
	}

	public String getCentralAvailableTable() {
		return centralAvailableTable;
	}

	public void setCentralAvailableTable(String centralAvailableTable) {
		this.centralAvailableTable = centralAvailableTable;
	}

	public CentralDTO getCentral() {
		return central;
	}

	public void setCentral(CentralDTO central) {
		this.central = central;
	}

}
