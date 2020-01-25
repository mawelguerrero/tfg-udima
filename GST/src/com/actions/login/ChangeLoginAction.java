package com.actions.login;

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
import com.forms.login.ChangeLoginForm;
import com.util.Constantes;
import com.util.LogUtils;
import com.util.LoggerFactory;
import com.util.login.LoginUtil;

/**
 * Clase que extiende de la clase BaseAction.
 * 
 * Esta clase controla la lógica del cambio de contraseña.
 * 
 * @author Manuel Guerrero del Pino
 * @version 1.0
 */

public class ChangeLoginAction extends BaseAction {

	private static Logger _logger = LoggerFactory
			.getLogger(ChangeLoginAction.class);

	ActionErrors errors = null;

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) {
		this.limpiarError();
		LogUtils.logAction(_logger, LogUtils.DEBUG, mapping, form, req, res);

		ChangeLoginForm oChangeLoginForm = (ChangeLoginForm) form;
		UserDTO oUser = (UserDTO) req.getSession().getAttribute(
				Constantes.SES_USER_KEY);

		if ((oChangeLoginForm.getPassword() == null)
				|| (oChangeLoginForm.getPassword().equalsIgnoreCase(""))) {
			oChangeLoginForm.setPassword(oUser.getPassword());
		}
		String pageEvent = oChangeLoginForm.getPageEvent();

		if ((pageEvent != null) && (pageEvent.equalsIgnoreCase("iniciado"))) {

			if (oUser.getLogin().toUpperCase().equals(
					oChangeLoginForm.getUser().toUpperCase())) {

				errors = LoginUtil.validarUpdate(oChangeLoginForm, req);
				if (!errors.isEmpty()) {
					oChangeLoginForm.limpiarContrasenas();
					this.salvarError(errors, req);
					return mapping.findForward("changeLogin");
				} else {
					if (!LoginUtil.updatePasswordUser(oChangeLoginForm)) {
						this
								.crearAnadirError("validation.errorActualizaPasswordChangeLogin");
						this.salvarError(errors, req);
						return mapping.findForward("changeLogin");
					} else {
						if (this.verifiActuUserSession(oChangeLoginForm, req) != null) {
							oChangeLoginForm.limpiarContrasenas();
							this.crearAnadirError("validation.okChangeLogin");
							this.salvarError(errors, req);
							return mapping.findForward("changeLogin");
						} else {
							this
									.crearAnadirError("validation.errorActualizaPasswordChangeLogin");
							this.salvarError(errors, req);
							return mapping.findForward("changeLogin");
						}
					}
				}
			} else {
				oChangeLoginForm.limpiarContrasenas();
				this
						.crearAnadirError("validation.errorActualizaPasswordChangeLoginNoUser");
				this.salvarError(errors, req);
				return mapping.findForward("changeLogin");
			}
		} else {
			oChangeLoginForm.limpiarContrasenas();
			this.salvarError(errors, req);
			return mapping.findForward("changeLogin");
		}
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

	private UserDTO verifiActuUserSession(ChangeLoginForm oChangeLoginForm,
			HttpServletRequest req) {
		UserDTO oUser = null;
		try {
			oUser = LoginUtil.checkLogin(oChangeLoginForm.getUser(),
					oChangeLoginForm.getNewPassword());
			req.getSession().setAttribute(Constantes.SES_USER_KEY, oUser);
		} catch (Exception ex) {
			ex.printStackTrace();
			_logger.error(ex.toString());
			ActionError error = new ActionError(
					"validation.errorInternoUsuario");
			errors.add("errorMessage", error);
		}
		return oUser;

	}
}
