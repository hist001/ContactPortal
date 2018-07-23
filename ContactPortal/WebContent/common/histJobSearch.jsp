<!--
	파 일 명  : histJobSearch.jsp
	작성일자  : 2004/06/22
	작 성 자  : 
	내    용  : 직무 코드 검색 화면
-->
<%@ page contentType="text/html;charset=ksc5601"%>
<%@ page import="com.wms.fw.servlet.Box" %>
<%@ page import="com.wms.fw.servlet.HttpUtility" %>
<%@ page  import="com.wms.beans.dto.HistJobDTO"%>
<jsp:useBean id="codeFinder" class="com.wms.beans.CodeFinder" scope="application"/>
<jsp:useBean id="user" class="com.wms.beans.dto.UserInfo" scope="session"/>
<%request.setCharacterEncoding("ksc5601");%>
<%Box box = HttpUtility.getBox(request);%>
<%String urlPage=(box.get("mode").equals("prod"))?"/common/histJobSearchProd.jsp":"/common/histJobSearchDetail.jsp";%>

<%
//error출력 여부
boolean errorKey=false;
request.setCharacterEncoding("ksc5601");
//직무부문 
String JL = request.getParameter("JL");
//직무영역
String jobCd = request.getParameter("jobCd");
//직무부호
String histBizNo = request.getParameter("histBizNo");
//직무정보
HistJobDTO[] histJobList=(HistJobDTO[])session.getAttribute("histJobList");
//HistJobDTO[] histJobList=(HistJobDTO[])request.getAttribute("histJobList");
System.out.println("JL:"+JL);
if(histJobList!=null){
for(int i=0;i<histJobList.length;i++){
	System.out.println(histJobList[i]);
}
}else{
	System.out.println("자료가 없음");
}
String _ACT=request.getParameter("_ACT");
if(_ACT==null)_ACT="";
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7, IE=EmulateIE9"/>
<meta http-equiv="Content-Language" content="ko">
<meta http-equiv="Content-Type" content="text/html; charset=ks_c_5601-1987">
<LINK rel="stylesheet" type="text/css" href="../css/skin04_form.css">
<title>직무코드검색</title>
<script Language="JavaScript" src="/common/link/jobSearch.js"></script>
<script Language="JavaScript">
function searchBiz(){
	frm=document.forms[0]
    	value=frm.histBizNo.options[frm.histBizNo.selectedIndex].value;
	
	if(value==''){

		document.all["errorView"].style.display="";
		document.all["D1"].style.display="none";
		document.all["D"].style.display="none";
		return;
	}else{
		if(opener.toString()=='dailyInRptSch'){
			idx=value.lastIndexOf("-");
			name=value.substring(idx+1);
			tmp = value.substring(0,idx);
			idx = tmp.lastIndexOf("-");
			no  = tmp.substring(idx+1);
			//alert(no+":"+name);
			//opener.confirmItem(param, code, name, orgName);
			opener.confirmItem(null,no,name,null);
			self.close();
			return;
		}
		document.all["errorView"].style.display="none";
		document.all["D1"].style.display="";
		document.all["D"].style.display="";
		histBizChange();
	}
	
}
</script>
</head>

<body text="#000000" bgcolor="#FFFFFF" topmargin=0 leftmargin=0 rightmargin=0>
<form method="POST" action='/commonCon'>
<input type='hidden' name='key' value="false">
<input type='hidden' name='_ACT' >
<input type='hidden' name='_SCREEN' value='/common/histJobSearch.jsp'>
<input type='hidden' name='useFlag' value='Y'>

