function openDate(tfield,todate,tmpDiv){

	tmp=tmpDiv;	
	var tmpDt=tmp.all(tfield).value;
	openCalendarD(tfield,(tmpDt=='')?todate:tmpDt,'','');
}
function setDate(tnew,tmin,tmax,tfield){

   var checkDtLst=new Array(
		'pjtStartDt','pjtEndDt'
		//'contStartDt','contEndDt'//Project
		);
   var modifyPeriodKey=false;
   for(var i=0;i<checkDtLst.length;i++){
	   if(tfield==checkDtLst[i]){
		   modifyPeriodKey=true;
		   break;
	   }
   }
   
   tmp.all(tfield).value=tnew;
   if(modifyPeriodKey){	
	   //setCostPeriod();	
	   uptPjtDt('pjtStartDt','contStartDt');
	   uptPjtDt('pjtEndDt','contEndDt');
   }
   
}
function uptPjtDt(srcDt,ojbDt){
	var ojb=document.all(ojbDt);
	if(ojb!=undefined)ojb.value=(document.all(srcDt)).value;
}
function srchPjtEmp(prcsEmpManIdx){
  currPrcsEmpManIdx=prcsEmpManIdx;
  var pjtEmpNameColl=document.all.item('pjtEmpName');
  var pjtJobColl=document.all.item('pjtJob');
  var pjtEmpTypeColl=document.all.item('pjtEmpType');
  var pjtEmp_noColl=document.all.item('PjtEmp.pjtEmp_no');
  var empType='';
  var pjtEmp_tmp=
  "<tr>"+
	"<td width=50% class='td2' align='center' height=24 colspan=3>"+
	"������Ʈ�� ��ϵ� �����ο��� �����ϴ�.</td>"+
  "</tr>";
  pjtEmp_List =
  "<table width=100%  align=center cellpadding=1 cellspacing=1 border=0 class=table1>";
  if(pjtEmpNameColl==undefined){
	  pjtEmp_List+=pjtEmp_tmp;		
  }else{
	  if(pjtEmpNameColl.length==undefined){
			if(pjtEmpNameColl.value!=''){
			    if(pjtEmpTypeColl.value=='F'){empType='�������';}
				else if(pjtEmpTypeColl.value=='O'){empType='���־�ü';}
				else if(pjtEmpTypeColl.value=='I'){empType='�系����';}			
				pjtEmp_List+=
				"<tr style ='cursor:hand;' onclick=javascript:openDetail('"+pjtEmpNameColl.value+"','"+pjtJobColl.value+"','"+pjtEmpTypeColl.value+"','"+pjtEmp_noColl.value+"')>"+
					"<td width=20% class='td2' align='center' height=24>"+
					empType
					+"</td>"+
					"<td width=50% class='td2' align='center' height=24>"+
					pjtEmpNameColl.value+"</td>"+
					"<td width=30% class='td2' align='center' height=24>"+
					pjtJobColl.value+"</td>"+			
				"</tr>";
			}else{	  pjtEmp_List+=pjtEmp_tmp;		}
	  }else{
	      var isNone=true;
		  for (i=0;i< pjtEmpNameColl.length;i++){   
			if(pjtEmpNameColl[i].value!=''){
			    if(pjtEmpTypeColl[i].value=='F'){empType='�������';}
				else if(pjtEmpTypeColl[i].value=='O'){empType='���־�ü';}
				else if(pjtEmpTypeColl[i].value=='I'){empType='�系����';}			
				pjtEmp_List+=
				"<tr style ='cursor:hand;' onclick=\"javascript:openDetail('"+pjtEmpNameColl[i].value+"','"+pjtJobColl[i].value+"','"+pjtEmpTypeColl[i].value+"','"+pjtEmp_noColl[i].value+"')\">"+
					"<td width=20% class='td2' align='center' height=24>"+
					empType
					+"</td>"+
					"<td width=50% class='td2' align='center' height=24>"+
					pjtEmpNameColl[i].value+"</td>"+
					"<td width=30% class='td2' align='center' height=24>"+
					pjtJobColl[i].value+"</td>"+			
				"</tr>";
				isNone=false;
			}
		  }
		  if(isNone){pjtEmp_List+=pjtEmp_tmp;}
	  }
  }
  pjtEmp_List+="</table>";
  window.open('/project/srchPjtEmp.html', '������Ʈ�����ο�', 'width=500,height=300,toolbar=no,menubar=no,location=no,status=no,scrollbars=no' );

}

function setPjtPrcsEmp(pjtEmpName,pjtJob,pjtEmpType,pjtEmp_no){
    var obj_name=document.all("pjtPrcsEmp_name");
    var obj_job=document.all("pjtPrcsEmp_job");
    var obj_div=document.all("pjtPrcsEmp_div");

    var obj_no=document.all("PjtPrcsEmp.pjtEmp_no");

	if(obj_name.length==undefined){
		obj_name.value=pjtEmpName;
		obj_job.value=pjtJob;
		obj_no.value=pjtEmp_no;
	}else{
	    for(var i=0;i<obj_name.length;i++){
			if(obj_name[i].id==currPrcsEmpManIdx){
				obj_name[i].value=pjtEmpName;
				obj_job[i].value=pjtJob;
				obj_no[i].value=pjtEmp_no;
			}
		}
	}

}
function winClose(){
	self.close();
}


