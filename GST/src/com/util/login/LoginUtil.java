package com.util.login;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.dto.UserDTO;
import com.forms.login.ChangeLoginForm;
import com.util.AccesoBBDD;
import com.util.Constantes;
import com.util.GestionCommand;
import com.util.LoggerFactory;

/**
 * Clase de utilidad para el logado de la aplicacción
 * 
 * @author Manuel Guerrero del Pino
 * @version 1.0
 */

public class LoginUtil {

	private static Logger _logger = LoggerFactory.getLogger(LoginUtil.class);

	private final static String keyBuffer = "56cf65a2";

	public static byte[] encode(byte[] b) throws Exception {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		OutputStream b64os = MimeUtility.encode(baos, "base64");

		b64os.write(b);

		b64os.close();

		return baos.toByteArray();

	}

	public static byte[] decode(byte[] b) throws Exception {

		ByteArrayInputStream bais = new ByteArrayInputStream(b);

		InputStream b64is = MimeUtility.decode(bais, "base64");

		byte[] tmp = new byte[b.length];

		int n = b64is.read(tmp);

		byte[] res = new byte[n];

		System.arraycopy(tmp, 0, res, 0, n);

		return res;

	}

	private static SecretKeySpec getKey() {

		SecretKeySpec key = new SecretKeySpec(keyBuffer.getBytes(), "DES");

		return key;

	}

	public static String desencripta(String s) throws Exception {

		String s1 = null;

		if (s.indexOf("{DES}") != -1) {

			String s2 = s.substring("{DES}".length());

			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

			cipher.init(2, getKey());

			byte abyte0[] = cipher.doFinal(decode(s2.getBytes()));

			s1 = new String(abyte0);

		} else {

			s1 = s;

		}

		return s1;

	}

	public static String encripta(String s) throws Exception {

		byte abyte0[];

		SecureRandom securerandom = new SecureRandom();

		securerandom.nextBytes(keyBuffer.getBytes());

		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

		cipher.init(1, getKey());

		abyte0 = encode(cipher.doFinal(s.getBytes())); // antes

		return "{DES}" + new String(abyte0);

	}

