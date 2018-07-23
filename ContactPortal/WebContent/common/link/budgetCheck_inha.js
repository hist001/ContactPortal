var xmlHttp;
var xml ="";
var postData  = ""; 
var strBdChkRst = ""; 
var isCeoCheck = "N";
var isBdgChkOk = "N";

function budgetCheck()
{
	var frm = document.forms[0];
	if(frm.ACCTHCD.value != '0000')
	{
		budgetCheck_Ajax('BudgetCheck_Inha.do',getBudgetCheckRst);
	}
}


function budgetCheck_Ajax(pUrl,pFn)
{
	createXMLHttpRequest();
	xml = getPageInfo();
	//alert('xml : '+xml);
	//alert('pUrl : '+pUrl +'    pFn : '+pFn);
	postData ="";
	//var url = "/BudgetCheck_Inha.do";
	var url = "/"+pUrl;
	xmlHttp.open("POST", url, false);
    //xmlHttp.onreadystatechange = getBudgetCheckRst;  
	xmlHttp.onreadystatechange = pFn;  

    xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
	xmlHttp.setRequestHeader("Cache-Control", "no-cache");
	xmlHttp.setRequestHeader("Pragma", "no-cache");
    
    //escape(encodeURIComponent(xml));
    
	xmlHttp.send(xml);
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


function getBudgetCheckRst_inha() {

	var frm=document.forms[0];
	var bReturn = true;
	
	//alert('xmlHttp.readyState : '+xmlHttp.readyState);
    if(xmlHttp.readyState == 4) {
    	//alert('xmlHttp.status : '+xmlHttp.status);
        if(xmlHttp.status == 200) {
        	
			if(!isBudgetCheckOk())
			{
				isBdgChkOk = "N";	
				openWin("/Confer/BudgetMgt/budgetCheckRst.jsp"+strBdChkRst,"",500,300);
			}else{
				isBdgChkOk = "Y";
				if(frm.isUPT.value == 'N')
				{
					//bRst = save('N','DB0');
					bRst = send('N','DB0');
				}else{
					//bRst = save('Y','DB0');
					bRst = send('Y','DB0');
				}
			}
			
        }
    }
}


function getBudgetCheckRst() {

	var frm=document.forms[0];
	var bReturn = true;
	
    if(xmlHttp.readyState == 4) {
        if(xmlHttp.status == 200) {
        	//alert('OK');
			if(!isBudgetCheckOk())
			{
				isBdgChkOk = "N";	
				openWin("/Confer/BudgetMgt/budgetCheckRst.jsp"+strBdChkRst,"",500,300);
			}else{
				isBdgChkOk = "Y";
				
			}
			
        }
    }
}

function getBudgetCheckRstForSend() {

	var frm=document.forms[0];
	
	var bReturn = true;
    if(xmlHttp.readyState == 4) {
        if(xmlHttp.status == 200) {
        	//alert('OK');
			if(!isBudgetCheckOk())
			{
				showAcctBudgetContent_Inha();
				isBdgChkOk = "N";	
				openWin("/Confer/BudgetMgt/budgetCheckRst.jsp"+strBdChkRst,"",500,300);
			}else{
				isBdgChkOk = "Y";
				var Rst ='';

				if(frm.isUPT.value == 'N')
				{
					bRst = save('N','DB0');
				}else{
					bRst = save('Y','DB0');
				}
			}
			
        }
    }
}

function isBudgetCheckOk()
{
	var bReturn = true;
	var sChkValue ="";
	var res = xmlHttp.responseXML; 

	var rstValue = res.getElementsByTagName("VALUE"); 
	var rstIdx = res.getElementsByTagName("IDX");
	var rstKey = res.getElementsByTagName("KEY");
	var rstMsg = res.getElementsByTagName("MSG");
	
	var rstSn = res.getElementsByTagName("SN"); 
	var rstExecBudget = res.getElementsByTagName("EXECBUDGET");
	var rstUsedBudget = res.getElementsByTagName("USEDBUDGET");
	var rstRemBudget = res.getElementsByTagName("REMBUDGET");
	var rstBudgetRate = res.getElementsByTagName("BUDGETRATE");
	
	//alert('rstSn : '+rstSn);
	//alert('rstSn.length : '+rstSn.length);
	//alert('rstValue : '+rstValue);
	//alert('xmlHttp.responseXML : '+xmlHttp.responseText);
	//alert('rstExecBudget : '+rstExecBudget);
	//alert('rstUsedBudget : '+rstUsedBudget);
	//alert('rstBudgetRate : '+rstBudgetRate);
	
	for(i=0;i<rstValue.length;i++)
	{
		if(rstValue[i].firstChild.data=='N'){
		
			if(rstKey[i].firstChild.data=='BB'){
				isCeoCheck = 'Y';
			}else{
				bReturn= false;
				sChkValue = sChkValue+'&SN='+rstIdx[i].firstChild.data+'&KEY='+rstKey[i].firstChild.data+'&MSG='+rstMsg[i].firstChild.data;

			}
			
		}		
	}
	strBdChkRst = '?ceoCheck='+isCeoCheck+sChkValue;
	//showAcctBudgetContent(rstSn,rstExecBudget,rstUsedBudget,rstBudgetRate);
	
	//alert('pfn : '+pfn);
	showAcctBudgetContent_Inha(rstSn,rstExecBudget,rstUsedBudget,rstBudgetRate, rstRemBudget);
	//alert('bReturn : '+bReturn);
	return bReturn;
}

function showAcctBudgetContent_Inha(rstSn,rstExecBudget,rstUsedBudget,rstBudgetRate,rstRemBudget)
{
	var frm = document.forms[0];
	
	//frm.TOTBUDGET.value = rstExecBudget.firstChild[0].data;
	//frm.TOTEXEAMT.value = rstUsedBudget.firstChild[0].data;
	//frm.BUDGETUSEDAMTTMP.value = rstUsedBudget.firstChild.data;;
	//frm.TOTBUDGETRATE.value = rstBudgetRate.firstChild.data;
	
	//alert('rstSn.length : '+rstSn.length);
	
	if(rstSn.length>=1)
	{
		for(i=0;i<rstSn.length;i++)
		{
			frm.TOTBUDGET.value = cashReturn(rstExecBudget[i].firstChild.data);
			frm.TOTEXEAMT.value  = cashReturn(rstUsedBudget[i].firstChild.data);
			//frm.BUDGETUSEDAMTTMP[i].value = rstUsedBudget[i].firstChild.data;
			frm.TOTBUDGETRATE.value  = rstBudgetRate[i].firstChild.data;
			frm.TOTREMBUDGET.value = cashReturn(rstRemBudget[i].firstChild.data);
		}
	
	}else{

			//frm.TOTBUDGET.value = rstExecBudget[0].firstChild.data;
			//frm.TOTEXEAMT.value  = rstUsedBudget[0].firstChild.data;
			////frm.BUDGETUSEDAMTTMP.value = rstUsedBudget[0].firstChild.data;;
			//frm.TOTBUDGETRATE.value = rstBudgetRate[0].firstChild.data;
			
			frm.TOTBUDGET.value = 0;
			frm.TOTEXEAMT.value  = 0;
			//frm.BUDGETUSEDAMTTMP.value = 100;;;
			frm.TOTBUDGETRATE.value = 0;
	}
}

function showAcctBudgetContent(rstSn,rstExecBudget,rstUsedBudget,rstBudgetRate)
{
	var frm = document.forms[0];
	if(rstSn.length>1)
	{
		for(i=0;i<rstSn.length;i++)
		{
			frm.BUDGETAMT[i].value = rstExecBudget[i].firstChild.data;
			frm.BUDGETUSEDAMT[i].value = rstUsedBudget[i].firstChild.data;
			frm.BUDGETUSEDAMTTMP[i].value = rstUsedBudget[i].firstChild.data;
			frm.BUDGETRATE[i].value = rstBudgetRate[i].firstChild.data;
		}
	
	}else{

			frm.BUDGETAMT.value = rstExecBudget[0].firstChild.data;
			frm.BUDGETUSEDAMT.value = rstUsedBudget[0].firstChild.data;
			frm.BUDGETUSEDAMTTMP.value = rstUsedBudget[0].firstChild.data;;
			frm.BUDGETRATE.value = rstBudgetRate[0].firstChild.data;
	}
	
}