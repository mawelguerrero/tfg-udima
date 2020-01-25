package com.util;

/**
 * Clase para la correcta visualización del menú
 * 
 * @author Manuel Guerrero del Pino
 * @version 1.0
 */

public class Option implements java.io.Serializable {

	public Option(String id, String url, String name, String description) {
		_id = id;
		_url = url;
		_name = name;
		_description = description;
	}

	public Option(String id, String parentId, String url, String name,
			String description, String nivel) {
		_id = id;
		_parentId = parentId;
		_url = url;
		_name = name;
		_description = description;
		_nivel = nivel;
	}

	public Option(String url, String description, String nivel) {
		_url = url;
		_description = description;
		_nivel = nivel;
	}

	private String _id = null;

	public void setId(String id) {
		_id = id;
	}

	public String getId() {
		return _id;
	}

	private String _parentId = null;

	public void setParentId(String id) {
		_parentId = id;
	}

	public String getParentId() {
		return _parentId;
	}

	// Link correspondiente a la opción
	private String _url = null;

	public void setUrl(String url) {
		_url = url;
	}

	public String getUrl() {
		return _url;
	}

	// Nombre de la opción que sale por pantalla
	private String _name = null;

	public void setName(String name) {
		_name = name;
	}

	public String getName() {
		return _name;
	}

	// Descripción que sale en la parte de administración
	private String _description = null;

	public void setDescription(String description) {
		_description = description;
	}

	public String getDescription() {
		return _description;
	}

	// Nivel de la opcion de menu
	private String _nivel = null;

	public void setNivel(String nivel) {
		_nivel = nivel;
	}

	public String getNivel() {
		return _nivel;
	}

}
