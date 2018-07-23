<!--
	파 일 명  : changeOrgHighSearch.jsp
	작성일자  : 2004/08/18
	작 성 자  : 조원호
	내    용  : 그룹 검색 화면
-->
<%@ page contentType="text/html;charset=ksc5601" errorPage="/common/error.jsp"%>
<%@ page import="com.wms.beans.dto.EmpDTO"%>
<%EmpDTO[] orgList=(EmpDTO[])request.getAttribute("orgList");%>
<%String orgCd=request.getParameter("orgCd");%>
<%String orgName=request.getParameter("orgName");%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7, IE=EmulateIE9"/>
<meta http-equiv="Content-Language" content="ko">
<meta http-equiv="Content-Type" content="text/html; charset=ks_c_5601-1987">
<LINK rel="stylesheet" type="text/css" href="../css/skin04_form.css">
<style>
<!--
.viewContentL {text-align:left;cursor:hand;border-style:solid; border-color:#EBEEF0 #EBEEF0 #EBEEF0 #EBEEF0;border-width:0 0 1 0}
.viewContentC {text-align:center;cursor:hand;border-style:solid; border-color:#EBEEF0 #EBEEF0 #EBEEF0 #EBEEF0;border-width:0 0 1 0}
.viewContentR {text-align:right;cursor:hand;border-style:solid; border-color:#EBEEF0 #EBEEF0 #EBEEF0 #EBEEF0;border-width:0 0 1 0}
-->
</style>
<script Language="JavaScript" src="/common/link/common.js"></script>
<title>코드 검색</title>
<script Language="JavaScript" src="/common/link/jobSearch.js"></script>
<script language='javascript'>
<!--

function confirmItem(orgName,orgCd,empKName,empId){
	var preFrm=opener.document.forms[0];
    preFrm.orgCd.value=orgCd;
	preFrm.orgName.value=orgName+'('+orgCd+')';
	preFrm.oldEmpId.value=empId;
	preFrm.oldEmpKName.value=empKName+'('+empId+')';
	self.close();
}

function search(){
	var frm = document.forms[0];
	frm.action = '/commonCon';
	frm._ACT.value = 'RCO';
	frm._SCREEN.value = '/common/changeOrgHighSearch.jsp';

	frm.submit();
}

//-->
</script>
</head>

<body text="#000000" bgcolor="#FFFFFF" topmargin=0 leftmargin=0 rightmargin=0>
<form method="POST" >
<input type='hidden' name='_ACT' >
<input type='hidden' name='_SCREEN'>


<table cellpadding=1 cellspacing=1 class=table1 border=0 width=100%>
	<tr>
		<td class=td2>
			<table cellpadding=2 cellspacing=1 border=0 width=100%>
				<tr>
					<td width=20><img src="/images/i_formtitle.gif"></td><td><B>조직검색</B></td>
					<td align=right>
						<table cellpadding=0 cellspacing=0 border=0>
							<tr><td width=5></td>
							<a href="javascript:winClose()">
								<td class="base"  style ="cursor:hand;" onMouseOver="this.className='on'" onMouseOut="this.className='base'" onMouseDown="this.className='down'" onMouseUp="this.className='on'">닫기</td></a>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<table border=0><tr height=1><td></td></tr></table>




<Table  cellpadding=0 cellspacing=1 border=0 class=table1 width=100%>
	<tr>
		<td width="85%">
			<Table  cellpadding=0 cellspacing=0 border=0 class=table1 width=100%>
				<tr>
					<td width="20%" height="24" class=TD1>조직코드</td>
					<td width="15%" height="24" class=TD2><input name="orgCd" size="15" maxlength="15" onkeydown="javascript:if(event.keyCode==13){search();}"
					value='<%=(orgCd!=null)?orgCd:""%>'></td>
					<td width="20%" height="24" class=TD1>조직명</td>
					<td width="15%" height="24" class=TD2><input name="orgName" size="15" maxlength="15" style="ime-mode:active;" onkeydown="javascript:if(event.keyCode==13){search();}"
					value='<%=(orgName!=null)?orgName:""%>'
					></td>
				</tr>
			</Table>
		</td>
		<td width="15%" class=td2>
			<Table  cellpadding=0 cellspacing=0 border=0 class=table1 width=100%>
				<tr>
					<td width=5 class="td2"></td>
					<a href="javascript:search()">
						<td class="base"  style ="cursor:hand;"  onMouseOver="this.className='on'" onMouseOut="this.className='base'" onMouseDown="this.className='down'" onMouseUp="this.className='on'">검색</td>
					</a>
				</tr>
			</Table>
		</td>
	</tr>
</Table>
<table border=0><tr height=1><td></td></tr></table>
<Table  cellpadding=0 cellspacing=1 border=0 class=table1 width=100% id="table1">
	<tr>
		<td width="50%" height="24" class=TD1>조직</td>
		<td width="50%" height="24" class=TD1>조직장</td>
	</tr>
</Table>
<Table  cellpadding=0 cellspacing=1 border=0 class=table1 width=100% id="table1">
<%if(orgList!=null){
	for(int i=0;i<orgList.length;i++){
		EmpDTO dto = orgList[i];
%>
	<tr class='base' style ="cursor:hand;" onMouseOver="this.className='on'" onMouseOut="this.className='base'" onclick="javascript:confirmItem('<%=dto.empOrgName%>','<%=dto.empOrgCd%>','<%=dto.empKName%>','<%=dto.empId%>')">
		<td width="50%" height="24" ><%=dto.empOrgName+"("+dto.empOrgCd+")"%></td>
		<td width="50%" height="24" ><%=dto.empKName+"("+dto.empId+")"%></td>
	</tr>
<%	}%>
<%}else{%>
	<tr class='base' style ="cursor:hand;" onMouseOver="this.className='on'" onMouseOut="this.className='base'" >
		<td colspan=2 height="24" align='center'>해당 자료가 없습니다</td>
	</tr>
<%}%>
</table>



</form>
</body>
</html>