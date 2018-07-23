<!--
	파 일 명  : comPopupJobOrg.jsp
	작성일자  : 2006.08.01
	작 성 자  : mailbest
	내    용  : 직무/조직 팝업
-->
<%@ page contentType="text/html;charset=ksc5601" errorPage="/common/error.jsp"%>
<%@ page import="com.wms.comPopup.beans.dto.ComPopupDTO"%>
<%@ page import="com.wms.popupSet.beans.dto.PopupSetDTO"%>
<%@ page import="com.wms.fw.Utility"%>
<%@ page import="com.wms.beans.dto.*"%>
<%@ page import="java.util.*"%>

<jsp:useBean id="user" class="com.wms.beans.dto.UserInfo"
	scope="session" />

<%	//검색화면에서 테이블명에 따른 코드이름 표기
	String param       =  request.getParameter("param");
	String paramId     =  request.getParameter("paramId");
	String code        =  request.getParameter("code");
	String codeName    =  request.getParameter("codeName");
	
	String objcodeNo   =  request.getParameter("objcodeNo");
	String objcode1    =  request.getParameter("objcode1");
	String objcodeName1=  request.getParameter("objcodeName1");
	String objcode2    =  request.getParameter("objcode2");
	String objcodeName2=  request.getParameter("objcodeName2");	
	
    ComPopupDTO[] dtos = (ComPopupDTO[])request.getAttribute("returns");
    PopupSetDTO[] dto = (PopupSetDTO[])request.getAttribute("returns1");
    
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7, IE=EmulateIE9"/>
<base target="_self" />
<meta http-equiv="Content-Language" content="ko">
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<LINK rel="stylesheet" type="text/css"
	href="../css/skin04_form_popup.css">
