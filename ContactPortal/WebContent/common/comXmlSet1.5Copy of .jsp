<%/*
	�� �� ��  : comXmlSet.jsp
	�ۼ�����  : 2007-06-19
	�� �� ��  : v��ȣ 
	��    ��  : xml�� ����ϴ� ��ü
  */
%>
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
		parameters= new String[lengs]; 
		for(int j=0;j<lengs;j++){
			parameters[j]=box.get("p"+i+"_"+j);

//		System.out.println("parameters============>>" + parameters[0]);
		}
		request.setAttribute("fileName",box.get("fileName"+i));
		request.setAttribute("idx",box.get("idx"+i));
		request.setAttribute("parameters",parameters);
//		System.out.println("fileName============>>" + box.get("fileName"+i));
//		System.out.println("idx============>>" + box.get("idx"+i));

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

