<!--
	파 일 명  : orgSearch.jsp
	작성일자  : 2004/06/22
	작 성 자  : 
	내    용  : 코드 검색 화면
-->
<%@ page contentType="text/html;charset=ksc5601"%>
<%com.wms.beans.dto.ProdDTO[] prodList=(com.wms.beans.dto.ProdDTO[])request.getAttribute("prodList");%>
<%
String prodNo = request.getParameter("prodNo");
String bizNo  = request.getParameter("bizNo");
String url    = request.getParameter("url");
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7, IE=EmulateIE9"/>
<meta http-equiv="Content-Language" content="ko">
<meta http-equiv="Content-Type" content="text/html; charset=ks_c_5601-1987">
<LINK rel="stylesheet" type="text/css" href="/css/skin04_form.css">
<script Language="JavaScript" src="/common/link/number.js"></script>
<style>
<!--
.viewContentL {text-align:left;cursor:hand;border-style:solid; border-color:#EBEEF0 #EBEEF0 #EBEEF0 #EBEEF0;border-width:0 0 1 0}
.viewContentC {text-align:center;cursor:hand;border-style:solid; border-color:#EBEEF0 #EBEEF0 #EBEEF0 #EBEEF0;border-width:0 0 1 0}
.viewContentR {text-align:right;cursor:hand;border-style:solid; border-color:#EBEEF0 #EBEEF0 #EBEEF0 #EBEEF0;border-width:0 0 1 0}
-->
</style>
<title>코드 검색</title>
<script Language="JavaScript" src="/common/link/jobSearch.js"></script>
<script language='javascript'>
<!--
function numberCheck(strInput){
    if(!isEmpty(document.all["orgNo"].value)) {
    	if(!isInteger(document.all["orgNo"].value)) {
    		alert("정수만 입력할수 있습니다");
    		document.all["orgNo"].focus();
    		//document.form.orgNo.focus();
    		
    	}
    } 
}
//-->
</script>
</head>

<body text="#000000" bgcolor="#FFFFFF" topmargin=0 leftmargin=0 rightmargin=0>
<form method="POST" action="--WEBBOT-SELF--">
<input type='hidden' name='_ACT' >
<input type='hidden' name='_SCREEN'>
<input type='hidden' name='url' value='<%=url%>'>
<input type='hidden' name='prodName'>
<input type='hidden' name='prcsNo'>
<input type='hidden' name='prcsName'>
<input type='hidden' name='startDt'>
<input type='hidden' name='endDt'>
<input type='hidden' name='planMh'>



<table cellpadding=1 cellspacing=1 class=table1 border=0 width=100%>
	<tr>
		<td class=td2>
			<table cellpadding=2 cellspacing=1 border=0 width=100%>
				<tr>
					<td width=20><img src="/images/i_formtitle.gif"></td><td><B>
					제품등록 >> 조직코드검색</B></td>
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
		<td width="70%">
			<Table  cellpadding=1 cellspacing=1 border=0 class=table1 width=100%>
				<tr id=rowa style=display>
					<td width="25%" class=TD1>조직코드</td>
					<td width="10%" class=TD2><input name="orgNo" size="10" maxlength="5" onblur="javascript:numberCheck('orgNo')"></td>
				</tr>
			</Table>
		</td>
		<td width="30%" class=td2>
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
<%
if(prodList!=null){%>

<div id=D1 style="display" >
<Table  cellpadding=3 cellspacing=1 border=0 class=table1 width=100% id="table1">
	<tr>
		<td width="20%" class=TD1>조직코드</td>
		<td width="80%" class=TD1>조직명</td>
	</tr>
</Table>
<Table  cellpadding=3 cellspacing=1 border=0 class=table1 width=100% id="table1">
<%for(int i=0;i<prodList.length;i++){%>
	<tr class='base' style ="cursor:hand;" onMouseOver="this.className='on'" onMouseOut="this.className='base'" onclick="javascript:selectItem('<%=prodList[i].prodNo%>','<%=prodList[i].prodName%>','<%=prodList[i].bizNo%>','<%=prodList[i].bizCqCd%>','<%=prodList[i].contDt%>','<%=prodList[i].delIDt%>','<%=prodList[i].goalMh%>'
)">
		<td width="5%" class='viewContentL'> </td>
		<td width="10%" class='viewContentL'><%=prodList[i].prodNo%></td>
		<td width="35%" class='viewContentL'><%=prodList[i].prodName%></td>
		<td width="15%" class='viewContentL'><%=prodList[i].bizNo%></td>
		<td width="35%" class='viewContentL'><%=prodList[i].bizCqCd%></td>
	</tr>
<%}%>	
</table>
<%}%>
</form>
</body>
</html>