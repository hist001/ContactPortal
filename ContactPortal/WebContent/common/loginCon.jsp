<%@ page contentType="text/html;charset=ksc5601" errorPage="/common/error.jsp"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.util.ArrayList"%>
<%  request.setCharacterEncoding("ksc5601");%>
<%
    String code = (request.getParameter("code")!=null)?request.getParameter("code")+"%":"%";
    String name = (request.getParameter("name")!=null)?request.getParameter("name")+"%":"%";
    request.setAttribute("returns", com.wms.beans.dao.AllSearch.allSearch("emp", code, name));
%>
<jsp:forward page="/common/loginSearch.jsp"/>