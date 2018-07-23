function TF_read() {
  var view = document.read_tf;
  if (confirm("수정하시겠습니까?")) {
    for (var i = 0; i < view.length; i++) {
      view[i].readOnly = false;
    }
  }
  else{
    alert("취소되었습니다.");
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
//변동내역 등록
function mopenchange(param){
    window.open('../FA2/FAOpen/WR_changem.jsp?S_H_NUM='+param, 'ot', 'width=1000px, height=280px');
  }

//수리내역 등록,수정
function openrepair(param){
    window.open('../FA2/FAOpen/WR_repair.jsp?S_FA_NUM='+param, 'ot', 'width=1000px, height=280px');
  }
function mopenrepair(param){
    window.open('../FA2/FAOpen/WR_repairm.jsp?REP_NUM='+param, 'ot', 'width=1000px, height=280px');
  }
//대여내역 등록,수정
function openborrow(param){
    window.open('../FA2/FAOpen/WR_borrow.jsp?S_FA_NUM='+param, 'ot', 'width=1000px, height=280px');
  }
function mopenborrow(param){
    window.open('../FA2/FAOpen/WR_borrowm.jsp?RENT_NUM='+param, 'ot', 'width=1000px, height=280px');
  }

//메모내역 등록,수정
function openmemo(param){
    window.open('../FA2/FAOpen/WR_memo.jsp?S_FA_NUM='+param, 'ot', 'width=700px, height=280px');
  }
function mopenmemo(param){
    window.open('../FA2/FAOpen/WR_memom.jsp?S_M_NUM='+param,'ot', 'width=700px, height=280px');
    }

//자산등록부분
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

//처리 부분 스크립트
function opencheck(){
	if(confirm("처리를 하시겠습니까?")){
		alert("처리가 완료 되었습니다.");
	}
	else{
		alert("취소 되었습니다.");
	}
}
//삭제 부분 스크립트
function opendelete(){
	if(confirm("삭제 하시겠습니까?")){
		alert("삭제 완료 되었습니다.");
	}
	else{
		alert("취소 되었습니다.");
	}
}

function TF_image(){
	window.open('../FA2/FAOpen/WR_toolimage.jsp', 'ot', 'width=770px, height=330px');
}