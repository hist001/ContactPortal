<!--
	파 일 명  : comAriUpload.jsp
	작성일자  : 2011/05/25
	작 성 자  : mailbest
	내    용  : 공통 파일 업로드(ari)
-->
<%@ page contentType="text/html;charset=euc-kr"%>
<%@ page import="com.wms.common.beans.dto.DataSet"%>
<%@ page import="com.wms.fw.Utility"%>
<%@ page import="com.wms.fw.servlet.HttpUtility"%>
<%@ page import="com.wms.fw.servlet.Box"%>
<%@ page import="com.wms.fw.db.DataBaseUtil"%>
<%@ page import="java.io.File,java.net.URLEncoder" %>
<jsp:useBean id="user" class="com.wms.beans.dto.UserInfo" scope="session"/>
<%
  request.setCharacterEncoding("UTF-8");
  Box box = HttpUtility.getBox(request); 

  String srvName = request.getServerName();
  String srvPort = Integer.toString(request.getServerPort());
  String webPath = request.getRequestURI().substring(0, request.getRequestURI().lastIndexOf('/'));
  String srvPath = "C:/upload/"+box.get("PATH");
 
  File fileDir = new File(srvPath);
  File files[] = fileDir.listFiles();
%>
<%
	request.setCharacterEncoding("EUC-KR");
	
	String[] parameters=new String[20];
	request.setAttribute("fileName","Common.xml");
	request.setAttribute("idx","COM_010");
	request.setAttribute("parameters",parameters);
	request.setAttribute("setString","Y");//parameter을 컬럼명으로 지정할때 사용
	
	parameters[0]= "UI_NO"; 
	parameters[1]= box.get("UI_NO");
	parameters[2]="OBJ_NO"; 
	parameters[3]= box.get("DIR")+box.get("OBJ_NO");
	parameters[4]="DELFLAG"; 
	parameters[5]= "N";	
%>
<jsp:include page="/common/comDataSet.jsp" flush="true"/>
<%
DataSet set=(DataSet)request.getAttribute("set");	
%>
<%	
int idx=0;
if (set!=null)	idx=set.getLength("UI_NO"); 
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7, IE=EmulateIE9"/>
<meta http-equiv="Content-Language" content="ko">
<meta http-equiv="Content-Type"	content="text/html; charset=UTF-8">
<LINK rel="stylesheet" type="text/css"	href="../css/skin04_viewstyle.css">
<script Language="JavaScript" src="/common/link/comAjax.js"></script>
<script Language="JavaScript" src="/common/link/combo.js"></script>

