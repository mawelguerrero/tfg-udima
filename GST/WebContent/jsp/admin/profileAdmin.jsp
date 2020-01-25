<!-- Manuel Guerrero del Pino - Administración de Roles -->
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
    function validarFormularioCrear(){
      if(document.forms[0].profile[document.forms[0].profile.selectedIndex].value!="-1"){
        alert('Debe deseleccionar el perfil elegido antes de crear uno nuevo');
      } else {
        validarFormulario();
      }
    }

    function validarFormulario(){
      var lstSeleccionados = document.forms[0].lista_seleccionados;
      if((document.forms[0].name.value == "")||(lstSeleccionados.length==0)) {
        alert('Debe introducir un nombre de perfil y al menos una opcion.');
      } else {
        for(i=lstSeleccionados.length-1; i>=0 ;i--) {
          lstSeleccionados.options[i].selected=true;
        }
        document.forms[0].submit();
      }
    }


    function validaPerfilSeleccionado(deleteProf) {
      var lstSeleccionados = document.forms[0].lista_seleccionados;
      if((document.forms[0].idProfile.value == "")
          ||(document.forms[0].name.value == "")){
          <!--    ||(document.forms[0].profile[document.forms[0].profile.selectedIndex].value=="-")) {-->
        alert('Seleccione antes un perfil existente.');
      } else {
        var iContador = 0;
        if(deleteProf){
          for(i=lstSeleccionados.length-1; i>=0 ;i--) {
            lstSeleccionados.options[i].selected=true;
          }
          document.forms[0].submit();
        }else if((lstSeleccionados.length > 0)) {
          for(i=lstSeleccionados.length-1; i>=0 ;i--) {
            lstSeleccionados.options[i].selected=true;
          }
          document.forms[0].submit();
        }else{
          alert('El perfil debe tener asociado al menos una opcion.');
        }
      }
    }


    function deselecciona() {
      var lstSeleccionados = document.forms[0].lista_seleccionados;
      if(lstSeleccionados!=null){
        for(i=lstSeleccionados.length-1; i>=0 ;i--) {
          lstSeleccionados.options[i].selected=false;
        }
      }
    }


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

    function changeProfile(){
      document.forms[0].idProfile.value = document.forms[0].profile[document.forms[0].profile.selectedIndex].value;
      if(document.forms[0].idProfile.value=='-1')
        document.forms[0].pageEvent.value='';
      else
        document.forms[0].pageEvent.value='change_combo_profile';
      document.forms[0].submit();
    }
  </script>

</HEAD>
<BODY marginwidth="0" marginheight="0" leftmargin="0"
	bgproperties="fixed" onload="deselecciona()">
<html:form action="/profileAdmin.do">

	<nested:hidden property="pageEvent" />
	<nested:hidden property="idProfile" />
	<nested:hidden property="seleccion" />

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

					<td class="titular">Gesti&oacute;n de Perfiles</td>
					<td rowspan="2" align="right" valign="top"><img
						src="/GST/images/px.gif" width="115" height="74"></td>
				</tr>
				<html:errors />
				<tr>
					<td valign="bottom"><!-----------------------------tabla de los combos--------------------------->
					<table width="700" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td><img src="/GST/images/px.gif" width="140" height="1"></td>
							<td><img src="/GST/images/px.gif" width="20" height="10"></td>
						</tr>
						<tr>
							<td class="textocampo">Perfiles</td>
							<td><img src="/GST/images/px.gif" width="1" height="1"></td>
						</tr>
						<tr>
							<td><html:select styleClass="caja" property="profile"
								size="1" style="width:290"
								onchange="javascript:changeProfile();">

								<html:options collection="profileTable" property="id"
									labelProperty="description" />
							</html:select></td>
						</tr>
						<tr>
							<td colspan="3"><img src="/GST/images/px.gif" width="140"
								height="5"></td>
						</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td class="linea" colspan="2"><img src="/GST/images/px.gif"
						width="1" height="1"></td>
				</tr>
			</table>
			<!-------------fin tabla central--------------> <!--------------tabla de resultados-------------------->


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
					<td class="subseccion" colspan="8">Detalle del Perfíl</td>
				</tr>
				<tr>
					<td colspan="3"><img src="/GST/images/px.gif" width="10"
						height="20"></td>
				</tr>
				<tr>
					<td class="textocampo" width="210">Nombre del Perfil <label
						class="textotitulo"> *</label></td>
				</tr>
				<tr>
					<td width="210"><html:text styleClass="caja" property="name"
						maxlength="30" style="width:290" /></td>
				</tr>
				<tr>
					<td colspan="8"><img src="/GST/images/px.gif" width="1"
						height="20"></td>
				</tr>
				<tr>
					<td colspan="2" class="textocampo" width="250">Opciones
					disponibles</td>
					<td class="vacio" width="200"></td>
					<td class="textocampo" width="250">Opciones seleccionadas *</td>
				</tr>

				<tr>
					<td colspan="2" class="vacio" valign="top" width="290"><html:select
						property="lista_disponibles" styleClass="caja" size="5"
						style="width:290" multiple="true">
						<html:options collection="profileAvailableTable" property="id"
							labelProperty="description" />
					</html:select></td>

					<td class="vacio" align="center" width="200"><a
						href="javascript:moverElementosLista(document.forms[0].lista_disponibles, document.forms[0].lista_seleccionados)"><img
						src="/GST/images/flecha_right.gif" alt="Deseleccionar" border="0"></a>
					<br>
					<a
						href="javascript:moverElementosLista(document.forms[0].lista_seleccionados, document.forms[0].lista_disponibles)"><img
						src="/GST/images/flecha_left.gif" alt="Seleccionar" border="0"></a></td>

					<td class="vacio" align="right" width="290"><html:select
						property="lista_seleccionados" size="5" styleClass="caja"
						style="width:290" multiple="true">
						<html:options collection="profileSelectedTable" property="id"
							labelProperty="description" />
					</html:select></td>
				</tr>
				<tr>
					<td colspan="3"><img src="/GST/images/px.gif" width="1"
						height="20"></td>
				</tr>
				<tr>
					<td colspan="8" align="right"><logic:notEqual
						name="profileAdminForm" property="name" value="">
						<a
							href="javascript: document.forms[0].pageEvent.value='mod_profile';validaPerfilSeleccionado(false);"><img
							src="/GST/images/bot_modificar.gif" hspace="5" border="0"></a>
						<a
							href="javascript: document.forms[0].pageEvent.value='delete_profile';validaPerfilSeleccionado(true);"><img
							src="/GST/images/bot_borrar.gif" hspace="5" border="0"></a>

					</logic:notEqual> <logic:equal name="profileAdminForm" property="name" value="">
						<a
							href="javascript: document.forms[0].pageEvent.value='create_profile';validarFormularioCrear();"><img
							src="/GST/images/bot_crear_nuevo.gif" hspace="5" border="0"></a>
					</logic:equal></td>
				</tr>


			</table>

			</td>
		</tr>


	</table>
</html:form>
</BODY>
</html>
</html:html>
