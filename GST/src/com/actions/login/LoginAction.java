package com.actions.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.util.LogUtils;
import com.util.LoggerFactory;
import com.actions.BaseAction;
import com.dto.UserDTO;
import com.forms.login.LoginForm;
import com.util.Constantes;
import com.util.login.LoginUtil;

/**
 * Clase que extiende de la clase BaseAction.
 * 
 * Esta clase controla la lógica de logado de la aplicación.
 * 
 * @author Manuel Guerrero del Pino
 * @version 1.0
 */

public class LoginAction extends BaseAction {

	private static Logger _logger = LoggerFactory.getLogger(LoginAction.class);

	ActionErrors errors = null;

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) {
		this.limpiarError();
		LogUtils.logAction(_logger, LogUtils.DEBUG, mapping, form, req, res);

		LoginForm oLoginForm = (LoginForm) form;
		String pageEvent = oLoginForm.getPageEvent();

		if (pageEvent != null) {
			_logger.debug("Ejecutando evento '" + pageEvent + "'");

			errors = oLoginForm.validate(mapping, req);
			if (!errors.isEmpty()) {
				this.salvarError(errors, req);
				return mapping.findForward("loginForm");
			}
			UserDTO oUser = verificarUsuario(oLoginForm);
			if (oUser == null) {
				this.salvarError(errors, req);
				return mapping.findForward("loginForm");
			}
			req.getSession().setAttribute(Constantes.SES_USER_KEY, oUser);
			this.salvarError(errors, req);
			return mapping.findForward("success");

		} else {
			// Invalidamos una posible sesion anterior al login
			HttpSession session = req.getSession();
			if (!session.isNew()) {
				session.invalidate();
			}
		}
		this.salvarError(errors, req);
		return mapping.findForward("loginForm");
	}

	private UserDTO verificarUsuario(LoginForm oLoginForm) {
		UserDTO oUser = null;
		try {
			if (oLoginForm.getUser() != null
					&& !oLoginForm.getUser().equals("")
					&& oLoginForm.getPassword() != null
					&& !oLoginForm.getPassword().equals("")) {

				oUser = LoginUtil.checkLogin(oLoginForm.getUser(), oLoginForm
						.getPassword());
			} else {
				_logger.debug("Debe rellenar todos los campos");
				this.crearAnadirError("validation.datosIncompletos");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			this.crearAnadirError("validation.errorInternoUsuario");
		}
		return oUser;

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
