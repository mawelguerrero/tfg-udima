package com.actions.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.actions.BaseAction;
import com.dto.UserDTO;
import com.forms.admin.CentralAdminForm;
import com.util.LogUtils;
import com.util.LoggerFactory;
import com.util.Pager;
import com.util.admin.CentralUtil;

/**
 * Clase que extiende de la clase BaseAction. 
 * 
 * Esta clase controla la lógica de negocio de la 
 * administación de centralitas.
 * 
 * @author Manuel Guerrero del Pino
 * @version 1.0
 */

public class CentralAdminAction extends BaseAction {

	private static Logger _logger = LoggerFactory
			.getLogger(CentralAdminAction.class);

	private ActionErrors errors = null;

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) {
		LogUtils.logAction(_logger, LogUtils.DEBUG, mapping, form, req, res);

		CentralAdminForm oCentralAdminForm = (CentralAdminForm) form;

		UserDTO usr = BaseAction.getUser(req);
		String pageEvent = (String) req.getParameter("pageEvent");

		if (pageEvent != null) {
			_logger.debug("Ejecutando evento '" + pageEvent + "'");

			if (pageEvent.equals("success")) {
				oCentralAdminForm.init();
				this.limpiarError();
				HttpSession sess = req.getSession();
				sess.setAttribute("ResultadoPaginadoCentrales", null);

			} else if (pageEvent.equals("buscar")) {
				this.limpiarError();

				Pager resultPager = (Pager) CentralUtil
						.buscar(oCentralAdminForm);

				HttpSession sess = req.getSession();
				sess.setAttribute("ResultadoPaginadoCentrales", resultPager);

				this.salvarError(errors, req);

			} else if (pageEvent.equals("previous")) {
				Pager p = (Pager) getFromSession("ResultadoPaginadoCentrales",
						req);
				if (p.isPreviousPage()) {
					p.goPreviousPage();
				}

			} else if (pageEvent.equals("next")) {
				Pager p = (Pager) getFromSession("ResultadoPaginadoCentrales",
						req);
				if (p.isNextPage()) {
					p.goNextPage();
				}

			} else if (pageEvent.equals("view_central")) {

				CentralUtil.cargarCentral(oCentralAdminForm);

				req.setAttribute("centralAvailableTable", CentralUtil
						.cargarOfertasDisponiblesCentral(oCentralAdminForm));
				req.setAttribute("centralSelectedTable", CentralUtil
						.cargarOfertasSeleccionadasCentral(oCentralAdminForm));

				return mapping.findForward("viewCentral");
			} else if (pageEvent.equals("mod_central")) {
				this.modificarCentral(oCentralAdminForm, req, mapping);

				this.salvarError(errors, req);
				req.setAttribute("centralAvailableTable", CentralUtil
						.cargarOfertasDisponiblesCentral(oCentralAdminForm));
				req.setAttribute("centralSelectedTable", CentralUtil
						.cargarOfertasSeleccionadasCentral(oCentralAdminForm));
				return mapping.findForward("viewCentral");

			}

		}
		return mapping.findForward("success");

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

	private void modificarCentral(CentralAdminForm oCentralAdminForm,
			HttpServletRequest req, ActionMapping mapping) {

		boolean modificarCentral = CentralUtil
				.modificarCentral(oCentralAdminForm);
		if (modificarCentral) {
			_logger.debug("Centralita modificada correctamente");
			// Necesario crear nuevos errores
			this.crearAnadirError("validation.modifyCentralAdmin");
		} else {
			this.crearAnadirError("validation.errorModifyCentralAdmin");
		}

	}

	private void crearAnadirError(String sError) {
		errors = new ActionErrors();
		ActionError error = new ActionError(sError);
		errors.add("errorMessage", error);
	}

}
