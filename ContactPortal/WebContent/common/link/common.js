/*******************************************************************************
 * WMS ���� JavaScript
 ******************************************************************************/


var GTabobj;  // Tab classó���� ���
var Gobj; // row classó���� ���
var firstX =0 ;	
var firstY =0 ;
var setDiridx='Y';// �ѹ���ġ��
var preObjId ='';
var revCn;		// ������ üũ�� ���
/*******************************************************************************
 * ���� üũ�ϴ� �Լ�
 ******************************************************************************/
/**
 * �Է°��� NULL���� üũ
 */
function isNull(input) {
    if (input.value == null || input.value == "") {
        return true;
    }
    return false;
}

/**
 * �Է°��� �����̽� �̿��� �ǹ��ִ� ���� �ִ��� üũ ex) if (isEmpty(form.keyword)) { alert("�˻�������
 * �Է��ϼ���."); }
 */
function isEmpty(input) {
    if (input.value == null || input.value.replace(/ /gi,"") == "") {
        return true;
    }
    return false;
}
/**
 * �Է°��� �����̽� �̿��� �ǹ��ִ� ���� �ִ��� üũ ex) if (isEmptyVal(form.keyword.value)) {
 * alert("�˻������� �Է��ϼ���."); }
 */
function isEmptyVal(s) {
    if (s == null || s.replace(/ /gi,"") == "") {
        return true;
    }
    return false;
}

// ���� ���������� �˻��Ѵ�
function isDigit(c) 
{
  return ((c >= "0") && (c <= "9"))
}

// ���� ���������� �˻��Ѵ�.
function isInteger(s) 
{
   var i;
	
   if (isEmptyVal(s))
      return false
   
   
   for (i = 0; i < s.length; i++) {
      var c = s.charAt(i);
      if (!isDigit(c)) return false; 
   }
   return true;
}

// ��ȣ�� �ִ� ���������� �˻��Ѵ�.
function isSignedInteger(s) 
{
    if (isEmpty(s))
        return false
    
    var startPos = 0 ;
    
    if ((s.charAt(0) == "-") || (s.charAt(0) == "+"))
       startPos = 1;
    return (isInteger(s.substring(startPos, s.length)))
}

// 100����� �������ϵ��� �Ѵ�.
function isNonnegativeInteger(s) 
{
  return ( isSignedInteger(s) && (parseInt(s) >= 0) && (parseInt(s) <= 100) );
}
// �ð����� �˻�
function isTime(s,mode){
	// mode==1 hour
	// mode==2 mi
	if(!isInteger(s))
		return false;
	if(mode==1){
		if(s<0||s>23)
			return false;
	}else{
		if(s<0||s>59)
			return false;
	}
	return true;

}
// �������������� �˻��Ѵ�.
function isDate(s) 
{
    // alert("1:"+s);
    if (isEmptyVal(s))
        return false;

    // alert("2:"+s);
    if (! (s.length == 10))
        return false;

    // alert("3:"+s);
    if ((!(s.charAt(4) == "-")) || (!(s.charAt(7) == "-")))
        return false;
        
    // alert("4:"+s);
    if ( !isInteger(s.substring(0, 4)) || !isInteger(s.substring(5, 7)) || !isInteger(s.substring(8, 10)) )
        return false;

    // alert("5:"+s);
    if (! isFormatDate(s))
        return false;

   return true;
}

// YYYY-MM-DD�� ���������� �˻��Ѵ�.
function isFormatDate(s) 
{
  // alert("6:"+s);
  if ( isMMDate(s.substring(5, 7)) && isDDDate(s.substring(8, 10)) )
  return true
  else
  return false
}

// ���Է½� �ش޿��� �´����� �˻��Ѵ�.
// YYYY-MM-DD?? ???????? ????.
function rFormatDate(inputDt) 
{   
	var inputDt = inputDt.replace("-","")
	yyyy= inputDt.substring(0,4);
	mm= inputDt.substring(4,6);
	dd= inputDt.substring(6,8);
	
	inputDt = yyyy + "-" + mm + "-" + dd ;
 return inputDt
}

// ???????? ???????? ???????? ????????.
function isMMDate(s) 
{
    // alert("7:" + s +" int :" + parseInt(s));
    if (s.length > 1) {
      if (s.charAt(0) == 0) { s = s.substring(1, 2) }
    }
    // alert("7-1:" + s +" int :" + parseInt(s));
      
    return ((parseInt(s) > 0) && (parseInt(s) <= 12))
}

// ���Է½� �ش� ���ڰ� ���ڸ� �Ѵ��� �˻��Ѵ�.
function isDDDate(s) 
{
    // alert("8:"+s +" int :" +parseInt(s));
    if (s.length > 1) {
      if (s.charAt(0) == 0) { s = s.substring(1, 2) }
    }
    return ((parseInt(s) > 0) && (parseInt(s) <= 31))
}


/**
 * �Է°��� Ư�� ����(chars)�� �ִ��� üũ Ư�� ���ڸ� ������� ������ �� �� ��� ex) if
 * (hasChars(form.name,"!,*&^%$#@~;")) { alert("�̸� �ʵ忡�� Ư�� ���ڸ� ����� �� �����ϴ�."); }
 */
function hasChars(input,chars) {
    for (var inx = 0; inx < input.value.length; inx++) {
       if (chars.indexOf(input.value.charAt(inx)) != -1)
           return true;
    }
    return false;
}

/**
 * 
 * @param a
 * @return
 */
