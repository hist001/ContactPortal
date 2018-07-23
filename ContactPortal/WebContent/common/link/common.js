/*******************************************************************************
 * WMS 공용 JavaScript
 ******************************************************************************/


var GTabobj;  // Tab class처리시 사용
var Gobj; // row class처리시 사용
var firstX =0 ;	
var firstY =0 ;
var setDiridx='Y';// 한번펼치기
var preObjId ='';
var revCn;		// 교차행 체크시 사용
/*******************************************************************************
 * 값을 체크하는 함수
 ******************************************************************************/
/**
 * 입력값이 NULL인지 체크
 */
function isNull(input) {
    if (input.value == null || input.value == "") {
        return true;
    }
    return false;
}

/**
 * 입력값에 스페이스 이외의 의미있는 값이 있는지 체크 ex) if (isEmpty(form.keyword)) { alert("검색조건을
 * 입력하세요."); }
 */
function isEmpty(input) {
    if (input.value == null || input.value.replace(/ /gi,"") == "") {
        return true;
    }
    return false;
}
/**
 * 입력값에 스페이스 이외의 의미있는 값이 있는지 체크 ex) if (isEmptyVal(form.keyword.value)) {
 * alert("검색조건을 입력하세요."); }
 */
function isEmptyVal(s) {
    if (s == null || s.replace(/ /gi,"") == "") {
        return true;
    }
    return false;
}

// 값이 숫자인지를 검사한다
function isDigit(c) 
{
  return ((c >= "0") && (c <= "9"))
}

// 값이 정수인지를 검사한다.
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

// 부호가 있는 정수인지를 검사한다.
function isSignedInteger(s) 
{
    if (isEmpty(s))
        return false
    
    var startPos = 0 ;
    
    if ((s.charAt(0) == "-") || (s.charAt(0) == "+"))
       startPos = 1;
    return (isInteger(s.substring(startPos, s.length)))
}

// 100평균을 넘지못하도록 한다.
function isNonnegativeInteger(s) 
{
  return ( isSignedInteger(s) && (parseInt(s) >= 0) && (parseInt(s) <= 100) );
}
// 시간인지 검사
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
// 날자형식인지를 검사한다.
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

// YYYY-MM-DD의 형식인지를 검사한다.
function isFormatDate(s) 
{
  // alert("6:"+s);
  if ( isMMDate(s.substring(5, 7)) && isDDDate(s.substring(8, 10)) )
  return true
  else
  return false
}

// 월입력시 해달월이 맞는지를 검사한다.
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

// 일입력시 해당 일자가 날자를 넘는지 검사한다.
function isDDDate(s) 
{
    // alert("8:"+s +" int :" +parseInt(s));
    if (s.length > 1) {
      if (s.charAt(0) == 0) { s = s.substring(1, 2) }
    }
    return ((parseInt(s) > 0) && (parseInt(s) <= 31))
}


/**
 * 입력값에 특정 문자(chars)가 있는지 체크 특정 문자를 허용하지 않으려 할 때 사용 ex) if
 * (hasChars(form.name,"!,*&^%$#@~;")) { alert("이름 필드에는 특수 문자를 사용할 수 없습니다."); }
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
	var RegExp = /[\{\}\/?;|*~`,!^\+┼<>@\#$%&\'\"\\\=]/gi;// 정규식 구문
	var obj =a
	if (RegExp.test(obj.value)) {
		alert("이름 필드에는 특수 문자를 사용할 수 없습니다.");
		obj.value = obj.value.substring(0, obj.value.length - 1);// 특수문자를 지우는
																	// 구문
		return false;
	} 
	return true;
}

/**
 * 입력값이 특정 문자(chars)만으로 되어있는지 체크 특정 문자만 허용하려 할 때 사용 ex) if
 * (!hasCharsOnly(form.blood,"ABO")) { alert("혈액형 필드에는 A,B,O 문자만 사용할 수 있습니다."); }
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
 * 입력값에 숫자만 있는지 체크 (번호 입력란 체크. 금액입력란은 isNumComma를 사용해야 합니다.)
 */
function isNumber(input) {
    var chars = "0123456789";
    return hasCharsOnly(input,chars);
}
function isNumber1(input) {
    var chars = "0123456789";
    if(!hasCharsOnly(input,chars))
    {
    	alert("숫자만 입력하실 수 있습니다.");
    	input.select();
    	return false;
    }
    else
    	return true;
}
/**
 * 입력값이 숫자,대시(-)로 되어있는지 체크 (계좌번호 입력란 체크)
 */
function isNumDash(input){
    var chars = "-0123456789";
    if(!hasCharsOnly(input,chars))
    {
    	alert("계좌번호는 숫자와 '-'만 입력 가능합니다");
    	input.select();
// input.value = '';
// input.focus();
    	return false;
    }
    else
    	return true;
}

/**
 * 입력값이 숫자,대시(-)로 되어있는지 체크 (전화번호 입력란 체크)
 */
function isPhoneNum(input){
    var chars = "-0123456789";
    if(!hasCharsOnly(input,chars))
    {
    	alert("전화번호는 숫자와 '-'만 입력 가능합니다");
    	input.select();
    	return false;
    }
    else
    	return true;
}
/**
 * 입력값이 숫자,콤마(,)로 되어있는지 체크 (금액 입력란 체크)
 */
function isNumComma(input){
    var chars = ",0123456789";
    if(!hasCharsOnly(input,chars))
    {
    	alert("숫자와 ','만 입력 가능합니다");
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
    	alert(input.name+"에는 숫자와 ','와'-'만 입력 가능합니다");
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
    	alert(input.name+"에는 숫자와 ','와'.'만 입력 가능합니다");
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
    	alert(input.name+"에는 숫자와 '.'만 입력 가능합니다");
    	input.select();
    	return false;
    }
    else
    	return true;    
}


