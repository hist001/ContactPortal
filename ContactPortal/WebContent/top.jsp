<%@ page contentType="text/html;charset=EUC-KR"
	errorPage="/common/error.jsp"%>
<%@ page import="com.wms.common.beans.dto.DataSet"%>
<%@ page import="com.wms.fw.util.DateUtil"%>
<%@ page import="com.wms.fw.Utility"%>
<%@ page import="com.wms.fw.servlet.HttpUtility"%>
<%@ page import="com.wms.fw.servlet.Box"%>
<%@ page import="com.wms.fw.util.AESUtil"%>
<jsp:useBean id="user" class="com.wms.beans.dto.UserInfo"
	scope="session" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script Language="JavaScript" src="/common/link/comAction.js"></script>
<script Language="JavaScript" src="/common/link/common.js"></script>
<LINK rel="stylesheet" type="text/css"
	href="../css/skin00_viewstyle.css">
<script language='javascript'>
	function menuHidden() {
		obj = document.getElementById('af');
		if (obj.value != 1) {
			timeSteper('+', 'af');
		} else {
			timeSteper('-', 'af');
		}
	}
	function actFn() {
		parent.frames['mainBody'].cols = pRate * 2 + ",830,*";
	}
	function openMenual() {
		return;
		openWin(
				'http://mas.hist.co.kr/common.do?_DATA=f1ff0160323c90b5dabf62b1a289f4ff&_SCREEN=/Manual/mu_index.jsp?S_MU_TYPE=PROD-20160304-001',
				'test', '1024', '768');
	}
</script>
<title>Welcome to HIST WMS</title>
</head>
<body text="#000000" bgcolor="#FFFFFF" topmargin=0 leftmargin=0
	scroll=no>

	<form method="post" action="" name="_DominoForm">
		<input type='hidden' id='af' value='1'>
		<div
			style="position: absolute; left: 510px; top: 16px; display =''; z-index =999; color: white">
			<%=user.empKName%>(<%=user.empId%>)
			<%//AESUtil.AesDecode("f1ff0160323c90b5dabf62b1a289f4ff")%>
		</div>
		<img src='/images/top.bmp' onclick='openMenual()'>
	</form>
</body>
</html>