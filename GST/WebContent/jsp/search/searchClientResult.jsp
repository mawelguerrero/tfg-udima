<!-- Manuel Guerrero del Pino - Resultado búsqueda VIP -->
<%@ page import="java.util.*,com.actions.*,com.util.Pager"%>
<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%
	Pager p = (Pager) session.getAttribute("ResultadoPaginado");
	int totalPages = p.getNumberPages();
	int thisPage = p.getNumberCurrentPage();
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

function visualizarClienteVIP(idClienteVIP){
  document.forms[0].id.value=idClienteVIP;
  document.forms[0].pageEvent.value='view_clienteVIP';
  document.forms[0].submit();
}
</SCRIPT>


</HEAD>
<BODY marginwidth="0" marginheight="0" leftmargin="0"
	bgproperties="fixed">
<html:form action="/searchClient.do">

	<nested:hidden property="pageEvent" />
	<nested:hidden property="id" />

	<table width="700" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td><img src="/GST/images/px.gif" width="1" height="300"></td>
			<td valign="top"><!--------------tabla central----------------->
			<table width="700" border="0" cellspacing="0" cellpadding="0">

				<tr>

					<td class="titular">Buscador de Clientes VIP</td>
					<td rowspan="2" align="right" valign="top"><img
						src="/GST/images/px.gif" width="115" height="74"></td>
				</tr>
				<html:errors />

				<!-- Tabla de busqueda -->
				<tr>
					<td>
					<table border="0" cellpadding="0" cellspacing="0">

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



						<!-- Inic Tabla -->
						<tr>
							<td>
							<table width="700" border="0" cellpadding="0" cellspacing="0">

								<%
										if (p.isEmpty() || p.getPage().isEmpty()) {
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
									<td align="center" class="tablehead">Fecha</td>
									<td align="center" class="tablehead">Telefono</td>
									<td align="center" class="tablehead">Nombre</td>
									<td align="center" class="tablehead">Apellidos</td>
									<td align="center" class="tablehead">Comentario</td>
								</tr>
								<tr>
									<td><img src="/GST/images/px.gif" width="1" height="5"></td>
								</tr>
								<tr>
									<td class="linea" colspan="10"><img
										src="/GST/images/px.gif" width="1" height="1"></td>
								</tr>
								<tr>
									<td><img src="/GST/images/px.gif" width="1" height="5"></td>
								</tr>
								<!-- Fin Cabecera Tabla -->
								<!-- Inic Datos Tabla -->
								<%
										List results = p.getPage();
										Iterator it = results.iterator();
										String tlfn = "";
										String nombre = "";
										String apellidos = "";
										String fecha = "";
										String comentario = "";
										String idClienteVIP = "";
										int idCliente = 0;

										int i = 0;
										while (it.hasNext()) {
											i++;
											HashMap h = (HashMap) it.next();
											tlfn = (String) h.get("TELEFONO");
											nombre = (String) h.get("NOMBRE");
											apellidos = (String) h.get("APELLIDOS");
											fecha = (String) h.get("FECHA");
											comentario = (String) h.get("COMENTARIO");
											idClienteVIP = (String) h.get("ID_CLIENTE_POTENCIAL");

											idCliente = Integer.parseInt(idClienteVIP);

											String clase = ((i % 2 == 0) ? "grisoscuro10" : "gris10b");
								%>
								<tr>
									<td align="center" class="tabledata"><%=fecha%></td>
									<td align="center" class="tabledata"><a href="#"
										onclick="javascript:visualizarClienteVIP(<%=idCliente%>);"><%=tlfn%></a></td>
									<td align="center" class="tabledata"><%=nombre%></td>
									<td align="center" class="tabledata"><%=apellidos%></td>
									<td align="center" class="tabledata"><%=comentario%></td>
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

						<tr>
							<td colspan="5" align="left"><a
								href="javascript: document.forms[0].pageEvent.value='success';document.forms[0].submit();"><img
								src="/GST/images/bot_volver.gif" hspace="5" border="0"></a></td>
						</tr>

					</table>
					</td>
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
