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
	String idx		   =  request.getParameter("idx");	
	String CHK1    	   = request.getParameter("CHK1");
	String btFlag      = request.getParameter("btFlag");
	
	
		idx = "0";
	
	

	if(radioSel==null)  radioSel="pjt";
	
    ComPopupDTO[] dtos = (ComPopupDTO[])request.getAttribute("returns");
    PopupSetDTO[] dto = (PopupSetDTO[])request.getAttribute("returns1");    
    
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7, IE=EmulateIE9"/>
<base target="_self">
<meta http-equiv="Content-Language" content="ko">
<meta http-equiv="Content-Type"
	content="text/html; charset=euc-kr">
<LINK rel="stylesheet" type="text/css" href="../css/skin04_form_popup.css">
<style type="">
</style>
<title>코드 검색</title>
<script Language="JavaScript" src="/common/link/common.js"	type="text/javascript"></script>
<script Language="JavaScript" src="/common/link/jobSearch.js"	type="text/javascript"></script>
<script language='javascript' type="text/javascript"><!--

function confirmItem1(codeNo,code,name){
	 var frm = document.forms[0];
	 var opener=window.dialogArguments;
	    if (codeNo!= null && codeNo!="" && codeNo!='undefined'){
		  if (opener.document.all('<%=codeNo2%>').length==undefined){
			opener.document.all('<%=codeNo2%>').value=codeNo;
			opener.document.all('<%=codeCd2%>').value=code;
			opener.document.all('<%=codeName2%>').value=name;
    	}else{
			opener.document.all('<%=codeNo2%>')[<%=idx%>].value=codeNo;
			opener.document.all('<%=codeCd2%>')[<%=idx%>].value=code;
			opener.document.all('<%=codeName2%>')[<%=idx%>].value=name;
			}
		}

  		self.close();
}   
function confirmItem(codeNo,code,name,code2,name2){
	var frm= document.forms[0];

    if(frm.param.value=='job_new'){ //직무 선택 후 조직 조회
		frm.code.value=code2;	
		frm.jobCd.value=codeNo;
		frm.jobName.value=name;
		search();
	}else { 						//PJT선택 후 공정 조회
	   if(frm.param.value=='pjt_new' & frm.radioSel[1].checked){ 

	    if (codeNo!= null && codeNo!="" && codeNo!='undefined'){
		     if (window.dialogArguments.document.all('<%=codeNo1%>').length==undefined){
			    window.dialogArguments.document.all('<%=codeNo1%>').value=codeNo;
				window.dialogArguments.document.all('<%=codeCd1%>').value=code;
				window.dialogArguments.document.all('<%=codeName1%>').value=name;
				window.dialogArguments.document.all('<%=codeView1%>').value=code+" "+name;
				window.dialogArguments.document.all('<%=actFlag%>').value='D';
    		}else{
		   		window.dialogArguments.document.all('<%=codeNo1%>')[<%=idx%>].value=codeNo;
				window.dialogArguments.document.all('<%=codeCd1%>')[<%=idx%>].value=code;
				window.dialogArguments.document.all('<%=codeName1%>')[<%=idx%>].value=name;
				window.dialogArguments.document.all('<%=codeView1%>')[<%=idx%>].value=code+" "+name;
				window.dialogArguments.document.all('<%=actFlag%>')[<%=idx%>].value='D';
    		} 
    	}
	   
		
		document.form1.code2.value=codeNo;
	
        searchPrcsList();

       toggleObj('togg_List',1,2);
       toggleObj('tab_popInfo',2,4);      
	 }else if(frm.param.value=='pjt_new' & frm.radioSel[0].checked){  //조직조회 후 선택
	 
	    if (codeNo!= null && codeNo!="" && codeNo!='undefined'){
	     if (window.dialogArguments.document.all('<%=codeNo1%>').length==undefined){	    
		   	window.dialogArguments.document.all('<%=codeNo1%>').value=frm.jobCd.value;
			window.dialogArguments.document.all('<%=codeCd1%>').value=frm.jobCd.value;
			window.dialogArguments.document.all('<%=codeName1%>').value=frm.jobName.value;
			window.dialogArguments.document.all('<%=codeView1%>').value=frm.jobCd.value+" "+frm.jobName.value;
	
			window.dialogArguments.document.all('<%=codeNo2%>').value=codeNo;
			window.dialogArguments.document.all('<%=codeCd2%>').value=code;
			window.dialogArguments.document.all('<%=codeName2%>').value=name;
			window.dialogArguments.document.all('<%=codeView2%>').value=code+" "+name;
    	}else{
		   	window.dialogArguments.document.all('<%=codeNo1%>')[<%=idx%>].value=frm.jobCd.value;
			window.dialogArguments.document.all('<%=codeCd1%>')[<%=idx%>].value=frm.jobCd.value;
			window.dialogArguments.document.all('<%=codeName1%>')[<%=idx%>].value=frm.jobName.value;
			window.dialogArguments.document.all('<%=codeView1%>')[<%=idx%>].value=frm.jobCd.value+" "+frm.jobName.value;
	
			window.dialogArguments.document.all('<%=codeNo2%>')[<%=idx%>].value=codeNo;
			window.dialogArguments.document.all('<%=codeCd2%>')[<%=idx%>].value=code;
			window.dialogArguments.document.all('<%=codeName2%>')[<%=idx%>].value=name;
			window.dialogArguments.document.all('<%=codeView2%>')[<%=idx%>].value=code+" "+name;
    		}	 
    	}
	 
	 
		
		self.close();
	   }
		
	}
}
//검색
function search(){
	var frm = document.forms[0];
	divSearch(); //검색 지연창 보이기 	
    
	setBtFlag('S'); //검색확인

	frm.action = 'comCodeList.do';
	if (frm.paramDiv.value=='null'){   //초기 조회시
		frm.paramDiv.value=frm.param.value;
	}

	if (frm.radioSel[0].checked){		//직무+부서
//		alert(frm.CHK1.value + '/'+ frm.btFlag.value);
		if(frm.CHK1.value=='Y' & frm.btFlag.value=='C'){ //직무 선택 후 프로젝트 조회
			frm.code.value ='';
			frm.codeName.value ='';
			frm.param.value='pjt_new';
			frm.paramId.value='pjt_1';
		}else if(frm.CHK1.value=='Y' & frm.btFlag.value=='S'){	//직무+부서 조회
			frm.param.value='pjt_new';
			frm.paramId.value='pjt_1';
		}else{							//직무 조회
			frm.param.value='job_new';
			frm.paramId.value='job_1';
		}
	}else {		
		//프로젝트 조회
		if (frm.paramDiv.value=='sale'||frm.param.value=='sale'){      //영업보고
			frm.paramId.value='pjt_5';
			frm.paramDiv.value='sale';
		}else if(frm.paramDiv.value=='group'||frm.param.value=='group'){//합동보고
			frm.paramId.value='pjt_4';
			frm.paramDiv.value='group';
		}
		frm.param.value='pjt_new';
	}

	frm._ACT.value = 'AC';	
	frm._SCREEN.value = '/common/comPopup4.jsp?empid='+frm.empId.value
											+'&code='+ frm.code.value 
											+'&CHK1='+ frm.CHK1.value 
											+'&btFlag='+ frm.btFlag.value 
											+'&codeName=' + frm.codeName.value ; 

	frm.code.value=frm.code.value.toUpperCase() ;
	frm.codeName.value=frm.codeName.value.toUpperCase();
	
	document.onmousedown=dbclick; //더블클릭 방지
	frm.submit();
}

