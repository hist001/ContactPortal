<!--
	�� �� ��  : secuPopup.jsp
	�ۼ�����  : 2013.06.26
	�� �� ��  : mailbest
	��    ��  : url �����˾�
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
<title>����Ʈ</title> 
<meta http-equiv="content-type" content="text/html; charset=utf8"> 
    <body bgcolor="white" text="black" link="blue" vlink="purple" alink="red"  onload="Frameset();"> 
    <p>�� �������� ������, �������� �� �� �ִ� �������� �ʿ��մϴ�.</a></p> 
    </body> 

</html> 
