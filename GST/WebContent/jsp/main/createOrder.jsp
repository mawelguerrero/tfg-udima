<!-- Manuel Guerrero del Pino - Creación de Pedido -->
<%@ page import="java.util.*,com.actions.*"%>
<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%
	boolean existeClienteVIP = false;
	existeClienteVIP = ((Boolean) session
			.getAttribute("ExisteClienteVIP")).booleanValue();

	boolean existeCliente = false;
	existeCliente = ((Boolean) session.getAttribute("ExisteCliente"))
			.booleanValue();
%>

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

					<td class="titular">Creación de un Pedido</td>
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
						styleClass="caja" maxlength="30" size="30" disabled="true" /></td>
				</tr>


				<tr>
					<td class="textocampo">Permanencia en meses</td>
					<td class="textocampo">Precio en euros</td>
				</tr>
				<tr>
					<td class="vacio"><nested:text property="oferta.permanencia"
						styleClass="caja" maxlength="10" size="10" disabled="true" /></td>
					<td class="vacio"><nested:text property="oferta.precio"
						styleClass="caja" maxlength="10" size="10" disabled="true" /></td>
				</tr>


				<tr>
					<td class="textocampo" colspan="5">Información</td>
				</tr>
				<tr>
					<td class="vacio" colspan="5"><nested:text
						property="oferta.informacion" styleClass="caja" maxlength="150"
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
					<td class="textocampo">C.C.C.</td>
				</tr>
				<tr>
					<td class="vacio"><nested:text property="cliente.formaPago"
						styleClass="caja" maxlength="25" size="25" /></td>
					<td class="vacio"><nested:text property="cliente.numeroCuenta"
						styleClass="caja" maxlength="30" size="30" /></td>
				</tr>

				<tr>
					<td class="textocampo">Número tarjeta</td>
					<td class="textocampo">Fecha de caducidad</td>
				</tr>
				<tr>
					<td class="vacio"><nested:text
						property="cliente.numeroTarjeta" styleClass="caja" maxlength="20"
						size="20" /></td>
					<td class="vacio"><nested:text
						property="cliente.fechaCaducidad" styleClass="caja" maxlength="50"
						size="15" /></td>
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
						property="cliente.informacion" styleClass="caja" maxlength="150"
						size="100" /></td>
				</tr>
				<tr>
					<td colspan="3"><img src="/GST/images/px.gif" width="1"
						height="10"></td>
				</tr>

				<tr>
					<td class="subseccion" colspan="3">Introduzca los datos del cliente</td>
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
						styleClass="caja" maxlength="9" size="9" /></td>
					<td class="vacio"><nested:text property="cliente.documentoID"
						styleClass="caja" maxlength="50" size="30" /></td>
				</tr>

				<tr>
					<td class="textocampo">Nombre</td>
					<td colspan="3" class="textocampo">Apellidos</td>
				</tr>
				<tr>
					<td class="vacio"><nested:text property="cliente.nombre"
						styleClass="caja" maxlength="40" size="40" /></td>
					<td colspan="3" class="vacio"><nested:text
						property="cliente.apellidos" styleClass="caja" maxlength="60"
						size="60" /></td>
				</tr>

				<tr>
				<tr>
					<td class="textocampo">Movil</td>
					<td class="textocampo">Fax</td>
					<td class="textocampo">Email</td>
				</tr>
				<tr>
					<td class="vacio"><nested:text property="cliente.movil"
						styleClass="caja" maxlength="9" size="9" /></td>
					<td class="vacio"><nested:text property="cliente.fax"
						styleClass="caja" maxlength="10" size="10" /></td>
					<td class="vacio"><nested:text property="cliente.email"
						styleClass="caja" maxlength="20" size="20" /></td>
				</tr>



				<tr>
					<td class="textocampo">Población</td>
					<td class="textocampo">Provincia</td>
					<td class="textocampo">Código Postal</td>
				</tr>
				<tr>
					<td class="vacio"><nested:text property="cliente.poblacion"
						styleClass="caja" maxlength="30" size="30" /></td>
					<td class="vacio"><nested:text property="cliente.provincia"
						styleClass="caja" maxlength="30" size="30" /></td>
					<td class="vacio"><nested:text property="cliente.codPostal"
						styleClass="caja" maxlength="5" size="10" /></td>

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
							styleClass="caja" maxlength="50" size="45" /></td>
						<td class="vacio"><nested:text property="cliente.num"
							styleClass="caja" maxlength="10" size="10" /></td>
						<td class="vacio"><nested:text property="cliente.piso"
							styleClass="caja" maxlength="10" size="10" /></td>
						<td class="vacio"><nested:text property="cliente.letra"
							styleClass="caja" maxlength="10" size="10" /></td>
						<td class="vacio"><nested:text property="cliente.escalera"
							styleClass="caja" maxlength="10" size="10" /></td>
					</tr>
				</table>




				<tr>
					<td colspan="3"><img src="/GST/images/px.gif" width="1"
						height="10"></td>
				</tr>

				<%
				if (existeCliente) {
				%>

				<tr>
					<td colspan="5" align="right"><a
						href="javascript: document.forms[0].pageEvent.value='modificarPedido';document.forms[0].submit();"><img
						src="/GST/images/bot_modificar.gif" hspace="5" border="0"></a> <a
						href="javascript: document.forms[0].pageEvent.value='borrarPedido';document.forms[0].submit();"><img
						src="/GST/images/bot_borrar.gif" hspace="5" border="0"></a></td>
				</tr>


				<%
				} else if (existeClienteVIP) {
				%>
				<tr>
					<td colspan="5" align="right"><a
						href="javascript: document.forms[0].pageEvent.value='crearPedidoDeVIP';document.forms[0].submit();"><img
						src="/GST/images/generar_pedido.gif" hspace="5" border="0"></a></td>
				</tr>
				<%
				} else {
				%>
				<tr>
					<td colspan="5" align="right"><a
						href="javascript: document.forms[0].pageEvent.value='crearPedido';document.forms[0].submit();"><img
						src="/GST/images/generar_pedido.gif" hspace="5" border="0"></a></td>
				</tr>
				<%
				}
				%>

				<tr>
					<td class="linea" colspan="2"><img src="/GST/images/px.gif"
						width="1" height="1"></td>
				</tr>

			</table>

			</td>
		</tr>


	</table>
</html:form>
</BODY>
</html>
</html:html>
