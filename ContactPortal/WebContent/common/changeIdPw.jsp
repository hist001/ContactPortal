<!--
	�� �� ��  : changeIdPw.jsp
	�ۼ�����  : 2008-12-24
	�� �� ��  : mailbest
	��    ��  : ���̵�/�н����� ����
-->
<%@ page contentType="text/html;charset=EUC-KR" errorPage="/common/error.jsp"%>
<%@ page import="com.wms.common.beans.dto.DataSet"%>
<%@ page import="com.wms.fw.util.DateUtil"%>
<%@ page import="com.wms.fw.Utility"%>
<%@ page import="com.wms.fw.servlet.HttpUtility" %>
<%@ page import="com.wms.fw.servlet.Box" %>
<%
	request.setCharacterEncoding("ksc5601");
	Box box = HttpUtility.getBox(request);

	String[] parameters=new String[20];
	
	request.setAttribute("fileName","Common.xml");
	request.setAttribute("idx","COM_002");
	request.setAttribute("parameters",parameters);
	request.setAttribute("setString","Y");//parameter�� �÷������� �����Ҷ� ���
	
	String currentDate = DateUtil.getTodayString("-");
	                                                                                                                                                                                                                                                 
	parameters[0]= "S_EMPID"; 
	parameters[1]=box.get("empId");
	
	parameters[2]= "S_PW";
	parameters[3]=box.get("PW");
%>
<jsp:include page="/common/comDataSet.jsp" flush="true"/>
<%
DataSet set=(DataSet)request.getAttribute("set");	
%>
<%	
int idx=0;
if (set!=null)	idx=set.getLength("ID"); 
%>
 
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7, IE=EmulateIE9"/>
<base target="_self" />
<meta http-equiv="Content-Language" content="ko">
<meta http-equiv="Content-Type" content="text/html; charset=ks_c_5601-1987">
<LINK rel="stylesheet" type="text/css" href="../css/skin04_viewstyle.css">
<script Language="JavaScript" src="/common/link/comm_check.js"></script>
<script Language="JavaScript" src="/common/link/common.js" type="text/javascript"></script>
<script language='javascript'>
<!--
	//VARCHAR2 
	var varNames = new Array()
	var varLengs = new Array()
	
	//NUMBER 
	var numNames= new Array('')
	var DATA_PRECISION= new Array(0)
	var DATA_SCALE= new Array(0)
	
	//VCHAR DT 
	var vdtNames = new Array()
	var vdtLengs = new Array()
	
	var nullChkAttribute = new Array('EMPID','ID','PASSWORD','PW');
	var nullChkName = Array('���','���̵�','���Է�','�н�����');
	
	var itemIdx=<%=idx%>;

function search(){
	divSearch(); //�˻� ����â ���̱� 
	var frm =document.forms[0];
	frm.submit();
}
function save(){
	var frm =document.forms[0];
	if(gfn_check()){
		if(frm.PASSWORD.value!= frm.PW.value){
			alert("�н����尡 ���� ���� �ʽ��ϴ�.");
			frm.PASSWORD.focus();
			return;
		}
		frm._DATA.value='<%=box.get("empId")%>';
		frm.action = 'saveIdPw.do';
		frm._SCREEN.value='saveOk.jsp';
		alert('����Ǿ����ϴ�.');
		frm.submit();
		self.close();
	}
}
//-->
</script>
</head>
<title>ID/PW ����</title>
<body text="#000000" bgcolor="#FFFFFF" topmargin="0" leftmargin="0"	rightmargin="0" onload="">
<form name="form1" method="POST" action="">
<input type='hidden' name='_SCREEN' >
<input type='hidden' name='_DATA' value=''>
<!--�˻� ���� â-->
<div id=processing style='position:absolute;display="none";z-index=999;background-color="#f0fff0";'></div>
	<table cellpadding="1" cellspacing="1" class="table1" border="0" width="100%">
		<tr align=right>
			<td width=''class="td2" >
			<table><td>
				<jsp:include page="/common/comButton.jsp?objName=���̵� �˻�&onClick=search()" flush="false"/></td><td>									
				<jsp:include page="/common/comButton.jsp?objName=���&onClick=save()" flush="false"/></td><td>									
				<jsp:include page="/common/comButton.jsp?objName=�ݱ�&onClick=self.close();" flush="false"/></td><td>									
			</table>
			</td>
		</tr>
	</table>	
	<table cellpadding="1" cellspacing="1" class="" border="0" width="100%" height='70%'>
		<tr align='center' >
			<td width='20'></td>
			<td class="td2" align='center'>
				<table border='0' width='200'>
					<tr align=left>
						<td width=''class="td2" >���</td>
						<td><input type='text' name='empId' size='20' value='<%=box.get("empId")%>'
							onkeydown="javascript:if(event.keyCode==13){search();}"></td>
					</tr>
					  
					<tr align=left style='<%if(idx==0) out.println("display:none");%>'>
						<td width=''class="td2" >���̵�</td>
						<td><input type='text' name='ID' size='20' value='<%if(idx!=0) out.println(set.get("ID"));%>'></td>
					</tr>
					<tr align=left>
						<td width=''class="td2" >�н�����</td>
						<td><input type='password' name='PW' size='20' value='<%=box.get("PW")%>'
							onkeydown="javascript:if(event.keyCode==13){search();}">
						</td>
					</tr>
					  
					<tr align=left style='<%if(idx==0) out.println("display:none");%>'>
						<td width=''class="td2" >���Է�</td>
						<td><input type='password' name='PASSWORD' size='20' value='<%=box.get("PASSWORD")%>'></td>
					</tr>
					
					<tr align=left>
						<td width=''class="td2" height='40' colspan='2'></td>
					</tr>
					<tr align=left>
						<td width=''class="td2" align='center' colspan='2' style='color:blue'>
							<%if(idx==0 && !box.get("PW").equals("")){ out.println("�н����尡 Ʋ�Ƚ��ϴ�.!!!");}%>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>	
</form>
</body>
</html>


