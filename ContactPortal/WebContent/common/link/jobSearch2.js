
function codeSelect(orgCd){//parent�� �� ����	
	bizNo=document.forms[0].bizC.value;
	if(bizNo=='ZZZ'){
		var grpCd=orgCd.substring(0,2);
		if(!(grpCd=='CP'||grpCd=='CQ'||grpCd=='CR')){
			alert('�濵�����ι������� ��������� ����� �� �ֽ��ϴ�.');
			return;
		}
	}

	opener.select(
		document.forms[0].jobC.value,
		document.forms[0].jobN.value,
		document.forms[0].bizC.value,
		document.forms[0].bizN.value,
		document.forms[0].idx.value);
	self.close();
}

function winClose(){//�ݱ�
	self.close();
}

function jobChange(s){//��������  ���ý�
   	if(s=='RC'&&document.forms[0].jobCd.options[document.forms[0].jobCd.selectedIndex].value=='null')
		return
	if(s=='RG'&&document.forms[0].JL.options[document.forms[0].JL.selectedIndex].value=='null')
		return
	
	document.forms[0]._ACT.value=s;

	if(s!='RC')
	document.forms[0].action='/kms/histJobSearch.jsp'
	document.forms[0].key.value="false";
	document.forms[0].submit();
}

function histBizChange(){//������ȣ ���ý�
	s=document.forms[0].histBizNo.options[document.forms[0].histBizNo.selectedIndex].value	;
	idx=s.indexOf("-");
    	bizC=s.substring(0,idx)
	s=s.substring(idx+1);
	if(bizC=='000'){
		document.all["D1"].style.display="";				
		document.all["D"].style.display="none";
	}else{
		idx=s.indexOf("-");
		bizN=s.substring(0,idx)
		s=s.substring(idx+1);

		idx=s.indexOf("-");
		jobC=s.substring(0,idx)
		jobN=s.substring(idx+1);
		
		document.all["D"].style.display="";
		document.all["D1"].style.display="none";
		document.forms[0].bizC.value=bizC;
		document.forms[0].jobC.value=jobC;
		document.forms[0].jobN.value=jobN;
		document.forms[0].bizN.value=bizN;
	}
}

function histBizChange2(jobCd, jobName, bizNo, bizName){//������ȣ ���ý�
	if(bizNo==''){
		document.all["D1"].style.display="";				
		document.all["D"].style.display="none";
	}else{
		document.all["D"].style.display="";
		document.all["D1"].style.display="none";
		document.forms[0].jobC.value=jobCd;
		document.forms[0].jobN.value=jobName;
		document.forms[0].bizC.value=bizNo;
		document.forms[0].bizN.value=bizName;
	}
	document.forms[0].selJobCd2.value=jobCd;
	document.forms[0].selJobName2.value=jobName;
}

function bizChange(s){//��� ���� ���ý�
	if(s=='R'&&document.forms[0].bizNo.options[document.forms[0].bizNo.selectedIndex].value=='null')
		return
	if(s=='RC'&&document.forms[0].BM.options[document.forms[0].BM.selectedIndex].value=='null')
		return
	if(s=='RG'&&document.forms[0].BL.options[document.forms[0].BL.selectedIndex].value=='null'){
		return
	}else if(s=='RG'&&document.forms[0].BL.options[document.forms[0].BL.selectedIndex].value!='null'){					  document.forms[0].bizNo.options[document.forms[0].bizNo.selectedIndex].value='null'
	}
	document.forms[0]._ACT.value=s;
	if(s!='R')
	document.forms[0].action='/kms/histJobSearch.jsp'
	document.forms[0].key.value="true";
	document.forms[0].submit();
}
function bizCodeChange(c,n){//����ڵ� ���ý�
        s=document.forms[0].bizNo.options[document.forms[0].bizNo.selectedIndex].text;
		document.all["D"].style.display="";
		document.forms[0].bizC.value=document.forms[0].bizNo.options[document.forms[0].bizNo.selectedIndex].value;
		document.forms[0].bizN.value=s.substring(s.indexOf(" ")+1);
		document.forms[0].jobC.value=c
		document.forms[0].jobN.value=n
		
}


function selectProd(s){//��ǰ �����ȣ ���ý�
	opener.document.forms[0].prodNo.value=s
	self.close();
}


