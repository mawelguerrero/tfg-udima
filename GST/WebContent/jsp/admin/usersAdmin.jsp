<!-- Manuel Guerrero del Pino - Administración de Usuarios -->
<%@ page import="java.util.*,com.actions.*"%>
<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<html:html>

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

<!-------------------------Funciones javascript------------------------------------->

  function validarCrearUsuario(){
    if((document.forms[0].login.value == "") ||  (document.forms[0].password.value == "")
        || (document.forms[0].name.value == "")  ) {
      alert('Debe rellenar los campos obligatorios del formulario.');
    } else {
      for(i=document.forms[0].lista_perfiles_seleccionados.length-1; i>=0 ;i--) {
        document.forms[0].lista_perfiles_seleccionados.options[i].selected=true;
      }

      document.forms[0].submit();
    }
  }

<!--------------------------------------------------------------------------------->

  function validarModifyUsuario() {
    if(document.forms[0].nombre.value == "")   {
      alert('Debe rellenar los campos obligatorios del formulario.');
    } else {
      for(i=document.forms[0].lista_perfiles_seleccionados.length-1; i>=0 ;i--) {
        document.forms[0].lista_perfiles_seleccionados.options[i].selected=true;
      }
    document.forms[0].submit();
    }
  }
  
<!---------------------------------------------------------------------------------->

  function validarBorrarUsuario() {
    if(document.forms[0].name.value == ""){
      alert('Debe elegir un usuario existente.');
    } else {
      document.forms[0].submit();
    }
  }

<!---------------------------------------------------------------------------------->

  function cambiarAdmin(id){
    document.forms[0].operation.value='view_users';
    document.forms[0].submit();
  }

