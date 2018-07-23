function rowchange(c, kk, length) {
    for (i=1; i<=c ; i++) {
         document.all["row"+i+"a"].style.display="";
         frm.jobNo[i].value = frm.jobNo[0].value;
         frm.jobName[i].value = frm.jobName[0].value;
         frm.jobNoName[i].value = frm.jobNoName[0].value;
         frm.jobUserNo[i].value = frm.jobUserNo[0].value;
         frm.orgCd[i].value = frm.orgCd[0].value;
         frm.orgUserCd[i].value = frm.orgUserCd[0].value;
         frm.orgName[i].value = frm.orgName[0].value;
         frm.orgCdName[i].value = frm.orgCdName[0].value;
         frm.title[i].value = frm.title[0].value;
		 frm.mainNo[i].value = frm.mDocNo.value;
    }
    for (j=parseInt(c)+1; j<=kk ; j++) {
		if(j>length){
            document.all["row"+j+"a"].style.display="none";
	         frm.jobNo[j-1].value = "";
	         frm.jobName[j-1].value = "";
	         frm.jobNoName[j-1].value = "";
	         frm.jobUserNo[j-1].value = "";
	         frm.orgCd[j-1].value = "";
	         frm.orgName[j-1].value = "";
	         frm.orgCdName[j-1].value = "";
	         frm.title[j-1].value = "";
	         frm.mainNo[j-1].value = "";
		}else{
			document.forms[0].sel.selectedIndex=length-1;
		}

    }
	return;
}

function chBtype(){
	frm.billType.value=frm.bType[frm.bType.selectedIndex].value;
	if((frm.billType.value=="1A") || (frm.billType.value=="1B")|| (frm.billType.value=="1C")|| (frm.billType.value=="1D")|| (frm.billType.value=="1E")){
		frm.taxAmt.disabled = false;
		frm.taxAmt1.disabled = false;
		frm.taxAmt2.disabled = false;
		frm.taxAmt3.disabled = false;
		frm.taxAmt4.disabled = false;
		frm.supplyAmt.disabled = false;
		frm.taxAmt.style.backgroundColor = "#F0F0FF";
		frm.supplyAmt.style.backgroundColor = "#F0F0FF";
		if(frm.supplyAmt.value!="0"){
			dataCheckSum(frm.supplyAmt);
		} else {
			frm.taxAmt.value = "0";
		}
	} else {
		frm.taxAmt.disabled = true;
		frm.taxAmt.value = "0";
		frm.taxAmt1.disabled = true;
		frm.taxAmt1.value = "";
		frm.taxAmt2.disabled = true;
		frm.taxAmt2.value = "";
		frm.taxAmt3.disabled = true;
		frm.taxAmt3.value = "";
		frm.taxAmt4.disabled = true;
		frm.taxAmt4.value = "";
		dataCheckSum(frm.taxAmt);		
		frm.supplyAmt.style.backgroundColor = "#FFFFFF";
		frm.taxAmt.style.backgroundColor = "#FFFFFF";
		frm.supplyAmt.disabled = true;
		frm.supplyAmt.value = frm.totAmt.value;
	}
}

function search(param){
	//window.open("/common/allSearch.jsp?param="+param,"","height=300,width=500,toolbar=no,location=no,directories=no,scrollbars=yes,resizable=yes");	
	openWin("/common/allSearch.jsp?param="+param,"",500,300);
}

function confirmItem(param, code, name, orgName){
	if(param=='org'){
		frm.useOrgCd.value=code;	
		frm.useOrgName.value=name;
		frm.useOrgCdName.value=code + " " + name;
		frm.orgCd[0].value=code;	
		//frm.orgName[0].value=name;
		frm.orgCdName[0].value=code + " " + name;
	} else if(param=='emp'){
		frm.useEmpId.value=code;
		frm.useEmpName.value=name;
	} else if(param=='acct'){
		frm.acctCd.value=code;
		frm.acctName.value=name;
		frm.acctCdName.value=code + " " + name;
	}
}

