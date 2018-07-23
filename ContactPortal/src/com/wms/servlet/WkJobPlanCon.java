/*************************************************************
*	�� �� ��  : WkJobPlanCon.java
*	�ۼ�����  : 2004/06/22
*	�� �� ��  : 
*	��    ��  : �ְ�������ȹ Ȯ�� ����
*             : ����(CRUD)�� �ϱ����� Ŭ����
*************************************************************/
package com.wms.servlet;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.wms.fw.servlet.*;
import com.wms.fw.*;
import com.wms.fw.util.DateUtil;
import com.wms.beans.dto.*;
import com.wms.beans.dao.*;
import com.wms.beans.*;
import java.util.*;
import java.util.ArrayList;
import com.wms.common.beans.*;
//�ְ����� Ȯ�� ���� ó�� Controller
public class WkJobPlanCon extends WmsServlet{

	protected void performTask(HttpServletRequest req, HttpServletResponse res)	throws Exception {
		try {
			Box box = HttpUtility.getBox(req);
			String _ACT=box.get("_ACT");
			String _SCREEN=box.get("_SCREEN");			
			HttpSession session = req.getSession();
			String page=_SCREEN;
		    UserInfo user = (UserInfo)session.getAttribute("user");
			String mgrFg=user.getMgrUnionFlag();			
		    if(mgrFg.equals("Y"))
			    throw new Exception("���������ڰ� �ƴմϴ�.");
	        if(_ACT.equals("RC")){//�ְ�������ȹȮ�� -> �˻�
				IWkJobPlan iwkJobPlan = (IWkJobPlan)DAOFactory.getDAO("IWkJobPlan");
				ArrayList planList = iwkJobPlan.wkJobSearch(box.get("plEmpId").trim(),user.empOrgCd,box.get("startDt"),box.get("endDt"));
				session.setAttribute("planList", planList);
				printJspPage(req,res,page);
				return;

			}else if(_ACT.equals("RPn")){//�ְ�������ȹȮ�� ����Ʈ���� ������ȹ�׸� �Ѱ� VIEW
				IWkJobPlan iwkJobPlan = (IWkJobPlan)DAOFactory.getDAO("IWkJobPlan");
				req.setAttribute("dto",iwkJobPlan.wkJobPlanSearch(box.get("planCd")));
				printJspPage(req,res,page);
				return;
			
			}else if(_ACT.equals("RPc")){//�ְ�������ȹȮ�� ����Ʈ���� ���������׸� �Ѱ� VIEW
				IWkJobPlan iwkJobPlan = (IWkJobPlan)DAOFactory.getDAO("IWkJobPlan");
				req.setAttribute("dto",iwkJobPlan.wkJobPrcsSearch(box.get("jobNo"),box.get("prcsNo")));
				printJspPage(req,res,page);
				return;
			
			}else if(_ACT.equals("SP")){//���� ��ȹ��||����������ü
				String orgCd = req.getParameter("orgCd");
				String plEmpId=req.getParameter("plEmpId");
				String startDt=req.getParameter("startDt");
				String endDt  =req.getParameter("endDt");
				String[] week=DateUtil.getWeekDay("��",DateUtil.getTodayString("-"),1);
				if(orgCd==null)orgCd=user.empOrgCd;
				if(plEmpId==null)plEmpId=user.empId;
				if(startDt==null)startDt=Utility.replace(week[0],"-");
				if(endDt==null)endDt=Utility.replace(week[1],"-");
                if(mgrFg.equals("G"))
             		plEmpId=(user.apKey)?user.apEmpId:user.empId;

				IGroupEmp groupemp =(IGroupEmp)DAOFactory.getDAO("IGroupEmp");
				EmpDTO[] empList=groupemp.empSubSearch(plEmpId,orgCd, IGroupEmp.SUB_PLANNER_SEARCH);
				//session.setAttribute("empSubList", empSubList);
				session.setAttribute("empSubList", WMSUtility.classify(empList,user.empOrgCd).get(0));

				page="/wkJobPlanCon?_ACT=RC&plEmpId="+plEmpId+"&startDt="+startDt+"&endDt="+endDt+"&_SCREEN=/WorkPlan/confirmWorkPlan.jsp&empId="+user.empId;
				System.out.println(page);
				progressMessagePage(req,res,page);
				return;				
			
			}else if(_ACT.equals("C")){//�ϰ� ���� Ȯ����
			    WePlanTempDTO[] dtoList = (WePlanTempDTO[])box.copyToEntities("com.wms.beans.dto.WePlanTempDTO");			
				String plEmpId=req.getParameter("plEmpId");
				String startDt=req.getParameter("startDt");
				String endDt=req.getParameter("endDt");

				//�����ο����� ������ ��й�..
                ArrayList returns = null;
				if(dtoList!=null){
					returns = new ArrayList();
					for(int i=0;i<dtoList.length;i++){
						String reEmpIDs[]=Utility.toArray(dtoList[i].reEmpId,",");
						if(reEmpIDs!=null){
                            WePlanTempDTO[] recList=new WePlanTempDTO[reEmpIDs.length]; 
							for(int j=0;j<reEmpIDs.length;j++){
                                WePlanTempDTO dto =(WePlanTempDTO)Utility.clone(dtoList[i]);
                                dto.startDt=startDt;
								dto.endDt = endDt;
								dto.plEmpId = plEmpId;
								dto.reEmpId=reEmpIDs[j];
								recList[j]=dto;
						 	}
							returns.add(recList);
						}
					}
				}
				
				IWkJobPlan iwkJobPlan = (IWkJobPlan)DAOFactory.getDAO("IWkJobPlan");
				System.out.println(iwkJobPlan.unionConfirm(returns));
				
			}else if(_ACT.equals("CP")){//���� ���� Ȯ����
			    WePlanTempDTO[] dtoList = (WePlanTempDTO[])box.copyToEntities("com.wms.beans.dto.WePlanTempDTO","reEmpId");			
				IWkJobPlan iwkJobPlan = (IWkJobPlan)DAOFactory.getDAO("IWkJobPlan");
				if(iwkJobPlan.confirm(dtoList,"W"))
					System.out.println("Ȯ�� ����");
				else
					throw new Exception("Ȯ�� ����");
				int idx=Integer.parseInt(box.get("idx"));
				ArrayList planList=(ArrayList)session.getAttribute("planList");
				planList.remove(idx);
				req.setAttribute("msg","Ȯ�� ����");
				printJspPage(req,res,"/common/confirmOK.jsp");
				return;	
				
			}else if(_ACT.equals("CPr")){//���� ������ȹ �����
			    PrcsDTO dto = new PrcsDTO();
				box.copyToEntity(dto);
				PrcsManCntDTO[] prcsList =(PrcsManCntDTO[])box.copyToEntities("com.wms.beans.dto.PrcsManCntDTO","empId");			
                int idx=0;
				String empId=null;
				AsdEmpDTO[] emp=new AsdEmpDTO[prcsList.length];				
				for(int i=0;i<prcsList.length;i++){
					empId=prcsList[i].empId;
					idx=empId.indexOf(" ");
					if(idx!=-1){
						prcsList[i].empId=empId.substring(0,idx);
						prcsList[i].empKName=empId.substring(idx+1);

						emp[i]=new AsdEmpDTO();
						emp[i].asdEmpId=prcsList[i].empId;
						emp[i].asdEmpKName=prcsList[i].empKName;

					}
				}
				dto.prcsManCntDTOs=prcsList;
				user.temp=dto;
				///////////////////////���������� ���� �κ�
				idx=Integer.parseInt(box.get("idx"));
				ArrayList planList=(ArrayList)session.getAttribute("planList");
				JobPlanDTO dTmp=(JobPlanDTO)planList.get(idx);
				dTmp.jobTitle= dto.jobTitle;
				dTmp.jobDetail=dto.prcsDesc;
                dTmp.planMh=   dto.plMh;
	
                dTmp.values=emp;

				///////////////////////end
				printJspPage(req,res,page);
				return;		
			}else if(_ACT.equals("CPn")){//���� �⺻��ȹ �����
				int idx=Integer.parseInt(box.get("idx"));
				ArrayList planList=(ArrayList)session.getAttribute("planList");
				JobPlanDTO dto=(JobPlanDTO)planList.get(idx);
				dto.jobTitle=box.get("jobTitle");
				dto.jobDetail=box.get("jobDetail");
                dto.planMh=Integer.parseInt(box.get("planMh"));
				dto.endDt=box.get("endDt");

				AsdEmpDTO[] emp=(AsdEmpDTO[])box.copyToEntities("com.wms.beans.dto.AsdEmpDTO");				
	            idx=0;
				String empId=null;
				for(int i=0;i<emp.length;i++){
					empId=emp[i].asdEmpId;
					idx=empId.indexOf(" ");
					if(idx!=-1){
						emp[i].asdEmpId=empId.substring(0,idx);
						emp[i].asdEmpKName=empId.substring(idx+1);
					}
				}
                dto.values=emp;
				IJobPlan jobPlan =(IJobPlan)DAOFactory.getDAO("IJobPlan");								
                if(!jobPlan.save( dto ))
					throw new Exception("������ �����߽��ϴ�.");
				printJspPage(req,res,_SCREEN);
				return;
	
			}else if(_ACT.equals("UT")){//�ӽ� Ȯ�� ��ȹ ����
			    WePlanTempDTO[] dtoList = (WePlanTempDTO[])box.copyToEntities("com.wms.beans.dto.WePlanTempDTO","reEmpId");			
				IWkJobPlan iwkJobPlan = (IWkJobPlan)DAOFactory.getDAO("IWkJobPlan");
				if(!iwkJobPlan.updateTemp(dtoList,(WePlanTempDTO[])user.temp))
					throw new Exception("Ȯ�� ����");

			}else if(_ACT.equals("UWD")){// Ȯ�� ��ȹ ���� ����
			/*
				WeeklyPlanDetailDTO[] dtoDetatilList = (WeeklyPlanDetailDTO[])box.copyToEntities("com.wms.beans.dto.WeeklyPlanDetailDTO","reEmpId");	
				WeeklyPlanDTO[] dtoList              = (WeeklyPlanDTO[])box.copyToEntities("com.wms.beans.dto.WeeklyPlanDTO","reEmpId");	
                for(int i=0;i<dtoList.length;i++){
					int returns=com.wms.fw.db.DataBaseUtil.checkRec(dtoList[i],"WeeklyPlan","reEmpId" );
					String reEmpId=dtoList[i].reEmpId;
					if(returns==3){
						System.out.println(reEmpId+"�� ���������� ����� �Ǿ��ֽ��ϴ�.");
					}else if(returns==2){
						System.out.println(reEmpId+"�� ������������ ����� �Ǿ��ֽ��ϴ�.");

					}else{
						System.out.println(reEmpId+"�� ����� �ȵǾ��ֽ��ϴ�.");

					}
				}
				1.������ ������Ͻ�...
				1)reEmpId�� ���� ������ üũ
				2)reEmpId�� ������, WeeklyPlan�� ���
				3)WeeklyPlanDetail�� delete
				4)���� ���ŵ� WeeklyPlanDetail�� ���
			*/
				WePlanTempDTO dto=new WePlanTempDTO();
				box.copyToEntity(dto);
				IWkJobPlan iwkJobPlan = (IWkJobPlan)DAOFactory.getDAO("IWkJobPlan");
				if(iwkJobPlan.updateWeekly(dto,(WePlanTempDTO[])user.temp)){
					printJspPage(req,res,page);
				    return;
				}else{
					throw new Exception("Ȯ�� ����");
				}
			}else if(_ACT.equals("RWT")){//�ӽ� Ȯ�� ��ȹ �˻�
				IWkJobPlan iwkJobPlan = (IWkJobPlan)DAOFactory.getDAO("IWkJobPlan");
				WePlanTempDTO[] tempList = iwkJobPlan.wkJobTempDetail(box.get("weStartDt"),box.get("jobNo"),box.get("prcsNo"),box.get("planCd"));
				user.temp=tempList;
				printJspPage(req,res,page);
				return;
			}else if(_ACT.equals("RU")){// Ȯ�� ��ȹ ��� �˻�
				IWkJobPlan iwkJobPlan = (IWkJobPlan)DAOFactory.getDAO("IWkJobPlan");
                String plEmpId=req.getParameter("plEmpId");
				if(plEmpId==null||plEmpId.trim().equals("null"))plEmpId=user.empId;
                if(mgrFg.equals("G"))
             		plEmpId=(user.apKey)?user.apEmpId:user.empId;
				String startDt=req.getParameter("startDt");
				if(startDt!=null){
					startDt=DateUtil.transDate(startDt);
				}
				if(startDt==null||startDt.trim().equals("null")||startDt.trim().equals("")){
					startDt=DateUtil.getTodayString("-");
				}
				
				String[] week=null;
				if(DateUtil.checkDate("��")){
					week=DateUtil.getWeekDay("��",startDt,0);
					startDt=Utility.replace(week[0],"-");
					req.setAttribute("weeklyList", iwkJobPlan.wkJobWeeklySearch(plEmpId,startDt));
					page="/WorkPlan/weeklyPlanList.jsp";
				}else{
					week=DateUtil.getWeekDay("��",startDt,1);
					startDt=Utility.replace(week[0],"-");
					req.setAttribute("planList"  , iwkJobPlan.wkJobTempSearch(plEmpId,startDt));
					page="/WorkPlan/weekPlanTempList.jsp";
				}
				req.setAttribute("startDt",startDt);
				req.setAttribute("endDt",Utility.replace(week[1],"-"));

				printJspPage(req,res,page);
				return;
			}else if(_ACT.equals("RWD")){// Ȯ�� ��ȹ ���� �˻�
				IWkJobPlan iwkJobPlan = (IWkJobPlan)DAOFactory.getDAO("IWkJobPlan");
				user.temp=iwkJobPlan.wkJobWeeklyDetail(box.get("weekStartDt"),box.get("jobNo"),box.get("prcsNo"),box.get("planCd"));
				printJspPage(req,res,page);
				return;
			}			
		    progressMessagePage(req,res,page);

		} catch(Exception e) {
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
            erorrMessagePage(req,res,e);
		}

	}
	
	
}
