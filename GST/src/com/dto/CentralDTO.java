package com.dto;

/**
 * Objeto Centralitas
 * 
 * @author Manuel Guerrero del Pino
 * @version 1.0
 */

public class CentralDTO {

	// Datos de la central
	private String nombre;
	private String provincia;
	private String poblacion;
	private String cdPostal;
	private String cdMiga;

	public void init() {
		this.nombre = "";
		this.provincia = "";
		this.poblacion = "";
		this.cdPostal = "";
		this.cdMiga = "";
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}

	public String getCdPostal() {
		return cdPostal;
	}

	public void setCdPostal(String cdPostal) {
		this.cdPostal = cdPostal;
	}

	public String getCdMiga() {
		return cdMiga;
	}

	public void setCdMiga(String cdMiga) {
		this.cdMiga = cdMiga;
	}

}
