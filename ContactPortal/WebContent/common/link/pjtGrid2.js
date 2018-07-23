var rightCostIdx=25;// 추가 2006-08-03
var leftCostIdx=24;// 추가 2006-08-03
var tmpIdx=0;

var startcol=0;
var startrow=0;
var endcol=-1;
var endrow=-1;
var maxIdx=-1;

var mouseDown=false;

var srtGt;
var endGt;

var sumArray; //합계 인덱스 배열
var sumKindArr=new Array("매출계","직접원가계","간접원가계","한계이익","영업이익");
function initGt(){
	srtGt='9999-12-31';
	endGt='1000-01-01';
}
function setGt(){
	srtGt= document.all('pjtStartDt').value;
	endGt=document.all('pjtEndDt').value;
	//alert('srtGt:'+srtGt+" endGt:"+endGt);
}
function setSumIdxs(objArray){
	sumArray=objArray;
}
function setMaxIdx(max){
	maxIdx=max;
}
function setCostIdx(costIdx){
	rightCostIdx+=costIdx;
}
function setCostPeriod(){
	//var reNum = new RegExp("[^0-9]", "g");
	var startDt = document.all('pjtStartDt').value;
	var endDt   = document.all('pjtEndDt').value;
	var alertKey=false;
   	var removeKey=false;

	if(startDt!=''&&endDt!=''){
		if(endDt>=startDt){
			var from_tmp=startDt;
			var to_tmp=endDt;

			//종료일자가 이전자료보다 큰 경우
			if(endGt>'1000-01-01')from_tmp=endGt;
			var fromTime=new Date();
			var toTime=new Date();
			var gapIdx=0;

			//alert("setCostPeriod : 여기까지는 괜찮아 ");

			if(endGt!=endDt){
				fromTime.setFullYear(from_tmp.substring(0,4))
				fromTime.setMonth(from_tmp.substring(5,7)-1);
				fromValue=fromTime.getTime();
				toTime.setFullYear(endDt.substring(0,4))
				toTime.setMonth(endDt.substring(5,7)-1);
				toValue=toTime.getTime();
				time=toValue-fromValue
				gapIdx=Math.round(time/((((1000*60)*60)*24)*30))
				if(gapIdx>30){gapIdx-=1;}//디버깅,개월 계산에서 29이상이면 1이 추가됨.
				if(gapIdx!=0){
					//alert('원가계획기간을 변경합니다.');
					alertKey=true;

					if(gapIdx>0){
						for(var i=((rightCostIdx==25)?0:1);i<=gapIdx;i++){
							addCost(true,from_tmp,(rightCostIdx==25)?true:false);
							//addCost2(true,from_tmp,(rightCostIdx==25)?true:false);
						}
					}else{
						removeKey=true;
						for(var i=gapIdx;i<0;i++)removeCost(true);
					}
				}
			}
			if(srtGt<'9999-12-31'){to_tmp=srtGt;}else{srtGt=startDt;}
			if(srtGt!=startDt){

				fromTime.setFullYear(to_tmp.substring(0,4))
				fromTime.setMonth(to_tmp.substring(5,7)-1);
				fromValue=fromTime.getTime();
				toTime.setFullYear(startDt.substring(0,4))
				toTime.setMonth(startDt.substring(5,7)-1);
				toValue=toTime.getTime();
				time=toValue-fromValue
				gapIdx=Math.round(time/((((1000*60)*60)*24)*30))
				if(gapIdx!=0){
					if(!alertKey){
						//alert('원가계획기간을 변경합니다.');	
						alertKey=true;}			
					if(gapIdx>0){
						removeKey=true;
						for(var i=1;i<=gapIdx;i++)removeCost(false);
					}else{	
						for(var i=gapIdx;i<0;i++){
							addCost(false,to_tmp,false);
							//addCost2(false,to_tmp,false);
						}
					}
				}
			}
			srtGt=startDt;
			endGt=endDt;
			if(removeKey){
				 for(var i=0;i<maxIdx;i++){sumWidth(i);}
				 sumTotal();
			}
		}else{
			alert('수행일자를 올바르게 입력하시오.');
		}
	}

}
/*기간추가*/
function addCost(flag,currentdate,root){
	//alert(flag+"::"+currentdate+"::"+root);
	var idx=(flag)?rightCostIdx-1:leftCostIdx+1;
	var yearDiv=document.all("yearDiv"+idx);
	var acctDiv=document.all("acctDiv"+idx);	   
	var yearVal;
	var monthVal;
	
	//alert("acctDiv.all('year') : " + acctDiv.all("year"));
	
	if(acctDiv.all("year")==null){
		yearVal=currentdate.substring(0,4);
		monthVal=currentdate.substring(5,7);
		flag=true;
	}else{
		yearVal=acctDiv.all("year")[0].value;	
		monthVal=acctDiv.all("month")[0].value;
	}
	var ym;
    if(root){
		ym=shiftDate(yearVal+"-"+monthVal+"-01",0)		
	}else{
		ym=shiftDate(yearVal+"-"+monthVal+"-01",((flag)?1:-1))
	}
	//alert(yearVal+"::"+monthVal+"::"+ym);
	var htmlHd = 
        "<table height=22 align=center cellpadding=1 cellspacing=1 border=0 class=table1 >"+
		"<tr>"+
		"<td class='td1' align='center' width=100 height=19 ><span style=' font-size:9pt;'> "+ym+"</span></td></tr>"+
		"</table>";
	//var htmlSum=
    //    "<table height=22 align=center cellpadding=1 cellspacing=1 border=0 class=table1 >"+
	//	"<tr>"+
	//	"<td class='td2' align='right' width=100 >"+
	//	"<input class='inputText' type=text value='0' name='sumHeight' id='sumHeight"+idx+"' readonly style= 'background-color: #FFFFCC;' >"+
	//    "</td></tr>"+
	//	  "</table>";

	var idx=(flag)?rightCostIdx:leftCostIdx;		
    var acLst=document.all("PjtCost.acctCd");
	var isSum=false;
	var htmlBody = 	
	 
				"<table  align=center cellpadding=1 cellspacing=1 border=0   class=table1 >";
						//alert("sumArray.length:"+sumArray.length);
			for(var i =0;i<maxIdx;i++){
				for(var j=0;j<sumArray.length;j++){
					//alert("sumArray의 값:"+sumArray[j]+":row 숫자="+i);
					if(sumArray[j]==i){								
						isSum=true;
						break;
					}
				}
				htmlBody += "<tr><td class='td2' align='right' width=100 id='cell"+i+"'	"+((!isSum)?"	onmousedown=t_mousedown("+idx+","+i+") onmouseup=t_mouseup("+idx+","+i+")  onmousemove=t_mousemove("+idx+","+i+")":"")+" height=24 style ='background-color:white;'>"+
				"<input class='inputText' "+((isSum)?"readOnly":"")+" type=text value='0' name='amt' onchange='cashObj(this);sumObj("+idx+","+i+");' onPaste=doPaste() "+ 
				" style ='background-color:"+((isSum)?"#FAFAFA":"white")+";' acctVal='"+acLst[i].value+"'>"+
				"<input type='hidden' name='year' value='"+ym.substring(0,4)+"'>"+
				"<input type='hidden' name='month' value='"+ym.substring(6,8)+"'>"+
				"<input type='hidden' name='PjtCostDetail.acctCd' value='"+acLst[i].value+"'>"+
				"<input type='hidden' name='pjtCostDetail_div' value='"+((isSum)?"C":"I")+"'>"+
				"</td></tr>";
				isSum=false;
				}
				htmlBody += 	"</table>";

	document.all("yearDiv"+idx).insertAdjacentHTML('beforeEnd',htmlHd);
	document.all("acctDiv"+idx).insertAdjacentHTML('beforeEnd',htmlBody);
	//document.all("sumDiv"+idx).insertAdjacentHTML('beforeEnd',htmlSum);
	if(flag){rightCostIdx++}
	else{leftCostIdx--}
}
function addCost2(flag,currentdate,root){
	//alert(flag+"::"+currentdate+"::"+root);
	var idx=(flag)?rightCostIdx-1:leftCostIdx+1;
	var yearDivs=document.all("yearDivs"+idx);
	var acctDivs=document.all("acctDivs"+idx);	   
	var yearVal;
	var monthVal;
	
	//alert("acctDivs.all('year') : " + acctDivs.all("year"));
	
	if(acctDivs.all("year")==null){
		yearVal=currentdate.substring(0,4);
		monthVal=currentdate.substring(5,7);
		flag=true;
	}else{
		yearVal=acctDivs.all("year")[0].value;	
		monthVal=acctDivs.all("month")[0].value;
	}
	var ym;
    if(root){
		ym=shiftDate(yearVal+"-"+monthVal+"-01",0)		
	}else{
		ym=shiftDate(yearVal+"-"+monthVal+"-01",((flag)?1:-1))
	}
	//alert(yearVal+"::"+monthVal+"::"+ym);
	var htmlHd = 
        "<table height=22 align=center cellpadding=1 cellspacing=1 border=0 class=table1 >"+
		"<tr>"+
		"<td class='td1' align='center' width=100 height=19 ><span style=' font-size:9pt;'> "+ym+"</span></td></tr>"+
		"</table>";
	//var htmlSum=
    //    "<table height=22 align=center cellpadding=1 cellspacing=1 border=0 class=table1 >"+
	//	"<tr>"+
	//	"<td class='td2' align='right' width=100 >"+
	//	"<input class='inputText' type=text value='0' name='sumHeight' id='sumHeight"+idx+"' readonly style= 'background-color: #FFFFCC;' >"+
	//    "</td></tr>"+
	//	  "</table>";

	var idx=(flag)?rightCostIdx:leftCostIdx;		
    var acLst=document.all("PjtCost.acctCd");
	var isSum=false;
	var htmlBody = 	
	 
				"<table  align=center cellpadding=1 cellspacing=1 border=0   class=table1 >";
						//alert("sumArray.length:"+sumArray.length);
			for(var i =0;i<maxIdx;i++){
				for(var j=0;j<sumArray.length;j++){
					//alert("sumArray의 값:"+sumArray[j]+":row 숫자="+i);
					if(sumArray[j]==i){								
						isSum=true;
						break;
					}
				}
				htmlBody += "<tr><td class='td2' align='right' width=100 id='cell"+i+"'	"+((!isSum)?"	onmousedown=t_mousedown("+idx+","+i+") onmouseup=t_mouseup("+idx+","+i+")  onmousemove=t_mousemove("+idx+","+i+")":"")+" height=24 style ='background-color:white;'>"+
				"<input class='inputText' "+((isSum)?"readOnly":"")+" type=text value='0' name='amt' onchange='cashObj(this);sumObj("+idx+","+i+");' onPaste=doPaste() "+ 
				" style ='background-color:"+((isSum)?"#FAFAFA":"white")+";' acctVal='"+acLst[i].value+"'>"+
				"<input type='hidden' name='year' value='"+ym.substring(0,4)+"'>"+
				"<input type='hidden' name='month' value='"+ym.substring(6,8)+"'>"+
				"<input type='hidden' name='PjtCostDetail.acctCd' value='"+acLst[i].value+"'>"+
				"<input type='hidden' name='pjtCostDetail_div' value='"+((isSum)?"C":"I")+"'>"+
				"</td></tr>";
				isSum=false;
				}
				htmlBody += 	"</table>";

	document.all("yearDivs"+idx).insertAdjacentHTML('beforeEnd',htmlHd);
	document.all("acctDivs"+idx).insertAdjacentHTML('beforeEnd',htmlBody);
	//document.all("sumDiv"+idx).insertAdjacentHTML('beforeEnd',htmlSum);
	if(flag){rightCostIdx++}
	else{leftCostIdx--}
}

