<!--
	*****************************************************
	�� �� ��  :index_left_a.jsp
	�ۼ�����  : 2010-08-99
	�� �� ��  : mailbest
	��     �� : ������ �޴���ȸ(�޴�/���⹰ Tab)
	*****************************************************
-->  
<%@ page contentType="text/html;charset=EUC-KR"
	errorPage="/common/error.jsp"%>
<%@ page import="com.wms.common.beans.dto.DataSet"%>
<%@ page import="com.wms.fw.util.DateUtil"%>
<%@ page import="com.wms.fw.Utility"%>
<%@ page import="com.wms.fw.servlet.HttpUtility"%>
<%@ page import="com.wms.fw.servlet.Box"%>
<jsp:useBean id="user" class="com.wms.beans.dto.UserInfo"	scope="session" />
<%
	request.setCharacterEncoding("ksc5601");
	Box box = HttpUtility.getBox(request);
	
	String[] parameters=new String[20];
	
	request.setAttribute("fileName","ProductDoc.xml");
	request.setAttribute("idx","MENU_001");
	request.setAttribute("parameters",parameters);
	request.setAttribute("setString","Y");//parameter�� �÷������� �����Ҷ� ���
		
	String currentDate = DateUtil.getTodayString("-");
	                                                                                                                                                                                                                                                 
	parameters[0]= "EMPID"; 
	parameters[1]=user.empId;
	
	parameters[2]= "S_TITLE_L";
	parameters[3]=box.get("S_TITLE_L");


	parameters[4]= "S_DOCTYPE";
	if (box.get("S_DOCTYPE").equals("")){		
		parameters[5] ="%";
	}else{
		parameters[5]=box.get("S_DOCTYPE");
	}
	
	parameters[6]= "S_DIV";
	if (box.get("S_DIV").equals("")){		
		parameters[7] ="%";
	}else{
		parameters[7]=box.get("S_DIV");
	}
	
	parameters[8]= "S_USETYPE";
	if (box.get("S_USETYPE").equals("")){		
		parameters[9] ="%";
	}else{
		parameters[9]=box.get("S_USETYPE");
	}
	
	parameters[10]= "S_TITLE";
	parameters[11]=box.get("S_TITLE");

	parameters[12]= "S_TITLE_P";
	parameters[13]=box.get("S_TITLE_P");
	
	parameters[14]= "S_MENU_ID";
	parameters[15]=""; //PMS�޴�

%>
<jsp:include page="/common/comDataSet.jsp" flush="true" />
<%
DataSet set=(DataSet)request.getAttribute("set");	
%>
<%	
int idx=0;
if (set!=null)	idx=set.getLength("CODENO"); 
%>

<!--�Ŵ�����Ʈ ��ȸ  -->
<%	   request.setAttribute("idx","MENU_002"); %>
<jsp:include page="/common/comDataSet.jsp" flush="true" />
<%
		DataSet set2=(DataSet)request.getAttribute("set");
		int idx2=0;
		if(set2!=null) idx2=set2.getLength("CODENO");
%>
<!--�Ŵ� ���ã�� ����Ʈ ��ȸ  -->
<%	   request.setAttribute("idx","MENU_003"); %>
<jsp:include page="/common/comDataSet.jsp" flush="true" />
<%
		DataSet set3=(DataSet)request.getAttribute("set");
		int idx3=0;
		if(set3!=null) idx3=set3.getLength("CODENO");
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7, IE=EmulateIE9"/>
<meta http-equiv="Content-Language" content="ko">
<meta http-equiv="Content-Type"	content="text/html; charset=ks_c_5601-1987">
<LINK rel="stylesheet" type="text/css"	href="../css/skin00_viewstyle.css">
<script Language="JavaScript" src="/common/link/common.js"	type="text/javascript"></script>
<script Language="JavaScript" src="/common/link/combo.js"></script>	
<script Language="JavaScript" src="/common/link/tagCalendar.js"	type="text/javascript"></script>
<script Language="JavaScript" src="/common/link/comm_check.js"></script>
<script Language="JavaScript" src="/common/link/comAction.js"></script>
<script Language="JavaScript" src="/common/link/comAjax.js"></script>
<script Language="JavaScript">
<!--

