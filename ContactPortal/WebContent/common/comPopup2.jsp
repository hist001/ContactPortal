<!--
	파 일 명  : comPopup.jsp
	작성일자  : 2006.06.01
	작 성 자  : mailbest
	내    용  : 통합 검색 화면
-->
<%@ page contentType="text/html;charset=ksc5601" errorPage="/common/error.jsp"%>
<%@ page import="com.wms.comPopup.beans.dto.ComPopupDTO" %>
<%@ page import="com.wms.fw.util.DateUtil" %>
<%@ page import="com.wms.fw.Utility" %>
<%@ page import="com.wms.beans.dto.*" %>
<%@ page import="java.util.*" %>

<jsp:useBean id="user" class="com.wms.beans.dto.UserInfo" scope="session"/>

<%	//검색화면에서 테이블명에 따른 코드이름 표기
	String param       =  request.getParameter("param");

	String codeNo1     =  request.getParameter("codeNo1");
	String codeCd1     =  request.getParameter("codeCd1");
	String codeName1   =  request.getParameter("codeName1");
	String codeView1   =  request.getParameter("codeView1");	

	String codeNo2     =  request.getParameter("codeNo2");
	String codeCd2     =  request.getParameter("codeCd2");
	String codeName2   =  request.getParameter("codeName2");
	String codeView2   =  request.getParameter("codeView2");	

	String code        =  request.getParameter("code");
	String codeName    =  request.getParameter("codeName");

	String actFlag     =  request.getParameter("actFlag");	

	String jobCd       =  request.getParameter("jobCd");
	String jobName     =  request.getParameter("jobName");
	
	String radioSel    = request.getParameter("radioSel");

	if(radioSel==null)  radioSel="";

    ComPopupDTO[] dtos = (ComPopupDTO[])request.getAttribute("returns");
	String orgDiv=user.empOrgCd.substring(0,2);
	
	if(orgDiv.equals("CA")||
	   orgDiv.equals("CB")||
	   orgDiv.equals("CC")){
			orgDiv="GIS";
	   }else if(orgDiv.equals("CD")||
	            orgDiv.equals("CE")){
					orgDiv="SI";
	   }else if(orgDiv.equals("CG")||
	            orgDiv.equals("CH")||
				orgDiv.equals("CI")){
					orgDiv="ITO";
	   }else if(orgDiv.equals("CP")||
	           orgDiv.equals("CQ")){
					orgDiv="GEN";
	   }
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7, IE=EmulateIE9"/>
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
<title>코드 검색</title>
<script Language="JavaScript" src="/common/link/jobSearch.js"	type="text/javascript"></script>
<script language='javascript' type="text/javascript">
<!--
function confirmItem(codeNo,code,name,code2,name2){
	var frm= document.forms[0];
    if(frm.param.value=='job_radio'){
		
		frm.code.value=code2;
		frm.jobCd.value=codeNo;
		frm.jobName.value=name;
		<%  String tmp=null;
			if(orgDiv.equals("GIS")){
				tmp="org_gis";	
			}else if(orgDiv.equals("SI")){
				tmp="org_si";	
			}else if(orgDiv.equals("ITO")){
				tmp="org_ito";	
			}else if(orgDiv.equals("GEN")){
				tmp="org_mgm";	
			}
		%>
		frm.param.value='<%=tmp%>'
		search();
	}else {
	   if(frm.param.value.substring(0,3)=='pjt'){ 
	    opener.document.all('<%=codeNo1%>').value=codeNo;
	   
		opener.document.all('<%=codeCd1%>').value=code;
		opener.document.all('<%=codeName1%>').value=name;
		opener.document.all('<%=codeView1%>').value=code+" "+name;

		opener.document.all('<%=codeNo2%>').value=code2;
		opener.document.all('<%=codeCd2%>').value=code2;
		opener.document.all('<%=codeName2%>').value=name2;
		opener.document.all('<%=codeView2%>').value=code2+" "+name2;

		opener.document.all('<%=actFlag%>').value='D';
	  
	 }else{

	   	opener.document.all('<%=codeNo1%>').value=frm.jobCd.value;
		opener.document.all('<%=codeCd1%>').value=frm.jobCd.value;
		opener.document.all('<%=codeName1%>').value=frm.jobName.value;
		opener.document.all('<%=codeView1%>').value=frm.jobCd.value+" "+frm.jobName.value;

		opener.document.all('<%=codeNo2%>').value=codeNo;
		opener.document.all('<%=codeCd2%>').value=code;
		opener.document.all('<%=codeName2%>').value=name;
		opener.document.all('<%=codeView2%>').value=code+" "+name;

		opener.document.all('<%=actFlag%>').value='I';
		
	   }
		
		self.close();
	}
}
function setPjt(){
	var frm= document.forms[0];
		<%  tmp=null;
			if(orgDiv.equals("GIS")){
				//tmp="pjt_gis";	
				tmp="pjt_all";	
			}else if(orgDiv.equals("SI")){
				tmp="pjt_all";	
//				tmp="pjt_si";	
			}else if(orgDiv.equals("ITO")){
//				tmp="pjt_ito";	
				tmp="pjt_all";	
			}else if(orgDiv.equals("GEN")){
//				tmp="pjt_mgm";	
				tmp="pjt_all";	
			}
		%>
		frm.param.value='<%=tmp%>'
		search();
}
//검색
function search(){

	var frm = document.forms[0];
	frm.action = 'comCodeList.do';
	frm._ACT.value = 'AC';
	frm._SCREEN.value = '/common/comPopup2.jsp?empid='+frm.empId.value+'code='+ frm.code.value 
								+ '&codeName=' + frm.codeName.value ; 

	frm.code.value=frm.code.value.toUpperCase() ;
	frm.codeName.value=frm.codeName.value.toUpperCase();
		
	frm.submit();
}
function chkFn(){
	document.form1.indir.style.display='';
}
//-->
</script>
</head>

