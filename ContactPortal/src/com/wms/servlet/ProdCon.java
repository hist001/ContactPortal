/*************************************************************
*	파 일 명  : ProdCon.java
*	작성일자  : 2004/09/03
*	작 성 자  : 
*	내    용  : 제품 관련 제어처리
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
public class ProdCon extends WmsServlet{

	protected void performTask(HttpServletRequest req, HttpServletResponse res)	throws Exception {
		try {
			Box box = HttpUtility.getBox(req);
			String _ACT=box.get("_ACT");
			String _SCREEN=box.get("_SCREEN");
			HttpSession session = req.getSession();
			UserInfo user =(UserInfo)session.getAttribute("user");
			String page=_SCREEN;
			if(_ACT.equals("I")){//제품 및 Task 등록
				boolean returns = false;
				String accoItems = box.get("accoItems");
				ProdDTO dto = new ProdDTO();
				box.copyToEntity(dto);
				IProd iprod = (IProd)DAOFactory.getDAO("IProd");
				com.wms.fw.Utility.fixNullAndTrimAll(dto);
				returns = iprod.createProd(dto,accoItems);
				if(!returns){
					throw new Exception("제품등록에 실패했습니다.");
				}
				progressMessagePage(req, res, page);
				return;
			}else if(_ACT.equals("U")){
				//제품update 및 표준공정초기화
				ProdDTO updatedDto = new ProdDTO();
				//표준공정초기화기능--삭제됨
				String prcsChangeYN = box.get("prcsChangeYN");
				box.copyToEntity(updatedDto);
				IProd prod = (IProd)DAOFactory.getDAO("IProd");
				com.wms.fw.Utility.fixNullAndTrimAll(updatedDto);
			    int[] cnt = prod.updateProd(updatedDto,prcsChangeYN);
			    System.out.println("Updated row =" + cnt.length);
				progressMessagePage(req,res,page);

			}else if(_ACT.equals("D")){
				//제품 Delete, 삭제불가조건: (1)PROD.rsltmh>0 (2)PRCSCOST.rsltamt>0
				ProdDTO dto = new ProdDTO();
				box.copyToEntity(dto);
				IProd prod = (IProd)DAOFactory.getDAO("IProd");
				Boolean returns = new Boolean(prod.deleteProd(dto));
				if(!returns.booleanValue()){
					throw new Exception("삭제처리시 에러가 발생했습니다. <br> 일일보고에 실적이 0 이상이거나 <br> 공정원가가 0 이상일 수 있습니다.");				
				}
				req.setAttribute("returns", returns);
				progressMessagePage(req,res,page);

			}else if(_ACT.equals("R")){//한 건의 제품을 조회한다.
				//제품 조회
				ProdDTO dto = new ProdDTO();
				box.copyToEntity(dto);
				IProd prod = (IProd)DAOFactory.getDAO("IProd");
				ProdDTO proddto = prod.searchProd(dto.prodNo,dto.prodType);
				req.setAttribute("searchOneProd", proddto);
				
				//코드 조회
				//System.out.println("--------------"+dto.prcsType);
				ICoCode coCode = (ICoCode)DAOFactory.getDAO("ICoCode");
				//수정화면에서 표준공정은 무조건 공정형태코드만을 사용한다.
				CoCodeDTO[] coCodeDto = coCode.coCodeSearch("PR","");
		        	req.setAttribute("coCodeSearch", coCodeDto);	
				printJspPage(req,res,page);

			}else if(_ACT.equals("LR")){//조건에 해당하는 제품 List를 보여준다.
				//String empId = box.get("empId"); notes와의 연동문제로 수정함
				String empId = req.getParameter("empId");
				String prodName = box.get("prodName");
				String bizType = box.get("bizType");
				String prodStatus = box.get("prodStatus");
				if(empId==null){
					empId = user.empId;					
				}
				String prodType = box.get("prodType");
				String searchType = "N";	//normal 추정원가가 아님
				IProd prod = (IProd)DAOFactory.getDAO("IProd");
				//System.out.println("empId :: "+empId+" prodType :: "+prodType+" role :: "+user.role);
				
			  //ProdDTO[] proddto = prod.searchProdList(empId, prodType, prodName, user.role, searchType, bizType, prodStatus);
				ProdDTO[] proddto = prod.searchProdList(empId, prodType, prodName, user, searchType, bizType, prodStatus);
				
				req.setAttribute("prodName",prodName);
				req.setAttribute("bizType",bizType);
				req.setAttribute("prodStatus",prodStatus);
				
				req.setAttribute("searchProdList", proddto);	
				printJspPage(req,res,page);
			}else if(_ACT.equals("RD")){//해당 제품을 검색한다.
				String empId = box.get("empId");
				String prodNo = box.get("prodNo");
				String prodType = box.get("prodType");
				
				IProd prod = (IProd)DAOFactory.getDAO("IProd");
				ProdDTO[] proddto = prod.searchProdDetail(empId, prodNo, prodType);
				
				req.setAttribute("searchProdDetail", proddto);	
				printJspPage(req,res,page);
			}else if(_ACT.equals("LRP")){//해당 제품을 검색한다.
				String empId = box.get("empId");
				String prodNo = box.get("prodNo");
				String prodType = box.get("prodType");
				String prodName = box.get("prodName");

				IProd prod = (IProd)DAOFactory.getDAO("IProd");
				
				//ProdDTO[] proddto = prod.searchProd(empId, prodType, prodNo, prodName, user.role);
				ProdDTO[] proddto = prod.searchProd(empId, prodType, prodNo, prodName, user);
				
				req.setAttribute("searchProd", proddto);	
				printJspPage(req,res,page);
			}else if(_ACT.equals("LRPT")){//해당 제품을 검색한다.
				String empId = box.get("empId");
				String orgProdNo = box.get("prodNo");
				String outerProdNo = box.get("prodNo3");
				String prodType = box.get("prodType");
				String prodName = box.get("prodName3");
				String prcsType = box.get("prcsType");

				IProd prod = (IProd)DAOFactory.getDAO("IProd");
				
				//ProdDTO[] proddto = prod.searchProd(empId, prodType, orgProdNo, outerProdNo, prodName, prcsType, user.role);
				ProdDTO[] proddto = prod.searchProd(empId, prodType, orgProdNo, outerProdNo, prodName, prcsType, user);
				
				
				req.setAttribute("searchProdT", proddto);	
				printJspPage(req,res,page);
			}else if(_ACT.equals("SEARCH")){//해당 제품의 중복여부를 검색한다.
				ProdDTO[] returns = DupCheck.dupCheck3(box.get("key1"),box.get("value1"),box.get("tableName"));
				req.setAttribute("returns",returns);
				printJspPage(req,res,page);	
			}else if(_ACT.equals("LR2")){//추정원가에서 조건에 해당하는 제품 List를 보여준다.				
				String empId = req.getParameter("empId");
				String prodName = box.get("prodName");
				String bizType = box.get("bizType");
				String prodStatus = box.get("prodStatus");
				if(empId==null){
					empId = user.empId;					
				}
				String searchType = "A";	//추정원가에서 사용된 것을 표시함
				//user.role = "LR2";		
				String prodType = box.get("prodType");
				IProd prod = (IProd)DAOFactory.getDAO("IProd");
				//System.out.println("empId :: "+empId+" prodType :: "+prodType+" role :: "+user.role+" searchType :: " + searchType);
				
				//ProdDTO[] proddto = prod.searchProdList(empId, prodType, prodName, user.role, searchType, bizType, prodStatus);
				ProdDTO[] proddto = prod.searchProdList(empId, prodType, prodName, user, searchType, bizType, prodStatus);
				
				req.setAttribute("searchProdList", proddto);	
				printJspPage(req,res,page);
			}
		} catch(Exception e) {
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
            erorrMessagePage(req,res,e);
		}
	}
	
	
}
