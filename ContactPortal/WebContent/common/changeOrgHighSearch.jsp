<!--
	파 일 명  : changeOrgHighSearch.jsp
	작성일자  : 2004/08/18
	작 성 자  : 조원호
	내    용  : 그룹 검색 화면
-->
<%@ page contentType="text/html;charset=ksc5601" errorPage="/common/error.jsp"%>
<%@ page import="com.wms.beans.dto.EmpDTO"%>
<%EmpDTO[] orgList=(EmpDTO[])request.getAttribute("orgList");%>
<%String empId=request.getParameter("empId");%>
<%String empKName=request.getParameter("empKName");%>

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

function confirmItem(empKName,empId,empOrgName){
	var preFrm=opener.document.forms[0];
    //preFrm.orgCd.value=orgCd;
	//preFrm.orgName.value=orgName+'('+orgCd+')';
	preFrm.oldEmpId.value=empId;
	preFrm.oldEmpKName.value=empKName+'('+empId+')'+empOrgName;
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
					<td width=20><img src="/images/i_formtitle.gif"></td><td><B>승인자검색</B></td>
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
					<td width="20%" height="24" class=TD1>사번</td>
					<td width="15%" height="24" class=TD2><input name="empId" size="15" maxlength="15" onkeydown="javascript:if(event.keyCode==13){search();}"
					value='<%=(empId!=null)?empId:""%>'></td>
					<td width="20%" height="24" class=TD1>성명</td>
					<td width="15%" height="24" class=TD2><input name="empKName" size="15" maxlength="15" style="ime-mode:active;" onkeydown="javascript:if(event.keyCode==13){search();}"
					value='<%=(empKName!=null)?empKName:""%>'
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
		<td width="50%" height="24" class=TD1>사번</td>
		<td width="50%" height="24" class=TD1>성명</td>
	</tr>
</Table>
<Table  cellpadding=0 cellspacing=1 border=0 class=table1 width=100% id="table1">
<%if(orgList!=null){
	String key="";
	for(int i=0;i<orgList.length;i++){
		EmpDTO dto = orgList[i];
		if(!dto.empId.equals(key)){
%>
	<tr class='base' style ="cursor:hand;" onMouseOver="this.className='on'" onMouseOut="this.className='base'" onclick="javascript:confirmItem('<%=dto.empKName%>','<%=dto.empId%>','<%=dto.empOrgName%>')">
		<td width="50%" height="24" ><%=dto.empId%></td>
		<td width="50%" height="24" ><%=dto.empKName%></td>
	</tr>
<%		}
	    key=dto.empId;
	}%>
<%}else{%>
	<tr class='base' style ="cursor:hand;" onMouseOver="this.className='on'" onMouseOut="this.className='base'" >
		<td colspan=2 height="24" align='center'>해당 자료가 없습니다</td>
	</tr>
<%}%>
</table>



</form>
</body>
</html>