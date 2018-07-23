<!--
	파 일 명  : calendar.jsp
	작성일자  : 2004/06/22
	작 성 자  : 
	내    용  : 달력 화면
--><%@ page contentType="text/html;charset=ksc5601" errorPage="/common/error.jsp"%>
<%@ page import="com.wms.fw.util.DateUtil"%>
<%
String currentdate = DateUtil.getTodayString("-");
String basedate = request.getParameter("base");
if(basedate==null)
   basedate=DateUtil.getTodayString("-");

String fieldname = request.getParameter("field");
String mindate = request.getParameter("min");
String maxdate = request.getParameter("max");
String idx=request.getParameter("idx");
String[] cdate= DateUtil.getCal(basedate, 42);

String preMonth=DateUtil.getTodayString(DateUtil.add(java.sql.Date.valueOf(basedate), 0, -1, 0));
String nextMonth=DateUtil.getTodayString(DateUtil.add(java.sql.Date.valueOf(basedate), 0, 1, 0));

%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7, IE=EmulateIE9"/>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=euc-kr">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<link rel=stylesheet type="text/css" href="/css/skin04_viewstyle.css">
<link rel=stylesheet type="text/css" href="/css/skin04_form.css">
<script Language="JavaScript" src="/common/link/calendarD.js"></script>
<script Language="VBScript" src="/common/link/VBDayConverter.js"></script>
<script Language="JavaScript">
</script>

<title><%=basedate.substring(0,4)%>년 <%=basedate.substring(5,7)%>월</title></head>
<body text="#000000" bgcolor="#FFFFFF" topmargin=0 leftmargin=0 rightmargin=0 onload="document.body.style.backgroundColor = &quot;white&quot;;">

<form action=""><table width=170 height=180 cellpadding=1 cellspacing=1 border=0 align=center>
<input type='hidden' name='fieldname' value='<%=fieldname%>'>
<input type='hidden' name='mindate' value='<%=mindate%>'>
<input type='hidden' name='maxdate' value='<%=maxdate%>'>
<tr valign=top height=7><td></td></tr>
<tr valign=top>
	<td>
		<table width=100% height=24 cellpadding=0 cellspacing=1 class=tablecalendar>
			<tr valign=middle class=movecalendar>
				<td width=22 style="vertical-align:bottom" align=left>&nbsp;<a href="/common/calendarD.jsp?field=<%=fieldname%>&min=<%=mindate%>&max=<%=maxdate%>&base=<%=preMonth%><%=(idx!=null)?"&idx="+idx:""%>"><img src="../images/b_schd_arrowleft.gif" border=0 width=13 height=11  alt="지난달로 가기"></a></td>
				<td align=center style="vertical-align:middle"><b><select name="YearList" class=ymselect onchange="javascript:RefreshCalendar('<%=fieldname%>','<%=basedate%>','<%=maxdate%>','<%=mindate%>','<%=(idx!=null)?idx:""%>')">
<%
    int year = Integer.parseInt(basedate.substring(0,4));
	int index=year-3;
	for(int i=0;i<=10;i++){%>
		<option value='<%=index+i%>' <%=(index+i==year)?" selected ":""%>><%=index+i%></option>
	<%}
%>
</select>년<select name="MonthList" class=ymselect onchange="javascript:RefreshCalendar('<%=fieldname%>','<%=basedate%>','<%=maxdate%>','<%=mindate%>','<%=(idx!=null)?idx:""%>')">
<%
	int month= Integer.parseInt(basedate.substring(5,7));
	for(int i=1;i<=12;i++){%>
		<option value='<%=(i<10)?"0"+i:""+i%>' <%=(i==month)?" selected ":""%>><%=(i<10)?"0"+i:""+i%></option>
	<%}
%>
</select>월</b></td>
				<td width=22 style="vertical-align:bottom" align=right><a href="/common/calendarD.jsp?field=<%=fieldname%>&min=<%=mindate%>&max=<%=maxdate%>&base=<%=nextMonth%><%=(idx!=null)?"&idx="+idx:""%>"><img src="../images/b_schd_arrowright.gif" border=0 width=13 height=11 alt="다음달로 가기"></a>&nbsp;</td>
			</tr>
			</table>
	</td>
<tr align=center>
	<td>
		<table width=100% height=150 cellspacing=0 cellpadding=0 border=0>
			<tr  class=trcal align=center valign=middle>
				<td width=16%><font color=red>일</font></td>
				<td width=14%>월</td>
				<td width=14%>화</td>
				<td width=14%>수</td>
				<td width=14%>목</td>
				<td width=14%>금</td>
				<td width=14%><font color=blue>토</font></td></tr>
           <%
		   for(int i=0;i<42;i++){%>
			<%=((i!=0)&&(((i)%7)==0))?"</tr>":""%>
			
			<%=(i%7==0)?"<tr  bgcolor=white align=center>":""%>
		          <td><a href="javascript:ReturnDateD<%=(idx!=null&&!idx.trim().equals(""))?"2":""%>('<%=cdate[i]%>','<%=mindate%>','<%=maxdate%>','<%=fieldname%>'<%=(idx!=null&&!idx.trim().equals(""))?","+idx:" "%>)"
				 onmouseover=disLUNDate(<%=cdate[i].substring(0,4)%>,<%=cdate[i].substring(5,7)%>,<%=cdate[i].substring(8)%>) onmouseout=hideLUNDate()>
				  <%if(!cdate[i].substring(5,7).equals(basedate.substring(5,7))){%>
					 <font color=#808080><%=cdate[i].substring(8)%></font>
				  <%}else if(cdate[i].equals(basedate)){%>
                     <font color=red><%=cdate[i].substring(8)%></font>
                  <%}else{%>
				    <%=cdate[i].substring(8)%>
				  <%}%>
				 </a></td>
           <%}%>
			<tr height=2 class=trcal>
				<td colspan=7></td>
			</tr>
		</table>
	</td>
</tr>
<tr>
	<td>
		<table border=0 cellpadding=0 cellspacing=1 width=100%><tr><td><span id=LUNDate><span>
			&nbsp;</td><td width=60 align=center><A href="/common/calendarD.jsp?field=<%=fieldname%>&min=<%=mindate%>&max=<%=maxdate%>&base=<%=currentdate%><%=(idx!=null)?"&idx="+idx:""%>">
			[오늘]</a></td><td width=5></td><td width=40 class="base"  style ="cursor:hand;" onMouseOver="this.className='on'" onMouseOut="this.className='base'" onMouseDown="this.className='down'" onMouseUp="this.className='on'" onclick="window.close()">
			닫기</td></tr></table>
	</td>
</tr>
</table></form>
</body>
</html>
