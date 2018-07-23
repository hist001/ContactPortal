var reNum = new RegExp("[^0-9.-]", "g");
var reDt = new RegExp("[^0-9]", "g");
/* 값 초기화 */
function gfn_init(){
	var tmp;
	if(varNames!=undefined){
		for(var i=0;i<varNames.length;i++){
			tmp=document.all(varNames[i]);

			if(tmp!=undefined){	
				//alert(tmp.name);
				if(tmp.length==undefined){
					tmp.style.textAlign="left";
					tmp.onblur=function onblur(){ 
						var idx=0;
						for(var j=0;j<varNames.length;j++){
							if(varNames[j]==this.name){
								idx=j;break;
							}
						}					
						if(gfn_getByte(this.value)>varLengs[j]){
							alert('글자수가 지정된 범위를 벗어났습니다.');
							this.focus();						
							return false;
						}					
					};
				}else{
					for(var k=0;k<tmp.length;k++){
						tmp[k].style.textAlign="left";
						tmp[k].onblur=function onblur(){ 
							var idx=0;
							for(var j=0;j<varNames.length;j++){
								if(varNames[j]==this.name){
									idx=j;break;
								}
							}					
							if(gfn_getByte(this.value)>varLengs[j]){
								alert('글자수가 지정된 범위를 벗어났습니다.');
								this.focus();						
								return false;
							}					
						};
					}
				}	
			}else{
							
			}
		}
	}	
	if(numNames!=undefined){
		for(var i=0;i<numNames.length;i++){			
			tmp=document.all(numNames[i]);						
			if(tmp!=undefined){		
				if(tmp.length==undefined){
					tmp.style.textAlign="right";
					tmp.onblur=function onblur(){	
						this.value=this.value.replace(reNum, '');
						var idx=0;
						for(var j=0;j<numNames.length;j++){
							if(numNames[j]==this.name){
								idx=j;break;
							}
						}
						this.value=gfn_exRound(this.value, DATA_SCALE[j]+1); 		
			
						if(this.value.length>DATA_PRECISION[j]){
							alert('숫자가 지정된 범위를 벗어났습니다.');
							this.focus();						
							return false;
						}
						gfn_cashObj(this);
					};
				}else{
					for(var k=0;k<tmp.length;k++){
						tmp[k].style.textAlign="right";
						tmp[k].onblur=function onblur(){	
							this.value=this.value.replace(reNum, '');
							var idx=0;
							for(var j=0;j<numNames.length;j++){
								if(numNames[j]==this.name){
									idx=j;break;
								}
							}
							this.value=gfn_exRound(this.value, DATA_SCALE[j]+1); 					
							if(this.value.length>DATA_PRECISION[j]){
								alert('숫자가 지정된 범위를 벗어났습니다.');
								this.focus();						
								return false;
							}
							gfn_cashObj(this);
						};
					}
				}
			}
		}
	}
	if(vdtNames!=undefined){
		for(var i=0;i<vdtNames.length;i++){
			tmp=document.all(vdtNames[i]);
			if(tmp!=undefined){	
				if(tmp.length==undefined){
					tmp.style.textAlign="center";
					tmp.onblur=function onblur(){
						this.value=this.value.replace(reDt, '');
						var idx=0;
						for(var j=0;j<vdtNames.length;j++){
							if(vdtNames[j]==this.name){
								idx=j;break;
							}
						}
						if(!(this.value.length==8||this.value.length==6) && this.value.length!=0  ){
							alert('날짜입력이 잘못되었습니다.');
							this.focus();						
							return false;
						}else{							
							if(vdtLengs[j]=='10'){
								this.value=this.value.substring(0,4)+"-"+
								this.value.substring(4,6)+"-"+
								this.value.substring(6,8);
							}else{
								this.value=this.value.substring(0,4)+"-"+
								this.value.substring(4,6)
							}
						}
					};
				}else{
					for(var k=0;k<tmp.length;k++){
						tmp[k].style.textAlign="center";
						tmp[k].onblur=function onblur(){
							this.value=this.value.replace(reDt, '');
							var idx=0;
							for(var j=0;j<vdtNames.length;j++){
								if(vdtNames[j]==this.name){
									idx=j;break;
								}
							}
							if(!(this.value.length==8||this.value.length==6) && this.value.length!=0  ){
								alert('날짜입력이 잘못되었습니다.');
								this.focus();						
								return false;
							}else{
								if(vdtLengs[j]==10){
									this.value=this.value.substring(0,4)+"-"+
									this.value.substring(4,6)+"-"+
									this.value.substring(6,8);
								}else{
									this.value=this.value.substring(0,4)+"-"+
									this.value.substring(4,6)
								}
							}
						};
					}
				}
			}
		}
	}
	if(nullChkAttribute!=undefined){
		for(var i=0;i<nullChkAttribute.length;i++){
			tmp=document.all(nullChkAttribute[i]);
			if(tmp!=undefined){	
				if(tmp.length==undefined){
					tmp.style.backgroundColor="#F0F0FF";
				}else{
					for(var k=0;k<tmp.length;k++){
						tmp[k].style.backgroundColor="#F0F0FF";
					}
				}	
			}
		}
	}
	

}

