<!--
	파 일 명  : comXmlSet.jsp
	작성일자 : 2007/06/04
	작 성 자  : mailbest
	내    용   : xml parameter처리
-->  
<%@ page contentType="text/xml;charset=UTF-8" errorPage="/common/error.jsp"%>
<%@ page import="com.wms.common.beans.dto.DataSet"%>
<%@ page import="com.wms.fw.util.DateUtil"%>
<%@ page import="com.wms.fw.Utility"%>
<%@ page import="com.wms.fw.servlet.HttpUtility"%>
<%@ page import="com.wms.fw.servlet.Box"%>
<jsp:useBean id="user" class="com.wms.beans.dto.UserInfo" scope="session" />
<%
	PrintWriter wout = null;
	request.setCharacterEncoding("ksc5601");
	Box box = HttpUtility.getBox(request);
	String $tc = box.get("$tc");//total cnt;
	int tc = Integer.parseInt($tc);
	String[] entityNames = new String[tc];
	String[] parameters = null;
	DataSet[] set = new DataSet[tc];
	int lengs = 0;
	for(int i=0;i<tc;i++){
		entityNames[i]= box.get("entity"+i);
		lengs = Integer.parseInt(box.get("$leng"+i));
		if (entityNames[i].equals("Combo")){
			parameters= new String[lengs];
		}else{
			parameters= new String[lengs*2];
		} 
		
		request.setAttribute("fileName",box.get("fileName"+i));
		request.setAttribute("idx",box.get("idx"+i));
		request.setAttribute("parameters",parameters);
		
		if (entityNames[i].equals("Combo")){
			for(int j=0;j<lengs;j++){
				parameters[j]=box.get("p"+i+"_"+j);	
			}
		}else{
		    request.setAttribute("setString","Y");//parameter
			for(int j=0;j<lengs;j++){
				parameters[j*2]="p"+j;
				parameters[(j*2+1)]=box.get("p"+j);
//				System.out.println(j*2+":lengs===========>"+"p"+j);
//				System.out.println((j*2+1)+":lengs===========>"+box.get("p"+j));
			}
		}
%>
		<jsp:include page="/common/comDataSet.jsp" flush="true" />
<%
		set[i]=(DataSet)request.getAttribute("set");
	}
	try{
			//request.setCharacterEncoding("UTF-8");
			response.setContentType("text/xml;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");	        	
			wout = response.getWriter();	
			wout.println("<root>");			
			for(int i=0;i<tc;i++){
				if (set[i]!=null) {
					wout.println(set[i].toXml(entityNames[i]));
				} else {
					response.setStatus(HttpServletResponse.SC_NO_CONTENT);
					break;
				}	
			}
			wout.println("</root>");			
			wout.close();

	}catch(Exception e1){
		e1.printStackTrace();
	}
	finally{
		try{
			if(wout!=null)wout.close();
		}catch(Exception e1){e1.printStackTrace();}
	}
%>

