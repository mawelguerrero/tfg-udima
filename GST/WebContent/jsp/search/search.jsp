<!-- Manuel Guerrero del Pino - Buscador de Clientes -->
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
	bgproperties="fixed">
<html:form action="/search.do">

	<nested:hidden property="pageEvent" />

	<table width="700" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td><img src="/GST/images/px.gif" width="1" height="300"></td>
			<td valign="top"><!--------------tabla central----------------->
			<table width="700" border="0" cellspacing="0" cellpadding="0">

				<tr>

					<td class="titular">Buscador</td>
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
							<td class="subseccion" colspan="5">Datos del cliente</td>
						</tr>
						<tr>
							<td colspan="3"><img src="/GST/images/px.gif" width="1"
								height="10"></td>
						</tr>


						<tr>
							<td class="textocampo">Nombre</td>
							<td colspan="2" class="textocampo">Apellidos</td>
						</tr>
						<tr>
							<td class="vacio"><nested:text property="cliente.nombre"
								styleClass="caja" maxlength="50" size="30" /></td>
							<td colspan="2" class="vacio"><nested:text
								property="cliente.apellidos" styleClass="caja" maxlength="50"
								size="60" /></td>
						</tr>

						<tr>
							<td class="textocampo">DNI/NIF</td>
							<td class="textocampo">Teléfono</td>
						</tr>
						<tr>
							<td class="vacio"><nested:text
								property="cliente.documentoID" styleClass="caja" maxlength="50"
								size="20" /></td>
							<td class="vacio"><nested:text property="cliente.telefono"
								styleClass="caja" maxlength="9" size="9" /></td>
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

						<tr>
							<td colspan="2" class="textocampo">Calle</td>
							<td class="textocampo">Número</td>
						</tr>
						<tr>
							<td colspan="2" class="vacio"><nested:text
								property="cliente.calle" styleClass="caja" maxlength="50"
								size="45" /></td>
							<td class="vacio"><nested:text property="cliente.num"
								styleClass="caja" maxlength="50" size="5" /></td>

						</tr>

						<tr>
							<td colspan="3"><img src="/GST/images/px.gif" width="1"
								height="10"></td>
						</tr>
						<tr>
							<td class="subseccion" colspan="5">Datos de la oferta</td>
						</tr>
						<tr>
							<td colspan="3"><img src="/GST/images/px.gif" width="1"
								height="10"></td>
						</tr>
						<tr>
							<td class="textocampo">Tipo de la oferta</td>
							<td class="textocampo">Nombre</td>
							<td class="textocampo">Permanencia en meses</td>
							<td class="textocampo">Precio en euros</td>


						</tr>
						<tr>

							<td><nested:select styleClass="caja" property="oferta.tipo"
								size="1" style="width:190">
								<nested:options collection="typeOffertList" property="id"
									labelProperty="description" />
							</nested:select></td>


							<td><nested:text styleClass="caja" property="oferta.nombre"
								maxlength="30" size="30" /></td>

							<td><nested:text styleClass="caja"
								property="oferta.permanencia" maxlength="5" size="10" /></td>


							<td><nested:text styleClass="caja" property="oferta.precio"
								maxlength="5" size="10" /></td>
						</tr>

						<tr>
							<td colspan="3"><img src="/GST/images/px.gif" width="1"
								height="10"></td>
						</tr>
						<tr>
							<td class="subseccion" colspan="5">Datos del pedido</td>
						</tr>

						<tr>
							<td colspan="3"><img src="/GST/images/px.gif" width="1"
								height="10"></td>
						</tr>
						<tr>
							<td class="textocampo">Identificador del pedido</td>
						</tr>

						<tr>
							<td><nested:text styleClass="caja"
								property="pedido.idPedido" maxlength="10" size="20" /></td>
						</tr>
						<tr>
							<td colspan="3"><img src="/GST/images/px.gif" width="1"
								height="10"></td>
						</tr>

						<tr>
							<td class="textocampo">Fecha Inicio</td>
							<td class="textocampo">Fecha Fin</td>
						</tr>
						<tr>
							<td class="vacio" nowrap="nowrap"><jsp:include
								page="/jsp/util/calendar.jsp" flush="true">
								<jsp:param name="inputid" value="fechaInicio" />
								<jsp:param name="class" value="caja" />
							</jsp:include></td>
							<td class="vacio" nowrap="nowrap"><jsp:include
								page="/jsp/util/calendar.jsp" flush="true">
								<jsp:param name="inputid" value="fechaFin" />
								<jsp:param name="class" value="caja" />
							</jsp:include></td>
						</tr>

						<tr>
							<td colspan="3"><img src="/GST/images/px.gif" width="1"
								height="10"></td>
						</tr>

						<tr>
							<td colspan="5" align="right"><a
								href="javascript: document.forms[0].pageEvent.value='buscar';document.forms[0].submit();"><img
								src="/GST/images/bot_buscar.gif" hspace="5" border="0"></a></td>
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
