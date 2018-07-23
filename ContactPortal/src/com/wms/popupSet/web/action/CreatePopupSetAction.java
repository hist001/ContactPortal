/*************************************************************
*	�� �� ��  : CreatePopupSetAction.java_
*	�ۼ�����  : 2006-07-26
*	�� �� ��  : mailbest
*	��    ��  : �˾�����  ��� Ŭ����
*************************************************************/
package com.wms.popupSet.web.action;

import com.wms.fw.web.action.WmsActionImpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.wms.popupSet.beans.dto.*;
import com.wms.popupSet.beans.dao.*;
import com.wms.beans.dto.UserInfo;
import com.wms.fw.DAOFactory;
import com.wms.fw.Logger;

public class CreatePopupSetAction extends WmsActionImpl{

	 public void perform(HttpServletRequest req,HttpServletResponse res){
		String page = box.get("_SCREEN");
		UserInfo user =(UserInfo)session.getAttribute("user");
		boolean returns = false;
							
		try {
			PopupSetDTO dto = new PopupSetDTO();	
			System.out.println(box);	
			box.copyToEntity(dto);				
			
			IPopupSet ipopupSet = (IPopupSet)DAOFactory.getDAO("IPopupSet");
			returns = ipopupSet.insertPopupSet(dto);			
			req.setAttribute("returns", Boolean.valueOf(returns));			
			progressMessagePage(req,res,page);
		} catch(Exception e) {
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
        		erorrMessagePage(req,res,e);
		}
	}
}
