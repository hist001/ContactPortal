var obj;

function init()
{
	obj=window.dialogArguments;
    document.getElementById('progressBar').style.display = 'block';
    document.getElementById('progressBarText').innerHTML = '진행률 : 0%';
	obj.document.all["com_Files"].submit();
    window.setTimeout("fn_webservice_call()", 100);
    return true;
}

var xmlHttp;


function fn_createXMLHttpRequest() {
	if(xmlHttp==null){
		if (window.ActiveXObject) {
			xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		else if (window.XMLHttpRequest) {
			xmlHttp = new XMLHttpRequest();     
		}
	}
}

function fn_webservice_call(){
	fn_createXMLHttpRequest();
	var url = "/common/uploadProgress.jsp"; 		
	xmlHttp.open("GET", url, true);
	xmlHttp.onreadystatechange = fn_callback;
	xmlHttp.send(null);					
}

function fn_callback() {

    if (xmlHttp.readyState == 4) {
		if (xmlHttp.status == 200) {

			fn_progress_display(xmlHttp.responseXML.getElementsByTagName("TOTALSIZE"),
				                xmlHttp.responseXML.getElementsByTagName("BYTESREAD"),
								xmlHttp.responseXML.getElementsByTagName("ISEND"));
		}else if (xmlHttp.status == 204){//데이터가 존재하지 않을 경우
		}else{}
	}else{
	}
}
function fn_progress_display(totalsize, bytesread, isEnd){
	var bytesToRead=0;
	var totalToSize=0;
	var progressPercent=0;
	var isToEnd="false";

	if(totalsize!=null){
		bytesToRead=bytesread[0].firstChild.text; 
		totalToSize=totalsize[0].firstChild.text;
		isToEnd=isEnd[0].firstChild.text;
		if(totalToSize>0)
			progressPercent=Math.ceil((bytesToRead / totalToSize) * 100);
	}
	//alert(bytesToRead+"/"+totalToSize+"="+progressPercent+":"+isToEnd);
	
	document.getElementById('progressBarText').innerHTML = '진행률 : ' + progressPercent + '%';

	document.getElementById('progressBarBoxContent').style.width = parseInt(progressPercent * 3.5) + 'px';

    if(progressPercent==100 || isToEnd=="true"){
		//alert('파일업로드가 완료되었습니다.');
		this.close();
	}else{
		window.setTimeout('fn_webservice_call()', 10);
	}
    
}
