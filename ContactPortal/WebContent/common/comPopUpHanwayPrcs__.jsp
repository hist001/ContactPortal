<!--
	파 일 명  : comPopUpHanwayPrcs.jsp
	작성일자  : 2009.06.05
	작 성 자  : mailbest
	내    용  : 직무/조직, 공정/PJT 팝업
-->
<%@ page contentType="text/html;charset=ksc5601"%>
<html>
<script Language="JavaScript" src="/common/link/common.js" type="text/javascript"></script>
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
//	alert(document.domain);
	document.domain = "mas.hist.co.kr";
//	window.domain = "mas.hist.co.kr";
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
			<input type="text" size="10" name="pjtcd1"> 
			<input type="text" size="30" name="pjtName1">
			<input type="text" size="10" name="pjtcd2"> 
			<input type="text" size="20" name="pjtName2">
		</td>
		<td>
			<input type="button"  value="검색11" onclick="chgDomain();popupSearchPrcs('pjt_new','pjt_4','pjtno1','pjtcd1','pjtName1','pjtcd2','pjtName2',600);chgDomainEnd();">
		</td>
	</tr>	
</table>
</form>
</body>
</html>
