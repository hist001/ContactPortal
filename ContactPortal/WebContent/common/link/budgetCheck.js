var xmlHttp;
var xml ="";
var postData  = ""; 
var strBdChkRst = ""; 
var isCeoCheck = "N";
var isBdgChkOk = "N";

var loopChk = 0;

function budgetCheck()
{
	var frm = document.forms[1];

	if(frm.EVENTDT.value == ''){
		alert("�߻����ڸ� �Է��Ͻʽÿ�.");
		return false;
	}else if(frm.SUPPLYAMT.value == ''){
		alert("���ް����� �Է��Ͻʽÿ�.");
		return false;
	}else if(frm.ACCTCD.value == ''){
		alert("ȸ������ڵ带 �Է��Ͻʽÿ�.");
		return false;
	}else if(frm.ORGCD.value == ''){
		alert("����μ��� �Է��Ͻʽÿ�.");
		return false;
	}else if(frm.BUDGETUSEDAMT.value ==''){
		alert("����ݾ��� �Է��Ͻʽÿ�.");
		return false;
	}else{
		fn_validateForm();
		budgetCheck_Ajax('BudgetCheck_PaySlip.do',getBudgetCheckRst);
	}
}


function budgetCheck_Ajax(pUrl,pFn)
{
	divSearch('����üũ�� �Դϴ�.');
	//$('#processing').show();
	createXMLHttpRequest();
	xml = getPageInfo();
	//alert('xml : '+xml);
	//alert('pUrl : '+pUrl +'    pFn : '+pFn);
	postData ="";
	//var url = "/BudgetCheck_Inha.do";
	var url = "/"+pUrl;
	xmlHttp.open("POST", url, false);
	xmlHttp.onreadystatechange = pFn;  

    xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
	xmlHttp.setRequestHeader("Cache-Control", "no-cache");
	xmlHttp.setRequestHeader("Pragma", "no-cache");

    
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
	var Frm = document.forms[1];
	//alert('3');
	//alert(Frm.BUDGETUSEDAMT[0].value);
    for(var i=0; i<Frm.elements.length; i++){ 
        if(i < Frm.elements.length-1){ 
            postData += Frm.elements[i].name + "=" + encodeURIComponent(Frm.elements[i].value) + "&"; 
        }else if(i == Frm.elements.length-1){ 
            postData += Frm.elements[i].name + "=" + encodeURIComponent(Frm.elements[i].value); 
        } 
    } 
    return postData;    
}


	function getBudgetCheckRst_Hist() {
	
		var frm=document.forms[1];
		var bReturn = true;
		var cardYnCheck = false;
		
		//alert('xmlHttp.readyState : '+xmlHttp.readyState);
	    if(xmlHttp.readyState == 4) {
	    	//alert('xmlHttp.status : '+xmlHttp.status);
	        if(xmlHttp.status == 200) {
	        	
				if(!isBudgetCheckOk())
				{
					isBdgChkOk = "N";	
					$('#processing').hide();
					//openWin("/Confer/BudgetMgt/budgetCheckRst.jsp"+strBdChkRst,"",500,300);
					openWin("/Slip/budgetCheckRst.jsp"+strBdChkRst,"",500,300);
				}else{
					isBdgChkOk = "Y";
					
					var tmpStr = '';
					
					if(frm.ACCTCD.length != undefined){
						for(var i=0; i<frm.ACCTCD.length; i++){
							if(frm.ACCTCD[i].value.substring(0, 4) == '5013' || frm.ACCTCD[i].value.substring(0, 4) == '5023'){ 	// üũ ���� : �޽ĺ�, �����
								cardYnCheck = true;
								if(frm.CERTIFTYPE[i].value == 'C'){		// ����ī���ΰ��
									tmpStr = tmpStr + frm.CERTIFNO[i].value + ',' + frm.CDAPPLNO[i].value;
									
									if(frm.ACCTCD.length-1 != i){
										tmpStr = tmpStr + ',';
									}	
								}
							}
						}
					}else{
						if(frm.ACCTCD.value.substring(0, 4) == '5013' || frm.ACCTCD.value.substring(0, 4) == '5023'){ 	// üũ ���� : �޽ĺ�, �����
							cardYnCheck = true;
							tmpStr = tmpStr + frm.CERTIFNO.value + ',' + frm.CDAPPLNO.value;
							
							if(frm.CERTIFTYPE.value == 'C'){		// ����ī���ΰ��
								if(frm.ACCTCD.length-1 != i){
									tmpStr = tmpStr + ',';
								}
							}
						}
					}
					
					var form_data = {tmp: tmpStr};
					
					if(!isClick){				// �ι�° �ۼ��Ϸ� ��ư Ŭ���� �������� ��� üũ ����
						isClick = true;			// �ۼ��Ϸ� Ŭ����(ù ��°)
						if(cardYnCheck){		// �޽�, ������ ��� üũ
							cardUsedPreCheck(form_data);			// ����ī�� ���� üũ

							//cardUsedCheck(form_data);				// ����ǰ�� ���� üũ	
						}else{
							if(frm.isUPT.value == 'N')
							{
								bRst = send('N','DB0');
							}else{
								bRst = send('Y','DB0');
							}	
						}						
					}else{
						if(frm.isUPT.value == 'N')
						{
							bRst = send('N','DB0');
						}else{
							bRst = send('Y','DB0');
						}
					}
				}
	        }
	    }
	}

	// ����ī�� �������� ��� �߰�
	function cardUsedCheck(form_data){
			$.ajax({
		    type: "POST",
		    data: form_data,
		    url:"cardUsedCheck_PaySlip.do",
		    dataType:'json', 
		    success: function(data) {
		              for(var i=0; i<data.length; i++){
		            	  test(data[i].SN, data[i].MSG, form_data);
		              }
		    },
		    error : function(xhr, status, error) {
				alert("�����߻�"+error);
			}
			});
		
	}
	
	function cardUsedPreCheck(form_data){
			$.ajax({
		    type: "POST",
		    data: form_data,
		    url:"cardUsedPreCheck_PaySlip.do",
		    dataType:'json', 
		    success: function(data) {
		              for(var i=0; i<data.length; i++){
		            	  test(data[i].SN, data[i].MSG, form_data);
		              }
		    },
		    error : function(xhr, status, error) {
				alert("�����߻�"+error);
			}
			});
		
	}
	
	function openPop(url, winName, sizeW, sizeH)
	{
		var popup;
		var nLeft = screen.width/2 - sizeW/2 ;
		var nTop  = screen.height/2 - sizeH/2 ;
		opt = ",toolbar=no,menubar=no,location=no,status=no,resizable=yes,scrollbars=yes";
		popup = window.open(url, winName, "left=" + nLeft + ",top=" +  nTop + ",width=" + sizeW + ",height=" + sizeH  + opt );
		
		return popup;
	}
	
	function preCheckPaylistSave(form_data){
		$.ajax({
		    type: "POST",
		    data: form_data,
		    url:"preCheckPayslipSave.do",
		    dataType:'json', 
		    success: function(data) {
		              for(var i=0; i<data.length; i++){
		            	  test(data[i].SN, data[i].MSG, form_data);
		              }
		    },
		    error : function(xhr, status, error) {
				alert("�����߻�"+error);
			}
			});
	}
		
		function test(a,b, form_data){
			loopChk++;
			
			var chkCount = 0;					// ������ üũ�� �� ���� �Ǿ����� Ȯ��
			if(a=='A' && b!='0'){
				$('#processing').hide();
				isChk = false;					// �ѹ��̶� �������˿� �ɸ��� �׸��� �ִٸ� ���� �Ұ�
				chkCount = chkCount + 1;

				alert(" ���� ī�带 �� ��ҿ��� ����� ������ \r\n�����Ƿ� Ȯ���� �ּ���.(2�ð� ����)");
				openWin('/Slip/sameCardOneLocCheck.jsp?key='+form_data.tmp,"",800,460);
				
			}else if(a=='B' && b!='0'){
				$('#processing').hide();
				chkCount = chkCount + 1;
				isChk = false;

				alert(" ���� ��ҿ��� �ٸ� ī�尡 ���� ������ \r\n�����Ƿ� Ȯ���� �ּ���.(30�� ����)");
				openWin('/Slip/otherCardOneLocCheck.jsp?key='+form_data.tmp,"",800,460);
			}else if(a=='C' && b!='0'){
				$('#processing').hide();
				isChk = false;
				chkCount = chkCount + 1;
				
				alert(" ���� ī�带 �ٸ� ��ҿ��� ����� ������ \r\n�����Ƿ� Ȯ���� �ּ���.(2�ð� ����)");
				openWin('/Slip/sameCardOtherLocCheck.jsp?key='+form_data.tmp,"",800,460);
			}
			
			if(loopChk == 3 && isChk == true){
				if(frm.isUPT.value == 'N')
				{
					bRst = send('N','DB0');
				}else{
					bRst = send('Y','DB0');
				}
			}else{
				return false;
			}
		}
		
		function test1(a,b, form_data){
			
			var chkCount = 0;					// ������ üũ�� �� ���� �Ǿ����� Ȯ��
			if(a=='A' && b!='0'){
				$('#processing').hide();
				isChk = false;					// �ѹ��̶� �������˿� �ɸ��� �׸��� �ִٸ� ���� �Ұ�
				chkCount = chkCount + 1;
				if(confirm(" ���� ī�带 �� ��ҿ��� ����� ������ \r\n�����Ƿ� Ȯ���� �ּ���.(2�ð� ����)")){
					
//				    window.open('','popupView','');   
//				     
//				    document.form1.action = "/Slip/sameCardOneLocCheck.jsp"; 
//				    document.form1.target = 'popupView'; //window,open()�� �ι�° �μ��� ���ƾ� �ϸ� �ʼ���.   
//				    document.form1.CERTIFNO.value = "4289099302261074";
//				    document.form1.CDAPPLNO.value = "03939928";
//				    document.form1.submit(); 

					openWin('/Slip/sameCardOneLocCheck.jsp?key='+form_data.tmp,"",800,460);
				}
			}else if(a=='B' && b!='0'){
				$('#processing').hide();
				chkCount = chkCount + 1;
				isChk = false;
				if(confirm(" ���� ��ҿ��� �ٸ� ī�尡 ���� ������ \r\n�����Ƿ� Ȯ���� �ּ���.(30�� ����)")){
					openWin('/Slip/otherCardOneLocCheck.jsp?CERTIFNO='+form_data.CERTIFNO+'&CDAPPLNO='+form_data.CDAPPLNO,"",800,460);	
				}
			}else if(a=='C' && b!='0'){
				$('#processing').hide();
				isChk = false;
				chkCount = chkCount + 1;
				if(confirm(" ���� ī�带 �ٸ� ��ҿ��� ����� ������ \r\n�����Ƿ� Ȯ���� �ּ���.(2�ð� ����)")){
					openWin('/Slip/sameCardOtherLocCheck.jsp?CERTIFNO='+form_data.CERTIFNO+'&CDAPPLNO='+form_data.CDAPPLNO,"",800,460);
				}
			}
			if(isChk == true && chkCount == 3){
				if(frm.isUPT.value == 'N')
				{
					bRst = send('N','DB0');
				}else{
					bRst = send('Y','DB0');
				}
			}else{
				return false;
			}
			
		}

	
