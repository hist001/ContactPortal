var xmlHttp;
var xml ="";
var postData  = ""; 

function pop_AcctPrint(fnFlag,param,param2,idx,mode,pa1,pa2,pa3,pa4,pa5)
{
	frm=document.forms[0];
	var bRetrun = 'Y';
	var cnt = 0;
	
	if(fnFlag == 'acct'){
		searchItem(param2,idx,mode);
		
	}else if(fnFlag == 'bizacq'){

		if(frm.acctCd.length == 'undefined'){
			cnt = 1;
		}else{
			cnt = frm.acctCd.length;
		}
		
		for(i=0;i<cnt;i++){
			if(frm.acctCd[i].value =='50239'){
				bRetrun = 'N';
			}
		}
		
		if(bRetrun == 'Y')
		{
			click_BizAcq(param);
			//popupSearchN('bizAcq_N','pjt_6',pa1,pa2,pa3,pa4,pa5);
		}else{
			popupSearchN('bizAcq_acctCd','pjt_6',pa1,pa2,pa3,pa4,pa5);
		}
	}
	
}


function chkBizacqAcctcd()
{
	createXMLHttpRequest();
	xml = getPageInfo();
	postData ="";
	var url = "/chkBizacqAcctcd.do";
	
	xmlHttp.open("POST", url, false);
    xmlHttp.onreadystatechange = setAcctPrintFlag;
    xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");  

	xmlHttp.send(xml);
	
}

function setAcctPrintFlag() {

	var frm=document.forms[0];
	
	var bReturn = true;
    if(xmlHttp.readyState == 4) {
        if(xmlHttp.status == 200) {
			//alert("frm.BIZACQACCTFLAG.value : " + frm.BIZACQACCTFLAG.value);
			//alert("displayChkFlag() : " + displayChkFlag());
			frm.BIZACQACCTFLAG.value = displayChkFlag2();
        }
    }
    
    return bReturn;
}

function displayChkFlag2()
{
	var bReturn = true;
	var res = xmlHttp.responseXML; 
	var rXml = res.getElementsByTagName("BAFLAG"); 
	var rSn = res.getElementsByTagName("SN");
	
	for(i=0;i<rXml.length;i++)
	{
		if(rXml[i].firstChild.data=='N'){
			bReturn= false;
		}		
	}
	
	return bReturn;
}

function createXMLHttpRequest() {
    if (window.ActiveXObject) {
        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
    } 
    else if (window.XMLHttpRequest) {
        xmlHttp = new XMLHttpRequest();
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

function checkBizacqAcctcd()
{
	createXMLHttpRequest();
	xml = getPageInfo();
	postData ="";
	var url = "/chkBizacqAcctcd.do";
	
	xmlHttp.open("POST", url, false);
    xmlHttp.onreadystatechange = setUseFlag;
    xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");  

	xmlHttp.send(xml);
	
}

function setUseFlag() {

	var frm=document.forms[0];
	
	var bReturn = true;
    if(xmlHttp.readyState == 4) {
        if(xmlHttp.status == 200) {
			//alert("frm.BIZACQACCTFLAG.value : " + frm.BIZACQACCTFLAG.value);
			//alert("displayChkFlag() : " + displayChkFlag());
			frm.BIZACQACCTFLAG.value = displayChkFlag();
        }
    }
}

function displayChkFlag()
{
	var bReturn = true;
	var res = xmlHttp.responseXML; 
	var rXml = res.getElementsByTagName("BAFLAG"); 
	var rSn = res.getElementsByTagName("SN");
	
	for(i=0;i<rXml.length;i++)
	{
		if(rXml[i].firstChild.data=='N'){
			//rSn[i].firstChild.data
			alert("도서인쇄비(50239) 계정 사용시 지정된 업체(하나문화사,일흥인쇄(주))를 거래처로 선택하시기 바랍니다.\n업체변경, 추가를 원하시면 담당자(송재일)에게 연락하시기 바랍니다.");
			bReturn= false;
		}		
	}
	
	return bReturn;
}