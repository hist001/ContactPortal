<%@ page contentType="text/html;charset=EUC-KR" %>
<html>
    <head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7, IE=EmulateIE9"/>
        <title>&nbsp;</title>
        <!--<LINK rel="stylesheet" type="text/css" href="../css/skin04_viewstyle.css">-->
        <LINK rel="stylesheet" type="text/css" href="../css/skinPrint_form.css">
		<script Language="JavaScript" src="/common/link/comm_check.js"></script>
    </head>
	<script language=javascript >		   
		//document.write(unescape('<%=request.getParameter("content") %>'));
	function init(){
		var div = document.all("div");
		if(div != undefined){
			if(div.length == undefined){
				div.style.overflow='visible'
			}else{
				for(var i=0;i<div.length;i++){
					div[i].style.overflow='visible'
				}
			}
		}
		printGo();
		self.close();
	}

	function printGo() {

	factory.printing.header = "";
	factory.printing.footer = "";
	factory.printing.portrait = true;
	factory.printing.leftMargin = 0.0;
	factory.printing.topMargin = 10.0;
	factory.printing.rightMargin = 0.0;
	factory.printing.bottomMargin = 10.0;
	factory.printing.Print(true, window);

	}
	</script>

    <body onload="init()">	
	<object id=factory style="display:none" classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="http://mas.hist.co.kr/common/cab/smsx.cab#Version=6,5,439,12" VIEWASTEXT>    
	</object>	
	

    	<script language=javascript >		   
			var html = "";
			for(var i = 1; i < 10; i++)
			{
				var obj = opener.document.all("printArea" + i);
				if(obj)
				{
					html += obj.innerHTML;
					html += "<br>";

				}
				else
				{
					break;
				}
			}		
			//html=replace(html,"740","700")
			document.write(html);
    	</script>

    </body>
</html>
