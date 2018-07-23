<!--
	파 일 명  : comRefileAdd.jsp
	작성일자  : 2009/01/16
	작 성 자  : 최성식(test)
	내     용  : 파일 첨부 UI
-->
<%@ page contentType="text/html;charset=ksc5601" errorPage="/common/error.jsp"%>

<%@ page import="com.wms.common.beans.dto.DataSet"%>
<%@ page import="com.wms.fw.util.DateUtil"%>
<%@ page import="com.wms.fw.Utility"%>
<%@ page import="com.wms.fw.servlet.HttpUtility"%>
<%@ page import="com.wms.fw.servlet.Box"%>
<%@ page import="java.text.NumberFormat"%>
<%@ page import="java.math.BigDecimal"%>

<jsp:useBean id="user" class="com.wms.beans.dto.UserInfo" scope="session"/>
<%
	request.setCharacterEncoding("ksc5601");
	Box box = HttpUtility.getBox(request);
	

	String currentDate = DateUtil.getTodayString("-");

	String empId = request.getParameter("empId");
	if(empId==null){empId=user.empId;}
	
	String[] parameters=new String[10];
	
	//Sort기능 제공
	String[] paramSort= new String[3]; 
	request.setAttribute("paramSort",paramSort);	
	paramSort[0]= box.get("paramSortTmp");
	paramSort[1]= box.get("paramSortAsc");
	paramSort[2]= box.get("paramSortIdx");	
	
	request.setAttribute("fileName","project.xml");
	request.setAttribute("idx","S_038");
	request.setAttribute("parameters",parameters);
	request.setAttribute("setString","Y");//parameter을 컬럼명으로 지정할때 사용
	
	System.out.println("rPJT_no : "+ box.get("rpjt_no"));
	parameters[0] = "rpjt_no"; 
	parameters[1] = box.get("rpjt_no");

%>
<jsp:include page="/common/comDataSet.jsp" flush="true" />
<%
	DataSet set=(DataSet)request.getAttribute("set");
%>
<%
	int idx=0;
	int idx_pjt=0;
	System.out.println("set : " +set);
	if (set!=null) idx=set.getLength("PJTFILES_NO");
