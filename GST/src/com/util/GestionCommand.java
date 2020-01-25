package com.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import sun.jdbc.rowset.CachedRowSet;

/**
 * Clase de utilidad para realizar querys contra la BBDD
 * 
 * @author Manuel Guerrero del Pino
 * @version 1.0
 */

public class GestionCommand {
	// Tipos de datos.
	public final static int TYPE_INT = 1;
	public final static int TYPE_LONG = 2;
	public final static int TYPE_DATE = 3;
	public final static int TYPE_DOUBLE = 4;
	public final static int TYPE_NULL = 5;
	public final static int TYPE_STRING = 6;
	public final static int TYPE_TIMESTAMP = 7;

	public final static Integer ITYPE_INT = new Integer(1);
	public final static Integer ITYPE_LONG = new Integer(2);
	public final static Integer ITYPE_DATE = new Integer(3);
	public final static Integer ITYPE_DOUBLE = new Integer(4);
	public final static Integer ITYPE_NULL = new Integer(5);
	public final static Integer ITYPE_STRING = new Integer(6);
	public final static Integer ITYPE_TIMESTAMP = new Integer(7);

	// Formatos de resultados.
	public final static int RESULT_RESULTSET = 1;
	public final static int RESULT_ARRAYLIST = 2;
	public final static int RESULT_ERROR_CODE = 3;

	// Formato de la fecha devuelta.
	private final static String FORMAT_DATE = "dd/MM/yyyy";

	private String SQLCommand = null;

	private boolean log = true;

	// Datos para la generaci�n de trazas.
	private static Logger logger = com.util.LoggerFactory
			.getLogger(GestionCommand.class);

	private String _dsName = null;

	public GestionCommand(String sql, String dsName) {
		logger.debug("Sentencia a ejecutar " + sql + ", datasource=" + dsName);
		SQLCommand = sql;
		_dsName = dsName;

	}

	// Constructor de la clase.
	public GestionCommand(String commandSQL) {
		try {
			// logger.debug("Constructor de la clase GestionCommand");
			SQLCommand = commandSQL;
			logger.debug("Sentencia SQL a ejecutar " + commandSQL);
		} catch (Exception e) {
			logger.fatal("COntructor GestionCommand Exception "
					+ e.getMessage());
		}
	}

	// Constructor de la clase.
	public GestionCommand(String commandSQL, boolean log) {
		try {
			// logger.debug("Constructor de la clase GestionCommand");
			SQLCommand = commandSQL;
			this.log = log;
			if (log)
				logger.debug("Sentencia SQL a ejecutar " + commandSQL);

		} catch (Exception e) {
			logger.fatal("COntructor GestionCommand Exception "
					+ e.getMessage());
		}
	}

	/**
	 * Realiza el comando SQL especificado en el constructor, con los datos
	 * indicados.
	 * 
	 * @param objConnection.
	 *            Objeto de conexi�n a la base de datos.
	 * @param hstValor.
	 *            Lista de los valores del comando.
	 * @param hstTipo.
	 *            Lista de los tipos de los valores del comando.
	 * @param iTipoResultado.
	 *            Tipo de resultado del comando a ejecutar. Tambi�n influye en
	 *            c�mo se ejecuta (executeUpdate o executeQuery)
	 * @return: objeto tipo Object, segun sea el tipo de resultado: un objeto
	 *          Resultset, una objeto ArrayList de objetos HashMap o un dato
	 *          tipo int con el resultado de la operaci�n.
	 * @exception: Exception. Si ha ocurrido algun error lanza una excepci�n.
	 * @author Manuel Guerrero del Pino
	 * @version 1.0
	 * 
	 * 
	 */
	public Object executeCommand(Connection connection, Hashtable hstValue,
			Hashtable hstType, int typeResult) throws SQLException {

		PreparedStatement objPreparedStatement = null;
		if (log)
			logger.debug("metodo executeCommand ( " + hstValue + " , "
					+ hstType + " , " + typeResult + " )");
		try {

			objPreparedStatement = connection.prepareStatement(SQLCommand);
			prepareData(objPreparedStatement, hstValue, hstType);
			return buildObjectResult(typeResult, objPreparedStatement);
		} catch (SQLException exc) {
			logger.error("SQLException en executeCommnad " + exc.getMessage());
			throw new SQLException("Error en la ejecuci�n de un comando. "
					+ exc.getMessage());
		} catch (Exception e) {
			logger.fatal("Exception en executeCommnad " + e.getMessage(), e);

		} finally {
			if (objPreparedStatement != null) {
				objPreparedStatement.close();
				objPreparedStatement = null;
			}
		}
		return null;
	}