//공정 정보리스트
function searchPrcsList(){

	var ifrm  = ifmPrcsList.form1;
	
	if (document.form1.code2.value=="") {
		alert("프로젝트를 먼저 선택 하십시오!"); 
		return;
	}
	
	ifrm.action='comCodeList.do';
	ifrm._SCREEN.value = '/common/comPopupPrcsDetail.jsp';
	ifrm.param.value="prcs_1";
	ifrm.code.value=document.form1.code2.value;
	ifrm.target="";
	ifrm.submit();
}

function dbclick() 
{
    if(event.button==1) alert("잠시 기다려 주십시요. 현재 처리중입니다.");  
}
//직무/프로젝트 1차 선택여부
function setChk() 
{
	var frm = document.forms[0];
    frm.CHK1.value="Y";
	frm.code.value ='';
	frm.codeName.value ='';
}
//선택('C')-검색('S') 구분
function setBtFlag(chkflag) 
{
	var frm = document.forms[0];
    frm.btFlag.value=chkflag;
}
//Radio체크  초기화
function setRadio() 
{
	var frm = document.forms[0];
    frm.CHK1.value='N';
}
//
--></script>
</head>

<body text="#000000" bgcolor="#FFFFFF" topmargin="0" leftmargin="0"
	rightmargin="0"  >
