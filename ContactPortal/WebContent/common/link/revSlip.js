var isSave = "";
var vDocNo = "";
var vMainNo = "";

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

var nullChkAttribute = new Array('EVENTDT','USEEMPNAME','TOTAMT','BIZACQCDNAME','TOTAMT','MTITLE','DETAIL','ORGNAME','ACCTCDNAME','AMOUNT','BIZACQACCTCD','PHONNO','RECIVEDT','JOBNAME','ORGNAME','TITLE');
var nullChkName = Array('�߻�����','�����','�����Ѿ�','�ŷ�ó','�����Ѿ�','����','���޳���','�μ���','�������','�ݾ�', '�ŷ�ó','��ȭ��ȣ','�Աݿ�����','���ԱͼӺμ�','ȸ��μ�','����');


	$(document).ready(function(){


		$('#btnUseOrg').bind('click',
								function()
								{
									//popupSearchN('org_new','org_2','useOrgCd','useOrgUserCd','useOrgName','','','700','650');
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
							
	});
	
	function fn_fileUploadCallBack()
	{
		//alert('fileUpload');
		execSaveRst();
	}
	
	function fn_fileUpload()
	{
		//�������
		var frmFs;
		frmFs = $('#frmFile'); 
		frmFs.attr("action","FileUploadAjax.do");
		frmFs.submit(); 

	}	
	
	function com_popup(type, id1,id2,id3, ymd)
	{
		if(type=='acct'){
			//popupSearchN('acct_new','',id1,id2,id3,'','','','600')
			popupSearchN('act_new','act_0',id1,id2,id3,'','','','600');
			
		}else if(type=='org'){
			//popupSearchN('org_new','org_2',id1,id2,id3,'','','700','600');
			popupSearchN('org_new','org_0',id1,id2,id3,'','','700','600',0,'',ymd);
		}else if(type=='jobno'){
			//popupSearchN('pjt_new','pjt_3',id1,id2,id3,'','','700','600');
			popupSearchN('org_new','org_0',id1,id2,id3,'','','700','600',0,'',ymd);
		}else if(type =='obg'){
			popupSearchN('obg_new','obg_0',id1,id2,id3,'','','700','600',0,'',ymd);
			//popupSearchN('obg_new','obg_0','useOrgCd','useOrgUserCd','useOrgName','','','700','650');
		}
		
		
		
		
	}
	
	function fn_mkItemRow(idx)
	{

		var itemHtml = 
				"<div id= 'itemDiv"+idx+"' >"+
				"<input type=hidden name=IT_NO value=''>"+
				"<input type=hidden name=OBJ_NO value=''>"+
				"<input type=hidden name=BUDGETAMT value=''>"+
				"<input type=hidden name=BUDGETRATE value=''>"+
				"<input type=hidden name=CDAPPLNO value=''>"+
				"<input type=hidden name=BUDGETUSEDAMT value=''>"+
				"<input type=hidden name=OVERRATE value=''>"+	
				"<table align='center' cellpadding='1' cellspacing='1' border='0' class='table2' width='989' height='25'>"+
				"	<tr>"+
				"		<td width='3%'  class=TD2 height='25' align=center>"+idx+"<input type=hidden name=SN value='"+idx+"'></td>"+
				"		<td width='12%' class=TD2 height='25'>"+
				"			<table cellpadding=0 cellspacing=0 border=0 > "+
				"			<tr> "+
				"			<td width=100 >	 "+
				"				<input type='hidden' name='ACCTCD' id='ACCTCD"+idx+"'   value='' style='height:20;text-align:left;' size='5' maxlength='5'>  "+
				"				<input type='hidden' name='ACCTNAME' id='ACCTNAME"+idx+"' value='' > "+
				"				<input readonly	name='ACCTCDNAME' id='ACCTCDNAME"+idx+"' value='' style='width:99%;height:20;text-align:left;ime-mode:active'>  "+
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
				"		</td>"+
				"		<td width='15%' class=TD2>"+
				"			<table cellpadding=0 cellspacing=0 border=0 > "+
				"			<tr> "+
				"			<td width=110 >	 "+
				"			<input readonly type='hidden' name='ORGCDNAME' style='width:80%;height:24;text-align:left;ime-mode:active'>  "+
				"			<input type='hidden' name='LABCOSTTYPE'>  "+
				"			<input type='hidden' name='ORGCD' id='ORGCD"+idx+"'  size='4' maxlength='4' value='' > "+
				"			<input type='hidden' name=ORGUSERCD id='ORGUSERCD"+idx+"' size='4' maxlength='4' value='' style='width:40;height:20;text-align:left;ime-mode:active;'>  "+
				"			<input readonly type='text' name='ORGNAME' id='ORGNAME"+idx+"'  value='' style='width:109;height:20;text-align:left;text-valign:middle;ime-mode:active;' >  "+
				"			</td> "+
				"			<td width=35 align=right> "+
				"				<table cellpadding=0 cellspacing=0 border=0 align=right valign='middle'> "+
				"					<tr> "+
				"						<td class='bt_f' width='0' nowrap></td> "+
				"						<td width=35 name='btnPopupOrgCd' class='button' style='cursor:hand;' onMouseOver=this.className='buttonOn' onMouseOut=this.className='button' "+
				"							onclick=com_popup('org','ORGCD"+idx+"','ORGUSERCD"+idx+"','ORGNAME"+idx+"',document.forms[1].EVENTDT.value) >�μ� </td> "+
				"						<td CLASS='bt_e' nowrap width='0'></td> "+
				"					</tr> "+
				"				</table> "+			
				"			</td> "+
				"			</tr> "+			
				"			</table> "+
				"		</td>"+	
				"		<td width='15%' class=TD2>"+
				"			<table cellpadding=0 cellspacing=0 border=0 > "+
				"			<tr> "+
				"			<td width=110 > "+
				"			<input readonly type='hidden' name='ORGCDNAME' style='width:80%;height:24;text-align:left;ime-mode:active'> "+
				"			<input type='hidden' name='JOBNONAME'> "+
				"			<input type='hidden' name='JOBNO' id='JOBNO"+idx+"' size='4' maxlength='4' value='' > "+
				"			<input readonly type='text' name=JOBUSERNO  id='JOBUSERNO"+idx+"' size='4' maxlength='4' value='' style='width:30;height:20;text-align:left;ime-mode:active;'> "+
				"			<input readonly type='text' name='JOBNAME' id='JOBNAME"+idx+"'  value='' style='width:75;height:20;text-align:left;ime-mode:active;' > "+
				"			</td> "+
				"			<td width=35 align=right> "+
				"				<table cellpadding=0 cellspacing=0 border=0 align=right valign='middle'> "+
				"					<tr> "+
				"						<td class='bt_f' width='0' nowrap></td> "+
				"						<td width=35  name='btnPopupJobNo' class='button' style='cursor:hand;' onMouseOver=this.className='buttonOn' onMouseOut=this.className='button' "+
				"							onclick=com_popup('jobno','JOBNO"+idx+"','JOBUSERNO"+idx+"','JOBNAME"+idx+"',document.forms[1].EVENTDT.value)  >�μ� </td> "+
				"						<td CLASS='bt_e' nowrap width='0'></td> "+
				"					</tr> "+
				"				</table> "+			
				"			</td>"+ 
				"			</tr> "+
				"			</table>"+		
				"		</td>"+
				"		<td width='10%' class=TD2>"+
				"			<input type='hidden' name='CDTYPE' style='height:20;text-align:left;' value='D'>  "+
				"			<input type='radio' name='chadea"+idx+"' value='C' class='Attach' onclick=com_function('cha','"+idx+"') >���� "+
				"			<input type='radio' name='chadea"+idx+"' value='D' class='Attach' onclick=com_function('dea','"+idx+"') checked>�뺯 "+		
				"		</td>"+
				"		<td width='12%' class=TD2>"+
				"			<input name='AMOUNT' value='0'  style='height:20;text-align:right;ime-mode:disabled' size='14' maxlength='16'"+
				"				onkeyup='this.value=cashReturn(this.value)'> ��	"+
				"		</td>"+
				"		<td width='12%' class=TD2>"+
				"			<input name='TITLE' value=''  style='width:100%;height:20;text-align:left;ime-mode:active' maxlength='50'>"+
				"		</td>"+
				"		<td width='18%' class=TD2>"+				
				"			<select name='CERTIFTYPE' style='background-color: #FFFFFF' onchange=//certTypeChange('0')>"+
				"				<option value='N'>-</option>"+
				"				<option value='S'>���Ź�ȣ</option>"+
				"				<option value='E'>����ȣ</option>"+
				"			</select>"+
				"			<input name='CERTIFNO' style='width:95;height:24;background-color: #FFFFFF;text-align:left' size='30' maxlength='40'>"+
				"		</td>"+	
				"		<td width='3%' class=TD2>"+	
				"			<table cellpadding=0 cellspacing=0 border=0 align=right id='btnDel' name='btnDel'> "+	
				"			<tr> "+	
				"				<td class='bt_f' width=0 nowrap ></td> "+	
				"				<td width=30 name='btnRemoveItem' class='button' style='cursor:hand;'  onMouseOver=this.className='buttonOn'  onMouseOut=this.className='button' >x "+	
				"				</td> "+	
				"				<td CLASS='bt_e' nowrap width=0 ></td> "+	
				"			</tr> "+	
				"			</table>"+			
				"		</td>"+		
				"	</tr>"+
				"</table>"+
				"<table border=0><tr><td></td></tr></table>"+
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
			"<input value="+fileIdx+" name='file'  style='width:100%;  background-color:#FFFFFF;text-align:left;ime-mode:active; '  size='50' type='file' id='files"+fileIdx+"'>"+
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
		var Frm = document.forms[1];
		var postData='';
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
	
			//Ajax�� ���Ͼ��ε� �� �����ϸ� �����Ѵ�.
		
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
			}
			
		}else{
			frm.AMOUNT.value = replace(frm.AMOUNT.value,',','');
		}		
	}	
	
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
	
	function fn_setAmountBySupplyAmt()
	{
		// ��ް��� �Է½� �߻�Ǵ� �Լ�
		var frm = document.forms[1];

		//if(frm.BILLTYPE.value == '21'){
				//fn_setTaxAmt();
				//fn_setTaxToAmount();
				fn_setTotAmt();
				fn_setRealAmt();
				//fn_setToTaxBill();
		//}
	}	
	
	function fn_setAmountByTaxAmt()
	{
		var frm = document.forms[1];
		fn_setTotAmt();
		fn_setRealAmt();
	}	
	
	function fn_setAmountByRealAmt()
	{
		// ��ް��� �Է½� �߻�Ǵ� �Լ�
		var frm = document.forms[1];
		
		//fn_setTotAmt();
		fn_setDiffAmt();
	}	
	
	function fn_setToTaxBill()
	{
		var frm = document.forms[1];
		
		if(frm.BILLTYPE.value == '11'){
			if(frm.SUPPLYAMT.value != 0)
			{
				//frm.REALAMT.value = cashReturn(  parseInt(trim(removeChar(frm.SUPPLYAMT.value,",")))+parseInt(trim(removeChar(frm.SUPPLYAMT.value,",")))/10      );
				frm.SUPPLYAMT1.value = cashReturn(  parseInt(trim(removeChar(frm.SUPPLYAMT.value,","))) );
				frm.TAXAMT1.value = cashReturn(  parseInt(trim(removeChar(frm.TAXAMT.value,","))) );
				
				frm.SUPPLYTOTAMT.value = cashReturn(  parseInt(trim(removeChar(frm.SUPPLYAMT.value,","))) );
				frm.TAXTOTAMT.value = cashReturn(  parseInt(trim(removeChar(frm.TAXAMT.value,","))) );
				
				
			}
		}else{
			if(frm.SUPPLYAMT.value != 0)
			{
				frm.SUPPLYAMT1.value = cashReturn(  parseInt(trim(removeChar(frm.SUPPLYAMT.value,","))) );
				frm.TAXAMT1.value = cashReturn(  parseInt(trim(removeChar(frm.TAXAMT.value,","))) );
				
				frm.SUPPLYTOTAMT.value = cashReturn(  parseInt(trim(removeChar(frm.SUPPLYAMT.value,","))) );
				frm.TAXTOTAMT.value = cashReturn(  parseInt(trim(removeChar(frm.TAXAMT.value,","))) );
			}
			
		}
		
	}
	
	
	function fn_setRealAmt()
	{
		var frm = document.forms[1];
		
		if(frm.BILLTYPE.value == '11'||frm.BILLTYPE.value=='1E'){
			if(frm.SUPPLYAMT.value != 0)
			{
				//frm.TAXAMT.value = cashReturn(parseInt(trim(removeChar(frm.TOTAMT.value,","))) - parseInt(trim(removeChar(frm.SUPPLYAMT.value,","))));
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
		if(frm.BILLTYPE.value == '11'||frm.BILLTYPE.value == '1E'){
			//if(frm.SUPPLYAMT.value != 0)
			//{
				//frm.TOTAMT.value = cashReturn(  parseInt(trim(removeChar(frm.SUPPLYAMT.value,",")))+parseInt(trim(removeChar(frm.SUPPLYAMT.value,",")))/10      );
				frm.TOTAMT.value = cashReturn(  parseInt(trim(removeChar(frm.SUPPLYAMT.value,",")))+parseInt(trim(removeChar(frm.TAXAMT.value,",")))     );
			//}
		}else{
			//if(frm.SUPPLYAMT.value != 0)
			//{
				frm.TOTAMT.value = cashReturn(  parseInt(trim(removeChar(frm.SUPPLYAMT.value,","))) );
			//}
		}
	}		
	
	function fn_setTaxAmt()
	{
		var frm = document.forms[1];
		
		if(frm.BILLTYPE.value == '11'){
			if(frm.SUPPLYAMT.value != 0)
			{
				//frm.TAXAMT.value = cashReturn(parseInt(trim(removeChar(frm.TOTAMT.value,","))) - parseInt(trim(removeChar(frm.SUPPLYAMT.value,","))));
				frm.TAXAMT.value = parseInt(trim(removeChar(frm.SUPPLYAMT.value,",")))/10
			}
		}else{
			frm.TAXAMT.value = 0;
		}
	}	
	
	function fn_setDiffAmt()
	{
		var frm = document.forms[1];
				
		frm.DIFFAMT.value = cashReturn(parseInt(trim(removeChar(frm.TOTAMT.value,",")))-parseInt(trim(removeChar(frm.REALAMT.value,","))));
	}		
	
	function sumCheck1(field){
		var frm = document.forms[1];
		var str = field.value;
		if(str.length==0){
		    return;
		}
		if(!isNumDashComma(field)){
		    field.select();
		    field.focus();
		    return;
		} else {
			if((frm.BILLTYPE.value=="11")){ //���ݰ�꼭�ΰ�� �ΰ����� ����Ѵ�.
				//amt = Math.round(parseInt(trim(removeChar(frm.supplyAmt1.value,","))) / 10);
				//amt = parseInt(parseInt(trim(removeChar(frm.supplyAmt1.value,","))) / 10);
				amt = parseInt(trim(removeChar(frm.TAXAMT.value,",")));
				frm.TAXAMT1.value = cashReturn(amt);
				amt = 0;
				amt = isInt(frm.TAXAMT1) + isInt(frm.TAXAMT2) + isInt(frm.TAXAMT3) + isInt(frm.TAXAMT4);
				frm.TAXTOTAMT.value = cashReturn(amt);
			}
			amt = 0;
			amt = isInt(frm.SUPPLYAMT1) + isInt(frm.SUPPLYAMT2) + isInt(frm.SUPPLYAMT3) + isInt(frm.SUPPLYAMT4);
			frm.SUPPLYTOTAMT.value = cashReturn(amt);
			
			//field.value = cashReturn(frm.taxamt.value);
		}
	}

	function sumCheck2(field){
		var str = field.value;
		if(str.length==0){
		    return;
		}
		if(!isNumDashComma(field)){
		    field.select();
		    field.focus();
		    return;
		} else {
			if((frm.BILLTYPE.value=="11")){ //���ݰ�꼭�ΰ�� �ΰ����� ����Ѵ�.
				//amt = Math.round(parseInt(trim(removeChar(frm.supplyAmt2.value,","))) / 10);
				amt = parseInt(parseInt(trim(removeChar(frm.SUPPLYAMT2.value,","))) / 10);
				frm.TAXAMT2.value = cashReturn(amt);
				amt = 0;
				amt = isInt(frm.TAXAMT1) + isInt(frm.TAXAMT2) + isInt(frm.TAXAMT3) + isInt(frm.TAXAMT4);
				frm.TAXTOTAMT.value = cashReturn(amt);
			}
			amt = 0;
			amt = isInt(frm.SUPPLYAMT1) + isInt(frm.SUPPLYAMT2) + isInt(frm.SUPPLYAMT3) + isInt(frm.SUPPLYAMT4);
			frm.SUPPLYTOTAMT.value = cashReturn(amt);
			
			field.value = cashReturn(frm.SUPPLYAMT2.value);
		}
	}

	function sumCheck3(field){
		var str = field.value;
		if(str.length==0){
		    return;
		}
		if(!isNumDashComma(field)){
		    field.select();
		    field.focus();
		    return;
		} else {
			if((frm.BILLTYPE.value=="11")){ //���ݰ�꼭�ΰ�� �ΰ����� ����Ѵ�.
				//amt = Math.round(parseInt(trim(removeChar(frm.supplyAmt3.value,","))) / 10);
				amt = parseInt(parseInt(trim(removeChar(frm.SUPPLYAMT3.value,","))) / 10);
				frm.TAXAMT3.value = cashReturn(amt);
				amt = 0;
				amt = isInt(frm.TAXAMT1) + isInt(frm.TAXAMT2) + isInt(frm.TAXAMT3) + isInt(frm.TAXAMT4);
				frm.TAXTOTAMT.value = cashReturn(amt);
			}
			amt = 0;
			amt = isInt(frm.SUPPLYAMT1) + isInt(frm.SUPPLYAMT2) + isInt(frm.SUPPLYAMT3) + isInt(frm.SUPPLYAMT4);
			frm.SUPPLYTOTAMT.value = cashReturn(amt);
			
			field.value = cashReturn(frm.SUPPLYAMT3.value);
		}
	}

	function sumCheck4(field){
		var str = field.value;
		if(str.length==0){
		    return;
		}
		if(!isNumDashComma(field)){
		    field.select();
		    field.focus();
		    return;
		} else {
			if((frm.BILLTYPE.value=="11") ){ //���ݰ�꼭�ΰ�� �ΰ����� ����Ѵ�.
				amt = Math.round(parseInt(trim(removeChar(frm.SUPPLYAMT4.value,","))) / 10);
				frm.TAXAMT4.value = cashReturn(amt);
				amt = 0;
				amt = isInt(frm.TAXAMT1) + isInt(frm.TAXAMT2) + isInt(frm.TAXAMT3) + isInt(frm.TAXAMT4);
				frm.TAXTOTAMT.value = cashReturn(amt);
			}
			amt = 0;
			amt = isInt(frm.SUPPLYAMT1) + isInt(frm.SUPPLYAMT2) + isInt(frm.SUPPLYAMT3) + isInt(frm.SUPPLYAMT4);
			frm.SUPPLYTOTAMT.value = cashReturn(amt);
			
			field.value = cashReturn(frm.SUPPLYAMT4.value);
		}
	}

	function sumTaxCheck1(field){
		var str = field.value;
		var amt = 0;
		if(str.length==0){
		    return;
		}
		if(!isNumDashComma(field)){
		    field.select();
		    field.focus();
		    return;
		} else {
			frm.TAXAMT1.value = cashReturn(trim(removeChar(frm.TAXAMT1.value,",")));
			amt = 0;
			amt = isInt(frm.TAXAMT1) + isInt(frm.TAXAMT2) + isInt(frm.TAXAMT3) + isInt(frm.TAXAMT4);
			frm.TAXTOTAMT.value = cashReturn(amt);
		}
	}

	function sumTaxCheck2(field){
		var str = field.value;
		var amt = 0;
		if(str.length==0){
		    return;
		}
		if(!isNumDashComma(field)){
		    field.select();
		    field.focus();
		    return;
		} else {
			frm.TAXAMT2.value = cashReturn(trim(removeChar(frm.TAXAMT2.value,",")));
			amt = 0;
			aamt = isInt(frm.TAXAMT1) + isInt(frm.TAXAMT2) + isInt(frm.TAXAMT3.value) + isInt(frm.TAXAMT4);
			frm.TAXTOTAMT.value = cashReturn(amt);
		}
	}

	function sumTaxCheck3(field){
		var str = field.value;
		var amt = 0;
		if(str.length==0){
		    return;
		}
		if(!isNumDashComma(field)){
		    field.select();
		    field.focus();
		    return;
		} else {
			frm.TAXAMT3.value = cashReturn(trim(removeChar(frm.TAXAMT3.value,",")));
			amt = 0;
			amt = isInt(frm.TAXAMT1) + isInt(frm.TAXAMT2) + isInt(frm.TAXAMT3) + isInt(frm.TAXAMT4);
			frm.TAXTOTAMT.value = cashReturn(amt);
		}
	}

	function sumTaxCheck4(field){
		var str = field.value;
		var amt = 0;
		if(str.length==0){
		    return;
		}
		if(!isNumDashComma(field)){
		    field.select();
		    field.focus();
		    return;
		} else {
			frm.TAXAMT4.value = cashReturn(trim(removeChar(frm.TAXAMT4.value,",")));
			amt = 0;
			amt = isInt(frm.TAXAMT1) + isInt(frm.TAXAMT2) + isInt(frm.TAXAMT3) + isInt(frm.TAXAMT4);
			frm.TAXTOTAMT.value = cashReturn(amt);
		}

	}	
	
	function isInt(field){
		var str = trim(field.value);
		if(str.length==0){
		    return 0;
		}
		return parseInt(trim(removeChar(str,",")));
	}
	
	function setChkFlagRev(i)
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
	
	function fn_checkRdo(nm,i)
	{
		var frm=document.forms[1];
		//alert('id : '+i+'  nm : '+nm);
		//$('#'+i).val( $("input:radio[name=nm+'']:checked").val()  );

		if(i=='DOMICILE')
		{
			$('#DOMICILE').val( $("input:radio[name=rdoDOMICILE]:checked").val());

		}else if(i=='ADVANCECLASS'){
			
			$('#ADVANCECLASS').val( $("input:radio[name=rdoAp]:checked").val());
			
		}else if(i=='OUTSOURCETYPE'){			
			
			$('#OUTSOURCETYPE').val( $("input:radio[name=rdoOut]:checked").val());
			
		}
		
	}
	
	function isAmountChkOK()
	{
		var frm1 = document.forms[1];
		var chaSum = 0;
		var daeSum = 0;
		var taxSum = 0;
		
		if(frm1.AMOUNT.length == undefined)
		{
			if(frm1.CDTYPE.value == 'C')
			{
				chaSum = parseInt(trim(removeChar(frm.AMOUNT.value,",")));
			}else{
				daeSum = parseInt(trim(removeChar(frm.AMOUNT.value,",")));
				if(frm1.ACCTCD.value == '21081')
				{
					taxSum = parseInt(trim(removeChar(frm1.AMOUNT.value,",")));
				}
			}
		}else{
			for(var i=0; i<frm1.AMOUNT.length; i++)
			{
				if(frm1.CDTYPE[i].value == 'C')
				{
					chaSum = parseInt(chaSum) + parseInt(trim(removeChar(frm.AMOUNT[i].value,",")));
				}else{
					daeSum = parseInt(daeSum) + parseInt(trim(removeChar(frm.AMOUNT[i].value,",")));
					if(frm1.ACCTCD[i].value == '21081')
					{
						taxSum = parseInt(trim(removeChar(frm1.AMOUNT[i].value,",")));
					}	
				}
			}
		}
		
		//chaSum - daeSum;
		
		if( parseInt(trim(removeChar(frm1.REALAMT.value,","))) != ( daeSum-chaSum )   )
		{
			alert('�����޾�= �뺯�� - ������  �ݾ��� ��ġ���� �ʽ��ϴ�.');
			return false;
		}
		
		if( parseInt(trim(removeChar(frm1.TAXAMT.value,","))) - taxSum != 0   )
		{
			alert('�ΰ��� �ݾ��� ��ġ���� �ʽ��ϴ�.');
			return false;
		}		
		
		if(!isConfNoOK()){
			alert('��ǰ�ǸŽ� , ���Ź�ȣ�� �Է��Ͻñ� �ٶ�ϴ�.');
			return false;
		}		
		
		if(frm.BILLTYPE.value == '11'||frm.BILLTYPE.value == '14'||frm.BILLTYPE.value == '16'||frm.BILLTYPE.value=='1E')
		{
			var isBillOK = false;
			if(frm1.ACCTCD.length  != undefined){
				for(var i=0; i<frm1.ACCTCD.length; i++)
				{
					if(frm.ACCTCD[i].value == '21081')
					{
						isBillOK = true;
					}
				}
			}else{
				if(frm.ACCTCD.value == '21081'){
					isBillOK = true;
				}
			}
			
			if(!isBillOK)
			{
				alert('������� ���ݰ�꼭(��,�鼼,����,����)�� �����ΰ���(21081) �� ȸ��ó������ �� �߰��Ͻñ� �ٶ�ϴ�.')
				return false;
			}
		}else{
			var isBillOK = true
			if(frm1.ACCTCD.length  != undefined){
				for(var i=0; i<frm1.ACCTCD.length; i++)
				{
					if(frm.ACCTCD[i].value == '21081')
					{
						isBillOK = false;
					}
				}
			}else{
				if(frm.ACCTCD.value == '21081'){
					isBillOK = false;
				}
			}
			
			if(!isBillOK)
			{
				alert('�������  ��Ÿ �̸� �����ΰ���(21081)�� �ʿ����ϴ�.');
				return false;
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
				if(frm1.ACCTCD[i].value=='43015'||frm1.ACCTCD[i].value=='41021')
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
			if(frm1.ACCTCD.value=='43015'||frm1.ACCTCD.value=='41021')
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
				popupSearchN('acct','','ACCTCD','ACCTNAME','ACCTCDNAME','','','','600',i, '');
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
		
		if(document.forms[1].CDTYPE.length >1)
		{
			frm.CDTYPE[i].value = "C";
		}else{
			frm.CDTYPE.value = "C";
		}	
	}

	function dea(i){

		if(document.forms[1].CDTYPE.length >1)
		{
			frm.CDTYPE[i].value = "D";
		}else{
			frm.CDTYPE.value = "D";
		}
	}	
	
	function certTypeChange(idx){
		frm.CERTIFTYPE[idx].value=frm.certType[idx][frm.certType[idx].selectedIndex].value;
	}	
	
	function fn_showMTitleToTitle()
	{
		var frm = document.forms[1];
		if(frm.MTITLE.value != '')
		{
			if(frm.ACCTCD.length != undefined)
			{
				frm.TITLE[0].value = frm.MTITLE.value;	
			}else{
				frm.TITLE.value = frm.MTITLE.value;
			}
		}
	}	
			