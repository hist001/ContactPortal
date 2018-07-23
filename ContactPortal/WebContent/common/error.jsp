<!--
	파 일 명  : error.jsp
	작성일자  : 2004/06/22
	작 성 자  : 
	내    용  : 에러를 출력하는 화면
-->
<%@ page contentType="text/html;charset=ksc5601" isErrorPage="true"%>
<%java.lang.Exception e =(java.lang.Exception)request.getAttribute("e");%>
<%String msg=(e==null)?exception.toString():e.toString();
  msg =msg.substring(msg.indexOf(":")+1);
  if(e instanceof java.lang.NullPointerException )
  msg="<br><br>세션이 종료되었습니다."
     +"<br> 재로그인 후  다시 시작 하시기 "
	 +"바랍니다. ";
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7, IE=EmulateIE9"/>
<title>Error </title>
<link rel=stylesheet type="text/css" href="../css/skin04_form.css">

</head>
</head>
<body text="#000000" bgcolor="#FFFFFF" topmargin=0 leftmargin=00 rightmargin=0 onload="document.body.style.backgroundColor = &quot;white&quot;">
<table width=100% cellspacing=0 cellpadding=2 border=0 class=loctitle>
	<tr valign=middle>
		<td width=20></td>
		<td width=20><img src="/images/sign_position.gif"></td>
		<td align=left >오류메세지</td>
	</tr>
</table>
<table id=error2 style=display:"" cellpadding=0 cellspacing=0 border=0 width=100% height=80%>
	<tr>
		<td width=100% height=100% align=center valign=middle>
			<table cellpadding=0 cellspacing=1 border=0 align=center width=420 height=180 background="../images/alert_bg.gif">
				<tr height=100% valign=middle>
					<td width=65></td>
					<td width=15><img src="../images/alert_bullet.gif"></td>
					<td><br><br>
					<font size='2pt'color=red><%=msg%></font>
					<br>
<BR><br><font color=darkblue>업무관리 담당자</font></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</body>
</html>

