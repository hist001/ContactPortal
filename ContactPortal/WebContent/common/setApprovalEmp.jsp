<!--
	파 일 명  : setApprovalEmp.jsp
	작성일자  : 2004/06/22
	작 성 자  : 
	내    용  : 대결자 등록 화면
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
		<td align=left >기본자료처리 &gt;&gt; 대결자 설정</td>
	</tr>
</table>
<table height=8%><tr><td></td></tr></table>
<table width=70% cellspacing=2 cellpadding=0 border=0 align=center>
	<tr height=25>
		<td align=right><table cellpadding=0 cellspacing=0 border=0><tr><td width=5></td><a href="JavaScript:save()"><td class="base"  style ="cursor:hand;" onMouseOver="this.className='on'" onMouseOut="this.className='base'" onMouseDown="this.className='down'" onMouseUp="this.className='on'"> 설정 저장 </td></a></tr></table></td>	
	</tr>
</table><table width=70% cellspacing=1 cellpadding=1 class=table1 align=center>	
	<tr height=25>
		<td width=100 class=td1>등록정보</td>
		<td class=td2><%=user.empKName%>&nbsp<%=user.eDutyName%>/<%=user.empOrgName%>(<%=DateUtil.getTimeStampString()%>)</td>
	</tr>
</table>
<br>
<table width=70% cellspacing=1 cellpadding=1 class=table1 align=center>
	<tr height=25>
		<td class=td2><font size="2" color="#0000ff" face="굴림">
<input name="apEmpKName" value="<%=(apKey)?apEmp.empKName:" "%>" SIZE=40 readOnly></font><font size="2" color="#0000FF" face="굴림"> </font><font size="2" color="#0000FF"> </font><input type=button class=buttona value="대결자 지정" onclick="apEmpSearch()"><font size="2" color="#0000FF" face="굴림"> </font>&nbsp;<input type=button class=buttona value="대결자 지정해제" onclick="DeleteAgent()">		</td>
	</tr>
	<tr height=25>
		<td class=td2>* 대결자를 지정할 경우 업무계획 및 업무확정 권한이 대결자에게 위임됩니다.<br>* 대결을 해제하는 시점까지 결재 권한 위임은 유효하며, 대결을 해제한 후에 모든 권한이 본인에게 돌아옵니다.</td>
	</tr>
</table>
 </form>
</body>
</html>
