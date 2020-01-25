<!-- Manuel Guerrero del Pino - Gestión de Centrales -->
<%@ page import="java.util.*,com.actions.*,com.util.Pager"%>
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

<script>
  function moverElementosLista(objOrigen, objDestino) {
    index = objOrigen.selectedIndex;

    if (index==-1)   {
      alert("Debe seleccionar un elemento para poder moverlo");
      return;
    }

    var texto = "";
    var value = "";

    for(i=objOrigen.length-1; i>=0 ;i--) {
      if(objOrigen.options[i].selected == true){
        texto = objOrigen.options[i].text;
        value = objOrigen.options[i].value;
        objDestino.options[objDestino.length] = new Option(texto, value);
        objOrigen.options[i] = null;
      }
    }
  }
  
   function modificarCentralita() {
   
 
    for(i=document.forms[0].lista_seleccionados.length-1; i>=0 ;i--) {
      document.forms[0].lista_seleccionados.options[i].selected=true;
    }
      
    document.forms[0].pageEvent.value='mod_central';      
    document.forms[0].submit();
    
  }
 
</script>

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
					<td class="textotitulo" align="right">Los campos marcados con
					* son obligatorios</td>
				</tr>
				<tr>

					<td class="titular">Gestión de Centrales</td>
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
							<td class="subseccion" colspan="5">Datos de la Central</td>
						</tr>
						<tr>
							<td colspan="5"><img src="/GST/images/px.gif" width="1"
								height="10"></td>
						</tr>

						<tr>
							<td class="textocampo">Nombre</td>
							<td class="textocampo">Código MIGA</td>
						</tr>
						<tr>
							<td class="vacio"><nested:text property="central.nombre"
								styleClass="caja" maxlength="40" size="40" /></td>
							<td class="vacio"><nested:text property="central.cdMiga"
								styleClass="caja" maxlength="7" size="7" style="width:135" /></td>
						</tr>
						<tr>
							<td colspan="5"><img src="/GST/images/px.gif" width="1"
								height="10"></td>
						</tr>

						<tr>
							<td class="textocampo">Población</td>
							<td class="textocampo">Código Postal</td>
						</tr>
						<tr>
							<td class="vacio"><nested:text property="central.poblacion"
								styleClass="caja" maxlength="40" size="40" /></td>
							<td class="vacio"><nested:text property="central.cdPostal"
								styleClass="caja" maxlength="5" size="5" style="width:135" /></td>
						</tr>

						<tr>
							<td colspan="5"><img src="/GST/images/px.gif" width="1"
								height="10"></td>
						</tr>

						<tr>
							<td class="textocampo">Provincia</td>
						</tr>
						<tr>
							<td class="vacio" colspan="2"><nested:text
								property="central.provincia" styleClass="caja" maxlength="40"
								size="40" /></td>
						</tr>
						<tr>
							<td colspan="5"><img src="/GST/images/px.gif" width="1"
								height="10"></td>
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

		<tr>
			<td>
			<table width="700" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td colspan="5"><img src="/GST/images/px.gif" width="1"
						height="10"></td>
				</tr>
				<tr>
					<td class="subseccion" colspan="5">Oferta</td>
				</tr>
				<tr>
					<td colspan="8"><img src="/GST/images/px.gif" width="1"
						height="10"></td>
				</tr>
				<tr>
					<td colspan="2" class="textocampo" width="350">Ofertas
					disponibles</td>
					<td class="vacio" width="250"></td>
					<td class="textocampo" width="350">Ofertas seleccionadas *</td>
				</tr>
				<tr>
					<td colspan="2" class="vacio" valign="top" width="350"><html:select
						property="lista_disponibles" styleClass="caja" size="5"
						style="width:200" multiple="true">
						<html:options collection="centralAvailableTable" property="id"
							labelProperty="description" />
					</html:select></td>
					<td class="vacio" align="center" width="250"><a
						href="javascript:moverElementosLista(document.forms[0].lista_disponibles, document.forms[0].lista_seleccionados)">
					<img src="/GST/images/flecha_right.gif" alt="Deseleccionar"
						border="0"> </a> <br>
					<a
						href="javascript:moverElementosLista(document.forms[0].lista_seleccionados, document.forms[0].lista_disponibles)">
					<img src="/GST/images/flecha_left.gif" alt="Seleccionar" border="0">
					</a></td>
					<td class="vacio" valign="top" width="350"><html:select
						property="lista_seleccionados" styleClass="caja" size="5"
						style="width:200" multiple="true">
						<html:options collection="centralSelectedTable" property="id"
							labelProperty="description" />
					</html:select></td>
				</tr>

				<tr>
					<td colspan="8"><img src="/GST/images/px.gif" width="10"
						height="30"></td>
				</tr>

				<tr>
					<td colspan="5" align="right"><a
						href="javascript:modificarCentralita();"><img
						src="/GST/images/bot_modificar.gif" hspace="5" border="0"></a></td>
				</tr>

			</table>
			</td>
		</tr>






	</table>
</html:form>
</BODY>
</html>
</html:html>
