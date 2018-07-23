<%@ page contentType="text/html;charset=ksc5601"%>
<%@ page  import="com.wms.beans.dto.HistJobDTO"%>
<%@ page import="com.wms.beans.dto.ProdDTO"    %>

<jsp:useBean id="codeFinder" class="com.wms.beans.CodeFinder" scope="application"/>
<jsp:useBean id="user" class="com.wms.beans.dto.UserInfo" scope="session"/>
<%	ProdDTO[] proddtos =(ProdDTO[])request.getAttribute("searchProd");%>
<%
    //ADD
	String prodNo = request.getParameter("prodNo");
	String prodName = request.getParameter("prodName");
	String prcsNo = request.getParameter("prcsNo");
	String strProdType = request.getParameter("prodType");;
	String jobCd = request.getParameter("jobCd");
	String bizNo  = request.getParameter("bizNo");
	String url    = request.getParameter("url");
	prodNo = (prodNo==null)?"":prodNo;
	prodName = (prodName==null)?"":prodName;
	strProdType = (strProdType==null)?"":strProdType;
	System.out.println("strProdType:"+strProdType);
	//ADD END 
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7, IE=EmulateIE9"/>
<meta http-equiv="Content-Language" content="ko">
<meta http-equiv="Content-Type" content="text/html; charset=ks_c_5601-1987">
<LINK rel="stylesheet" type="text/css" href="/css/skin04_form.css">
<title>사업코드 검색</title>
<script Language="JavaScript" src="/common/link/jobSearch.js"></script>
<script Language="JavaScript">
function selectEmpJob(prodNo,prodName){

	frm=document.forms[0]

	opener.bizReg(prodNo,prodName);
	self.close();
}
function changeSelect(){
	document.forms[0].submit();
}
function changePrcsMode(){
	var frm=document.forms[0];
	frm.prodType.value='P';
	document.all["divProdType"].innerHTML=frm.prodType.value+"<input name='prodNo' size='10' onkeydown='javascript:if(event.keyCode==13){searchProd();}'>";
	document.all["divKind1"].innerHTML="제품코드";
	document.all["divKind2"].innerHTML="제품명";
	document.all["divKind1_1"].innerHTML="제품코드";
	document.all["divKind2_1"].innerHTML="제품명";
}

function changeTaskMode(){
	var frm=document.forms[0];
	frm.prodType.value='T';	
	document.all["divProdType"].innerHTML=frm.prodType.value+"<input name='prodNo' size='10' onkeydown='javascript:if(event.keyCode==13){searchProd();}'>";
	document.all["divKind1"].innerHTML="Task코드";
	document.all["divKind2"].innerHTML="Task명";
	document.all["divKind1_1"].innerHTML="Task코드";
	document.all["divKind2_1"].innerHTML="Task명";
}

function changeBizMode(){
	var frm = document.forms[0];
	frm.action='/common//bizItemSearch.jsp?mode=biz'
	//frm.key.value="true";
	frm.submit();
}
function searchProd(){
	var frm = document.forms[0];
	frm._ACT.value='LRP';
	frm._SCREEN.value="/common/bizItemSearchProd.jsp?mode=prod";
	frm.action='/prodCon';		
	frm.submit();
}
</script>
</head>

