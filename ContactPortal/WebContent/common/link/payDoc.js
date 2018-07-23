function itemSearch(empId,idx,incomeYN,orgCd,labCostType){ 
	if(orgCd==""){
		alert('부서코드를 입력하시오.');
		return;
	}
	//openWin("/searchProd.do?_SCREEN=/Budget/WorkItemSearch.jsp?empId="+empId+"&idx="+idx+"&incomeYN="+incomeYN+"&orgCd="+orgCd+"&labCostType="+labCostType,"",600,500);
	openWin("/initJob.do?_SCREEN=/Budget/WorkItemSearch.jsp?empId="+empId+"&idx="+idx+"&incomeYN="+incomeYN+"&orgCd="+orgCd+"&labCostType="+labCostType,"",600,500);
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

function click_BizAcq(param){
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

function chBtype(){
	frm.billType.value=frm.bType[frm.bType.selectedIndex].value;
	if((frm.billType.value=="1A") || (frm.billType.value=="1B")||(frm.billType.value=="1C")||(frm.billType.value=="1D")||(frm.billType.value=="1E"))
	{
		frm.taxAmt.disabled = false;
		frm.taxAmt.style.backgroundColor = "#F0F0FF";
		//frm.supplyAmt.disabled = false;
		//frm.supplyAmt.style.backgroundColor = "#F0F0FF";
		
		if(frm.C1.checked) {
			if(frm.supplyAmt.value!="0"){
			
				frm.totAmt.value = frm.supplyAmt.value;
				amt = parseInt(10*parseInt(trim(removeChar(frm.totAmt.value,","))) / 11)
				frm.supplyAmt.value = amt;
				frm.taxAmt.value = cashReturn(parseInt(trim(removeChar(frm.totAmt.value,","))-parseInt(amt)));
				dataCheckSum(frm.totAmt);
				
			} else {
				frm.taxAmt.value = "0";
			}
		}else{
			if(frm.supplyAmt.value!="0"){
				frm.totAmt.value = frm.supplyAmt.value;
				dataCheckSum(frm.supplyAmt);
			} else {
				frm.taxAmt.value = "0";
			}
		}
		
	} else {
	
		if(frm.C1.checked) {
			//alert("");
			frm.supplyAmt.value = frm.totAmt.value;
			
			frm.taxAmt.value = "0";
			frm.taxAmt.disabled = true;
			frm.taxAmt.style.backgroundColor = "#FFFFFF";
			//dataCheckSum(frm.supplyAmt);
		}else{
			frm.taxAmt.value = "0";
			frm.taxAmt.disabled = true;
			//frm.supplyAmt.style.backgroundColor = "#FFFFFF";
			frm.taxAmt.style.backgroundColor = "#FFFFFF";
			//frm.supplyAmt.disabled = true;
			//frm.supplyAmt.value = frm.totAmt.value;
			dataCheckSum(frm.supplyAmt);
		}
		
	}
}

function iChk(){
	frm.domicile.value = "2";
	//?????????? 77?????? ????????.
	if(frm.bizAcqAcctCd.value==""){
		//frm.bizAcqAcctCd.value = "77"+frm.useOrgUserCd.value;
		//frm.bizAcqName.value = frm.useOrgName.value;
		if(frm.pjtOrg_Cd.value != ''){
		frm.bizAcqAcctCd.value = "77"+frm.pjtOrg_Cd.value;
		frm.bizAcqName.value = frm.pjtOrg_Name.value;
		frm.bizAcqCdName.value = frm.bizAcqAcctCd.value+" "+frm.bizAcqName.value;
		frm.bizCeoName.value = "";
		frm.bizAcqRegNo.value = "999-99-99999";
		}else{
			alert("부서/PJT 명을 먼저 선택하세요");
		}
	}
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

function chg_cdType(i){

	if(frm.acctCd[i].value.substring(0,1)=='5'||frm.acctCd[i].value.substring(0,1)=='7'){
		if(frm.cdType[i].value=='C'){
		
		}else{
			alert("비용계정 선택시, 차변을 선택하시기 바랍니다.");
			frm.cdType[i].value = "D";
			document.forms[0].acctCd[i].value = '';
			document.forms[0].acctName[i].value = '';
			document.forms[0].acctCdName[i].value = '';	
		}
	}else if(frm.acctCd[i].value.substring(0,1)=='4'||frm.acctCd[i].value.substring(0,1)=='6'){
		if(frm.cdType[i].value=='D'){
		
		}else{
			alert("수입계정 선택시, 대변을 선택하시기 바랍니다.");
			frm.cdType[i].value = "C";
			document.forms[0].acctCd[i].value = '';
			document.forms[0].acctName[i].value = '';
			document.forms[0].acctCdName[i].value = '';	
		}
	}	
}

function Achk(){

	//alert('frm.dchk[0].checked : '+frm.dchk[0].checked);
	//alert('removeChar(frm.supplyAmt.value,",") : '+removeChar(frm.supplyAmt.value,","));
	if( ( frm.dchk[0].checked == true ) && removeChar(frm.supplyAmt.value,",") >= 100000)
	{
		frm.detail.value = '1) 접대처 및 인원 : \n2) 사   유 : \n3) 일   시 : \n4) 장   소 : \n5) 금   액 : ';
		//alert('2');
		document.getElementById('div_addDetail').style.display = ""; 
		
	}else{
		frm.detail.value = '1) 접대처 및 접대자 : \n2) 사   유 : \n3) 일   시 : \n4) 장   소 : \n5) 금   액 : ';
		//alert('1');
		document.getElementById('div_addDetail').style.display = "none"; 
	}
	//frm.detail.value = '1) 접대처(거래처명) : \n2) 일      시 : \n3) 장      소 : \n4) 금      액 : ';
	frm.detailType.value = "A";
	
	
}

function Bchk(){
	frm.detailType.value = "B";
	frm.detail.value = '1) 취식인원 :         외     명\n2) 일      시 : \n3) 장      소 : \n4) 금      액 : ';
	document.getElementById('div_addDetail').style.display = "none"; 
}

function Cchk(){
	frm.detailType.value = "C";
	frm.detail.value = '';
	document.getElementById('div_addDetail').style.display = "none"; 
}

function checkAcct()
{
	//alert('frm.dchk[0].checked : '+frm.dchk[0].checked);
	//alert('frm.supplyAmt.value : '+frm.supplyAmt.value);
	if( (frm.dchk[0].checked ) && (removeChar(frm.supplyAmt.value,",") >= 100000))
	{
		//document.getElementById('div_addDetail').style.display = ""; 
		Achk();
	}else{
		//document.getElementById('div_addDetail').style.display = "none"; 
		//Achk();
	}
}

function dChange(field){
	var str = field.value;
	field.value = removeChar(str,",");
}

function dataCheckSum(field){
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
		if(field.name=="supplyAmt"){ //??????????????
			if((frm.billType.value=="1A") || (frm.billType.value=="1B")||(frm.billType.value=="1C")||(frm.billType.value=="1D")||(frm.billType.value=="1E")){ //???????????????? ???????? ????????.
				//if(parseInt(trim(removeChar(frm.taxAmt.value,",")))==0){
					amt = parseInt(parseInt(trim(removeChar(frm.supplyAmt.value,","))) / 10)
					frm.taxAmt.value = cashReturn(amt);
				//}
			}else{
						frm.taxAmt.value = "0";
			}
			amt = parseInt(parseInt(trim(removeChar(frm.supplyAmt.value,","))))+
			parseInt(parseInt(trim(removeChar(frm.taxAmt.value,","))))
			frm.totAmt.value = cashReturn(amt);
			frm.realAmt.value = cashReturn(amt);
			
		}
		if(field.name=="taxAmt"){ //???????? ????
			if((frm.billType.value=="1A") || (frm.billType.value=="1B")||(frm.billType.value=="1C")||(frm.billType.value=="1D")||(frm.billType.value=="1E")){ //???????????????? ???????? ????????.
				//amt = parseInt(parseInt(trim(removeChar(frm.supplyAmt.value,","))) / 10)
				//frm.taxAmt.value = cashReturn(amt);
				//if(parseInt(trim(removeChar(frm.totAmt.value,",")))==0){
					amt = parseInt(parseInt(trim(removeChar(frm.supplyAmt.value,","))))+
						  parseInt(parseInt(trim(removeChar(frm.taxAmt.value,","))))
					frm.totAmt.value = cashReturn(amt);
					
					//추가된 로직 2006. 11. 03
					frm.realAmt.value = cashReturn(amt);
				//}
			}
		}

		if(field.name=="totAmt"){ //??????????????
			if((frm.billType.value=="1A") || (frm.billType.value=="1B")||(frm.billType.value=="1C")||(frm.billType.value=="1D")||(frm.billType.value=="1E")){ //???????????????? ???????? ????????.
				if(parseInt(trim(removeChar(frm.supplyAmt.value,",")))==0){
					//alert("test1"+parseInt(trim(removeChar(frm.supplyAmt.value,","))))
					frm.supplyAmt.value = cashReturn(frm.totAmt.value);
									}
			} else {
					frm.supplyAmt.value = cashReturn(frm.totAmt.value);
			}
			if(parseInt(trim(removeChar(frm.realAmt.value,",")))==0){
				frm.realAmt.value = cashReturn(frm.totAmt.value);
			}
		}
		if(parseInt(trim(removeChar(frm.totAmt.value,",")))==0){
			amt = 0;
			amt = parseInt(trim(removeChar(frm.supplyAmt.value,","))) + parseInt(trim(removeChar(frm.taxAmt.value,",")));
			frm.totAmt.value = cashReturn(amt);
			frm.realAmt.value = cashReturn(amt);
		}
		
		if(field.name=="realAmt"){ //?????? ?????? ????????????
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

function certTypeChange(idx){
	frm.certifType[idx].value=frm.certType[idx][frm.certType[idx].selectedIndex].value;
	if((frm.certifType[idx].value=="C")){
		frm.cdApplNo[idx].style.visibility="visible";
	} else {
		frm.cdApplNo[idx].style.visibility="hidden";	
	}
}

function chkChecked(){
	if(frm.C1.checked) {
		frm.pCardYn.value = "Y";
		frm.bizAcqAcctCd.value = "9BC001";
		frm.bizAcqName.value = "비씨카드";
		frm.bizAcqCdName.value = "9BC001 비씨카드";
		frm.bizCeoName.value = "이종호";
		frm.bizAcqRegNo.value = "214-81-37726";
		frm.rdo1[1].checked = true;
		oChk();
		for(var i=0;i<frm.certifType.length;i++){
			frm.certifType[i].value='C';
			frm.certType[i].selectedIndex=1;
			frm.all.cdApplNo[i].style.display="";			
			frm.all.selCard[i].style.display="";			
		}
	} else {
		frm.pCardYn.value = "N";
		frm.bizAcqAcctCd.value = "";
		frm.bizAcqName.value = "";
		frm.bizAcqCdName.value = "";
		frm.bizCeoName.value = "";
		frm.bizAcqRegNo.value = "";
		
		for(var i=0;i<frm.certifType.length;i++){
			frm.certifType[i].value='N';
			frm.certType[i].selectedIndex=0;
			frm.all.cdApplNo[i].style.display="none";	
			frm.all.selCard[i].style.display="none";			
		}
	}
}

function fn_chkDetailType()
{
	if( (frm.dchk[0].checked ) && (removeChar(frm.supplyAmt.value,",") >= 100000))
	{
		document.getElementById('div_addDetail').style.display = ""; 
	}else{
		document.getElementById('div_addDetail').style.display = "none"; 

	}
}
