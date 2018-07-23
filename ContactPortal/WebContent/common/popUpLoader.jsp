<!--
	파 일 명  : popUpLoader.jsp
	작성일자  : 2006/11/20
	작 성 자  : mailbest
	내    용  : 협업메뉴에서 바로 팝업창 띄우기
-->
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7, IE=EmulateIE9"/>
<title></title>
<script language="javascript" src="/common/link/common.js"></script>
</head>
<%String screenParam = request.getParameter("_SCREEN");%>
<script language="Javascript">
function init(){
	wObj ='<%=request.getParameter("width")%>' ;
	hObj ='<%=request.getParameter("height")%>' ; 
	if(wObj=='null'){
		openFullWin('<%=screenParam%>',"");
	}else{
	    //openWinSet(wObj,hObj);
	}
		openFullWin('<%=screenParam%>',"");
}		
function openWinSet(width,height){
	openWinFix('<%=screenParam%>','',width,height);
}		
</script>		
<body onload="init()">
</body>
</html>