function tmpSave_Check()
	{
		var tmp;

		for(var i=0;i<numNames.length;i++){
			tmp=document.all(numNames[i]);
			if(tmp==undefined){
				return false;
			}
		}
		return true;
	}

/* 값 오라클자료형으로 변환 */
function gfn_check(){	
	var tmp;


	for(var i=0;i<numNames.length;i++){
		tmp=document.all(numNames[i]);
		if(tmp!=undefined){
			if(tmp.length!=undefined){
				for(var j=0;j<tmp.length;j++){
					tmp[j].value=tmp[j].value.replace(reNum, '');
				}
			}else{
				tmp.value=tmp.value.replace(reNum, '');
			}
		}
	}
	for(var i=0;i<vdtNames.length;i++){
		tmp=document.all(vdtNames[i]);
		var idx=0;
		if(vdtLengs[i]==10){
			idx=8;
		}else{
			idx=6;
		}

		if(tmp!=undefined){
			if(tmp.length!=undefined){
				for(var j=0;j<tmp.length;j++){
					tmp[j].value=tmp[j].value.replace(reDt, '').substring(0,idx);
				}
			}else{
				tmp.value=tmp.value.replace(reDt, '').substring(0,idx);
			}			
		}
	}
	for(var i=0;i<nullChkAttribute.length;i++){
		tmp=document.all(nullChkAttribute[i]);
		if(tmp!=undefined){
			if(tmp.length!=undefined){
				for(var j=0;j<tmp.length;j++){
					if(tmp[j].value==undefined||tmp[j].value==''){
						alert(nullChkName[i]+'(을)를 입력하십시오.');						
						tmp[j].focus();						
						return false;
					}
				}
			}else{
					if(tmp.value==undefined||tmp.value==''){
						alert(nullChkName[i]+'(을)를 입력하십시오.');						
						tmp.focus();						
						return false;
					}
			}
		}

	}
	return true;
}
/* ? ???????? ?? */
function gfn_tmp_save(){	
	var tmp;


	for(var i=0;i<numNames.length;i++){
		tmp=document.all(numNames[i]);
		if(tmp!=undefined){
			if(tmp.length!=undefined){
				for(var j=0;j<tmp.length;j++){
					tmp[j].value=tmp[j].value.replace(reNum, '');
				}
			}else{
				tmp.value=tmp.value.replace(reNum, '');
			}
		}
	}
	for(var i=0;i<vdtNames.length;i++){
		tmp=document.all(vdtNames[i]);
		var idx=0;
		if(vdtLengs[i]==10){
			idx=8;
		}else{
			idx=6;
		}

		if(tmp!=undefined){
			if(tmp.length!=undefined){
				for(var j=0;j<tmp.length;j++){
					tmp[j].value=tmp[j].value.replace(reDt, '').substring(0,idx);
				}
			}else{
				tmp.value=tmp.value.replace(reDt, '').substring(0,idx);
			}			
		}
	}
	return true;
}

