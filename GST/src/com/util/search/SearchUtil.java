package com.util.search;

import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import com.forms.search.SearchForm;
import com.util.AccesoBBDD;
import com.util.GestionCommand;
import com.util.LoggerFactory;
import com.util.Pager;

/**
 * Clase de utilidad para la busqueda de las peticiones
 * 
 * @author Manuel Guerrero del Pino
 * @version 1.0
 */

public class SearchUtil {

	private static Logger _logger = LoggerFactory
			.getLogger(SearchClientUtil.class);

	public static Pager buscar(SearchForm oSearchForm) {

		String sql = null;
		Hashtable hstValues = null;
		Hashtable hstTypes = null;

		Connection conn = null;
		GestionCommand gc = null;

		try {

			sql = "SELECT C.TXDESCNOM, C.TXDESCAPE, C.CDTELEFONO, O.TXNOMOFER AS NOMBRE_OFERTA, P.CDPEDIDO, P.FHINICIO "
					+ "FROM TPFG_CLIENTES C, TPFG_OFERTAS O, TPFG_PEDIDOS P "
					+ "WHERE P.CDCLIENTE=C.CDCLIENTE "
					+ "AND P.CDOFERTA=O.CDOFERTA ";
			
			hstValues = new Hashtable();
			hstTypes = new Hashtable();

			int i = 1;

			// Datos del cliente

			if (oSearchForm.getCliente().getNombre() != null
					&& !oSearchForm.getCliente().getNombre().equals("")) {
				sql = sql + "AND C.TXDESCNOM = ? ";
				hstValues.put(new Integer(i), oSearchForm.getCliente()
						.getNombre());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i++;
			}

			if (oSearchForm.getCliente().getApellidos() != null
					&& !oSearchForm.getCliente().getApellidos().equals("")) {
				sql = sql + "AND C.TXDESCAPE = ? ";
				hstValues.put(new Integer(i), oSearchForm.getCliente()
						.getApellidos());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i++;
			}

			if (oSearchForm.getCliente().getDocumentoID() != null
					&& !oSearchForm.getCliente().getDocumentoID().equals("")) {
				sql = sql + "AND C.CDNIF = ? ";
				hstValues.put(new Integer(i), oSearchForm.getCliente()
						.getDocumentoID());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i++;
			}

			if (oSearchForm.getCliente().getProvincia() != null
					&& !oSearchForm.getCliente().getProvincia().equals("")) {
				sql = sql + "AND C.TXPROVINCIA = ? ";
				hstValues.put(new Integer(i), oSearchForm.getCliente()
						.getProvincia());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i++;
			}

			if (oSearchForm.getCliente().getPoblacion() != null
					&& !oSearchForm.getCliente().getPoblacion().equals("")) {
				sql = sql + "AND C.TXPOBLACION = ? ";
				hstValues.put(new Integer(i), oSearchForm.getCliente()
						.getPoblacion());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i++;
			}

			if (oSearchForm.getCliente().getCodPostal() != null
					&& !oSearchForm.getCliente().getCodPostal().equals("")) {
				sql = sql + "AND C.CDPOSTAL = ? ";
				hstValues.put(new Integer(i), oSearchForm.getCliente()
						.getCodPostal());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i++;
			}

			if (oSearchForm.getCliente().getTelefono() != null
					&& !oSearchForm.getCliente().getTelefono().equals("")) {
				sql = sql + "AND C.CDTELEFONO = ? ";
				hstValues.put(new Integer(i), oSearchForm.getCliente()
						.getTelefono());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i++;
			}

			if (oSearchForm.getCliente().getCalle() != null
					&& !oSearchForm.getCliente().getCalle().equals("")) {
				sql = sql + "AND C.TXCALLE = ? ";
				hstValues.put(new Integer(i), oSearchForm.getCliente()
						.getCalle());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i++;
			}

			if (oSearchForm.getCliente().getNum() != null
					&& !oSearchForm.getCliente().getNum().equals("")) {
				sql = sql + "AND C.TXNUMERO = ? ";
				hstValues
						.put(new Integer(i), oSearchForm.getCliente().getNum());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i++;
			}

			// Datos de la oferta

			if (oSearchForm.getOferta().getTipo() != null
					&& !oSearchForm.getOferta().getTipo().equals("-1")) {
				sql = sql + "AND O.CDTIPOFER = ? ";
				hstValues
						.put(new Integer(i), oSearchForm.getOferta().getTipo());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i++;
			}

			if (oSearchForm.getOferta().getNombre() != null
					&& !oSearchForm.getOferta().getNombre().equals("")) {
				sql = sql + "AND O.TXNOMOFER = ? ";
				hstValues.put(new Integer(i), oSearchForm.getOferta()
						.getNombre());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i++;
			}

			if (oSearchForm.getOferta().getPermanencia() != null
					&& !oSearchForm.getOferta().getPermanencia().equals("")) {
				sql = sql + "AND O.CDPERMAN = ? ";
				hstValues.put(new Integer(i), oSearchForm.getOferta()
						.getPermanencia());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i++;
			}

			if (oSearchForm.getOferta().getPrecio() != null
					&& !oSearchForm.getOferta().getPrecio().equals("")) {
				sql = sql + "AND O.IMPRECIO = ? ";
				hstValues.put(new Integer(i), oSearchForm.getOferta()
						.getPrecio());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i++;
			}

			// Datos del pedido

			if (oSearchForm.getPedido().getIdPedido() != null
					&& !oSearchForm.getPedido().getIdPedido().equals("")) {
				sql = sql + "AND P.CDPEDIDO = ? ";
				hstValues.put(new Integer(i), oSearchForm.getPedido()
						.getIdPedido());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i++;
			}

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

			if (oSearchForm.getFechaInicio() != null
					&& !oSearchForm.getFechaInicio().equals("")) {
				sql = sql + "AND P.FHINICIO >= ? ";
				Date date = sdf.parse(oSearchForm.getFechaInicio());
				hstValues.put(new Integer(i), sdf2.format(date));
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i++;
			}

			if (oSearchForm.getFechaFin() != null
					&& !oSearchForm.getFechaFin().equals("")) {
				sql = sql + "AND P.FHINICIO <= ? ";
				Date date = sdf.parse(oSearchForm.getFechaFin());
				hstValues.put(new Integer(i), sdf2.format(date));
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i++;
			}

			sql = sql + "ORDER BY P.CDPEDIDO DESC";

			conn = AccesoBBDD.getConnection();

			gc = new GestionCommand(sql);

			ArrayList result = (ArrayList) gc.executeCommand(conn, hstValues,
					hstTypes, GestionCommand.RESULT_ARRAYLIST);

			Pager p = new Pager(result, 10);

			return p;

		} catch (Exception ex) {
			_logger.error("Error buscando los clientes VIP de BD", ex);
			return null;
		} finally {
			try {
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

	}

	public static void visualizarPedido(SearchForm oSearchForm) {

		String sql = null;
		Hashtable hstValues = null;
		Hashtable hstTypes = null;

		Connection conn = null;
		GestionCommand gc = null;

		try {

			sql = "SELECT C.TXDESCNOM, C.TXDESCAPE, C.CDTELEFONO, C.CDNIF, C.TXCALLE, C.TXNUMERO, C.TXESCALERA, C.TXPISO, "
					+ "C.TXPOBLACION, C.TXPROVINCIA, C.CDPOSTAL, C.TXFORMPAGO, C.TXCCC, C.CDMOVIL, "
					+ "C.CDFAX, C.TXDESCEMA, C.TXLETRA, P.FHINICIO, "
					+ "O.TXNOMOFER AS NOMBRE_OFERTA, O.CDTIPOFER, O.CDPERMAN, O.IMPRECIO, O.TXINFORM "
					+ "FROM TPFG_CLIENTES C, TPFG_OFERTAS O, TPFG_PEDIDOS P "
					+ "WHERE P.CDCLIENTE=C.CDCLIENTE "
					+ "AND P.CDOFERTA=O.CDOFERTA " + "AND P.CDPEDIDO = ? ";

			hstValues = new Hashtable();
			hstTypes = new Hashtable();

			hstValues.put(new Integer(1), oSearchForm.getIdSeleccion());
			hstTypes.put(new Integer(1), GestionCommand.ITYPE_STRING);

			conn = AccesoBBDD.getConnection();

			gc = new GestionCommand(sql);

			ResultSet rsResult = (ResultSet) gc.executeCommand(hstValues,
					hstTypes, GestionCommand.RESULT_RESULTSET);

			if (rsResult.next()) {

				oSearchForm.getPedido().setIdPedido(
						oSearchForm.getIdSeleccion());

				oSearchForm.getCliente()
						.setNombre(rsResult.getString("TXDESCNOM"));
				oSearchForm.getCliente().setTelefono(
						rsResult.getString("CDTELEFONO"));
				oSearchForm.getCliente().setApellidos(
						rsResult.getString("TXDESCAPE"));
				oSearchForm.getCliente().setCalle(rsResult.getString("TXCALLE"));
				oSearchForm.getCliente().setNum(rsResult.getString("TXNUMERO"));
				oSearchForm.getCliente().setLetra(rsResult.getString("LETRA"));
				oSearchForm.getCliente().setEscalera(
						rsResult.getString("TXESCALERA"));
				oSearchForm.getCliente().setPiso(rsResult.getString("TXPISO"));
				oSearchForm.getCliente().setCodPostal(
						rsResult.getString("CDPOSTAL"));
				oSearchForm.getCliente().setProvincia(
						rsResult.getString("TXPROVINCIA"));
				oSearchForm.getCliente().setPoblacion(
						rsResult.getString("TXPOBLACION"));
				oSearchForm.getCliente().setDocumentoID(
						rsResult.getString("CDNIF"));
				oSearchForm.getCliente().setFormaPago(
						rsResult.getString("TXFORMPAGO"));
				oSearchForm.getCliente().setNumeroCuenta(
						rsResult.getString("TXCCC"));
				oSearchForm.getCliente().setMovil(rsResult.getString("CDMOVIL"));
				oSearchForm.getCliente().setFax(rsResult.getString("CDFAX"));
				oSearchForm.getCliente().setEmail(rsResult.getString("TXDESCEMA"));
				oSearchForm.getPedido().setFechaInicio(
						rsResult.getString("FHINICIO"));
				oSearchForm.getOferta().setNombre(
						rsResult.getString("TXNOMOFER"));
				oSearchForm.getOferta().setTipo(
						rsResult.getString("CDTIPOFER"));
				oSearchForm.getOferta().setPermanencia(
						rsResult.getString("CDPERMAN"));
				oSearchForm.getOferta().setPrecio(rsResult.getString("IMPRECIO"));
				oSearchForm.getOferta().setInformacion(
						rsResult.getString("TXINFORM"));

			}

		} catch (Exception ex) {
			_logger.error("Error buscando lo datos de las centrales.", ex);

		} finally {
			try {
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

	}

}
