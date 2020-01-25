<!-- Manuel Guerrero del Pino - Contratación -->
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

					<td class="titular">Contratación de ofertas</td>
					<td rowspan="2" align="right" valign="top"><img
						src="/GST/images/px.gif" width="115" height="50"></td>
				</tr>
				<html:errors />


				<table width="700" border="0" cellspacing="0" cellpadding="0">

					<tr>
						<td colspan="3"><img src="/GST/images/px.gif" width="1"
							height="1"></td>
					</tr>
					<tr>
						<td class="subseccion" colspan="3">Introduzca el teléfono
						para el que solicita la oferta</td>
					</tr>

					<tr>
						<td colspan="3"><img src="/GST/images/px.gif" width="1"
							height="10"></td>
					</tr>

					<tr>
						<td class="textocampo">Teléfono</td>
					</tr>
					<tr>
						<td class="vacio"><nested:text property="cliente.telefono"
							styleClass="caja" maxlength="9" size="9" /></td>

					</tr>
					<tr>
						<td colspan="3"><img src="/GST/images/px.gif" width="1"
							height="10"></td>
					</tr>


					<tr>
						<td class="subseccion" colspan="3">Si no dispone de número de
						teléfono introduzca su dirección</td>
					</tr>
					<tr>
						<td colspan="3"><img src="/GST/images/px.gif" width="1"
							height="10"></td>
					</tr>


					<tr>
						<td class="textocampo">Provincia</td>
						<td class="textocampo">Poblacion</td>
						<td class="textocampo">Codigo Postal</td>
					</tr>
					<tr>
						<td class="vacio"><nested:text property="cliente.provincia"
							styleClass="caja" maxlength="30" size="30" style="width:250" /></td>
						<td class="vacio"><nested:text property="cliente.poblacion"
							styleClass="caja" maxlength="30" size="30" style="width:250" /></td>
						<td class="vacio"><nested:text property="cliente.codPostal"
							styleClass="caja" maxlength="5" size="5" /></td>

					</tr>
				</table>

				<table width="700" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="textocampo">Calle</td>
						<td class="textocampo">Numero</td>
						<td class="textocampo">Piso</td>
						<td class="textocampo">Letra</td>
						<td class="textocampo">Escalera</td>
					</tr>
					<tr>
						<td class="vacio"><nested:text property="cliente.calle"
							styleClass="caja" maxlength="50" size="50" style="width:350" /></td>
						<td class="vacio"><nested:text property="cliente.num"
							styleClass="caja" maxlength="5" size="5" /></td>
						<td class="vacio"><nested:text property="cliente.piso"
							styleClass="caja" maxlength="5" size="5" /></td>
						<td class="vacio"><nested:text property="cliente.letra"
							styleClass="caja" maxlength="5" size="5" /></td>
						<td class="vacio"><nested:text property="cliente.escalera"
							styleClass="caja" maxlength="5" size="5" /></td>
					</tr>

					<tr>
						<td colspan="3"><img src="/GST/images/px.gif" width="1"
							height="10"></td>
					</tr>

					<tr>
						<td colspan="5" align="right"><a
							href="javascript: document.forms[0].pageEvent.value='buscarOferta';document.forms[0].submit();"><img
							src="/GST/images/bot_buscar.gif" hspace="5" border="0"></a></td>
					</tr>

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
