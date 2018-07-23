<!DOCTYPE html>
<html lang="ko">
<head>
<%@ page contentType="text/html;charset=UTF-8" errorPage="/common/error.jsp"%>
<jsp:include page="/common/head_contents.jsp" flush="true" /></head>
<script type="text/javascript" src="/includes/bootstrap-3.3.2/js/bootstrap-datepicker.min.js"></script>
<link rel="stylesheet" href="/includes/bootstrap-3.3.2/css/bootstrap-datepicker3.min.css" />
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
	
	
	// 쿼리 저장된 파일 위치 현재는 QueryContainer 폴더이다.
	request.setAttribute("fileName", "SampleQuery.xml");
	// XML 파일의 ID
	request.setAttribute("idx", "U_002");
	request.setAttribute("parameters", parameters);
	request.setAttribute("setString","Y"); // 파라메터를 칼럼명으로 세팅
%>

<jsp:include page="/common/comDataSet.jsp" flush="true" />
<%
	DataSet set = (DataSet)request.getAttribute("set");
	int ResLen=(set==null)?0:set.getLength();
%>
<script>
	$(document).ready(function(){
		// 참고 JQuery UI Date Picker는 시간을 지원한다. Bootstrap은 지원 안됨
		$('#hiredate').datepicker({
            format: 'yyyy-mm-dd'
        });
	});
	
	function insert() {
		$.toast('데이터를 저장합니다.');
		
		var frm = document.form1;
		//frm.STARTDT.value = dateSplit(frm.STARTDT.value);
		//frm.ENDDT.value = dateSplit(frm.ENDDT.value);
		//frm._SCREEN.value='/common/saveOk.jsp';
		frm.ename.value="Hello"
		frm.dflag.value='U';
		frm.action='hafSample.do';
		frm.target='_self';
		frm.submit();
		//$("form[name='form1']").ajaxSubmit({url: 'hafSample.do', type: 'post'})
		//alert('저장되었습니다!');
	}
</script>

</script>
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
				<form name="form1" method="POST" onsubmit="return insert()" >
					<input type="hidden" name="_SCREEN"> <input type="hidden"
						name="dflag" value=''>
					<h2>입력 샘플 페이지입니다.</h2>
					<div class="form-group">
					    <label for="exampleInputEmail1">번호</label>
					    <input type="text" class="form-control" name="empno" aria-describedby="" placeholder="">
					 </div>
					<div class="form-group">
					    <label for="ename">이름</label>
					    <input type="text" class="form-control" id="ename" name="ename" aria-describedby="" placeholder="">
					     
					 </div>
					 <div class="form-group">
					    <label for="exampleInputEmail1">직업</label>
					    <!--  <input type="text" class="form-control" id="job" aria-describedby="" placeholder="">-->
					    <select class="form-control" name="job">
					      <option>JOB</option>
					      <option>CLERK</option>
					      <option>ANALYST</option>
					      <option>DEVELOPER</option>
					      <option>PLAYER</option>
					    </select>
					 </div>
					<div class="form-group">
					    <label for="exampleInputEmail1">고용일</label>
					    <input type="text" class="form-control" id="hiredate" name="hiredate" aria-describedby="" placeholder="">
					 </div>
					 <input type="hidden" name="deptno" id="deptno" value="20">
					  <button type="submit" class="btn btn-primary">저장</button>
					</table>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
