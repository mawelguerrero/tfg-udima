package com.forms.search;

import org.apache.struts.action.ActionForm;

import com.dto.CentralDTO;
import com.dto.ClientDTO;

/**
 * Clase que extiende de la clase ActionForm.
 * 
 * Esta clase es el formulario para la busqueda de peticiones VIP.
 * 
 * @author Manuel Guerrero del Pino
 * @version 1.0
 */

public class SearchClientForm extends ActionForm {

	private String pageEvent;
	private String id;

	private ClientDTO cliente;

	private CentralDTO central;

	// Fechas
	private String fechaInicio;
	private String fechaFin;

	public void init() {
		this.id = "";

		this.cliente = new ClientDTO();
		this.central = new CentralDTO();

		// Fechas
		this.fechaInicio = "";
		this.fechaFin = "";

	}

	public String getPageEvent() {
		return pageEvent;
	}

	public void setPageEvent(String pageEvent) {
		this.pageEvent = pageEvent;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ClientDTO getCliente() {
		return cliente;
	}

	public void setCliente(ClientDTO cliente) {
		this.cliente = cliente;
	}

	public CentralDTO getCentral() {
		return central;
	}

	public void setCentral(CentralDTO central) {
		this.central = central;
	}

}
