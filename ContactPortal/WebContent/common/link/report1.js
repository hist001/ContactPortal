/*
*   다중 선택시 Flag이용 함수(ex) 일일업무보고 등록, 수정시 이용)
*/
function chkChecked(s){//삭제선택시 
	if(document.forms[0].chk[s].checked==true){
		document.forms[0].delFlag[s].value='Y'
	}else{
		document.forms[0].delFlag[s].value='N'
	}
	//alert(s+"::"+document.forms[0].delFlag[s].value);
	
}
/*
*   일일 업무 보고서 등록시 체크 함수
*/
function reportCheck(){//삭제선택시 
	var idx = document.forms[0].jobTitle.length;
	for(i=0;i<idx;i++){
		if(document.forms[0].delFlag[i].value=='N'){
			if(isEmpty(document.forms[0].jobNo[i])){
				alert('PROJECT 또는 직무를 선택하시오.');
				//document.forms[0].jobNo[i].focus();
				return false;
			}
			if((document.forms[0].jobNo[i]=='null')){
				alert(document.forms[0].jobNo[i]);
				//document.forms[0].jobNo[i].focus();
				return false;
			}
			if(isEmpty(document.forms[0].prcsNo[i])){
				alert('공정 또는 조직을 선택하시오.');
				//document.forms[0].jobNo[i].focus();
				return false;
			}
			//if(isEmpty(document.forms[0].startDt[i])){
			//	alert('시작일자를 선택하시오.');
				//document.forms[0].startDt[i].focus();
			//	return false;
			//}
			//if(isEmpty(document.forms[0].endDt[i])){
			//	alert('종료일자를 선택하시오.');
				//document.forms[0].endDt[i].focus();
			//	return false;
			//}		
			if(isEmpty(document.forms[0].jobTitle[i])){
				alert('제목을 입력하시오.');
				document.forms[0].jobTitle[i].focus();
				return false;
			}
			if(isEmpty(document.forms[0].jobDetail[i])){
				alert('내용을 입력하시오.');
				document.forms[0].jobDetail[i].focus();
				return false;

			}
			hh=document.forms[0].hh[i].options[document.forms[0].hh[i].selectedIndex].value;
			mm=document.forms[0].mm[i].options[document.forms[0].mm[i].selectedIndex].value;
			if(hh=='00'&&mm=='00'){
				alert('작업시간을 입력하시오.');
				document.forms[0].hh[i].focus();
				return false;

			}
			if(document.forms[0].name!='sale'
			 &&document.forms[0].name!='confirmUpdate'
			 &&document.forms[0].transFlag[i].value=='Y'){
				alert('교통비 내역 포함되어있음. 일일영업보고에서 수정하시오. ');
				return false;
			}

		
		}
    }
	return true;
}
/*
*   일일 영업 업무 보고서 등록시 체크 함수
*/
function saleReportCheck(){//삭제선택시 
	var idx = document.forms[0].jobTitle.length;
	for(i=0;i<idx;i++){
		if(document.forms[0].delFlag[i].value=='N'){
//			if(document.forms[0].jobNo[i].value = 'null' ||document.forms[0].jobNo[i].value = ''){
//				alert('PROJECT 또는 직무 선택이 잘못되었습니다. 다시 선택하여 주십시오');
//				//document.forms[0].jobNo[i].focus();
//				return false;
//			}
			if(isEmpty(document.forms[0].jobNo[i])){
				alert('PROJECT 또는 직무를 선택하시오.');
				//document.forms[0].jobNo[i].focus();
				return false;
			}
			if(isEmpty(document.forms[0].prcsNo[i])){
				alert('공정 또는 조직을 선택하시오.');
				//document.forms[0].prcsNo[i].focus();
				return false;
			}
			if(isEmpty(document.forms[0].jobTitle[i])){
				alert('제목을 입력하시오.');
				document.forms[0].jobTitle[i].focus();
				return false;
			}
			if(isEmpty(document.forms[0].jobDetail[i])){
				alert('내용을 입력하시오.');
				document.forms[0].jobDetail[i].focus();
				return false;
			}
			hh=document.forms[0].hh[i].options[document.forms[0].hh[i].selectedIndex].value;
			mm=document.forms[0].mm[i].options[document.forms[0].mm[i].selectedIndex].value;
			if(hh=='00'&&mm=='00'){
				alert('작업시간을 입력하시오.');
				document.forms[0].hh[i].focus();
				return false;

			}
			if(document.forms[0].totCount[i].value!='0'&&document.forms[0].bizAcqCd[i].value==''){
				alert('거래처를 입력하시오.');
				document.forms[0].B6[i].focus();
				return false;
			}

		
		}
    }
    
    var contentLength =0;
    for(i=0;i<idx;i++){
    	contentLength += trim(document.forms[0].jobDetail[i].value).length;
    }
    if(contentLength <10){
    	alert('작성내용이 부실하므로 보완바랍니다.');
    	document.forms[0].jobDetail[0].focus();
    	return false;
    }
    
	return true;
}
/*
*   기본 업무 계획시  체크 함수
*/
function planCheck(s){

	if(s!='change'&&s!='prcs'&&isEmpty(document.forms[0].jobNo)){
		alert('제품/공정을 선택하시오.');
		return false;
	}
	if(s!='prcs'&&(isEmpty(document.forms[0].planMh )||document.forms[0].planMh.value=='0') ){
		alert('작업시간을 입력하시오.');
		return false;
	}
	//if(s=='prcs'&&(isEmpty(document.forms[0].plMh )||document.forms[0].plMh.value=='0' )){
	//	alert('작업시간을 입력하시오.');
	//	return false;
	//}
		
	if(s!='prcs'&&isEmpty(document.forms[0].jobTitle)){
		alert('제목을 입력하시오.');
		document.forms[0].jobTitle.focus();
		return false;
	}
	if(s!='prcs'&&isEmpty(document.forms[0].jobDetail)){
		alert('내용을 입력하시오.');
		document.forms[0].jobDetail.focus();
		return false;

	}
	if(s=='prcs'&&isEmpty(document.forms[0].prcsDesc )){
		alert('내용을 입력하시오.');
		document.forms[0].prcsDesc.focus();
		return false;
	}
	return true;
}
/*
*   일일 업무 보고서 등록화면에서 보고자 전환시 함수
*/
function reportChecked(){//보고자 선택시 
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
    /*
	report=document.forms[0].reportViewer.options[document.forms[0].reportViewer.selectedIndex].value;

	idx=report.indexOf(" ");

    document.forms[0].empId.value=report.substring(0,idx);
	document.forms[0].empKName.value=report.substring(idx+1);
	document.forms[0]._ACT.value='RSD';
	document.forms[0].submit();
	*/

}
/*
*   전일 일일 업무 보고서 등록화면에서 보고자 전환시 함수
*/
function reportPreChecked(){//보고자 선택시 
	report=document.forms[0].reportViewer.options[document.forms[0].reportViewer.selectedIndex].value;

	idx=report.indexOf(" ");

    document.forms[0].empId.value=report.substring(0,idx);
	document.forms[0].empKName.value=report.substring(idx+1);
	document.forms[0]._ACT.value='RSA';
	document.forms[0].submit();

}
/*
*   일일 업무 보고서 등록화면에서 업무추가 선택시 함수
*/
function rowchange(c, kk, length) {

        for (i=1; i<=c ; i++) {
			s=document.forms[0].delFlag[i-1].value;
			if(s=='Y'){
				document.forms[0].delFlag[i-1].value='Y'
			}else{
				document.forms[0].delFlag[i-1].value='N'
			}
			//document.forms[0].chk[i-1].checked='true'
            document.all["row"+i+"a"].style.display="";
        }
        for (j=parseInt(c)+1; j<=kk ; j++) {
			if(j>length){
		    document.forms[0].delFlag[j-1].value='C'
                 
            document.all["row"+j+"a"].style.display="none";
			}else{
				document.forms[0].sel.selectedIndex=length-1
			}

        }
		return;
}