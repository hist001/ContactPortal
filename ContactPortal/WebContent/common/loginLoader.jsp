<!--
	파 일 명  : loginLoader.jsp
	작성일자  : 2009/10/20
	작 성 자  : mailbest
	내    용  : 로그인 처리
-->
<%@ page language="java" contentType="text/html; charset=EUC-KR"  pageEncoding="EUC-KR" errorPage="/common/error.jsp"%>
<%@ page import="com.wms.fw.util.DateUtil,com.wms.fw.Utility"%>
<%
	String screenParam = request.getParameter("_SCREEN");
  	response.sendRedirect(screenParam); %>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7, IE=EmulateIE9"/>
</head>
<body onload="">
<script language="Javascript">
	RequestDispatcher dispatcher = application.getRequestDispatcher("<%=screenParam%>");
	dispatcher.forward(request, response);
</script>
</body>
</html>