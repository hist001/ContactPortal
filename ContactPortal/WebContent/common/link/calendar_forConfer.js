	
var gMonths = new Array("1��","2��","3��","4��","5��","6��","7��","8��","9��","10��","11��","12��");
var weekArray = new Array('��','��','ȭ','��','��','��','��');

//���� ��¥
var todayDateObject = new Date();
var currentYear = todayDateObject.getYear();
var currentMonth = todayDateObject.getMonth()+1;
var currentDay = todayDateObject.getDate();
var currentYMD = "";
//�̹� ���õ� ��¥
var selectedYMD = "";
var textFieldObject ;
var divObject;
//���ڻ� �����ϱ�
var sunColor = "EA3030";
var satColor = "1F63CA";
var norColor = "000000";
var otherColor = "#808080";
//���� �����ϱ�
var todayBgColor = "FFE0D1";
var selectedBgColor = "A9B4FF";
var norBgColor = "FFFFFF";

//������ �⵵�� ���� ����(�⺻��)
var startYearScope = 2000;
var endYearScope = 2015;

//���� �����ְ� �ִ� �޷��� ��,��
var nowYear = "";
var nowMonth = "";

//var seperator="-";
/**************************************
*    ������ ��, ���� ������ ����� ��ȯ
***************************************/
function getPrevYearMonth(_year, _month) {
    if(Number(_month) < 2) {
        return Number(_year)-1 + "12";
    }else {
        return Number(_year) +""+ getTwoLength(Number(_month)-1);
    }
}
/***************************************
*    ������ ��, ���� �̿��� ����� ��ȯ
***************************************/
function getNextYearMonth(_year, _month) {
    if(_month == 12) {
        return Number(_year)+1 + "01";
    }else {
        return Number(_year) +""+ getTwoLength(Number(_month)+1);
    }
}
/***************************************
*    ������ ��¥�� ���ڸ� ���ڷ� ��ȯ
***************************************/
function getTwoLength(_day) {
    if(Number(_day) < 10) {
        return "0" + _day;
    }else {
        return _day;
    }
}

/***************************************
*    ������ ��, ���� ������ ����Ѵ�.
***************************************/
function getMonthDayCount(_year, _month){
    selectedMonthFirstDay = new Date(_year, _month-1 , 1);
    selectedNextMonthFirstDay = new Date(_year, _month, 1);
    var _selectedMonthDayCount = (selectedNextMonthFirstDay - selectedMonthFirstDay)/1000/60/60/24;
    return _selectedMonthDayCount;
}

/*
*    ������ ���� ù���� ������ ��ȯ
*/
function getDayOfWeek(_year, _month){
     var _dayOfWeek= new Date(_year, _month-1, 1).getDay();
     return _dayOfWeek;
}

/***************************************
*    ������ ���� ��¥�� �迭�� ��ȯ
***************************************/
function getMonthMetrix(_year, _month) {
    var monthMetrix = new Array(42);
    var selectedMonthDayCount = getMonthDayCount(_year, _month);
    var selectedPrevMonthDayCount = 0;
    var selectedFirstDayOfWeek = 0;
    if(_month == 1)
        selectedPrevMonthDayCount = getMonthDayCount(_year-1, 12);
    else 
        selectedPrevMonthDayCount = getMonthDayCount(_year, _month - 1);

    selectedFirstDayOfWeek = getDayOfWeek(_year, _month)

    var startNum = 1;
    var nextStatrNum = 1;
    for(i=0; i < 6; i++) {
        for(j = 0; j < 7; j ++) {
            var tempYearMonthDay = "00000000";
            var info = 0;
            if( i==0 && selectedFirstDayOfWeek > j ) {
                _tempDay = (selectedPrevMonthDayCount - selectedFirstDayOfWeek) + (j+1);
                tempYearMonthDay = getPrevYearMonth(_year, _month) + "" + getTwoLength(_tempDay);
            }else if(startNum <= selectedMonthDayCount) {
                tempYearMonthDay = _year + "" + getTwoLength(_month) + "" + getTwoLength(startNum);
                startNum ++;
            }else {                    
                tempYearMonthDay = getNextYearMonth(_year, _month) + "" + getTwoLength(nextStatrNum);
                nextStatrNum ++;
            }
            monthMetrix[(i*7) + (j)] = tempYearMonthDay;
        }
    }
    return monthMetrix;
}

