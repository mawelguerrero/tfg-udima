package com.util.admin;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;

import com.dto.UserDTO;
import com.util.GestionCommand;
import com.util.LoggerFactory;
import com.util.Option;

/**
 * Clase de utilidad para la gestion del menú
 * 
 * @author Manuel Guerrero del Pino
 * @version 1.0
 */

public class Options extends ActionForm {

	private static Logger _logger = LoggerFactory.getLogger(Options.class);

	private Options _menu = null;

	public Options() {
	}

	public Options getMenu() {
		return _menu;
	}

	public static ArrayList getOpcionesMenuOLD(String idUsuario) {

		ArrayList pathOptions = new ArrayList();

		Option o = null;
		o = new Option("/centralAdmin.do?pageEvent=success",
				"Gestión de Centrales", "0");
		pathOptions.add(0, o);
		o = new Option("/offertAdmin.do?pageEvent=success",
				"Gestión de Ofertas", "0");
		pathOptions.add(0, o);
		o = new Option("/profileAdmin.do?operation=success",
				"Gestión de Roles", "0");
		pathOptions.add(0, o);
		o = new Option("/usersAdmin.do?operation=success",
				"Gestión de Usuarios", "0");
		pathOptions.add(0, o);
		o = new Option("URL", "Administración", "1");
		pathOptions.add(0, o);
		o = new Option("/searchClient.do?operation=success",
				"Buscador de Clientes VIP", "0");
		pathOptions.add(0, o);
		o = new Option("/search.do?operation=success", "Buscador", "0");
		pathOptions.add(0, o);
		o = new Option("URL", "Información", "1");
		pathOptions.add(0, o);
		o = new Option("/offertMain.do?operation=success",
				"Contratación de Ofertas", "0");
		pathOptions.add(0, o);
		o = new Option("/coverage.do?operation=success",
				"Consulta de Cobertura", "0");
		pathOptions.add(0, o);
		o = new Option("URL", "Contratación", "1");
		pathOptions.add(0, o);

		return pathOptions;
	}

	public static ArrayList getOpcionesMenu(String idUsuario) {

		ArrayList pathOptions = new ArrayList();

		UserDTO oUser = null;

		String sql = "SELECT OP.CDPERMISO, OP.TXNOMPERM , OP.TXURL, OP.CDNIVEL "
				+ "FROM TPFG_USUARIOS USU, TPFG_USUROLES USUPER, TPFG_ROLPERMISOS PEROP, TPFG_PERMISOS OP "
				+ "WHERE USU.CDUSUARIO = ?"
				+ "AND USU.CDUSUARIO=USUPER.CDUSUARIO "
				+ "AND USUPER.CDROLUSU=PEROP.CDROLUSU "
				+ "AND PEROP.CDPERMISO=OP.CDPERMISO "
				+ "ORDER BY OP.CDPERMISO DESC";

		GestionCommand gc = new GestionCommand(sql, false);
		Hashtable hstValues = new Hashtable();
		Hashtable hstTypes = new Hashtable();

		hstValues.put(new Integer(1), idUsuario);
		hstTypes.put(new Integer(1), GestionCommand.ITYPE_STRING);

		try {
			ResultSet rs = (ResultSet) gc.executeCommand(hstValues, hstTypes,
					GestionCommand.RESULT_RESULTSET);
			if (!rs.next()) {
				_logger.error("Error al recuperar las opciones de menu");
			} else {

				Option o = null;
				while (rs.next()) {

					o = new Option(rs.getString("URL"), rs.getString("NOMBRE"),
							rs.getString("NIVEL"));
					pathOptions.add(0, o);
				}

			}
		} catch (java.sql.SQLException ex) {
			ex.printStackTrace();
			_logger.error("Error al recuperar los permisos de menu" + ex);
		}

		return pathOptions;
	}

	public static ArrayList getListaOpcionesMenu(String idUsuario) {

		ArrayList lista = new ArrayList();

		String sql = "SELECT OP.CDPERMISO,OP.CDPERMHER "
				+ "FROM TPFG_USUARIOS USU, TPFG_USUROLES USUPER, TPFG_ROLPERMISOS PEROP, TPFG_PERMISOS OP "
				+ "WHERE USU.CDUSUARIO = ?"
				+ "AND USU.CDUSUARIO=USUPER.CDUSUARIO "
				+ "AND USUPER.CDROLUSU=PEROP.CDROLUSU "
				+ "AND PEROP.CDPERMISO=OP.CDPERMISO "
				+ "ORDER BY OP.CDPERMISO DESC";
		
		GestionCommand gc = new GestionCommand(sql, false);
		Hashtable hstValues = new Hashtable();
		Hashtable hstTypes = new Hashtable();

		hstValues.put(new Integer(1), idUsuario);
		hstTypes.put(new Integer(1), GestionCommand.ITYPE_STRING);

		try {
			ResultSet rs = (ResultSet) gc.executeCommand(hstValues, hstTypes,
					GestionCommand.RESULT_RESULTSET);
			while (rs.next()) {

				if (!lista.contains(rs.getString("CDPERMISO"))) {
					lista.add(rs.getString("CDPERMISO"));
				}

				if (!lista.contains(rs.getString("CDPERMHER"))) {
					lista.add(rs.getString("CDPERMHER"));
				}

			}

		} catch (java.sql.SQLException ex) {
			ex.printStackTrace();
			_logger.error("Error al recuperar la lista de permisos de menu"
					+ ex);
		}

		return lista;
	}

	public static ArrayList getOpcionesMenuNEW(String idUsuario) {

		// recuperan la lista de opciones
		ArrayList lista = getListaOpcionesMenu(idUsuario);

		ArrayList pathOptions = new ArrayList();

		String sql = "SELECT CDPERMISO, TXNOMPERM , TXURL, CDNIVEL "
				+ "FROM TPFG_PERMISOS ";

		for (int i = 0; i < lista.size(); i = i + 1) {
			if (i == 0) {
				sql = sql + "WHERE CDPERMISO = ? ";
			} else {
				sql = sql + "OR CDPERMISO = ? ";
			}
		}

		sql = sql + "ORDER BY CDPERMISO DESC";

		GestionCommand gc = new GestionCommand(sql, false);
		Hashtable hstValues = new Hashtable();
		Hashtable hstTypes = new Hashtable();

		for (int i = 0; i < lista.size(); i = i + 1) {
			hstValues.put(new Integer(i + 1), lista.get(i));
			hstTypes.put(new Integer(i + 1), GestionCommand.ITYPE_STRING);
		}

		try {
			ResultSet rs = (ResultSet) gc.executeCommand(hstValues, hstTypes,
					GestionCommand.RESULT_RESULTSET);

			Option o = null;
			while (rs.next()) {

				o = new Option(rs.getString("TXURL"), rs.getString("TXNOMPERM"), rs
						.getString("CDNIVEL"));
				pathOptions.add(0, o);
			}

		} catch (java.sql.SQLException ex) {
			ex.printStackTrace();
			_logger.error("Error al recuperar los permisos de menu" + ex);
		}

		return pathOptions;
	}

} // Options
