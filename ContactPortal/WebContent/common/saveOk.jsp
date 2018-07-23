<!--
	파 일 명  : saveOk.jsp
	작성일자  : 2008/07/14
	작 성 자  : mailbest
	내    용  : 저장 성공
-->
<%@ page contentType="text/html;charset=ksc5601" errorPage="/common/error.jsp"%> 
<%@ page import="com.wms.fw.servlet.Box"%>
<%@ page import="com.wms.fw.servlet.HttpUtility"%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7, IE=EmulateIE9"/>
<meta http-equiv="Content-Language" content="ko">
<meta http-equiv="Content-Type" content="text/html; charset=ks_c_5601-1987">

<title>저장완료</title>
<LINK rel="stylesheet" type="text/css" href="../css/skin04_viewstyle.css">

<script language='javascript'>
	//Modal팝업일때 처리
	if(opener==undefined && window.dialogArguments!=undefined) {
	    opener = window.dialogArguments;
		self.close();
	}
	
	if(opener!=undefined){
		opener.search();	
		self.close();
	}

</script>
<body onload="">
</body>
</html>