function itemSearch(empId,idx,incomeYN,orgCd,labCostType){
	
	if(orgCd==""){
		alert('먼저 부서부터 선택하세요');
		return;
	}
	
	
	//openWin("/searchProd.do?_SCREEN=/Budget/WorkItemSearch.jsp?empId="+empId+"&idx="+idx+"&incomeYN="+incomeYN+"&orgCd="+orgCd+"&labCostType="+labCostType,"",600,500);
	openWin("/initJob.do?_SCREEN=/Budget/WorkItemSearch.jsp?empId="+empId+"&idx="+idx+"&incomeYN="+incomeYN+"&orgCd="+orgCd+"&labCostType="+labCostType,"",600,500);
	//openWin("/Budget/WorkItemSearch.jsp?empId="+empId+"&idx="+idx+"&incomeYN="+incomeYN,"",600,500);
}

function selectItem(prodNo,prodName,prcsNo,prcsName,plStartDt,plEndDt,plMh,idx){
	frm.jobNo[idx].value=prodNo+prcsNo;
	frm.jobName[idx].value=prodName;
	frm.jobNoName[idx].value=prodNo + prcsNo + " " + prodName;
}

function setValue(jobNo,jobName,bizNo,bizName,idx){
	frm.jobNo[idx].value=jobNo+bizNo;
	frm.jobName[idx].value=jobName;
	frm.jobNoName[idx].value=jobNo + bizNo + " " + jobName;
}

function searchItem(param,idx,mode){
var ym, orgCd
	if(isEmpty(frm.eventDt)){alert("발생일자부터 선택하세요");return true;}	
	if(isEmpty(frm.orgCd[idx])){alert("회계처리 대상 부서부터 선택하세요");return true;}
	ym = removeChar(frm.eventDt.value,"-");
	orgCd = frm.orgCd[idx].value;
	openWin("/Budget/budgetItemSearch.jsp?param="+param+"&idx="+idx+"&mode="+mode+"&ym="+ym+"&orgCd="+orgCd,"",500,300);
	//openWin("/Budget/budgetItemSearch.jsp?param="+param+"&idx="+idx+"&mode="+mode,"",500,300);
}

function setItem(param, code, name, idx){
	if(param=="org"){
		frm.orgCd[idx].value=code;
		frm.orgName[idx].value=name;
		frm.orgCdName[idx].value=code + " " + name;
		frm.jobNo[idx].value="";
		frm.jobName[idx].value=" ";
		frm.jobNoName[idx].value=" ";
	} else {
		frm.acctCd[idx].value=code;
		frm.acctName[idx].value=name;
		frm.acctCdName[idx].value=code + " " + name;
		if(code=="21081"){
			frm.amount[idx].value =frm.taxAmt.value;
		}
	}
}

function click_BizAcq(param, enmpId, orgCd){
		frm._SCREEN.value='/Budget/bizAcqSearch.jsp';
		frm.target='bizAcqSearch';
		frm.action='/initSearchBizAcq.do';
	    openWin("","bizAcqSearch",600,500);
		frm.submit();
	//openWin("/Budget/bizAcqSearch.jsp?param="+param+"&enmpId="+enmpId+"&orgCd="+orgCd,"",600,500);
}

function setBizAcqItem(param, code, name, bizAcqRegNo, ceoName){
	frm.bizAcqAcctCd.value=code;
	frm.bizAcqName.value=name;
	frm.bizAcqCdName.value=code + " " + name;
	frm.bizAcqRegNo.value=bizAcqRegNo;
	frm.bizCeoName.value=ceoName;
}

