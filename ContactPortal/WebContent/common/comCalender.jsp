<!--
	파 일 명  : comCalender.jsp
	작성일자  : 2008/3/28
	작 성 자  : mailbest
	내    용  : 공통 달력
-->
<%@ page contentType="text/html;charset=euc-kr"
	errorPage="/common/error.jsp"%>
<%@ page import="com.wms.fw.servlet.Box"%>
<%@ page import="com.wms.fw.servlet.HttpUtility"%>
<%Box box = HttpUtility.getBox(request);%>
<%
//calType : calStd-표준달력, calRange-기간달력, calMonth-월달력

	String	calType		=box.get("calType");
	String	calName		=box.get("calName");
	String	calValue	=box.get("calValue");
	String	calOnClick	=box.get("calOnClick");
	String	calFromName	=box.get("calFromName");
	String	calToName	=box.get("calToName");
	String	calFrom		=box.get("calFrom");
	String	calTo		=box.get("calTo");
%>
<script language='javascript' type="text/javascript">
 var frm;
</script>

<% if (calType.equals("calStd")){%>
<table border="0" cellpadding="0" cellspacing="0">
	<tr align='center'>
		<td><input type='text' value='<%=calValue%>' id='<%=calName%>'
			name=<%=calName%> size='10'
			onkeyup="if(event.keyCode!=8) chkFormatDate(this,8)"
			onkeydown="if(event.keyCode==13) <%=calOnClick%>;"> <img
			src="../../images/navi_08_icon.gif"
			onClick='getcal(frm.<%=calName%>);'
			style="cursor:hand;cursor:pointer;"></td>
	</tr>
</table>
<%}else if (calType.equals("calRange")){ %>
<table border="0" cellpadding="0" cellspacing="0">
	<tr align='center'>
		<td><input type=text name='<%=calFromName%>' size='10'
			value='<%=calFrom%>'
			onkeyup="if(event.keyCode!=8) chkFormatDate(this,8)"
			onkeydown="if(event.keyCode==13) <%=calOnClick%>;"> <img
			src="../../images/navi_08_icon.gif" border="0"
			style="background-color: #F0F0FF;vertical-align:middle;cursor:hand;cursor:pointer;"
			onclick='getcal(frm.<%=calFromName%>);'> <!--기간 달력 처리 버튼 -->
		<img src="../../images/prv_month.gif"
			onClick='setMon("<%=calFromName%>","<%=calToName%>",-1);<%=calOnClick%>' style="cursor:hand;cursor:pointer;"> <img
			src="../../images/cur_month.gif"
			onClick='setMon("<%=calFromName%>","<%=calToName%>",0);<%=calOnClick%>' style="cursor:hand;cursor:pointer;"> <img
			src="../../images/next_month.gif"
			onClick='setMon("<%=calFromName%>","<%=calToName%>",1);<%=calOnClick%>' style="cursor:hand;cursor:pointer;"> <!--기간 달력 처리 버튼 End-->
		<input type=text name='<%=calToName%>' size='10' value='<%=calTo%>'
			onkeyup="if(event.keyCode!=8) chkFormatDate(this,8)"
			onkeydown="if(event.keyCode==13) <%=calOnClick%>;"> <img 	src="../../images/navi_08_icon.gif" border="0"
			style="background-color: #F0F0FF;vertical-align:middle;cursor:hand;cursor:pointer;"
			onclick='getcal(frm.<%=calToName%>);'></td>
	</tr>
</table>
<%}else if (calType.equals("calRangeMonth")){ %>
<table border="0" cellpadding="0" cellspacing="0">
	<tr align='center'>
		<td><input type=text name='<%=calFromName%>' size='6' maxlength='7'
			value='<%=calFrom%>'
			onkeyup="if(event.keyCode!=8) chkFormatDate(this,6)"
			onkeydown="if(event.keyCode==13) <%=calOnClick%>;">
		</td>
		<td>
		<!--기간 달력 처리 버튼 -->
		<img src="../../images/prv_month.gif"
			onClick='setMon("<%=calFromName%>","<%=calToName%>",-1);<%=calOnClick%>' style="cursor:hand;cursor:pointer;"> <img
			src="../../images/cur_month.gif"
			onClick='setMon("<%=calFromName%>","<%=calToName%>",0);<%=calOnClick%>' style="cursor:hand;cursor:pointer;"> <img
			src="../../images/next_month.gif"
			onClick='setMon("<%=calFromName%>","<%=calToName%>",1);<%=calOnClick%>' style="cursor:hand;cursor:pointer;"> 
		<!--기간 달력 처리 버튼 End-->
		</td>
		<td>
		<input type=text name='<%=calToName%>' size='6' maxlength='7'
			value='<%=calTo%>'
			onkeyup="if(event.keyCode!=8) chkFormatDate(this,6)"
			onkeydown="if(event.keyCode==13) <%=calOnClick%>;"></td></tr>			
</table>
<%}else if (calType.equals("calMonth")){ %>
<table border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td><input type=text name=<%=calName%> size='6' maxlength='7' 	value='<%=calValue%>'	onkeyup="if(event.keyCode!=8) chkFormatDate(this,6)"
			onkeydown="if(event.keyCode==13) <%=calOnClick%>;">&nbsp;</td>
		<td><!--월간 달력 처리 버튼 -->
		<table border="0" cellpadding="0" cellspacing="0">
			<tr height='10px'>
				<td><img src="../../images/hprv_month.gif"
					onClick='setMon("<%=calName%>","<%=calName%>",1);chkFormatDate("<%=calName%>",6);<%=calOnClick%>;'
					style="cursor:hand;cursor:pointer;"></td>
			</tr>
			<tr height='10px'>
				<td><img src="../../images/hnext_month.gif"
					onClick='setMon("<%=calName%>","<%=calName%>",-1);chkFormatDate("<%=calName%>",6);<%=calOnClick%>;'
					style="cursor:hand;cursor:pointer;"></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<%}else if (calType.equals("calYear")){ %>
<table border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td><input type=text name='<%=calName%>' id='<%=calName%>' size='4' maxlength='4'
			value='<%=calValue%>'
			onkeydown="if(event.keyCode==13) <%=calOnClick%>;">&nbsp;</td>
		<td><!--년간 달력 처리 버튼 -->
		<table border="0" cellpadding="0" cellspacing="0">
			<tr height='10px'>
				<td><img src="../../images/hprv_month.gif"
					onClick='addYear("<%=calName%>",1);<%=calOnClick%>;'
					style="cursor:hand;cursor:pointer;"></td>
			</tr>
			<tr height='10px'>
				<td><img src="../../images/hnext_month.gif"
					onClick='addYear("<%=calName%>",-1);<%=calOnClick%>;'
					style="cursor:hand;cursor:pointer;"></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<%}%>
<script language='javascript' type="text/javascript">
	if ('<%=calType%>'=='calRange') {
		frm = selform('<%=calFromName%>');
	}else{
		frm = selform('<%=calName%>');		
	}
</script>
