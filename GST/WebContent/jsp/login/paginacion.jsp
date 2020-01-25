<!-- Manuel Guerrero del Pino - Paginación de Listados -->
<tr>
	<td class="vacio" nowrap>
	<%
	if (totalPages > 1) {
	%>
	<table border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="vacio">
			<%
			if (thisPage != 1) {
			%> <a href="javascript:previous()"><img
				src="/GST/images/flecha_left.gif" border="0"></a> <%
 } else {
 %> <img
				src="/GST/images/px.gif" width="16" height="1"> <%
 }
 %>
			</td>
			<td><img src="/GST/images/px.gif" width="5" height="1"></td>
			<td class="textopagin"><%=thisPage%>ª p&aacute;gina de <%=totalPages%></td>
			<td><img src="/GST/images/px.gif" width="5" height="1"></td>
			<td class="vacio">
			<%
			if (thisPage != totalPages) {
			%> <a href="javascript:next()"><img
				src="/GST/images/flecha_right.gif" border="0"></a> <%
 } else {
 %> <img
				src="/GST/images/px.gif" width="16" height="1"> <%
 }
 %>
			</td>
		</tr>
	</table>
	<%
	}
	%>
	</td>
</tr>
