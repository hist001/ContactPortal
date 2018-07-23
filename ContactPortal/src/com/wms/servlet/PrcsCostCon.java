/*************************************************************
*	�� �� ��  : PrcsCostCon.java
*	�ۼ�����  : 2004/09/03
*	�� �� ��  : 
*	��    ��  : �������� ���� ����ó��
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

public class PrcsCostCon extends WmsServlet{

	protected void performTask(HttpServletRequest req, HttpServletResponse res)
		throws Exception {
		try {
			//���� �˻�
			//���� �ο� �˻�
			//���� ���� �˻�
			//
			Box box = HttpUtility.getBox(req);
			String _ACT=box.get("_ACT");
			String _SCREEN=box.get("_SCREEN");			
			HttpSession session = req.getSession();
			
			if(_ACT.equals("I")){//���� ����
				String prodNo = box.get("prodNo");
				String prodType = box.get("prodType");
				String prcsNo = box.get("prcsNo");
				String[] accoItems = req.getParameterValues("accoItem");
				String[] chkFlags = req.getParameterValues("chkFlag");
				
				IPrcsCost iprcscost = (IPrcsCost)DAOFactory.getDAO("IPrcsCost");
				boolean prcscostdto = iprcscost.add(prodNo, prodType, prcsNo, accoItems, chkFlags);

				progressMessagePage(req,res,_SCREEN);
			}else if(_ACT.equals("U")){//save
				String prodNo = box.get("prodNo");
				String prodType = box.get("prodType");
				String prcsNo = box.get("prcsNo");
				String[] accoItems = req.getParameterValues("accoItem");
				String[] contAmts= req.getParameterValues("contAmt");
				String[] goalAmts = req.getParameterValues("goalAmt");
				String[] chkFlags = req.getParameterValues("chkFlag");
				
				IPrcsCost iprcscost = (IPrcsCost)DAOFactory.getDAO("IPrcsCost");
				int prcscostdto = iprcscost.update(prodNo, prodType, prcsNo, accoItems, contAmts, goalAmts, chkFlags);

				progressMessagePage(req,res,_SCREEN);
			}else if(_ACT.equals("D")){//save
				String prodNo = box.get("prodNo");
				String prodType = box.get("prodType");
				String prcsNo = box.get("prcsNo");
				String[] accoItems = req.getParameterValues("accoItem");
				String[] chkFlags = req.getParameterValues("chkFlag");

				IPrcsCost iprcscost = (IPrcsCost)DAOFactory.getDAO("IPrcsCost");
				int prcscostdto = iprcscost.delete(prodNo, prodType, prcsNo, accoItems, chkFlags);

				progressMessagePage(req,res,_SCREEN);
			}else if(_ACT.equals("LR")){//�ش� ��ǰ�� ���� List
				String prodNo = box.get("prodNo");
				String prodType = box.get("prodType");
				String prcsNo = box.get("prcsNo");

				IPrcsCost iprcscost = (IPrcsCost)DAOFactory.getDAO("IPrcsCost");
				PrcsCostDTO[] prcscostdto = iprcscost.prcsCostSearch(prodNo, prodType, prcsNo);
			        req.setAttribute("prcsCostSearchList", prcscostdto);	
			        
				//��ǰ �⺻���� ��ȸ
				IProd iprod = (IProd)DAOFactory.getDAO("IProd");
				ProdDTO proddto = iprod.searchProd(prodNo,prodType);
			        req.setAttribute("searchProd", proddto);	

				//Ư�������ڵ� ��ȸ
				IPrcs iprcs = (IPrcs)DAOFactory.getDAO("IPrcs");
				PrcsDTO prcsdto = iprcs.prcsSubSearch(prodNo, prcsNo, prodType);
			        req.setAttribute("prcsSubSearch", prcsdto);	

			        printJspPage(req,res,_SCREEN);		
			}else if(_ACT.equals("R")){//�ϳ��� ������ �˻��Ѵ�.
				//������ȸ
				String prodNo = box.get("prodNo");
				String prodType = box.get("prodType");
				String prcsNo = box.get("prcsNo");
				String accoItem = box.get("accoItem");

				IPrcsCost iprcscost = (IPrcsCost)DAOFactory.getDAO("IPrcsCost");
				PrcsCostDTO prcscostdto = iprcscost.prcsCostSubSearch(prodNo, prodType, prcsNo, accoItem);
			        req.setAttribute("prcsCostSubSearch", prcscostdto);	
			        
				//��ǰ �⺻���� ��ȸ
				IProd iprod = (IProd)DAOFactory.getDAO("IProd");
				ProdDTO proddto = iprod.searchProd(prodNo,prodType);
			        req.setAttribute("searchProd", proddto);	

				//Ư�������ڵ� ��ȸ
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
