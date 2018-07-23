/*************************************************************
*	파 일 명  : DayJobCon.java
*	작성일자  : 2004/06/22
*	작 성 자  : 
*	내    용  : 일일업무보고를 제어(CRUD)하는 클래스
*************************************************************/
package com.wms.servlet;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.wms.fw.servlet.*;
import com.wms.fw.*;
import com.wms.fw.util.*;
import com.wms.beans.dto.*;
import com.wms.beans.dao.*;
import com.wms.common.beans.*;
import java.util.*;
public class DayJobCon extends WmsServlet{

	protected void performTask(HttpServletRequest req, HttpServletResponse res)
		throws Exception {
		try {
			Box box = HttpUtility.getBox(req);
			String _ACT=box.get("_ACT");
			String _SCREEN=box.get("_SCREEN");			
			HttpSession session = req.getSession();
			UserInfo user =(UserInfo)session.getAttribute("user");
			String page=_SCREEN;
			String empId = req.getParameter("empId");
			if(empId==null)
				empId = user.empId;
			if(_ACT.equals("C")){//confirm
				//session 에서 현재 로그인한 승인자의 ID 와 이름을 받음
				
				boolean confirmResult = false;
				DayJobReportDTO dto = new DayJobReportDTO();
		        box.copyToEntity(dto);
				IDayJob dayJob = (IDayJob)DAOFactory.getDAO("IDayJob");
				confirmResult = dayJob.confirm(dto);

				if(!confirmResult)
					throw new Exception("승인 실패했습니다.");
				printJspPage(req,res,page);	
				return;


			}else if(_ACT.equals("CA")){//의견등록
				
				boolean confirmResult = false;
				DayJobReportDTO dto   = new DayJobReportDTO();
				ApOpinionDTO    apDto = new ApOpinionDTO();
		        box.copyToEntity(dto);
				box.copyToEntity(apDto);
				IDayJob dayJob = (IDayJob)DAOFactory.getDAO("IDayJob");
				confirmResult = dayJob.confirm(dto);

				if(!confirmResult)
					throw new Exception("등록에 실패했습니다.");
				if(dayJob.apOpinionRegist(apDto)){//반려 인 경우
					com.wms.fw.Config conf = new com.wms.fw.Configuration();
					String host     = conf.get("com.wms.mail.server").trim();
					String sender   = apDto.apEmpKName;
					String from     = apDto.apEmpId+"@hist.co.kr";		
					String to       = apDto.reEmpId+"@hist.co.kr";
					String receiver = apDto.reEmpKName;
					String subject  = DateUtil.transDate(apDto.reportDt)+" 일일업무보고 "+((apDto.apFlag.equals("A"))?"승인의견":"반려");
					//String content  = apDto.apContents;
					String content  = req.getParameter("apContents");

					Utility.sendResponseMail(from,sender,to,receiver,subject,content,host);
				}
				printJspPage(req,res,page);	
				return;


			}else if(_ACT.equals("S")){//일일업무보고 저장및 수정
			    String flag="R";
                if(_SCREEN.equals("confirmUpdate"))
					flag="C";
				DayJobReportDTO dDto=new DayJobReportDTO();
				box.copyToEntity(dDto);		
				
				System.out.println("box:"+box);
				
				JobReportDTO[] dtos=(JobReportDTO[])box.copyToEntities("com.wms.beans.dto.JobReportDTO");
								 
				dDto.plans=dtos;
				IDayJob job = (IDayJob)DAOFactory.getDAO("IDayJob");
				System.out.println("-------foodChkVar2--------->"+box.get("foodChkVar2"));
				if(!job.save((DayJobReportDTO)user.temp,dDto,flag,box.get("foodChkVar"),box.get("foodChkVar2"),box.get("orgNo")))
					throw new Exception("저장을 실패했습니다.");  
                if(_SCREEN.equals("confirmUpdate")){
					page="/dayJobCon?"
						   +"_SCREEN=/WorkExec/approvalDayReport.jsp"
					       +"&reEmpId="+dDto.reEmpId+"&_ACT=RD"+"&reportDt="+dDto.reportDt;
				}else{
					printJspPage(req,res,"/WorkExec/saveOk.jsp");	
					return;
				}
			}else if(_ACT.equals("SS")){//영업업무보고 저장및 수정
			    String flag="S";
				DayJobReportDTO dDto=new DayJobReportDTO();
				box.copyToEntity(dDto);		
				JobReportDTO[] dtos=(JobReportDTO[])box.copyToEntities("com.wms.beans.dto.JobReportDTO");
                TransDetailDTO[] tDtos=(TransDetailDTO[])box.copyToEntities("com.wms.beans.dto.TransDetailDTO");				
				
				for(int i=0;i<dtos.length;i++){
					dtos[i].transDetailDTO=tDtos[i];
				}
				dDto.plans=dtos;    
				IDayJob job = (IDayJob)DAOFactory.getDAO("IDayJob");
				   
				String foodChkVar2 ;  
				if(!box.get("orgName").equals("")){ 
					foodChkVar2 ="Y"; 
				}else if(box.get("orgName").equals("")){    
					foodChkVar2="N";	  
				}else{
					foodChkVar2="M";					
				}
				 
				if(!job.save((DayJobReportDTO)user.temp,dDto,flag, box.get("foodChkVar"),foodChkVar2,box.get("orgNo")))
					throw new Exception("저장을 실패했습니다.");
				printJspPage(req,res,"/WorkExec/saveOk.jsp");	
				return;
				
			}else if(_ACT.equals("SU")){//합동업무보고 저장
				DayJobReportDTO dDto=new DayJobReportDTO();
				box.copyToEntity(dDto);		
				JobReportDTO[] dtos=(JobReportDTO[])box.copyToEntities("com.wms.beans.dto.JobReportDTO");
				AsdEmpDTO[] empList=(AsdEmpDTO[])box.copyToEntities("com.wms.beans.dto.AsdEmpDTO");
				dDto.plans=dtos;
				//System.out.println(box);
				IDayJob job = (IDayJob)DAOFactory.getDAO("IDayJob");
				
				String message=job.unionSave(dDto,empList);
				req.setAttribute("groupMsg",message);
			    printJspPage(req,res,"/WorkExec/saveOk.jsp");	
			    return;

			}else if(_ACT.equals("RD")){//일일업무보고 한건선택
				String repDate =Utility.replace(box.get("reportDt"),"-");
				IDayJob dayJob =(IDayJob)DAOFactory.getDAO("IDayJob");
                DayJobReportDTO dto=dayJob.dayJobSearch(box.get("reEmpId"), repDate);
			    req.setAttribute("dayJob",dto );
				if(dto!=null&&dto.apFlag.equals("Y"))
					req.setAttribute("opinion", dayJob.opinionSearch(box.get("reEmpId"),repDate));
			    printJspPage(req,res,_SCREEN);	
			    return;

			}else if(_ACT.equals("ORG") ){//일일업무보고 한건선택
				String managerFlag = user.getManagerFlag();
				String userOrgCd = user.empOrgCd;
				String teamCd = box.get("teamCd");
				
				IOrg org =(IOrg)DAOFactory.getDAO("IOrg");
				OrgDTO[] orgdtoT = org.searchTeamOrg(managerFlag, userOrgCd);
				OrgDTO[] orgdtoG = org.searchGroupOrg(teamCd, managerFlag, userOrgCd);

				req.setAttribute("searchTeamOrg", orgdtoT);
				req.setAttribute("searchGroupOrg", orgdtoG);
			
				printJspPage(req,res,_SCREEN);	
				return;
			}else if(_ACT.equals("RTr")){//일일업무보고 한건선택
				IDayJob dayJob =(IDayJob)DAOFactory.getDAO("IDayJob");
			    req.setAttribute("tran", dayJob.transSearch(box.get("reEmpId"),box.get("reportDt"),box.get("sn")));				        
			    printJspPage(req,res,_SCREEN);	
			    return;

			}else if(_ACT.equals("SP")||_ACT.equals("SE")){//하위 계획자||하위직원전체
			    int mode=(_ACT.equals("SP"))?IGroupEmp.SUB_PLANNER_SEARCH:IGroupEmp.SUB_ALL_SEARCH;
			    //자신의 직책과 대결자의 직책을 동시에 가지고 오는 메서드.
				String mgrFg=user.getMgrUnionFlag();
				if(mgrFg.equals("GT"))mgrFg="T";//팀장의 대결은 그냥 팀장이다.
				//2005/02/07개편이후 조직 수정
				if(mgrFg.equals("TE"))mgrFg="E";//부문장의 대결은 그냥 부문장이다.
				EmpDTO[] empList = null;
				IGroupEmp groupemp =(IGroupEmp)DAOFactory.getDAO("IGroupEmp");
				if(mgrFg.equals("T")||mgrFg.equals("E")){
					mode=IGroupEmp.SUB_PLANNER_SEARCH;
				    empList=groupemp.empSubSearch((user.apKey)?user.apEmpId:user.empId,mode);
				}
				/*else if(mgrFg.equals("TE")){//팀장이 부문장의 대결을 겸할 경우...
					mode=IGroupEmp.SUB_PLANNER_SEARCH;
				    EmpDTO[] sup=groupemp.empSubSearch(user.apEmpId,mode);
				    EmpDTO[] sub=groupemp.empSubSearch(user.empId,mode);
					empList=new EmpDTO[sup.length+sub.length];

					for(int i=0;i<sup.length;i++){
						//sup[i].empOrgCd=sup[i].empOrgCd.substring(0,1)+"000";
						//2005/02/07개편이후 조직 수정
						sup[i].empOrgCd=user.apUser.empOrgCd;
						//System.out.println("팀장이 부문장의 대결을 겸할 경우 대결자 조직번호:"+user.apUser.empOrgCd);							
						empList[i]=sup[i];
					}
					for(int i=0;i<sub.length;i++){
						sub[i].empOrgCd=sub[i].empOrgCd.substring(0,2)+"00";
						empList[i+sup.length]=sub[i];
					}

				}*/
				else{
					empList=groupemp.empSubSearch((user.apKey)?user.apEmpId:user.empId,mode);
				}
				String orgCd =  req.getParameter("orgCd");
				String startDt= req.getParameter("startDt");
				String endDt=   req.getParameter("endDt");
				String reportDt=req.getParameter("reportDt");

				if(orgCd==null)orgCd=user.empOrgCd;
				if(mgrFg.equals("T"))orgCd=orgCd.substring(0,2)+"00";//그룹장이 팀장 대결인 경우.. 조직관리를 위해...
				if(startDt==null){startDt=com.wms.fw.util.DateUtil.getTodayString();
				}else{
					startDt=Utility.replace(startDt,"-");
				}
				if(endDt==null){endDt=com.wms.fw.util.DateUtil.getTodayString();
				}else{
					endDt=Utility.replace(endDt,"-");
				}
				if(reportDt==null){reportDt=com.wms.fw.util.DateUtil.getTodayString();
				}else{
					reportDt=Utility.replace(reportDt,"-");
				}
			    //if(mgrFg.equals("T")||mgrFg.equals("E")){
				//2005/02/07개편이후 조직 수정
				if(mgrFg.equals("T")){
					ArrayList list = new ArrayList();
                    
					list.add(empList);
					
					req.setAttribute("empList", list);

				}else{
					req.setAttribute("empList", WMSUtility.classify(empList,user.empOrgCd));

				}
				IDayJob dayJob =(IDayJob)DAOFactory.getDAO("IDayJob");				
				DayJobReportDTO[] dtoList=null;
				HJobDTO[]         jobList=null;
				if(_ACT.equals("SP")){				
					IHJob hJob = (IHJob)DAOFactory.getDAO("IHJob");
					dtoList=dayJob.confirmSearch((user.apKey&&user.getManagerFlag().equals("Y"))?user.apEmpId:empId, 					                               
					                              orgCd,"",""
					                             );	
					jobList = hJob.selectHJobConfList((user.apKey&&user.getManagerFlag().equals("Y"))?user.apEmpId:empId);
					req.setAttribute("jobList", jobList);
				}else{	
					dtoList=dayJob.dayJobSubSearch(empId, 
					                               startDt,
						                           endDt,                 
					                                             orgCd,
					                                             IGroupEmp.SUB_ALL_SEARCH);				
             	}
				req.setAttribute("dtoList", dtoList);
				
				printJspPage(req,res,_SCREEN);
				return;
			}else if(_ACT.equals("RSD")){//일일업무보고입력화면요청
			    String reportDt=req.getParameter("reportDt");
				if(reportDt==null){reportDt=com.wms.fw.util.DateUtil.getTodayString();
				}else{
					reportDt=Utility.replace(reportDt,"-");
				}
				IGroupEmp groupemp =(IGroupEmp)DAOFactory.getDAO("IGroupEmp");
				EmpDTO[] empList=groupemp.empSubSearch(user.empId,user.empOrgCd,IGroupEmp.SUB_ALL_SEARCH);
				boolean flag=false;//겸직 유무판단(true==겸직)
				int count=0;
				for(int i=0;i<empList.length;i++){
						System.out.println(empList[i].empId);
						if(empList[i].empId.equals(user.empId))count++;
						if(count>1){
							flag=true;break;
						}
				}
			    if(user.getManagerFlag().equals("T")||user.getManagerFlag().equals("E")||user.getManagerFlag().equals("GG")||user.empId.equals("20000038")){
					session.setAttribute("empList", empList);

				}else{
					if(flag){
						session.setAttribute("empList", groupemp.groupSubSearch(user.empId,IGroupEmp.SUB_ALL_SEARCH));
					}else{
						//ArrayList list=Utility.classify(empList,user.empOrgCd);
						//session.setAttribute("empList", Utility.classify(empList,user.empOrgCd).get(0));
						session.setAttribute("empList", empList);
					}

				}
				IDayJob dayJob =(IDayJob)DAOFactory.getDAO("IDayJob");
				DayJobReportDTO dto=dayJob.dayJobSearch(empId, reportDt);
				if(_SCREEN.equals("/WorkExec/saleDayReport.jsp")){
					String returns= dayJob.searchCarPrice(empId);
					if(returns!=null){
						req.setAttribute("price",returns.substring(returns.indexOf(":")+1));
						req.setAttribute("carType",returns.substring(0,returns.indexOf(":")));
					}
				}
				user.temp=dto;
				req.setAttribute("dayJob", dto);
				if(dto!=null&&dto.apFlag!=null&&dto.apFlag.equals("Y"))
					System.out.println("일일업무보고 조회======>");
					req.setAttribute("opinion", dayJob.opinionSearch(empId,reportDt));
				printJspPage(req,res,_SCREEN);
				return;			
			}else if(_ACT.equals("RSU")){//배정하기 위한 직원검색
				IGroupEmp groupemp =(IGroupEmp)DAOFactory.getDAO("IGroupEmp");

				EmpDTO[] empList=null;
				if(!user.getManagerFlag().equals("Y")){
				  empList=groupemp.empSubSearch(user.empId,user.empOrgCd,IGroupEmp.SUB_ALL_SEARCH);
				}else{
				  empList=groupemp.empComradeSearch(user.empId,user.empOrgCd);
				}
				req.setAttribute("empSubList", WMSUtility.classify(empList,user.empOrgCd).get(0));
				printJspPage(req,res,_SCREEN);
				return;			
			}else if(_ACT.equals("RUR")){//합동 업무보고에서의 직원검색
				IGroupEmp groupemp =(IGroupEmp)DAOFactory.getDAO("IGroupEmp");
				EmpDTO[] empList=null;
				empList=groupemp.empUnionSearch(user.empId,user.empOrgCd,req.getParameter("reportDt"),(user.getManagerFlag().equals("Y"))?false:true);

				//empList=groupemp.empSubSearch(user.empId,user.empOrgCd,IGroupEmp.SUB_ALL_SEARCH);
				ArrayList eList =WMSUtility.classify(empList,user.empOrgCd);
				Object obj=null;
				if(eList!=null)
					obj=eList.get(0);
				req.setAttribute("empSubList",obj );

				printJspPage(req,res,_SCREEN);
				return;			
			}else if(_ACT.equals("DLR")){
				IDayJob dayJob =(IDayJob)DAOFactory.getDAO("IDayJob");
				empId = req.getParameter("empId");
				String orgCd = req.getParameter("orgCd");
				String startDt= req.getParameter("startDt");
				String endDt=   req.getParameter("endDt");
				String managerFlag = req.getParameter("managerFlag");
                		if(empId==null || empId.equals("")) 
                			empId = user.empId;
                		if(orgCd==null || orgCd.equals("")) 
                			orgCd = user.empOrgCd;
                		if(managerFlag==null || managerFlag.equals("")) 
                			managerFlag = user.getManagerFlag();

				DayJobReportInqDTO[] dtoList =dayJob.dayJobSearch2(empId,startDt,endDt,orgCd,managerFlag);
				req.setAttribute("dayJobSearch2", dtoList);
				printJspPage(req,res,_SCREEN);
				return;
			//list처리 Test
			}else if(_ACT.equals("DLR1")){
				IDayJob dayJob =(IDayJob)DAOFactory.getDAO("IDayJob");
				empId = req.getParameter("empId");
				String orgCd = req.getParameter("orgCd");
				String startDt= req.getParameter("startDt");
				String endDt=   req.getParameter("endDt");
				String managerFlag = req.getParameter("managerFlag");
                		if(empId==null || empId.equals("")) 
                			empId = user.empId;
                		if(orgCd==null || orgCd.equals("")) 
                			orgCd = user.empOrgCd;
                		if(managerFlag==null || managerFlag.equals("")) 
                			managerFlag = user.getManagerFlag();

				DayJobReportInqDTO[] dtoList =dayJob.dayJobSearch3(empId,startDt,endDt,orgCd,managerFlag);
				req.setAttribute("dayJobSearch3", dtoList);
				printJspPage(req,res,_SCREEN);
				return;
			//날짜넣고 검색
			}else if(_ACT.equals("DLR2")){
				IDayJob dayJob =(IDayJob)DAOFactory.getDAO("IDayJob");
				empId = req.getParameter("empId");
				String orgCd = req.getParameter("orgCd");
				String startDt= req.getParameter("startDt");
				String endDt=   req.getParameter("endDt");
				String keyword = req.getParameter("keyword");
				String managerFlag = req.getParameter("managerFlag");
                		if(empId==null || empId.equals("")) 
                			empId = user.empId;
                		if(orgCd==null || orgCd.equals("")) 
                			orgCd = user.empOrgCd;
                		if(managerFlag==null || managerFlag.equals("")) 
                			managerFlag = user.getManagerFlag();

				DayJobReportInqDTO[] dtoList =dayJob.dayJobSearch4(empId,startDt,endDt,orgCd,managerFlag,keyword);
				req.setAttribute("dayJobSearch3", dtoList);
				printJspPage(req,res,_SCREEN);
				return;
	
			}else if(_ACT.equals("RSY")){//전일 일일업무보고입력화면요청
			    String reportDt=req.getParameter("reportDt");
			    IDayJob dayJob =(IDayJob)DAOFactory.getDAO("IDayJob");
				String[] reportDts= dayJob.preReportDtSearch(empId);
				if(reportDts==null)throw new Exception("이미 승인 되었거나,작성할 수 없습니다.");
				if(reportDt==null){reportDt=reportDts[0];
				}else{
					reportDt=Utility.replace(reportDt,"-");
				}
				if(reportDt.equals(com.wms.fw.util.DateUtil.getTodayString()))
					reportDt=reportDts[0];
				DayJobReportDTO dto=dayJob.dayJobSearch(empId, reportDt);
				if(_SCREEN.equals("/WorkExec/salePreDayReport.jsp")){
					String returns= dayJob.searchCarPrice(empId);
					if(returns!=null){
						req.setAttribute("price",returns.substring(returns.indexOf(":")+1));
						req.setAttribute("carType",returns.substring(0,returns.indexOf(":")));
					}
				}
				user.temp=dto;
				req.setAttribute("dayJob", dto);
				req.setAttribute("reportDts",reportDts );
				if(dto!=null&&dto.apFlag.equals("Y"))
					req.setAttribute("opinion", dayJob.opinionSearch(empId,reportDt));
				printJspPage(req,res,_SCREEN);
				return;			
			}else if(_ACT.equals("RSA")){//전일 일일업무보고입력화면요청 ,승인자가 하위보고자를 요청
			    String reportDt=req.getParameter("reportDt");
				String[] reportDts= new String[1];
				reportDts[0]=reportDt;

				IDayJob dayJob =(IDayJob)DAOFactory.getDAO("IDayJob");
				DayJobReportDTO dto=dayJob.dayJobSearch(empId, reportDt);
				if(_SCREEN.equals("/WorkExec/salePreDayReport.jsp")){
					String returns= dayJob.searchCarPrice(empId);
					if(returns!=null){
						req.setAttribute("price",returns.substring(returns.indexOf(":")+1));
						req.setAttribute("carType",returns.substring(0,returns.indexOf(":")));
					}
				}
				user.temp=dto;
				req.setAttribute("dayJob", dto);
				req.setAttribute("reportDts",reportDts );
				printJspPage(req,res,_SCREEN);
				return;			
			}else if(_ACT.equals("RN")){//다음 건이 있는지에 대한 

				IDayJob dayJob =(IDayJob)DAOFactory.getDAO("IDayJob");
				String actFlag = box.get("actFlag");
				DayJobReportDTO dto=dayJob.nextRepSearch(user.empId,actFlag);
				String url="/WorkExec/approvalDayReport.jsp";
				if(dto!=null){//업무보고 승인이 존재할 경우
					dto=dayJob.dayJobSearch(dto.reEmpId, dto.reportDt);
					req.setAttribute("dayJob",dto );				        
					if(dto.apFlag.equals("Y"))
						req.setAttribute("opinion", dayJob.opinionSearch(dto.reEmpId,dto.reportDt));
					
				}else{//업무보고 승인이 없는 경우
					boolean key=false;
					if(actFlag.equals("WEO")){//업무보고승인인경우
						IHJob hJob = (IHJob)DAOFactory.getDAO("IHJob");
						HJobDTO[] jobList = hJob.selectHJobConfList((user.apKey&&user.getManagerFlag().equals("Y"))?user.apEmpId:empId);
						if(jobList!=null){
							//직무표 한개 가져오기
							HJobDTO returns = hJob.selectHJob(jobList[0].empId, 
								                               jobList[0].revisionNo, 
								                               jobList[0].statusFlag);
							req.setAttribute("confirmYN","Y");
							req.setAttribute("returns",returns);				
							url="/HJob/updateHJob.jsp";							
						}else{
							key=true;
						}
					}else{//일일보고미승인인경우
						key=true;
					}
					if(key){
						req.setAttribute("endFlag","Y");
						req.setAttribute("msg","더이상 승인할 건이 없습니다.");
						url="/common/approval_ConfirmOk.jsp";
					}
				}
				printJspPage(req,res,url);
				return;			
			}else if(_ACT.equals("UBiz")){//거래처 주소값등록
			    String bizAcqCd=box.get("bizAcqCd");
			    String loc=box.get("loc");

				IDayJob dayJob =(IDayJob)DAOFactory.getDAO("IDayJob");
				if(dayJob.updateLoc(bizAcqCd,loc)){
					printJspPage(req,res,page);
					return;
				}else{
					throw new Exception("저장을 실패했습니다.");
				}
			}
			//화면을 진행시켜주는 메서드
			progressMessagePage(req,res,page);
		} catch(Exception e) {
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
        		erorrMessagePage(req,res,e);
		}
	}
	
	
}
