<!--
	�� �� ��  : confirmOk.jsp
	�ۼ�����  : 2004/06/22
	�� �� ��  : 
	��    ��  : ���� ����
-->
<%@ page contentType="text/html;charset=ksc5601" errorPage="/common/error.jsp"%> 
<jsp:useBean id="msg" class="java.lang.String" scope="request"/>

<%msg=(msg.equals(""))?request.getParameter("msg"):msg;%>
<%
String groupMsg=null;
if(msg.equals("�յ� ���� ����"))
	groupMsg=request.getParameter("groupMsg");
%>


<html>

<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7, IE=EmulateIE9"/>
<meta http-equiv="Content-Language" content="ko">
<meta http-equiv="Content-Type" content="text/html; charset=ks_c_5601-1987">

<title>ó�� ���</title>
<LINK rel="stylesheet" type="text/css" href="../css/skin04_viewstyle.css">

<script language='javascript'>
<!--
function winOnLoad(){
	setTimeout("winClose()",3000)
}

function winClose(){
	if('���� ���� ����'!='<%=msg%>'
	   &&'���� ���� ����'!='<%=msg%>'
	   &&'�յ� ���� ����'!='<%=msg%>'
       &&'����� ���� �Ϸ�'!='<%=msg%>')
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
		<td align=left >ó���޼���</td>
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
<BR><BR>(3���Ŀ� �ڵ������� ����˴ϴ�)</font></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<center>
<table>
<tr align=center>
<td class='base'  style ="cursor:hand;" onMouseOver="this.className='on'" onMouseOut="this.className='base'" onMouseDown="this.className='down'" onMouseUp="this.className='on'" onclick="javascript:winClose();">�ݱ�</td>
</tr>
</table>
<center>
</body>
</html>


