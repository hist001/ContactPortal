	function chk_rdnColumn()
	{
		frm = document.forms[0];
		var bReturn=false;

		if(frm.RDN_STATUS.value =='RI'){
			if(isEmpty(frm.RDN_CONTENTS)){alert("������ �Է��ϼ���");bReturn = true;}	
		}else if(frm.RDN_STATUS.value =='RE'){
			if(isEmpty(frm.RDN_CONTENTS)){alert("������ �Է��ϼ���");bReturn = true;}	
			if(isEmpty(frm.GRPEMPID)){alert("�׷����� �����ϼ���");bReturn = true;}	
			if(frm.PGTYPE=='R'){
				if(isEmpty(frm.PLANDT)){alert("��ȹ���� �Է��ϼ���");bReturn = true;}
			}
			if(isEmpty(frm.PRCSNO)){alert("������ �����ϼ���");bReturn = true;}
			if(isEmpty(frm.MNGORG_NO)){alert("�����μ��� �����ϼ���");bReturn = true;}
		}else if(frm.RDN_STATUS.value =='DI'){

		}else if(frm.RDN_STATUS.value =='DE'){
			if(isEmpty(frm.RDN_CONTENTS)){alert("������ �Է��ϼ���");bReturn = true;}	
			if(isEmpty(frm.GRPEMPID)){alert("�׷����� �����ϼ���");bReturn = true;}	
			if(frm.PGTYPE=='R'){
				if(isEmpty(frm.PLANDT)){alert("��ȹ���� �Է��ϼ���");bReturn = true;}
			}
			if(isEmpty(frm.PRCSNO)){alert("������ �����ϼ���");bReturn = true;}
			if(isEmpty(frm.MNGORG_NO)){alert("�����μ��� �����ϼ���");bReturn = true;}
			if(isEmpty(frm.WRKEMPID)){alert("�����ڸ� �����ϼ���");bReturn = true;}
		}else if(frm.RDN_STATUS.value =='WI'){
			if(isEmpty(frm.RDN_CONTENTS)){alert("������ �Է��ϼ���");bReturn = true;}	
			if(isEmpty(frm.GRPEMPID)){alert("�׷����� �����ϼ���");bReturn = true;}	
			if(frm.PGTYPE=='R'){
				if(isEmpty(frm.PLANDT)){alert("��ȹ���� �Է��ϼ���");bReturn = true;}
			}	
			if(isEmpty(frm.PRCSNO)){alert("������ �����ϼ���");bReturn = true;}
			if(isEmpty(frm.MNGORG_NO)){alert("�����μ��� �����ϼ���");bReturn = true;}
			if(isEmpty(frm.WRKEMPID)){alert("�����ڸ� �����ϼ���");bReturn = true;}
			if(isEmpty(frm.PREDDT)){alert("�������� �Է��ϼ���");bReturn = true;}
		}else if(frm.RDN_STATUS.value =='WE'){
			if(isEmpty(frm.RDN_CONTENTS)){alert("������ �Է��ϼ���");bReturn = true;}	
			if(isEmpty(frm.GRPEMPID)){alert("�׷����� �����ϼ���");bReturn = true;}
			if(frm.PGTYPE=='R'){
				if(isEmpty(frm.PLANDT)){alert("��ȹ���� �Է��ϼ���");bReturn = true;}
			}
			if(isEmpty(frm.PRCSNO)){alert("������ �����ϼ���");bReturn = true;}
			if(isEmpty(frm.MNGORG_NO)){alert("�����μ��� �����ϼ���");bReturn = true;}
			if(isEmpty(frm.WRKEMPID)){alert("�����ڸ� �����ϼ���");bReturn = true;}
			if(isEmpty(frm.PREDDT)){alert("�������� �Է��ϼ���");bReturn = true;}
		}else if(frm.RDN_STATUS.value =='FI'){
			if(isEmpty(frm.RDN_CONTENTS)){alert("������ �Է��ϼ���");bReturn = true;}	
			if(isEmpty(frm.GRPEMPID)){alert("�׷����� �����ϼ���");bReturn = true;}	
			if(frm.PGTYPE=='R'){
				if(isEmpty(frm.PLANDT)){alert("��ȹ���� �Է��ϼ���");bReturn = true;}
			}	
			if(isEmpty(frm.PRCSNO)){alert("������ �����ϼ���");bReturn = true;}
			if(isEmpty(frm.MNGORG_NO)){alert("�����μ��� �����ϼ���");bReturn = true;}
			if(isEmpty(frm.WRKEMPID)){alert("�����ڸ� �����ϼ���");bReturn = true;}
			if(isEmpty(frm.PREDDT)){alert("�������� �Է��ϼ���");bReturn = true;}
		}else if(frm.RDN_STATUS.value =='FE'){
			if(isEmpty(frm.RDN_CONTENTS)){alert("������ �Է��ϼ���");bReturn = true;}	
			if(isEmpty(frm.GRPEMPID)){alert("�׷����� �����ϼ���");bReturn = true;}	
			if(frm.PGTYPE=='R'){
				if(isEmpty(frm.PLANDT)){alert("��ȹ���� �Է��ϼ���");bReturn = true;}
			}
			if(isEmpty(frm.PRCSNO)){alert("������ �����ϼ���");bReturn = true;}
			if(isEmpty(frm.MNGORG_NO)){alert("�����μ��� �����ϼ���");bReturn = true;}
			if(isEmpty(frm.WRKEMPID)){alert("�����ڸ� �����ϼ���");bReturn = true;}
			if(isEmpty(frm.PREDDT)){alert("�������� �Է��ϼ���");bReturn = true;}
			if(isEmpty(frm.ENDDT)){alert("�Ϸ����� �Է��ϼ���");bReturn = true;}
		}
		
		return bReturn; //�̻��� ���°��.
	}

