<!--
	�� �� ��  : allSearch.jsp
	�ۼ�����  : 2004/06/22
	�� �� ��  : ��뿵
	��    ��  : ���� �˻� ȭ��
-->
<%@ page contentType="text/html;charset=ksc5601"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.util.ArrayList"%>

<%	//�˻�ȭ�鿡�� ���̺�� ���� �ڵ��̸� ǥ��
	String param =  request.getParameter("param");

	String tableName = new String();
	if(param.equals("biz")){
		tableName = "���";
	}else if(param.equals("emp")){
		tableName = "����";
	}else if(param.equals("org")||param.equals("actOrg")||param.equals("dutyOrg1")||param.equals("dutyOrg2")||param.equals("dutyOrg3")||param.equals("dutyOrg4")){
		tableName = "����";
	}else if(param.equals("bizChag")){
		tableName = "�ŷ�ó";
	}else if(param.equals("curEmp")||param.equals("subWrEmp")||param.equals("pmEmp")||param.equals("saleEmp")){
		tableName = "����";
	}else if(param.equals("coCodeType")){
		tableName = "�ڵ�����";
	}else if(param.equals("histJob")){
		tableName = "����";
	}else if(param.equals("stdPrcsType")){
		tableName = "����";
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
<title>�ڵ� �˻�</title>
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
					<td width=20><img src="/images/i_formtitle.gif"></td><td><B><%=tableName%>�˻�</B></td>
					<td align=right>
						<table cellpadding=0 cellspacing=0 border=0>
							<tr><td width=5></td>
							<a href="javascript:winClose()">
								<td class="base"  style ="cursor:hand;" onMouseOver="this.className='on'" onMouseOut="this.className='base'" onMouseDown="this.className='down'" onMouseUp="this.className='on'">�ݱ�</td></a>
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
					<td width="25%" height="24" class=TD1><%=tableName%>�˻�(����1)</td>
					<td width="10%" height="24" class=TD2><input name="code" size="10" maxlength="10" onkeydown="javascript:if(event.keyCode==13){search();}"></td>
					<td width="25%" height="24" class=TD1><%=tableName%>�˻�(����2)</td>
					<td width="10%" height="24" class=TD2><input name="name" size="15" maxlength="15" style="ime-mode:active;" onkeydown="javascript:if(event.keyCode==13){search();}"></td>
				</tr>
			</Table>
		</td>
		<td width="15%" class=td2>
			<Table  cellpadding=0 cellspacing=0 border=0 class=table1 width=100%>
				<tr>
					<td width=5 class="td2"></td>
					<a href="javascript:search()">
						<td class="base"  style ="cursor:hand;"  onMouseOver="this.className='on'" onMouseOut="this.className='base'" onMouseDown="this.className='down'" onMouseUp="this.className='on'">�˻�</td>
					</a>
				</tr>
			</Table>
		</td>
	</tr>
</Table>
<table border=0><tr height=1><td></td></tr></table>
<%
if(returns!=null){   
	if(tableName.equals("����")/*||tableName.equals("ǥ�ذ����ڵ�Ÿ��")*/){
%>

<div id=D1 style="display" >
<Table  cellpadding=0 cellspacing=1 border=0 class=table1 width=100% id="table1">
	<tr>
		<td width="35%" height="24" class=TD1>����(����1)</td>
		<td width="35%" height="24" class=TD1>����(����2)</td>		
		<td width="30%" height="24" class=TD1>���</td>
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
		<td width="30%" height="24" class=TD1><%=tableName%>�ڵ�(����1)</td>
		<td width="70%" height="24" class=TD1><%=tableName%>��(����2)</td>
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
		<td width="30%" height="24" class=TD1><%=tableName%>�ڵ�(����1)</td>
		<td width="70%" height="24" class=TD1><%=tableName%>��(����2)</td>
	</tr>
</Table>
<Table  cellpadding=0 cellspacing=1 border=0 class=table1 width=100% id="table1">

	<tr class='base' style ="cursor:hand;" onMouseOver="this.className='on'" onMouseOut="this.className='base'" onclick="javascript:confirmItem('<%=param%>','','','')">
		<td colspan=2 height="24" align='center'>�ش� �ڷᰡ �����ϴ�(Ŭ���ϸ� �ش��÷��� ������ ���ϴ�).</td>
	</tr>
</table>
<%}%>
</form>
</body>
</html>