	public Object executeCommand(Hashtable hstValue, Hashtable hstType,
			int typeResult) throws SQLException {
		Connection connection = null;
		if (log)
			logger.debug("metodo executeCommand ( " + hstValue + " , "
					+ hstType + " , " + typeResult + " )");
		try {
			if (log)
				logger.debug("Obtenemos la conexion");

			if (_dsName == null) {
				connection = AccesoBBDD.getConnection();
			} else {
				connection = AccesoBBDD.getConnection();
			}

			if (connection != null) {
				return executeCommand(connection, hstValue, hstType, typeResult);
			} else {
				logger.debug("No se ha podido establecer la conexion");
				throw new SQLException(
						"No se  ha podido establecer la conexion");
			}
		} catch (SQLException excSQL) {
			logger.error("SQLException" + excSQL.getMessage());
			throw new SQLException("Error en la ejecuci�n de un comando. "
					+ excSQL.getMessage());
		} catch (Exception excGeneral) {
			logger.fatal("Exception " + excGeneral.getMessage());

		} finally {
			if (!connection.isClosed()) {
				if (log)
					logger.debug("Cerramos la conexion");
				connection.close();
				connection = null;
			}
		}
		return null;
	}

	public Object executeCommand(Hashtable hstValue, Hashtable hstType,
			int typeResult, String dbLink) throws SQLException {
		Connection connection = null;
		if (log)
			logger.debug("metodo executeCommand ( " + hstValue + " , "
					+ hstType + " , " + typeResult + " )");
		try {
			if (log)
				logger.debug("Obtenemos la conexion");

			if (_dsName == null) {
				connection = AccesoBBDD.getConnection();
			} else {
				connection = AccesoBBDD.getConnection();
			}

			if (connection != null) {
				Object result = executeCommand(connection, hstValue, hstType,
						typeResult);
				closeDBLink(connection, dbLink);
				return result;

			} else {
				logger.debug("No se ha podido establecer la conexion");
				throw new SQLException(
						"No se  ha podido establecer la conexion");
			}
		} catch (SQLException excSQL) {
			logger.error("SQLException" + excSQL.getMessage());
			throw new SQLException("Error en la ejecuci�n de un comando. "
					+ excSQL.getMessage());
		} catch (Exception excGeneral) {
			logger.fatal("Exception " + excGeneral.getMessage());

		} finally {
			if (!connection.isClosed()) {
				if (log)
					logger.debug("Cerramos la conexion");
				closeDBLink(connection, dbLink);
				connection.close();
				connection = null;
			}
		}
		return null;
	}

	private void closeDBLink(Connection connection, String dbLink) {

		if (dbLink.startsWith("@")) {
			dbLink = dbLink.substring(1);
		}

		try {
			logger.debug("Cerrando conexi�n con DBLINK " + dbLink);
			connection.commit();
		} catch (Exception ex) {
			logger.error("Error cerrando DBLINK " + dbLink + ": "
					+ ex.getMessage());
		}

	}