function uptCost(acctDiv,cell){
	var cell=acctDiv.all(cell);
    var pjtCostDetail_div=cell.all("pjtCostDetail_div");
	pjtCostDetail_div.value='U';
	//alert('pjtCostDetail_div.value:'+pjtCostDetail_div.value);
	acctDiv.insertAdjacentHTML('beforeEnd',"");
}

function removeCost(flag){
	if(flag){
		removeCostYm(--rightCostIdx);
		//removeCostYm2(--rightCostIdx);
	}else{
		removeCostYm(++leftCostIdx);
		//removeCostYm2(++leftCostIdx);
	}
}
function removeCostYm(idx){

	//alert("idx : " + idx);
	var yearDiv=document.all("yearDiv"+idx);
	var acctDiv=document.all("acctDiv"+idx);	
	//var sumDiv=document.all("sumDiv"+idx);	
	var year=acctDiv.all("year")[0].value;
	var month=acctDiv.all("month")[0].value;
	var pjtCostDetail_div=acctDiv.all("pjtCostDetail_div")[0].value;
	if(pjtCostDetail_div=='C'||pjtCostDetail_div=='U'){
	
	//alert("removeCostYm : 여기는 들어온겨?");
	
		var html="<input type=hidden name=pjtCostDetail_div value=D>"+
				 "<input type=hidden name=year value='"+year+"'>"+
				 "<input type=hidden name='PjtCostDetail.acctCd' value='"+idx+"'>"+
				 "<input type=hidden name=month value='"+month+"'>";
		pjtRemoveDiv.insertAdjacentHTML('beforeEnd',html);
		
	}
	//alert("removeCostYm : 여기는 들어왔다")
	
	yearDiv.innerHTML="";
	acctDiv.innerHTML="";
	//sumDiv.innerHTML="";
}
function removeCostYm2(idx){
	var yearDiv=document.all("yearDivs"+idx);
	var acctDiv=document.all("acctDivs"+idx);	
	//var sumDiv=document.all("sumDiv"+idx);	
	var year=acctDiv.all("year")[0].value;
	var month=acctDiv.all("month")[0].value;
	var pjtCostDetail_div=acctDiv.all("pjtCostDetail_div")[0].value;
	if(pjtCostDetail_div=='C'||pjtCostDetail_div=='U'){
		var html="<input type=hidden name=pjtCostDetail_div value=D>"+
				 "<input type=hidden name=year value='"+year+"'>"+
				 "<input type=hidden name='PjtCostDetail.acctCd' value='"+idx+"'>"+
				 "<input type=hidden name=month value='"+month+"'>";
		pjtRemoveDiv.insertAdjacentHTML('beforeEnd',html);
			}
	yearDiv.innerHTML="";
	acctDiv.innerHTML="";
	//sumDiv.innerHTML="";
}

