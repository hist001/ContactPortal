<!--
	�� �� ��  : comPopupJobOrgDetail.jsp
	�ۼ�����  : 2006.08.01
	�� �� ��  : mailbest
	��    ��  : ����/���� �˾�
-->
<%@ page contentType="text/html;charset=ksc5601" errorPage="/common/error.jsp"%>
<%@ page import="com.wms.comPopup.beans.dto.ComPopupDTO"%>
<%@ page import="com.wms.fw.Utility" %>
<%@ page import="com.wms.beans.dto.*" %>
<%@ page import="java.util.*" %>

<jsp:useBean id="user" class="com.wms.beans.dto.UserInfo" scope="session"/>

<%	//�˻�ȭ�鿡�� ���̺�� ���� �ڵ��̸� ǥ��
	String param       =  request.getParameter("param");
	String paramId     =  request.getParameter("paramId");
	String code        =  request.getParameter("code");
	String codeName    =  request.getParameter("codeName");
	
    ComPopupDTO[] dtos = (ComPopupDTO[])request.getAttribute("returns");
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
<title>�ڵ� �˻�</title>
<script Language="JavaScript" src="/common/link/common.js"	type="text/javascript"></script>
<script Language="JavaScript" src="/common/link/jobSearch.js"	type="text/javascript"></script>
<script language='javascript' type="text/javascript">
<!--
function confirmItem(codeNo,code,name,code2,name2){
	 var opener = parent;
  	 opener.confirmItem1(codeNo,code,name);
}
//-->
</script>
</head>

<body text="#000000" bgcolor="#FFFFFF" topmargin="0" leftmargin="0"	rightmargin="0">

<form  method="POST"  name="form1" >
<input type="hidden" name="_SCREEN" >
<input type='hidden' name='_ACT'> 
<input type='hidden' name='param' value="<%=param%>"> 
<input type='hidden' name='paramId' value="<%=paramId%>"> 
<input type='hidden' name='code' value=""> 

<Table cellpadding="0" cellspacing="1" border="0" class="table1"
	width="100%" >
	<tr>
		<td width="90%" height="24" class="TD1">��ȸ ����</td>
		<td width="" height="24" class="TD1"><%if(dtos!=null) {%><input type="button"  onclick="setLevel('0_All','table1')"  value=" + "><%} %></td>
	</tr>
</Table>

<%
if(dtos!=null ){
%>
<Table cellpadding="0" cellspacing="1" border="0"	width="100%" id="table1">
	
		<% for (int i=0;i<dtos.length;i++){ %>
		<tr id="<%=dtos[i].level%>_<%=dtos[i].highCode%>"   bordercolor=#0066CC
			class='onbase' style ="cursor:hand;<%if (dtos[i].level>= 1){ out.print("display:none");}%>"
			onMouseOver="this.className='on'" onMouseOut="this.className='onbase'" 
			onclick="setLevel('<%=dtos[i].level%>_<%=dtos[i].codeNo%>','table1');" >		
			<td width="85%" height="24" align="left">
				<!--Tree����  -->
				<%=Utility.makeTree(dtos,i)%>
				<!--DATA ��ȸ  -->
				<% if(!dtos[i].code2.equals("")){%><%=dtos[i].codeNo %><%}else{%><%=dtos[i].codeNo %><%}%>_
				<% if(!dtos[i].code2.equals("")){%><%=dtos[i].code2 %><%}else{%><%=dtos[i].code %><%}%>_
				<% if(!dtos[i].codeName2.equals("")){%><%=dtos[i].codeName2 %><%}else{%><%=dtos[i].codeName %><%}%>
			</td>
			<td style='display:none'><%=dtos[i].codeNo %>
			</td>
			<td width="9%" align="center" >
				<!--���ù�ư Y/N -->
				<% if (!dtos[i].selFlag.equals("N")){%>
				<input type="button" onclick="confirmItem('<%=dtos[i].codeNo%>',
														  '<%=dtos[i].code%>','<%=dtos[i].codeName %>',
  													      '<%=dtos[i].code2%>','<%=dtos[i].codeName2 %>');" 
  													      value="����" style="cursor:hand;">
				<%} %>
			</td>
		</tr>

		<%}%>
</Table>
<%	} else { %>
<Table cellpadding="0" cellspacing="1" border="0" class="table1" width="100%" >

	<tr class='base' style="cursor:hand;" onMouseOver="this.className='on'"
		onMouseOut="this.className='base'">
		<td colspan="2" height="24" align='center'>�ش� �ڷᰡ �����ϴ�(Ŭ���ϸ� �ش��÷��� ������
		���ϴ�).</td>
	</tr>
</Table>

<%} %>

</form>
</body>
<script type="text/javascript">
	//��ȸ������ ������ ��ü ����
		setLevel('0_All','table1');
</script>
</html>