/***************************************
*    gubun 1: ��, 2: ��, 3: �� �� ��ȯ
***************************************/
function getStr(_date, gubun) {
    if(gubun == 1) {
        return _date.substring(0, 4);
    }else if(gubun ==2) {
        return _date.substring(4, 6);
    }else {
        return _date.substring(6);
    }
}

/*************************************************
*    �ش���� ���� �迭�� �޾Ƽ� table �±׷� ����
*************************************************/
function getMonthMetrixHtml(_year, _month,tmin) {

    var _tempMetrix = getMonthMetrix(_year, _month);
    var _metrixHtml = "";
    _metrixHtml += ("<table width='100%' border='0' cellpadding='0' cellspacing='1' style='z-index:99'>");

    _metrixHtml += "<tr>";
    for(i=0; i<7; i++)
        _metrixHtml += ("<td height='24' bgcolor='#F4F4F4' borderColor='#FFFFFF' valign='middle' align='center' style='font-size:12px;color:#737373;font-weight:bold'>" + weekArray[i] + "</td>");
    _metrixHtml += "</tr>";

    for(i=0; i < 6; i++) {
        _metrixHtml += "<tr>";
        for(j = 0; j < 7; j++) {
            var _strDate = _tempMetrix[(i*7) + (j)];
            var color = "";
            var bgColor = "";
            if(j==0) {
                color = sunColor;
            }else if(j == 6) {
                color = satColor;
            }else {
                color = norColor;
            }
            if(_year != getStr(_strDate, 1) || Number(_month) != Number(getStr(_strDate, 2))) {
                color = otherColor;
            }
            if(_strDate == currentYMD) {
                bgColor = todayBgColor;
            }else if(_strDate == selectedYMD) {
                bgColor = selectedBgColor;
            }else {
                bgColor = norBgColor;
            }
            _metrixHtml += "<td height='23' bgcolor='"+bgColor+"' borderColor='#FFFFFF' valign='middle' align='center' style='cursor:hand;' onclick=\"parent.setDateExit('"+_strDate+"',"+tmin+");\">";
            _metrixHtml += "<font style='font-family:����;font-size:12px' color='"+color+"'>"+Number(getStr(_strDate, 3)) + "</font>";
            _metrixHtml += "</TD>";
        }
        _metrixHtml += "</tr>";
    }
    _metrixHtml += ("</table>");
    return _metrixHtml;
}

/**********************************
*    �޷���ü�� html �� ��ȯ �Ѵ�.
**********************************/
function getCalendarHtml(_year, _month,tmin) {

    var _html = "";
    _html += ("<table border='0' cellspacing='4' cellpadding='0' width='100%' bgcolor='#88A8D8'>");//?? ??? ??
    _html += ("<tr>");
    _html += ("        <td align='center'><input type='button' name='PrevMonth' value='<' style='height:20;width:20;FONT:16 Fixedsys' onClick='parent.prevMonth()'>");
    _html += ("&nbsp;<SELECT name='tbSelYear' onChange='parent.resetDate(tbSelYear.value, tbSelMonth.value,"+tmin+")' style='font-family:??; font-size:13px; color:#737373;'>");

    for(i = startYearScope; i <= endYearScope; i++) {
        if(i == _year) {
            _html += ("<OPTION value='"+i+"' selected>"+i+"</OPTION>");
        }else {
            _html += ("<OPTION value='"+i+"'>"+i+"</OPTION>");
        }
    }

    _html += ("</SELECT>");
    _html += ("&nbsp;<select name='tbSelMonth' onChange='parent.resetDate(tbSelYear.value, tbSelMonth.value,"+tmin+")' style='font-family:??; font-size:13px; color:#737373;'>");

    for (i=0; i<12; i++) {
        if((i) == (Number(_month)-1)) {
            _html += ("<option value='"+(i+1)+"' selected>"+gMonths[i]+"</option>");
        }else {
            _html += ("<option value='"+(i+1)+"'>"+gMonths[i]+"</option>");
        }
    }

    _html += ("</SELECT>");
    _html += ("&nbsp;<input type='button' name='PrevMonth' value='>' style='height:20;width:20;FONT:16 Fixedsys' onclick='parent.nextMonth()'>");
    _html += ("&nbsp;<a href='javascript:parent.cancelDate()'><img src='/images/common/icon_close.gif' align='absmiddle' border=0></a>");
    _html += ("</td>");
    _html += ("</tr><tr>");
    _html += ("<td align='center' bgcolor=#D6D6D6>");
    _html += getMonthMetrixHtml( Number(_year), Number(_month), tmin);
    _html += ("</td>");
    _html += ("</tr>");
    _html += ("</TABLE>");
    //_html += ("</body></html>");
    return _html;
}

