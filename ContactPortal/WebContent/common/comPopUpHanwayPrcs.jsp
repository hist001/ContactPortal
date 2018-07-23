<!--
	파 일 명  : comPopUpHanwayPrcs2.jsp
	작성일자  : 2009.06.05
	작 성 자  : mailbest
	내    용  : 직무/조직+PJT/공정 팝업
-->
<%@ page contentType="text/html;charset=ksc5601"%>
<html>
<script Language="JavaScript" src="/common/link/common.js" type="text/javascript"></script>
<script Language="JavaScript" src="/common/link/comm_check.js" type="text/javascript"></script>
<script Language="JavaScript" src="/common/link/payDoc.js"></script>

<script lagnguae="javascript">
 function init(){
}
 function setOpenerParam(){
 	var frm = document.forms[0];
//	alert(document.form1.pjtno1.value);
//	document.domain = "hist.co.kr";
	
//	alert(document.domain);
//	parent.document.forms[0].all("txtPJTCode").value=document.form1.pjtno1.value;	
}
 function chgDomain(){
// 	alert("domain 변경");
// 	document.domain = "mas.hist.co.kr";
//	alert(document.domain);
}

 function chgDomainEnd(){
 	//alert("domain 변경");
	//alert(document.domain);
	document.domain = "hist.co.kr";
	//alert(document.domain);
}
</script>
<LINK rel="stylesheet" type="text/css"href="../css/skin04_viewstyle.css">
<body text="#000000" bgcolor="#FFFFFF" topmargin="0" leftmargin="0"	rightmargin="0" onload='init()'>
<form name="form1" method="post" action="" enctype='multipart/form-data'>
<table align="center" border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr height=''>
		<td>
			<input type="hidden" size="10" name="pjtno1">
			<input type="text" size="8" name="pjtcd1"> 
			<input type="text" size="30" name="pjtName1">
			<input type="text" size="8" name="pjtcd2"> 
			<input type="text" size="32" name="pjtName2">
			<input type="hidden" size="20" name="pjtcd1B">
			<input type="hidden" size="20" name="pjtcd2B">
			<input type="hidden" size="20" name="flag">
		</td>
		<td width="50" class="td2" align=right>
		<Table cellpadding="0" cellspacing="0" border="0" class="table2" width="50">
			<tr >
				<td width="5" class="td2"></td>
				<td class="bt_f" nowrap></td>
				<td class='button' width='50' style="cursor:hand;"
					onMouseOver="this.className='buttonOn'"
					onMouseOut="this.className='button'"
					onclick="chgDomain();	
							popupSearch4('sale',
										'pjtno1',
										'pjtcd1',
										'pjtName1',
										'pjtcd1B',
										'pjtcd2',
										'pjtcd2',
										'pjtName2',
										'pjtcd2B',
										'flag');
							chgDomainEnd();">검색</td>
				<td class="bt_e" nowrap></td>						
			</tr>
		</Table>
		</td>		
	
	</tr>	
</table>
</form>
</body>
</html>