function getBudgetCheckRst() {

	var frm=document.forms[1];
	var bReturn = true;
	
    if(xmlHttp.readyState == 4) {
        if(xmlHttp.status == 200) {
        	//alert('OK');
			if(!isBudgetCheckOk())
			{
				isBdgChkOk = "N";	
				$('#processing').hide();
				openWin("/Slip/budgetCheckRst.jsp"+strBdChkRst,"",500,300);
				
			}else{
				isBdgChkOk = "Y";
				$('#processing').hide();
			}
			
        }
    }
}

function getBudgetCheckRstForSend() {

	var frm=document.forms[1];
	
	var bReturn = true;
    if(xmlHttp.readyState == 4) {
        if(xmlHttp.status == 200) {
        	//alert('OK');
			if(!isBudgetCheckOk())
			{
				showAcctBudgetContent_Inha();
				$('#processing').hide()
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
	//alert('isBudgetCheckOk');
	var bReturn = true;
	var sChkValue ="";
	var res = xmlHttp.responseXML;
	var resTXT = xmlHttp.responseText;
	//alert('res : '+res);
	//alert('resTXT : '+resTXT);
	var rstSn = res.getElementsByTagName("SN"); 
	var rstExecBudget = res.getElementsByTagName("EXECBUDGET");
	var rstUsedBudget = res.getElementsByTagName("USEDBUDGET");
	var rstRemBudget = res.getElementsByTagName("REMBUDGET");
	var rstBudgetRate = res.getElementsByTagName("BUDGETRATE");
	var rstBUD_YN = res.getElementsByTagName("BUD_YN");
	var rstCEO_YN = res.getElementsByTagName("CEO_YN");
	var rstBIZ_YN = res.getElementsByTagName("BIZ_YN");
	//alert('rstCEO_YN : '+rstCEO_YN);
	strBdChkRst = ""
	//alert('rstSn : '+rstSn);
	//alert('rstSn.length : '+rstSn.length);
	//alert('rstValue : '+rstValue);
	
	//alert('rstExecBudget : '+rstExecBudget);
	//alert('rstUsedBudget : '+rstUsedBudget);
	//alert('rstBudgetRate : '+rstBudgetRate);
	var intBudCheck = 0;
	var intCeoCheck = 0;
	var intBizCheck = 0;
	//alert('intCeoCheck : '+intCeoCheck);
	for(i=0;i<rstSn.length;i++)
	{
		//alert('rstExecBudget[i].firstChild.data : '+rstExecBudget[i].firstChild.data);
		var j=eval(i)+1;
		$('#BUDGETAMT'+j).val(rstExecBudget[i].firstChild.data);
		$('#BUDGETUSEDAMT'+j).val(rstUsedBudget[i].firstChild.data);
		$('#BUDGETRATE'+j).val(rstBudgetRate[i].firstChild.data);

		intBudCheck=eval(intBudCheck)+eval(rstBUD_YN[i].firstChild.data);
		//alert('rstCEO_YN['+i+'].firstChild.data : '+rstCEO_YN[i].firstChild.data);
		intCeoCheck=eval(intCeoCheck)+eval(rstCEO_YN[i].firstChild.data);
		intBizCheck=eval(intBizCheck)+eval(rstBIZ_YN[i].firstChild.data);
		
		if(rstBUD_YN[i].firstChild.data == '1')
		{
			sChkValue = sChkValue+'&SN='+rstSn[i].firstChild.data+'&MSG=������  �����մϴ�.';
		}
		//alert('rstBIZ_YN['+i+'].firstChild.data : '+rstBIZ_YN[i].firstChild.data);
		if(rstBIZ_YN[i].firstChild.data == '1')
		{
			sChkValue = sChkValue+'&SN='+rstSn[i].firstChild.data+'&MSG=���� �ŷ�ó�� �ƴմϴ�.';
		}
		
	}
	//alert('intCeoCheck : '+intCeoCheck);
	if(intBudCheck>0) bReturn= false;
	if(intCeoCheck>0){		
		isCeoCheck = 'Y';
		$('#CEOCHECK').val(isCeoCheck);
		//alert('$(#CEOCHECK).val() : '+$('#CEOCHECK').val());
	}
	if(intBudCheck>0) bReturn= false;
	
	strBdChkRst = '?ceoCheck='+isCeoCheck+sChkValue;

	//showAcctBudgetContent_Inha(rstSn,rstExecBudget,rstUsedBudget,rstBudgetRate, rstRemBudget);
	//alert('bRturn : '+bReturn);
	return bReturn;
}

/*
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
			//frm.TOTBUDGET.value = cashReturn(rstExecBudget[i].firstChild.data);
			//frm.TOTEXEAMT.value  = cashReturn(rstUsedBudget[i].firstChild.data);
			//frm.BUDGETUSEDAMTTMP[i].value = rstUsedBudget[i].firstChild.data;
			//frm.TOTBUDGETRATE.value  = rstBudgetRate[i].firstChild.data;
			//frm.TOTREMBUDGET.value = cashReturn(rstRemBudget[i].firstChild.data);
		}
	
	}else{


			
			//frm.TOTBUDGET.value = 0;
			//frm.TOTEXEAMT.value  = 0;
			//frm.TOTBUDGETRATE.value = 0;
	}
}
*/

function showAcctBudgetContent(rstSn,rstExecBudget,rstUsedBudget,rstBudgetRate)
{
	var frm = document.forms[1];
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