/*���� ÷�� ��� �߰� */
 function addFile_Pjt(){
	var html = 	"<div id='fileDiv"+fileIdx+"'>"+	
				"<input type='hidden' name='pjtFiles_no'  value='"+fileIdx+"'>"+
				"<input type='hidden' name='delFlag' value='N'>"+
	"&nbsp;<select name=docType>"+
	"<option value='E'>�����ڷ�</option>"+
	"<option value='G'>����ڷ�</option>"+
	"<option value='D'>�����ڷ�</option>"+
		

	//"<option value='E'>���ü�Ẹ��</option>"+
	//"<option value='F'>���ں�����ȹ��</option>"+
	//"<option value='D'>�ϷẸ��</option>"+
	//"<option value='G'>��༭</option>"+	
	//"<option value='Z'>��Ÿ</option>"+
	"</select>"+
	"&nbsp;<input name='file"+fileIdx+"'  style='width:60%;  background-color:#FFFFFF;text-align:left;ime-mode:active; '  size='20' type='file' id='files'>"+
	"<input type='hidden' name='pjtFiles_div' value='I'>"+
		"&nbsp;&nbsp;&nbsp;<input type=button value='����'  onclick=removeFile(fileDiv"+fileIdx+")></div>";
	fileIdx++;
	fileSpan.insertAdjacentHTML('afterBegin',html);
}
function removeFile(fileDiv){
	fileDiv.outerHTML=""; 
}
function updateFiles_Pjt(obj,fileDiv){	

	var delFlag=fileDiv.all["delFlag"];
	var pjtFiles_div=fileDiv.all["pjtFiles_div"];
	if(obj.checked==true){
		delFlag.value='Y';
		pjtFiles_div.value='D'
	}else{
		delFlag.value='N';
		pjtFiles_div.value='C'
	}
}
/*���ϴٿ�ε�*/
function download(dir,fileSystemName,fileOriginName){
	location.href=	"downLoadFiles.do?dir="+dir+"&fileSystemName="+fileSystemName+"&fileOriginName="+fileOriginName;			
}
/*������Ʈ ���� �ο�*/
function addMan(currentdate){
  
	var html = 	"<div id='manDiv"+manIdx+"'>"+

	<!--�޴� �߰�-->

   "<table width=100%  align=center cellpadding=1 cellspacing=1 border=0 class=table1>"+
   "<tr><td width=95% class='td2'>"+
/*
   "<table width=100%  align=center cellpadding=1 cellspacing=1 border=0 >"+
		"<tr>"+
			"<td width=20% class='td1' align='left' height=24>"+
			"����</td>"+
			"<td width=15% class='td1' align='left' height=24>"+
			"�������</td>"+
			"<td width=20% class='td1' align='left' height=24>"+
			"����Ⱓ</td>"+
			"<td width=45% class='td1' align='left' height=24>"+
			"�󼼳���"+
			"</td>"+		
	    "</tr>"+


	"</table>"+
	//"<tr><td colspan=4>"+
*/
	<!--�޴� ��-->
		        "<table align=center cellpadding=1 cellspacing=1 border=0 width=100%  >"+
		        "<tr>"+
				"<td align='center' width=20% height=26 class=TD2><input  name='pjtEmpName'  style= 'width:75%;  text-align:left; background-color: #FFFFFF' size='10' id='pjtEmpName"+manIdx+"'>"+		
				"<input type='button' value='�˻�'onClick=popupSearch('emp','referCd"+manIdx+"','','pjtEmpName"+manIdx+"')>"+
				"<input type='hidden' name='referCd' id='referCd"+manIdx+"'>"+
				"</td>"+
				"<td  width=15% height=24 class=TD2 align='center'>"+
			    "<input  name='pjtJob' style= 'width:99%;  text-align:left; background-color: #FFFFFF' size='10'>"+
				"</td>"+				
				"<td  width=20% height=24 class=TD2 align='center'><input name='pjtEmpStartDt'   style= 'width:30%;  text-align:left; background-color: #FFFFFF' size='20'>&nbsp;"+
				"<a href=javascript:openDate('pjtEmpStartDt','"+currentdate+"',manDiv"+manIdx+")><img src='../images/navi_08_icon.gif' border=0 style='vertical-align:middle'></a>~<input name='pjtEmpEndDt'   style= 'width:30%;  text-align:left; background-color: #FFFFFF' size='20'>&nbsp;"+
				"<a href=javascript:openDate('pjtEmpEndDt','"+currentdate+"',manDiv"+manIdx+")><img src='../images/navi_08_icon.gif' border=0 style='vertical-align:middle'></a>"+
				"&nbsp;</td>"+
				"<td  width=45% height=24 class=TD2 colspan=2>"+
				"<input name='pjtEmpDetail'   style= 'width:98%;  text-align:left; background-color: #FFFFFF' size='3' value=''>"+
				"&nbsp;"+				

				"</td></tr>"+
/*
		"<tr>"+
			"<td width=35% class='td1' align='left' height=24 colspan=2>"+
			"���㱸��</td>"+
			"<td width=20% class='td1' align='left' height=24>"+
			"������</td>"+
			"<td width=23% class='td1' align='left' height=24>"+
			"�������ΰǺ�"+
			"</td>"+	
			"<td width=22% class='td1' align='left' height=24>"+
			"�������Ļ���"+
			"</td>"+								

	    "</tr>"+
*/

				"<tr>"+
				"<td colspan=2 height=26 align=center >"+
				"����<input type='radio' name='reportR"+manIdx+"' class=Attach checked  onclick=empRChg(manDiv"+manIdx+",'Y')>&nbsp;������<input type='radio' name='reportR"+manIdx+"' class=Attach onclick=empRChg(manDiv"+manIdx+",'N')>"+
				"&nbsp;&nbsp;"+			
				"������&nbsp;<input name='pjtPortion'   style= 'width:50;  text-align:right; background-color: #FFFFFF' size='3' readonly value='100'>%</td>"+
				"<td align=center ><select name='techGrade'>"+ 
				"<option value=''><----�����ϼ���----></option>"+
				"<option value='A'>�ʱ�</option>"+
				"<option value='B'>�߱�</option>"+
				"<option value='C'>���</option>"+
				"<option value='D'>Ư��</option>"+
				"</select>&nbsp;&nbsp;</td>"+
				"<td ><input name='mthLabCost'   onchange='cashObj(this)' style= 'width:150;  text-align:right; background-color: #FFFFFF' size='3'  value=''>��</td>"+
				"<td ><input name='mthEmpBeneft'   onchange='cashObj(this)' style= 'width:150;  text-align:right; background-color: #FFFFFF' size='3'  value=''>��&nbsp;"+
				"<input type='hidden' name='pjtEmp_div' value='I'>"+
				"<input type='hidden' name='pjtEmpType' value='I'>"+
				"<input type='hidden' name='fullExecYN' value='Y'>"+
				"<input type='hidden' name='PjtEmp.pjtEmp_no' value='"+manIdx+"'>"+
				"</td>"+
				"</tr>"+
				"</table>"+
				
				"</td>"+
				"<td align='center' valign=top class='td2'><input type=button value='����'  onclick=removeMan(manDiv"+manIdx+",'"+manIdx+"')>"+
				"</td>"+
				"</tr></table>"+
				"<table border=0><tr height=1><td></td></tr></table>"+
		        "</div>";

	manIdx++;
	manSpan.insertAdjacentHTML('beforeEnd',html);
}
function empRChg(div,value){
	var portion=div.all("pjtPortion");
	
	if(value=='Y'){
		portion.readOnly=true;
		portion.value=100;
		portion.style.backgroundColor="#FfFfFF";
	}else{
		portion.readOnly=false;
		portion.value=0;
		portion.style.backgroundColor="#F0F0FF";
	}
	div.all("fullExecYN").value=value;	

}
function uptMan(manDiv){
	var pjtEmp_div=manDiv.all("pjtEmp_div");
	pjtEmp_div.value='U';	
	manDiv.insertAdjacentHTML('beforeEnd',"");
}
function removeMan(manDiv,pjtEmp_no){		
 

    var obj_no=document.all("PjtPrcsEmp.pjtEmp_no");
    var cnt=0;
	if(obj_no.length==undefined){
		if(obj_no.value==pjtEmp_no){
			eval("removePrcsEmpManDiv(prcsEmpManDiv"+obj_no.id+")");
			cnt++;
		}
	}else{
	    arrayObj=new Array(obj_no.length);
	    for(var i=0;i<obj_no.length;i++){
			if(obj_no[i].value==pjtEmp_no){
			    arrayObj[i]="prcsEmpManDiv"+obj_no[i].id;				
				cnt++;
			}
		}
		for(var i=0;i<arrayObj.length;i++){
			if(arrayObj[i]!=undefined){
				eval("removePrcsEmpManDiv("+arrayObj[i]+")");
			}
		}
		
	}
	var pjtEmp_div=manDiv.all("pjtEmp_div").value;
	if(pjtEmp_div=='C'||pjtEmp_div=='U'){
		var html="<input type=hidden name=pjtEmp_div value=D>"+
				 "<input type=hidden name=PjtEmp.pjtEmp_no value='"+pjtEmp_no+"'>"+
				 "<input type=hidden name=pjtEmpName value='"+pjtEmp_no+"'>";

		pjtRemoveDiv.insertAdjacentHTML('beforeEnd',html);
	}
	manDiv.outerHTML="";


}
function srchPrcs(){
	  window.open('/project/srchPjtPrcs.jsp', '������Ʈ�������', 'width=700,height=500,toolbar=no,menubar=no,location=no,status=no,scrollbars=no' );
}
/*������Ʈ ���� ����*/
function addPrcs(highPrcsCd,prcsCd,prcsName,currentdate){

	var html = 	"<div id='prcsDiv"+prcsIdx+"'>"+
	"<table width=100%  align=center cellpadding=1 cellspacing=1 border=0 class=table1 ><tr><td class='td2'>"+
	"<table width=100%  align=center cellpadding=1 cellspacing=1 border=0  >"+
		"<tr>"+
			"<td width='5%' class='td1' align='left' height='24'>"+
			"No</td>"+
			"<td width=10% class='td1' align='left' height='24'>"+
			"�����ܰ�</td>"+
			"<td width=25% class='td1' align='left' height=24>"+
			"�����Ⱓ</td>"+
			"<td width=50% class='td1' align='left' height=24>"+
			"���γ���"+
			"</td>"+
			
	    "</tr>"+

		"<tr>"+
		"<td  class='td2' align='left' height=24 colspan=6>"+

		        "<table align=center cellpadding=1 cellspacing=1 border=0 width=100% >"+
		        "<tr><td  width=5% height=24 class=TD2 align='center'>"+
			    "<input  name='PjtPrcs.prcsCd' readonly   style= 'width:99%;  text-align:left;font-weight:700; background-color: #FFFFFF' size='10' value='"+prcsCd+"'>"+
				"<input type='hidden' name='highPrcsCd'  value='"+highPrcsCd+"'>"+
				"<input type='hidden' name='pjtPrcs_div'  value='I'>"+
				"</td>"+
				"<td align='center' width=10% height=24 class=TD2><input  name='empView2'   style= 'width:96%; font-weight:700;  text-align:left; background-color: #FFFFFF' size='10' value='"+prcsName+"'>"+		
				"</td>"+	
				"<td  width=25% height=24 class=TD2 align='center'>"+
				"<input name='prcsStartDt' readonly style= 'width:70; height:22;"+ "background-color: #FFFFFF;text-align:center;padding-top:6;"+
				"value='' size='10'>&nbsp;"+
				"<a href=javascript:openDate('prcsStartDt','"+currentdate+"',prcsDiv"+prcsIdx+")><img src='../images/navi_08_icon.gif' border=0 style='vertical-align:middle'></a>&nbsp; ~ "+
				"<input name='prcsEndDt' readonly  style= 'width:70; height:22;background-color: #FFFFFF;text-align:center;padding-top:6;"+
				"value='' size='10'>&nbsp;"+
				"<a href=javascript:openDate('prcsEndDt','"+currentdate+"',prcsDiv"+prcsIdx+")><img src='../images/navi_08_icon.gif' border=0 style='vertical-align:middle'></a> "+
				"</td>"+
				"<td  width=50% height=24 class=TD2><input name='pjtPrcsDetail'   style= 'width:85%;  text-align:left; background-color: #FFFFFF' size='3' >&nbsp;&nbsp;&nbsp;&nbsp;"+				


				"<input type=button value='����'  onclick=removePrcs(prcsDiv"+prcsIdx+")>"+
				"</td></tr></table>"+
			"</td></tr>"+
			"<tr>"+
			"<td  class='td2' align='left' height=24 colspan=6>"+
		<!--���������ο�-->
			"<table width=100%  algn=center cellpadding=1 cellspacing=1 border=0  class=table1>"+
				"<tr>"+
					"<td width=16% class='td3' align='center' height=24 >���������η�</td>"+
					"<td width=70% class='td2' align='left' height=24 >"+

				   "<table width=100%  align=center cellpadding=1 cellspacing=1 border=0 >"+
						"<tr>"+
							"<td width=20% class='td1' align='left' height=24>"+
							"����</td>"+
							"<td width=30% class='td1' align='left' height=24>"+
							"����</td>"+				
							"<td width=50% class='td1' align='left' height=24>"+
							"����Ⱓ"+
							"</td>"+
							
						"</tr>"+
						"<tr>"+
						"<td  class='td2' align='left' height=24 colspan=6>"+
						"<div id='prcsEmpDiv"+prcsIdx+"'>"+

						"</div>"+
						"</td></tr>"+
					"</table>"+

			  "</td>"+
			  "<td width=14% class='td2' align='center' valign=top>"+
			  "<input type='button' value='�����η��߰�' onclick=addPrcsEmp(prcsEmpDiv"+prcsIdx+",'"+prcsCd+"','"+currentdate+"')>"+
			  "</td>"+
			  "</tr>"+
			 "</table>"+
		<!--���������ο� ����-->


			"</td></tr></table>"+
		"<table border=0><tr height=1><td></td></tr></table>"+
		"</td></tr></table>"+
		"<table border=0><tr height=1><td></td></tr></table>"+
		"</div>"
		;

	prcsSpan.insertAdjacentHTML('beforeEnd',html);
	eval("addPrcsEmp(prcsEmpDiv"+prcsIdx+",'"+prcsCd+"','"+currentdate+"')");
	
	prcsIdx++;

}
function uptPrcs(prcsDiv){
	var pjtPrcs_div=prcsDiv.all("pjtPrcs_div");
	pjtPrcs_div.value='U';
	prcsDiv.insertAdjacentHTML('beforeEnd',"");
}
function removePrcs(prcsDiv){
	var pjtPrcs_div=prcsDiv.all("pjtPrcs_div").value;
	var prcsCd=prcsDiv.all("PjtPrcs.prcsCd").value;

	if(pjtPrcs_div=='C'||pjtPrcs_div=='U'){
		var html="<input type=hidden name=pjtPrcs_div value=D>"+
				 "<input type=hidden name=PjtPrcs.prcsCd value='"+prcsDiv.all("PjtPrcs.prcsCd").value+"'>";
		pjtRemoveDiv.insertAdjacentHTML('beforeEnd',html);
	

		/*�����ο� ����*/
		var pjtEmp_no=prcsDiv.all("PjtPrcsEmp.pjtEmp_no");
		var pjtPrcsEmp_div=prcsDiv.all("pjtPrcsEmp_div");

		if(pjtEmp_no.length==undefined){
			 if(pjtPrcsEmp_div.value=='C'||pjtPrcsEmp_div.value=='U'){
			 html="<input type=hidden name=pjtPrcsEmp_div value=D>"+
				 "<input type=hidden name=PjtPrcsEmp.pjtEmp_no value='"+pjtEmp_no.value+"'>"+
				 "<input type=hidden name=PjtPrcsEmp.prcsCd value='"+prcsCd+"'>";							
			 pjtRemoveDiv.insertAdjacentHTML('beforeEnd',html);
			 }
		}else{
			for(var i=0;i<pjtEmp_no.length;i++){
				if(pjtPrcsEmp_div[i].value=='C'||pjtPrcsEmp_div[i].value=='U'){
				 html="<input type=hidden name=pjtPrcsEmp_div value=D>"+
					 "<input type=hidden name=PjtPrcsEmp.pjtEmp_no value='"+pjtEmp_no[i].value+"'>"+
					 "<input type=hidden name=PjtPrcsEmp.prcsCd value='"+prcsCd+"'>";								
				 pjtRemoveDiv.insertAdjacentHTML('beforeEnd',html);
				}
			}
		}
	}
	
	prcsDiv.outerHTML=""; 
}
<!--����-�����η�-->
function addPrcsEmp(prcsEmpDiv,prcsCd,currentdate){
	var html = 	"<div id='prcsEmpManDiv"+prcsEmpManIdx+"'>"+
				"<input type='hidden' name='pjtPrcsEmp_div' value='I'>"+
				"<input type='hidden' name='PjtPrcsEmp.pjtEmp_no' id='"+prcsEmpManIdx+"'  >"+
				"<input type='hidden' name='PjtPrcsEmp.prcsCd' value='"+prcsCd+"'>"+					
		        "<table align=center cellpadding=1 cellspacing=1 border=0 width=100% >"+
		        "<tr><td  width=20% height=24 class=TD2 align='center'>"+
			    "<input  name='pjtPrcsEmp_job' readonly   style= 'width:99%;  text-align:left; background-color: #FFFFFF' size='10'>"+
				"</td>"+
				"<td align='center' width=30% height=24 class=TD2><input  name='pjtPrcsEmp_name' id='"+prcsEmpManIdx+"'  style= 'width:75%;font-weight:700;  text-align:left; background-color: #FFFFFF' size='10'>"+		
				"<input type='button' value='�˻�' onclick=javascript:srchPjtEmp("+prcsEmpManIdx+") >"+
				"<input type='hidden' name='empId'>"+
				"</td>"+					
				"<td  width=50% height=24 class=TD2>"+				
				"<input name='prcsInStartDt' readonly style= 'width:70; height:22;"+ "background-color: #FFFFFF;text-align:center;padding-top:6;"+
				"value='' size='10'>&nbsp;"+
				"<a href=javascript:openDate('prcsInStartDt','"+currentdate+"',prcsEmpManDiv"+prcsEmpManIdx+")><img src='../images/navi_08_icon.gif' border=0 style='vertical-align:middle'></a>&nbsp; ~ "+
				"<input name='prcsInEndDt' readonly  style= 'width:70; height:22;background-color: #FFFFFF;text-align:center;padding-top:6;"+
				"value='' size='10'>&nbsp;"+
				"<a href=javascript:openDate('prcsInEndDt','"+currentdate+"',prcsEmpManDiv"+prcsEmpManIdx+")><img src='../images/navi_08_icon.gif' border=0 style='vertical-align:middle'></a> "+
				"&nbsp;&nbsp;&nbsp;&nbsp;"+				
				"<input type=button value='����'  onclick=removePrcsEmpManDiv(prcsEmpManDiv"+prcsEmpManIdx+")>"
				"</td></tr></table>"+
		        "</div>";

	prcsEmpManIdx++;
	prcsEmpDiv.insertAdjacentHTML('beforeEnd',html);
}
function uptPrcsEmpMan(prcsEmpManDiv){
	var pjtPrcsEmp_div=prcsEmpManDiv.all("pjtPrcsEmp_div");
	pjtPrcsEmp_div.value='U';	
	prcsEmpManDiv.insertAdjacentHTML('beforeEnd',"");
}
function removePrcsEmpManDiv(prcsEmpManDiv){	
	var pjtPrcsEmp_div=prcsEmpManDiv.all("pjtPrcsEmp_div").value;
	if(pjtPrcsEmp_div=='C'||pjtPrcsEmp_div=='U'){
		var html="<input type=hidden name=pjtPrcsEmp_div value=D>"+
				 "<input type=hidden name=PjtPrcsEmp.pjtEmp_no value='"+prcsEmpManDiv.all("PjtPrcsEmp.pjtEmp_no").value+"'>"+
				 "<input type=hidden name=PjtPrcsEmp.prcsCd value='"+prcsEmpManDiv.all("PjtPrcsEmp.prcsCd").value+"'>";
		pjtRemoveDiv.insertAdjacentHTML('beforeEnd',html);
		//alert(html);
	}			
	prcsEmpManDiv.outerHTML="";

}
function uptBiz(idx){
	var pjtBiz_div=document.all("pjtBiz_div");
	if(pjtBiz_div[idx].value=='C'){
		pjtBiz_div[idx].value='U';	
	}
	pjtRemoveDiv.insertAdjacentHTML('beforeEnd',"");
}
function uptEnv(idx){
	var pjtEnv_div=document.all("pjtEnv_div");
	pjtEnv_div[idx].value='U';	
	pjtRemoveDiv.insertAdjacentHTML('beforeEnd',"");
}
/*����*/
function save(status){
	var frm = document.forms[0];
	if(checkSave(frm)){
		frm.apprStatus.value=status;
		frm.submit();
	}
}
/*������Ʈ ���� ����*/
function changePjtType(type,obj,currentdate){	
	//if(obj.checked==false){return;}
	var frm=document.forms[0];
	var html='';
	var color='';
	if(type=='P'||type=='R'||type=='T'){
		if(type=='T'){
			color = '#FFFFFF'
		}else{
			color = '#F0F0FF'
		}
	frm.name='devPjt';
    frm.action='devPjtReg.do';
	frm.contAmt.readOnly=true;
	html=		
		"<table align=center cellpadding=1 cellspacing=1 border=0 class='table1' width=995 id=table1>"+
		"<tr>"+
			"<td width=129 class='td1' align=center height='24'>"+"���뿪�ݾ�"+"</td>"+
			"<td width=336 class=td2 align=left height='24'>"+
				"<input name=servAmt  onchange='cashObj(this);sumAmt();' style='width:80%; height:22; background-color: #FFFFFF;text-align:right;padding-top:6;ime-mode:active;' size=50 maxlength=25 value='0'>&nbsp;��</td>"+
			"<td width=130 class=td1 align=left height=24>"+"��๰ǰ�ݾ�"+"</td>"+
			"<td width=387 class=td2 align=left height=24 colspan=3>"+
				"<input name=goodsAmt  onchange='cashObj(this);sumAmt();' style='width:90%; height:22; background-color: #FFFFFF;text-align:right;padding-top:6;ime-mode:active;' size=50 maxlength=25 value='0'>&nbsp;��</td>"+
		"</tr>"+
		"<tr>"+
			"<td width=129 class=td1 align=center height=24>"+"����������Ʈ"+"</td>"+
			"<td width=336 class=td2 align=left height=24>"+
				"<input name=businessPjtView style='width:80%; height:22; background-color: "+color+";text-align:left;padding-top:6;ime-mode:active;' size=50 maxlength=25 readonly>&nbsp;"+
				"<input type='button' value='�˻�'onClick=popupSearch('businessPjt','businessPjt_no','','businessPjtView')>"+
				"<input name='businessPjt_no' type='hidden' value='' title='����������Ʈ'></td>"+
			"<td width=130 class=td1 align=left height=24>"+"�������"+"</td>"+
			"<td width=166 class=td2 align=center height=24 >"+
				"<select name=excuteType style=background-color: #FFFFFF>"+
				"<option value=ALONE>"+"�ܵ�"+"</option>"+ 
				"<option value=UNION>"+"������������"+"</option>"+
				"<option value=SPLIT>"+"�������޺д�"+"</option>"+
				"</select>"+
			"</td>"+
			"<td width=90 class=td1 align=left height=24>"+"��������"+"</td>"+
			"<td width=125 class=td2 align=center height=24 >"+

				"<select name=profitType style='background-color: #F0F0FF' title='��������'>"+
				"<option value=''>"+"������"+"</option>"+ 
				"<option value='P'>"+"���������"+"</option>"+
				"<option value='S'>"+"�ý����Ǹ�"+"</option>"+
				"<option value='D'>"+"û����ó��"+"</option>"+
				"<option value='N'>"+"������ó��"+"</option>"+
				"</select>"+
			"</td>"+

		"</tr>"+
		"<tr>"+
			"<td width=129 class=td1 align=center height=25>"+"���ں���"+"</td>"+
			"<td width=336 class=td2 align=left height=25>"+
				"(<input name=maTerm style='width:10%; height:22; background-color: #FFFFFF;text-align:left;padding-top:6;ime-mode:active;' size=50 maxlength=25>����&nbsp;"+
				"������"+
				"<input name=warrantyRate style='width:10%; height:22; background-color: #FFFFFF;text-align:left;padding-top:6;ime-mode:active;' size=50 maxlength=25>%)"+
			"</td>"+
			"<td width=130 class=td1 align=left height=25>�������� ������</td>"+
			"<td width=387 class=td2 align=left height=25 colspan=3>"+
				"<input name=quotaPercent style='width:90%; height:22;background-color:#FFFFFF;text-align:left;padding-top:6;ime-mode:active;' size=50 maxlength=25>&nbsp;%"+
			"</td>"+
		"</tr>"+
		"<tr>"+
			"<td width=129 class=td1 align=center height=24>"+"������ȹ"+"</td>"+
			"<td width=336 class=td2 align=left height=24>"+
			"</td>"+
			"<td width=130 class=td1 align=left height=24>"+"��ü���"+"</td>"+
			"<td width=387 class=td2 align=left height=24 colspan=3>(<input name=penaltyRate  onchange='cashObj(this)' style='width:10%; height:22; background-color: #FFFFFF;text-align:left;padding-top:6;ime-mode:active;' size=50 maxlength=25>/1000)</td>"+
		"</tr>"+
		"<tr>"+
			"<td width=129 class=td1 align=center height=24>"+"���߹��"+"</td>"+
			"<td width=859 class=td2 align=left height=24 colspan=5>"+
			   "<input type=hidden name='pjtEnv_div' value=I>"+
			   "<input type=hidden name='pjtEnvModelCd' value=1>"+
				"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;���߹����&nbsp;"+
				"<select name=pjtEnvCd style=background-color: #FFFFFF>"+
				"<option value=3>"+"HIST4FRONT/IE"+"</option>"+
				"<option value=4>"+"HIST4FRONT/CS"+"</option>"+
				"<option value=5>"+"HIST4FRONT/RAD"+"</option>"+
				"<option value=6>"+"Method/1"+"</option>"+
				"<option value=1>"+"������ ���"+"</option>"+
				"<option value=7>"+"��ü���� �����"+"</option>"+
				"<option value=2>"+"CBD �����"+"</option>"+
				"<option value=8>"+"��Ÿ"+"</option>"+
				"</select>"+
			   "<input type=hidden name='pjtEnv_div' value=I>"+
			   "<input type=hidden name='pjtEnvModelCd' value=2>"+
				"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;���ߵ���&nbsp;"+
				"<select name=pjtEnvCd style=background-color: #FFFFFF>"+
				"<option value=4>"+"Visual Basic"+"</option>"+
				"<option value=5>"+"Delphi"+"</option>"+
				"<option value=6>"+"Power Builder"+"</option>"+
				"<option value=7>"+"Developer/2000"+"</option>"+
				"<option value=8>"+"VisualInterDev"+"</option>"+
				"<option value=2>"+"C#"+"</option>"+
				"<option value=3>"+"C++�� Tool"+"</option>"+
				"<option value=1>"+"JAVA�� Tool"+"</option>"+
				"<option value=9>"+"VisualAge Series"+"</option>"+
				"<option value=10>"+"��Ÿ"+"</option>"+

				"</select>"+
			   "<input type=hidden name='pjtEnv_div' value=I>"+
			   "<input type=hidden name='pjtEnvModelCd' value=3>"+
				"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;DB&nbsp;"+
				"<select name=pjtEnvCd style=background-color: #FFFFFF>"+
				"<option value=3>"+"Oracle 7.X"+"</option>"+
				"<option value=1>"+"Oracle 8.X"+"</option>"+
				"<option value=4>"+"Informix"+"</option>"+
				"<option value=2>"+"SQL Server"+"</option>"+
				"<option value=5>"+"DB/2"+"</option>"+
				"<option value=6>"+"��Ÿ"+"</option>"+
				"</select>"+
			   "<input type=hidden name='pjtEnv_div' value=I>"+
			   "<input type=hidden name='pjtEnvModelCd' value=4>"+
				"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�ü��&nbsp;"+
				"<select name=pjtEnvCd style=background-color: #FFFFFF>"+
				"<option value=3>"+"Sun OS"+"</option>"+
				"<option value=1>"+"UNIX �迭"+"</option>"+
				"<option value=2>"+"Window NT"+"</option>"+
				"<option value=4>"+"Linux"+"</option>"+
				"<option value=5>"+"��Ÿ"+"</option>"+
				"</select></td>"+
		"</tr>"+
	"</table>";

	}else if(type=='SV'){
		frm.name='svcPjt';
		frm.action='svcPjtReg.do';
		frm.contAmt.readOnly=false;
		html=
		"<table align=center cellpadding=1 cellspacing=1 border=0 class='table1' width=995 id=table1>"+
		"<tr>"+
			"<td width=129 class=td1 align=center height=24>"+"����������Ʈ"+"</td>"+
			"<td width=336 class=td2 align=left height=24>"+
				"<input name=businessPjtView style='width:80%; height:22; background-color: #FFFFFF;text-align:left;padding-top:6;ime-mode:active;' size=50 maxlength=25>&nbsp;"+
				"<input type='button' value='�˻�'onClick=popupSearch('businessPjt','businessPjt_no','','businessPjtView')>"+
				"<input name='businessPjt_no' type='hidden' value='' title='����������Ʈ'></td>"+
			"<td width=130 class=td1 align=left height=24>"+"�������"+"</td>"+
			"<td width=166 class=td2 align=center height=24 >"+
			"<input name=contDt readonly style= 'width:70; height:22; background-color: #FFFFFF;text-align:center;padding-top:6;' value='' size=10>&nbsp;<a href=javascript:openCalendar('contDt','"+currentdate+"','','')><img src='../images/navi_08_icon.gif' border=0 style='vertical-align:middle'>"+
			"</td>"+
			"<td width=90 class=td1 align=left height=24>"+"��������"+"</td>"+
			"<td width=125 class=td2 align=center height=24 >"+

				"<select name=profitType style='background-color: #F0F0FF' title='��������'>"+
				"<option value=''>"+"������"+"</option>"+ 
				"<option value='P'>"+"���������"+"</option>"+
				"<option value='S'>"+"�ý����Ǹ�"+"</option>"+
				"<option value='D'>"+"û����ó��"+"</option>"+
				"<option value='N'>"+"������ó��"+"</option>"+
				"</select>"+
			"</td>"+

		"</tr>"+
	"</table>";


	}//else{}

	pjtTypeDiv.innerHTML="";
	pjtTypeDiv.insertAdjacentHTML('beforeEnd',html);
}

