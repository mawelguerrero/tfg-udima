package com.actions;

import java.util.Locale;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.util.PropertyMessageResources;

import com.dto.UserDTO;
import com.util.Constantes;


/**
 * Clase que extiende de la clase Action
 *
 * @author Manuel Guerrero del Pino
 * @version 1.0 
 */

public class BaseAction extends Action {

	public static UserDTO getUser(HttpServletRequest req) {
		HttpSession sess = req.getSession();
		return (UserDTO) sess.getAttribute(Constantes.SES_USER_KEY);
	}

	public static Object getFromSession(String key, HttpServletRequest req) {
		HttpSession sess = req.getSession();
		return sess.getAttribute(key);
	}

	private static String getMessageBundle(String theKey, String theBundle,
			HttpServletRequest theRequest, HttpServlet theServlet) {
		HttpSession theSession = theRequest.getSession();
		PropertyMessageResources messages = (PropertyMessageResources) theServlet
				.getServletContext().getAttribute(theBundle);
		String message = messages.getMessage((Locale) theSession
				.getAttribute(Globals.LOCALE_KEY), theKey);

		return message;
	}

	public static String getLabelMessage(String theLabel, String theBundle,
			HttpServletRequest theRequest, HttpServlet theServlet) {
		String message = "";
		message += getMessageBundle(theLabel, theBundle, theRequest, theServlet);
		return message;
	}

	public static String getLabelMessage(String theLabel, String theBundle,
			HttpServletRequest theRequest, ActionServlet theServlet) {
		return getLabelMessage(theLabel, theBundle, theRequest,
				(HttpServlet) theServlet);
	}

}
