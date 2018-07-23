<!--
	파 일 명  : empItemSearch.jsp
	작성일자  : 2004/06/22
	작 성 자  : 
	내    용  : 직원 코드 검색 화면
-->
<%@ page contentType="text/html;charset=ksc5601" errorPage="/common/error.jsp"%>
<%@ page import="com.wms.beans.dto.*" %>
<jsp:useBean id="user" class="com.wms.beans.dto.UserInfo" scope="session"/>
<%
	EmpDTO[] empList=(com.wms.beans.dto.EmpDTO[])request.getAttribute("empSearch");
	PrcsDTO prcsdto = (PrcsDTO)request.getAttribute("prcsSubSearch");
	ProdDTO proddto = (ProdDTO)request.getAttribute("searchProd");	//prcsCon에서 가져오는 값
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7, IE=EmulateIE9"/>
<meta http-equiv="Content-Language" content="ko">
<meta http-equiv="Content-Type" content="text/html; charset=ks_c_5601-1987">
<LINK rel="stylesheet" type="text/css" href="/css/skin04_form.css">
<style>
<!--
.viewContentL {text-align:left;cursor:hand;border-style:solid; border-color:#EBEEF0 #EBEEF0 #EBEEF0 #EBEEF0;border-width:0 0 1 0}
.viewContentC {text-align:center;cursor:hand;border-style:solid; border-color:#EBEEF0 #EBEEF0 #EBEEF0 #EBEEF0;border-width:0 0 1 0}
.viewContentR {text-align:right;cursor:hand;border-style:solid; border-color:#EBEEF0 #EBEEF0 #EBEEF0 #EBEEF0;border-width:0 0 1 0}
-->
</style>
<title>코드 검색</title>
<script Language="JavaScript" >

function winClose(){
	self.close();
}

function selectItem(i){
	document.forms[0]._ACT.value='I';
	document.forms[0]._SCREEN.value='/Product/prcsEmpSearch.jsp';
	document.forms[0].empId.value=i;
	document.forms[0].submit();
	opener.reloadPrcsMan();
	self.close();
	
}

function itemSearch(){
	document.forms[0]._ACT.value='ER';
	document.forms[0]._SCREEN.value='/Product/empSearch.jsp';
	document.forms[0].empId.value=document.forms[0].txtEmpId.value;
	document.forms[0].empKName.value=document.forms[0].txtEmpKName.value;
	document.forms[0].submit();
}

</script>

</head>

<body text="#000000" bgcolor="#FFFFFF" topmargin=0 leftmargin=0 rightmargin=0>
<form method="POST" action="/commonCon">
<input type='hidden' name='_ACT'>
<input type='hidden' name='_SCREEN' >
<input type='hidden' name='prodType' value='P'>
<input type='hidden' name='empId' <%=user.empId%>>
<input type='hidden' name='prodNo' value='<%=prcsdto.prodNo%>'>
<input type='hidden' name='prcsNo' value='<%=prcsdto.prcsNo%>'>
<input type='hidden' name='empKName' >


<table cellpadding=1 cellspacing=1 class=table1 border=0 width=100%>
	<tr>
		<td class=td2>
			<table cellpadding=2 cellspacing=1 border=0 width=100%>
				<tr>
					<td width=20><img src="/images/i_formtitle.gif"></td><td><B>
					제품 >> 사원검색</B></td>
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
		<td width="30%">
			<Table  cellpadding=1 cellspacing=1 border=0 class=table1 width=100%>
				<tr id=rowa style=display>
					<td width="25%" class=TD1>사번</td>
					<td width="10%" class=TD2><input name="txtEmpId" size="10"  ></td>
				</tr>
			</Table>
		</td>
		<td width="30%">
			<Table  cellpadding=1 cellspacing=1 border=0 class=table1 width=100%>
				<tr id=rowa style=display>
					<td width="25%" class=TD1>성명</td>
					<td width="10%" class=TD2><input name="txtEmpKName" size="10" style="ime-mode:active;"></td>
				</tr>
			</Table>
		</td>
		<td width="20%" class=td2>
			<Table  cellpadding=1 cellspacing=1 border=0 class=table1 width=100%>
				<tr>
					<td width=5 class="td2"></td>
					<a href="javascript:itemSearch()">
						<td class="base"  style ="cursor:hand;" onMouseOver="this.className='on'" onMouseOut="this.className='base'" onMouseDown="this.className='down'" onMouseUp="this.className='on'">검색</td>
					</a>
				</tr>
			</Table>
		</td>
	</tr>
</Table>
<table border=0><tr height=1><td></td></tr></table>
<div id=D1 style="display" >
<Table  cellpadding=3 cellspacing=1 border=0 class=table1 width=100% id="table1">
	<tr>
		<td width="20%" class=TD1>사번</td>
		<td width="40%" class=TD1>성명</td>
		<td width="40%" class=TD1>소속</td>
	</tr>
</Table>
<Table  cellpadding=3 cellspacing=1 border=0 class=table1 width=100% id="table1">
<%if(empList!=null){%>
<%	for(int i=0;i<empList.length;i++){%>
	<tr class='base' style ="cursor:hand;" onMouseOver="this.className='on'" onMouseOut="this.className='base'" onclick="javascript:selectItem('<%=empList[i].empId%>')">
		<td width="5%" class='viewContentL'> </td>
		<td width="15%" class='viewContentL'><%=empList[i].empId%></td>
		<td width="40%" class='viewContentL'><%=empList[i].empKName%></td>
		<td width="40%" class='viewContentL'><%=empList[i].empOrgName%></td>
	</tr>
<%	}%>	
<%} else {%>
<tr>
	<td align=center width="100%"> 검색된 자료가 없습니다. </td>
</tr>
<%}%>	
</table>
</form>
</body>
</html>