
<!--
	�� �� ��  : fileUpload.jsp
	�ۼ�����  : 2007/06/04
	�� �� ��  : ����ȣ
	��    ��  : ���� ÷�� UI
-->
<%@ page contentType="text/html;charset=ksc5601" errorPage="/common/error.jsp"%>
<%@ page import="com.wms.common.beans.dto.UploadFilesDTO"%>
<jsp:useBean id="user" class="com.wms.beans.dto.UserInfo" scope="session"/>
<jsp:useBean id="isCall" class="java.lang.String" scope="request"/>
<%		
		UploadFilesDTO[] files = (UploadFilesDTO[])request.getAttribute("files");

		String ui_no	=request.getParameter("ui_no");
		String obj_no	=request.getParameter("obj_no");
		String dir		=request.getParameter("dir");
		System.out.println("@@@@@@@ fileUpload.jsp ::::>>>>" + dir);
%>
<!--�׽�Ʈ ����-->
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7, IE=EmulateIE9"/>
<LINK rel="stylesheet" type="text/css" href="../css/skin04_form.css">
<script Language="JavaScript" src="../common/link/comfiles.js"></script>
</head>
<body text="#000000" style="background-color=#FFFFFF" topmargin=0 leftmargin=0 rightmargin=0 onload="init('<%=ui_no%>','<%=obj_no%>','<%=dir%>')">
<FORM   id="com_Files" method="Post" action='/common/upload.jsp?ui_no=<%=ui_no%>&obj_no=<%=obj_no%>&dir=<%=dir%>' enctype="multipart/form-data">
<table align=center cellpadding=1 cellspacing=1 border=0 class=table1 width=100% id="table1" >
	<tr>
		<td width="80" class='td1' align='center' height="24">
		�����ڷ�
		</td>
		<td width="" class='td2' align='left' height="24" >
		<span id='fileSpan' style="width: 100%; height:80; overflow-y: scroll;">
<%
		if(files!=null){
%>
				<script>				
				init('<%=ui_no%>','<%=obj_no%>','<%=dir%>');
				setFileIdx(<%=files[files.length-1].file_no%>);
				</script>			
	        <%			
			for(int i=0;i<files.length;i++){
				
				if(files[i].delFlag.equals("N")){%>
				<script>				
				selectFile('<%=files[i].file_no%>',
					       '<%=files[i].dir%>',
				           '<%=files[i].fileSystemName%>',
				           '<%=files[i].fileOriginName%>')
				</script>
				
			<%  
				}
			}
		}
%>
        </span>
		</td>
		<td width="20%" class='td2' align='center' height="24" valign=top >
			<jsp:include page="/common/comButton.jsp?objName=�����߰�&onClick=addFile();" flush="false"/>
			<jsp:include page="/common/comButton.jsp?objName=��������&onClick=saveFile();" flush="false"/>														
		</td>
	</tr>	
</table>

</FORM>
</body>