/* 값 출력*/
function gfn_display(){
	var tmp;

	if(numNames!=undefined){
		for(var i=0;i<numNames.length;i++){
			tmp = document.all(numNames[i]);
			if(tmp!=undefined){
				if(tmp.length==undefined){
					if(tmp.value!=""){
						tmp.value=gfn_cashReturn(tmp.value);
					}
				}else{
					for(var k=0;k<tmp.length;k++){
						if(tmp[k].value!=""){
							tmp[k].value=gfn_cashReturn(tmp[k].value);
						}
					}

				}
			}
		}
	}
	if(vdtNames!=undefined){
		for(var i=0;i<vdtNames.length;i++){
			tmp = document.all(vdtNames[i]);

			if(tmp!=undefined){
				if(tmp.length==undefined){
					if(tmp.value!=""){	
						if(tmp.value.length==8){						
							tmp.value=tmp.value.substring(0,4)+"-"+
							tmp.value.substring(4,6)+"-"+
							tmp.value.substring(6,8);
						}
					}
				}else{
					for(var k=0;k<tmp.length;k++){
						if(tmp[k].value!=""){	
							if(tmp[k].value.length==8){						
								tmp[k].value=tmp[k].value.substring(0,4)+"-"+
								tmp[k].value.substring(4,6)+"-"+
								tmp[k].value.substring(6,8);
							}
						}
					}
				}
			}
		}
	}

}

/**
 *  인쇄영역을 지정한대로  인쇄
 */	
function gfn_printPopup(sizeW,sizeH)
{
	var nLeft = screen.width/2 - sizeW/2 ;
	var nTop  = screen.height/2 - sizeH/2 ;
	opt = ",toolbar=no,menubar=no,location=no,status=no,resizable=yes,scrollbars=yes";
	window.open("/common/PrintScreenChk.jsp", "printWindow", "left=" + nLeft + ",top=" +  nTop + ",width=" + sizeW + ",height=" + sizeH  + opt );
}
/**
 * 문자열의 바이트 길이를 리턴
 */
function gfn_getByte(s){
   	var len = 0;
	if ( s == null ) return 0;
   	for(var i=0;i<s.length;i++){
      	var c = escape(s.charAt(i));
      	if ( c.length == 1 ) len ++;
      	else if ( c.indexOf("%u") != -1 ) len += 2;
      	else if ( c.indexOf("%") != -1 ) len += c.length/3;
   	}
   	return len;
}
/**
 *  문자열에 있는 특정문자패턴을 다른 문자패턴으로 바꾸는 함수.
 */
function replace(targetStr, searchStr, replaceStr)
{
	var len, i, tmpstr;

	len = targetStr.length;
	tmpstr = "";

	for ( i = 0 ; i < len ; i++ ) {
		if ( targetStr.charAt(i) != searchStr ) {
			tmpstr = tmpstr + targetStr.charAt(i);
		}
		else {
			tmpstr = tmpstr + replaceStr;
		}
	}
	return tmpstr;
}

/**
 *	orgChar 문자열에서 rmChar문자열을 없애고 리턴한다
 *	계좌번호나 금액에서 '-'나 ','를 제거할때 사용한다
 */
function removeChar(orgChar, rmChar){
    return replace(orgChar,rmChar,"");
}
function gfn_cashObj(input){
	if(input==undefined||input.value=='')
		return null;
	input.value=gfn_cashReturn(input.value);
	return true;
}
//숫자를 금형으로 리턴
function gfn_cashReturn(num)
{    
	var num = removeChar(num+"",",");
	if(isNaN(num)){
		alert(num+" "+'숫자만 입력가능합니다.');
		return;
	}
	var cashReturn = "";
	var preValue="";
	var postValue="";
	var strPoint="";
	var pointIndex = num.indexOf(".");
	var result=0;
	var directive="";
	
	if(pointIndex<0){
		preValue=num;
	}else{
		preValue = num.substring(0,num.indexOf("."));
		postValue = num.substring(num.indexOf(".")+1, num.length);
		if(postValue.length>3){
			postValue = postValue.substring(0,2);
		}
		strPoint=".";
	}
	if(preValue.charAt(0)=='-'){
		directive="-";
		preValue=preValue.substring(1);		
	}
	for(var i=preValue.length-1;i>=0;i--){
		cashReturn = preValue.charAt(i) + cashReturn;
		if(i != 0 && i%3 == preValue.length%3) cashReturn = "," + cashReturn;
	}

	return directive+cashReturn+strPoint+postValue;
}

