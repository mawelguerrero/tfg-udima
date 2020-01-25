package com.util.main;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import com.forms.main.OffertMainForm;
import com.util.AccesoBBDD;
import com.util.GestionCommand;
import com.util.LoggerFactory;

/**
 * Clase de utilidad para la gestion de las peticiones
 * 
 * @author Manuel Guerrero del Pino
 * @version 1.0
 */

public class OffertMainUtil {

	private static Logger _logger = LoggerFactory
			.getLogger(OffertMainUtil.class);

	public static ArrayList buscar(OffertMainForm oOffertMainForm) {

		String sql = null;
		Hashtable hstValues = null;
		Hashtable hstTypes = null;

		Connection conn = null;
		GestionCommand gc = null;

		try {

			sql = "SELECT DISTINCT O.CDOFERTA, O.TXNOMOFER, O.CDTIPOFER, O.CDPERMAN, O.IMPRECIO, O.TXINFORM, CO.CDMIGA  "
					+ "FROM TPFG_CENTRALES_RANGOS CR, TPFG_CENTRALES_OFERTAS CO, TPFG_OFERTAS O "
					+ "WHERE CR.CDMIGA=CO.CDMIGA "
					+ "AND CO.CDOFERTA=O.CDOFERTA ";

			hstValues = new Hashtable();
			hstTypes = new Hashtable();

			if (oOffertMainForm.getCliente().getTelefono() != null
					&& !oOffertMainForm.getCliente().getTelefono().equals("")) {
				sql = sql
						+ "AND ? BETWEEN  CR.CDRANDISPI AND CR.CDRANDISPF ";
				hstValues.put(new Integer(1), oOffertMainForm.getCliente()
						.getTelefono());
				hstTypes.put(new Integer(1), GestionCommand.ITYPE_STRING);
			} else if (oOffertMainForm.getCliente().getCodPostal() != null
					&& !oOffertMainForm.getCliente().getCodPostal().equals("")) {
				sql = sql + "AND CR.CDPOSTAL = ? ";
				hstValues.put(new Integer(1), oOffertMainForm.getCliente()
						.getCodPostal());
				hstTypes.put(new Integer(1), GestionCommand.ITYPE_STRING);
			}

			sql = sql + "ORDER BY O.CDTIPOFER, O.IMPRECIO ASC";

			conn = AccesoBBDD.getConnection();

			gc = new GestionCommand(sql);

			ArrayList result = (ArrayList) gc.executeCommand(conn, hstValues,
					hstTypes, GestionCommand.RESULT_ARRAYLIST);

			return result;

		} catch (Exception ex) {
			_logger
					.error(
							"Error buscando las ofertas con cobertura disponibles de BD",
							ex);
			return null;
		} finally {
			try {
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

	}

	public static OffertMainForm cargarInfo(String idClienteVIP,
			OffertMainForm oOffertMainForm) {

		String sql = null;
		Hashtable hstValues = null;
		Hashtable hstTypes = null;

		Connection conn = null;
		GestionCommand gc = null;

		try {

			sql = "SELECT CDTELEFONO, TXDESCNOM, TXDESCAPE, TXCALLE, CDNUMERO,"
					+ "TXLETRA, TXESCALERA, TXPISO, CDPOSTAL, YXPROVIN, TXPOBLAC, CDMIGA "
					+ "FROM TPFG_CLIENTES_VIP "
					+ "WHERE CDCLIVIP = ? ";

			hstValues = new Hashtable();
			hstTypes = new Hashtable();

			hstValues.put(new Integer(1), idClienteVIP);
			hstTypes.put(new Integer(1), GestionCommand.ITYPE_STRING);

			conn = AccesoBBDD.getConnection();

			gc = new GestionCommand(sql);

			ArrayList result = (ArrayList) gc.executeCommand(conn, hstValues,
					hstTypes, GestionCommand.RESULT_ARRAYLIST);

			if (!result.isEmpty()) {
				HashMap aux = (HashMap) result.get(0);

				oOffertMainForm.getCliente().setTelefono(
						(String) aux.get("CDTELEFONO"));
				oOffertMainForm.getCliente().setNombre(
						(String) aux.get("TXDESCNOM"));
				oOffertMainForm.getCliente().setApellidos(
						(String) aux.get("TXDESCAPE"));
				oOffertMainForm.getCliente()
						.setCalle((String) aux.get("TXCALLE"));
				oOffertMainForm.getCliente().setNum((String) aux.get("CDNUMERO"));
				oOffertMainForm.getCliente()
						.setLetra((String) aux.get("TXLETRA"));
				oOffertMainForm.getCliente().setEscalera(
						(String) aux.get("TXESCALERA"));
				oOffertMainForm.getCliente().setPiso((String) aux.get("TXPISO"));
				oOffertMainForm.getCliente().setCodPostal(
						(String) aux.get("CDPOSTAL"));
				oOffertMainForm.getCliente().setProvincia(
						(String) aux.get("YXPROVIN"));
				oOffertMainForm.getCliente().setPoblacion(
						(String) aux.get("TXPOBLAC"));
				oOffertMainForm.getCentral().setCdMiga(
						(String) aux.get("CDMIGA"));

			}

			return oOffertMainForm;

		} catch (Exception ex) {
			_logger
					.error(
							"Error buscando las ofertas con cobertura disponibles de BD",
							ex);
			return null;
		} finally {
			try {
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

	}

	public static boolean guardar(OffertMainForm oOffertMainForm) {

		boolean resultado = false;

		String idVIP = "";

		if (oOffertMainForm.getCliente().getIdCliente() == null
				|| oOffertMainForm.getCliente().getIdCliente().equals("")) {
			idVIP = existeClienteVIP(oOffertMainForm);
		} else {
			idVIP = oOffertMainForm.getCliente().getIdCliente();
		}

		if (idVIP != null && !idVIP.isEmpty()) {
			resultado = actualizarClienteVIP(oOffertMainForm, idVIP);
			if (resultado) {
				resultado = guardarPeticionVIP(oOffertMainForm,
						idVIP);
			}
		} else {
			resultado = guardarClienteVIP(oOffertMainForm);
			if (resultado) {
				resultado = guardarPeticionVIP(oOffertMainForm,
						existeClienteVIP(oOffertMainForm));
			}
		}

		return resultado;

	}

	public static boolean actualizarClienteVIP(
			OffertMainForm oOffertMainForm, String idClienteVIP) {
		boolean modificado = false;

		_logger.debug("Actualizar el cliente VIP");

		Integer result = null;
		String sql = null;
		GestionCommand gc = null;
		Connection conn = null;
		Hashtable hstValues = null;
		Hashtable hstTypes = null;

		try {

			conn = AccesoBBDD.getConnection();
			conn.setAutoCommit(false);

			sql = "UPDATE TPFG_CLIENTES_VIP SET CDMIGA= ? ";

			hstValues = new Hashtable();
			hstTypes = new Hashtable();

			hstValues.put(new Integer(1), oOffertMainForm.getCentral()
					.getCdMiga());
			hstTypes.put(new Integer(1), GestionCommand.ITYPE_STRING);

			int i = 2;

			if (oOffertMainForm.getCliente().getTelefono() != null
					&& !oOffertMainForm.getCliente().getTelefono().equals("")) {
				sql = sql + ", CDTELEFONO = ? ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getTelefono());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getCalle() != null
					&& !oOffertMainForm.getCliente().getCalle().equals("")) {
				sql = sql + ", TXCALLE = ? ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getCalle());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getNum() != null
					&& !oOffertMainForm.getCliente().getNum().equals("")) {
				sql = sql + ", CDNUMERO = ? ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getNum());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getLetra() != null
					&& !oOffertMainForm.getCliente().getLetra().equals("")) {
				sql = sql + ", TXLETRA = ? ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getLetra());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getEscalera() != null
					&& !oOffertMainForm.getCliente().getEscalera().equals("")) {
				sql = sql + ", TXESCALERA = ? ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getEscalera());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getCodPostal() != null
					&& !oOffertMainForm.getCliente().getCodPostal().equals("")) {
				sql = sql + ", CDPOSTAL = ? ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getCodPostal());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getProvincia() != null
					&& !oOffertMainForm.getCliente().getProvincia().equals("")) {
				sql = sql + ", YXPROVIN = ? ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getProvincia());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getPoblacion() != null
					&& !oOffertMainForm.getCliente().getPoblacion().equals("")) {
				sql = sql + ", TXPOBLAC = ? ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getPoblacion());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getPiso() != null
					&& !oOffertMainForm.getCliente().getPiso().equals("")) {
				sql = sql + ", TXPISO = ? ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getPiso());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getNombre() != null
					&& !oOffertMainForm.getCliente().getNombre().equals("")) {
				sql = sql + ", TXDESCNOM = ? ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getNombre());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getApellidos() != null
					&& !oOffertMainForm.getCliente().getApellidos().equals("")) {
				sql = sql + ", TXDESCAPE = ? ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getApellidos());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			sql = sql + " WHERE CDCLIVIP =" + idClienteVIP;

			gc = new GestionCommand(sql);

			// Devuelve el numero de filas
			result = (Integer) gc.executeCommand(conn, hstValues, hstTypes,
					gc.RESULT_ERROR_CODE);

			if (result == null) {
				_logger.error("Error al modificar el cliente VIP en BD");
				return false;
			}

			// Confirmamos
			conn.commit();

			modificado = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			_logger.error(
					"Error modificando usuario  en BD. Haciendo rollback", ex);

			// Rollback de la transacción
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			modificado = false;
		} finally {
			try {
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return modificado;

	}

	public static boolean guardarPeticionVIP(
			OffertMainForm oOffertMainForm, String idClienteVIP) {

		boolean guardado = false;

		_logger.debug("Creación de una peticion VIP");

		Integer result = null;
		String sql = null;
		GestionCommand gc = null;
		Connection conn = null;
		Hashtable hstValues = null;
		Hashtable hstTypes = null;

		try {

			conn = AccesoBBDD.getConnection();
			conn.setAutoCommit(false);

			gc = new GestionCommand(sql);
			hstValues = new Hashtable();
			hstTypes = new Hashtable();
			String res = null;

			sql = "INSERT INTO TPFG_PEDIDOS_VIP (CDCLIVIP, TXFECHA, TXOBSERV) "
					+ "VALUES (?,SYSDATE(),?)";

			gc = new GestionCommand(sql);

			hstValues.put(new Integer(1), idClienteVIP);
			hstTypes.put(new Integer(1), GestionCommand.ITYPE_STRING);

			hstValues.put(new Integer(2), oOffertMainForm.getCliente()
					.getInformacion());
			hstTypes.put(new Integer(2), GestionCommand.ITYPE_STRING);

			gc = new GestionCommand(sql);

			// Devuelve el numero de filas insertadas
			result = (Integer) gc.executeCommand(conn, hstValues, hstTypes,
					gc.RESULT_ERROR_CODE);

			if (result == null) {
				// no se ha dado de alta y se para el proceso de acontratacion
				_logger.error("Error crear una oferta en BD");
				return guardado;
			}

			// Confirmamos
			conn.commit();
			guardado = true;

		} catch (Exception ex) {
			ex.printStackTrace();
			_logger.error(
					"Error insertando un perfil en BD. Haciendo rollback", ex);

			// Rollback de la transacción
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} finally {
			try {
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return guardado;

	}

	public static String existeClienteVIP(OffertMainForm oOffertMainForm) {

		String sql = null;
		Hashtable hstValues = null;
		Hashtable hstTypes = null;

		Connection conn = null;
		GestionCommand gc = null;

		String idClienteVIP = "";

		try {

			sql = "SELECT CDCLIVIP FROM TPFG_CLIENTES_VIP "
					+ "WHERE CDMIGA = ? ";

			hstValues = new Hashtable();
			hstTypes = new Hashtable();

			hstValues.put(new Integer(1), oOffertMainForm.getCentral()
					.getCdMiga());
			hstTypes.put(new Integer(1), GestionCommand.ITYPE_STRING);

			int i = 2;

			if (oOffertMainForm.getCliente().getTelefono() != null
					&& !oOffertMainForm.getCliente().getTelefono().equals("")) {
				sql = sql + "AND CDTELEFONO = ? ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getTelefono());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getCalle() != null
					&& !oOffertMainForm.getCliente().getCalle().equals("")) {
				sql = sql + "AND TXCALLE = ? ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getCalle());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getNum() != null
					&& !oOffertMainForm.getCliente().getNum().equals("")) {
				sql = sql + "AND CDNUMERO = ? ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getNum());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getLetra() != null
					&& !oOffertMainForm.getCliente().getLetra().equals("")) {
				sql = sql + "AND TXLETRA = ? ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getLetra());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getEscalera() != null
					&& !oOffertMainForm.getCliente().getEscalera().equals("")) {
				sql = sql + "AND TXESCALERA = ? ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getEscalera());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getPiso() != null
					&& !oOffertMainForm.getCliente().getPiso().equals("")) {
				sql = sql + "AND TXPISO = ? ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getPiso());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getCodPostal() != null
					&& !oOffertMainForm.getCliente().getCodPostal().equals("")) {
				sql = sql + "AND CDPOSTAL = ? ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getCodPostal());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getProvincia() != null
					&& !oOffertMainForm.getCliente().getProvincia().equals("")) {
				sql = sql + "AND YXPROVIN = ? ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getProvincia());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getPoblacion() != null
					&& !oOffertMainForm.getCliente().getPoblacion().equals("")) {
				sql = sql + "AND TXPOBLAC = ? ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getPoblacion());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			conn = AccesoBBDD.getConnection();

			gc = new GestionCommand(sql);

			ArrayList result = (ArrayList) gc.executeCommand(conn, hstValues,
					hstTypes, GestionCommand.RESULT_ARRAYLIST);

			if (!result.isEmpty()) {
				HashMap aux = (HashMap) result.get(0);
				idClienteVIP = (String) aux.get("CDCLIVIP");

			}

			return idClienteVIP;

		} catch (Exception ex) {
			_logger
					.error(
							"Error buscando las ofertas con cobertura disponibles de BD",
							ex);
			return null;
		} finally {
			try {
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

	}

	public static OffertMainForm existeClienteOferta(
			OffertMainForm oOffertMainForm) {

		String sql = null;
		Hashtable hstValues = null;
		Hashtable hstTypes = null;

		Connection conn = null;
		GestionCommand gc = null;

		OffertMainForm resultado = oOffertMainForm;

		try {

			sql = "SELECT CDCLIENTE, TXDESCNOM, TXDESCAPE, CDNIF, "
					+ " TXCALLE, TXNUMERO, TXESCALERA, TXPISO, TXPOBLACION, "
					+ " TXPROVINCIA, CDPOSTAL, TXFORMPAGO, CDTELEFONO, "
					+ " TXCCC, CDMOVIL, CDFAX, TXDESCEMA, TXLETRA "
					+ " FROM TPFG_CLIENTES WHERE CDMIGA = ? ";

			hstValues = new Hashtable();
			hstTypes = new Hashtable();

			hstValues.put(new Integer(1), oOffertMainForm.getCentral()
					.getCdMiga());
			hstTypes.put(new Integer(1), GestionCommand.ITYPE_STRING);

			int i = 2;

			if (oOffertMainForm.getCliente().getTelefono() != null
					&& !oOffertMainForm.getCliente().getTelefono().equals("")) {
				sql = sql + "AND CDTELEFONO = ? ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getTelefono());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getCalle() != null
					&& !oOffertMainForm.getCliente().getCalle().equals("")) {
				sql = sql + "AND TXCALLE = ? ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getCalle());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getNum() != null
					&& !oOffertMainForm.getCliente().getNum().equals("")) {
				sql = sql + "AND TXNUMERO = ? ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getNum());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getLetra() != null
					&& !oOffertMainForm.getCliente().getLetra().equals("")) {
				sql = sql + "AND "
						+ "TXLETRA = ? ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getLetra());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getEscalera() != null
					&& !oOffertMainForm.getCliente().getEscalera().equals("")) {
				sql = sql + "AND TXESCALERA = ? ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getEscalera());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getPiso() != null
					&& !oOffertMainForm.getCliente().getPiso().equals("")) {
				sql = sql + "AND TXPISO = ? ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getPiso());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getCodPostal() != null
					&& !oOffertMainForm.getCliente().getCodPostal().equals("")) {
				sql = sql + "AND CDPOSTAL = ? ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getCodPostal());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getProvincia() != null
					&& !oOffertMainForm.getCliente().getProvincia().equals("")) {
				sql = sql + "AND TXPROVINCIA = ? ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getProvincia());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getPoblacion() != null
					&& !oOffertMainForm.getCliente().getPoblacion().equals("")) {
				sql = sql + "AND TXPOBLACION = ? ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getPoblacion());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			conn = AccesoBBDD.getConnection();

			gc = new GestionCommand(sql);

			ArrayList result = (ArrayList) gc.executeCommand(conn, hstValues,
					hstTypes, GestionCommand.RESULT_ARRAYLIST);

			if (!result.isEmpty()) {
				HashMap aux = (HashMap) result.get(0);
				resultado.getCliente().setIdCliente(
						(String) aux.get("CDCLIENTE"));
				resultado.getCliente().setNombre((String) aux.get("TXDESCNOM"));
				resultado.getCliente().setApellidos(
						(String) aux.get("TXDESCAPE"));
				resultado.getCliente().setDocumentoID(
						(String) aux.get("CDNIF"));
				resultado.getCliente().setCalle((String) aux.get("TXCALLE"));
				resultado.getCliente().setNum((String) aux.get("TXNUMERO"));
				resultado.getCliente()
						.setEscalera((String) aux.get("TXESCALERA"));
				resultado.getCliente().setPiso((String) aux.get("TXPISO"));
				resultado.getCliente().setPoblacion(
						(String) aux.get("TXPOBLACION"));
				resultado.getCliente().setProvincia(
						(String) aux.get("TXPROVINCIA"));
				resultado.getCliente().setCodPostal(
						(String) aux.get("CDPOSTAL"));
				resultado.getCliente().setFormaPago(
						(String) aux.get("TXFORMPAGO"));
				resultado.getCliente()
						.setTelefono((String) aux.get("CDTELEFONO"));
				resultado.getCliente().setNumeroCuenta(
						(String) aux.get("TXCCC"));
				resultado.getCliente().setMovil((String) aux.get("CDMOVIL"));
				resultado.getCliente().setFax((String) aux.get("CDFAX"));
				resultado.getCliente().setEmail((String) aux.get("TXDESCEMA"));
				resultado.getCliente().setLetra((String) aux.get("TXLETRA"));

				if (resultado.getCliente().getIdCliente() != null
						&& !resultado.getCliente().getIdCliente().isEmpty())
					resultado = cargarDatosOferta(resultado);

			}

			return resultado;

		} catch (Exception ex) {
			_logger
					.error(
							"Error buscando las ofertas con cobertura disponibles de BD",
							ex);
			return null;
		} finally {
			try {
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

	}

	public static OffertMainForm cargarDatosOferta(
			OffertMainForm oOffertMainForm) {

		String sql = null;
		Hashtable hstValues = null;
		Hashtable hstTypes = null;

		Connection conn = null;
		GestionCommand gc = null;

		OffertMainForm resultado = oOffertMainForm;

		try {

			sql = "SELECT P.FHINICIO, P.FHFINAL, O.TXNOMOFER, O.CDTIPOFER, O.CDOFERTA, O.CDPERMAN, "
					+ " O.IMPRECIO, O.TXINFORM FROM TPFG_PEDIDOS P, TPFG_OFERTAS O "
					+ " WHERE P.CDOFERTA=O.CDOFERTA "
					+ " AND P.CDCLIENTE = ? ";

			hstValues = new Hashtable();
			hstTypes = new Hashtable();

			hstValues.put(new Integer(1), oOffertMainForm.getCliente()
					.getIdCliente());
			hstTypes.put(new Integer(1), GestionCommand.ITYPE_STRING);

			conn = AccesoBBDD.getConnection();

			gc = new GestionCommand(sql);

			ArrayList result = (ArrayList) gc.executeCommand(conn, hstValues,
					hstTypes, GestionCommand.RESULT_ARRAYLIST);

			if (!result.isEmpty()) {
				HashMap aux = (HashMap) result.get(0);

				resultado.getPedido().setFechaInicio(
						(String) aux.get("FHINICIO"));
				resultado.getPedido()
						.setFechaFin((String) aux.get("FHFINAL"));
				resultado.getOferta().setNombre((String) aux.get("TXNOMOFER"));
				resultado.getOferta().setTipo((String) aux.get("CDTIPOFER"));
				resultado.getOferta()
						.setIdOferta((String) aux.get("CDOFERTA"));
				resultado.getOferta().setPermanencia(
						(String) aux.get("CDPERMAN"));
				resultado.getOferta().setPrecio((String) aux.get("IMPRECIO"));
				resultado.getOferta().setInformacion(
						(String) aux.get("TXINFORM"));

			}

			return resultado;

		} catch (Exception ex) {
			_logger
					.error(
							"Error buscando las ofertas con cobertura disponibles de BD",
							ex);
			return null;
		} finally {
			try {
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

	}

	public static OffertMainForm cargarOfertaSeleccionada(
			OffertMainForm oOffertMainForm) {

		String sql = null;
		Hashtable hstValues = null;
		Hashtable hstTypes = null;

		Connection conn = null;
		GestionCommand gc = null;

		OffertMainForm resultado = oOffertMainForm;

		try {

			sql = "SELECT O.TXNOMOFER, O.CDTIPOFER, O.CDPERMAN, "
					+ " O.IMPRECIO, O.TXINFORM FROM TPFG_OFERTAS O "
					+ " WHERE O.CDOFERTA = ? ";

			hstValues = new Hashtable();
			hstTypes = new Hashtable();

			hstValues.put(new Integer(1), oOffertMainForm.getOferta()
					.getIdOferta());
			hstTypes.put(new Integer(1), GestionCommand.ITYPE_STRING);

			conn = AccesoBBDD.getConnection();

			gc = new GestionCommand(sql);

			ArrayList result = (ArrayList) gc.executeCommand(conn, hstValues,
					hstTypes, GestionCommand.RESULT_ARRAYLIST);

			if (!result.isEmpty()) {
				HashMap aux = (HashMap) result.get(0);

				resultado.getOferta().setNombre((String) aux.get("TXNOMOFER"));
				resultado.getOferta().setTipo((String) aux.get("CDTIPOFER"));
				resultado.getOferta().setPermanencia(
						(String) aux.get("CDPERMAN"));
				resultado.getOferta().setPrecio((String) aux.get("IMPRECIO"));
				resultado.getOferta().setInformacion(
						(String) aux.get("TXINFORM"));

			}

			return resultado;

		} catch (Exception ex) {
			_logger
					.error(
							"Error buscando las ofertas con cobertura disponibles de BD",
							ex);
			return null;
		} finally {
			try {
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

	}

	public static boolean guardarClienteVIP(OffertMainForm oOffertMainForm) {

		boolean guardado = false;

		_logger.debug("Creación de un cliente VIP");

		Integer result = null;
		String sql = null;
		GestionCommand gc = null;
		Connection conn = null;
		Hashtable hstValues = null;
		Hashtable hstTypes = null;

		try {

			conn = AccesoBBDD.getConnection();
			conn.setAutoCommit(false);

			gc = new GestionCommand(sql);
			hstValues = new Hashtable();
			hstTypes = new Hashtable();
			String res = null;

			sql = "INSERT INTO TPFG_CLIENTES_VIP (   ";

			int i = 1;

			if (oOffertMainForm.getCliente().getTelefono() != null
					&& !oOffertMainForm.getCliente().getTelefono().equals("")) {
				sql = sql + " CDTELEFONO, ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getTelefono());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getNombre() != null
					&& !oOffertMainForm.getCliente().getNombre().equals("")) {
				sql = sql + " TXDESCNOM, ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getNombre());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getApellidos() != null
					&& !oOffertMainForm.getCliente().getApellidos().equals("")) {
				sql = sql + " TXDESCAPE, ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getApellidos());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getCalle() != null
					&& !oOffertMainForm.getCliente().getCalle().equals("")) {
				sql = sql + " TXCALLE, ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getCalle());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getNum() != null
					&& !oOffertMainForm.getCliente().getNum().equals("")) {
				sql = sql + " CDNUMERO, ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getNum());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getLetra() != null
					&& !oOffertMainForm.getCliente().getLetra().equals("")) {
				sql = sql + " TXLETRA, ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getLetra());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getEscalera() != null
					&& !oOffertMainForm.getCliente().getEscalera().equals("")) {
				sql = sql + " TXESCALERA, ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getEscalera());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getPiso() != null
					&& !oOffertMainForm.getCliente().getPiso().equals("")) {
				sql = sql + " TXPISO, ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getPiso());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getCodPostal() != null
					&& !oOffertMainForm.getCliente().getCodPostal().equals("")) {
				sql = sql + " CDPOSTAL, ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getCodPostal());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getProvincia() != null
					&& !oOffertMainForm.getCliente().getProvincia().equals("")) {
				sql = sql + " YXPROVIN, ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getProvincia());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getPoblacion() != null
					&& !oOffertMainForm.getCliente().getPoblacion().equals("")) {
				sql = sql + " TXPOBLAC, ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getPoblacion());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			sql = sql + " CDMIGA) VALUES ( ";
			hstValues.put(new Integer(i), oOffertMainForm.getCentral()
					.getCdMiga());
			hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);

			for (int j = 0; j < i - 1; j++) {
				sql = sql + " ?, ";
			}

			sql = sql + " ?)";

			gc = new GestionCommand(sql);

			// Devuelve el numero de filas insertadas
			result = (Integer) gc.executeCommand(conn, hstValues, hstTypes,
					gc.RESULT_ERROR_CODE);

			if (result == null) {
				// no se ha dado de alta y se para el proceso de acontratacion
				_logger.error("Error crear una oferta en BD");
				return guardado;
			}

			// Confirmamos
			conn.commit();
			guardado = true;

		} catch (Exception ex) {
			ex.printStackTrace();
			_logger.error(
					"Error insertando un perfil en BD. Haciendo rollback", ex);

			// Rollback de la transacción
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			// guardado = false;
		} finally {
			try {
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return guardado;

	}

	public static OffertMainForm crearPedido(OffertMainForm oOffertMainForm) {

		/*
		 * if (oOffertMainForm.getCliente().getTelefono() == null ||
		 * oOffertMainForm.getCliente().getTelefono().isEmpty()) {
		 * 
		 * String telefonoCliente = asignarNumeroTelefono(oOffertMainForm
		 * .getCentral().getCdMiga());
		 * oOffertMainForm.getCliente().setTelefono(telefonoCliente); }
		 */

		boolean newClient = crearNuevoCliente(oOffertMainForm);

		// para sacar el id del cliente
		if (newClient)
			existeClienteOferta(oOffertMainForm);

		crearNuevaPeticion(oOffertMainForm);

		return oOffertMainForm;

	}

	public static String asignarNumeroTelefono(String codigoMiga) {

		String numTelefono = "";

		String sql = null;
		Hashtable hstValues = null;
		Hashtable hstTypes = null;

		Connection conn = null;
		GestionCommand gc = null;

		try {

			sql = "SELECT  CDRANDISPI " + "FROM TPFG_CENTRALES_RANGOS "
					+ "WHERE CDMIGA = ?  "
					+ "AND CDRANDISPF > CDRANDISPI "
					+ "ORDER BY CDRANGO ASC " + "LIMIT 1 ";

			hstValues = new Hashtable();
			hstTypes = new Hashtable();

			hstValues.put(new Integer(1), codigoMiga);
			hstTypes.put(new Integer(1), GestionCommand.ITYPE_STRING);

			conn = AccesoBBDD.getConnection();

			gc = new GestionCommand(sql);

			ArrayList result = (ArrayList) gc.executeCommand(conn, hstValues,
					hstTypes, GestionCommand.RESULT_ARRAYLIST);

			if (!result.isEmpty()) {
				HashMap aux = (HashMap) result.get(0);
				numTelefono = (String) aux.get("CDRANDISPI");
			}

			actualizarRangoDisponible(codigoMiga, numTelefono);

			return numTelefono;

		} catch (Exception ex) {
			_logger
					.error(
							"Error buscando las ofertas con cobertura disponibles de BD",
							ex);
			return null;
		} finally {
			try {
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

	}

	public static void actualizarRangoDisponible(String codigoMiga,
			String numTelefono) {

		Integer result = null;
		String sql = null;
		GestionCommand gc = null;
		Connection conn = null;
		Hashtable hstValues = null;
		Hashtable hstTypes = null;

		try {

			conn = AccesoBBDD.getConnection();
			conn.setAutoCommit(false);

			sql = "UPDATE TPFG_CENTRALES_RANGOS  SET   CDRANDISPI= ? "
					+ " WHERE CDMIGA = " + codigoMiga
					+ " AND CDRANDISPI = " + numTelefono;

			hstValues = new Hashtable();
			hstTypes = new Hashtable();

			int num = Integer.parseInt(numTelefono);

			num = num + 1;

			hstValues.put(new Integer(1), String.valueOf(num));
			hstTypes.put(new Integer(1), GestionCommand.ITYPE_STRING);

			gc = new GestionCommand(sql);

			// Devuelve el numero de filas
			result = (Integer) gc.executeCommand(conn, hstValues, hstTypes,
					gc.RESULT_ERROR_CODE);

			if (result == null) {
				_logger.error("Error al modificar la central  en BD");

			}

			// Confirmamos
			conn.commit();

		} catch (Exception ex) {
			ex.printStackTrace();
			_logger.error(
					"Error modificando usuario  en BD. Haciendo rollback", ex);

			// Rollback de la transacción
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} finally {
			try {
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

	}

	public static boolean crearNuevoCliente(OffertMainForm oOffertMainForm) {

		boolean guardado = false;

		_logger.debug("Creación de un cliente ");

		Integer result = null;
		String sql = null;
		GestionCommand gc = null;
		Connection conn = null;
		Hashtable hstValues = null;
		Hashtable hstTypes = null;

		try {

			conn = AccesoBBDD.getConnection();
			conn.setAutoCommit(false);

			gc = new GestionCommand(sql);
			hstValues = new Hashtable();
			hstTypes = new Hashtable();
			String res = null;

			sql = "INSERT INTO TPFG_CLIENTES (   ";

			int i = 1;

			if (oOffertMainForm.getCliente().getTelefono() != null
					&& !oOffertMainForm.getCliente().getTelefono().equals("")) {
				sql = sql + " CDTELEFONO, ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getTelefono());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getNombre() != null
					&& !oOffertMainForm.getCliente().getNombre().equals("")) {
				sql = sql + " TXDESCNOM, ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getNombre());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getDocumentoID() != null
					&& !oOffertMainForm.getCliente().getDocumentoID()
							.equals("")) {
				sql = sql + " CDNIF, ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getDocumentoID());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getApellidos() != null
					&& !oOffertMainForm.getCliente().getApellidos().equals("")) {
				sql = sql + " TXDESCAPE, ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getApellidos());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getCalle() != null
					&& !oOffertMainForm.getCliente().getCalle().equals("")) {
				sql = sql + " TXCALLE, ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getCalle());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getNum() != null
					&& !oOffertMainForm.getCliente().getNum().equals("")) {
				sql = sql + " TXNUMERO, ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getNum());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getLetra() != null
					&& !oOffertMainForm.getCliente().getLetra().equals("")) {
				sql = sql + " TXLETRA, ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getLetra());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getEscalera() != null
					&& !oOffertMainForm.getCliente().getEscalera().equals("")) {
				sql = sql + " TXESCALERA, ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getEscalera());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getPiso() != null
					&& !oOffertMainForm.getCliente().getPiso().equals("")) {
				sql = sql + " TXPISO, ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getPiso());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getCodPostal() != null
					&& !oOffertMainForm.getCliente().getCodPostal().equals("")) {
				sql = sql + " CDPOSTAL, ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getCodPostal());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getProvincia() != null
					&& !oOffertMainForm.getCliente().getProvincia().equals("")) {
				sql = sql + " TXPROVINCIA, ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getProvincia());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getPoblacion() != null
					&& !oOffertMainForm.getCliente().getPoblacion().equals("")) {
				sql = sql + " TXPOBLACION, ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getPoblacion());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getFormaPago() != null
					&& !oOffertMainForm.getCliente().getFormaPago().equals("")) {
				sql = sql + " TXFORMPAGO, ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getFormaPago());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getNumeroCuenta() != null
					&& !oOffertMainForm.getCliente().getNumeroCuenta().equals(
							"")) {
				sql = sql + " TXCCC, ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getNumeroCuenta());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getMovil() != null
					&& !oOffertMainForm.getCliente().getMovil().equals("")) {
				sql = sql + " CDMOVIL, ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getMovil());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getFax() != null
					&& !oOffertMainForm.getCliente().getFax().equals("")) {
				sql = sql + " CDFAX, ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getFax());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getCliente().getEmail() != null
					&& !oOffertMainForm.getCliente().getEmail().equals("")) {
				sql = sql + " TXDESCEMA, ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getEmail());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			sql = sql + " CDMIGA) VALUES ( ";
			hstValues.put(new Integer(i), oOffertMainForm.getCentral()
					.getCdMiga());
			hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);

			for (int j = 0; j < i - 1; j++) {
				sql = sql + " ?, ";
			}

			sql = sql + " ?)";

			gc = new GestionCommand(sql);

			// Devuelve el numero de filas insertadas
			result = (Integer) gc.executeCommand(conn, hstValues, hstTypes,
					gc.RESULT_ERROR_CODE);

			if (result == null) {
				// no se ha dado de alta y se para el proceso de acontratacion
				_logger.error("Error crear una oferta en BD");
				return guardado;
			}

			// Confirmamos
			conn.commit();
			guardado = true;

		} catch (Exception ex) {
			ex.printStackTrace();
			_logger.error(
					"Error insertando un perfil en BD. Haciendo rollback", ex);

			// Rollback de la transacción
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			// guardado = false;
		} finally {
			try {
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return guardado;

	}

	public static boolean crearNuevaPeticion(OffertMainForm oOffertMainForm) {

		boolean guardado = false;

		_logger.debug("Creación de una peticion ");

		Integer result = null;
		String sql = null;
		GestionCommand gc = null;
		Connection conn = null;
		Hashtable hstValues = null;
		Hashtable hstTypes = null;

		try {

			conn = AccesoBBDD.getConnection();
			conn.setAutoCommit(false);

			gc = new GestionCommand(sql);
			hstValues = new Hashtable();
			hstTypes = new Hashtable();
			String res = null;

			sql = "INSERT INTO TPFG_PEDIDOS (   ";

			int i = 1;

			if (oOffertMainForm.getCliente().getIdCliente() != null
					&& !oOffertMainForm.getCliente().getIdCliente().equals("")) {
				sql = sql + " CDCLIENTE, ";
				hstValues.put(new Integer(i), oOffertMainForm.getCliente()
						.getIdCliente());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			if (oOffertMainForm.getOferta().getPermanencia() != null
					&& !oOffertMainForm.getOferta().getPermanencia().equals("")) {

				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.MONTH, Integer.parseInt(oOffertMainForm
						.getOferta().getPermanencia()));

				java.util.Date date = calendar.getTime();
				java.sql.Date sqlDate = new java.sql.Date(date.getTime());

				sql = sql + " FHFINAL, ";
				hstValues.put(new Integer(i), sqlDate);
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_DATE);
				i = i + 1;
			}

			if (oOffertMainForm.getOferta().getIdOferta() != null
					&& !oOffertMainForm.getOferta().getIdOferta().equals("")) {
				sql = sql + " CDOFERTA, ";
				hstValues.put(new Integer(i), oOffertMainForm.getOferta()
						.getIdOferta());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i = i + 1;
			}

			sql = sql + " FHINICIO) VALUES ( ";

			for (int j = 0; j < i - 1; j++) {
				sql = sql + " ?, ";
			}

			sql = sql + " SYSDATE())";

			gc = new GestionCommand(sql);

			// Devuelve el numero de filas insertadas
			result = (Integer) gc.executeCommand(conn, hstValues, hstTypes,
					gc.RESULT_ERROR_CODE);

			if (result == null) {
				// no se ha dado de alta y se para el proceso de acontratacion
				_logger.error("Error crear una oferta en BD");
				return guardado;
			}

			// Confirmamos
			conn.commit();
			guardado = true;

		} catch (Exception ex) {
			ex.printStackTrace();
			_logger.error(
					"Error insertando un perfil en BD. Haciendo rollback", ex);

			// Rollback de la transacción
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			// guardado = false;
		} finally {
			try {
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return guardado;

	}

	public static void borrarClienteVIP(OffertMainForm oOffertMainForm) {

		_logger
				.debug("Eliminando las peticiones VIP y el cliente VIP");

		Integer result = null;
		String sql = null;
		GestionCommand gc = null;
		Connection conn = null;
		Hashtable hstValues = null;
		Hashtable hstTypes = null;

		try {

			conn = AccesoBBDD.getConnection();
			conn.setAutoCommit(false);

			sql = "DELETE FROM TPFG_PEDIDOS_VIP WHERE CDCLIVIP=? ";

			gc = new GestionCommand(sql);

			hstValues = new Hashtable();
			hstTypes = new Hashtable();

			hstValues.put(new Integer(1), oOffertMainForm.getCliente()
					.getIdCliente());
			hstTypes.put(new Integer(1), GestionCommand.ITYPE_STRING);

			// Devuelve el numero de filas eliminadas
			result = (Integer) gc.executeCommand(conn, hstValues, hstTypes,
					gc.RESULT_ERROR_CODE);

			if (result == null) {
				_logger
						.error("No se ha borrado ninguna fila de la BD porque no existia");

			}

			sql = "DELETE FROM TPFG_PEDIDOS_VIP WHERE CDCLIVIP=? ";

			gc = new GestionCommand(sql);

			hstValues = new Hashtable();
			hstTypes = new Hashtable();

			hstValues.put(new Integer(1), oOffertMainForm.getCliente()
					.getIdCliente());
			hstTypes.put(new Integer(1), GestionCommand.ITYPE_STRING);

			// Devuelve el numero de filas eliminadas
			result = (Integer) gc.executeCommand(conn, hstValues, hstTypes,
					gc.RESULT_ERROR_CODE);

			if (result == null) {
				_logger
						.error("No se ha borrado ninguna fila de la BD porque no existia");

			}

			// Confirmamos
			conn.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			_logger
					.error(
							"Error elimiando los perfiles asignados al usuario en BD. Haciendo rollback",
							ex);

			// Rollback de la transacción
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} finally {
			try {
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

	}

	static public boolean updateClient(OffertMainForm oOffertMainForm) {

		boolean modificado = false;

		_logger.debug("Actualizando cliente en BD");

		Integer result = null;
		String sql = null;
		GestionCommand gc = null;
		Connection conn = null;
		Hashtable hstValues = null;
		Hashtable hstTypes = null;

		try {

			conn = AccesoBBDD.getConnection();
			conn.setAutoCommit(false);

			sql = "UPDATE TPFG_CLIENTES SET TXDESCNOM = ? , TXDESCAPE = ? , CDNIF =?, "
					+ " TXCALLE = ? , TXNUMERO = ?,  TXESCALERA = ? , TXPISO = ? , TXPOBLACION = ? , "
					+ " TXPROVINCIA = ?, CDPOSTAL = ?, TXFORMPAGO = ?, CDTELEFONO = ?, "
					+ " TXCCC = ? , CDMOVIL = ?, CDFAX = ?, TXDESCEMA = ?,  TXLETRA = ?  WHERE CDCLIENTE = ? ";

			gc = new GestionCommand(sql);

			hstValues = new Hashtable();
			hstTypes = new Hashtable();

			hstValues.put(new Integer(1), oOffertMainForm.getCliente()
					.getNombre());
			hstTypes.put(new Integer(1), GestionCommand.ITYPE_STRING);

			hstValues.put(new Integer(2), oOffertMainForm.getCliente()
					.getApellidos());
			hstTypes.put(new Integer(2), GestionCommand.ITYPE_STRING);

			hstValues.put(new Integer(3), oOffertMainForm.getCliente()
					.getDocumentoID());
			hstTypes.put(new Integer(3), GestionCommand.ITYPE_STRING);

			hstValues.put(new Integer(4), oOffertMainForm.getCliente()
					.getCalle());
			hstTypes.put(new Integer(4), GestionCommand.ITYPE_STRING);

			hstValues.put(new Integer(5), new Integer(oOffertMainForm
					.getCliente().getNum()));
			hstTypes.put(new Integer(5), GestionCommand.ITYPE_INT);

			hstValues.put(new Integer(6), oOffertMainForm.getCliente()
					.getEscalera());
			hstTypes.put(new Integer(6), GestionCommand.ITYPE_STRING);

			hstValues.put(new Integer(7), oOffertMainForm.getCliente()
					.getPiso());
			hstTypes.put(new Integer(7), GestionCommand.ITYPE_STRING);

			hstValues.put(new Integer(8), oOffertMainForm.getCliente()
					.getPoblacion());
			hstTypes.put(new Integer(8), GestionCommand.ITYPE_STRING);

			hstValues.put(new Integer(9), oOffertMainForm.getCliente()
					.getProvincia());
			hstTypes.put(new Integer(9), GestionCommand.ITYPE_STRING);

			hstValues.put(new Integer(10), oOffertMainForm.getCliente()
					.getCodPostal());
			hstTypes.put(new Integer(10), GestionCommand.ITYPE_STRING);

			hstValues.put(new Integer(11), oOffertMainForm.getCliente()
					.getFormaPago());
			hstTypes.put(new Integer(11), GestionCommand.ITYPE_STRING);

			hstValues.put(new Integer(12), oOffertMainForm.getCliente()
					.getTelefono());
			hstTypes.put(new Integer(12), GestionCommand.ITYPE_STRING);

			hstValues.put(new Integer(13), oOffertMainForm.getCliente()
					.getNumeroCuenta());
			hstTypes.put(new Integer(13), GestionCommand.ITYPE_STRING);

			hstValues.put(new Integer(14), oOffertMainForm.getCliente()
					.getMovil());
			hstTypes.put(new Integer(14), GestionCommand.ITYPE_STRING);

			hstValues.put(new Integer(15), oOffertMainForm.getCliente()
					.getFax());
			hstTypes.put(new Integer(15), GestionCommand.ITYPE_STRING);

			hstValues.put(new Integer(16), oOffertMainForm.getCliente()
					.getEmail());
			hstTypes.put(new Integer(16), GestionCommand.ITYPE_STRING);

			hstValues.put(new Integer(17), oOffertMainForm.getCliente()
					.getLetra());
			hstTypes.put(new Integer(17), GestionCommand.ITYPE_STRING);

			hstValues.put(new Integer(18), oOffertMainForm.getCliente()
					.getIdCliente());
			hstTypes.put(new Integer(18), GestionCommand.ITYPE_STRING);

			// Devuelve el numero de filas modificadas
			result = (Integer) gc.executeCommand(conn, hstValues, hstTypes,
					gc.RESULT_ERROR_CODE);

			if (result == null) {
				_logger.error("Error al realizar el update en TPFG_CLIENTES");
				return false;
			}

			// Confirmamos
			conn.commit();
			modificado = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			_logger.error(
					"Error modificando TPFG_CLIENTES en BD. Haciendo rollback", ex);

			// Rollback de la transacción
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			modificado = false;
		} finally {
			try {
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return modificado;

	}

	static public boolean updatePedido(OffertMainForm oOffertMainForm) {

		boolean modificado = false;

		_logger.debug("Actualizando pedido en BD");

		Integer result = null;
		String sql = null;
		GestionCommand gc = null;
		Connection conn = null;
		Hashtable hstValues = null;
		Hashtable hstTypes = null;

		try {

			conn = AccesoBBDD.getConnection();
			conn.setAutoCommit(false);

			sql = "UPDATE TPFG_PEDIDOS SET CDOFERTA = ? , FHINICIO = SYSDATE() , FHFINAL =?  WHERE CDCLIENTE = ? ";

			gc = new GestionCommand(sql);

			hstValues = new Hashtable();
			hstTypes = new Hashtable();

			hstValues.put(new Integer(1), oOffertMainForm.getIdOferta());
			hstTypes.put(new Integer(1), GestionCommand.ITYPE_STRING);

			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, Integer.parseInt(oOffertMainForm
					.getOferta().getPermanencia()));

			java.util.Date date = calendar.getTime();
			java.sql.Date sqlDate = new java.sql.Date(date.getTime());

			hstValues.put(new Integer(2), sqlDate);
			hstTypes.put(new Integer(2), GestionCommand.ITYPE_DATE);

			hstValues.put(new Integer(3), oOffertMainForm.getCliente()
					.getIdCliente());
			hstTypes.put(new Integer(3), GestionCommand.ITYPE_STRING);

			// Devuelve el numero de filas modificadas
			result = (Integer) gc.executeCommand(conn, hstValues, hstTypes,
					gc.RESULT_ERROR_CODE);

			if (result == null) {
				_logger.error("Error al realizar el update en TPFG_PEDIDOS");
				return false;
			}

			// Confirmamos
			conn.commit();
			modificado = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			_logger.error("Error modificando TPFG_PEDIDOS en BD. Haciendo rollback",
					ex);

			// Rollback de la transacción
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			modificado = false;
		} finally {
			try {
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return modificado;

	}

	static public boolean sePuedeBorrarPeticion(OffertMainForm oOffertMainForm) {

		boolean resultado = false;

		String sql = null;
		GestionCommand gc = null;
		Connection conn = null;
		Hashtable hstValues = null;
		Hashtable hstTypes = null;

		try {

			conn = AccesoBBDD.getConnection();
			conn.setAutoCommit(false);

			sql = "SELECT CDPEDIDO FROM TPFG_PEDIDOS WHERE FHFINAL <= SYSDATE() AND CDCLIENTE = ?";

			gc = new GestionCommand(sql);

			hstValues = new Hashtable();
			hstTypes = new Hashtable();

			hstValues.put(new Integer(1), oOffertMainForm.getCliente()
					.getIdCliente());
			hstTypes.put(new Integer(1), GestionCommand.ITYPE_STRING);

			ArrayList result = (ArrayList) gc.executeCommand(conn, hstValues,
					hstTypes, GestionCommand.RESULT_ARRAYLIST);

			if (!result.isEmpty()) {
				resultado = true;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			resultado = false;
		} finally {
			try {
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return resultado;

	}

	public static boolean deleteClient(OffertMainForm oOffertMainForm) {
		boolean borrado = false;

		_logger.debug("Eliminando CLIENTE en BD");

		Integer result = null;
		String sql = null;
		String sqlPrevia = null;
		GestionCommand gc = null;
		Connection conn = null;
		Hashtable hstValues = null;
		Hashtable hstTypes = null;

		try {

			conn = AccesoBBDD.getConnection();
			conn.setAutoCommit(false);

			sqlPrevia = "DELETE FROM TPFG_CLIENTES WHERE CDCLIENTE = ? ";

			gc = new GestionCommand(sqlPrevia);

			hstValues = new Hashtable();
			hstTypes = new Hashtable();

			hstValues.put(new Integer(1), new Integer(oOffertMainForm
					.getCliente().getIdCliente()));
			hstTypes.put(new Integer(1), GestionCommand.ITYPE_INT);

			// Devuelve el numero de filas eliminadas
			result = (Integer) gc.executeCommand(conn, hstValues, hstTypes,
					gc.RESULT_ERROR_CODE);

			if (result == null) {
				_logger.error("Ningun CLIENTE a eliminar ");
			}
			// Confirmamos
			conn.commit();
			borrado = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			_logger.error("Haciendo rollback", ex); // Rollback de la
													// transacción
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			borrado = false;
		} finally {
			try {
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return borrado;
	}

	public static boolean deletePedido(OffertMainForm oOffertMainForm) {
		boolean borrado = false;

		_logger.debug("Eliminando PEDIDO en BD");

		Integer result = null;
		String sql = null;
		String sqlPrevia = null;
		GestionCommand gc = null;
		Connection conn = null;
		Hashtable hstValues = null;
		Hashtable hstTypes = null;

		try {

			conn = AccesoBBDD.getConnection();
			conn.setAutoCommit(false);

			sqlPrevia = "DELETE FROM TPFG_PEDIDOS WHERE CDCLIENTE = ? ";

			gc = new GestionCommand(sqlPrevia);

			hstValues = new Hashtable();
			hstTypes = new Hashtable();

			hstValues.put(new Integer(1), new Integer(oOffertMainForm
					.getCliente().getIdCliente()));
			hstTypes.put(new Integer(1), GestionCommand.ITYPE_INT);

			// Devuelve el numero de filas eliminadas
			result = (Integer) gc.executeCommand(conn, hstValues, hstTypes,
					gc.RESULT_ERROR_CODE);

			if (result == null) {
				_logger.error("Ninguna peticion a eliminar ");
			}
			// Confirmamos
			conn.commit();
			borrado = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			_logger.error("Haciendo rollback", ex); // Rollback de la
													// transacción
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			borrado = false;
		} finally {
			try {
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return borrado;
	}

}
