	var selobj; //��� OBJ
	var pRate=0;  //progress
	var rScale=100; //range
	var acRate=0; //acRate
	var sVal=1;   //stepValue

//Timer �ʱ�ȭ
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

//������ ���
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
//����ó�� 
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
//Image ������ �̵�
function imgRightMove(obj){
    pRate = pRate+sValSet();
    if(stepProgress(1))pRate=rScale;
	obj.style.left= objWidth*pRate/rScale;
    if(stepProgress(1))pRate=0;
}
//Image ���� �̵�
function imgLeftMove(obj){
    pRate = pRate-sValSet();
    if(stepProgress(2))pRate=0;
	obj.style.left= objWidth*pRate/rScale;
    if(stepProgress(2))pRate=0;
}
//Image �Ʒ� �̵�
function imgDownMove(obj){
    pRate = pRate+sValSet();
    if(stepProgress(1))pRate=rScale;
	obj.style.top= objHeight*pRate/rScale;
    if(stepProgress(1))pRate=0;
}
//Image ���� �̵�
function imgTopMove(obj){
    pRate = pRate-sValSet();
    if(stepProgress(2))pRate=0;
	obj.style.top= objHeight*pRate/rScale;;
    if(stepProgress(2))pRate=0;
}
//Image �����ϰ� ���� 
function imgVividView(obj){
    pRate = pRate+sValSet();
    if(stepProgress(1))pRate=rScale;
	setOpacity(pRate/rScale*100,obj);
    if(stepProgress(1))pRate=0;
}
//Image �帮��  ���� 
function imgDisVividView(obj){
    pRate = pRate-sValSet();
    if(stepProgress(2))pRate=0;
	setOpacity(pRate/rScale*100,obj);
   if(stepProgress(2))pRate=0;
}
//image 0% ����
function imgStart(obj){
    setOpacity(0,obj);
	pRate=0;
}
//image 100% ����
function imgEnd(obj){
    setOpacity(100,obj);
	pRate=100;
}

//Image ó�� 
function setOpacity(value,obj) {
	obj.style.opacity = value/100;
	obj.style.filter = 'alpha(opacity=' + value + ')';
}
//Progress ����ó��
function progress(obj){
    pRate = pRate+sValSet();

    if(stepProgress(1))pRate=rScale;
	obj.value= pRate/rScale;
    actFn();
}
//Progress ����ó��
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
	}else if (type=='1'){//100% ����
		imgStart(obj);
	}else if (type=='2'){//100% ����
		imgEnd(obj);
	}else if (type=='3'){//�����ϰ� ���� 
		selobj= setInterval("imgVividView(obj)", 10); //10000(10�ʰ������� ����: �ֱ���)
	}else if (type=='4'){//�帮��  ���� 
		pRate=rScale;  //progress
		selobj= setInterval("imgDisVividView(obj)", 10); //10000(10�ʰ������� ����: �ֱ���)
	}else if (type=='5'){//������ �̵� 
		selobj= setInterval("imgRightMove(obj)", 10); //10000(10�ʰ������� ����: �ֱ���)
	}else if (type=='6'){//���� �̵� 
		pRate=rScale;  //progress
		selobj= setInterval("imgLeftMove(obj)", 10); //10000(10�ʰ������� ����: �ֱ���)
	}else if (type=='7'){//�Ʒ� �̵� 
		selobj= setInterval("imgDownMove(obj)", 10); //10000(10�ʰ������� ����: �ֱ���)
	}else if (type=='8'){//�� �̵� 
		pRate=rScale;  //progress
		selobj= setInterval("imgTopMove(obj)", 10); //10000(10�ʰ������� ����: �ֱ���)
	}	
//	obj.value=selobj;
	
//	setTimeout("imgView()", 1000); //10000(10?? ??: ???)
//  clearTimeout();
}