var fileIdx=0; //FILE NO
var idx=1;     //VIEW SEQ
var ui_no;     //UI NO 
var obj_no;    //OBJ NO
var fileType="000";//FILETYPE
var $dir;    	//Directory Name
//var flag = false;
/* 초기화 */
 function init(ui,obj,dir){
	ui_no   = ui;
	obj_no  = obj;	
    $dir  	= dir;	
 }
 /*FILE NO 설정*/
 function setFileIdx(file_idx){
	 fileIdx=Number(file_idx);
	 fileIdx++;
 }
/*파일 첨부 기능 추가 */
 function addFile(){
	var html = 	"<div id='fileDiv"+fileIdx+"'>&nbsp;"+	
				"<input name='file"+fileIdx+"' id='files'  style='width:80%;  background-color:#FFFFFF;text-align:left;ime-mode:active; '  size='20' type='file'>"+
				"<input type='hidden' name='div' value='I'>"+
				"<input type='hidden' name='ui_no' value='"+ui_no+"'>"+
				"<input type='hidden' name='obj_no' value='"+obj_no+"'>"+
				"<input type='hidden' name='file_no' value='"+fileIdx+"'>"+
				"<input type='hidden' name='delFlag' value='N'>"+
				"<input type='hidden' name='dir' value='"+$dir+"'>"+
				"<input type='hidden' name='fileType' value='"+fileType+"'>&nbsp;&nbsp;&nbsp;"+
	            "<input type=button value='삭제'  onclick=removeFile(fileDiv"+fileIdx+")>"+
		        "</div>";
	fileIdx++;
	//flag=true;
	fileSpan.insertAdjacentHTML('afterBegin',html);
}
/*파일 조회*/
function selectFile(file_idx,dir,fileSystemName,fileOriginName){
	
	if(dir.indexOf('C:') != -1){
		dir = dir.replace('C:','');	
	}
	 
	var html = 	"<div id='fileDiv"+file_idx+"'>&nbsp;"+(idx++)+"&nbsp;"+	
				"<input type='hidden' name='file"+file_idx+"' >"+
				"<input type='hidden' name='div' value='U'>"+
				"<input type='hidden' name='ui_no' value='"+ui_no+"'>"+
				"<input type='hidden' name='obj_no' value='"+obj_no+"'>"+
				"<input type='hidden' name='file_no' value='"+file_idx+"'>"+
				"<input type='hidden' name='delFlag' value='N'>"+
				"<input type='hidden' name='dir' value='"+dir+"'>"+
				"<input type='hidden' name='fileType' value='"+fileType+"'>&nbsp;&nbsp;&nbsp;"+
	            "<a href=\"javascript:download('"+dir+"','"+fileSystemName+"','"+fileOriginName+"')\">"+
				fileOriginName+"</a>"+
				"&nbsp;&nbsp;&nbsp;<input type='checkbox' style='border:0' onchange=updateFiles(this,fileDiv"+file_idx+") >(삭제)"+
		        "</div>";
	fileSpan.insertAdjacentHTML('beforeEnd',html);

}
//파일조회
function selectFileView(file_idx,dir,fileSystemName,fileOriginName){
	
	if(dir.indexOf('C:') != -1){
		dir = dir.replace('C:','');	
	}

	var html = 	"<div id='fileDiv"+file_idx+"'>&nbsp;"+(idx++)+"&nbsp;"+	
				"<input type='hidden' name='file"+file_idx+"' >"+
				"<input type='hidden' name='div' value='U'>"+
				"<input type='hidden' name='ui_no' value='"+ui_no+"'>"+
				"<input type='hidden' name='obj_no' value='"+obj_no+"'>"+
				"<input type='hidden' name='file_no' value='"+file_idx+"'>"+
				"<input type='hidden' name='delFlag' value='N'>"+
				"<input type='hidden' name='dir' value='"+dir+"'>"+				
				"<input type='hidden' name='fileType' value='"+fileType+"'>&nbsp;&nbsp;&nbsp;"+
	            "<a href=\"javascript:download('"+dir+"','"+fileSystemName+"','"+fileOriginName+"')\">"+
				fileOriginName+"</a>"+
		        "</div>";
	fileSpan.insertAdjacentHTML('beforeEnd',html);
}
/*파일 삭제*/
function removeFile(fileDiv){		
	fileDiv.outerHTML=""; 
}
/*파일 등록*/
function saveFile(){

	if(ui_no=="undefined"||obj_no=="undefined"){
		alert("사용자 정의가 누락되었습니다. 관리자에게 문의하십시오.");
		return false;
	}
	
	var obj=document.all["files"];
//	var size = document.getElementById("files").files[0].size
	if(obj!=undefined){
		if(obj.length==undefined){
			//파일체크(확장자,사이즈)	
			if (fileChk(obj.value,obj.files[0].size)==false){
				return;
			}
			
			if(obj.value=='')
				removeFile(document.all["fileDiv"+obj.name.substr(4)]);					
		}else{
			for(var i=obj.length-1;i>-1;i--){
				//파일체크(확장자,사이즈)
				if(fileChk(obj[i].value,obj[i].files[0].size)==false){
					return;
				}
				
				if(obj[i].value=='')
					removeFile(document.all["fileDiv"+obj[i].name.substr(4)]);				
			}
		}
	}
	window.showModalDialog("/common/fileProgress.jsp", this,"dialogWidth:350px; dialogHeight:30px; status:no; scroll:no");
	//window.open("/common/fileProgress.jsp", "fileUpload", 350, 30)

}

