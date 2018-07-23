<!--
	파 일 명  : secuPopup.jsp
	작성일자  : 2013.06.26
	작 성 자  : mailbest
	내    용  : url 보안팝업
-->
<%@ page contentType="text/html;charset=ksc5601" errorPage="/common/error.jsp"%>
<%@ page import="com.wms.common.beans.dto.DataSet"%>
<%@ page import="com.wms.fw.util.DateUtil"%>
<%@ page import="com.wms.fw.servlet.HttpUtility"%>
<%@ page import="com.wms.fw.Utility"%>
<%@ page import="com.wms.fw.servlet.Box"%>

<%
		request.setCharacterEncoding("ksc5601");
		Box box = HttpUtility.getBox(request);
%>		
<jsp:useBean id="user" class="com.wms.beans.dto.UserInfo" scope="session"/>

<html> 
<SCRIPT LANGUAGE="JavaScript"> 
		function Frameset() { 
		framecode = "<frameset rows='1*'>" 
		+ "<frame name=main src='<%=box.get("pUrl") %>?<%=box.get("sParam") %>'>" 
		+ "</frameset>"; 

		document.write(framecode); 
		document.close(); 
		}

</script> 
<head> 
<title>사이트</title> 
<meta http-equiv="content-type" content="text/html; charset=utf8"> 
    <body bgcolor="white" text="black" link="blue" vlink="purple" alink="red"  onload="Frameset();"> 
    <p>이 페이지를 보려면, 프레임을 볼 수 있는 브라우저가 필요합니다.</a></p> 
    </body> 

</html> 
