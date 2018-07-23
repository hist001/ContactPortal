<!DOCTYPE html>
<html lang="ko">
<head>
<%@ page contentType="text/html;charset=UTF-8" errorPage="/common/error.jsp"%>
<%@ include file="/common/head_contents.jsp" %></head>

<!--  JSP 기본 세트 읽어오기 -->
<%@ page import="com.wms.common.beans.dto.DataSet"%>
<%@ page import="com.wms.fw.util.DateUtil"%>
<%@ page import="com.wms.fw.servlet.HttpUtility"%>
<%@ page import="com.wms.fw.Utility"%>
<%@ page import="com.wms.fw.servlet.Box"%>

<!-- 유저 기본 정보 세트 읽어오기: 세션 Scope -->
<jsp:useBean id="user" class="com.wms.beans.dto.UserInfo" scope="session" />

<!-- 기본 DAO 세팅  -->
<%
	Box box = HttpUtility.getBox(request);
	
	String[] parameters=new String[30];
	parameters[0] ="EMPNO";
	// 샘플임 값을 세팅할 것
	parameters[1] ="79";
	
	// 쿼리 저장된 파일 위치 현재는 QueryContainer 폴더이다.
	request.setAttribute("fileName", "SampleQuery.xml");
	// XML 파일의 ID
	request.setAttribute("idx", "U_001");
	request.setAttribute("parameters", parameters);
	request.setAttribute("setString","Y"); // 파라메터를 칼럼명으로 세팅
%>

<jsp:include page="/common/comDataSet.jsp" flush="true" />
<%
	DataSet set = (DataSet)request.getAttribute("set");
	int ResLen=(set==null)?0:set.getLength();
%>
<body>
 
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
				<h2>읽기 샘플 페이지입니다: 직원번호 79로 시작하는 리스트</h2>
				<table class="table table-striped">
					<thead>
						      <th scope="col">#</th>
						      <th scope="col">번호</th>
						      <th scope="col">이름</th>
						      <th scope="col">직업</th>
						      <th scope="col">날짜</th>
					</thead>
					<tbody>
						<%
							for (int i = 0; i < ResLen; i++) {
						%>
						<tr>
							<th scope="row"><%=i+1%></th>
							<td><%=set.get("EMPNO", i)%></td>
							<td><%=set.get("ENAME", i)%></td>
							<td><%=set.get("JOB", i)%></td>
							<td><%=set.get("HIREDATE", i)%></td>
						</tr>
						<%
							}
						%>
					</tbody>
				</table>
			</div>
		</div>
	</div>

</body>
</html>
