<!--
	파 일 명  : unionSearch.jsp
	작성일자  : 2004/06/22
	작 성 자  : 
	내    용  : 통합 검색 화면
-->
<%@ page contentType="text/html;charset=ksc5601"%>
<%@ page import="java.util.Hashtable"%>
<%Hashtable searchList =(Hashtable)request.getAttribute("searchList");%>
<%String searchNo =  request.getParameter("searchNo");%>
<%String tableName = request.getParameter("tableName");%>
<%String flag=       request.getParameter("flag");
%>
<%
String title="조직";
if(tableName!=null&&!tableName.equals("org"))
	title=(tableName.equals("biz"))?"사업":"거래처";
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
<script Language="JavaScript" src="/common/link/common.js"></script>
<title>코드 검색</title>
<script Language="JavaScript" src="/common/link/jobSearch.js"></script>
<script language='javascript'>
<!--
function check(){
    //if(isEmpty(document.all["searchNo"].value)) {    	
    //		alert("입력하시오.");
    //		document.all["searchNo"].focus();    		    
    //} 
}
function confirmItem(no,name,flag){
 opener.confirmItem(no,name,flag);
 self.close();
}
//-->
</script>
</head>

<body text="#000000" bgcolor="#FFFFFF" topmargin=0 leftmargin=0 rightmargin=0>
<form method="POST" action="/commonCon">
<input type='hidden' name='_ACT' value='RU'>
<input type='hidden' name='_SCREEN' value='/common/unionSearch.jsp'>
<input type='hidden' name='tableName' value='<%=tableName%>'>
<input type='hidden' name='flag' value='<%=flag%>'>

<table cellpadding=1 cellspacing=1 class=table1 border=0 width=100%>
	<tr>
		<td class=td2>
			<table cellpadding=2 cellspacing=1 border=0 width=100%>
				<tr>
					<td width=20><img src="/images/i_formtitle.gif"></td><td><B>
					제품등록 >> <%=title%>코드검색</B></td>
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
					<td width="25%" class='viewContentL'><%=title%>코드</td>
					<td width="10%" class='viewContentL'><input name="searchNo" size="10" maxlength="5" onblur="javascript:check()"
					value='<%=(searchNo!=null)?searchNo:""%>'
					></td>
				</tr>
			</Table>
		</td>
		<td width="30%" class=td2>
			<Table  cellpadding=1 cellspacing=1 border=0 class=table1 width=100%>
				<tr>
					<td width=5 class="td2"></td>
					<a href="javascript:document.forms[0].submit()">
						<td class="base"  style ="cursor:hand;" onMouseOver="this.className='on'" onMouseOut="this.className='base'" onMouseDown="this.className='down'" onMouseUp="this.className='on'">검색</td>
					</a>
				</tr>
			</Table>
		</td>
	</tr>
</Table>
<table border=0><tr height=1><td></td></tr></table>
<%
if(searchList!=null){%>

<div id=D1 style="display" >
<Table  cellpadding=3 cellspacing=1 border=0 class=table1 width=100% id="table1">
	<tr>
		<td width="20%"  class='viewContentL'><%=title%>코드</td>
		<td width="80%"  class='viewContentL'><%=title%>명</td>
	</tr>
</Table>
<Table  cellpadding=3 cellspacing=1 border=0 class=table1 width=100% id="table1">

	<tr class='base' style ="cursor:hand;" onMouseOver="this.className='on'" onMouseOut="this.className='base'" onclick="javascript:confirmItem('<%=searchList.get("no")%>','<%=searchList.get("name")%>','<%=flag%>')">
		<td width="15%" class='viewContentL'><%=searchList.get("no")%></td>
		<td width="35%" class='viewContentL'><%=searchList.get("name")%></td>
	</tr>	
</table>
<%}else if(searchNo!=null){%>
<div id=D1 style="display" >
<Table  cellpadding=3 cellspacing=1 border=0 class=table1 width=100% id="table1">
	<tr>
		<td width="20%" class='viewContentL'><%=title%>코드</td>
		<td width="80%" class='viewContentL'><%=title%>명</td>
	</tr>
</Table>
<Table  cellpadding=3 cellspacing=1 border=0 class=table1 width=100% id="table1">

	<tr class='base' align='center'>
		<td colspan=2 align='center'>해당 자료가 없습니다.</td>
	</tr>
</table>
<%}%>
</form>
</body>
</html>