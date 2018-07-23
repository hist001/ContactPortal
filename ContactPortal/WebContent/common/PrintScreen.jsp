<%@ page contentType="text/html;charset=EUC-KR" %>
<html>
    <head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7, IE=EmulateIE9"/>
        <title>&nbsp;</title>
        <!--<LINK rel="stylesheet" type="text/css" href="../css/skin04_viewstyle.css">-->
        <LINK rel="stylesheet" type="text/css" href="../css/skinPrint_form.css">
    </head>
	<script language=javascript >		   
		//document.write(unescape('<%=request.getParameter("content") %>'));
	function init(){
		printGo();
		self.close();
	}

	function printGo() {

	factory.printing.header = "";
	factory.printing.footer = "";
	factory.printing.portrait = true;
	factory.printing.leftMargin = 5.0;
	factory.printing.topMargin = 15.0;
	factory.printing.rightMargin = 5.0;
	factory.printing.bottomMargin = 15.0;
	factory.printing.Print(true, window);

	}
	</script>

    <body onload="init()">	
	<object id=factory style="display:none" classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="http://mas.hist.co.kr/common/cab/smsx.cab#Version=6,5,439,12" VIEWASTEXT>
	</object>	
    	<script language=javascript >		   
    		document.write(unescape('<%=request.getParameter("content") %>'));
    	</script>
    </body>
</html>
