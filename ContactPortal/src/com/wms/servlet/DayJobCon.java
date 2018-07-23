/*************************************************************
*	�� �� ��  : DayJobCon.java
*	�ۼ�����  : 2004/06/22
*	�� �� ��  : 
*	��    ��  : ���Ͼ������� ����(CRUD)�ϴ� Ŭ����
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
				//session ���� ���� �α����� �������� ID �� �̸��� ����
				
				boolean confirmResult = false;
				DayJobReportDTO dto = new DayJobReportDTO();
		        box.copyToEntity(dto);
				IDayJob dayJob = (IDayJob)DAOFactory.getDAO("IDayJob");
				confirmResult = dayJob.confirm(dto);

				if(!confirmResult)
					throw new Exception("���� �����߽��ϴ�.");
				printJspPage(req,res,page);	
				return;


			}else if(_ACT.equals("CA")){//�ǰߵ��
				
				boolean confirmResult = false;
				DayJobReportDTO dto   = new DayJobReportDTO();
				ApOpinionDTO    apDto = new ApOpinionDTO();
		        box.copyToEntity(dto);
				box.copyToEntity(apDto);
				IDayJob dayJob = (IDayJob)DAOFactory.getDAO("IDayJob");
				confirmResult = dayJob.confirm(dto);

				if(!confirmResult)
					throw new Exception("��Ͽ� �����߽��ϴ�.");
				if(dayJob.apOpinionRegist(apDto)){//�ݷ� �� ���
					com.wms.fw.Config conf = new com.wms.fw.Configuration();
					String host     = conf.get("com.wms.mail.server").trim();
					String sender   = apDto.apEmpKName;
					String from     = apDto.apEmpId+"@hist.co.kr";		
					String to       = apDto.reEmpId+"@hist.co.kr";
					String receiver = apDto.reEmpKName;
					String subject  = DateUtil.transDate(apDto.reportDt)+" ���Ͼ������� "+((apDto.apFlag.equals("A"))?"�����ǰ�":"�ݷ�");
					//String content  = apDto.apContents;
					String content  = req.getParameter("apContents");

					Utility.sendResponseMail(from,sender,to,receiver,subject,content,host);
				}
				printJspPage(req,res,page);	
				return;


			}else if(_ACT.equals("S")){//���Ͼ������� ����� ����
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
					throw new Exception("������ �����߽��ϴ�.");  
                if(_SCREEN.equals("confirmUpdate")){
					page="/dayJobCon?"
						   +"_SCREEN=/WorkExec/approvalDayReport.jsp"
					       +"&reEmpId="+dDto.reEmpId+"&_ACT=RD"+"&reportDt="+dDto.reportDt;
				}else{
					printJspPage(req,res,"/WorkExec/saveOk.jsp");	
					return;
				}
			}else if(_ACT.equals("SS")){//������������ ����� ����
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
					throw new Exception("������ �����߽��ϴ�.");
				printJspPage(req,res,"/WorkExec/saveOk.jsp");	
				return;
				
			}else if(_ACT.equals("SU")){//�յ��������� ����
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

			}else if(_ACT.equals("RD")){//���Ͼ������� �ѰǼ���
				String repDate =Utility.replace(box.get("reportDt"),"-");
				IDayJob dayJob =(IDayJob)DAOFactory.getDAO("IDayJob");
                DayJobReportDTO dto=dayJob.dayJobSearch(box.get("reEmpId"), repDate);
			    req.setAttribute("dayJob",dto );
				if(dto!=null&&dto.apFlag.equals("Y"))
					req.setAttribute("opinion", dayJob.opinionSearch(box.get("reEmpId"),repDate));
			    printJspPage(req,res,_SCREEN);	
			    return;

			}else if(_ACT.equals("ORG") ){//���Ͼ������� �ѰǼ���
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
			}else if(_ACT.equals("RTr")){//���Ͼ������� �ѰǼ���
				IDayJob dayJob =(IDayJob)DAOFactory.getDAO("IDayJob");
			    req.setAttribute("tran", dayJob.transSearch(box.get("reEmpId"),box.get("reportDt"),box.get("sn")));				        
			    printJspPage(req,res,_SCREEN);	
			    return;

			}else if(_ACT.equals("SP")||_ACT.equals("SE")){//���� ��ȹ��||����������ü
			    int mode=(_ACT.equals("SP"))?IGroupEmp.SUB_PLANNER_SEARCH:IGroupEmp.SUB_ALL_SEARCH;
			    //�ڽ��� ��å�� ������� ��å�� ���ÿ� ������ ���� �޼���.
				String mgrFg=user.getMgrUnionFlag();
				if(mgrFg.equals("GT"))mgrFg="T";//������ ����� �׳� �����̴�.
				//2005/02/07�������� ���� ����
				if(mgrFg.equals("TE"))mgrFg="E";//�ι����� ����� �׳� �ι����̴�.
				EmpDTO[] empList = null;
				IGroupEmp groupemp =(IGroupEmp)DAOFactory.getDAO("IGroupEmp");
				if(mgrFg.equals("T")||mgrFg.equals("E")){
					mode=IGroupEmp.SUB_PLANNER_SEARCH;
				    empList=groupemp.empSubSearch((user.apKey)?user.apEmpId:user.empId,mode);
				}
				/*else if(mgrFg.equals("TE")){//������ �ι����� ����� ���� ���...
					mode=IGroupEmp.SUB_PLANNER_SEARCH;
				    EmpDTO[] sup=groupemp.empSubSearch(user.apEmpId,mode);
				    EmpDTO[] sub=groupemp.empSubSearch(user.empId,mode);
					empList=new EmpDTO[sup.length+sub.length];

					for(int i=0;i<sup.length;i++){
						//sup[i].empOrgCd=sup[i].empOrgCd.substring(0,1)+"000";
						//2005/02/07�������� ���� ����
						sup[i].empOrgCd=user.apUser.empOrgCd;
						//System.out.println("������ �ι����� ����� ���� ��� ����� ������ȣ:"+user.apUser.empOrgCd);							
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
				if(mgrFg.equals("T"))orgCd=orgCd.substring(0,2)+"00";//�׷����� ���� ����� ���.. ���������� ����...
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
				//2005/02/07�������� ���� ����
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
			}else if(_ACT.equals("RSD")){//���Ͼ��������Է�ȭ���û
			    String reportDt=req.getParameter("reportDt");
				if(reportDt==null){reportDt=com.wms.fw.util.DateUtil.getTodayString();
				}else{
					reportDt=Utility.replace(reportDt,"-");
				}
				IGroupEmp groupemp =(IGroupEmp)DAOFactory.getDAO("IGroupEmp");
				EmpDTO[] empList=groupemp.empSubSearch(user.empId,user.empOrgCd,IGroupEmp.SUB_ALL_SEARCH);
				boolean flag=false;//���� �����Ǵ�(true==����)
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
					System.out.println("���Ͼ������� ��ȸ======>");
					req.setAttribute("opinion", dayJob.opinionSearch(empId,reportDt));
				printJspPage(req,res,_SCREEN);
				return;			
			}else if(_ACT.equals("RSU")){//�����ϱ� ���� �����˻�
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
			}else if(_ACT.equals("RUR")){//�յ� ������������ �����˻�
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
			//listó�� Test
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
			//��¥�ְ� �˻�
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
	
			}else if(_ACT.equals("RSY")){//���� ���Ͼ��������Է�ȭ���û
			    String reportDt=req.getParameter("reportDt");
			    IDayJob dayJob =(IDayJob)DAOFactory.getDAO("IDayJob");
				String[] reportDts= dayJob.preReportDtSearch(empId);
				if(reportDts==null)throw new Exception("�̹� ���� �Ǿ��ų�,�ۼ��� �� �����ϴ�.");
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
			}else if(_ACT.equals("RSA")){//���� ���Ͼ��������Է�ȭ���û ,�����ڰ� ���������ڸ� ��û
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
			}else if(_ACT.equals("RN")){//���� ���� �ִ����� ���� 

				IDayJob dayJob =(IDayJob)DAOFactory.getDAO("IDayJob");
				String actFlag = box.get("actFlag");
				DayJobReportDTO dto=dayJob.nextRepSearch(user.empId,actFlag);
				String url="/WorkExec/approvalDayReport.jsp";
				if(dto!=null){//�������� ������ ������ ���
					dto=dayJob.dayJobSearch(dto.reEmpId, dto.reportDt);
					req.setAttribute("dayJob",dto );				        
					if(dto.apFlag.equals("Y"))
						req.setAttribute("opinion", dayJob.opinionSearch(dto.reEmpId,dto.reportDt));
					
				}else{//�������� ������ ���� ���
					boolean key=false;
					if(actFlag.equals("WEO")){//������������ΰ��
						IHJob hJob = (IHJob)DAOFactory.getDAO("IHJob");
						HJobDTO[] jobList = hJob.selectHJobConfList((user.apKey&&user.getManagerFlag().equals("Y"))?user.apEmpId:empId);
						if(jobList!=null){
							//����ǥ �Ѱ� ��������
							HJobDTO returns = hJob.selectHJob(jobList[0].empId, 
								                               jobList[0].revisionNo, 
								                               jobList[0].statusFlag);
							req.setAttribute("confirmYN","Y");
							req.setAttribute("returns",returns);				
							url="/HJob/updateHJob.jsp";							
						}else{
							key=true;
						}
					}else{//���Ϻ���̽����ΰ��
						key=true;
					}
					if(key){
						req.setAttribute("endFlag","Y");
						req.setAttribute("msg","���̻� ������ ���� �����ϴ�.");
						url="/common/approval_ConfirmOk.jsp";
					}
				}
				printJspPage(req,res,url);
				return;			
			}else if(_ACT.equals("UBiz")){//�ŷ�ó �ּҰ����
			    String bizAcqCd=box.get("bizAcqCd");
			    String loc=box.get("loc");

				IDayJob dayJob =(IDayJob)DAOFactory.getDAO("IDayJob");
				if(dayJob.updateLoc(bizAcqCd,loc)){
					printJspPage(req,res,page);
					return;
				}else{
					throw new Exception("������ �����߽��ϴ�.");
				}
			}
			//ȭ���� ��������ִ� �޼���
			progressMessagePage(req,res,page);
		} catch(Exception e) {
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
        		erorrMessagePage(req,res,e);
		}
	}
	
	
}