function t_mousedown(tmpcol,tmprow){
	var frm = document.forms[0];
    
	if(!mouseDown){
		for(var i=startcol;i<=endcol;i++){
			for(var j=startrow;j<=endrow;j++){
				rows=frm.all("acctDiv"+i);
				cell=rows.all("cell"+j);
				if(cell!=undefined)cell.style.backgroundColor='#ffffff';
			
			}

		}
		rows=frm.all("acctDiv"+tmprow);
		startcol=tmpcol;
		startrow=tmprow;
		endcol=tmpcol;
		endrow=tmprow;
		mouseDown=true;
	}
	return true;

}
function t_mousemove(tmpcol,tmprow){
	//event.returnValue=false;
	var frm = document.forms[0];
	if(mouseDown){
		for(var i=startcol;i<=endcol;i++){
			for(var j=startrow;j<=endrow;j++){
				rows=frm.all("acctDiv"+i);
				cell=rows.all("cell"+j);
				if(cell!=undefined)cell.style.backgroundColor='#ffffff';
				
			}
		}
		endcol=tmpcol;
		endrow=tmprow;
		for(var i=startcol;i<=endcol;i++){
			for(var j=startrow;j<=endrow;j++){
				rows=frm.all("acctDiv"+i);
				cell=rows.all("cell"+j);
				if(cell!=undefined)cell.style.backgroundColor='#D0FED0';

			}

		}
	}
	return true;

}
function t_mouseup(tmpcol,tmprow){
	var frm = document.forms[0];
	mouseDown=false;
	endcol=tmpcol;
	endrow=tmprow;

	var rows;
	var cell;

	for(var i=startcol;i<=endcol;i++){
		for(var j=startrow;j<=endrow;j++){
			rows=frm.all("acctDiv"+i);
			cell=rows.all("cell"+j);
			cell.style.backgroundColor='#D0FED0';
		}
	}
	return true;

}


