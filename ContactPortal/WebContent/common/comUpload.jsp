<!--
	파 일 명  : comUpload.jsp
	작성일자  : 2007/06/04
	작 성 자  : 조원호
	내    용  : 공통 파일 업로드, CRUD
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
		String ui_no   = null;
		String obj_no  = null;
		String dir     = null;
		try {			
			
			//디렉토리 미정일 경우 /upload/common	
			if (request.getParameter("dir").equals("")||request.getParameter("dir").equals("undefined")||request.getParameter("dir").equals("null")) {				
				dir="C:/upload/common";
			}else{
				dir = "C:/upload/"+ request.getParameter("dir");
			}
			
			MultipartData data=new MultipartData();
			session.setAttribute("multi_data",data);
			data.setPrefixName(System.currentTimeMillis()+"");
			data.setMultipartData(request, dir,1024*1024*10);					
			Box box=data.getBox();	

			UploadFilesDTO[] files=(UploadFilesDTO[])box.copyToEntities("com.wms.common.beans.dto.UploadFilesDTO","file_no",null);	
			
			ui_no=files[0].ui_no;
			obj_no=files[0].obj_no;
//			dir=files[0].dir;

			Hashtable columnList=new Hashtable();			
			columnList.put("createDtm","");			

			LinkedList sqlList=new LinkedList();
					
			for(int i=0;i<files.length;i++){	
				System.out.println(files[i]);
				if(files[i].div.equals("I")){
					files[i].dir=dir.replaceAll("c:/","");//"/upload/common";
					files[i].delFlag="N";
					files[i].createDtm="Sysdate";
					files[i].crEmpId=user.empId;
					files[i].div=null;					
					System.out.println(files[i]);
					sqlList.add(MakeQueryHelper.makeInsertQuery(files[i],columnList,"Com_Files"));
				}else if(files[i].div.equals("U")){
					if(files[i].delFlag.equals("Y")){
						sqlList.add(
							"update Com_Files set delFlag='Y' "+
							"where ui_no='"+files[i].ui_no+"' and "+ 
							"obj_no='"+files[i].obj_no+"'     and "+
							"file_no='"+files[i].file_no+"'        ");
					}
				}
			}
		

			/*DB CUD*/
			con = DataBaseUtil.getConnection();
			con.setAutoCommit(false);
			stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			for(int i=0;i<sqlList.size();i++){				
				stmt.addBatch((String)sqlList.get(i));
				System.out.println(sqlList.get(i));
			}			
			stmt.executeBatch();
			con.commit();
			
			dir = request.getParameter("dir");
			
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
						RequestDispatcher dispatcher = application.getRequestDispatcher("/common/comSelectUpload.jsp?ui_no="+ui_no+"&obj_no="+obj_no+"&dir="+dir);
						dispatcher.forward(request, response);
					}catch(Exception e){
						Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
					}
				}
%>