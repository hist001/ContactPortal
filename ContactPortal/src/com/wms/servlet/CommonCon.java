/*************************************************************
*	파 일 명  : CommonCon.java
*	작성일자  : 2004/06/22
*	작 성 자  : 
*	내    용  : 공통 조회 관련 제어(CRUD)처리를 하는 클래스
*************************************************************/
package com.wms.servlet;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.wms.fw.servlet.*;
import com.wms.fw.*;
import com.wms.fw.db.*;
import com.wms.beans.*;
import com.wms.beans.dto.*;
import com.wms.beans.dao.*;
import java.util.*;
public class CommonCon extends WmsServlet{

	protected void performTask(HttpServletRequest req, HttpServletResponse res)
		throws Exception {
		try {
			Box box = HttpUtility.getBox(req);
			String _ACT=box.get("_ACT");
			String _SCREEN=box.get("_SCREEN");			
			HttpSession session = req.getSession();
			String page=_SCREEN;
			if(_ACT.equals("R")){//상품목록검색

				ProdDTO dto = new ProdDTO();
				box.copyToEntity(dto);
				IProd prod = (IProd)DAOFactory.getDAO("IProd");
				ProdDTO[] prodList=prod.searchProd(dto);
				req.setAttribute("prodList" ,prodList);
				printJspPage(req,res,page);
				return;
			}else if(_ACT.equals("RD")){//공정목록검색

				PrcsDTO dto = new PrcsDTO();
				box.copyToEntity(dto);
				IProd prod = (IProd)DAOFactory.getDAO("IProd");
				PrcsDTO[] prcsList=prod.searchDetailPrcs(dto);
  			        req.setAttribute("prcsList" ,prcsList);
				printJspPage(req,res,page);
				return;
			}else if(_ACT.equals("RC")){//직무검색

				HistJobDTO dto = new HistJobDTO();
				box.copyToEntity(dto);
				IHistJob histJob = (IHistJob)DAOFactory.getDAO("IHistJob");
				//16번과 17번은 통합해서 출력..
				HistJobDTO[] histJobList=null;
				if(dto.jobCd.equals("16")){
					HistJobDTO[] histTemp1=histJob.searchHistJob(dto);
					dto.jobCd="17";
					HistJobDTO[] histTemp2=histJob.searchHistJob(dto);
					histJobList=new HistJobDTO[histTemp1.length+histTemp2.length];
					System.arraycopy(histTemp1,0,histJobList,0,histTemp1.length);
					System.arraycopy(histTemp2,0,histJobList,histTemp1.length,histTemp2.length);

				}else{                
					histJobList=histJob.searchHistJob(dto);
				}
				//통합끝
				
				if(box.get("rdo")!=null&&box.get("rdo").equals("JOB")){
					req.setAttribute("histJobList" ,histJobList);
				}else{
					session.setAttribute("histJobList" ,histJobList);
				}
				printJspPage(req,res,page);
				return;
			}else if(_ACT.equals("RE")){//개인직무검색
				IHistJob histJob = (IHistJob)DAOFactory.getDAO("IHistJob");
				HistJobDTO[] empJobList=histJob.searchEmpJob(box.get("empId"));
				req.setAttribute("empJobList" ,empJobList);
				printJspPage(req,res,page);
				return;
			}else if(_ACT.equals("RU")){//통합검색

				req.setAttribute("searchList" ,DataBaseUtil.unionSearch(box.get("searchNo"),box.get("tableName")));
				printJspPage(req,res,page);
				return;
			}else if(_ACT.equals("AC")){
				String type = box.get("param");
				String code = box.get("code");
				String name = box.get("name");
				
				Hashtable returns = new Hashtable();
				if(type.equals("curEmp")||type.equals("subWrEmp")||type.equals("pmEmp")||type.equals("saleEmp")){
					type="emp";
				}
				if(type.equals("org")||type.equals("prcsOrg")||type.equals("actOrg")
					||type.equals("dutyOrg1")||type.equals("dutyOrg2")||type.equals("dutyOrg3")||type.equals("dutyOrg4")){
					type="org";
				}
				returns = AllSearch.allSearch(type, code, name);				
				req.setAttribute("returns", returns);
				printJspPage(req, res, page);			
			}else if(_ACT.equals("AR")){
				UserInfo user = (UserInfo)session.getAttribute("user");
				IGroupEmp groupEmp = (IGroupEmp)DAOFactory.getDAO("IGroupEmp");
				EmpDTO apEmp=groupEmp.empApSearch(user.empId);
				req.setAttribute("apEmp", apEmp);
				printJspPage(req, res, page);			
				return;
			}else if(_ACT.equals("ASR")){
				UserInfo user = (UserInfo)session.getAttribute("user");
				IGroupEmp groupEmp = (IGroupEmp)DAOFactory.getDAO("IGroupEmp");
				EmpDTO[] empList=groupEmp.empSelectSearch(user.empId);
				req.setAttribute("empList", empList);
				printJspPage(req, res, page);			
				return;
			}else if(_ACT.equals("UA")){
				UserInfo user = (UserInfo)session.getAttribute("user");
				IGroupEmp groupEmp = (IGroupEmp)DAOFactory.getDAO("IGroupEmp");
				if(!box.get("apEmpId").trim().equals(""))
					groupEmp.apUpdate(user.empId,box.get("apEmpId"),box.get("apEmpKName"));
				req.setAttribute("msg","대결자 지정 완료");
				page="/common/confirmOk.jsp";
				printJspPage(req, res, page);
				return;
			}else if(_ACT.equals("RCO")){//기존의 승인자 조회
				IGroupEmp groupEmp = (IGroupEmp)DAOFactory.getDAO("IGroupEmp");
				EmpDTO[] orgList=groupEmp.orgSearch(box.get("empId"),box.get("empKName"));
				req.setAttribute("orgList",orgList);
				printJspPage(req, res, page);
				return;
			}else if(_ACT.equals("RCN")){//새 승인자 조회
				IGroupEmp groupEmp = (IGroupEmp)DAOFactory.getDAO("IGroupEmp");
				EmpDTO[] orgList=groupEmp.orgEmpSearch(box.get("empId"),box.get("empKName"));
				req.setAttribute("orgList",orgList);
				printJspPage(req, res, page);
				return;
			}else if(_ACT.equals("UU")){//일괄 승인자변경
				IGroupEmp groupEmp = (IGroupEmp)DAOFactory.getDAO("IGroupEmp");
				groupEmp.highUpdate(box.get("oldEmpId"),box.get("newEmpId"));
				//req.setAttribute("orgList",orgList);
				page="/common/confirmOk.html";
				//printJspPage(req, res, page);
				//return;
			}else if(_ACT.equals("JL")){//일괄 승인자변경
				String jobName = box.get("jobName");
				IHistJob histJob = (IHistJob)DAOFactory.getDAO("IHistJob");
				HistJobDTO[] histjobdtos=histJob.histJobInquiryList("%",jobName);
				req.setAttribute("histJobInquiryList",histjobdtos);
				
				printJspPage(req, res, page);
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