/**
 * 영문만 입력 가능
 */
function isAlphabet(input){
    var chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz ";
    return hasCharsOnly(input,chars);
}

/**
 * 숫자만 제외하고 입력 가능(key제어)
 */
function onlyAlphabet(){
   if((event.keyCode>=96 && event.keyCode<=105) ||(event.keyCode>=48 && event.keyCode<=57) ){
	    event.returnValue=false;
    }   
}
/**
 * orgChar 문자열에서 rmChar문자열을 없애고 리턴한다 계좌번호나 금액에서 '-'나 ','를 제거할때 사용한다
 */
function removeChar(orgChar, rmChar){
    return replace(orgChar,rmChar,"");
}

/**
 * 입력값에서 콤마를 없애고 문자열 리턴. --> 되도록 removeChar 로 쓰세요. (-_-)
 */
function removeComma(input) {
    return input.value.replace(/,/gi,"");
}

/**
 * 입력값에서 '-'를 빼고 set --> 요것도.. 되도록 removeChar 로 쓰세요. (-_-)
 */
function setUnFormat(input){
	input.value = replace(input.value,"-","");
}

/**
 * 패스워드 입력란 체크 check : size 4 , 숫자만입력
 */
function isPassword(input)
{
	var chars = "0123456789";
	if(isEmpty(input))
	{
		alert(input.name+'를 입력하십시오');
		input.select();
    	return false;
	}
	
    else if(!hasCharsOnly(input,chars))
    {
    	alert(input.name+'는 숫자만 입력 가능합니다');
    	input.select();
    	return false;
    }

    else if(input.value.length != 4)
    {
    	alert(input.name+' 길이는 4자리입니다');
    	input.select();

    	return false;
    }
    else 
    	return true;
}

/**
 * 특수문자 있는지 확인 있으면 false, 없으면 true리턴
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
 * 한글만 가능
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
 * 영문 & 숫자만 입력 가능 (2002.06.25)
 */
function isAlphaNum(input){
    var chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789 ";
    return hasCharsOnly(input,chars);
}
function isEmailChk(input){
    var chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@. ";
    if(!hasCharsOnly(input,chars))
    {
    	alert("E-Mail은 영문, 숫자, '@','.' 만 입력 가능합니다");
    	input.select();
// input.value = '';
// input.focus();
    	return false;
    }
    else
    	return true;
}


/**
 * 한글 입력 불가 한글 있으면 false, 아니면 true리턴 (2002.06.25)
 */

function preventHan(input){ 
	var chars = input.value;
	for(i=0;i<chars.length;i++) {
		var a = chars.charCodeAt(i);
		if (a > 128) { 
			alert(input.name+'에 한글을 입력할 수 없습니다'); 
			input.select();
			return false; 
		}
	}
	return true;
}

/*******************************************************************************
 * 외환 에서 영문필드 체크 case --> to upper case 한글입력 불가
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
 * 외환 에서 숫자필드 체크
 */
function checkNumField(input)
{
	if(!isNumber(input))
	{
		alert(input.name+'를 숫자로 입력하십시오');
		input.select();
		return false
	}
	return true;
}

/**
 * 주어진 길이에 맞게 c을 채운다(뒷쪽으로) fillChar(input, 5, '0') --> (input.value :22) 22000
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

// 자릿수 채우기 (앞에서 부터)
function fillCharL(input, leng, c)
{
	var i;
	var rtn = "";
	
	// 디폴트 2자리
	if (leng==undefined) leng=2;

	// 디폴트 0 으로 채우기
	if (c==undefined) c="0";
	
	for ( i = 0; i < leng - input.length; i++ )
	{
		rtn = c + rtn;
	}
	rtn = rtn+ input;
	return rtn;
}

/**
 * 입력값의 바이트 길이를 리턴 ex) if (getByteLength(form.title) > 100) { alert("제목은 한글
 * 50자(영문 100자) 이상 입력할 수 없습니다."); }
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
 * 문자열의 바이트 길이를 리턴 ex) if (getByteLength(form.title) > 100) { alert("제목은 한글
 * 50자(영문 100자) 이상 입력할 수 없습니다."); }
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
 * 문자열에 있는 특정문자패턴을 다른 문자패턴으로 바꾸는 함수.
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
 * 문자열에 있는 특정문자패턴을 다른 문자패턴으로 바꾸는 함수 왼쪽부터 어떤 형을 삭제 하고 싶을때 (ex)0000040540 ==>
 * 40540 다른문자열이 나올시 replace중단 숫자앞에 0이 붙어서 나올시만 사용 바람.
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
 * 문자열에서 좌우 공백제거
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
 * 숫자를 금액형식으로 리턴 (000,000,000)
 */
 // 구형
 /*
	 * function cashReturn3(num) { var numValue = ""+num; var cashReturn3 = "";
	 * for (var i = numValue.length-1; i >= 0; i--){ cashReturn3 =
	 * numValue.charAt(i) + cashReturn3; if (i != 0 && i%3 == numValue.length%3)
	 * cashReturn3 = "," + cashReturn3; } return cashReturn3; }
	 */