	public static boolean updatePasswordUser(ChangeLoginForm oChangeLoginForm) {
		boolean bRetorno = true;
		Connection conn = null;
		GestionCommand gc = null;

		try {

			String sql = "UPDATE TPFG_USUARIOS SET CDPASSWORD = ? "
					+ "WHERE CDLOGIN = ? " + "AND CDPASSWORD = ? ";

			Hashtable hstValues = new Hashtable();
			Hashtable hstTypes = new Hashtable();

			hstValues.put(new Integer(1), encripta(oChangeLoginForm
					.getNewPassword()));
			hstTypes.put(new Integer(1), GestionCommand.ITYPE_STRING);

			hstValues.put(new Integer(2), oChangeLoginForm.getUser());
			hstTypes.put(new Integer(2), GestionCommand.ITYPE_STRING);

			hstValues.put(new Integer(3), encripta(oChangeLoginForm
					.getPassword()));
			hstTypes.put(new Integer(3), GestionCommand.ITYPE_STRING);

			conn = AccesoBBDD.getConnection();
			conn.setAutoCommit(false); // Modo transaccional

			gc = new GestionCommand(sql);

			// Devuelve el numero de filas insertadas/eliminadas/modificadas
			Integer oIntResult = (Integer) gc.executeCommand(conn, hstValues,
					hstTypes, gc.RESULT_ERROR_CODE);

			if (oIntResult == null) {
				_logger.error("Error al actualizar el password del usuario");
				bRetorno = false;
			} else {
				// Confirmamos
				conn.commit();
			}
		} catch (SQLException ex) {
			bRetorno = false;
			ex.printStackTrace();
			_logger.error("Excepcion al actualizar el password del usuario");
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (Exception ex) {
			_logger.error("Excepcion al actualizar el password del usuario");
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

		return bRetorno;
	}

	// Validación del login
	public static ActionErrors validarUpdate(ChangeLoginForm oChangeloginForm,
			HttpServletRequest req) {

		ActionErrors errors = new ActionErrors();

		_logger
				.debug("Validando formulario de Cambio de contraseña changeLogin");

		if ((oChangeloginForm.getPasswordForm() != null)
				&& (!oChangeloginForm.getPasswordForm().equalsIgnoreCase(""))
				&& (oChangeloginForm.getNewPassword() != null)
				&& (!oChangeloginForm.getNewPassword().equalsIgnoreCase(""))
				&& (oChangeloginForm.getConfirmNewPassword() != null)
				&& (!oChangeloginForm.getConfirmNewPassword().equalsIgnoreCase(
						"")) && (oChangeloginForm.getUser() != null)
				&& (!oChangeloginForm.getUser().equalsIgnoreCase(""))) {

			if (oChangeloginForm.getNewPassword().equalsIgnoreCase(
					oChangeloginForm.getConfirmNewPassword())) {
				if (oChangeloginForm.getPassword().equalsIgnoreCase(
						oChangeloginForm.getPasswordForm())) {

					if (oChangeloginForm.getPassword().equalsIgnoreCase(
							oChangeloginForm.getNewPassword())) {
						ActionError error = new ActionError(
								"validation.errorPasswordRepetidoChangeLogin");
						errors.add("errorMessage", error);
					}

				} else {
					ActionError error = new ActionError(
							"validation.errorPasswordChangeLogin");
					errors.add("errorMessage", error);
				}
			} else {
				ActionError error = new ActionError(
						"validation.newPasswordChangeLogin");
				errors.add("errorMessage", error);
			}
		} else {
			ActionError error = new ActionError("validation.camposChangeLogin");
			errors.add("errorMessage", error);
		}

		return errors;

	}

	// **********************************************
	// Métodos de negocio para la gestión de usuarios
	// **********************************************
	public static UserDTO checkLogin(String login, String password)
			throws Exception {

		UserDTO oUser = null;

		String sql = "SELECT DISTINCT USU.CDUSUARIO, USU.CDLOGIN, USU.TXDESCNOM, USU.TXDESCEMA "
				+ "FROM TPFG_USUARIOS USU "
				+ "WHERE ((USU.CDLOGIN = ?)"
				+ "AND (USU.CDPASSWORD = ?))";

		GestionCommand gc = new GestionCommand(sql, false);
		Hashtable hstValues = new Hashtable();
		Hashtable hstTypes = new Hashtable();

		hstValues.put(new Integer(1), login);
		hstTypes.put(new Integer(1), GestionCommand.ITYPE_STRING);

		hstValues.put(new Integer(2), encripta(password));
		hstTypes.put(new Integer(2), GestionCommand.ITYPE_STRING);

		try {
			ResultSet rs = (ResultSet) gc.executeCommand(hstValues, hstTypes,
					GestionCommand.RESULT_RESULTSET);
			if (!rs.next()) {
				_logger.error("Error en control de acceso [login=" + login
						+ ",password=" + password + "]");
				throw new Exception("validation.usuarioErroneo");
			} else {

				_logger.info("Login realizado correctamente [login=" + login
						+ "]");

				oUser = new UserDTO();
				oUser.setidUser((String) rs.getString("CDUSUARIO"));

				oUser.setLogin((String) rs.getString("CDLOGIN"));
				oUser.setPassword(desencripta(password));
				oUser.setName((String) rs.getString("TXDESCNOM"));
				oUser.setEmail((String) rs.getString("TXDESCEMA"));

			}
		} catch (java.sql.SQLException ex) {
			ex.printStackTrace();
			_logger.error("Excepcion recuperando datos de usuario [login="
					+ login + ",cliente=" + login + "] : " + ex.getMessage());
			throw new Exception("validation.datosUsuario");
		}
		Constantes.USER_KEY = oUser.getIdUser();
		return oUser;
	}

	public boolean validarOpcionMenu(ArrayList opciones, String url,
			ArrayList opcionesUser) {
		boolean resultado = false;
		boolean perteneceAMenu = false;
		int maxLV1 = 0;
		int maxLV2 = 0;
		// Validamos que esté en las opciones de menú generales
		int max = opciones.size();
		int i = 0;
		for (i = 0; i < max; i++) {
			if (((String) opciones.get(i)).indexOf(url) > -1) {
				perteneceAMenu = true;

				i = max;
			}
		}

		if (perteneceAMenu) {
			maxLV1 = opciones.size();
			maxLV2 = opcionesUser.size();

			for (i = 0; i < maxLV2; i++) {
				String miurl = (String) opcionesUser.get(i);

				if (miurl.indexOf(url) > -1) {

					resultado = true;
					i = maxLV1;
					maxLV2 = 0;
				}
			}

		}

		else
			resultado = true;

		return resultado;
	}

}
