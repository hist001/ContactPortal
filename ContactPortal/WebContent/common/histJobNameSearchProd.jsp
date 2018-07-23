<%@ page contentType="text/html;charset=ksc5601"%>
<%@ page  import="com.wms.beans.dto.HistJobDTO"%>
<%@ page import="com.wms.beans.dto.ProdDTO"    %>

<jsp:useBean id="codeFinder" class="com.wms.beans.CodeFinder" scope="application"/>
<jsp:useBean id="user" class="com.wms.beans.dto.UserInfo" scope="session"/>
<%

	//流公康开
	String strJobCd = request.getParameter("selJobCd");
	if(strJobCd==null)strJobCd="";
	//流公康开
	String strJobName = request.getParameter("selJobName");
	if(strJobName==null)strJobName="";
	String key=request.getParameter("key");
	/*
	//荤诀何巩
	String BL = request.getParameter("BL");
	//荤诀内靛
	String bizNo = request.getParameter("bizNo");
	//荤诀贸府蜡公
	
	//流公困摹蔼
	String index=(String)request.getAttribute("idx");
	int idx=(index==null)?0:Integer.parseInt(index);
	String strJobC=request.getParameter("jobC");
	if(strJobC==null)strJobC="";
	String strJobN=request.getParameter("jobN");
	if(strJobN==null)strJobN="";
    */
%>
<%	ProdDTO[] proddtos =(ProdDTO[])request.getAttribute("searchProd");
	/*
	String prodNo = request.getParameter("prodNo");
	String prodName = request.getParameter("prodName");
	String prodType = request.getParameter("prodType");;
	prodNo = (prodNo==null)?"":prodNo;
	prodName = (prodName==null)?"":prodName;
	prodType = (prodType==null)?"":prodType;
	*/
    //ADD
	String prodNo = request.getParameter("prodNo");
	String prodName = request.getParameter("prodName");
	String prcsNo = request.getParameter("prcsNo");
	String strProdType = request.getParameter("prodType");;
	String jobCd = request.getParameter("jobCd");
	String bizNo  = request.getParameter("bizNo");
	String url    = request.getParameter("url");
	prodNo = (prodNo==null)?"":prodNo;
	prodName = (prodName==null)?"":prodName;
	strProdType = (strProdType==null)?"":strProdType;
	System.out.println("strProdType:"+strProdType);
	//ADD END 
%>
<script Language="JavaScript">
function changePrcsMode(){
	var frm=document.forms[0];
	frm.prodType.value='P';
	document.all["divProdType"].innerHTML=frm.prodType.value+"<input name='prodNo' size='10' onkeydown='javascript:if(event.keyCode==13){searchProd();}'>";
	document.all["divKind1"].innerHTML="力前内靛";
	document.all["divKind2"].innerHTML="力前疙";
	document.all["divKind1_1"].innerHTML="力前内靛";
	document.all["divKind2_1"].innerHTML="力前疙";
}

function changeTaskMode(){
	var frm=document.forms[0];
	frm.prodType.value='T';	
	document.all["divProdType"].innerHTML=frm.prodType.value+"<input name='prodNo' size='10' onkeydown='javascript:if(event.keyCode==13){searchProd();}'>";
	document.all["divKind1"].innerHTML="Task内靛";
	document.all["divKind2"].innerHTML="Task疙";
	document.all["divKind1_1"].innerHTML="Task内靛";
	document.all["divKind2_1"].innerHTML="Task疙";
}

function changeBizMode(){
	var frm = document.forms[0];
	frm.action='/common/histJobNameSearch.jsp?mode=biz'
	frm.key.value="true";
	frm.viewFlag.value="no";
	frm.selJobCd.value=frm.selJobCd2.value;
	frm.selJobName.value=frm.selJobName2.value;
	frm.submit();
}
function searchProd(){
	var frm = document.forms[0];
	frm._ACT.value='LRP';
	frm._SCREEN.value="/common/histJobNameSearch.jsp?mode=prod";
	frm.action='/prodCon';	
	frm.key.value="true";
	frm.viewFlag.value="no";
	frm.selJobCd.value=frm.selJobCd2.value;
	frm.selJobName.value=frm.selJobName2.value;
	frm.submit();
}
</script>

<div id=D1 style="display:<%=(key!=null&&key.equals("true"))?" ":"none"%>" >

