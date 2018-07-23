/*************************************************************
*	�� �� ��  : AccoCon.java
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

public class AccoCon extends WmsServlet{

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
			
			if(_ACT.equals("LR")){//�ش� ��ǰ�� ���� Lis
				String empId = box.get("empId");
				String prodNo = box.get("prodNo");
				String prcsNo = box.get("prcsNo");
				String prodType = box.get("prodType");
				String accoItem = box.get("txtAccoItem");
				String accoKorName = box.get("txtAccoKName");

				IAcco iacco = (IAcco)DAOFactory.getDAO("IAcco");
				AccoDTO[] accodto = iacco.searchAccoList(accoItem, accoKorName, prodNo, prodType, prcsNo);
			        req.setAttribute("searchAccoList", accodto);	
			        
				//��ǰ �⺻���� ��ȸ
				IProd iprod = (IProd)DAOFactory.getDAO("IProd");
				ProdDTO proddto = iprod.searchProd(prodNo,prodType);
			        req.setAttribute("searchProd", proddto);	

				//��ü�����ڵ� ��ȸ
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
