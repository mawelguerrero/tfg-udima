package com.actions.admin;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.actions.BaseAction;
import com.dto.UserDTO;
import com.forms.DataBean;
import com.forms.admin.ProfileAdminForm;
import com.util.LogUtils;
import com.util.LoggerFactory;
import com.util.admin.ProfilesUtil;

/**
 * Clase que extiende de la clase BaseAction.
 * 
 * Esta clase controla la lógica de negocio de la administación de perfiles.
 * 
 * @author Manuel Guerrero del Pino
 * @version 1.0
 */

public class ProfileAdminAction extends BaseAction {

	private static Logger _logger = LoggerFactory
			.getLogger(ProfileAdminAction.class);

	private ActionErrors errors = null;

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) {
		this.limpiarError();
		LogUtils.logAction(_logger, LogUtils.DEBUG, mapping, form, req, res);

		ProfileAdminForm oProfileAdminForm = (ProfileAdminForm) form;

		// Usuario de sesion
		UserDTO usr = BaseAction.getUser(req);
		String pageEvent2 = (String) req.getParameter("pageEvent");

		if (pageEvent2 != null) {
			_logger.debug("Ejecutando evento '" + pageEvent2 + "'");

			if (pageEvent2.equals("change_combo_profile")) {
				// el usuario ha seleccionado un perfil
				if (!oProfileAdminForm.getIdProfile().equals("-")) {
					this.assignAttributesProfile(req, oProfileAdminForm);
					this.iniProfile(oProfileAdminForm);
					this.salvarError(errors, req);
					oProfileAdminForm.setIsModificable(true);
					return mapping.findForward("success");
				}
			} else if (pageEvent2.equals("create_profile")) {
				// el usuario va a crear un nuevo perfil
				this.createProfile(oProfileAdminForm, usr);
				this.iniAttributesProfile(req, oProfileAdminForm);
				this.salvarError(errors, req);
				return mapping.findForward("success");
			} else if (pageEvent2.equals("mod_profile")) {
				// el usuario va a modificar un perfil existente
				if (this.modifyProfile(oProfileAdminForm)) {
					this.assignAttributesProfile(req, oProfileAdminForm);
					oProfileAdminForm.setIsModificable(true);
				} else {
					this.iniAttributesProfile(req, oProfileAdminForm);
					oProfileAdminForm.reset();
				}
				this.salvarError(errors, req);
				return mapping.findForward("success");
			} else if (pageEvent2.equals("delete_profile")) {
				// el usuario va a eliminar un perfil existente
				if (this.deleteProfile(oProfileAdminForm)) {
					this.iniAttributesProfile(req, oProfileAdminForm);
				}
				oProfileAdminForm.reset();
				this.salvarError(errors, req);
				return mapping.findForward("success");
			}

		}
		this.iniAttributesProfile(req, oProfileAdminForm);
		this.salvarError(errors, req);
		return mapping.findForward("success");

	} // ActionForward

	private void iniProfile(ProfileAdminForm oProfileAdminForm) {
		DataBean oData = ProfilesUtil.getDescription(Integer
				.parseInt(oProfileAdminForm.getIdProfile()));
		oProfileAdminForm.setTipoProfile(oData.getDescription());
		oProfileAdminForm.setName(oData.getValue());
	}

	private void createProfile(ProfileAdminForm myForm, UserDTO usr) {
		boolean creado = ProfilesUtil.createProfile(myForm, myForm
				.getLista_seleccionados());
		if (creado) {
			this.crearAnadirError("validation.createPerfil");
		} else {
			this.crearAnadirError("validation.errorCreatePerfil");
		}

	}

	private boolean modifyProfile(ProfileAdminForm myForm) {
		boolean bRetorno = false;

		// Se pueden modificar todos los perfiles menos el del administrador
		if (!myForm.getIdProfile().equals("1")) {

			boolean borrado = ProfilesUtil.deleteProfileOptions(myForm);
			if (borrado) {
				boolean creado = ProfilesUtil.createProfileOptions(myForm,
						myForm.getLista_seleccionados());
				if (creado) {
					boolean updated = ProfilesUtil.updateProfile(myForm);
					if (updated) {
						bRetorno = true;
						this.crearAnadirError("validation.modifyPerfil");
						this.iniProfile(myForm);
					} else {
						_logger.debug("Error al modificar (update) el perfil");
						this.crearAnadirError("validation.errorModifyPerfil");
					}
				} else {
					_logger
							.debug("Error al modificar (crear) las opciones del perfil");
					this
							.crearAnadirError("validation.errorModifyPerfilOptions");
				}
			} else {
				_logger
						.debug("Error al modificar (eliminar) las opciones del perfil");
				this.crearAnadirError("validation.errorModifyPerfilOptions");
			}
		} else {
			_logger.debug("No se puede modifica el perfil ADMINISTRADOR");
			this.crearAnadirError("validation.errorAdmin");
		}

		return bRetorno;
	}

	private boolean deleteProfile(ProfileAdminForm myForm) {
		// Los perfiles de administrador y superadministrador no pueden borrarse
		boolean bRetorno = false;
		// Se pueden borrar todos los perfiles menos el del administrador
		if (!myForm.getIdProfile().equals("1")) {

			boolean borrado = ProfilesUtil.deleteProfile(myForm);
			if (borrado) {
				bRetorno = true;
				myForm.reset();
				this.crearAnadirError("validation.deletePerfil");
			} else {
				this.crearAnadirError("validation.errorDeletePerfil");
				this.iniProfile(myForm);
			}
		} else {
			_logger.debug("No se puede borrar el perfil ADMINISTRADOR");
			this.crearAnadirError("validation.errorAdmin");
		}
		return bRetorno;
	}

	private void assignAttributesProfile(HttpServletRequest req,
			ProfileAdminForm myForm) {
		req.setAttribute("profileTable", ProfilesUtil
				.getInitialProfiles("Crear Nuevo Perfil"));
		req.setAttribute("profileAvailableTable", ProfilesUtil
				.getInitialAvailableOptions(Integer.parseInt(myForm
						.getIdProfile())));
		req.setAttribute("profileSelectedTable", ProfilesUtil
				.getInitialSelectedOptions(Integer.parseInt(myForm
						.getIdProfile())));
	}

	private void iniAttributesProfile(HttpServletRequest req,
			ProfileAdminForm myForm) {
		req.setAttribute("profileTable", ProfilesUtil
				.getInitialProfiles("Crear Nuevo Perfil"));
		myForm.setIdProfile("-");
		req.setAttribute("profileAvailableTable", ProfilesUtil
				.getInitialOption());
		req.setAttribute("profileSelectedTable", new ArrayList());
		myForm.setName("");
		myForm.setLista_disponibles("");
	}

	private void salvarError(ActionErrors errors, HttpServletRequest req) {
		if ((errors != null) && (!errors.isEmpty())) {
			this.saveErrors(req, errors);
		}
	}

	private void limpiarError() {
		if ((errors != null) && (!errors.isEmpty())) {
			errors.clear();
		}
	}

	private void crearAnadirError(String sError) {
		errors = new ActionErrors();
		ActionError error = new ActionError(sError);
		errors.add("errorMessage", error);
	}
} 
