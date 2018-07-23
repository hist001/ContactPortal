<!--
	파 일 명  : allSearch.jsp
	작성일자  : 2004/06/22
	작 성 자  : 허대영
	내    용  : 통합 검색 화면
-->
<%System.out.println("allSearch.jsp");%>
<%@ page contentType="text/html;charset=ksc5601" errorPage="/common/error.jsp"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.util.ArrayList"%>

<jsp:useBean id="user" class="com.wms.beans.dto.UserInfo" scope="session"/>

<%
try{
System.out.println("allSearch.jsp");
	//검색화면에서 테이블명에 따른 코드이름 표기
	String param =  request.getParameter("param");
	String empId =  request.getParameter("empId");

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
		tableName = "코드타입";
	}else if(param.equals("histJob")){
		tableName = "직무";
	}else if(param.equals("teamJob")){
		tableName = "팀직무";
	}else if(param.equals("stdPrcsType")){
		tableName = "공정";
	}else if(param.equals("prod")){
		tableName = "제품";
	}else if(param.equals("oppt")){
		tableName = "수주";
	}else if(param.equals("opptCurEmp")){
		tableName = "직원";
	}
%>

<%
	Hashtable returns = new Hashtable();
	ArrayList col1 = new ArrayList();
	ArrayList col2 = new ArrayList();
	ArrayList col3 = new ArrayList();
	ArrayList col4 = null;
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
//	alert(param+"::"+"::"+ code+"::"+"::"+name+"::"+"::"+orgName);
	opener.confirmItem(param, code, name, orgName);
	self.close();
}


function bizAcqChk(){
	var frm = document.forms[0];
	alert(frm.bizAcqName.value);
}

function search(){
	var frm = document.forms[0];
	frm.action = '/commonCon';
	frm._ACT.value = 'AC';
	frm._SCREEN.value = '/common/allSearch.jsp';
	frm.param.value = "<%=param%>";
	if('<%=param%>'=='teamJob'){
		frm.empId.value="<%=empId%>";
	}
	frm.code.value=frm.code.value.toUpperCase();
	frm.name.value=frm.name.value.toUpperCase();
	frm.submit();
}


function searchCodeFocus(){
	var frm = document.forms[0];
	frm.name.focus();
}


