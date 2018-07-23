/*************************************************************
*	파 일 명  : WeekJobPlanCon.java
*	작성일자  : 2004/06/22
*	작 성 자  : 
*	내    용  : 업무계획을 제어(CRUD)하는 클래스
*************************************************************/
package com.wms.servlet;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.StringTokenizer;
import java.util.ArrayList;
import com.wms.fw.servlet.*;
import com.wms.fw.*;
import com.wms.beans.dto.*;
import com.wms.beans.dao.*;
import com.wms.fw.util.*;
import com.wms.common.beans.*;
public class WeekJobPlanCon extends WmsServlet{

	protected void performTask(HttpServletRequest req, HttpServletResponse res)
		throws Exception {
		try {

			Box box = HttpUtility.getBox(req);
			String _ACT=box.get("_ACT");
			String _SCREEN=box.get("_SCREEN");			
			HttpSession session = req.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
			boolean managerflag=(user.getManagerFlag().equals("Y"))?false:true;
			String empId = req.getParameter("empId");
			if(empId==null)
				empId = user.empId;
			if(_ACT.equals("C")){//confirm
			}else if(_ACT.equals("S")){//save

			}else if(_ACT.equals("RS")){//한 사람의 주간업무계획 검색
				String startDt = Utility.replace(box.get("startDt"),"-");

				WeekJobPlanDetailDTO dto = new WeekJobPlanDetailDTO();
				dto.weekStartDt = startDt;
				dto.reEmpId = empId;
				dto.jobNo = box.get("jobNo");
				dto.prcsNo = box.get("prcsNo");
				
				IWeekJobPlan weekjobplan = (IWeekJobPlan)DAOFactory.getDAO("IWeekJobPlan");
				WeekJobPlanDetailDTO weekjobplandetaildto = weekjobplan.weekJobPlanSubSearch(dto,user.getManagerFlag().equals("Y")?false:true);
			        req.setAttribute("weekjobplansubsearch", weekjobplandetaildto);	
			        
			        printJspPage(req,res,_SCREEN);		
			}else if(_ACT.equals("RD")){//detail search
			}else if(_ACT.equals("R")){//계획자를 포함한 이하 직원의 주간업무계획 검색

				String startDt = Utility.replace(box.get("startDt"),"-");

				IWeekJobPlan weekjobplan = (IWeekJobPlan)DAOFactory.getDAO("IWeekJobPlan");
			    req.setAttribute("weekjobplanlistsearch", weekjobplan.weekJobPlanListSearch(empId, startDt, managerflag));	
			        
			    printJspPage(req,res,_SCREEN);		
			}else if(_ACT.equals("SP")||_ACT.equals("SE")){//하위 계획자||하위직원전체
			    int mode=(_ACT.equals("SP"))?IGroupEmp.SUB_PLANNER_SEARCH:IGroupEmp.SUB_ALL_SEARCH;
				IGroupEmp groupemp =(IGroupEmp)DAOFactory.getDAO("IGroupEmp");
				String orgCd = req.getParameter("orgCd");
				if(orgCd==null)orgCd=user.empOrgCd;
				if(user.getManagerFlag().equals("T"))
					mode=IGroupEmp.SUB_PLANNER_SEARCH;
				EmpDTO[] empList=null;
				if(user.getManagerFlag().equals("C")||user.getManagerFlag().equals("E")){
					empList=groupemp.empSubSearch(empId, null, 100);
				}else{
					empList=groupemp.empSubSearch(empId, orgCd, mode);
				}
				
			    if(user.getManagerFlag().equals("T")||user.getManagerFlag().equals("GG")||user.empId.equals("20000038")){
					ArrayList list = new ArrayList();
                    
					list.add(empList);
					
					session.setAttribute("empList", list);

				}else{
					session.setAttribute("empList", WMSUtility.classify(empList,user.empOrgCd));

				}
				
				IWeekJobPlan weekjobplan = (IWeekJobPlan)DAOFactory.getDAO("IWeekJobPlan");

				String startDt=req.getParameter("startDt");
				String endDt  =req.getParameter("endDt");
				String[] week=DateUtil.getWeekDay("월",DateUtil.getTodayString("-"),1);

				if(startDt==null){
					startDt=Utility.replace(week[0],"-");
				}else{
					startDt = Utility.replace(box.get("startDt"),"-");
				}
				if(endDt==null)endDt=Utility.replace(week[1],"-");

			    req.setAttribute("weekjobplanlistsearch", weekjobplan.weekJobPlanListSearch(empId, startDt, managerflag));	
                if(_ACT.equals("SP")){
				progressMessagePage(req,res,"/weekJobPlanCon?_SCREEN=/WorkPlan/searchWorkPlan.jsp&_ACT=R&empId="+empId+"&startDt="+startDt+"&endDt="+endDt);
				return;
				}
			}
			//화면을 진행시켜주는 메서드
		} catch(Exception e) {
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
            		erorrMessagePage(req,res,e);
		}
	}
}