//�Ŵ� ��ȸ
function openUrl_m(Url,useType,docType,docNo){
	var frm = document.forms[0];

	frm.S_DOCNO.value =docNo;
	
	getObj = document.getElementById('UrlLink_m');	
	getObj.target="main";
	if (useType=='N' & Url!=''){
		getObj.href="/"+Url;
		getObj.click();
	} else if (useType=='N' & docType=='JSPID' & Url==''){
		getObj.href="/common/empty.html";
		getObj.click();
	} else if (useType=='Y' & docType=='JSPID'){
		getObj.href="/common/underConstruct.jsp";
		getObj.click();
	} else if ( docType=='PKG'){
		getObj.href="/common/empty.html";
		getObj.click();
	}
}

/*tree �׸���*/
function addItem(lev,id,htmlStart,htmlMid,htmlEnd,levelWidth){
	var treeImg='';
    if (levelWidth ==undefined) levelWidth='10';

	for(i=0;i<lev;i++) if(i!=0) treeImg = treeImg+ '<img src="../images/treeImage/s.gif" width="'+levelWidth+'" height="10" style="border:1">'; //tree ��������
	
	htmlMid=treeImg+htmlMid;
	document.getElementById(id).insertAdjacentHTML('beforeEnd',htmlStart+htmlMid+htmlEnd);
}
//�Ŵ� Tree ����
function setTree(){		
	var id ='', html ='',lev ='', objImg ='',htmlRow='';
	//Tree type ����
	treeType = '2';	
	
	<%for(int i=0;i<idx2;i++){%>
		LEV ='<%=set2.get("LEV",i)%>';
		//Level 1 �ΰ��
		if(LEV==1) {
			id ='D_<%=set2.get("CODENO",0)%>_@';
		} else if ('<%=set2.get("LEV",i)%>'>'<%=set2.get("LEV",i-1)%>') {
			id ='D_<%=set2.get("HIGHCODE",i)%>';
		} else{
			id ='D_<%=set2.get("HIGHCODE",i)%>';
		}
	    
	    //Tree row ����
			htmlStart ='<div id= "<%=set2.get("CODENO",i)%>" '+
			           'style="height:24px;cursor:hand;display:;text-align:left;vertical-align:middle;" '+
			           'onClick="setDir(this.id,2,3,'+treeType+');setBgColor(this,2);"  '+
			           'onMouseover="setBgColor(this,1);"  '+
			           'onMouseout="setBgColor(this,0);">'

		//Tree image ����
		if((<%=Integer.parseInt(set2.get("LEV",i))%> < <%=Integer.parseInt(set2.get("LEV",i+1))%>) & ( '<%=set2.get("MU_CNT",i+1)%>'>=1)) {
	 	 	 objImg= "tree_plus.gif";	 
		}else{
 		 	 objImg= "tree.gif";	 
		}

		//Tree img �⺻
			htmlMid ='<img id="img_<%=set2.get("CODENO",i)%>" '+
					 'style="vertical-align:middle;" '+
					 'onClick="setDir(this.id,2,1,'+treeType+');"'+
					 'src="../images/treeImage/'+treeType+'/'+objImg+ '">';
		
		//Tree ���� ó��
		if('<%=set2.get("URL",i)%>'==''){
			htmlRow="<font color='#d0d0d0' onClick=openUrl_m('<%=set2.get("URL",i)%>','<%=set2.get("USETYPE",i)%>','<%=set2.get("DOCTYPE",i)%>','<%=set2.get("CODENO",i)%>');><%=set2.get("L_TITLE",i)%></font>";
		}else{
			htmlRow="<font color='' onClick=openUrl_m('<%=set2.get("URL",i)%>','<%=set2.get("USETYPE",i)%>','<%=set2.get("DOCTYPE",i)%>','<%=set2.get("CODENO",i)%>');><%=set2.get("L_TITLE",i)%></font>";
		}

		htmlMid =htmlMid + htmlRow;
		 
		htmlEnd ='</div><div id= "D_<%=set2.get("CODENO",i)%>" '+
		         'style="cursor:hand;'+
		         'display:none;'+
		         '"></div>';	

		//Tree Node ���		
		addItem(LEV,id,htmlStart,htmlMid,htmlEnd);

		//�ʱ� ��ȸ LEVEL ����
		if (LEV<1){
			getObj = document.getElementById('D_<%=set2.get("CODENO",i)%>');	
			getObj.style.display='';
		}
	<%}%>
}
//���ã�� ���
function setPopUpData(selData){
   	divSearch(); //�˻� ����â ���̱� 
	strArray = selData.split("��");
	for (i=0;i*3<strArray.length-2;i++){
		j=i*3;
		gfn_ajax_request('',new Array('<%=user.empId%>',strArray[j]),'ComPopup','FAV_001');
	}
	var frm = document.forms[0];
	frm.submit();			 
}
//���ã�� ���� ���� (���� ��)
function rmFdBtn(){
	var frm = document.forms[0];
	if(frm.S_DOCNO.value!='' && confirm("���ã�⿡��  ���� �Ͻðڽ��ϱ�?")){
	   	divSearch(); //�˻� ����â ���̱� 
		gfn_ajax_request('',new Array("fav_0",'<%=user.empId%>',frm.S_DOCNO.value),'ComPopup','FAV_003');
		frm.submit();
	} else {
		alert("���� �� ����� ���� ���� �ϼ���!");
	}
}
//���ã�� ����
function setFav(param){
	var frm = document.forms[0];
	frm.S_DOCNO.value=param.substring(2,20);
}
//�Ŵ�����
function menuMgt(rootYN){
	var frm = document.forms[0];
	if(rootYN=='Y'){//root�Ŵ� ���
		openWin("http://mas.hist.co.kr/ProductDoc/regDocSub.jsp?S_HIGHDOCNO=PROD-20090225-001","",600,450);
	}else{
		openWin("http://mas.hist.co.kr/ProductDoc/regDocSub.jsp?S_HIGHDOCNO="+frm.S_DOCNO.value,"",600,450);
	}		
}
//-->
</script>
</HEAD>
<TITLE>[MAIN MENU]</TITLE>
<body text="#000000" bgcolor="#FFFFFF" topmargin="0" leftmargin="0" rightmargin="0" onload="setTree();">
<form name="form1" method="POST" action="">
<input type='hidden' name='treeImgClk' value=''>
<input type='hidden' name='S_DOCNO' value=''>
<!--�˻� ���� â-->
<div id=processing style='position:absolute;display="none";z-index=999;background-color="#f0fff0";'></div>
<div ID="divList" style="position:relative;left:0;top:0;height:;overflow-x:; overflow-y:auto;z-index:5" >	
<table width='100%' border='0'>
	<tr >
		<td>
