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
import com.forms.DataBean;
import com.forms.search.SearchForm;
import com.util.LogUtils;
import com.util.LoggerFactory;
import com.util.Pager;
import com.util.search.SearchUtil;

/**
 * Clase que extiende de la clase BaseAction.
 * 
 * Esta clase controla la lógica del buscador de peticiones.
 * 
 * @author Manuel Guerrero del Pino
 * @version 1.0
 */

public class SearchAction extends BaseAction {

	private static Logger _logger = LoggerFactory.getLogger(SearchAction.class);

	private ActionErrors errors = null;

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) {

		LogUtils.logAction(_logger, LogUtils.DEBUG, mapping, form, req, res);

		SearchForm oSearchForm = (SearchForm) form;

		// Usuario de sesion
		UserDTO usr = BaseAction.getUser(req);
		String pageEvent = (String) req.getParameter("pageEvent");

		if (pageEvent != null) {
			_logger.debug("Ejecutando evento '" + pageEvent + "'");

			if (pageEvent.equals("buscar")) {

				// Busqueda segun los criterios introducidos
				Pager resultPager = (Pager) SearchUtil.buscar(oSearchForm);

				HttpSession sess = req.getSession();
				sess.setAttribute("ResultadoPaginado", resultPager);
				return mapping.findForward("searchResult");

			} else if (pageEvent.equals("view_pedido")) {

				SearchUtil.visualizarPedido(oSearchForm);

				return mapping.findForward("viewPedido");

			} else if (pageEvent.equals("searchResult")) {

				return mapping.findForward("searchResult");
			}

		}
		req.setAttribute("typeOffertList", this.cargarTipoOferta());
		oSearchForm.init();
		return mapping.findForward("success");

	}

	private ArrayList cargarTipoOferta() {
		ArrayList listaOfertas = new ArrayList();
		listaOfertas.add(new DataBean("-1", ""));
		listaOfertas.add(new DataBean("1", "VOZ"));
		listaOfertas.add(new DataBean("2", "DATOS"));
		listaOfertas.add(new DataBean("3", "VOZ y DATOS"));
		return listaOfertas;
	}

}
