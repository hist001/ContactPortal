/*-------- AJAX�� �̿��� webservice (Common Code) ---------*/
var xmlHttp;
var ID;

/*
	   ȣ���Լ�
	   fn_combo(

	   combo��,
	   parameter�迭,
	   �����combo��,
	   comboŸ��(1(name),2(codecd+name)),
	   combo������(Ÿ��2�ΰ�� �̸��� codecd������ ������),
	   xml-id 

	   )
*/
var objParam1
var objParam2
var idx;

function fn_combo(combo_id,parameters){
	//alert('combo_id : ' +combo_id);
	ID=combo_id;
	fn_webservice_call(parameters,document.all(combo_id).xml_id);
}

function fn_dpwSelect(xml_id,parameters,obj1,obj2){
	ID=xml_id;
	objParam1 = obj1;
	objParam2 = obj2;
	fn_webservice_dpw_call(parameters,xml_id);
}

/* xmlhttprequest ��ü ȹ�� */
function fn_createXMLHttpRequest() {
    if (window.ActiveXObject) {
		xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    else if (window.XMLHttpRequest) {
		xmlHttp = new XMLHttpRequest();     
    }
}
/* SERVER comXmlSet call */
function fn_webservice_call(parameters,xml_id){
	fn_createXMLHttpRequest();
	var url = "/common/comXmlSet.jsp?"+fn_makeUri(parameters)+"&fileName0=Combo.xml&idx0="+xml_id+"&entity0=Combo&$tc=1"; 		
	xmlHttp.open("GET", url, false);
	xmlHttp.onreadystatechange = fn_callback;
	xmlHttp.send(null);			
}

function fn_webservice_dpw_call(parameters,xml_id){
	fn_createXMLHttpRequest();
	var url = "/common/comXmlSet.jsp?"+fn_makeUri(parameters)+"&fileName0=DPW.xml&idx0="+xml_id+"&entity0=Combo&$tc=1"; 		
	xmlHttp.open("GET", url, false);
	xmlHttp.onreadystatechange = fn_dpw_callback;
	xmlHttp.send(null);			
}
/* callback �Լ� ���� */
function fn_callback() {
    if (xmlHttp.readyState == 4) {
		if (xmlHttp.status == 200) {
			//alert('xmlHttp : '+ xmlHttp.responseTEXT);
			fn_display(xmlHttp.responseXML.getElementsByTagName("CODE_NO"),
				       xmlHttp.responseXML.getElementsByTagName("CODE_CD"),
				       xmlHttp.responseXML.getElementsByTagName("CODE_NAME")
				);
		}else if (xmlHttp.status == 204){//�����Ͱ� �������� ���� ���
		}else{} 
	}else{
	}
}

function fn_dpw_callback() {
    if (xmlHttp.readyState == 4) {
		if (xmlHttp.status == 200) {
			//alert('xmlHttp : '+ xmlHttp.responseTEXT);
			fn_dpwDisplay(xmlHttp.responseXML.getElementsByTagName("CODE_NO"),
				       xmlHttp.responseXML.getElementsByTagName("CODE_CD"),
				       xmlHttp.responseXML.getElementsByTagName("CODE_NAME")
				);
		}else if (xmlHttp.status == 204){//�����Ͱ� �������� ���� ���
		}else{}
	}else{
	}
}

function fn_dpwDisplay(par1,par2,par3) {
	var sObj1 = objParam1;
	var sObj2 = objParam2;
	var dObj1 = document.getElementById(sObj1);
	var dObj2 = document.getElementById(sObj2);

	if(par1[0] != null){
	dObj1.value = par2[0].firstChild.data;
	dObj2.value = cashReturn(par3[0].firstChild.data);
	}
}

/* UI ���� */
function fn_display(NO_arr,CD_arr,NAME_arr){
	var msg="";	
    var size = NO_arr.length;
    var nextNode;
    //alert('ID : ' +ID);
    var combo=document.all(ID);
    //alert('combo.exid : ' +combo.exid);
    //alert('excombo : ' +excombo);
    var excombo=document.all(combo.exid);

	if(excombo != undefined && combo.exid != undefined){
		combo.onchange=function onchange(){
			fn_combo(excombo.id,new Array(this.options[combo.selectedIndex].value ),excombo.xml_id);
		}
	}
	//alert('combo.options : ' +combo.options)
	combo.options.innerHTML="";
    if(combo.defaultType!=undefined){
		if(combo.defaultType.toUpperCase()=='Y'){
			var addedOpt=document.createElement('OPTION');
			combo.add(addedOpt);
			addedOpt.text="��ü";
			addedOpt.value="";			
		}
	}

	for (var i = 0; i < size; i++) {
	  var addedOpt=document.createElement('OPTION');
	  combo.add(addedOpt);
	  if(combo.flag=="0"){
		  addedOpt.innerText=CD_arr[i].firstChild.data;
	  }else if(combo.flag=="2"){
			if(combo.prefix!=undefined){
				addedOpt.innerText=NO_arr[i].firstChild.data+combo.prefix+NAME_arr[i].firstChild.data;
			}else{
				addedOpt.innerText=NO_arr[i].firstChild.data+":"+NAME_arr[i].firstChild.data;
			}
	  }else{
		  addedOpt.innerText=NAME_arr[i].firstChild.data+"";
	  }
	  
	  
	  addedOpt.value=NO_arr[i].firstChild.data+"";	 
	  if(combo.rev_value!=undefined){
		  if(combo.rev_value==addedOpt.value){

				//���� ���� combo�����
			    if(combo.defaultType!=undefined){
					if(combo.defaultType.toUpperCase()=='Y'){
					  combo.selectedIndex=i+1;					
					}else{
					  combo.selectedIndex=i;					
					}
				} else {
					  combo.selectedIndex=i;					
				}

		  }
	  }
	} 

	//alert('excombo : '+excombo + '   combo.exid : '+combo.exid);
	if(excombo != undefined && combo.exid != undefined){
		fn_combo(excombo.id,new Array(combo.options[combo.selectedIndex].value ),excombo.xml_id);
	}


}
/* AJAX �Ķ���� ��ġ */
function fn_makeUri(parameters){
	//alert();
	var len=0;
	var uri="";
	var value="";
	if(parameters!=null){
		len=parameters.length;
		for(var i=0;i<parameters.length;i++){
			value=parameters[i];
			if(value!=""){
				uri+="&p0_"+i+"="+value;
			}
		}
	}
	uri+="&$leng0="+len;
	return uri;
}