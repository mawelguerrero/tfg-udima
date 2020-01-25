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
import com.forms.DataBean;
import com.forms.admin.UserAdminForm;
import com.util.LogUtils;
import com.util.LoggerFactory;
import com.util.admin.ProfilesUtil;
import com.util.admin.UserUtil;

/**
 * Clase que extiende de la clase BaseAction.
 * 
 * Esta clase controla la lógica de negocio de la administación de usuarios.
 * 
 * @author Manuel Guerrero del Pino
 * @version 1.0
 */

public class UsersAdminAction extends BaseAction {

	private static Logger _logger = LoggerFactory
			.getLogger(UsersAdminAction.class);

	ActionErrors errors = null;

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) {

		LogUtils.logAction(_logger, LogUtils.DEBUG, mapping, form, req, res);

		UserAdminForm oUserActionForm = (UserAdminForm) form;
		String pageEvent = (String) req.getParameter("operation");

		try {
			if ((req.getParameter("name").equals("-1"))
					&& (!pageEvent.equals("create_user")))
				pageEvent = "inicio";
		} catch (Exception e) {
		}

		if (pageEvent != null) {
			_logger.debug("Ejecutando evento '" + pageEvent + "'");
			if (pageEvent.equals("inicio") || pageEvent.equals("success")) {
				req.setAttribute("perfilesAvailableTable", ProfilesUtil
						.getInitialProfiles(""));
				req.setAttribute("perfilesSelectedTable", new ArrayList());
				req.setAttribute("usersTable", UserUtil.loadUsersTable());
				oUserActionForm.reset();
				return mapping.findForward("success");
			} else if (pageEvent.equals("view_users")) {
				oUserActionForm.setIsModificable(false);
				oUserActionForm.setId_usuario(req.getParameter("name"));
				String idusuario = comboUser(oUserActionForm);
				req.setAttribute("idusuarios", idusuario);
			} else if (pageEvent.equals("create_user")) {
				oUserActionForm.setId_usuario(req.getParameter("name"));
				boolean creado = this.createUser(oUserActionForm, req, mapping);
				if (creado)
					oUserActionForm.reset();
				this.salvarError(errors, req);
			} else if (pageEvent.equals("delete_user")) {
				this.deleteUser(oUserActionForm);
				oUserActionForm.reset();
				this.salvarError(errors, req);
			} else if (pageEvent.equals("mod_user")) {
				boolean modificado = this.modifyUser(oUserActionForm, req,
						mapping);
				if (modificado) {
					oUserActionForm.reset();
					oUserActionForm.setId_usuario("-1");
				} else
					oUserActionForm.setIsModificable(false);
				this.salvarError(errors, req);
			}
			req.setAttribute("perfilesAvailableTable", UserUtil
					.getInitialAvailablePerfiles(oUserActionForm
							.getId_usuario()));
			req.setAttribute("perfilesSelectedTable", UserUtil
					.getInitialSelectedPerfilUser(oUserActionForm
							.getId_usuario()));

		}
		req.setAttribute("usersTable", UserUtil.loadUsersTable());
		return mapping.findForward("success");
	}

	private String comboUser(UserAdminForm myForm) {
		ArrayList tupla = (ArrayList) UserUtil.selectByPrimaryKey(myForm
				.getId_usuario());
		myForm.setId_usuario((String) ((DataBean) tupla.get(0))
				.getDescription());
		myForm.setLogin((String) ((DataBean) tupla.get(1)).getDescription());
		myForm.setNombre((String) ((DataBean) tupla.get(2)).getDescription());
		myForm.setMail((String) ((DataBean) tupla.get(3)).getDescription());
		myForm.setPassword((String) ((DataBean) tupla.get(4)).getDescription());
		myForm.setIsModificable(false);

		return myForm.getId_usuario();

	}

	private boolean createUser(UserAdminForm myForm, HttpServletRequest req,
			ActionMapping mapping) {

		boolean hecho = false;
		errors = myForm.validate(mapping, req);
		if ((errors != null) && (!errors.isEmpty())) {
			this.saveErrors(req, errors);

		} else {

			hecho = UserUtil.checkLoginUser(myForm);

			if (hecho) {

				hecho = UserUtil.createUser(myForm, myForm
						.getLista_perfiles_seleccionados());
				if (hecho) {
					_logger.debug("Usuario creado correctamente");

					this.crearAnadirError("validation.createUser");
				} else {
					this.crearAnadirError("validation.errorCreateUser");
				}
			} else {
				this.crearAnadirError("validation.loginCheck");
			}

		}
		return hecho;
	}

	private void deleteUser(UserAdminForm myForm) {

		boolean borrado = UserUtil.delete(myForm);
		if (borrado) {
			_logger.debug("Administrador de cliente eliminado correctamente");

			this.crearAnadirError("validation.deleteUserAdmin");
		} else {
			this.crearAnadirError("validation.errorDeleteUserAdmin");

		}

	}

	private boolean modifyUser(UserAdminForm myForm, HttpServletRequest req,
			ActionMapping mapping) {
		boolean modificado = false;
		errors = myForm.validate(mapping, req);
		if ((errors != null) && (!errors.isEmpty())) {
			this.saveErrors(req, errors);

		} else {

			modificado = UserUtil.modify(myForm, myForm
					.getLista_perfiles_seleccionados());
			if (modificado) {
				_logger
						.debug("Administrador de cliente modificado correctamente");
				this.crearAnadirError("validation.modifyUserAdmin");
			} else {
				this.crearAnadirError("validation.errorModifyUserAdmin");
			}

		}
		return modificado;
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
