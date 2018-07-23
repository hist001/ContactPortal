
<!--
	파 일 명  : comfileDown.jsp
	작성일자  : 2009/12/24
	작 성 자  : MAILBEST
	내    용  : 공통 파일 다운로드 
-->
<%@ page contentType="text/html;charset=ksc5601" errorPage="/common/error.jsp"%>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="com.wms.fw.util.DateUtil"%>
<%@ page import="com.wms.fw.Utility"%>
<%@ page import="com.wms.fw.servlet.HttpUtility" %>
<%@ page import="com.wms.fw.servlet.Box" %>
<jsp:useBean id="user" class="com.wms.beans.dto.UserInfo" scope="session"/>
<%
	request.setCharacterEncoding("ksc5601");
	Box box = HttpUtility.getBox(request);
%>
<html>
<script Language="JavaScript" src="/common/link/common.js"	type="text/javascript"></script>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7, IE=EmulateIE9"/>
<LINK rel="stylesheet" type="text/css" href="../css/skin04_form.css">
<script Language="JavaScript" src="../common/link/comfiles.js"></script>
</head>
<body text="#000000" style="background-color=#FFFFFF" topmargin=0 leftmargin=0 rightmargin=0>
<script language='javascript'><!--
 	download('<%=box.get("dir")%>','<%=box.get("sysFileName")%>','<%=box.get("FileName")%>')
--></script>
</body>
</html>