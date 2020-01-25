<!-- Manuel Guerrero del Pino - Datos de Cobertura -->
<%@ page import="java.util.*,com.actions.*"%>
<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%
			ArrayList resultadoOfertas = (ArrayList) session
			.getAttribute("ResultadoOfertasConCobertura");
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

<script type="text/javascript">

function cancelar() {
	document.forms[0].pageEvent.value='cancelar';
	document.forms[0].submit();
}

function aceptar() {
	
	var selectedVal;

	for( i = 0; i < document.forms[0].ofertaSeleccionada.length; i++ )
	{
	  if(document.forms[0].ofertaSeleccionada[i].checked){
	    selectedVal = document.forms[0].ofertaSeleccionada[i].value; 
	    break;
	  }
	}
	document.forms[0].pageEvent.value='aceptar';
	document.forms[0].idOferta.value=selectedVal;
	document.forms[0].submit();
}

</script>


</HEAD>
<BODY marginwidth="0" marginheight="0" leftmargin="0"
	bgproperties="fixed" onload="deselecciona()">
<html:form action="/offertMain.do">

	<nested:hidden property="pageEvent" />
	<nested:hidden property="idOferta" />

	<table width="700" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td><img src="/GST/images/px.gif" width="1" height="300"></td>
			<td valign="top"><!--------------tabla central----------------->
			<table width="700" border="0" cellspacing="0" cellpadding="0">


				<logic:notEmpty name="offertMainForm" property="oferta.idOferta">

					<tr>

						<td class="titular">Oferta contratada actualmente</td>
						<td rowspan="2" align="right" valign="top"><img
							src="/GST/images/px.gif" width="115" height="50"></td>
					</tr>
					<tr>
						<td colspan="5"><img src="/GST/images/px.gif" width="1"
							height="10"></td>
					</tr>

					<tr>
						<td class="textocampo"><b><bean:write
							name="offertMainForm" property="oferta.nombre" /></b></td>
					</tr>

					<tr>
						<td class="textocampo">La permanenia de la oferta es de <bean:write
							name="offertMainForm" property="oferta.permanencia" /> meses</td>
					</tr>

					<tr>
						<td class="textocampo">El precio es de <bean:write
							name="offertMainForm" property="oferta.precio" /> euros</td>
					</tr>

					<tr>
						<td class="textocampo">Descripcion: <bean:write
							name="offertMainForm" property="oferta.informacion" /></td>
					</tr>

				</logic:notEmpty>


				<tr>
					<td colspan="5"><img src="/GST/images/px.gif" width="1"
						height="20"></td>
				</tr>

				<tr>

					<td class="titular">Ofertas disponibles</td>
					<td rowspan="2" align="right" valign="top"><img
						src="/GST/images/px.gif" width="115" height="50"></td>
				</tr>
				<html:errors />
				<tr>
					<td colspan="5"><img src="/GST/images/px.gif" width="1"
						height="10"></td>
				</tr>

				<!-- Inic Tabla -->
				<tr>
					<td>
					<table width="700" border="0" cellpadding="0" cellspacing="0">

						<%
								if (resultadoOfertas.isEmpty()) {
								String error = "No se han encontrado ofertas con los criterios introducidos";
						%>
						<tr>
							<td align="center" class="textotitulo" colspan="6"><%=error%></td>
						</tr>
						<%
						} else {
						%>

						<!-- Inic Datos Tabla -->
						<%
								Iterator it = resultadoOfertas.iterator();
								String idOferta = "";
								String nombre = "";
								String tipoOferta = "";
								String permanencia = "";
								String precio = "";
								String informacion = "";

								int ofertasVoz = 0;
								int ofertaDatos = 0;
								int ofertasVozDatos = 0;

								int i = 0;
								while (it.hasNext()) {
									i++;
									HashMap h = (HashMap) it.next();
									idOferta = (String) h.get("ID_OFERTA");
									nombre = (String) h.get("NOMBRE");
									tipoOferta = (String) h.get("TIPO_OFERTA");
									permanencia = (String) h.get("PERMANENCIA");
									precio = (String) h.get("PRECIO");
									informacion = (String) h.get("INFORMACION");

									if (tipoOferta.equals("1")) {
								ofertasVoz = ofertasVoz + 1;
									}

									if (tipoOferta.equals("2")) {
								ofertaDatos = ofertaDatos + 1;
									}

									if (tipoOferta.equals("3")) {
								ofertasVozDatos = ofertasVozDatos + 1;
									}

									String clase = ((i % 2 == 0) ? "grisoscuro10" : "gris10b");
						%>
						<%
						if (ofertasVoz == 1) {
						%>
						<tr>
							<td class="subseccion" colspan="3">Ofertas de voz</td>
						</tr>


						<%
									ofertasVoz = ofertasVoz + 1;
									}
						%>


						<%
						if (ofertaDatos == 1) {
						%>
						<tr>
							<td colspan="5"><img src="/GST/images/px.gif" width="1"
								height="10"></td>
						</tr>
						<tr>
							<td class="subseccion" colspan="3">Ofertas de Datos</td>
						</tr>

						<%
									ofertaDatos = ofertaDatos + 1;
									}
						%>

						<%
						if (ofertasVozDatos == 1) {
						%>
						<tr>
							<td colspan="5"><img src="/GST/images/px.gif" width="1"
								height="10"></td>
						</tr>
						<tr>
							<td class="subseccion" colspan="3">Ofertas de voz y datos</td>
						</tr>

						<%
									ofertasVozDatos = ofertasVozDatos + 1;
									}
						%>

						<tr>
							<td colspan="5"><img src="/GST/images/px.gif" width="1"
								height="10"></td>
						</tr>

						<tr>
							<td class="subseccion3"><img src="/GST/images/px.gif"
								width="5" height="1"> <input id="ofertaSeleccionada"
								name="ofertaSeleccionada" type="radio" value=<%=idOferta%>>
							<%=nombre%></td>
						</tr>

						<tr>
							<td class="textocampo" style="aling: center">La permanenia
							de la oferta es de <%=permanencia%> meses</td>
						</tr>


						<tr>
							<td class="textocampo">El precio es de <%=precio%> euros</td>
						</tr>

						<tr>
							<td class="textocampo">Descripcion: <%=informacion%></td>
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


				<%
				if (!resultadoOfertas.isEmpty()) {
				%>
				<!-- Tabla Botonera -->
				<tr>
					<td align="right">
					<table border="0" cellspacing="0" cellpadding="0" width="200">
						<tr>
							<td class="textocamposinInput" valign="middle" align="left">
							<a href="#" onclick="cancelar();"><img
								src="/GST/images/bot_cancelar.gif" border="0" alt="" /></a></td>
							<td class="textocamposinInput" valign="middle" align="right">
							<a href="#" onclick="aceptar();" style="display: table-row">
							<img src="/GST/images/bot_aceptar.gif" border="0" alt="" /></a></td>
						</tr>
					</table>
					</td>
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