<table cellpadding=1 cellspacing=1 class=table1 border=0 width=100%>
	<tr>
		<td class=td2>
			<table cellpadding=2 cellspacing=1 border=0 width=100%>
				<tr>
					<td width=20><img src="/images/i_formtitle.gif"></td><td><B>업무보고 >> 신규업무항목검색</B></td>
					<td align=right>
						<table cellpadding=0 cellspacing=0 border=0>
							<tr>
								<td width=5></td>
								<a href="javascript:codeSelect('<%=user.empOrgCd%>')">
								<td class="base"  style ="cursor:hand;" onMouseOver="this.className='on'" onMouseOut="this.className='base'" onMouseDown="this.className='down'" onMouseUp="this.className='on'">확인</td></a>
								<td width=5></td>
								<a href="javascript:winClose()">
								<td class="base"  style ="cursor:hand;" onMouseOver="this.className='on'" onMouseOut="this.className='base'" onMouseDown="this.className='down'" onMouseUp="this.className='on'">닫기</td></a>
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
	<td  width="20%" class=TD1>작업구분</td>
	<td  width="80%" class=TD2>직무</td>
</Table>
<table border=0><tr height=1><td></td></tr></table>
<Table  cellpadding=0 cellspacing=1 border=0 class=table1 width=100%>
	<tr id=rowa style=display>
		<td width="70%">
			<Table  cellpadding=1 cellspacing=1 border=0 class=table1 width=100%>
				<tr>
					<td width="20%" class=TD1>직무 부문</td>
					<td width="80%" class=TD2>
						<select size="1" name="JL" onchange="javascript:jobChange('RG')">
						<option value="null" >선택하세요..</option>
							<%=codeFinder.getHtml("JL",JL)%>
						</select></td>
				</tr>
				<tr>
					<td width="20%" class=TD1>직무 영역</td>
					<td width="80%" class=TD2>
						<select size="1" name="jobCd" onchange="javascript:jobChange('RC')">
						<option value="null" >선택하세요..</option>
						<%=codeFinder.getHtml(JL,jobCd)%>		
						</select></td>
				</tr>
				<tr>
					<td width="20%" class=TD1>직무 코드</td>
					<td width="80%" class=TD2>
						<select size="1" name="histBizNo" onchange="javascript:searchBiz()">
						<option value="null" >선택하세요..</option>	
						<%

                        			String no=null;
						if(histJobList!=null&&JL!=null){
                         
						for(int i=0;i<histJobList.length;i++){
							
  	                      		if(histJobList[i].useFlag.equals("Y")){
									no=(histJobList[i].bizNo==null)?"000":histJobList[i].bizNo;
									no+="-"+histJobList[i].bizName;	
									no+="-"+histJobList[i].jobCd.trim();
									no+="-"+histJobList[i].jobName.trim();
									if(no.equals(histBizNo))
										request.setAttribute("idx",i+"");
								%>
									<option value='<%=no%>'
									<%=(no.equals(histBizNo))?" selected ":""%>
									><%=histJobList[i].jobCd+" "+histJobList[i].jobName%>
									</option>
						<%}else{%>
									<option value=''
									><%=histJobList[i].jobCd+" "+histJobList[i].jobName%>
									</option>
						<%}
						 }//for 
						}else if(_ACT.equals("RC")){
                           				errorKey=true;
						}


					%>
						</select></td>
				</tr>
			</table>
		</td>
	</tr>
</Table>
<table border=0><tr height=1><td></td></tr></table>
<div id="errorView" style="display<%=(errorKey)?"":":none"%>" >
<Table  cellpadding=0 cellspacing=0 border=0 class=table1 width=100% id="table1">
	<tr class="trout">
	    <td width='30%'></td>
		<td colspan=2 align='center' height="350" width="40%">
			<table cellpadding=0 cellspacing=1 border=0 align=center width="100%" height=180 background="../images/alert_bg.gif">
				<tr class='onbase' style ="cursor:hand;" onMouseOver="this.className='base'" onMouseOut="this.className='onbase'" onclick="javascript:codeSelect()">
					<td width="100%" align=center class=td2>본 직무코드는 제품/공정코드를 선택하시기 바랍니다.
 					</td>
				</tr>
			</table>
		</td>
	    <td width='30%'></td>
	</tr>
</table>

</div>
<table border=0><tr height=1><td></td></tr></table>
<jsp:include page="<%=urlPage%>" flush="true"/>
</form>
</body>
</html>