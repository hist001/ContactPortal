/*************************************************************
*	파 일 명  : PrcsManCntCon.java
*	작성일자  : 2004/09/03
*	작 성 자  : 
*	내    용  : 공정인원 관련 제어처리
*************************************************************/
package com.wms.servlet;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.StringTokenizer;
import com.wms.fw.servlet.*;
import com.wms.fw.*;
import com.wms.beans.dto.*;
import com.wms.beans.dao.*;
import com.wms.fw.util.*;

public class PrcsManCntCon extends WmsServlet{

	protected void performTask(HttpServletRequest req, HttpServletResponse res)
		throws Exception {
		try {
			//공정 검색
			//공정 인원 검색
			//공정 원가 검색
			//
			Box box = HttpUtility.getBox(req);
			String _ACT=box.get("_ACT");
			String _SCREEN=box.get("_SCREEN");			
			HttpSession session = req.getSession();
			
			if(_ACT.equals("I")){//공정 생성
				String prodNo = box.get("prodNo");
				String prodType = box.get("prodType");
				String prcsNo = box.get("prcsNo");
				String[] prcsEmpIds = req.getParameterValues("prcsEmpId");
				String[] chkFlags = req.getParameterValues("chkFlag");

				IPrcsManCnt iprcsmancnt  = (IPrcsManCnt)DAOFactory.getDAO("IPrcsManCnt");
				boolean prcsmancntdto = iprcsmancnt.add(prodNo, prodType, prcsNo, prcsEmpIds, chkFlags);

				progressMessagePage(req,res,_SCREEN);
			}else if(_ACT.equals("U")){//save
				PrcsManCntDTO updatedDto = new PrcsManCntDTO();
				box.copyToEntity(updatedDto);
				IPrcsManCnt iprcsmancnt = (IPrcsManCnt)DAOFactory.getDAO("IPrcsManCnt");
				com.wms.fw.Utility.fixNullAndTrimAll(updatedDto);
				int prcsmancntdto = iprcsmancnt.update(updatedDto);

				progressMessagePage(req,res,_SCREEN);
			}else if(_ACT.equals("D")){//save
				String prodNo = box.get("prodNo");
				String prodType = box.get("prodType");
				String prcsNo = box.get("prcsNo");
				String[] prcsEmpIds = req.getParameterValues("prcsEmpId");
				String[] chkFlags = req.getParameterValues("chkFlag");

				IPrcsManCnt iprcsmancnt = (IPrcsManCnt)DAOFactory.getDAO("IPrcsManCnt");
				int prcsmancntdto = iprcsmancnt.delete(prodNo, prodType, prcsNo, prcsEmpIds, chkFlags);

				progressMessagePage(req,res,_SCREEN);
			}else if(_ACT.equals("LR")){//해당 제품의 공정 List
				String prodNo = box.get("prodNo");
				String prcsNo = box.get("prcsNo");
				String prodType = box.get("prodType");

				IPrcsManCnt iprcsmancnt = (IPrcsManCnt)DAOFactory.getDAO("IPrcsManCnt");
				PrcsManCntDTO[] prcsmancntdto = iprcsmancnt.prcsManCntSearchList(prodNo, prcsNo, prodType);
			        req.setAttribute("prcsManCntSearchList", prcsmancntdto);	

				//제품 기본정보 조회
				IProd iprod = (IProd)DAOFactory.getDAO("IProd");
				ProdDTO proddto = iprod.searchProd(prodNo,prodType);
			        req.setAttribute("searchProd", proddto);	

				//특정공정코드 조회
				IPrcs iprcs = (IPrcs)DAOFactory.getDAO("IPrcs");
				PrcsDTO prcsdto = iprcs.prcsSubSearch(prodNo, prcsNo, prodType);
			        req.setAttribute("prcsSubSearch", prcsdto);	

			        printJspPage(req,res,_SCREEN);		
			}else if(_ACT.equals("R")){//하나의 공정을 검색한다.
				String prodNo = box.get("prodNo");
				String prcsNo = box.get("prcsNo");
				String prodType = box.get("prodType");
				String empId = box.get("empId");

				IPrcsManCnt iprcsmancnt = (IPrcsManCnt)DAOFactory.getDAO("IPrcsManCnt");
				PrcsManCntDTO prcsmancntdto = iprcsmancnt.prcsManCntSubSearch(prodNo, prcsNo, prodType, empId);
			        req.setAttribute("prcsManCntSubSearch", prcsmancntdto);	

				//제품 기본정보 조회
				IProd iprod = (IProd)DAOFactory.getDAO("IProd");
				ProdDTO proddto = iprod.searchProd(prodNo,prodType);
			        req.setAttribute("searchProd", proddto);	

				//특정공정코드 조회
				IPrcs iprcs = (IPrcs)DAOFactory.getDAO("IPrcs");
				PrcsDTO prcsdto = iprcs.prcsSubSearch(prodNo, prcsNo, prodType);
			        req.setAttribute("prcsSubSearch", prcsdto);	

			        printJspPage(req,res,_SCREEN);		
			}else if(_ACT.equals("ER") || _ACT.equals("OER") || _ACT.equals("PER")){//직원 검색
				String empId = box.get("empId");
				String prodNo = box.get("prodNo");
				String prcsNo = box.get("prcsNo");
				String prodType = box.get("prodType");
				String empKName = box.get("empKName");
				String orgName = box.get("orgName");
				String prodEmpCallYN = box.get("prodEmpCallYN");

				IPrcsManCnt iprcsmancnt = (IPrcsManCnt)DAOFactory.getDAO("IPrcsManCnt");
				IProd prod = (IProd)DAOFactory.getDAO("IProd");
				EmpDTO[] empdto=null;
				//ProdEmpDTO[] prodEmps = null;
                		
				if(_ACT.equals("ER")){
					empdto = iprcsmancnt.empSearch(prodNo, prcsNo, prodType, empId, empKName, orgName, prodEmpCallYN);
					System.out.println("ER::"+box);
				}else if(_ACT.equals("OER")){
					empdto = iprcsmancnt.otherPrcsEmpSearch(prodNo, prcsNo, prodType, empId, empKName, orgName);					
				}else if(_ACT.equals("PER")){
					empdto = iprcsmancnt.prcsEmpSearch(prodNo, prcsNo, prodType);
				}
			    req.setAttribute("empSearch", empdto);	
				System.out.println("1111");

				//제품 기본정보 조회
				IProd iprod = (IProd)DAOFactory.getDAO("IProd");
				ProdDTO proddto = iprod.searchProd(prodNo,prodType);
			    req.setAttribute("searchProd", proddto);	
				System.out.println("2222");

				//특정공정코드 조회
				IPrcs iprcs = (IPrcs)DAOFactory.getDAO("IPrcs");
				PrcsDTO prcsdto = iprcs.prcsSubSearch(prodNo, prcsNo, prodType);
		        req.setAttribute("prcsSubSearch", prcsdto);	
				System.out.println("3333");
		        printJspPage(req,res,_SCREEN);		
			}else if(_ACT.equals("R")){//하나의 공정을 검색한다.
	                }
		} catch(Exception e) {
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
            		erorrMessagePage(req,res,e);
		}
	}
}
