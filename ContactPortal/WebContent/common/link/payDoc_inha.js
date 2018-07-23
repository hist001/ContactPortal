var isSave = "";
var vDocNo = "";
var vMainNo = "";

//VARCHAR2 
var varNames = new Array()
var varLengs = new Array()

//NUMBER 
var numNames= new Array('TOTAMT','TAXAMT','AMOUNT','SUPPLAYAMT','REALAMT','DIFFAMT','REALAMT','DIFFAMT','ADVANCEAMT');
var DATA_PRECISION= new Array(20,20,20,20,20,20,20,20,20);
var DATA_SCALE= new Array(10,10,10,10,10,10,10,10,10);

//VCHAR DT 
var vdtNames = new Array('EVENTDT','REQDT','RECIVEDT')
var vdtLengs = new Array('10','10','10')

var nullChkAttribute = new Array('EVENTDT','USEEMPNAME','TOTAMT','BIZACQCDNAME','TOTAMT','MTITLE','DETAIL','ORGNAME','ACCTCDNAME','AMOUNT','BIZACQACCTCD','PHONNO');
var nullChkName = Array('발생일자','담당자','지급총액','거래처','지급총액','제목','지급내역','부서명','계정과목','금액', '거래처','전화번호');

function fn_chkAccItem()
{
	var bReturn = true;
	var frm = document.forms[0];
	
	if(frm.AMOUNT.length == undefined || frm.AMOUNT.length == 1)
	{
		if(frm.AMOUNT.length == undefined){
			if(frm.ORGNAME.value=='' || frm.ORGCD.value=='' || frm.ORGNAME.value=='' || frm.ORGCD.value=='' || frm.TITLE.value=='')
			{
				bReturn = false;
			}
			
		}else if(frm.AMOUNT.lengt == 1){
			if(frm.ORGNAME[0].value == '' || frm.ORGCD[0].value == '' || frm.ORGNAME[0].value == '' || frm.ORGCD[0].value == '' || frm.TITLE[0].value == '')
			{
				bReturn = false;
			}
		}
	}else{
		for(var i=0; i<frm.AMOUNT.length; i++)
		{
			if(frm.ORGNAME[i].value == '' || frm.ORGCD[i].value == '' || frm.ORGNAME[i].value == '' || frm.ORGCD[i].value == '' || frm.TITLE[i].value == '')
			{
				bReturn = false;
			}	
		}
	}	
	
	if(bReturn == false)
	{
		alert('부서명, 계정과목, 적요는 필수입력사항입니다.');
	}
	
	return bReturn;
}

function fn_showTotAmtToAmount()
{
	var frm = document.forms[0];

	if(frm.AMOUNT.length == undefined && frm.isUPT.value == 'N'){
		frm.AMOUNT.value = cashReturn(removeChar(frm.TOTAMT.value,','));
	}
	
	if(frm.BILLTYPE.value == '2')
	{
		frm.SUPPLYAMT.value = cashReturn(removeChar(frm.TOTAMT.value,','));
		frm.REALAMT.value = cashReturn(removeChar(frm.TOTAMT.value,','));
		//frm.MTITLE.focus();
	}
}

function fn_showMTitleToTitle()
{
	var frm = document.forms[0];
	if(frm.MTITLE.value != '')
	{
		if(frm.ACCTCD.length != undefined)
		{
			frm.TITLE[0].value = frm.MTITLE.value;	
		}else{
			frm.TITLE.value = frm.MTITLE.value;
		}
	}
}


function fn_showAcctHdToAcctCd()
{
	var frm = document.forms[0];
	if(frm.ACCTHCD.value != '00000')
	{
		if(frm.ACCTCD.length != undefined)
		{
			frm.ACCTCD[0].value = frm.ACCTHCD.value;
			frm.ACCTNAME[0].value = frm.ACCTHCD.value;
			frm.ACCTCDNAME[0].value = frm.ACCTHCD.options[frm.ACCTHCD.selectedIndex].text;		
		}else{
			frm.ACCTCD.value = frm.ACCTHCD.value;
			frm.ACCTNAME.value = frm.ACCTHCD.value;
			frm.ACCTCDNAME.value = frm.ACCTHCD.options[frm.ACCTHCD.selectedIndex].text;		
		}
	}
}

function fn_cashReturn(obj)
{
	var str = obj.value;
	obj.value = cashReturn(removeChar(str,","));
}

	

