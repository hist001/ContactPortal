var addBtnFlg = false;
	var rowIdx = 0;
function addRptDiv(pjt_drType,job_drType,org_drType){

				
		var html =
		"<span id = rptDiv"+rptIdx+">"+
		"<table border=0><tr height=0><td></td></tr></table>"+
		"<table cellpadding=1 cellspacing=1 border=0 class=table1 width=100%>"+
			"<tr>"+
				"<td width=5% class=TD2 align=center style='background-color: #F5F5F5'>"+	
					(rptIdx+1)+"<br><br>"+
					"<input type=radio id=rdoDir"+rptIdx+" name=rdoDir"+rptIdx+" value='D' class=Attach checked onclick=drtRpt(rptSpan"+rptIdx+",'"+pjt_drType+"','"+job_drType+"','"+org_drType+"')>직접"+
					"<br>"+
					"<input type=radio id=rdoDir"+rptIdx+" name=rdoDir"+rptIdx+" value='I' class=Attach onclick=indrtRpt(rptSpan"+rptIdx+","+rptIdx+",'"+pjt_drType+"','"+job_drType+"','"+org_drType+"')>간접"+
					"<br>"+
				"</td>"+
				"<td width=90% class=TD2  style='background-color: #F5F5F5' align=center>"+	
				"<span align=left   name=rptSpan"+rptIdx+" id=rptSpan"+rptIdx+">"+																										
				"</span>"+
				"</td>"+		 
				"<td width=5% rowspan=2 class=TD2 align=center>"+		
					" <table border='0' cellpadding='0' cellspacing='0'><tr>"+			
									"<td CLASS=bt_f nowrap></td>"+
									"<td class=button style=cursor:hand; "+
										"onclick=removeDiv(rptDiv"+rptIdx+")>"+
										"<img src='/images/delete.gif' style=vertical-align:middle;width:9 >"+
										"삭제"+
										"</td>"+
									"<td CLASS=bt_e nowrap></td>"+	
					"</tr>" +
							"</table>" +
				"</td>"+		
			   "</tr>"+
		"</table>"+	
		"</span>";

		rptBody.insertAdjacentHTML('beforeEnd',html);	
		drtRpt(document.getElementById("rptSpan"+rptIdx),pjt_drType,job_drType,org_drType);
		rptIdx++;
	}
	
	function addRptDivC(pjt_drType,job_drType,org_drType){
		
		if(!addBtnFlg){
			var lastItemNo =  $(".test").filter(":last").attr("id").replace("rowCnt", "");
			rowIdx = Number(lastItemNo)+1;
			addBtnFlg=true;
		}

		rowIdx = Math.round(rowIdx/10) * 10 + 10;
			
		var html =
		"<span id = rptDiv"+rptIdx+">"+
		"<table border=0><tr height=0><td></td></tr></table>"+
		"<table cellpadding=1 cellspacing=1 border=0 class=table1 width=100%>"+
			"<tr>"+
				"<td width=5% class=TD2 align=center style='background-color: #F5F5F5'>"+	
				(rptIdx+1)+"<br><br>"+
					"<input style='display:none' type=radio id=rdoDir"+rptIdx+" name=rdoDir"+rptIdx+" value='D' class=Attach  onclick=drtRpt(rptSpan"+rptIdx+",'"+pjt_drType+"','"+job_drType+"','"+org_drType+"')>"+
					"<br>"+
					"<input style='display:none' type=radio id=rdoDir"+rptIdx+" name=rdoDir"+rptIdx+" value='I' class=Attach checked onclick=indrtRpt(rptSpan"+rptIdx+","+rptIdx+",'"+pjt_drType+"','"+job_drType+"','"+org_drType+"')>"+
					"<br>"+
				"</td>"+
				"<td width=90% class=TD2  style='background-color: #F5F5F5' align=center>"+	
				"<span align=left   name=rptSpan"+rptIdx+" id=rptSpan"+rptIdx+">"+																										
				"</span>"+ 
				"<td width=5% rowspan=2 class=TD2 align=center>"+		
					" <table border='0' cellpadding='0' cellspacing='0'><tr>"+			
									"<td CLASS=bt_f nowrap></td>"+
									"<td class=button style=cursor:hand; "+
										"onclick=removeDiv(rptDiv"+rptIdx+")>"+
										"<img src='/images/delete.gif' style=vertical-align:middle;width:9 >"+
										"삭제"+
										"</td>"+
									"<td CLASS=bt_e nowrap></td>"+	
					"</tr>" +
							"</table>" +
				"</td>"+		
			   "</tr>"+
		"</table>"+	
		"</span>";

		rptBody.insertAdjacentHTML('beforeEnd',html);	
		indrtRpt(document.getElementById("rptSpan"+rptIdx),rptIdx,pjt_drType,job_drType,org_drType);
		//rowIdx++;
		rptIdx++;
	}

	function drtRpt(rptSpan,pjt_drType,job_drType,org_drType){
		//alert("drtRpt:"+pjt_drType);
	rptSpan.innerHTML=
	"<table cellpadding=0 cellspacing=0 border=0 width=100%>"+
			"<tr>"+
				"<td width=66% class=TD2>"+
					"<input type='hidden' id='jobNoId"+rptSpan.id+"'  readonly name='jobNo' 	style='height:24; width : 44%; background-color: #F0F0F0'>"+
					"<input type='text' id='jobView"+rptSpan.id+"' 	  readonly name='jobView'		style='valign:middle;height:24;background-color: #F0F0F0; width:55>&nbsp;"+
					"<input type='text' id='jobNameId"+rptSpan.id+"'  readonly name='jobName'	style='valign:middle;height:24;background-color: #F0F0F0; width:200 '>&nbsp;"+
					"<input type='text' id='prcsNoId"+rptSpan.id+"'   readonly name='prcsNo' 	style='valign:middle;height:24;background-color: #F0F0F0; width:50  ''>&nbsp;"+
					"<input type='text' id='prcsNameId"+rptSpan.id+"' readonly name='prcsName'	style='valign:middle;height:24;background-color: #F0F0F0; width:195 '>&nbsp;"+
					"<img src='/images/search.gif' style='vertical-align:middle;cursor:hand;' onclick=javascript:popupSearchPrcs('pjt_new','pjt_4','jobNoId"+rptSpan.id+"','jobView"+rptSpan.id+"','jobNameId"+rptSpan.id+"','prcsNoId"+rptSpan.id+"','prcsNameId"+rptSpan.id+"',600,750) >"+
					"<input type=hidden name='sn' >"+
				"</td>"+
				"<td width='19%' class=TD2 align=center>"+
					"&nbsp;"+
					"<input type='text' name='startDt' size='10' readonly style='height:24;'> ~ <input type='text' name='endDt' size='10' readonly style='height:24;'></td>"+
				"<td width='15%' class=TD2 align=center>"+							
					"<select size='1' name='hh'>"+
						"<option value='00' >00</option>"+
						"<option value='01' >01</option>"+
						"<option value='02' >02</option>"+
						"<option value='03' >03</option>"+
						"<option value='04' >04</option>"+
						"<option value='05' >05</option>"+
						"<option value='06' >06</option>"+
						"<option value='07' >07</option>"+
						"<option value='08' >08</option>"+
					"</select>"+
					"시간"+
					"<select size='1' name='mm'>"+
						"<option value='00' >00</option>"+
						"<option value='10' >10</option>"+
						"<option value='20' >20</option>"+
						"<option value='30' >30</option>"+
						"<option value='40' >40</option>"+
						"<option value='50' >50</option>"+
					"</select>분"+
				"</td>"+
			"</tr>"+
			"<tr>"+
				"<td width=100% colspan=4 class=TD2>"+
					"<table cellpadding=0 cellspacing=0 border=0 class=table1 width=100%>"+
						"<tr>"+
						"<td class=TD2>"+
						"<input type='text' name='jobTitle' size='20' style='height:24; width :100%; font-weight:700;ime-mode:active;' ></td>"+
						"</tr>"+
						"<tr>"+
						"<td  class=TD2>"+
						"<font size='2'>"+
						"<textarea rows='7' name='jobDetail' style='WIDTH: 100%;ime-mode:active;'>"+
						"</textarea></font>"+
						"<input type=hidden name='delFlag' value='N'>"+
						"<input type=hidden name='transFlag' value='N'>"+
						"<input type=hidden name='actFlag' value='D'>"+
						"</td>"+
						"</tr>"+
					"</table>"+
				"</td>"+
			"</tr>"+			
		"</table>";
									
	}

	function indrtRpt(rptSpan,i,pjt_drType,job_drType,org_drType){
		var bizChag = 'bizChag';
		rptSpan.innerHTML=
	"<table cellpadding=0 cellspacing=0 border=0 width=100%>"+
		"<tr>"+		
			"<td width=50% class=TD2  height=25 >"+
			"<input type=text name='jobTmpNo'   readonly id='jobTmpNo"+rptSpan.id+"' style='vertical-align:middle;height:24;background-color: #F0F0F0; width:59' >&nbsp;"+
			"<input type=text name='jobTmpName' readonly id='jobTmpName"+rptSpan.id+"' style='vertical-align:middle;height:24; width : 320; background-color: #F0F0F0' >&nbsp;"+
			"<img src='/images/search.gif' style='vertical-align:middle;cursor:hand;' onclick=popupSearchN('job_new','job_1','jobTmpNo"+rptSpan.id+"','jobTmpNo"+rptSpan.id+"','jobTmpName"+rptSpan.id+"','','',600,650) >"+
			"</td>"+
			"<td width=50% class=TD2 height=24 valign=top rowspan=2>"+				
				"<table  cellspacing=1 cellpadding=1 border=0  width=100% class=table1>"+
					"<tr>"+
						"<td width='10%' class=td1  height=24   align=center >코드</td>"+
						"<td width=50% class=td1  height=20   align=center >부서(팀·그룹/PJT)</td>"+
						"<td width=30% class=td1  height=20   align=center >작업시간</td>"+
						"<td width=10% class=td1  height=20   align=center >"+
						"<table cellspacing='0' cellpadding='0' border='0'>"+
						"	<tr>"+
						"		<td class='bt_f' nowrap></td>"+
						"		<td class='button' width='' style='cursor:hand;'"+
						"			onMouseOver=''"+
						"			onMouseOut='' onclick=multiPopupSearch('pjt_new','pjt_1','bizSpan"+i+"',600,650,'"+i+"')>추가</td>"+
						"		<td class='bt_e' nowrap></td>"+
						"	</tr>"+
						"</table>	"+
						
						"</td>"+
					"</tr>"+
				"</table>"+											
				"<span align=left   name=bizSpan"+i+" id=bizSpan"+i+">"+															                     
				"</span>"+				
			"</td>"+			
		"</tr>"+
		"<tr>"+		
			"<td width=50%  class=TD2  height=24>"+
				"<input type=hidden name=tmpSn value="+rowIdx+">"+
				"<input type='text' style='vertical-align : middle; height:24; width : 100%;' name='jobTmpTitle' >"+
			"</td>"+				
		"</tr>"+					
		"<tr>"+
			"<td width=50%  class=TD2  height=25>"+
				"<textarea rows=7 name='jobTmpDetail' style='WIDTH: 100%;ime-mode:active;'>"+
				"</textarea>"+
			"</td>"+		
	
		"<td class=TD2 width=100% valign=bottom colspan=4>"+
			"<table cellpadding=0 cellspacing=0 border=0 class=table1 width=100%>"+
				"<tr>"+
					"<td class=TD2 width=65% >"+
						"<input type=text id='chagVName"+bizIdx+"' name='chagVName' size=41 style=width:70%;font-weight:700; height:24;ime-mode:active; value=''>&nbsp;"+
						"<input type=hidden id='vLoc"+bizIdx+"' name='vLoc'  >"+
						"<input type=button id=B2 name=B6 class='base' style ='cursor:hand;' onclick=javascript:search('"+bizChag+"',"+bizIdx+") value=거래처>"+
					"</td>"+
					"<td width=35% class=TD2 align=center>"+
						"<input type=text id='totCount"+bizIdx+"' name='totCount' size='11' style='text-align: right' value='0' readonly> 원&nbsp;"+					
						"<a href=javascript:transRegOpen("+Number(bizIdx+1)+","+bizIdx+"); id=B2 name=B6 class='base' style ='cursor:hand;' >교통비</a>"+
					"</td>"+
				"</tr>"+
			"</table>"
		"</td>"+
		"</tr>"+
		
		"</table>";
	addBiz(document.getElementById("bizSpan"+i),pjt_drType,job_drType,org_drType);
	}

	function addBiz(bizSpan,pjt_drType,job_drType,org_drType){
		if(!addBtnFlg){
			var lastItemNo =  $(".test").filter(":last").attr("id").replace("rowCnt", "");
			rowIdx = Number(lastItemNo)+1;
			addBtnFlg=true;
		}
		var html =	
			"<div id='bizDiv"+bizIdx+"' >" +
			"<table cellpadding=1 cellspacing=1 border=0 width=100% style='border-collapse: collapse;'>" +
			"<tr>" + 
			"<td  class=TD2  height=24  align=left><input type='text' name='prcsNoView'   id='subPrcsNoView"+bizIdx+"'   readonly   style='vertical-align:middle;height:24;background-color: #FFFFFF; width:50' ></td>"+ 
			"<td  class=TD2  height=24  align=left><input type='text' name='prcsName' id='subPrcsName"+bizIdx+"' readonly   style='vertical-align:middle;height:24;background-color: #FFFFFF; width:160' >" +
			"<input type=hidden name='jobNo'>"+
			"<input type=hidden name='jobName'>"+
			"<input type=hidden name='jobTitle'>"+
			"<input type=hidden name='jobDetail'>"+
			"<input type=hidden name='startDt'>"+
			"<input type=hidden name='endDt'>"+
			"<input type=hidden name='sn'>"+
			"<input type=hidden name='delFlag' value='N'>"+
			"<input type=hidden name='transFlag' value='N'>"+
			"<input type=hidden name='actFlag' value='I'>"+ 
			"&nbsp;<img src='/images/search.gif' style='vertical-align:middle;cursor:hand;' onclick=popupSearchN('pjt_new','pjt_1','subPrcsNo"+bizIdx+"','subPrcsNoView"+bizIdx+"','subPrcsName"+bizIdx+"','','',600,650) >"+
			"<input type='hidden' name='prcsNo'   id='subPrcsNo"+bizIdx+"'   readonly   style='vertical-align:middle;height:24;background-color: #FFFFFF; width:50' >"+
			"	</td>"+ 
			"	<td width='30%' class=TD2  height=24 align=center>"+ 
			"		<select name='hh' onchange=''>"+ 
			"			<option value='00'>00</option>"+ 
			"			<option value='01'>01</option>"+ 
			"			<option value='02'>02</option>"+ 
			"			<option value='03'>03</option>"+ 
			"			<option value='04'>04</option>"+ 
			"			<option value='05'>05</option>"+ 
			"			<option value='06'>06</option>"+ 
			"			<option value='07'>07</option>"+ 
			"			<option value='08'>08</option>"+ 
			"		</select>시간"+ 
			"		<select name='mm' onchange=''>"+ 
			"			<option value='00'>00</option>"+ 
			"			<option value='10'>10</option>"+ 
			"			<option value='20'>20</option>"+ 
			"			<option value='30'>30</option>"+ 
			"			<option value='40'>40</option>"+ 
			"			<option value='50'>50</option>"+ 
			"		</select>분"+ 
			"	</td>"+ 
			"	<td width='10%' class=TD2  height=24  align=center>" +
			" <table border='0' cellpadding='0' cellspacing='0'><tr>"+			
					"<td CLASS=bt_f nowrap></td>"+
					"<td class=button style=cursor:hand; "+
						"onclick=removeDiv(bizDiv"+bizIdx+")>" +
						"<img src='/images/delete.gif' style=vertical-align:middle;width:9 >"+
						"삭제"+
						"</td>"+
					"<td CLASS=bt_e nowrap></td>"+	
	"</tr>" +
			"</table>" +
			"</td>"+					
			"</tr>" +
			"</table>" +
			"</div>";

		bizIdx++;
		rowIdx++;
		bizSpan.insertAdjacentHTML('beforeEnd',html);	
		//bizSpan.insertAdjacentHTML('afterBegin',html);	
	}

	function removeDiv(div){
		var sn;
	
		bizIdx--;
		
		if(!addBtnFlg){
			var lastItemNo =  $(".test").filter(":last").attr("id").replace("rowCnt", "");
			rowIdx = Number(lastItemNo)+1;
			addBtnFlg = true;
		}
		
		div.outerHTML="";
		
		if(div.id.substring(0,6) == 'rptDiv'){
			sn = div.id.replace("rptDiv","");
			deleteData(Number(sn));	
		}else{
			sn = div.id.replace("bizDiv","");
		}
		
		
			
	}
	
	function deleteData(sn){
		$.ajax({
			type: "POST",
			url: "transCostSave.do",
			data: "reportDt="+reportDt+"&sn="+sn+"&del=d"
		});
	}
	/*
	*   일일 업무 보고서 등록화면에서 보고자 전환시 함수
	*/
	function trnRpt(){//보고자 선택시 
	    var frm=document.forms[0];
		report=frm.reportViewer.options[frm.reportViewer.selectedIndex].value;

		idx=report.indexOf(" ");
		lastIdx=report.lastIndexOf(" ");
		frm.empId.value=report.substring(0,idx);
		frm.orgCd.value=report.substring(idx+1,lastIdx);
		frm.empKName.value=report.substring(lastIdx+1);
		//alert("empId="+frm.empId.value+":orgCd="+frm.orgCd.value+":empKName="+frm.empKName.value+":");
		frm._ACT.value='RSD';
		frm.submit();

	}	
	function save(statusFlag,delayFlag,delayCntInMonth){
		var frm=document.forms[0];
		var isReg=true;
		var idx=rptBody.children.length;
		var firstNode;
		var secondNode;
		var thirdNode;
		
		var jobNo; 
		var jobName;
		var jobTitle;
		var jobDetail;
		var sn =1;   
		var chaVName;

		if(statusFlag =='Y')   
				alert("지연보고 "+delayCntInMonth+"회차입니다. 4회차일경우 경고장 발부, 8회차 또는 경고장 2회 발부시는 상벌심의위원회에 회부되므로 일일보고에 만전을 기해주시기 바랍니다.");
				
		for(var i=0;i<idx;i++){ 
			//직/간접 체크유무
			firstNode=rptBody.children[i].children[1].children[0].children[0];
			secondNode=firstNode.children[0].children[2];				
			if(!secondNode.checked){			
				secondNode=firstNode.children[1].children[0].children[0].children[0];
				jobNo=secondNode.children[0].children[0].children[0].value;
				jobName=secondNode.children[0].children[0].children[1].value;
				//jobName=jobName.substring(jobName.indexOf(" ")+1);
				
				sn = secondNode.children[1].children[0].children[0].value;
				if(sn.length == 1){
					sn = '0'+sn;
				}
				jobTitle=secondNode.children[1].children[0].children[1].value;
				jobDetail=secondNode.children[2].children[0].children[0].value;
				//chaVName=secondNode.children[2].children[1].children[0].children[0].children[0].children[0].children[0].value;
				
				//간접인력의 Project/사업들
				secondNode=firstNode.children[1].children[0].children[0].children[0].children[0].children[1].children[1];
				
				for(var j=0;j<secondNode.children.length;j++){
					//Project/사업 한건
					thirdNode=secondNode.children[j].children[0].children[0].children[0].children[1];
					thirdNode.children[1].value=jobNo;					
					thirdNode.children[2].value=jobName;					
					thirdNode.children[3].value=jobTitle;					
					thirdNode.children[4].value=jobDetail;		
					thirdNode.children[5].value=currentdate//'  currentdate%>';		
					thirdNode.children[6].value=currentdate//'  currentdate%>';
					if(String(sn).length == 1){
						sn = '0'+sn;
					}
					thirdNode.children[7].value=sn;
					//thirdNode.children[8].value=chaVName;
					//alert("직접 sn::"+thirdNode.children[7].value);
					if(secondNode.children.length >= 2){
						Number(sn++);
					}
					//sn++;
				}
			}else{ 
				secondNode=firstNode.children[1].children[0].children[0].children[0].children[0].children[0].children[6];
				//alert("간접 secondNode.value::"+secondNode.value);
				secondNode.value=sn;	
				//alert("간접else sn::"+secondNode.value);			
				sn++;
			}			
		} 
		 
		frm.statusFlag.value=statusFlag; 
		
		if(statusFlag=='WEO'){
			isReg=rptChk();
		}else if(statusFlag=='WES'){
			isReg=rptChk2();
		}
	    
		if(document.getElementById("foodChk")){ 
			if(document.form1.foodChk.checked==true&&frm.chkFood2.value !='Y'){
		 		frm.foodChkVar.value='Y';
		 	}else if(document.form1.foodChk.checked !=true&&frm.chkFood2.value =='Y'){
		 		frm.foodChkVar.value='N';
		 	}else { 
		 		frm.foodChkVar.value='M';
		 	}
		 } 
//		if(isReg)frm.submit();
		
		if(document.getElementById("foodChk1")){
			if(document.form1.foodChk1.checked==true&&frm.chkFood1.value !='Y'){
		 		frm.foodChkVar1.value='Y';
		 	}else if(document.form1.foodChk1.checked !=true&&frm.chkFood1.value =='Y'){
		 		frm.foodChkVar1.value='N';
		 	}else { 
		 		frm.foodChkVar1.value='M';
		 	}
		 }
		  
		if(isReg)frm.submit();
	}
	
