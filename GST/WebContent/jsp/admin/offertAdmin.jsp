<!-- Manuel Guerrero del Pino - Administración de Ofertas -->
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
<script languaje="JavaScript">


  function validarCrearOferta(){
    //if((document.forms[0].tipo.value == "-1") || (document.forms[0].nombre.value == "") ||  (document.forms[0].permanencia.value == "")
        //|| (document.forms[0].precio.value == "") || (document.forms[0].informacion.value == "") ) {
      //alert('Debe rellenar los campos obligatorios del formulario.');
    //} else {
    	document.forms[0].pageEvent.value='create_offert';
      document.forms[0].submit();
    //}
  }


  function validarModifyOferta() {
    document.forms[0].pageEvent.value='mod_offert';
    document.forms[0].submit();
  }
  

  function validarBorrarOferta() {
    document.forms[0].pageEvent.value='delete_offert';
    document.forms[0].submit();
    
  }

  function cambiarOffert(){
    document.forms[0].pageEvent.value='view_offerts';
    document.forms[0].submit();
  }

</script>


</HEAD>
<BODY marginwidth="0" marginheight="0" leftmargin="0"
	bgproperties="fixed" onload="deselecciona()">
<html:form action="/offertAdmin.do">

	<nested:hidden property="pageEvent" />

	<table width="700" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td><img src="/GST/images/px.gif" width="1" height="300"></td>
			<td valign="top"><!--------------tabla central----------------->
			<table width="700" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="textotitulo" align="right">Los campos marcados con
					* son obligatorios</td>
				</tr>
				<tr>

					<td class="titular">Gesti&oacute;n de Ofertas</td>
					<td rowspan="2" align="right" valign="top"><img
						src="/GST/images/px.gif" width="115" height="74"></td>
				</tr>
				<html:errors />

				<tr>
					<td valign="bottom"><!-----------------------------tabla de los combos--------------------------->
					<table width="700" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td><img src="/GST/images/px.gif" width="140" height="1"></td>
							<td><img src="/GST/images/px.gif" width="20" height="1"></td>
						</tr>

						<tr>
							<td class="textocampo">Nombre de la oferta</td>
							<td><img src="/GST/images/px.gif" width="1" height="1"></td>
						</tr>
						<tr>
							<td><nested:select styleClass="caja"
								property="oferta.idOferta" size="1" style="width:300"
								onchange="javascript:cambiarOffert()">

								<nested:options collection="offertTable" property="id"
									labelProperty="description" />

							</nested:select></td>
						</tr>
						<tr>
							<td colspan="3"><img src="/GST/images/px.gif" width="140"
								height="5"></td>
						</tr>
					</table>
					</td>
				</tr>
				<!----------------------------- FIN tabla de los combos--------------------------->

				<tr>
					<td class="linea" colspan="2"><img src="/GST/images/px.gif"
						width="1" height="1"></td>
				</tr>
			</table>

			<!--------------tabla de resultados-------------------->

			<table width="700" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td><img src="/GST/images/px.gif" width="135" height="10"></td>
					<td><img src="/GST/images/px.gif" width="370" height="10"></td>

					<td colspan="2" align="center"><img src="/GST/images/px.gif"
						width="1" height="1"></td>
				</tr>
			</table>

			<!-------------fin de tabla de resultados--------------> <!----------------tabla de operativa-------------------->

			<table width="700" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td colspan="3"><img src="/GST/images/px.gif" width="1"
						height="10"></td>
				</tr>
				<tr>
					<td class="subseccion" colspan="3">Detalle de la oferta</td>
				</tr>
				<tr>
					<td colspan="3"><img src="/GST/images/px.gif" width="1"
						height="10"></td>
				</tr>
				<tr>
					<td colspan="3">
					<table width="700" border="0" cellspacing="0" cellpadding="0">

						<tr>
							<td class="textocampo" width="265">Nombre<label
								class="textotitulo"> *</label></td>
							<td class="textocampo">Tipo de la oferta<label
								class="textotitulo"> *</label></td>
						</tr>
						<tr>

							<td><nested:text styleClass="caja" property="oferta.nombre"
								maxlength="50" style="width:135" /></td>

							<td><nested:select styleClass="caja" property="oferta.tipo"
								size="1" style="width:190">
								<nested:options collection="typeOffertList" property="id"
									labelProperty="description" />
							</nested:select></td>

						</tr>
						<tr>
							<td colspan="3"><img src="/GST/images/px.gif" width="1"
								height="10"></td>
						</tr>
						<tr>
							<td class="textocampo" width="165">Precio en euros<label
								class="textotitulo"> *</label></td>
							<td class="textocampo">Permanencia en meses<label
								class="textotitulo"> *</label></td>

						</tr>
						<tr>
							<td><nested:text styleClass="caja" property="oferta.precio"
								maxlength="50" style="width:135" /></td>

							<td><nested:text styleClass="caja"
								property="oferta.permanencia" maxlength="50" style="width:135" /></td>
						</tr>

						<tr>

							<td colspan="3"><img src="/GST/images/px.gif" width="1"
								height="10"></td>
						</tr>


						<tr>
							<td colspan="2" class="textocampo">Texto descriptivo<label
								class="textotitulo"> *</label></td>
							<td></td>
						</tr>

						<tr>
							<td colspan="2"><nested:textarea rows="10" cols="110"
								styleClass="caja" property="oferta.informacion" /></td>
							<td></td>
						</tr>
						<tr>

							<td colspan="3"><img src="/GST/images/px.gif" width="1"
								height="10"></td>
						</tr>
					</table>
					</td>

				</tr>

				<tr>
					<td colspan="3"><img src="/GST/images/px.gif" width="1"
						height="10"></td>
				</tr>
				<tr>
					<td colspan="3" align="right"><nested:equal
						property="isModificable" value="true">
						<a href="javascript:validarCrearOferta();"><img
							src="/GST/images/bot_crear_nuevo.gif" hspace="5" border="0"></a>
					</nested:equal>
				</tr>
				<tr>
					<td colspan="3" align="right"><nested:equal
						property="isModificable" value="false">
						<a href="javascript:validarModifyOferta();"> <img
							src="/GST/images/bot_modificar.gif" hspace="5" border="0"></a>
					</nested:equal> <nested:equal property="isModificable" value="false">
						<a href="javascript:validarBorrarOferta();"> <img
							src="/GST/images/bot_borrar.gif" hspace="5" border="0"></a>
					</nested:equal></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
</BODY>
</html>
</html:html>
