package com.dto;

/**
 * Objeto pedido
 * 
 * @author Manuel Guerrero del Pino
 * @version 1.0
 */

public class OrderDTO {

	// Pedido
	private String idPedido;
	private String fechaInicio;
	private String fechaFin;
	private String estado;

	public void init() {
		this.idPedido = "";
		this.fechaInicio = "";
		this.fechaFin = "";
		this.estado = "";
	}

	public String getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(String idPedido) {
		this.idPedido = idPedido;
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}
