<!--
	파 일 명  : WorkItemSearch.jsp
	작성일자  : 2004/06/22
	작 성 자  : 
	내    용  : 코드 검색 화면
-->
<%@ page contentType="text/html;charset=ksc5601"%>
<%@ page import="com.wms.fw.Utility"            %>
<%@ page import="com.wms.beans.dto.PrcsDTO"     %>
<%@ page import="com.wms.beans.dto.ProdDTO"     %>
<%@ page import="com.wms.beans.dto.HistJobDTO"     %>
<jsp:useBean id="user" class="com.wms.beans.dto.UserInfo" scope="session"/>
<%
	String rdo=request.getParameter("rdo");//직무,제품 선택
	String empId=request.getParameter("empId");
	if(empId==null)empId=user.empId;
	ProdDTO[] proddtos =(ProdDTO[])request.getAttribute("searchProd");
	HistJobDTO[] histList=null;
	if(rdo!=null&&rdo.equals("JOB")){
		histList=(HistJobDTO[])request.getAttribute("histJobList");
	}

	String prodNo = request.getParameter("prodNo");
	String prodName = request.getParameter("prodName");
	String prcsNo = request.getParameter("prcsNo");
	String strProdType = request.getParameter("prodType");;
	String jobCd = request.getParameter("jobCd");
	String bizNo  = request.getParameter("bizNo");
	String url    = request.getParameter("url");
	prodNo = (prodNo==null)?"":prodNo;
	prodName = (prodName==null)?"":prodName;
	strProdType = (strProdType==null)?"P":strProdType;
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7, IE=EmulateIE9"/>
<meta http-equiv="Content-Language" content="ko">
<meta http-equiv="Content-Type" content="text/html; charset=ks_c_5601-1987">
<LINK rel="stylesheet" type="text/css" href="../css/skin04_form.css">
<title>코드 검색</title>
<script Language="JavaScript" src="/common/link/jobSearch.js"></script>
<script Language="JavaScript" src="/common/link/common.js"></script>

<script Language="JavaScript">

function itemSearch(){
	document.forms[0]._ACT.value='LRP';
	document.forms[0]._SCREEN.value='/common/WorkItemSearch.jsp'
	document.forms[0].action='/prodCon';
	document.forms[0].target='';
	document.forms[0].empId.value='';
	document.forms[0].submit();
}

function confirmProd(no,name,prodType){
	document.forms[0].prodNo.value=no;
	document.forms[0].prodName.value=name;
	document.forms[0].prodType.value=prodType;
}

function changePrcsMode(){
	var frm=document.forms[0];
	frm.prodType.value='P';
	document.all["divProdType"].innerHTML=frm.prodType.value+"<input name='prodNo' size='10' onkeydown='javascript:if(event.keyCode==13){itemSearch();}'>";
	document.all["divKind1"].innerHTML="제품코드";
	document.all["divKind2"].innerHTML="제품명";
	document.all["divKind1_1"].innerHTML="제품코드";
	document.all["divKind2_1"].innerHTML="제품명";
}

function changeTaskMode(){
	var frm=document.forms[0];
	frm.prodType.value='T';	
	document.all["divProdType"].innerHTML=frm.prodType.value+"<input name='prodNo' size='10' onkeydown='javascript:if(event.keyCode==13){itemSearch();}'>";
	document.all["divKind1"].innerHTML="Task코드";
	document.all["divKind2"].innerHTML="Task명";
	document.all["divKind1_1"].innerHTML="Task코드";
	document.all["divKind2_1"].innerHTML="Task명";
}

function changeJobMode(){
	var frm=document.forms[0];
	frm.prodType.value='J';	
	frm.empId.value='<%=user.empId%>';	
	frm.target='';	
	
	rdoJob_click();
}

function searchPrcs(prodType, prodNo,prodName){
	var frm=document.forms[0];
	frm._SCREEN.value='/common/WorkItemPrcsSearch.jsp';
	frm._ACT.value='LR';
	frm.prodType.value=prodType;
	frm.prodNo.value=prodNo;
	frm.selProdName.value=prodName;
	frm.action='/prcsCon';
	openWin("","a2",500,400);
	frm.target='a2';
	frm.submit();
}

function selectItem(prodNo,prodName,prcsNo,prcsName,plStartDt,plEndDt,plMh){
	opener.selectItem(prodNo,prodName,prcsNo,prcsName,plStartDt,plEndDt,plMh);
	self.close();
};
</script>

</head>

<body text="#000000" bgcolor="#FFFFFF" topmargin=0 leftmargin=0 rightmargin=0>
<form method="POST" action="--WEBBOT-SELF--">
<input type='hidden' name='_ACT' >
<input type='hidden' name='_SCREEN'>
<input type='hidden' name='url' value='<%=url%>'>
<input type='hidden' name='startDt'>
<input type='hidden' name='endDt'>
<input type='hidden' name='planMh'>
<input type='hidden' name='prodType' value='<%=strProdType%>'>
<input type='hidden' name='selProdName' >
<input type='hidden' name='empId' value='<%=empId%>'>
<input type='hidden' name='idx' value='<%=request.getParameter("idx")%>'>

