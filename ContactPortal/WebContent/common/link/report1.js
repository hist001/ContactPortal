/*
*   ���� ���ý� Flag�̿� �Լ�(ex) ���Ͼ������� ���, ������ �̿�)
*/
function chkChecked(s){//�������ý� 
	if(document.forms[0].chk[s].checked==true){
		document.forms[0].delFlag[s].value='Y'
	}else{
		document.forms[0].delFlag[s].value='N'
	}
	//alert(s+"::"+document.forms[0].delFlag[s].value);
	
}
/*
*   ���� ���� ���� ��Ͻ� üũ �Լ�
*/
function reportCheck(){//�������ý� 
	var idx = document.forms[0].jobTitle.length;
	for(i=0;i<idx;i++){
		if(document.forms[0].delFlag[i].value=='N'){
			if(isEmpty(document.forms[0].jobNo[i])){
				alert('PROJECT �Ǵ� ������ �����Ͻÿ�.');
				//document.forms[0].jobNo[i].focus();
				return false;
			}
			if((document.forms[0].jobNo[i]=='null')){
				alert(document.forms[0].jobNo[i]);
				//document.forms[0].jobNo[i].focus();
				return false;
			}
			if(isEmpty(document.forms[0].prcsNo[i])){
				alert('���� �Ǵ� ������ �����Ͻÿ�.');
				//document.forms[0].jobNo[i].focus();
				return false;
			}
			//if(isEmpty(document.forms[0].startDt[i])){
			//	alert('�������ڸ� �����Ͻÿ�.');
				//document.forms[0].startDt[i].focus();
			//	return false;
			//}
			//if(isEmpty(document.forms[0].endDt[i])){
			//	alert('�������ڸ� �����Ͻÿ�.');
				//document.forms[0].endDt[i].focus();
			//	return false;
			//}		
			if(isEmpty(document.forms[0].jobTitle[i])){
				alert('������ �Է��Ͻÿ�.');
				document.forms[0].jobTitle[i].focus();
				return false;
			}
			if(isEmpty(document.forms[0].jobDetail[i])){
				alert('������ �Է��Ͻÿ�.');
				document.forms[0].jobDetail[i].focus();
				return false;

			}
			hh=document.forms[0].hh[i].options[document.forms[0].hh[i].selectedIndex].value;
			mm=document.forms[0].mm[i].options[document.forms[0].mm[i].selectedIndex].value;
			if(hh=='00'&&mm=='00'){
				alert('�۾��ð��� �Է��Ͻÿ�.');
				document.forms[0].hh[i].focus();
				return false;

			}
			if(document.forms[0].name!='sale'
			 &&document.forms[0].name!='confirmUpdate'
			 &&document.forms[0].transFlag[i].value=='Y'){
				alert('����� ���� ���ԵǾ�����. ���Ͽ��������� �����Ͻÿ�. ');
				return false;
			}

		
		}
    }
	return true;
}
/*
*   ���� ���� ���� ���� ��Ͻ� üũ �Լ�
*/
function saleReportCheck(){//�������ý� 
	var idx = document.forms[0].jobTitle.length;
	for(i=0;i<idx;i++){
		if(document.forms[0].delFlag[i].value=='N'){
//			if(document.forms[0].jobNo[i].value = 'null' ||document.forms[0].jobNo[i].value = ''){
//				alert('PROJECT �Ǵ� ���� ������ �߸��Ǿ����ϴ�. �ٽ� �����Ͽ� �ֽʽÿ�');
//				//document.forms[0].jobNo[i].focus();
//				return false;
//			}
			if(isEmpty(document.forms[0].jobNo[i])){
				alert('PROJECT �Ǵ� ������ �����Ͻÿ�.');
				//document.forms[0].jobNo[i].focus();
				return false;
			}
			if(isEmpty(document.forms[0].prcsNo[i])){
				alert('���� �Ǵ� ������ �����Ͻÿ�.');
				//document.forms[0].prcsNo[i].focus();
				return false;
			}
			if(isEmpty(document.forms[0].jobTitle[i])){
				alert('������ �Է��Ͻÿ�.');
				document.forms[0].jobTitle[i].focus();
				return false;
			}
			if(isEmpty(document.forms[0].jobDetail[i])){
				alert('������ �Է��Ͻÿ�.');
				document.forms[0].jobDetail[i].focus();
				return false;
			}
			hh=document.forms[0].hh[i].options[document.forms[0].hh[i].selectedIndex].value;
			mm=document.forms[0].mm[i].options[document.forms[0].mm[i].selectedIndex].value;
			if(hh=='00'&&mm=='00'){
				alert('�۾��ð��� �Է��Ͻÿ�.');
				document.forms[0].hh[i].focus();
				return false;

			}
			if(document.forms[0].totCount[i].value!='0'&&document.forms[0].bizAcqCd[i].value==''){
				alert('�ŷ�ó�� �Է��Ͻÿ�.');
				document.forms[0].B6[i].focus();
				return false;
			}

		
		}
    }
    
    var contentLength =0;
    for(i=0;i<idx;i++){
    	contentLength += trim(document.forms[0].jobDetail[i].value).length;
    }
    if(contentLength <10){
    	alert('�ۼ������� �ν��ϹǷ� ���Ϲٶ��ϴ�.');
    	document.forms[0].jobDetail[0].focus();
    	return false;
    }
    
	return true;
}
/*
*   �⺻ ���� ��ȹ��  üũ �Լ�
*/
function planCheck(s){

	if(s!='change'&&s!='prcs'&&isEmpty(document.forms[0].jobNo)){
		alert('��ǰ/������ �����Ͻÿ�.');
		return false;
	}
	if(s!='prcs'&&(isEmpty(document.forms[0].planMh )||document.forms[0].planMh.value=='0') ){
		alert('�۾��ð��� �Է��Ͻÿ�.');
		return false;
	}
	//if(s=='prcs'&&(isEmpty(document.forms[0].plMh )||document.forms[0].plMh.value=='0' )){
	//	alert('�۾��ð��� �Է��Ͻÿ�.');
	//	return false;
	//}
		
	if(s!='prcs'&&isEmpty(document.forms[0].jobTitle)){
		alert('������ �Է��Ͻÿ�.');
		document.forms[0].jobTitle.focus();
		return false;
	}
	if(s!='prcs'&&isEmpty(document.forms[0].jobDetail)){
		alert('������ �Է��Ͻÿ�.');
		document.forms[0].jobDetail.focus();
		return false;

	}
	if(s=='prcs'&&isEmpty(document.forms[0].prcsDesc )){
		alert('������ �Է��Ͻÿ�.');
		document.forms[0].prcsDesc.focus();
		return false;
	}
	return true;
}
/*
*   ���� ���� ���� ���ȭ�鿡�� ������ ��ȯ�� �Լ�
*/
function reportChecked(){//������ ���ý� 
	var frm=document.forms[0];
	report=frm.reportViewer.options[frm.reportViewer.selectedIndex].value;

	idx=report.indexOf(" ");
	lastIdx=report.lastIndexOf(" ");
	frm.empId.value=report.substring(0,idx);
	frm.orgCd.value=report.substring(idx+1,lastIdx);
	frm.empKName.value=report.substring(lastIdx+1);
	//alert("empId="+frm.empId.value+":orgCd="+frm.orgCd.value+":empKName="+frm.empKName.value+":");
	frm._ACT.value='RSD';
	frm.submit();
    /*
	report=document.forms[0].reportViewer.options[document.forms[0].reportViewer.selectedIndex].value;

	idx=report.indexOf(" ");

    document.forms[0].empId.value=report.substring(0,idx);
	document.forms[0].empKName.value=report.substring(idx+1);
	document.forms[0]._ACT.value='RSD';
	document.forms[0].submit();
	*/

}
/*
*   ���� ���� ���� ���� ���ȭ�鿡�� ������ ��ȯ�� �Լ�
*/
function reportPreChecked(){//������ ���ý� 
	report=document.forms[0].reportViewer.options[document.forms[0].reportViewer.selectedIndex].value;

	idx=report.indexOf(" ");

    document.forms[0].empId.value=report.substring(0,idx);
	document.forms[0].empKName.value=report.substring(idx+1);
	document.forms[0]._ACT.value='RSA';
	document.forms[0].submit();

}
/*
*   ���� ���� ���� ���ȭ�鿡�� �����߰� ���ý� �Լ�
*/
function rowchange(c, kk, length) {

        for (i=1; i<=c ; i++) {
			s=document.forms[0].delFlag[i-1].value;
			if(s=='Y'){
				document.forms[0].delFlag[i-1].value='Y'
			}else{
				document.forms[0].delFlag[i-1].value='N'
			}
			//document.forms[0].chk[i-1].checked='true'
            document.all["row"+i+"a"].style.display="";
        }
        for (j=parseInt(c)+1; j<=kk ; j++) {
			if(j>length){
		    document.forms[0].delFlag[j-1].value='C'
                 
            document.all["row"+j+"a"].style.display="none";
			}else{
				document.forms[0].sel.selectedIndex=length-1
			}

        }
		return;
}