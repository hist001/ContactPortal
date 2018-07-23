<!--
	파 일 명  : comMailForm.jsp
	작성일자  : 2015-10-02
	작 성 자  : mailbest
	내    용  : 공통 메일발송 폼
-->
<%@ page pageEncoding="EUC-KR" contentType="text/html; charset=EUC-KR"%> 
<table  width=100% cellspacing=1 cellpadding=1 border=0 align='center'class=table1 >
	<tr>
		<td  class=td1 width='100'>메일코드</td>
		<td  class=td2 ><input type='text' name='mailCode' value='' style='width:100%'>
	</tr>
	<tr>
		<td  class=td1 width='100'>발송자</td>
		<td  class=td2 ><input type='text' name='fromName' value='' style='width:100%'>
	</tr>
	<tr>
		<td  class=td1 width='100'>발송자주소</td>
		<td  class=td2 ><input type='text' name='fromAddr' value='' style='width:100%'></td>
	</tr>
	<tr>
		<td class=td1>수신자 </td>
		<td class=td2 colspan=2><input type='text' name='toName' value="" style='width:100%'></td>
	</tr>
	<tr>
		<td class=td1>수신자주소</td>
		<td class=td2 colspan=2><input type='text' name='toAddr' value="" style='width:100%'></td>
	</tr>
	<tr>
		<td class=td1>참조자 </td>
		<td class=td2 colspan=2><input type='text' name='toCCName' value="" style='width:100%'></td>
	</tr>
	<tr>
		<td class=td1>참조자주소</td>
		<td class=td2 colspan=2><input type='text' name='toCCAddr' value="" style='width:100%'></td>
	</tr>
	<tr>
		<td class=td1>제목 </td>
		<td class=td2 colspan=2><input type='text' name='mailTitle' value='' style='width:100%'></td>
	<tr>
		<td class=td1>본문</td>
		<td class=td2 colspan=2><input type='text' name='mailBody' value='' style='width:100%'></td>
	</tr>
</table>