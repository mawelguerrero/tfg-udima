<!-- Manuel Guerrero del Pino - FrameSet que separa APP Principal -->
<%@ page import="com.util.Constantes,com.util.*"%>
<html>
<head>
<title><%=Constantes.APP_NAME%></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%
double rnd = Math.random();
%>
</head>
<frameset rows="0,120,*" frameborder="NO" border="0" framespacing="0">
	<frame name="oculto" scrolling="NO" noresize src="">
	<frame name="cabecera" scrolling="NO" noresize
		src="/GST/jsp/login/cabecera.jsp">
	<frameset cols="235,545*" frameborder="NO" border="0" framespacing="0">
		<frame name="menu" scrolling="NO" noresize
			src="/GST/jsp/login/menu.jsp" noresize>
		<frame name="contenido" scrolling="auto" src="/GST/jsp/login/home.jsp">
		<frame src="/GST/html/vacio.htm" name="vacio" scrolling="NO" noresize>
	</frameset>
</frameset>

<noframes>
<body bgcolor="#FFFFFF" text="#000000">

Su navegador no soporta frames
</body>
</noframes>
</html>
