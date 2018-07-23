
<!--
	파 일 명  : comSelectUpload.jsp   
	작성일자  : 2007/06/04
	작 성 자  : 조원호
	내    용  : 공통 파일 업로드 Read
-->
<%@ page contentType="text/html;charset=ksc5601" errorPage="/common/error.jsp"%>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="com.wms.fw.servlet.Box"%>
<%@ page import="com.wms.fw.Logger"%>
<%@ page import="com.wms.fw.servlet.MultipartData"%>
<%@ page import="com.wms.common.beans.dto.UploadFilesDTO"%>
<%@ page import="com.wms.beans.MakeQueryHelper"%>
<%@ page import="com.wms.fw.db.DataBaseUtil"%>
<%@ page import="com.wms.fw.servlet.HttpUtility" %>
<jsp:useBean id="user" class="com.wms.beans.dto.UserInfo" scope="session"/>
<%		
		request.setCharacterEncoding("ksc5601");
		Box box = HttpUtility.getBox(request);

		Connection con = null;
		Statement stmt = null;
		
		String ui_no   = box.get("ui_no");
		String obj_no  = box.get("obj_no");
		String dir     = box.get("dir");
		String viewType= box.get("view");
		
		RequestDispatcher dispatcher=null;
		try {			
			/*DB CUD*/
			con = DataBaseUtil.getConnection();
			stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

			/*select*/
			String sql="select * from Com_Files  \n"+
                        "where ui_no='"+ui_no+"' and "+ 
			            "obj_no='"+obj_no+"'";
			if(!("").equals(dir)){
				sql = sql + " and DIR like '%'||'"+ dir +"'";	
			}
			
			sql = sql + "and delflag = 'N' order by file_no \n";

// 			System.out.println(sql);

			ResultSet rs = DataBaseUtil.executeSQL(con,stmt,sql);		
			UploadFilesDTO[] files=(UploadFilesDTO[])DataBaseUtil.moveToEntities("com.wms.common.beans.dto.UploadFilesDTO",rs);

			request.setAttribute("files",files);
			request.setAttribute("isCall","Y");

//			System.out.println(viewType);
//			System.out.println("/common/comfileUpload.jsp?ui_no="+ui_no+"&obj_no="+obj_no+"&dir="+dir);
    
			if (viewType==null||viewType.equals("")){
				dispatcher = application.getRequestDispatcher("/common/comfileUpload.jsp?ui_no="+ui_no+"&obj_no="+obj_no+"&dir="+dir);
			}else{
				dispatcher = application.getRequestDispatcher("/common/comfileView.jsp?ui_no="+ui_no+"&obj_no="+obj_no+"&dir="+dir);
			}
			dispatcher.forward(request, response);

												
		} catch(Exception e) {
			if(con!=null)
				con.rollback();
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
			e.printStackTrace();						
		}
		finally{
					try{
						if(stmt!=null)stmt.close();
						if(con!=null){
							con.setAutoCommit(true);
							con.close();
						}
					}catch(Exception e){
						Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
					}
				}
%>
