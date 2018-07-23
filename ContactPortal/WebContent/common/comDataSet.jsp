<!--
	파 일 명  : comDataSet.jsp
	작성일자  : 2006/10/18
	작 성 자  : mailbest
	내    용  : DataSet을 이용한 조회
-->
<%@ page contentType="text/html;charset=ksc5601" errorPage="/common/error.jsp"%>
<%@ page import="com.wms.fw.servlet.Box" %>
<%@ page import="com.wms.fw.servlet.HttpUtility" %>
<%@ page import="com.wms.fw.Utility" %>
<%@ page import="com.wms.fw.util.DateUtil"%>
<%@ page import="com.wms.common.beans.dto.DataSet"%>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.util.*" %>
<%@ page import="com.wms.fw.Logger" %>
<%@ page import="com.wms.fw.db.DataBaseUtil" %>
<%@ page import="com.wms.fw.Configuration" %>
<%@ page import="com.wms.fw.db.SQLMapping" %>
<%@ page import="com.wms.fw.db.SQLXmlDAO" %>
<jsp:useBean id="user" class="com.wms.beans.dto.UserInfo" scope="session"/>
<%!String dir= null;%>
<%String fileName      = (String)request.getAttribute("fileName");%>
<%String idx           = (String)request.getAttribute("idx");%>
<%String setString     = (String)request.getAttribute("setString");%>
<%String setReturn     = (String)request.getAttribute("setReturn");%>
<%String[] parameters  = (String[])request.getAttribute("parameters");%>
<%String[] paramSort   = (String[])request.getAttribute("paramSort");%>
<% if(!user.isLogin()) { %>
      <script>location.href = "/common/error.html"; </script>
<%} %>
<%
Connection con = null;
Statement stmt=null;
ResultSet rs = null;
DataSet  returns =null;

boolean isExec=true;
try{
	if(isExec){
	if(dir==null) dir = (new Configuration()).get("com.wms.fw.sql.dir");		

	HashMap hm      = SQLXmlDAO.loadRequestMappings(dir+"/"+fileName);
	SQLMapping sm 	=   (SQLMapping)hm.get(idx);
	
	String sql 		= "";
	
	if(parameters!=null)
		if(setString!="Y"||setString==null){ //변수명을 지정해서 처리
			for(int i=0;i<parameters.length;i++)sm.setString(i+1,parameters[i]);

			  sql = sm.toSql();

		}else{                               //변수를 배열로 처리 [기본]
			for(int i=0;i<parameters.length;i+=2){
				sm.setStringParam(parameters[i],parameters[i+1]);
				
			};
			  sql=sm.paramToString();
		}
	
	if(paramSort!=null&&!paramSort[0].equals("")){
		sql = "select * from ("+sql+")  order by " + paramSort[0];
		if (!paramSort[1].equals("0"))	{
			sql =	sql + " DESC";		
		}
	}

	System.out.println("Sql ===> " +idx + "\n" + sql);
	
	con = DataBaseUtil.getConnection();
	stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
	//조회처리
	if(setReturn!="N"||setReturn==null){
		rs = DataBaseUtil.executeSQL(con,stmt,sql);		
		returns=DataBaseUtil.moveToSet(rs);
	//등록/삭제/수정 처리
	}else{
		stmt.execute(sql);
		con.commit();
	}
   	
	}
	//com.wms.fw.Utility.fixNullAndTrimAll(returns);
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
request.setAttribute("set",returns);
%>	

