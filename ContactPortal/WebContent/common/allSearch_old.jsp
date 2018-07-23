<!--
	파 일 명  : allSearch.jsp
	작성일자  : 2004/06/22
	작 성 자  : 허대영
	내    용  : 통합 검색 화면
-->
<%@ page contentType="text/html;charset=ksc5601"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.util.ArrayList"%>

<%	//검색화면에서 테이블명에 따른 코드이름 표기
	String param =  request.getParameter("param");

	String tableName = new String();
	if(param.equals("biz")){
		tableName = "사업";
	}else if(param.equals("emp")){
		tableName = "직원";
	}else if(param.equals("org")||param.equals("actOrg")||param.equals("dutyOrg1")||param.equals("dutyOrg2")||param.equals("dutyOrg3")||param.equals("dutyOrg4")){
		tableName = "조직";
	}else if(param.equals("bizChag")){
		tableName = "거래처";
	}else if(param.equals("curEmp")||param.equals("subWrEmp")||param.equals("pmEmp")||param.equals("saleEmp")){
		tableName = "직원";
	}else if(param.equals("coCodeType")){
		tableName = "코드종류";
	}else if(param.equals("histJob")){
		tableName = "직무";
	}else if(param.equals("stdPrcsType")){
		tableName = "공정";
	}
%>

<%
	Hashtable returns = new Hashtable();
	ArrayList col1 = new ArrayList();
	ArrayList col2 = new ArrayList();
	ArrayList col3 = new ArrayList();
	
	returns =(Hashtable)request.getAttribute("returns");

%>

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

function confirmItem(param, code, name, orgName){
	opener.confirmItem(param, code, name, orgName);
	self.close();
}

function search(){
	var frm = document.forms[0];
	frm.action = '/commonCon';
	frm._ACT.value = 'AC';
	frm._SCREEN.value = '/common/allSearch.jsp';
	frm.param.value = "<%=param%>";
	frm.code.value=frm.code.value+"%";
	frm.name.value=frm.name.value+"%";
	frm.submit();
}

//-->
</script>
</head>

<body text="#000000" bgcolor="#FFFFFF" topmargin=0 leftmargin=0 rightmargin=0>
<form method="POST" >
<input type='hidden' name='_ACT' >
<input type='hidden' name='_SCREEN'>
<input type='hidden' name='param'>


<table cellpadding=1 cellspacing=1 class=table1 border=0 width=100%>
	<tr>
		<td class=td2>
			<table cellpadding=2 cellspacing=1 border=0 width=100%>
				<tr>
					<td width=20><img src="/images/i_formtitle.gif"></td><td><B><%=tableName%>검색</B></td>
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
				<tr id=rowa style=display>
					<td width="25%" height="24" class=TD1><%=tableName%>검색(조건1)</td>
					<td width="10%" height="24" class=TD2><input name="code" size="10" maxlength="10" onkeydown="javascript:if(event.keyCode==13){search();}"></td>
					<td width="25%" height="24" class=TD1><%=tableName%>검색(조건2)</td>
					<td width="10%" height="24" class=TD2><input name="name" size="15" maxlength="15" style="ime-mode:active;" onkeydown="javascript:if(event.keyCode==13){search();}"></td>
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
<%
if(returns!=null){   
	if(tableName.equals("직원")/*||tableName.equals("표준공정코드타입")*/){
%>

<div id=D1 style="display" >
<Table  cellpadding=0 cellspacing=1 border=0 class=table1 width=100% id="table1">
	<tr>
		<td width="35%" height="24" class=TD1>조직(조건1)</td>
		<td width="35%" height="24" class=TD1>성명(조건2)</td>		
		<td width="30%" height="24" class=TD1>사번</td>
	</tr>
</Table>
<Table  cellpadding=0 cellspacing=1 border=0 class=table1 width=100% id="table1">

	<%	col1 = (ArrayList)returns.get("code"); 
		col2 = (ArrayList)returns.get("name");
		col3 = (ArrayList)returns.get("orgName");
		for(int i=0 ; i<col1.size() ; i++){    %>

	<tr class='td2' style ="cursor:hand;" onMouseOver="this.className='leftnavi1'" onMouseOut="this.className='td2'" onclick="javascript:confirmItem('<%=param%>','<%=col1.get(i)%>','<%=col2.get(i)%>','<%=col3.get(i)%>')" >
		<td width="35%" height="24" align="left"><%=(String)col3.get(i)%></td>
		<td width="35%" height="24" align="left"><%=(String)col2.get(i)%></td>		
		<td width="30%" height="24" align="center"><%=(String)col1.get(i)%></td>
	</tr>	

		<%}%>
</table>
	<%}else {	%>
<div id=D1 style="display" >
<Table  cellpadding=0 cellspacing=1 border=0 class=table1 width=100% id="table1">
	<tr>
		<td width="30%" height="24" class=TD1><%=tableName%>코드(조건1)</td>
		<td width="70%" height="24" class=TD1><%=tableName%>명(조건2)</td>
	</tr>
</Table>
<Table  cellpadding=0 cellspacing=1 border=0 class=table1 width=100% id="table1">

	<%	col1 = (ArrayList)returns.get("code"); 
		col2 = (ArrayList)returns.get("name");
		col3 = (ArrayList)returns.get("orgName");
		for(int i=0 ; i<col1.size() ; i++){    %>

	<tr class='td2' style ="cursor:hand;" onMouseOver="this.className='leftnavi1'" onMouseOut="this.className='td2'" onclick="javascript:confirmItem('<%=param%>','<%=col1.get(i)%>','<%=col2.get(i)%>','<%=(col3!=null)?(String)col3.get(i):""%>')" >
		<td width="30%" height="24" align="center"><%=(String)col1.get(i)%></td>
		<td width="70%" height="24" align="left"><%=(String)col2.get(i)%></td>
	</tr>	
	
	<%	}%>
</table>
<%	}%>
<%}else if(returns==null){ %>
<div id=D1 style="display" >
<Table  cellpadding=0 cellspacing=1 border=0 class=table1 width=100% id="table1">
	<tr>
		<td width="30%" height="24" class=TD1><%=tableName%>코드(조건1)</td>
		<td width="70%" height="24" class=TD1><%=tableName%>명(조건2)</td>
	</tr>
</Table>
<Table  cellpadding=0 cellspacing=1 border=0 class=table1 width=100% id="table1">

	<tr class='base' style ="cursor:hand;" onMouseOver="this.className='on'" onMouseOut="this.className='base'" onclick="javascript:confirmItem('<%=param%>','','','')">
		<td colspan=2 height="24" align='center'>해당 자료가 없습니다(클릭하면 해당컬럼에 공백이 들어갑니다).</td>
	</tr>
</table>
<%}%>
</form>
</body>
</html>