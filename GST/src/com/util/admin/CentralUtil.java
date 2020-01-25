package com.util.admin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import com.forms.DataBean;
import com.forms.admin.CentralAdminForm;
import com.util.AccesoBBDD;
import com.util.GestionCommand;
import com.util.LoggerFactory;
import com.util.Pager;

import com.util.Constantes;

/**
 * Clase de utilidad para la gestion de centralitas
 * 
 * @author Manuel Guerrero del Pino
 * @version 1.0
 */

public class CentralUtil {

	private static Logger _logger = LoggerFactory.getLogger(CentralUtil.class);

	public static Pager buscar(CentralAdminForm oCentralAdminForm) {

		String sql = null;
		Hashtable hstValues = null;
		Hashtable hstTypes = null;

		Connection conn = null;
		GestionCommand gc = null;

		try {
			// Sentencia SQL - Obtención centrales
			sql = "SELECT CDMIGA, TXPROVIN, TXPOBLAC, CDPOSTAL, TXDESCCEN  "
					+ "FROM TPFG_CENTRALES  ";

			hstValues = new Hashtable();
			hstTypes = new Hashtable();

			int i = 1;

			if (oCentralAdminForm.getCentral().getProvincia() != null
					&& !oCentralAdminForm.getCentral().getProvincia()
							.equals("")) {

				if (i == 1) {
					sql = sql + Constantes.SQL_WHERE;
				} else {
					sql = sql + Constantes.SQL_AND;
				}

				sql = sql + "TXPROVIN = ? ";
				hstValues.put(new Integer(i), oCentralAdminForm.getCentral()
						.getProvincia());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i++;
			}

			if (oCentralAdminForm.getCentral().getPoblacion() != null
					&& !oCentralAdminForm.getCentral().getPoblacion()
							.equals("")) {
				if (i == 1) {
					sql = sql + Constantes.SQL_WHERE;
				} else {
					sql = sql + Constantes.SQL_AND;
				}
				sql = sql + "TXPOBLAC = ? ";
				hstValues.put(new Integer(i), oCentralAdminForm.getCentral()
						.getPoblacion());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i++;
			}

			if (oCentralAdminForm.getCentral().getCdPostal() != null
					&& !oCentralAdminForm.getCentral().getCdPostal()
							.equals("")) {
				if (i == 1) {
					sql = sql + Constantes.SQL_WHERE;
				} else {
					sql = sql + Constantes.SQL_AND;
				}
				sql = sql + "CDPOSTAL = ? ";
				hstValues.put(new Integer(i), oCentralAdminForm.getCentral()
						.getCdPostal());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i++;
			}

			if (oCentralAdminForm.getCentral().getCdMiga() != null
					&& !oCentralAdminForm.getCentral().getCdMiga().equals("")) {
				if (i == 1) {
					sql = sql + Constantes.SQL_WHERE;
				} else {
					sql = sql + Constantes.SQL_AND;
				}
				sql = sql + "CDMIGA = ? ";
				hstValues.put(new Integer(i), oCentralAdminForm.getCentral()
						.getCdMiga());
				hstTypes.put(new Integer(i), GestionCommand.ITYPE_STRING);
				i++;
			}

			sql = sql + "ORDER BY CDMIGA ASC";

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

	public static void cargarCentral(CentralAdminForm oCentralAdminForm) {

		String sql = null;
		Hashtable hstValues = null;
		Hashtable hstTypes = null;

		Connection conn = null;
		GestionCommand gc = null;

		try {

			sql = "SELECT TXDESCCEN, CDMIGA, TXPROVIN, TXPOBLAC, CDPOSTAL  "
					+ "FROM TPFG_CENTRALES  " + "WHERE CDMIGA = ? ";

			hstValues = new Hashtable();
			hstTypes = new Hashtable();

			hstValues.put(new Integer(1), oCentralAdminForm.getId());
			hstTypes.put(new Integer(1), GestionCommand.ITYPE_STRING);

			conn = AccesoBBDD.getConnection();

			gc = new GestionCommand(sql);

			ResultSet rsResult = (ResultSet) gc.executeCommand(hstValues,
					hstTypes, GestionCommand.RESULT_RESULTSET);

			if (rsResult.next()) {

				oCentralAdminForm.getCentral().setNombre(
						rsResult.getString("TXDESCCEN"));
				oCentralAdminForm.getCentral().setCdMiga(
						rsResult.getString("CDMIGA"));
				oCentralAdminForm.getCentral().setCdPostal(
						rsResult.getString("CDPOSTAL"));
				oCentralAdminForm.getCentral().setPoblacion(
						rsResult.getString("TXPOBLAC"));
				oCentralAdminForm.getCentral().setProvincia(
						rsResult.getString("TXPROVIN"));
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

	public static ArrayList cargarOfertasSeleccionadasCentral(
			CentralAdminForm oCentralAdminForm) {

		String sql = null;
		Hashtable hstValues = null;
		Hashtable hstTypes = null;

		Connection conn = null;
		GestionCommand gc = null;

		ArrayList listaSeleccionada = new ArrayList();

		try {

			hstValues = new Hashtable();
			hstTypes = new Hashtable();

			hstValues.put(new Integer(1), oCentralAdminForm.getId());
			hstTypes.put(new Integer(1), GestionCommand.ITYPE_STRING);

			conn = AccesoBBDD.getConnection();

			sql = "SELECT O.CDOFERTA , O.TXNOMOFER FROM TPFG_CENTRALES_OFERTAS CO, TPFG_OFERTAS O "
					+ " WHERE CO.CDOFERTA=O.CDOFERTA AND CO.CDMIGA =?";

			gc = new GestionCommand(sql);

			ResultSet listaSeleccionadaResult = (ResultSet) gc.executeCommand(
					hstValues, hstTypes, GestionCommand.RESULT_RESULTSET);

			while (listaSeleccionadaResult.next()) {
				listaSeleccionada.add(new DataBean(listaSeleccionadaResult
						.getString("CDOFERTA"), listaSeleccionadaResult
						.getString("TXNOMOFER")));
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

		return listaSeleccionada;

	}

	public static ArrayList cargarOfertasDisponiblesCentral(
			CentralAdminForm oCentralAdminForm) {

		String sql = null;
		Hashtable hstValues = null;
		Hashtable hstTypes = null;

		Connection conn = null;
		GestionCommand gc = null;

		ArrayList listaDisponible = new ArrayList();

		try {

			hstValues = new Hashtable();
			hstTypes = new Hashtable();

			hstValues.put(new Integer(1), oCentralAdminForm.getId());
			hstTypes.put(new Integer(1), GestionCommand.ITYPE_STRING);

			conn = AccesoBBDD.getConnection();

			// OFERTAS DISPONIBLES
			sql = "SELECT O.CDOFERTA , O.TXNOMOFER FROM TPFG_OFERTAS O "
					+ "WHERE O.CDOFERTA  NOT IN ( "
					+ "SELECT OF.CDOFERTA FROM TPFG_CENTRALES_OFERTAS CO, TPFG_OFERTAS OF "
					+ "WHERE CO.CDOFERTA=OF.CDOFERTA AND CO.CDMIGA = ?)";

			gc = new GestionCommand(sql);

			ResultSet listaDisponiblesResult = (ResultSet) gc.executeCommand(
					hstValues, hstTypes, GestionCommand.RESULT_RESULTSET);

			while (listaDisponiblesResult.next()) {
				listaDisponible.add(new DataBean(listaDisponiblesResult
						.getString("CDOFERTA"), listaDisponiblesResult
						.getString("TXNOMOFER")));
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

		return listaDisponible;
	}

	public static boolean modificarCentral(CentralAdminForm oCentralAdminForm) {
		boolean modificado = false;

		_logger.debug("Modificando Central");

		Integer result = null;
		String sql = null;
		GestionCommand gc = null;
		Connection conn = null;
		Hashtable hstValues = null;
		Hashtable hstTypes = null;

		try {

			conn = AccesoBBDD.getConnection();
			conn.setAutoCommit(false);

			sql = "UPDATE TPFG_CENTRALES  SET   TXPROVIN=? , TXPOBLAC=?, CDPOSTAL=?, "
					+ " TXDESCCEN = ?   WHERE CDMIGA ="
					+ oCentralAdminForm.getCentral().getCdMiga();

			hstValues = new Hashtable();
			hstTypes = new Hashtable();

			hstValues.put(new Integer(1), oCentralAdminForm.getCentral()
					.getProvincia());
			hstTypes.put(new Integer(1), GestionCommand.ITYPE_STRING);
			hstValues.put(new Integer(2), oCentralAdminForm.getCentral()
					.getPoblacion());
			hstTypes.put(new Integer(2), GestionCommand.ITYPE_STRING);
			hstValues.put(new Integer(3), oCentralAdminForm.getCentral()
					.getCdPostal());
			hstTypes.put(new Integer(3), GestionCommand.ITYPE_STRING);
			hstValues.put(new Integer(4), oCentralAdminForm.getCentral()
					.getNombre());
			hstTypes.put(new Integer(4), GestionCommand.ITYPE_STRING);

			gc = new GestionCommand(sql);

			// Devuelve el numero de filas
			result = (Integer) gc.executeCommand(conn, hstValues, hstTypes,
					gc.RESULT_ERROR_CODE);

			if (result == null) {
				_logger.error("Error al modificar la central  en BD");
				return false;
			}

			// Confirmamos
			conn.commit();

			// Se borran las ofertas asociadas a la central para crear las
			// nuevas
			int opcion;

			deleteOfertasCentral(oCentralAdminForm.getCentral().getCdMiga());

			String checked[] = oCentralAdminForm.getLista_seleccionados();

			if (checked != null) {
				for (int i = 0; i < checked.length; i++) {

					opcion = (new Integer(checked[i])).intValue();

					sql = "INSERT INTO TPFG_CENTRALES_OFERTAS (CDOFERTA,CDMIGA) "
							+ "VALUES (?,?)";

					gc = new GestionCommand(sql);
					hstValues = new Hashtable();
					hstTypes = new Hashtable();

					hstValues.put(new Integer(1), new Integer(opcion));
					hstTypes.put(new Integer(1), GestionCommand.ITYPE_INT);
					hstValues.put(new Integer(2), oCentralAdminForm
							.getCentral().getCdMiga());
					hstTypes.put(new Integer(2), GestionCommand.ITYPE_STRING);

					// Devuelve el numero de filas
					result = (Integer) gc.executeCommand(conn, hstValues,
							hstTypes, gc.RESULT_ERROR_CODE);

					if (result == null) {
						_logger
								.error("Error al crear la relacion oferta centralita");
						// return false;
					} else {
						conn.commit();
					}
				}// fin for
			}

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

	public static void deleteOfertasCentral(String codigoMiga) {

		_logger.debug("Eliminando la relacion oferta y central");

		Integer result = null;
		String sql = null;
		GestionCommand gc = null;
		Connection conn = null;
		Hashtable hstValues = null;
		Hashtable hstTypes = null;

		try {

			conn = AccesoBBDD.getConnection();
			conn.setAutoCommit(false);

			sql = "DELETE FROM TPFG_CENTRALES_OFERTAS WHERE CDMIGA=? ";

			gc = new GestionCommand(sql);

			hstValues = new Hashtable();
			hstTypes = new Hashtable();

			hstValues.put(new Integer(1), codigoMiga);
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

}