<!--검색 지연 창-->
<div id=processing style='position:absolute;display="none";z-index=999;background-color="#f0fff0";'></div>	
<form method="POST" action="" name='form1'>
<input type='hidden' name='_ACT'> 
<input type='hidden' name='_SCREEN'> 
<input type='hidden' name='paramDiv' value="<%=request.getParameter("paramDiv")%>"> 
<input type='hidden' name='param' value="<%=param%>"> 
<input type='hidden' name='paramId' value="<%=param%>"> 
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
<input type="hidden" name="code2" value=''>
<INPUT TYPE='hidden' NAME='CHK1' VALUE='<%=CHK1%>' SIZE='1'>
<INPUT TYPE='hidden' NAME='btFlag' VALUE='<%=btFlag%>' SIZE='1'>

<input type="hidden" name="idx" value='<%=idx%>'>



<table cellpadding="1" cellspacing="1" class="table1" border="0" width="100%">
	<tr>
		<td class="td2">
		<table cellpadding="2" cellspacing="1" border="0" width="100%">
			<tr>
				<td width="20"><img src="../images/i_formtitle.gif" alt=""></td>
				<td><B>검색</B></td>
				<td width="50" class="td2">
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

<table border="0">
	<tr height="1">
		<td></td>
	</tr>
</table>


<Table cellpadding="0" cellspacing="1" border="0" class="table1"
	width="100%">
	<tr>
		<td width="">
		<Table cellpadding="0" cellspacing="0" border="0" class="table1"
			width="100%">
			<tr>
				<%if( param.equals("emp")){ %>
				<td width="25%" height="24" class="TD1">조직명</td>
				<%}else{ %>
				<td width="25%" height="24" class="TD1">코드검색</td>
				<%} %>
				
				<td width="10%" height="24" class="TD2"><input name="code" size="12"
					maxlength="12" style="ime-mode:inactive;" onkeyup="checkEngField(this);" 
					onkeydown="if(event.keyCode==13){search();}"
					value="<%=(code!=null)?code:""%>"></td>
				<td width="25%" height="24" class="TD1">이름검색</td>
				<td width="10%" height="24" class="TD2"><input name="codeName" size="15"
					maxlength="15" style="ime-mode:active;"
					onkeydown="javascript:if(event.keyCode==13){search();}"
					value="<%=(codeName!=null)?codeName:""%>"></td>
			</tr>
		</Table>
		</td>	
		<td width="50" class="td2" align=right>
		<Table cellpadding="0" cellspacing="0" border="0" class="table2" width="50">
			<tr >
				<td width="5" class="td2"></td>
				<td class="bt_f" nowrap></td>
				<td class='button' width='50' style="cursor:hand;"
					onMouseOver="this.className='buttonOn'"
					onMouseOut="this.className='button'"
					onclick="search();">검색</td>
				<td class="bt_e" nowrap></td>						
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
					onclick="setRadio();search();"
					value='job_radio' <%=(radioSel.equals("job_radio"))?"checked":" "%>></td>
				<td width="25%" height="24" class="TD1">프로젝트보고
				<input type='radio' name="radioSel"  
					onclick="setRadio();search();"
					value='pjt' <%=(radioSel.equals("pjt"))?"checked":" "%>></td>
				
			</tr>
		</Table>
		<table border="0">
			<tr height="1">
				<td></td>
			</tr>
		</table>
		
<div id="prcs_tab" style="display:none">		
<img src="../images/tabimage/popup_project_en.gif"
	onclick="toggleObj('togg_List',1,1);toggleObj('tab_popInfo',2,1);"
	id="tab_popInfo" alt=""><img
	src="../images/tabimage/popup_project_dis.gif"
	onclick="toggleObj('togg_List',1,1);toggleObj('tab_popInfo',2,2);"
	id="tab_popInfo" style="display:none" alt=""><img
	src="../images/tabimage/detailWorkList_en.gif"
	onclick="searchPrcsList();toggleObj('togg_List',1,2);toggleObj('tab_popInfo',2,3);"
	id="tab_popInfo" style="display:none" alt=""><img
	src="../images/tabimage/detailWorkList_dis.gif"
	onclick="searchPrcsList();toggleObj('togg_List',1,2);toggleObj('tab_popInfo',2,4);"
	id="tab_popInfo" alt="">
