<%@ page contentType="text/html;charset=ksc5601"%>
<jsp:useBean id="codeFinder" class="com.wms.beans.CodeFinder" scope="application"/>
<jsp:useBean id="user" class="com.wms.beans.dto.UserInfo" scope="session"/>
<%@ page  import="com.wms.beans.dto.HistJobDTO"%>
<%

	//직무영역
	String strJobCd = request.getParameter("selJobCd");
	if(strJobCd==null)strJobCd="";
	//직무영역
	String strJobName = request.getParameter("selJobName");
	if(strJobName==null)strJobName="";
	//사업부문
	String BL = request.getParameter("BL");
	//사업코드
	String bizNo = request.getParameter("bizNo");
	//사업처리유무
	String key=request.getParameter("key");
	//직무위치값
	String index=(String)request.getAttribute("idx");
	int idx=(index==null)?0:Integer.parseInt(index);
	String strJobC=request.getParameter("jobC");
	if(strJobC==null)strJobC="";
	String strJobN=request.getParameter("jobN");
	if(strJobN==null)strJobN="";
	//직무정보
	HistJobDTO[] histJobList=(HistJobDTO[])session.getAttribute("histJobList");
%>

<script Language="JavaScript">

function bizChange2(s){//사업 관련 선택시
	if(s=='R'&&document.forms[0].bizNo.options[document.forms[0].bizNo.selectedIndex].value=='null')
		return;
	if(s=='RC'&&document.forms[0].BM.options[document.forms[0].BM.selectedIndex].value=='null')
		return;
	if(s=='RG'&&document.forms[0].BL.options[document.forms[0].BL.selectedIndex].value=='null'){
		return;
	}else if(s=='RG'&&document.forms[0].BL.options[document.forms[0].BL.selectedIndex].value!='null'){					 
		 document.forms[0].bizNo.options[document.forms[0].bizNo.selectedIndex].value='null'
	}
	document.forms[0]._ACT.value=s;
	if(s!='R')
	document.forms[0].action='/common/histJobNameSearch.jsp'
	document.forms[0].key.value="true";
	document.forms[0].viewFlag.value="no";
	document.forms[0].selJobCd.value=document.forms[0].selJobCd2.value;
	document.forms[0].selJobName.value=document.forms[0].selJobName2.value;
	document.forms[0].submit();
}

function bizCodeChange2(){//사업코드 선택시
	document.forms[0].selJobCd2.value='<%=strJobCd%>';
	document.forms[0].selJobName2.value='<%=strJobName%>';
	document.forms[0].jobC.value='<%=strJobCd%>';
	document.forms[0].jobN.value='<%=strJobName%>';
	s=document.forms[0].bizNo.options[document.forms[0].bizNo.selectedIndex].text;
	document.forms[0].bizC.value=document.forms[0].bizNo.options[document.forms[0].bizNo.selectedIndex].value;
	document.forms[0].bizN.value=s.substring(s.indexOf(" ")+1);
	document.all["D"].style.display="";
	document.all["errorView"].style.display="none";
	document.forms[0].viewFlag.value="no";
}
function changePrcsMode(prodType){
	var frm = document.forms[0];
	frm.action='/common/histJobNameSearch.jsp?mode=prod&prodType='+prodType
	frm.key.value="true";
	frm.viewFlag.value="no";
	frm.selJobCd.value=frm.selJobCd2.value;
	frm.selJobName.value=frm.selJobName2.value;
	frm.submit();
}
</script>

<div id=D1 style="display:<%=(key!=null&&key.equals("true"))?" ":"none"%>" >

<Table  cellpadding=0 cellspacing=1 border=0 class=table1 width=100% id="table2">
	<tr id=rowa0 style=display>
		<td width="70%">
			<Table  cellpadding=1 cellspacing=1 border=0 class=table1 width=100% id="table3">
				<tr>
					<td width="20%" class=TD1>직무 코드</td>
					<td width="80%" class=TD2>
						<input readonly type='text' size=10 name='selJobCd2' value='<%=strJobCd%>'>
						<input readonly type='text' size=50 name='selJobName2' value='<%=strJobName%>'>
					</td>
				</tr>

				</table>
		</td>
	</tr>
</Table>
<table border=0 id="table4"><tr height=1><td></td></tr></table>
<Table  cellpadding=0 cellspacing=1 border=0 class=table1 width=100%>
<td width=100% >
	<Table  cellpadding=3 cellspacing=1 border=0 class=table1 width=100% >
	<td  width="20%" class=TD1 height=24>구&nbsp;분</td>
	<td  width="80%" class=TD2>
		<input type=radio name="rdoTmp" value="PROD" onclick="javascript:changePrcsMode('P')" class=Attach >제품&nbsp; 
		<input type=radio id="rdoTmp" name="rdoTmp" value="TASK" onclick="javascript:changePrcsMode('T')" class=Attach >Task&nbsp; 
		<input type=radio name="rdoTmp" value="JOB" class=Attach checked>사업
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
					<td width="20%" class=TD1>사업 부문</td>
					<td width="80%" class=TD2>
						<select size="1" name="BL" onchange="javascript:bizChange2('RG')">
						<option value="null" >선택하세요..</option>
							<%=codeFinder.getHtml("BL",BL)%>
						</select></td>
				</tr>

				<tr>
					<td width="20%" class=TD1>사업 코드</td>
					<td width="80%" class=TD2>
						<select size="1" name="bizNo" onchange="javascript:bizCodeChange2()">
						<option value="null" >선택하세요..</option>	
						<%
						if(BL!=null){
						com.wms.beans.dto.Code[] codes=codeFinder.get(BL);
						if(codes!=null)
						for(int i=0;i<codes.length;i++){
							com.wms.beans.dto.Code[] subCodes=codeFinder.get(codes[i].codeNo);
							if(subCodes!=null)
							for(int j=0;j<subCodes.length;j++){%>
                            <option value='<%=subCodes[j].codeNo%>' ><%=subCodes[j].codeNo+" "+subCodes[j].codeName%></option>
							<%
							}
						%>
						    
						<%}
				          }%>	</select></td>
				</tr>
			</table>
		</td>
	</tr>
</Table>
<table border=0><tr height=1><td></td></tr></table>

</div>

<div id=D style="display:none" >
<Table  cellpadding=3 cellspacing=1 border=0 class=table1 width=100% id="table1">
	<tr>
	
		<td width="25%" class=TD1>직무부호</td>
		<td width="25%" class=TD1>직무명</td>
		<td width="25%" class=TD1>사업부호</td>	
	    <td width="25%" class=TD1>사업명</td>
	</tr>
	<tr height=2><td class=TD2 colspan=4></td></tr>

	<tr  class='TD2' style="cursor:hand;" onMouseOver="this.className='TD2'" onMouseOut="this.className='TD2'" onclick="javascript:codeSelect('<%=user.empOrgCd%>')">
	
		<td width="25%"><input readonly type='text' name='jobC'></td>
		<td width="25%"><input readonly type='text' name='jobN' style="ime-mode:active;"></td>
		<td width="25%"><input readonly type='text' name='bizC'></td>
		<td width="25%"><input readonly type='text' name='bizN' style="ime-mode:active;"></td>

	</tr>

</table>
</div>
