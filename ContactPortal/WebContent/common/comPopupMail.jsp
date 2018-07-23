<!--
	파 일 명  : comPopupMail.jsp
	작성일자  : 2007.04.11
	작 성 자  : mailbest
	내    용  : 통합 검색 화면
-->
<%@ page contentType="text/html;charset=ksc5601" errorPage="/common/error.jsp"%>
<%@ page import="com.wms.comPopup.beans.dto.ComPopupDTO" %>
<%@ page import="com.wms.popupSet.beans.dto.PopupSetDTO" %>
<%@ page import="com.wms.fw.util.DateUtil" %>
<%@ page import="com.wms.fw.Utility" %>
<%@ page import="com.wms.beans.dto.*" %>
<%@ page import="java.util.*" %>

<jsp:useBean id="user" class="com.wms.beans.dto.UserInfo" scope="session"/>

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
<meta http-equiv="Content-Type"
	content="text/html; charset=euc-kr">
<LINK rel="stylesheet" type="text/css" href="../css/skin04_form_popup.css">
<style type="">
<!--
.viewContentL {text-align:left;cursor:hand;border-style:solid; border-color:#EBEEF0 #EBEEF0 #EBEEF0 #EBEEF0;border-width:0 0 1 0}
.viewContentC {text-align:center;cursor:hand;border-style:solid; border-color:#EBEEF0 #EBEEF0 #EBEEF0 #EBEEF0;border-width:0 0 1 0}
.viewContentR {text-align:right;cursor:hand;border-style:solid; border-color:#EBEEF0 #EBEEF0 #EBEEF0 #EBEEF0;border-width:0 0 1 0}
-->
</style>
<script Language="JavaScript" src="/common/link/common.js"	type="text/javascript"></script>
<script Language="JavaScript" src="/common/link/jobSearch.js"	type="text/javascript"></script>
<script Language="JavaScript" src="/common/link/combo.js"></script>	
<title>코드 검색</title>
<script language='javascript' type="text/javascript">
<!--

function chkSelect(){
		var frm=document.forms[0];
		var chkCnt = 0;
		var opener = window.dialogArguments;
	 	var opt = "";
	 	var idx = 0;
	 	
		if (typeof(frm.insFlag.length)!= "undefined"){	
			for (var a=0 ; a<frm.insFlag.length ; a++){						
				if(frm.insFlag[a].checked==true){
					frm.chk[a].value='Y';				

				    opt = opener.document.createElement('INPUT');
				    opt.type = "text";
				    opt.id = "empId";
				    opt.name = "empId";
				    opener.document.forms[0].appendChild(opt);

				    opt1 = opener.document.createElement('INPUT');
				    opt1.type = "text";
				    opt1.id = "empName";
				    opt1.name = "empName";
				    opener.document.forms[0].appendChild(opt1);
				    opt.value=frm.chkEmpId[a].value;
				    opt1.value=frm.chkEmpName[a].value;

				    opt2 = opener.document.createElement('INPUT');
				    opt2.type = "checkbox";
				    opt2.id = "insFlag";
				    opt2.name = "insFlag";
				    opener.document.forms[0].appendChild(opt2);
				    
				    opt3 = opener.document.createElement('INPUT');
				    opt3.type = "hidden";
   				    opt3.id = "chk";
				    opt3.name = "chk";
				    opener.document.forms[0].appendChild(opt3);
					
					opt4 = opener.document.createElement('br');
				    opener.document.forms[0].appendChild(opt4);
				    
					delCnt = true;
					chkCnt = chkCnt + 1;
				}else{
					frm.chk[a].value='N';	
				}
			}		 		
		}else{
			if(frm.insFlag.checked==true){
				frm.chk.value='Y';
				
				    opt = opener.document.createElement('INPUT');
				    opt.type = "text";
				    opt.id = "empId";
				    opt.name = "empId";
				    opener.document.forms[0].appendChild(opt);

				    opt1 = opener.document.createElement('INPUT');
				    opt1.type = "text";
				    opt1.id = "empName";
				    opt1.name = "empName";
				    opener.document.forms[0].appendChild(opt1);
				    opt.value=frm.chkEmpId.value;
				    opt1.value=frm.chkEmpName.value;

				    opt2 = opener.document.createElement('INPUT');
				    opt2.type = "checkbox";
				    opt2.id = "insFlag";
				    opt2.name = "insFlag";
				    opener.document.forms[0].appendChild(opt2);
				    
				    opt3 = opener.document.createElement('INPUT');
				    opt3.type = "hidden";
   				    opt3.id = "chk";
				    opt3.name = "chk";
				    opener.document.forms[0].appendChild(opt3);
					
					opt4 = opener.document.createElement('br');
				    opener.document.forms[0].appendChild(opt4);
				    
				delCnt=true;
				chkCnt = chkCnt + 1;
				
			}else{
				frm.chk.value='N';
			}	
		}//***undefined end***
		//alert("chkCnt ::"+chkCnt);
		//alert("END");
		opener.document.forms[0].target="";
		self.close();

}

function confirmItem(codeNo,code,name,code2,name2,objcodeNo,objcode,objcodeName,objcode2,objcodeName2){
	 var opener = window.dialogArguments;
    if (objcodeNo!= null & objcodeNo!="" & objcodeNo!='undefined') 
    	opener.document.forms[0].all(objcodeNo).value = codeNo;
    if (objcode!= null & objcode!="" & objcode!='undefined') 
    	opener.document.forms[0].all(objcode).value = code;
    if (objcodeName!= null & objcodeName!="" & objcodeName!='undefined') 
    	opener.document.forms[0].all(objcodeName).value = name;
    if (objcode2!= null & objcode2!="" & objcode2!='undefined') 
    	opener.document.forms[0].all(objcode2).value = code2;
    if (objcodeName2!= null & objcodeName2!="" & objcodeName2!='undefined') 
    	opener.document.forms[0].all(objcodeName2).value = name2;
	self.close();
}
//검색
function search(){
	var frm = document.forms[0];
	frm.action = 'comCodeList.do';
	frm._SCREEN.value = '/common/comPopupMail.jsp?empid='+frm.empId.value+'code='+ frm.code.value + '&codeName=' + frm.codeName.value ; 

	frm.code.value=frm.code.value.toUpperCase() ;
	frm.codeName.value=frm.codeName.value.toUpperCase();
		
	frm.submit();
	window.focus();
}
//-->
</script>
</head>

<body text="#000000" bgcolor="#FFFFFF" topmargin="0" leftmargin="0"	rightmargin="0">
<form method="POST" action="" onload="init();">
<input type='hidden' name='_ACT'> 
<input type='hidden' name='_SCREEN'> 
<input type='hidden' name='param' value="<%=param%>"> 
<input type='hidden' name='paramId' value="<%=paramId%>"> 
<input type="hidden" name="empId" value='<%=user.empId%>'>

<input type="hidden" name="empOrgNo" value='<%=user.empOrgNo%>'>
<input type="hidden" name="empOrgCd" value='<%=user.empOrgCd%>'>
<input type="hidden" name="empOrgName" value='<%=user.empOrgName%>'>

<input type="hidden" name="objcodeNo" value='<%=objcodeNo%>'>
<input type="hidden" name="objcode1" value='<%=objcode1%>'>
<input type="hidden" name="objcodeName1" value='<%=objcodeName1%>'>
<input type="hidden" name="objcode2" value='<%=objcode2%>'>
<input type="hidden" name="objcodeName2" value='<%=objcodeName2%>'>

<table cellpadding="1" cellspacing="1" class="table1" border="0" width="100%">
	<tr>
		<td class="td2">
		<table cellpadding="2" cellspacing="1" border="0" width="100%">
			<tr>
				<td width="20"><img src="../images/i_formtitle.gif" alt=""></td>
				<td><b>수신자 선택</b></td>
				<td align="right">
				<table cellpadding="0" cellspacing="0" border="0">
					<tr >
						<td width="5"></td>
						<td class="base" style="cursor:hand;" onclick="chkSelect();"
							onMouseOver="this.className='on'"
							onMouseOut="this.className='base'"
							onMouseDown="this.className='down'"
							onMouseUp="this.className='on'">저장</td>
						<td width="5"></td>
						<td class="base" style="cursor:hand;" onclick="self.close();"
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
				<td width="10%" height="24" class="TD2"><input name="code" size="10"
					maxlength="10" style="ime-mode:active;" 
					onkeydown="javascript:if(event.keyCode==13){search();}"
					value="<%=(code!=null)?code:""%>"></td>
				<%}else{ %>
				<td width="25%" height="24" class="TD1">코드검색</td>
				<td width="10%" height="24" class="TD2"><input name="code" size="10"
					maxlength="10" style="ime-mode:inactive;" onkeyup="checkEngField(this);" 
					onkeydown="javascript:if(event.keyCode==13){search();}"
					value="<%=(code!=null)?code:""%>"></td>
				<%} %>
				
				<td width="25%" height="24" class="TD1">이름검색</td>
				<td width="10%" height="24" class="TD2"><input name="codeName" size="15"
					maxlength="15" style="ime-mode:active;"
					onkeydown="javascript:if(event.keyCode==13){search();}"
					value="<%=(codeName!=null)?codeName:""%>"></td>
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
<Table cellpadding="0" cellspacing="1" border="0" class="table1"
	width="100%" >
	<tr>
		<td width="90%" height="24" class="TD1">조회 내역</td>
		<td width="" height="24" class="TD1"><%if(dtos!=null) {%><input type="button"  onclick="setLevel('0_All','table1')"  value=" + "><%} %></td>
	</tr>
</Table>

<%

if(dtos!=null ){
%>

<div id="viewlist" style="position:relative;top:0;left:0;width:100%;height:445px; overflow-x:none; overflow-y:auto">
<Table cellpadding="0" cellspacing="0" border="0"	width="100%" id="table1">

		<% 
		String idKName=null;
		for (int i=0;i<dtos.length;i++){ %>
		<tr id="<%=dtos[i].level%>_<%=dtos[i].highCode%>"   bordercolor=#0066CC
			class='onbase' style ="cursor:hand;<%if (dtos[i].level> Utility.parseInt(dto[0].param2)){ out.print("display:none");}%>"
			onMouseOver="this.className='on'" onMouseOut="this.className='onbase'" 
			onclick="setLevel('<%=dtos[i].level%>_<%=dtos[i].codeNo%>','table1');" >		
			<td width="85%" height="24" align="left">
				<!--Tree생성  -->
				<%=Utility.makeTree(dtos,i)%>
				<!--검색조건이 있는경우 트리구조 사용을 적용 안할때 사용 
				<%if (code.equals("") & codeName.equals("")){ %>			
					<%=Utility.makeTree(dtos,i)%>
				<%}else{ %>
					<img src="../images/treeImage/0_.gif" alt="">
				<%} %>
				-->
				<!--DATA 조회  -->
				<% if(!dtos[i].code2.equals("")){%><%=dtos[i].code2 %><%}else{%><%=dtos[i].code %><%}%>_
				<% if(dtos[i].code.substring(0,1).equals("B")& param.equals("pjt_new")){%><font color=blue>[영업]</font><%} %>
				<% if(!dtos[i].codeName2.equals("")){%>
					<%=dtos[i].codeName2 %><%}else{%><%=dtos[i].codeName %>
				<%}%>
			</td>
			<td style='display:none'><%=dtos[i].codeNo %>
			</td>
			<td width="9%" align="center" >
				<!--선택버튼 Y/N -->
				<% if (!dtos[i].selFlag.equals("N")){
					idKName = dtos[i].codeNo+" "+dtos[i].codeName;
				%>
				<input type = 'hidden' name ='chk'>
				<input type = "checkbox" name="insFlag" value='<%= idKName %>' >
				<input type = 'hidden' name ='chkEmpId' value='<%= dtos[i].codeNo %>'>
				<input type = 'hidden' name ='chkEmpName' value='<%= dtos[i].codeName %>'>
				
				<%} %>
			</td>
		</tr>

		<%}%>
</Table>
</div>
<%	} else if (dtos==null) { %>
<Table cellpadding="0" cellspacing="1" border="0" class="table1"	width="100%" >

	<tr class='base' style="cursor:hand;" onMouseOver="this.className='on'"
		onMouseOut="this.className='base'">
		<!-- 
		<td colspan="2" height="24" align='center' onclick="confirmItem('','','','','','<%=objcodeNo %>',
																	  '<%=objcode1%>','<%=objcodeName1%>',
																	  '<%=objcode2%>','<%=objcodeName2 %>');">
		해당 자료가 없습니다(클릭하면 해당컬럼에 공백이 들어갑니다).
		</td>
		 -->
		<td colspan="2" height="24" align='center'>
		해당 자료가 없습니다.
		</td>

	</tr>
</Table>

<%} %>
</form>
</body>
<script>
	//조회조건이 있을때 전체 보기
	if (document.forms[0].code.value != '' || document.forms[0].codeName.value != ''){
		setLevel('0_All','table1');
	}
</script>
</html>
