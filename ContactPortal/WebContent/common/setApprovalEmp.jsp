<!--
	�� �� ��  : setApprovalEmp.jsp
	�ۼ�����  : 2004/06/22
	�� �� ��  : 
	��    ��  : ����� ��� ȭ��
-->

<%@ page contentType="text/html;charset=ksc5601" 
import="com.wms.beans.dto.EmpDTO" 
import="com.wms.beans.dto.ApprovalDTO"
import="com.wms.fw.util.DateUtil"
import="com.wms.fw.Utility"%>
<jsp:useBean id="user" class="com.wms.beans.dto.UserInfo" scope="session"/>
<jsp:useBean id="apEmp" class="com.wms.beans.dto.EmpDTO" scope="request"/>

<%
//ApprovalDTO approval =(ApprovalDTO)user.approvalList.get(user.empOrgCd);
boolean apKey=(apEmp.empId!=null)?true:false;
%>

<html>

<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7, IE=EmulateIE9"/>
<meta http-equiv="Content-Language" content="ko">
<meta http-equiv="Content-Type" content="text/html; charset=ks_c_5601-1987">
<LINK rel="stylesheet" type="text/css" href="../css/skin04_form.css">
<script Language="JavaScript" src="/common/link/common.js"></script>
<script language='javascript'>
function apEmpSearch(){
		
	openWin("", "selectApPersons", 400, 300);  
	document.forms[0].action='/commonCon';
	document.forms[0]._ACT.value='ASR';
    document.forms[0]._SCREEN.value='/common/selectApPersons.jsp';
	document.forms[0].target='selectApPersons';
	document.forms[0].submit();
	
}
function DeleteAgent(){
	document.forms[0].apEmpKName.value="";
}
function save(){
	document.forms[0].action='/commonCon';
	document.forms[0]._ACT.value='UA';
	document.forms[0].target='';
	document.forms[0].submit();
}
</script>
</head>
<body text="#000000" bgcolor="#FFFFFF" topmargin=0 leftmargin=00 rightmargin=0 onload="document.body.style.backgroundColor = &quot;white&quot;">
<form name="topForm" method="POST" action="/commonCon">


<input type="hidden" name="_ACT"  value='UA'>
<input type="hidden" name="_SCREEN" value="">
<input type="hidden" name="apEmpId" value="<%=(apKey)?apEmp.empId:""%>">
<table width=100% cellspacing=0 cellpadding=0 border=0 class=loctitle>
	<tr valign=middle>
		<td width=20></td>
		<td width=30><img src="../images/sign_position.gif"></td>
		<td align=left >�⺻�ڷ�ó�� &gt;&gt; ����� ����</td>
	</tr>
</table>
<table height=8%><tr><td></td></tr></table>
<table width=70% cellspacing=2 cellpadding=0 border=0 align=center>
	<tr height=25>
		<td align=right><table cellpadding=0 cellspacing=0 border=0><tr><td width=5></td><a href="JavaScript:save()"><td class="base"  style ="cursor:hand;" onMouseOver="this.className='on'" onMouseOut="this.className='base'" onMouseDown="this.className='down'" onMouseUp="this.className='on'"> ���� ���� </td></a></tr></table></td>	
	</tr>
</table><table width=70% cellspacing=1 cellpadding=1 class=table1 align=center>	
	<tr height=25>
		<td width=100 class=td1>�������</td>
		<td class=td2><%=user.empKName%>&nbsp<%=user.eDutyName%>/<%=user.empOrgName%>(<%=DateUtil.getTimeStampString()%>)</td>
	</tr>
</table>
<br>
<table width=70% cellspacing=1 cellpadding=1 class=table1 align=center>
	<tr height=25>
		<td class=td2><font size="2" color="#0000ff" face="����">
<input name="apEmpKName" value="<%=(apKey)?apEmp.empKName:" "%>" SIZE=40 readOnly></font><font size="2" color="#0000FF" face="����"> </font><font size="2" color="#0000FF"> </font><input type=button class=buttona value="����� ����" onclick="apEmpSearch()"><font size="2" color="#0000FF" face="����"> </font>&nbsp;<input type=button class=buttona value="����� ��������" onclick="DeleteAgent()">		</td>
	</tr>
	<tr height=25>
		<td class=td2>* ����ڸ� ������ ��� ������ȹ �� ����Ȯ�� ������ ����ڿ��� ���ӵ˴ϴ�.<br>* ����� �����ϴ� �������� ���� ���� ������ ��ȿ�ϸ�, ����� ������ �Ŀ� ��� ������ ���ο��� ���ƿɴϴ�.</td>
	</tr>
</table>
 </form>
</body>
</html>
