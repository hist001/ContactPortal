/*************************************************************
*	�� �� ��  : PrcsCon.java
*	�ۼ�����  : 2004/09/03
*	�� �� ��  : 
*	��    ��  : ���� ���� ���� ó��
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

public class PrcsCon extends WmsServlet{

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
			UserInfo user =(UserInfo)session.getAttribute("user");			
			if(_ACT.equals("I")){//���� ����
				PrcsDTO insertDto = new PrcsDTO();
				box.copyToEntity(insertDto);
				IPrcs iprcs = (IPrcs)DAOFactory.getDAO("IPrcs");
				com.wms.fw.Utility.fixNullAndTrimAll(insertDto);
				int prcsdto = iprcs.add(insertDto);
			        
			        System.out.println("���� insert �Ǽ� = "+prcsdto);
				progressMessagePage(req,res,_SCREEN);
			}else if(_ACT.equals("ICP")){//�����ڵ带 �����ϸ鼭 ������� ����
				String prodNo = box.get("prodNo");
				String prcsNo = box.get("prcsNo");
				String prodType = box.get("prodType");
				String nPrcsNo = box.get("nPrcsNo");
				String nPrcsName = box.get("nPrcsName");
				
				IPrcs iprcs = (IPrcs)DAOFactory.getDAO("IPrcs");
				int prcsdto = iprcs.addCopyPrcs(prodNo, prcsNo, prodType, nPrcsNo, nPrcsName);
			        
			        System.out.println("���� insert �Ǽ� = "+prcsdto);
				progressMessagePage(req,res,_SCREEN);
			}else if(_ACT.equals("ICP2")){//�����ڵ�� ������ �ʰ� ���븸 ����
				String prodNo = box.get("prodNo");
				String prodType = box.get("prodType");
				String copyPrcsNo = box.get("prcsNo");
				String[] copiedPrcsNo = req.getParameterValues("copiedPrcsNo");
				String[] chkFlags = req.getParameterValues("chkFlag");

				IPrcs iprcs = (IPrcs)DAOFactory.getDAO("IPrcs");
				int prcsdto = iprcs.updateCopyPrcs2(prodNo, prodType, copyPrcsNo, copiedPrcsNo, chkFlags);
				//int prcsdto = iprcs.deleteCopyPrcs2(prodNo, prodType, copiedPrcsNo, chkFlags);
				//int prcsdto = iprcs.addCopyPrcs2(prodNo, prodType, copyPrcsNo, copiedPrcsNo, chkFlags);

				progressMessagePage(req,res,_SCREEN);
			}else if(_ACT.equals("IUCOP")){//�����ڵ�� ������ �ʰ� ���븸 ����
				String copiedProdNo = box.get("prodNo");//copiedProdNo, ������ ����� ��ǰ(����ǰ)
				String prodNo = box.get("prodNo2");//����� ������ �ִ� ��ǰ(Ÿ��ǰ)
				String prodType = box.get("prodType");
				String[] copyPrcsNos = req.getParameterValues("copyPrcsNo");
				String[] chkFlags = req.getParameterValues("chkFlag");
				String empOrgCd = user.empOrgCd;

				IPrcs iprcs = (IPrcs)DAOFactory.getDAO("IPrcs");
				int prcsdto = iprcs.copyOuterPrcs(copiedProdNo,prodNo, prodType, copyPrcsNos, chkFlags,empOrgCd);

				progressMessagePage(req,res,_SCREEN);
			}else if(_ACT.equals("U")){//save
				System.out.println("prcscon ����....******");
				PrcsDTO updatedDto = new PrcsDTO();
				box.copyToEntity(updatedDto);
				IPrcs iprcs = (IPrcs)DAOFactory.getDAO("IPrcs");
				com.wms.fw.Utility.fixNullAndTrimAll(updatedDto);
				int prcsdto = iprcs.updatePrcs(updatedDto);
			        
			        System.out.println("���� update �Ǽ� = "+prcsdto);
				progressMessagePage(req,res,_SCREEN);
			}else if(_ACT.equals("D")){//save
				String prodNo = box.get("prodNo");
				String prcsNo = box.get("prcsNo");
				String prodType = box.get("prodType");
				IPrcs iprcs = (IPrcs)DAOFactory.getDAO("IPrcs");
				int prcsdto = iprcs.deletePrcs(prodNo, prcsNo, prodType);
			    if(prcsdto==0){
					throw new Exception("������ ������ �����ϴ�. ���Ͼ������� ������ �ִ°�� ������ ������ �� �����ϴ�.");
				}    
		        System.out.println("���� delete �Ǽ� = "+prcsdto);
				progressMessagePage(req,res,_SCREEN);
			}else if(_ACT.equals("LR")){//�ش� ��ǰ�� ���� List
				String empId = box.get("empId"); 
				String prodNo = box.get("prodNo");
				String prodType = box.get("prodType");
				String prodNo2 = box.get("prodNo2");//�������� ȭ���� ������ȣ
				System.out.println("prodNo2.....="+prodNo2);
				if(prodNo2!=null & !prodNo2.trim().equals("")){
					prodNo = prodNo2;
				}

				IPrcs iprcs = (IPrcs)DAOFactory.getDAO("IPrcs");
				//admin ��� �߰��� ���� user.empId �߰�
				PrcsDTO[] prcsdto = iprcs.prcsSearch(empId, prodNo, prodType, user.role);
				req.setAttribute("prcsSearchList", prcsdto);	

				printJspPage(req,res,_SCREEN);		
			}else if(_ACT.equals("R")){//�ϳ��� ������ �˻��Ѵ�.
				//������ȸ
				String empId = box.get("empId");
				String prodNo = box.get("prodNo");
				String prcsNo = box.get("prcsNo");
				String prodType = box.get("prodType");
				IPrcs iprcs = (IPrcs)DAOFactory.getDAO("IPrcs");
				PrcsDTO prcsdto = iprcs.prcsSubSearch(prodNo, prcsNo, prodType);
			    req.setAttribute("prcsSubSearch", prcsdto);	
			        
				//�����ο� ��ȸ
				IPrcsManCnt iprcsmancnt = (IPrcsManCnt)DAOFactory.getDAO("IPrcsManCnt");
				PrcsManCntDTO[] prcsmancntdto = iprcsmancnt.prcsManCntSearchList(prodNo, prcsNo, prodType);
			    req.setAttribute("prcsManCntSearchList", prcsmancntdto);	

				//��ǰ �⺻���� ��ȸ--�����Ѱ� �˻��� ��ǰ������ ����ϴµ� �̶� admin�� ��츸 ���̰� curempid�� ����
				IProd iprod = (IProd)DAOFactory.getDAO("IProd");
				ProdDTO[] proddto = iprod.searchProd(empId, prodType, prodNo, "%", user);
			    req.setAttribute("searchProd", (proddto==null)?null:proddto[0]);	

				//��ü�����ڵ� ��ȸ
				//admin ����߰��� ���� user.empId �߰�
				PrcsDTO[] prcsdtos = iprcs.prcsSearch(empId, prodNo, prodType, user.role);
			    req.setAttribute("prcsSearchList", prcsdtos);	
			    
			    //������ �߰������� ���������� ���� ó��
			    if(box.get("reloadingFlag").equals("Y")){
			    	prcsdto.prcsName = box.get("prcsName");
			    	prcsdto.plStartDt = box.get("plStartDt");
			    	prcsdto.plEndDt = box.get("plEndDt");
			    	prcsdto.plMh = Integer.parseInt(box.get("plMh"));
			    	prcsdto.prcStatus = box.get("prcStatus");
			    	prcsdto.prcsDesc = box.get("prcsDesc");
			    	req.setAttribute("prcsSubSearch", prcsdto);	
			    }

			    printJspPage(req,res,_SCREEN);		
			}else if(_ACT.equals("CR")){//������������ ȭ�鿡�� �ʿ��� �ڷ�
				//������ȸ
				String empId = box.get("empId");
				String prodNo = box.get("prodNo");
				String prcsNo = box.get("prcsNo");
				String prodType = box.get("prodType");
				String prcsCd = box.get("prcsCd");
				IStdPrcs istdprcs = (IStdPrcs)DAOFactory.getDAO("IStdPrcs");
				StdPrcsDTO[] stdprcsdto = istdprcs.searchStdPrcs(prcsCd, prcsNo);
			        req.setAttribute("searchStdPrcs", stdprcsdto);	
			        
			        printJspPage(req,res,_SCREEN);		
			}else if(_ACT.equals("CLR")){//�������� ȭ�鿡�� �ʿ��� �ڷ�
				//������ȸ
				String prodNo = box.get("prodNo");
				String prodType = box.get("prodType");
				String prcsNo = box.get("prcsNo");
				
				IPrcs iprcs = (IPrcs)DAOFactory.getDAO("IPrcs");
				PrcsDTO[] prcsdto1 = iprcs.copyPrcsSearch(prodNo, prodType, prcsNo);
				PrcsDTO[] prcsdto2 = iprcs.copiedPrcsSearch(prodNo, prodType);

			        req.setAttribute("copyPrcsSearch", prcsdto1);
			        req.setAttribute("copiedPrcsSearch", prcsdto2);	
			        
			        printJspPage(req,res,_SCREEN);		
			}else if(_ACT.equals("VD")){//�����ڵ尡 �ߺ��Ǵ����� üũ�Ѵ�.
				String prodNo = box.get("prodNo");
				String prcsNo = box.get("prcsNo");
				String prodType = box.get("prodType");
				IPrcs iprcs = (IPrcs)DAOFactory.getDAO("IPrcs");
				boolean chekPrcsCd = iprcs.prcsCdSearch(prodNo,prodType,prcsNo);

			        req.setAttribute("prcsCdSearch", new Boolean(chekPrcsCd));
			        printJspPage(req,res,_SCREEN);
			}
		} catch(Exception e) {
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
            		erorrMessagePage(req,res,e);
		}
	}
}
