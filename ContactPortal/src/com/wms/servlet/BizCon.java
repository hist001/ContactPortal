/*************************************************************
*	파 일 명  : BizCon.java
*	작성일자  : 2004/06/22
*	작 성 자  : 
*	내    용  : 사업코드관리 제어(CRUD)처리 클래스
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
public class BizCon extends WmsServlet{

	protected void performTask(HttpServletRequest req, HttpServletResponse res)	throws Exception {
		try {
			Box box = HttpUtility.getBox(req);
			String _ACT=box.get("_ACT");
			String _SCREEN=box.get("_SCREEN");
			HttpSession session = req.getSession();
			UserInfo user =(UserInfo)session.getAttribute("user");
			String page=_SCREEN;
			if(_ACT.equals("I")){//사용안함
				BasicDataDTO dto = new BasicDataDTO();
				int returns = 0;
				dto.set(req);
				dto.remove("empId");
				dto.remove("dupYN");
				dto.remove("key");
				dto.remove("value");
				IBasicData ibd = (IBasicData)DAOFactory.getDAO("IBasicData");
				returns = ibd.createBasicData(dto);
				req.setAttribute("returns", String.valueOf(returns));
				progressMessagePage(req,res,page);	
			}else if(_ACT.equals("U")){
				BasicDataDTO dto = new BasicDataDTO();
				int returns = 0;
				dto.set(req);
				dto.remove("empId");
				dto.remove("dupYN");
				dto.remove("key");
				dto.remove("value");
				String[] pks = new String[1];
				pks[0] = "bizNo";
				IBasicData ibd = (IBasicData)DAOFactory.getDAO("IBasicData");
				returns = ibd.updateBasicData(dto,pks);
				System.out.println("---------"+pks[0]);
				req.setAttribute("returns", returns+"");
				progressMessagePage(req,res,page);	
			}else if(_ACT.equals("D")){//사용안함
				BasicDataDTO dto = new BasicDataDTO();
				int returns = 0;
				dto.set(req);
				dto.remove("empId");
				dto.remove("dupYN");
				dto.remove("key");
				dto.remove("value");
				String[] pks = new String[1];
				pks[0] = "bizNo";
				IBasicData ibd = (IBasicData)DAOFactory.getDAO("IBasicData");
				returns = ibd.deleteBasicData(dto,pks);
				req.setAttribute("returns", String.valueOf(returns));
				progressMessagePage(req,res,page);				
			}else if(_ACT.equals("R")){//ONLY ONE
				BasicDataDTO dto = new BasicDataDTO();
				BasicDataDTO returns = new BasicDataDTO();
				dto.set(req);
				dto.remove("empId");
				dto.remove("dupYN");
				dto.remove("key");
				dto.remove("value");
				IBasicData ibd = (IBasicData)DAOFactory.getDAO("IBasicData");
				returns = ibd.selectBasicData(dto);
				req.setAttribute("returns",returns);				
				printJspPage(req,res,page);
			}else if(_ACT.equals("LR")){//LIST
				BasicDataDTO dto = new BasicDataDTO();
				BasicDataDTO[] returns = null ;
				dto.set(req);
				dto.remove("empId");
				dto.remove("dupYN");
				dto.remove("key");
				dto.remove("value");
				System.out.println("dto::"+dto);
				IBasicData ibd = (IBasicData)DAOFactory.getDAO("IBasicData");
				returns = ibd.selectBasicDataList(dto);
				req.setAttribute("returns",returns);				
				printJspPage(req,res,page);
			}else if(_ACT.equals("CHK")){
				//중복체크
				//DupCheck dc = new DupCheck();
				boolean result = DupCheck.dupCheck(box.get("key"),box.get("tableName"),box.get("value"));
				Boolean returns = new Boolean(result);
				req.setAttribute("returns",returns);				
				printJspPage(req,res,page);			
			}
		} catch(Exception e) {
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
            erorrMessagePage(req,res,e);
		}
	}
	
	
}
