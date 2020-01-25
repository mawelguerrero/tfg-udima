<!-- Manuel Guerrero del Pino - Menú de la APP -->
<%@ page import="java.util.*"%>
<%@ page import="com.dto.UserDTO"%>
<%@ page import="com.util.Constantes"%>
<%@ page import="com.util.admin.Options"%>
<%@ page import="com.util.Option"%>
<%@ page import="com.util.*"%>

<html>
<head>
<title><%=Constantes.APP_NAME%></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<META http-equiv="Pragma" content="no-cache">
<META http-equiv="Expires" content="0">
<META http-equiv="Cache-Control" content="no-cache">
<link rel="stylesheet" type="text/css" href="/GST/css/style.css">

<script language="javascript" src="/GST/js/menu.js"></script>
<script>

function ocultarbarra(){

  this.window.status='';
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
function changeLogin(action){
     parent.contenido.location.href=action;
}
</script>
</head>
<%
	request.getSession(false);

	UserDTO oUser = new UserDTO();
	oUser = (UserDTO) request.getSession().getAttribute(
			Constantes.SES_USER_KEY);
	String strUser = oUser.getIdUser();
	Options objOptions = new Options();
	ArrayList hsOpciones = objOptions.getOpcionesMenuNEW(strUser);
%>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0"
	marginwidth="0" marginheight="0">

<table width="220" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="220">
		<div id="divCont">
		<%
			ListIterator iter = hsOpciones.listIterator();
			int cont = -1;
			int levelAnt = 0;
			int nivel = 0;

			//Recorro el arrayList de Opciones
			while (iter.hasNext()) {
				Option opcion = (Option) iter.next();
				nivel = Integer.parseInt(opcion.getNivel());

				//Si la opcion es de nivel 1, pinto pinto un padre (desplegable en naranja)
				if (nivel == 1) {
					cont++;
					String strMen = "divTop" + cont;
					levelAnt = 1;
		%>
		<div id="<%=strMen%>" class="clTop">
		<table width="210" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><a class="menuf" href="#"
					onclick="menu(<%=cont%>); return false" onfocus="this.blur()"><%=opcion.getDescription()%></a></td>
			</tr>
		</table>
		<%
					} else {
					//Si el nivel es distinto de 1 pinto un hijo (en gris)
					String strMen1 = "divSub" + cont;

					//Si es el primer dato pongo la cabecera de los hijos
					if (levelAnt == 1) {
				levelAnt = nivel;
		%>
		<div id="<%=strMen1%>" class="clSub">
		<table width="210" border="0" cellspacing="0" cellpadding="0">
			<%
						}

						//Pinto el contenido del hijo
			%>
			<tr>
				<td class="itemMenu"><img src="/GST/images/px.gif"
					width="<%=5*(nivel - 1)%>" height="8" border="0"> <%
 			String descmenu = opcion.getUrl().substring(0,
 			opcion.getUrl().length() - 2)
 			+ "&descmenu=" + opcion.getDescription() + "')";
 %> <a href="#"
					onclick="javascript:loadCenterAction('<%=opcion.getUrl()%>');return false;"><%=opcion.getDescription()%></a>

				</td>

			</tr>
			<%
					}

					// comprobamos el siguiente para cerrar las tablas y los div
					if (iter.hasNext()) {
						Option opcionNext = (Option) iter.next();
						iter.previous();
						if (opcionNext.getNivel().equals("1")) {

					//Si el siguiente es un padre cierro el desplegable
			%>
		</table>
		</div>
		</div>
		<%
					}
					//Si no tenemos next, tambien cerramos el desplegable porque es el final de las opciones
				} else {
		%>
		
</table>

<%
	}
	}//Fin del while
%>


<script language="javascript">
<!--
FoldNumber = <%=cont+1%>;
if(bw.bw) onload = initFoldout;
//-->
</script>
<td bgcolor="#D6D6D6"><img border="0" src="/GST/images/px.gif"
	width="1" height="400"></td>


</body>


</html>
