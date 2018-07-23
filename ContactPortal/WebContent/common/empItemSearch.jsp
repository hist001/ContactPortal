<!--
	파 일 명  : empItemSearch.jsp
	작성일자  : 2004/06/22
	작 성 자  : 
	내    용  : 직원 코드 검색 화면
-->
<%@ page contentType="text/html;charset=ksc5601" errorPage="/common/error.jsp"%>
<%@ page import="com.wms.fw.Utility"%>
<%com.wms.beans.dto.HistJobDTO[] empJobList=(com.wms.beans.dto.HistJobDTO[])request.getAttribute("empJobList");%>
<%
	String idx=request.getParameter("idx");
	String strProdType = request.getParameter("prodType");
	strProdType="J";
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7, IE=EmulateIE9"/>
<meta http-equiv="Content-Language" content="ko">
<meta http-equiv="Content-Type" content="text/html; charset=ks_c_5601-1987">
<LINK rel="stylesheet" type="text/css" href="../css/skin04_form.css">
<title>코드 검색</title>
<script Language="JavaScript" src="/common/link/jobSearch.js"></script>
<script Language="JavaScript">
var idx=<%=idx%>
var ix;
function setEmpJob(i){
    ix=i;
	var frm = document.forms[0]
	
	if(frm.bizNo[i].value!=''){
	   if(opener.document.forms[0].jobTitle.length==null){
			 opener.document.forms[0].jobTitle.value=frm.jobTitle[i].value
	   }else{
	        opener.document.forms[0].jobTitle[idx].value=frm.jobTitle[i].value
	   }
		select(
			frm.jobCd[i].value,
			frm.jobName[i].value,
			frm.bizNo[i].value,
			frm.bizName[i].value);
	}else{
		openWin("/common/bizItemSearch.jsp", "", '600', '500');  
	}
}

function histSearch(){
	openWin("/common/histJobSearch.jsp", "", '600', '500');  
}

function histNameSearch(){
	openWin("/common/histJobNameSearch.jsp", "", '600', '500');  
}

function bizReg(bizNo,bizName){
	var frm = document.forms[0]
	frm.bizNo[ix].value=bizNo
	frm.bizName[ix].value=bizName
}

function changeMode(prodType){
	var frm=document.forms[0];
	frm.prodType.value=prodType;
	document.forms[0]._ACT.value='LRP';
	document.forms[0]._SCREEN.value='/common/WorkItemSearch.jsp'
	document.forms[0].action='/prodCon';
	document.forms[0].empId.value='00000000';
	document.forms[0].submit();
}
</script>

</head>
<body text="#000000" bgcolor="#FFFFFF" topmargin=0 leftmargin=0 rightmargin=0>
<form method="POST" action="--WEBBOT-SELF--">
<input type='hidden' name='_ACT' >
<input type='hidden' name='_SCREEN'>
<input type='hidden' name='url'>
<input type='hidden' name='prodType'>
<input type='hidden' name='empId'>

<table cellpadding=1 cellspacing=1 class=table1 border=0 width=100%>
	<tr>
		<td class=td2>
			<table cellpadding=2 cellspacing=1 border=0 width=100%>
				<tr>
					<td width=20><img src="/images/i_formtitle.gif"></td><td><B>업무보고 >> 직무/사업코드선택</B></td>
					<td align=right>
						<table cellpadding=0 cellspacing=0 border=0>
							<tr>
							<td width=5></td>
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
<Table  cellpadding=3 cellspacing=1 border=0 class=table1 width=100%>
	<td  width="10%" class=TD1>작업구분</td>
	<td  width="15%" class=TD2>
		<input type=radio id="rdo" name="rdo" value="PROD" class=Attach onclick="javascript:changeMode('P')">제품&nbsp; 
		<input type=radio id="rdo" name="rdo" value="TASK" class=Attach onclick="javascript:changeMode('T')">Task&nbsp; 
		<input type=radio id="rdo" name="rdo" value="JOB" class=Attach onclick="rdoJob_click()" <%=(strProdType.equals("J"))?"checked":" "%>>직무
	</td>
</Table>
<table border=0 width=100%>
	<tr height=1>
		<td width="75%" align="center"></td>
		<td width=10%>
			<input align=left type="button" class="base" style ="cursor:hand;" onMouseOver="this.className='on'" onMouseOut="this.className='base'" onMouseDown="this.className='down'" onMouseUp="this.className='on'" value="직무명으로검색" onclick="javascript:histNameSearch()"></td>
		</td>
		<td align="right" width=15%> <input type="button" class="base" style ="cursor:hand;" onMouseOver="this.className='on'" onMouseOut="this.className='base'" onMouseDown="this.className='down'" onMouseUp="this.className='on'" value="전체검색" onclick="javascript:histSearch()"></td>
	</tr>
</table>
<%
if(empJobList!=null){
%>

<div id=D1 style="display" >
<Table  cellpadding=3 cellspacing=1 border=0 class=table1 width=100% id="table1">
	<tr>
		<td width="47%" class=TD1>직무코드</td>
		<td width="47%" class=TD1>제품/사업코드</td>
		<td width="6%" class=TD1>선택</td>
	</tr>

<%for(int i=0;i<empJobList.length;i++){
	Utility.fixNullAndTrimAll(empJobList[i]);
	%>
	<tr >
	    <input type=hidden name='jobTitle'   value='<%=empJobList[i].jobTitle%>' >
		<td width="47%" class=TD2>
			<input type="text" name='jobCd' readonly size=10 value='<%=empJobList[i].jobCd%>' style='border-style : hidden; width : 20%'>
			<input type="text" name='jobName' readonly value='<%=empJobList[i].jobName%>' style='border-style : hidden; width : 70%'>
	
		</td>
		<td width="47%" class=TD2>
			<input type="text" name='bizNo' readonly value='<%=empJobList[i].bizNo%>' size=10 style='border-style : hidden; width : 20%'>
			<input type="text" name='bizName' readonly value='<%=empJobList[i].bizName%>' style='border-style : hidden; width : 70%'>
		</td>
		<td width="6%" align=center class=TD2>
			<input type=button value='선택' class='base' style ="cursor:hand;" onMouseOver="this.className='on'" onMouseOut="this.className='base'" onclick="javascript:setEmpJob(<%=i%>)"></td>
	</tr>
<%}%>	
</table>
<input type="hidden" name='jobTitle'    >
<input type=text name='jobCd'    style=display:none>
<input type=text name='jobName'  style=display:none>
<input type=text name='bizNo'   style=display:none>
<input type=text name='bizName' style=display:none>

<%}else{%>

<Table  cellpadding=3 cellspacing=1 border=0 class=table1 width=100% id="table1">
	<tr>
		<td width="47%" class=TD1>직무코드</td>
		<td width="47%" class=TD1>제품/사업코드</td>
		<td width="6%" class=TD1>선택</td>
	</tr>
		<tr class='base' align='center'>
			<td colspan=3 align='center'>해당 자료가 없습니다.</td>
		</tr>
	</table>
<%}%>
</form>
</body>
</html>