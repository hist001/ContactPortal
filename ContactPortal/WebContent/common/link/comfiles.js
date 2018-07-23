var fileIdx=0; //FILE NO
var idx=1;     //VIEW SEQ
var ui_no;     //UI NO 
var obj_no;    //OBJ NO
var fileType="000";//FILETYPE
var $dir;    	//Directory Name
//var flag = false;
/* �ʱ�ȭ */
 function init(ui,obj,dir){
	ui_no   = ui;
	obj_no  = obj;	
    $dir  	= dir;	
 }
 /*FILE NO ����*/
 function setFileIdx(file_idx){
	 fileIdx=Number(file_idx);
	 fileIdx++;
 }
/*���� ÷�� ��� �߰� */
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
	            "<input type=button value='����'  onclick=removeFile(fileDiv"+fileIdx+")>"+
		        "</div>";
	fileIdx++;
	//flag=true;
	fileSpan.insertAdjacentHTML('afterBegin',html);
}
/*���� ��ȸ*/
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
				"&nbsp;&nbsp;&nbsp;<input type='checkbox' style='border:0' onchange=updateFiles(this,fileDiv"+file_idx+") >(����)"+
		        "</div>";
	fileSpan.insertAdjacentHTML('beforeEnd',html);

}
//������ȸ
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
/*���� ����*/
function removeFile(fileDiv){		
	fileDiv.outerHTML=""; 
}
/*���� ���*/
function saveFile(){

	if(ui_no=="undefined"||obj_no=="undefined"){
		alert("����� ���ǰ� �����Ǿ����ϴ�. �����ڿ��� �����Ͻʽÿ�.");
		return false;
	}
	
	var obj=document.all["files"];
//	var size = document.getElementById("files").files[0].size
	if(obj!=undefined){
		if(obj.length==undefined){
			//����üũ(Ȯ����,������)	
			if (fileChk(obj.value,obj.files[0].size)==false){
				return;
			}
			
			if(obj.value=='')
				removeFile(document.all["fileDiv"+obj.name.substr(4)]);					
		}else{
			for(var i=obj.length-1;i>-1;i--){
				//����üũ(Ȯ����,������)
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
/*���ϴٿ�ε�*/
function download(dir,fileSystemName,fileOriginName){
	if(fileSystemName.substring(0,5)=="COMS-") fileSystemName=fileOriginName;
	location.href=	"downLoadFiles.do?dir="+dir+"&fileSystemName="+fileSystemName+"&fileOriginName="+fileOriginName;			
}

/* ���� ���ε� üũ(�뷮/Ȯ����)
���� : fileChk(���ϸ�, ���ϻ�����, ���ε���������)
����� : true/flase
*/
function fileChk(fileName,fileSize,size){
	var chkWord = fileName.substring((fileName.length-4),fileName.length);
	//Ȯ���� üũ
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
	  alert(chkWord.toUpperCase()+' => ���ε� �Ұ� �����Դϴ�.');
	  return false;
		}
	 // alert(chkWord.toUpperCase()+'=>���ε� ��� �����Դϴ�.');
	 return fileSizeChk(fileName,fileSize,size); //���ϻ�����üũ
}
/* ���� ���ε� üũ(default 10MB) */
function fileSizeChk(fileName,fileSize,size){
	 if(size==undefined) size=10; //�⺻ 10MB 
	 
	 if(fileSize<1024000*size){
		//  alert(size+'MB �뷮�̳� �����Դϴ�.');
		  return true;
	 }else{
		  alert(fileName + ' => ' +size+'MB �뷮 �ʰ� �����Դϴ�.');
		  return false;
	 }
}  