<!------------------------ Funciones para las listas -------------------------------->

  function validarFormularioCrear(){
    if(document.forms[0].profile[document.forms[0].profile.selectedIndex].value!="-1"){
      alert('Debe deseleccionar el perfil elegido antes de crear uno nuevo');
    } else {
      validarFormulario();
    }
  }

  function validarFormulario() {
    var lstSeleccionados = document.forms[0].lista_perfiles_seleccionados;
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
    var lstSeleccionados = document.forms[0].lista_perfiles_seleccionados;
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

      }else if((lstSeleccionados.length > 0)){
        for(i=lstSeleccionados.length-1; i>=0 ;i--) {
          lstSeleccionados.options[i].selected=true;
        }
        document.forms[0].submit();
      }else{
        alert('El perfil debe tener asociado al menos una opcion.');
      }
    }
  }


  function deselecciona(){
    var lstSeleccionados = document.forms[0].lista_perfiles_seleccionados;
    var lstDisponibles=document.forms[0].lista_perfiles_disponibles;
    if(lstSeleccionados!=null){
      for(i=lstSeleccionados.length-1; i>=0 ;i--) {
        lstSeleccionados.options[i].selected=false;
        lstDisponibles.options[i].selected=false;
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
    document.forms[0].pageEvent.value='change_combo_profile';
    document.forms[0].idProfile.value = document.forms[0].profile[document.forms[0].profile.selectedIndex].value;
    document.forms[0].submit();
  }

<!--------------------Fin funciones javascript------------------------------------------>

</script>

<%
	boolean ismodificable = false;
	String id_usuarios = "-1";
	try {
		id_usuarios = (String) request.getAttribute("idusuarios");
		if (id_usuarios != null && !id_usuarios.equals("-1"))
			ismodificable = true;
	} catch (Exception e) {
	}
%>

</HEAD>
<BODY marginwidth="0" marginheight="0" leftmargin="0"
	bgproperties="fixed">
<html:form action="/usersAdmin.do">

	<nested:hidden property="operation" />
	<nested:hidden property="id_usuario" value="<%=id_usuarios%>" />
	<!--         <nested:hidden property="seleccion"/>   -->

	<!---------------------tabla 1 de arriba--------------------------------------------->
	<!-------------------------fin tabla uno----------------------------------------->
	<!-----------------------tabla central ------------------------------------------>

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

					<td class="titular">Administración de Usuarios</td>
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
							<td class="textocampo">Nombre de Usuario</td>
							<td><img src="/GST/images/px.gif" width="1" height="1"></td>
						</tr>
						<tr>
							<td><html:select styleClass="caja" name="usersAdminForm"
								property="name" size="1" style="width:400"
								onchange="javascript:cambiarAdmin(id.value)">

								<html:options collection="usersTable" property="id"
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
					<td class="subseccion" colspan="3">Detalle del administrador</td>
				</tr>
				<tr>
					<td colspan="3"><img src="/GST/images/px.gif" width="1"
						height="10"></td>
				</tr>
				<tr>
					<td colspan="3">
					<table width="700" border="0" cellspacing="0" cellpadding="0">

						<tr>
							<td class="textocampo" width="165">Login<label
								class="textotitulo"> *</label></td>
						</tr>
						<tr>
							<td><html:text name="usersAdminForm" styleClass="caja"
								property="login" maxlength="15" style="width:135"
								disabled="<%=ismodificable%>" /></td>
						</tr>
						<tr>

							<td colspan="3"><img src="/GST/images/px.gif" width="1"
								height="10"></td>
						</tr>
						<tr>
							<td class="textocampo" valign="midddle" width="165">Contrase&ntilde;a<label
								class="textotitulo"> *</label></td>
						</tr>
						<tr>
							<td><html:password styleClass="caja" property="password"
								maxlength="15" style="width:135" disabled="<%=ismodificable%>" />
							</td>
						</tr>
						<tr>
							<td colspan="3"><img src="/GST/images/px.gif" width="1"
								height="10"></td>
						</tr>
						<tr>
							<td colspan="2" class="textocampo">Nombre y Apellidos<label
								class="textotitulo"> *</label></td>
							<td></td>
						</tr>
						<tr>
							<td colspan="2"><html:text styleClass="caja"
								property="nombre" maxlength="60" style="width:350" /></td>
							<td></td>
						</tr>
						<tr>

							<td colspan="3"><img src="/GST/images/px.gif" width="1"
								height="10"></td>
						</tr>
						<tr>
							<td colspan="2" class="textocampo">Correo electrónico</td>
							<td></td>
						</tr>
						<tr>
							<td colspan="2" class="vacio"><html:text styleClass="caja"
								property="mail" maxlength="40" style="width:350" /></td>
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
					<td colspan="3">
					<table width="700" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="textocampo" width="250">Perfiles disponibles</td>
							<td class="vacio" width="200">&nbsp;</td>
							<td class="textocampo" width="250">Perfiles seleccionadas *</td>
						</tr>

						<tr>
							<td class="vacio" valign="top"><html:select
								property="lista_perfiles_disponibles" styleClass="caja" size="5"
								style="width:250" multiple="true">
								<html:options collection="perfilesAvailableTable" property="id"
									labelProperty="description" />
							</html:select></td>
							<td class="vacio" align="center"><a
								href="javascript:moverElementosLista(document.forms[0].lista_perfiles_disponibles, document.forms[0].lista_perfiles_seleccionados)"><img
								src="/GST/images/flecha_right.gif" alt="Deseleccionar"
								border="0"></a> <br>
							<a
								href="javascript:moverElementosLista(document.forms[0].lista_perfiles_seleccionados, document.forms[0].lista_perfiles_disponibles)"><img
								src="/GST/images/flecha_left.gif" alt="Seleccionar" border="0"></a></td>
							<td class="vacio"><html:select
								property="lista_perfiles_seleccionados" size="5"
								styleClass="caja" style="width:250" multiple="true">
								<html:options collection="perfilesSelectedTable" property="id"
									labelProperty="description" />
							</html:select></td>
						</tr>
						<tr>
							<td colspan="3"><img src="/GST/images/px.gif" width="1"
								height="10"></td>
						</tr>
						<tr>
							<td colspan="3" align="right"><logic:equal
								name="usersAdminForm" property="isModificable" value="true">
								<a
									href="javascript:document.forms[0].operation.value='create_user';validarCrearUsuario();"><img
									src="/GST/images/bot_crear_nuevo.gif" hspace="5" border="0"></a>
							</logic:equal>
						<tr>
							<td colspan="3" align="right"><logic:equal
								name="usersAdminForm" property="isModificable" value="false">
								<a
									href="javascript:document.forms[0].operation.value='mod_user';validarModifyUsuario();">
								<img src="/GST/images/bot_modificar.gif" hspace="5" border="0"></a>
							</logic:equal> <logic:equal name="usersAdminForm" property="isModificable"
								value="false">
								<a
									href="javascript:document.forms[0].operation.value='delete_user';validarBorrarUsuario();">
								<img src="/GST/images/bot_borrar.gif" hspace="5" border="0"></a>
							</logic:equal></td>
						</tr>

					</table>

					<logic:present name="errores">
						<table width="740">
							<tr align="center">
								<td>Error de validación</td>
								<html:errors />
							</tr>
						</table>
					</logic:present> <!-----------------------fin tabla central de la foto------------------------------------------>
					</html:form>
</BODY>

</html:html>

