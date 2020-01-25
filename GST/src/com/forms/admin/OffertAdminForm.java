package com.forms.admin;

import org.apache.struts.action.ActionForm;

import com.dto.OffertDTO;

/**
 * Clase que extiende de la clase ActionForm.
 * 
 * Esta clase es el formulario para la administración de ofertas.
 * 
 * @author Manuel Guerrero del Pino
 * @version 1.0
 */

public class OffertAdminForm extends ActionForm {

	private String pageEvent;

	private OffertDTO oferta = new OffertDTO();

	private boolean isModificable = true;

	public boolean getIsModificable() {
		return isModificable;
	}

	public void setIsModificable(boolean isModificable) {
		this.isModificable = isModificable;
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

	public void reset() {
		setIsModificable(true);
		oferta = new OffertDTO();
	}

}