/*����������Ʈ ����߰�(2006-07-04)*/

/* ���ְ��þ�ü */
function addCorp(span){
	var divId=span.id.substring(0,span.id.indexOf("Span"));
	var type='R';
	if(divId=='cowork')type='C';
	var html="<div id='"+divId+"Div"+corpIdx+"'>"+
			"<table width=100%  align=center cellpadding=1 cellspacing=1 border=0"+ "class=table1 id='table1'>"+
			"<tr id=tr1>"+
				"<td id='td1' width=27% class='td2' align='left' height=24>"+
				"<input id='corpName"+corpIdx+"' name='corpName'   style= 'width:75%;  text-align:left; background-color: #FFFFFF' size='10'>"+
				"<input id='bizAcqCd"+corpIdx+"' type='hidden' name='bizAcqCd'>"+
				"<input type='hidden' name='pjtCorp_div' value='I'>"+
				"<input type='hidden' name='corp_no' value=''>"+
				"<input type='hidden' name='corpType' value='"+type+"'>"+
				"<input type='button' value='�˻�' onclick=popupSearch('bizAcq','bizAcqCd"+corpIdx+"','','corpName"+corpIdx+"')></td>"+
				"<td width=11% class='td2' align='left' height=24>"+
				"<input name='corpPortion'   style= 'width:100%;  text-align:right; background-color: #FFFFFF' size='10'></td>"+
				"<td width=45% class='td2' align='left' height=24>"+
				"<input name='corpDetail'   style= 'width:100%;  text-align:left; background-color: #FFFFFF' size='10'></td>"+
				"<td width=17% class='td2' align='left' height=24>"+
				"<input type='button' value='����' onclick=removeCorp("+divId+"Div"+corpIdx+")>"+
				"</td>"+
			"</tr>"+
			"</table>";	
	span.insertAdjacentHTML('beforeEnd',html);
	corpIdx++;
}
function uptCorp(corpDiv){
	corpDiv.all("pjtCorp_div").value="U";
	pjtRemoveDiv.insertAdjacentHTML('beforeEnd',"");
}
function removeCorp(corpDiv){
	var pjtCorp_div=corpDiv.all("pjtCorp_div").value;
	var corp_no=corpDiv.all("corp_no").value;
	if(pjtCorp_div=='C'||pjtCorp_div=='U'){
		var html="<input type=hidden name=pjtCorp_div value=D>"+
				 "<input type=hidden name=corp_no value='"+corp_no+"'>"+
				 "<input type=hidden name=corpName value='"+corp_no+"'>";
		pjtRemoveDiv.insertAdjacentHTML('beforeEnd',html);
	}	

	corpDiv.outerHTML=""; 
}
function addProposal(currentdate){
	var html = 	"<div id='proposalDiv"+proposalIdx+"'>"+
		        "<table align=center cellpadding=1 cellspacing=1 border=0 width=100%>"+
		        "<tr>"+
				"<td  width=20% height=24><input  id='pjtEmpName"+proposalIdx+"' name='pjtEmpName'   style= 'width:70%;  text-align:left; background-color: #FFFFFF' size='10'>"+		
				"<input type='button' value='�˻�' onclick=javascript:popupSearch('emp','referCd"+proposalIdx+"','','pjtEmpName"+proposalIdx+"');regPjtEmp(proposalDiv"+proposalIdx+",'"+proposalIdx+"') >"+
				"<input type='hidden' name='referCd' id='referCd"+proposalIdx+"'>"+
				"<input type='hidden' name='fullExecYN' value='Y'>"+
				"<input type='hidden' name='pjtEmpType' value='I'>"+
				"<input type='hidden' name='PjtEmp.pjtEmp_no' value='"+proposalIdx+"'>"+
				"<input type='hidden' name='pjtEmp_div' value='I'>"+
				//������ ���Ե� �ο�
				/*
				"<input type='hidden' name='pjtPrcsEmp_div' value='I'>"+
				"<input type='hidden' name='PjtPrcsEmp.prcsCd' value='CT'>"+
				"<input type='hidden' name='PjtPrcsEmp.pjtEmp_no' value=''>"+
				*/
				"</td>"+	
				"<td  width=15% height=24>&nbsp;<input name='pjtEmpStartDt'   style= 'width:70;  text-align:left; background-color: #FFFFFF' size='20'><a href=javascript:openDate('pjtEmpStartDt','"+currentdate+"',proposalDiv"+proposalIdx+")>&nbsp;<img src='../images/navi_08_icon.gif' border=0 style='vertical-align:middle'></a>~<input name='pjtEmpEndDt'   style= 'width:70;  text-align:left; background-color: #FFFFFF' size='20'><a href=javascript:openDate('pjtEmpEndDt','"+currentdate+"',proposalDiv"+proposalIdx+")>&nbsp;<img src='../images/navi_08_icon.gif' border=0 style='vertical-align:middle'></a>"+
				"</td>"+			
				"<td  width=15% height=24><input name='pjtJob'   style= 'width:90%;  text-align:left; background-color: #FFFFFF' size='20'>&nbsp;</td>"+
				"<td  width=50% height=24><input name='pjtEmpDetail'   style= 'width:80%;  text-align:left; background-color: #FFFFFF' size='20'>"+
				"<input type=button value='����'  onclick=removeProposal(proposalDiv"+proposalIdx+")>"+
				"</td></tr></table>"+
		        "</div>";

	proposalIdx++;
	proposalSpan.insertAdjacentHTML('beforeEnd',html);	

}
function removeProposal(proposalSpan){	
	var pjtEmp_div=proposalSpan.all("pjtEmp_div").value;
	var pjtEmp_no=proposalSpan.all("PjtEmp.pjtEmp_no").value;
	if(pjtEmp_div=='C'||pjtEmp_div=='U'){
		var html="<input type=hidden name=pjtEmp_div value=D>"+
				 "<input type=hidden name=PjtEmp.pjtEmp_no value='"+pjtEmp_no+"'>"+
				 "<input type=hidden name=pjtEmpName value='"+pjtEmp_no+"'>"+
			     "";
/* �̺�Ʈ�� ��������� ��� ����(����-�η�)
				 "<input type=hidden name=pjtPrcsEmp_div value=D>"+
				 "<input type=hidden name=PjtPrcsEmp.prcsCd value='CT'>"+
				 "<input type=hidden name=PjtPrcsEmp.pjtEmp_no value='"+pjtEmp_no+"'>";

*/

		pjtRemoveDiv.insertAdjacentHTML('beforeEnd',html);
	}
	proposalSpan.outerHTML="";	
}
//���� ����� ���� �̺�Ʈ ó��
function rsltChange(){
	var html="";
	var frm = document.forms[0];
	if(frm.rslt.options[frm.rslt.selectedIndex].value=="SUCCESS"){
		html=
	"<table align=center cellpadding=1 cellspacing=1 border=0 class=table1 width=995 id=table4>"+
		"<tr>"+
			"<td width=129 class='td3' align='center' height='24'>������ü</td>"+
			"<td width=856 class='td2' align='left' height='24'>"+
				"<input name='successCorpView' style= 'width:35%; height:22; background-color:#FFFFFF;text-align:left;ime-mode:active' size='20'>"+
				"<input name='successCorpCd' type='hidden'>&nbsp;"+
				"<input type='button' size='10' value='�˻�'  onClick=popupSearch('bizAcq','successCorpCd','','successCorpView')>"+
			"</td>"+
		"</tr>"+
		"<tr>"+
			"<td width=129 class='td3' align='center' height='48'>��������</td>"+
			"<td width=856 class='td2' align='left' height='48' >"+
				"<textarea rows='5' name='successFactor' cols=100% style='width:100%;height:95%;background-color: #FFFFFF;text-align:left;ime-mode:active;' maxlength='1000'></textarea></td>"+
		"</tr>"+
		"</table>";

	}else if(frm.rslt.options[frm.rslt.selectedIndex].value=="FAIL"){
		html=
	"<table align=center cellpadding=1 cellspacing=1 border=0 class=table1 width=995 id=table4>"+
		"<tr>"+
			"<td width=129 class='td3' align='center' height=48>���ֿ���</td>"+
			"<td width=856 class='td2' align='left' height=48 >"+
				"<textarea rows='5' name='failFactor' cols=100% style='width:100%;height:95%;background-color: #FFFFFF;text-align:left;ime-mode:active;' maxlength='1000'></textarea></td>"+
		"</tr>"+
		"</table>";
	}else if(frm.rslt.options[frm.rslt.selectedIndex].value=="CANCEL"){
		html=
	"<table align=center cellpadding=1 cellspacing=1 border=0 class=table1 width=995 id=table4>"+
		"<tr>"+
			"<td width=129 class='td3' align='center' height=48>��һ���</td>"+
			"<td width=856 class='td2' align='left' height=48 >"+
				"<textarea rows='5' name='cancelFactor' cols=100% style='width:100%;height:95%;background-color: #FFFFFF;text-align:left;ime-mode:active;' maxlength='1000'></textarea></td>"+
		"</tr>"+
		"</table>";
	}

	appendSpan.innerHTML=html;
}
function regPjtEmp(div,idx){//������Ʈ �η¿� ����ϱ� ���� �̺�Ʈ
/* �̺�Ʈ ����� ��������� ��� ����
	if(div.all("pjtEmpName").value!='')
		div.all("PjtPrcsEmp.pjtEmp_no").value=idx;
*/
}
/*����,���� ���� ó�� ����*/
function checkSave(frm){    
	var checkLst;
	var tmp;
	if(frm.name=='devPjt'){
		if(frm.pjtType_T.value == 'T'){
			checkLst=new Array('pjtName','pjtStartDt','pjtEndDt','pmEmpId','pjtDiv',
					'bizView_1', 'orderCorpCd','contStartDt','contEndDt','contAmt', 'profitType','pjtDetail');	
		}else{
//			checkLst=new Array('pjtName','pjtStartDt','pjtEndDt','pmEmpId','pjtDiv',
					//'bizView_1', 'orderCorpCd','contStartDt','contEndDt','contAmt', 'businessPjt_no','profitType','pjtDetail');
			
			checkLst=new Array('pjtName','pjtStartDt','pjtEndDt','pmEmpId','pjtDiv',
					'bizView_1', 'orderCorpCd','contStartDt','contEndDt','contAmt', 'profitType','pjtDetail');
		}
		
	}else{
		checkLst=new Array('pjtName','pmEmpId','pjtStartDt','pjtEndDt','pjtDiv','bizView_1','profitType');
	}

	//dataModify(frm)
	for(var i=0;i<checkLst.length;i++){
		tmp=frm.all(checkLst[i]);
		if(tmp!=undefined){
			if(tmp.value==''){
				alert(tmp.title+"�� �Է��Ͻÿ�.");
				return false;
			}
		}
	}
	
    /*���� �߰� Ȯ��*/
	var obj=document.all["files"];
	if(obj!=undefined){
		if(obj.length==undefined){
			if(obj.value=='')
				removeFile(document.all["fileDiv"+obj.name.substr(4)]);					
		}else{
			for(var i=obj.length-1;i>-1;i--){
				if(obj[i].value=='')
					removeFile(document.all["fileDiv"+obj[i].name.substr(4)]);				
			}
		}

	}
	return dataModify(frm);
}
function dataModify(frm){//���� , �ݾ� DB�� ����ϱ� ���� ó��
	var checkNumLst=new Array(
		'contAmt','planWorkMh','amt',//Project
		'servAmt','goodsAmt','penaltyRate',//DevPjt
		'inservAmt','outservAmt','proposalAmt',//BusinessPjt
		'expdirectCost','proposeAmt','priceWeight','techWeight',
		'techScore','techRank','priceScore','priceRank',
		'quotaPercent','expContAmt',
		'workCost','workMh',
		'mthLabCost','mthEmpBeneft'//pjtEmp
		);
	var checkDtLst=new Array(
		'pjtStartDt','pjtEndDt','contStartDt','contEndDt',//Project
		'contDt',//SvcPjt
		'proposalstartDt','proposalEndDt','proposalExpDt','saleDt','rsltDt',//BusinessPjt
		'tenderDt','tenderSendDt','tenderNoticeDt','announceDt',
		'prcsStartDt','prcsEndDt',//PjtPrcs
		'prcsInStartDt','prcsInEndDt',//PjtPrcsEmp
		'pjtEmpStartDt','pjtEmpEndDt'//PjtEmp
		);

	var reNum = new RegExp("[^0-9.-]", "g");
	var reDt = new RegExp("[^0-9]", "g");
	var tmp;
	for(var i=0;i<checkNumLst.length;i++){
		tmp=frm.all(checkNumLst[i]);
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
	for(var i=0;i<checkDtLst.length;i++){
		tmp=frm.all(checkDtLst[i]);
		if(tmp!=undefined){
			if(tmp.length!=undefined){
				for(var j=0;j<tmp.length;j++){
					tmp[j].value=tmp[j].value.replace(reDt, '');
				}
			}else{
				tmp.value=tmp.value.replace(reDt, '');
			}			
		}
	}
	return true;
}
function dataInit(frm){//���� , �ݾ� DB�� ����ϱ� ���� ó��
	var checkNumLst=new Array(
		'contAmt','planWorkMh',//'amt','sumWidth',//Project
		'servAmt','goodsAmt','penaltyRate',//DevPjt
		'inservAmt','outservAmt','proposalAmt',//BusinessPjt
		'expdirectCost','proposeAmt','priceWeight','techWeight',
		'techScore','techRank','priceScore','priceRank',
		'quotaPercent','expContAmt',
		'workCost','workMh',
		'mthLabCost','mthEmpBeneft'//pjtEmp
		);
	var checkDtLst=new Array(
		'pjtStartDt','pjtEndDt','contStartDt','contEndDt',//Project
		'contDt',//SvcPjt
		'proposalstartDt','proposalEndDt','proposalExpDt','saleDt','rsltDt',//BusinessPjt
		'tenderDt','tenderSendDt','tenderNoticeDt','announceDt',
		'prcsStartDt','prcsEndDt',//PjtPrcs
		'prcsInStartDt','prcsInEndDt',//PjtPrcsEmp
		'pjtEmpStartDt','pjtEmpEndDt'//PjtEmp
		);

	var tmp;
	for(var i=0;i<checkNumLst.length;i++){
		tmp=frm.all(checkNumLst[i]);
		if(tmp!=undefined){
			if(tmp.length!=undefined){
				for(var j=0;j<tmp.length;j++){
					//tmp[j].value=tmp[j].value.replace(reNum, '');
					tmp[j].value=cashReturn(tmp[j].value);

				}
			}else{
				tmp.value=cashReturn(tmp.value);
			}
		}
	}
	for(var i=0;i<checkDtLst.length;i++){
		tmp=frm.all(checkDtLst[i]);
		if(tmp!=undefined){
			if(tmp.length!=undefined){
				for(var j=0;j<tmp.length;j++){
					if(tmp[j].value.length==8){
						tmp[j].value=tmp[j].value.substring(0, 4)+'-'+tmp[j].value.substring(4, 6)+'-'+tmp[j].value.substring(6);
					}
				}
			}else{
				if(tmp.value.length==8){
					tmp.value=tmp.value.substring(0, 4)+'-'+tmp.value.substring(4, 6)+'-'+tmp.value.substring(6);
				}
			}			
		}
	}
	return true;
}

