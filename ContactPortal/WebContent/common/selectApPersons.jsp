<%@ page contentType="text/html;charset=ksc5601" errorPage="/common/error.jsp"%>
<%@ page import="com.wms.beans.dto.EmpDTO"%>
<jsp:useBean id="user" class="com.wms.beans.dto.UserInfo" scope="session"/>
<%EmpDTO[] empList=(EmpDTO[])request.getAttribute("empList");%>
<%String apEmpId=request.getParameter("apEmpId");%>
<%boolean apKey=(apEmpId!=null&&!apEmpId.trim().equals(""))?true:false;%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7, IE=EmulateIE9"/>
<meta http-equiv="Content-Language" content="ko">
<meta http-equiv="Content-Type" content="text/html; charset=ks_c_5601-1987">
<LINK rel="stylesheet" type="text/css" href="../css/skin04_viewstyle.css">

<title>대결자선택 화면</title>
<script language='javascript'>
function apEmpSearch(){
	frm=document.forms[0];
	preFrm=opener.document.forms[0];
	for(var i=0;i<<%=(empList!=null)?empList.length:0%>;i++){
		if(frm.emp[i].checked==true){
			preFrm.apEmpId.value=frm.empId[i].value;
			preFrm.apEmpKName.value=frm.empKName[i].value;
			break;
	    }
	}

	opener.document.forms[0].target="";
	self.close();
}
</script>
</head>

<body text="#000000" bgcolor="#FFFFFF" topmargin=0 leftmargin=0 rightmargin=0>
<form method="POST" action="--WEBBOT-SELF--">
<table width=100% cellspacing=0 cellpadding=0 border=0 class=loctitle>
	<tr valign=middle>
		<td width=20></td>
		<td width=30><img src="../images/sign_position.gif"></td>
		<td align=left >대결자 선택</td>
	</tr>
</table>
<table width=97% cellspacing=2 cellpadding=0 border=0 align=center>
	<tr height=10 >
		
		<td align=right >
			<table cellpadding=0 cellspacing=0 border=0>
				<tr>
					<td></td>
					<td width=5></td>
					<a href="javascript:apEmpSearch()">
					<td class="base"  style ="cursor:hand;" onMouseOver="this.className='on'" onMouseOut="this.className='base'" onMouseDown="this.className='down'" onMouseUp="this.className='on'">저장</td>
					</a>
					<td width=5></td>
					<td class="base"  style ="cursor:hand;" onMouseOver="this.className='on'" onMouseOut="this.className='base'" onMouseDown="this.className='down'" onMouseUp="this.className='on'" onclick="Onclick=window.close()">닫기</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<table width=97% cellspacing=0 cellpadding=0 border=0  align=center class=trskin style="table-layout : fixed;">
	<tr height=22 valign=middle align=center>
		<td width="45%">소속</td>
		<td width="45%">성명</td>
		<td width="10%">선택</td>
	</tr>
</table>
<div id="viewlist" align=center style="position:relative;left:12;top:0;width:97%; overflow-x:none; overflow-y:auto;">
   <table width=100% cellspacing=0 cellpadding=0 border=0  align=center>
<%

if(empList!=null){
for(int i=0;i<empList.length;i++){%>
	<input type='hidden' name='empId' value='<%=empList[i].empId%>'>
	<input type='hidden' name='empKName' value='<%=empList[i].empKName%>'>
	<tr>
		<td width="45%" height="24" class='viewContent'><%=empList[i].empOrgName%></td>
		<td width="45%" height="24" class='viewContent'><%=empList[i].empKName%>(<%=empList[i].empId%>)</td>
		<td width="10%" height="24" class='viewContentC'><input type="radio" name="emp" 
		<%if(apKey&&apEmpId.equals(empList[i].empId)){%>
                    checked='true'
		<%}
		%>
		></td>
	</tr>
<%  
}
}else{
%>
<tr><td colspan=3 height="24" align="center">해당 직원이 없습니다.</td></tr>
<%
}%>
	</table>
</div>
<div id='h1' style="display:none">
<input type='hidden' name='empId'    >
<input type='hidden' name='empKName' >
<input type="radio"  name="emp"      >
</div>
</form>
</body>
</html>