function hasSChar(a){
	var RegExp = /[\{\}\/?;|*~`,!^\+��<>@\#$%&\'\"\\\=]/gi;// ���Խ� ����
	var obj =a
	if (RegExp.test(obj.value)) {
		alert("�̸� �ʵ忡�� Ư�� ���ڸ� ����� �� �����ϴ�.");
		obj.value = obj.value.substring(0, obj.value.length - 1);// Ư�����ڸ� �����
																	// ����
		return false;
	} 
	return true;
}

/**
 * �Է°��� Ư�� ����(chars)������ �Ǿ��ִ��� üũ Ư�� ���ڸ� ����Ϸ� �� �� ��� ex) if
 * (!hasCharsOnly(form.blood,"ABO")) { alert("������ �ʵ忡�� A,B,O ���ڸ� ����� �� �ֽ��ϴ�."); }
 */
function hasCharsOnly(input,chars) {
	// alert(input);
	// alert(input.value);
	// alert(input.value.length);
    if(input.value.length==null){
       if (chars.indexOf(input.value) == -1)
           return false;
    }else{
	    for (var inx = 0; inx < input.value.length; inx++) {
	       if (chars.indexOf(input.value.charAt(inx)) == -1)
	           return false;
	    }
    }
    return true;
}

/**
 * �Է°��� ���ڸ� �ִ��� üũ (��ȣ �Է¶� üũ. �ݾ��Է¶��� isNumComma�� ����ؾ� �մϴ�.)
 */
function isNumber(input) {
    var chars = "0123456789";
    return hasCharsOnly(input,chars);
}
function isNumber1(input) {
    var chars = "0123456789";
    if(!hasCharsOnly(input,chars))
    {
    	alert("���ڸ� �Է��Ͻ� �� �ֽ��ϴ�.");
    	input.select();
    	return false;
    }
    else
    	return true;
}
/**
 * �Է°��� ����,���(-)�� �Ǿ��ִ��� üũ (���¹�ȣ �Է¶� üũ)
 */
function isNumDash(input){
    var chars = "-0123456789";
    if(!hasCharsOnly(input,chars))
    {
    	alert("���¹�ȣ�� ���ڿ� '-'�� �Է� �����մϴ�");
    	input.select();
// input.value = '';
// input.focus();
    	return false;
    }
    else
    	return true;
}

/**
 * �Է°��� ����,���(-)�� �Ǿ��ִ��� üũ (��ȭ��ȣ �Է¶� üũ)
 */
function isPhoneNum(input){
    var chars = "-0123456789";
    if(!hasCharsOnly(input,chars))
    {
    	alert("��ȭ��ȣ�� ���ڿ� '-'�� �Է� �����մϴ�");
    	input.select();
    	return false;
    }
    else
    	return true;
}
/**
 * �Է°��� ����,�޸�(,)�� �Ǿ��ִ��� üũ (�ݾ� �Է¶� üũ)
 */
function isNumComma(input){
    var chars = ",0123456789";
    if(!hasCharsOnly(input,chars))
    {
    	alert("���ڿ� ','�� �Է� �����մϴ�");
    	input.select();
    	return false;
    }
    else
    	return true;    
}

function isNumDashComma(input){
    var chars = "-,0123456789";
    if(!hasCharsOnly(input,chars))
    {
    	alert(input.name+"���� ���ڿ� ','��'-'�� �Է� �����մϴ�");
    	input.select();
    	return false;
    }
    else
    	return true;    
}

function isNumCommaDot(input){
    var chars = ",0123456789.";
    if(!hasCharsOnly(input,chars))
    {
    	alert(input.name+"���� ���ڿ� ','��'.'�� �Է� �����մϴ�");
    	input.select();
    	return false;
    }
    else
    	return true;    
}

function isNumDot(input){
    var chars = "0123456789.";
    if(!hasCharsOnly(input,chars))
    {
    	alert(input.name+"���� ���ڿ� '.'�� �Է� �����մϴ�");
    	input.select();
    	return false;
    }
    else
    	return true;    
}


/**
 * ������ �Է� ����
 */
function isAlphabet(input){
    var chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz ";
    return hasCharsOnly(input,chars);
}

/**
 * ���ڸ� �����ϰ� �Է� ����(key����)
 */
function onlyAlphabet(){
   if((event.keyCode>=96 && event.keyCode<=105) ||(event.keyCode>=48 && event.keyCode<=57) ){
	    event.returnValue=false;
    }   
}
/**
 * orgChar ���ڿ����� rmChar���ڿ��� ���ְ� �����Ѵ� ���¹�ȣ�� �ݾ׿��� '-'�� ','�� �����Ҷ� ����Ѵ�
 */
function removeChar(orgChar, rmChar){
    return replace(orgChar,rmChar,"");
}

/**
 * �Է°����� �޸��� ���ְ� ���ڿ� ����. --> �ǵ��� removeChar �� ������. (-_-)
 */
function removeComma(input) {
    return input.value.replace(/,/gi,"");
}

/**
 * �Է°����� '-'�� ���� set --> ��͵�.. �ǵ��� removeChar �� ������. (-_-)
 */
function setUnFormat(input){
	input.value = replace(input.value,"-","");
}

/**
 * �н����� �Է¶� üũ check : size 4 , ���ڸ��Է�
 */
function isPassword(input)
{
	var chars = "0123456789";
	if(isEmpty(input))
	{
		alert(input.name+'�� �Է��Ͻʽÿ�');
		input.select();
    	return false;
	}
	
    else if(!hasCharsOnly(input,chars))
    {
    	alert(input.name+'�� ���ڸ� �Է� �����մϴ�');
    	input.select();
    	return false;
    }

    else if(input.value.length != 4)
    {
    	alert(input.name+' ���̴� 4�ڸ��Դϴ�');
    	input.select();

    	return false;
    }
    else 
    	return true;
}

/**
 * Ư������ �ִ��� Ȯ�� ������ false, ������ true����
 */
function hasPeculChar(input)
{
	var chars = trim(input.value);
	if(chars.length == 0)
		return true;
	else
	{
		for(i=0;i<chars.length;i++)
		{ 
			var a = chars.charCodeAt(i); 
			if((a > 32 && a < 48) || (a > 57 && a < 65) || (a > 90 && a < 97))
				return false;
		}
		return true;
	}
}

/**
 * �ѱ۸� ����
 */
function isHangul(input)
{
	var str = input.value;
    for(var idx=0;idx < str.length;idx++)
    {
        var c = escape(str.charAt(idx));
        if ( c.indexOf("%u") == -1 ) 
        {
            return false;
        }
    }
    return true;
}

function isHanOrPecChar(input)
{
	alert(hasPeculChar(input)+':'+isHangul(input));
	if((!hasPeculChar(input)) || (isHangul(input)))
		return false;
	else
		return true;
	
}

/**
 * ���� & ���ڸ� �Է� ���� (2002.06.25)
 */
function isAlphaNum(input){
    var chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789 ";
    return hasCharsOnly(input,chars);
}
function isEmailChk(input){
    var chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@. ";
    if(!hasCharsOnly(input,chars))
    {
    	alert("E-Mail�� ����, ����, '@','.' �� �Է� �����մϴ�");
    	input.select();
// input.value = '';
// input.focus();
    	return false;
    }
    else
    	return true;
}


/**
 * �ѱ� �Է� �Ұ� �ѱ� ������ false, �ƴϸ� true���� (2002.06.25)
 */

function preventHan(input){ 
	var chars = input.value;
	for(i=0;i<chars.length;i++) {
		var a = chars.charCodeAt(i);
		if (a > 128) { 
			alert(input.name+'�� �ѱ��� �Է��� �� �����ϴ�'); 
			input.select();
			return false; 
		}
	}
	return true;
}

/*******************************************************************************
 * ��ȯ ���� �����ʵ� üũ case --> to upper case �ѱ��Է� �Ұ�
 */
function checkEngField(input)
{
	if(!preventHan(input))
	{
		return;
	}
	else
		input.value=input.value.toUpperCase();
}

/*******************************************************************************
 * ��ȯ ���� �����ʵ� üũ
 */
function checkNumField(input)
{
	if(!isNumber(input))
	{
		alert(input.name+'�� ���ڷ� �Է��Ͻʽÿ�');
		input.select();
		return false
	}
	return true;
}

/**
 * �־��� ���̿� �°� c�� ä���(��������) fillChar(input, 5, '0') --> (input.value :22) 22000
 */
function fillChar(input, leng, c)
{
	var i;
	var rtn = "";
	var val = input.value;
	for ( i = 0; i < leng - val.length; i++ )
	{
		rtn = c + rtn;
	}
	rtn = val + rtn;
	input.value = rtn;
}

// �ڸ��� ä��� (�տ��� ����)
function fillCharL(input, leng, c)
{
	var i;
	var rtn = "";
	
	// ����Ʈ 2�ڸ�
	if (leng==undefined) leng=2;

	// ����Ʈ 0 ���� ä���
	if (c==undefined) c="0";
	
	for ( i = 0; i < leng - input.length; i++ )
	{
		rtn = c + rtn;
	}
	rtn = rtn+ input;
	return rtn;
}

/**
 * �Է°��� ����Ʈ ���̸� ���� ex) if (getByteLength(form.title) > 100) { alert("������ �ѱ�
 * 50��(���� 100��) �̻� �Է��� �� �����ϴ�."); }
 */
function getByteLength(input){
    var byteLength = 0;
    for (var inx = 0; inx < input.value.length; inx++) {
        var oneChar = escape(input.value.charAt(inx));
        if ( oneChar.length == 1 ) {
            byteLength ++;
        } else if (oneChar.indexOf("%u") != -1) {
            byteLength += 2;
        } else if (oneChar.indexOf("%") != -1) {
            byteLength += oneChar.length/3;
        }
    }
    return byteLength;
}

/**
 * ���ڿ��� ����Ʈ ���̸� ���� ex) if (getByteLength(form.title) > 100) { alert("������ �ѱ�
 * 50��(���� 100��) �̻� �Է��� �� �����ϴ�."); }
 */
function getByte(s){
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
 * ���ڿ��� �ִ� Ư������������ �ٸ� ������������ �ٲٴ� �Լ�.
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
 * ���ڿ��� �ִ� Ư������������ �ٸ� ������������ �ٲٴ� �Լ� ���ʺ��� � ���� ���� �ϰ� ������ (ex)0000040540 ==>
 * 40540 �ٸ����ڿ��� ���ý� replace�ߴ� ���ھտ� 0�� �پ ���ýø� ��� �ٶ�.
 */
function replaceStr(targetStr, searchStr, replaceStr)
{
	var len, i, tmpstr;

	len = targetStr.length;
	tmpstr = "";

	for ( i = 0 ; i < len ; i++ ) {
		if ( targetStr.charAt(i) != searchStr ) {
			tmpstr = tmpstr + targetStr.charAt(i);
			searchStr ="A";
		}
		else {
			tmpstr = tmpstr + replaceStr;
		}
	}
	return tmpstr;
}
/**
 * ���ڿ����� �¿� ��������
 */
function trim(str)
{
	return replace(str," ","");
}
 
/**
 * 2002.05.30. string, null --> integer
 */
function toInt(str)
{
	var num = parseInt(str, 10);
	if(isNaN(num))
		return 0;
	else
		return num;
}


/**
 * ���ڸ� �ݾ��������� ���� (000,000,000)
 */
 // ����
 /*
	 * function cashReturn3(num) { var numValue = ""+num; var cashReturn3 = "";
	 * for (var i = numValue.length-1; i >= 0; i--){ cashReturn3 =
	 * numValue.charAt(i) + cashReturn3; if (i != 0 && i%3 == numValue.length%3)
	 * cashReturn3 = "," + cashReturn3; } return cashReturn3; }
	 */
// ����
function cashReturn(num)
{
	var num = removeChar(num+"",",");
	if(isNaN(num)){
		if(num!="-")
		{
		alert('���ڸ� �Է°����մϴ�.');
		return '';
		}
	}
	var cashReturn = "";
	var preValue="";
	var postValue="";
	var strPoint="";
	var pointIndex = num.indexOf(".");
	var result=0;
	var directive="";
	
	// alert("3::"+pointIndex);
	if(pointIndex<0){
		preValue=num;
	}else{
		preValue = num.substring(0,num.indexOf("."));
		postValue = num.substring(num.indexOf(".")+1, num.length);
		if(postValue.length>3){
			// alert("4:::"+postValue);
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
function cashObj(input){	
	if(input.value=='')
		return null;
	input.value=cashReturn(input.value);
	return true;
}
// ���ڿ��� �Ҽ��� i��° ���� �߶󳻱� dotRound�� ����ϼ���
function dotCut(num,i){
	var returns="";
	var preValue="";
	var postValue="";
	var idxPoint="";
	var strPoint="";
	
	if(num.indexOf(".")>0){
		idxPoint=num.indexOf(".");
		strPoint=".";
		postValue=num.substring(idxPoint+1,num.length);
		if(postValue.length >= i){
			postValue = postValue.substring(0,i-1);
			// alert("portValue::"+postValue);
		}
	}else{
		idxPoint=num.length;
	}
	
	preValue=num.substring(0,idxPoint);

	returns = preValue+strPoint+postValue;
	return returns;
}

function dotRound(num,i)
{
    var returns;
    returns = Math.round(num * Math.pow(10, Math.abs(i)-1))
    returns = returns / Math.pow(10, Math.abs(i)-1)
    
    return returns;
}

/*******************************************************************************
 * form ���� �Լ�
 ******************************************************************************/
/**
 * radio : ���õ� radio��ư�� �ִ��� üũ
 */
function hasCheckedRadio(input) {
    if (input.length > 1) {
        for (var inx = 0; inx < input.length; inx++) {
            if (input[inx].checked) return true;
        }
    } else {
        if (input.checked) return true;
    }
    return false;
}

/**
 * radio : radio���� ���õ� ���� �����´�.
 */
function getCheckedRadio(input)
{
	var val;
	var len = input.length;
	
	if(len > 1)
	{
		for(var i = 0 ; i < len ; i++)
		{
			if(input[i].checked == true)
				val = input[i].value;
		}
		return val;
	}
	else
		if(input.checked == true)
			return input.value;
}

/**
 * checkbox : ���õ� üũ�ڽ��� �ִ��� üũ
 */
function hasCheckedBox(input) {
    return hasCheckedRadio(input);
}

/**
 * checkbox : ȭ�鿡 �����Ǿ��ִ� ��� üũ�ڽ��� ���õ� ������ ���Ѵ�.
 */
function get_checked(){
	var checked_cnt = 0;
	for ( i = 0; i < document.forms.length; i++ ) {
		for ( j = 0; j < document.forms[i].elements.length; j++ ) {
			if ( document.forms[i].elements[j].type == 'checkbox' ) {
				if ( document.forms[i].elements[j].checked ) {
					checked_cnt++;
				}
			}
		}
	}
	return checked_cnt;
}

/**
 * checkbox : ���õ� üũ�ڽ��� ����� �� ������ ��ȯ
 */
function hasMultiCheckedRadio(input) {
	var kkkk = 0;
    if (input.length > 1) {
        for (var inx = 0; inx < input.length; inx++) {
            if (input[inx].checked) {
			kkkk++;
			}
        }
    } else {
		 if (input.checked) kkkk=1;
	}
    return kkkk;
}

/**
 * checkbox : ȭ�鿡 �����Ǿ� �ִ� üũ�ڽ��� ������ ����
 */
function getCheckBoxCnt()
{
	rtnCnt	=	0;
	for ( i=0;i < document.forms[0].elements.length;i++)
	{
		if	(document.forms[0].elements[i].type == "checkbox")
		{
			rtnCnt++;
		}
	}
	return	rtnCnt;
}

/**
 * checkbox : ���� �ӿ� �ִ� üũ�ڽ��� ��� ����/�����ϰ� �Ѵ�
 */
function setCheckBoxStatus(flag)
{
	for ( i=0;i < document.forms[0].elements.length;i++)
	{
		if	(document.forms[0].elements[i].type == "checkbox")
		{
			if	(flag	==	"T")
			{
				document.forms[0].elements[i].checked=true;
			}
			else
			{
				document.forms[0].elements[i].checked=false;
			}
		}
	}
}

/**
 * checkbox : ���� �ӿ� �ִ� üũ�ڽ��� ��� Ȱ��/��Ȱ���ϰ� �Ѵ�
 */
function setCheckBoxUse(flag)
{
	for ( i=0;i < document.forms[0].elements.length;i++)
	{
		if	(document.forms[0].elements[i].type == "checkbox")
		{
			if	(flag	==	"T")
			{
				document.forms[0].elements[i].disabled=true;
			}
			else
			{
				document.forms[0].elements[i].disabled=false;
			}
		}
	}
}

/**
 * select : select���� str���� ���� option�� ���õǵ��� ����
 */
function setSelect(input,str) {
	for(i=0;i<input.options.length;i++){
		if(input.options[i].value == str)
			input.options[i].selected=true;
	}
}
/**
 * select : select�� options�� �� ����
 */
function dpOptions(input)
{
	var len = input.length;
	for(var i=0; i<len; i++) 
		input.options[0]=null;
}
/**
 * select : select���� ���õ� �� ���� (2002.06.11)
 */
function getSelectedOption(obj)
{
	var idx = obj.selectedIndex;
	var v_sel = obj.options[idx].value;
	return v_sel;
}
/**
 * ��â ���� �Լ�(scrollbars=yes,full size)
 */
function openFullWin(url, winName)
{
	opt = ",toolbar=no,menubar=no,location=no,status=no,resizable=yes,channelmode=yes,scrollbars=yes";	
	window.open(url, winName, opt);	
}
/**
 * ��â ���� �Լ�(scrollbars=yes)
 */
function openWin(url, winName, sizeW, sizeH)
{
	var nLeft = screen.width/2 - sizeW/2 ;
	var nTop  = screen.height/2 - sizeH/2 ;
	opt = ",toolbar=no,menubar=no,location=no,status=no,resizable=yes,scrollbars=yes";
	window.open(url, winName, "left=" + nLeft + ",top=" +  nTop + ",width=" + sizeW + ",height=" + sizeH  + opt );
}
/**
 * ��â ���� �Լ�(scrollbars=no,full size)
 */
function openFullWinFix(url, winName)
{

	opt = ",toolbar=no,menubar=no,location=no,status=no,resizable=yes,channelmode=yes,scrollbars=no";	
	window.open(url, winName, opt );
}
/**
 * ��â ���� �Լ�(scrollbars=yes)
 */

/**
 * ��â ���� �Լ�(scrollbars=no)
 */
function openWinFix(url, winName, sizeW, sizeH)
{
	var nLeft  = screen.width/2 - sizeW/2 ;
	var nTop  = screen.height/2 - sizeH/2 ;

	opt = ",toolbar=no,menubar=no,location=no,status=no,scrollbars=no";
	window.open(url, winName, "left=" + nLeft + ",top=" +  nTop + ",width=" + sizeW + ",height=" + sizeH  + opt );
}

/**
 * �Ⱥ����� �ʰ� ��â ���� �Լ�
 */
function openWinHidden(url, winName, sizeW, sizeH)
{
	opt = ",toolbar=no,menubar=no,location=no,status=no,resizable=yes,scrollbars=yes";
	window.open(url, winName, "left=" + screen.width + ",top=" +  screen.width + ",width=" + sizeW + ",height=" + sizeH  + opt );

}
function openModalWin(url, winName, sizeW, sizeH)
{
	if (sizeW==null || sizeW=="" || sizeW==undefined){width=500};
	if (sizeH==null || sizeH=="" || sizeH==undefined){height=400};
	
	showModalDialog(url,
					window,
					"dialogWidth:"+sizeW+
					"px;dialogHeight:"+sizeH+"px;toolbar:0;resizable:0;status:no;help:no");
}
/**
 * �Է°��� maxlength="00" ���� �����Ǿ� ���� ��� �� ���̸� �ʰ��Ͽ����� ����(�ѱ��� ��� 2byte �� ����ϹǷ� ����) �ش�
 * �������� �ִ� text, textarea, password�� �� ��� üũ�Ѵ�.
 * 
 * �ѱ۷� �Է¹޴� field�� �ִ� ���, maxlength�� ������ �Ŀ� submit�ϱ� ���� isOverLen()�� ����ؼ� ������
 * �ʰ��Ǵ� ���� ����� �� �ִ�.
 * 
 * ex) if (isOverLen()) return;
 */
function isOverLen(){
	for(frmIdx=0;frmIdx<window.document.forms.length;frmIdx++){
		objFrm=window.document.forms[frmIdx];
		for(elemIdx=0;elemIdx<objFrm.elements.length;elemIdx++){
			objElem=objFrm.elements[elemIdx];
			if( (objElem.type=="text") || (objElem.type=="textarea")  || (objElem.type=="password") ){
				if(objElem.maxLength != null){
					
					if (objElem.maxLength < getByte(objElem.value)){
						alert(objElem.name+"�� ���ѵ� ���̸� �ʰ� �Ͽ����ϴ�.\n�ٽ� �Է��Ͽ� �ֽʽÿ�.");
						objElem.select();
						return true;
					}
				}
			}
		}
	}
	return false;
}

/**
 * SELECT���� ���õ� ��¥�� 'YYYYMMDD'������ ���ڿ��� ���� (2002.06.08)
 */
function getDayString(obj_yy, obj_mm, obj_dd){
	
	var i_yy = obj_yy.selectedIndex;
	var i_mm = obj_mm.selectedIndex;
	var i_dd = obj_dd.selectedIndex;
		
	var v_yy = obj_yy.options[i_yy].value;
	var v_mm = obj_mm.options[i_mm].value;
	var v_dd = obj_dd.options[i_dd].value;	
	
    return ""+v_yy+v_mm+v_dd;
}

/*******************************************************************************
 * ��Ÿ Ư�������� �� üũ
 ******************************************************************************/
/**
 * �ֹε�Ϲ�ȣ üũ.
 */
function isValidSsn(userSid1,userSid2){
   var ju = userSid1.value;
   var ju1 = userSid2.value;
   juid = new Array(0,0,0,0,0,0,0,0,0,0,0,0,0);

	if(!isNumber(userSid1) || !isNumber(userSid2))
		return false;

	if(getByteLength(userSid1)!=6 || getByteLength(userSid2)!=7)
		return false;

	for(var i = 0; i<6;i++)
		juid[i] = ju.substring(i,i+1);
	for(i=0;i<7;i++)
		juid[i+6] = ju1.substring(i,i+1);
    for(var sum = 0, i = 0;i<12;i++)
		sum += juid[i] * ((i >7) ? (i-6) : (i+2));

    var mod = 11 - sum%11;
	if(mod >= 10)
		mod -= 10;

	if(mod != juid[12])
          return false;
    else
		  return true;
}

/**
 * ����ڵ�Ϲ�ȣ üũ.
 */
function isValidOffNum(input){
	tmpStr 			= input.value;
	tmpSum			= new Number(0);
	tmpMod			= new Number(0);
	resValue			= new Number(0);
	var intOffNo 		= new Array(0,0,0,0,0,0,0,0,0,0);
	var strChkNum 	= new Array(1,3,7,1,3,7,1,3,5);

	for(i = 0 ; i < 10 ; i ++){
		intOffNo[i] = new Number(tmpStr.substring(i, i+1));
	}

	for(i = 0 ; i < 9 ; i ++){
		tmpSum = tmpSum + (intOffNo[i]*strChkNum[i]);
	}

	tmpSum = tmpSum + ((intOffNo[8]*5)/10);

	tmpMod = parseInt(tmpSum%10, 10);

	if(tmpMod == 0){
		resValue = 0;
	}
	else{
		resValue = 10 - tmpMod;
	}

	if(resValue == intOffNo[9]){
		return true;

	}
	else{
		alert('��ȿ�� ����ڵ�Ϲ�ȣ�� �ƴմϴ�');
		input.select();
		return false;
	}
}

/**
 * �ڵ� ��Ŀ�� �̵�(���簴ü, �̵���ü, MaxLength)
 */
function autoFocus(input1, input2, maxLen) {
	if(input1.value.length == maxLen && event.keyCode != 9 && event.keyCode != 16) input2.focus() ;
}

/*******************************************************************************
 * ��¥ ���� function
 ******************************************************************************/
/**
 * �� ��¥�� ���� ���̸� ���� date1:��������, date2:��������
 */
function getDayBetween(date1,date2) { 
	    date1.setMilliseconds(0);
	    date2.setMilliseconds(0);	   
		var day_gab = Math.floor( (date2-date1) / (60*60*24*1000) )
		return day_gab ;
}

/**
 * ���� ���� �Ķ���ͷ� �Ѱ��ָ� �ش�Ǵ� ���� �� ���� ����
 */
function getLastday(year,mon){
	if (mon == 4 || mon==6 || mon==9 || mon==11)
	{
		intLastDay=30;
	}
	else if (mon==2 && !(year % 4 == 0))
	{
		intLastDay=28;
	}
	else if (mon==2 && year % 4 == 0)
	{
		if (year % 100 == 0)
		{
			if (year % 400 == 0)
				intLastDay=29;
			else
				intLastDay=28;
		}
		else
		{
			intLastDay=29;
		}
	}
	else
	{
		intLastDay=31;
	}
	return intLastDay
}

/**
 * ������ �⵵, ���� ���� �� select�� ��¥�� display�Ѵ�
 */
 function displayDay(obj_year, obj_month, obj_day) {
 	var YEAR=obj_year.options[obj_year.selectedIndex].value;
 	var MONTH=obj_month.options[obj_month.selectedIndex].value;
 	var daysInMonth=new Date(new Date(YEAR,MONTH,1)-86400000).getDate();
 	for(var i=0; i<obj_day.length; i++) obj_day.options[i]=null;
 	for(var j=0; j<daysInMonth; j++) {
 		if(j<9) var k="0"+(j+1); else var k=j+1;
 		obj_day.options[j]=new Option(k, k);
 	}
 }

/**
 * fromDt, toDt�� ��¥ ���� ��.. from < to�̸� 1�� , from > to�̸� -1, ������ 0����
 * (2002.07.03)
 */
function getSequence(fromDt, toDt)
{
	var fromDate = new Date();
	var f_yy = fromDt.substr(0, 4);
	var f_mm = fromDt.substr(4, 2);
	var f_dd = fromDt.substr(6, 2);
	fromDate.setYear(f_yy);	
	fromDate.setMonth(f_mm);
	fromDate.setDate(f_dd);
	
	var toDate = new Date();
	var t_yy = toDt.substr(0, 4);
	var t_mm = toDt.substr(4, 2);
	var t_dd = toDt.substr(6, 2);
	toDate.setYear(t_yy);	
	toDate.setMonth(t_mm);
	toDate.setDate(t_dd);
	
	var interval = toDate-fromDate;
	
	if(interval > 0)
		return 1;
	else if(interval == 0)
		return 0;
	else
		return -1;
}

/**
 * ��¥�� y, m, d��ŭ �̵��ؼ� ���� (dt : YYYYMMDD(���ڿ�), ����Ÿ�� : YYYYMMDD) y, m, d : +�� �־���
 * ��¥�� ������ �̵�(���ϱ�), -�� �־��� ��¥�� �ڷ� �̵�(����) (2002.06.08)
 */
function shiftDate(dt,y,m,d)
{
	var org_dt = new Date();
	var yy = dt.substr(0, 4);
	var mm = dt.substr(4, 2);
	var dd = dt.substr(6, 2);
	org_dt.setYear(yy);
	org_dt.setMonth(mm-1);
	org_dt.setDate(dd);
	var new_dt = org_dt;
	new_dt.setDate(org_dt.getDate() + d);
	new_dt.setMonth(new_dt.getMonth() + m);
	new_dt.setYear(new_dt.getYear() + y);

	var n_yy  = new_dt.getFullYear();
    var n_mm = new_dt.getMonth()+1;
    var n_dd   = new_dt.getDate();

    if (("" + n_mm).length == 1) 	{ n_mm = "0" + n_mm; 	}
    if (("" + n_dd).length   == 1) 	{ n_dd = "0" + n_dd;  }

	return ""+n_yy+n_mm+n_dd;
}

/**
 * �����ϰ� �������� �ְ�, �������� �������� �������� �ش� interval��ŭ ����ؼ� �����Ѵ�. 0 : 3���� 1 : 1���� �� 2 :
 * 1���� �� 3 : 3���� �� 4 : 1���� 5 : 3���� 6 : 5���� (2003.03.27)
 */
function changeDate(f_yy, f_mm, f_dd, t_yy, t_mm, t_dd, i)
{
	var dminus = 0;
	var mminus = 0;
	
	var from;
	var date=new Date();
	var yy;
	var oldfrdate1=new Date();
	var oldfr1yy;
	var minus;
	j=t_yy.selectedIndex;
	date.setYear(t_yy.options[j].value);
	j=t_mm.selectedIndex;
	date.setMonth(t_mm.options[j].value-1);
	j=t_dd.selectedIndex;
	date.setDate(t_dd.options[j].value);
	switch(i){
		case 0:
			dminus = 3 
			from=date.getDate() - dminus;
			date.setDate(from);
			break;	
		case 1:
			dminus = 6;
			from=date.getDate()- dminus;
			date.setDate(from);
			break;
		case 2:
			mminus = 1;
			from=date.getMonth()-mminus;
			date.setMonth(from);
			break;
		case 3:	
			mminus = 3;
			from=date.getMonth()-mminus;
			date.setMonth(from);
			break;
		case 4:
			mminus = 1;
			from=date.getYear()-mminus;
			date.setYear(from);
			break;
		case 5:
			mminus = 3;
			from=date.getYear()-mminus;
			date.setYear(from);
			break;
		case 6:
			mminus = 5;
			from=date.getYear()-mminus;
			date.setYear(from);
			break;
		}
	yy=date.getYear();
	oldfrdate1.setYear(f_yy.options[0].text);
	olfr1yy=oldfrdate1.getYear();
	if(yy<olfr1yy){
		if(yy<2000){
				yy=yy+1900;
				f_yy.options[0].text=yy;
				for(j=1;j<t_yy.options.length;j++){
				f_yy.options[j].text=(yy+1);
				yy=yy+1;
				}
				f_yy.options[0].selected=true;
		} 
	}	else if(yy<2000){

		yy=yy+1900;	
		}
	for(j=0;j<t_yy.options.length;j++){
		if(f_yy.options[j].text==yy){ 
			f_yy.options[j].selected=true;
		}
	}

	for(j=0;j<t_mm.options.length;j++){
		if(f_mm.options[j].text==date.getMonth()+1){
		f_mm.options[j].selected=true;
		}
	}
	displayDay(f_yy, f_mm, f_dd);
	for(j=0;j<f_dd.options.length;j++){
		if(f_dd.options[j].text==date.getDate()){
			f_dd.options[j].selected=true;
		}
	}
}


/*******************************************************************************
 * MultiSelect ����
 ******************************************************************************/	
function getSelectNum(objName)
{
	// var obj = document.all(objName);
	var	obj	=	objName;
	
	intLoop	=	0;
	for ( i =0; i < obj.length; i ++) 
	{
		if ( obj.options[i].selected )	intLoop++;
	}	
	return	intLoop;
}

function InsertList(objName, strText, strValue)
{
	var	i	=	0;
	// var obj = document.all(objName);
	var	obj		=	objName;
	obj.length++;
	
	if	(obj.selectedIndex	<	0)	obj.selectedIndex	=	0;

	for	(i=obj.length-1;i > obj.selectedIndex;i--)
	{
		obj.options[i].text	=	obj.options[i-1].text;
		obj.options[i].value	=	obj.options[i-1].value;
	}
	
	obj.options[obj.selectedIndex].text		=	strText;
	obj.options[obj.selectedIndex].value	=	strValue;
	
	obj.selectedIndex	=	obj.selectedIndex;

}
/**
 * multi select �̵��� ����ϴ� method parameter : object�� name�� �ƴϰ� ��ü.
 */	
function MoveArrow(objNameFrom, objNameTo)
{

	var	i	=	0;
	var	j	=	0;
	var	k	=	0;
	
	// var objFrom = document.all(objNameFrom);
	var	objFrom		=	objNameFrom;

	var selectedText	=	new Array();
	var selectedValue	=	new Array();
	var unselectedText	=	new Array();
	var unselectedValue	=	new Array();
			
	for ( i =0; i < objFrom.length; i ++) 
	{
		if ( objFrom.options[i].selected )
		{
			selectedText[k]		=	objFrom.options[i].text;
			selectedValue[k]	=	objFrom.options[i].value;
			k++;
		}
		else
		{
			unselectedText[j]	=	objFrom.options[i].text;
			unselectedValue[j]	=	objFrom.options[i].value;
			j++;
		}
	}

	for	(i=getSelectNum(objNameFrom)-1; i >= 0;i--)
	{
		InsertList(objNameTo, selectedText[i], selectedValue[i]);
	}

	objFrom.length	=	objFrom.length	-	getSelectNum(objNameFrom);
	
	for	( i=0; i<objFrom.length;i++)
	{
		objFrom.options[i].text		=	unselectedText[i];
		objFrom.options[i].value	=	unselectedValue[i];
	}
	if	(objFrom.selectedIndex	<	0)
		objFrom.selectedIndex	=	objFrom.length	-	1;
	else
		objFrom.selectedIndex	=	objFrom.selectedIndex;
}

function MoveUp(objName)
{
	// obj = document.all(objName);
	var	obj =	objName;
	
	if	(getSelectNum(objName)	!=	1)
	{
		alert("�̵��ÿ��� �� �ุ �����ؾ��մϴ�.");
		return;
	}

	if	(obj.selectedIndex	==	0)	return;
	
	var	tmpText		=	obj.options[obj.selectedIndex].text;
	var	tmpValue	=	obj.options[obj.selectedIndex].value;
	
	obj.options[obj.selectedIndex].text		=	obj.options[obj.selectedIndex-1].text;
	obj.options[obj.selectedIndex].value	=	obj.options[obj.selectedIndex-1].value;

	obj.options[obj.selectedIndex-1].text	=	tmpText;
	obj.options[obj.selectedIndex-1].value	=	tmpValue;
	
	obj.selectedIndex--;
}

function MoveDn(objName)
{
	// obj = document.all(objName);
	var	obj =	objName;
	
	if	(getSelectNum(objName)	!=	1)
	{
		alert("�̵��ÿ��� �� �ุ �����ؾ��մϴ�.");
		return;
	}
	
	if	(obj.selectedIndex	==	obj.length-1)	return;
	
	var	tmpText		=	obj.options[obj.selectedIndex].text;
	var	tmpValue	=	obj.options[obj.selectedIndex].value;
	
	obj.options[obj.selectedIndex].text		=	obj.options[obj.selectedIndex+1].text;
	obj.options[obj.selectedIndex].value	=	obj.options[obj.selectedIndex+1].value;

	obj.options[obj.selectedIndex+1].text	=	tmpText;
	obj.options[obj.selectedIndex+1].value	=	tmpValue;
	
	obj.selectedIndex++;
}

 


function getFixed(sDate){
	var s;
	var arr;

	s = new String(sDate);
	arr = s.split("-");
	if(arr.length == 3){
		s = arr[0] + "-";
		if(arr[1].length == 1) arr[1] = "0" + arr[1];
		s1 = arr[0];
		s = s + arr[1] + "-";
		s2 = arr[1];
		if(arr[2].length == 1) arr[2] = "0" + arr[2];
		s3 = arr[2];
		s = s + arr[2];
	}else{
		s = sDate;
	}
	return s;
}
/**
 * ���ڸ� ���ڷ� ��ȯ
 */
function parseFull(HalfVal)
{
	var FullChar = [
	               "��", "��","��","��","��","��","��","��","��",    // 33~
	        "��","��","��","��","��","��","��","��","��","��",      // 41~
	        "��","��","��","��","��","��","��","��","��","��",      // 51~
	        "��","��","��","��","��","��","��","��","��","��",      // 61~
	        "��","��","��","��","��","��","��","��","��","��",      // 71~
	        "��","��","��","��","��","��","��","��","��","��",      // 81~
	        "��","��","��","��","��","��","��","��","��","��",      // 91~
	        "��","��","��","��","��","��","��","��","��","��",      // 101~
	        "��","��","��","��","��","��","��","��","��","��",      // 111~
	        "��","��","��","��","��","��"                        	// 121~
	        ];
		var stFinal = "";
        var ascii;
        for( i = 0; i < HalfVal.length; i++)
        {
                ascii = HalfVal.charCodeAt(i);
                if( (31 < ascii && ascii < 128))
                {
                  stFinal += FullChar[ascii-32];
                }
                else
               {
                  stFinal += HalfVal.charAt(i);
                }
        }
        return stFinal;
}

/**
 * ���ڸ� ���ڷ� ��ȯ
 */
function parseHalf(FullVal) {
	var HalfChar = [
	        " ", "!","\"","#","$","%","&","'","(",
	        ")","*","+",",","-",".","/","0","1","2",
	        "3","4","5","6","7","8","9",":",";","<",
	        "=",">","?","@","A","B","C","D","E","F",
	        "G","H","I","J","K","L","M","N","O","P",
	        "Q","R","S","T","U","V","W","X","Y","Z",
	        "[","\\","]","^","_","`","a","b","c","d",
	        "e","f","g","h","i","j","k","l","m","n",
	        "o","p","q","r","s","t","u","v","w","x",
	        "y","z","{","|","}","~"
	        ];
	var stFinal = "";
	var ascii;

	for(var i = 0; i < FullVal.length; i++) {
		ascii = FullVal.charCodeAt(i);
		if (65280 < ascii && ascii < 65375) {
			stFinal += HalfChar[ascii - 65280];
		} else if (12288 == ascii) {
			stFinal += HalfChar[ascii - 12288];
		} else if (65510 == ascii) {
			stFinal += HalfChar[60];
		} else {
			stFinal += FullVal.charAt(i);
		}
	}
	return stFinal;
}

