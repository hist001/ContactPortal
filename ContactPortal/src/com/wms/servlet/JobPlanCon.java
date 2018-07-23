/*************************************************************
*	파 일 명  : JobPlanCon.java
*	작성일자  : 2004/06/22
*	작 성 자  : 
*	내    용  : 업무계획을 제어(CRUD)하는 클래스
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
public class JobPlanCon extends WmsServlet{

	protected void performTask(HttpServletRequest req, HttpServletResponse res)
		throws Exception {
		try {
			Box box = HttpUtility.getBox(req);
			String _ACT=box.get("_ACT");
			String _SCREEN=box.get("_SCREEN");
			HttpSession session = req.getSession();
			UserInfo user =(UserInfo)session.getAttribute("user");
			boolean managerflag=(user.getManagerFlag().equals("Y"))?false:true;
			String page=_SCREEN;
			if(_ACT.equals("R")){//목록보기
				IJobPlan jobPlan =(IJobPlan)DAOFactory.getDAO("IJobPlan");								
                ArrayList list = jobPlan.jobPlanSearch( user.empId, managerflag );
				//IGroupEmp groupemp =(IGroupEmp)DAOFactory.getDAO("IGroupEmp");
				//EmpDTO[] empList=groupemp.empSubSearch(user.empId,user.empOrgCd,IGroupEmp.SUB_PLANNER_SEARCH);
				//session.setAttribute("empList", empList);

				req.setAttribute("dtoList",list);
				printJspPage(req,res,_SCREEN);
				return;			
			}else if(_ACT.equals("RD")){//상세보기
				IJobPlan jobPlan =(IJobPlan)DAOFactory.getDAO("IJobPlan");								
                JobPlanDTO dto = jobPlan.jobPlanDetailSearch( box.get("empId"),box.get("planCd") );
				req.setAttribute("dto",dto);
				printJspPage(req,res,_SCREEN);
				return;			
			}else if(_ACT.equals("RS")){//배정하기 위한 직원검색
				IGroupEmp groupemp =(IGroupEmp)DAOFactory.getDAO("IGroupEmp");
				EmpDTO[] empList=null;
				String orgCd=req.getParameter("orgCd");
				orgCd =(orgCd==null||orgCd.trim().equals(""))?user.empOrgNo:orgCd;
				/*그룹장의 하위직원선택이냐, 동료직원의 선택이냐..
				String id="plEmpId";
				if(req.getParameter("personFlag")==null)
					id="empId";
				if(user.isPlanner()||user.isManager()){
				   empList=groupemp.empSubSearch(box.get(id),orgCd,IGroupEmp.SUB_ALL_SEARCH);
				}else{
				   empList=groupemp.empComradeSearch(user.empId,orgCd);
				}*/
                empList=groupemp.empSearch(orgCd);
				req.setAttribute("empSubList", WMSUtility.classify(empList,orgCd).get(0));
				printJspPage(req,res,_SCREEN);
				return;			
			}else if(_ACT.equals("S")){//2005/04/08 이후 등록은 jobPlanRegAction.do에서 처리
				System.out.println("잘못된 호출 JobPlanCon.java : _ACT=S :2005/04/08 이후 등록은 jobPlanRegAction.do에서 처리해야 함");
				JobPlanDTO dto= new JobPlanDTO();
				box.copyToEntity(dto);
				AsdEmpDTO[] emp=(AsdEmpDTO[])box.copyToEntities("com.wms.beans.dto.AsdEmpDTO");
				dto.values=emp;
	
				IJobPlan jobPlan =(IJobPlan)DAOFactory.getDAO("IJobPlan");								
                if(!jobPlan.save( dto ))
					throw new Exception("저장을 실패했습니다.");
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