// 신형
function cashReturn(num)
{
	var num = removeChar(num+"",",");
	if(isNaN(num)){
		if(num!="-")
		{
		alert('숫자만 입력가능합니다.');
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
// 숫자에서 소수점 i번째 이하 잘라내기 dotRound를 사용하세요
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
 * form 관련 함수
 ******************************************************************************/
/**
 * radio : 선택된 radio버튼이 있는지 체크
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
 * radio : radio에서 선택된 값을 가져온다.
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
 * checkbox : 선택된 체크박스가 있는지 체크
 */
function hasCheckedBox(input) {
    return hasCheckedRadio(input);
}

/**
 * checkbox : 화면에 생성되어있는 모든 체크박스중 선택된 갯수를 구한다.
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
 * checkbox : 선택된 체크박스가 몇개인지 그 개수를 반환
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
 * checkbox : 화면에 생성되어 있는 체크박스의 갯수를 리턴
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
 * checkbox : 폼에 속에 있는 체크박스를 모두 선택/비선택하게 한다
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
 * checkbox : 폼에 속에 있는 체크박스를 모두 활성/비활성하게 한다
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
 * select : select에서 str값을 가진 option을 선택되도록 설정
 */
function setSelect(input,str) {
	for(i=0;i<input.options.length;i++){
		if(input.options[i].value == str)
			input.options[i].selected=true;
	}
}
/**
 * select : select의 options들 다 삭제
 */
function dpOptions(input)
{
	var len = input.length;
	for(var i=0; i<len; i++) 
		input.options[0]=null;
}
/**
 * select : select에서 선택된 값 리턴 (2002.06.11)
 */
function getSelectedOption(obj)
{
	var idx = obj.selectedIndex;
	var v_sel = obj.options[idx].value;
	return v_sel;
}
/**
 * 새창 여는 함수(scrollbars=yes,full size)
 */
function openFullWin(url, winName)
{
	opt = ",toolbar=no,menubar=no,location=no,status=no,resizable=yes,channelmode=yes,scrollbars=yes";	
	window.open(url, winName, opt);	
}
/**
 * 새창 여는 함수(scrollbars=yes)
 */
function openWin(url, winName, sizeW, sizeH)
{
	var nLeft = screen.width/2 - sizeW/2 ;
	var nTop  = screen.height/2 - sizeH/2 ;
	opt = ",toolbar=no,menubar=no,location=no,status=no,resizable=yes,scrollbars=yes";
	window.open(url, winName, "left=" + nLeft + ",top=" +  nTop + ",width=" + sizeW + ",height=" + sizeH  + opt );
}
/**
 * 새창 여는 함수(scrollbars=no,full size)
 */
function openFullWinFix(url, winName)
{

	opt = ",toolbar=no,menubar=no,location=no,status=no,resizable=yes,channelmode=yes,scrollbars=no";	
	window.open(url, winName, opt );
}
/**
 * 새창 여는 함수(scrollbars=yes)
 */

/**
 * 새창 여는 함수(scrollbars=no)
 */
function openWinFix(url, winName, sizeW, sizeH)
{
	var nLeft  = screen.width/2 - sizeW/2 ;
	var nTop  = screen.height/2 - sizeH/2 ;

	opt = ",toolbar=no,menubar=no,location=no,status=no,scrollbars=no";
	window.open(url, winName, "left=" + nLeft + ",top=" +  nTop + ",width=" + sizeW + ",height=" + sizeH  + opt );
}

/**
 * 안보이지 않게 새창 여는 함수
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
 * 입력값에 maxlength="00" 으로 설정되어 있을 경우 그 길이를 초과하였는지 리턴(한글일 경우 2byte 를 사용하므로 유용) 해당
 * 페이지에 있는 text, textarea, password의 값 모두 체크한다.
 * 
 * 한글로 입력받는 field가 있는 경우, maxlength를 설정한 후에 submit하기 전에 isOverLen()를 사용해서 사이즈
 * 초과되는 것을 잡아줄 수 있다.
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
						alert(objElem.name+"가 제한된 길이를 초과 하였습니다.\n다시 입력하여 주십시오.");
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
 * SELECT에서 선택된 날짜를 'YYYYMMDD'형식의 문자열로 리턴 (2002.06.08)
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
 * 기타 특정형식의 값 체크
 ******************************************************************************/
/**
 * 주민등록번호 체크.
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
 * 사업자등록번호 체크.
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
		alert('유효한 사업자등록번호가 아닙니다');
		input.select();
		return false;
	}
}

/**
 * 자동 포커스 이동(현재객체, 이동객체, MaxLength)
 */
function autoFocus(input1, input2, maxLen) {
	if(input1.value.length == maxLen && event.keyCode != 9 && event.keyCode != 16) input2.focus() ;
}

/*******************************************************************************
 * 날짜 관련 function
 ******************************************************************************/
/**
 * 두 날짜간 일자 차이를 리턴 date1:시작일자, date2:종료일자
 */
function getDayBetween(date1,date2) { 
	    date1.setMilliseconds(0);
	    date2.setMilliseconds(0);	   
		var day_gab = Math.floor( (date2-date1) / (60*60*24*1000) )
		return day_gab ;
}

/**
 * 연과 월을 파라메터로 넘겨주면 해당되는 월의 일 수를 리턴
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
 * 선택한 년도, 월에 따라 일 select에 날짜를 display한다
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
 * fromDt, toDt간 날짜 간격 비교.. from < to이면 1을 , from > to이면 -1, 같으면 0리턴
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
 * 날짜를 y, m, d만큼 이동해서 리턴 (dt : YYYYMMDD(문자열), 리턴타입 : YYYYMMDD) y, m, d : +는 주어진
 * 날짜를 앞으로 이동(더하기), -는 주어진 날짜를 뒤로 이동(빼기) (2002.06.08)
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
 * 시작일과 종료일이 있고, 종료일을 기준으로 시작일을 해당 interval만큼 계산해서 세팅한다. 0 : 3일전 1 : 1주일 전 2 :
 * 1개월 전 3 : 3개월 전 4 : 1년전 5 : 3년전 6 : 5년전 (2003.03.27)
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
 * MultiSelect 관련
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
 * multi select 이동시 사용하는 method parameter : object의 name이 아니고 객체.
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
		alert("이동시에는 한 행만 선택해야합니다.");
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
		alert("이동시에는 한 행만 선택해야합니다.");
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
 * 반자를 전자로 변환
 */
function parseFull(HalfVal)
{
	var FullChar = [
	               "　", "！","＂","＃","＄","％","＆","＇","（",    // 33~
	        "）","＊","＋","，","－","．","／","０","１","２",      // 41~
	        "３","４","５","６","７","８","９","：","；","＜",      // 51~
	        "＝","＞","？","＠","Ａ","Ｂ","Ｃ","Ｄ","Ｅ","Ｆ",      // 61~
	        "Ｇ","Ｈ","Ｉ","Ｊ","Ｋ","Ｌ","Ｍ","Ｎ","Ｏ","Ｐ",      // 71~
	        "Ｑ","Ｒ","Ｓ","Ｔ","Ｕ","Ｖ","Ｗ","Ｘ","Ｙ","Ｚ",      // 81~
	        "［","￦","］","＾","＿","｀","Ａ","Ｂ","Ｃ","Ｄ",      // 91~
	        "Ｅ","Ｆ","Ｇ","Ｈ","Ｉ","Ｊ","Ｋ","Ｌ","Ｍ","Ｎ",      // 101~
	        "Ｏ","Ｐ","Ｑ","Ｒ","Ｓ","Ｔ","Ｕ","Ｖ","Ｗ","Ｘ",      // 111~
	        "Ｙ","Ｚ","｛","｜","｝","～"                        	// 121~
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
 * 전자를 반자로 변환
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
 * e-mail체크
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
 * 입력값이 사용자가 정의한 포맷 형식인지 체크 자세한 format 형식은 자바스크립트의 'regular expression'을 참조
 */
function isValidFormat(input,format) {
    if (input.value.search(format) != -1) {
        return true; // 올바른 포맷 형식
    }
    return false;
}

/**
 * day는 현재 날짜. value는 차감하고자 하는 날자의 수
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
 * 특수문자 입력 막기 (숫자만 허용,space도 막는다.)48~57
 */
function checkSpecialChar11(){	    
	if(event.keyCode < 48 || event.keyCode >57){ 
		event.returnValue = false;
	}
}

/**
 * 인쇄영역을 지정한대로 인쇄
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
 * Parameter : objid : Object ID Toggle 대상 object objTab : '1' - toggle Area ,
 * '2' = tab toggle index : toggle object index objDivMove : '1' DivMove 적용
 * x_position : 마우스로 부터 상태 x좌표 y_position : 마우스로 부터 상태 y좌표 Return : ?? ?? :
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
	// textarea 내역 핸들링
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
	// tab 핸들링
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
	// 항상조회
		toggleObj.style.display='';	
	} else if (objTab=='4'){
	// 항상 미조회
		toggleObj.style.display='none';	
	}

	if (objTab==undefined){	
		if (toggleObj.style.display=='none'){
			toggleObj.style.display='';		
		}else if (toggleObj.style.display!='none') {	
			toggleObj.style.display='none';
		}
	}

	// 마우스 이동 경로 따라 이동
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

	// 마우스 클릭 후 이동
	if(objDivMove=='2'&& event.button==1 && firstX!=0 && firstY!=0){
		toggleObj.style.top  = event.y - firstY;
		toggleObj.style.left = event.x - firstX;
	} 
	}catch(err){ } return 
}
// obj내 마우스 초기 위치 지정
function setMouseXY(ObjId){
    	var toggleObj =document.getElementById(ObjId) ;
		firstX = event.x - parseInt(toggleObj.style.left) ;	
		firstY = event.y - parseInt(toggleObj.style.top);	
}
// obj내 마우스 위치 해제
function setMouseXYFree(ObjId){
    	var toggleObj =document.getElementById(ObjId) ;
		firstX = 0;	
		firstY = 0;	
}
/*******************************************************************************
 * Function?? : setLevel(highcode, tableName) Parameter : object ID = highcode
 * --> 'Level_상위코드' Return : Date : 2006.06.16 mailbest
 ******************************************************************************/	
function setLevel(highcode, tableName){
  	var frm =  document.forms[0];
  	var tableName = document.getElementById(tableName);
	var arr;

   	// 관리 가능 레벨 99레벨
	arr = highcode.split("_");
	level = eval(arr[0])+1;

	var highcode =  highcode.substr(arr[0].length+1,highcode.length-(arr[0].length+1)); 
	var setlevelCd = level +'_'+ highcode; 

		// 조회된 내역이 없을때
		if (tableName==null) return;
		
		// 전체 보기
		if (highcode=='All'){
			for(k=0;k< tableName.rows.length;k++){
					tableName.rows[k].style.display='';
			}
		// 전체 닫기
		} else if(highcode=='Def'){
			for(k=1;k< tableName.rows.length;k++){
					tableName.rows[k].style.display='none';
			}
		}
		
		if (document.all(setlevelCd)==null) return;

		
		// 선택 level 하위 level 열기/닫기
		if (document.all(setlevelCd).length==undefined){
	        // 하위Level이 한개 일때
			if (document.all(setlevelCd).style.display=='none'){
				document.all(setlevelCd).style.display='';
			} else if (document.all(setlevelCd).style.display!='none'){
				document.all(setlevelCd).style.display='none';
			}		

			// 상위 level을 닫을 때 하위 level 전부 닫기
		    if (document.all(setlevelCd).style.display=='none'){	
				for(k=0;k< tableName.rows.length;k++){
					if (tableName.rows[k].id.substr(0,1) > level || tableName.rows[k].id.substr(0,2) > level){							
						tableName.rows[k].style.display='none';
					}

				}
			}
		
		} else { // 하위Level이 여러게 일때 배열 처리
			for(j=0;j< document.all(setlevelCd).length;j++) {
				if (document.all(setlevelCd)[j].style.display=='none'){
					document.all(setlevelCd)[j].style.display='';		
				}else if (document.all(setlevelCd)[j].style.display!='none') {	
					document.all(setlevelCd)[j].style.display='none';
				}
			}			// 상위 level을 닫을 때 하위 level 전부 닫기
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
 * 구분코드(org:조직,biz-사업...) codeNo - 시스템코드 (project.prj_no, org.org_no)를 담을 object
 * code - 사용자코드를 담을 object codeName - 사용자코드명을 담을 object code2 - 사용자코드2를 담을
 * object codeName2 - 사용자코드명2을 담을 object Return : Date : 2006.06.25 mailbest
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
 * Parameter : 1) param - 구분코드(org_new:조직,biz_new-사업...) 2) paramId - 팝업구분코드
 * (org_1,pjt_1,emp_1) 3) codeNo - 시스템코드 (project.prj_no, org.org_no)를 담을 object
 * 4) code - 사용자코드를 담을 object 5) codeName - 사용자코드명을 담을 object 6) code2 - 사용자코드2를
 * 담을 object 7) codeName2 - 사용자코드명2을 담을 object 8) width - 팝업 넓이 9) height - 팝업
 * 높이 10) idx - 동일 컬럼명에 대한 배열 처리 *** default:0 으로 입력 11) autoSel - true/false 팝업
 * 호출 시 자동 선택 처리 (선택 대상이 하나일때 자동 선택 기능) 12) setYmd - 팝업 조회시 기준 년월일 전달 Return :
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
 * Parameter : param - 구분코드(org_new:조직,biz_new-사업...) paramId - 팝업구분코드
 * (org_1,pjt_1,emp_1) codeNo - 코드를 담을 object width - 팝업 넓이 height - 팝업 높이 idx -
 * 동일 컬럼명에 대한 배열 처리 Return : 다중선택 한 DATA를 전달 Date : 2008.11.27 mailbest
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
// 배열을 Sting으로
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
 * Parameter : param - 구분코드(org_new:조직,biz_new-사업...) paramId - 팝업구분코드
 * (org_1,pjt_1,emp_1) codeNo - 시스템코드 (project.prj_no, org.org_no)를 담을 object
 * code - 사용자코드를 담을 object codeName - 사용자코드명을 담을 object code2 - 사용자코드2를 담을
 * object codeName2 - 사용자코드명2을 담을 object width - 팝업 넓이 height - 팝업 높이 Return :
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
 * Parameter : param - 구분코드(org_new:조직,biz_new-사업...) paramId - 팝업구분코드
 * (org_1,pjt_1,emp_1) codeNo - 시스템코드 (project.prj_no, org.org_no)를 담을 object
 * code - 사용자코드를 담을 object codeName - 사용자코드명을 담을 object code2 - 사용자코드2를 담을
 * object codeName2 - 사용자코드명2을 담을 object width - 팝업 넓이 height - 팝업 높이 Return :
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
 * Parameter : param - 구분코드(org_new:조직,biz_new-사업...) paramId - 팝업구분코드
 * (org_1,pjt_1,emp_1) codeNo - 시스템코드 (project.prj_no, org.org_no)를 담을 object
 * code - 사용자코드를 담을 object codeName - 사용자코드명을 담을 object code2 - 사용자코드2를 담을
 * object codeName2 - 사용자코드명2을 담을 object width - 팝업 넓이 height - 팝업 높이 Return :
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
 * 구분코드(org:조직,biz-사업...) codeNo - 시스템코드 (project.prj_no, org.org_no)를 담을 object
 * code - 사용자코드를 담을 object codeName - 사용자코드명을 담을 object code2 - 사용자코드2를 담을
 * object codeName2 - 사용자코드명2을 담을 object Return : Date : 2006.07.09 add cylim
 * 영업보고와 합동보고시 popup 창이 별도로 필요하여 추가
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
 * 구분코드(org:조직,biz-사업...) codeNo - 시스템코드 (project.prj_no, org.org_no)를 담을 object
 * code - 사용자코드를 담을 object codeName - 사용자코드명을 담을 object code2 - 사용자코드2를 담을
 * object codeName2 - 사용자코드명2을 담을 object Function :
 * popupSearch1(param,codeNo,code,codeName) Return : Date : 2006.07.09 modify
 * whcho 영업보고와 합동보고시 popup 창이 별도로 필요하여 추가
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
 * Parameter : param - 구분코드(org:조직,biz-사업...) codeNo - 시스템코드 (project.prj_no,
 * org.org_no)를 담을 object code - 사용자코드를 담을 object codeName - 사용자코드명을 담을 object
 * code2 - 사용자코드2를 담을 object codeName2 - 사용자코드명2을 담을 object Return : Date :
 * 2006.07.09 modify whcho 영업보고와 합동보고시 popup 창이 별도로 필요하여 추가
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
 * Function : clearText(objName) Parameter : objName - 값 삭제 대상 object Name
 * Return : Date : 2006.08.02 mailbest
 ******************************************************************************/	
