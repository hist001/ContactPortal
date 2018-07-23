/*************************************************************
*	�� �� ��  : ScrAuthCon.java
*	�ۼ�����  : 2004/06/22
*	�� �� ��  : 
*	��    ��  : ȭ����� ����(CRUD)ó�� Ŭ����
*************************************************************/
package com.wms.servlet;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.wms.fw.servlet.*;
import com.wms.fw.*;
import com.wms.beans.dto.*;
import com.wms.beans.dao.*;
import java.util.*;

public class ScrAuthCon extends WmsServlet{

	protected void performTask(HttpServletRequest req, HttpServletResponse res)	throws Exception {
		try{
			Box box = HttpUtility.getBox(req);
			String _ACT=box.get("_ACT");
			String _SCREEN=box.get("_SCREEN");
			HttpSession session = req.getSession();
			UserInfo user =(UserInfo)session.getAttribute("user");
			String page=_SCREEN;
			if(_ACT.equals("AC")){
				String scrName = box.get("scrName");
				if(AuthCheck.authCheck(user.empId, scrName)){
					Boolean returns = Boolean.valueOf(AuthCheck.authCheck(user.empId, scrName));
					req.setAttribute("result",returns);
					printJspPage(req, res, page);					
				}else{
					throw new Exception("�ش�ȭ�鿡 ���� ������ �����ϴ�.");
				}
			}else if(_ACT.equals("I")){
				BasicDataDTO dto = new BasicDataDTO();
				int returns = 0;
				dto.set(req);
				dto.remove("screenName");
				IBasicData ibd = (IBasicData)DAOFactory.getDAO("IBasicData");
				returns = ibd.createBasicData(dto);
				req.setAttribute("returns", String.valueOf(returns));
				progressMessagePage(req,res,page);
			}else if(_ACT.equals("U")){
				BasicDataDTO dto = new BasicDataDTO();
				int returns = 0;
				dto.set(req);
				dto.remove("screenName");
				String[] pks = new String[2];
				pks[0] = "empId";
				pks[1] = "scrName";
				IBasicData ibd = (IBasicData)DAOFactory.getDAO("IBasicData");
				returns = ibd.updateBasicData(dto,pks);
				System.out.println("---------"+pks[0]+":"+pks[1]);
				req.setAttribute("returns", returns+"");
				progressMessagePage(req,res,page);	
			}else if(_ACT.equals("D")){
				BasicDataDTO dto = new BasicDataDTO();
				int returns = 0;
				dto.set(req);
				dto.remove("screenName");
				String[] pks = new String[2];
				pks[0] = "empId";
				pks[1] = "scrName";
				IBasicData ibd = (IBasicData)DAOFactory.getDAO("IBasicData");
				returns = ibd.deleteBasicData(dto,pks);
				req.setAttribute("returns", String.valueOf(returns));
				progressMessagePage(req,res,page);				
			}else if(_ACT.equals("R")){//ONLY ONE
				BasicDataDTO dto = new BasicDataDTO();
				BasicDataDTO returns = new BasicDataDTO();
				dto.set(req);
				dto.remove("screenName");
				IBasicData ibd = (IBasicData)DAOFactory.getDAO("IBasicData");
				returns = ibd.selectBasicData(dto);
				req.setAttribute("returns",returns);				
				printJspPage(req,res,page);
			}else if(_ACT.equals("LR")){//LIST
				BasicDataDTO dto = new BasicDataDTO();
				BasicDataDTO[] returns = null ;
				dto.set(req);
				dto.remove("empId");
				dto.remove("screenName");
				System.out.println("dto::"+dto);
				IBasicData ibd = (IBasicData)DAOFactory.getDAO("IBasicData");
				returns = ibd.selectBasicDataList(dto);
				req.setAttribute("returns",returns);				
				printJspPage(req,res,page);
			}
		}catch(Exception e) {
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
			erorrMessagePage(req,res,e);
		}
	}	
}
