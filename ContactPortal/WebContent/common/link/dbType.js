function openChgOrgRootWin(org_no, orgcd, orgName, highorg_no,highorgcd,highorgname, idx)
{
	/*
	alert("org_no : " + org_no
	+ "\n orgcd : " + orgcd
	+ "\n orgName : " + orgName
	+ "\n highorg_no : " + highorg_no
	+ "\n highorgcd : " + highorgcd
	+ "\n highorgname : " + highorgname
	+ "\n idx : " + idx);
	*/
	document.all.divChgWin.style.left=20;
	document.all.divChgWin.style.top=900;
	document.all.divChgWin.style.display="none";
	
	var tmpStr = "/CostARs/DistBasis/dbOrgChg.jsp?org_no="+org_no+"&orgCd="+orgcd+"&idx="+idx+"&orgName="+escape(orgName)+"&highDbOrg_no="+highorg_no+"&HighOrgUserCd="+highorgcd+"&highOrgName="+escape(highorgname);
	openWin(tmpStr,"",530,50);
}

function openOrgRootWin(org_no, orgcd, orgName, highorg_no,highorgcd,highorgname, idx)
{
	/*
	alert("org_no : " + org_no
	+ "\n orgcd : " + orgcd
	+ "\n orgName : " + orgName
	+ "\n highorg_no : " + highorg_no
	+ "\n highorgcd : " + highorgcd
	+ "\n highorgname : " + highorgname);
	*/
		
	/*
	alert("event.clientX : " + event.clientX
	+ "\n event.clientY : " + event.clientY);
	*/
	
	var orgWin =  document.getElementById("divChgWin");
	var html = "";
	
	orgWin.innerHTML = html;
	//alert(orgWin.innerHTML);
	html =  "<table cellpadding='0' cellspacing='1' bgcolor='slategray'> "
			+ "	<tr height='15'> "
			+ "		<td align='center'>	"
			+ "		</td> "
			+ "	</tr> "
			+ "	<tr> "	
			+ "		<td bgcolor=white style='padding-left:10;padding-right:10;padding-bottom:10;padding-top:5'> "
			+ "			<input name=btn_ChgOrg type=button value=조직개편  onclick=openChgOrgRootWin('"+org_no+"','"+orgcd+"','"+orgName+"','"+highorg_no+"','"+highorgcd+"','"+highorgname+"','"+idx+"')> "
			+ "			<input name=btn_CancelOrg type=button value=취소  onClick=document.all.divChgWin.style.display='none';> "
			+ "		</td> "
			+ "	</tr> "
			+ "</table> ";
		
	orgWin.insertAdjacentHTML("BeforeEnd",html);
	//alert(orgWin.innerHTML);
	
	document.all.divChgWin.style.left=event.x-15;
	document.all.divChgWin.style.top=event.y-15;
	//document.all.divChgWin.style.zIndex=35;
	document.all.divChgWin.style.display=""		
}

function openDbTypeCdList(empid)
{
	//alert(empid);
	openWin("/costARs/DistBasis/dbTypeList.jsp?_DATA="+empid,"",700,300);
}

function errDbCheck()
{
	var frm = document.forms[0];
	
	if(isEmpty(frm.dbName)){alert("배부표명을 입력하세요");return true;}

	/*
	for(i=0;i<frm.dbOrg_no.length;i++){
		if(isEmpty(frm.dbOrg_no)){
			alert("배부시작을 선택하세요");return true;
		}
	}
	*/
}

function initUptAcct()
{
	var srcDbType = document.form1.pk;
	var chgDbType = parent.document.testForm.dbTypeCd
	
	if(chgDbType !=null)
	{
		if(chgDbType.length == undefined)
		{
			for(j=0; j<srcDbType.length;j++)
			{
				var srcAccType = document.getElementById(srcDbType[j].value);
				if(chgDbType.id == srcAccType.id)
				{
					srcAccType.value = chgDbType.value;
				}
			}
			
		}else{
			for(i=0; i<chgDbType.length;i++)
			{
				for(j=0; j<srcDbType.length;j++)
				{
					var srcAccType = document.getElementById(srcDbType[j].value);
					if(chgDbType[i].id == srcAccType.id)
					{
						srcAccType.value = chgDbType[i].value;
					}
				}				
			}
		}
	}
	
}