function init(){
	//window.event.returnValue=false; 
}
function sumObj(wIdx,hIdx){

	sumWidth(hIdx);
	sumHeight(wIdx);
	sumTotal();

}

function sumWidth(hIdx){
	var reNum = new RegExp("[^0-9.-]", "g");
	var rows;
	var amts;
	var cell;
	var input;
	var total=0;

    for(var i=leftCostIdx+1;i<rightCostIdx;i++){
		rows=document.all("acctDiv"+i);
		cell=rows.all("cell"+hIdx);
		input=cell.all("amt");
		total+=Number(input.value.replace(reNum, ''));
	}
	var sum=document.all("sumWidth"+hIdx);
	sum.value=cashReturn(total);

}

function sumHeight(wIdx){
	var reNum = new RegExp("[^0-9.-]", "g");
	var rows;
	var amts;
	rows=document.all("acctDiv"+wIdx);

	amts=rows.all("amt");
	var total=0;	
    var directSum=0;
    var profit=0;
	for(var i=0;i<amts.length;i++){
		if(isNaN(amts[i].acctVal)){
            if(amts[i].acctVal=='직접원가계'){
				amts[i].value=cashReturn(total);
				directSum=total;
			}else if(amts[i].acctVal=='간접원가계'){
				amts[i].value=cashReturn(total-directSum);
			}else if(amts[i].acctVal=='한계이익'||amts[i].acctVal=='영업이익'){
				amts[i].value=cashReturn(profit-total);
			}else if(amts[i].acctVal=='매출계'){
				amts[i].value=cashReturn(profit);
			}
		}else{
			if(Number(amts[i].acctVal)%40000<10000){//매출액,용역,물품
				profit+=Number(amts[i].value.replace(reNum, ''));
			}
			else{
				total+=Number(amts[i].value.replace(reNum, ''));
			}
		}
	}
	
	//sum=document.all("sumDiv"+wIdx);		
	//sum.all("sumHeight").value=cashReturn(total);

}
function sumWidthTotal(){	
	/*
	var reNum = new RegExp("[^0-9.-]", "g");
	var rows;
	var amts;
	var cell;
	var input;
	var total=0;
    //alert(sumArray.length);
	//기간이 40개월 넘어가면 생략
	if(rightCostIdx<65){

    for(var j=0;j<sumArray.length;j++){
		total=0;
		for(var i=leftCostIdx+1;i<rightCostIdx;i++){
			rows=document.all("acctDiv"+i);
			cell=rows.all("cell"+sumArray[j]);
			input=cell.all("amt");
			total+=Number(input.value.replace(reNum, ''));
		}
		var sum=document.all("sumWidth"+sumArray[j]);
		sum.value=cashReturn(total);
	}

	}
	*/
	//
	var reNum = new RegExp("[^0-9.-]", "g");
	var rows;
	var amts;
	//rows=document.all("acctDiv"+wIdx);

	amts=document.all("sumWidth");
	var total=0;	
    var directSum=0;
    var profit=Number(amts[0].value.replace(reNum, ''));
	for(var i=1;i<amts.length;i++){		
		if(isNaN(amts[i].acctVal)){
            if(amts[i].acctVal=='직접원가계'){
				amts[i].value=cashReturn(total);
				directSum=total;
			}else if(amts[i].acctVal=='간접원가계'){
				amts[i].value=cashReturn(total-directSum);
			}else if(amts[i].acctVal=='한계이익'||amts[i].acctVal=='영업이익'){
				amts[i].value=cashReturn(profit-total);
			}else if(amts[i].acctVal=='매출계'){
				amts[i].value=cashReturn(profit);
			}
		}else{
			if(Number(amts[i].acctVal)%40000<10000){//매출액,용역,물품
				profit+=Number(amts[i].value.replace(reNum, ''));
			}
			else{
				total+=Number(amts[i].value.replace(reNum, ''));
			}
		}
	}
	
	//sum=document.all("sumDiv"+wIdx);		
	//sum.all("sumHeight").value=cashReturn(total);


}

