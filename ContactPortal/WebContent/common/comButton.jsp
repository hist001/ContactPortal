<!--
	�� �� ��  : comButton.jsp
	��    ��  : ���� ��ư
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
	String	objsize=box.get("objSize"); // 'T'- ���ڸ� , ���°�� ���� �����߰�
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
<!--�˾� �˻� -->
<%}else if(objName.equals("")){%> 
	<Button	onclick="<%=onClick%>" width=''>&nbsp;<img src='/images/search.gif' style="vertical-align:middle;cursor:hand;" />
</Button>	
<!--�˻� -->
<%}else if(objName.equals("��ȸ")||
		objName.equals("���")||
		objName.equals("����")||
		objName.equals("����")||
		objName.equals("�˻�")||
		objName.equals("�ݱ�")||
		objName.equals("Excel")||
		objName.equals("����")||
		objName.equals("���")||
		objName.equals("�μ�")||
		objName.equals("�׷���")||
		objName.equals("Graph")||
		objName.equals(" ")||
		objName.equals("���û���")||
		objName.equals("ȯ�漳��")||
		objName.equals("����")||
		objName.equals("�ް�����ȹ��")){%> 
<table id='<%=objName%>' border="0" cellpadding="0" cellspacing="0">
	<tr>			
		<td CLASS="bt_f" nowrap></td>
		<td class='button' style="cursor:hand;"
			onMouseOver="this.className='buttonOn'"
			onMouseOut="this.className='button'"
			onclick="<%=onClick%>" >
		<%if(objName.equals("��ȸ")){%>	
			<img src='/images/search.gif' style="vertical-align:middle;width:10" >
		<%}else if(objName.equals("���")){ %>
			<img src='/images/insert.gif' style="vertical-align:middle;" >
		<%}else if(objName.equals("����")){ %>
			<img src='/images/insert.gif' style="vertical-align:middle;" >
		<%}else if(objName.equals("����")){ %>
			<img src='/images/insert.gif' style="vertical-align:middle;" >
		<%}else if(objName.equals("�˻�")){ %>
			<img src='/images/search0.gif' style="vertical-align:middle;" >
		<%}else if(objName.equals("�ݱ�")){ %>
			<img src='/images/close_i.png' style="vertical-align:middle;" >
		<%}else if(objName.equals("����")){ %>
			<img src='/images/xls.gif' style="vertical-align:middle;width:12;" >
		<%}else if(objName.equals("Excel")){ %>
			<img src='/images/xls.gif' style="vertical-align:middle;width:12;" >
		<%}else if(objName.equals("�μ�")){ %>
			<img src='/images/print.gif' style="vertical-align:middle;" >
		<%}else if(objName.equals("���")){ %>
			<img src='/images/print.gif' style="vertical-align:middle;" >
		<%}else if(objName.equals("����")){ %>
			<img src='/images/delete.gif' style="vertical-align:middle;" >
		<%}else if(objName.equals("���û���")){ %>
			<img src='/images/delete.gif' style="vertical-align:middle;" >
		<%}else if(objName.equals("�׷���")){ %>
			<img src='/images/graph.gif' style="vertical-align:middle;width:10" >
		<%}else if(objName.equals("Graph")){ %>
			<img src='/images/graph.gif' style="vertical-align:middle;width:10" >
		<%}else if(objName.equals("ȯ�漳��")){ %>
			<img src='/images/config.png' style="vertical-align:middle;width:19" >
		<%}else if(objName.equals("�ް�����ȹ��")){ %>
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