/**
 * e-mailüũ
 */
function isValidEmail(input) {
	if (input.value=="") {
		return true;
	}else{
// var format = /^(\S+)@(\S+)\.([A-Za-z]+)$/;
    var format = /^((\w|[\-\.])+)@((\w|[\-\.])+)\.([A-Za-z]+)$/;
    return isValidFormat(input,format);
  }
}

/**
 * �Է°��� ����ڰ� ������ ���� �������� üũ �ڼ��� format ������ �ڹٽ�ũ��Ʈ�� 'regular expression'�� ����
 */
function isValidFormat(input,format) {
    if (input.value.search(format) != -1) {
        return true; // �ùٸ� ���� ����
    }
    return false;
}

/**
 * day�� ���� ��¥. value�� �����ϰ��� �ϴ� ������ ��
 */
function makeDate(day,value){
	date =new Date();
	date.setYear(day.substring(0,4));
	date.setMonth(day.substring(5,7)-1);
	date.setDate(day.substring(8));

	var dminus = value;
	var from=date.getDate()- dminus;
	date.setDate(from);
	var n_yy  = date.getFullYear();
    var n_mm = date.getMonth()+1;
    var n_dd   = date.getDate();

    if (("" + n_mm).length == 1) n_mm = "0" + n_mm; 	
    if (("" + n_dd).length   == 1) n_dd = "0" + n_dd;  
	return n_yy+"-"+n_mm+"-"+n_dd;
}

