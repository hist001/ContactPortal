function TF_read() {
  var view = document.read_tf;
  if (confirm("�����Ͻðڽ��ϱ�?")) {
    for (var i = 0; i < view.length; i++) {
      view[i].readOnly = false;
    }
  }
  else{
    alert("��ҵǾ����ϴ�.");
  }
}

function search() {
    var frm = document.searchForm;
    frm.action = '/FA2/FAindex2Main.jsp';
    frm.target = '_self';
    frm.submit();   
 }
 
 function create() {
    openWin("/FA2/FAindex2Main.jsp?S_FA_NUM=*","",730,400);
 }
 
 
function setValues(Obj,val){
    document.getElementById("S_CONDITION").value = val;   
    document.getElementById("searchBtn").value = Obj;   
 }

function openchange(param){
    //window.open('../FA2/FAOpen/WR_change.jsp', 'ot', 'width=1000px, height=280px');
    window.open('../FA2/FAOpen/WR_change.jsp?S_FA_NUM='+param, 'ot', 'width=1000px, height=280px');
  }
//�������� ���
function mopenchange(param){
    window.open('../FA2/FAOpen/WR_changem.jsp?S_H_NUM='+param, 'ot', 'width=1000px, height=280px');
  }

//�������� ���,����
function openrepair(param){
    window.open('../FA2/FAOpen/WR_repair.jsp?S_FA_NUM='+param, 'ot', 'width=1000px, height=280px');
  }
function mopenrepair(param){
    window.open('../FA2/FAOpen/WR_repairm.jsp?REP_NUM='+param, 'ot', 'width=1000px, height=280px');
  }
//�뿩���� ���,����
function openborrow(param){
    window.open('../FA2/FAOpen/WR_borrow.jsp?S_FA_NUM='+param, 'ot', 'width=1000px, height=280px');
  }
function mopenborrow(param){
    window.open('../FA2/FAOpen/WR_borrowm.jsp?RENT_NUM='+param, 'ot', 'width=1000px, height=280px');
  }

//�޸𳻿� ���,����
function openmemo(param){
    window.open('../FA2/FAOpen/WR_memo.jsp?S_FA_NUM='+param, 'ot', 'width=700px, height=280px');
  }
function mopenmemo(param){
    window.open('../FA2/FAOpen/WR_memom.jsp?S_M_NUM='+param,'ot', 'width=700px, height=280px');
    }

//�ڻ��Ϻκ�
function openproduct(param){
	window.open('../FA2/FAOpen/WR_product.jsp?S_FA_NUM='+param, 'ot', 'width=1000px, height=350px');
}
function openproductm(){
	window.open('../FA2/FAOpen/WR_productm.jsp?S_FA_NUM='+param, 'ot', 'width=1000px, height=350px');
}

function RegistAF(param){
    window.open('../FA2/FAOpen/WR_mchange.jsp?S_FA_NUM='+param, 'ot', 'width=1000px, height=280px');
  }
function ModifyAF(param,idx){
	window.open('../FA2/FAOpen/WR_mmodify.jsp?S_FA_NUM='+param, 'ot', 'width=1000px, height=280px');
}

//ó�� �κ� ��ũ��Ʈ
function opencheck(){
	if(confirm("ó���� �Ͻðڽ��ϱ�?")){
		alert("ó���� �Ϸ� �Ǿ����ϴ�.");
	}
	else{
		alert("��� �Ǿ����ϴ�.");
	}
}
//���� �κ� ��ũ��Ʈ
function opendelete(){
	if(confirm("���� �Ͻðڽ��ϱ�?")){
		alert("���� �Ϸ� �Ǿ����ϴ�.");
	}
	else{
		alert("��� �Ǿ����ϴ�.");
	}
}

function TF_image(){
	window.open('../FA2/FAOpen/WR_toolimage.jsp', 'ot', 'width=770px, height=330px');
}