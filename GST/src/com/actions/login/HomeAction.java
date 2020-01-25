package com.actions.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.actions.BaseAction;
import com.dto.UserDTO;
import com.forms.login.HomeForm;
import com.util.Constantes;

/**
 * Clase que extiende de la clase BaseAction.
 * 
 * Esta clase controla la lógica de la página inicial de la aplicación.
 * 
 * @author Manuel Guerrero del Pino
 * @version 1.0
 */

public class HomeAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) {

		UserDTO usr = (UserDTO) req.getSession().getAttribute(
				Constantes.SES_USER_KEY);

		HomeForm myForm = (HomeForm) form;

		myForm.setUserName(usr.getName());

		req.getSession().setAttribute(Constantes.SES_USER_KEY, usr);

		req.getSession(false);

		return mapping.findForward("home");
	}

}
