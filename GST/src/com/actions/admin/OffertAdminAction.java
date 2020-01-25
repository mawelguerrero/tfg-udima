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
import com.forms.admin.OffertAdminForm;
import com.util.LogUtils;
import com.util.LoggerFactory;
import com.util.admin.OffertUtil;

/**
 * Clase que extiende de la clase BaseAction.
 * 
 * Esta clase controla la lógica de negocio de la administación de ofertas.
 * 
 * @author Manuel Guerrero del Pino
 * @version 1.0
 */

public class OffertAdminAction extends BaseAction {

	private static Logger _logger = LoggerFactory
			.getLogger(OffertAdminAction.class);

	private ActionErrors errors = null;

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) {

		LogUtils.logAction(_logger, LogUtils.DEBUG, mapping, form, req, res);

		OffertAdminForm oOffertAdminForm = (OffertAdminForm) form;

		UserDTO usr = BaseAction.getUser(req);
		String pageEvent = (String) req.getParameter("pageEvent");

		if (pageEvent != null) {
			_logger.debug("Ejecutando evento '" + pageEvent + "'");

			if (pageEvent.equals("success")) {
				oOffertAdminForm.reset();
				req.setAttribute("offertTable", OffertUtil.loadOffertsTable());
				req.setAttribute("typeOffertList", this.cargarTipoOferta());
				oOffertAdminForm.setIsModificable(true);
				return mapping.findForward("success");

			} else if (pageEvent.equals("create_offert")) {
				boolean creado = this.createOffert(oOffertAdminForm, req,
						mapping);
				if (creado)
					oOffertAdminForm.reset();
				this.salvarError(errors, req);

			} else if (pageEvent.equals("mod_offert")) {
				boolean modificado = this.modifyOffert(oOffertAdminForm, req,
						mapping);
				if (modificado) {
					oOffertAdminForm.reset();
				} else
					oOffertAdminForm.setIsModificable(false);
				this.salvarError(errors, req);

			} else if (pageEvent.equals("delete_offert")) {
				this.deleteOffert(oOffertAdminForm);
				oOffertAdminForm.reset();
				this.salvarError(errors, req);
			} else if (pageEvent.equals("view_offerts")) {

				if (req.getParameter("oferta.idOferta") != null
						&& req.getParameter("oferta.idOferta").equals("-1")) {
					oOffertAdminForm.reset();
				} else {
					oOffertAdminForm.setIsModificable(false);
					oOffertAdminForm.getOferta().setIdOferta(
							req.getParameter("oferta.idOferta"));
					cargarOferta(oOffertAdminForm);
				}
			}

		}
		req.setAttribute("offertTable", OffertUtil.loadOffertsTable());
		req.setAttribute("typeOffertList", this.cargarTipoOferta());
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

	private void cargarOferta(OffertAdminForm myForm) {
		ArrayList tupla = (ArrayList) OffertUtil.selectByPrimaryKey(myForm
				.getOferta().getIdOferta());
		myForm.getOferta().setNombre(
				(String) ((DataBean) tupla.get(0)).getDescription());
		myForm.getOferta().setTipo(
				(String) ((DataBean) tupla.get(1)).getDescription());
		myForm.getOferta().setPermanencia(
				(String) ((DataBean) tupla.get(2)).getDescription());
		myForm.getOferta().setPrecio(
				(String) ((DataBean) tupla.get(3)).getDescription());
		myForm.getOferta().setInformacion(
				(String) ((DataBean) tupla.get(4)).getDescription());

	}

	private void salvarError(ActionErrors errors, HttpServletRequest req) {
		if ((errors != null) && (!errors.isEmpty())) {
			this.saveErrors(req, errors);
		}

	}

	private boolean createOffert(OffertAdminForm myForm,
			HttpServletRequest req, ActionMapping mapping) {

		boolean hecho = false;
		errors = myForm.validate(mapping, req);
		if ((errors != null) && (!errors.isEmpty())) {
			this.saveErrors(req, errors);

		} else {

			hecho = OffertUtil.createOffert(myForm);
			if (hecho) {
				_logger.debug("Oferta creada correctamente");
				this.crearAnadirError("validation.createOffert");
			} else {
				this.crearAnadirError("validation.errorCreateOffert");
			}

		}
		return hecho;
	}

	private void crearAnadirError(String sError) {
		errors = new ActionErrors();
		ActionError error = new ActionError(sError);
		errors.add("errorMessage", error);
	}

	private void deleteOffert(OffertAdminForm myForm) {

		boolean borrado = OffertUtil.delete(myForm);
		if (borrado) {
			_logger.debug("Oferta eliminada correctamente");
			this.crearAnadirError("validation.deleteOffert");
		} else {
			this.crearAnadirError("validation.errorDeleteOffert");

		}

	}

	private boolean modifyOffert(OffertAdminForm myForm,
			HttpServletRequest req, ActionMapping mapping) {

		boolean modificado = false;
		errors = myForm.validate(mapping, req);
		if ((errors != null) && (!errors.isEmpty())) {
			this.saveErrors(req, errors);

		} else {

			modificado = OffertUtil.modify(myForm);
			if (modificado) {
				_logger.debug("Oferta modificada correctamente");
				this.crearAnadirError("validation.modifyOffert");
			} else {
				this.crearAnadirError("validation.errorModifyOffert");
			}

		}
		return modificado;
	}

}