/* 반올림을 처리하는 메서드
   val : 대상 값
   pos : 반올림 위치값
         (+) 소수점 자리수
		 (-) 정수 자리수
*/
function gfn_exRound(val, pos)
{
    var rtn;
	if(pos>0){
		rtn = Math.round(val * Math.pow(10, Math.abs(pos)-1));
		rtn = rtn / Math.pow(10, Math.abs(pos)-1);
	}else{
		rtn = Math.round(val / Math.pow(10, Math.abs(pos)));
		rtn = rtn * Math.pow(10, Math.abs(pos));
	}

    return rtn;
}

/*파일 첨부 기능 추가 */
 function addFile(){
	var html = 	"<div id='fileDiv"+fileIdx+"'>"+	
				"<input type='hidden' name='CONFFILE_NO'  value='"+fileIdx+"'>"+
				"<input type='hidden' name='DELFLAG' value='N'>"+
	"&nbsp;<input name='file"+fileIdx+"'  style='width:60%;  background-color:#FFFFFF;text-align:left;ime-mode:active; '  size='20' type='file' id='files'>"+
	"<input type='hidden' name='conferFile_div' value='I'>"+
		"&nbsp;&nbsp;&nbsp;<input type=button value='삭제'  onclick=removeFile(fileDiv"+fileIdx+")></div>";
	fileIdx++;
	fileSpan.insertAdjacentHTML('afterBegin',html);
}
function removeFile(fileDiv){
	fileDiv.outerHTML=""; 
}
function updateFiles(obj,fileDiv){	

	var delFlag=fileDiv.all["DELFLAG"];
	var pjtFiles_div=fileDiv.all["conferFile_div"];
	if(obj.checked==true){
		delFlag.value='Y';
		pjtFiles_div.value='D'
	}else{
		delFlag.value='N';
		pjtFiles_div.value='C'
	}
}
/*파일다운로드*/
function download(dir,fileSystemName,fileOriginName){
	location.href=	"downLoadFiles.do?dir="+dir+"&fileSystemName="+fileSystemName+"&fileOriginName="+fileOriginName;			
}

/*Popup*/
function gfn_popUp(div,it_no){
	if(div=='BIZ'){
		popupSearchN('biz_new','biz_'+it_no,'BIZCD','BIZCD','BIZNAME','BIZTYPECD','','','600',it_no+"");

	}else{
		var v_div=document.all("IT_NO");
		var idx=0;
		if(v_div!=undefined){
			if(v_div.length!=undefined){
				for(var i=0;i<v_div.length;i++){
					if(v_div[i].value==it_no){
						idx=i;break;
					}
				}
			}
		}
		if(div=='ACD'){		
			popupSearchN('ACD','','IT_TYPE','IT_TYPE','IT_TYPE_NAME','','','','600',i+"");
		}else if(div=='CUST'){
			popupSearchN('cust','','BUY_CORPCD','','BUY_CORPNAME','','','','600',i+"")
		}else if(div=='IV'){
			popupSearchN('ivp_new','ivp_0','IVIT_NO','IVIT_NO','IVIT_NAME','','','','700',i+"")
		}
	}
	
}

function gfn_enterEvent(){
	if(event.keyCode == 13){
		openerReload();
	}
}

function setFinder(src,idx,value){
	var obj =document.all(src);
	if(obj != undefined){
		if(obj.length != undefined ){
			obj[idx].value=value;
		}else{
			obj.value=value;
		}
	}
}

function getFinder(src,value){
	var obj =document.all(src);
	var returns = 0;
	if(obj != undefined){
		if(obj.length != undefined ){
			for(var i=0;i<obj.length;i++){
				if(obj[i].value==value){
					returns=i;
					break;
				}
			}		
		}
	}
	return returns;
}