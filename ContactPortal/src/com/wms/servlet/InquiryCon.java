/*************************************************************
*	파 일 명  : InquiryCon.java
*	작성일자  : 2004/09/03
*	작 성 자  : 
*	내    용  : 현황조회 관련 제어처리
*************************************************************/
package com.wms.servlet;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.wms.fw.servlet.*;
import com.wms.fw.*;
import com.wms.beans.dto.*;
import com.wms.beans.dao.*;
import com.wms.beans.dao.AllSearch;
import java.util.*;
import com.wms.fw.util.DateUtil;

public class InquiryCon extends WmsServlet{

	protected void performTask(HttpServletRequest req, HttpServletResponse res)	throws Exception {
		try {
			Box box = HttpUtility.getBox(req);
			String _ACT=box.get("_ACT");
			String _SCREEN=box.get("_SCREEN");
			HttpSession session = req.getSession();
			UserInfo user =(UserInfo)session.getAttribute("user");
			String page=_SCREEN;

			if(_ACT.equals("ProdLR")){//제품 및 Task 등록
				String prodType = box.get("prodType");
				String prodNo = box.get("prodNo");
				String prodName = box.get("prodName");
				String startDt = box.get("startDt");
				String endDt = box.get("endDt");
				String bizNo = box.get("bizNo");
				String prodStatus = box.get("prodStatus");
				String strPageNum = box.get("pageNum");
				
				if(strPageNum==null || strPageNum.equals("")){
					session.removeAttribute("pageinfo");
					strPageNum="1";
				}
				
				int pageNum = Integer.parseInt(strPageNum);
				com.wms.fw.Config conf = new com.wms.fw.Configuration();
			        String strPageSize = conf.get("com.wms.pageSize");
				int pageSize = Integer.parseInt(strPageSize);
				
				IProd iprod = (IProd)DAOFactory.getDAO("IProd");
				//int prodListCnt = iprod.prodInquiryListCnt(prodType,startDt,endDt,bizNo,prodStatus,prodNo,prodName);
				ProdDTO[] proddto = iprod.prodInquiryList(prodType,startDt,endDt,bizNo,prodStatus,prodNo,prodName,pageNum,pageSize);

//			        req.setAttribute("prodInquiryList", proddto);	
			        PageInfo pageInfo = new PageInfo(proddto,pageNum,pageSize,(proddto!=null)?proddto[0].totCnt:0,new ProdDTO());
			        session.setAttribute("pageinfo", pageInfo);

				printJspPage(req, res, page);			
			}else if(_ACT.equals("PrcsLR")){//제품별공정조회
				String prodType = box.get("prodType");
				String prodNo = box.get("prodNo");

				IPrcs iprcs = (IPrcs)DAOFactory.getDAO("IPrcs");
				PrcsDTO[] prcsdto = iprcs.prcsSearch("", prodNo, prodType,user.role);
			        req.setAttribute("prcsSearchList", prcsdto);	

				printJspPage(req, res, page);			
			}else if(_ACT.equals("EmpLR")){ // 직원정보조회-페이징
				String orgCd = box.get("orgCd");
				String orgName = box.get("orgName");
				String empId = box.get("txtEmpId");
				String empKName = box.get("empKName");
				String jobCd = box.get("jobCd");
				String jobName = box.get("jobName");
				String strPageNum = box.get("pageNum");
				if(strPageNum==null || strPageNum.equals("")){
					session.removeAttribute("pageinfo");
					strPageNum="1";
				}
				int pageNum = Integer.parseInt(strPageNum);
				com.wms.fw.Config conf = new com.wms.fw.Configuration();
			        String strPageSize = conf.get("com.wms.pageSize");
				int pageSize = Integer.parseInt(strPageSize);

				IEmp iemp = (IEmp)DAOFactory.getDAO("IEmp");
				EmpDTO[] empdto = iemp.empInquiryList(orgCd, orgName, empId, empKName, jobCd, jobName, pageNum, pageSize);
			        //req.setAttribute("empInquiryList", empdto);	
			        PageInfo pageInfo = new PageInfo(empdto,pageNum,pageSize,(empdto!=null)?empdto[0].totCnt:0,new EmpDTO());
			        session.setAttribute("pageinfo", pageInfo);

				printJspPage(req, res, page);			
			}else if(_ACT.equals("EmpLRA")){ // 직원정보조회
				String orgCd = box.get("orgCd");
				String orgName = box.get("orgName");
				String empId = box.get("txtEmpId");
				String empKName = box.get("empKName");
				String jobCd = box.get("jobCd");
				String jobName = box.get("jobName");

				IEmp iemp = (IEmp)DAOFactory.getDAO("IEmp");
				EmpDTO[] empdto = iemp.empInquiryList(orgCd, orgName, empId, empKName, jobCd, jobName);
			        req.setAttribute("empInquiryList", empdto);	

				printJspPage(req, res, page);			
			}else if(_ACT.equals("EmpJobLR")){//개인별직무조회
				String empId = box.get("empId");

				IEmp iemp = (IEmp)DAOFactory.getDAO("IEmp");
				EmpDTO empapprovaldto = iemp.empApprovalInquiryList(empId);
			        req.setAttribute("empApprovalInquiryList", empapprovaldto);

				EmpDTO empjobdto = iemp.empJobInquiryList(empId);
			        req.setAttribute("empJobInquiryList", empjobdto);

				printJspPage(req, res, page);			
			}else if(_ACT.equals("CodeLR")){ //공통코드조회
				String cdType = box.get("cdType");
				String cdTypeName = box.get("cdTypeName");
				String cd = box.get("cd");
				String cdName = box.get("cdName");

				ICoCode icocode = (ICoCode)DAOFactory.getDAO("ICoCode");
				CoCodeDTO[] cocodedto = icocode.coCodeInquiryList(cdType,cdTypeName,cd,cdName);
			        req.setAttribute("coCodeInquiryList", cocodedto);

				printJspPage(req, res, page);			
			}else if(_ACT.equals("BizLR")){//사업조회
				String bizNo = box.get("bizNo");
				String bizName = box.get("bizName");

				IBiz ibiz = (IBiz)DAOFactory.getDAO("IBiz");
				BizDTO[] bizdto = ibiz.bizInquiryList(bizNo,bizName);
			        req.setAttribute("bizInquiryList", bizdto);

				printJspPage(req,res,page);
			}else if(_ACT.equals("StdPrcsLR")){//표준공정조회
				String cd = box.get("cd");
				String prcsName = box.get("prcsName");

				IStdPrcs istdprcs = (IStdPrcs)DAOFactory.getDAO("IStdPrcs");
				StdPrcsDTO[] stdprcsdto = istdprcs.stdPrcsInquiryList(cd,prcsName);
			        req.setAttribute("stdPrcsInquiryList", stdprcsdto);

				ICoCode coCode = (ICoCode)DAOFactory.getDAO("ICoCode");
				//수정화면에서 표준공정은 무조건 공정형태코드만을 사용한다.
				CoCodeDTO[] coCodeDto = coCode.coCodeSearch("PR","");
		        	req.setAttribute("coCodeSearch", coCodeDto);	

				printJspPage(req,res,page);
			}else if(_ACT.equals("HistJobLR")){//전사직무조회
				String jobCd = box.get("jobCd");
				String jobName = box.get("jobName");
				String strPageNum = box.get("pageNum");
				if(strPageNum==null || strPageNum.equals("")){
					session.removeAttribute("pageinfo");
					strPageNum="1";
				}
				int pageNum = Integer.parseInt(strPageNum);
				com.wms.fw.Config conf = new com.wms.fw.Configuration();
			        String strPageSize = conf.get("com.wms.pageSize");
				int pageSize = Integer.parseInt(strPageSize);

				IHistJob ihistjob = (IHistJob)DAOFactory.getDAO("IHistJob");
				HistJobDTO[] histjobdto = ihistjob.histJobInquiryList(jobCd,jobName,pageNum,pageSize);
			        //req.setAttribute("histJobInquiryList", histjobdto);
			        PageInfo pageInfo = new PageInfo(histjobdto,pageNum,pageSize,(histjobdto!=null)?histjobdto[0].totCnt:0,new HistJobDTO());
			        session.setAttribute("pageinfo", pageInfo);

				printJspPage(req,res,page);
			}else if(_ACT.equals("HistJobLRA")){//전사직무조회
				String jobCd = box.get("jobCd");
				String jobName = box.get("jobName");

				IHistJob ihistjob = (IHistJob)DAOFactory.getDAO("IHistJob");
				HistJobDTO[] histjobdto = ihistjob.histJobInquiryList(jobCd,jobName);
			        req.setAttribute("histJobInquiryList", histjobdto);

				printJspPage(req,res,page);
			}
			/*
			2005.08.11 거래처관리 기능 재개발로 인한 거래처조회 및 기본자료처리 기능 삭제
			else if(_ACT.equals("BizAcqLR")){//거래처 리스트 검색
				String bizAcqCd = box.get("bizAcqCd");
				String bizAcqName = box.get("bizAcqName");
				String strPageNum = box.get("pageNum");
				if(strPageNum==null || strPageNum.equals("")){
					session.removeAttribute("pageinfo");
					strPageNum="1";
				}
				int pageNum = Integer.parseInt(strPageNum);
				com.wms.fw.Config conf = new com.wms.fw.Configuration();
			        String strPageSize = conf.get("com.wms.pageSize");
				int pageSize = Integer.parseInt(strPageSize);

				IBizChag ibizchag = (IBizChag)DAOFactory.getDAO("IBizChag");
				BizChagDTO[] bizchagdto = ibizchag.bizChagInquiryList(bizAcqCd, bizAcqName, pageNum, pageSize);
//			        req.setAttribute("bizChagSearchList", bizchagdto);	
			        PageInfo pageInfo = new PageInfo(bizchagdto,pageNum,pageSize,(bizchagdto!=null)?bizchagdto[0].totCnt:0,new BizChagDTO());
			        session.setAttribute("pageinfo", pageInfo);
			        
			        printJspPage(req,res,_SCREEN);		
			}
			else if(_ACT.equals("BizAcqLRA")){//거래처 리스트 검색
				String bizAcqCd = box.get("bizAcqCd");
				String bizAcqName = box.get("bizAcqName");

				IBizChag ibizchag = (IBizChag)DAOFactory.getDAO("IBizChag");
				BizChagDTO[] bizchagdto = ibizchag.bizChagInquiryList(bizAcqCd, bizAcqName);
			        req.setAttribute("bizChagInquiryList", bizchagdto);	
			        
			        printJspPage(req,res,_SCREEN);		
			}*/
			else if(_ACT.equals("OrgLR")){//조직 리스트 검색
				String orgCd = box.get("orgCd");
				String orgName = box.get("orgName");

				IOrg iorg = (IOrg)DAOFactory.getDAO("IOrg");
				OrgDTO[] orgdto = iorg.searchOrg(orgCd, orgName);
			        req.setAttribute("searchOrg", orgdto);	
			        
			        printJspPage(req,res,_SCREEN);		
			}else if(_ACT.equals("StatusLR")){//거래처 리스트 검색
				String statusCd = box.get("statusCd");
				String statusName = box.get("statusName");

				IDayErr dayErr =(IDayErr)DAOFactory.getDAO("IDayErr");
				StatusDTO[] statusdto = dayErr.searchStatusList(statusCd,statusName);
				req.setAttribute("searchStatusList",statusdto);
			        
			        printJspPage(req,res,_SCREEN);		
			}
			
		} catch(Exception e) {
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
            		erorrMessagePage(req,res,e);
		}
	}
	
	
}