/**
 * Ư������ �Է� ���� (���ڸ� ���,space�� ���´�.)48~57
 */
function checkSpecialChar11(){	    
	if(event.keyCode < 48 || event.keyCode >57){ 
		event.returnValue = false;
	}
}

/**
 * �μ⿵���� �����Ѵ�� �μ�
 */	
function printPopup()
{
    var html = "";
    for(var i = 1; i < 10; i++)
    {
        var frm = document.getElementById("printArea" + i);
        if(frm)
        {
            html += frm.innerHTML;
            html += "<br>";

        }
        else
        {
            break;
        }
    }

    var frm = document.forms["printForm"];

    frm.elements["content"].value=escape(html);

    popWindow = window.open("", "printWindow", "width=850,height=500,toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=1,resizable=1");

    frm.target = "printWindow";
    frm.submit();
}	

/*******************************************************************************
 * Function?? : toggleObj(objId,objTab,index,objDivMove,x_position,y_position)
 * Parameter : objid : Object ID Toggle ��� object objTab : '1' - toggle Area ,
 * '2' = tab toggle index : toggle object index objDivMove : '1' DivMove ����
 * x_position : ���콺�� ���� ���� x��ǥ y_position : ���콺�� ���� ���� y��ǥ Return : ?? ?? :
 * 2006.02.16 mailbest
 ******************************************************************************/
function toggleObj(objId,objTab,index,objDivMove,x_position,y_position){
	var toggleObj =document.getElementById(objId+'');
	var toggleObjNm =document.getElementsByName(objId+'');
	var tabObj = '' ;
	var frm = document.forms[0];
	index = index - 1 ;
// alert(x_position);
// alert(y_position);
	try{	
    if(x_position==undefined) x_position=0;
    if(y_position==undefined) y_position=0;
    
	x_position=x_position + 30;
    y_position=y_position + 10;

	if (objTab=='1'){	   
	// textarea ���� �ڵ鸵
		for(var i=0 ;i<toggleObjNm.length;i++ ){		
			if (i!=index ) {
				toggleObjNm[i].style.display='none';
    		}else{
    			toggleObjNm[i].style.display='';
    		}	
		}	
		
			// for(var i=0 ;i<frm.all(objId).length;i++ ){
// if (i!=index ) {
// frm.all(objId)[i].style.display='none';
// }else{
// frm.all(objId)[i].style.display='';
// }
// }
	} else if (objTab=='2'){
	// tab �ڵ鸵
		for(var i=0 ;i<toggleObjNm.length;i++ ){	
			if (i==index){
				if( i%2==0 ){
					toggleObjNm[i].style.display='';	
				}else{				
					toggleObjNm[i-1].style.display='';	
					toggleObjNm[i].style.display='none';	
				}
			}else if (i!=index ){
				if( i%2==0 ){
					toggleObjNm[i].style.display='none';	
				}else if (index!=i-1 ){
					toggleObjNm[i].style.display='';	
				}else{
					toggleObjNm[i].style.display='none';	
				}			
			}
		}
	} else if (objTab=='3'){		
	// �׻���ȸ
		toggleObj.style.display='';	
	} else if (objTab=='4'){
	// �׻� ����ȸ
		toggleObj.style.display='none';	
	}

	if (objTab==undefined){	
		if (toggleObj.style.display=='none'){
			toggleObj.style.display='';		
		}else if (toggleObj.style.display!='none') {	
			toggleObj.style.display='none';
		}
	}

	// ���콺 �̵� ��� ���� �̵�
	if(objDivMove=='1'){
		toggleObj.style.top = document.body.scrollTop + event.y - 10;
		toggleObj.style.left = event.x;
		if (objTab=='1'){	
			toggleObj.style.display='';			
		}else{
			toggleObj.style.display='none';	
		}
		toggleObj.style.top = document.body.scrollTop + event.y + x_position;
		toggleObj.style.left = event.x + y_position ;		
	} 

	// ���콺 Ŭ�� �� �̵�
	if(objDivMove=='2'&& event.button==1 && firstX!=0 && firstY!=0){
		toggleObj.style.top  = event.y - firstY;
		toggleObj.style.left = event.x - firstX;
	} 
	}catch(err){ } return 
}
// obj�� ���콺 �ʱ� ��ġ ����
function setMouseXY(ObjId){
    	var toggleObj =document.getElementById(ObjId) ;
		firstX = event.x - parseInt(toggleObj.style.left) ;	
		firstY = event.y - parseInt(toggleObj.style.top);	
}
// obj�� ���콺 ��ġ ����
function setMouseXYFree(ObjId){
    	var toggleObj =document.getElementById(ObjId) ;
		firstX = 0;	
		firstY = 0;	
}
/*******************************************************************************
 * Function?? : setLevel(highcode, tableName) Parameter : object ID = highcode
 * --> 'Level_�����ڵ�' Return : Date : 2006.06.16 mailbest
 ******************************************************************************/	
