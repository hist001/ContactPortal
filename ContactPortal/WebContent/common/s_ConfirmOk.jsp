<!--
	파 일 명  : s_ConfirmOk.jsp
	작성일자  : 2004/06/22
	작 성 자  : 
	내    용  : 주소 저장 성공
-->
<%@ page contentType="text/html;charset=ksc5601" errorPage="/common/error.jsp"%> 
<%String loc=request.getParameter("loc");%>

<html>

<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7, IE=EmulateIE9"/>
<meta http-equiv="Content-Language" content="ko">
<meta http-equiv="Content-Type" content="text/html; charset=ks_c_5601-1987">
<script language='javascript'>
function winOnLoad(){
	opener.document.forms[0].loc.value='<%=loc%>';
	setTimeout("winClose()",500)
}
function winClose(){
	self.close();
}
</script>
<title>처리상태</title>
<LINK rel="stylesheet" type="text/css" href="../css/skin04_viewstyle.css">




<body onload='winOnLoad()'>

<table id=error2  align=center cellpadding=0 cellspacing=0 border=0 width=100% height=80%>
	<tr>
		<td width=100% height=100% align=center valign=middle>
			<table cellpadding=0 cellspacing=1 border=0 align=center width=100% height=100% >
				<tr height=100% valign=middle>					
					<td align=center ><font color=darkblue><br>
					<img src="../images/alert_bullet.gif">처리되었습니다.<br><br></font></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<table>
<tr align=center>

</tr>
</table>
</body>
</html>