function changeAcct(dbOrg_no,dbOrg_UserCd,orgName,acctCd,acctkName,thisDbType)
{
		/*
		alert("dbOrg_no : " + dbOrg_no
		+ "\n dbOrg_UserCd : " + dbOrg_UserCd
		+ "\n orgName : " + orgName
		+ "\n acctCd : " + acctCd
		+ "\n acctkName : " + acctkName
		+ "\n dbTypeCd : " + thisDbType.options[thisDbType.selectedIndex].value);
		*/

		var acctObj = parent.document.getElementById(dbOrg_no+acctCd);
		var dbTypeCd = thisDbType.options[thisDbType.selectedIndex].value;
		var obj = parent.document.getElementById('div_hiddenData');

		var html = "";	
		if(acctObj == null)
		{
			html =  "<table><tr><td> "
					+ "<input type=hidden name=DistributionBasis_Org_Acct.dbOrg_no id=dbOrg_no value='"+dbOrg_no+"'> "
					+ "<input type=hidden name=dbOrg_UserCd id=dbOrg_UserCd  value='"+dbOrg_UserCd+"'> "
					+ "<input type=hidden name=orgName id=orgName  value='"+orgName+"'> "
					+ "<input type=hidden name=acctCd id=acctCd value='"+acctCd+"'> "
					+ "<input type=hidden name=acctkName id=acctkName value='"+acctkName+"'> "
					+ "<input type=hidden name=dbTypeCd id="+dbOrg_no+acctCd+" value='"+dbTypeCd+"'> "
					+ "</td></tr></table> "
			
			obj.insertAdjacentHTML("BeforeEnd",html);
			//alert(obj.innerHTML);
		}else{
			acctObj.value = dbTypeCd;
		}
}

function changeAllAcct(dbOrg_no,dbOrg_UserCd,orgName,thisDbType)
{
		var dbTypeCdAllObj = document.getElementsByName('dbTypeCd');
		var acctCdAllObj =  document.getElementsByName('acctCd');
		var acctkName = '';
		//alert("acctCdAllObj : " + acctCdAllObj);
		//alert("acctCdAllObj.length : " + acctCdAllObj.length);
		
		for(i=0; i<dbTypeCdAllObj.length;i++)
		{
			dbTypeCdAllObj[i].value = thisDbType.value;
			//alert("acctCdAllObj["+i+"].value : " + acctCdAllObj[i].value);
			acctCd = acctCdAllObj[i].value;
			changeAcct(dbOrg_no,dbOrg_UserCd,orgName,acctCd,acctkName,thisDbType);
		}
}

function getDbAccList(db_no, dbOrg_no)
{
	var ifAcct;
	div_dbAcct.location = "/CostARs/DistBasis/uptAcct.jsp?db_no="+db_no+"&dbOrg_no="+dbOrg_no;
}

function saveDb(){

	var frm = document.forms[0];
		
	errDbCheck();
	
	frm.action = 'regDb.do';
	frm._SCREEN.value = '/Car/reloadingSaveOk.jsp';
	frm.target='';
	frm.submit();	
	
}

function uptDb(){

	var frm = document.forms[0];
		
	frm.action = 'uptDb.do';
	frm._SCREEN.value = '/Car/reloadingSaveOk.jsp';
	frm.target='';
	frm.submit();
	
		
}

function addDBType(currentdate){
  
	var html = 	"" +
"<div id='manDiv"+manIdx+"'> "+
"	<table width=100%  align=center cellpadding=1 cellspacing=1 border=0 class=table1> "+
"		<tr> "+
"			<td width=100% class='td2'> "+
"				<table align=center cellpadding=1 cellspacing=1 border=0 width=100%  > "+
"					<tr> "+
"						<td align='left' width=20% height=26 class=TD2> "+
"							<input  name='pjtEmpName'  style= 'width:60%;  text-align:left; background-color: #FFFFFF' size='10' id='pjtEmpName"+manIdx+"'> "+
"							<input type='button' value='????'onClick=popupSearch('emp','referCd"+manIdx+"','','pjtEmpName"+manIdx+"')> "+
"							<input type='hidden' name='referCd' id='referCd"+manIdx+"'> "+
"						</td> "+
"						<td  width=60% height=24 class=TD2 align='left'> "+
"							<input  name='pjtJob' style= 'width:99%;  text-align:left; background-color: #FFFFFF' size='10'> "+
"						</td> "+
"						<td  width=20% height=24 class=TD2 align='left'>"+
"							<input name='pjtEmpStartDt'   style= 'width:50%;  text-align:left; background-color: #FFFFFF' size='20'>&nbsp; "+
"							<input type='button' value='??'  onclick=removeMan(manDiv"+manIdx+",'"+manIdx+"')> "+
"						</td> "+
"					</tr> "+
"			   </table> "+
"			</td> " +
"	    </tr> "+
"    </table> "+
"</div>"; 

	manIdx++;
	manSpan.insertAdjacentHTML('beforeEnd',html);
	//alert(manSpan.innerHTML);
}