function setLevel(highcode, tableName){
  	var frm =  document.forms[0];
  	var tableName = document.getElementById(tableName);
	var arr;

   	// ���� ���� ���� 99����
	arr = highcode.split("_");
	level = eval(arr[0])+1;

	var highcode =  highcode.substr(arr[0].length+1,highcode.length-(arr[0].length+1)); 
	var setlevelCd = level +'_'+ highcode; 

		// ��ȸ�� ������ ������
		if (tableName==null) return;
		
		// ��ü ����
		if (highcode=='All'){
			for(k=0;k< tableName.rows.length;k++){
					tableName.rows[k].style.display='';
			}
		// ��ü �ݱ�
		} else if(highcode=='Def'){
			for(k=1;k< tableName.rows.length;k++){
					tableName.rows[k].style.display='none';
			}
		}
		
		if (document.all(setlevelCd)==null) return;

		
		// ���� level ���� level ����/�ݱ�
		if (document.all(setlevelCd).length==undefined){
	        // ����Level�� �Ѱ� �϶�
			if (document.all(setlevelCd).style.display=='none'){
				document.all(setlevelCd).style.display='';
			} else if (document.all(setlevelCd).style.display!='none'){
				document.all(setlevelCd).style.display='none';
			}		

			// ���� level�� ���� �� ���� level ���� �ݱ�
		    if (document.all(setlevelCd).style.display=='none'){	
				for(k=0;k< tableName.rows.length;k++){
					if (tableName.rows[k].id.substr(0,1) > level || tableName.rows[k].id.substr(0,2) > level){							
						tableName.rows[k].style.display='none';
					}

				}
			}
		
		} else { // ����Level�� ������ �϶� �迭 ó��
			for(j=0;j< document.all(setlevelCd).length;j++) {
				if (document.all(setlevelCd)[j].style.display=='none'){
					document.all(setlevelCd)[j].style.display='';		
				}else if (document.all(setlevelCd)[j].style.display!='none') {	
					document.all(setlevelCd)[j].style.display='none';
				}
			}			// ���� level�� ���� �� ���� level ���� �ݱ�
		    if (document.all(setlevelCd)[0].style.display=='none'){	
				for(k=0;k< tableName.rows.length;k++){
					if (tableName.rows[k].id.substr(0,1) > level || tableName.rows[k].id.substr(0,2) > level){							
						tableName.rows[k].style.display='none';
					}
						
				}			
			}
					
		}
}			
/*******************************************************************************
 * Function : popupSearch(param,codeNo,code,codeName) Parameter : param -
 * �����ڵ�(org:����,biz-���...) codeNo - �ý����ڵ� (project.prj_no, org.org_no)�� ���� object
 * code - ������ڵ带 ���� object codeName - ������ڵ���� ���� object code2 - ������ڵ�2�� ����
 * object codeName2 - ������ڵ��2�� ���� object Return : Date : 2006.06.25 mailbest
 ******************************************************************************/	
function popupSearch(param,codeNo,code1,codeName1,code2,codeName2){
	
	openWin("/common/comPopup.jsp?param="+param+
								"&objcodeNo="+codeNo+
								"&objcode1="+code1+
								"&objcodeName1=" + codeName1+
								"&objcode2="+code2+
								"&objcodeName2=" + codeName2 ,"",600,400);
}		
/*******************************************************************************
 * Function :
 * popupSearchN(param,paramId,codeNo,code1,codeName1,code2,codeName2,width,height,idx,autoSel)
 * Parameter : 1) param - �����ڵ�(org_new:����,biz_new-���...) 2) paramId - �˾������ڵ�
 * (org_1,pjt_1,emp_1) 3) codeNo - �ý����ڵ� (project.prj_no, org.org_no)�� ���� object
 * 4) code - ������ڵ带 ���� object 5) codeName - ������ڵ���� ���� object 6) code2 - ������ڵ�2��
 * ���� object 7) codeName2 - ������ڵ��2�� ���� object 8) width - �˾� ���� 9) height - �˾�
 * ���� 10) idx - ���� �÷��� ���� �迭 ó�� *** default:0 ���� �Է� 11) autoSel - true/false �˾�
 * ȣ�� �� �ڵ� ���� ó�� (���� ����� �ϳ��϶� �ڵ� ���� ���) 12) setYmd - �˾� ��ȸ�� ���� ����� ���� Return :
 * Date : 2006.06.25 mailbest
 ******************************************************************************/	
function popupSearchN(param,paramId,codeNo,code1,codeName1,code2,codeName2,width,height,idx,autoSel,setYmd){
	if (width==null || width=="" || width==undefined){width=550};
	if (height==null ||height=="" || height==undefined){height=400};
	if (navigator.userAgent.match(/iPad|iPhone|Mobile|UP.Browser|Android|BlackBerry|Windows CE|Nokia|webOS|Opera Mini|SonyEricsson|opera mobi|Windows Phone|IEMobile|POLARIS|Chrome/) != null){		
		openWin("/common/comPopupMo.jsp?param="+param+
				"&paramId="+paramId+
				"&objcodeNo="+codeNo+
				"&objcode1="+code1+
				"&objcodeName1=" + 	codeName1+
				"&objcode2="+code2+
				"&objcodeName2=" + codeName2+
				"&idx=" + idx+
				"&autoSel=" + autoSel+
				"&setYmd=" + setYmd,
				"",
				width, height);
		}else{
			showModalDialog("/common/comPopupN.jsp?param="+param+
					"&paramId="+paramId+
					"&objcodeNo="+codeNo+
					"&objcode1="+code1+
					"&objcodeName1=" + 	codeName1+
					"&objcode2="+code2+
					"&objcodeName2=" + codeName2+
					"&idx=" + idx+
					"&autoSel=" + autoSel+
					"&setYmd=" + setYmd,
					window,
					"dialogWidth:"+width+
					"px;dialogHeight:"+height+"px;toolbar:0;resizable:1;status:no;help:no");
		}
}	
/*******************************************************************************
 * Function :
 * multiPopupSearch(param,paramId,codeNo,code1,codeName1,code2,codeName2,width,height,idx)
 * Parameter : param - �����ڵ�(org_new:����,biz_new-���...) paramId - �˾������ڵ�
 * (org_1,pjt_1,emp_1) codeNo - �ڵ带 ���� object width - �˾� ���� height - �˾� ���� idx -
 * ���� �÷��� ���� �迭 ó�� Return : ���߼��� �� DATA�� ���� Date : 2008.11.27 mailbest
 ******************************************************************************/	
function multiPopupSearch(param,paramId,codeNo,width,height,idx,params){
	if (width==null || width=="" || width==undefined){width=500};
	if (height==null ||height=="" || height==undefined){height=400};
	showModalDialog("/common/comPopupMulti.jsp?param="+param+
										"&paramId="+paramId+
										"&objcodeNo="+codeNo+
										getArrayTOString(params) +
										"&idx=" + idx,
										window,
										"dialogWidth:"+width+
										"px;dialogHeight:"+height+"px;toolbar:0;resizable:1;status:no;help:no");
}	
// �迭�� Sting����
function getArrayTOString(params){
	var len=0;
	var str="";
	var value="";
	if(params!=null){
		len=params.length;
		for(var i=0;i<params.length;i++){
			value=params[i];
			if(value!=""){
				str+="&p"+i+"="+value;
			}
		}
	}
	return str;	
}	
/*******************************************************************************
 * Function :
 * popupSearchN(param,paramId,codeNo,code1,codeName1,code2,codeName2,width,height)
 * Parameter : param - �����ڵ�(org_new:����,biz_new-���...) paramId - �˾������ڵ�
 * (org_1,pjt_1,emp_1) codeNo - �ý����ڵ� (project.prj_no, org.org_no)�� ���� object
 * code - ������ڵ带 ���� object codeName - ������ڵ���� ���� object code2 - ������ڵ�2�� ����
 * object codeName2 - ������ڵ��2�� ���� object width - �˾� ���� height - �˾� ���� Return :
 * Date : 2006.06.25 mailbest
 ******************************************************************************/	
function comPopupMail(param,paramId,codeNo,code1,codeName1,code2,codeName2,width,height){

	if (width==null || width=="" || width==undefined){width=500};
	if (height==null ||height=="" || height==undefined){height=600};
	
	showModalDialog("/common/comPopupMail.jsp?param="+param+
										"&paramId="+paramId+
										"&objcodeNo="+codeNo+
										"&objcode1="+code1+
										"&objcodeName1=" + 	codeName1+
										"&objcode2="+code2+
										"&objcodeName2=" + codeName2,
										window,
										"dialogWidth:"+width+
										"px;dialogHeight:"+height+"px;toolbar:0;resizable:1;status:no;help:no");
}		
/*******************************************************************************
 * Function :
 * popupSearchPrcs(param,paramId,codeNo,code1,codeName1,code2,codeName2,width,height)
 * Parameter : param - �����ڵ�(org_new:����,biz_new-���...) paramId - �˾������ڵ�
 * (org_1,pjt_1,emp_1) codeNo - �ý����ڵ� (project.prj_no, org.org_no)�� ���� object
 * code - ������ڵ带 ���� object codeName - ������ڵ���� ���� object code2 - ������ڵ�2�� ����
 * object codeName2 - ������ڵ��2�� ���� object width - �˾� ���� height - �˾� ���� Return :
 * Date : 2006.08.01 mailbest
 ******************************************************************************/	
function popupSearchPrcs(param,paramId,codeNo,code1,codeName1,code2,codeName2,width,height){

	if (width==null || width=="" || width==undefined){width=500};
	if (height==null ||height=="" || height==undefined){height=400};
	
	showModalDialog("/common/comPopupPrcs.jsp?param="+param+
										"&paramId="+paramId+
										"&objcodeNo="+codeNo+
										"&objcode1="+code1+
										"&objcodeName1=" + 	codeName1+
										"&objcode2="+code2+
										"&objcodeName2=" + codeName2,
										window,
										"dialogWidth:"+width+
										"px;dialogHeight:"+height+"px;toolbar:0;resizable:1;status:no;help:no");
}

function popupSearchPrcs_hanway(param,paramId,codeNo,code1,codeName1,code2,codeName2,width,height){

	if (width==null || width=="" || width==undefined){width=500};
	if (height==null ||height=="" || height==undefined){height=400};
	
	showModalDialog("/common/comPopupPrcs_hanway.jsp?param="+param+
										"&paramId="+paramId+
										"&objcodeNo="+codeNo+
										"&objcode1="+code1+
										"&objcodeName1=" + 	codeName1+
										"&objcode2="+code2+
										"&objcodeName2=" + codeName2,
										window,
										"dialogWidth:"+width+
										"px;dialogHeight:"+height+"px;toolbar:0;resizable:1;status:no;help:no");
}			
/*******************************************************************************
 * Function :
 * popupSearchJobOrg(param,paramId,codeNo,code1,codeName1,code2,codeName2,width,height)
 * Parameter : param - �����ڵ�(org_new:����,biz_new-���...) paramId - �˾������ڵ�
 * (org_1,pjt_1,emp_1) codeNo - �ý����ڵ� (project.prj_no, org.org_no)�� ���� object
 * code - ������ڵ带 ���� object codeName - ������ڵ���� ���� object code2 - ������ڵ�2�� ����
 * object codeName2 - ������ڵ��2�� ���� object width - �˾� ���� height - �˾� ���� Return :
 * Date : 2006.08.01 mailbest
 ******************************************************************************/	
function popupSearchJobOrg(param,paramId,codeNo,code1,codeName1,code2,codeName2,width,height){

	if (width==null || width=="" || width==undefined){width=500};
	if (height==null ||height=="" || height==undefined){height=400};
	
	showModalDialog("/common/comPopupJobOrg.jsp?param="+param+
										"&paramId="+paramId+
										"&objcodeNo="+codeNo+
										"&objcode1="+code1+
										"&objcodeName1=" + 	codeName1+
										"&objcode2="+code2+
										"&objcodeName2=" + codeName2,
										window,
										"dialogWidth:"+width+
										"px;dialogHeight:"+height+"px;toolbar:0;resizable:1;status:no;help:no");
}		
/*******************************************************************************
 * Function : popupSearch1(param,codeNo,code,codeName) Parameter : param -
 * �����ڵ�(org:����,biz-���...) codeNo - �ý����ڵ� (project.prj_no, org.org_no)�� ���� object
 * code - ������ڵ带 ���� object codeName - ������ڵ���� ���� object code2 - ������ڵ�2�� ����
 * object codeName2 - ������ڵ��2�� ���� object Return : Date : 2006.07.09 add cylim
 * ��������� �յ������ popup â�� ������ �ʿ��Ͽ� �߰�
 ******************************************************************************/	
		
function popupSearch1(param,codeNo,code1,codeName1,code2,codeName2){
	openWin("/common/comPopup1.jsp?param="+param+
									"&objcodeNo="+codeNo+
									"&objcode1="+code1+
									"&objcodeName1=" + codeName1+
									"&objcode2="+code2+
									"&objcodeName2=" + codeName2 ,"",500,400);
}		

