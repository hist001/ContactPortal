<!--
	�� �� ��  : changHighEmp.jsp
	�ۼ�����  : 2004/06/22
	�� �� ��  : 
	��    ��  : ������ ���� ȭ��
-->

<%@ page contentType="text/html;charset=ksc5601" 
import="com.wms.beans.dto.EmpDTO" 
import="com.wms.fw.util.DateUtil"
import="com.wms.fw.Utility"%>
<jsp:useBean id="user" class="com.wms.beans.dto.UserInfo" scope="session"/>

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
function orgSearch(){
		
	openWin('/common/changeOrgHighSearch.jsp', "changeOrgHighSearch", 500, 400);  
	//document.forms[0].action='/commonCon';
	//document.forms[0]._ACT.value='ASR';
    //document.forms[0]._SCREEN.value='/common/changeOrgHighSearch.jsp';
	//document.forms[0].target='changeOrgHighSearch';
	//document.forms[0].submit();
	
}
function DeleteAgent(){
	document.forms[0].apEmpKName.value="";
}
function save(){
    alert("OLD �������� New ���������� �����Ͻðڽ��ϱ�?")
	document.forms[0].action='/commonCon';
	document.forms[0]._ACT.value='UA';
	document.forms[0].target='';
	document.forms[0].submit();
}
</script>
</head>
<body text="#000000" bgcolor="#FFFFFF" topmargin=0 leftmargin=00 rightmargin=0 onload="document.body.style.backgroundColor = &quot;white&quot;">
<form name="topForm" method="POST" action="/commonCon">
<input type='hidden' name='orgCd'>
<input type='hidden' name='oldEmpId'>
<input type='hidden' name='newEmpId'>

<input type="hidden" name="_ACT"  >
<input type="hidden" name="_SCREEN" >
<table width=100% cellspacing=0 cellpadding=0 border=0 class=loctitle>
	<tr valign=middle>
		<td width=20></td>
		<td width=30><img src="../images/sign_position.gif"></td>
		<td align=left >�⺻�ڷ�ó�� &gt;&gt; ������ �ϰ�����</td>
	</tr>
</table>
<table height=8%><tr><td></td></tr></table>
<table width=70% cellspacing=2 cellpadding=0 border=0 align=center>
	<tr height=25>
		<td align=right><table cellpadding=0 cellspacing=0 border=0><tr><td width=5></td><a href="JavaScript:save()"><td class="base"  style ="cursor:hand;" onMouseOver="this.className='on'" onMouseOut="this.className='base'" onMouseDown="this.className='down'" onMouseUp="this.className='on'"> ���� ���� </td></a></tr></table></td>	
	</tr>
</table>
<table width=70% cellspacing=1 cellpadding=1 class=table1 align=center>
	<tr height=25>
		<td width="10%" class=td1>������</td>
		<td width="90%"  class=td2 colspan="2">
			<input name="orgName" value="" style="width : 85% " readOnly>
			<input type=button class=buttona value="��������" onclick="orgSearch()">
		</td>
	</tr>
	<tr height=25>
		<td width="40%" class=td1 colspan="2">OLD �������</td>
		<td width="60%" class=td1>NEW �������</td>
	</tr>
	<tr height=25>
		<td class=td2 colspan="2">
			<input name="oldEmpKName" value="" style="width : 100% " readOnly></td>
		<td class=td2>
			<input name="newEmpKName" value="" style="width : 85% " readOnly>
			<input type=button class=buttona style=" align : right"  value="����" onclick="apEmpSearch()">
		</td>
	</tr>
	<tr height=25>
		<td class=td2 colspan="3">* �������� ������ ��� ������ȹ �� ����Ȯ�� ������ ���ο� ���������� ����˴ϴ�.<br>* ����� �������� OLD�������� ������ ��� �������ϴ�.</td>
	</tr>
</table>
 </form>
</body>
</html>