<!--
	파 일 명  : changHighEmp.jsp
	작성일자  : 2004/06/22
	작 성 자  : 
	내    용  : 승인자 수정 화면
-->

<%@ page contentType="text/html;charset=ksc5601" 
errorPage="/common/error.jsp"
import="com.wms.beans.dto.EmpDTO" 
import="com.wms.fw.util.DateUtil"
import="com.wms.fw.Utility"%>
<jsp:useBean id="user" class="com.wms.beans.dto.UserInfo" scope="session"/>
<%
String mgrFg=user.getMgrUnionFlag();
if(mgrFg.equals("Y"))
	throw new Exception("업무승인자가 아닙니다.");
%>
<html>

<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7, IE=EmulateIE9"/>
<meta http-equiv="Content-Language" content="ko">
<meta http-equiv="Content-Type" content="text/html; charset=ks_c_5601-1987">
<LINK rel="stylesheet" type="text/css" href="../css/skin04_form.css">
<script Language="JavaScript" src="/common/link/common.js"></script>
<script language='javascript'>
function empSearch(){
		
	openWin('/common/changeNewHighSearch.jsp', "changeNewHighSearch", 500, 400);  
	//document.forms[0].action='/commonCon';
	//document.forms[0]._ACT.value='ASR';
    //document.forms[0]._SCREEN.value='/common/changeNewHighSearch.jsp';
	//document.forms[0].target='selectApPersons';
	//document.forms[0].submit();
	
}
function orgSearch(){
		
	openWin('/common/changeOrgHighSearch.jsp', "changeOrgHighSearch", 500, 400);  
	//document.forms[0].action='/commonCon';
	//document.forms[0]._ACT.value='ASR';
    //document.forms[0]._SCREEN.value='/common/changeOrgHighSearch.jsp';
	//document.forms[0].target='changeOrgHighSearch';
	//document.forms[0].submit();
	
}
function save(){
	frm=document.forms[0];
	if(frm.oldEmpId.value==''){
		alert("일괄 변경할 기존의 승인자를 선택하십시오.");
		return;
	}
	if(frm.newEmpId.value==''){
		alert("일괄 변경을 적용할 새승인자를 선택하십시오.");
		return;
	}
    if(confirm("OLD 승인자에서 New 승인자로 일괄변경하시겠습니까?")){
	document.forms[0].action='/commonCon';
	document.forms[0]._ACT.value='UU';
	frm._SCREEN.value='/common/changHighEmp.jsp'
	document.forms[0].target='';
	document.forms[0].submit();
	}
}
</script>
</head>
<body text="#000000" bgcolor="#FFFFFF" topmargin=0 leftmargin=00 rightmargin=0 onload="document.body.style.backgroundColor = &quot;white&quot;">
<form name="topForm" method="POST" action="/commonCon">
<input type='hidden' name='oldEmpId'>
<input type='hidden' name='newEmpId'>

<input type="hidden" name="_ACT"  >
<input type="hidden" name="_SCREEN"  >
<table width=100% cellspacing=0 cellpadding=0 border=0 class=loctitle>
	<tr valign=middle>
		<td width=20></td>
		<td width=30><img src="../images/sign_position.gif"></td>
		<td align=left >기본자료처리 &gt;&gt; 승인자 일괄변경</td>
	</tr>
</table>
<table height=8%><tr><td></td></tr></table>
<table width=70% cellspacing=2 cellpadding=0 border=0 align=center>
	<tr height=25>
		<td align=right><table cellpadding=0 cellspacing=0 border=0><tr><td width=5></td><a href="JavaScript:save()"><td class="base"  style ="cursor:hand;" onMouseOver="this.className='on'" onMouseOut="this.className='base'" onMouseDown="this.className='down'" onMouseUp="this.className='on'"> 일괄변경 </td></a></tr></table></td>	
	</tr>
</table>
<table width=70% cellspacing=1 cellpadding=1 class=table1 align=center>
	<!--
	<tr height=25>

		<td width="10%" class=td1>조직명</td>
		<td width="90%"  class=td2 colspan="2">
			<input name="orgName" value="" style="width : 85% " readOnly>
			<input type=button class=buttona value="조직선택" onclick="orgSearch()">
		</td>

	</tr>
	-->
	<tr height=25>
		<td width="50%" class=td1 colspan="2">OLD 승인자명</td>
		<td width="50%" class=td1>NEW 승인자명</td>
	</tr>
	<tr height=25>
		<td class=td2 colspan="2">
			<input name="oldEmpKName" value="" style="width : 85% ;text-align : center " readOnly>
			<input type=button class=buttona style=" align : right"  value="선택" onclick="orgSearch()">
		</td>
		<td class=td2>
			<input name="newEmpKName" value="" style="width : 85% ;text-align : center" readOnly>
			<input type=button class=buttona style=" align : right"  value="선택" onclick="empSearch()">
		</td>
	</tr>
	<tr height=25>
		<td class=td2 colspan="3">* 승인자를 변경할 경우 업무계획 및 보고승인 권한이 새로운 승인자로 변경됩니다.<br>* 변경된 시점에서 OLD승인자의 권한은 모두 없어집니다.</td>
	</tr>
</table>
 </form>
</body>
</html>