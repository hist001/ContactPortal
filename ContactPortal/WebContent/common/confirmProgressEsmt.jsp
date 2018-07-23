<!--
	파 일 명  : confirmProgressEsmt.jsp
	작성일자  : 2007/12/26
	작 성 자  : cylim
	내    용  : 처리 절차
-->
<%@ page contentType="text/html;charset=ksc5601" errorPage="/common/error.jsp"%> 
<%@ page import="com.wms.fw.util.DateUtil"%>
<%@ page import="com.wms.fw.servlet.HttpUtility" %>
<%@ page import="com.wms.fw.Utility"%>
<%@ page import="com.wms.fw.servlet.Box" %>
<jsp:useBean id="CPG_MSG" class="java.lang.String" scope="request"/>
<jsp:useBean id="CPG_CALL" class="java.lang.String" scope="request"/>
<jsp:useBean id="PJT_NO" class="java.lang.String" scope="request"/>
<jsp:useBean id="CONF_NO" class="java.lang.String" scope="request"/>
<%
	request.setCharacterEncoding("ksc5601");	

	System.out.println("jsp:PJT_NO::"+PJT_NO);
	System.out.println("jsp:CONF_NO::"+CONF_NO);

%>
<html>

<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7, IE=EmulateIE9"/>
<meta http-equiv="Content-Language" content="ko">
<meta http-equiv="Content-Type" content="text/html; charset=ks_c_5601-1987">

<title>처리 결과</title>
<LINK rel="stylesheet" type="text/css" href="/css/skin04_viewstyle.css">

<script language='javascript'>
<!--
function winOnLoad(){

//alert('<%= PJT_NO%>');alert('<%= CONF_NO%>');

	setTimeout("winClose()",3000);
}

function winClose(){
	var frm = document.form1;
	opener.reloading('<%=PJT_NO%>','<%=CONF_NO%>');
	self.close();
}

//-->
</script>


<body onload="javascript:winOnLoad()">
<form name="form1"  method="POST">

<table width=100% cellspacing=0 cellpadding=2 border=0 class=loctitle>
	<tr valign=middle>
		<td width=20></td>
		<td width=20><img src="/images/sign_position.gif"></td>
		<td align=left >처리메세지</td>
	</tr>
</table>
<table id=error2 style=display:"" cellpadding=0 cellspacing=0 border=0 width=100% height=80%>
	<tr>
		<td width=100% height=100% align=center valign=middle>
			<table cellpadding=0 cellspacing=1 border=0 align=center width=420 height=180 background="/images/alert_bg.gif">
				<tr height=100% valign=middle>
					<td width=65></td>
					<td width=15><img src="/images/alert_bullet.gif"></td>
					<td><font color=darkblue ><br>
					<br><br><%=CPG_MSG%> 처리되었습니다.
                    <BR><BR>(3초후에 자동적으로 종료됩니다)</font></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<center>
<table cellpadding=0 cellspacing=0 border=0>
<tr align=center >
<td class="bt_f" width="0" nowrap></td>
<td width=35  class='button' style="cursor:hand;" onMouseOver="this.className='buttonOn'" onMouseOut="this.className='button'" onclick="javascript:winClose();">닫기</td>
<td CLASS="bt_e" nowrap width="0"></td>	
</tr>
</table>
<center>
</body>
</html>


