/*-------- AJAX를 이용한 webservice (Common Code) ---------*/
var xmlHttpObj;
var GID;
var GType;


function gfn_service(com_id,parameters,xml_name,xml_id){
	GID=com_id;
	gfn_webservice_call(parameters,xml_name,xml_id);

}
/*
	   SELECT
   	        
   	        호출함수
	   fn_ajax_service(
					   com명,
					   parameter배열,
					   xml-id 
	   )
 */
function gfn_ajax_service(com_id,parameters,xml_name,xml_id,fn_obj){
	GType=fn_obj;
	GID=com_id;
	gfn_webservice_call(parameters,xml_name,xml_id);

}

/*
		INSERT/UPDATE/DELETE
*/
function gfn_ajax_request(com_id,parameters,xml_name,xml_id,fn_obj){
	GID=com_id;
	gfn_webservice_request(parameters,xml_name,xml_id,fn_obj);
}

/* xmlhttprequest 객체 획득 */
function gfn_createXMLHttpRequest() {
    if (window.ActiveXObject) {
		xmlHttpObj = new ActiveXObject("Microsoft.XMLHTTP");
    }
    else if (window.XMLHttpRequest) {
		xmlHttpObj = new XMLHttpRequest();     
    }
}
/* SERVER comXmlSet c/u/d call */
function gfn_webservice_request(parameters,xml_name,xml_id,fn_obj){
	gfn_createXMLHttpRequest();
	var url = "comAjax.do?"+gfn_makeUri(parameters)+"&fileName0="+xml_name+".xml&idx0="+xml_id;
	xmlHttpObj.open("GET", url, false);
	if(fn_obj == undefined){
		xmlHttpObj.onreadystatechange = gfn_callback;
	}else{
		xmlHttpObj.onreadystatechange = fn_obj;
	}
	xmlHttpObj.send(null);			
}

/* SERVER comXmlSet r call */
function gfn_webservice_call(parameters,xml_name,xml_id){
	gfn_createXMLHttpRequest();
	var url = "/common/comXmlSet.jsp?"+gfn_makeUri(parameters)+"&fileName0="+xml_name+".xml&idx0="+xml_id+"&entity0=ASeach&$tc=1";
	xmlHttpObj.open("GET", url, false);
	xmlHttpObj.onreadystatechange = gfn_callback;
	xmlHttpObj.send(null);		
}

/* AJAX 파라메터 설치 */
function gfn_makeUri(parameters){
	var len=0;
	var uri="";
	var value="";
	if(parameters!=null){
		len=parameters.length;
		for(var i=0;i<parameters.length;i++){
			value=parameters[i];
			if(value!=""){
				uri+="&p"+i+"="+value;
			}
		}
	}
	uri+="&$leng0="+len;
	return uri;	
}
/* callback 함수 정의 (c/r/u/d)*/
function gfn_callback() {
//	try{
		if (xmlHttpObj.readyState == 4 &&xmlHttpObj.status == 200) {
	    	if (GID!=''&& document.getElementById(GID).value!=undefined & GType=='') {
	    		document.getElementById(GID).innerHTML=xmlHttpObj.responseText;
	    	}else if (GID!=''){
	    		 //document.getElementById(GID).value='true';
	    		if(GType=='gGrid'){		
		    		gfn_setGridData();
	    		}else if(GType=='aSearch'){
		    		gfn_setData();
	    		}else if(GType=='gData'){
		    		gfn_Data();
	    		}
	    	}
		}else if (xmlHttpObj.readyState == 4 && xmlHttpObj.status == 204){//데이터가 존재하지 않을 경우
		}
//	}catch(err){ } return;
	
}
/* 사원 조회 처리*/
function gfn_setData() {
	sCODE_NO	=	xmlHttpObj.responseXML.getElementsByTagName("CODE_NO");
	sCODE_CD	=	xmlHttpObj.responseXML.getElementsByTagName("CODE_CD");
	sCODE_NAME	=	xmlHttpObj.responseXML.getElementsByTagName("CODE_NAME");
	sD_CODE_NAME=	xmlHttpObj.responseXML.getElementsByTagName("D_CODE_NAME");;	
	
	//기존 row삭제
	rmData();
	if(sCODE_NO.length>0){
		//조회 row등록
		for( i=0;i<sCODE_NO.length;i++){
			 objCode = sCODE_NO[i].firstChild.data+"§"+
					   sCODE_CD[i].firstChild.data+"§"+
			 		   sCODE_NAME[i].firstChild.data+"§"+
			 		   sD_CODE_NAME[i].firstChild.data;
			 mkData(objCode);		    		   
		}	
	}
}
/* 공통  처리*/
function gfn_Data() {
	p0	=	xmlHttpObj.responseXML.getElementsByTagName("P0");
	if(p0.length>0){
		//조회 row등록
		for( i=0; i< p0.length ; i++){
			 objCode = i+"§"+p0[i].firstChild.data  //Data
	 		 mkData(objCode);
		}
	}
}

/* 공통 처리*/
function gfn_setGridData() {

	sCODE_NO	=	xmlHttpObj.responseXML.getElementsByTagName("CODE_NO");
//	return;
	//기존 row삭제
//	gfn_rmData();
	
	if(sCODE_NO.length>0){

		//조회 row등록
		for( i=0;i<sCODE_NO.length;i++){
			 objCode = sCODE_NO.length+"§"+
			           i+"§" +
			           i+"§"+ sCODE_NO[i].firstChild.data ;
			           
			 mkData(objCode);
		}	
	}
}

//Data삭제
function gfn_rmData(){
	try{
		obj=document.getElementById('iGrid');
		if(GID.length>0) {
			for(i=0;GID.length>0;i++)	obj[0].removeNode(true);
		}else{
			obj.removeNode(true);
		}
	}catch(e){
	}finally{
	}
}