function errCheck(){
	var amt1 = 0;
	var amt2 = 0;
//널체크
	if(isEmpty(frm.eventDt)){alert("발생일자를 선택하세요");return true;}
	if(isEmpty(frm.crEmpId)){alert("기안자 정보가 없습니다.");return true;}
	if(isEmpty(frm.useOrgCd)){alert("부서/PJT명를 선택하세요");return true;}
	if(isEmpty(frm.useEmpId)){alert("사용자를 선택하세요");return true;}
	if(isEmpty(frm.phonNo)){alert("전화번호를 입력하세요");frm.phonNo.focus();return true;}
	if(isEmpty(frm.bizAcqAcctCd)){alert("거래처를 선택하세요");return true;}
	if(isEmpty(frm.domicile)){alert("영수/청구 구분을 선택하세요");return true;}
	if(frm.supplyAmt.value=="0"){alert("공급가액을 입력하세요");frm.supplyAmt.focus();return true;}
	if(frm.realAmt.value=="0"){alert("실입금액을 입력하세요");frm.realAmt.focus();return true;}
	if(isEmpty(frm.reciveDt)){alert("입금예정일을 선택하세요");return true;}
	if(isEmpty(frm.outSourceType)){alert("외주 구분을 선택하세요");return true;}
	if(isEmpty(frm.mTitle)){alert("제목을 입력하세요");frm.mTitle.focus();return true;}
	if(isEmpty(frm.detail)){alert("지급내역을 입력하세요");frm.detail.focus();return true;}
	if(frm.billType.value!="3." && frm.billType.value!="4."){
		if(isEmpty(frm.contents1)){alert("매출세금계산서 내역을 입력하세요");frm.contents1.focus();return true;}
		if(isEmpty(frm.supplyAmt1)){alert("매출세금계산서 공급가액을 입력하세요");frm.supplyAmt1.focus();return true;}
	}
	if(frm.bizAcqAcctCd.value.substring(0,2)=="77"){
	   if(frm.domicile.value=="1"){alert("내부거래처인경우 영수/청구 구분은 영수이어야 합니다.");return true;}
	}
	
	cTot = 0;
	dTot = 0;
	chkvat = 0;
	chkRev = 0;
	for(i=0;i<frm.sel.selectedIndex+1;i++){
		frm.mainNo[i].value = frm.mDocNo.value;
		if(isEmpty(frm.acctCd[i])){alert("계정을 선택하세요");return true;}
		if(isEmpty(frm.orgCd[i])){alert("회계처리대상부서를 선택하세요");return true;}
		if(isEmpty(frm.amount[i])){alert("신청액을 입력하세요");frm.amount[i].focus();return true;}
		if(frm.amount[i].value=="0"){alert("0원은 사용할 수 없습니다. 금액을 입력하세요");frm.amount[i].focus();return true;}
		if(isEmpty(frm.jobNo[i])){alert("제품/사업코드을 선택하세요");return true;}
		if(isEmpty(frm.title[i])){alert("개요를 입력하세요");frm.title[i].focus();return true;}
		//계정코드 체크
		acc = frm.acctCd[i].value;
		if(acc=="21081"){
			if(frm.billType.value!="1A" && frm.billType.value!="1B" && frm.billType.value!="1C" && frm.billType.value!="1D" && frm.billType.value!="1E"){
				alert("계정코드가 21081 예수부가가치세인경우 증빙구분은 세금계산서가 선택되어야 합니다.");
				return true;
			}
		}
		if(acc=="41021" || acc=="41022"){
			if(frm.certifType[i].value!="S"){
				alert("계정코드가 "+acc+"인경우 구매요청번호를 선택하셔야 합니다.");
				return true;
			} else {
				if(frm.certifNo[i].value.length!=11) {
					alert("구매요청번호는 11자리로 입력하여 주십시요.예) 2005110101X");
					return true;
				}
			}
		}
		//jth 200051229 오경철과장님 요청으로 다시 풀어줌.
		//if(acc.substring(0,1)=="5"){alert("수입품의에서는 비용계정을 선택할 수 없습니다.");return true;}
		
		//합계체크
		if(acc=="21081"){
			chkvat = chkvat + parseInt(trim(removeChar(frm.amount[i].value,",")));
		} else {
			// 오경철 과장님 요구로 예수금도 공급액에 포함시킴 (2006.12.13)
			//if(acc != '21041'){
			if(frm.cdType[i].value =="D"){
				chkRev = chkRev + parseInt(trim(removeChar(frm.amount[i].value,",")));
			}
			//}
		}
		if(frm.cdType[i].value =="C") { //차변인경우
			cTot = cTot + parseInt(trim(removeChar(frm.amount[i].value,",")));
		} else { //대변인경우
			dTot = dTot + parseInt(trim(removeChar(frm.amount[i].value,",")));
		}
		frm.amount[i].value = removeChar(frm.amount[i].value,",");
	}
	
	//alert(cTot+ " : " + dTot);
	chAmt = 0;
	chAmt = parseInt(trim(removeChar(frm.totAmt.value,",")));
	if(dTot!=chAmt){
		alert("수입총액은 회계항목 대변 합계와 같아야 합니다.");
		return true;
	}
	chAmt = 0;
	chAmt = parseInt(trim(removeChar(frm.diffAmt.value,",")));
	if(cTot!=chAmt){
		alert("차인액은 회계항목 차변 합계와 같아야 합니다.");
		return true;
	}
	chAmt = 0;
	chAmt = parseInt(trim(removeChar(frm.taxAmt.value,",")));
	if(chkvat!=chAmt){
		alert("21081 예수부가가치세의 합이 틀립니다. 확인하여 주십시요.");
		return true;
	}

	if(frm.billType.value=="1A" || frm.billType.value=="1B" || frm.billType.value=="1C"|| frm.billType.value=="1D"|| frm.billType.value=="1E"){
		amt1 = parseInt(trim(removeChar(frm.taxAmt.value,",")));
		amt2 = parseInt(parseInt(trim(removeChar(frm.supplyAmt.value,","))) / 10);
		//alert(amt1+' : ' + amt2);
		if(amt1 < (amt2-9) || amt1 > (amt2+9)) {
			alert("부가세는 공급가액의 10% 이내로 입력되어야 합니다.");
			frm.taxAmt.focus();
			return true;
		}
	}
	
	if(frm.taxAmt.disabled){
		frm.taxAmt.disabled = false;
		frm.taxAmt.value = "0";
	}
	
	//매출세금계산서
	if(frm.billType.value!="3." && frm.billType.value!="4."){
		//alert(chkRev+" : "+frm.supplyTotAmt.value);
		//alert(frm.supplyAmt.value+" : "+frm.supplyTotAmt.value);
		if(chkRev!=parseInt(trim(removeChar(frm.supplyTotAmt.value,",")))){
			alert("수입액과 매출세금계산서의 공급가액 합계 값이 다릅니다.");
			return true;
		}
		
		if(frm.billType.value!="2A" && frm.billType.value!="2B" && frm.billType.value!="2C"){
			if(frm.taxAmt.value!=frm.taxTotAmt.value){
				alert("부가세와 매출세금계산서의 부가세 합계 값이 다릅니다.");
				return true;
			}
		}
	}
	
	frm.createDtm.value = removeChar(frm.createDtm.value,"-");
	frm.eventDt.value = removeChar(frm.eventDt.value,"-");
	frm.reciveDt.value = removeChar(frm.reciveDt.value,"-");
	frm.supplyAmt.value = removeChar(frm.supplyAmt.value,",");
	frm.realAmt.value = removeChar(frm.realAmt.value,",");
	frm.taxAmt.value = removeChar(frm.taxAmt.value,",");
	frm.totAmt.value = removeChar(frm.totAmt.value,",");
	frm.diffAmt.value = removeChar(frm.diffAmt.value,",");
	frm.supplyAmt1.value = removeChar(frm.supplyAmt1.value,",");
	frm.taxAmt1.value = removeChar(frm.taxAmt1.value,",");
	frm.supplyAmt2.value = removeChar(frm.supplyAmt2.value,",");
	frm.taxAmt2.value = removeChar(frm.taxAmt2.value,",");
	frm.supplyAmt3.value = removeChar(frm.supplyAmt3.value,",");
	frm.taxAmt3.value = removeChar(frm.taxAmt3.value,",");
	frm.supplyAmt4.value = removeChar(frm.supplyAmt4.value,",");
	frm.taxAmt4.value = removeChar(frm.taxAmt4.value,",");
	//disable 만든것은 넘어가지 않음로 여기서 풀어준다.
	frm.taxAmt.disabled = false;
	frm.supplyAmt.disabled = false;
	
	return false; //이상이 없는경우.
}

