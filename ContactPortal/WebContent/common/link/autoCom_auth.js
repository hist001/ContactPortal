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
	} else if (xmlHttp.status == 204){//데이터가 존재하지 않을 경우
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
		//alert('화살표아래');
		//document.all("name_table").moveRow(0,0);
		//alert(document.all("name_table").firstChild.firstChild.outerHTML);
		document.all("name_table").firstChild.firstChild.focus();
	}
}


//입력창에서 검색영역으로 이동
function mvDataFocus(keycode){
	if(keycode==40) {//방향키down
		if(iText.length>0){
			iText[0].focus()
		}else{
			iText.focus();
		}
	}else if(event.keyCode==9) {
		rmData();
	}
}
//선택값 전달
function sendData(code){
	sendDataObj(code,sObj,sObjNo,sObjCd);
}
//선택값 OBJ로 전달
function sendDataObj(code,sObj,sObjNo,sObjCd){
	var objCodes=code.split('§');
	
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
	
	rmData();//기존조회내역 삭제
}
//키보드 선택이동
function focusChg(keyCode){
	try{
		if(iText.length>0){
			if(keyCode==40) {//방향키down
				autoComidx=autoComidx+1;
				iText[autoComidx-1].style.backgroundColor="darksalmon";
				iText[autoComidx-2].style.backgroundColor="white";
			}else if(keyCode==38){//방향키up
				autoComidx=autoComidx-1;   
				iText[autoComidx-1].style.backgroundColor="darksalmon";
				iText[autoComidx].style.backgroundColor="white";
			}else if(keyCode==13){//Enter
				sendData(iText[autoComidx-1].value);
			}
		}else{
			if(keyCode==40) {//방향키down
				iText.style.backgroundColor="darksalmon";
			}else if(keyCode==13){//Enter
				sendData(iText.value);
			}
		}
		
	}catch(e){
	}finally{
	}		
}

//등록 row 생성
function mkData(objCode){
	var objCodes=objCode.split('§');
	html="<table id='iText' class='' cellpadding=0 cellspacing=2 border=0 "+ 
		 " onkeyup='focusChg(event.keyCode);'" +  
		 " onMouseDown='sendData(this.value);'" +  
		 " onMouseOver=chgClass(this,'Mover'); " +
		 " onMouseOut=chgClass(this,'Mout'); " +
		 " style='width:100%;background-color:white;cursor:hand;' " +
		 " value='"+objCode+"'><tr><td style='background-color:antiquewhite;filter:alpha(opacity=100)' align='left' height=15>"+objCodes[3]+"</td></tr></table>";
	sText.insertAdjacentHTML('afterBegin',html);
}

//Data삭제
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
		
		//높이 사이즈
		targetObj.style.height='0';
	}catch(e){
	}finally{
	}
}
//Data조회
function viewData(obj){
	if(obj!=''&& obj.value!=''){
		autoComidx=0;	
		gfn_ajax_service(obj.name,new Array(obj.value),'comPopup',sXmlId,'aSearch');
	}
}
//자동검색
function autoSearch(obj,left,top,objNo,objCd,xmlId,sLength){
	(left == undefined)?left=0:left; //검색 리스트 위치 : X좌표
	(top == undefined )?top=0:top;   //검색 리스트 위치 : Y좌표

	sObjNo="";
	sObjCd="";
	if(objNo != undefined )	{
		sObjNo=document.getElementById(objNo); //선택 값지정 :NO
	}
	if(objCd != undefined )	sObjCd=document.getElementById(objCd); //선택 값 지정:CODE
	(xmlId != undefined )?sXmlId=xmlId:sXmlId='COM_001';  //Default 직원 조회
	(sLength != undefined )?sLength=sLength:sLength=1;  //검색 시작 문자길이

	sObj=document.getElementById(obj.id);
    //검색 초기화
    sObj.style.color='black';
   	
	//작성중 문자 확인 후 데이타 조회 (tab,shift,arrow 하단)
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

//객체 위치 처리(보정처리)
function autoObjXYSet(source,target,left,top){
	var sourceObj=document.getElementById(source);
	var targetObj=document.getElementById(target);
	var ret=getBounds(sourceObj);
}
//객체 위치 처리(보정처리)
function autoObjXYSet(source,target,left,top){
	var sourceObj=document.getElementById(source);
	var targetObj=document.getElementById(target);
	var ret=getBounds(sourceObj);

	if (document.getElementById("iText") != null){
		//높이 사이즈
		if(iText.length>8) {
			targetObj.style.height='150';
		}else if(iText.length==0) {
			targetObj.style.height='0';
		}else if(iText.length!= undefined ){
			targetObj.style.height=iText.length*'18' +18;
		}
	}	
	
	//위치잡기
	targetObj.style.left=ret.left-left;
	targetObj.style.top=ret.top+20+top; //절대위치
}
/**
 * 좌표 값을 측정한다.
 * @param obj 측정 요망 객체
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
