package com.util.admin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;

import com.forms.DataBean;
import com.forms.admin.UserAdminForm;
import com.util.AccesoBBDD;
import com.util.GestionCommand;
import com.util.LoggerFactory;
import com.util.login.LoginUtil;

/**
 * Clase de utilidad para la gestion de los usuarios
 * 
 * @author Manuel Guerrero del Pino
 * @version 1.0
 */

public class UserUtil {

	private static Logger _logger = LoggerFactory.getLogger(UserUtil.class);

	public static ArrayList loadUsersTable() {
		_logger.debug("Recuperando los datos del usuario de cliente de BD");

		ArrayList _arlUsersList = new ArrayList();
		String sql = "SELECT CDUSUARIO,TXDESCNOM FROM TPFG_USUARIOS ";
		// Consulta SQL
		try {
			GestionCommand gc = new GestionCommand(sql);
			Hashtable hstValues = new Hashtable();
			Hashtable hstTypes = new Hashtable();

			ResultSet rsResult = (ResultSet) gc.executeCommand(hstValues,
					hstTypes, GestionCommand.RESULT_RESULTSET);
			_arlUsersList.add(new DataBean("-1", "Crear Nuevo Usuario"));
			while (rsResult.next()) {
				_arlUsersList.add(new DataBean(
						rsResult.getString("CDUSUARIO"), rsResult
								.getString("TXDESCNOM")));
			}
		} catch (Exception ex) {
			_logger.error("Error recuperando los datos de los usuarios", ex);
		}
		return _arlUsersList;
	} // getList

	public static ArrayList selectByPrimaryKey(String idusuario) {
		_logger.debug("Recuperando los datos del usuario de cliente de BD");

		ArrayList _arlUsersList = new ArrayList();
		String sql = "SELECT CDUSUARIO, CDLOGIN, TXDESCNOM, TXDESCEMA, CDPASSWORD FROM TPFG_USUARIOS WHERE CDUSUARIO="
				+ idusuario;
		// Consulta SQL
		try {
			GestionCommand gc = new GestionCommand(sql);
			Hashtable hstValues = new Hashtable();
			Hashtable hstTypes = new Hashtable();

			ResultSet rsResult = (ResultSet) gc.executeCommand(hstValues,
					hstTypes, GestionCommand.RESULT_RESULTSET);

			if (rsResult.next()) {
				_arlUsersList.add(new DataBean("CDUSUARIO", rsResult
						.getString("CDUSUARIO")));
				_arlUsersList.add(new DataBean("CDLOGIN", rsResult
						.getString("CDLOGIN")));
				_arlUsersList.add(new DataBean("TXDESCNOM", rsResult
						.getString("TXDESCNOM")));
				_arlUsersList.add(new DataBean("TXDESCEMA", rsResult
						.getString("TXDESCEMA")));
				_arlUsersList.add(new DataBean("CDPASSWORD", LoginUtil
						.desencripta(rsResult.getString("CDPASSWORD"))));
			}

		} catch (Exception ex) {
			_logger.error(
					"Error recuperando los datos del usuario de cliente de BD",
					ex);
		}
		return _arlUsersList;
	} // getList

