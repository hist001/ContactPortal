
<!--
	파 일 명  : selectUpload.jsp
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
<jsp:useBean id="user" class="com.wms.beans.dto.UserInfo" scope="session"/>
<%		Connection con = null;
		Statement stmt = null;
		
		String ui_no   = request.getParameter("ui_no");
		String obj_no  = request.getParameter("obj_no");
		String dir     = request.getParameter("dir");
		
//		System.out.println("selectUpload.jsp dir===>>>"+dir);
		try {			
			/*DB CUD*/
			con = DataBaseUtil.getConnection();
			stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

			/*select*/
			String sql="select * from Com_Files  \n"+
                        "where ui_no='"+ui_no+"' and "+ 
			            "obj_no='"+obj_no+"' "+
                        "order by file_no \n";

//			System.out.println("selectUpload sql ==>/n" + sql);

			ResultSet rs = DataBaseUtil.executeSQL(con,stmt,sql);		
			UploadFilesDTO[] files=(UploadFilesDTO[])DataBaseUtil.moveToEntities("com.wms.common.beans.dto.UploadFilesDTO",rs);
		
//			System.out.println("selectUpload sql ==>/n" + sql);
			request.setAttribute("files",files);
			request.setAttribute("isCall","Y");

			RequestDispatcher dispatcher = application.getRequestDispatcher("/common/fileUpload.jsp?ui_no="+ui_no+"&obj_no="+obj_no+"&dir="+dir);
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
