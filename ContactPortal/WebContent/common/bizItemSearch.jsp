<!--
	�� �� ��  : bizItemSearch.jsp
	�ۼ�����  : 2004/06/22
	�� �� ��  : 
	��    ��  : ��� �ڵ� �˻� ȭ��
-->
<%@ page contentType="text/html;charset=ksc5601" errorPage="/common/error.jsp"%>
<jsp:useBean id="codeFinder" class="com.wms.beans.CodeFinder" scope="application"/>
<jsp:useBean id="user" class="com.wms.beans.dto.UserInfo" scope="session"/>
<%
//����ι�
String BL = request.getParameter("BL");

%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7, IE=EmulateIE9"/>
<meta http-equiv="Content-Language" content="ko">
<meta http-equiv="Content-Type" content="text/html; charset=ks_c_5601-1987">
<LINK rel="stylesheet" type="text/css" href="/css/skin04_form.css">
<title>����ڵ� �˻�</title>
<script Language="JavaScript" src="/common/link/jobSearch.js"></script>
<script Language="JavaScript">
function selectEmpJob(){

	frm=document.forms[0]
	biz=frm.biz.options[frm.biz.selectedIndex].value
	idx = biz.indexOf(":");
	bizNo=biz.substring(0,idx);
	
	if(bizNo=='ZZZ'){
		var grpCd='<%=user.empOrgCd.substring(0,2)%>';
		if(!(grpCd=='CP'||grpCd=='CQ'||grpCd=='CR')){
			alert('�濵�����ι������� ��������� ����� �� �ֽ��ϴ�.');
			return;
		}
	}
	bizName=biz.substring(idx+1);
	opener.bizReg(bizNo,bizName);
	self.close();
}
function changeSelect(){
	document.forms[0].submit();
}
function changePrcsMode(prodType){
	var frm = document.forms[0];
	frm.action='/common/bizItemSearchProd.jsp?mode=prod&prodType='+prodType
	frm.submit();
}
</script>
</head>

<body text="#000000" bgcolor="#FFFFFF" topmargin=0 leftmargin=0 rightmargin=0>
<form method="POST" action='/common/bizItemSearch.jsp'>
<input type='hidden' name='_ACT' >
<input type='hidden' name='_SCREEN'>

<table cellpadding=1 cellspacing=1 class=table1 border=0 width=100%>
	<tr>
		<td class=td2>
			<table cellpadding=2 cellspacing=1 border=0 width=100%>
				<tr>
					<td width=20><img src="/images/i_formtitle.gif"></td><td><B>�������� >> �űԾ����׸�˻�</B></td>
					<td align=right>
						<table cellpadding=0 cellspacing=0 border=0>
							<tr>
								<td width=5></td>
								<a href="javascript:select()">
								<td class="base"  style ="cursor:hand;" onMouseOver="this.className='on'" onMouseOut="this.className='base'" onMouseDown="this.className='down'" onMouseUp="this.className='on'">Ȯ��</td></a>
								<td width=5></td>
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
<td width=100% >
	<Table  cellpadding=3 cellspacing=1 border=0 class=table1 width=100% >
	<td  width="20%" class=TD1 height=24>��&nbsp;��</td>
	<td  width="80%" class=TD2>
		<input type=radio name="rdoTmp" value="PROD" onclick="javascript:changePrcsMode('P')" class=Attach >��ǰ&nbsp; 
		<input type=radio id="rdoTmp" name="rdoTmp" value="TASK" onclick="javascript:changePrcsMode('T')" class=Attach >Task&nbsp; 
		<input type=radio name="rdoTmp" value="JOB" class=Attach checked>���
	</td>
	</table>
	</td>
</td>
</Table>
<table border=0><tr height=1><td></td></tr></table>
<Table  cellpadding=0 cellspacing=1 border=0 class=table1 width=100%>
	<tr id=rowa style=display>
		<td width="70%">
			<Table  cellpadding=1 cellspacing=1 border=0 class=table1 width=100%>
				<tr>
					<td width="40%" class=TD1>��� �ι�</td>
					<td width="60%" class=TD2>
						<select size="1" name="BL" onchange="javascript:changeSelect()">
						<option value="null" >�����ϼ���..</option>
							<%=codeFinder.getHtml("BL",BL)%>
						</select></td>
				</tr>

				<tr>
					<td width="40%" class=TD1>��� �ڵ�</td>
					<td width="60%" class=TD2>
						<select size="1" name="biz" onchange="javascript:selectEmpJob()">
						<option value="null" >�����ϼ���..</option>	
						<%
						if(BL!=null){
						com.wms.beans.dto.Code[] codes=codeFinder.get(BL);
						if(codes!=null)
						for(int i=0;i<codes.length;i++){
							com.wms.beans.dto.Code[] subCodes=codeFinder.get(codes[i].codeNo);
							if(subCodes!=null)
							for(int j=0;j<subCodes.length;j++){%>
                            <option value='<%=subCodes[j].codeNo+":"+subCodes[j].codeName%>' ><%=subCodes[j].codeNo+" "+subCodes[j].codeName%></option>
							<%
							}
						%>
						    
						<%}
				          }%>
						</select></td>
				</tr>
			</table>
		</td>
	</tr>
</Table>
</form>
</body>
</html>