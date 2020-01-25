<!-- Manuel Guerrero del Pino - Calendario -->
<%@page import="com.util.*"%>
<%@page import="com.util.Constantes"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>

<link rel="stylesheet" type="text/css" media="all"
	href="/GST/css/calendar.css" title="winter" />
<script type="text/javascript" src="/GST/js/calendar.js"></script>

<%
		try {

		String value = request.getParameter("value");
		if (value == null) {
			value = "";
		}

		String inputId = request.getParameter("inputid");
		if (inputId == null) {
			inputId = "date0";
		}

		String readonly = "readonly";
		if (request.getParameter("readonly") != null) {
			readonly = "readonly";
		}

		boolean deleteAllowed = true;
		if (request.getParameter("nodelete") != null) {
			deleteAllowed = false;
		}

		String onchange = request.getParameter("onchange");
		if (onchange == null)
			onchange = "";
		else
			onchange = ("onchange=javascript:" + onchange + "('"
			+ inputId + "');");

		String htmlClass = request.getParameter("class");
		if (htmlClass == null) {
			htmlClass = "";
		} else {
			htmlClass = "class=" + htmlClass;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		String startDate = request.getParameter("startdate");
		String finalDate = request.getParameter("finaldate");

		if (startDate == null) {
			startDate = Constantes.CAL_START;
		}
		if (finalDate == null) {
			finalDate = Constantes.CAL_FINAL;
		}

		if (finalDate != null) {
			//Hay que sumar un día por compatibilidad con el calendario
			Calendar cal = Calendar.getInstance();
			cal.setTime(sdf.parse(finalDate));
			cal.add(Calendar.DAY_OF_MONTH, 1);
			finalDate = sdf.format(cal.getTime());
		}

		boolean isCalRestricted = false;
		String disableFunction = "null";

		if (startDate != null || finalDate != null) {
			disableFunction = "disable" + inputId;
			isCalRestricted = true;
		}

		if (isCalRestricted) {
%>

<script>
function <%=disableFunction%>(calDate)
{

<%
  }
  //Limite inferior de fechas
  if(startDate!=null)
  {
    Date d1 = sdf.parse(startDate);
%>

  var startDate = new Date(<%=d1.getTime()%>);
  if(calDate.getTime()<startDate.getTime())
  {
    return true;
  }


<%
  }

  //Limite superior de fechas
  if(finalDate != null)
  {
   Date d2 = sdf.parse(finalDate);
%>
  var finalDate = new Date(<%=d2.getTime()%>);
  if(calDate.getTime()>=finalDate.getTime()){
    return true;
  }

<%
  }

  if(isCalRestricted){ //Cerramos los parentesis de la funcion JS
%>
}
</script>
<%
}
%>


<input type="text" name="<%=inputId%>" id="<%=inputId%>"
	value="<%=value%>" size="12" maxlength="10" <%=htmlClass%>
	<%=readonly%> <%= onchange%>>
<a href=#
	onClick="return showCalendar('<%=inputId%>', 'dd/mm/y', <%=disableFunction%>);"><img
	src="/GST/images/calendario2.gif" border="0" align="absbottom"></a>
<%
if (deleteAllowed) {
%>
<a href=# onClick="document.forms['0'].<%=inputId%>.value=''"><img
	src="/GST/images/boton_borrar_p.gif" border="0" align="absbottom"></a>
<%
}
%>

<%
		} catch (Exception ex) {
		ex.printStackTrace();
	}
%>