function iChk(){
	frm.domicile.value = "2";
}

function oChk(){
	frm.domicile.value = "1";
}

function apY(){
	frm.advanceClass.value = "1";
}

function apN(){
	frm.advanceClass.value = "2";
}

function chkOut(){
	frm.outSourceType.value = "Y";
}

function chkIn(){
	frm.outSourceType.value = "N";
}

function cha(i){
	if(frm.acctCd[i].value.substring(0,1)=='5'||frm.acctCd[i].value.substring(0,1)=='7'){
		frm.cdType[i].value = "C";
	}else if(frm.acctCd[i].value.substring(0,1)=='4'||frm.acctCd[i].value.substring(0,1)=='6'){
		alert("수입계정 선택시, 대변을 선택하시기 바랍니다.");
		frm.cdType[i].value = "C";
		document.forms[0].acctCd[i].value = '';
		document.forms[0].acctName[i].value = '';
		document.forms[0].acctCdName[i].value = '';	

	}else{
		frm.cdType[i].value = "C";
	}
}

function dea(i){
	if(frm.acctCd[i].value.substring(0,1)=='4'||frm.acctCd[i].value.substring(0,1)=='6'){
		frm.cdType[i].value = "D";
	}else if(frm.acctCd[i].value.substring(0,1)=='5'||frm.acctCd[i].value.substring(0,1)=='7'){
		alert("비용계정 선택시, 차변을 선택하시기 바랍니다.");
		frm.cdType[i].value = "D";
		document.forms[0].acctCd[i].value = '';
		document.forms[0].acctName[i].value = '';
		document.forms[0].acctCdName[i].value = '';	
	}else{
		frm.cdType[i].value = "D";
	}
}