function searchCode(){
	var frm = document.forms[0];
	frm.action = '/commonCon';
	frm._ACT.value = 'AC';
	frm._SCREEN.value = '/common/allSearch.jsp';
	frm.param.value = "<%=param%>";
	if('<%=param%>'=='teamJob'){
		frm.empId.value="<%=empId%>";
	}
//	frm.code.value=frm.code.options[frm.code.selectedIndex].value;
	frm.name.value=frm.name.value.toUpperCase();	
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
<input type='hidden' name='empId' value=''>


<table cellpadding=1 cellspacing=1 class=table1 border=0 width=100%>
	<tr>
		<td class=td2>
			<table cellpadding=2 cellspacing=1 border=0 width=100%>
				<tr>
					<td width=20><img src="../images/i_formtitle.gif"></td><td><B><%=tableName%>검색</B></td>
					<td align=right>
						<table cellpadding=0 cellspacing=0 border=0>
							<tr><td width=5></td>
								<td >
								<Table cellpadding="0" cellspacing="0" border="0" class="table2" width="100%">
									<tr >
										<td width="5" class="td2"></td>
										<td class="bt_f" nowrap></td>
										<td class='button' width='50' style="cursor:hand;"
											onMouseOver="this.className='buttonOn'"
											onMouseOut="this.className='button'"
											onclick="self.close()">닫기</td>
										<td class="bt_e" nowrap></td>						
									</tr>
								</Table>									
								</td>
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
if(tableName.equals("직원")){
 if(returns!=null){   
%>
<Table  cellpadding=0 cellspacing=1 border=0 class=table1 width=100%>
	<tr>
		<td width="85%">
			<Table  cellpadding=0 cellspacing=0 border=0 class=table1 width=100%>
				<tr>
					<td width="25%" height="24" class=TD1>조직검색</td>
					<td width="10%" height="24" class=TD2><input name="code" size="10" maxlength="10" onkeydown="javascript:if(event.keyCode==13){search();}"></td>
					<td width="25%" height="24" class=TD1>성명검색</td>
					<td width="10%" height="24" class=TD2><input name="name" size="15" maxlength="15" style="ime-mode:active;" onkeydown="javascript:if(event.keyCode==13){search();}"></td>
				</tr>
			</Table>
		</td>
		<td width="15%" class=td2>
			<Table  cellpadding=0 cellspacing=0 border=0 class=table1 width=100%>
				<tr>
					<td width=5 class="td2"></td>
						<td >
							<Table cellpadding="0" cellspacing="0" border="0" class="table2" width="100%">
								<tr >
									<td width="5" class="td2"></td>
									<td class="bt_f" nowrap></td>
									<td class='button' width='50' style="cursor:hand;"
										onMouseOver="this.className='buttonOn'"
										onMouseOut="this.className='button'"
										onclick="javascript:searchCode()">검색</td>
									<td class="bt_e" nowrap></td>						
								</tr>
							</Table>							
						</td>
				</tr>
			</Table>
		</td>
	</tr>
</Table>
<table border=0><tr height=1><td></td></tr></table>
<Table  cellpadding=0 cellspacing=1 border=0 class=table1 width=100% id="table1">
	<tr>
		<td width="35%" height="24" class=TD1>조직</td>
		<td width="35%" height="24" class=TD1>성명</td>		
		<td width="30%" height="24" class=TD1>사번</td>
	</tr>
</Table>
<Table  cellpadding=0 cellspacing=1 border=0 class=table1 width=100% id="table1">
	<%	col1 = (ArrayList)returns.get("code"); 
		col2 = (ArrayList)returns.get("name");
		col3 = (ArrayList)returns.get("orgName");
		if(param.equals("opptCurEmp"))col4=(ArrayList)returns.get("orgCd");
		for(int i=0 ; i<col1.size() ; i++){    %>
	<tr class='td2' style ="cursor:hand;" onMouseOver="this.className='leftnavi1'" onMouseOut="this.className='td2'" onclick="javascript:confirmItem('<%=(param.equals("opptCurEmp"))?col4.get(i):param%>','<%=col1.get(i)%>','<%=col2.get(i)%>','<%=col3.get(i)%>')" >
		<td width="35%" height="24" align="left"><%=(String)col3.get(i)%></td>
		<td width="35%" height="24" align="center"><%=(String)col2.get(i)%></td>		
		<td width="30%" height="24" align="center"><%=(String)col1.get(i)%></td>
	</tr>	
       <%} %>
</table>		                    

<% }else if(returns==null) {%>

<Table  cellpadding=0 cellspacing=1 border=0 class=table1 width=100%>
	<tr>
		<td width="85%">
			<Table  cellpadding=0 cellspacing=0 border=0 class=table1 width=100%>
				<tr>
					<td width="25%" height="24" class=TD1>조직검색</td>
					<td width="10%" height="24" class=TD2><input name="code" size="10" maxlength="10" onkeydown="javascript:if(event.keyCode==13){search();}"></td>
					<td width="25%" height="24" class=TD1>성명검색</td>
					<td width="10%" height="24" class=TD2><input name="name" size="15" maxlength="15" style="ime-mode:active;" onkeydown="javascript:if(event.keyCode==13){search();}"></td>
				</tr>
			</Table>
		</td>
		<td width="15%" class=td2>
			<Table  cellpadding=0 cellspacing=0 border=0 class=table1 width=100%>
				<tr>
					<td width=5 class="td2"></td>
					<td>
						<Table cellpadding="0" cellspacing="0" border="0" class="table2" width="100%">
							<tr >
								<td width="5" class="td2"></td>
								<td class="bt_f" nowrap></td>
								<td class='button' width='50' style="cursor:hand;"
									onMouseOver="this.className='buttonOn'"
									onMouseOut="this.className='button'"
									onclick="javascript:searchCode()">검색</td>
								<td class="bt_e" nowrap></td>						
							</tr>
						</Table>
					</td>
				</tr>
			</Table>
		</td>
	</tr>
</Table>
<table border=0><tr height=1><td></td></tr></table>
<Table  cellpadding=0 cellspacing=1 border=0 class=table1 width=100% id="table1">
	<tr>
		<td width="35%" height="24" class=TD1>조직</td>
		<td width="35%" height="24" class=TD1>성명</td>		
		<td width="30%" height="24" class=TD1>사번</td>
	</tr>
</Table>
<Table  cellpadding=0 cellspacing=1 border=0 class=table1 width=100% id="table1">
	<tr class='base' style ="cursor:hand;" onMouseOver="this.className='on'" onMouseOut="this.className='base'" onclick="javascript:confirmItem('<%=param%>','','','')">
		<td colspan=2 height="24" align='center'>해당 자료가 없습니다(클릭하면 해당컬럼에 공백이 들어갑니다).</td>
	</tr>
</table>

<%   }%>

<% }else if(tableName.equals("직무")){
		                     if(returns!=null){   
%>

<Table  cellpadding=0 cellspacing=1 border=0 class=table1 width=100%>
	<tr>
		<td width="85%">
			<Table  cellpadding=0 cellspacing=0 border=0 class=table1 width=100%>
				<tr>
					<td width="25%" height="24" class=TD1>대표<%=tableName%>부문</td>
					
					<!--
					<td width="10%" height="24" class=TD2>

						<select name="code"  onchange='javascript:searchCodeFocus()'>
						<option value = ""  ></option>
						<option value = "1" >GIS</option>
						<option value = "2" >SI</option>
						<option value = "3" >SM</option>
						<option value = "4" >경영지원</option>
						</select>					
					-->
							<td width="10%" height="24" class=TD2>
								<input name="code" size="10" maxlength="10" onkeydown="javascript:if(event.keyCode==13){searchCode();}">
							</td>
					
					<td width="25%" height="24" class=TD1><%=tableName%>명</td>
					<td width="10%" height="24" class=TD2><input name="name" size="15" maxlength="15" style="ime-mode:active;" onkeydown="javascript:if(event.keyCode==13){searchCode();}"></td>
				</tr>
			</Table>
		</td>
		<td width="15%" class=td2>
			<Table  cellpadding=0 cellspacing=0 border=0 class=table1 width=100%>
				<tr>
					<td width=5 class="td2"></td>
					<td>
						<Table cellpadding="0" cellspacing="0" border="0" class="table2" width="100%">
							<tr >
								<td width="5" class="td2"></td>
								<td class="bt_f" nowrap></td>
								<td class='button' width='50' style="cursor:hand;"
									onMouseOver="this.className='buttonOn'"
									onMouseOut="this.className='button'"
									onclick="javascript:searchCode()">검색</td>
								<td class="bt_e" nowrap></td>						
							</tr>
						</Table>
					</td>				
				</tr>
			</Table>
		</td>
	</tr>
</Table>
<table border=0><tr height=1><td></td></tr></table>	
<Table  cellpadding=0 cellspacing=1 border=0 class=table1 width=100% id="table1">
	<tr>
		<td width="46%" height="24" class=TD1><%=tableName%>코드</td>
		<td width="54%" height="24" class=TD1><%=tableName%>명</td>
	</tr>
</Table>
<Table  cellpadding=0 cellspacing=1 border=0 class=table1 width=100% id="table1">
	<%	col1 = (ArrayList)returns.get("code"); 
		col2 = (ArrayList)returns.get("name");
		col3 = (ArrayList)returns.get("orgName");
		for(int i=0 ; i<col1.size() ; i++){    %>

	<tr class='td2' style ="cursor:hand;" onMouseOver="this.className='leftnavi1'" onMouseOut="this.className='td2'" onclick="javascript:confirmItem('<%=param%>','<%=col1.get(i)%>','<%=col2.get(i)%>','<%=(col3!=null)?(String)col3.get(i):""%>')" >
		<td width="46%" height="24" align="center" ><%=(String)col1.get(i)%></td>
		<td width="54%" height="24" align="left">&nbsp;<%=(String)col2.get(i)%></td>
	</tr>	
	<%	   }%>
</table>	
	
<%	} else if(returns==null) {%>

<Table  cellpadding=0 cellspacing=1 border=0 class=table1 width=100%>
	<tr>
		<td width="85%">
			<Table  cellpadding=0 cellspacing=0 border=0 class=table1 width=100%>
				<tr>
					<td width="25%" height="24" class=TD1>대표<%=tableName%>부문</td>
		<!--

					<td width="10%" height="24" class=TD2>
						<select name="code"  onchange='javascript:searchCodeFocus()'>
						<option value = ""  ></option>
						<option value = "1" >GIS</option>
						<option value = "2" >SI</option>
						<option value = "3" >SM</option>
						<option value = "4" >경영지원</option>
						</select>					
					</td>
		-->

							<td width="10%" height="24" class=TD2>
								<input name="code" size="10" maxlength="10" onkeydown="javascript:if(event.keyCode==13){search();}">
							</td>

					<td width="25%" height="24" class=TD1><%=tableName%>명</td>
					<td width="10%" height="24" class=TD2><input name="name" size="15" maxlength="15" style="ime-mode:active;" onkeydown="javascript:if(event.keyCode==13){search();}"></td>
				</tr>
			</Table>
		</td>
		<td width="15%" class=td2>
			<Table  cellpadding=0 cellspacing=0 border=0 class=table1 width=100%>
				<tr>
					<td width=5 class="td2"></td>
					<td>
						<Table cellpadding="0" cellspacing="0" border="0" class="table2" width="100%">
							<tr >
								<td width="5" class="td2"></td>
								<td class="bt_f" nowrap></td>
								<td class='button' width='50' style="cursor:hand;"
									onMouseOver="this.className='buttonOn'"
									onMouseOut="this.className='button'"
									onclick="javascript:searchCode()">검색</td>
								<td class="bt_e" nowrap></td>						
							</tr>
						</Table>
					</td>								</tr>
			</Table>
		</td>
	</tr>
</Table>
<table border=0><tr height=1><td></td></tr></table>
<Table  cellpadding=0 cellspacing=1 border=0 class=table1 width=100% id="table1">
	<tr>
		<td width="30%" height="24" class=TD1><%=tableName%>코드</td>
		<td width="70%" height="24" class=TD1><%=tableName%>명</td>
	</tr>
</Table>

<Table  cellpadding=0 cellspacing=1 border=0 class=table1 width=100% id="table1">

	<tr class='base' style ="cursor:hand;" onMouseOver="this.className='on'" onMouseOut="this.className='base'" onclick="javascript:confirmItem('<%=param%>','','','')">
		<td colspan=2 height="24" align='center'>해당 자료가 없습니다(클릭하면 해당컬럼에 공백이 들어갑니다).</td>
	</tr>
</table>




<%   }%>

<%} else {	%>
<%		if(returns!=null){	%>
<Table  cellpadding=0 cellspacing=1 border=0 class=table1 width=100%>
	<tr>
		<td width="85%">
			<Table  cellpadding=0 cellspacing=0 border=0 class=table1 width=100%>
				<tr>
					<td width="25%" height="24" class=TD1><%=tableName%>코드</td>
					<td width="10%" height="24" class=TD2><input name="code" size="10" maxlength="10" onkeydown="javascript:if(event.keyCode==13){search();}"></td>
					<td width="25%" height="24" class=TD1><%=tableName%>명</td>
					<td width="10%" height="24" class=TD2><input name="name" size="15" maxlength="15" style="ime-mode:active;" onkeydown="javascript:if(event.keyCode==13){search();}"></td>
				</tr>
			</Table>
		</td>
		<td width="15%" class=td2>
			<Table  cellpadding=0 cellspacing=0 border=0 class=table1 width=100%>
				<tr>
					<td width=5 class="td2"></td>
					<td>
						<Table cellpadding="0" cellspacing="0" border="0" class="table2" width="100%">
							<tr >
								<td width="5" class="td2"></td>
								<td class="bt_f" nowrap></td>
								<td class='button' width='50' style="cursor:hand;"
									onMouseOver="this.className='buttonOn'"
									onMouseOut="this.className='button'"
									onclick="javascript:search">검색</td>
								<td class="bt_e" nowrap></td>						
							</tr>
						</Table>
					</td>				
				</tr>
			</Table>
		</td>
	</tr>
</Table>
<table border=0><tr height=1><td></td></tr></table>	
<Table  cellpadding=0 cellspacing=1 border=0 class=table1 width=100% id="table1">

	<tr>
		<td width="46%" height="24" class=TD1><%=tableName%>코드
</td>
		<td width="54%" height="24" class=TD1><%=tableName%>명</td>
	</tr>
</Table>

<Table  cellpadding=0 cellspacing=1 border=0 class=table1 width=100% id="table1">
	<%	col1 = (ArrayList)returns.get("code"); 
		col2 = (ArrayList)returns.get("name");
		col3 = (ArrayList)returns.get("orgName");
		for(int i=0 ; i<col1.size() ; i++){    %>

	<tr class='td2' style ="cursor:hand;" onMouseOver="this.className='leftnavi1'" onMouseOut="this.className='td2'" onclick="javascript:confirmItem('<%=param%>','<%=col1.get(i)%>','<%=col2.get(i)%>','<%=(col3!=null)?(String)col3.get(i):""%>')" >
		<td width="46%" height="24" align="center" ><%=(String)col1.get(i)%></td>
		<td width="54%" height="24" align="left">&nbsp;<%=(String)col2.get(i)%></td>
	</tr>	
	<%	   }%>
	 <% // 영업팀에 관련한 영업보고용 거래처명 text등록
	 if(user.empOrgNo.equals("1259")|| user.empOrgNo.equals("1024")||user.empOrgNo.equals("1001")||user.empOrgNo.equals("1412")||user.empOrgNo.equals("1411")
			 || user.empOrgNo.equals("1555")
			 || user.empOrgNo.equals("1559")
			 || user.empOrgNo.equals("1560")
			 || user.empOrgNo.equals("1561")
			 || user.empOrgNo.equals("1562")
			 || user.empOrgNo.equals("1563")
			 || user.empOrgNo.equals("1564")
			 || user.empOrgNo.equals("1565")
			 || user.empOrgNo.equals("1554")
			 || user.empOrgNo.equals("1566")
			 || user.empOrgNo.equals("1567")
			 || user.empOrgNo.equals("1568")
			 || user.empOrgNo.equals("1569")
			 || user.empOrgNo.equals("1570")
			 || user.empOrgNo.equals("1556")
			 || user.empOrgNo.equals("1574")
			 || user.empOrgNo.equals("1573")
			 || user.empOrgNo.equals("1557")
			 || user.empOrgNo.equals("1571")
			 || user.empOrgNo.equals("1572") 	 
	 )
	 {%>
<%--
<Table  align=center cellpadding=0 cellspacing=1 border=0 class=table2  id="table1">
	<tr align=center>
	<td >
		<table cellpadding=0 cellspacing=0 border=0 width=100%>
			<tr width=100%><td width=230 align=right>미등록 거래처명을 입력하세요.</td>
			<td>
			<input name="text"  id=bizAcqName  name=bizAcqName  size="50" maxlength="50" ></td>
			<td >
				<Table cellpadding="0" cellspacing="0" border="0" class="table2" width="100%">
					<tr >
					<td width="5" class="td2"></td>
					<td class="bt_f" nowrap></td>
					<td class='button' width='50' style="cursor:hand;"
					onMouseOver="this.className='buttonOn'"
					onMouseOut="this.className='button'"
					onclick="javascript:confirmItem('<%=param%>',document.forms[0].bizAcqName.value,document.forms[0].bizAcqName.value,'')">등록</td>
					<td class="bt_e" nowrap></td>						
					</tr>
				</Table>									
			</td>
			</tr>
		</table>
	</td>
	</tr>
</table>
 --%>
<%}%>
</table>
	
<%	} else if(returns==null) {%>

<Table  cellpadding=0 cellspacing=1 border=0 class=table1 width=100%>
	<tr>
		<td width="85%">
			<Table  cellpadding=0 cellspacing=0 border=0 class=table1 width=100%>
				<tr>
					<td width="25%" height="24" class=TD1>코드</td>
					<td width="10%" height="24" class=TD2><input name="code" size="10" maxlength="10" onkeydown="javascript:if(event.keyCode==13){search();}"></td>
					<td width="25%" height="24" class=TD1>명</td>
					<td width="10%" height="24" class=TD2><input name="name" size="15" maxlength="15" style="ime-mode:active;" onkeydown="javascript:if(event.keyCode==13){search();}"></td>
				</tr>
			</Table>
		</td>
		<td width="15%" class=td2>
			<Table  cellpadding=0 cellspacing=0 border=0 class=table1 width=100%>
				<tr>
					<td width=5 class="td2"></td>
					<td>
						<Table cellpadding="0" cellspacing="0" border="0" class="table2" width="100%">
							<tr >
								<td width="5" class="td2"></td>
								<td class="bt_f" nowrap></td>
								<td class='button' width='50' style="cursor:hand;"
									onMouseOver="this.className='buttonOn'"
									onMouseOut="this.className='button'"
									onclick="javascript:search()">검색</td>
								<td class="bt_e" nowrap></td>						
							</tr>
						</Table>
					</td>				
				</tr>
			</Table>
		</td>
	</tr>
</Table>
<table border=0><tr height=1><td></td></tr></table>
<Table  cellpadding=0 cellspacing=1 border=0 class=table1 width=100% id="table1">
	<tr>
		<td width="30%" height="24" class=TD1><%=tableName%>코드</td>
		<td width="70%" height="24" class=TD1><%=tableName%>명</td>
	</tr>
</Table>

<Table  cellpadding=0 cellspacing=1 border=0 class=table2 width=100% id="table1">
	<tr onMouseOver="this.className='on'" onMouseOut="this.className=''" onclick="javascript:confirmItem('<%=param%>','','','')">
		<td colspan=2 height="24" align='center'>해당 자료가 없습니다(클릭하면 해당컬럼에 공백이 들어갑니다).</td>
	</tr>
</table>


<%  } %>
<%} %>
</form>
</body>
</html>
<%}catch(Exception e){e.printStackTrace();}%>