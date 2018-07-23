	var selobj; //대상 OBJ
	var pRate=0;  //progress
	var rScale=100; //range
	var acRate=0; //acRate
	var sVal=1;   //stepValue

//Timer 초기화
function timerInit(objName) {
	frm= document.forms[0];
	clearInterval(selobj);
//	pRate=0;  //progress
	sVal= 1 ;
	acRate=1;
	rScale= 100;

//	obj=document.getElementById(objName);	

	if (obj.width=='undefined'){
		objWidth=obj.width;
		objHeight=obj.height;
	}else{
		objWidth=200;
		objHeight=200;
	}
	
//	frm.imgProgress.value=pRate;
//	frm.imgInterval.value=0;
//	frm.imgstepLevel.value=0;
}

//증가값 계산
function sValSet(){
	frm= document.forms[0];
	sVal=Math.pow(sVal, acRate);
	sVal=sVal+acRate;

//	sVal=Math.pow(2,acRate);
//	acRate=1/((Math.log(sVal,10)+10)/1)
//	acRate=Math.log(sVal,2);
//	sVal=sVal+acRate;


//	alert(sVal+'/'+acRate);
//	frm.imgstepLevel.value=sVal;
//	frm.imgInterval.value=sVal;	
//	frm.imgProgress.value=pRate;
	return sVal;
}
//진행처리 
function stepProgress(pType){
	if(pType==1 & pRate/rScale>=1 ){
		clearInterval(selobj);
		timerInit(obj);
		return true;
	}else if(pType==2 & pRate/rScale<=0) {
		clearInterval(selobj);
		timerInit(obj);
		return true;
	}
	return false;
}
//Image 오른쪽 이동
function imgRightMove(obj){
    pRate = pRate+sValSet();
    if(stepProgress(1))pRate=rScale;
	obj.style.left= objWidth*pRate/rScale;
    if(stepProgress(1))pRate=0;
}
//Image 왼쪽 이동
function imgLeftMove(obj){
    pRate = pRate-sValSet();
    if(stepProgress(2))pRate=0;
	obj.style.left= objWidth*pRate/rScale;
    if(stepProgress(2))pRate=0;
}
//Image 아래 이동
function imgDownMove(obj){
    pRate = pRate+sValSet();
    if(stepProgress(1))pRate=rScale;
	obj.style.top= objHeight*pRate/rScale;
    if(stepProgress(1))pRate=0;
}
//Image 위로 이동
function imgTopMove(obj){
    pRate = pRate-sValSet();
    if(stepProgress(2))pRate=0;
	obj.style.top= objHeight*pRate/rScale;;
    if(stepProgress(2))pRate=0;
}
//Image 선명하게 보기 
function imgVividView(obj){
    pRate = pRate+sValSet();
    if(stepProgress(1))pRate=rScale;
	setOpacity(pRate/rScale*100,obj);
    if(stepProgress(1))pRate=0;
}
//Image 흐리게  보기 
function imgDisVividView(obj){
    pRate = pRate-sValSet();
    if(stepProgress(2))pRate=0;
	setOpacity(pRate/rScale*100,obj);
   if(stepProgress(2))pRate=0;
}
//image 0% 보기
function imgStart(obj){
    setOpacity(0,obj);
	pRate=0;
}
//image 100% 보기
function imgEnd(obj){
    setOpacity(100,obj);
	pRate=100;
}

//Image 처리 
function setOpacity(value,obj) {
	obj.style.opacity = value/100;
	obj.style.filter = 'alpha(opacity=' + value + ')';
}
//Progress 증가처리
function progress(obj){
    pRate = pRate+sValSet();

    if(stepProgress(1))pRate=rScale;
	obj.value= pRate/rScale;
    actFn();
}
//Progress 감소처리
function dProgress(obj){
    pRate = pRate-sValSet();
    if(stepProgress(2))pRate=0;
	obj.value= pRate/rScale;
    actFn();
}
//Action Progress
function timeSteper(type,objName){
	obj=document.getElementById(objName);
	timerInit(objName);
	if (type=='+'){//Progress +
		selobj= setInterval("progress(obj)", 10);
	}else if (type=='-'){//Progress -
	    pRate='100';
		selobj= setInterval("dProgress(obj)", 10);	
	}else if (type=='1'){//100% 보기
		imgStart(obj);
	}else if (type=='2'){//100% 보기
		imgEnd(obj);
	}else if (type=='3'){//선명하게 보기 
		selobj= setInterval("imgVividView(obj)", 10); //10000(10초간격으로 실행: 주기적)
	}else if (type=='4'){//흐리게  보기 
		pRate=rScale;  //progress
		selobj= setInterval("imgDisVividView(obj)", 10); //10000(10초간격으로 실행: 주기적)
	}else if (type=='5'){//오른쪽 이동 
		selobj= setInterval("imgRightMove(obj)", 10); //10000(10초간격으로 실행: 주기적)
	}else if (type=='6'){//왼쪽 이동 
		pRate=rScale;  //progress
		selobj= setInterval("imgLeftMove(obj)", 10); //10000(10초간격으로 실행: 주기적)
	}else if (type=='7'){//아래 이동 
		selobj= setInterval("imgDownMove(obj)", 10); //10000(10초간격으로 실행: 주기적)
	}else if (type=='8'){//위 이동 
		pRate=rScale;  //progress
		selobj= setInterval("imgTopMove(obj)", 10); //10000(10초간격으로 실행: 주기적)
	}	
//	obj.value=selobj;
	
//	setTimeout("imgView()", 1000); //10000(10?? ??: ???)
//  clearTimeout();
}