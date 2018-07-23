<!--
	파 일 명  : confirmOk.jsp
	작성일자  : 2004/06/22
	작 성 자  : 
	내    용  : 저장 성공
-->
<%@ page contentType="text/html;charset=ksc5601" errorPage="/common/error.jsp"%> 
<jsp:useBean id="msg" class="java.lang.String" scope="request"/>

<%msg=(msg.equals(""))?request.getParameter("msg"):msg;%>
<%
String groupMsg=null;
if(msg.equals("합동 업무 보고"))
	groupMsg=request.getParameter("groupMsg");
%>


<html>

<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7, IE=EmulateIE9"/>
<meta http-equiv="Content-Language" content="ko">
<meta http-equiv="Content-Type" content="text/html; charset=ks_c_5601-1987">

<title>처리 결과</title>
<LINK rel="stylesheet" type="text/css" href="../css/skin04_viewstyle.css">

<script language='javascript'>
<!--
function winOnLoad(){
	setTimeout("winClose()",3000)
}

function winClose(){
	if('개별 업무 보고'!='<%=msg%>'
	   &&'일일 영업 보고'!='<%=msg%>'
	   &&'합동 업무 보고'!='<%=msg%>'
       &&'대결자 지정 완료'!='<%=msg%>')
		opener.endSearch();
	self.close();
}
//-->
</script>


<body onload="javascript:winOnLoad()">
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
					<%=(groupMsg==null)?msg.substring(msg.indexOf(":")+1):groupMsg%><br><br>
<BR><BR>(3초후에 자동적으로 종료됩니다)</font></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<center>
<table>
<tr align=center>
<td class='base'  style ="cursor:hand;" onMouseOver="this.className='on'" onMouseOut="this.className='base'" onMouseDown="this.className='down'" onMouseUp="this.className='on'" onclick="javascript:winClose();">닫기</td>
</tr>
</table>
<center>
</body>
</html>


