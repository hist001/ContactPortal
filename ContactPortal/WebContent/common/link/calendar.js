
// ���ڼ��� ���׸� â Calendar�� - ��/�� �̵� JS
function RefreshCalendar(tfield, base, max, min, idx){
	idx1 = document.all.YearList.selectedIndex
	idx2 = document.all.MonthList.selectedIndex
	base = document.all.YearList.options[idx1].value + "-"+ document.all.MonthList.options[idx2].value +"-01";
	document.location.href = "/common/calendar.jsp?field=" + tfield +"&min="+ min + "&max="+ max +"&base="+ base +"&idx="+idx;
}

// ���ڼ��� ���׸� â �ڿ����� Ÿ�� ��ȿ�� �˻� Calendar�� - ��/�� �̵� JS by ��ٽ�
function RefreshCalendarTarget(){
	idx1 = document.all.YearList.selectedIndex
	idx2 = document.all.MonthList.selectedIndex
	base = document.all.YearList.options[idx1].value + "-"+ document.all.MonthList.options[idx2].value +"-01";
	document.location.href = dbpath + "/calendarTarget.jsp?field=" + fieldname +"&min="+ mindate + "&max="+ maxdate +"&base="+ base;
}

// �������� �����޴��� - ��/�� �̵� JS
function RefreshCalendar2(){
	idx1 = document.all.YearList.selectedIndex
	idx2 = document.all.MonthList.selectedIndex
	base = document.all.YearList.options[idx1].value + "-"+ document.all.MonthList.options[idx2].value +"-01";
	document.location.href = "/common/calendar,jsp?field=" + fieldname +"&min="+ mindate + "&max="+ maxdate +"&base="+ base+onlineMenu2;
}

// �ش������� ��������ǥ�� �Լ�
function disLUNDate(y,m,d){
	document.all.LUNDate.innerText = SOL2LUN(y,m,d);
}

// ǥ�õ� ���� ���ߴ� �Լ�
function hideLUNDate(){
	document.all.LUNDate.innerHTML = "&nbsp;";
}

//�Խ��ǿ��� ����ϴ� JS
//base�� �������� ��������� ������ ����Ѵ�.
function openCalendar(tfield, base, max, min) {
     CalPath =  "/common/calendar.jsp?field=" + tfield + "&base=" + base + "&max=" + max + "&min=" + min;
     openW=window.open(CalPath,"","width=200,Height=220,location=center,left=300,top=200");
}
//tfield�� �ݺ��Ǵ� ��� index�� �Է¹޾� ó���ϴ� �Լ�
function openCalendar2(tfield, base, max, min, idx) {
     CalPath =  "/common/calendar.jsp?field=" + tfield + "&base=" + base + "&max=" + max + "&min=" + min +"&idx="+idx;

     openW=window.open(CalPath,"","width=200,Height=220,location=center,left=300,top=200");
}
//function openCalendar(tfield, max,min) {
//     CalPath =  "/common/calendar.jsp?field=" + tfield + "&base=" + document.all[tfield].value + "&max=" + max + "&min=" + min;
//     openW=window.open(CalPath,"","width=200,Height=220,location=center,left=300,top=200");
//}
//�Խ��ǿ��� ����ϴ� JS
function openCalendarAppro(tfield, max,min) {
     CalPath =  "/common/calendarAppro.jsp?field=" + tfield + "&base=" + document.all[tfield].value + "&max=" + max + "&min=" + min;
     openW=window.open(CalPath,"","width=200,Height=220,location=center,left=300,top=200");
}

//�ڿ������� Ÿ���ʵ��� ��¥ ��ȿ�� �˻�� JS - ��ٽ� 2003-06-23
function openCalendarTarget(tfield, max,min) {
     CalPath =  "/common/calendarTarget.jsp?field=" + tfield + "&base=" + document.all[tfield].value + "&max=" + max + "&min=" + min;
     openW=window.open(CalPath,"","width=200,Height=220,location=center,left=300,top=200");
}