/*******************************************************************************
 * Function : popupSearch2(param,codeNo,code,codeName) Parameter : param -
 * �����ڵ�(org:����,biz-���...) codeNo - �ý����ڵ� (project.prj_no, org.org_no)�� ���� object
 * code - ������ڵ带 ���� object codeName - ������ڵ���� ���� object code2 - ������ڵ�2�� ����
 * object codeName2 - ������ڵ��2�� ���� object Function :
 * popupSearch1(param,codeNo,code,codeName) Return : Date : 2006.07.09 modify
 * whcho ��������� �յ������ popup â�� ������ �ʿ��Ͽ� �߰�
 ******************************************************************************/	
		
function popupSearch2(param,codeNo1,codeCd1,codeName1,codeView1,codeNo2,codeCd2,codeName2,codeView2,actFlag){
	openWin("/common/comPopup2.jsp?param="+param+
		                          "&codeNo1="+codeNo1+"&codeCd1="+codeCd1+
		                          "&codeName1=" +codeName1+"&codeView1=" +codeView1+
		                          "&codeNo2="+codeNo2+"&codeCd2="+codeCd2+
		                          "&codeName2=" +codeName2+"&codeView2=" +codeView2+
								  "&actFlag=" + actFlag ,"",700,500);
}	
/*******************************************************************************
 * Function :
 * popupSearch3(param,codeNo1,codeCd1,codeName1,codeView1,codeNo2,codeCd2,codeName2,codeView2,actFlag)
 * Parameter : param - �����ڵ�(org:����,biz-���...) codeNo - �ý����ڵ� (project.prj_no,
 * org.org_no)�� ���� object code - ������ڵ带 ���� object codeName - ������ڵ���� ���� object
 * code2 - ������ڵ�2�� ���� object codeName2 - ������ڵ��2�� ���� object Return : Date :
 * 2006.07.09 modify whcho ��������� �յ������ popup â�� ������ �ʿ��Ͽ� �߰�
 ******************************************************************************/	
		
function popupSearch3(param,codeNo1,codeCd1,codeName1,codeView1,codeNo2,codeCd2,codeName2,codeView2,actFlag,idx){
	openWin("/common/comPopup3.jsp?param="+param+
		                          "&codeNo1="+codeNo1+"&codeCd1="+codeCd1+
		                          "&codeName1=" +codeName1+"&codeView1=" +codeView1+
		                          "&codeNo2="+codeNo2+"&codeCd2="+codeCd2+
		                          "&codeName2=" +codeName2+"&codeView2=" +codeView2+
								  "&actFlag=" + actFlag +
     							  "&idx=" + idx ,"",700,500);
}		
/*******************************************************************************
 * Function : popupSearch4(param,codeNo,code,codeName) Parameter : param -
 * ????????(org:????,biz-????...) codeNo - ?????????? (project.prj_no,
 * org.org_no)?? ???? object code - ???????????? ???? object codeName -
 * ?????????????? ???? object code2 - ??????????2?? ???? object codeName2 -
 * ????????????2?? ???? object Function :
 * popupSearch4(param,codeNo,code,codeName) Return : Date : 2010.02.04 modify
 * whcho ?????????? ?????????? popup ???? ?????? ???????? ????
 ******************************************************************************/	
		
function popupSearch4(param,codeNo1,codeCd1,codeName1,codeView1,codeNo2,codeCd2,codeName2,codeView2,actFlag){
	openModalWin("/common/comPopup4.jsp?param="+param+
		                          "&codeNo1="+codeNo1+"&codeCd1="+codeCd1+
		                          "&codeName1=" +codeName1+"&codeView1=" +codeView1+
		                          "&codeNo2="+codeNo2+"&codeCd2="+codeCd2+
		                          "&codeName2=" +codeName2+"&codeView2=" +codeView2+
								  "&actFlag=" + actFlag , "",700,500);
}	

/*******************************************************************************
 * Function : clearText(objName) Parameter : objName - �� ���� ��� object Name
 * Return : Date : 2006.08.02 mailbest
 ******************************************************************************/	
function clearText(objName){
	var frm= document.forms[0];		
  	var obj = frm.all(objName);
	obj.value="";
}	
/*******************************************************************************
 * Function : cutStr(str,len, tail)���ڿ� ���� �ڸ���&���Ӹ� ���̱� Parameter : str - ��� ���ڿ�
 * len - ���� ���� ���� tail - ���� ��
 * 
 * Return : cutStr('���� �������',2,'...') -> '����...' Date : 2006.08.02 mailbest
 ******************************************************************************/	
 function cutStr(str,len, tail) 
{
    var l = 0;
    
	if (tail==undefined) var tail='';

    for (var i=0; i<str.length; i++) 
    {
        l += (str.charCodeAt(i) > 128) ? 2 : 1;
        if (l > len) return str.substring(0,i) + tail;
    }
    return str;
}
/*******************************************************************************
 * Function : divSearch(msg) ���� �˾� Parameter : obj - optional ���� �޽��� Return :
 * Date : 2007.12.28 mailbest
 ******************************************************************************/	
function divSearch(msg){

	if (msg==undefined){
		msg = 'ó�� �� �Դϴ� . . .'; // �⺻�� ����
	}
	document.getElementById('processing').innerHTML='<table  border=0 cellpadding=0 cellspacing=0 width=100% height=100% style="background:white">' +
									'<tr >' +
										'<td align=center valign=middle > ' +
										'<img src="/images/loading.jpg" width="119" height="40">' +										
// '<embed id=ePreview src=/images/loading_c.swf width=119 height=40
// type=application/x-shockwave-flash
// pluginspage=http://www.macromedia.com/go/getflashplayer>' +
										'<br><marquee><b><font color="#3B7584">' + msg +'</font></b></marquee></td> ' +
									'</tr>' +
									'</table>  ';
	document.getElementById('processing').style.width="119";
	document.getElementById('processing').style.height="40";
	document.getElementById('processing').style.filter="alpha(opacity=80)";	
		
	var sizeW = document.body.offsetWidth/2 - document.getElementById('processing').style.width.replace('px','')/2;
	var sizeH = document.body.offsetHeight/2 - document.getElementById('processing').style.height.replace('px','')/2; 
	
	document.getElementById('processing').style.left=sizeW;
	document.getElementById('processing').style.top=sizeH;						
	
	document.getElementById('processing').style.display="";
}
/*******************************************************************************
 * Function : �÷� ��ȸ ���� ó�� Parameter : sortCol - ���� ó�� �� �÷���, idx - �÷� index(����)
 * Return : Date : 2007.03.23 mailbest
 ******************************************************************************/	
function setOrderBy(sortCol,idx){
	var paramSortTmpObj = document.getElementsByName('paramSortTmp');
	var paramSortAscObj = document.getElementsByName('paramSortAsc');
	var  paramSortIdxObj = document.getElementsByName('paramSortIdx');
	// �÷� ���� ��ȸ��
	paramSortTmpObj[0].value= sortCol;// ���� �÷�
	
	if (paramSortAscObj[0].value=="0"){ 	// ����
		paramSortAscObj[0].value="1"; 		// ASC
	}else{
		paramSortAscObj[0].value="0";		// DESC
	}
    
    paramSortIdxObj[0].value= idx; // ���� �÷� ���ؽ�
	
}
// �÷� ���� img �ʱ�ȭ
function setSort(){
	var paramSortObj = document.getElementsByName('paramSort');
	var paramSortAscObj = document.getElementsByName('paramSortAsc');
	var paramSortIdxObj = document.getElementsByName('paramSortIdx');	
	var sort_Obj = document.getElementsByName('sort_');	
	
	var sortCol = paramSortObj[0].value;
	
	if (sortCol==""){
		paramSortAscObj[0].value=''; 		// ASC
		paramSortIdxObj[0].value=''; 		// ASC
	}
	
	var sortIdx = paramSortIdxObj[0].value * 2 ;

	for (var i=0;i<sort_Obj.length;i++){
		sort_Obj[i].style.display='none';
	}		
	
	if (sortIdx!= 0 && paramSortAscObj[0].value=='0'){ 
		sort_Obj[sortIdx-1].style.display='';
	}else if (sortIdx!= 0 ){
		sort_Obj[sortIdx-2].style.display='';  
	}  
}
// üũ
function chkFlag(param){	
	frm = document.forms[0];
	if (frm.dflag.length!=undefined){
		document.all.dflag[param].value="Y";	
		document.all.chkLine[param].style.color ="c71585";	
	}else{
		document.all.dflag.value="Y";	
		document.all.chkLine.style.color ="c71585";
	}	
}
// ����üũ
function chgFlag(param){	
	frm = document.forms[0];
	if (frm.dflag.length!=undefined){
		document.all.dflag[param].value="U";	
		document.all.chkLine[param].style.color ="red";	
	}else{
		document.all.dflag.value="U";	
		document.all.chkLine.style.color ="red";	
	}	
}

// ����� �Է½� ��¥���˿� �°� ���� (keyboard����)
function chkFormatDate(inputDt,opt)  
{	
	frm=document.forms[0];
    if (inputDt.name==''||inputDt.name==undefined){// name���� ��������
    	inputDt = document.getElementsByName(inputDt);
    }
        
	if(event.keyCode==8 || event.keyCode==46 || event.keyCode==37 || event.keyCode==39|| event.keyCode==36) 	return;
	
	if (opt==6){// yyyy-mm
		opt=7;
	}else{// yyyy-mm-dd
		opt=10;
	}

    if (inputDt.name==''||inputDt.name==undefined){// name���� ��������
    	inputDt[0].value = removeChar(inputDt[0].value,"-");
    	
    	var yyyy= inputDt[0].value.substring(0,4);
    	var mm= inputDt[0].value.substring(4,6);
    	var dd= inputDt[0].value.substring(6,8);
    }else{
	    inputDt.value = removeChar(inputDt.value,"-");
	    
	    var yyyy= inputDt.value.substring(0,4);
	    var mm= inputDt.value.substring(4,6);
	    var dd= inputDt.value.substring(6,8);
	}
	mkinputDt="";
	if (opt==7){    
		mkinputDt = yyyy + "-" + mm ;
	}else{
		mkinputDt = yyyy + "-" + mm + "-" + dd ;
	}	
	
    if (inputDt.name==''||inputDt.name==undefined){// name���� ��������
    	inputDt[0].value= mkinputDt.substring(0,opt);
    }else{
    	inputDt.value= mkinputDt.substring(0,opt);
    }
}
// ī���ȣ ���Ŀ� �°� ���� (keyboard����)
function mkFrmCardNo(inputDt,opt)  
{	

	if(event.keyCode==8 || event.keyCode==46 || event.keyCode==37 || event.keyCode==39) 	return;
	
	inputDt.value = removeChar(inputDt.value,"-");
		
	no1= inputDt.value.substring(0,4);
	no2= inputDt.value.substring(4,8);
    no3= inputDt.value.substring(8,12);
    no4= inputDt.value.substring(12,16);
	
	mkFrm = no1 + "-" + no2 + "-" + no3+ "-" + no4 ;
	
	inputDt.value= mkFrm; 	
}

// ����ڹ�ȣ ���Ŀ� �°� ���� (keyboard����)
function mkFrmBizNo(inputBiz,opt)  
{	

	if(event.keyCode==8 || event.keyCode==46 || event.keyCode==37 || event.keyCode==39) 	return;
	
	inputBiz.value = removeChar(inputBiz.value,"-");
		
	no1= inputBiz.value.substring(0,3);
	no2= inputBiz.value.substring(3,5);
    no3= inputBiz.value.substring(5,10);
	
	mkFrm = no1 + "-" + no2 + "-" + no3 ;
	
	inputBiz.value= mkFrm; 	
	
	
}