<style type="">
<!--
.viewContentL {text-align:left;cursor:hand;border-style:solid; border-color:#EBEEF0 #EBEEF0 #EBEEF0 #EBEEF0;border-width:0 0 1 0}
.viewContentC {text-align:center;cursor:hand;border-style:solid; border-color:#EBEEF0 #EBEEF0 #EBEEF0 #EBEEF0;border-width:0 0 1 0}
.viewContentR {text-align:right;cursor:hand;border-style:solid; border-color:#EBEEF0 #EBEEF0 #EBEEF0 #EBEEF0;border-width:0 0 1 0}
-->
</style>
<script Language="JavaScript" src="/common/link/common.js"
	type="text/javascript"></script>
<title>코드 검색</title>
<script Language="JavaScript" src="/common/link/jobSearch.js"
	type="text/javascript"></script>
<script language='javascript' type="text/javascript">
<!--

function confirmItem1(codeNo,code,name){
	
	 var opener = window.dialogArguments;
	 var frm = document.forms[0];
  	opener.document.forms[0].all(frm.objcodeNo.value.value).value = codeNo;
  	opener.document.forms[0].all(frm.objcode2.value).value = code;
  	opener.document.forms[0].all(frm.objcodeName2.value).value = name;
  	self.close();
}    	
function confirmItem(codeNo,code,name,code2,name2,objcodeNo,objcode,objcodeName,objcode2,objcodeName2){
	 var opener = window.dialogArguments;
	 var frm = document.forms[0]; 
	 
	opener.document.forms[0].all(frm.objcode1.value).value = code;
	opener.document.forms[0].all(frm.objcodeName1.value).value = name;
	
	//프로젝트 조회
	searchPrcsList();
	
	//Tab 정리
	toggleObj('togg_List',1,2);
	toggleObj('tab_popInfo',2,4);
}
//검색
function search(){
	var frm = document.forms[0];
	frm.action = 'comCodeList.do';
	frm._SCREEN.value = '/common/comPopupJobOrg.jsp' ; 

	frm.submit();
}

//공정 정보리스트
function searchPrcsList(){
	var ifrm  = ifmPrcsList.form1;
		
	ifrm.action='comCodeList.do';
	ifrm._SCREEN.value = '/common/comPopupJobOrgDetail.jsp';
	ifrm.paramId.value="org_1";
	ifrm.param.value="org_new";
	ifrm.target="";
	ifrm.submit();
}

//-->
</script>
</head>

<body text="#000000" bgcolor="#FFFFFF" topmargin="0" leftmargin="0"
	rightmargin="0">
<form method="POST" action="" name='form1'>
<input type='hidden'
	name='_ACT'> 
	<input type='hidden' name='_SCREEN'> 
	<input type='hidden' name='param' value="<%=param%>"> 
	<input type=hidden name='paramId'	value="<%=paramId%>"> 
	<input type="hidden" name="empId"	value='<%=user.empId%>'> 

	<input type="hidden" name="codeNo" value=''> 
	<input type="hidden" name="code1" value=''> 
	<input type="hidden" name="codeName1" value=''> 
	<input type="hidden" name="code2" value=''>
	<input type="hidden" name="codeName2" value=''> 
	
	<input type="hidden" name="objcodeNo" value='<%=objcodeNo%>'> 
	<input type="hidden" name="objcode1" value='<%=objcode1%>'> 
	<input type="hidden" name="objcodeName1" value='<%=objcodeName1%>'> 
	<input type="hidden" name="objcode2" value='<%=objcode2%>'> 
	<input type="hidden" name="objcodeName2" value='<%=objcodeName2%>'>


<table cellpadding="1" cellspacing="1" class="table1" border="0"
	width="100%">
	<tr>
		<td class="td2">
		<table cellpadding="2" cellspacing="1" border="0" width="100%">
			<tr>
				<td width="20"><img src="../images/i_formtitle.gif" alt=""></td>
				<td><B>검색</B></td>
				<td align="right">
				<table cellpadding="0" cellspacing="0" border="0">
					<tr onclick="self.close();">
						<td width="5"></td>
						<td class="base" style="cursor:hand;"
							onMouseOver="this.className='on'"
							onMouseOut="this.className='base'"
							onMouseDown="this.className='down'"
							onMouseUp="this.className='on'">닫기</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>

<table border="0">
	<tr height="1">
		<td></td>
	</tr>
</table>


<Table cellpadding="0" cellspacing="1" border="0" class="table1"
	width="100%">
	<tr>
		<td width="85%">
		<Table cellpadding="0" cellspacing="0" border="0" class="table1"
			width="100%">
			<tr>
				<%if( param.equals("emp")){ %>
				<td width="25%" height="24" class="TD1">조직명</td>
				<%}else{ %>
				<td width="25%" height="24" class="TD1">코드검색</td>
				<%} %>

				<td width="10%" height="24" class="TD2"><input name="code" size="10"
					maxlength="10" style="ime-mode:inactive;" onkeyup="checkEngField(this);"
					onkeydown="javascript:if(event.keyCode==13){search();}"
					value='<%=(code!=null)?code:""%>'></td>
				<td width="25%" height="24" class="TD1">이름검색</td>
				<td width="10%" height="24" class="TD2"><input name="codeName"
					size="15" maxlength="15" style="ime-mode:active;"
					onkeydown="javascript:if(event.keyCode==13){search();}"
					value='<%=(codeName!=null)?codeName:""%>'></td>
			</tr>
		</Table>
		</td>
		<td width="15%" class="td2">
		<Table cellpadding="0" cellspacing="0" border="0" class="table1"
			width="100%">
			<tr onclick="search();">
				<td width="5" class="td2"></td>

				<td class="base" style="cursor:hand;"
					onMouseOver="this.className='on'"
					onMouseOut="this.className='base'"
					onMouseDown="this.className='down'" onMouseUp="this.className='on'">검색</td>

			</tr>
		</Table>
		</td>
	</tr>
</Table>
<table border="0">
	<tr height="1">
		<td></td>
	</tr>
</table>
<img src="../images/tabimage/job_org_en.gif"
	onclick="toggleObj('togg_List',1,1);toggleObj('tab_popInfo',2,1);"
	id="tab_popInfo" alt=""><img
	src="../images/tabimage/job_org_dis.gif"
	onclick="toggleObj('togg_List',1,1);toggleObj('tab_popInfo',2,2);"
	id="tab_popInfo" style="display:none" alt=""><img
	src="../images/tabimage/popup_project_en.gif"
	onclick="searchPrcsList();toggleObj('togg_List',1,2);toggleObj('tab_popInfo',2,3);"
	id="tab_popInfo" style="display:none" alt=""><img
	src="../images/tabimage/popup_project_dis.gif"
	onclick="searchPrcsList();toggleObj('togg_List',1,2);toggleObj('tab_popInfo',2,4);"
	id="tab_popInfo" alt="">

<div id="togg_List">
<Table cellpadding="0" cellspacing="1" border="0" class="table1"
	width="100%">
	<tr>
		<td width="90%" height="24" class="TD1">조회 내역</td>
		<td width="" height="24" class="TD1"><%if(dtos!=null) {%><input
			type="button" onclick="setLevel('0_All','table1')" value=" + "><%} %></td>
	</tr>
</Table>
<%
if(dtos!=null ){
%>
<Table id="table1" cellpadding="0" cellspacing="1" border="0"
	width="100%">

	<% for (int i=0;i<dtos.length;i++){ %>
	<tr id="<%=dtos[i].level%>_<%=dtos[i].highCode%>" bordercolor="#0066CC"
		class='onbase'
		style='cursor:hand;<%if (dtos[i].level> Utility.parseInt(dto[0].param2)){ out.print("display:none");}%>'
		onMouseOver="this.className='on'" onMouseOut="this.className='onbase'"
		onclick="setLevel('<%=dtos[i].level%>_<%=dtos[i].codeNo%>','table1');">
		<td width="85%" height="24" align="left">
		<!--Tree생성  --> 
		<%=Utility.makeTree(dtos,i)%>
		<!--DATA 조회  -->
		 <% if(!dtos[i].code2.equals("")){%><%=dtos[i].code2 %><%}else{%><%=dtos[i].code %><%}%>_
		<% if(!dtos[i].codeName2.equals("")){%><%=dtos[i].codeName2 %><%}else{%><%=dtos[i].codeName %><%}%>
		</td>
		<td style='display:none'><%=dtos[i].codeNo %></td>
		<td width="9%" align="center"><!--선택버튼 Y/N --> <% if (!dtos[i].selFlag.equals("N")){%>
		<input type="button"
			onclick="confirmItem('<%=dtos[i].codeNo%>',
								  '<%=dtos[i].code%>','<%=dtos[i].codeName %>',
								  '<%=dtos[i].code2%>','<%=dtos[i].codeName2 %>',
								  '<%=objcodeNo %>',
								  '<%=objcode1%>','<%=objcodeName1%>',
								  '<%=objcode2%>','<%=objcodeName2 %>');"
			value="선택" style="cursor:hand;"> <%} %></td>
	</tr>

	<%}%>
</Table>
<%	} else if (dtos==null) { %>
<Table id="table2" cellpadding="0" cellspacing="1" border="0"
	class="table1" width="100%">
	<tr class='base' style="cursor:hand;" onMouseOver="this.className='on'"
		onMouseOut="this.className='base'">
		<td colspan="2" height="24" align='center'>해당 자료가 없습니다(클릭하면 해당컬럼에 공백이
		들어갑니다).</td>
	</tr>
</Table>

<%} %></div>
 <!--공정  조회--> 
<div id="togg_List" style="position:relative;left:0;top:0; overflow-x:none; overflow-y:none;">
	<iframe name='ifmPrcsList' src="/common/comPopupJobOrgDetail.jsp" frameborder="0"  marginwidth="0" marginheight="0" 
	width="100%" height="100%" scrolling='no'></iframe> 
</div>  
	
</form>
</body>
<script type="text/javascript">
	//Tab Setting
    toggleObj("togg_List",1,1);
	toggleObj("tab_popInfo",2,1);

	//조회조건이 있을때 전체 보기
	if (document.form1.code.value != '' || document.form1.codeName.value != ''){
		setLevel('0_All','table1');
	}
</script>

</html>