function dChange(field){
	var str = trim(field.value);
	if(str.length!=0){
		field.value = removeChar(str,",");
	}
	return;
}

function certTypeChange(idx){
	frm.certifType[idx].value=frm.certType[idx][frm.certType[idx].selectedIndex].value;
}
function dataCheckSum(field){
	var frm = document.forms[0];
	var str = trim(field.value);
	var amt = 0;
	if(str.length==0){
	    field.name.value = 0;
	    str = 0;
	}
	if(!isNumDashComma(field)){
	    field.select();
	    field.focus();
	    return;
	} else {
		if(field.name=="supplyAmt"){ //공급가액일경우
			if((frm.billType.value=="1A") || (frm.billType.value=="1B")|| (frm.billType.value=="1C")|| (frm.billType.value=="1D")|| (frm.billType.value=="1E")||(frm.taxAmt.value=="0")){ //세금계산서인경우 부가세를 계산한다.
				if(parseInt(trim(removeChar(frm.taxAmt.value,",")))==0){
					amt = parseInt(parseInt(trim(removeChar(frm.supplyAmt.value,","))) / 10)
					frm.taxAmt.value = cashReturn(amt);
				}
				setDetail();//상세내역 셋팅
				sumCheck1(frm.supplyAmt1);
			}
		}
		
		if(field.name=="taxAmt"){ //부가세인 경우
			if((frm.billType.value=="1A") || (frm.billType.value=="1B" || (frm.billType.value=="1C") || (frm.billType.value=="1D") || (frm.billType.value=="1E"))){ //세금계산서인경우 부가세를 계산한다.
				//amt = parseInt(parseInt(trim(removeChar(frm.supplyAmt.value,","))) / 10)
				//frm.taxAmt.value = cashReturn(amt);
				//if(parseInt(trim(removeChar(frm.totAmt.value,",")))==0){
					amt = parseInt(parseInt(trim(removeChar(frm.supplyAmt.value,","))))+
						  parseInt(parseInt(trim(removeChar(frm.taxAmt.value,","))))
					frm.totAmt.value = cashReturn(amt);
					frm.realAmt.value = cashReturn(amt);
					
					//sumCheck1(frm.supplyAmt1);
					//setDetail();//상세내역 셋팅
				//}
			}
		}

		if(field.name=="totAmt"){ //공급가액일경우
			if((frm.billType.value=="1A") || (frm.billType.value=="1B") || (frm.billType.value=="1C") || (frm.billType.value=="1D") || (frm.billType.value=="1E")){ //세금계산서인경우 부가세를 계산한다.
				if(parseInt(trim(removeChar(frm.supplyAmt.value,",")))==0){
					//alert("test1"+parseInt(trim(removeChar(frm.supplyAmt.value,","))))
					frm.supplyAmt.value = cashReturn(frm.totAmt.value);
				}
			} else { //만일 세금계산서가 아니면
					//alert("test2"+parseInt(trim(removeChar(frm.supplyAmt.value,","))))
					frm.supplyAmt.value = cashReturn(frm.totAmt.value);
			} 
			//실입금액이 0이면 수입총액을 넣어준다.
			if(parseInt(trim(removeChar(frm.realAmt.value,",")))==0){
				frm.realAmt.value = cashReturn(frm.totAmt.value);
			}
		}
		
		//수입총액이 0이면 공급가와 부가세를 더해서 넣어준다.
		if(parseInt(trim(removeChar(frm.totAmt.value,",")))==0){
			amt = 0;
			amt = parseInt(trim(removeChar(frm.supplyAmt.value,","))) + parseInt(trim(removeChar(frm.taxAmt.value,",")));
			frm.totAmt.value = cashReturn(amt);
			frm.realAmt.value = cashReturn(amt);
		}
		
		if(field.name=="realAmt"){ //변경한 금액이 실입금액이면
			if(parseInt(trim(removeChar(str,","))) > parseInt(trim(removeChar(frm.totAmt.value,",")))) {
				alert("지급총액보다 실입금액이 많습니다.");
				frm.realAmt.focus();
		    	return;
			} else {
				amt = parseInt(trim(removeChar(frm.totAmt.value,","))) - parseInt(trim(removeChar(str,","))) 
				frm.diffAmt.value = cashReturn(amt);
			}
		} else {
			if(parseInt(trim(removeChar(frm.realAmt.value,","))) > parseInt(trim(removeChar(frm.totAmt.value,",")))) {
				alert("지급총액보다 실입금액이 많습니다.");
				frm.realAmt.focus();
		    	return;
			} else {
				amt = parseInt(trim(removeChar(frm.totAmt.value,","))) - parseInt(trim(removeChar(frm.realAmt.value,","))) 
				frm.diffAmt.value = cashReturn(amt);
			}
		}
		field.value = cashReturn(str);
	}
}