function change(s){//��ǰ �����ȣ �˻���
	if(s=='R'&&document.forms[0].bizNo.options[document.forms[0].bizNo.selectedIndex].value=='null')
		return
	if(s=='RC'&&document.forms[0].BM.options[document.forms[0].BM.selectedIndex].value=='null')
		return
	if(s=='RG'&&document.forms[0].BL.options[document.forms[0].BL.selectedIndex].value=='null'){
		return
	}else if(s=='RG'&&document.forms[0].BL.options[document.forms[0].BL.selectedIndex].value!='null'){					  document.forms[0].bizNo.options[document.forms[0].bizNo.selectedIndex].value='null'
	}
	document.forms[0]._ACT.value=s;
	if(s!='R')
	document.forms[0].action=document.forms[0]._SCREEN.value
	document.forms[0].submit();
}
//WorkItemProdSearch.jsp
function itemSearch(){//��ǰ�˻�
    if(document.forms[0].rdo[0].checked==true){
		document.forms[0]._ACT.value='RD'

	}else{
	
		document.forms[0]._ACT.value="RC"
	}
	document.forms[0]._SCREEN.value='/kms/WorkItemSearch.jsp'

	document.forms[0].action='/commonCon';
	document.forms[0].method='post'
	//"/commonCon?_ACT=RE&_SCREEN=/common/empItemSearch.jsp&empId=<%=user.empId%>"
	document.forms[0].submit();
	
}
function allSearch(){//��ǰ,�����Ǵ�
	s="/kms/histJobSearch.jsp";
    if (document.forms[0].rdo[0].checked) {
		s="/kms/WorkItemProdSearch.jsp";
	}
	openWin(s, "", '600', '500');   	

}
/**
 *	��â ���� �Լ�(scrollbars=yes)
 */
function openWin(url, winName, sizeW, sizeH)
{
	var nLeft = screen.width/2 - sizeW/2 ;
	var nTop  = screen.height/2 - sizeH/2 ;

	opt = ",toolbar=no,menubar=no,location=no,status=no,scrollbars=yes";
	window.open(url, winName, "left=" + nLeft + ",top=" +  nTop + ",width=" + sizeW + ",height=" + sizeH  + opt );
}

function selectItem(prodNo,prodName,prcsNo,prcsName,realStartDt,realEndDt,mh){//parent�� ������	
	if(document.forms[0].url.value=='createWorkPlanDetail'){
		opener.document.forms[0].jobNo.value=prodNo;
		opener.document.forms[0].jobName.value=prodName;
		opener.document.forms[0].prcsNo.value=prcsNo;
		opener.document.forms[0].prcsName.value=prcsName;
		if(realStartDt!=null&&realStartDt!='null')
		opener.document.forms[0].startDt.value=		realStartDt.substring(0,4)+"-"+realStartDt.substring(4,6)+"-"+realStartDt.substring(6);

		if(realEndDt!=null&&realEndDt!='null')
		opener.document.forms[0].endDt.value=realEndDt.substring(0,4)+"-"+realEndDt.substring(4,6)+"-"+realEndDt.substring(6);
		opener.document.forms[0].planMh.value=mh;
		opener.document.forms[0].actFlag.value='D';
		//***********************************
		
		opener.document.all["start"].style.display="none";
		opener.document.all["end"].style.display="none";
		if(realStartDt!=null&&realStartDt!='null')
			opener.document.forms[0].startDt.value=		realStartDt.substring(0,4)+"-"+realStartDt.substring(4,6)+"-"+realStartDt.substring(6);

		if(realEndDt!=null&&realEndDt!='null')
			opener.document.forms[0].endDt.value=realEndDt.substring(0,4)+"-"+realEndDt.substring(4,6)+"-"+realEndDt.substring(6);
		//***********************************
		self.close();	
	}else{
		opener.selectItem(prodNo,prodName,prcsNo,prcsName,realStartDt,realEndDt,mh);
		self.close();
	}

}

function select(jobNo,jobName,bizNo,bizName){//parent�� �� ����

	if(document.forms[0].url.value=='createWorkPlanDetail'){
		opener.document.forms[0].jobNo.value=jobNo;
		opener.document.forms[0].jobName.value=jobName;
		opener.document.forms[0].prcsNo.value=bizNo;
		opener.document.forms[0].prcsName.value=bizName;
		opener.document.forms[0].actFlag.value='I';
		opener.document.all["start"].style.display="";
		opener.document.all["end"].style.display="";

		self.close();
    }else{
		opener.setValue(jobNo,jobName,bizNo,bizName);
		self.close();
	}
}

function rdoPrcs_click() {//��ǰ����
//alert("aaa");
    document.all["rowa"].style.display="";
    document.all["rowb"].style.display="none";
}

function rdoJob_click() {//��������
	document.forms[0]._ACT.value="RE"
	document.forms[0]._SCREEN.value="/kms/empItemSearch.jsp"
	document.forms[0].action='/commonCon';
	document.forms[0].method='post'
	//"/commonCon?_ACT=RE&_SCREEN=/common/empItemSearch.jsp&empId=<%=user.empId%>"
	document.forms[0].submit();
}
function rdochk1_click() {//����,��ǰ����
//alert("aaa");
    document.all["rowa"].style.display="";
    document.all["rowb"].style.display="none";
}

function rdochk2_click() {//����,��ǰ����
    document.all["rowa"].style.display="none";
    document.all["rowb"].style.display="";
}