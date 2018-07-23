<%
/* 	파 일 명  : comPage.jsp
	작성일자  : 2018-01-11
	작 성 자  : mailbest
	내    용  : comPage조회 */
%>
<script language='javascript'>
//페이징 처리
function pageSelect(movePage,page,pageGroup,maxPage,maxPageGroup){
	var frm= document.forms[0];
	if(movePage=='H' & page=='1') {
		alert("첫 페이지 입니다.");
		return;
	}else if (movePage=='H' & page!='1') {
		frm.S_PAGE.value=1;	
		frm.S_PAGEGROUP.value=1;
	}else if(movePage=='F' & page=='1'){
		alert("첫 페이지 입니다.");
		return; 
	}else if(movePage=='F' & pageGroup=='1'){
		alert("첫 페이지 입니다.");
		return; 
	}else if(movePage=='F' & pageGroup!='1'){
		frm.S_PAGEGROUP.value=pageGroup-1;
		frm.S_PAGE.value=(pageGroup-2)*<%=paramPage[1]%>+1;
	}else if(movePage=='R' & pageGroup=='1' &pageGroup!=maxPageGroup){
		frm.S_PAGEGROUP.value=pageGroup+1;
		frm.S_PAGE.value=(pageGroup)*<%=paramPage[1]%>+1;
	}else if(movePage=='R' & pageGroup!=maxPageGroup){
		frm.S_PAGEGROUP.value= pageGroup+1;
		frm.S_PAGE.value=(frm.S_PAGEGROUP.value-1)*<%=paramPage[1]%>+1;
	}else if(movePage=='R' & pageGroup==maxPageGroup){
		alert("마지막 페이지 입니다.");
		return; 
	}else if(movePage=='E' & page==maxPage){
		alert("마지막 페이지 입니다.");
		return; 
	}else if(movePage=='E' & page!=maxPage ){
		frm.S_PAGE.value=maxPage;
		frm.S_PAGEGROUP.value=maxPageGroup;
	}else{
		frm.S_PAGE.value=movePage;
		frm.S_PAGEGROUP.value=pageGroup;
	}
	search();
}	
</script>
<!--페이지 처리 START  -->
<% if(idx!=0){ %>
<div width='100%' align='center'>
<input type='hidden' name='S_PAGEGROUPSIZE'  size='5' value="<%=paramPage[1]%>" >
<input type='hidden' name='S_PAGE'  size='5' value="<%=paramPage[2]%>" >
<input type='hidden' name='S_PAGEGROUP' size='5' value="<%=paramPage[3]%>">
<input type='hidden' name='PAGE_GROUP_CNT' size='5' value="<%=set.get("PAGE_GROUP_CNT",0) %>">
<input type='hidden' name='MAX_PAGE' size='5' value="<%=set.get("MAX_PAGE",0) %>">
<input type='hidden' name='MAX_PAGE_GROUP' size='5' value="<%=set.get("MAX_PAGE_GROUP",0) %>">

<span onclick="pageSelect('H',<%=paramPage[2]%>,<%=paramPage[3]%>,<%=set.get("MAX_PAGE",0) %>,<%=set.get("MAX_PAGE_GROUP",0) %>)" style='cursor:hand;cursor:pointer'>
<img src='/images/firstpage.png'>
</span>
<span onclick="pageSelect('F',<%=paramPage[2]%>,<%=paramPage[3]%>,<%=set.get("MAX_PAGE",0) %>,<%=set.get("MAX_PAGE_GROUP",0) %>)" style='cursor:hand;cursor:pointer'>
<img src='/images/frontpage.png'>
</span>&nbsp;
<%
for(int i=0;i<Integer.parseInt(set.get("PAGE_GROUP_CNT",0));i++){ %>
<span  onclick="pageSelect('<%=(Integer.parseInt(paramPage[3])-1)*Integer.parseInt(paramPage[1])+i+1%>',<%=paramPage[2]%>,<%=paramPage[3]%>,<%=set.get("MAX_PAGE",0) %>,<%=set.get("MAX_PAGE_GROUP",0) %>);" style='height:25px;cursor:hand;cursor:pointer;valign:middle'>
	<font size='2'  >
    <%if(Integer.parseInt(paramPage[2])==((Integer.parseInt(paramPage[3])-1)*Integer.parseInt(paramPage[1])+i+1)){%>
		<b><%=(Integer.parseInt(paramPage[3])-1)*Integer.parseInt(paramPage[1])+i+1%></b>
	<%}else{ %>
		<%=(Integer.parseInt(paramPage[3])-1)*Integer.parseInt(paramPage[1])+i+1%>
	<%} %>&nbsp;&nbsp;
	</font>
</span>
<%}%>

<span onclick="pageSelect('R',<%=paramPage[2]%>,<%=paramPage[3]%>,<%=set.get("MAX_PAGE",0) %>,<%=set.get("MAX_PAGE_GROUP",0) %>)" style='cursor:hand;cursor:pointer'>
<img src='/images/nextpage.png'>
</span>
<span onclick="pageSelect('E',<%=paramPage[2]%>,<%=paramPage[3]%>,<%=set.get("MAX_PAGE",0) %>,<%=set.get("MAX_PAGE_GROUP",0) %>)" style='cursor:hand;cursor:pointer'>
<img src='/images/lastpage.png'>
</span>
</div>
<%}%>
<!--페이지 처리 END -->