<body text="#000000" bgcolor="#FFFFFF" topmargin=0 leftmargin=0 rightmargin=0>
<form method="POST" action='/common/bizItemSearchProd.jsp'>
<input type='hidden' name='_ACT' >
<input type='hidden' name='_SCREEN'>
<input type='hidden' name=prodType value=<%=strProdType%>>
<table cellpadding=1 cellspacing=1 class=table1 border=0 width=100%>
	<tr>
		<td class=td2>
			<table cellpadding=2 cellspacing=1 border=0 width=100%>
				<tr>
					<td width=20><img src="/images/i_formtitle.gif"></td><td><B>업무보고 >> 신규업무항목검색</B></td>
					<td align=right>
						<table cellpadding=0 cellspacing=0 border=0>
							<tr>
								<td width=5></td>
								<a href="javascript:select()">
								<td class="base"  style ="cursor:hand;" onMouseOver="this.className='on'" onMouseOut="this.className='base'" onMouseDown="this.className='down'" onMouseUp="this.className='on'">확인</td></a>
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
<Table  cellpadding=0 cellspacing=1 border=0 class=table1 width=100% >
<td width=100% >
	<Table  cellpadding=3 cellspacing=1 border=0 class=table1 width=100% >
		<td  width="20%" class=TD1 height=24>구&nbsp;분</td>
		<td  width="80%" class=TD2>
			<input type=radio id="rdoTmp" name="rdoTmp" value="PROD" onclick="javascript:changePrcsMode()" class=Attach <%=(strProdType.equals("P"))?"checked":" "%>>제품&nbsp; 
			<input type=radio id="rdoTmp" name="rdoTmp" value="TASK" onclick="javascript:changeTaskMode()" class=Attach <%=(strProdType.equals("T"))?"checked":" "%>>Task&nbsp; 
			<input type=radio id="rdoTmp" name="rdoTmp" value="JOB" class=Attach onclick="javascript:changeBizMode()">사업
	</table>
	</td>
</td>
</Table>

<table border=0><tr height=1><td></td></tr></table>
<Table  cellpadding=0 cellspacing=1 border=0 class=table1 width=100%>
<td width=100% >
	<Table  cellpadding=3 cellspacing=1 border=0 class=table1 width=100% >
	<tr>
					<td width="20%" class=TD1 height=24>
					<diV id="divKind1"><%=(strProdType.trim().equals("P"))?"제품코드":"Task코드"%></div>
					</td>
	
					<td class=TD2 height=20%>
					<div id="divProdType">						<%=(strProdType.trim().equals("P"))?"P":"T"%><input name='prodNo' size='10' value='<%=prodNo%>' onkeydown="javascript:if(event.keyCode==13){searchProd();}"></div></td>
					<td width="20%" class=TD1><div id="divKind2"><%=(strProdType.trim().equals("P"))?"제품명":"Task명"%></div></td>
					<td width="30%" class=TD2>
						<input name="prodName" size="20"  style="width=100%" value='' style="ime-mode:active"  value='<%=prodName%>' onkeydown="javascript:if(event.keyCode==13){searchProd();}">
					</td>

					<td width="10%" class=TD2 align='center'>
					<input type='button' value='검색' style='vertical-align : middle;  width : 100%;' onclick="javascript:searchProd()">
					</td>

	</tr>
<!--</Table>

<table border=0><tr height=1><td></td></tr></table>
<Table  cellpadding=1 cellspacing=1 border=0 class=table1 width=100% id="table1">
-->
<tr height=3><td colspan=5 class=TD2 ></td></tr>
	<tr>
		<td width="20%" class=TD1 height=24 ><div id="divKind1_1"><%=(strProdType.trim().equals("P"))?"제품코드":"Task코드"%></div></td>
		<td width="80%" class=TD1 colspan=4><div id="divKind2_1" ><%=(strProdType.trim().equals("P"))?"제품명":"Task명"%></td>
	</tr>	<%
	if(proddtos!=null){
		String bizName="";
		for(int i=0;i<proddtos.length;i++){

	%>
		<tr class='td2' style ="cursor:hand;" onMouseOver="this.className='leftnavi1'" onMouseOut="this.className='td2'"
	onclick="javascript:selectEmpJob( '<%=proddtos[i].prodType+proddtos[i].prodNo%>', '<%=proddtos[i].prodName%>')">
			<td width="20%" align="center" height=24><%=proddtos[i].prodType%><%=proddtos[i].prodNo%></td>
			<td width="80%" height=24 colspan=4><%=proddtos[i].prodName%> </td>
		</tr>
	<%}%>	
	<%}else{%>
		
			<tr class='base' align='center'>
				<td colspan=5 align='center'>해당 자료가 없습니다.</td>
			</tr>

	<%}%>
</table>
</table>
</form>
</body>
</html>
