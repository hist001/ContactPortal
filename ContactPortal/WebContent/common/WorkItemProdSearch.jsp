<!--
	�� �� ��  : WorkItemProdSearch.jsp
	�ۼ�����  : 2004/06/22
	�� �� ��  : 
	��    ��  : ��ǰ �ڵ� �˻� ȭ��
-->
<%@ page contentType="text/html;charset=ksc5601"%>
<jsp:useBean id="codeFinder" class="com.wms.beans.CodeFinder" scope="application"/>

<%
//����ι�
String BL = request.getParameter("BL");
//�������
String BM = request.getParameter("BM");
//�����ȣ
String bizNo = request.getParameter("bizNo");
//url
String url = request.getParameter("url");
if(url==null)url="";
com.wms.beans.dto.ProdDTO[] prodList=(com.wms.beans.dto.ProdDTO[])request.getAttribute("prodList");%>
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
<title>��ǰ �˻�</title>
<script Language="JavaScript" src="/common/link/jobSearch.js"></script>
<script Language="JavaScript">
function selectEmpJob(bizNo,bizName){
	opener.bizReg(bizNo,bizName);
	self.close();
}
</script>
</head>

<body text="#000000" bgcolor="#FFFFFF" topmargin=0 leftmargin=0 rightmargin=0>
<form method="POST" action='/commonCon'>
<input type='hidden' name='_ACT' >
<input type='hidden' name='_SCREEN' value='/common/WorkItemProdSearch.jsp'>
<input type='hidden' name='url' value='<%=url%>'>
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
<Table  cellpadding=3 cellspacing=1 border=0 class=table1 width=100%>
	<td  width="10%" class=TD1>�۾�����</td>
	<td  width="15%" class=TD2>��ǰ&nbsp;</td>
</Table>
<table border=0><tr height=1><td></td></tr></table>
<Table  cellpadding=0 cellspacing=1 border=0 class=table1 width=100%>
	<tr id=rowa style=display>
		<td width="70%">
			<Table  cellpadding=1 cellspacing=1 border=0 class=table1 width=100%>
				<tr>
					<td width="40%" class=TD1>��� �ι�</td>
					<td width="60%" class=TD2>
						<select size="1" name="BL" onchange="javascript:change('RG')">
						<option value="null" >�����ϼ���..</option>
							<%=codeFinder.getHtml("BL",BL)%>
						</select></td>
				</tr>
				<tr>
					<td width="40%" class=TD1>��� ����</td>
					<td width="60%" class=TD2>
						<select size="1" name="BM" onchange="javascript:change('RC')">
						<option value="null" >�����ϼ���..</option>
						<%=codeFinder.getHtml(BL,BM)%>		</select></td>
				</tr>
				<tr>
					<td width="40%" class=TD1>��� �ڵ�</td>
					<td width="60%" class=TD2>
						<select size="1" name="bizNo" onchange="javascript:change('R')">
						<option value="null" >�����ϼ���..</option>	
						<%=codeFinder.getHtml(BM,bizNo)%>	</select></td>
				</tr>
			</table>
		</td>
	</tr>
</Table>
<table border=0><tr height=1><td></td></tr></table>
<%if(prodList!=null){%>
<div id=D1 style="display:<%=(prodList!=null)?" ":"none"%>" >
<Table  cellpadding=3 cellspacing=1 border=0 class=table1 width=100% id="table1">
	<tr>
		<td width="30%" class=TD1>��ǰ�ڵ�</td>
		<td width="70%" class=TD1>��ǰ��</td>
	</tr>
</Table>
<Table  cellpadding=3 cellspacing=1 border=0 class=table1 width=100% id="table1">
<%

	for(int i=0;i<prodList.length;i++){
	%>
	<tr class='td2' style ="cursor:hand;" onMouseOver="this.className='leftnavi1'" onMouseOut="this.className='td2'"	onclick=
   <%if(url.equals("empItem")){%>	
    "javascript:selectEmpJob('<%=prodList[i].prodType+
	                             prodList[i].prodNo%>',
						     '<%=prodList[i].prodName%>')"
   <%}else{%>
    "javascript:selectProd('<%=prodList[i].prodType+
                           prodList[i].prodNo%>')"
   <%}%>
	>
		<td width="30%" height="24" align="center"><%=prodList[i].prodType+prodList[i].prodNo%></td>
		<td width="70%" height="24"><%=prodList[i].prodName%></td>
	</tr>
   <%}//end of for%>
</table>
<%}else if(request.getParameter("_ACT")!=null&&request.getParameter("_ACT").equals("R")){%>
<Table  cellpadding=3 cellspacing=1 border=0 class=table1 width=100% id="table1">
 	<tr  class='base' style ="cursor:hand;" onMouseOver="this.className='on'" onMouseOut="this.className='base'" >
	
		<td >
		������ ��ǰ�� ���� �������� �ʽ��ϴ�.
		</td>

	</tr>
</table>

<%}%>
</form>
</body>
</html>