function sumTotal(){
	sumWidthTotal();
	//var reNum = new RegExp("[^0-9.-]", "g");
	//var amts=document.all("sumHeight");	
	//var total=0;
	//for(var i=0;i<amts.length;i++){
	//	total+=Number(amts[i].value.replace(reNum, ''));
	//}
	//var sum=document.all("sumTotal");		
	//sum.value=cashReturn(total);
}

function scrollDiv(flag){
 if(flag==1){
  document.all.acctDiv.scrollTop = document.all.yearDiv.scrollTop;
  document.all.yearDetailDiv.scrollTop = document.all.yearDiv.scrollTop;
 }else if(flag==2){
  document.all.yearDiv.scrollTop = document.all.acctDiv.scrollTop;
  document.all.yearDetailDiv.scrollTop = document.all.acctDiv.scrollTop;
 }else if(flag==3){
  document.all.acctDiv.scrollTop = document.all.yearDetailDiv.scrollTop;
  document.all.yearDiv.scrollTop = document.all.yearDetailDiv.scrollTop;
 }
 /*
 else if(flag==4){ 	
  document.all.sumDiv.scrollLeft = document.all.itemDiv.scrollLeft;
 }else if(flag==5){ 	
  document.all.itemDiv.scrollLeft = document.all.sumDiv.scrollLeft;
 }
 */

}