// �Խ��ǿ��� �Խø������� ���� ��¥ �������� �������� ���ϵ��� ó��
//2003 /6 ���������� : ������ �ִ�����, �ּ����� ���̸� ���ð����ϴٷ� ��.
function ReturnDate(tnew,tmin,tmax,tfield) {
	if (tmax == "") {
		if (tnew >= tmin) {
			self.opener.document.all[tfield].value= tnew ;	
			self.opener.document.all[tfield].innerText= tnew ;	
			window.close();
		}else {
			alert("������ ��¥�� ��ȿ���� �ʽ��ϴ�.")
		}

	}
	else { if (tnew >= tmin && tnew <= tmax) {
			self.opener.document.all[tfield].value= tnew ;	
			self.opener.document.all[tfield].innerText= tnew ;	
			window.close();
		}  else {
			alert("������ ��¥�� ��ȿ���� �ʽ��ϴ�.")
//			self.opener.document.all[tfield].value= tnew ;	
//			self.opener.document.all[tfield].innerText= tnew ;	
//			window.close();
		}
	}
}
//�ݺ��Ǿ����� tfield�� ó���ϴ� �Լ�
function ReturnDate2(tnew,tmin,tmax,tfield,idx) {
	if (tmax == "") {
		if (tnew >= tmin) {
			self.opener.document.all[tfield][parseInt(idx)].value= tnew ;	
			self.opener.document.all[tfield][parseInt(idx)].innerText= tnew ;	
			window.close();
		}else {
			alert("������ ��¥�� ��ȿ���� �ʽ��ϴ�.")
		}

	}
	else { if (tnew >= tmin && tnew <= tmax) {
			self.opener.document.all[tfield].value= tnew ;	
			self.opener.document.all[tfield].innerText= tnew ;	
			window.close();
		}  else {
			alert("������ ��¥�� ��ȿ���� �ʽ��ϴ�.")
//			self.opener.document.all[tfield].value= tnew ;	
//			self.opener.document.all[tfield].innerText= tnew ;	
//			window.close();
		}
	}
}
//����-�����ݽ�û������ ���
//2003 /6 ���������� : ������ �ִ�����, �ּ����� ���̸� ���ð����ϴٷ� ��.
function ReturnDateAppro(tnew,tmin,tmax,tfield) {
	//2003.09.09 ������ ����(���μ� �븮 ��û) - �������� ���� ����
	if (tmax == "") {
//		if (tnew >= tmin) {
			self.opener.document.all[tfield].value= tnew ;	
			self.opener.document.all[tfield].innerText= tnew ;	
			kyungjodate2(tnew);
			window.close();
//		}else {
//			alert("������ ��¥�� ��ȿ���� �ʽ��ϴ�.")
//		}

	} else {
		if (tnew >= tmin && tnew <= tmax) {
			self.opener.document.all[tfield].value= tnew ;	
			self.opener.document.all[tfield].innerText= tnew ;	
			kyungjodate2(tnew);
			window.close();
		} else {
			alert("������ ��¥�� ��ȿ���� �ʽ��ϴ�.")
		}
	}
}


// �Խ��ǿ��� �Խø������� ���� ��¥ �������� �������� ���ϵ��� ó��
//2003 /6 ��ٽ� �߰� : ������ �ִ�����, �ּ����� ���̸� ���ð����ϴٷ� ��.
function ReturnDateTarget(tnew,tmin,tmax,tfield) {
	var thisForm = document.all;
	var openForm = self.opener.document.all;
	var startDate = openForm.ScheduleDate.value;
	var startHour = openForm.SHour.value;
	var startMin = openForm.SMin.value;

	var endHour = openForm.FSHour.value;
	var endMin = openForm.FSMin.value;
	var arrStart = startDate.split("-");
	var arrEnd = tnew.split("-");

	var sYear = parseInt(arrStart[0], 10);
	var sMonth = parseInt(arrStart[1], 10);
	var sDay = parseInt(arrStart[2], 10);

	var eYear = parseInt(arrEnd[0], 10);
	var eMonth = parseInt(arrEnd[1], 10);
	var eDay = parseInt(arrEnd[2], 10);

	var objStart = new Date(sYear, sMonth, sDay, parseInt(endHour, 10), parseInt(endMin, 10));
	var objEnd = new Date(eYear, eMonth, eDay, parseInt(endHour, 10), parseInt(endMin, 10));

	if(objStart.getTime() > objEnd.getTime()) {
		alert("������ ��¥�� ��ȿ���� �ʽ��ϴ�.");
		return;
	}
	
	////////////////////////////////////////////////////////////////////////////////

	openForm.dayList.value =  "";
	dayListValue = "";
	for(var i = objStart.getTime(); i <= objEnd.getTime(); i+=1000*60*60*24) {
		var objThis = new Date(i);
		var thisYear = objThis.getYear();
		var thisMonth = (objThis.getMonth().toString(10).length == 1) ? "0" + objThis.getMonth() : objThis.getMonth();
		var thisDate = (objThis.getDate().toString(10).length == 1) ? "0" + objThis.getDate() : objThis.getDate();
		
		if(dayListValue.indexOf(thisYear +  "-" + thisMonth) == -1) {
			dayListValue +=  thisYear +  "-" + thisMonth + ":";
			//dayListValue +=  thisYear +  "-" + thisMonth + "-" + thisDate + ":";
		}
	}
	openForm.dayList.value = dayListValue.substr(0, dayListValue.length-1);

	////////////////////////////////////////////////////////////////////////////////

	self.opener.document.all[tfield].value= tnew;
	window.close();
	
}