function clearText(objName){
	var frm= document.forms[0];		
  	var obj = frm.all(objName);
	obj.value="";
}	
/*******************************************************************************
 * Function : cutStr(str,len, tail)문자열 길이 자르기&줄임말 붙이기 Parameter : str - 대상 문자열
 * len - 문자 길이 지정 tail - 붙임 말
 * 
 * Return : cutStr('한진 정보통신',2,'...') -> '한진...' Date : 2006.08.02 mailbest
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
 * Function : divSearch(msg) 지연 팝업 Parameter : obj - optional 지연 메시지 Return :
 * Date : 2007.12.28 mailbest
 ******************************************************************************/	
function divSearch(msg){

	if (msg==undefined){
		msg = '처리 중 입니다 . . .'; // 기본값 설정
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
 * Function : 컬럼 조회 정렬 처리 Parameter : sortCol - 정렬 처리 할 컬럼명, idx - 컬럼 index(순서)
 * Return : Date : 2007.03.23 mailbest
 ******************************************************************************/	
function setOrderBy(sortCol,idx){
	var paramSortTmpObj = document.getElementsByName('paramSortTmp');
	var paramSortAscObj = document.getElementsByName('paramSortAsc');
	var  paramSortIdxObj = document.getElementsByName('paramSortIdx');
	// 컬럼 선택 조회시
	paramSortTmpObj[0].value= sortCol;// 정렬 컬럼
	
	if (paramSortAscObj[0].value=="0"){ 	// 정렬
		paramSortAscObj[0].value="1"; 		// ASC
	}else{
		paramSortAscObj[0].value="0";		// DESC
	}
    
    paramSortIdxObj[0].value= idx; // 정렬 컬럼 인텍스
	
}
// 컬럼 정렬 img 초기화
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
// 체크
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
// 변경체크
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

// 년월일 입력시 날짜포맷에 맞게 변경 (keyboard제어)
function chkFormatDate(inputDt,opt)  
{	
	frm=document.forms[0];
    if (inputDt.name==''||inputDt.name==undefined){// name으로 들어왔을때
    	inputDt = document.getElementsByName(inputDt);
    }
        
	if(event.keyCode==8 || event.keyCode==46 || event.keyCode==37 || event.keyCode==39|| event.keyCode==36) 	return;
	
	if (opt==6){// yyyy-mm
		opt=7;
	}else{// yyyy-mm-dd
		opt=10;
	}

    if (inputDt.name==''||inputDt.name==undefined){// name으로 들어왔을때
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
	
    if (inputDt.name==''||inputDt.name==undefined){// name으로 들어왔을때
    	inputDt[0].value= mkinputDt.substring(0,opt);
    }else{
    	inputDt.value= mkinputDt.substring(0,opt);
    }
}
// 카드번호 형식에 맞게 변경 (keyboard제어)
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

// 사업자번호 형식에 맞게 변경 (keyboard제어)
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

// 문서번호 형식에 맞게 변경 (keyboard제어)
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
// 년도 증가
function addYear(objId,addYear){
    	var year =document.getElementById(objId);
    	year.value =parseInt(year.value)+parseInt(addYear);    	
}
// 당월,이전달,다음달 셋팅
function setMon(frDT,toDT,addMonth){
		var frm=document.forms[0];		
		var now = new Date();
		var From_date;
		var To_date;
		var _year;
		var _month;
		
		var frDTObj = document.getElementsByName(frDT); 
		var toDTObj = document.getElementsByName(toDT); 
		
		
		// 이전,다음 달
		if (addMonth!=0){
			
			_year	=	frDTObj[0].value.substring(0,4);
			_month	=	frDTObj[0].value.substring(5,7);
			
			if (addMonth<0){
				now=getPrevYearMonth(_year,_month);		
			}else if (addMonth>0){
				now=getNextYearMonth(_year,_month);		
			}
			
			_year	=	now.substring(0,4);// 월
			_month	=	now.substring(4,6);// 년
			
		} else {
			// 당월
			_year  = now.getFullYear();						// 년
			_month = getTwoLength(now.getMonth()+1);  	// 월
		}
		
		From_date = new Date(_year,_month, 1);// 월초
		To_date = new Date(_year,_month, 0);// 월말
		
		var s_date  = getTwoLength(From_date.getDate());
		var e_date  = getTwoLength(To_date.getDate());

		From_date	=_year+"-"+_month+"-"+s_date; // 월초
		To_date		=_year+"-"+_month+"-"+e_date; // 월말
		
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

// DHTML에서 rowIndex 반환
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
					
					// 인덱스 반환
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
			
					// 인덱스 반환
					getIndex=objMaxLen -(objMaxSourceIndex-objIndex)/objTerm;
				}
		}
    return getIndex;
}

// object color 설정
function setBox(obj){
	var setColor=new Array('gray','white','#F0F0FF');
   	setObjColor(obj,setColor);
}
// object color 적용
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
// 체크박스 선택 처리
function setChkFlag(obj,selrow){
    var frm= document.forms[0];
   	var i=	dhtmlIndex(obj);
   	if (selrow!=undefined) i=selrow;// row지정시 적용
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
	
		// class 처리
		if (obj.name=='chkLine') chgClass(obj,'Chk');
}

// 체크박스 전체 선택/해제 처리
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
// Row 선텍시 class 처리
function chgClass(obj,param){
	var frm=document.forms[0];
	var objNms=document.getElementsByName(obj.name);
	// Mouseover시
	if(param=="Mover" && (obj.className=='onbase striped' || obj.className=='striped' || obj.className=='onbase')){
		if(obj.className.indexOf('striped') >= 0){
			revCn = true;
		}else{
			revCn = false;
		}
		obj.className='on';
	// Mouseout시
	}else if (param=="Mout" && obj.className=='on' || obj.className=='striped') {
		if(revCn){
			obj.className='striped';	
		}else{
			obj.className='onbase';
		}
		
		if(Gobj!=undefined) Gobj.className='onbaseD0';	
	// Row선택시 선택
	}else if (param=='Mfect') {
		if(Gobj!=undefined) Gobj.className='onbase'; // &&
														// obj.sourceIndex!=Gobj.sourceIndex
		Gobj=obj;
		obj.className='onbaseD0';	
	// TAB 선택시 선택
	}else if (param=='MTab') {
		if(GTabobj!=undefined ) GTabobj.className='tabBt';// &&
															// obj.sourceIndex!=GTabobj.sourceIndex
		GTabobj=obj;
		obj.className='tabBtOn';	
	// TABs 선택시 선택 (작은탭)
	}else if (param=='MTabs') {
		if(GTabobj!=undefined ) GTabobj.className='tabBts';// &&
															// obj.sourceIndex!=Gobj.sourceIndex
		GTabobj=obj;
		obj.className='tabBtsOn';	
	// check 선택시 class변경
	}else if (param=='Chk') {
		if (obj.className!='onbaseD1'){
			obj.className='onbaseD1';	
		}else{
			obj.className='onbase';	
		}
	}
}
// div 높이 자동 조절
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
  
  // 모달 팝업 창사이즈 적용 처리 2010-03-17
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

// div 너비 자동 조절(KDS추가 20110720)
function setDivWidthSize(width,divName){
  var ChgWinSize= width;
  var DivNameStr = "";
  var windowWidth=""
    
  if(divName==undefined) {
	  DivNameStr="divList";  
  }else{
	  DivNameStr=divName;  
  }
  // 모달 팝업 창사이즈 적용 처리
  if(document.body.clientWidth==0) {
	windowWidth=window.dialogArguments.document.body.clientWidth;
  }else{
	windowWidth=document.body.clientWidth;
  }
  
  document.all(DivNameStr).style.width=windowWidth-ChgWinSize*2;
}

// form 체크 후 해당 obj가 있는 폼 지정
function selform(obj){
	for(i=0;i<document.forms.length;i++){
		if(document.forms[i].all(obj)!=null){
			return document.forms[i];
		}
	} 	
	return false;
}
// 엔터키 ,줄바꿈 키 Count Return
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

// 메뉴얼 조회
function openManual(location,muCd,muType,muSubType){
	 if(muSubType==undefined)   muSubType="USR";  // 사용자 매뉴얼
	path = replace(location+"","&","%26");
	openWin("/Manual/mu_index.jsp?S_PATH="+path+"&S_MU_CD="+muCd+"&S_MU_TYPE="+muType+"&S_MU_SUBTYPE="+muSubType,"",1000,640);
}

// 전자문서관리시스템(EDMS) 조회
function openEDMS(location,muCd,muType){
	path = replace(location+"","&","%26");
	openWinFix("/ED/edMain.jsp","",813,615);
}

function imgResize(MaxWidth,PrintYn) {
  for(var i=0;i<document.images.length;i++) {
    if(document.images[i].width > MaxWidth) {
      document.images[i].height=document.images[i].height*(MaxWidth/document.images[i].width);
      document.images[i].width=MaxWidth;
    }else if(document.images[i].width=='730'&PrintYn=='Y'){// 인쇄 이미지 사이즈 원복
    	document.images[i].height=document.images[i].height*(MaxWidth/document.images[i].width);
    	document.images[i].width=MaxWidth;
    }
  }
}

// Tree이미지 처리
function setTreeImg(obj){
	var frm=document.forms[0];	
	// 최초 obj 상태
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
/* tree 그리기 */
function addItem(lev,id,htmlStart,htmlMid,htmlEnd,levelWidth){
	var treeImg='';
    if (levelWidth ==undefined) levelWidth='10';

	for(i=0;i<lev;i++) if(i!=0) treeImg = treeImg+ '<img src="../images/treeImage/s.gif" width="'+levelWidth+'" height="10" style="border:1">'; // tree
																																				// 레벨간격
	
	htmlMid=treeImg+htmlMid;
	document.getElementById(id).insertAdjacentHTML('beforeEnd',htmlStart+htmlMid+htmlEnd);
}

// Tree 폴더처리
function setDir(obj,mEvent,imgCk,treeType){
    // mEvent : 0-mouseOut, 1-mouseOver , 2-mouseClick
    // imgCk : '1'-Image Click ,'0'/''-Imgag Non Click
	// 미정의된 Parameter 처리
   	if (mEvent ==undefined) mEvent='';
   	if (imgCk ==undefined) imgCk='';
   	if (treeType ==undefined) treeType='1';
   	
	// 이미지 클릭의 경우 이름변경 처리
	obj=obj.replace("img_","");

	// img obj 처리
	getObj = document.getElementById('img_'+obj);
	imgObj = getObj.src.substring(getObj.src.length-5,60);
	
	// root는 제외
	if(obj!='ROOT')	{
		// 하위폴더가 없을때 제외
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
	// setDir이벤트 1회만 처리
	if(imgCk=='3')	setDiridx='Y';
}
 
// Tree 이미지 처리(New Tree 적용)
function settreeImage(obj,treeType){	
	// 미정의된 Parameter 처리
   	if (treeType ==undefined) treeType='';	
	getTreeObj = document.getElementById('img_'+obj);
	
	// root일때
	if (obj.substring(obj.length-1,100)=='@') return;
	// 하위폴더가 없을때
	if (getTreeObj.src.substring(getTreeObj.src.length-8,60)=='tree.gif') return;

	// tree이미지 토글 처리
	if (getTreeObj.src.substring(getTreeObj.src.length-10,60)=='/tree+.gif'){
		getTreeObj.src='../images/treeImage/'+treeType+'/tree-.gif';// 시스템 등록 폴더
	}else if (getTreeObj.src.substring(getTreeObj.src.length-14,60)=='edms/tree+.gif'){
		getTreeObj.src='../images/edms/tree-.gif';     // 사용자등록 폴더
	}else if (getTreeObj.src.substring(getTreeObj.src.length-10,60)=='/tree-.gif'){
		getTreeObj.src='../images/treeImage/'+treeType+'/tree+.gif';
	}else if (getTreeObj.src.substring(getTreeObj.src.length-14,60)=='edms/tree-.gif'){
		getTreeObj.src='../images/edms/tree+.gif';
	}
}

// Tree 배경 처리
function setBgColor(obj,mEvent,imgCk){		
    // mEvent : 0-mouseOut, 1-mouseOver , 2-mouseClick
    // imgCk : '1'-Image Click ,'0'/''-Imgag Non Click

	// 미정의된 Parameter 처리
   	if (mEvent ==undefined) mEvent='';
   	if (imgCk ==undefined) imgCk='';
	getObj = document.getElementById(obj.id);
	
	// 기 선택된 row의 경우 배경색 변경 제외 처리
	if ((getObj.style.backgroundColor=='#c7e3e4'||getObj.style.backgroundColor=='rgb(199, 227, 228)')& mEvent!=2) return;

	if (mEvent==0){// mouseOut
		getObj.style.backgroundColor ='';
	}else if (mEvent==1){// mouseOver
		getObj.style.backgroundColor ='c6d6ec';
	}else if (mEvent==2){// //mouseClick

		// 직전 선택 row 초기화
		if (preObjId!=''){
			preObj = document.getElementById(preObjId);
			preObj.style.backgroundColor ='';
		}		
		
		preObjId=obj.id;
		getObj.style.backgroundColor ='c7e3e4';
	}
}
// 문자 입력완성 체크
function chkCharFinish(str) {
	if (str==null) return false;

	for(var i=0;i<str.length;i++){
		var c=str.charCodeAt(i);
		// (0xAC00 <=c && c<=0xD7A3) 초중종성이 모인 한글자
		// (0x3131<=c && c<=0x318E) 자음 모음
		if(0xAC00 <= c && c <= 0xD7A3){// 한글완성
			// return true;
		}else if (0x3131<=c && c<=0x318E){// 한글 작성중 포함
			return false;
		}else{// 한글 외 문자
		}
	}
	return true;
}
// obj위치 정하기
function setPosition(obj,mod_x,mod_y){

	// 미정의된 Parameter 처리
   	if (mod_x ==undefined) mod_x=0;// '550';
   	if (mod_y ==undefined) mod_y=0;// '375';

	var tObj = document.all(obj);
	var frm=document.forms[0];
	// 기 조회된 화면은 위치조정을 하지 않는다.
	if (tObj.style.display!='none') return;


    // 브라우져 넓이, 스크롤이 되었다면 그만큼 더한다. (document.body.clientWidth : 화면에 보이는 사이즈만)
    var browserWidth = document.body.clientWidth + document.body.scrollLeft;
    // 브라우져 높이, 스크롤이 되었다면 그만큼 더한다. (document.body.browserHeight : 화면에 보이는 사이즈만)
    var browserHeight = document.body.clientHeight + document.body.scrollTop;  
    // textFieldObject 객체의 위치 얻어오기
    var point = getObjectXY(tObj);
    var divLeft = point.x;
    var divTop  = point.y + tObj.offsetHeight;  // 텍스트 박스 높이만큼 더한다.(텍스트 박스 밑으로
												// 보이게 하기 위해)

    if (divLeft < document.body.scrollLeft) {
        // tObj 객체가 왼쪽으로 보이지 않는 곳에 위치할 때
        divLeft = document.body.scrollLeft;
    } else if ((divLeft + frameWidth+200) > browserWidth) { 
    		
        // tObj 객체가 오른쪽으로 보이지 않는 곳에 위치할 때
        divLeft = browserWidth - frameWidth - 750;         // 실제윈도우는 약간 더 크다.
															// 그래서 10을 더 뺐다.
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
// Table내 Row Drag이동(jQuery)
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
// 스크롤 연동
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

// 화면중앙위치
function winResize(inputW, inputH){
	window.resizeTo(inputW,inputH);

	LeftPosition = (screen.width) ? (screen.width-inputW)/2 : 0;
	TopPosition = (screen.height) ? (screen.height-inputH)/2 : 0;

	window.moveTo(LeftPosition,TopPosition);
 }
// 레이어팝업 조회
function layer_open(el){

	var temp = $('#' + el);		// 레이어의 id를 temp변수에 저장
	var bg = temp.prev().hasClass('bg');	// dimmed 레이어를 감지하기 위한 boolean 변수

	if(bg){
		$('.layer').fadeIn();
	}else{
		temp.fadeIn();	// bg 클래스가 없으면 일반레이어로 실행한다.
	}

	// 화면의 중앙에 레이어를 띄운다.
	if (temp.outerHeight() < $(document).height() ) temp.css('margin-top', '-'+temp.outerHeight()/2+'px');
	else temp.css('top', '0px');
	if (temp.outerWidth() < $(document).width() ) temp.css('margin-left', '-'+temp.outerWidth()/2+'px');
	else temp.css('left', '0px');

	temp.find('div.pop-conts').click(function(e){
		if(bg){
			$('.layer').fadeOut();
		}else{
			temp.fadeOut();		// '닫기'버튼을 클릭하면 레이어가 사라진다.
		}
		e.preventDefault();
	});

	$('.layer .bg').click(function(e){
		$('.layer').fadeOut();
		e.preventDefault();
	});

}	
//구글 analytics 코드
(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
	  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
	  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
	  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');
	  ga('create', 'UA-72465170-1', 'auto');
	  ga('send', 'pageview');
