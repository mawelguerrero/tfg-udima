package com.dto;

/**
 * Objeto Oferta
 * 
 * @author Manuel Guerrero del Pino
 * @version 1.0
 */

public class OffertDTO {

	// oferta
	private String nombre;
	private String idOferta;
	private String tipo;
	private String permanencia;
	private String precio;
	private String informacion;

	public void init() {
		this.nombre = "";
		this.idOferta = "";
		this.tipo = "";
		this.permanencia = "";
		this.precio = "";
		this.informacion = "";

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getIdOferta() {
		return idOferta;
	}

	public void setIdOferta(String idOferta) {
		this.idOferta = idOferta;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getPermanencia() {
		return permanencia;
	}

	public void setPermanencia(String permanencia) {
		this.permanencia = permanencia;
	}

	public String getPrecio() {
		return precio;
	}

	public void setPrecio(String precio) {
		this.precio = precio;
	}

	public String getInformacion() {
		return informacion;
	}

	public void setInformacion(String informacion) {
		this.informacion = informacion;
	}

}