////////// �ְ����� �Խø������� ���� ��¥ �������� �������� ���ϵ��� ó���ұ�? ����? by jolly 2003-04-03
function formatDate(date) {
	var myYear = date.getYear();
	var myMonth = date.getMonth()+1;
	var myWeekday = date.getDate();
	return (myYear + "-" + setDate(myMonth) + "-" + setDate(myWeekday));
}

//now = "2003-05-05", mode = "s" | "e"
function getStartEndWeek(now, mode) {
	var arrNow = now.split("-");
	
	nowYear = parseInt(arrNow[0], 10);
	nowMonth= parseInt(arrNow[1], 10) - 1;
	nowDay= parseInt(arrNow[2], 10);

	var now = new Date(nowYear, nowMonth, nowDay);
	var nowDayOfWeek = now.getDay();

	if(mode == "s") {
		var weekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek + 1);
		return formatDate(weekStartDate);
	}else {
		var weekEndDate = new Date(nowYear, nowMonth, nowDay + (6 - nowDayOfWeek));
		return formatDate(weekEndDate);
	}
}

function openCalendar_week(tfield, max,min) {
   	CalPath =  "/common/calendar_week.jsp?readForm&field=" + tfield + "&base=" + document.all[tfield].value + "&max=" + max + "&min=" + min;
     openW=window.open(CalPath,"","width=200,Height=220,location=center,left=300,top=200");
}

function getNthWeek(week) {
	var arrWeek = week.split("-");

	var nowYear = parseInt(arrWeek[0], 10);
	var nowMonth= parseInt(arrWeek[1], 10) - 1;
	var nowDay= parseInt(arrWeek[2], 10);

	var now = new Date(nowYear, nowMonth, nowDay);
	var nowFirstDate = new Date(nowYear, nowMonth, 1);

	var nowDayOfWeek = nowFirstDate.getDay();
	
	var sum = nowDay + nowDayOfWeek;
	
	if((sum % 7) > 0 ) 
		return parseInt(sum / 7, 10) + 1;
	else
		return parseInt(sum / 7, 10);
}

function setDate(target) {
	var tmp1 = new String(target);
	return (tmp1.length == 1) ? "0" + tmp1 : tmp1;
}

function ReturnDate_week(tnew, tmin, tmax, tfield) {
	//if(tnew >= tmin) {
		var openForm = self.opener.document.all;

		openForm.WeekStart.value = openForm.WeekStart.innerText = getStartEndWeek(tnew, "s");	
		openForm.WeekEnd.value = openForm.WeekEnd.innerText = getStartEndWeek(tnew, "e");


		var arrWeek = tnew.split("-");

		var nowYear = parseInt(arrWeek[0], 10);
		var nowMonth= parseInt(arrWeek[1], 10);
		var nowDay= parseInt(arrWeek[2], 10);

		var sequence = nowYear + "�� " + setDate(nowMonth) + "�� " + getNthWeek(openForm.WeekStart.value) + "°��";

		openForm.WeekSequence.value = sequence;
		
		window.close();
	//}else {
		//alert("������ ��¥�� ��ȿ���� �ʽ��ϴ�.")
	//}
}

////////////////////////////////////////////////////////////////////////

// ���������� ���ʸ޴�����  ��ü/�������� ���ý� ȣ���ϴ� JS
function openAll(Rurl, Lurl,clickmenu){
	if(clickmenu == onlineMenu)
		return;
	targetview = Lurl;

	if(targetview == "ByDayForShare?openview"){
		resetOtherOnimg(onlineMenu);
		onlineMenu = "allsche";
		onlineMenu2 = "&keyall=ByDayForShare";
	}
	else{
		resetOtherOnimg(onlineMenu);
		onlineMenu = "persche";
		onlineMenu2 = "";
	}
	if(basedate != "")
		parent.content.location.href = Rurl + "&keydate="+basedate;
	else
		parent.content.location.href = Rurl + basedate;
}

// ���������� ���ʸ޴�����  �׷����� ���ý� ȣ���ϴ� JS
function openGroup(url){
	if(onlineMenu == "groupsche")
		return;
	resetOtherOnimg(onlineMenu);
	onlineMenu = "groupsche";
	onlineMenu2 = "&keyall=group";
	
	//targetview = "GroupSchedule?openview";

	parent.content.location.href = url
}

function moveMonth(url){
	document.location.href = url;
}

function gotoToday(url){
	document.location.href = url;
		parent.content.location.href = "/emate-k15/todo.nsf/dayform?OpenForm";
}

function openTodo(url){
	if(onlineMenu == "groupsche"){
		alert("���� Ȥ�� ��ü������ ���� ��밡�� �մϴ�.");
		return;
	}
	parent.content.location.href = url;	
	
	pos1 = url.indexOf("keydate=");
	if(pos1 != -1){
		basedate = url.substring(pos1+"keydate=".length,url.length);
	}
}