function delRDN(){
	var frm = document.forms[0];
	
		frm.action = '/RDNDel.do';
		frm.submit();
}

function uptRDN(){
	var frm = document.forms[0];
	
	chk_data();
	
	if(!chk_rdnColumn()){
		frm.action = '/RDNUpt.do';
		frm.submit();
	}
}

function regRDN(){

	var frm = document.forms[0];
	
	chk_data();
	if(!chk_rdnColumn()){
	//if(checkSave(frm)){
		//frm._SCREEN.value='/Budget/chkBudget.jsp';
		//frm.target='RDNReg';
		frm.action = '/RDNReg.do';
		frm.submit();
	//}
	}
}

function chk_data()
{
	var frm = document.forms[0];
	
	frm.CREATEDDTM.value = removeChar(frm.CREATEDDTM.value,"-")
	frm.ENDDT.value = removeChar(frm.ENDDT.value,"-")
	frm.PLANDT.value = removeChar(frm.PLANDT.value,"-")
	frm.REVDT.value = removeChar(frm.REVDT.value,"-")
	frm.PREDDT.value = removeChar(frm.PREDDT.value,"-")
	
	if(frm.RDN_STATUS.value != 'FE'){
		frm.CFMEMPID.value = '';
	}else{
		frm.CFMEMPID.value = '';
	}
}
	
function openFile(){
	window.showModalDialog("/Guarantee/gutFile.jsp?ui_no=RDN&obj_no="+document.form1.RDN_NO.value, this,"dialogWidth:500px; dialogHeight:100px; status:no; scroll:no");
}

/*
function search(){
	//window.location.reload(); 
//	var frm = document.forms[0];

//	frm.action='/RDN/notiList.jsp';
//	frm.submit();
}
*/

function openRDNDetail(pjt_no,pgtype,rdn_no,width,height){

	if (width==null || width=="" || width==undefined){width=900};
	if (height==null ||height=="" || height==undefined){height=800};
	
	if(pgtype=='R'){
		openWin("/RDN/uptRst.jsp?PJT_NO="+pjt_no+
											"&PGTYPE="+pgtype+
											"&RDN_NO="+rdn_no,
											"",750,370);
	}else if(pgtype=='D'){
		openWin("/RDN/uptDir.jsp?PJT_NO="+pjt_no+
											"&PGTYPE="+pgtype+
											"&RDN_NO="+rdn_no,
											"",750,370);
	}else if(pgtype=='N'){
		openWin("/RDN/uptNoti.jsp?PJT_NO="+pjt_no+
											"&PGTYPE="+pgtype+
											"&RDN_NO="+rdn_no,
											"",750,370);
	}										
}

function popupRDNList(pjt_no,pgtype,objcode1,objcodeName1,width,height){

	if (width==null || width=="" || width==undefined){width=500};
	if (height==null ||height=="" || height==undefined){height=400};
	
	openWin("/RDN/rdn_Popup.jsp?pjt_no="+pjt_no+
										"&PGTYPE="+pgtype+
										"&objcode1="+objcode1+
										"&objcodeName1="+objcodeName1,
										"",500,300);

										
}

function regReqRstDir(pjt_no)
	{
		var frm = document.forms[0];
		
		if(frm.PGTYPE.value=='R'){
			openWin('/RDN/regRst.jsp?pjt_no='+pjt_no,"���⹰���",750,370);
		}else if(frm.PGTYPE.value == 'D'){
			openWin('/RDN/regDir.jsp?pjt_no='+pjt_no,"����ó���õ��",750,370);
		}else if(frm.PGTYPE.value == 'N'){
			openWin('/RDN/regNoti.jsp?pjt_no='+pjt_no,"���޻��׵��",750,370);
		}
	}
	
	function setTabNum(i)
	{
		var frm = document.forms[0];
		frm.tabNum.value = i;
	}