<style type="text/css">
DL.title1 { font-size:9pt;list-style-image: url(../images/arrow1.gif); }
body { font-size:9pt; }
table,caption,th,td,{ font-size:9pt; }
textarea,select { font-size:9pt;border:solid 1px #7F9DB9; }
.input_image { border:none;vertical-align:middle; }
input { font-size:9pt;vertical-align:middle;border:solid 1px #7F9DB9; }
input.readonly { font-size:9pt;border:solid 1px #7F9DB9;color:#5D5D5D; }
pre { font-size:9pt;line-height:130%; }
a:link { color: #0000FF; text-decoration: none }
a:visited { color: #00000; text-decoration: none }
a:active { color: #000000; text-decoration: none }
a:hover { color: #28658F; text-decoration: underline }
fieldset.desc { border:#C7C7C7 solid 1px; }
legend.desc { font-size:9pt; }
pre.desc { font-size:9pt;color:#3C3C3C;margin-top:5px;margin-bottom:5px;margin-left:10px;margin-right:5px; }
</style>
<script language="javascript">
  var FILE_SERVER = "기존파일";
  var FILE_LOCAL  = "신규추가";
  var FILE_DELETE = "삭제예정";

  /* 리스트뷰 보기 형식 */
  function SetListStyle(nStyle){
      document.all.AriListObj.SetListViewStyle(nStyle);    
  }
  /* 환경설정 */
  function OpenSetup(){
    document.all.AriListObj.SetOption();
  }
  /* 다운로드 폴더 열기 */
  function ChangeFolder(){
    var ari = document.all.AriListObj;
    if(ari.SetDownloadPathDlg(true)){
      myform.download_path.value = ari.DownloadPath;
    }
  }
  /* 다운로드 폴더 변경 */
  function OpenFolder(){
    document.all.AriListObj.OpenFolder();
  }
  
  /* 선택된 파일 목록에서 삭제 */
  function DeleteFile()
  {
    var flag = "";
    var ari = document.all.AriListObj;

    for(var i=ari.getItemCount()-1; i>=0; i--){
      if(ari.IsSelected(i)){
        flag = ari.getItemText(i, 2);

        if(flag==FILE_SERVER){
          ari.setItemText(i, 2, FILE_DELETE);
        }else if(flag==FILE_LOCAL){
          ari.DeleteItem(i);
        }
      }
    }
  }
  /* 파일 다운로드 */
  function FileDownload(bAll){
    var ari = document.all.AriDownObj;
    
    if(ari.GetCount()==0){
      alert("다운로드할 파일이 하나도 없습니다.");
      return;
    }
    if(bAll){
      ari.Download();
    }else{
      if(ari.GetSelectedItemCount()>0){
        ari.DownloadSelectedFile();
      }else{
        alert("선택된 파일이 하나도 없습니다.");
        return;
      }
    }
  }
  /* 파일 업로드 */
  function UploadStart()
	{
	  var frm= document.forms[0];
	  var fileName, fileUrl, fileSize, flag;
	  var ari = document.all.AriListObj;
	  
    // 파일데이터 추가
    for(var i=0; i<ari.getItemCount(); i++){
      flag = ari.getItemText(i, 2);
      if(flag==FILE_LOCAL){

		//파일 DB기등록 여부 확인 후 DB등록 
		if(chkFile(ari.getItemText(i,0))) {
		
        ari.AddUploadItem(i,
                          ari.getItemText(i,0),
                          ari.getItemData(i),
                          ari.GetFileItemSize(i),
                          "",
                          "");                    
		}else{
			alert("'"+ari.getItemText(i,0)+ "' 파일은 이미 등록되어 있습니다.");
		}
      }else if(flag==FILE_DELETE){
		//삭제 파일 존재 확인 
		frm.CHK_DEL_FL.value='Y';
        ari.AddFormData("delFiles", ari.getItemText(i,0));
		//파일 DB삭제 등록
      	delFile(ari.getItemText(i,6));
      }
    }

	if(frm.CHK_DEL_FL.value=='Y' &&!confirm("삭제 데이타가 선택되어있습니다. 계속진행 하시겠습니까?")) {
		return;
	}
	
    ari.AddHeader("hasExt",true);
    ari.Upload();
	}
/* 선택 파일 다운로드 */
function DownloadStart(bSelected)
	{
	  var fileName, fileUrl, fileSize, flag;
	  var ari = document.all.AriListObj;
      var fileCnt = 0;

    for(var i=0; i<ari.getItemCount(); i++){
      flag = ari.getItemText(i, 2);
      if(flag==FILE_SERVER){
        if(!bSelected || (bSelected && ari.IsSelected(i))){
          ari.AddDownloadItem(i,
                                  ari.getItemText(i,5),
                                  ari.getItemData(i),
                                  ari.GetFileItemSize(i),
                                  "");
          fileCnt++;
        }
      }
    }
    if(fileCnt==0){
      alert("다운로드 항목이 하나도 없습니다.");
      return;
    }
		ari.Download();
	}
	
//등록 파일 여부 체크
function chkFile(filename){
    var frm= document.forms[0];
    frm.dupFile.value ='';
	chkDupFile('<%=box.get("UI_NO")%>','<%=box.get("DIR")%><%=box.get("OBJ_NO")%>',filename);
	if (frm.dupFile.value!='Y'){
		return true;
	}else{
	 	return false;
	}
}
//파일 DB등록 COMS번호 가져오기 
function getComsNo(){
	gfn_ajax_service('comsNo',new Array(),'Common','COM_007','gData');
}
function mkData(objCode){
	var objCodes=objCode.split('§');
	if (objCodes[1]=='Y'){
		document.forms[0].dupFile.value=objCodes[1];
	}else{	
		document.forms[0].comsNo.value=objCodes[1];
	}
}
//파일 DB기등록 체크
function chkDupFile(UI_NO,DIR,FILENAME){
	gfn_ajax_service('comFile',new Array(UI_NO,DIR,FILENAME),'Common','COM_009','gData');
}	
//파일 DB등록
function createFile(UI_NO,DIR,FILENAME,SYSFILENAME,PATH){
	gfn_ajax_request('dupFile',new Array(UI_NO,DIR,FILENAME,'<%=user.empId%>',SYSFILENAME,PATH),'Common','COM_005');
}	
//파일 DB삭제
function delFile(OBJ_NO){
	gfn_ajax_request('comFile',new Array(OBJ_NO,'<%=box.get("UI_NO")%>','<%=box.get("DIR")%><%=box.get("OBJ_NO")%>','<%=user.empId%>'),'Common','COM_012');
}	
</script>
<style>
<!--
  a{color:#ffffff ; font-size:13pt; font-weight:bold; font-style: italic}
  b{color:#000000 ; font-size:14pt; font-weight:bold;}	
  p{color:#000000 ; font-size:11pt; font-weight:bold;}
  q{color:#ffffff ; font-size:10pt; font-weight:bold; font-style: italic}
-->
</style>  

</head>
<body text="#000000" bgcolor="#FFFFFF" topmargin="0" leftmargin="0"	rightmargin="0" onload="javascript:">
<form name="myform" method="POST" action="">
<input type="hidden" name="_SCREEN">
<input type="hidden" name="DIR" value="<%=box.get("DIR")%><%=box.get("OBJ_NO")%>">	
<input type="hidden" name="PATH" value="<%=box.get("PATH")%>">	
<input type="hidden" name="fileDir" value="<%=fileDir%>">	
<input type="hidden" name="DOC_DIV" value="<%=box.get("DOC_DIV")%>">	
<input type="hidden" name="CHK_DEL_FL" value="">	
<input type='hidden' name ='comFile' id ='comFile' value=''>
<input type='hidden' name ='comsNo' id ='comsNo' value=''>
<input type='hidden' name ='dupFile' id ='dupFile' value=''>
<input type='hidden' name ='UI_NO' id ='UI_NO' value='<%=box.get("UI_NO")%>'>
<input type='hidden' name ='OBJ_NO' id ='UI_NO' value='<%=box.get("OBJ_NO")%>'>
<table  width='100%' height='100%' cellspacing=0 cellpadding=0 border=0  >
 	<tr height='1' style='display:none'>
 		<td><img src='../images/edms/config.bmp' style='cursor:hand' onClick='OpenSetup()'></td>
	</tr>
 	<tr height='100%'>
 		<td>
			<!-- 파일 다운로드  -->
			<table width="100%" height='100%' border="0"  cellpadding="0" cellspacing="0">
			  <tr >
			    <td>
			      <table border="0" width="100%" height="100%" cellpadding="0" cellspacing="0">
			        <tr>
			          <td>
			            <!-- button begin -->
				            <table width="100%"  height="100%" cellpadding="0" cellspacing="0" border="0" >
				              <tr >
				                <td height=''>
									<table border='0'><tr>			    
									<td><jsp:include page="/common/comButton.jsp?objName=파일추가&onClick=onclick=document.all.AriListObj.OpenFileDlg(true);"  flush="false"/></td>
									<td><jsp:include page="/common/comButton.jsp?objName=저장&onClick=UploadStart();" flush="false"/></td>
									<td><jsp:include page="/common/comButton.jsp?objName=선택삭제&onClick=DeleteFile();" flush="false" /></td>
									<td><jsp:include page="/common/comButton.jsp?objName=선택다운&onClick=DownloadStart(true);" flush="false" /></td>
									<td><jsp:include page="/common/comButton.jsp?objName=다운로드&onClick=DownloadStart(false);" flush="false" />
									</td></tr>
									</table>	
			           		 </td>
			                <td align="right">
			                  <select name="listViewStyle" style="font-size:9pt;" onpropertychange="SetListStyle(this.value)">
			                    <option value="1">큰아이콘</option>
			                    <option value="2">작은아이콘</option>
			                    <option value="3">간단히</option>
			                    <option value="4" selected>자세히</option>
			                  </select>
			                </td>
							<td width='85'  align='right'><jsp:include page="/common/comButton.jsp?objName=환경설정&onClick=OpenSetup();" flush="false" /></td>			                      
			              </tr>
			            </table>
			            <!-- button end -->
			          </td>
			        </tr>
			        <tr height='100%'>
			          <td>
			            <table border="0" width="100%" height='100%' cellpadding="0" cellspacing="0">
			              <tr>
			                <td>
			                  <!-- 아리업로더 이벤트 -->
			                  <SCRIPT FOR="AriListObj" EVENT="OnFile(filename, size)">
			                    var idx = this.getItemCount()+1;
			                    var name = filename.substring(filename.lastIndexOf("\\")+1);
			                    var newIndex = this.InsertFileItem(idx, name, size, filename);
			                    this.setItemText(newIndex, 1, this.GetFormatSize(size));
			                    this.setItemText(newIndex, 2, FILE_LOCAL);
			                  </SCRIPT>
							  <script For="AriListObj" event="OnDBClick(nIndex)">
							   alert(nIndex + " 행이 의 더블클릭 이벤트가 발생했습니다.");
							  </script>
			                  <SCRIPT FOR="AriListObj" EVENT="OnInitialize();">
			                    document.all.download_path.value = this.DownloadPath;
			                    this.SetSystemImageList();
			                    this.SetListViewStyle(4);
			                  
			                    var fileurl="";
			                    var index = this.getItemCount();
			                  <%
			                    if(files!=null){
			                      for(int i=0; i<files.length; i++){
			                        if(files[i].isFile()){
										////DB DEL_CHK 비교처리 :: START
			                        	for(int j=0;j<idx;j++){
			                        		if(files[i].getName().equals(set.get("FILESYSTEMNAME",j))){	    
//			                        			files[i].getName().substring(0,files[i].getName().length()).equals(set.get("FILEORIGINNAME",j))
			                  %>
			                  	 
			                      fileurl = "http://<%=srvName%>:<%=srvPort%><%=webPath%>/comSubDownload.jsp?DIR=<%=box.get("DIR")%><%=box.get("OBJ_NO")%>&PATH=<%=box.get("PATH")%>&fn=<%= URLEncoder.encode(files[i].getName(),"UTF-8")%>&userId=<%=user.empId%>";
			                      this.InsertFileItem(index, "<%=set.get("FILEORIGINNAME",j)%><%// files[i].getName()%>",<%=files[i].length()%>, fileurl);
			                      this.setItemText(index, 1, this.GetFormatSize(<%=files[i].length()%>));
			                      this.setItemText(index, 2, FILE_SERVER);
			                      this.setItemText(index, 3, "<%=set.get("CREMPID",j)%>");
			                      this.setItemText(index, 4, "<%=set.get("CREATEDT",j)%>");
			                      this.setItemText(index, 5, "<%=set.get("FILEORIGINNAME",j)%>");
			                      this.setItemText(index, 6, "<%=set.get("FILESYSTEMNAME",j)%>");
			                      index++;
			                  <%
			                        		}
			                        	}
			                        }
			                      }
			                    }
			                  %>
			                  </SCRIPT>
			                  <SCRIPT FOR="AriListObj" EVENT="OnComplete(bUp, response);">
			                    if(bUp){
//			                      alert("자료가 변경되었습니다..");
			                      location.reload();
			                  	}else{
			                    	if(confirm("다운로드가 완료되었습니다. 다운로드 폴더를 열어보시겠습니까?")){
			                    	  this.OpenFolder();
			                  	  }
			                  	}
			                  </SCRIPT>
							  <OBJECT ID="AriListObj" WIDTH="100%" HEIGHT="100%"							  
							    CLASSID="CLSID:B64C557F-E49E-43f5-BA69-06B2CFE76366"
							    CODEBASE="/common/cab/AriUploaderX_3.1.1.2.cab#version=3,1,1,2">
									<PARAM NAME="License" VALUE="D2BEC928D8EB49BC332B8AD17EF5B8D4B7B23A739AC50EC68C653734A72CB9ECAFEDEDAAE4967ED0C4EA226598207E4B4BEDA329129C78DEE692B7902A659542407EE3E5AD57F3EB43B68824B738B96F06BD081180CED42D83CA2D7DA273CA94D81A4699BC1D6393460A5EEB542047B06BD7E8C8B081BA5DAB041A82268DFA12B98DFFC78A6D601555AFB527642A06E8D910F35BB2EFA2290701D2B2E697A08D045824874C809E7DDA2D11FCCC1E2138BFFE2A0691AE9BBFC6A4A6965AC8E34F9FE34FD075A7F90E72C6FC66074B0BDA293331832B9722BCF72896017AB785F091E8545A0F7F1420262608D791B5EE5F6DA1FAA3CEEEE5EEB46656CCF03E9DC5CF34E4EA26B3E6800C288A705A74F8A5A13424F57358BEB60311F936322A58F2991604E20DC92CA0BF656AD0DD8A1B36707F09E67F9C0EEFB71D23D56F8D61FBA830F7633EEEA4F978E2885950FCD59A8F8012C43E0EB7712A283A82AB70673E79E817C403400899160FA9D67F34DCC596A43729A9B75597C1AC2A5B53C2B6DA7E979EFF9E17B02F403ADCE920A252F0422DF40758B6F984B0CB735BEB597529ACEE5451F78B6D1189FAEEF85119F49101E5E66520517E3EAC5E4DFAF8BA09AF4B69A06025D9E7DFBFD4281791BF0B6A8847918AF02C3CBDC3804A9989C37BC02D4B9C42120AE221DB8EEEDAB19CDF27116F79495F88D4962C6719FCF118C2D2" />
									<PARAM NAME="ServerUri" VALUE="http://<%= srvName+":"+srvPort+webPath %>/comSubUpload.jsp?DIR=<%=box.get("DIR")%>&PATH=<%=box.get("PATH")%>&DOC_DIV=<%=box.get("DOC_DIV")%>&UI_NO=<%=box.get("UI_NO")%>&OBJ_NO=<%=box.get("OBJ_NO")%>&userId=<%=user.empId%>" />
									<PARAM NAME="ColumnName" VALUE="파일명,크기,구분,등록자,등록일,-,-" />
									<PARAM NAME="ColumnWidth" VALUE="170,80,80,70,75,0,0" />
									<PARAM NAME="ColumnAlign" VALUE="LEFT,RIGHT,CENTER,CENTER,CENTER,LEFT,LEFT" />
									<PARAM NAME="ShowPopupMenu" VALUE="0" />
									<PARAM NAME="Border" VALUE="0" />
							  </OBJECT>
			                </td>
			              </tr>
			            </table>
			          </td>
			        </tr>
			      </table>
			    </td>
			  </tr>
			  <tr height='35' valign=middle style='display:none'>
			    <td>
			      <!-- set download path begin -->
			      다운로드 경로 : <input type="text" name="download_path" size="30" readonly class="readonly">
			      <img src="../images/edms/chgFolder.gif" class="input_image" onclick="ChangeFolder();">
			      <img src="../images/edms/openFolder.gif" class="input_image" onclick="OpenFolder();">
			      <!-- set download path end -->
			    </td>
			  </tr>
			</table>
		</td>
	</tr>
</table>
</form>
</body>
</html>