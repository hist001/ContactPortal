<%@ page contentType="text/html;charset=ksc5601"%>
<jsp:useBean id="movePage" class="java.lang.String" scope="request"/>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7, IE=EmulateIE9"/>
<title>처리가 완료되었습니다.</title>
<link type="text/css" rel=stylesheet href="/css/sample.css">
<script Language="JavaScript" src="/common/link/common.js"></script>
<script language="javascript">
function pageMove(){
<jsp:include page='/common/hidden.jsp' flush='true'/>
	location.href='<%=movePage%>';
}
</script>
</head>
<form name='hidden'></form>
<body onload="javascript:pageMove();">
<table width=800 align="center">
  <tr>
    <td width=2%>&nbsp;</td>
    <tr>
	  <td width=96%>
        <table width=100% border=0 cellpadding=1 cellspacing=0 align="center">
          <tr>
            <td class=back>
              <table width=100% border=0 cellpadding=1 cellspacing=1 align="center">
		        <tr align="center">
			      <td class=list>
                  </td>
				</tr>
		     </table>
          </td>
        </tr>
      </table>  
    </td>
  </tr>
  <tr>
    <td width=2%>&nbsp;</td>
  </tr>
</table>
</body>
</html>