<!--
	�� �� ��  : comPopupMulit.jsp
	�ۼ�����  : 2008.11.27
	�� �� ��  : mailbest
	��    ��  : ���� �˻� ȭ��(��Ƽ����)
-->
<%@ page contentType="text/html;charset=ksc5601" errorPage="/common/error.jsp"%>
<%@ page import="com.wms.comPopup.beans.dto.ComPopupDTO" %>
<%@ page import="com.wms.popupSet.beans.dto.PopupSetDTO" %>
<%@ page import="com.wms.beans.dto.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.wms.fw.util.DateUtil"%>
<%@ page import="com.wms.fw.servlet.HttpUtility"%>
<%@ page import="com.wms.fw.Utility"%>
<%@ page import="com.wms.fw.servlet.Box"%>
<jsp:useBean id="user" class="com.wms.beans.dto.UserInfo" scope="session"/>
<%	
	request.setCharacterEncoding("ksc5601");
	Box box = HttpUtility.getBox(request);

	//�˻�ȭ�鿡�� ���̺�� ���� �ڵ��̸� ǥ��
	String param       =  request.getParameter("param");
	String paramId     =  request.getParameter("paramId");
	String code        =  request.getParameter("code");
	String codeName    =  request.getParameter("codeName");
	String objcodeNo   =  request.getParameter("objcodeNo");
	String idx		   =  request.getParameter("idx");	

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
<LINK rel="stylesheet" type="text/css" href="../css/skin04_viewstyle.css">
<style type="">
<!--
.viewContentL {text-align:left;cursor:hand;border-style:solid; border-color:#EBEEF0 #EBEEF0 #EBEEF0 #EBEEF0;border-width:0 0 1 0}
.viewContentC {text-align:center;cursor:hand;border-style:solid; border-color:#EBEEF0 #EBEEF0 #EBEEF0 #EBEEF0;border-width:0 0 1 0}
.viewContentR {text-align:right;cursor:hand;border-style:solid; border-color:#EBEEF0 #EBEEF0 #EBEEF0 #EBEEF0;border-width:0 0 1 0}
-->
</style>
<script Language="JavaScript" src="/common/link/common.js"	type="text/javascript"></script>
<script Language="JavaScript" src="/common/link/combo.js"></script>	
<script Language="JavaScript" src="/common/link/comAjax.js"></script>
<script Language="JavaScript" src="/common/link/jobSearch.js"	type="text/javascript"></script>
<title>�ڵ� �˻�</title>
<script language='javascript' type="text/javascript">
<!--
//���ã�� �ʱ�ȭ
function init(){
<%if (user.auths.containsKey("DocAdmin")){%>
	fn_combo('paremCb1',new Array('fav_popup','<%=user.empId%>'));
<%}%>	
}

//���� ���
function confirmItem(){
	 var opener = window.dialogArguments;
	 var frm = opener.document;

	 codeNo		=	getChkCodeList(); //üũ��� String ó��
	
	 //�˾� ����â������ ó�� �Լ� ȣ��
	//if (codeNo!="") 
	opener.setPopUpData(codeNo);
	self.close();
}

//�˻�
function search(){
	var frm = document.forms[0];
	try{
		if(frm.HIGHDOCNO.value=='' && frm.highCode.value==''){
		    opener = window.dialogArguments;
			frm.highCode.value=opener.document.forms[0].S_DOCTYPE.value;	
			frm.HIGHDOCNO.value=opener.document.forms[0].S_POPHIGHDOCNO.value;	
		}
	}catch( e ){
	
	}

	divSearch(); //����â ���̱� 	
	frm.action = 'comCodeList.do';
	frm._SCREEN.value = '/common/comPopupMulti.jsp?empid='+frm.empId.value+'&code='+ frm.code.value + '&codeName=' + frm.codeName.value ; 

	frm.code.value=frm.code.value.toUpperCase() ;
	frm.codeName.value=frm.codeName.value.toUpperCase();

	frm.submit();
	window.focus();
}

//���� ���� �̵�
function setHighcode(param){
	var frm = document.forms[0];
	frm.HIGHDOCNO.value=param;
	search();
}
//üũ ��� Stringó��
function getChkCodeList(){
	var frm = document.forms[0];
    var selCodeList= "";
    var fChk="N";
    var chkRowCnt=0;

   	<%if(dtos!=null){%>
		 if (frm.all("dflag").length==undefined ){
			 if (frm.dflag.value=="D") {
				 selCodeList= "<%=dtos[0].codeNo %>��<%=dtos[0].code %>��<%=dtos[0].codeName %>";
				 chkRowCnt=1;
			 }
		}else{	 
			<%for(int i=0 ; i<dtos.length ; i++) {%>  
			if(frm.dflag[<%=i%>].value=="D"){
			    if(fChk=="N"){
				    selCodeList= "<%=dtos[i].codeNo %>��<%=dtos[i].code %>��<%=dtos[i].codeName %>";
				    fChk ="Y";
			    }else{
				    selCodeList= selCodeList +"��"+"<%=dtos[i].codeNo %>��<%=dtos[i].code %>��<%=dtos[i].codeName %>";
			    }
				chkRowCnt=chkRowCnt+1;
		    }
   			<%}%>
   		}  
   <%}%>	
   
   if (chkRowCnt==0) return "";
   
   selCodeList=selCodeList+"��<%=objcodeNo%>��<%=idx%>";   
   return selCodeList;
}
//���ã�� �ڵ���
function addComboCd(){
	if(confirm("���ã��� ��� �Ͻðڽ��ϱ�?")){
	var frm = document.forms[0];
	gfn_ajax_request('',new Array("fav_popup",'<%=user.empId%>',frm.HIGHDOCNO.value),'ComPopup','FAV_002');
	init();
	}
}
//���ã�� �ڵ����
function rmComboCd(){
	if(confirm("���ã�⿡��  ���� �Ͻðڽ��ϱ�?")){
		var frm = document.forms[0];
		gfn_ajax_request('',new Array("fav_popup",'<%=user.empId%>',frm.HIGHDOCNO.value),'ComPopup','FAV_003');
		init();
	}
}

//-->
</script>
</head>

<body onload='init()' text="#000000" bgcolor="#FFFFFF" topmargin="0" leftmargin="0"	rightmargin="0"  >
<form method="POST" action="" name='form1' >
<!--�˻� ���� â-->
<div id=processing	style='position:absolute;display="none";z-index=99;background-color="#f0fff0";'></div>
<input type='hidden' name='_ACT'> 
<input type='hidden' name='_SCREEN'> 
<input type='hidden' name='param' value="<%=param%>"> 
<input type='hidden' name='paramId' value="<%=paramId%>"> 
<input type="hidden" name="empId" value='<%=user.empId%>'>

<input type="hidden" name="empOrgNo" value='<%=user.empOrgNo%>'>
<input type="hidden" name="empOrgCd" value='<%=user.empOrgCd%>'>
<input type="hidden" name="empOrgName" value='<%=user.empOrgName%>'>

<input type="hidden" name="highCode" value=''>
<input type="hidden" name="HIGHDOCNO" value='<%=box.get("HIGHDOCNO")%>'>
<input type="hidden" name="codeNo" value='<%=objcodeNo%>'>
<input type="hidden" name="objcodeNo" value='<%=objcodeNo%>'>
<input type="hidden" name="idx" value='<%=idx%>'>

<table cellpadding="1" cellspacing="1" class="table1" border="0" width="100%">
	<tr>
		<td class="td2">
		<table cellpadding="0" cellspacing="0" border="0" width="100%">
			<tr>
				<td width="20"><img src="../images/i_formtitle.gif" alt=""></td>
				<td><B>�˻�</B></td>
				<td width=50 align="right">
				<table cellpadding="0" cellspacing="0" border="0" align="right">
					<td class="bt_f" nowrap></td>
					<td class='button' width='50' style="cursor:hand;"
						onMouseOver="this.className='buttonOn'"
						onMouseOut="this.className='button'"
						onclick="self.close();">�ݱ�</td>
					<td class="bt_e" nowrap></td>			
				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>

<table border="0"><tr height="1"><td></td></tr></table>
<%if (user.auths.containsKey("DocAdmin")){%>
<table border="0">
	<tr height="1">
		<td width='100%'>				
			<select id='paremCb1' name='paremCb1' exid=''  defaultType='Y' prefix=':' flag='1' xml_id='S_051' rev_value='<%=box.get("paremCb1")%>' onChange='setHighcode(this.value)'></select>
		</td>
		<td name="addFd" style='cursor:hand' align='right'>
			<img id="addFdBtn" name='addFdBtn' src='/images/edms/rmFolder.png' alt="���ã�� ����"
			 onClick="rmComboCd()"  
			 onMouseOver= "this.src='/images/edms/rmFolder_over.png'"
			 onMouseOut="this.src='/images/edms/rmFolder.png'"
			></td>			
		<td name="addFd" width='15' style='cursor:hand' align='right'>
			<img id="addFdBtn" name='addFdBtn' src='/images/edms/mkFolder.png' alt="���ã��  �߰�"
			 onClick="addComboCd()"  
			 onMouseOver= "this.src='/images/edms/mkFolder_over.png'"
			 onMouseOut="this.src='/images/edms/mkFolder.png'"
			></td>
	</tr>
</table>
<%}%>			

<table border="0"><tr height="1"><td></td></tr></table>

<Table cellpadding="0" cellspacing="1" border="0" class="table1" width="100%">
	<tr>
		<td width="">
			<Table cellpadding="2" cellspacing="0" border="0" class="table1" width="100%">
				<tr>
				<%if( param.equals("emp")){ %>
					<td width="24%" height="24" class="TD1">������</td>
					<td width="10%" height="24" class="TD2" align=center>
						<input name="code" size="10" maxlength="10" style="ime-mode:active;" onkeydown="javascript:if(event.keyCode==13){search();}" value="<%=(code!=null)?code:""%>">
					</td>
				<%}else{ %>
					<td width="24%" height="24" class="TD1">�ڵ�˻�</td>
					<td width="10%" height="24" class="TD2" align=center>
						<input name="code" size="10" maxlength="10" style="ime-mode:inactive;" onkeyup="checkEngField(this);" onkeydown="javascript:if(event.keyCode==13){search();}" value="<%=(code!=null)?code:""%>">
					</td>
				<%} %>
					<td width="24%" height="24" class="TD1">�̸��˻�</td>
					<td width="10%" height="24" class="TD2">
						<input name="codeName" size="15" maxlength="15" style="ime-mode:active;" onkeydown="javascript:if(event.keyCode==13){search();}" value="<%=(codeName!=null)?codeName:""%>">
					</td>
				</tr>
			</Table>
		</td>	
		<td width="60" class="td2" align="right">
			<jsp:include page="/common/comButton.jsp?objName=�˻�&onClick=search()" flush="false"/>
		</td>
	</tr>
</Table>
<table border="0">
	<tr height="1">
		<td></td>
	</tr>
</table>
<Table cellpadding="0" cellspacing="1" border="0" class="table1" width="100%" >
	<tr>
		<td class="TD1">
		<%if(dtos!=null) {%>
			<table width='100%' cellspacing=0 cellpadding=0 border=0>
				<tr>				
					<td width="" height="24" class="TD1">��ȸ ����</td>
					<td width='50' align='center'>
						<jsp:include page="/common/comButton.jsp?objName=+&onClick=setLevel('0_All','table1')" flush="false"/>						
					</td>	
					<td width="60" class='viewContentH'>
						<input type='checkbox' name='selChk' onClick='selCheck(this,"chkFlag")' style=':none;border:none'>
					</td>							
					<td width='50'align='right'>
						<jsp:include page="/common/comButton.jsp?objName=����&onClick=confirmItem();" flush="false"/>
					</td>
				</tr>
			</table>
		<%}else{ %>
			<table width='100%' cellspacing=0 cellpadding=0 border=0>
				<tr>				
					<td width="" height="24" class="TD1">��ȸ ����</td>
				</tr>
			</table>
		<%} %>
		</td>
	</tr>
</Table>
	<div id='divList' style="position:relative;left:0;top:0;height:500;overflow-x:none;background:; overflow-y:auto;z-index:5">
<%
if(dtos!=null ){
%>
<table width='100%' border=0>
<tr>
<td width="">
<div style="position:relative;left:0;top:0;height:80%;width:100%; overflow-x:none; overflow-y:auto;">
<Table cellpadding="0" cellspacing="0" border="0" width="100%" height="100%" id="table1">
		<% for (int i=0;i<dtos.length;i++){ %>
		
		<tr id="<%=dtos[i].level%>_<%=dtos[i].highCode%>"   bordercolor=#0066CC 
			class='onbase' style ="cursor:hand;<%if (dtos[i].level> Utility.parseInt(dto[0].param2)){ out.print("display:none");}%>"
			onMouseOver="this.className='on'" onMouseOut="this.className='onbase'">		
			<td width="" height="24" align="left" onclick="setLevel('<%=dtos[i].level%>_<%=dtos[i].codeNo%>','table1');" >
				<!--Tree Level ����  -->				
				<!--�˻������� �ִ°�� Tree Level ���� ���Ҷ� ��� START-->
				<%if (code.equals("") & codeName.equals("")){ %>			
					<%=Utility.makeTree(dtos,i)%>
				<%} %>
				<!-- END-->
				
				<!--DATA ��ȸ  -->
				<% if(param.equals("pjt_new")){%>
					<%=dtos[i].code %>
				<%}else{%>
					<%=dtos[i].code %>
				<%}%>_
				<% if(dtos[i].code.substring(0,1).equals("B")& param.equals("pjt_new")){%>
					<font color=blue>[����]</font>
				<%}else{%>
					<font color=blue></font>
				<%}%>
				
				<% if(param.equals("pjt_new")){%>
					<%=dtos[i].codeName %>
				<%}else{%>
					<%=dtos[i].codeName %>
				<%}%>	
				<%try {%>
				<font color='green' onClick="setHighcode('<%if(dtos[i].level==1){%><%=dtos[i].highCode%><%}else{%><%=dtos[i].codeNo%><%}%>')">
						<%if(Integer.parseInt(dtos[i].LEVELSUBCNT)>0 & dtos[i].level!=1){%>
							[<%=dtos[i].LEVELSUBCNT%>]
						<%}else if(Integer.parseInt(dtos[i].LEVELSUBCNT)>0 && dtos[i].level==1 && !dtos[i].highCode.equals("END")){%>
							[����]
						<%}%>
				</font>
				<%}catch (Exception e){	}finally{}%>

			</td>
			<td width="" align="right" >
				<!--üũ Y/N -->
				<% if (!dtos[i].selFlag.equals("N")){%>
				<table cellspacing=0 cellpadding=0 border=0 >
					<tr  class='onbase'>
						<td width='' style="cursor:hand;" >
			   		    <input type='hidden' name='dflag'   value='C' >	
						<input class='onbase' id='chkFlag' type='checkbox' name='chkBox' onClick='setChkFlag(this,"<%=i%>");' style='border:none' >
						</td>
					</tr>
				</table>				  
				<%}else{ %>
				<table cellspacing=0 cellpadding=0 border=0>
					<tr  class='onbase'>
						<td width='' style="cursor:hand;" >
			   		    <input type='hidden' name='dflag'   value='C' >	
						<input class='onbase' id='chkFlag' type='checkbox' name='chkBox' onClick='setChkFlag(this,"<%=i%>");' style='display:none' disabled>
						</td>
					</tr>
				</table>
				<%}%>
			</td>
		</tr>
		<%}%>
</Table>
</div>
</td>
</tr>
</table>
<%	} else if (dtos==null) { %>
<Table cellpadding="0" cellspacing="1" border="0" class="table2" width="100%" >
	<tr onMouseOver="this.className='on'" onMouseOut="this.className=''">
		<td colspan="2" height="24" align='center' onclick="confirmItem()">
		�ش� �ڷᰡ �����ϴ�(Ŭ���ϸ� �ش��÷��� ������ ���ϴ�).
		</td>
	</tr>
</Table>

<%} %>
</div>
</form>
</body>
<script>
	//��ȸ������ ������ ��ü ����
	if (document.forms[0].code.value != '' || document.forms[0].codeName.value != ''){
		setLevel('0_All','table1');
	}
	setDivSize(65); //div������ ����
</script>
</html>