function fn_CheckAmt()
{
	var frm = document.forms[0];
	if(frm.BILLTYPE.value=='1')
	{
		frm.TAXAMT.value = cashReturn(parseInt(trim(removeChar(frm.TOTAMT.value,","))) - parseInt(trim(removeChar(frm.SUPPLYAMT.value,","))));
	}

	if(frm.ACCTCD.length == undefined)
	{
		frm.AMOUNT.value = frm.TAXAMT.value;
	}else{
		frm.AMOUNT[0].value = frm.TAXAMT.value;
	}
}

function fn_errCheck()
{
	var frm = document.forms[0];
	if(!isEventDtOk()){ return false; }
	if(!isTotAndSumSame(frm.DOCTYPE.value)){ return false; }
	
	return true;
}

function isTenRow()
{
	var frm = document.forms[0];
	var amtLength = 0;
	var bReturn = true;
	if(frm.AMOUNT.length != undefined)
	{
		amtLength = frm.AMOUNT.length;
	}

	if(amtLength >= 10)
	{
		bReturn = false;
	}
	
	return bReturn;
}


function isTotAndSumSame(docType)
{
	var frm = document.forms[0];
	var sumAmt = 0;
	var bReturn = true;
	
	//alert('docType : '+docType);
	if(docType == 'B'){
		if(frm.AMOUNT.length != undefined)
		{
			for(var i=0;i<frm.AMOUNT.length;i++)
			{
				if(frm.cdType[i].value == 'D'){
					sumAmt = sumAmt + parseInt(trim(removeChar(frm.AMOUNT[i].value,",")));
				}
			}
		}else{
				//alert('frm.cdType.value : '+frm.cdType.value);
				if(frm.cdType.value == 'D'){
					sumAmt = parseInt(trim(removeChar(frm.AMOUNT.value,",")));
				}
		}
	}else{
		if(frm.AMOUNT.length != undefined)
		{
			for(var i=0;i<frm.AMOUNT.length;i++)
			{
				if(frm.cdType[i].value == 'C'){
					sumAmt = sumAmt + parseInt(trim(removeChar(frm.AMOUNT[i].value,",")));
				}
			}
		}else{
				if(frm.cdType.value == 'C'){
					sumAmt = parseInt(trim(removeChar(frm.AMOUNT.value,",")));
				}
		}
	}
	
	//alert('sumAmt : '+sumAmt);
	//alert('frm.TOTAMT.value : '+parseInt(trim(removeChar(frm.TOTAMT.value,","))));
	if(parseInt(trim(removeChar(frm.TOTAMT.value,",")))!=sumAmt)
	{
		if(confirm('회계처리 내용의 금액이 총액과 다릅니다. 작성완료 하시겠습니까?')){
			bReturn=true;
		}else{
			bReturn=false;
		}
	}

	return bReturn;
}

function isEventDtOk()
{
	var frm = document.forms[0];
	var iRtn = removeChar(frm.EVENTDT.value,'-','');
	var iExpirDate = removeChar(frm.EXPIREDATE.value,'-');


	if( iRtn  - iExpirDate >=0)
	{
		if(iCurDate-iRtn >=0){
		
		}else{
			alert('발생일자는 현재일보다  과거이어야  합니다.');
			return false;
		}	
	}else{
		if(iRtn != '')
		{
			alert('마감일 이후에는 작성이 불가능 합니다.');
		}else{
			alert('발생일자는 필수입력입니다.');
		}
		return false;
	}

	return true;
}

function isSaveOk()
{
	//alert('isSaveOk');
	var bReturn = false;
	var sChkValue ="";
	var res = xmlHttp.responseXML; 

	//alert(res.getElementsByTagName("SAVERST")[0].firstChild.nodeValue);
	var rstValue = res.getElementsByTagName("SAVERST"); 
	var rstDocNo = res.getElementsByTagName("DOCNO");
	var rstMainNo = res.getElementsByTagName("MAINNO");
	
	//alert('xmlHttp.responseText : '+xmlHttp.responseText);
	//alert('rstExecBudget : '+rstExecBudget);
	//alert('rstUsedBudget : '+rstUsedBudget);
	//alert('rstBudgetRate : '+rstBudgetRate);
	
	//alert('vValue : '+ rstValue.length );
	var vValue = rstValue[0].firstChild.data;
	//var vValue = rstValue[0].firstChild.nodeValue;
	//alert('rstDocNo[0] : '+rstDocNo[0].firstChild);
	
	if(rstMainNo[0].firstChild != null  ){
		vMainNo = rstMainNo[0].firstChild.data;
	}
	
	if(rstDocNo[0].firstChild != null  ){
		vDocNo = rstDocNo[0].firstChild.data;
	}
	bReturn = true;
	
	return bReturn;
}

