<!-- Manuel Guerrero del Pino - Resultado Clientes VIP -->
<%@ page
	import="java.util.*,com.actions.*,com.util.Pager,java.util.ArrayList,com.forms.DataBean"%>
<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%@page import="com.forms.DataBean;"%>
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
<html:form action="/searchClient.do">

	<nested:hidden property="pageEvent" />
	<nested:hidden property="id" />

	<table width="700" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td valign="top"><!--------------tabla central----------------->
			<table width="700" border="0" cellspacing="0" cellpadding="0">

				<tr>

					<td class="titular">Buscador de Clientes VIP</td>
					<td rowspan="2" align="right" valign="top"><img
						src="/GST/images/px.gif" width="115" height="74"></td>
				</tr>
				<html:errors />

				<tr>
					<td>
					<table width="700" border="0" cellpadding="0" cellspacing="0">

						<tr>
							<td colspan="5"><img src="/GST/images/px.gif" width="1"
								height="10"></td>
						</tr>
						<tr>
							<td class="subseccion" colspan="5">Datos de la central</td>
						</tr>
						<tr>
							<td colspan="5"><img src="/GST/images/px.gif" width="1"
								height="10"></td>
						</tr>

						<tr>
							<td class="textocampo">Nombre</td>
							<td class="textocampo">Provincia</td>
							<td class="textocampo">Código MIGA</td>
						</tr>
						<tr>
							<td class="vacio"><nested:text property="central.nombre"
								styleClass="caja" maxlength="50" size="45" /></td>
							<td class="vacio"><nested:text property="central.provincia"
								styleClass="caja" maxlength="30" size="30" /></td>
							<td class="vacio"><nested:text property="central.codMiga"
								styleClass="caja" maxlength="7" size="10" /></td>
						</tr>

						<tr>
							<td class="textocampo">Población</td>
							<td class="textocampo">Código Postal</td>
						</tr>
						<tr>
							<td class="vacio"><nested:text property="central.poblacion"
								styleClass="caja" maxlength="30" size="30" /></td>
							<td class="vacio"><nested:text property="central.codPostal"
								styleClass="caja" maxlength="5" size="10" /></td>
						</tr>

						<tr>
							<td colspan="5"><img src="/GST/images/px.gif" width="1"
								height="10"></td>
						</tr>

						<tr>
							<td class="subseccion" colspan="5">Lista de ofertas
							disponibles</td>
						</tr>
						<tr>
							<td colspan="5"><img src="/GST/images/px.gif" width="1"
								height="5"></td>
						</tr>

						<%
							ArrayList lista = (ArrayList) request
								.getAttribute("listaOfertasCentral");

							String oferta = "";
							if (lista != null)
								for (int i = 0; i < lista.size(); i++) {
									oferta = (String) lista.get(i);
						%>
						<tr>
							<td class="textocampo"><%=oferta%></td>
						</tr>
						<%
						}
						%>



						<tr>
							<td colspan="5"><img src="/GST/images/px.gif" width="1"
								height="10"></td>
						</tr>

						<tr>
							<td class="subseccion" colspan="5">Datos del Cliente VIP</td>
						</tr>
						<tr>
							<td colspan="5"><img src="/GST/images/px.gif" width="1"
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
							<td class="subseccion">Lista de Pedidos VIP</td>
						</tr>

						<!-- Inic Tabla -->
						<tr>
							<td>
							<table width="700" border="0" cellpadding="0" cellspacing="0">

								<!-- Inic Cabecera Tabla -->
								<tr>
									<td align="center" class="tablehead">Fecha</td>
									<td align="center" class="tablehead">Información</td>
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
											ArrayList listaPeticiones = (ArrayList) request
											.getAttribute("listaPeticionesVIP");

									if (listaPeticiones != null)
										for (int i = 0; i < listaPeticiones.size(); i++) {
											DataBean auxDB = null;
								%>
								<tr>
									<td align="center" class="tabledata"><%=((DataBean) listaPeticiones.get(i)).getId()%></td>
									<td align="center" class="tabledata"><%=((DataBean) listaPeticiones.get(i)).getValue()%></td>
								</tr>
								<tr>
									<td><img src="/GST/images/px.gif" width="1" height="5"></td>
								</tr>
								<!-- Fin Datos Tabla -->
								<%
								}
								%>
							</table>
							</td>
						</tr>
						<!-- Fin Tabla -->



					</table>
					</td>
				</tr>

				<tr>
					<td class="linea" colspan="2"><img src="/GST/images/px.gif"
						width="1" height="1"></td>
				</tr>
			</table>
	</table>
</html:form>
</BODY>
</html>
</html:html>
