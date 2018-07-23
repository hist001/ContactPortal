<!--
	파 일 명  : comFASearch.jsp
	작성일자  : 2008/12/18
	작 성 자  : mailbest
	내    용  : 고정자산 검색조건
-->
<%@ page contentType="text/html;charset=euc-kr"
	errorPage="/common/error.jsp"%>
<%@ page import="com.wms.fw.servlet.Box"%>
<%@ page import="com.wms.fw.servlet.HttpUtility"%>
<%Box box = HttpUtility.getBox(request);%>
<%
	String	faName		=box.get("faName");
	String	faValue		=box.get("faValue");
	String	faOnClick	=box.get("faOnClick");

	String	faFromName	=box.get("faFromName");
	String	faToName	=box.get("faToName");
	String	faFrom		=box.get("faFrom");
	String	faTo		=box.get("faTo");
%>
<script language='javascript' type="text/javascript">
 var frm;
</script>

<table border="0" cellpadding="0" cellspacing="0" width='230'>
	<tr align='left'>
		<td><input type=text name='<%=faFromName%>' size='10'
			value='<%=faFrom%>'
			onkeyup="checkEngField(this);"
			onkeydown="if(event.keyCode==9) document.forms[0].all('<%=faToName%>').value=this.value;
					   if(event.keyCode==13) <%=faOnClick%>;">
		<img src="/images/search.gif" border="0"
			style="vertical-align:middle;cursor:hand;"
			onclick="popupSearchN('fam_new','fam_0','','<%=faFromName%>','')">~&nbsp;
		<input type=text name='<%=faToName%>' size='10' value='<%=faTo%>'
			onkeyup="checkEngField(this);"
			onkeydown="if(event.keyCode==13) <%=faOnClick%>;">
		<img src="/images/search.gif" border="0"
			style="vertical-align:middle;cursor:hand;"
			onclick="popupSearchN('fam_new','fam_0','','<%=faToName%>','')">
		</td>
	</tr>
</table>
<script language='javascript' type="text/javascript">
//		frm = selform('<%=faName%>');		
</script>
