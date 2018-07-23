/*************************************************************
*	파 일 명  : AccoCon.java
*	작성일자  : 2004/09/03
*	작 성 자  : 
*	내    용  : 계정과목 관련 제어처리
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

public class AccoCon extends WmsServlet{

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
			
			if(_ACT.equals("LR")){//해당 제품의 공정 Lis
				String empId = box.get("empId");
				String prodNo = box.get("prodNo");
				String prcsNo = box.get("prcsNo");
				String prodType = box.get("prodType");
				String accoItem = box.get("txtAccoItem");
				String accoKorName = box.get("txtAccoKName");

				IAcco iacco = (IAcco)DAOFactory.getDAO("IAcco");
				AccoDTO[] accodto = iacco.searchAccoList(accoItem, accoKorName, prodNo, prodType, prcsNo);
			        req.setAttribute("searchAccoList", accodto);	
			        
				//제품 기본정보 조회
				IProd iprod = (IProd)DAOFactory.getDAO("IProd");
				ProdDTO proddto = iprod.searchProd(prodNo,prodType);
			        req.setAttribute("searchProd", proddto);	

				//전체공정코드 조회
				IPrcs iprcs = (IPrcs)DAOFactory.getDAO("IPrcs");
				PrcsDTO prcsdto = iprcs.prcsSubSearch(prodNo, prcsNo, prodType);
			        req.setAttribute("prcsSubSearch", prcsdto);	

			        printJspPage(req,res,_SCREEN);		
			}
		} catch(Exception e) {
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
            		erorrMessagePage(req,res,e);
		}
	}
}