<body text="#000000" bgcolor="#FFFFFF" topmargin="0" leftmargin="0"
	rightmargin="0"  >
<form method="POST" action="" name='form1'>
<input type='hidden' name='_ACT'> 
<input type='hidden' name='_SCREEN'> 
<input type='hidden' name='param' value="<%=param%>"> 
<input type="hidden" name="empId" value='<%=user.empId%>'>

<input type="hidden" name="empOrgNo" value='<%=user.empOrgNo%>'>
<input type="hidden" name="empOrgCd" value='<%=user.empOrgCd%>'>
<input type="hidden" name="empOrgName" value='<%=user.empOrgName%>'>

<input type="hidden" name="codeNo1" value='<%=codeNo1%>'>
<input type="hidden" name="codeCd1" value='<%=codeCd1%>'>
<input type="hidden" name="codeName1" value='<%=codeName1%>'>
<input type="hidden" name="codeView1" value='<%=codeView1%>'>

<input type="hidden" name="codeNo2" value='<%=codeNo2%>'>
<input type="hidden" name="codeCd2" value='<%=codeCd2%>'>
<input type="hidden" name="codeName2" value='<%=codeName2%>'>
<input type="hidden" name="codeView2" value='<%=codeView2%>'>


<input type="hidden" name="actFlag" value='<%=actFlag%>'>

<input type="hidden" name="jobCd" value='<%=jobCd%>'>
<input type="hidden" name="jobName" value='<%=jobName%>'>


<table cellpadding="1" cellspacing="1" class="table1" border="0" width="100%">
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
					maxlength="10"
					onkeydown="javascript:if(event.keyCode==13){search();}"
					value="<%=(code!=null)?code:""%>"></td>
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
					onMouseDown="this.className='down'" onMouseUp="this.className='on'">
				검색1</td>
				
			</tr>
		</Table>
		</td>
	</tr>
</Table>

		<Table cellpadding="0" cellspacing="0" border="1" class="table1"
			width="100%" >
			<tr>			
				<td width="25%" height="24" class="TD1">직무/조직보고
				<input type='radio' name="radioSel" 
					onclick="chkFn;document.forms[0].param.value='job_radio';search();"
					value='job_radio' <%=(radioSel.equals("job_radio"))?"checked":" "%>></td>
				<td width="25%" height="24" class="TD1">프로젝트보고
				<input type='radio' name="radioSel"  
					onclick="setPjt();"
					value='pjt' <%=(radioSel.equals("pjt"))?"checked":" "%>></td>
				
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
		
<Table cellpadding="0" cellspacing="1" border="0"	width="100%" id="table1">
	
		<% for (int i=0;i<dtos.length;i++){ %>
		<tr id="<%=dtos[i].level%>_<%=dtos[i].highCode%>"  class='td2' bordercolor=#0066CC
			style="<%if (dtos[i].level>2){ out.print("display:none");}%>"
			onclick="setLevel('<%=dtos[i].level%>_<%=dtos[i].codeNo%>','table1');" >		
			<td width="85%" height="24" align="left">
				<!--Tree생성  -->
				<%=Utility.makeTree(dtos,i)%>
				<!--DATA 조회  -->
				<% if(!dtos[i].code2.equals("")&& !param.equals("pjt_new")){%><%=dtos[i].code2 %><%}else{%><%=dtos[i].code %><%}%>_
				<a href="#">
				<% if(!dtos[i].codeName2.equals("")&& !param.equals("pjt_new")){%><%=dtos[i].codeName2 %><%}else{%><%=dtos[i].codeName %><%}%></a>
			</td>
			<td style='display:none'><%=dtos[i].codeNo %>
			</td>
			<td width="10%" align="center" >
				<!--선택버튼 Y/N -->
				<% if (!dtos[i].selFlag.equals("N")){%>
				<input type="button" onclick="confirmItem('<%=dtos[i].codeNo%>',
														  '<%=dtos[i].code%>',
														  '<%=dtos[i].codeName %>',
														  '<%=dtos[i].code2%>',
														  '<%=dtos[i].codeName2 %>');"
														  value="선택">
														  
				<%} %>
			</td>
		</tr>
	<%}%>
</Table>
<%	} else if (dtos==null) { %>
<Table cellpadding="0" cellspacing="1" border="0" class="table1"	width="100%" >
	<tr class='base' style="cursor:hand;" onMouseOver="this.className='on'"
		onMouseOut="this.className='base'">
		<td colspan="2" height="24" align='center'>해당 자료가 없습니다(클릭하면 해당컬럼에 공백이
		들어갑니다).</td>
	</tr>
</Table>
<%} %></form>
</body>
<script>
	//조회조건이 있을때 전체 보기
	if (document.forms[0].code.value != '' || document.forms[0].codeName.value != ''){
		setLevel('0_All','table1');
	}
</script>
</html>
