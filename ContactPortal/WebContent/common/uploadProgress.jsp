<%@ page import="com.wms.fw.servlet.MultipartData"%>
<%@ page import="com.wms.fw.Logger"%>
<%

		try {
			MultipartData data=(MultipartData)session.getAttribute("multi_data");
   			request.setCharacterEncoding("UTF-8");				       
			long totalSize=0;
			long bytesRead=0;
			boolean isEnd=false;
			if(data!=null){
				totalSize = data.getTotalSize();
				bytesRead = data.getBytesRead();
				isEnd     = data.isEnd();
			}
			response.setContentType("text/xml;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");	        	
			PrintWriter wout = response.getWriter();	            	            
			wout.println("<response>");
			wout.println("<TOTALSIZE>" + totalSize + "</TOTALSIZE>");
			wout.println("<BYTESREAD>" + bytesRead + "</BYTESREAD>");
			wout.println("<ISEND>" + isEnd + "</ISEND>");
			wout.println("</response>");
			wout.close();
																				
		} catch(Exception e) {
			//Logger.err.println(com.hist.haf.Utility.getStackTrace(e)); 
			e.printStackTrace();						
		}
		


%>
