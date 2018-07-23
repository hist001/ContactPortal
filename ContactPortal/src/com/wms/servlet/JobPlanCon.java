/*************************************************************
*	�� �� ��  : JobPlanCon.java
*	�ۼ�����  : 2004/06/22
*	�� �� ��  : 
*	��    ��  : ������ȹ�� ����(CRUD)�ϴ� Ŭ����
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
			if(_ACT.equals("R")){//��Ϻ���
				IJobPlan jobPlan =(IJobPlan)DAOFactory.getDAO("IJobPlan");								
                ArrayList list = jobPlan.jobPlanSearch( user.empId, managerflag );
				//IGroupEmp groupemp =(IGroupEmp)DAOFactory.getDAO("IGroupEmp");
				//EmpDTO[] empList=groupemp.empSubSearch(user.empId,user.empOrgCd,IGroupEmp.SUB_PLANNER_SEARCH);
				//session.setAttribute("empList", empList);

				req.setAttribute("dtoList",list);
				printJspPage(req,res,_SCREEN);
				return;			
			}else if(_ACT.equals("RD")){//�󼼺���
				IJobPlan jobPlan =(IJobPlan)DAOFactory.getDAO("IJobPlan");								
                JobPlanDTO dto = jobPlan.jobPlanDetailSearch( box.get("empId"),box.get("planCd") );
				req.setAttribute("dto",dto);
				printJspPage(req,res,_SCREEN);
				return;			
			}else if(_ACT.equals("RS")){//�����ϱ� ���� �����˻�
				IGroupEmp groupemp =(IGroupEmp)DAOFactory.getDAO("IGroupEmp");
				EmpDTO[] empList=null;
				String orgCd=req.getParameter("orgCd");
				orgCd =(orgCd==null||orgCd.trim().equals(""))?user.empOrgNo:orgCd;
				/*�׷����� �������������̳�, ���������� �����̳�..
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
			}else if(_ACT.equals("S")){//2005/04/08 ���� ����� jobPlanRegAction.do���� ó��
				System.out.println("�߸��� ȣ�� JobPlanCon.java : _ACT=S :2005/04/08 ���� ����� jobPlanRegAction.do���� ó���ؾ� ��");
				JobPlanDTO dto= new JobPlanDTO();
				box.copyToEntity(dto);
				AsdEmpDTO[] emp=(AsdEmpDTO[])box.copyToEntities("com.wms.beans.dto.AsdEmpDTO");
				dto.values=emp;
	
				IJobPlan jobPlan =(IJobPlan)DAOFactory.getDAO("IJobPlan");								
                if(!jobPlan.save( dto ))
					throw new Exception("������ �����߽��ϴ�.");
				printJspPage(req,res,_SCREEN);
				return;
			}
			//ȭ���� ��������ִ� �޼���
			progressMessagePage(req,res,page);
		} catch(Exception e) {
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
            erorrMessagePage(req,res,e);
		}
	}
	
	
}
