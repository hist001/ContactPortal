<!--
	파 일 명  : approval_ConfirmOk.jsp
	작성일자  : 2004/09/17
	작 성 자  : 
	내    용  : 업무보고승인 이벤트발생 이후 출력화면
-->
<%@ page contentType="text/html;charset=ksc5601" errorPage="/common/error.jsp"%> 
<jsp:useBean id="msg" class="java.lang.String" scope="request"/>
<jsp:useBean id="endFlag" class="java.lang.String" scope="request"/>
<%msg=(msg.equals(""))?request.getParameter("msg"):msg;%>
<%String actFlag=request.getParameter("actFlag");%>
<%if(msg==null){msg="개인직무";actFlag="WEO";}%>
<html>

<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7, IE=EmulateIE9"/>
<meta http-equiv="Content-Language" content="ko">
<meta http-equiv="Content-Type" content="text/html; charset=ks_c_5601-1987">

<title>처리결과</title>
<LINK rel="stylesheet" type="text/css" href="../css/skin04_viewstyle.css">

<script language='javascript'>
<!--
function winOnLoad(){
	var s="winProg()";
	if('<%=endFlag%>'=='Y'){
		s="winClose()";
	}
	setTimeout(s,50);
	

}

function winClose(){
	opener.endSearch();
	self.close();
}
function winProg(){
	frm = document.forms[0];
	frm.submit();
}
//-->
</script>
<body onload="javascript:winOnLoad()">
<form action='/dayJobCon' method="POST" >
<input type='hidden' name='actFlag' value='<%=actFlag%>' >
<input type='hidden' name='_ACT' value='RN'>
<input type='hidden' name='_SCREEN' value='/common/approval_ConfirmOk.jsp'>

</form>
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
			<table cellpadding=0 cellspacing=1 border=0 align=center width=420 height=180 background="../images/alert_bg.gif">
				<tr height=100% valign=middle>
					<td width=65></td>
					<td width=15><img src="../images/alert_bullet.gif"></td>
					<td><font color=darkblue><br>
					<%=msg.substring(msg.indexOf(":")+1)%><br><br></font></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<center>
<table>
<tr align=center>
<td class='base'  style ="cursor:hand;" onMouseOver="this.className='on'" onMouseOut="this.className='base'" onMouseDown="this.className='down'" onMouseUp="this.className='on'" onclick="javascript:winClose();" >닫기</td>
</tr>
</table>
</center>
</body>
</html>


