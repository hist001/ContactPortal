var isSave = "";
var vDocNo = "";
var vMainNo = "";

var xmlHttp;
var xml ="";
var postData  = ""; 
var strBdChkRst = ""; 
var isCeoCheck = "N";
var isBdgChkOk = "N";

//VARCHAR2 
var varNames = new Array()
var varLengs = new Array()

//NUMBER 
var numNames= new Array('TOTAMT','TAXAMT','AMOUNT','SUPPLAYAMT','REALAMT','DIFFAMT','REALAMT','DIFFAMT','ADVANCEAMT');
var DATA_PRECISION= new Array(20,20,20,20,20,20,20,20,20);
var DATA_SCALE= new Array(10,10,10,10,10,10,10,10,10);

//VCHAR DT 
var vdtNames = new Array('EVENTDT')
var vdtLengs = new Array('10')

var nullChkAttribute = new Array('EVENTDT','USEEMPNAME','TOTAMT','BIZACQCDNAME','TOTAMT','MTITLE','DETAIL','ORGNAME','ACCTCDNAME','AMOUNT','BIZACQACCTCD','PHONNO','JOBNAME','ORGNAME','TITLE');
var nullChkName = Array('�߻�����','�����','�����Ѿ�','�ŷ�ó','�����Ѿ�','����','���޳���','�μ���','�������','�ݾ�', '�ŷ�ó','��ȭ��ȣ','��ͼӺμ�','����μ�','����');


	$(document).ready(function(){

		$('#btnUseOrg').bind('click',
								function()
								{
									popupSearchN('obg_new','obg_0','useOrgCd','useOrgUserCd','useOrgName','','','700','650');
								}
							);
							
		$('#btnBizAcq').bind('click',
								function()
								{
									//popupSearchN('cust','','BIZACQREGNO','BIZACQCDNAME','BIZCEONAME');	
									click_BizAcq('bizAcq');
								}
							);
	
		$('#btAddItem').bind('click',
								function()
								{
									$('#itemSpan').append(fn_mkItemRow(itemIdx));

									$("td[name='btnRemoveItem']").bind('click',
															function()
															{
																//$('#itemDiv').remove();
																$(this).parent().parent().parent().parent().parent().parent().parent().parent().remove();
																//itemIdx--;
															}
														);
														
									itemIdx++;
								}
							);	
		

		$("td[name='btnRemoveItem']").bind('click',
								function()
								{
									$(this).parent().parent().parent().parent().parent().parent().parent().parent().remove();
									//itemIdx--;
								}
							);

		var frmF = $('#frmFile');
		$('#frmFile').ajaxForm(fn_fileUploadCallBack);
		frmF.submit(function(){return false; });
		
		
		$('#btnPopDistributer').bind('click',
				function()
				{
					getDist($('#EVENTDT').val() );
				}
			);		
							
	})
	
	function fn_fileUploadCallBack()
	{
		//alert('fileUpload');
		//execSaveRst();
		fn_sendMail();
		
	}
	
	function fn_fileUpload()
	{
		//�������
		var frmFs;
		frmFs = $('#frmFile'); 
		frmFs.attr("action","FileUploadAjax.do");
		frmFs.submit(); 

	}		
	
	function fn_sendMailCallBack()
	{
		var frm=document.forms[0];
		
		var bReturn = true;
	    if(xmlHttp.readyState == 4) {
	        if(xmlHttp.status == 200) {
	        	if(isMailOk())
	        	{
					//alert('OK');
					execSaveRst();
					
				}else{
					alert('���Ϲ߼ۿ� �����߽��ϴ�.');
				}
	        }
	    }
	}
	
	function isMailOk()
	{

		//alert('isMailOk');
		var bReturn = false;
		var sChkValue ="";
		var res = xmlHttp.responseXML; 
		//alert('res : '+res);
		var rstValue = res.getElementsByTagName("MAILSEND"); 
		var vValue = rstValue[0].firstChild.data;
		//alert('vValue : '+rstValue)
		if(vValue=='Y')
		{
			bReturn = true;
		}
		
		return bReturn;
	}
	
	function fn_sendMail()
	{
		createXMLHttpRequest();
		xml = getPageInfo();
		
		//alert('xml : '+xml);
		
		postData ="";
		var url = "/BudgetAlarmMailSend.do";
		//alert('url : '+url);
		xmlHttp.open("POST", url, true);
		xmlHttp.onreadystatechange = fn_sendMailCallBack; 
	    xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		xmlHttp.setRequestHeader("Cache-Control", "no-cache");
		xmlHttp.setRequestHeader("Pragma", "no-cache");
	
		xmlHttp.send(xml);

	}	
	
	function fn_checkRdo(nm,i)
	{
		var frm=document.forms[1];
		//alert('id : '+i+'  nm : '+nm);
		//$('#'+i).val( $("input:radio[name=nm+'']:checked").val()  );

		if(i=='DOMICILE')
		{
			$('#DOMICILE').val( $("input:radio[name=rdoDOMICILE]:checked").val());
			if($('#'+i).val()==2)
			{	
				if(frm.BIZACQCDNAME.value == '')
				{
					frm.BIZACQACCTCD.value = "77"+frm.USEORGUSERCD.value;
					frm.BIZACQNAME.value = frm.USEORGNAME.value;
					frm.BIZACQCDNAME.value = frm.BIZACQACCTCD.value+" "+frm.BIZACQNAME.value;
					frm.BIZCEONAME.value = "";
					frm.BIZACQREGNO.value = "999-99-99999";
				}
			}
		}else if(i=='ADVANCECLASS'){
			$('#ADVANCECLASS').val( $("input:radio[name=rdoAp]:checked").val());
		}else if(i=='DETAIL'){
			
			$('#DETAILTYPE').val( $("input:radio[name=rdoDETAILTYPE]:checked").val());
			
			if($("input:radio[name=rdoDETAILTYPE]:checked").val()=='A')
			{
				$('#DETAIL').val( '1) ����� : \n2) ��   �� : \n3) ��   �� : \n4) ��   �� : 	\n (1) ���(�ݾ�) : 	\n (2) ������ :');
			}else if($("input:radio[name=rdoDETAILTYPE]:checked").val()=='B'){
				$('#DETAIL').val( '1) ����ο�(������) : \n2) ��   �� : \n3) ��   �� : \n4) ��   �� : \n5) ��   �� : ');	
			}else if($("input:radio[name=rdoDETAILTYPE]:checked").val()=='C'){
				$('#DETAIL').val('');
			}else if($("input:radio[name=rdoDETAILTYPE]:checked").val()=='D'){
				$('#DETAIL').val( '1) �� �� ��  : \n2) ������� : \n3) �����Ⱓ :  ��  �� ��(����) ~ �� �� ��(����)\n4) �� �� ��  :  ��   �׷�    ����  ����	\n5) ��     ��  : ');
			}
		}
		

		
	}
	
	function com_popup(type, id1,id2,id3,ymd)
	{
		if(type=='acct'){
			popupSearchN('act_new','act_0',id1,id2,id3,'','','','600');
			//popupSearchN('act_new','act_new',id1,id2,id3,'','','','600');
			//popupSearchN('acct_new','',id1,id2,id3,'','','','600')
		}else if(type=='org'){
			popupSearchN('org_new','org_0',id1,id2,id3,'','','700','600',0,'',ymd);
		}else if(type=='jobno'){
			//popupSearchN('pjt_new','pjt_3',id1,id2,id3,'','','700','600');
			popupSearchN('org_new','org_0',id1,id2,id3,'','','700','600',0,'',ymd);
		}else if(type =='obg'){
			popupSearchN('obg_new','obg_0',id1,id2,id3,'','','700','600',0,'',ymd);
			//popupSearchN('obg_new','obg_0','useOrgCd','useOrgUserCd','useOrgName','','','700','650');
		}else if(type=='emp'){
			//popupSearchN('emp','','USEEMPNO','USEEMPID','USEEMPNAME');
			popupSearchN('emp','','id1','id2','id3');
			//popupSearchN('emp_acct','',id1,id2,id3,'','','700','600');
		}			
	}
	
	function fn_mkItemRow(idx)
	{
		//alert('idx : '+idx);
		//alert ('varEmpName : '+varEmpName);
		//alert ('varEmpId : '+varEmpId);
		var sn = idx+1;
		var csn = idx-1;
		var itemHtml = 
			"<div id= 'itemDiv"+idx+"' >"+
			"<table align='center' cellpadding='1' cellspacing='1' border='0' class='table1' width='989' height='25'>"+
			"	<tr>"+
			"		<td width='3%' class=TD2 rowspan='2' style='background-color: #FFFFFF' align=center>"+idx+
			"			<input type=hidden name=SN value='"+idx+"'>"+
			"			<table cellpadding=0 cellspacing=0 border=0 align=right id='btnDel' name='btnDel'> "+	
			"			<tr> "+	
			"				<td class='bt_f' width=0 nowrap ></td> "+	
			"				<td width=30 name='btnRemoveItem' class='button' style='cursor:hand;'  onMouseOver=this.className='buttonOn'  onMouseOut=this.className='button' >x "+	
			"				</td> "+	
			"				<td CLASS='bt_e' nowrap width=0 ></td> "+	
			"			</tr> "+	
			"			</table>"+		
			"		</td> "+
			"		<td width='16%' class='td2' align='left' height='25'>"+
			"			<table cellpadding=0 cellspacing=0 border=0 > "+
			"			<tr> "+
			"			<td width=120 >	"+
			"				<input type='hidden' name='ACCTCD' id='ACCTCD"+idx+"'   value='' style='height:20;text-align:left;' size='5' maxlength='5'> "+
			"				<input type='hidden' name='ACCTNAME' id='ACCTNAME"+idx+"'  value='' > "+
			"				<input readonly	name='ACCTCDNAME' id='ACCTCDNAME"+idx+"'  value='' style='width:99%;height:20;text-align:left;ime-mode:active'> "+
			"			</td> "+
			"			<td width=35 align=right> "+
			"				<table cellpadding=0 cellspacing=0 border=0 align=right valign='middle'> "+
			"					<tr> "+
			"						<td class='bt_f' width='0' nowrap></td> "+
			"						<td width=35  name='btnPopupAcctCd'  class='button' style='cursor:hand;' onMouseOver=this.className='buttonOn' onMouseOut=this.className='button' "+
			"							onclick=com_popup('acct','ACCTNAME"+idx+"','ACCTCD"+idx+"','ACCTCDNAME"+idx+"') >���� </td> "+
			"						<td CLASS='bt_e' nowrap width='0'></td> "+
			"					</tr> "+
			"				</table> "+			
			"			</td> "+
			"			</tr> "+
			"			</table>"+ 
			"		</td> "+
			"		<td width='18%' class='td2' align='left' height='25'> "+
			"			<table cellpadding=0 cellspacing=0 border=0 > "+
			"			<tr> "+
			"			<td width=130 >	"+
			"			<input readonly type='hidden' name='ORGCDNAME' style='width:80%;height:24;text-align:left;ime-mode:active'> "+
			"			<input type='hidden' name='LABCOSTTYPE'> "+
			"			<input type='hidden' name='ORGCD' id='ORGCD"+idx+"' size='4' maxlength='4' value='' > "+
			"			<input type='hidden' name='ORGUSERCD' id='ORGUSERCD"+idx+"' size='4' maxlength='4' value='' style='width:40;height:20;text-align:left;ime-mode:active;'> "+
			"			<input type='text' name='ORGNAME' id='ORGNAME"+idx+"'  value='' style='width:128;height:20;text-align:left;text-valign:middle;ime-mode:active;' >  "+
			"			</td> "+
			"			<td width=35 align=right> "+
			"				<table cellpadding=0 cellspacing=0 border=0 align=right valign='middle'> "+
			"					<tr> "+
			"						<td class='bt_f' width='0' nowrap></td> "+
			"						<td width=35  class='button' style='cursor:hand;' onMouseOver=this.className='buttonOn' onMouseOut=this.className='button' onclick=com_popup('org','ORGCD"+idx+"','ORGUSERCD"+idx+"','ORGNAME"+idx+"',document.forms[1].EVENTDT.value)  >�μ� </td> "+
			"						<td CLASS='bt_e' nowrap width='0'></td> "+
			"					</tr> "+
			"				</table> "+			
			"			</td> "+
			"			</tr> "+			
			"			</table> "+
			"		</td> "+
			"		<td width='13%' class='td2' align='center' height='25'>"+
			"			<input type='hidden' name='CDTYPE' id='CDTYPE"+idx+"' style='height:20;text-align:left;' value='C'>  "+
			"			<input type='radio' name='chadea"+idx+"' value='C' class='Attach' onclick=com_function('cha','"+csn+"') checked>���� "+
			"			<input type='radio' name='chadea"+idx+"' value='D' class='Attach' onclick=com_function('dea','"+csn+"') >�뺯 "+
			"		</td>"+
			"		<td width='15%' class='td2' align='left' height='25'>"+
			"			<input readonly name='BUDGETAMT' id='BUDGETAMT"+idx+"' value='0'  style='height:20;text-align:right;ime-mode:disabled' size='13' maxlength='16' "+
			"				onkeyup='this.value=cashReturn(this.value)'> �� "+
			"		</td>"+
			"		<td width='13%' class='td2' align='left' height='25'>"+
			"			<input name='AMOUNT' value='0' style='height:20;text-align:right;ime-mode:disabled' size='13' maxlength='16'"+
			"				onkeyup='this.value=cashReturn(this.value)'> �� "+
			"		</td>"+
			"		<td width='13%' class='td2' align='left' height='25'>"+
			"			<input readonly name='BUDGETUSEDAMT' id='BUDGETUSEDAMT"+idx+"' value='0' style='height:20;text-align:right;ime-mode:disabled' size='13' maxlength='16'"+
			"				onkeyup='this.value=cashReturn(this.value)'> �� "+
			"		</td>"+
			"		<td width='10%' class='td2'>"+
			"			<input readonly name='BUDGETRATE' id='BUDGETRATE"+idx+"' value='0' style='height:20;text-align:right;ime-mode:disabled' size='10' maxlength='16' readonly> % "+
			"		</td>"+					
			"	</tr>"+
			"	<tr>"+
			"		<td width='16%' class=td2 height='25'>"+
			"			<table cellpadding=0 cellspacing=0 border=0 > "+
			"			<tr> "+
			"			<td width=130 >	 "+
			"			<input readonly type='hidden' name='ORGCDNAME' style='width:80%;height:24;text-align:left;ime-mode:active'>  "+
			"			<input type='hidden' name='JOBNONAME'>  "+
			"			<input type='hidden' name='JOBNO' id='JOBNO"+idx+"' size='4' maxlength='4' value='' > "+
			"			<input readonly type='text' name='JOBUSERNO' id='JOBUSERNO"+idx+"' size='4' maxlength='4' value='' style='width:35;height:20;text-align:left;ime-mode:active;'>  "+
			"			<input readonly type='text' name='JOBNAME' id='JOBNAME"+idx+"'  value='' style='width:80;height:20;text-align:left;text-valign:middle;ime-mode:active;' >  "+
			"			</td> "+
			"			<td width=35 align=right> "+
			"				<table cellpadding=0 cellspacing=0 border=0 align=right valign='middle'> "+
			"					<tr> "+
			"						<td class='bt_f' width='0' nowrap></td> "+
			"						<td width=35  name='btnPopupJobNo' class='button' style='cursor:hand;' onMouseOver=this.className='buttonOn' onMouseOut=this.className='button' onclick=com_popup('jobno','JOBNO"+idx+"','JOBUSERNO"+idx+"','JOBNAME"+idx+"',document.forms[1].EVENTDT.value) >�μ�</td> "+
			"						<td CLASS='bt_e' nowrap width='0'></td> "+
			"					</tr> "+
			"				</table> "+		
			"			</td> "+
			"			</tr> "+			
			"			</table>"+ 		
			"		</td>"+
			"		<td class=td2 colspan='3'>"+
			"			<input name='TITLE' value=''  style='width:100%;height:20;text-align:left;ime-mode:active' maxlength='50'>"+
			"		</td>"+
			//"	<td class=td2 > "+
			//"		<table cellpadding=0 cellspacing=0 border=0 > "+
			//"		<tr> "+
			//"		<td width=100> "+
			//"		<input type='hidden' name='USEREMPNO'  id='USEREMPNO' value='' "+
			//"			 > "+
			//"		<input type='hidden' name='USEREMPID' id='USEREMPID'  style='height:22;text-align:left; width:45' size='10' maxlength='50' value='' "+
			//"			 > "+
			//"		<input readonly name='USEREMPNAME' id='USEREMPNAME' style='height:22;text-align:left; width:100' size='10' maxlength='50' value=''  "+
			//"			 > "+
			//"		</td> "+
			//"		<td width='*' align=left> "+
			//"			<table  cellpadding=0 cellspacing=0 border=0 > "+
			//"				<tr> "+
			//"					<td class='bt_f' width='0' nowrap></td> "+
			//"					<td width=35  class='button' style='cursor:hand;' onMouseOver=this.className='buttonOn' onMouseOut=this.className='button' onclick=com_popup('emp','','USEREMPNO','USEREMPID','USEREMPNAME') > ���� </td> "+
			//"					<td CLASS='bt_e' nowrap width='0'></td> "+
			//"				</tr> "+
			//"			</table> "+
			//"		</td> "+
			//"		</tr> "+
			//"		</table> "+	
			"	</td> "+	
			"		<td width='13%' class=td2 colspan='3'>"+
			"			<table  cellpadding=0 cellspacing=0 border=0 >"+
			"			<tr>"+
			"			<td>"+		
			"				<select name='CERTIFTYPE' style='background-color: #FFFFFF' onchange=//certTypeChange('')>"+
			"					<option value='N' selected>-</option>"+
			"					<option value='C' >Card No</option>"+
			"					<option value='S' >���ſ�û��ȣ</option>"+
			"					<option value='L' >LC NO</option>"+
			"					<option value='E' >����ȣ</option>"+
			"				</select>"+
			"			</td>"+
			"			<td>"+
			"				<input name='CERTIFNO' value='' style='height:20;background-color: #FFFFFF;text-align:left;' size='20' maxlength='40'>"+
			"				<input name='CDAPPLNO' value='' style='height:20;background-color: #FFFFFF;text-align:left;' size='8' maxlength='10'>"+
			"				<input name='SELCARD' type=button value='�˻�' style='display:none' onclick=openWin('/vat/cardApList_Slip.jsp?EMPID="+varEmpId+"&USER_NAME="+varEmpName+"&SET_STATUS=Y&idx="+csn+"&regPay=Y&regDiv=','',1000,400) >"+
			"				<input type=hidden name='IT_NO'><input type=hidden name='OBJ_NO'><input type=hidden name='BUDGETRATE'><input type=hidden name='OVERRATE'>"+
			
			"			</td>"+
			"			</tr>"+
			"			</table>"+
			"		</td>"+
			"	</tr>"+
			"</table>"+
			"<table border=0><tr height=1><td></td></tr></table>"+
			"</div>";
	
		return itemHtml;
	}
	
	function removefile(item_no){		
		var div =document.all("fileDiv"+item_no);
		div.outerHTML="";
		fileIdx = fileIdx -1;

		/*
		if(document.getElementById("itemSpanEmpty") !=  null){
			document.getElementById("itemSpanEmpty").style.display = 'none'
		}
		*/
	}	
	
	function fn_mkFileRow(){
		var html = 	
			"<div id= 'fileDiv"+fileIdx+"' width='100%'>"+
			"<table width='100%'  align=center cellpadding=0 cellspacing=0 border=0 class=''>"+
			"<tr ID='chkLine' height=24 class='onbase'>"+
			"<input type='hidden' size='1' name='DELFLAG' value='N'>"+
			"<td  class=viewContentL height=24 width='95%' align='center'>"+
			"<input value="+fileIdx+" name='file'  id='file"+fileIdx+"'  style='width:100%;  background-color:#FFFFFF;text-align:left;ime-mode:active; '  size='20' type='file' >"+
			"<input type='hidden' name='CRFLAG' id='CRFLAG"+fileIdx+"' value='Y'>"+
			"<input type='hidden' name='NO' id='NO"+fileIdx+"' value="+fileIdx+">"+
			"</td>"+
			"<td class=viewContentC height='24' width='5%' >"+			
			"<table cellspacing='0' cellpadding='0' border='0' ><tr>"+
				"<td class='bt_f' nowrap></td>"+
				"<td class='button'  width='30' style='cursor:hand;' onMouseOver=this.className='buttonOn' onMouseOut=this.className='button' onclick='removefile("+fileIdx+")'>����</td>" +
				"<td class='bt_e' nowrap></td>"+
			"</tr></table></td>"+
			"</tr></table>"+
			"</div>";

			fileIdx++;
			fileSpan.insertAdjacentHTML('beforeEnd',html);
			//document.all.divList.scrollTop=99999;
			

			/*
			if(document.getElementById("itemSpanEmpty") !=  null){
				document.getElementById("itemSpanEmpty").style.display = 'none'
			}
			*/
			
		}	
	
	
	function dbclick() 
	{
	    if(event.button==1) alert("��� ��ٷ� �ֽʽÿ�. ���� ó�����Դϴ�.");  
	}
	
	function createXMLHttpRequest() {
	    if (window.ActiveXObject) {
	        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	    } 
	    else if (window.XMLHttpRequest) {
	        xmlHttp = new XMLHttpRequest();
	    }
	}
	
	function AcctRptSave_Ajax(pUrl)
	{
		//alert('1');
		createXMLHttpRequest();
		xml = getPageInfo();
		
		
		//alert('xml : '+xml);
		
		postData ="";
		var url = "/"+pUrl;
		//alert('url : '+url);
		xmlHttp.open("POST", url, true);
		xmlHttp.onreadystatechange = getSaveRst; 
	    xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		xmlHttp.setRequestHeader("Cache-Control", "no-cache");
		xmlHttp.setRequestHeader("Pragma", "no-cache");
	
		xmlHttp.send(xml);
	}
	
	function getPageInfo()
	{
		//alert('2');
		var Frm = document.forms[1];
		//alert(Frm.BUDGETUSEDAMT1.value);
		var postData='';
		//alert('Frm.elements.length : '+Frm.elements.length);
	    for(var i=0; i<Frm.elements.length; i++){ 
	        if(i < Frm.elements.length-1){ 
	            postData += Frm.elements[i].name + "=" + encodeURIComponent(Frm.elements[i].value) + "&"; 
	        }else if(i == Frm.elements.length-1){ 
	            postData += Frm.elements[i].name + "=" + encodeURIComponent(Frm.elements[i].value); 
	        } 
	    } 
	    return postData;    
	}
	
	function getSaveRst() {
	
		var frm=document.forms[1];
		
		var bReturn = true;
	    if(xmlHttp.readyState == 4) {
	        if(xmlHttp.status == 200) {
	        	if(isSaveOk())
	        	{
					//alert('OK');
					showDocContent();
					fn_fileUpload();
					//execSaveRst();
				}else{
					alert('���忡 �����߽��ϴ�.');
				}
	        }
	    }
	}
	
	function isSaveOk()
	{
		//alert('isSaveOk');
		var bReturn = false;
		var sChkValue ="";
		var res = xmlHttp.responseXML; 
		//alert('res : '+res);
		var rstValue = res.getElementsByTagName("SAVERST"); 
		var rstDocNo = res.getElementsByTagName("DOCNO");
		var rstMainNo = res.getElementsByTagName("MAINNO");

		var vValue = rstValue[0].firstChild.data;
		
		if(rstMainNo[0].firstChild != null  ){
			vMainNo = rstMainNo[0].firstChild.data;
		}
		
		if(rstDocNo[0].firstChild != null  ){
			vDocNo = rstDocNo[0].firstChild.data;
		}
		bReturn = true;
		
		return bReturn;
	}
	
	function showDocContent()
	{
		var frm0 = document.forms[0];
		var frm = document.forms[1];
		
		frm.DOCNO.value = vDocNo;
		frm.MAINNO.value = vMainNo;
		frm0.DOCNO_F.value = vDocNo;
		frm0.MAINNO_F.value = vMainNo;
	}
	
	function execSaveRst() {
			//alert(frm.STATUS.value);
	
			if(frm.STATUS.value =='DB0'){
				frm.action = '/HanwayLink.do';
				frm._SCREEN.value = 'SendOk.jsp';
				frm.target='_self';
				frm.submit();
				//self.close();
			}else{
				//frm.action = '/common/saveOk.jsp';
				//frm.submit();
				self.close();
			}

	}
	
	function fn_validateForm()
	{
		var frm = document.forms[1];
		
		frm.TOTAMT.value = replace(frm.TOTAMT.value,',','');
		frm.SUPPLYAMT.value = replace(frm.SUPPLYAMT.value,',','');
		frm.TAXAMT.value = replace(frm.TAXAMT.value,',','');
		frm.REALAMT.value = replace(frm.REALAMT.value,',','');
		frm.DIFFAMT.value = replace(frm.DIFFAMT.value,',','');
		frm.EVENTDT.value = replace(frm.EVENTDT.value,'-','');
		frm.RECIVEDT.value = replace(frm.RECIVEDT.value,'-','');
		
		frm.TAXAMT1.value = replace(frm.TAXAMT1.value,',','');
		frm.UNITCOST1.value = replace(frm.UNITCOST1.value,',','');
		frm.SUPPLYAMT1.value = replace(frm.SUPPLYAMT1.value,',','');	
			
		frm.TAXAMT2.value = replace(frm.TAXAMT2.value,',','');
		frm.UNITCOST2.value = replace(frm.UNITCOST2.value,',','');
		frm.SUPPLYAMT2.value = replace(frm.SUPPLYAMT2.value,',','');		
		
		frm.TAXAMT3.value = replace(frm.TAXAMT3.value,',','');
		frm.UNITCOST3.value = replace(frm.UNITCOST3.value,',','');
		frm.SUPPLYAMT3.value = replace(frm.SUPPLYAMT3.value,',','');
		
		frm.TAXAMT4.value = replace(frm.TAXAMT4.value,',','');
		frm.UNITCOST4.value = replace(frm.UNITCOST4.value,',','');
		frm.SUPPLYAMT4.value = replace(frm.SUPPLYAMT4.value,',','');
		

		if(frm.AMOUNT.length != undefined)
		{
			for(var i=0;i<frm.AMOUNT.length;i++)
			{
				frm.AMOUNT[i].value = replace(frm.AMOUNT[i].value,',','');
				frm.BUDGETAMT[i].value = replace(frm.BUDGETAMT[i].value,',','');
				frm.BUDGETUSEDAMT[i].value = replace(frm.BUDGETUSEDAMT[i].value,',','');
				//frm.BUDGETRATE[i].value = replace(frm.BUDGETRATE[i].value,',','');

			}
			
		}else{
			frm.AMOUNT.value = replace(frm.AMOUNT.value,',','');
			frm.BUDGETAMT.value = replace(frm.BUDGETAMT.value,',','');
			frm.BUDGETUSEDAMT.value = replace(frm.BUDGETUSEDAMT.value,',','');
			//frm.BUDGETRATE.value = replace(frm.BUDGETRATE.value,',','');
		}		
	}
	
	/////////////////////////////   ����üũ    /////////////////////////////////
	
	/*
	function budgetCheck()
	{
		var frm = document.forms[0];

		fn_validateForm();
		budgetCheck_Ajax('BudgetCheck_PaySlip.do',getBudgetCheckRst);

	}
	
	function budgetCheck_Ajax(pUrl,pFn)
	{
		createXMLHttpRequest();
		xml = getPageInfo();
		//alert('xml : '+xml);
		//alert('pUrl : '+pUrl +'    pFn : '+pFn);
		postData ="";
		//var url = "/BudgetCheck.do";
		var url = "/"+pUrl;
		xmlHttp.open("POST", url, false);
	    //xmlHttp.onreadystatechange = getBudgetCheckRst;  
		xmlHttp.onreadystatechange = pFn;  
	
	    xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		xmlHttp.setRequestHeader("Cache-Control", "no-cache");
		xmlHttp.setRequestHeader("Pragma", "no-cache");
	    
	    //escape(encodeURIComponent(xml));
	    
		xmlHttp.send(xml);
	}	
	*/
	function getBudgetCheckRst() {
	
		var frm=document.forms[1];
		var bReturn = true;
		
	    if(xmlHttp.readyState == 4) {
	        if(xmlHttp.status == 200) {
	        	//alert('OK');
	        	
				//if(!isBudgetCheckOk())
				//{
				//	isBdgChkOk = "N";	
				//	openWin("/Confer/BudgetMgt/budgetCheckRst.jsp"+strBdChkRst,"",500,300);
				//}else{
				//	isBdgChkOk = "Y";					
				//}
				
				
	        }
	    }
	}	
	/*
	function isBudgetCheckOk()
	{
		var bReturn = true;
		var sChkValue ="";
		var res = xmlHttp.responseXML; 
	
		var rstValue = res.getElementsByTagName("VALUE"); 
		var rstIdx = res.getElementsByTagName("IDX");
		var rstKey = res.getElementsByTagName("KEY");
		var rstMsg = res.getElementsByTagName("MSG");
		
		var rstSn = res.getElementsByTagName("SN"); 
		var rstExecBudget = res.getElementsByTagName("EXECBUDGET");
		var rstUsedBudget = res.getElementsByTagName("USEDBUDGET");
		var rstRemBudget = res.getElementsByTagName("REMBUDGET");
		var rstBudgetRate = res.getElementsByTagName("BUDGETRATE");
		
		//alert('rstSn : '+rstSn);
		//alert('rstSn.length : '+rstSn.length);
		//alert('rstValue : '+rstValue);
		//alert('xmlHttp.responseXML : '+xmlHttp.responseText);
		//alert('rstExecBudget : '+rstExecBudget);
		//alert('rstUsedBudget : '+rstUsedBudget);
		//alert('rstBudgetRate : '+rstBudgetRate);
		
		for(i=0;i<rstValue.length;i++)
		{
			if(rstValue[i].firstChild.data=='N'){
			
				if(rstKey[i].firstChild.data=='BB'){
					isCeoCheck = 'Y';
				}else{
					bReturn= false;
					sChkValue = sChkValue+'&SN='+rstIdx[i].firstChild.data+'&KEY='+rstKey[i].firstChild.data+'&MSG='+rstMsg[i].firstChild.data;
	
				}
				
			}		
		}
		strBdChkRst = '?ceoCheck='+isCeoCheck+sChkValue;
		//showAcctBudgetContent(rstSn,rstExecBudget,rstUsedBudget,rstBudgetRate);
		
		//alert('pfn : '+pfn);
		showAcctBudgetContent_Inha(rstSn,rstExecBudget,rstUsedBudget,rstBudgetRate, rstRemBudget);
		//alert('bReturn : '+bReturn);
		return bReturn;
	}	
	*/
	function click_BizAcq(param){
		frm._SCREEN.value='/Budget/bizAcqSearch.jsp';
		frm.target='bizAcqSearch';
		frm.action='/initSearchBizAcq.do';
	    openWin("","bizAcqSearch",600,500);
		frm.submit();
	//openWin("/Budget/bizAcqSearch.jsp?param="+param+"&enmpId="+enmpId+"&orgCd="+orgCd,"",600,500);
	}	
	
	function setBizAcqItem(param, code, name, bizAcqRegNo, ceoName){
		frm.BIZACQACCTCD.value=code;
		frm.BIZACQNAME.value=name;
		frm.BIZACQCDNAME.value=code + " " + name;
		frm.BIZACQREGNO.value=bizAcqRegNo;
		frm.BIZCEONAME.value=ceoName;
	}	
	
	function chkChecked(){
		if(frm.C1.checked) {
			frm.PCARDYN.value = "Y";
			frm.BIZACQACCTCD.value = "9BC001";
			frm.BIZACQNAME.value = "��ī��";
			frm.BIZACQCDNAME.value = "9BC001 ��ī��";
			frm.BIZCEONAME.value = "����ȣ";
			frm.BIZACQREGNO.value = "214-81-37726";
			frm.rdoDOMICILE[1].checked = true;
			frm.DOMICILE.value = "1";
			//alert('frm.CERTIFTYPE.length : '+frm.CERTIFTYPE.length);
			frm.BILLTYPE.selectedIndex = 4;
			
			if(frm.SN.length != undefined ){
				for(var i=0;i<frm.CERTIFTYPE.length;i++){
					frm.CERTIFTYPE[i].selectedIndex=1;
					//frm.CDAPPLNO[i].style.visibility="visible";
					frm.CDAPPLNO[i].style.display="";
					frm.SELCARD[i].style.display="";
					
				}
			}else{
				frm.CERTIFTYPE.selectedIndex=1;
				//frm.CDAPPLNO.style.visibility="visible";
				frm.CDAPPLNO.style.display="";
				frm.SELCARD.style.display="";
			}
		} else {
			frm.PCARDYN.value = "N";
			frm.BIZACQACCTCD.value = "";
			frm.BIZACQNAME.value = "";
			frm.BIZACQCDNAME.value = "";
			frm.BIZCEONAME.value = "";
			frm.BIZACQREGNO.value = "";
			
			
			if(frm.SN.length != undefined ){
				for(var i=0;i<frm.CERTIFTYPE.length;i++){
					frm.CERTIFTYPE[i].selectedIndex=0;
					frm.CDAPPLNO[i].style.display="none";
					frm.SELCARD[i].style.display="none";
					
				}
			}else{
				frm.CERTIFTYPE.selectedIndex=0;
				frm.CDAPPLNO.style.display="none";
				frm.SELCARD.style.display="none";
			}			
		}
	}

	function com_function(div,it_no){
		//var no = 0;
		//alert('it_no : '+it_no);
		if(div=='BIZ'){
			popupSearchN('biz_new','biz_'+it_no,'BIZCD','BIZCD','BIZNAME','BIZTYPECD','','','600',it_no+"");

		}else{
			var v_div=document.all("SN");
			var idx=0;
			if(v_div!=undefined){
				if(v_div.length!=undefined){
					for(var i=0;i<v_div.length;i++){
						if(v_div[i].value==it_no){
							//no = i;
							idx=i;break;
						}
					}
				}
			}
			if(div=='org'){		
				popupSearchN('org','','ORGCD','ORGUSERCD','ORGNAME','','','700','600', i ,'');
			}else if(div=='acct'){
				popupSearchN('act_new','0','ACCTCD','ACCTNAME','ACCTCDNAME','','','','600',i, '');
			}else if(div=='acct_b'){
				popupSearchN('acct_b','','ACCTCD','ACCTNAME','ACCTCDNAME','','','','600',i, '');
			}else if(div=='acct_b_etc'){
				popupSearchN('acct_b_etc','','ACCTCD','ACCTNAME','ACCTCDNAME','','','','600',i, '');
			}else if(div=='cha'){
				cha(it_no);
			}else if(div=='dea'){
				dea(it_no);
			}
		}
		
	}
	
	function cha(i){

		var frm = document.forms[1];
		//alert('i : '+i);
		//alert('document.forms[1].CDTYPE.length : '+document.forms[1].CDTYPE.length);
		if(document.forms[1].CDTYPE.length >1)
		{
			frm.CDTYPE[i].value = "C";
		}else{
			frm.CDTYPE.value = "C";
		}	
	}

	function dea(i){
		//alert('document.forms[1].CDTYPE.length : '+document.forms[1].CDTYPE.length);
		if(document.forms[1].CDTYPE.length >1)
		{
			frm.CDTYPE[i].value = "D";
		}else{
			frm.CDTYPE.value = "D";
		}
	}
	
	function fn_setAmountByTotAmt()
	{
		// �����Ѿ� �Է½� �߻�Ǵ� �Լ�
		var frm = document.forms[1];

		if(frm.BILLTYPE.value == '1'){
			if(frm.VATTYPE.value == '201'){			//�Ϲݸ���(���װ���)

			}else if(frm.VATTYPE.value == '211'){	//�Ϲݸ���(���׺Ұ���)
				fn_setAmount();
			}else if(frm.VATTYPE.value == '221'){	//�ڻ����(���װ���)

			}else if(frm.VATTYPE.value == '231'){	//�ڻ����(���׺Ұ���)
				fn_setAmount();
			}else if(frm.VATTYPE.value == '281'){	//��������(���׺Ұ���)
				fn_setAmount();
			}else if(frm.VATTYPE.value == '291'){	//��������(���׺Ұ���)
				fn_setAmount();
			}
		}else if(frm.BILLTYPE.value == '2'){		//��꼭
			if(frm.VATTYPE.value == '401'){			//�Ƿ��� ����
				fn_setAmount();		
			}		
		}else if(frm.BILLTYPE.value == '3'){		//û����
			fn_setAmount();
		}else if(frm.BILLTYPE.value == '4'){		//�ſ�ī��(����)
			fn_setAmount();
		}else if(frm.BILLTYPE.value == '9'){		//��Ÿ
			fn_setAmount();
		}
			
	}
	
	function fn_setAmount()
	{
		var frm = document.forms[1];
		if(frm.AMOUNT.length == undefined || frm.AMOUNT.length == 1)
		{
			if(frm.AMOUNT.length == undefined){
				frm.AMOUNT.value = cashReturn(removeChar(frm.TOTAMT.value,','));
				
			}else if(frm.AMOUNT.lengt == 1){
				frm.AMOUNT[0].value = cashReturn(removeChar(frm.TOTAMT.value,','));
			}
		}else{
			frm.AMOUNT[0].value = cashReturn(removeChar(frm.TOTAMT.value,','));
		}	
	}	

	function fn_setAmountByTaxAmt()
	{
		var frm = document.forms[1];
		
		fn_setTotAmt();
		fn_setRealAmt();
	}
	
	function fn_setAmountBySupplyAmt()
	{
		// ��ް��� �Է½� �߻�Ǵ� �Լ�
		var frm = document.forms[1];

		//if(frm.BILLTYPE.value == '21'){
				//fn_setTaxAmt();
				//fn_setTaxToAmount();
				fn_setTotAmt();
				fn_setRealAmt();
		//}
	}	
	
	function fn_setAmountByRealAmt()
	{
		// ��ް��� �Է½� �߻�Ǵ� �Լ�
		var frm = document.forms[1];
		
		//fn_setTotAmt();
		fn_setDiffAmt();
	}	
		
	function fn_setRealAmt()
	{
		var frm = document.forms[1];
		
		if(frm.BILLTYPE.value == '21'||frm.BILLTYPE.value == '14'||frm.BILLTYPE.value=='1E'){
			if(frm.SUPPLYAMT.value != 0)
			{
				//frm.REALAMT.value = cashReturn(  parseInt(trim(removeChar(frm.SUPPLYAMT.value,",")))+parseInt(trim(removeChar(frm.SUPPLYAMT.value,",")))/10      );
				frm.REALAMT.value = cashReturn(  parseInt(trim(removeChar(frm.SUPPLYAMT.value,",")))+parseInt(trim(removeChar(frm.TAXAMT.value,",")))     );
			}
		}else{
			if(frm.SUPPLYAMT.value != 0)
			{
				frm.REALAMT.value = cashReturn(  parseInt(trim(removeChar(frm.SUPPLYAMT.value,","))) );
			}
		}
	}		
	
	function fn_setTotAmt()
	{
		var frm = document.forms[1];
		
		//alert('frm.BILLTYPE.value : '+frm.BILLTYPE.value);
		if(frm.BILLTYPE.value == '21'||frm.BILLTYPE.value == '14'||frm.BILLTYPE.value == '1E'){
			if(frm.SUPPLYAMT.value != 0)
			{
				
				//frm.TOTAMT.value = cashReturn(  parseInt(trim(removeChar(frm.SUPPLYAMT.value,",")))+parseInt(trim(removeChar(frm.SUPPLYAMT.value,",")))/10      );
				frm.TOTAMT.value = cashReturn(  parseInt(trim(removeChar(frm.SUPPLYAMT.value,",")))+parseInt(trim(removeChar(frm.TAXAMT.value,",")))     );
			}
		}else{
			if(frm.SUPPLYAMT.value != 0)
			{
				frm.TOTAMT.value = cashReturn(  parseInt(trim(removeChar(frm.SUPPLYAMT.value,","))) );
			}
		}
	}		
	
	function fn_setTaxAmt()
	{
		var frm = document.forms[1];
		
		if(frm.BILLTYPE.value == '21'||frm.BILLTYPE.value == '14'){
			if(frm.SUPPLYAMT.value != 0)
			{
				//frm.TAXAMT.value = cashReturn(parseInt(trim(removeChar(frm.TOTAMT.value,","))) - parseInt(trim(removeChar(frm.SUPPLYAMT.value,","))));
				frm.TAXAMT.value = parseInt(trim(removeChar(frm.SUPPLYAMT.value,",")))/10
			}
		}else{
			
		}
	}	
	
	function fn_setDiffAmt()
	{
		var frm = document.forms[1];
				
		frm.DIFFAMT.value = cashReturn(parseInt(trim(removeChar(frm.TOTAMT.value,",")))-parseInt(trim(removeChar(frm.REALAMT.value,","))));
	}		
	
	function certTypeChange(idx){
		//frm.CERTIFTYPE[idx].value=frm.CERTYPE[idx][frm.CERTYPE[idx].selectedIndex].value;
		
		if(frm.AMOUNT.length == undefined || frm.AMOUNT.length == 1)
		{		
			if((frm.CERTIFTYPE.value=="C")){
				frm.CDAPPLNO.style.display="";
			} else {
				frm.CDAPPLNO.style.display="none";	
			}
		}else{
			if((frm.CERTIFTYPE[idx].value=="C")){
				frm.CDAPPLNO[idx].style.display="";
			} else {
				frm.CDAPPLNO[idx].style.display="none";	
			}
		}
	}
	
	function isEventDtOk()
	{
		var frm = document.forms[1];
		var iRtn = removeChar(frm.EVENTDT.value,'-','');
		var iExpirDate = removeChar(frm.EXPIREDATE.value,'-');


		if( iRtn  - iExpirDate >=0)
		{
			if(iCurDate-iRtn >=0){
			
			}else{
				alert('�߻����ڴ� �����Ϻ���  ����̾��  �մϴ�.');
				return false;
			}	
		}else{
			if(iRtn != '')
			{
				alert('������ ���Ŀ��� �ۼ��� �Ұ��� �մϴ�.');
			}else{
				alert('�߻����ڴ� �ʼ��Է��Դϴ�.');
			}
			return false;
		}

		return true;
	}	
	
	function isAmountChkOK()
	{
		var frm1 = document.forms[1];
		var chaSum = 0;
		var daeSum = 0;
		var taxSum = 0;
		
		//alert('frm1.AMOUNT.length : '+frm1.AMOUNT.length);
		if(frm1.AMOUNT.length == undefined)
		{
			if(frm1.CDTYPE.value == 'C')
			{
				chaSum = parseInt(trim(removeChar(frm.AMOUNT.value,",")));
				if(frm1.ACCTCD.value == '11131')
				{
					taxSum = parseInt(trim(removeChar(frm1.AMOUNT.value,",")));
				}
			}else{
				daeSum = parseInt(trim(removeChar(frm.AMOUNT.value,",")));
			}
		}else{
			for(var i=0; i<frm1.AMOUNT.length; i++)
			{
				if(frm1.CDTYPE[i].value == 'C')
				{
					chaSum = parseInt(chaSum) + parseInt(trim(removeChar(frm.AMOUNT[i].value,",")));
					if(frm1.ACCTCD[i].value == '11131')
					{
						taxSum = parseInt(trim(removeChar(frm1.AMOUNT[i].value,",")));
					}
				}else{
					daeSum = parseInt(daeSum) + parseInt(trim(removeChar(frm.AMOUNT[i].value,",")));
				}
			}
		}
		
		//chaSum - daeSum;
		//alert('chaSum : '+chaSum);
		//alert('daeSum : '+daeSum);
		if(parseInt(trim(removeChar(frm1.SUPPLYAMT.value,","))) == 0 )
		{
			alert('��ް����� �Է��ϼ���');
			return false;
		}
		/*
		if(parseInt(trim(removeChar(frm1.TOTAMT.value,","))) != parseInt(trim(removeChar(frm1.SUPPLYAMT.value,",")))+parseInt(trim(removeChar(frm1.TAXAMT.value,",")))  )
		{
			alert('�����Ѿ� =��ް���+�ΰ��� �ݾ��� ��ġ���� �ʽ��ϴ�.');
			return false;
		}		
		*/
		if(parseInt(trim(removeChar(frm1.TOTAMT.value,","))) == 0 )
		{
			alert('�����Ѿ��� �Է��ϼ���');
			return false;
		}
		
		if(parseInt(trim(removeChar(frm1.REALAMT.value,","))) == 0 )
		{
			alert('�����޾��� �Է��ϼ���');
			return false;
		}		
		
		if(parseInt(trim(removeChar(frm1.TOTAMT.value,","))) != parseInt(trim(removeChar(frm1.REALAMT.value,",")))+parseInt(trim(removeChar(frm1.DIFFAMT.value,",")))  )
		{
			alert('�����Ѿ� =�����޾�+���ξ� �ݾ��� ��ġ���� �ʽ��ϴ�.');
			return false;
		}			
		
		if( parseInt(trim(removeChar(frm1.REALAMT.value,","))) != ( chaSum - daeSum)   )
		{
			alert('�����޾�=������-�뺯��  �ݾ��� ��ġ���� �ʽ��ϴ�.');
			return false;
		}
		
		if( parseInt(trim(removeChar(frm1.TAXAMT.value,","))) - taxSum != 0   )
		{
			alert('�ΰ��� �ݾ��� ��ġ���� �ʽ��ϴ�.');
			return false;
		}
		
		if(!isCardOK())
		{
			alert('����ī���ȣ , ���ι�ȣ�� �Է��Ͻñ� �ٶ�ϴ�.');
			return false;
		}
		
		if(!isConfNoOK()){
			alert('��ǰ���Խ� , ���ſ�û��ȣ�� �Է��Ͻñ� �ٶ�ϴ�.');
			return false;
		}
		
		if(frm.BILLTYPE.value == '21'||frm.BILLTYPE.value == '14'||frm.BILLTYPE.value=='16'||frm.BILLTYPE.value=='1E'||frm.BILLTYPE.value=='23'||frm.BILLTYPE.value=='26')
		{
			var isBillOK = false;
			if(frm1.ACCTCD.length  != undefined){
				for(var i=0; i<frm1.ACCTCD.length; i++)
				{
					if(frm.ACCTCD[i].value == '11131')
					{
						isBillOK = true;
					}
				}
			}else{
				if(frm.ACCTCD.value == '11131'){
					isBillOK = true;
				}
			}
			
			if(!isBillOK)
			{
				alert('������� ���ݰ�꼭(��,�鼼,����,����)�� ���޺ΰ���ġ��(11131) �� ȸ��ó������ �� �߰��Ͻñ� �ٶ�ϴ�.')
				return false;
			}
		}else{
			var isBillOK = true
			if(frm1.ACCTCD.length  != undefined){
				for(var i=0; i<frm1.ACCTCD.length; i++)
				{
					if(frm.ACCTCD[i].value == '11131')
					{
						isBillOK = false;
					}
				}
			}else{
				if(frm.ACCTCD.value == '11131'){
					isBillOK = false;
				}
			}
			
			if(!isBillOK)
			{
				alert('�������  ��Ÿ �̸� ���޺ΰ���ġ��(11131)�� �ʿ����ϴ�.');
				return false;
			}
		}
		
		return true;
	}
	
	function isCardOK()
	{
		frm1 = document.forms[1];
		//alert($('#PCARDYN').val()); 

		if(  $('#PCARDYN').val() == 'Y'    )
		{
			if(frm1.ACCTCD.length  != undefined){
				for(var i=0; i<frm1.ACCTCD.length; i++)
				{
					if(frm1.CERTIFTYPE[i].value=='C')
					{
						if(frm1.CDAPPLNO[i].value==''){
							return false;
						}
					}else{
						return false;
					}
				}
			}else{
				if(frm1.CERTIFTYPE.value=='C')
				{
					if(frm1.CDAPPLNO.value==''){
						return false;
					}
				}else{
					return false;
				}
			}
		}
		
		return true;
	}
	
	function isConfNoOK()
	{
		frm1 = document.forms[1];
		//alert($('#PCARDYN').val()); 

		if(frm1.ACCTCD.length  != undefined){
			for(var i=0; i<frm1.ACCTCD.length; i++)
			{
				if(frm1.ACCTCD[i].value=='5001101'||frm1.ACCTCD[i].value=='5001102')
				{
					if(frm1.CERTIFTYPE[i].value=='S'){
						if(frm1.CERTIFNO[i].value==''){
							return false;
						}
					}else{
						return false;
					}
				}
			}
		}else{
			if(frm1.ACCTCD.value=='5001101'||frm1.ACCTCD.value=='5001102')
			{
				if(frm1.CERTIFTYPE.value=='S'){
					if(frm1.CERTIFNO.value==''){
						return false;
					}
				}else{
					return false;
				}
			}
		}

		return true;
	}	
	
	function checkFile(frm){
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
		}	
	
	function setChkFlagPay(i)
	{
		//frmF = document.froms[0];
		if($('#DELFLAG'+i).val()=='N')
		{
			$('#DELFLAG'+i).val('Y');
		}else
		{			
			$('#DELFLAG'+i).val('N');
		}
	}	
	
	function getDist(param){
		if(isEmpty(frm.EVENTDT)){alert("�߻����ڸ� �����ϼ���");return true;}
		if(parseInt(trim(removeChar(frm.TOTAMT.value,","))) > 10000) {
			openWin("/Budget/searchDist.jsp?param="+param,"",600,500);
		} else {
			alert("��αݾ��� �ּ� 10,000��� Ŀ�� �մϴ�.");
			frm.TOTAMT.focus();
			frm.TOTAMT.select();
		}
	}	
	
	function fn_addItem()
	{
		$('#itemSpan').append(fn_mkItemRow(itemIdx));

		$("td[name='btnRemoveItem']").bind('click',
								function()
								{
									//$('#itemDiv').remove();
									$(this).parent().parent().parent().parent().parent().parent().parent().parent().remove();
									//itemIdx--;
								}
							);
							
		itemIdx++;		
	}
	
	function fn_showMTitleToTitle()
	{
		var frm = document.forms[1];
		if(frm.MTITLE.value != '')
		{
			if(frm.ACCTCD.length != undefined)
			{
				for(var i=0;i<frm.ACCTCD.length ;i++)
				{
					frm.TITLE[i].value = frm.MTITLE.value;
				}
			}else{
				frm.TITLE.value = frm.MTITLE.value;
			}
		}
	}	