function shiftDate(dt,m)
{
	var org_dt = new Date();
	var yy = dt.substr(0, 4);
	var mm = dt.substr(5, 2);
	/*
	org_dt.setYear(yy);
	org_dt.setMonth(mm-1);

	alert("shiftDate::"+dt+"::"+m+"::"+(org_dt.getMonth()+1));
    
	var new_dt = org_dt;
	new_dt.setMonth(org_dt.getMonth() + m);


	var n_yy  = new_dt.getFullYear();
    var n_mm = new_dt.getMonth()+1;
	*/
	//alert("dt::"+dt+":m:"+m+":month:"+(mm-1+m));

	org_dt.setYear(yy);
	org_dt.setMonth((mm-1)+m,'01');
	var n_yy  = org_dt.getFullYear();
    var n_mm  = org_dt.getMonth()+1;

    if (("" + n_mm).length == 1) 	{ n_mm = "0" + n_mm; 	}

	return n_yy+"년 "+n_mm+"월";
}
function regDetail(idx){
  tmpIdx=idx;		
  window.open('/project/pjtCostRgDetail.html', '상세내역등록', 'width=500,height=250,toolbar=no,menubar=no,location=no,status=no,scrollbars=no' );
}  

//셀에서 내용복사 이벤트처리(Ctrl+C)
function doCopy() {
   // alert('doCopy');
	event.returnValue=false;
	var frm = document.forms[0];
	var rows;
	var cell;
	var input;
	var txt="";

	for(var i=startrow;i<=endrow;i++){
		for(var j=startcol;j<=endcol;j++){
			rows=frm.all("acctDiv"+j);
			cell=rows.all("cell"+i);
			input =cell.all("amt");
			txt+=input.value+((j==endcol)?'\n':'\t');			

		}

	}
	//alert(txt);
	window.clipboardData.setData("Text",txt);	
}
function doPaste(){
	var frm = document.forms[0];
	event.returnValue=false;
	var clipboardText=window.clipboardData.getData("Text");
	var tmpTxt=clipboardText;
	//alert(tmpTxt);
	/*시작*/
	if(startcol==-1){startcol=0;startrow=0;}//1.

	//alert("startcol : " +startcol + " startrow : " + startrow);

	var tmpChar;
	var colsCnt=0;
	var rowsCnt=0;
	var tmpval;
	
	//alert("시작");
	
	for (i=0;i<clipboardText.length; i++) { 
		tmpChar=clipboardText.charAt(i); 
		if(tmpChar=="\n"){rowsCnt++;}
	}
	tmpval=clipboardText.substring(0,clipboardText.indexOf("\n")+1);
	for(i=0;i<tmpval.length; i++){
		tmpChar=tmpval.charAt(i); 
		if ( (tmpChar=="\t") || (tmpChar=="\n") ) {colsCnt++; }
		
	}
	
	//alert("rowsCnt : " + rowsCnt + " colsCnt : " + colsCnt);
			
	//2.
	var txtStartIdx=-1;
	var rowIIdx=0;
	var rowEIdx=0;
	var reNum = new RegExp("[^0-9.-]", "g");
	var rowMax=startrow+rowsCnt;
	var colMax=startcol+colsCnt;
	
	//alert("rowMax : " + rowMax + " colMax : " + colMax);
	
	rowMax=(rowMax>maxIdx)?maxIdx:rowMax;
	colMax=(colMax>rightCostIdx)?rightCostIdx:colMax;

	//alert("rowMax : " + rowMax + " colMax : " + colMax);

	//for(var i=startrow;i<startrow+rowsCnt;i++){
	//for(var i=startrow;i<maxIdx;i++){
	
	//alert("startrow : " + startrow + " rowMax : " + rowMax );
	for(var i=startrow;i<rowMax;i++){
		rowEIdx=clipboardText.indexOf("\n",rowIIdx+1);
		tmpTxt=clipboardText.substring(rowIIdx,
									   clipboardText.indexOf("\n",rowEIdx)+1);									   
		rowIIdx=rowEIdx+1;
		
		//for(var j=startcol;j<startcol+colsCnt;j++){
		//for(var j=startcol;j<rightCostIdx;j++){
		
		//alert("startcol : " + startcol + " colMax : " + colMax );
		
		for(var j=startcol;j<colMax;j++){
			rows=frm.all("acctDiv"+j);
			cell=rows.all("cell"+i);
			input =cell.all("amt");
			
			//alert("rows : " + rows + " cell : " + cell + " input : " + input);
			
			pjtCostDetail_div =cell.all("pjtCostDetail_div");
			
			//alert("pjtCostDetail_div : " + pjtCostDetail_div);
			if(pjtCostDetail_div.value=='C'||pjtCostDetail_div.value=='U'){
				pjtCostDetail_div.value='U';
			}
			
			
			//alert("tmpTxt : " + tmpTxt);
			txtStartIdx=tmpTxt.indexOf("\t");
			
			
			//alert("txtStartIdx : " + txtStartIdx);
			
			if(txtStartIdx==-1)	{txtStartIdx=tmpTxt.indexOf("\n");}
			tmpVal=tmpTxt.substring(0,txtStartIdx);
			
			//alert("tmpVal : " + tmpVal);
			
			input.value=cashReturn(tmpVal.replace(reNum, ''));
			
			//alert("input.value : " + input.value);

			//sumObj(j,i);
			tmpTxt=tmpTxt.substring(txtStartIdx+1,tmpTxt.length);
			
			//alert("tmpTxt : " + tmpTxt);

		}
		//sumTotal();
	}//4.

	//alert("여기까찌");
	for(var i=startrow;i<maxIdx;i++)sumWidthAmt(i);
	for(var j=startcol;j<rightCostIdx;j++)sumHeightAmt(j);
	//sumTotal();
	sumWhTotal();
	sumTotAmt();
	

	/*종료*/ 
			
}
function doTable() {	
	//alert('doTable');
	if(event.ctrlKey){
		if(event.keyCode==22){
			doPaste();
		}else{
			//alert(event.keyCode);
		}
	}else{
		//alert('doTable else');
	}
}

