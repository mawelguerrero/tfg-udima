package com.actions.search;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.actions.BaseAction;
import com.dto.UserDTO;
import com.forms.search.SearchClientForm;
import com.util.LogUtils;
import com.util.LoggerFactory;
import com.util.Pager;
import com.util.search.SearchClientUtil;

/**
 * Clase que extiende de la clase BaseAction.
 * 
 * Esta clase controla la lógica de negocio del buscador de peticiones VIP.
 * 
 * @author Manuel Guerrero del Pino
 * @version 1.0
 */

public class SearchClientAction extends BaseAction {

	private static Logger _logger = LoggerFactory
			.getLogger(SearchClientAction.class);

	private ActionErrors errors = null;

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) {

		LogUtils.logAction(_logger, LogUtils.DEBUG, mapping, form, req, res);

		SearchClientForm oSearchClientForm = (SearchClientForm) form;

		// Usuario de sesion
		UserDTO usr = BaseAction.getUser(req);
		String pageEvent = (String) req.getParameter("pageEvent");

		if (pageEvent != null) {
			_logger.debug("Ejecutando evento '" + pageEvent + "'");

			if (pageEvent.equals("buscar")) {

				Pager resultPager = (Pager) SearchClientUtil
						.buscar(oSearchClientForm);

				HttpSession sess = req.getSession();
				sess.setAttribute("ResultadoPaginado", resultPager);
				oSearchClientForm.init();
				this.salvarError(errors, req);
				return mapping.findForward("searchClientResult");
			} else if (pageEvent.equals("previous")) {
				Pager p = (Pager) getFromSession("ResultadoPaginado", req);
				if (p.isPreviousPage()) {
					p.goPreviousPage();
				}
				return mapping.findForward("searchClientResult");

			} else if (pageEvent.equals("next")) {
				Pager p = (Pager) getFromSession("ResultadoPaginado", req);
				if (p.isNextPage()) {
					p.goNextPage();
				}
				return mapping.findForward("searchClientResult");

			} else if (pageEvent.equals("view_clienteVIP")) {

				SearchClientUtil.visualizarClienteVIP(oSearchClientForm);

				ArrayList listaOfertasCentral = SearchClientUtil
						.listaOfertasCentral(oSearchClientForm.getCentral()
								.getCdMiga());
				req.setAttribute("listaOfertasCentral", listaOfertasCentral);

				ArrayList listaPeticionesVIP = SearchClientUtil
						.listaPeticionesVIP(oSearchClientForm.getId());
				req.setAttribute("listaPeticionesVIP",
						listaPeticionesVIP);

				return mapping.findForward("viewClienteVIP");

			}

		}
		oSearchClientForm.init();
		this.salvarError(errors, req);
		return mapping.findForward("success");

	}

	private void salvarError(ActionErrors errors, HttpServletRequest req) {
		if ((errors != null) && (!errors.isEmpty())) {
			this.saveErrors(req, errors);
		}
	}

}