package com.dto;

/**
 * Objeto Cliente
 * 
 * @author Manuel Guerrero del Pino
 * @version 1.0
 */

public class ClientDTO {

	// Datos del cliente
	private String idCliente;
	private String nombre;
	private String apellidos;
	private String documentoID;
	private String telefono;
	private String movil;

	// Direccion del cliente
	private String provincia;
	private String poblacion;
	private String codPostal;
	private String calle;
	private String num;
	private String piso;
	private String letra;
	private String escalera;
	private String fax;
	private String email;

	// Datos bancarios
	private String formaPago;
	private String numeroCuenta;
	private String numeroTarjeta;
	private String fechaCaducidad;

	// Informacion
	private String informacion;

	public void init() {
		// Datos del cliente
		this.idCliente = "";
		this.nombre = "";
		this.apellidos = "";
		this.documentoID = "";
		this.telefono = "";
		this.movil = "";

		// Direccion del cliente
		this.provincia = "";
		this.poblacion = "";
		this.codPostal = "";
		this.calle = "";
		this.num = "";
		this.piso = "";
		this.letra = "";
		this.escalera = "";
		this.fax = "";
		this.email = "";

		// Datos bancarios
		this.formaPago = "";
		this.numeroCuenta = "";
		this.numeroTarjeta = "";
		this.fechaCaducidad = "";

		// Informacion
		this.informacion = "";

	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDocumentoID() {
		return documentoID;
	}

	public void setDocumentoID(String documentoID) {
		this.documentoID = documentoID;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getMovil() {
		return movil;
	}

	public void setMovil(String movil) {
		this.movil = movil;
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

	public String getCodPostal() {
		return codPostal;
	}

	public void setCodPostal(String codPostal) {
		this.codPostal = codPostal;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getPiso() {
		return piso;
	}

	public void setPiso(String piso) {
		this.piso = piso;
	}

	public String getLetra() {
		return letra;
	}

	public void setLetra(String letra) {
		this.letra = letra;
	}

	public String getEscalera() {
		return escalera;
	}

	public void setEscalera(String escalera) {
		this.escalera = escalera;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}

	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public String getNumeroTarjeta() {
		return numeroTarjeta;
	}

	public void setNumeroTarjeta(String numeroTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
	}

	public String getFechaCaducidad() {
		return fechaCaducidad;
	}

	public void setFechaCaducidad(String fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}

	public String getInformacion() {
		return informacion;
	}

	public void setInformacion(String informacion) {
		this.informacion = informacion;
	}

}
