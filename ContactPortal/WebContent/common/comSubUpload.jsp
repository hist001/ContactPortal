<!--***************************************
	파 일 명  : comSubUpload.jsp
	작성일자  : 2010-03-19
	작 성 자  : mailbest
	내    용  : ARI UPLOAD
*******************************************-->
<%@ page contentType="text/html;charset=euc-kr"%>
<%@ page import="com.wms.common.beans.dto.DataSet"%>
<%@ page import="com.wms.fw.servlet.Box" %>
<%@ page import="com.wms.fw.servlet.HttpUtility" %>
<%@ page import="com.wms.fw.Utility" %>
<%@ page import="com.wms.fw.util.DateUtil"%>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="com.wms.fw.Logger" %>
<%@ page import="com.wms.fw.db.DataBaseUtil"%>
<%@ page import="com.wms.fw.Configuration" %>
<%@ page import="com.wms.fw.db.SQLMapping" %>
<%@ page import="com.wms.fw.db.SQLXmlDAO" %>
<jsp:useBean id="user" class="com.wms.beans.dto.UserInfo" scope="session"/>
<%@ page import="java.util.Iterator,
				arisoft.upload.FileEntity,
				arisoft.upload.FieldEntity,
				arisoft.upload.AriUploader,
				arisoft.upload.DateTimeFileRenamePolicy,
				arisoft.upload.FileUploadException,
				java.io.File,
				java.io.FileInputStream" %>
<%!String dir= null;%>				
<%
	request.setCharacterEncoding("UTF-8");
	Box box = HttpUtility.getBox(request);
    
	// 만약 업로드 디렉터리가 없으면 만든다. (실제 업로드된 파일을 저장할 디렉터리로 바꾸세요)
    File fileDir = new File("C:/upload/"+box.get("PATH"));
	if(!fileDir.exists()) fileDir.mkdirs();  // 업로드 디렉터리가 없으면 만든다
	
	 boolean bHasExt = true;
     DateTimeFileRenamePolicy dtPolicy = new DateTimeFileRenamePolicy(bHasExt);
  	// 아리업로더 객체 생성
	AriUploader ari = new AriUploader(request, fileDir.getAbsolutePath(), Integer.MAX_VALUE,dtPolicy);
	
  	ari.setLicense("43b0bbc7bc8a79fd186fae8dd6d7d00c2fafc1492a61524242962001fcbcd75268cefcc494d648200cef181c73fd239240fd2534c39474009712726b1c8962a387818be40fe30ec033744911fff379a3b232f3a85e46b58f8fd90afea02d2b2a8fd90afea02d2b2a8fd90afea02d2b2a8fd90afea02d2b2a8fd90afea02d2b2a8fd90afea02d2b2a8fd90afea02d2b2a8fd90afea02d2b2a8fd90afea02d2b2a");
//  	System.out.println("bHasExt ===>"+ bHasExt);
//  	System.out.println("comSubUpload ===>"+ dtPolicy);
  	ari.upload();
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7, IE=EmulateIE9"/>
<title></title>
</head>
<body onload="">
<form name="frmUpload" method="POST" action="">
<table border="0" cellpadding="1" cellspacing="1" bgcolor="silver">
  <caption align="left"><h4>[File Data]</h4></caption>
  <tr bgcolor="#EEEEEE">
    <th width="80">Form Name</th>
    <th width="120">Original Name</th>
    <th width="120">Sub Folder</th>
    <th>File Path</th>
    <th>Ext Value</th>
  </tr>
<%
 /**
  * Get File Data
  */
  Iterator files = ari.getFileNames();
  while (files.hasNext()) {
    String formName = (String)files.next();
    FileEntity entity = ari.getFileEntity(formName);
    File f = ari.getFile(formName);
    String orginFileName = f.getName();
    String subDir = entity.getSubFolder();
    
    String fileName = entity.getFileName();
    String orgFileName = entity.getOriginalFileName();
    
//  System.out.println("formName=====>" +fileName);
//	System.out.println("orginFileName=====>" +orgFileName);
//	System.out.println("subDir=====>" +fileDir.getAbsolutePath());
//	System.out.println("entity=====>" +entity);
//	System.out.println("f=====>" +f);
//	System.out.println("comSubUpload.jsp ::: user.empId=====>" +user.empId);

/*DB 자료 등록=======================================================================*/
	String[] parameters=new String[20];
    parameters[0]="p0"; 
  	parameters[1]= box.get("UI_NO");
  	parameters[2]="p1"; 
  	parameters[3]= box.get("DIR");      
  	parameters[4]="p2"; 
  	parameters[5]= orgFileName;      
  	parameters[6]="p3"; 
  	parameters[7]= box.get("userId");      
  	parameters[8]="p4"; 
  	parameters[9]= fileName;      
  	parameters[10]="p5"; 
  	parameters[11]= "/upload/"+box.get("PATH");      

	Connection con = null;
	Statement stmt=null;
	ResultSet rs = null;
	try{
		if(dir==null) dir = (new Configuration()).get("com.wms.fw.sql.dir");		
		HashMap hm      = SQLXmlDAO.loadRequestMappings(dir+"\\"+"Common.xml");
		SQLMapping sm 	=   (SQLMapping)hm.get("COM_011");
		String sql 		= "";

		//변수를 배열로 처리 [기본]
		for(int i=0;i<parameters.length;i+=2){
			sm.setStringParam(parameters[i],parameters[i+1]);
		};

		sql=sm.paramToString();
		
		//System.out.println("COM_011 ===> " + sql);
		con = DataBaseUtil.getConnection();
		stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		rs = DataBaseUtil.executeSQL(con,stmt,sql);		
	}catch(Exception e){
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
	}
	finally{
		try{
			if(rs!=null)rs.close();
			if(stmt!=null)stmt.close();
			if(con!=null)con.close();
		}catch(Exception e){
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
		}
	}
/*=======================================================================*/
%>
<script language="javascript">
	alert(fileName+'/'+orgFileName);
</script>
  <tr bgcolor="#FFFFFF">
    <td><%=formName%></td>
    <td><input type='text' name='DIR' value='<%=box.get("DIR")%>'></td>
    <td><input type='text' name='DOC_DIV' value='<%=box.get("DOC_DIV")%>'></td>
    <td><input type='text' name='FL_NO' value='<%=box.get("FL_NO")%>'></td>
    <td><input type='text' name='FILENAME' value='<%=orginFileName%>'></td>
    <td><%=subDir%></td>
    <td><%=f.getAbsolutePath()%></td>
    <td><%=entity.getValueExt("ext1")%></td>
  </tr>
<%
  }
%>
</table><br>
<%
 /**
  * Process Delete Files
  */
  String delFiles[] = ari.getParameterValues("delFiles");
  if(delFiles!=null){
%>
<table border="0" cellpadding="1" cellspacing="1" bgcolor="silver">
  <caption align="left"><h4>[Deleted Files]</h4></caption>
  <tr bgcolor="#EEEEEE">
    <th width="80">File Name</th>
    <th width="120">State</th>
  </tr>
<%
    for(int i=0; i<delFiles.length; i++){
      File delFile = new File(fileDir, delFiles[i]);
%>
  <tr bgcolor="#FFFFFF">
    <td><%=delFiles[i]%></td>
    <td><%=(delFile.delete())%></td>
  </tr>
<%
//  	System.out.println("**************************************DELETE : "+delFiles[i]);
    }
%>
</table><br>
<%
  }
  
%>
<input type="button" value="?????" onclick="location.replace('listform.jsp');">
</form>

</body>
</html>