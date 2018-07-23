/*************************************************************
*	�� �� ��  : InstallCon.java
*	�ۼ�����  : 2004/09/03
*	�� �� ��  : 
*	��    ��  : ��ġ���� ���� ����ó��
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
public class InstallCon extends WmsServlet{

	protected void performTask(HttpServletRequest req, HttpServletResponse res)	throws Exception {
		try {
			Box box = HttpUtility.getBox(req);
			String _ACT=box.get("_ACT");
			String _SCREEN=box.get("_SCREEN");
			HttpSession session = req.getSession();
			UserInfo user =(UserInfo)session.getAttribute("user");
			String page=_SCREEN;
			if(_ACT.equals("I")){//��ġ�������
				
				boolean returns = false;
				InstallDTO installDTO = new InstallDTO();
				box.copyToEntity(installDTO);
				IInstall iinstall = (IInstall)DAOFactory.getDAO("IInstall");
				com.wms.fw.Utility.fixNullAndTrimAll(installDTO);
				returns = iinstall.createInstall(installDTO);
				if(!returns){
					throw new Exception("��ġ���� ��Ͽ� �����߽��ϴ�.");
				}
				progressMessagePage(req, res, page);
				return;

			}else if(_ACT.equals("U")){
				//��ġ���� update
				InstallDTO installDTO = new InstallDTO();
				box.copyToEntity(installDTO);
				IInstall iinstall = (IInstall)DAOFactory.getDAO("IInstall");
				com.wms.fw.Utility.fixNullAndTrimAll(installDTO);
				boolean rt = iinstall.updateInstall(installDTO);
				Boolean returns = new Boolean(rt);
				req.setAttribute("returns", returns);
					
				progressMessagePage(req, res, page);
			}else if(_ACT.equals("D")){
				InstallDTO installDTO = new InstallDTO();
				box.copyToEntity(installDTO);
				IInstall iinstall = (IInstall)DAOFactory.getDAO("IInstall");
				boolean rt = iinstall.deleteInstall(installDTO);
				Boolean returns = new Boolean(rt);
				req.setAttribute("returns", returns);
				progressMessagePage(req, res, page);

			}else if(_ACT.equals("R")){//�� ���� ��ġ������ ��ȸ�Ѵ�.
				InstallDTO installDTO = new InstallDTO();
				box.copyToEntity(installDTO);
				IInstall iinstall = (IInstall)DAOFactory.getDAO("IInstall");
				installDTO = (InstallDTO)iinstall.searchInstall(installDTO);
				System.out.println("-----------"+installDTO);
				req.setAttribute("searchInstall", installDTO);
				printJspPage(req,res,page);
			}else if(_ACT.equals("RD")){//detail search
			}else if(_ACT.equals("LR")){//���ǿ� �ش��ϴ� ��ġ����List�� �����ش�.
				String prodType = box.get("prodType");
				String prodNo = box.get("prodNo");
				String prcsNo = box.get("prcsNo");
				System.out.println("----------"+prodType+prodNo+prcsNo);
								
				IInstall iinstall = (IInstall)DAOFactory.getDAO("IInstall");
				InstallDTO[] installDTOs = (InstallDTO[])iinstall.searchInstallList(prodType,prodNo,prcsNo);
				req.setAttribute("searchInstallList",installDTOs);
				printJspPage(req,res,page);
			}
		} catch(Exception e) {
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
            erorrMessagePage(req,res,e);
		}
	}
	
	
}
