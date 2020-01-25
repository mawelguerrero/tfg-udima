package com.forms.search;

import org.apache.struts.action.ActionForm;

import com.dto.ClientDTO;
import com.dto.OffertDTO;
import com.dto.OrderDTO;

/**
 * Clase que extiende de la clase ActionForm.
 * 
 * Esta clase es el formulario para la busqueda de peticiones.
 * 
 * @author Manuel Guerrero del Pino
 * @version 1.0
 */

public class SearchForm extends ActionForm {

	private String pageEvent;
	
	private String idSeleccion;

	private ClientDTO cliente;

	private OffertDTO oferta;

	private OrderDTO pedido;

	// Fechas
	private String fechaInicio;
	private String fechaFin;

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

	public void init() {

		this.cliente = new ClientDTO();
		this.oferta = new OffertDTO();
		this.pedido = new OrderDTO();

		this.idSeleccion = "";

	}

	public String getPageEvent() {
		return pageEvent;
	}

	public void setPageEvent(String pageEvent) {
		this.pageEvent = pageEvent;
	}

	public ClientDTO getCliente() {
		return cliente;
	}

	public void setCliente(ClientDTO cliente) {
		this.cliente = cliente;
	}

	public OffertDTO getOferta() {
		return oferta;
	}

	public void setOferta(OffertDTO oferta) {
		this.oferta = oferta;
	}

	public OrderDTO getPedido() {
		return pedido;
	}

	public void setPedido(OrderDTO pedido) {
		this.pedido = pedido;
	}

	public String getIdSeleccion() {
		return idSeleccion;
	}

	public void setIdSeleccion(String idSeleccion) {
		this.idSeleccion = idSeleccion;
	}
}