	public static boolean createUser(ActionForm form, String[] checked) {
		boolean guardado = false;

		UserAdminForm myForm = (UserAdminForm) form;

		_logger.debug("Creación de un usuario en BD");

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
			sql = "INSERT INTO TPFG_USUARIOS (CDLOGIN, CDPASSWORD, TXDESCNOM, TXDESCEMA) "
					+ "VALUES (?,?,?,?)";

			gc = new GestionCommand(sql);

			// 1-Codigo
			hstValues.put(new Integer(1), myForm.getLogin());
			hstTypes.put(new Integer(1), GestionCommand.ITYPE_STRING);

			hstValues.put(new Integer(2), LoginUtil.encripta(myForm
					.getPassword()));
			hstTypes.put(new Integer(2), GestionCommand.ITYPE_STRING);

			hstValues.put(new Integer(3), myForm.getNombre());
			hstTypes.put(new Integer(3), GestionCommand.ITYPE_STRING);

			hstValues.put(new Integer(4), myForm.getMail());
			hstTypes.put(new Integer(4), GestionCommand.ITYPE_STRING);

			gc = new GestionCommand(sql);

			// Devuelve el numero de filas insertadas
			result = (Integer) gc.executeCommand(conn, hstValues, hstTypes,
					gc.RESULT_ERROR_CODE);

			if (result == null) {
				// no se ha dado de alta y se para el proceso de acontratacion
				_logger.error("Error crear un usuario en BD");
				return guardado;
			}
			conn.commit();
			guardado = true;

			int opcion;
			String idusuario = null;

			for (int i = 0; i < checked.length; i++) {

				// Consulta SQL
				sql = "SELECT MAX(CDUSUARIO) from TPFG_USUARIOS ";
				gc = new GestionCommand(sql);
				hstValues = new Hashtable();
				hstTypes = new Hashtable();

				ResultSet rsResult = (ResultSet) gc.executeCommand(hstValues,
						hstTypes, GestionCommand.RESULT_RESULTSET);
				while (rsResult.next()) {
					idusuario = rsResult.getString(1);
				}

				if (idusuario == null) {
					idusuario = "1";
				} else {
					idusuario = String.valueOf(Integer.parseInt(idusuario));
				}

				opcion = (new Integer(checked[i])).intValue();

				// Inserción de la relacion de los usuarios con los perfiles
				sql = "INSERT INTO TPFG_USUROLES (CDROLUSU,CDUSUARIO) "
						+ "VALUES (?,?)";

				gc = new GestionCommand(sql);
				hstValues = new Hashtable();
				hstTypes = new Hashtable();

				hstValues.put(new Integer(1), new Integer(opcion));
				hstTypes.put(new Integer(1), GestionCommand.ITYPE_INT);

				hstValues.put(new Integer(2), new Integer(idusuario));
				hstTypes.put(new Integer(2), GestionCommand.ITYPE_INT);

				// Devuelve el numero de filas insertadas/eliminadas/modificadas
				result = (Integer) gc.executeCommand(conn, hstValues, hstTypes,
						gc.RESULT_ERROR_CODE);

				if (result == null) {
					// no se ha dado de alta y se para el proceso de
					// acontratacion
					_logger.error("Error al crear un rol en TPFG_USUROLES");
					// return false;
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

	public static boolean deleteUsuarios_Perfiles(String idusuario) {
		boolean borrado = false;

		_logger.debug("Eliminando usuario de rol en BD");

		Integer result = null;
		String sql = null;
		GestionCommand gc = null;
		Connection conn = null;
		Hashtable hstValues = null;
		Hashtable hstTypes = null;

		try {

			conn = AccesoBBDD.getConnection();
			conn.setAutoCommit(false);

			sql = "DELETE FROM TPFG_USUROLES WHERE CDUSUARIO=? ";

			gc = new GestionCommand(sql);

			hstValues = new Hashtable();
			hstTypes = new Hashtable();

			hstValues.put(new Integer(1), idusuario);
			hstTypes.put(new Integer(1), GestionCommand.ITYPE_STRING);

			// Devuelve el numero de filas eliminadas
			result = (Integer) gc.executeCommand(conn, hstValues, hstTypes,
					gc.RESULT_ERROR_CODE);

			if (result == null) {
				_logger
						.error("No se ha borrado ninguna fila de la BD porque no existia");
				return false;
			}

			// Confirmamos
			conn.commit();
			borrado = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			_logger
					.error(
							"Error eliminando los roles asignados al usuario en BD. Haciendo rollback",
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
		return true;

	}

	public static boolean delete(UserAdminForm myForm) {
		boolean borrado = false;

		_logger.debug("Eliminando usuario en BD");

		// borro la relacion del usuario con los perfiles
		deleteUsuarios_Perfiles(myForm.getId_usuario());

		Integer result = null;
		String sql = null;
		GestionCommand gc = null;
		Connection conn = null;
		Hashtable hstValues = null;
		Hashtable hstTypes = null;

		try {

			conn = AccesoBBDD.getConnection();
			conn.setAutoCommit(false);

			sql = "DELETE FROM TPFG_USUARIOS WHERE CDUSUARIO=?";

			gc = new GestionCommand(sql);

			hstValues = new Hashtable();
			hstTypes = new Hashtable();

			hstValues.put(new Integer(1), myForm.getId_usuario());
			hstTypes.put(new Integer(1), GestionCommand.ITYPE_STRING);

			// Devuelve el numero de filas eliminadas
			result = (Integer) gc.executeCommand(conn, hstValues, hstTypes,
					gc.RESULT_ERROR_CODE);

			if (result == null) {
				_logger.error("Error al eliminar usuario");
				return false;
			}

			// Confirmamos
			conn.commit();
			borrado = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			_logger.error("Error elimiando usuario. Haciendo rollback", ex);

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

	public static boolean modify(UserAdminForm myForm, String checked[]) {
		boolean modificado = false;

		_logger.debug("Modificando usuario");

		Integer result = null;
		String sql = null;
		GestionCommand gc = null;
		Connection conn = null;
		Hashtable hstValues = null;
		Hashtable hstTypes = null;

		try {

			conn = AccesoBBDD.getConnection();
			conn.setAutoCommit(false);

			sql = "UPDATE TPFG_USUARIOS SET TXDESCNOM=? ,"
					+ " TXDESCEMA = ? WHERE CDUSUARIO ="
					+ myForm.getId_usuario();

			hstValues = new Hashtable();
			hstTypes = new Hashtable();

			hstValues.put(new Integer(1), myForm.getNombre());
			hstTypes.put(new Integer(1), GestionCommand.ITYPE_STRING);
			hstValues.put(new Integer(2), myForm.getMail());
			hstTypes.put(new Integer(2), GestionCommand.ITYPE_STRING);

			gc = new GestionCommand(sql);

			// Devuelve el numero de filas
			result = (Integer) gc.executeCommand(conn, hstValues, hstTypes,
					gc.RESULT_ERROR_CODE);

			if (result == null) {
				_logger.error("Error al modificar usuario  en BD");
				return false;
			}

			// Confirmamos
			conn.commit();

			// Se borran las relaciones perfiles usuario para crear las nuevas
			int opcion;
			String idusuario = myForm.getId_usuario();
			idusuario = String.valueOf(Integer.parseInt(idusuario));
			deleteUsuarios_Perfiles(idusuario);

			if (checked != null) {
				for (int i = 0; i < checked.length; i++) {

					opcion = (new Integer(checked[i])).intValue();

					sql = "INSERT INTO TPFG_USUROLES (CDROLUSU,CDUSUARIO) "
							+ "VALUES (?,?)";

					gc = new GestionCommand(sql);
					hstValues = new Hashtable();
					hstTypes = new Hashtable();

					hstValues.put(new Integer(1), new Integer(opcion));
					hstTypes.put(new Integer(1), GestionCommand.ITYPE_INT);
					hstValues.put(new Integer(2), new Integer(idusuario));
					hstTypes.put(new Integer(2), GestionCommand.ITYPE_INT);

					// Devuelve el numero de filas
					result = (Integer) gc.executeCommand(conn, hstValues,
							hstTypes, gc.RESULT_ERROR_CODE);

					if (result == null) {
						_logger.error("Error al crear un TPFG_USUROLES");
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
	} // modify

	public static ArrayList getInitialSelectedPerfilUser(String idUser) {
		_logger
				.debug("Recuperando la lista de roles seleccionados para el usuario "
						+ idUser);

		ArrayList _arlProfilesList = new ArrayList();

		String sql = " SELECT  usu.CDUSUARIO ,usu.TXDESCNOM ,perf.TXDESCROL, perf.CDROLUSU "
				+ " FROM TPFG_USUARIOS  usu, TPFG_ROLES perf, TPFG_USUROLES  usu_perf "
				+ " where usu.CDUSUARIO = usu_perf.CDUSUARIO AND usu.CDUSUARIO= ? "
				+ " and usu_perf.CDROLUSU=perf.CDROLUSU ";

		GestionCommand gc = new GestionCommand(sql);
		Hashtable hstValues = new Hashtable();
		Hashtable hstTypes = new Hashtable();

		_logger.debug("InitialSelectedPerfilUser:" + sql);

		hstValues.put(new Integer(1), idUser);
		hstTypes.put(new Integer(1), GestionCommand.ITYPE_STRING);

		Connection conn = null;

		try {
			conn = AccesoBBDD.getConnection();
			ResultSet rsResult = (ResultSet) gc.executeCommand(conn, hstValues,
					hstTypes, GestionCommand.RESULT_RESULTSET);
			;

			while (rsResult.next()) {
				_arlProfilesList.add(new DataBean(rsResult
						.getString("CDROLUSU"), rsResult
						.getString("TXDESCROL")));
			}
		}

		catch (Exception ex) {
			_logger.error("Error recuperando la lista de perfiles de BD", ex);
		}

		return _arlProfilesList;

	} // getInitialSelectedProfiles

	public static ArrayList getInitialAvailablePerfiles(String idUser) {
		_logger.debug("Recuperando la lista de perfiles del usuario con id "
				+ idUser);

		ArrayList _arlProfilesList = new ArrayList();

		// Consulta SQL
		String sql = "   select p1.TXDESCROL ,p1.CDROLUSU from TPFG_ROLES p1 "
				+ " WHERE p1.TXDESCROL not in ( " + "select p.TXDESCROL "
				+ " from TPFG_ROLES p, TPFG_USUROLES up, TPFG_USUARIOS u "
				+ " WHERE p.CDROLUSU = up.ID_PERFIL "
				+ " AND u.CDUSUARIO = up.CDUSUARIO "
				+ " AND u.CDUSUARIO = ? )";

		_logger.debug("InitialAvailablePerfiles:" + sql);

		// GestionCommand
		GestionCommand gc = new GestionCommand(sql);
		Hashtable hstValues = new Hashtable();
		Hashtable hstTypes = new Hashtable();

		hstValues.put(new Integer(1), idUser);
		hstTypes.put(new Integer(1), GestionCommand.ITYPE_STRING);

		Connection conn = null;

		try {
			conn = AccesoBBDD.getConnection();
			ResultSet rsResult = (ResultSet) gc.executeCommand(conn, hstValues,
					hstTypes, GestionCommand.RESULT_RESULTSET);
			;

			Integer i = new Integer(idUser);

			while (rsResult.next()) {
				int i2 = rsResult.getInt("CDROLUSU");
				Integer ii = new Integer(i2);
				_arlProfilesList.add(new DataBean(ii.toString(), rsResult
						.getString("TXDESCROL")));

			}

		} catch (Exception ex) {
			_logger.error("Error recuperando la lista de perfiles de BD", ex);
		} finally {
			try {
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return _arlProfilesList;

	} // getInitialSelectedProfiles

	public static boolean checkLoginUser(ActionForm form) {

		UserAdminForm myForm = (UserAdminForm) form;

		boolean resultado = false;

		String sql = "SELECT CDUSUARIO " + "FROM TPFG_USUARIOS "
				+ "WHERE CDLOGIN = ?";

		GestionCommand gc = new GestionCommand(sql, false);
		Hashtable hstValues = new Hashtable();
		Hashtable hstTypes = new Hashtable();

		hstValues.put(new Integer(1), myForm.getLogin());
		hstTypes.put(new Integer(1), GestionCommand.ITYPE_STRING);

		try {
			ResultSet rs = (ResultSet) gc.executeCommand(hstValues, hstTypes,
					GestionCommand.RESULT_RESULTSET);

			if (!rs.next()) {
				resultado = true;
			}

		} catch (java.sql.SQLException ex) {
			ex.printStackTrace();
			_logger.error("Error ya existe un usuario con ese login" + ex);
		}

		return resultado;
	}

} // Users
