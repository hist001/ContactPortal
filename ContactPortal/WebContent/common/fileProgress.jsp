<!--
	파 일 명  : uploadProgress.jsp
	작성일자  : 2007/06/04
	작 성 자  : 조원호
	내    용  : 파일 진행률 UI
-->
<%@ page contentType="text/html;charset=ksc5601" language="java" %>

<html>
    <head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7, IE=EmulateIE9"/>
        <title>파일 진행률</title>
		<script Language="JavaScript" src="../common/link/upload.js"></script>
		<style type="text/css">
            body { font: 11px Lucida Grande, Verdana, Arial, Helvetica, sans serif; }
            #progressBar { padding-top: 5px; }
            #progressBarBox { width: 340px; height: 20px; border: 1px inset; background: #eee;}
            #progressBarBoxContent { width: 0; height: 20px; border-right: 1px solid #4B0082; background: #483D8B }
        </style>
    </head>
<body onload='init()'>
<center>
        <div id="progressBar" style="display: none;">

            <div id="theMeter">
                <div id="progressBarText"></div>

                <div id="progressBarBox" align=left>
                <div id="progressBarBoxContent" align=left></div>
                </div>
            </div>
        </div>
</center>
</body>
</html>