/*******************************************************************
*    ���õ� ��¥�� �Է¹ڽ��� ������ �޷��� �����.
*******************************************************************/

var SEPERATOR = ".";

function setDateExit(ymd,tmin){

	if (ymd >= tmin) {
	
	    if(isSeperator==true){
	        textFieldObject.value = ymd.substring(0,4) + SEPERATOR +ymd.substring(4,6)+ SEPERATOR +ymd.substring(6,8);
	    }else{
	        textFieldObject.value = ymd;
	    }
	    toggleCalendar();
	}else{
		alert("������ ��¥�� ��ȿ���� �ʽ��ϴ�.")
	}
}

var isSeperator=true;

function isFormat(t){
    isSeperator=t;
}

/**********************************
*    �޷��� �����.
**********************************/    
function cancelDate() {
    toggleCalendar();
}

/**********************************
*    �޷��� ����ų� , ���̰� �Ѵ�.
**********************************/
function toggleCalendar() {
    if(divObject.style.display == "none") {
        divObject.style.display = "";
    }else {
        divObject.style.display = "none";
    }    
}

/*******************************************************************
*    �־��� ��,�� �����͸� �������� �޷��� �����ش�.
*******************************************************************/


function resetDate(_year, _month, tmin){

    if( Number(startYearScope) > Number(_year)) {
        alert("���ù����� ���� �����ϴ�.");
        return;
    }else if( Number(endYearScope) < Number(_year)) {
        alert("���ù����� ���� �����ϴ�.");
        return;
    }
    nowYear = _year;
    nowMonth = getTwoLength(_month);

    //iframe ���� �����ϸ鼭 �߰���
    if(calIframe.document.body.innerHTML == "") {
        calIframe.document.write("<html><head><title>Wellcome to ITEP</title><link rel='stylesheet' href='/common/css/.css'></head><body marginwidth='0' marginheight='0' topmargin='0' leftmargin='0'></body></html>");
    }
    calIframe.document.body.innerHTML = "";
    calIframe.document.body.innerHTML = getCalendarHtml( _year, getTwoLength(_month),tmin );

}