<input type='hidden' name=prodType value=<%=strProdType%>>
<Table  cellpadding=0 cellspacing=1 border=0 class=table1 width=100% id="table2">
	<tr id=rowa0 style=display>
		<td width="70%">
			<Table  cellpadding=1 cellspacing=1 border=0 class=table1 width=100% id="table3">
				<tr>
					<td width="20%" class=TD1>流公 内靛</td>
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
<Table  cellpadding=0 cellspacing=1 border=0 class=table1 width=100% >
<td width=100% >
	<Table  cellpadding=3 cellspacing=1 border=0 class=table1 width=100% >
		<td  width="20%" class=TD1 height=24>备&nbsp;盒</td>
		<td  width="80%" class=TD2>
			<input type=radio id="rdoTmp" name="rdoTmp" value="PROD" onclick="javascript:changePrcsMode()" class=Attach <%=(strProdType.equals("P"))?"checked":" "%>>力前&nbsp; 
			<input type=radio id="rdoTmp" name="rdoTmp" value="TASK" onclick="javascript:changeTaskMode()" class=Attach <%=(strProdType.equals("T"))?"checked":" "%>>Task&nbsp; 
			<input type=radio id="rdoTmp" name="rdoTmp" value="JOB" class=Attach onclick="javascript:changeBizMode()">荤诀
	</table>
	</td>
</td>
</Table>

<table border=0><tr height=1><td></td></tr></table>
<Table  cellpadding=0 cellspacing=1 border=0 class=table1 width=100%>
<td width=100% >
	<Table  cellpadding=3 cellspacing=1 border=0 class=table1 width=100% >
	<tr>
					<td width="20%" class=TD1 height=24>
					<diV id="divKind1"><%=(strProdType.trim().equals("P"))?"力前内靛":"Task内靛"%></div>
					</td>
	
					<td class=TD2 height=20%>
					<div id="divProdType">						<%=(strProdType.trim().equals("P"))?"P":"T"%><input name='prodNo' size='10' value='<%=prodNo%>' onkeydown="javascript:if(event.keyCode==13){searchProd();}"></div></td>
					<td width="20%" class=TD1><div id="divKind2"><%=(strProdType.trim().equals("P"))?"力前疙":"Task疙"%></div></td>
					<td width="30%" class=TD2>
						<input name="prodName" size="20"  style="width=100%" value='' style="ime-mode:active"  value='<%=prodName%>' onkeydown="javascript:if(event.keyCode==13){searchProd();}">
					</td>

					<td width="10%" class=TD2 align='center'>
					<input type='button' value='八祸' style='vertical-align : middle;  width : 100%;' onclick="javascript:searchProd()">
					</td>

	</tr>
<!--</Table>

<table border=0><tr height=1><td></td></tr></table>
<Table  cellpadding=1 cellspacing=1 border=0 class=table1 width=100% id="table1">
-->
<tr height=3><td colspan=5 class=TD2 ></td></tr>
	<tr>
		<td width="20%" class=TD1 height=24 ><div id="divKind1_1"><%=(strProdType.trim().equals("P"))?"力前内靛":"Task内靛"%></div></td>
		<td width="80%" class=TD1 colspan=4><div id="divKind2_1" ><%=(strProdType.trim().equals("P"))?"力前疙":"Task疙"%></td>
	</tr>
<!--</Table>
<Table  cellpadding=3 cellspacing=1 border=0 class=table1 width=100% id="table1">-->
	<%
	if(proddtos!=null){
		String bizName="";
		for(int i=0;i<proddtos.length;i++){

	%>
		<tr class='td2' style ="cursor:hand;" onMouseOver="this.className='leftnavi1'" onMouseOut="this.className='td2'"
	onclick="javascript:histBizChange2( 
	'<%=strJobCd%>', 
	'<%=strJobName%>',
    '<%=proddtos[i].prodType+proddtos[i].prodNo%>', 
	'<%=proddtos[i].prodName%>'
	)">
			<td width="20%" align="center" height=24><%=proddtos[i].prodType%><%=proddtos[i].prodNo%></td>
			<td width="80%" height=24 colspan=4><%=proddtos[i].prodName%> </td>
		</tr>
	<%}%>	
	<%}else{%>
		
			<tr class='base' align='center'>
				<td  align='center' colspan=5>秦寸 磊丰啊 绝嚼聪促.</td>
			</tr>

	<%}%>
</table>
</table>
<!--<table border=0><tr height=1><td></td></tr></table>-->
</div>

<div id=D style="display:none" >
<Table  cellpadding=3 cellspacing=1 border=0 class=table1 width=100% id="table1">
	<tr>
		<td width="25%" class=TD1>流公锅龋</td>	
	    <td width="25%" class=TD1>流公疙</td>	
		<td width="25%" class=TD1>力前锅龋</td>
		<td width="25%" class=TD1>力前疙</td>
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