%>
<script language='javascript'>
	//var rfileIdx=0; //FILE NO

	function openHanwayDoc()
	{
		//_iWidth=830;iHeight=540;sSize="fix"
		//http://spstest2.hist.co.kr/COVINet/COVIFlowNet/popupListitemsForInterface.aspx?location=RELDOCLINK
		//alert("openHanwayDoc");
		document.domain = 'hist.co.kr';
		//document.domain='test.com';
		openWinFix('http://hanway.hist.co.kr/COVINet/COVIFlowNet/popupListitemsForInterface.aspx?location=RELDOCLINK', 'refile', '830', '540');
	}
	
	function addHanwayFiles(rdoc)
	{
		//alert("+++++++++++++++++=");
		var html = '';
		var chkbx = rdoc.all['chkID'];
		for(var i=0;i<chkbx.length;i++)
		{
			if(chkbx[i].checked == true)
			{
				var xmlContent = chkbx[i].value;
				var adoc = xmlContent.split("@@@");
				
			    if(adoc[1].indexOf("|:|")>0){
	
	                var aForm = adoc[1].split("|:|");
	
	            }else{
	
	            	adoc[1]=adoc[1].replace("&amp;","AMPERSAND").replace("&quot;","DOUBLE_QUOTE");
	                adoc[1]=adoc[1].replace("&amp;","AMPERSAND").replace("&quot;","DOUBLE_QUOTE");  //hjy
	
	                var aForm = adoc[1].split(";");
	                aForm[0]=aForm[0].replace("AMPERSAND","&amp;").replace("DOUBLE_QUOTE","&quot;");
	                aForm[1]=aForm[1].replace("AMPERSAND","&amp;").replace("DOUBLE_QUOTE","&quot;");
	                aForm[2]=aForm[2].replace("AMPERSAND","&amp;").replace("DOUBLE_QUOTE","&quot;");
	            }
				
				var objXML = new ActiveXObject("MSXML.DOMDocument");
	
	            objXML.loadXML(aForm[0]);
	            var pibd1 = aForm[0];
	            var piid = aForm[1];
	            var bstate = aForm[2];
	            var fmid = objXML.selectSingleNode("ClientAppInfo/App/forminfos/forminfo").getAttribute('id');
	            var fmnm = objXML.selectSingleNode("ClientAppInfo/App/forminfos/forminfo").getAttribute('name');
	            var fmpf = objXML.selectSingleNode("ClientAppInfo/App/forminfos/forminfo").getAttribute('prefix');
	            var fmrv = objXML.selectSingleNode("ClientAppInfo/App/forminfos/forminfo").getAttribute('revision');
	            var scid = objXML.selectSingleNode("ClientAppInfo/App/forminfos/forminfo").getAttribute('schemaid');
	            var fiid = objXML.selectSingleNode("ClientAppInfo/App/forminfos/forminfo").getAttribute('instanceid');
	            var fmfn = objXML.selectSingleNode("ClientAppInfo/App/forminfos/forminfo").getAttribute('filename');
	            var fmsb = objXML.selectSingleNode("ClientAppInfo/App/forminfos/forminfo").getAttribute('subject');
	            
	            //alert("fmnm : " + fmnm);
	            //alert("fmfn : " + fmfn);
	            //alert("fmsb : "+fmsb);
	            //alert("piid : "+piid);
	            //alert("fiid : "+fiid);
				
				
				html =  html + "<div id='rfileDiv"+fileIdx+"'  style='width:600;'> "+
							"<input type='hidden' name='pjtFiles_no'  value='"+fileIdx+"'>"+
							"<input type='hidden' name='delFlag' value='N'>"+
							"<input type='hidden' name='docType' value='H'>"+
							"<input type=text name=fileOriginName width='600' value='"+fmsb+"' readonly>&nbsp"+
							"<input type=hidden name=fileSystemName value="+piid+','+fiid +" >&nbsp"+
							"<input type='hidden' name='pjtFiles_div' value='I'>"+
							"<input type='button' value='삭제'  onclick=removeRFile(rfileDiv"+fileIdx+")>"+
							"</div>";
				fileIdx++;
			}
			
		}
		//alert("document.domain : " + document.domain);
		
		//alert("document.domain : " + document.domain);
		rFileSpan.insertAdjacentHTML('afterBegin',html);
		html = '';

	}

	
	function removeRFile(rfileDiv){		
		rfileDiv.outerHTML=""; 
	}
	
	function updateFiles_Pjt(obj,fileDiv){	
	
		var delFlag=fileDiv.all["delFlag"];
		var pjtFiles_div=fileDiv.all["pjtFiles_div"];
		if(obj.checked==true){
			delFlag.value='Y';
			pjtFiles_div.value='D'
		}else{
			delFlag.value='N';
			pjtFiles_div.value='C';
		}
	}
	
	function openHanwayView(piid,fiid){

		//https://hanway.hist.co.kr/COVINet/COVIFlowNet/Forms/formlink.aspx?piid=&fiid=
		//https://spstest2.hist.co.kr/COVINet/COVIFlowNet/Forms/formlink.aspx?piid=&fiid=
		openWinFix('http://hanway.hist.co.kr/COVINet/COVIFlowNet/Forms/formlink.aspx?piid='+piid+'&fiid='+fiid, 'refile', '780', '700');
	}

</script>
<xml id="xmlDoc2"></xml>
<table align=center cellpadding=1 cellspacing=1 border=0 class=table1 width=100%  height="100%" id="table1">
	<tr>
		<td width="130" class='td1' align='center' height="24">
		<xml id=xmlDoc2></xml>
		관련문서
		</td>
		<td width="720" class='td2' align='left' height="24" >
		<span id='rFileSpan' style="width:720;height=80;overflow-y:scroll">
		<%int seqence_ridx=1; %>
		<%if(set!=null){
			for(int i=0;i<idx;i++){	
				System.out.println("DOCTYPE : " +set.get("DOCTYPE",i));
				if(set.get("DELFLAG",i).equals("N")&& set.get("DOCTYPE",i).equals("H")){%>

					<div id='rfileDiv<%=set.get("PJTFILES_NO",i)%>'>&nbsp;<%=seqence_ridx++%>&nbsp;
					<input type='hidden' name='pjtFiles_no'  value='<%=set.get("PJTFILES_NO",i)%>'>
					<input type='hidden' name='pjtFiles_div' value='C'>
					<input type='hidden' name='delFlag' value='N'>
					<input type='hidden' name='file<%=set.get("PJTFILES_NO",i)%>' >
					<input type='hidden' name='docType' value='<%=set.get("DOCTYPE",i)%>'>
					<a href="javascript:openHanwayView('<%=set.get("PIID",i)%>','<%=set.get("FIID",i)%>')";>
						<%=set.get("FILEORIGINNAME",i)%></a>		
					<input type='checkbox' style='border:0' onclick=updateFiles_Pjt(this,rfileDiv<%=set.get("PJTFILES_NO",i)%>) >(삭제)
					</div>
				
				<%} %>
			<%}%>
			
		<%}%>
        </span>
		</td>
		<td width="130" class='td2' align='center' height="24" valign=top >
		<input type=button onclick='openHanwayDoc()'  value='+추가'>
		</td>
	</tr>	
</table>