function Trim(sValue)
{
        sValue = String(sValue);
        // 다중 공백 제거
        for( ; sValue.indexOf("  ") != -1 ; )
                sValue = sValue.replace( "  ", " " );
        
        // 앞 공백 제거
        if( sValue.indexOf(" ") == 0 )
                sValue = sValue.substring( 1, sValue.length );

        // 뒤 공백 제거
        if( sValue.lastIndexOf(" ") == sValue.length -1 )
                sValue = sValue.substring( 0, sValue.length-1 );

        return sValue;
}
function Trim2(sValue)
{
        sValue = String(sValue);
        // 다중 엔터 제거
        for( ; sValue.indexOf("\n\n") != -1 ; )
                sValue = sValue.replace( "\n\n", "\n" );
        
        // 앞 엔터 제거
        if( sValue.indexOf("\n") == 0 )
                sValue = sValue.substring( 1, sValue.length );

        // 뒤 엔터 제거
        if( sValue.lastIndexOf("\n") == sValue.length -1 )
                sValue = sValue.substring( 0, sValue.length-1 );

        return sValue;      
}
	
	/*
	*   일일 업무 보고서 등록시 체크 함수
	*/
	function rptChk(){//삭제선택시 
		var frm=document.forms[0];
		var isReg=true;
		var idx=rptBody.children.length;
		var firstNode;
		var secondNode;
		var thirdNode;

		var hh;
		var mm;
		var jobNo;
		var jobName;
		var jobTitle;
		var jobDetail;
		var sn =1;
		if(idx==0){
			alert('업무항목을 선택하십시오.');
			return false;
		}
		for(var i=0;i<idx;i++){
			//직/간접 체크유무
			firstNode=rptBody.children[i].children[1].children[0].children[0];
			secondNode=firstNode.children[0].children[2];				
			if(!secondNode.checked){			
				secondNode=firstNode.children[1].children[0].children[0].children[0];

				if(secondNode.children[0].children[0].children[0].value==''){
					alert('직무를 선택하십시오.');					
					return false;
				}				
				if(secondNode.children[1].children[0].children[0].value==''){
					alert('제목를 입력하십시오.');
					secondNode.children[1].children[0].children[0].focus();
					return false;
				}				

				if(trim(secondNode.children[2].children[0].children[0].value)==''){
					alert('내용을 입력하십시오.');
					secondNode.children[2].children[0].children[0].focus();
					return false;
				}else if(trim(secondNode.children[2].children[0].children[0].value).length - nullCharCnt(secondNode.children[2].children[0].children[0].value) <10 ){
					alert('작성내용이 부실하므로 보완 바랍니다.'); 
					
//					secondNode.children[2].children[0].children[0].focus(); 
					return false;
				}else if(secondNode.children[2].children[0].children[0].value.length > 2000 ){
					alert('입력가능 문자수를 초과하였습니다. 4000Byte 이내로 작성해 주세요.');				
					return false;
				}

				//간접인력의 Project/사업들
				secondNode=firstNode.children[1].children[0].children[0].children[0].children[0].children[1].children[1];				

				if(secondNode.children.length==0){
					alert('부서(팀·그룹/PJT)를 선택하십시오.');						
					return false;
				}
				for(var j=0;j<secondNode.children.length;j++){
					//Project/사업 한건
					thirdNode=secondNode.children[j].children[0].children[0].children[0].children[1];

					if(thirdNode.children[0].value==''){
						alert('부서(팀·그룹/PJT)를 선택하십시오.');						
						//alert(thirdNode.outerHTML);
						return false;
					}
					thirdNode=secondNode.children[j].children[0].children[0].children[0].children[2];
					if(thirdNode.children[0].value=='00'&&thirdNode.children[1].value=='00'){
						alert('작업시간을 입력하십시오.');
						thirdNode.children[0].focus();
						return false;

					}					
				}

				
			}else{
				secondNode=firstNode.children[1].children[0].children[0].children[0];
				jobNo=secondNode.children[0].children[0].children[0].value;
				prcsNo=secondNode.children[0].children[0].children[3].value;
				hh=secondNode.children[0].children[2].children[0].value;
				mm=secondNode.children[0].children[2].children[1].value;
				jobTitle=secondNode.children[1].children[0].children[0].children[0].children[0].children[0].children[0].value;
				jobDetail=secondNode.children[1].children[0].children[0].children[0].children[1].children[0].children[0].children[0].value;
				if(jobNo==''){
					alert('Project를 선택하십시오.');
					return false;
				}
				if(prcsNo==''){
					alert('공정을 선택하십시오.');
					return false;
				}
				if(jobTitle==''){
					alert('제목을 입력하십시오.');
					secondNode.children[1].children[0].children[0].children[0].children[0].children[0].children[0].focus();
					return false;
				}				
				if(trim(jobDetail)==''){
					alert('내용을 입력하십시오.');
					secondNode.children[1].children[0].children[0].children[0].children[1].children[0].children[0].children[0].focus();
					return false;
				}else if(trim(jobDetail).length-nullCharCnt(jobDetail) <10){ 
					alert('작성내용이 부실하므로 보완 바랍니다.'); 					
//					secondNode.children[2].children[0].children[0].focus();
					return false; 
				}else if(jobDetail.length > 2000){ 
					alert('입력가능 문자수를 초과하였습니다. 4000Byte 이내로 작성해 주세요.');				
					return false;
				}
				
				if(hh=='00'&&mm=='00'){
					alert('작업시간을 입력하십시오.');
					secondNode.children[0].children[2].children[0].focus();
					return false;

				}
				
				secondNode=firstNode.children[1].children[0].children[0].children[0].children[0].children[0].children[6];
				//secondNode.value=sn;
				//alert("secondNode.value.sn::"+secondNode.value);				
				sn++;
				
				
			}		
			
		}
        return true;

	}
	
	function rptChk2(){
		
		var frm=document.forms[0];
		var isReg=true;
		var idx=rptBody.children.length;
		var firstNode;
		var secondNode;
		var thirdNode;

		var hh;
		var mm;
		var jobNo;
		var jobName;
		var jobTitle;
		var jobDetail;
		var sn =1;

	          if(idx != 0 ){
			
		for(var i=0;i<idx;i++){
			firstNode=rptBody.children[i].children[1].children[0].children[0];
			secondNode=firstNode.children[0].children[2];	
			
			if(!secondNode.checked){			
				secondNode=firstNode.children[1].children[0].children[0].children[0];

				if(secondNode.children[2].children[0].children[0].value.length > 2000 ){
					alert('입력가능 문자수를 초과하였습니다. 4000Byte 이내로 작성해 주세요.');				
					return false;
				}

				secondNode=firstNode.children[1].children[0].children[0].children[0].children[0].children[1].children[1];				
				
			}else{
				secondNode=firstNode.children[1].children[0].children[0].children[0];
				jobNo=secondNode.children[0].children[0].children[0].value;
				prcsNo=secondNode.children[0].children[0].children[3].value;
				hh=secondNode.children[0].children[2].children[0].value;
				mm=secondNode.children[0].children[2].children[1].value;
				jobTitle=secondNode.children[1].children[0].children[0].children[0].children[0].children[0].children[0].value;
				jobDetail=secondNode.children[1].children[0].children[0].children[0].children[1].children[0].children[0].children[0].value;
				
				if(jobDetail.length > 2000){ 
					alert('입력가능 문자수를 초과하였습니다. 4000Byte 이내로 작성해 주세요.');				
					return false;
				}
				
				secondNode=firstNode.children[1].children[0].children[0].children[0].children[0].children[0].children[6];
				sn++;
			}		
			
		}		
	           }	
        return true;	
	}
	
	/**
	 *  문자열에서 좌우 공백제거
	 */
	function trim(str)
	{ 
		//alert('indexOf:'+str.indexOf("\n",0));
			var re = new RegExp("[\\n ]", "g");
			str=str.replace(re,'');

		return str;
	}

	function drtRptSch(span){		
		var frm=document.forms[0];
		tmpSpan=span;			
		openWin("/WorkExec/dailyInitRptSch.jsp?empId="+empId, "dailyRptSch", 600, 500);   	

	}
	function indrtRptProdSch(span){		
		//drtRptSch(span); 	
		var frm=document.forms[0];
		tmpSpan=span;			        
		openWin("/common/bizItemSearch.jsp?empId="+empId, "indrtRptProdSch", 600, 500);   
	}
	function itemSearch(span){		
		var frm=document.forms[0];
		tmpSpan=span;			
		openWin("/common/WorkItemSearch.jsp?empId="+empId, "WorkItemSearch", 600, 500);   	

	}
	function indrtRptSch(span){		
		var frm=document.forms[0];
		tmpSpan=span;			
		var url="/commonCon?_SCREEN=/WorkExec/dailyInRptSch.jsp"+
			    "&_ACT=RE&empId="+empId
		openWin(url, "indailyRptSch", 600, 500);   	

	}
	
	
	function confirmItem(param, code, name, orgName){
		var tmpChild=tmpSpan.children[0].children[0].children[0].children[0];
		tmpChild.children[0].value=code;
		tmpChild.children[1].value=code+" "+name;

	}
	function bizReg(prodNo,prodName){
		selectItem(prodNo,prodName,null,null,null,null,null);
	}
	
	function selectItem(prodNo,prodName,prcsNo,prcsName,realStartDt,realEndDt,mh){
		var isBiz=(tmpSpan.id.substring(0,3)=='biz')?true:false;
        
		var tmpChild=tmpSpan.children[0].children[0].children[0].children[0];
		if(isBiz){

			tmpChild=tmpSpan.children[0].children[0].children[0];

			tmpChild.children[0].children[0].value=prodNo;
			tmpChild.children[1].children[0].value=prodName;
		}else{

			tmpChild.children[0].value=prodNo;
			tmpChild.children[1].value=prodName;
			tmpChild.children[2].value=prcsNo;
			tmpChild.children[3].value=prcsName;
			tmpChild.children[4].value=prodNo+" "+prodName;
			tmpChild.children[5].value=prcsNo+" "+prcsName;

			tmpChild=tmpSpan.children[0].children[0].children[0].children[1];

			if(realStartDt!=null&&realStartDt!='null'&&realStartDt!=''){
				tmpChild.children[0].value=		
				realStartDt.substring(0,4)+"-"+
				realStartDt.substring(4,6)+"-"+
				realStartDt.substring(6);
			}else{
				tmpChild.children[0].value='';
			}

			if(realEndDt!=null&&realEndDt!='null'&&realEndDt!=''){
				tmpChild.children[1].value=
				realEndDt.substring(0,4)+"-"+
				realEndDt.substring(4,6)+"-"+
				realEndDt.substring(6);
			}else{
				tmpChild.children[1].value='';
			}
		}

	}
	

	function commify(n) {
		  var reg = /(^[+-]?\d+)(\d{3})/;   // 정규식
		  n += '';                          // 숫자를 문자열로 변환

		  while (reg.test(n))
		    n = n.replace(reg, '$1' + ',' + '$2');

		  return n;
		}