/*******************************************************************
*    �޷��� �����ֱ����� �ʱ�ȭ�� ��Ų�� �޷��� �����ش�.
*******************************************************************/
function CalendarStart(textField, txtIndex, startYear, endYear, seper,tmin) {
    //if(startYear != "" && startYear.length == 4) {  
     
    if(startYear > 999 && startYear < 10000) {
        startYearScope = startYear;
    }
    //if(endYear != "" && endYear.length == 4 ) {
    if(endYear > 999 && endYear < 10000) {
        endYearScope = endYear;
    } 
    
    if (seper != null) {
        SEPERATOR = seper;  
    }
    
    currentYMD = currentYear +""+ getTwoLength(currentMonth) + "" +getTwoLength(currentDay);
    //textField �� �迭 �ΰ�� �迭ó��.
    if (textField.length > 1) {    
        textFieldObject = textField[txtIndex];  //eval("document.all."+textFieldName);
    } else {
        textFieldObject = textField;       
    }
    
    divObject = cal_Div;//div;

    selectedYMD = textFieldObject.value;
    
    if(isSeperator) {
        selectedYMD = selectedYMD.replace(SEPERATOR,"");   
        selectedYMD = selectedYMD.replace(SEPERATOR,"");   
    }
    

    if(selectedYMD.length != 8 || selectedYMD == "99991231") {
        resetDate( getStr(currentYMD,1), getStr(currentYMD, 2),tmin);
    }else {
        resetDate( getStr(selectedYMD,1), getStr(selectedYMD, 2),tmin);
    }
    
    //�޷�â ��ġ ���ϱ�
    
    //������ ����, ��ũ���� �Ǿ��ٸ� �׸�ŭ ���Ѵ�. (document.body.clientWidth : ȭ�鿡 ���̴� �����)
    var browserWidth = document.body.clientWidth + document.body.scrollLeft;
    //������ ����, ��ũ���� �Ǿ��ٸ� �׸�ŭ ���Ѵ�. (document.body.browserHeight : ȭ�鿡 ���̴� �����)
    var browserHeight = document.body.clientHeight + document.body.scrollTop;  
    //textFieldObject ��ü�� ��ġ ������
    var point = getObjectXY(textFieldObject);
    var divLeft = point.x;
    var divTop  = point.y + textFieldObject.offsetHeight;  //�ؽ�Ʈ �ڽ� ���̸�ŭ ���Ѵ�.(�ؽ�Ʈ �ڽ� ������ ���̰� �ϱ� ����)

    if (divLeft < document.body.scrollLeft) {
        //textFieldObject ��ü�� �������� ������ �ʴ� ���� ��ġ�� ��
        divLeft = document.body.scrollLeft;
    } else if ((divLeft + frameWidth) > browserWidth) { 
        //textFieldObject ��ü�� ���������� ������ �ʴ� ���� ��ġ�� ��
        divLeft = browserWidth - frameWidth;         // ����������� �ణ �� ũ��. �׷��� 10�� �� ����.
    }
    
    if ((divTop + frameHeight ) > browserHeight) {
        divTop = browserHeight - frameHeight;
    }
    
    with(divObject.style){
        left = divLeft;
        top  = divTop;
    }
    
    toggleCalendar();
}

/*******************************************************************
*    ���� �Էµ� �ؽ�Ʈ �ڽ��� ��ǥ�� ���Ѵ�.
*******************************************************************/
function getObjectXY(aTag){
    var oTmp = aTag;
    var pt = new Point(0,0);
    do {
        pt.x += oTmp.offsetLeft;
        pt.y += oTmp.offsetTop;
        oTmp = oTmp.offsetParent;
    } while(oTmp.tagName!="BODY");
    return pt;
}

/**********************************
*    ��ǥ�� ��Ÿ���� ���� ��ü
**********************************/
function Point(iX, iY){
    this.x = iX;
    this.y = iY;
}


function nextYear(){
    resetDate(Number(nowYear) + 1, Number(nowMonth));
}
function prevYear() {
    resetDate(Number(nowYear) - 1, Number(nowMonth));
}
function nextMonth() {
    if(Number(nowMonth) == 12) {
        resetDate(Number(nowYear)+1 , 1);
    }else {
        resetDate(Number(nowYear) , Number(nowMonth)+1);
    }
}
function prevMonth() {
    if(Number(nowMonth) == 1) {
        resetDate(Number(nowYear)-1 , 12);
    }else {
        resetDate(Number(nowYear) , Number(nowMonth)-1);
    }
}
function getcal(param,tmin) {
	
 	 CalendarStart(param, 0, 2000, null, '-',tmin);
}

var frameWidth = 200;
var frameHeight=215;

document.write("<div id=cal_Div style='position:absolute;display:none;z-index:99'><iframe id='calIframe' frameborder='0' scrolling='no' width='" + frameWidth+ "' height='" + frameHeight+ "'></iframe></div>");
/*
//  iframe ���� ����Ǹ鼭 ����� ����
//  1. ���� div �ȿ� iframe �±׸� ����
//  2. iframe �ȿ��� �߻��ϴ� ��ũ��Ʈ �Լ��� parent �� ����
//  3. resetDate(year, month) �Լ��� ���� �߰�(iframe �� html �±׸� write ��) 
*/	