function amtChange(field,idx){
	var str = field[idx].value;
	field[idx].value = removeChar(str,",");
}

function dataCheck(field,idx){
	var str = field[idx].value;
	if(!isNumDashComma(field[idx])){
	    field[idx].focus();
	    return;
	}
	field[idx].value = cashReturn(str);
}

function numCheck(field){
	var str = trim(field.value);
	if(str.length==0){
	    return;
	}
	if(!isInteger(str)){
   		alert("숫자만 입력 가능합니다");
	    field.select();
	    field.focus();
	}
}

function dotCheck(field){
	var str = field.value;
	if(!isNumDot(field)){
	    field.select();
	    field.focus();
	}
}

function phoneCheck(field){
	if(!isPhoneNum(field)){
	    field.select();
	    field.focus();
	}
}

function titleCp(i){
	if(trim(frm.title[0].value)==""){ 
		frm.title[0].value = frm.mTitle.value;
	}
	if(trim(frm.title[i+1].value)==""){
	   frm.title[i+1].value = frm.title[0].value;
	}
}

function isInt(field){
	var str = trim(field.value);
	if(str.length==0){
	    return 0;
	}
	return parseInt(trim(removeChar(str,",")));
}

function sumCheck1(field){
	var frm = document.forms[0];
	var str = field.value;
	if(str.length==0){
	    return;
	}
	if(!isNumDashComma(field)){
	    field.select();
	    field.focus();
	    return;
	} else {
		if((frm.billType.value=="1A") || (frm.billType.value=="1B") || (frm.billType.value=="1C") || (frm.billType.value=="1D") || (frm.billType.value=="1E")){ //세금계산서인경우 부가세를 계산한다.
			//amt = Math.round(parseInt(trim(removeChar(frm.supplyAmt1.value,","))) / 10);
			//amt = parseInt(parseInt(trim(removeChar(frm.supplyAmt1.value,","))) / 10);
			amt = parseInt(trim(removeChar(frm.taxAmt.value,",")));
			frm.taxAmt1.value = cashReturn(amt);
			amt = 0;
			amt = isInt(frm.taxAmt1) + isInt(frm.taxAmt2) + isInt(frm.taxAmt3) + isInt(frm.taxAmt4);
			frm.taxTotAmt.value = cashReturn(amt);
		}
		amt = 0;
		amt = isInt(frm.supplyAmt1) + isInt(frm.supplyAmt2) + isInt(frm.supplyAmt3) + isInt(frm.supplyAmt4);
		frm.supplyTotAmt.value = cashReturn(amt);
		
		//field.value = cashReturn(frm.taxamt.value);
	}
}

