/*************************************************************
*	�� �� ��  : PrcsManCntCon.java
*	�ۼ�����  : 2004/09/03
*	�� �� ��  : 
*	��    ��  : �����ο� ���� ����ó��
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
			}else if(_ACT.equals("LR")){//�ش� ��ǰ�� ���� List
				String prodNo = box.get("prodNo");
				String prcsNo = box.get("prcsNo");
				String prodType = box.get("prodType");

				IPrcsManCnt iprcsmancnt = (IPrcsManCnt)DAOFactory.getDAO("IPrcsManCnt");
				PrcsManCntDTO[] prcsmancntdto = iprcsmancnt.prcsManCntSearchList(prodNo, prcsNo, prodType);
			        req.setAttribute("prcsManCntSearchList", prcsmancntdto);	

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
				String prodNo = box.get("prodNo");
				String prcsNo = box.get("prcsNo");
				String prodType = box.get("prodType");
				String empId = box.get("empId");

				IPrcsManCnt iprcsmancnt = (IPrcsManCnt)DAOFactory.getDAO("IPrcsManCnt");
				PrcsManCntDTO prcsmancntdto = iprcsmancnt.prcsManCntSubSearch(prodNo, prcsNo, prodType, empId);
			        req.setAttribute("prcsManCntSubSearch", prcsmancntdto);	

				//��ǰ �⺻���� ��ȸ
				IProd iprod = (IProd)DAOFactory.getDAO("IProd");
				ProdDTO proddto = iprod.searchProd(prodNo,prodType);
			        req.setAttribute("searchProd", proddto);	

				//Ư�������ڵ� ��ȸ
				IPrcs iprcs = (IPrcs)DAOFactory.getDAO("IPrcs");
				PrcsDTO prcsdto = iprcs.prcsSubSearch(prodNo, prcsNo, prodType);
			        req.setAttribute("prcsSubSearch", prcsdto);	

			        printJspPage(req,res,_SCREEN);		
			}else if(_ACT.equals("ER") || _ACT.equals("OER") || _ACT.equals("PER")){//���� �˻�
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

				//��ǰ �⺻���� ��ȸ
				IProd iprod = (IProd)DAOFactory.getDAO("IProd");
				ProdDTO proddto = iprod.searchProd(prodNo,prodType);
			    req.setAttribute("searchProd", proddto);	
				System.out.println("2222");

				//Ư�������ڵ� ��ȸ
				IPrcs iprcs = (IPrcs)DAOFactory.getDAO("IPrcs");
				PrcsDTO prcsdto = iprcs.prcsSubSearch(prodNo, prcsNo, prodType);
		        req.setAttribute("prcsSubSearch", prcsdto);	
				System.out.println("3333");
		        printJspPage(req,res,_SCREEN);		
			}else if(_ACT.equals("R")){//�ϳ��� ������ �˻��Ѵ�.
	                }
		} catch(Exception e) {
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
            		erorrMessagePage(req,res,e);
		}
	}
}
