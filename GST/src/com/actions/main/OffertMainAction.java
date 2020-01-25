package com.actions.main;

import java.util.ArrayList;
import java.util.HashMap;

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
import com.forms.main.OffertMainForm;
import com.util.LogUtils;
import com.util.LoggerFactory;
import com.util.main.OffertMainUtil;


/**
 * Clase que extiende de la clase BaseAction.
 * 
 * Esta clase controla la lógica de negocio de la gestión de las peticiones.
 * 
 * @author Manuel Guerrero del Pino
 * @version 1.0
 */

public class OffertMainAction extends BaseAction {

	private static Logger _logger = LoggerFactory
			.getLogger(OffertMainAction.class);

	private ActionErrors errors = null;

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) {
		LogUtils.logAction(_logger, LogUtils.DEBUG, mapping, form, req, res);

		OffertMainForm oOffertMainForm = (OffertMainForm) form;

		String pageEvent = (String) req.getParameter("pageEvent");

		if (pageEvent != null) {
			_logger.debug("Ejecutando evento '" + pageEvent + "'");

			if (pageEvent.equals("buscar") || pageEvent.equals("buscarOferta")) {

				if ((oOffertMainForm.getCliente().getTelefono() != null && !oOffertMainForm
						.getCliente().getTelefono().equals(""))
						|| (oOffertMainForm.getCliente().getCodPostal() != null
								&& !oOffertMainForm.getCliente().getCodPostal()
										.equals("")
								&& oOffertMainForm.getCliente().getCalle() != null
								&& !oOffertMainForm.getCliente().getCalle()
										.equals("")
								&& oOffertMainForm.getCliente().getNum() != null && !oOffertMainForm
								.getCliente().getNum().equals(""))) {

					// Busqueda segun los criterios introducidos
					ArrayList result = OffertMainUtil.buscar(oOffertMainForm);

					if (result != null && !result.isEmpty()) {
						HashMap h = (HashMap) result.get(0);
						String codMiga = (String) h.get("CODIGO_MIGA");

						oOffertMainForm.getCentral().setCdMiga(codMiga);
						// Se comprueba si existe el cliente 
						if (oOffertMainForm.getCliente().getTelefono() != null
								&& !oOffertMainForm.getCliente().getTelefono()
										.equals("")) {
							oOffertMainForm = OffertMainUtil
									.existeClienteOferta(oOffertMainForm);

						}

					}

					HttpSession sess = req.getSession();
					sess.setAttribute("ResultadoOfertasConCobertura", result);
					sess.setAttribute("OffertMainForm", oOffertMainForm);

					return mapping.findForward("resultCoverage");
				} else {
					this.crearAnadirError("validation.consultaCobertura");
					this.salvarError(errors, req);

					if (pageEvent.equals("buscar")) {
						return mapping.findForward("coverage");
					} else {
						return mapping.findForward("success");
					}
				}

			} else if (pageEvent.equals("cancelar")) {

				String idClienteVIP = OffertMainUtil
						.existeClienteVIP(oOffertMainForm);

				if (idClienteVIP != null && !idClienteVIP.isEmpty()) {
					oOffertMainForm.getCliente().setIdCliente(
							idClienteVIP);
					oOffertMainForm = OffertMainUtil.cargarInfo(
							idClienteVIP, oOffertMainForm);
				}

				HttpSession sess = req.getSession();
				sess.setAttribute("OffertMainForm", oOffertMainForm);

				return mapping.findForward("clienteVIP");

			} else if (pageEvent.equals("aceptar")) {
				String idOferta = (String) req.getParameter("idOferta");

				if (idOferta == null || idOferta.equals("")
						|| idOferta.equals("undefined")) {
					this.crearAnadirError("validation.seleccionarOferta");
					this.salvarError(errors, req);
					return mapping.findForward("resultCoverage");
				} else {
					oOffertMainForm.getOferta().setIdOferta(idOferta);

					// cargar los datos de la oferta seleccionada
					oOffertMainForm = OffertMainUtil
							.cargarOfertaSeleccionada(oOffertMainForm);

					boolean existeClienteVIP = false;
					boolean existeCliente = false;
					if (oOffertMainForm.getCliente().getIdCliente() == null
							|| oOffertMainForm.getCliente().getIdCliente()
									.equals("")) {
						String idClienteVIP = OffertMainUtil
								.existeClienteVIP(oOffertMainForm);
						if (idClienteVIP != null
								&& !idClienteVIP.isEmpty()) {
							existeClienteVIP = true;
							oOffertMainForm.getCliente().setIdCliente(
									idClienteVIP);

							oOffertMainForm = OffertMainUtil.cargarInfo(
									idClienteVIP, oOffertMainForm);
						}
					} else {
						existeCliente = true;
					}

					if (oOffertMainForm.getCliente().getTelefono() == null
							|| oOffertMainForm.getCliente().getTelefono()
									.isEmpty()) {

						String telefonoCliente = OffertMainUtil
								.asignarNumeroTelefono(oOffertMainForm
										.getCentral().getCdMiga());
						oOffertMainForm.getCliente().setTelefono(
								telefonoCliente);

					}

					HttpSession sess = req.getSession();
					sess.setAttribute("OffertMainForm", oOffertMainForm);
					sess.setAttribute("ExisteClienteVIP",
							existeClienteVIP);
					sess.setAttribute("ExisteCliente", existeCliente);
					return mapping.findForward("createOrder");
				}

			} else if (pageEvent.equals("guardar")) {

				// Guarda los datos del cliente VIP
				// sms que se ha guardado ok

				boolean guardado = OffertMainUtil.guardar(oOffertMainForm);
				if (guardado) {
					_logger
							.debug("Guardado correctamente el cliente VIP");

					this.crearAnadirError("validation.clienteVIPOK");
				} else {
					this.crearAnadirError("validation.clienteVIPKO");

				}

				this.salvarError(errors, req);

				return mapping.findForward("clienteVIP");

			} else if (pageEvent.equals("crearPedido")) {

				oOffertMainForm = OffertMainUtil.crearPedido(oOffertMainForm);

				HttpSession sess = req.getSession();
				sess.setAttribute("OffertMainForm", oOffertMainForm);

				// se redirigue a la pantalla de visualizacion
				return mapping.findForward("confirmOrder");

			} else if (pageEvent.equals("crearPedidoDeVIP")) {

				// borrar el cliente VIP y las pedidos VIP
				OffertMainUtil.borrarClienteVIP(oOffertMainForm);

				oOffertMainForm = OffertMainUtil.crearPedido(oOffertMainForm);

				HttpSession sess = req.getSession();
				sess.setAttribute("OffertMainForm", oOffertMainForm);

				// se redirigue a la pantalla de visualizacion
				return mapping.findForward("confirmOrder");

			} else if (pageEvent.equals("modificarPedido")) {

				if (OffertMainUtil.updateClient(oOffertMainForm)) {
					if (OffertMainUtil.updatePedido(oOffertMainForm)) {
						this.crearAnadirError("validation.modificarPedido");
					} else {
						this
								.crearAnadirError("validation.errorModificarPedido");
					}
				} else {
					this.crearAnadirError("validation.errorModificarCliente");
				}

				this.salvarError(errors, req);
				return mapping.findForward("createOrder");

			} else if (pageEvent.equals("borrarPedido")) {

				if (OffertMainUtil.sePuedeBorrarPeticion(oOffertMainForm)) {

					if (OffertMainUtil.deleteClient(oOffertMainForm)) {
						if (OffertMainUtil.deletePedido(oOffertMainForm)) {
							this.crearAnadirError("validation.borrarPedido");
							// Crea una peticion VIP con los datos del
							// cliente dado de baja
							oOffertMainForm.getCliente().setInformacion(
									"Cliente dado de baja de la oferta: "
											+ oOffertMainForm.getOferta()
													.getNombre());

							boolean guardado = OffertMainUtil
									.guardarClienteVIP(oOffertMainForm);
							if (guardado) {
								guardado = OffertMainUtil
										.guardarPeticionVIP(
												oOffertMainForm,
												OffertMainUtil
														.existeClienteVIP(oOffertMainForm));
							}

							if (guardado) {
								_logger
										.debug("Guardado correctamente el cliente VIP");
								this
										.crearAnadirError("validation.clienteVIPOKalBorrar");
							} else {
								this
										.crearAnadirError("validation.clienteVIPKOalBorrar");

							}
						} else {
							this
									.crearAnadirError("validation.errorBorrarPedidoExistente");
						}
					} else {
						this.crearAnadirError("validation.errorBorrarCliente");
					}
				} else {
					this.crearAnadirError("validation.errorBorrarPedido");
				}
				this.salvarError(errors, req);
				return mapping.findForward("createOrder");

			}

		}

		HttpSession sess = req.getSession();
		oOffertMainForm.init();
		sess.setAttribute("OffertMainForm", oOffertMainForm);
		return mapping.findForward((String) req.getParameter("operation"));

	} 

	private void salvarError(ActionErrors errors, HttpServletRequest req) {
		if ((errors != null) && (!errors.isEmpty())) {
			this.saveErrors(req, errors);
		}

	}

	private void crearAnadirError(String sError) {
		errors = new ActionErrors();
		ActionError error = new ActionError(sError);
		errors.add("errorMessage", error);
	}

} 