function sumCheck2(field){
	var str = field.value;
	if(str.length==0){
	    return;
	}
	if(!isNumDashComma(field)){
	    field.select();
	    field.focus();
	    return;
	} else {
		if((frm.billType.value=="1A") || (frm.billType.value=="1B") || (frm.billType.value=="1C") || (frm.billType.value=="1D") || (frm.billType.value=="1E")){ //세금계산서인경우 부가세를 계산한다.
			//amt = Math.round(parseInt(trim(removeChar(frm.supplyAmt2.value,","))) / 10);
			amt = parseInt(parseInt(trim(removeChar(frm.supplyAmt2.value,","))) / 10);
			frm.taxAmt2.value = cashReturn(amt);
			amt = 0;
			amt = isInt(frm.taxAmt1) + isInt(frm.taxAmt2) + isInt(frm.taxAmt3) + isInt(frm.taxAmt4);
			frm.taxTotAmt.value = cashReturn(amt);
		}
		amt = 0;
		amt = isInt(frm.supplyAmt1) + isInt(frm.supplyAmt2) + isInt(frm.supplyAmt3) + isInt(frm.supplyAmt4);
		frm.supplyTotAmt.value = cashReturn(amt);
		
		field.value = cashReturn(frm.supplyAmt2.value);
	}
}

function sumCheck3(field){
	var str = field.value;
	if(str.length==0){
	    return;
	}
	if(!isNumDashComma(field)){
	    field.select();
	    field.focus();
	    return;
	} else {
		if((frm.billType.value=="1A") || (frm.billType.value=="1B") || (frm.billType.value=="1C") || (frm.billType.value=="1D") || (frm.billType.value=="1E")){ //세금계산서인경우 부가세를 계산한다.
			//amt = Math.round(parseInt(trim(removeChar(frm.supplyAmt3.value,","))) / 10);
			amt = parseInt(parseInt(trim(removeChar(frm.supplyAmt3.value,","))) / 10);
			frm.taxAmt3.value = cashReturn(amt);
			amt = 0;
			amt = isInt(frm.taxAmt1) + isInt(frm.taxAmt2) + isInt(frm.taxAmt3) + isInt(frm.taxAmt4);
			frm.taxTotAmt.value = cashReturn(amt);
		}
		amt = 0;
		amt = isInt(frm.supplyAmt1) + isInt(frm.supplyAmt2) + isInt(frm.supplyAmt3) + isInt(frm.supplyAmt4);
		frm.supplyTotAmt.value = cashReturn(amt);
		
		field.value = cashReturn(frm.supplyAmt3.value);
	}
}

