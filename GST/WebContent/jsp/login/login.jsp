<!-- Manuel Guerrero del Pino - Menú de la APP -->
<%@ page import="com.util.*,com.util.Constantes"%>
<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page contentType="text/html; charset=iso-8859-1"%>
<html:html>
	
	<HEAD>
	<TITLE><%=Constantes.APP_NAME%></TITLE>
	<META http-equiv=Content-Type content="text/html; charset=windows-1252">
	<!-- Fireworks 4.0Dreamweaver 4.0 target.Created Thu Oct 11 16:45:12 GMT+0200 (Hora de verano romance) 2001-->
	<link href="/GST/css/style.css" rel="stylesheet" type="text/css">
	<META content="MSHTML 6.00.2600.0" name=GENERATOR>
	
	<script language="JavaScript">
	
	    function doSubmit(e){
	      var numKey=-1;
	      //var numKey=window.event.keyCode;   //No compatible con Mozilla
	
	      if(window.event) {
	        // Estamos en IE, se puede utilizar keyCode
	        numKey = e.keyCode;
	      } else if(e.which) {
	        // Mozilla
	        numKey = e.which;
	      }
	
	      if (numKey==13){
	        login();
	      }
	    }
	
	    function login() {
	      if(document.forms[0].user.value == ''
	      || document.forms[0].password.value == ''){
	
	      	alert('Debe rellenar todos los campos');
	        
	      }
	      else{
	        document.forms[0].submit();
	      }
	    }
	  </script>
	
	
	
	</HEAD>
	<BODY bgColor=#ffffff topMargin=20
		onLoad="document.forms[0].password.value=''">
		<html:form action="/login.do" method="POST" focus="user">
			<html:hidden property="pageEvent" />
			<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
				<TR>
					<TD vAlign=center align=middle>
					<table cellspacing=0 cellpadding=0 width="85%" align=center border=0>
						<tr>
							<td valign=top background="/GST/images/f_sup.gif" colspan=2><img
								height=10 src="/GST/images/e_sup_izq.gif" width=10></td>
							<td background="/GST/images/f_sup.gif"><img height=10
								src="/GST/images/px.gif" width=96></td>
							<td background="/GST/images/f_sup.gif"><img height=10
								src="/GST/images/px.gif" width=200></td>
							<td background="/GST/images/f_sup.gif"><img height=10
								src="/GST/images/px.gif" width=350></td>
							<td valign=top align=right background="/GST/images/f_sup.gif"
								colspan=2><img height=10 src="/GST/images/e_sup_dcha.gif"
								width=10></td>
						</tr>
						<tr>
							<td class="linea2"><img height=1 src="/GST/images/px.gif"
								width=1></td>
							<td><img height=24 src="/GST/images/px.gif" width=34></td>
							<td><img src="/GST/images/icono.jpg" alt="logo" vspace="10"></td>
							<td colspan="2" align="center" valign="bottom"><img
								src="/GST/images/SGST.jpg" alt="extranet" vspace="10"
								distribuidores></td>
							<td><img height=1 src="/GST/images/px.gif" width=34></td>
							<td class="linea2"><img height=1 src="/GST/images/px.gif"
								width=1></td>
						</tr>
		
						<tr>
							<td class="linea2" colspan=7 height=1><img height=1
								src="/GST/images/px.gif" width=1></td>
						</tr>
						<tr>
							<td class="linea2" rowspan=5><img height=1
								src="/GST/images/px.gif" width=1></td>
							<td rowspan=5>&nbsp;</td>
							<td colspan=3><img height=20 src="/GST/images/px.gif" width=1></td>
							<td rowspan=5>&nbsp;</td>
							<td class="linea2" rowspan=5><img height=1
								src="/GST/images/px.gif" width=1></td>
						</tr>
						<tr>
							<td align=right colspan=2 class="textocampo">Usuario&nbsp;&nbsp;</td>
							<td><html:text property="user" styleClass="caja"
								onkeypress="doSubmit(event);" style="width:110" maxlength="15" /></td>
		
						</tr>
						<tr>
							<td colspan=3><img height=10 src="/GST/images/px.gif" width=1></td>
						</tr>
						<tr>
							<td align=right colspan=2 class="textocampo">Contraseña&nbsp;&nbsp;</td>
							<td><html:password property="password" styleClass="caja"
								onkeypress="doSubmit(event);" style="width:110" maxlength="15" /></td>
						</tr>
						<tr>
							<td colspan=2>&nbsp;</td>
							<td><a href="javascript:document.forms[0].submit();"><img
								src="/GST/images/b_entrar.gif" alt=entrar width=110 height=19
								vspace="5" border=0></a></td>
						</tr>
						<tr>
							<td class="linea2"><img height=1 src="/GST/images/px.gif"
								width=1></td>
							<td colspan="5" class="errorlogin" bgcolor="ffffff" align="center"
								height="20"><logic:present name="errors">
								<table width="940">
									<tr align="center">
										<html:errors />
									</tr>
								</table>
							</logic:present></td>
							<td class="linea2"><img height=1 src="/GST/images/px.gif"
								width=1></td>
						</tr>
						<tr>
							<td class="linea2"><img height=1 src="/GST/images/px.gif"
								width=1></td>
							<td bgcolor=#ffffff colspan=5><img src="/GST/images/px.gif"
								width=15 height="20"></td>
							<td class="linea2"><img height=1 src="/GST/images/px.gif"
								width=1></td>
						</tr>
						<tr>
							<td class="linea2"><img height=1 src="/GST/images/px.gif"
								width=1></td>
							<td background="/GST/images/ini.jpg" colspan=5><img height=200
								src="/GST/images/px.gif" " width=1></td>
							<td class="linea2"><img height=1 src="/GST/images/px.gif"
								width=1></td>
						</tr>
						<tr>
							<td background="/GST/images/f_inf.gif" colspan=2><img height=6
								src="/GST/images/e_inf_izq.gif" width=10></td>
							<td background="/GST/images/f_inf.gif" colspan=3><img height=6
								src="/GST/images/px.gif" width=1></td>
							<td align=right background="/GST/images/f_inf.gif" colspan=2><img
								height=6 src="/GST/images/e_inf_dcha.gif" width=10></td>
						</tr>
					</table>
					</TD>
				</TR>
			</TABLE>
		</html:form>
	</BODY>
</html:html>