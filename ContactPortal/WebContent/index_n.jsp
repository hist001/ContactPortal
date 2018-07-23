<%@ page contentType="text/html;charset=UTF-8" errorPage="/common/error.jsp"%>
<%@ page import="com.wms.fw.util.DateUtil,com.wms.fw.Utility"%>
<jsp:useBean id="user" class="com.wms.beans.dto.UserInfo" scope="session"/>
<%
String[] week=DateUtil.getWeekDay("월",DateUtil.getTodayString("-"),0);
String startDt=Utility.replace(week[0],"-");
String endDt=Utility.replace(week[1],"-");

System.out.println("_"+user.empId);
System.out.println("_"+user.empKName);
System.out.println("_"+user.empOrgNo);
System.out.println("_"+user.empOrgName);
System.out.println("_"+user.role);
System.out.println("_drType::"+user.drType);
System.out.println("_wEndDt::"+user.wEndDt);

	HttpServletRequest m_req;
	String LoginFlag="";

	if (Integer.parseInt(user.wEndDt) < Integer.parseInt(DateUtil.getTodayString())){
		System.out.println("퇴직자::"+user.wEndDt);
		LoginFlag="false";
		response.sendRedirect("Index.html"); 
	 }
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/common/head_contents.jsp" %></head>
 
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">스케줄링 포털</a>
        </div>
        <div class="collapse navbar-collapse" id="myNavbar">
            <ul class="nav navbar-nav" id="mainmenus">
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#" id="logout"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
            </ul>
        </div>
    </div>
</nav> 
<div class="container-fluid">
		<div class="row content" style="height: 100%">
			<div class="col-md-2 sidenav">
				<%@ include file="/common/userStatus.jsp"%></head>
			</div>
			<!-- 본문이 삽입되는 위치이다 -->
			<div class="col-md-9 text-left">
				<h2>샘플 페이지입니다.</h2>
			</div>
		</div>
	</div>

</body>
</html>