function sumCheck4(field){
	var str = field.value;
	if(str.length==0){
	    return;
	}
	if(!isNumDashComma(field)){
	    field.select();
	    field.focus();
	    return;
	} else {
		if((frm.billType.value=="1A") || (frm.billType.value=="1B") || (frm.billType.value=="1C") || (frm.billType.value=="1D") || (frm.billType.value=="1E")){ //세금계산서인경우 부가세를 계산한다.
			amt = Math.round(parseInt(trim(removeChar(frm.supplyAmt4.value,","))) / 10);
			frm.taxAmt4.value = cashReturn(amt);
			amt = 0;
			amt = isInt(frm.taxAmt1) + isInt(frm.taxAmt2) + isInt(frm.taxAmt3) + isInt(frm.taxAmt4);
			frm.taxTotAmt.value = cashReturn(amt);
		}
		amt = 0;
		amt = isInt(frm.supplyAmt1) + isInt(frm.supplyAmt2) + isInt(frm.supplyAmt3) + isInt(frm.supplyAmt4);
		frm.supplyTotAmt.value = cashReturn(amt);
		
		field.value = cashReturn(frm.supplyAmt4.value);
	}
}

function sumTaxCheck1(field){
	var str = field.value;
	var amt = 0;
	if(str.length==0){
	    return;
	}
	if(!isNumDashComma(field)){
	    field.select();
	    field.focus();
	    return;
	} else {
		frm.taxAmt1.value = cashReturn(trim(removeChar(frm.taxAmt1.value,",")));
		amt = 0;
		amt = isInt(frm.taxAmt1) + isInt(frm.taxAmt2) + isInt(frm.taxAmt3) + isInt(frm.taxAmt4);
		frm.taxTotAmt.value = cashReturn(amt);
	}
}

function sumTaxCheck2(field){
	var str = field.value;
	var amt = 0;
	if(str.length==0){
	    return;
	}
	if(!isNumDashComma(field)){
	    field.select();
	    field.focus();
	    return;
	} else {
		frm.taxAmt2.value = cashReturn(trim(removeChar(frm.taxAmt2.value,",")));
		amt = 0;
		amt = isInt(frm.taxAmt1) + isInt(frm.taxAmt2) + isInt(frm.taxAmt3) + isInt(frm.taxAmt4);
		frm.taxTotAmt.value = cashReturn(amt);
	}
}

function sumTaxCheck3(field){
	var str = field.value;
	var amt = 0;
	if(str.length==0){
	    return;
	}
	if(!isNumDashComma(field)){
	    field.select();
	    field.focus();
	    return;
	} else {
		frm.taxAmt3.value = cashReturn(trim(removeChar(frm.taxAmt3.value,",")));
		amt = 0;
		amt = isInt(frm.taxAmt1) + isInt(frm.taxAmt2) + isInt(frm.taxAmt3) + isInt(frm.taxAmt4);
		frm.taxTotAmt.value = cashReturn(amt);
	}
}

function sumTaxCheck4(field){
	var str = field.value;
	var amt = 0;
	if(str.length==0){
	    return;
	}
	if(!isNumDashComma(field)){
	    field.select();
	    field.focus();
	    return;
	} else {
		frm.taxAmt4.value = cashReturn(trim(removeChar(frm.taxAmt4.value,",")));
		amt = 0;
		amt = isInt(frm.taxAmt1) + isInt(frm.taxAmt2) + isInt(frm.taxAmt3) + isInt(frm.taxAmt4);
		frm.taxTotAmt.value = cashReturn(amt);
	}

}