function updateFiles(obj,fileDiv){	
	var delFlag=fileDiv.all["delFlag"];
	if(obj.checked==true){
		delFlag.value='Y';
	}else{
		delFlag.value='N';
	}
}
/*파일다운로드*/
function download(dir,fileSystemName,fileOriginName){
	if(fileSystemName.substring(0,5)=="COMS-") fileSystemName=fileOriginName;
	location.href=	"downLoadFiles.do?dir="+dir+"&fileSystemName="+fileSystemName+"&fileOriginName="+fileOriginName;			
}

/* 파일 업로드 체크(용량/확장자)
사용법 : fileChk(파일명, 파일사이즈, 업로드사이즈기준)
결과값 : true/flase
*/
function fileChk(fileName,fileSize,size){
	var chkWord = fileName.substring((fileName.length-4),fileName.length);
	//확장자 체크
	if(chkWord.toUpperCase()==".JPG"||
		chkWord.toUpperCase()==".GIF"||
		chkWord.toUpperCase()==".PNG"||
		chkWord.toUpperCase()==".TIF"||
		chkWord.toUpperCase()==".BMP"||
		chkWord.toUpperCase()==".HWP"||
		chkWord.toUpperCase()==".TXT"||
		chkWord.toUpperCase()==".ZIP"||
		chkWord.toUpperCase()==".XLS"||
		chkWord.toUpperCase()=="XLSX"||
		chkWord.toUpperCase()==".DOC"||
		chkWord.toUpperCase()=="DOCX"||
		chkWord.toUpperCase()==".PPT"||
		chkWord.toUpperCase()=="PPTX"||
		chkWord.toUpperCase()==".PDF"){
	}else{ 
	  alert(chkWord.toUpperCase()+' => 업로드 불가 파일입니다.');
	  return false;
		}
	 // alert(chkWord.toUpperCase()+'=>업로드 허용 파일입니다.');
	 return fileSizeChk(fileName,fileSize,size); //파일사이즈체크
}
/* 파일 업로드 체크(default 10MB) */
function fileSizeChk(fileName,fileSize,size){
	 if(size==undefined) size=10; //기본 10MB 
	 
	 if(fileSize<1024000*size){
		//  alert(size+'MB 용량이내 파일입니다.');
		  return true;
	 }else{
		  alert(fileName + ' => ' +size+'MB 용량 초과 파일입니다.');
		  return false;
	 }
}  