<table cellpadding=1 cellspacing=1 class=table1 border=0 width=100%>
	<tr>
		<td class=td2>
			<table cellpadding=2 cellspacing=1 border=0 width=100%>
				<tr>
					<td width=20><img src="/images/i_formtitle.gif"></td><td><B>업무보고 >> 신규업무항목선택</B></td>
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
<Table  cellpadding=3 cellspacing=1 border=0 class=table1 width=100%>
	<td  width="10%" class=TD1>작업구분</td>
	<td  width="15%" class=TD2>
		<input type=radio id="rdo" name="rdo" value="PROD" onclick="javascript:changePrcsMode()" class=Attach <%=(strProdType.equals("P"))?"checked":" "%>>제품&nbsp; 
		<input type=radio id="rdo" name="rdo" value="TASK" onclick="javascript:changeTaskMode()" class=Attach <%=(strProdType.equals("T"))?"checked":" "%>>Task&nbsp; 
		<input type=radio id="rdo" name="rdo" value="JOB" class=Attach onclick="javascript:changeJobMode()" <%=(strProdType.equals("J"))?"checked":" "%>>직무
	</td>
</Table>
<table border=0><tr height=1><td></td></tr></table>
<Table  cellpadding=0 cellspacing=1 border=0 class=table1 width=100%>
	<tr>
		<td width="70%">
			<Table  cellpadding=1 cellspacing=1 border=0 class=table1 width=100%>
				<tr id=rowa >
					<td width="20%" class=TD1><div id="divKind1"><%=(strProdType.trim().equals("P"))?"제품코드":"Task코드"%></div></td>
					<td width="20%" class=TD2><div id="divProdType"><%=(strProdType.trim().equals("P"))?"P":"T"%>
						<input name='prodNo' size='10' value='<%=prodNo%>' onkeydown="javascript:if(event.keyCode==13){itemSearch();}"></div></td>
					<td width="20%" class=TD1><div id="divKind2"><%=(strProdType.trim().equals("P"))?"제품명":"Task명"%></div></td>
					<td width="40%" class=TD2>
						<input name="prodName" size="20"  style="width=100%" value='<%=prodName%>' style="ime-mode:active"  onkeydown="javascript:if(event.keyCode==13){itemSearch();}"></td>
				</tr>
			</Table>
		</td>
		<td width="10%" class=td2>
			<Table  cellpadding=1 cellspacing=1 border=0 class=table1 width=100%>
				<tr>
					<td width=5 class="td2"></td>
					<a href="javascript:itemSearch()">
						<td class="base"  style ="cursor:hand;" onMouseOver="this.className='on'" onMouseOut="this.className='base'" onMouseDown="this.className='down'" onMouseUp="this.className='on'">검색</td>
					</a>
					<td width=5 class="td2"></td>
					<!--
					<a href="javascript:allSearch()">
						<td class="base"  style ="cursor:hand;" onMouseOver="this.className='on'" onMouseOut="this.className='base'" onMouseDown="this.className='down'" onMouseUp="this.className='on'">전체검색</td>
					</a>
					-->
				</tr>
			</Table>
		</td>
	</tr>
</Table>

<div id=D1 style="display" >
<table border=0><tr height=1><td></td></tr></table>
<Table  cellpadding=3 cellspacing=1 border=0 class=table1 width=100% id="table1">
	<tr>
		<td width="20%" class=TD1><div id="divKind1_1"><%=(strProdType.trim().equals("P"))?"제품코드":"Task코드"%></div></td>
		<td width="80%" class=TD1><div id="divKind2_1"><%=(strProdType.trim().equals("P"))?"제품명":"Task명"%></div></td>
	</tr>
</Table>
<Table  cellpadding=3 cellspacing=1 border=0 class=table1 width=100% id="table1">
	<%
	if(proddtos!=null){
		for(int i=0;i<proddtos.length;i++){
			//if(proddtos[i].prodStatus.equals("진행중")){
	%>
		<tr class='td2' style ="cursor:hand;" onMouseOver="this.className='leftnavi1'" onMouseOut="this.className='td2'" 
<%if(proddtos[i].prodStatus.equals("진행중")){%>
onclick="javascript:searchPrcs('<%=proddtos[i].prodType%>','<%=proddtos[i].prodNo%>','<%=proddtos[i].prodName%>')"
<%}else{%>
onclick="javascript:alert('제품이 진행중이 아닙니다.')"
<%}%>
>
			<td width="20%" align="center" height=24><%=proddtos[i].prodType%><%=proddtos[i].prodNo%></td>
			<td width="80%" height=24><%=proddtos[i].prodName%> </td>
		</tr>
	<%//}
		}%>	
	<%}else{%>
		<Table  cellpadding=3 cellspacing=1 border=0 class=table1 width=100% id="table1">
			<tr class='base' align='center'>
				<td colspan=2 align='center'>해당 자료가 없습니다.</td>
			</tr>
		</table>
	<%}%>
</table>
</div>
</form>
</body>
</html>