// ������ȣ ���Ŀ� �°� ���� (keyboard����)
function mkFrmDocNo(inputDt,opt)  
{	
	if(event.keyCode==8 || event.keyCode==46 || event.keyCode==37 || event.keyCode==39) 	return;
	
	inputDt.value = removeChar(inputDt.value,"-");
		
	no1= inputDt.value.substring(0,4);
	no2= inputDt.value.substring(4,12);
    no3= inputDt.value.substring(12,15);
	
	mkFrm = no1 + "-" + no2 + "-" + no3;
	
	inputDt.value= mkFrm.toUpperCase(); 	
}
// �⵵ ����
function addYear(objId,addYear){
    	var year =document.getElementById(objId);
    	year.value =parseInt(year.value)+parseInt(addYear);    	
}
// ���,������,������ ����
function setMon(frDT,toDT,addMonth){
		var frm=document.forms[0];		
		var now = new Date();
		var From_date;
		var To_date;
		var _year;
		var _month;
		
		var frDTObj = document.getElementsByName(frDT); 
		var toDTObj = document.getElementsByName(toDT); 
		
		
		// ����,���� ��
		if (addMonth!=0){
			
			_year	=	frDTObj[0].value.substring(0,4);
			_month	=	frDTObj[0].value.substring(5,7);
			
			if (addMonth<0){
				now=getPrevYearMonth(_year,_month);		
			}else if (addMonth>0){
				now=getNextYearMonth(_year,_month);		
			}
			
			_year	=	now.substring(0,4);// ��
			_month	=	now.substring(4,6);// ��
			
		} else {
			// ���
			_year  = now.getFullYear();						// ��
			_month = getTwoLength(now.getMonth()+1);  	// ��
		}
		
		From_date = new Date(_year,_month, 1);// ����
		To_date = new Date(_year,_month, 0);// ����
		
		var s_date  = getTwoLength(From_date.getDate());
		var e_date  = getTwoLength(To_date.getDate());

		From_date	=_year+"-"+_month+"-"+s_date; // ����
		To_date		=_year+"-"+_month+"-"+e_date; // ����
		
		frDTObj[0].value=From_date;
		toDTObj[0].value=To_date;
}
function rdopen(param1,param2) {
	var frm=document.forms[1];	
	var obj="Rdviewer";
	
	frm.all(obj).AutoAdjust = false;
	frm.all(obj).ZoomRatio=100;
	frm.all(obj).HideStatusBar();
	frm.all(obj).FileOpen(param1,param2);
}
function rdSet() {

	document.write("<object id=Rdviewer   classid='clsid:8068959B-E424-45ad-B62B-A3FA45B1FBAF' codebase='http://mas.hist.co.kr/ReportDesigner/cab/rdviewer40.cab#version=4,0,0,138' name=Rdviewer width=100%  height=100%></object>");
	document.write("<object id=TChart   classid='clsid:536600D3-70FE-4C50-92FB-640F6BFC49AD' codebase='http://mas.hist.co.kr/ReportDesigner/cab/teechart6.cab#version=6,0,0,5'  width=0%  height=0%></object>");
	document.write("<object id=rdpdf   classid='clsid:35CC04FB-767A-4A5D-B392-7E4668421590'  codebase='http://mas.hist.co.kr/ReportDesigner/cab/rdpdf.cab#version=1,0,0,7' width=0%  height=0%></OBJECT>");
	document.write("<object id=rdbarcode  classid='clsid:36C69B75-B8F5-4E53-B06D-1DBE860BA88B'  codebase='http://mas.hist.co.kr/ReportDesigner/cab/rdbarcode.cab#version=1,0,0,3' width=0%   height=0%></object>");
}

function rdSetCommon() {

	document.write("<object id=Rdviewer   classid='clsid:8068959B-E424-45ad-B62B-A3FA45B1FBAF' codebase='http://mas.hist.co.kr/common/cab/rdviewer40.cab#version=4,0,0,138' name=Rdviewer width=100%  height=100%>");
	document.write("<param name='MrrFilter' value='1'>");
	document.write("<param name='AutoAdjust' value='1'>");
	document.write("<param name='ViewToolBar' value='1'>");
	document.write("<param name='MakeIndexTree' value='0'>");
	document.write("<param name='ShowTreeWindow' value='0'>");
	document.write("<param name='ShowFirstMrd' value='0'>");
	document.write("<param name='ZoomRatio' value='100'>");
	document.write("</object>");
	document.write("<object id=TChart   classid='clsid:536600D3-70FE-4C50-92FB-640F6BFC49AD' codebase='http://mas.hist.co.kr/common/cab/teechart6.cab#version=6,0,0,5'  width=0%  height=0%></object>");
	document.write("<object id=rdpdf   classid='clsid:35CC04FB-767A-4A5D-B392-7E4668421590'  codebase='http://mas.hist.co.kr/common/cab/rdpdf.cab#version=1,0,0,7' width=0%  height=0%></OBJECT>");
	document.write("<object id=rdbarcode  classid='clsid:36C69B75-B8F5-4E53-B06D-1DBE860BA88B'  codebase='http://mas.hist.co.kr/common/cab/rdbarcode.cab#version=1,0,0,3' width=0%   height=0%></object>");
}

// DHTML���� rowIndex ��ȯ
function dhtmlIndex(obj){
	var frm =document.forms[0];
	var objName = document.getElementsByName(obj.id);
	var objMaxLen ;
	var getIndex=0;
			if(obj.id=="" ||obj.id=="undefinded"){
				if(frm.all(obj.name).length == "undefined" || frm.all(obj.name).length == null){
					getIndex=0;
				}else{
					objMaxLen = frm.all(obj.name).length;
					objMaxLen=objMaxLen-1;
					var objIndex = obj.sourceIndex;
					var objMinIndex = frm.all(obj.name)[0].sourceIndex;
					var objMaxSourceIndex = frm.all(obj.name)[objMaxLen].sourceIndex;
					
					var objSourceIndex = frm.all(obj.name)[objMaxLen-1].sourceIndex;
					var objTerm=objMaxSourceIndex-objSourceIndex;
					
					// �ε��� ��ȯ
					getIndex=objMaxLen -(objMaxSourceIndex-objIndex)/objTerm;
				}
				
			}else{
				if(objName.length == "undefined" || objName.length == null){
					getIndex=0;
				}else{
					objMaxLen = objName.length;
					objMaxLen=objMaxLen-1;
					var objIndex = obj.sourceIndex;
					var objMinIndex = objName[0].sourceIndex;
					var objMaxSourceIndex = objName[objMaxLen].sourceIndex;
				
					var objSourceIndex = objName[objMaxLen-1].sourceIndex;
					var objTerm=objMaxSourceIndex-objSourceIndex;
			
					// �ε��� ��ȯ
					getIndex=objMaxLen -(objMaxSourceIndex-objIndex)/objTerm;
				}
		}
    return getIndex;
}

// object color ����
function setBox(obj){
	var setColor=new Array('gray','white','#F0F0FF');
   	setObjColor(obj,setColor);
}
// object color ����
function setObjColor(obj,setColor){
    var frm= document.forms[0];
	var i=dhtmlIndex(obj);
	var objType ='';

	if (i!=0 || frm.all(obj.name).length!=undefined){
		
		objType =document.all(obj.name)[i].type;
		
		if (document.all(obj.name)[i].style.borderColor==setColor[0]){
			document.all(obj.name)[i].style.backgroundColor=setColor[1];
 			if (objType=='text'){
 				document.all(obj.name)[i].style.borderColor=setColor[1];
			}
		}else{
			document.all(obj.name)[i].style.backgroundColor=setColor[2];
 			if (objType=='text'){
				document.all(obj.name)[i].style.borderColor=setColor[0];
			}
		}
	}else{
		objType =document.all(obj.name).type;
		
		if (document.all(obj.name).style.borderColor==setColor[0]){
			document.all(obj.name).style.backgroundColor=setColor[1];
			if (objType=='text'){
				document.all(obj.name).style.borderColor=setColor[1];
			}
		}else{
			document.all(obj.name).style.backgroundColor=setColor[2];
			if (objType=='text'){
				document.all(obj.name).style.borderColor=setColor[0];
			}
		}
	}
}
// üũ�ڽ� ���� ó��
function setChkFlag(obj,selrow){
    var frm= document.forms[0];
   	var i=	dhtmlIndex(obj);
   	if (selrow!=undefined) i=selrow;// row������ ����
		if (i!=0 || frm.dflag.length!=undefined){ 
			if (frm.dflag[i].value=="D"){
				frm.dflag[i].value="R";	
				document.all(obj.name)[i].checked=false;
			} else{
				frm.dflag[i].value="D";	
				document.all(obj.name)[i].checked=true;
			}
			if(document.all("chkLine")!=null)  chgClass(document.all("chkLine")[i],"Chk");			
	
		}else{
			if (frm.dflag.value=="D"){
				document.all.dflag.value="R";		
				document.all(obj.name).checked=false;
			}else{
				document.all.dflag.value="D";	
				document.all(obj.name).checked=true;
			}
			if(document.all("chkLine")!=null) chgClass(document.all("chkLine"),"Chk");
		}
	
		// class ó��
		if (obj.name=='chkLine') chgClass(obj,'Chk');
}

// üũ�ڽ� ��ü ����/���� ó��
function selCheck(selobj,obj){	
   	var i=dhtmlIndex(selobj);
   	
	if (document.all(selobj.name).checked==true){
		for(i=0;i<document.all(obj).length;i++){
			if(document.all(obj)[i].checked==false & document.all(obj)[i].disabled==false){
				setChkFlag(document.all(obj)[i],i);
			}
		}
	}else{	
		for(i=0;i<document.all(obj).length;i++){
			if(document.all(obj)[i].checked==true & document.all(obj)[i].disabled==false){
				setChkFlag(document.all(obj)[i],i);
			}
		}
	}
	
	if (i==0) setChkFlag(document.all(obj));
}
// Row ���ؽ� class ó��
function chgClass(obj,param){
	var frm=document.forms[0];
	var objNms=document.getElementsByName(obj.name);
	// Mouseover��
	if(param=="Mover" && (obj.className=='onbase striped' || obj.className=='striped' || obj.className=='onbase')){
		if(obj.className.indexOf('striped') >= 0){
			revCn = true;
		}else{
			revCn = false;
		}
		obj.className='on';
	// Mouseout��
	}else if (param=="Mout" && obj.className=='on' || obj.className=='striped') {
		if(revCn){
			obj.className='striped';	
		}else{
			obj.className='onbase';
		}
		
		if(Gobj!=undefined) Gobj.className='onbaseD0';	
	// Row���ý� ����
	}else if (param=='Mfect') {
		if(Gobj!=undefined) Gobj.className='onbase'; // &&
														// obj.sourceIndex!=Gobj.sourceIndex
		Gobj=obj;
		obj.className='onbaseD0';	
	// TAB ���ý� ����
	}else if (param=='MTab') {
		if(GTabobj!=undefined ) GTabobj.className='tabBt';// &&
															// obj.sourceIndex!=GTabobj.sourceIndex
		GTabobj=obj;
		obj.className='tabBtOn';	
	// TABs ���ý� ���� (������)
	}else if (param=='MTabs') {
		if(GTabobj!=undefined ) GTabobj.className='tabBts';// &&
															// obj.sourceIndex!=Gobj.sourceIndex
		GTabobj=obj;
		obj.className='tabBtsOn';	
	// check ���ý� class����
	}else if (param=='Chk') {
		if (obj.className!='onbaseD1'){
			obj.className='onbaseD1';	
		}else{
			obj.className='onbase';	
		}
	}
}
// div ���� �ڵ� ����
function setDivSize(hight,divName){
	
  var ChgWinSize= hight;
  var DivNameStr;
  var windowHeight=""
  	  
  if(divName==undefined) {
	  DivNameStr="divList";  
  }else{
	  DivNameStr=divName;  
  }
  
  var windowObj=	document.getElementById(DivNameStr);
  
  // ��� �˾� â������ ���� ó�� 2010-03-17
  if(window.dialogArguments!=undefined && document.body.clientHeight==0){
	windowHeight=window.dialogArguments.document.body.clientHeight;
  }else{
	windowHeight=document.body.clientHeight;
  }
  
  if(windowHeight-ChgWinSize*2>0){
// document.all(DivNameStr).style.height=windowHeight-ChgWinSize*2;
	  windowObj.style.height=windowHeight-ChgWinSize*2;
	  }else{
// document.all(DivNameStr).style.height=0;
		  windowObj.style.height=0;
  }
  
}

// div �ʺ� �ڵ� ����(KDS�߰� 20110720)
function setDivWidthSize(width,divName){
  var ChgWinSize= width;
  var DivNameStr = "";
  var windowWidth=""
    
  if(divName==undefined) {
	  DivNameStr="divList";  
  }else{
	  DivNameStr=divName;  
  }
  // ��� �˾� â������ ���� ó��
  if(document.body.clientWidth==0) {
	windowWidth=window.dialogArguments.document.body.clientWidth;
  }else{
	windowWidth=document.body.clientWidth;
  }
  
  document.all(DivNameStr).style.width=windowWidth-ChgWinSize*2;
}

// form üũ �� �ش� obj�� �ִ� �� ����
function selform(obj){
	for(i=0;i<document.forms.length;i++){
		if(document.forms[i].all(obj)!=null){
			return document.forms[i];
		}
	} 	
	return false;
}
// ����Ű ,�ٹٲ� Ű Count Return
function nullCharCnt(obj){
	 var nullCnt=0;			
	  for(i=0;i<obj.length;i++)
		{ 
			var a = obj.charCodeAt(i); 
			// 13:keyEnter,10:lineFeed
			if (a== 10 || a==13) nullCnt=nullCnt+1;

		}
		return nullCnt;
}

