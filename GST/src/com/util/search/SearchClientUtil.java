package com.util.search;

import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import com.forms.DataBean;
import com.forms.search.SearchClientForm;
import com.util.AccesoBBDD;
import com.util.GestionCommand;
import com.util.LoggerFactory;
import com.util.Pager;

/**
 * Clase de utilidad para la busqueda de las peticiones VIP
 * 
 * @author Manuel Guerrero del Pino
 * @version 1.0
 */

public class SearchClientUtil {

	private static Logger _logger = LoggerFactory
			.getLogger(SearchClientUtil.class);

	public static Pager buscar(SearchClientForm oSearchClientForm) {

		String sql = null;
		Hashtable hstValues = null;
		Hashtable hstTypes = null;

		Connection conn = null;
		GestionCommand gc = null;

		try {

			sql = "SELECT CV.CDCLIVIP, CV.CDTELEFONO,CV.TXDESCNOM, CV.TXDESCAPE,  PV.TXFECHA, PV.TXOBSERV "
					+ "FROM TPFG_CLIENTES_VIP CV, TPFG_PEDIDOS_VIP PV, TPFG_CENTRALES C "
					+ "WHERE CV.CDCLIVIP=PV.CDCLIVIP "
					+ "AND CV.CDMIGA=C.CDMIGA ";

			hstValues = new Hashtable();
			hstTypes = new Hashtable();

			int i = 1;

			if (oSearchClientForm.getCentral().getProvincia() != null
					&& !oSearchClientForm.getCentral().getProvincia()
							.equals("")) {
				sql = sql + "AND C.TXPROVIN = ? ";
				hstValues.put(new Integer(i), oSearchClientForm.getCentral()
						.getProvincia());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i++;
			}

			if (oSearchClientForm.getCentral().getPoblacion() != null
					&& !oSearchClientForm.getCentral().getPoblacion()
							.equals("")) {
				sql = sql + "AND C.TXPOBLAC = ? ";
				hstValues.put(new Integer(i), oSearchClientForm.getCentral()
						.getPoblacion());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i++;
			}

			if (oSearchClientForm.getCentral().getCdPostal() != null
					&& !oSearchClientForm.getCentral().getCdPostal()
							.equals("")) {
				sql = sql + "AND C.CDPOSTAL = ? ";
				hstValues.put(new Integer(i), oSearchClientForm.getCentral()
						.getCdPostal());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i++;
			}

			if (oSearchClientForm.getCentral().getCdMiga() != null
					&& !oSearchClientForm.getCentral().getCdMiga().equals("")) {
				sql = sql + "AND C.CDMIGA = ? ";
				hstValues.put(new Integer(i), oSearchClientForm.getCentral()
						.getCdMiga());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i++;
			}

			if (oSearchClientForm.getCliente().getNombre() != null
					&& !oSearchClientForm.getCliente().getNombre().equals("")) {
				sql = sql + "AND CV.TXDESCNOM = ? ";
				hstValues.put(new Integer(i), oSearchClientForm.getCliente()
						.getNombre());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i++;
			}

			if (oSearchClientForm.getCliente().getApellidos() != null
					&& !oSearchClientForm.getCliente().getApellidos()
							.equals("")) {
				sql = sql + "AND CV.TXDESCAPE = ? ";
				hstValues.put(new Integer(i), oSearchClientForm.getCliente()
						.getApellidos());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i++;
			}

			if (oSearchClientForm.getCliente().getProvincia() != null
					&& !oSearchClientForm.getCliente().getProvincia()
							.equals("")) {
				sql = sql + "AND CV.YXPROVIN = ? ";
				hstValues.put(new Integer(i), oSearchClientForm.getCliente()
						.getProvincia());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i++;
			}

			if (oSearchClientForm.getCliente().getPoblacion() != null
					&& !oSearchClientForm.getCliente().getPoblacion()
							.equals("")) {
				sql = sql + "AND CV.TXPOBLAC = ? ";
				hstValues.put(new Integer(i), oSearchClientForm.getCliente()
						.getPoblacion());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i++;
			}

			if (oSearchClientForm.getCliente().getCodPostal() != null
					&& !oSearchClientForm.getCliente().getCodPostal()
							.equals("")) {
				sql = sql + "AND CV.CDPOSTAL = ? ";
				hstValues.put(new Integer(i), oSearchClientForm.getCliente()
						.getCodPostal());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i++;
			}

			if (oSearchClientForm.getCliente().getTelefono() != null
					&& !oSearchClientForm.getCliente().getTelefono().equals("")) {
				sql = sql + "AND CV.CDTELEFONO = ? ";
				hstValues.put(new Integer(i), oSearchClientForm.getCliente()
						.getTelefono());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i++;
			}

			if (oSearchClientForm.getCliente().getCalle() != null
					&& !oSearchClientForm.getCliente().getCalle().equals("")) {
				sql = sql + "AND CV.TXCALLE = ? ";
				hstValues.put(new Integer(i), oSearchClientForm.getCliente()
						.getCalle());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i++;
			}

			if (oSearchClientForm.getCliente().getNum() != null
					&& !oSearchClientForm.getCliente().getNum().equals("")) {
				sql = sql + "AND CV.CDNUMERO = ? ";
				hstValues.put(new Integer(i), oSearchClientForm.getCliente()
						.getNum());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i++;
			}

			if (oSearchClientForm.getCliente().getPiso() != null
					&& !oSearchClientForm.getCliente().getPiso().equals("")) {
				sql = sql + "AND CV.TXPISO = ? ";
				hstValues.put(new Integer(i), oSearchClientForm.getCliente()
						.getPiso());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i++;
			}

			if (oSearchClientForm.getCliente().getLetra() != null
					&& !oSearchClientForm.getCliente().getLetra().equals("")) {
				sql = sql + "AND CV.TXLETRA = ? ";
				hstValues.put(new Integer(i), oSearchClientForm.getCliente()
						.getLetra());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i++;
			}

			if (oSearchClientForm.getCliente().getEscalera() != null
					&& !oSearchClientForm.getCliente().getEscalera().equals("")) {
				sql = sql + "AND CV.TXESCALERA = ? ";
				hstValues.put(new Integer(i), oSearchClientForm.getCliente()
						.getEscalera());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i++;
			}

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

			if (oSearchClientForm.getFechaInicio() != null
					&& !oSearchClientForm.getFechaInicio().equals("")) {
				sql = sql + "AND PV.TXFECHA >= ? ";
				Date date = sdf.parse(oSearchClientForm.getFechaInicio());
				hstValues.put(new Integer(i), sdf2.format(date));
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i++;
			}

			if (oSearchClientForm.getFechaFin() != null
					&& !oSearchClientForm.getFechaFin().equals("")) {
				sql = sql + "AND PV.TXFECHA <= ? ";
				Date date = sdf.parse(oSearchClientForm.getFechaFin());
				hstValues.put(new Integer(i), sdf2.format(date));
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i++;
			}

			sql = sql + "ORDER BY PV.CDCLIVIP DESC";

			conn = AccesoBBDD.getConnection();

			gc = new GestionCommand(sql);

			ArrayList result = (ArrayList) gc.executeCommand(conn, hstValues,
					hstTypes, GestionCommand.RESULT_ARRAYLIST);

			Pager p = new Pager(result, 15);

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

	public static void visualizarClienteVIP(
			SearchClientForm oSearchClientForm) {

		String sql = null;
		Hashtable hstValues = null;
		Hashtable hstTypes = null;

		Connection conn = null;
		GestionCommand gc = null;

		try {

			sql = "SELECT TXDESCNOM, CDTELEFONO, TXDESCAPE, TXCALLE, CDNUMERO, TXLETRA, TXESCALERA, TXPISO, "
					+ "CDPOSTAL, YXPROVIN, TXPOBLAC, CDMIGA "
					+ "FROM TPFG_CLIENTES_VIP "
					+ "WHERE CDCLIVIP = ? ";

			hstValues = new Hashtable();
			hstTypes = new Hashtable();

			hstValues.put(new Integer(1), oSearchClientForm.getId());
			hstTypes.put(new Integer(1), GestionCommand.ITYPE_STRING);

			conn = AccesoBBDD.getConnection();

			gc = new GestionCommand(sql);

			ResultSet rsResult = (ResultSet) gc.executeCommand(hstValues,
					hstTypes, GestionCommand.RESULT_RESULTSET);

			if (rsResult.next()) {

				oSearchClientForm.getCliente().setNombre(
						rsResult.getString("TXDESCNOM"));
				oSearchClientForm.getCliente().setTelefono(
						rsResult.getString("CDTELEFONO"));
				oSearchClientForm.getCliente().setApellidos(
						rsResult.getString("TXDESCAPE"));
				oSearchClientForm.getCliente().setCalle(
						rsResult.getString("TXCALLE"));
				oSearchClientForm.getCliente().setNum(
						rsResult.getString("CDNUMERO"));
				oSearchClientForm.getCliente().setLetra(
						rsResult.getString("TXLETRA"));
				oSearchClientForm.getCliente().setEscalera(
						rsResult.getString("TXESCALERA"));
				oSearchClientForm.getCliente().setPiso(
						rsResult.getString("TXPISO"));
				oSearchClientForm.getCliente().setCodPostal(
						rsResult.getString("CDPOSTAL"));
				oSearchClientForm.getCliente().setProvincia(
						rsResult.getString("YXPROVIN"));
				oSearchClientForm.getCliente().setPoblacion(
						rsResult.getString("TXPOBLAC"));
				oSearchClientForm.getCentral().setCdMiga(
						rsResult.getString("CDMIGA"));
			}

			recuperaInfoCentralita(oSearchClientForm);

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

	public static ArrayList listaOfertasCentral(String codMiga) {

		ArrayList listaResult = new ArrayList();

		String sql = null;
		Hashtable hstValues = null;
		Hashtable hstTypes = null;

		Connection conn = null;
		GestionCommand gc = null;

		try {

			sql = "SELECT O.TXNOMOFER FROM TPFG_CENTRALES_OFERTAS CO, OFERTAS O  "
					+ "WHERE CO.CDOFERTA=O.CDOFERTA "
					+ "AND CO.CDMIGA = ?";

			hstValues = new Hashtable();
			hstTypes = new Hashtable();

			hstValues.put(new Integer(1), codMiga);
			hstTypes.put(new Integer(1), GestionCommand.ITYPE_STRING);

			conn = AccesoBBDD.getConnection();

			gc = new GestionCommand(sql);

			ResultSet rs = (ResultSet) gc.executeCommand(hstValues, hstTypes,
					GestionCommand.RESULT_RESULTSET);

			while (rs.next()) {
				listaResult.add(rs.getString("TXNOMOFER"));
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

		return listaResult;

	}

	public static ArrayList recuperaInfoCentralita(
			SearchClientForm oSearchClientForm) {

		ArrayList listaResult = new ArrayList();

		String sql = null;
		Hashtable hstValues = null;
		Hashtable hstTypes = null;

		Connection conn = null;
		GestionCommand gc = null;

		try {

			sql = "SELECT TXPROVIN, TXPOBLAC, CDPOSTAL, TXDESCCEN  "
					+ "FROM TPFG_CENTRALES WHERE CDMIGA = ? ";

			hstValues = new Hashtable();
			hstTypes = new Hashtable();

			hstValues.put(new Integer(1), oSearchClientForm.getCentral()
					.getCdMiga());
			hstTypes.put(new Integer(1), GestionCommand.ITYPE_STRING);

			conn = AccesoBBDD.getConnection();

			gc = new GestionCommand(sql);

			ResultSet rsResult = (ResultSet) gc.executeCommand(hstValues,
					hstTypes, GestionCommand.RESULT_RESULTSET);

			if (rsResult.next()) {

				oSearchClientForm.getCentral().setNombre(
						rsResult.getString("TXDESCCEN"));
				oSearchClientForm.getCentral().setCdPostal(
						rsResult.getString("CDPOSTAL"));
				oSearchClientForm.getCentral().setProvincia(
						rsResult.getString("TXPROVIN"));
				oSearchClientForm.getCentral().setPoblacion(
						rsResult.getString("TXPOBLAC"));
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

		return listaResult;

	}

	public static ArrayList listaPeticionesVIP(String idClienteVIP) {

		ArrayList listaResult = new ArrayList();

		String sql = null;
		Hashtable hstValues = null;
		Hashtable hstTypes = null;

		Connection conn = null;
		GestionCommand gc = null;

		try {

			sql = "SELECT TXFECHA, TXOBSERV  "
					+ "FROM TPFG_PEDIDOS_VIP WHERE CDCLIVIP = ? "
					+ "ORDER BY TXFECHA DESC ";

			hstValues = new Hashtable();
			hstTypes = new Hashtable();

			hstValues.put(new Integer(1), idClienteVIP);
			hstTypes.put(new Integer(1), GestionCommand.ITYPE_STRING);

			conn = AccesoBBDD.getConnection();

			gc = new GestionCommand(sql);

			ResultSet rs = (ResultSet) gc.executeCommand(hstValues, hstTypes,
					GestionCommand.RESULT_RESULTSET);

			while (rs.next()) {
				listaResult.add(new DataBean(rs.getString("TXFECHA"), rs
						.getString("TXOBSERV")));
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

		return listaResult;

	}

}
