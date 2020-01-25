package com.forms.main;

import org.apache.struts.action.ActionForm;

import com.dto.CentralDTO;
import com.dto.ClientDTO;
import com.dto.OffertDTO;
import com.dto.OrderDTO;

/**
 * Clase que extiende de la clase ActionForm.
 * 
 * Esta clase es el formulario para la gestión de peticiones.
 * 
 * @author Manuel Guerrero del Pino
 * @version 1.0
 */

public class OffertMainForm extends ActionForm {

	private String pageEvent;

	private String idOferta;

	private ClientDTO cliente;

	private OffertDTO oferta;

	private OrderDTO pedido;

	private CentralDTO central;

	public void init() {
		this.pageEvent = "";
		this.idOferta = "";
		this.cliente = new ClientDTO();
		this.cliente.init();
		this.oferta = new OffertDTO();
		this.oferta.init();
		this.pedido = new OrderDTO();
		this.pedido.init();
		this.central = new CentralDTO();
		this.central.init();

	}

	public String getPageEvent() {
		return pageEvent;
	}

	public void setPageEvent(String pageEvent) {
		this.pageEvent = pageEvent;
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

	public void setCliente(ClientDTO cliente) {
		this.cliente = cliente;
	}

	public CentralDTO getCentral() {
		return central;
	}

	public void setCentral(CentralDTO central) {
		this.central = central;
	}

	public ClientDTO getCliente() {
		return cliente;
	}

	public String getIdOferta() {
		return idOferta;
	}

	public void setIdOferta(String idOferta) {
		this.idOferta = idOferta;
	}

}