	public String toString(Hashtable hstValor, Hashtable hstTipo) {
		int iValores = -1;
		int iTipos = -1;
		int iTipo = -1;
		Integer intIndice = null;
		Enumeration enmDatos = null;
		StringBuffer stbResultado = new StringBuffer();

		try {

			// Datos generales.
			stbResultado.append("ComandoSQL = ");
			stbResultado.append(SQLCommand);
			stbResultado.append(". ");

			// Comprueba si los datos estan vac�os.
			if (hstValor == null) {
				// A�ade los datos.
				stbResultado.append("Lista de valores vac�a");
				return stbResultado.toString();
			}

			if (hstTipo == null) {
				// A�ade los datos.
				stbResultado.append("Lista de tipos vac�a");
				return stbResultado.toString();
			}

			// Comprueba que las listas son iguales.
			iValores = hstValor.size();
			iTipos = hstTipo.size();

			if (iValores != iTipos) {

				// A�ade los datos.
				stbResultado
						.append("N�mero de valores distinto del n�mero de tipos");
				return stbResultado.toString();
			}

			// Recorre la lista de valores.
			enmDatos = hstValor.keys();
			while (enmDatos.hasMoreElements()) {
				// Obtiene el valor y el tipo correspondiente.
				intIndice = (Integer) enmDatos.nextElement();
				iTipo = ((Integer) hstTipo.get(intIndice)).intValue();

				switch (iTipo) {
				case TYPE_INT:
					stbResultado.append("["
							+ ((Integer) hstValor.get(intIndice)).intValue()
							+ " : INT]");
					break;
				case TYPE_LONG:
					stbResultado.append("["
							+ ((Long) hstValor.get(intIndice)).longValue()
							+ " : LONG]");
					break;
				case TYPE_DATE:
					stbResultado.append("["
							+ (java.sql.Date) hstValor.get(intIndice)
							+ " : DATE]");
					break;
				case TYPE_DOUBLE:
					stbResultado.append("["
							+ ((Double) hstValor.get(intIndice)).doubleValue()
							+ " : DOUBLE]");
					break;
				case TYPE_STRING:
					stbResultado
							.append("[" + ((String) hstValor.get(intIndice))
									+ " : STRING]");
					break;
				case TYPE_NULL:
					stbResultado.append("[NULL]");
					break;
				default:
					stbResultado.append("[DESCONOCIDO]");
					break;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		// Devuelve la cadena final.
		return stbResultado.toString();

	}

	public String toString() {
		String strResultado = "";

		// Obtiene una cadena con los datos de la consulta.
		strResultado = "[ComandoSQL = " + SQLCommand + "]";
		return strResultado;
	}

	private void prepareData(PreparedStatement objPreparedStatement,
			Hashtable hstValor, Hashtable hstTipo) throws Exception {
		int iTipo = -1;
		int iValores = -1;
		int iTipos = -1;
		int iIndice = -1;
		Integer intIndice = null;
		Enumeration enmDatos = null;

		if (hstValor == null) {
			logger.fatal("Lista de Valores Vacia");
			throw new Exception("Lista de valores vac�a");
		}

		if (hstTipo == null) {
			logger.fatal("Lista de Tipos Vacia");
			throw new Exception("Lista de tipos vac�a");
		}

		iValores = hstValor.size();
		iTipos = hstTipo.size();

		if (iValores != iTipos) {
			logger.fatal("Numero de Valores distinto del numero de tipos");
			throw new Exception(
					"N�mero de valores distinto del n�mero de tipos");
		}

		enmDatos = hstValor.keys();
		while (enmDatos.hasMoreElements()) {
			// Obtiene el dato.
			intIndice = (Integer) enmDatos.nextElement();
			iTipo = ((Integer) hstTipo.get(intIndice)).intValue();
			iIndice = intIndice.intValue();

			// Detecta el tipo de datos.
			switch (iTipo) {
			case TYPE_INT:
				objPreparedStatement.setInt(iIndice, ((Integer) hstValor
						.get(intIndice)).intValue());
				break;
			case TYPE_LONG:
				objPreparedStatement.setLong(iIndice, ((Long) hstValor
						.get(intIndice)).longValue());
				break;
			case TYPE_DATE:
				objPreparedStatement.setDate(iIndice, ((java.sql.Date) hstValor
						.get(intIndice)));
				break;
			case TYPE_DOUBLE:
				objPreparedStatement.setDouble(iIndice, ((Double) hstValor
						.get(intIndice)).doubleValue());
				break;
			case TYPE_NULL:
				objPreparedStatement.setNull(iIndice, ((Integer) hstValor
						.get(intIndice)).intValue());// java.sql.Types.Null
				break;
			case TYPE_STRING:
				objPreparedStatement.setString(iIndice, ((String) hstValor
						.get(intIndice)));
				break;

			case TYPE_TIMESTAMP:
				objPreparedStatement.setTimestamp(iIndice,
						((java.sql.Timestamp) hstValor.get(intIndice)));
				break;

			default:
				logger.fatal("Tipo de dato no definido");
				throw new Exception("Tipo de dato no definido");
			}
		}

	}

	private Object buildObjectResult(int iTipoResultado,
			PreparedStatement objPreparedStatement) throws Exception {
		ResultSet objResultSet = null;
		try {

			switch (iTipoResultado) {
			case RESULT_RESULTSET:
				if (log)
					logger.debug("tipo resultado RESULTSET");
				objResultSet = objPreparedStatement.executeQuery();
				CachedRowSet objCachedRowSet = new CachedRowSet();
				objCachedRowSet.populate(objResultSet);
				return (Object) objCachedRowSet;

			case RESULT_ARRAYLIST:
				if (log)
					logger.debug("tipo resultado ARRAYLIST");
				int iContador = 0;
				int iTotal = 0;
				String strCampoTipo = null;
				String strCampoValor = null;
				String strCampoNombre = null;
				HashMap hsmRegistro = null;
				ArrayList arlResultados = new ArrayList();
				ResultSetMetaData objResultSetMetaData = null;

				// Obtiene la consulta.
				objResultSet = objPreparedStatement.executeQuery();

				// Recorre la lista de resultados obteniendo los campos leidos.
				while (objResultSet.next()) {
					// Reserva espacio para los datos del registro.
					hsmRegistro = new HashMap();

					// Obtiene la informaci�n completa de la consulta.
					objResultSetMetaData = objResultSet.getMetaData();

					// Obtiene los datos del registro.
					iTotal = objResultSetMetaData.getColumnCount();

					for (iContador = 1; iContador <= iTotal; iContador++) {

						// Obtiene los datos de la columna y la a�ade al
						// registro.
						strCampoNombre = objResultSetMetaData
								.getColumnName(iContador);
						strCampoTipo = objResultSetMetaData
								.getColumnTypeName(iContador);

						// Comprueba el tipo de campo.
						if (strCampoTipo.indexOf("DATE") >= 0) {
							Date datFecha = null;
							SimpleDateFormat sdfFecha = null;

							// Obtiene la fecha.
							datFecha = objResultSet.getDate(strCampoNombre);
							if (datFecha != null) {
								// Inicia el formato.
								sdfFecha = new SimpleDateFormat(FORMAT_DATE);

								// Aplica el formato.
								strCampoValor = sdfFecha.format(datFecha);
							} else {
								strCampoValor = "";
							}
						} else {
							strCampoValor = objResultSet
									.getString(strCampoNombre);
							if (strCampoValor == null) {
								strCampoValor = "";
							}

						}

						// A�ade la columna a los datos del registro.
						hsmRegistro.put(strCampoNombre, strCampoValor);
					}
					// A�ade el registro en la lista de resultados.
					arlResultados.add(hsmRegistro);
				}

				// Devuelve el resultado.
				return (Object) arlResultados;

			case RESULT_ERROR_CODE:
				if (log)
					logger.debug("tipo resultado int");
				int iResult = objPreparedStatement.executeUpdate();
				objPreparedStatement.close();
				objPreparedStatement = null;
				logger.debug("RESULT_ERROR_CODE = " + iResult);
				return ((Object) new Integer(iResult));

			default:
				logger.fatal("Tipo de Resultado solicitado no existe");
				throw new Exception("Tipo de resultado solicitado no existe");
			}
		} catch (Exception e) {
			logger.fatal("Exception buildObjectResult " + e.getMessage());
		} finally {
			try {
				if (objResultSet != null) {
					objResultSet.close();
					objResultSet = null;
				}

				if (objPreparedStatement != null) {
					objPreparedStatement.close();
					objPreparedStatement = null;
				}
			} catch (java.sql.SQLException exc2) {
				exc2.printStackTrace();
				throw new Exception(exc2.getMessage());
			}
		}

		return null;
	}

}
