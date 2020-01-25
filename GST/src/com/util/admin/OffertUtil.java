package com.util.admin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;

import com.forms.DataBean;
import com.forms.admin.OffertAdminForm;
import com.util.AccesoBBDD;
import com.util.GestionCommand;
import com.util.LoggerFactory;

/**
 * Clase de utilidad para la gestion de las ofertas
 * 
 * @author Manuel Guerrero del Pino
 * @version 1.0
 */

public class OffertUtil {

	private static Logger _logger = LoggerFactory.getLogger(OffertUtil.class);

	public static ArrayList loadOffertsTable() {
		_logger.debug("Recuperando las ofertas de BD");

		ArrayList _arlUsersList = new ArrayList();
		String sql = "SELECT CDOFERTA, NOMBRE FROM TPFG_OFERTAS ";
		// Consulta SQL
		try {
			GestionCommand gc = new GestionCommand(sql);
			Hashtable hstValues = new Hashtable();
			Hashtable hstTypes = new Hashtable();

			ResultSet rsResult = (ResultSet) gc.executeCommand(hstValues,
					hstTypes, GestionCommand.RESULT_RESULTSET);
			_arlUsersList.add(new DataBean("-1", "Crear Nueva Oferta"));
			while (rsResult.next()) {
				_arlUsersList.add(new DataBean(rsResult.getString("CDOFERTA"),
						rsResult.getString("TXNOMOFER")));
			}
		} catch (Exception ex) {
			_logger.error("Error recuperando los datos de las ofertas", ex);
		}
		return _arlUsersList;
	} // getList

	public static boolean createOffert(ActionForm form) {
		boolean guardado = false;

		OffertAdminForm myForm = (OffertAdminForm) form;

		_logger.debug("Creación de una oferta en BD");

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

			// Inserción de los datos administrativos
			sql = "INSERT INTO TPFG_OFERTAS (TXNOMOFER, CDTIPOFER, CDPERMAN, IMPRECIO, TXINFORM) "
					+ "VALUES (?,?,?,?,?)";

			gc = new GestionCommand(sql);

			// 1-Codigo
			hstValues.put(new Integer(1), myForm.getOferta().getNombre());
			hstTypes.put(new Integer(1), GestionCommand.ITYPE_STRING);

			hstValues.put(new Integer(2), myForm.getOferta().getTipo());
			hstTypes.put(new Integer(2), GestionCommand.ITYPE_STRING);

			hstValues.put(new Integer(3), myForm.getOferta().getPermanencia());
			hstTypes.put(new Integer(3), GestionCommand.ITYPE_STRING);

			hstValues.put(new Integer(4), myForm.getOferta().getPrecio());
			hstTypes.put(new Integer(4), GestionCommand.ITYPE_STRING);

			hstValues.put(new Integer(5), myForm.getOferta().getInformacion());
			hstTypes.put(new Integer(5), GestionCommand.ITYPE_STRING);

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
	} // createProfile

	public static ArrayList selectByPrimaryKey(String id) {
		_logger.debug("Recuperando los datos de la oferta de BD");

		ArrayList _arlOffertList = new ArrayList();
		String sql = "SELECT TXNOMOFER, CDTIPOFER, CDPERMAN, IMPRECIO, TXINFORM FROM TPFG_OFERTAS WHERE CDOFERTA="
				+ id;
		// Consulta SQL
		try {
			GestionCommand gc = new GestionCommand(sql);
			Hashtable hstValues = new Hashtable();
			Hashtable hstTypes = new Hashtable();

			ResultSet rsResult = (ResultSet) gc.executeCommand(hstValues,
					hstTypes, GestionCommand.RESULT_RESULTSET);

			if (rsResult.next()) {
				_arlOffertList.add(new DataBean("TXNOMOFER", rsResult
						.getString("TXNOMOFER")));
				_arlOffertList.add(new DataBean("CDTIPOFER", rsResult
						.getString("CDTIPOFER")));
				_arlOffertList.add(new DataBean("CDPERMAN", rsResult
						.getString("CDPERMAN")));
				_arlOffertList.add(new DataBean("IMPRECIO", rsResult
						.getString("IMPRECIO")));
				_arlOffertList.add(new DataBean("TXINFORM", rsResult
						.getString("TXINFORM")));
			}

		} catch (Exception ex) {
			_logger.error("Error recuperando los datos de la oferta BD", ex);
		}
		return _arlOffertList;
	} // getList

	public static boolean delete(OffertAdminForm myForm) {
		boolean borrado = false;

		_logger.debug("Eliminando oferta en BD");

		Integer result = null;
		String sql = null;
		GestionCommand gc = null;
		Connection conn = null;
		Hashtable hstValues = null;
		Hashtable hstTypes = null;

		try {

			conn = AccesoBBDD.getConnection();
			conn.setAutoCommit(false);

			sql = "DELETE FROM TPFG_OFERTAS WHERE CDOFERTA=?";

			gc = new GestionCommand(sql);

			hstValues = new Hashtable();
			hstTypes = new Hashtable();

			hstValues.put(new Integer(1), myForm.getOferta().getIdOferta());
			hstTypes.put(new Integer(1), GestionCommand.ITYPE_STRING);

			// Devuelve el numero de filas eliminadas
			result = (Integer) gc.executeCommand(conn, hstValues, hstTypes,
					gc.RESULT_ERROR_CODE);

			if (result == null) {
				_logger.error("Error al eliminar la oferta");
				return false;
			}

			// Confirmamos
			conn.commit();
			borrado = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			_logger.error("Error eliminando oferta. Haciendo rollback", ex);

			// Rollback de la transacción
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
	} // delete

	public static boolean modify(OffertAdminForm myForm) {
		boolean modificado = false;

		_logger.debug("Modificando oferta");

		Integer result = null;
		String sql = null;
		GestionCommand gc = null;
		Connection conn = null;
		Hashtable hstValues = null;
		Hashtable hstTypes = null;

		try {

			conn = AccesoBBDD.getConnection();
			conn.setAutoCommit(false);

			sql = "UPDATE OFERTAS SET TXNOMOFER=? ,"
					+ " CDTIPOFER = ? , CDPERMAN =? , IMPRECIO=? ,"
					+ " TXINFORM =? WHERE CDOFERTA ="
					+ myForm.getOferta().getIdOferta();

			hstValues = new Hashtable();
			hstTypes = new Hashtable();

			hstValues.put(new Integer(1), myForm.getOferta().getNombre());
			hstTypes.put(new Integer(1), GestionCommand.ITYPE_STRING);
			hstValues.put(new Integer(2), myForm.getOferta().getTipo());
			hstTypes.put(new Integer(2), GestionCommand.ITYPE_STRING);
			hstValues.put(new Integer(3), myForm.getOferta().getPermanencia());
			hstTypes.put(new Integer(3), GestionCommand.ITYPE_STRING);
			hstValues.put(new Integer(4), myForm.getOferta().getPrecio());
			hstTypes.put(new Integer(4), GestionCommand.ITYPE_STRING);
			hstValues.put(new Integer(5), myForm.getOferta().getInformacion());
			hstTypes.put(new Integer(5), GestionCommand.ITYPE_STRING);

			gc = new GestionCommand(sql);

			// Devuelve el numero de filas
			result = (Integer) gc.executeCommand(conn, hstValues, hstTypes,
					gc.RESULT_ERROR_CODE);

			if (result == null) {
				_logger.error("Error al modificar una oferta en BD");
				return false;
			}

			// Confirmamos
			conn.commit();
			modificado = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			_logger.error("Error modificando oferta en BD. Haciendo rollback",
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
	} // modify

}
