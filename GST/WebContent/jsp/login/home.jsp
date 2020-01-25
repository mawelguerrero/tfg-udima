<!-- Manuel Guerrero del Pino - Base de trabajo -->
<%@ page import="com.util.Constantes,com.dto.UserDTO"%>
<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page contentType="text/html; charset=iso-8859-1"%>
<html:html>
<head>
<title><%=Constantes.APP_NAME%></title>
<META http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<META http-equiv="Pragma" content="no-cache">
<META http-equiv="Expires" content="0">
<META http-equiv="Cache-Control" content="no-cache">
</head>

<link href="/GST/css/style.css" rel="stylesheet" type="text/css">
<style type="text/css">
</style>


<body marginwidth="20" marginheight="20" leftmargin="20">

<%
			UserDTO usr = (UserDTO) request.getSession().getAttribute(
			Constantes.SES_USER_KEY);
%>
<html:form action="/login.do" method="POST">
	<html:hidden property="pageEvent" />

	<%-- <b>Usuario: </b><bean:write name="homeForm" property="userName"/> --%>
	<%-- <b><bean:write name="homeForm" property="platformName"/>:</b> (<bean:write name="homeForm" property="platformCode"/>) --%>

	<%-- --------------------tabla central ---------------------------------------- --%>
	<table width="550" border="0" cellspacing="0" cellpadding="0">

		<tr>
			<td colspan="2"><img src="/GST/images/px.gif" width="1"
				height="30"></td>
		</tr>
		<tr>
			<td><img src="/GST/images/bienvenidos.gif" alt="Bienvenidos"
				width="114" height="20"></td>
			<td align="right" valign="top">
		<tr>
			<td colspan="2" class="titular"><strong>Bienvenid@</strong>
			<p>
			</td>
		</tr>
		<tr>
			<td rowspan="2" align="left"><img border="0"
				src="/GST/images/home.gif"></td>
		</tr>
		<tr>
			<td align="center" valign="bottom">
		</tr>
	</table>
	<br>
</html:form>
</body>

</html:html>
