<!--
	파 일 명  : comSwfView.jsp
	내    용  : 공통 SWF 파일 보기
-->
<%@ page contentType="text/html;charset=euc-kr" errorPage="/common/error.jsp"%>
<%@ page import="com.wms.fw.servlet.Box" %>
<%@ page import="com.wms.fw.servlet.HttpUtility" %>
<%@ page import="com.wms.fw.Utility"%>
<jsp:useBean id="user" class="com.wms.beans.dto.UserInfo" scope="session"/>
<%
	request.setCharacterEncoding("ksc5601");
	Box box = HttpUtility.getBox(request);	
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7, IE=EmulateIE9"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>[플래쉬 보기]</title>
</head>
<script language='javascript'>
function play(){
	var frm= document.forms[0];
	location.reload();
}

</script>

<body onload="">
<form name="form1" method="POST" action="" align='center'>
<EMBED style="FILTER: gray()" 
		src='downLoadFiles.do?dir=<%=box.get("DIR")%>&fileSystemName=<%=box.get("SYSTEM")%>&fileOriginName=<%=box.get("ORIGIN")%>' 
		type=application/x-shockwave-flash 
		width=700 
		height=700 
		autostart="true" 
		loop="true" 
		showcontrols="1" 
		showstatusbar="1" 
		showaudiocontrols="1" 
		volume="1" 
		pluginspage="http://www.macromedia.com/go/getflashplayer"> 
<table width='100%' border=0><tr><td align='center'>
<img src='/images/pass_year_right.jpg' onclick="play();">
</td></tr></table>
</form>
</body>
</html>
