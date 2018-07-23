<!--
	파 일 명  : comHeader.jsp
	내    용  : 공통 헤더 정렬
-->
<%@ page contentType="text/html;charset=euc-kr"
	errorPage="/common/error.jsp"%>
<%@ page import="com.wms.fw.servlet.Box"%>
<%@ page import="com.wms.fw.servlet.HttpUtility"%>
<%@ page import="com.wms.fw.Utility"%>
<%Box box = HttpUtility.getBox(request);%>
<%
	String	onClick=box.get("onClick");
	String	objName=box.get("objName");
	String	objCol=box.get("objCol");
	String	objIdx=box.get("objIdx");
	String	objWidth=box.get("objWidth");
	
%>
<td width="<%=objWidth%>" class='viewContentSH'
	onMouseOver="this.className='viewOnH'"
	onMouseOut="this.className='viewContentSH'"
	onclick='setOrderBy("<%=objCol%>","<%=objIdx%>");<%=onClick%>;'><%=objName%><img name='sort_'  id='sort_'	src='../../images/sort_down.gif'><img name='sort_' id='sort_'	src='../../images/sort_up.gif'>
</td>
