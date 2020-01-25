<!-- Manuel Guerrero del Pino - Cambio de Login -->
<%@ page import="com.util.*,com.util.Constantes"%>
<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page contentType="text/html; charset=iso-8859-1"%>
<html:html>
<head>
<title><%=Constantes.APP_NAME%></title>
<META http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<META http-equiv="Pragma" content="no-cache">
<META http-equiv="Expires" content="0">
<META http-equiv="Cache-Control" content="no-cache">
<SCRIPT language="JavaScript" src="/GST/js/validate.js"></SCRIPT>
<link href="/GST/css/style.css" rel="stylesheet" type="text/css">

<script languaje="JavaScript">

  function doSubmit(){
     var numKey=window.event.keyCode;
     if (numKey==13){
       login();
     }
   }

    function login() {

      if(document.forms[0].user.value == ''
      || document.forms[0].passwordForm.value == ''
      || document.forms[0].newPassword.value == ''
      || document.forms[0].confirmNewPassword.value == ''){

      	alert('Debe rellenar todos los campos');
        return false;
      }
      else{
        document.forms[0].pageEvent.value = 'iniciado';
        document.forms[0].submit();
      }
    }

    function limpiarPass() {
      document.forms[0].passwordForm.value == '';
      document.forms[0].newPassword.value == '';
      document.forms[0].confirmNewPassword.value == '';
    }
  </script>
</head>
<body leftmargin="0px" topmargin="0px" marginwidth="0px"
	marginheight="20px" bgcolor="#FFFFFF" onLoad="limpiarPass();">

<html:form action="/changeLogin.do" method="POST" focus="user">
	<html:hidden property="pageEvent" />

	<table width="100%" border="0" cellspacing="0" cellpadding="2"
		align="left" valign="top">
		<tr>
			<td class="titular">Cambio de contrase&ntilde;a de usuario</td>
		</tr>
		<tr>
			<td><img src="/GST/images/px.gif" width="1" height="5"></td>
		</tr>
		<html:errors />
		<tr>
			<td class="textotitulo">Debe introducir usuario y
			contrase&ntilde;a , dos veces la nueva y pulse el bot&oacute;n
			Modificar</td>
		</tr>
		<tr>
			<td align="center"><img src="/GST/images/px.gif" width="1"
				height="5"></td>
		</tr>
		<tr>
			<td align="left">
			<table border="0" cellspacing="0" cellpadding="3" width="700px">
				<tr>
					<td class="textocampo">Usuario:<label class="textotitulo"></label></td>
				<tr>
					<td><html:text property="user" styleClass="caja"
						onkeypress="doSubmit();" style="width:110" maxlength="15" /></td>
				<tr>
					<td class="textocampo">Contrase&ntilde;a&nbsp;:<label
						class="textotitulo"></label></td>

				</tr>
				<tr>
					<td><html:password property="passwordForm" styleClass="caja"
						onkeypress="doSubmit();" style="width:110" maxlength="15" /></td>

				</tr>
				<tr>
					<td class="textocampo">Nueva contrase&ntilde;a&nbsp;:<label
						class="textotitulo"></label></td>
				<tr>
					<td><html:password property="newPassword" styleClass="caja"
						onkeypress="doSubmit();" style="width:110" maxlength="15" /></td>
				<tr>
					<td class="textocampo">Confirmar contrase&ntilde;a&nbsp;:<label
						class="textotitulo"></label></td>

				</tr>
				<tr>
					<td><html:password property="confirmNewPassword"
						styleClass="caja" onkeypress="doSubmit();" style="width:110"
						maxlength="15" /></td>
				</tr>
				<tr>

					<td align="right"><a href="#"
						onClick="javascript:login();return false;"><img
						src="/GST/images/bot_modificar.gif" hspace="5" border="0"></a></td>
				</tr>

			</table>

			</html:form>
</BODY>
</html:html>