// �޴��� ��ȸ
function openManual(location,muCd,muType,muSubType){
	 if(muSubType==undefined)   muSubType="USR";  // ����� �Ŵ���
	path = replace(location+"","&","%26");
	openWin("/Manual/mu_index.jsp?S_PATH="+path+"&S_MU_CD="+muCd+"&S_MU_TYPE="+muType+"&S_MU_SUBTYPE="+muSubType,"",1000,640);
}

// ���ڹ��������ý���(EDMS) ��ȸ
function openEDMS(location,muCd,muType){
	path = replace(location+"","&","%26");
	openWinFix("/ED/edMain.jsp","",813,615);
}

function imgResize(MaxWidth,PrintYn) {
  for(var i=0;i<document.images.length;i++) {
    if(document.images[i].width > MaxWidth) {
      document.images[i].height=document.images[i].height*(MaxWidth/document.images[i].width);
      document.images[i].width=MaxWidth;
    }else if(document.images[i].width=='730'&PrintYn=='Y'){// �μ� �̹��� ������ ����
    	document.images[i].height=document.images[i].height*(MaxWidth/document.images[i].width);
    	document.images[i].width=MaxWidth;
    }
  }
}

// Tree�̹��� ó��
function setTreeImg(obj){
	var frm=document.forms[0];	
	// ���� obj ����
	var objStatus = obj.src.substring(obj.src.length-9,obj.src.length)	
	var tableName = document.forms[0].all('listProDoc');
	
	for(i=obj.id.substring(7,10);i<tableName.rows.length;i++){
		if(frm.all('tdNm'+i)!=null && frm.all('tdNm'+i).value>0)
	    document.all('treeImg'+i).src=document.all('treeImg'+i).src.substring(0,document.all('treeImg'+i).src.length-9) +'/'+ 'tree+.gif'	
	}

	
	if( objStatus=='tree+.gif'){
		obj.src=obj.src.substring(0,obj.src.length-9) + 'tree-.gif' ;
	} else if( objStatus=='tree-.gif'){
		obj.src=obj.src.substring(0,obj.src.length-9) + 'tree+.gif' ;
	}
}
/* tree �׸��� */
function addItem(lev,id,htmlStart,htmlMid,htmlEnd,levelWidth){
	var treeImg='';
    if (levelWidth ==undefined) levelWidth='10';

	for(i=0;i<lev;i++) if(i!=0) treeImg = treeImg+ '<img src="../images/treeImage/s.gif" width="'+levelWidth+'" height="10" style="border:1">'; // tree
																																				// ��������
	
	htmlMid=treeImg+htmlMid;
	document.getElementById(id).insertAdjacentHTML('beforeEnd',htmlStart+htmlMid+htmlEnd);
}

// Tree ����ó��
function setDir(obj,mEvent,imgCk,treeType){
    // mEvent : 0-mouseOut, 1-mouseOver , 2-mouseClick
    // imgCk : '1'-Image Click ,'0'/''-Imgag Non Click
	// �����ǵ� Parameter ó��
   	if (mEvent ==undefined) mEvent='';
   	if (imgCk ==undefined) imgCk='';
   	if (treeType ==undefined) treeType='1';
   	
	// �̹��� Ŭ���� ��� �̸����� ó��
	obj=obj.replace("img_","");

	// img obj ó��
	getObj = document.getElementById('img_'+obj);
	imgObj = getObj.src.substring(getObj.src.length-5,60);
	
	// root�� ����
	if(obj!='ROOT')	{
		// ���������� ������ ����
		if (imgObj=='e.gif') return;
	}
		
	if(obj!='ROOT' & (imgObj=='+.gif' || imgCk=='1' ) & setDiridx=='Y'){
		if(document.getElementById('D_'+document.getElementById(obj).id).style.display!='none'){
			document.getElementById('D_'+document.getElementById(obj).id).style.display='none';
		}else{
			document.getElementById('D_'+document.getElementById(obj).id).style.display='';
		}
			settreeImage(obj,treeType);
		setDiridx='N';
	}
	// setDir�̺�Ʈ 1ȸ�� ó��
	if(imgCk=='3')	setDiridx='Y';
}
 
// Tree �̹��� ó��(New Tree ����)
function settreeImage(obj,treeType){	
	// �����ǵ� Parameter ó��
   	if (treeType ==undefined) treeType='';	
	getTreeObj = document.getElementById('img_'+obj);
	
	// root�϶�
	if (obj.substring(obj.length-1,100)=='@') return;
	// ���������� ������
	if (getTreeObj.src.substring(getTreeObj.src.length-8,60)=='tree.gif') return;

	// tree�̹��� ��� ó��
	if (getTreeObj.src.substring(getTreeObj.src.length-10,60)=='/tree+.gif'){
		getTreeObj.src='../images/treeImage/'+treeType+'/tree-.gif';// �ý��� ��� ����
	}else if (getTreeObj.src.substring(getTreeObj.src.length-14,60)=='edms/tree+.gif'){
		getTreeObj.src='../images/edms/tree-.gif';     // ����ڵ�� ����
	}else if (getTreeObj.src.substring(getTreeObj.src.length-10,60)=='/tree-.gif'){
		getTreeObj.src='../images/treeImage/'+treeType+'/tree+.gif';
	}else if (getTreeObj.src.substring(getTreeObj.src.length-14,60)=='edms/tree-.gif'){
		getTreeObj.src='../images/edms/tree+.gif';
	}
}

// Tree ��� ó��
function setBgColor(obj,mEvent,imgCk){		
    // mEvent : 0-mouseOut, 1-mouseOver , 2-mouseClick
    // imgCk : '1'-Image Click ,'0'/''-Imgag Non Click

	// �����ǵ� Parameter ó��
   	if (mEvent ==undefined) mEvent='';
   	if (imgCk ==undefined) imgCk='';
	getObj = document.getElementById(obj.id);
	
	// �� ���õ� row�� ��� ���� ���� ���� ó��
	if ((getObj.style.backgroundColor=='#c7e3e4'||getObj.style.backgroundColor=='rgb(199, 227, 228)')& mEvent!=2) return;

	if (mEvent==0){// mouseOut
		getObj.style.backgroundColor ='';
	}else if (mEvent==1){// mouseOver
		getObj.style.backgroundColor ='c6d6ec';
	}else if (mEvent==2){// //mouseClick

		// ���� ���� row �ʱ�ȭ
		if (preObjId!=''){
			preObj = document.getElementById(preObjId);
			preObj.style.backgroundColor ='';
		}		
		
		preObjId=obj.id;
		getObj.style.backgroundColor ='c7e3e4';
	}
}
// ���� �Է¿ϼ� üũ
function chkCharFinish(str) {
	if (str==null) return false;

	for(var i=0;i<str.length;i++){
		var c=str.charCodeAt(i);
		// (0xAC00 <=c && c<=0xD7A3) ���������� ���� �ѱ���
		// (0x3131<=c && c<=0x318E) ���� ����
		if(0xAC00 <= c && c <= 0xD7A3){// �ѱۿϼ�
			// return true;
		}else if (0x3131<=c && c<=0x318E){// �ѱ� �ۼ��� ����
			return false;
		}else{// �ѱ� �� ����
		}
	}
	return true;
}
// obj��ġ ���ϱ�
function setPosition(obj,mod_x,mod_y){

	// �����ǵ� Parameter ó��
   	if (mod_x ==undefined) mod_x=0;// '550';
   	if (mod_y ==undefined) mod_y=0;// '375';

	var tObj = document.all(obj);
	var frm=document.forms[0];
	// �� ��ȸ�� ȭ���� ��ġ������ ���� �ʴ´�.
	if (tObj.style.display!='none') return;


    // ������ ����, ��ũ���� �Ǿ��ٸ� �׸�ŭ ���Ѵ�. (document.body.clientWidth : ȭ�鿡 ���̴� �����)
    var browserWidth = document.body.clientWidth + document.body.scrollLeft;
    // ������ ����, ��ũ���� �Ǿ��ٸ� �׸�ŭ ���Ѵ�. (document.body.browserHeight : ȭ�鿡 ���̴� �����)
    var browserHeight = document.body.clientHeight + document.body.scrollTop;  
    // textFieldObject ��ü�� ��ġ ������
    var point = getObjectXY(tObj);
    var divLeft = point.x;
    var divTop  = point.y + tObj.offsetHeight;  // �ؽ�Ʈ �ڽ� ���̸�ŭ ���Ѵ�.(�ؽ�Ʈ �ڽ� ������
												// ���̰� �ϱ� ����)

    if (divLeft < document.body.scrollLeft) {
        // tObj ��ü�� �������� ������ �ʴ� ���� ��ġ�� ��
        divLeft = document.body.scrollLeft;
    } else if ((divLeft + frameWidth+200) > browserWidth) { 
    		
        // tObj ��ü�� ���������� ������ �ʴ� ���� ��ġ�� ��
        divLeft = browserWidth - frameWidth - 750;         // ����������� �ణ �� ũ��.
															// �׷��� 10�� �� ����.
    }
    
    if ((divTop + frameHeight ) > browserHeight) {
        divTop = browserHeight - frameHeight;
    }	
	if(tObj.style.display=='none' ){
	    with(tObj.style){
	        left = (browserWidth -  tObj.offsetWidth)/2 + mod_x;
	        top  = (browserHeight - tObj.offsetHeight)/2 + mod_y + document.body.scrollTop/2;
	    }
	    
	}else{
	    with(tObj.style){
	        left = browserWidth/2  + mod_x/2;
	        top  = browserHeight/2 + mod_y/2 + document.body.scrollTop/2;
	    }
   }
}
// Table�� Row Drag�̵�(jQuery)
function SetTableDragDrop(tableID) {
		jQuery("#" + tableID).tableDnD({
		        onDrop: function(table, row) {           
	            var orderStr = "";
	            var rows = table.tBodies[0].rows;
	            for (var i=0; i<rows.length; i++) {
	            	orderStr += rows[i].id+",";
	            }
           		OrderUpdate(orderStr.substring (0, orderStr.length - 1));
	        }
	    });
}
// ��ũ�� ����
function scrollLink(sOjb,tObj,tScrollDiv){
   if (tScrollDiv==undefined){
		// top Scroll
	   document.getElementById(tObj).scrollTop = document.getElementById(sOjb).scrollTop;
	   // left Scroll
	   document.getElementById(tObj).scrollTop = document.getElementById(sOjb).scrollTop;
   }else if(tScrollDiv=='T'){
	   // top Scroll
	   document.getElementById(tObj).scrollTop = document.getElementById(sOjb).scrollTop;
   }else if(tScrollDiv=='L'){
	   // left Scroll
	   document.getElementById(tObj).scrollLeft = document.getElementById(sOjb).scrollLeft;
   }
}

// ȭ���߾���ġ
function winResize(inputW, inputH){
	window.resizeTo(inputW,inputH);

	LeftPosition = (screen.width) ? (screen.width-inputW)/2 : 0;
	TopPosition = (screen.height) ? (screen.height-inputH)/2 : 0;

	window.moveTo(LeftPosition,TopPosition);
 }
// ���̾��˾� ��ȸ
function layer_open(el){

	var temp = $('#' + el);		// ���̾��� id�� temp������ ����
	var bg = temp.prev().hasClass('bg');	// dimmed ���̾ �����ϱ� ���� boolean ����

	if(bg){
		$('.layer').fadeIn();
	}else{
		temp.fadeIn();	// bg Ŭ������ ������ �Ϲݷ��̾�� �����Ѵ�.
	}

	// ȭ���� �߾ӿ� ���̾ ����.
	if (temp.outerHeight() < $(document).height() ) temp.css('margin-top', '-'+temp.outerHeight()/2+'px');
	else temp.css('top', '0px');
	if (temp.outerWidth() < $(document).width() ) temp.css('margin-left', '-'+temp.outerWidth()/2+'px');
	else temp.css('left', '0px');

	temp.find('div.pop-conts').click(function(e){
		if(bg){
			$('.layer').fadeOut();
		}else{
			temp.fadeOut();		// '�ݱ�'��ư�� Ŭ���ϸ� ���̾ �������.
		}
		e.preventDefault();
	});

	$('.layer .bg').click(function(e){
		$('.layer').fadeOut();
		e.preventDefault();
	});

}	
//���� analytics �ڵ�
(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
	  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
	  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
	  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');
	  ga('create', 'UA-72465170-1', 'auto');
	  ga('send', 'pageview');
