<!--
	�� �� ��  : histJobNameSearch.jsp
	�ۼ�����  : 2004/06/22
	�� �� ��  : 
	��    ��  : ���� �ڵ� �˻� ȭ�� //
-->
<%@ page contentType="text/html;charset=ksc5601"%>
<jsp:useBean id="codeFinder" class="com.wms.beans.CodeFinder" scope="application"/>
<%@ page import="com.wms.fw.servlet.Box" %>
<%@ page import="com.wms.fw.servlet.HttpUtility" %>
<jsp:useBean id="user" class="com.wms.beans.dto.UserInfo" scope="session"/>
<%@ page  import="com.wms.beans.dto.HistJobDTO"%>
<%request.setCharacterEncoding("ksc5601");%>
<%Box box = HttpUtility.getBox(request);%>
<%String urlPage=(box.get("mode").equals("prod"))?"/common/histJobNameSearchProd.jsp":"/common/histJobNameSearchDetail.jsp";%>
<%
	//error��� ����
	request.setCharacterEncoding("ksc5601");
	//�����ι� 
	String JL = request.getParameter("JL");
	//��������
	String jobName = request.getParameter("jobName");
	if(jobName==null)jobName="";
	//��������
	String strJobCd = request.getParameter("selJobCd");
	if(strJobCd==null)strJobCd="";
	//��������
	String strJobName = request.getParameter("selJobName");
	if(strJobName==null)strJobName="";
	//ȭ��Display ����
	String strErrorView = request.getParameter("viewFlag");
	if(strErrorView==null)strErrorView="yes";
	//������ȣ
	String histBizNo = request.getParameter("histBizNo");
	//��������
	//HistJobDTO[] histJobList=(HistJobDTO[])session.getAttribute("histJobList");
	String _ACT=request.getParameter("_ACT");
	if(_ACT==null)_ACT="";

	HistJobDTO[] histjobdtos=(HistJobDTO[])request.getAttribute("histJobInquiryList");
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7, IE=EmulateIE9"/>
<meta http-equiv="Content-Language" content="ko">
<meta http-equiv="Content-Type" content="text/html; charset=ks_c_5601-1987">
<LINK rel="stylesheet" type="text/css" href="../css/skin04_form.css">
<title>�����ڵ�˻�</title>
<script Language="JavaScript" src="/common/link/jobSearch.js"></script>
<script Language="JavaScript">

function search(){
	frm=document.forms[0];
//	frm._SCREEN.value='';
	frm._ACT.value='JL';
	frm.viewFlag.value="yes";
    <%if(!box.get("mode").equals("prod")){%>
		document.forms[0].BL.selectedIndex=0;
		document.forms[0].bizNo.selectedIndex=0;
	<%}%>
	frm.submit();
}

function searchBiz(jobCd, jobName, bizNo, bizName){
	frm=document.forms[0];

	document.all["errorView"].style.display="none";
	document.all["D1"].style.display="";
	document.all["D"].style.display="";
	frm.viewFlag.value="no";
	frm.selJobCd.value=jobCd;
	frm.selJobName.value=jobName;

	histBizChange2(jobCd, jobName, bizNo, bizName);
}	//bizChange2(jobCd, jobName, bizNo, bizName);

</script>
</head>

<body text="#000000" bgcolor="#FFFFFF" topmargin=0 leftmargin=0 rightmargin=0>
<form method="POST" action='/commonCon'>
<input type='hidden' name='viewFlag'>
<input type='hidden' name='key' value="false">
<input type='hidden' name='_ACT' >
<input type='hidden' name='_SCREEN' value='/common/histJobNameSearch.jsp'>
<input type='hidden' name='useFlag' value='Y'>
<input type='hidden' name='selJobCd'>
<input type='hidden' name='selJobName'>
<!--
<input type='hidden' name='selJobCd' value='<%=strJobCd%>'>
<input type='hidden' name='selJobName' value='<%=strJobName%>'>
-->

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
								<a href="javascript:codeSelect('<%=user.empOrgCd%>')">
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

<Table  cellpadding=0 cellspacing=1 border=0 class=table1 width=100% height=24>
	<td  width="20%" class=TD1 height=24>������</td>
	<td  width="70%" class=TD2 height=24><input type="text" name="jobName" value='<%=jobName%>' size="36" style="ime-mode:active;" onkeydown="javascript:if(event.keyCode==13){search();}"></td>
	<td width="10%" class=TD2 height="24">
	<table   cellpadding=0 cellspacing=0 border=0 class=table1 width=100%>
		<a href="javascript:search()">
		<td class="base" height="24" style ="cursor:hand;" onMouseOver="this.className='on'" onMouseOut="this.className='base'" onMouseDown="this.className='down'" onMouseUp="this.className='on'">&nbsp;�˻�</td>
		</a>
	</table>
	</td>
</Table>
<table border=0><tr height=1><td></td></tr></table>

<jsp:include page="<%=urlPage%>" flush="true"/>

<div id="errorView" style="display<%=(strErrorView.trim().equals("yes"))?"":":none"%>" >

<table border=0><tr height=1><td></td></tr></table>
<Table  cellpadding=0 cellspacing=1 border=0 class=table1 width=100% id="table1">
	<tr>
		<td width="30%" height="24" class=TD1>�����ڵ�</td>
		<td width="70%" height="24" class=TD1>������</td>
	</tr>
</Table>
<Table  cellpadding=0 cellspacing=1 border=0 class=table1 width=100% id="table1">
	<%
	String no=null;
	if(histjobdtos!=null){
		for(int i=0 ; i<histjobdtos.length ; i++){    
			if(histjobdtos[i].useFlag.equals("Y")){
				
	%>
		<tr class='td2' style ="cursor:hand;" onMouseOver="this.className='leftnavi1'" onMouseOut="this.className='td2'"  onclick="javascript:searchBiz('<%=histjobdtos[i].jobCd%>','<%=histjobdtos[i].jobName%>','<%=histjobdtos[i].bizNo%>','<%=histjobdtos[i].bizName%>')">
			<td width="30%" height="24" align="center"><%=histjobdtos[i].jobCd%>
				<input type='hidden' name='histBizNo' value='<%=histjobdtos[i].jobCd%>'>
			</td>
			<td width="70%" height="24" align="left"><%=histjobdtos[i].jobName%></td>
		</tr>	
	<%  		}
		}
	}else{%>
		<Table  cellpadding=3 cellspacing=1 border=0 class=table1 width=100% id="table1">
			<tr class='base' align='center'>
				<td colspan=2 align='center'>�ش� �ڷᰡ �����ϴ�.</td>
			</tr>
		</table>
	<%}%>
</table>
<table border=0><tr height=1><td></td></tr></table>
</div>
</form>
</body>
</html>