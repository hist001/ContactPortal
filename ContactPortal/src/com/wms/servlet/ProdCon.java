/*************************************************************
*	�� �� ��  : ProdCon.java
*	�ۼ�����  : 2004/09/03
*	�� �� ��  : 
*	��    ��  : ��ǰ ���� ����ó��
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
			if(_ACT.equals("I")){//��ǰ �� Task ���
				boolean returns = false;
				String accoItems = box.get("accoItems");
				ProdDTO dto = new ProdDTO();
				box.copyToEntity(dto);
				IProd iprod = (IProd)DAOFactory.getDAO("IProd");
				com.wms.fw.Utility.fixNullAndTrimAll(dto);
				returns = iprod.createProd(dto,accoItems);
				if(!returns){
					throw new Exception("��ǰ��Ͽ� �����߽��ϴ�.");
				}
				progressMessagePage(req, res, page);
				return;
			}else if(_ACT.equals("U")){
				//��ǰupdate �� ǥ�ذ����ʱ�ȭ
				ProdDTO updatedDto = new ProdDTO();
				//ǥ�ذ����ʱ�ȭ���--������
				String prcsChangeYN = box.get("prcsChangeYN");
				box.copyToEntity(updatedDto);
				IProd prod = (IProd)DAOFactory.getDAO("IProd");
				com.wms.fw.Utility.fixNullAndTrimAll(updatedDto);
			    int[] cnt = prod.updateProd(updatedDto,prcsChangeYN);
			    System.out.println("Updated row =" + cnt.length);
				progressMessagePage(req,res,page);

			}else if(_ACT.equals("D")){
				//��ǰ Delete, �����Ұ�����: (1)PROD.rsltmh>0 (2)PRCSCOST.rsltamt>0
				ProdDTO dto = new ProdDTO();
				box.copyToEntity(dto);
				IProd prod = (IProd)DAOFactory.getDAO("IProd");
				Boolean returns = new Boolean(prod.deleteProd(dto));
				if(!returns.booleanValue()){
					throw new Exception("����ó���� ������ �߻��߽��ϴ�. <br> ���Ϻ��� ������ 0 �̻��̰ų� <br> ���������� 0 �̻��� �� �ֽ��ϴ�.");				
				}
				req.setAttribute("returns", returns);
				progressMessagePage(req,res,page);

			}else if(_ACT.equals("R")){//�� ���� ��ǰ�� ��ȸ�Ѵ�.
				//��ǰ ��ȸ
				ProdDTO dto = new ProdDTO();
				box.copyToEntity(dto);
				IProd prod = (IProd)DAOFactory.getDAO("IProd");
				ProdDTO proddto = prod.searchProd(dto.prodNo,dto.prodType);
				req.setAttribute("searchOneProd", proddto);
				
				//�ڵ� ��ȸ
				//System.out.println("--------------"+dto.prcsType);
				ICoCode coCode = (ICoCode)DAOFactory.getDAO("ICoCode");
				//����ȭ�鿡�� ǥ�ذ����� ������ ���������ڵ常�� ����Ѵ�.
				CoCodeDTO[] coCodeDto = coCode.coCodeSearch("PR","");
		        	req.setAttribute("coCodeSearch", coCodeDto);	
				printJspPage(req,res,page);

			}else if(_ACT.equals("LR")){//���ǿ� �ش��ϴ� ��ǰ List�� �����ش�.
				//String empId = box.get("empId"); notes���� ���������� ������
				String empId = req.getParameter("empId");
				String prodName = box.get("prodName");
				String bizType = box.get("bizType");
				String prodStatus = box.get("prodStatus");
				if(empId==null){
					empId = user.empId;					
				}
				String prodType = box.get("prodType");
				String searchType = "N";	//normal ���������� �ƴ�
				IProd prod = (IProd)DAOFactory.getDAO("IProd");
				//System.out.println("empId :: "+empId+" prodType :: "+prodType+" role :: "+user.role);
				
			  //ProdDTO[] proddto = prod.searchProdList(empId, prodType, prodName, user.role, searchType, bizType, prodStatus);
				ProdDTO[] proddto = prod.searchProdList(empId, prodType, prodName, user, searchType, bizType, prodStatus);
				
				req.setAttribute("prodName",prodName);
				req.setAttribute("bizType",bizType);
				req.setAttribute("prodStatus",prodStatus);
				
				req.setAttribute("searchProdList", proddto);	
				printJspPage(req,res,page);
			}else if(_ACT.equals("RD")){//�ش� ��ǰ�� �˻��Ѵ�.
				String empId = box.get("empId");
				String prodNo = box.get("prodNo");
				String prodType = box.get("prodType");
				
				IProd prod = (IProd)DAOFactory.getDAO("IProd");
				ProdDTO[] proddto = prod.searchProdDetail(empId, prodNo, prodType);
				
				req.setAttribute("searchProdDetail", proddto);	
				printJspPage(req,res,page);
			}else if(_ACT.equals("LRP")){//�ش� ��ǰ�� �˻��Ѵ�.
				String empId = box.get("empId");
				String prodNo = box.get("prodNo");
				String prodType = box.get("prodType");
				String prodName = box.get("prodName");

				IProd prod = (IProd)DAOFactory.getDAO("IProd");
				
				//ProdDTO[] proddto = prod.searchProd(empId, prodType, prodNo, prodName, user.role);
				ProdDTO[] proddto = prod.searchProd(empId, prodType, prodNo, prodName, user);
				
				req.setAttribute("searchProd", proddto);	
				printJspPage(req,res,page);
			}else if(_ACT.equals("LRPT")){//�ش� ��ǰ�� �˻��Ѵ�.
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
			}else if(_ACT.equals("SEARCH")){//�ش� ��ǰ�� �ߺ����θ� �˻��Ѵ�.
				ProdDTO[] returns = DupCheck.dupCheck3(box.get("key1"),box.get("value1"),box.get("tableName"));
				req.setAttribute("returns",returns);
				printJspPage(req,res,page);	
			}else if(_ACT.equals("LR2")){//������������ ���ǿ� �ش��ϴ� ��ǰ List�� �����ش�.				
				String empId = req.getParameter("empId");
				String prodName = box.get("prodName");
				String bizType = box.get("bizType");
				String prodStatus = box.get("prodStatus");
				if(empId==null){
					empId = user.empId;					
				}
				String searchType = "A";	//������������ ���� ���� ǥ����
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