function outTd(obj){
	obj.style.backgroundColor="white";
	obj.style.color="black"; 
}
function overTd(obj){
	obj.style.backgroundColor="#0072BB";
	obj.style.color="#FFFFFF"; 
}

//매출의 자동 계산(월분할)
function autoMonthly(idx){
	var reNum = new RegExp("[^0-9.-]", "g");
	var rows;
	var cell;

	var total=Number((document.all("sumWidth"+idx)).value.replace(reNum, ''));
    var monthlyVal=parseInt(total/(rightCostIdx-(leftCostIdx+1)));   
	//var gap=total-(monthlyVal*(rightCostIdx-(leftCostIdx+1)));
    var gap=total%(rightCostIdx-(leftCostIdx+1));
	var tmp=0;
   
    for(var i=leftCostIdx+1;i<rightCostIdx;i++){
		rows=document.all("acctDiv"+i);
		cell=rows.all("cell"+idx);
		input=cell.all("amt");
		input.value=cashReturn(monthlyVal);
		sumHeight(i);
		tmp+=monthlyVal;
		//uptCost(acctDiv,cell)

		(cell.all("pjtCostDetail_div")).value='U';
		//alert('pjtCostDetail_div.value:'+pjtCostDetail_div.value);
		rows.insertAdjacentHTML('beforeEnd',"");
	}  
	input.value=cashReturn(monthlyVal+gap);
	sumHeight(rightCostIdx-1);
	sumTotal();

	//alert("total:"+total+" tmp:"+tmp+" gap:"+gap);

}

