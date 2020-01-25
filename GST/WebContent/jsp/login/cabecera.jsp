<!-- Manuel Guerrero del Pino - Cabecera de la APP -->
<%@ page import="com.util.*"%>
<%@ page language="java"%>
<%@ page import="com.dto.UserDTO"%>
<%@ page import="com.util.Constantes"%>

<%
	UserDTO oUser = new UserDTO();
	oUser = (UserDTO) request.getSession().getAttribute(
			Constantes.SES_USER_KEY);
	String usuName = oUser.getName();
%>
<html>
<head>
<title><%=Constantes.APP_NAME%></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<META HTTP-EQUIV="cache-control" CONTENT="must-revalidate">
<META HTTP-EQUIV="cache-control" CONTENT="private">
<META HTTP-EQUIV="cache-control" CONTENT="no-cache">
<META HTTP-EQUIV="cache-control" CONTENT="max-age=0">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<link rel="stylesheet" type="text/css" href="/GST/css/style.css">

<script language="javascript" src="/GST/js/menu.js"></script>
<script language="JavaScript">
function CheckUserBox(box){

        var img1=document.getElementById('img_ult_hora');
        var link1=document.getElementById('link_ult_hora');
        if (box) {
            img1.src='images/mensaje2.gif';
            link1.href='XNETServlet?actionID=DisplayUserBox&borrarSesion=S&buzon=2&nuevo=0'
        } else {
            img1.src='images/pixel.gif';
            link1.href='javascript:void(0);'
        }

}
function chequeobrowser(){
        this.ver=navigator.appVersion
        this.agent=navigator.userAgent
        this.dom=document.getElementById?1:0
        this.ie5=(this.ver.indexOf("MSIE 5")>-1 && this.dom && !this.opera5)?1:0;
        this.ie6=(this.ver.indexOf("MSIE 6")>-1 && this.dom && !this.opera5)?1:0;
        this.ie4=(document.all && !this.dom && !this.opera5)?1:0;
        this.ns6=(this.dom && parseInt(this.ver) >= 5) ?1:0;
        this.ns4=(document.layers && !this.dom)?1:0;
        return this
}

var mibrowser = new chequeobrowser();
var recargamenu = false;


function MM_reloadPage(init) {
      //reloads the window if Nav4 resized
      if (init==true) with (navigator) {if ((appName=="Netscape")&&(parseInt(appVersion)==4)) {
        document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }}
      else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();


MM_reloadPage(true);
}

  function logout(){

   parent.location.href='/GST/login.do';
}

function loadCenterAction(action){

    var rnd;
    if (action.indexOf('?') != -1) {
        rnd='&rnd='+Math.random();
    } else {
        rnd='?rnd='+Math.random();
    }
     parent.contenido.location.href='/GST'+action+rnd;
}

//-->
</script>
<style>
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-color: #FFFFFF;
}
</style>
</head>
<body>
<table width="940" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td rowspan="2"><img src="/GST/images/sgst_mini.jpg"></td>
		<td align="right" valign="top"><!-----------------------tabla de botonera superior------------------------->
		<table width="520" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><img src="/GST/images/px.gif" width="1" height="15"></td>
			</tr>
			<tr>
				<td bgcolor="D3D4D6"><img src="/GST/images/px.gif" width="1"
					height="1"></td>
			</tr>
			<tr>
				<td align="right" class="vacio"><a href="#"
					onclick="javascript:logout();" class="menucabecera">desconectar</a>
				<img src="/GST/images/barra_gris_vertical.gif" hspace="5"> <a
					href="#"
					onclick="javascript:loadCenterAction('/jsp/login/home.jsp');return false;"
					target="contenido" class="menucabecera">inicio</a> <img
					src="/GST/images/barra_gris_vertical.gif" hspace="5"> <a
					href="#"
					onclick="javascript:loadCenterAction('/changeLogin.do');return false;"
					target="contenido" class="menucabecera">cambiar
				contraseña&nbsp;&nbsp;</a> <img
					src="/GST/images/barra_gris_vertical.gif"></td>
			</tr>
		</table>
		<!-----------------------fin tabla botonera superior----------------------->
		</td>
	</tr>
	<tr>
		<td valign="bottom">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td class="textotitulo" valign="bottom"><img
					src="/GST/images/px.gif" width="0" height="1"><%=usuName%></td>
				<td class="nombreusuario" valign="bottom"><img
					src="/GST/images/px.gif" width="5" height="1"></td>
				<td align="right" valign="bottom" class="fechacabecera"
					valign="bottom" nowrap="nowrap"><%=Validations.obtenerFechaActual()%>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</body>
</html>
