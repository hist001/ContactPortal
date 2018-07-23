var xmlHttp;
var completeDiv;
var inputField;
var nameTable;
var nameTableBody;
var sObj;
var sObjNo;
var sObjCd;
var sXmlId;

function createXMLHttpRequest() {
    if (window.ActiveXObject) {
	xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    else if (window.XMLHttpRequest) {
	xmlHttp = new XMLHttpRequest();     
    }
}
function initVars() {
    inputField = document.getElementById("names");
    nameTable = document.getElementById("name_table");
    completeDiv = document.getElementById("popup");
    nameTableBody = document.getElementById("name_table_body");
}
function findNames() {

    initVars();
    if (inputField.value.length > 0 && (inputField.value!='')) {
		createXMLHttpRequest();
		var url = "projectNameSrch.do?names=" + encodeURI(inputField.value);
		xmlHttp.open("GET", url, true);
		xmlHttp.onreadystatechange = callback;
		xmlHttp.send(null);
    } else {
	clearNames();
    }
}
function callback() {
    if (xmlHttp.readyState == 4) {
	if (xmlHttp.status == 200) {
	    setNames(xmlHttp.responseXML.getElementsByTagName("name"));
	} else if (xmlHttp.status == 204){//�����Ͱ� �������� ���� ���
	    clearNames();
	}else{clearNames();}
    }else{clearNames();}
}

function setNames(the_names) {

    clearNames();
    var size = the_names.length;

    setOffsets();

    var row, cell, txtNode;
		
    for (var i = 0; i < size; i++) {

	var nextNode = the_names[i].firstChild.data;
	row = document.createElement("tr");
	cell = document.createElement("td");
	
	cell.onmouseout = function() {this.className='mouseOver';};
	cell.onmouseover = function() {this.className='mouseOut';};
	cell.setAttribute("bgcolor", "#FFFFFF");
	cell.setAttribute("border", "0");
	cell.setAttribute("tabIndex", i);

	cell.onclick = function() { populateName(this);};  

			if(nextNode.length>(inputField.size/2)){nextNode=nextNode.substring(0,(inputField.size)/2)+'...';}
	
			//nextNode= '<font color=#FF0000>'+nextNode+'</font>'
	txtNode = document.createTextNode(nextNode);

			//eval(txtNode.outerHTML);

	cell.appendChild(txtNode);
	row.appendChild(cell);
	nameTableBody.appendChild(row);
    }
}
function setOffsets() {
    var end = inputField.offsetWidth;
    var left = calculateOffsetLeft(inputField);
    var top = calculateOffsetTop(inputField) + inputField.offsetHeight;
    completeDiv.style.border = "black 1px solid";
    completeDiv.style.left = left + "px";
    completeDiv.style.top = top + "px";
    nameTable.style.width = end + "px";
}

function calculateOffsetLeft(field) {
  return calculateOffset(field, "offsetLeft");
}
function calculateOffsetTop(field) {
  return calculateOffset(field, "offsetTop");
}
function calculateOffset(field, attr) {
  var offset = 0;
  while(field) {
    offset += field[attr]; 
    field = field.offsetParent;
  }
  return offset;
}
function populateName(cell) {
    inputField.value = cell.firstChild.nodeValue;
    clearNames();
}
function clearNames() {
	
    var ind = nameTableBody.childNodes.length;
    for (var i = ind - 1; i >= 0 ; i--) {
	 nameTableBody.removeChild(nameTableBody.childNodes[i]);
    }
    completeDiv.style.border = "none";
}

function moveNames(){		
	if(event.keyCode==40){
		//alert('ȭ��ǥ�Ʒ�');
		//document.all("name_table").moveRow(0,0);
		//alert(document.all("name_table").firstChild.firstChild.outerHTML);
		document.all("name_table").firstChild.firstChild.focus();
	}
}


//�Է�â���� �˻��������� �̵�
function mvDataFocus(keycode){
	if(keycode==40) {//����Űdown
		if(iText.length>0){
			iText[0].focus()
		}else{
			iText.focus();
		}
	}else if(event.keyCode==9) {
		rmData();
	}
}
//���ð� ����
function sendData(code){
	sendDataObj(code,sObj,sObjNo,sObjCd);
}
//���ð� OBJ�� ����
function sendDataObj(code,sObj,sObjNo,sObjCd){
	var objCodes=code.split('��');
	
	if(sObjNo != undefined)sObjNo.value=objCodes[0];
	if(sObjCd != undefined)sObjCd.value=objCodes[1];
	
	sObj.value=objCodes[2];
	sObj.style.color='blue';
	sObj.focus();
	
	var frm1 = document.forms[0];
	
	for(var i=frm1.rIdx.length-2;i>=0;i--){
		document.getElementById("div2"+frm1.rNo[i+1].value).getElementsByTagName("span")[3].outerHTML="<span class=auth>"+objCodes[2]+"("+objCodes[1]+")"+"</span>";
		frm1.rEmpid[i+1].value = objCodes[1];
	}
	
	rmData();//������ȸ���� ����
}
//Ű���� �����̵�
function focusChg(keyCode){
	try{
		if(iText.length>0){
			if(keyCode==40) {//����Űdown
				autoComidx=autoComidx+1;
				iText[autoComidx-1].style.backgroundColor="darksalmon";
				iText[autoComidx-2].style.backgroundColor="white";
			}else if(keyCode==38){//����Űup
				autoComidx=autoComidx-1;   
				iText[autoComidx-1].style.backgroundColor="darksalmon";
				iText[autoComidx].style.backgroundColor="white";
			}else if(keyCode==13){//Enter
				sendData(iText[autoComidx-1].value);
			}
		}else{
			if(keyCode==40) {//����Űdown
				iText.style.backgroundColor="darksalmon";
			}else if(keyCode==13){//Enter
				sendData(iText.value);
			}
		}
		
	}catch(e){
	}finally{
	}		
}

//��� row ����
function mkData(objCode){
	var objCodes=objCode.split('��');
	html="<table id='iText' class='' cellpadding=0 cellspacing=2 border=0 "+ 
		 " onkeyup='focusChg(event.keyCode);'" +  
		 " onMouseDown='sendData(this.value);'" +  
		 " onMouseOver=chgClass(this,'Mover'); " +
		 " onMouseOut=chgClass(this,'Mout'); " +
		 " style='width:100%;background-color:white;cursor:hand;' " +
		 " value='"+objCode+"'><tr><td style='background-color:antiquewhite;filter:alpha(opacity=100)' align='left' height=15>"+objCodes[3]+"</td></tr></table>";
	sText.insertAdjacentHTML('afterBegin',html);
}

//Data����
function rmData(){
	try{
		var targetObj=document.getElementById('autoSearch');
		if(iText.length>0) {
			for(i=0;iText.length+5>0;i++)	{
				iText[0].removeNode(true);
				if (iText.length==undefined) iText.removeNode(true);
				}
		}else{
			iText.removeNode(true);
		}
		
		//���� ������
		targetObj.style.height='0';
	}catch(e){
	}finally{
	}
}
//Data��ȸ
function viewData(obj){
	if(obj!=''&& obj.value!=''){
		autoComidx=0;	
		gfn_ajax_service(obj.name,new Array(obj.value),'comPopup',sXmlId,'aSearch');
	}
}
//�ڵ��˻�
function autoSearch(obj,left,top,objNo,objCd,xmlId,sLength){
	(left == undefined)?left=0:left; //�˻� ����Ʈ ��ġ : X��ǥ
	(top == undefined )?top=0:top;   //�˻� ����Ʈ ��ġ : Y��ǥ

	sObjNo="";
	sObjCd="";
	if(objNo != undefined )	{
		sObjNo=document.getElementById(objNo); //���� ������ :NO
	}
	if(objCd != undefined )	sObjCd=document.getElementById(objCd); //���� �� ����:CODE
	(xmlId != undefined )?sXmlId=xmlId:sXmlId='COM_001';  //Default ���� ��ȸ
	(sLength != undefined )?sLength=sLength:sLength=1;  //�˻� ���� ���ڱ���

	sObj=document.getElementById(obj.id);
    //�˻� �ʱ�ȭ
    sObj.style.color='black';
   	
	//�ۼ��� ���� Ȯ�� �� ����Ÿ ��ȸ (tab,shift,arrow �ϴ�)
	if(event.keyCode!=40 && event.keyCode!=9 && event.keyCode!=16){
		if( sObj.value.length>=sLength && chkCharFinish(sObj.value)){
			viewData(sObj);
				if(sObj.value==''){
					viewData(sObj);
				}			
		}
	}
	 mvDataFocus(event.keyCode);
	 focusChg(event.keyCode);	 
	 autoObjXYSet(obj.id,'autoSearch',left,top)
}	

//��ü ��ġ ó��(����ó��)
function autoObjXYSet(source,target,left,top){
	var sourceObj=document.getElementById(source);
	var targetObj=document.getElementById(target);
	var ret=getBounds(sourceObj);
}
//��ü ��ġ ó��(����ó��)
function autoObjXYSet(source,target,left,top){
	var sourceObj=document.getElementById(source);
	var targetObj=document.getElementById(target);
	var ret=getBounds(sourceObj);

	if (document.getElementById("iText") != null){
		//���� ������
		if(iText.length>8) {
			targetObj.style.height='150';
		}else if(iText.length==0) {
			targetObj.style.height='0';
		}else if(iText.length!= undefined ){
			targetObj.style.height=iText.length*'18' +18;
		}
	}	
	
	//��ġ���
	targetObj.style.left=ret.left-left;
	targetObj.style.top=ret.top+20+top; //������ġ
}
/**
 * ��ǥ ���� �����Ѵ�.
 * @param obj ���� ��� ��ü
 */
function getBounds(obj) { 
 var ret = new Object();
 if(document.all) { 
  var rect = obj.getBoundingClientRect(); 
  ret.left = rect.left + (document.documentElement.scrollLeft || document.body.scrollLeft); 
  ret.top = rect.top + (document.documentElement.scrollTop || document.body.scrollTop); 
  ret.width = rect.right - rect.left; 
  ret.height = rect.bottom - rect.top; 
 } else { 
  var box = document.getBoxObjectFor(obj); 
  ret.left = box.x; 
  ret.top = box.y; 
  ret.width = box.width; 
  ret.height = box.height; 
 } 

 return ret; 
}
