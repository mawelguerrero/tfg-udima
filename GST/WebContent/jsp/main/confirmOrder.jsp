<!-- Manuel Guerrero del Pino - Confirmación de Pedido -->
<%@ page import="java.util.*,com.actions.*"%>
<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>


<html:html>

<html>
<HEAD>
<TITLE></TITLE>
<META http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<META http-equiv="Pragma" content="no-cache">
<META http-equiv="Expires" content="0">
<META http-equiv="Cache-Control" content="no-cache">
<SCRIPT language="JavaScript" src="/GST/js/validate.js"></SCRIPT>
<link href="/GST/css/style.css" rel="stylesheet" type="text/css">
<style type="text/css">
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
}
</style>


</HEAD>
<BODY marginwidth="0" marginheight="0" leftmargin="0"
	bgproperties="fixed" onload="deselecciona()">
<html:form action="/offertMain.do">

	<nested:hidden property="pageEvent" />

	<table width="700" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td><img src="/GST/images/px.gif" width="1" height="300"></td>
			<td valign="top"><!--------------tabla central----------------->
			<table width="700" border="0" cellspacing="0" cellpadding="0">

				<tr>

					<td colspan="2" class="titular">Información de la petición
					creada</td>
					<td rowspan="6" align="right" valign="top"><img
						src="/GST/images/px.gif" width="115" height="74"></td>
				</tr>
				<html:errors />


				<tr>
					<td colspan="3"><img src="/GST/images/px.gif" width="1"
						height="10"></td>
				</tr>

				<tr>
					<td class="subseccion" colspan="5">Datos de la oferta</td>
				</tr>
				<tr>
					<td colspan="5"><img src="/GST/images/px.gif" width="1"
						height="10"></td>
				</tr>

				<tr>
					<td class="textocampo">Nombre</td>
				</tr>
				<tr>
					<td class="vacio"><nested:text property="oferta.nombre"
						styleClass="cajaDis" maxlength="30" size="30" disabled="true" /></td>
				</tr>

				<tr>
					<td class="textocampo">Permanencia en meses</td>
					<td class="textocampo">Precio en euros</td>
				</tr>
				<tr>
					<td class="vacio"><nested:text property="oferta.permanencia"
						styleClass="cajaDis" maxlength="10" size="10" disabled="true" /></td>
					<td class="vacio"><nested:text property="oferta.precio"
						styleClass="cajaDis" maxlength="10" size="10" disabled="true" /></td>
				</tr>

				<tr>
					<td colspan="5"><img src="/GST/images/px.gif" width="1"
						height="10"></td>
				</tr>

				<tr>
					<td class="textocampo" colspan="5">Información</td>
				</tr>
				<tr>
					<td class="vacio" colspan="5"><nested:text
						property="oferta.informacion" styleClass="cajaDis" maxlength="150"
						size="100" disabled="true" /></td>
				</tr>

				<tr>
					<td colspan="5"><img src="/GST/images/px.gif" width="1"
						height="10"></td>
				</tr>

				<tr>
					<td colspan="3"><img src="/GST/images/px.gif" width="1"
						height="10"></td>
				</tr>
				<tr>
					<td class="subseccion" colspan="3">Introduzca los datos de
					pago</td>
				</tr>
				<tr>
					<td colspan="3"><img src="/GST/images/px.gif" width="1"
						height="10"></td>
				</tr>

				<tr>
					<td class="textocampo">Forma de pago</td>
					<td class="textocampo">Cuenta corriente</td>
				</tr>
				<tr>
					<td class="vacio"><nested:text property="cliente.formaPago"
						styleClass="cajaDis" maxlength="25" size="25" disabled="true" /></td>
					<td class="vacio"><nested:text property="cliente.numeroCuenta"
						styleClass="cajaDis" maxlength="30" size="30" disabled="true" /></td>
				</tr>

				<tr>
					<td class="textocampo">Número tarjeta</td>
					<td class="textocampo">Fecha de caducidad</td>
				</tr>
				<tr>
					<td class="vacio"><nested:text
						property="cliente.numeroTarjeta" styleClass="cajaDis"
						maxlength="20" size="20" disabled="true" /></td>
					<td class="vacio"><nested:text
						property="cliente.fechaCaducidad" styleClass="cajaDis"
						maxlength="50" size="15" disabled="true" /></td>
				</tr>
				<tr>
					<td colspan="3"><img src="/GST/images/px.gif" width="1"
						height="10"></td>
				</tr>

				<tr>
					<td class="subseccion" colspan="3">Información adiccional</td>
				</tr>
				<tr>
					<td colspan="3"><img src="/GST/images/px.gif" width="1"
						height="10"></td>
				</tr>

				<tr>
					<td class="textocampo">Más información</td>

				</tr>
				<tr>
					<td class="vacio" colspan="3"><nested:text
						property="cliente.informacion" styleClass="cajaDis"
						maxlength="150" size="100" disabled="true" /></td>
				</tr>
				<tr>
					<td colspan="3"><img src="/GST/images/px.gif" width="1"
						height="10"></td>
				</tr>

				<tr>
					<td class="subseccion" colspan="3">Introduzca los datos del
					cliente</td>
				</tr>
				<tr>
					<td colspan="3"><img src="/GST/images/px.gif" width="1"
						height="10"></td>
				</tr>

				<tr>
					<td class="textocampo">Teléfono</td>
					<td class="textocampo">Documento</td>
				</tr>
				<tr>
					<td class="vacio"><nested:text property="cliente.telefono"
						styleClass="cajaDis" maxlength="9" size="9" disabled="true" /></td>
					<td class="vacio"><nested:text property="cliente.documentoID"
						styleClass="cajaDis" maxlength="50" size="30" disabled="true" /></td>
				</tr>

				<tr>
					<td class="textocampo">Nombre</td>
					<td colspan="3" class="textocampo">Apellidos</td>
				</tr>
				<tr>
					<td class="vacio"><nested:text property="cliente.nombre"
						styleClass="cajaDis" maxlength="40" size="40" disabled="true" /></td>
					<td colspan="3" class="vacio"><nested:text
						property="cliente.apellidos" styleClass="cajaDis" maxlength="60"
						size="60" disabled="true" /></td>
				</tr>

				<tr>
				<tr>
					<td class="textocampo">Movil</td>
					<td class="textocampo">Fax</td>
					<td class="textocampo">Email</td>
				</tr>
				<tr>
					<td class="vacio"><nested:text property="cliente.movil"
						styleClass="cajaDis" maxlength="10" size="10" disabled="true" /></td>
					<td class="vacio"><nested:text property="cliente.fax"
						styleClass="cajaDis" maxlength="10" size="10" disabled="true" /></td>
					<td class="vacio"><nested:text property="cliente.email"
						styleClass="cajaDis" maxlength="20" size="20" disabled="true" /></td>
				</tr>

				<tr>
					<td class="textocampo">Población</td>
					<td class="textocampo">Provincia</td>
					<td class="textocampo">Código Postal</td>
				</tr>
				<tr>
					<td class="vacio"><nested:text property="cliente.poblacion"
						styleClass="cajaDis" maxlength="30" size="30" disabled="true" /></td>
					<td class="vacio"><nested:text property="cliente.provincia"
						styleClass="cajaDis" maxlength="30" size="30" disabled="true" /></td>
					<td class="vacio"><nested:text property="cliente.codPostal"
						styleClass="cajaDis" maxlength="5" size="10" disabled="true" /></td>

				</tr>

				<table width="700" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="textocampo">Calle</td>
						<td class="textocampo">Número</td>
						<td class="textocampo">Piso</td>
						<td class="textocampo">Letra</td>
						<td class="textocampo">Escalera</td>
					</tr>
					<tr>
						<td class="vacio"><nested:text property="cliente.calle"
							styleClass="cajaDis" maxlength="50" size="45" disabled="true" /></td>
						<td class="vacio"><nested:text property="cliente.num"
							styleClass="cajaDis" maxlength="10" size="10" disabled="true" /></td>
						<td class="vacio"><nested:text property="cliente.piso"
							styleClass="cajaDis" maxlength="10" size="10" disabled="true" /></td>
						<td class="vacio"><nested:text property="cliente.letra"
							styleClass="cajaDis" maxlength="10" size="10" disabled="true" /></td>
						<td class="vacio"><nested:text property="cliente.escalera"
							styleClass="cajaDis" maxlength="10" size="10" disabled="true" /></td>
					</tr>
				</table>

				<tr>
					<td colspan="3"><img src="/GST/images/px.gif" width="1"
						height="10"></td>
				</tr>

			</table>

			</td>
		</tr>

	</table>
</html:form>
</BODY>
</html>
</html:html>
