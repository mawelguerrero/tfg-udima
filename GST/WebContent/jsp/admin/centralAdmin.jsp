<!-- Manuel Guerrero del Pino - Administración de Centrales -->
<%@ page import="java.util.*,com.actions.*,com.util.Pager"%>
<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%
			Pager p = (Pager) session
			.getAttribute("ResultadoPaginadoCentrales");
	int totalPages = 0;
	int thisPage = 0;
	if (p != null) {
		totalPages = p.getNumberPages();
		thisPage = p.getNumberCurrentPage();
	}
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

<SCRIPT language="JavaScript">

function previous(){
  document.forms[0].pageEvent.value='previous';
  document.forms[0].submit();
}

function next(){
  document.forms[0].pageEvent.value='next';
  document.forms[0].submit();
}

function visualizarCentralita(codigoCentral){
  document.forms[0].id.value=codigoCentral;
  document.forms[0].pageEvent.value='view_central';
  document.forms[0].submit();
}

</SCRIPT>

</HEAD>
<BODY marginwidth="0" marginheight="0" leftmargin="0"
	bgproperties="fixed" onload="deselecciona()">
<html:form action="/centralAdmin.do">

	<nested:hidden property="pageEvent" />
	<nested:hidden property="id" />

	<table width="700" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td valign="top"><!--------------tabla central----------------->
			<table width="700" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="titular">Gestión de Centrales</td>
					<td rowspan="2" align="right" valign="top"><img
						src="/GST/images/px.gif" width="115" height="74"></td>
				</tr>
				<html:errors />
				<tr>
					<td>
					<table width="700" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td colspan="3"><img src="/GST/images/px.gif" width="1"
								height="10"></td>
						</tr>
						<tr>
							<td class="subseccion" colspan="3">Datos de la central</td>
						</tr>
						<tr>
							<td colspan="3"><img src="/GST/images/px.gif" width="1"
								height="10"></td>
						</tr>
						<tr>
							<td colspan="3">
							<table width="700" border="0" cellspacing="0" cellpadding="0">

								<tr>
									<td class="textocampo">Provincia</td>
									<td class="textocampo">Población</td>

								</tr>
								<tr>
									<td><nested:text styleClass="caja"
										property="central.provincia" maxlength="40" size="40" /></td>
									<td><nested:text styleClass="caja"
										property="central.poblacion" maxlength="40" size="40" /></td>
								</tr>
								<tr>
									<td colspan="3"><img src="/GST/images/px.gif" width="1"
										height="10"></td>
								</tr>
								<tr>
									<td class="textocampo">Código Postal</td>
									<td class="textocampo" width="65">Código MIGA</td>

								</tr>
								<tr>
									<td><nested:text styleClass="caja"
										property="central.cdPostal" maxlength="5" style="width:135" /></td>
									<td><nested:text styleClass="caja"
										property="central.cdMiga" maxlength="7" style="width:135" /></td>
								</tr>
								<tr>
									<td colspan="3"><img src="/GST/images/px.gif" width="1"
										height="10"></td>
								</tr>
							</table>
							</td>
						</tr>
						<tr>
							<td colspan="5" align="right"><a
								href="javascript: document.forms[0].pageEvent.value='buscar';document.forms[0].submit();"><img
								src="/GST/images/bot_buscar.gif" hspace="5" border="0"></a></td>
						</tr>

						<tr>
							<td class="linea" colspan="2"><img src="/GST/images/px.gif"
								width="1" height="1"></td>
						</tr>
					</table>

					</td>
				</tr>

				<% if (p != null){ %>

				<!-- Inic Tabla -->
				<tr>
					<td valign="top">
					<table width="700" border="0" cellpadding="0" cellspacing="0">

						<tr>
							<td colspan="5"><img src="/GST/images/px.gif" width="1"
								height="10"></td>
						</tr>
						<tr>
							<td class="subseccion" colspan="5">Resultado de la búsqueda</td>
						</tr>
						<tr>
							<td colspan="5"><img src="/GST/images/px.gif" width="1"
								height="10"></td>
						</tr>

						<%
						if ( p.isEmpty() || p.getPage().isEmpty()) {
						String error = "No se han encontrado peticiones con los criterios indicados";
				%>
						<tr>
							<td align="center" class="textotitulo" colspan="6"><%=error%></td>
						</tr>
						<%
				} else {
				%>
						<!-- Inic Cabecera Tabla -->
						<tr>
							<td align="center" size="299" class="tablehead">Código miga</td>
							<td align="center" class="tablehead">Nombre</td>
							<td align="center" class="tablehead">Provincia</td>
							<td align="center" class="tablehead">Población</td>
							<td align="center" class="tablehead">Código postal</td>
						</tr>
						<tr>
							<td><img src="/GST/images/px.gif" width="1" height="5"></td>
						</tr>
						<tr>
							<td class="linea" colspan="10"><img src="/GST/images/px.gif"
								width="1" height="1"></td>
						</tr>
						<tr>
							<td><img src="/GST/images/px.gif" width="1" height="5"></td>
						</tr>
						<!-- Fin Cabecera Tabla -->
						<!-- Inic Datos Tabla -->
						<%
						List results = p.getPage();
						Iterator it = results.iterator();
						String codigoMiga = "";
						String provincia = "";
						String poblacion = "";
						String codigoPostal = "";
						String nombre = "";

						int i = 0;
						while (it.hasNext()) {
							i++;
							HashMap h = (HashMap) it.next();
							codigoMiga = (String) h.get("CDMIGA");
							provincia = (String) h.get("TXPROVIN");
							poblacion = (String) h.get("TXPOBLAC");
							codigoPostal = (String) h.get("CDPOSTAL");
							nombre = (String) h.get("TXDESCCEN");

							String clase = ((i % 2 == 0) ? "grisoscuro10" : "gris10b");
				%>
						<tr>
							<td align="center" class="tabledata"><a href="#"
								onclick="javascript:visualizarCentralita(<%=codigoMiga%>);"><%=codigoMiga%></a></td>
							<td align="center" class="tabledata"><%=nombre%></td>
							<td align="center" class="tabledata"><%=provincia%></td>
							<td align="center" class="tabledata"><%=poblacion%></td>
							<td align="center" class="tabledata"><%=codigoPostal%></td>

						</tr>
						<tr>
							<td><img src="/GST/images/px.gif" width="1" height="5"></td>
						</tr>
						<!-- Fin Datos Tabla -->
						<%
				}
				%>
						<%
				}
				%>
					</table>
					</td>
				</tr>
				<!-- Fin Tabla -->
				<tr>
					<td><img src="/GST/images/px.gif" width="1" height="25"></td>
				</tr>
				<!-- Inic Paginacion -->
				<%@ include file="../login/paginacion.jsp"%>
				<!-- Fin Paginacion -->

				<%} %>

			</table>
			</html:form>
</BODY>
</html>
</html:html>
