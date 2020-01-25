package com.util.admin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;

import com.forms.DataBean;
import com.forms.admin.ProfileAdminForm;
import com.util.AccesoBBDD;
import com.util.GestionCommand;
import com.util.LoggerFactory;

/**
 * Clase de utilidad para la gestion de los perfiles
 * 
 * @author Manuel Guerrero del Pino
 * @version 1.0
 */

public class ProfilesUtil {

	private static Logger _logger = LoggerFactory.getLogger(ProfilesUtil.class);

	public static ArrayList getInitialProfiles(String mensaje) {
		_logger
				.debug("Recuperando la lista de roles de la tabla TPFG_ROLES de BD");

		ArrayList _arlProfilesList = new ArrayList();

		// Consulta SQL
		String sql = "select CDROLUSU, TXDESCROL from TPFG_ROLES order by TXDESCROL";
		// GestionCommand
		Hashtable hstValues = new Hashtable();
		Hashtable hstTypes = new Hashtable();

		GestionCommand gc = null;
		Connection conn = null;

		try {
			conn = AccesoBBDD.getConnection();

			gc = new GestionCommand(sql);

			ResultSet rsResult = (ResultSet) gc.executeCommand(conn, hstValues,
					hstTypes, GestionCommand.RESULT_RESULTSET);
			;
			if (!mensaje.equals("")) {
				_arlProfilesList.add(new DataBean("-1", mensaje));
			}
			while (rsResult.next()) {
				_arlProfilesList.add(new DataBean(rsResult
						.getString("CDROLUSU"), rsResult
						.getString("TXDESCROL")));
			}

		} catch (Exception ex) {
			_logger.error("Error recuperando la lista de roles de BD", ex);
		} finally {
			try {
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return _arlProfilesList;
	}// getInitialProfiles

	public static DataBean getDescription(int idProfile) {
		_logger.debug("Recuperando el detalle de un rol");

		DataBean oData = null;

		// Consulta SQL
		String sql = "select TXDESCROL, CDROLUSU FROM TPFG_ROLES where CDROLUSU = ? order by TXDESCROL";

		GestionCommand gc = new GestionCommand(sql);
		Hashtable hstValues = new Hashtable();
		Hashtable hstTypes = new Hashtable();

		hstValues.put(new Integer(1), new Integer(idProfile));
		hstTypes.put(new Integer(1), GestionCommand.ITYPE_INT);

		Connection conn = null;

		try {
			conn = AccesoBBDD.getConnection();
			ResultSet rsResult = (ResultSet) gc.executeCommand(conn, hstValues,
					hstTypes, GestionCommand.RESULT_RESULTSET);
			;
			if (rsResult.next()) {
				oData = new DataBean(rsResult.getString("CDROLUSU"), rsResult
						.getString("TXDESCROL"));
			}

		} catch (Exception ex) {
			_logger.error("Error recuperando el detalle de un rol", ex);
		} finally {
			try {
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return oData;
	}// getDescription

	public static ArrayList getInitialOption() {
		_logger.debug("getInitialOption()");

		ArrayList _arlProfilesList = new ArrayList();

		String sql = "SELECT CDPERMISO, TXNOMPERM  FROM TPFG_PERMISOS WHERE CDNIVEL != '1' ORDER BY TXNOMPERM";

		GestionCommand gc = new GestionCommand(sql);
		Hashtable hstValues = new Hashtable();
		Hashtable hstTypes = new Hashtable();

		Connection conn = null;

		try {

			conn = AccesoBBDD.getConnection();
			ResultSet rsResult = (ResultSet) gc.executeCommand(conn, hstValues,
					hstTypes, GestionCommand.RESULT_RESULTSET);
			;
			while (rsResult.next()) {
				_arlProfilesList.add(new DataBean(rsResult
						.getString("CDPERMISO"), rsResult.getString("TXNOMPERM")));

			}

		} catch (Exception ex) {
			_logger.error("Error recuperando la lista de roles de BD", ex);
		} finally {
			try {
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return _arlProfilesList;
	} // getInitialOptio

	public static ArrayList getInitialAvailableOptions(int idPerfil) {
		_logger.debug("Recuperando la lista de roles de TPFG_PERMISOS de BD");

		ArrayList _arlProfilesList = new ArrayList();

		String sql = "select distinct o.CDPERMISO, o.TXNOMPERM "
				+ " from TPFG_PERMISOS o "
				+ " where o.CDPERMISO NOT IN "
				+ "      (select CDPERMISO from TPFG_ROLPERMISOS po, TPFG_ROLES p "
				+ "       where po.CDROLUSU=p.CDROLUSU "
				+ "       AND p.CDROLUSU =? )"
				+ "  AND o.CDNIVEL !='1' order by o.TXDESCROL";

		// GestionCommand
		GestionCommand gc = new GestionCommand(sql);
		Hashtable hstValues = new Hashtable();
		Hashtable hstTypes = new Hashtable();

		hstValues.put(new Integer(1), new Integer(idPerfil));
		hstTypes.put(new Integer(1), GestionCommand.ITYPE_INT);

		Connection conn = null;

		try {

			conn = AccesoBBDD.getConnection();
			ResultSet rsResult = (ResultSet) gc.executeCommand(conn, hstValues,
					hstTypes, GestionCommand.RESULT_RESULTSET);
			;

			while (rsResult.next()) {
				_arlProfilesList.add(new DataBean(rsResult
						.getString("CDPERMISO"), rsResult.getString("TXNOMPERM")));

			}

		} catch (Exception ex) {
			_logger.error("Error recuperando la lista de roles de BD", ex);
		} finally {
			try {
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return _arlProfilesList;
	} // getInitialAvailableProfiles

	public static ArrayList getInitialSelectedOptions(int idPerfil) {
		_logger.debug("Recuperando la lista de roles de TPFG_PERMISOS de BD");

		ArrayList _arlProfilesList = new ArrayList();

		// Consulta SQL
		String sql = "select distinct o.CDPERMISO, o.TXNOMPERM "
				+ " from TPFG_PERMISOS o "
				+ " where o.CDPERMISO IN "
				+ "      (select CDPERMISO from TPFG_ROLPERMISOS po, TPFG_ROLES p "
				+ "       where po.CDROLUSU=p.CDROLUSU "
				+ "       AND p.CDROLUSU =? )"
				+ " AND o.CDNIVEL !='1' order by o.TXNOMPERM";

		GestionCommand gc = new GestionCommand(sql);
		Hashtable hstValues = new Hashtable();
		Hashtable hstTypes = new Hashtable();

		hstValues.put(new Integer(1), new Integer(idPerfil));
		hstTypes.put(new Integer(1), GestionCommand.ITYPE_INT);

		Connection conn = null;

		try {
			conn = AccesoBBDD.getConnection();
			ResultSet rsResult = (ResultSet) gc.executeCommand(conn, hstValues,
					hstTypes, GestionCommand.RESULT_RESULTSET);
			;

			while (rsResult.next()) {
				_arlProfilesList.add(new DataBean(rsResult
						.getString("CDPERMISO"), rsResult.getString("TXNOMPERM")));

			}

		} catch (Exception ex) {
			_logger.error("Error recuperando la lista de roles de BD", ex);
		} finally {
			try {
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return _arlProfilesList;
	} // getInitialSelectedProfiles

	public static boolean deleteProfile(ActionForm form) {
		boolean borrado = false;
		ProfileAdminForm myForm = (ProfileAdminForm) form;

		_logger.debug("Eliminando rol en BD");

		Integer result = null;
		String sql = null;
		String sqlPrevia = null;
		GestionCommand gc = null;
		Connection conn = null;
		Hashtable hstValues = null;
		Hashtable hstTypes = null;

		try {

			deleteProfileOptions(form);

			conn = AccesoBBDD.getConnection();
			conn.setAutoCommit(false);

			// Tambien hay que borrar
			sqlPrevia = "DELETE FROM TPFG_USUROLES WHERE CDROLUSU = ? ";

			gc = new GestionCommand(sqlPrevia);

			hstValues = new Hashtable();
			hstTypes = new Hashtable();

			// 1-ID_Perfil (PK)
			hstValues.put(new Integer(1), new Integer(myForm.getIdProfile()));
			hstTypes.put(new Integer(1), GestionCommand.ITYPE_INT);

			// Devuelve el numero de filas insertadas/eliminadas/modificadas
			result = (Integer) gc.executeCommand(conn, hstValues, hstTypes,
					gc.RESULT_ERROR_CODE);

			if (result == null) {
				_logger.error("Ningun rol a eliminar ");
			}

			// Confirmamos
			conn.commit();

			// Ya es posible eliminar el perfil
			sql = "DELETE FROM TPFG_ROLES WHERE CDROLUSU = ? ";

			gc = new GestionCommand(sql);

			hstValues = new Hashtable();
			hstTypes = new Hashtable();

			// 1-ID_Perfil (PK)
			hstValues.put(new Integer(1), new Integer(myForm.getIdProfile()));
			hstTypes.put(new Integer(1), GestionCommand.ITYPE_INT);

			// Devuelve el numero de filas insertadas/eliminadas/modificadas
			result = (Integer) gc.executeCommand(conn, hstValues, hstTypes,
					gc.RESULT_ERROR_CODE);

			if (result == null) {
				// no se ha dado de alta y se para el proceso de acontratacion
				_logger.error("Error al eliminar roles en PERFILES");
				return false;
			}

			// Confirmamos
			conn.commit();
			borrado = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			_logger.error("Error elimiando roles en BD. Haciendo rollback",
					ex);

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
	} // deleteProfile

	static public boolean updateProfile(ActionForm form) {

		boolean modificado = false;
		ProfileAdminForm myForm = (ProfileAdminForm) form;

		_logger.debug("Actualizando rol en BD");

		Integer result = null;
		String sql = null;
		GestionCommand gc = null;
		Connection conn = null;
		Hashtable hstValues = null;
		Hashtable hstTypes = null;

		try {

			conn = AccesoBBDD.getConnection();
			conn.setAutoCommit(false);

			sql = "UPDATE TPFG_ROLES SET TXDESCROL = ? WHERE CDROLUSU = ? ";

			gc = new GestionCommand(sql);

			hstValues = new Hashtable();
			hstTypes = new Hashtable();

			// 1-Descripcion
			hstValues.put(new Integer(1), myForm.getName());
			hstTypes.put(new Integer(1), GestionCommand.ITYPE_STRING);
			// 2-ID_Perfil (PK)
			hstValues.put(new Integer(2), new Integer(myForm.getIdProfile()));
			hstTypes.put(new Integer(2), GestionCommand.ITYPE_INT);

			// Devuelve el numero de filas insertadas/eliminadas/modificadas
			result = (Integer) gc.executeCommand(conn, hstValues, hstTypes,
					gc.RESULT_ERROR_CODE);

			if (result == null) {
				// no se ha dado de alta y se para el proceso de acontratacion
				_logger.error("Error al realizar el update en TPFG_ROLES");
				return false;
			}

			// Confirmamos
			conn.commit();
			modificado = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			_logger.error("Error elimiando roles en BD. Haciendo rollback",
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

	}// updateProfile

	static public boolean createProfileOptions(ActionForm form, String[] checked) {

		boolean guardado = false;

		ProfileAdminForm myForm = (ProfileAdminForm) form;

		_logger.debug("Insertando la relacion permisos roles en BD");

		Integer result = null;
		String sql = null;
		GestionCommand gc = null;
		Connection conn = null;
		Hashtable hstValues = null;
		Hashtable hstTypes = null;

		try {

			conn = AccesoBBDD.getConnection();
			conn.setAutoCommit(false);
			int opcion;

			for (int i = 0; i < checked.length; i++) {

				opcion = (new Integer(checked[i])).intValue();
				sql = "INSERT INTO TPFG_ROLPERMISOS (CDPERMISO,CDROLUSU) "
						+ "VALUES (?,?)";

				gc = new GestionCommand(sql);

				hstValues = new Hashtable();
				hstTypes = new Hashtable();

				hstValues.put(new Integer(1), new Integer(opcion));
				hstTypes.put(new Integer(1), GestionCommand.ITYPE_INT);

				hstValues.put(new Integer(2),
						new Integer(myForm.getIdProfile()));
				hstTypes.put(new Integer(2), GestionCommand.ITYPE_INT);

				// Devuelve el numero de filas insertadas/eliminadas/modificadas
				result = (Integer) gc.executeCommand(conn, hstValues, hstTypes,
						gc.RESULT_ERROR_CODE);

				if (result == null) {
					// no se ha dado de alta y se para el proceso de
					// acontratacion
					_logger.error("Error al crear un TPFG_ROLPERMISOS");
					return false;
				}

			}

			// Confirmamos
			conn.commit();
			guardado = true;

		} catch (Exception ex) {
			ex.printStackTrace();
			_logger.error(
					"Error insertando un rol en BD. Haciendo rollback", ex);

			// Rollback de la transacción
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			guardado = false;
		} finally {
			try {
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
		return guardado;
	}

	static public boolean deleteProfileOptions(ActionForm form) {

		boolean borrado = false;
		ProfileAdminForm myForm = (ProfileAdminForm) form;

		_logger
				.debug("Eliminando permisos del rol en BD para modificar el rol");

		Integer result = null;
		String sql = null;
		GestionCommand gc = null;
		Connection conn = null;
		Hashtable hstValues = null;
		Hashtable hstTypes = null;

		try {

			conn = AccesoBBDD.getConnection();
			conn.setAutoCommit(false);

			sql = "DELETE FROM TPFG_ROLPERMISOS WHERE CDROLUSU = ? ";

			gc = new GestionCommand(sql);

			hstValues = new Hashtable();
			hstTypes = new Hashtable();

			// 1-ID_Perfil (PK)
			hstValues.put(new Integer(1), new Integer(myForm.getIdProfile()));
			hstTypes.put(new Integer(1), GestionCommand.ITYPE_INT);

			// Devuelve el numero de filas insertadas/eliminadas/modificadas
			result = (Integer) gc.executeCommand(conn, hstValues, hstTypes,
					gc.RESULT_ERROR_CODE);

			if (result == null) {
				// no se ha dado de alta y se para el proceso
				_logger
						.error("Error al eliminar opciones de perfiles en PERFILES_OPCIONES");
				return false;
			}

			// Confirmamos
			conn.commit();
			borrado = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			_logger.error("Error eliminando roles en BD. Haciendo rollback",
					ex);

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

	}// deleteProfileOptions

	public static boolean createProfile(ActionForm form, String[] checked) {
		boolean guardado = false;

		ProfileAdminForm myForm = (ProfileAdminForm) form;

		_logger.debug("Creacion de roles en BD");

		Integer result = null;
		String sql = null;
		GestionCommand gc = null;
		Connection conn = null;
		Hashtable hstValues = null;
		Hashtable hstTypes = null;

		try {

			conn = AccesoBBDD.getConnection();
			conn.setAutoCommit(false);

			hstValues = new Hashtable();
			hstTypes = new Hashtable();

			// TODO AQUIES DONDE SE DEBARIA CONTROLAR QUE NO SE TIENE PERFILES
			// CON EL MISMO NOMBRE, SI SE QUIERE

			// Inserción de los datos administrativos
			sql = "INSERT INTO TPFG_ROLES (TXDESCROL) " + "VALUES (?)";
			gc = new GestionCommand(sql);

			hstValues.put(new Integer(1), myForm.getName());
			hstTypes.put(new Integer(1), GestionCommand.ITYPE_STRING);

			// Devuelve el numero de filas insertadas/eliminadas/modificadas
			result = (Integer) gc.executeCommand(conn, hstValues, hstTypes,
					gc.RESULT_ERROR_CODE);

			if (result == null) {
				// no se ha dado de alta y se para el proceso de acontratacion
				_logger.error("Error al crear un rol");
				return false;
			}

			conn.commit();

			// OBTENGO EL ID DEL NUEVO PERFIL
			sql = "SELECT MAX(CDROLUSU) FROM TPFG_ROLES WHERE TXDESCROL = ?";
			gc = new GestionCommand(sql);

			hstValues.put(new Integer(1), myForm.getName());
			hstTypes.put(new Integer(1), GestionCommand.ITYPE_STRING);

			ResultSet rsResult = (ResultSet) gc.executeCommand(hstValues,
					hstTypes, GestionCommand.RESULT_RESULTSET);
			if (rsResult.next()) {
				myForm.setIdProfile(rsResult.getString("MAX(CDROLUSU)"));
			} else {
				return false;
			}

			int opcion;
			for (int i = 0; i < checked.length; i++) {

				opcion = (new Integer(checked[i])).intValue();
				sql = "INSERT INTO TPFG_ROLPERMISOS (CDPERMISO,CDROLUSU) "
						+ "VALUES (?,?)";
				gc = new GestionCommand(sql);
				hstValues = new Hashtable();
				hstTypes = new Hashtable();

				hstValues.put(new Integer(1), new Integer(opcion));
				hstTypes.put(new Integer(1), GestionCommand.ITYPE_INT);

				hstValues.put(new Integer(2),
						new Integer(myForm.getIdProfile()));
				hstTypes.put(new Integer(2), GestionCommand.ITYPE_INT);

				// Devuelve el numero de filas insertadas/eliminadas/modificadas
				result = (Integer) gc.executeCommand(conn, hstValues, hstTypes,
						gc.RESULT_ERROR_CODE);

				if (result == null) {
					_logger.error("Error al crear un permiso de rol");
					return false;
				}

			}

			conn.commit();
			guardado = true;

		} catch (Exception ex) {
			ex.printStackTrace();
			_logger.error(
					"Error insertando un rol en BD. Haciendo rollback", ex);

			// Rollback de la transacción
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			guardado = false;
		} finally {
			try {
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return guardado;
	} // createProfile

}// Profiles
