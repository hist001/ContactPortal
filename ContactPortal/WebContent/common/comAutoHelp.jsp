<!--
	파 일 명  : comAutoHelp.jsp
	내    용  : 메뉴얼 자동 호출
-->
<%@ page contentType="text/html;charset=euc-kr" errorPage="/common/error.jsp"%>
<%@ page import="com.wms.fw.servlet.Box" %>
<%@ page import="com.wms.fw.servlet.HttpUtility" %>
<%@ page import="com.wms.fw.Utility"%>
<%
String MU_CD = request.getParameter("MU_CD");
String MU_TYPE =request.getParameter("MU_TYPE");
String MU_USER =request.getParameter("MU_USER");

if (MU_TYPE==null) MU_TYPE="";

%>
<html>
<script Language="JavaScript" src="/common/link/common.js"></script>
<script language='javascript'>
function init(){
	openManual(window.location.pathname,"<%=MU_CD%>","<%=MU_TYPE%>","<%=MU_USER%>");
}	
</script>
<body onload="init()">
<form >
</form>
</body>
</html>