</div>
<div id="togg_List">		
		<Table cellpadding="0" cellspacing="1" border="0" class="table1"
			width="100%" >
			<tr>
				<td width="90%" height="24" class="TD1">조회 내역</td>
				<td width="" height="24" class="TD1">
				<%if(dtos!=null) {%>
				<table cellspacing=0 cellpadding=0 border=0>
					<tr>				
					<td class="bt_f" nowrap></td>
					<td class='button' width='15' style="cursor:hand;"
						onMouseOver="this.className='buttonOn'"
						onMouseOut="this.className='button'"
						onclick="setLevel('0_All','table1')">+</td>
					<td class="bt_e" nowrap></td>			
					</tr>
				</table>				
				<%} %>
				</td>
			</tr>
		</Table>

<%
if(dtos!=null ){
%>

<Table cellpadding="0" cellspacing="0" border="0"	width="100%" id="table1">
		
		<% for (int i=0;i<dtos.length;i++){ %>
		<tr id="<%=dtos[i].level%>_<%=dtos[i].highCode%>"  bordercolor="#0066CC"
			class='onbase'
			style="cursor:hand;<%if (dtos[i].level> Utility.parseInt(dto[0].param2)){ out.print("display:none");}%>" 
			onMouseOver="this.className='on'" onMouseOut="this.className='onbase'"
			 >		
			<td width="85%" height="24" align="left" onclick="setLevel('<%=dtos[i].level%>_<%=dtos[i].codeNo%>','table1');">
				<!--Tree생성  -->
				<%=Utility.makeTree(dtos,i)%>
				<!--DATA 조회  -->
				<% if(!dtos[i].code2.equals("")&& !param.equals("pjt_new")){%><%=dtos[i].code2 %><%}else{%><%=dtos[i].code %><%}%>_
				<% if(dtos[i].code.substring(0,1).equals("B")){%><font color=blue>[영업]</font><%} %>
				<% if(!dtos[i].codeName2.equals("")&& !param.equals("pjt_new")){%><%=dtos[i].codeName2 %><%}else{%><%=dtos[i].codeName %><%}%>
			</td>
			<td style='display:none'><%=dtos[i].codeNo %>
			</td>
			<td width="10%" align="center" >
				<!--선택버튼 Y/N -->
				<% if (!dtos[i].selFlag.equals("N")){%>

				<table cellspacing=0 cellpadding=0 border=0>	
					<td class="bt_f" nowrap></td>
					<td class='button' width='40' style="cursor:hand;"
						onMouseOver="this.className='buttonOn'"
						onMouseOut="this.className='button'"
						onclick="setBtFlag('C');setChk();confirmItem('<%=dtos[i].codeNo%>',
										  '<%=dtos[i].code%>','<%=dtos[i].codeName %>',
										  '<%=dtos[i].code2%>','<%=dtos[i].codeName2 %>');">선택</td>
					<td class="bt_e" nowrap></td>	
				</table>														  
														  
				<%} %>
			</td>
		</tr>
	<%}%>
</Table>
<%	} else if (dtos==null) { %>
<Table cellpadding="0" cellspacing="1" border="0" class="table2"	width="100%" >
	<tr onMouseOver="this.className='on'" onMouseOut="this.className=''">	
		<td colspan="2" height="24" align='center'>해당 자료가 없습니다(클릭하면 해당컬럼에 공백이
		들어갑니다).</td>
	</tr>
</Table>
<%} %></div>

 <!--공정  조회--> 
<div id="togg_List" style="position:relative;left:0;top:0;display:none; overflow-x:none; overflow-y:none;">
	<iframe name='ifmPrcsList' src="/common/comPopupPrcsDetail.jsp" frameborder="0"  marginwidth="0" marginheight="0" 
	width="100%" height="70%" scrolling=''></iframe> 
</div>  

</form>
</body>
<script>
	//pjt+공정보고
	if(document.forms[0].radioSel[1].checked){
		document.all("prcs_tab").style.display="";

		//Tab Setting
	    toggleObj("togg_List",1,1);
		toggleObj("tab_popInfo",2,1);
	//직무+조직보고
	} else {
		document.all("prcs_tab").style.display="none";
	}
	
	//조회조건이 있을때 전체 보기
	if (document.forms[0].code.value != '' || document.forms[0].codeName.value != ''){
		setLevel('0_All','table1');
	}
</script>
</html>
