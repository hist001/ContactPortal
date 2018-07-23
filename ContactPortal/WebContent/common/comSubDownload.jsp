<%@ page language="java" contentType="text/html; charset=ksc5601" errorPage="/common/error.jsp"
         import="java.io.*,
		 arisoft.download.AriDownloader,
		 java.net.URLDecoder,
		 com.wms.fw.Utility,
		 com.wms.fw.servlet.HttpUtility,
		 com.wms.fw.servlet.Box" %><%
		 String dir= null;		 
	try{
		//추가되는 부분 시작
		request.setCharacterEncoding("UTF-8");
		Box box = HttpUtility.getBox(request);
		//추가되는 부분 끝		
	  	String fileName = URLDecoder.decode(request.getParameter("fn"),"UTF-8");
	  	
	    File fileDir = new File("C:/upload/"+box.get("PATH"));
	    
	  	File file = new File(fileDir, fileName);
	  	boolean exists =  file.exists();
	  	boolean validPath = file.getCanonicalPath().length() > fileDir.getCanonicalPath().length();
	  	
	  	if(exists && validPath){	  	
		  	AriDownloader aridown = new AriDownloader(request, response);
		  	aridown.downloadFile(file);
	  	}else{
	  		response.sendError(404);
	  	}
%>
<%@ page import="com.wms.common.beans.dto.DataSet"%>
<%@ page import="com.wms.fw.util.DateUtil"%>
<%@ page import="com.wms.fw.Utility"%>
<%@ page import="com.wms.fw.db.DataBaseUtil"%>

<%@ page import="com.wms.common.beans.dto.DataSet"%>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.Statement" %>

<%@ page import="com.wms.fw.Logger" %>
<%@ page import="com.wms.fw.Configuration" %>
<%@ page import="com.wms.fw.db.SQLMapping" %>
<%@ page import="com.wms.fw.db.SQLXmlDAO" %>
<%@ page import="com.wms.edms.beans.dto.edFileDTO"%>
<jsp:useBean id="user" class="com.wms.beans.dto.UserInfo" scope="session"/>
<%
	}catch(Exception e){
		response.sendError(500);
		
	}
%>