<!--�Ŵ�����Ʈ ��ȸ -->		
	<div id="togg_left" name="togg_left" border="0" cellpadding="0" cellspacing="0" style="display:">	
		<table  width='100%' cellspacing=0 cellpadding=0 border=0>
			<tr>
				<td name="addFd" style='cursor:hand' align='right'>
					<img id="addFdBtn" name='addFdBtn' src='/images/edms/rnFolder.png' alt="��Ʈ�Ŵ� �߰�"
					 	 onClick="menuMgt('Y')"  
						 onMouseOver= "this.src='/images/edms/rnFolder_over.png'"
						 onMouseOut="this.src='/images/edms/rnFolder.png'"
					></td>		
				<td name="addFd" width='15' style='cursor:hand' align='right'>
					<img id="addFdBtn" name='addFdBtn' src='/images/edms/mkFolder.png' alt="�Ŵ��߰�"
					 onClick="menuMgt()"  
					 onMouseOver= "this.src='/images/edms/mkFolder_over.png'"
					 onMouseOut="this.src='/images/edms/mkFolder.png'"
					></td>
			</tr>
		</table>
	<a id='UrlLink_m' name='UrlLink_m'>
		<div id='<%=set2.get("CODENO",0)%>_@' ></div>
		<div id='D_<%=set2.get("CODENO",0)%>_@' ></div>
	</a>
	</div>			
		</td>
	</tr>
</table>
</div>
</form>
</body>
<script language="javascript" type="text/javascript">
	setDivSize(25); //div������ ����
</script>
</html>
