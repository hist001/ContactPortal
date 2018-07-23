/*************************************************************
*	파 일 명  : DayErrCon.java
*	작성일자  : 2004/06/22
*	작 성 자  : 
*	내    용  : 에러보고 제어(CRUD)처리 클래스
*************************************************************/
package com.wms.servlet;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.wms.fw.servlet.*;
import com.wms.fw.*;
import com.wms.beans.dto.*;
import com.wms.beans.dao.*;
import com.wms.common.beans.*;
import java.util.*;
public class DayErrCon extends WmsServlet{
//JYHGBYUGBY
////IJNUHYUHBY
	protected void performTask(HttpServletRequest req, HttpServletResponse res)
		throws Exception {
		try {
			Box box = HttpUtility.getBox(req);
			String _ACT=box.get("_ACT");
			String _SCREEN=box.get("_SCREEN");			
			HttpSession session = req.getSession();
			UserInfo user =(UserInfo)session.getAttribute("user");
			String page=_SCREEN;
            
			if(_ACT.equals("RDL")){//DailyReportLog
  
			    IDayErr dayErr =(IDayErr)DAOFactory.getDAO("IDayErr");
			    DayErrDTO[] dayerrdtos = dayErr.dayErrSearch(box);
			    req.setAttribute("dayErrList", dayerrdtos);				        			    

			    printJspPage(req,res,_SCREEN);	
			    return;

			}else if(_ACT.equals("RDLP")){//DailyReportLog-페이징 처리를 위한

			    IDayErr dayErr =(IDayErr)DAOFactory.getDAO("IDayErr");
			    String strPageNum = box.get("pageNum");
			    if(strPageNum==null || strPageNum.equals("")){
				session.removeAttribute("pageinfo");
				strPageNum="1";
			    }
			    int pageNum = Integer.parseInt(strPageNum);
			    com.wms.fw.Config conf = new com.wms.fw.Configuration();
			    String strPageSize = conf.get("com.wms.pageSize");
			    int pageSize = Integer.parseInt(strPageSize);
			    DayErrDTO[] dayerrdtos = dayErr.dayErrSearchPage(box,pageNum,pageSize);
				//Defalut 이름으로 검색시 없는 경우, 한문자를 줄려 검색을 다시함.2005-07-18 (조원호)
				if(dayerrdtos==null || dayerrdtos.length==0){
					int leng=box.get("empKName").length();
					if(leng >= 2){
						box.put("empKName",box.get("empKName").substring(0,leng-1));
						dayerrdtos = dayErr.dayErrSearchPage(box,pageNum,pageSize);
					}
				}

			    PageInfo pageInfo = new PageInfo(dayerrdtos,pageNum,pageSize, (dayerrdtos!=null)?dayerrdtos[0].totCnt:0,new DayErrDTO());
			    session.setAttribute("pageinfo", pageInfo);
			    
			    //상태값 가져오기
			    StatusDTO[] statusdto = dayErr.searchStatus();
			    req.setAttribute("searchStatus",statusdto);
			    
			    printJspPage(req,res,_SCREEN);	
			    return;
			    
			}else if(_ACT.equals("RDLP2")){//DailyReportLog-페이징 처리를 위한
			    IDayErr dayErr =(IDayErr)DAOFactory.getDAO("IDayErr");
			    String strPageNum = box.get("pageNum");
			    if(strPageNum==null || strPageNum.equals("")){
				session.removeAttribute("pageinfo");
				strPageNum="1";
			    } 
			    int pageNum = Integer.parseInt(strPageNum);
			    com.wms.fw.Config conf = new com.wms.fw.Configuration();
			    String strPageSize = "15";//conf.get("com.wms.pageSize");
			    int pageSize = Integer.parseInt(strPageSize);
			    DayErrDTO[] dayerrdtos = dayErr.dayErrSearchPage4TL(box,pageNum,pageSize);
				//Defalut 이름으로 검색시 없는 경우, 한문자를 줄여 검색을 다시함.2005-07-18 (조원호)
				if(dayerrdtos==null || dayerrdtos.length==0){
					int leng=box.get("empKName").length();
					if(leng >= 2){
						box.put("empKName",box.get("empKName").substring(0,leng-1));
						dayerrdtos = dayErr.dayErrSearchPage4TL(box,pageNum,pageSize);
					}
				}

			    PageInfo pageInfo = new PageInfo(dayerrdtos,pageNum,pageSize, (dayerrdtos!=null)?dayerrdtos[0].totCnt:0,new DayErrDTO());
			    session.setAttribute("pageinfo", pageInfo);
			    
			    //상태값 가져오기
			    StatusDTO[] statusdto = dayErr.searchStatus();
			    req.setAttribute("searchStatus",statusdto);
			    
			    printJspPage(req,res,_SCREEN);	
			    return;

			
			}else if(_ACT.equals("RMA")){//DailyReportLogMissing-일일미작성조회
 			    IDayErr dayErr =(IDayErr)DAOFactory.getDAO("IDayErr");
 			    DayErrDTO[] dayerrdtos = dayErr.dayErrMiSearch(box);
			    req.setAttribute("dayErrList", dayerrdtos);				        

			    printJspPage(req,res,_SCREEN);	
			    return;

			}else if(_ACT.equals("RM")){//DailyReportLogMissing-일일미작성조회-paging
 			    IDayErr dayErr =(IDayErr)DAOFactory.getDAO("IDayErr");
			    String strPageNum = box.get("pageNum");
 			    if(strPageNum==null || strPageNum.equals("")){
				session.removeAttribute("pageinfo");
				strPageNum="1";
			    }
			    int pageNum = Integer.parseInt(strPageNum);
			    com.wms.fw.Config conf = new com.wms.fw.Configuration();
			    String strPageSize = conf.get("com.wms.pageSize");
			    int pageSize = Integer.parseInt(strPageSize);
 
 			    DayErrDTO[] dayerrdtos = dayErr.dayErrMiSearch(box,pageNum,pageSize);
			    //req.setAttribute("dayErrList", dayerrdtos);				        

			    PageInfo pageInfo = new PageInfo(dayerrdtos,pageNum,pageSize, (dayerrdtos!=null)?dayerrdtos[0].totCnt:0,new DayErrDTO());
			    session.setAttribute("pageinfo", pageInfo);

			    //상태값 가져오기
			    StatusDTO[] statusdto = dayErr.searchStatus();
			    req.setAttribute("searchStatus",statusdto);

			    printJspPage(req,res,_SCREEN);	
			    return;

			}else if(_ACT.equals("RDG")){//업무 승인자재작성및 미승인 검색.
                		String empId = req.getParameter("empId");
				if(empId==null)empId=user.empId;
				IDayErr dayErr =(IDayErr)DAOFactory.getDAO("IDayErr");
				DayErrDTO[] dtos=dayErr.dayErrApSearch(box,empId);
				
			    req.setAttribute("dayErrList",dtos );				        
			    printJspPage(req,res,_SCREEN);	
			    return;				
			}else if(_ACT.equals("UR")){
				DayErrDTO dto = new DayErrDTO();
				box.copyToEntity(dto);
				IDayErr dayErr =(IDayErr)DAOFactory.getDAO("IDayErr");
                dayErr.updateDaily(dto);

			}else if(_ACT.equals("RSD")){//재작성 보고..

				EmpDTO[] empList=new EmpDTO[1];
				EmpDTO emp = new EmpDTO();
				emp.empId=user.empId;
				emp.empKName=user.empKName;
				empList[0]=emp;
				session.setAttribute("empList", empList);
				IDayJob dayJob =(IDayJob)DAOFactory.getDAO("IDayJob");								
				DayJobReportDTO dto=dayJob.dayJobSearch(user.empId, Utility.replace(box.get("reportDt"),"-"));
				if(_SCREEN.equals("/ErrorMissing/remakeSaleReport.jsp")){
					String returns= dayJob.searchCarPrice(user.empId);
					if(returns!=null){
						req.setAttribute("price",returns.substring(returns.indexOf(":")+1));
						req.setAttribute("carType",returns.substring(0,returns.indexOf(":")));
					}
				}

				//user.temp=dto;
				req.setAttribute("dayJob", dto);
				if(dto!=null&&dto.apFlag!=null&&dto.apFlag.equals("Y"))
					req.setAttribute("opinion", dayJob.opinionSearch(user.empId, Utility.replace(box.get("reportDt"),"-")));

				printJspPage(req,res,_SCREEN);
				return;			
			}else if(_ACT.equals("RLR")){//업무보고미승인 리스트
				String empId = new String();
				empId = user.empId;
				String role = new String();
				if(user.role==null){
					role = "";
				}else if(user.role!=null){
					role = user.role;
				}
				IDayErr dayErr =(IDayErr)DAOFactory.getDAO("IDayErr");
				System.out.println("~~~~~"+empId+" "+role);				
				DayErrDTO[] returns = dayErr.delayConfDayReportList(empId,role);
				com.wms.fw.Utility.fixNullAndTrimAll(returns);
				System.out.println("DayErrReturns "+returns);
				req.setAttribute("returns",returns);
				printJspPage(req,res,page);
				return;
			}else if(_ACT.equals("RD")){//일일업무보고 한건선택
				IDayJob dayJob =(IDayJob)DAOFactory.getDAO("IDayJob");
				System.out.println("~~~~"+box.get("reEmpId")+" "+box.get("reEmpId"));
			    req.setAttribute("dayJob", dayJob.dayJobSearch(box.get("reEmpId"), box.get("reEmpId")));				        
			    printJspPage(req,res,_SCREEN);	
			    return;
			}else if(_ACT.equals("S")){//업무보고  수정 저장
			    String flag="X";
				DayJobReportDTO dDto=new DayJobReportDTO();
				box.copyToEntity(dDto);		
				JobReportDTO[] dtos=(JobReportDTO[])box.copyToEntities("com.wms.beans.dto.JobReportDTO");
				dDto.plans=dtos;
				IDayJob job = (IDayJob)DAOFactory.getDAO("IDayJob");
				if(!job.save((DayJobReportDTO)user.temp,dDto,flag,null,null,null))
					throw new Exception("저장을 실패했습니다."); 
				printJspPage(req,res,"/common/confirmOK.jsp");	
				return;
			}else if(_ACT.equals("SP")){//하위 계획자||하위직원전체
			    int mode=IGroupEmp.SUB_PLANNER_SEARCH;
			    //자신의 직책과 대결자의 직책을 동시에 가지고 오는 메서드.
				String mgrFg=user.getMgrUnionFlag();
				if(mgrFg.equals("GT"))mgrFg="T";//팀장의 대결은 그냥 팀장이다.
				EmpDTO[] empList = null;
				IGroupEmp groupemp =(IGroupEmp)DAOFactory.getDAO("IGroupEmp");
				if(mgrFg.equals("T")||mgrFg.equals("E")){
					mode=IGroupEmp.SUB_PLANNER_SEARCH;
				    empList=groupemp.empSubSearch((user.apKey)?user.apEmpId:user.empId,mode);
				}else if(mgrFg.equals("TE")){//팀장이 부문장의 대결을 겸할 경우...
					mode=IGroupEmp.SUB_PLANNER_SEARCH;
				    EmpDTO[] sup=groupemp.empSubSearch(user.apEmpId,mode);
				    EmpDTO[] sub=groupemp.empSubSearch(user.empId,mode);
					empList=new EmpDTO[sup.length+sub.length];

					for(int i=0;i<sup.length;i++){
						sup[i].empOrgCd=sup[i].empOrgCd.substring(0,1)+"000";
						empList[i]=sup[i];
					}
					for(int i=0;i<sub.length;i++){
						sub[i].empOrgCd=sub[i].empOrgCd.substring(0,2)+"00";
						empList[i+sup.length]=sub[i];
					}

				}else{
					empList=groupemp.empSubSearch((user.apKey)?user.apEmpId:user.empId,mode);
				}
				String orgCd =  req.getParameter("orgCd");
				String startDt= req.getParameter("startDt");
				String endDt=   req.getParameter("endDt");
				String reportDt=req.getParameter("reportDt");
				String empId = req.getParameter("empId");
				if(empId==null)empId = user.empId;
				if(orgCd==null)orgCd=user.empOrgCd;
				if(mgrFg.equals("T"))orgCd=orgCd.substring(0,2)+"00";//그룹장이 팀장 대결인 경우.. 조직관리를 위해...

				if(reportDt==null){reportDt=com.wms.fw.util.DateUtil.getTodayString();
				}else{
					reportDt=Utility.replace(reportDt,"-");
				}
			    if(mgrFg.equals("T")||mgrFg.equals("E")){
					ArrayList list = new ArrayList();
					list.add(empList);					
					req.setAttribute("empList", list);
				}else{
					req.setAttribute("empList", WMSUtility.classify(empList,user.empOrgCd));

				}
				IDayJob dayJob =(IDayJob)DAOFactory.getDAO("IDayJob");				
				DayJobReportDTO[] dtoList=null;

				dtoList=dayJob.updateSearch((user.apKey&&user.getManagerFlag().equals("Y"))?user.apEmpId:empId,
					                              reportDt,
					                              orgCd
					                             );				
				req.setAttribute("dtoList", dtoList);					
				printJspPage(req,res,_SCREEN);
				return;
			}else if(_ACT.equals("RP")){//업무 보고 수정..

				EmpDTO[] empList=new EmpDTO[1];
				EmpDTO emp = new EmpDTO();
				emp.empId   = box.get("reEmpId");
				emp.empKName= box.get("reEmpKName");
				empList[0]=emp;
				session.setAttribute("empList", empList);
				IDayJob dayJob =(IDayJob)DAOFactory.getDAO("IDayJob");								
				DayJobReportDTO dto=dayJob.dayJobSearch(emp.empId, Utility.replace(box.get("reportDt"),"-"));
				
				req.setAttribute("dayJob", dto);
				if(dto!=null&&dto.apFlag!=null&&dto.apFlag.equals("Y"))
					req.setAttribute("opinion", dayJob.opinionSearch(emp.empId, Utility.replace(box.get("reportDt"),"-")));
				printJspPage(req,res,_SCREEN);
				return;			
			}
			//화면을 진행시켜주는 메서드
			progressMessagePage(req,res,page);
		} catch(Exception e) {
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
            erorrMessagePage(req,res,e);
		}
	}
	
	
}
