var xmlHttp;
var xml ="";
var postData  = ""; 

function suvFoodTicket()
{
	createXMLHttpRequest();
	xml = getPageInfo();
	postData ="";
	var url = "/dinnerTicketSuv.do";
	
	xmlHttp.open("POST", url, false);
    xmlHttp.onreadystatechange = displayDinnerTicket;
    xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");  

	xmlHttp.send(xml);
	
}

function searchFoodTicket()
{
	createXMLHttpRequest();
	xml = getPageInfo();
	postData ="";
	var url = "/dinnerTicketSearch.do";
	
	xmlHttp.open("POST", url, false);
    xmlHttp.onreadystatechange = displayDinnerTicket;
    xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");  

	xmlHttp.send(xml);
	
}

function getDinnerTicket()
{

	var frm=document.forms[0];
	var res = xmlHttp.responseXML;
	//var res = xmlHttp.responseText;
	var aKey = res.getElementsByTagName("KEY"); 
	var aVal = res.getElementsByTagName("VALUE");


	for(i=0;i<aKey.length;i++)
	{
		var name = aKey[i].firstChild.nodeValue;
		var tag = document.getElementById(name);

		if(tag != undefined || tag != null)
		{
			if(tag.type != 'select-one'){
			//alert("aVal[i].firstChild : " + aVal[i].firstChild);
				if(aVal[i].firstChild != null)
				{
					tag.value = aVal[i].firstChild.nodeValue;
				}
			}

		}
	}
	
}

function displayDinnerTicket() {

	var frm=document.forms[0];
	
	var bReturn = true;
    if(xmlHttp.readyState == 4) {
        if(xmlHttp.status == 200) {
			getDinnerTicket();
        }else{
        	alert("존재하지 않는 식권입니다.");
        }
    }
    
    return bReturn;
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