<!-- Manuel Guerrero del Pino - Cliente VIP -->
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

					<td class="titular">Datos del Cliente VIP</td>
					<td rowspan="2" align="right" valign="top"><img
						src="/GST/images/px.gif" width="115" height="50"></td>
				</tr>
				<html:errors />

				<tr>
					<td colspan="5"><img src="/GST/images/px.gif" width="1"
						height="10"></td>
				</tr>

				<tr>
					<td>
					<table width="700" border="0" cellpadding="0" cellspacing="0">


						<tr>
							<td class="subseccion" colspan="5">Datos del cliente</td>
						</tr>
						<tr>
							<td colspan="5"><img src="/GST/images/px.gif" width="1"
								height="10"></td>
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
							<td colspan="5"><img src="/GST/images/px.gif" width="1"
								height="10"></td>
						</tr>

						<tr>
							<td class="subseccion">Datos de la llamada</td>
						</tr>
						<tr>
							<td colspan="5"><img src="/GST/images/px.gif" width="1"
								height="10"></td>
						</tr>
						<tr>
							<td colspan="4" class="textocampo">Código miga de la central
							a la que pertenece</td>
						</tr>
						<tr>
							<td colspan="4" class="vacio"><nested:text
								property="central.codMiga" styleClass="caja" maxlength="10"
								size="10" disabled="true" /></td>

						</tr>

						<tr>
							<td colspan="1" class="textocampo">Teléfono</td>
						</tr>
						<tr>
							<td colspan="1" class="vacio"><nested:text
								property="cliente.telefono" styleClass="caja" maxlength="9"
								size="9" /></td>

						</tr>

						<tr>
							<td colspan="5" class="textocampo">Observaciones</td>
						</tr>
						<tr>
							<td colspan="5" class="vacio"><nested:text
								property="cliente.informacion" styleClass="caja" maxlength="60"
								size="100" /></td>

						</tr>

						<tr>
							<td class="linea" colspan="2"><img src="/GST/images/px.gif"
								width="1" height="10"></td>
						</tr>
						<tr>
							<td align="right"><a
								href="javascript: document.forms[0].pageEvent.value='guardar';document.forms[0].submit();"><img
								src="/GST/images/bot_guardar.gif" hspace="5" border="0"></a></td>
						</tr>

					</table>
					</td>
				</tr>

			</table>

			</td>
		</tr>

	</table>
</html:form>
</BODY>
</html>
</html:html>
