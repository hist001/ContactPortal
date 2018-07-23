<!--
	파 일 명  : comHelp.jsp
	내    용  : 공통 메뉴얼 호출
-->
<%@ page contentType="text/html;charset=euc-kr" errorPage="/common/error.jsp"%>
<%@ page import="com.wms.fw.servlet.Box" %>
<%@ page import="com.wms.fw.servlet.HttpUtility" %>
<%@ page import="com.wms.fw.Utility"%>
<%
String MU_CD = request.getParameter("MU_CD");
String MU_TYPE =request.getParameter("MU_TYPE");

if (MU_TYPE==null) 	MU_TYPE="10000000338";

%>
<table  border="0" cellpadding="0" cellspacing="0">
<% if( MU_CD==null){%>
 <tr onClick='openManual(window.location.pathname,"","<%=MU_TYPE%>");'>
 <%}else{ %>
 <tr onClick='openManual("","<%=MU_CD%>","<%=MU_TYPE%>");'>
 <%} %>
	<td style='cursor:hand;font-weight:bold;vertical-align:middle' width='5' >
	<img src='/images/manual/help.gif'>
	</td>		
	<td style='cursor:hand;font-weight:bold;vertical-align:middle' width='20' >Help&nbsp;
	</td>	
	</tr>
</table>