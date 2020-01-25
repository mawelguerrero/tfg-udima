package com.util;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.RequestProcessor;

/**
 * Clase que establece el Request de la sesión
 * 
 * @author Manuel Guerrero del Pino
 * @version 1.0
 */

public class SessionRequestProcessor extends RequestProcessor {
	protected ActionForward processActionPerform(HttpServletRequest req,
			HttpServletResponse res, Action action, ActionForm form,
			ActionMapping mapping) throws IOException, ServletException {
		boolean isLogin = false;
		String path = req.getServletPath();
		if ((path.indexOf("login.do") != -1)
				|| (path.indexOf("estado.do") != -1)
				|| (path.indexOf("home.do") != -1)) {
			isLogin = true;
		}

		return super.processActionPerform(req, res, action, form, mapping);
	}
}
