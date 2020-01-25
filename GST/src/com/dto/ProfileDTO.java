package com.dto;

/**
 * Objeto perfil
 * 
 * @author Manuel Guerrero del Pino
 * @version 1.0
 */

public class ProfileDTO {

	private String idProfile;
	private String profileTable;
	private String name;
	private String tipoProfile;
	private boolean isModificable;

	public void init() {
		this.idProfile = "";
		this.profileTable = "";
		this.name = "";
		this.tipoProfile = "";
		this.isModificable = false;
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

	public String getTipoProfile() {
		return tipoProfile;
	}

	public void setTipoProfile(String tipoProfile) {
		this.tipoProfile = tipoProfile;
	}

	public boolean isModificable() {
		return isModificable;
	}

	public void setModificable(boolean isModificable) {
		this.isModificable = isModificable;
	}

}
