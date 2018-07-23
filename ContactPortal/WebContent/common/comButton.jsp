<!--
	파 일 명  : comButton.jsp
	내    용  : 공통 버튼
-->
<%@ page contentType="text/html;charset=euc-kr" errorPage="/common/error.jsp"%>
<%@ page import="com.wms.fw.servlet.Box" %>
<%@ page import="com.wms.fw.servlet.HttpUtility" %>
<%@ page import="com.wms.fw.Utility"%>
<%Box box = HttpUtility.getBox(request);%>
<%
	String	onClick=box.get("onClick");
	String	onClickParam=box.get("onClickParam");
	String	objName=box.get("objName");
	String	objsize=box.get("objSize"); // 'T'- 글자만 , 없는경우 양쪽 공백추가
%>
<% if(!objName.equals("")&objsize.equals("T")){ %>
<table id='<%=objName%>' border="0" cellpadding="0" cellspacing="0">
	<tr>			
		<td CLASS="bt_f" nowrap></td>
		<td class='button' style="cursor:hand;"
			onMouseOver="this.className='buttonOn'"
			onMouseOut="this.className='button'"
			onclick="<%=onClick%>">
				<%if(objName.equals(" "))out.println("+"); %>
				<%=objName%>
		</td>
		<td CLASS="bt_e" nowrap></td>	
	</tr>
</table>
<!--팝업 검색 -->
<%}else if(objName.equals("")){%> 
	<Button	onclick="<%=onClick%>" width=''>&nbsp;<img src='/images/search.gif' style="vertical-align:middle;cursor:hand;" />
</Button>	
<!--검색 -->
<%}else if(objName.equals("조회")||
		objName.equals("등록")||
		objName.equals("수정")||
		objName.equals("저장")||
		objName.equals("검색")||
		objName.equals("닫기")||
		objName.equals("Excel")||
		objName.equals("엑셀")||
		objName.equals("출력")||
		objName.equals("인쇄")||
		objName.equals("그래프")||
		objName.equals("Graph")||
		objName.equals(" ")||
		objName.equals("선택삭제")||
		objName.equals("환경설정")||
		objName.equals("삭제")||
		objName.equals("휴가사용계획서")){%> 
<table id='<%=objName%>' border="0" cellpadding="0" cellspacing="0">
	<tr>			
		<td CLASS="bt_f" nowrap></td>
		<td class='button' style="cursor:hand;"
			onMouseOver="this.className='buttonOn'"
			onMouseOut="this.className='button'"
			onclick="<%=onClick%>" >
		<%if(objName.equals("조회")){%>	
			<img src='/images/search.gif' style="vertical-align:middle;width:10" >
		<%}else if(objName.equals("등록")){ %>
			<img src='/images/insert.gif' style="vertical-align:middle;" >
		<%}else if(objName.equals("수정")){ %>
			<img src='/images/insert.gif' style="vertical-align:middle;" >
		<%}else if(objName.equals("저장")){ %>
			<img src='/images/insert.gif' style="vertical-align:middle;" >
		<%}else if(objName.equals("검색")){ %>
			<img src='/images/search0.gif' style="vertical-align:middle;" >
		<%}else if(objName.equals("닫기")){ %>
			<img src='/images/close_i.png' style="vertical-align:middle;" >
		<%}else if(objName.equals("엑셀")){ %>
			<img src='/images/xls.gif' style="vertical-align:middle;width:12;" >
		<%}else if(objName.equals("Excel")){ %>
			<img src='/images/xls.gif' style="vertical-align:middle;width:12;" >
		<%}else if(objName.equals("인쇄")){ %>
			<img src='/images/print.gif' style="vertical-align:middle;" >
		<%}else if(objName.equals("출력")){ %>
			<img src='/images/print.gif' style="vertical-align:middle;" >
		<%}else if(objName.equals("삭제")){ %>
			<img src='/images/delete.gif' style="vertical-align:middle;" >
		<%}else if(objName.equals("선택삭제")){ %>
			<img src='/images/delete.gif' style="vertical-align:middle;" >
		<%}else if(objName.equals("그래프")){ %>
			<img src='/images/graph.gif' style="vertical-align:middle;width:10" >
		<%}else if(objName.equals("Graph")){ %>
			<img src='/images/graph.gif' style="vertical-align:middle;width:10" >
		<%}else if(objName.equals("환경설정")){ %>
			<img src='/images/config.png' style="vertical-align:middle;width:19" >
		<%}else if(objName.equals("휴가사용계획서")){ %>
			<img src='/images/print.gif' style="vertical-align:middle;" >
		<%}else if(objName.equals(" ")){ %>
			&nbsp;+
		<%}%>	
			<%=objName%>&nbsp;</td>			
		<td CLASS="bt_e" nowrap></td>
	</tr>
</table>
<%}else{%>
<table id='<%=objName%>' border="0" cellpadding="0" cellspacing="0">
	<tr>			
		<td CLASS="bt_f" nowrap></td>
		<td class='button' style="cursor:hand;"
			onMouseOver="this.className='buttonOn'"
			onMouseOut="this.className='button'"
			onclick="<%=onClick%>">&nbsp;&nbsp;<%=objName%>&nbsp;&nbsp;</td>			
		<td CLASS="bt_e" nowrap></td>	
	</tr>
</table>
<%}%>

