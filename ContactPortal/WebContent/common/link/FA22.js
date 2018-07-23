function TF_read() {
  var view = document.read_tf;
  if (confirm("수정 하시겠습니까?")) {
    for (var i = 0; i < view.length; i++) {
      view[i].readOnly = false;
    }
  }
  else{
    alert("취소 되었습니다.");
  }
}
/*기본 CRUD 스크립트
   파일 카피본 생성시 action 부분 url 수정
   */
function search() {
	alert("test");
    var frm = document.searchForm;
    frm.action = '/FA2/FAIndex2cpp.jsp';
    frm.target = '_self';
    frm.submit(); 
 }
 
 function create() {
    openWin("/FA2/FAIndex2cpp.jsp?S_FA_NUM=*","",730,400);
 }
 
 function faDetail(param,idx){
	    //search();
	 	var frm = document.form1;
		frm.action = '/FA2/FAIndex2cpp.jsp?FA_D_NUM='+param;
		frm.target = '_self';
		frm.submit();	
		
	} 
 function checkI(){
	 var I1 = document.getElementById("test10").value;
	 var I2 = document.getElementById("test20").value;
	 var I3 = document.getElementById("test30").value;
	 var I4 = document.getElementById("test40").value;
	 var checkI = document.getElementById("I_CODE").value;
	 if(I4!){
		 if(I3!){
			 
		 }
	 }
 }

   //콤보박스의 값을 S_CONDITION에 넣기
function setValues(Obj,val){
    document.getElementById("S_CONDITION").value = val;   
    document.getElementById("searchBtn").value = Obj;   
 }

function openchange(){
    window.open('../FA2/FAOpen/WR_change.jsp', 'ot', 'width=1000px, height=280px');
  }
function mopenchange(){
    window.open('../FA2/FAOpen/WR_changem.jsp', 'ot', 'width=1000px, height=280px');
  }
function openrepair(){
    window.open('../FA2/FAOpen/WR_repair.jsp', 'ot', 'width=1000px, height=280px');
  }
function mopenrepair(){
    window.open('../FA2/FAOpen/WR_repairm.jsp', 'ot', 'width=1000px, height=280px');
  }
function openborrow(){
    window.open('../FA2/FAOpen/WR_borrow.jsp', 'ot', 'width=1000px, height=280px');
  }
function mopenborrow(){
    window.open('../FA2/FAOpen/WR_borrowm.jsp', 'ot', 'width=1000px, height=280px');
  }
function openmemo(){
    window.open('../FA2/FAOpen/WR_memo.jsp', 'ot', 'width=1000px, height=280px');
  }
function mopenmemo(){
    window.open('../FA2/FAOpen/WR_memom.jsp', 'ot', 'width=1000px, height=280px');
  }
function RegistAF(param,idx){
    window.open('../FA2/FAOpen/WR_mchange.jsp?FA_D_NUM='+param, 'ot', 'width=1000px, height=280px');
  }
function ModifyAF(param,idx){
	window.open('../FA2/FAOpen/WR_mmodify.jsp?FA_D_NUM='+param, 'ot', 'width=1000px, height=280px');
  }
