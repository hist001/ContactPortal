<!--
	파 일 명  : prodSearch.jsp
	작성일자  : 2004/06/22
	작 성 자  : 
	내    용  : 통합 검색 화면
-->
<%@ page contentType="text/html;charset=ksc5601"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="com.wms.beans.dto.PrcsDTO"%>
<%
	PrcsDTO[] prcsdtos = (PrcsDTO[])request.getAttribute("prcsSearchList");
	String prodType =  request.getParameter("prodType");
	String empId = request.getParameter("empId");
	String strProdNo =  request.getParameter("prodNo");
	String strProdName = request.getParameter("selProdName");
	String url = request.getParameter("url");
	System.out.println("prcsdtos:"+prcsdtos);
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7, IE=EmulateIE9"/>
<meta http-equiv="Content-Language" content="ko">
<meta http-equiv="Content-Type" content="text/html; charset=ks_c_5601-1987">
<LINK rel="stylesheet" type="text/css" href="/css/skin04_form.css">
<script Language="JavaScript" src="/common/link/common.js"></script>
<title>코드 검색</title>
<script Language="JavaScript" src="/common/link/jobSearch.js"></script>
<script language='javascript'>
/*
function search(){
	document.forms[0]._ACT.value='LRP';
	if(document.forms[0].prodNo.value==null || document.forms[0].prodNo.value==''){
		document.forms[0].prodType.value='';
		document.forms[0].prodNo.value='';
	}else{
		if(isDigit(document.forms[0].prodNo.value.substring(0,1))){
			document.forms[0].prodType.value='';
			document.forms[0].prodNo.value=document.forms[0].prodNo.value.substring(0,document.forms[0].prodNo.value.length);
		}else{
			document.forms[0].prodType.value=document.forms[0].prodNo.value.substring(0,1);
			document.forms[0].prodNo.value=document.forms[0].prodNo.value.substring(1,document.forms[0].prodNo.value.length);
		}
	}
	document.forms[0].submit();
}
*/
</script>
</head>

<body text="#000000" bgcolor="#FFFFFF" topmargin=0 leftmargin=0 rightmargin=0>
<form method="POST" action="/prodCon">
<input type='hidden' name='_ACT'>
<input type='hidden' name='_SCREEN' value='/common/prodSearch.jsp'>
<input type='hidden' name='prodType' >
<input type='hidden' name='empId' value='<%=empId%>'>
<input type='hidden' name='url' value='<%=url%>'>
<input type='hidden' name='selProdName' value='<%=strProdName%>'>

<table cellpadding=1 cellspacing=1 class=table1 border=0 width=100%>
	<tr>
		<td class=td2>
			<table cellpadding=2 cellspacing=1 border=0 width=100%>
				<tr>
					<td width=20><img src="/images/i_formtitle.gif"></td><td><b>
					공정선택</b></td>
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
<%
if(prcsdtos!=null){%>

<div id=D1 style="display" >
<Table  cellpadding=3 cellspacing=1 border=0 class=table1 width=100% id="table1">
	<tr>
		<td width="20%" class=TD1>공정코드</td>
		<td width="80%" class=TD1>공정명</td>
	</tr>
</Table>
<Table  cellpadding=3 cellspacing=1 border=0 class=table1 width=100% id="table1">

	<%for(int i=0;i<prcsdtos.length;i++){
	%>
	<tr class='base' style ="cursor:hand;" onMouseOver="this.className='on'" onMouseOut="this.className='base'" onclick="javascript:selectItem('<%=prcsdtos[i].prodType+prcsdtos[i].prodNo%>','<%=strProdName%>','<%=prcsdtos[i].prcsNo%>','<%=prcsdtos[i].prcsName%>','<%=prcsdtos[i].plStartDt%>','<%=prcsdtos[i].plEndDt%>','<%=prcsdtos[i].plMh%>')">
		<td width="20%"  class='TD2' align="center"><%=prcsdtos[i].prcsNo%></td>
		<td width="80%"  class='TD2' align="left" ><%=prcsdtos[i].prcsName%></td>
	</tr>	
	<%}
	%>
</table>
<%}else{%>
<Table  cellpadding=3 cellspacing=1 border=0 class=table1 width=100% id="table1">
	<tr class='base' align='center'>
		<td colspan=2 align='center'>해당 자료가 없습니다.</td>
	</tr>
</table>
<%}%>
</form>
</body>
</html>