function showDocContent_Inha()
{
	var frm = document.forms[0];
	
	frm.DOCNO.value = vDocNo;
	frm.MAINNO.value = vMainNo;
}


function execSaveRst() {
		//alert(frm.STATUS.value);

		if(frm.STATUS.value =='DB0'){
			frm.action = '/HanwayLink.do';
			frm._SCREEN.value = 'SendOk.jsp';
			frm.target='_self';
			frm.submit();
			//self.close();
		}else{
			frm.action = '/common/saveOk.jsp';
			frm.submit();		
		}
		
		/*
		if(frm.STATUS.value=='DA0'){
			frm.action = '/common/saveOk.jsp';
			frm.submit();
		}else if(frm.STATUS.value =='DB0'){
			frm.action = '/HanwayLink.do';
			frm._SCREEN.value = 'SendOk.jsp';
			frm.target='_self';
			frm.submit();
			//self.close();
		}*/
}

function getSaveRst() {

	var frm=document.forms[0];
	
	var bReturn = true;
    if(xmlHttp.readyState == 4) {
        if(xmlHttp.status == 200) {
        	if(isSaveOk())
        	{
				//alert('OK');
				showDocContent_Inha();
				execSaveRst();
			}else{
				alert('저장에 실패했습니다.');
			}
        }
    }
}

function getPageInfo()
{
	var Frm = document.forms[0];

    for(var i=0; i<Frm.elements.length; i++){ 
        if(i < Frm.elements.length-1){ 
            postData += Frm.elements[i].name + "=" + encodeURIComponent(Frm.elements[i].value) + "&"; 
        }else if(i == Frm.elements.length-1){ 
            postData += Frm.elements[i].name + "=" + encodeURIComponent(Frm.elements[i].value); 
        } 
    } 
    return postData;    
}

function createXMLHttpRequest() {
    if (window.ActiveXObject) {
        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
    } 
    else if (window.XMLHttpRequest) {
        xmlHttp = new XMLHttpRequest();
    }
}

function AcctRptSave_Ajax(pUrl)
{
	createXMLHttpRequest();
	xml = getPageInfo();
	
	//alert('xml : '+xml);
	
	postData ="";
	var url = "/"+pUrl;
	//alert('url : '+url);
	xmlHttp.open("POST", url, true);
	xmlHttp.onreadystatechange = getSaveRst; 
    xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
	xmlHttp.setRequestHeader("Cache-Control", "no-cache");
	xmlHttp.setRequestHeader("Pragma", "no-cache");

	xmlHttp.send(xml);
}

function com_function(div,it_no){
	//var no = 0;
	//alert('it_no : '+it_no);
	if(div=='BIZ'){
		popupSearchN('biz_new','biz_'+it_no,'BIZCD','BIZCD','BIZNAME','BIZTYPECD','','','600',it_no+"");

	}else{
		var v_div=document.all("SN");
		var idx=0;
		if(v_div!=undefined){
			if(v_div.length!=undefined){
				for(var i=0;i<v_div.length;i++){
					if(v_div[i].value==it_no){
						//no = i;
						idx=i;break;
					}
				}
			}
		}
		if(div=='org'){		
			popupSearchN('org','','ORGCD','ORGUSERCD','ORGNAME','','','700','600', i ,'');
		}else if(div=='acct'){
			popupSearchN('acct','','ACCTCD','ACCTNAME','ACCTCDNAME','','','','600',i, '');
		}else if(div=='acct_b'){
			popupSearchN('acct_b','','ACCTCD','ACCTNAME','ACCTCDNAME','','','','600',i, '');
		}else if(div=='acct_b_etc'){
			popupSearchN('acct_b_etc','','ACCTCD','ACCTNAME','ACCTCDNAME','','','','600',i, '');
		}else if(div=='cha'){
			cha(i);
		}else if(div=='dea'){
			dea(i);
		}
	}
	
}

function dbclick() 
{
    if(event.button==1) alert("잠시 기다려 주십시요. 현재 처리중입니다.");  
}

function fn_chkUser() 
{
	var frm = document.forms[0];
	
	if(frm.USEEMPID.value.length!=7)
	{
		frm.USEEMPNO.value = '';
		frm.USEEMPID.value = '';
		frm.USEEMPNAME.value = '';
	}
}