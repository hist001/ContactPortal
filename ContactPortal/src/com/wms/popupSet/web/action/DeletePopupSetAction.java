/*************************************************************
*	파 일 명  : DeletePopupSetAction.java
*	작성일자  : 2005/02/11
*	작 성 자  : 허대영
*	내    용  : 조직정보 삭제처리, BasicData 처리에서 분리시킴
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

public class DeletePopupSetAction extends WmsActionImpl{

	 public void perform(HttpServletRequest req,HttpServletResponse res){
		String page = box.get("_SCREEN");
		UserInfo user =(UserInfo)session.getAttribute("user");
		boolean returns = false;
							
		try {
			PopupSetDTO dto = new PopupSetDTO();
			box.copyToEntity(dto);	
			IPopupSet ipopupSet = (IPopupSet)DAOFactory.getDAO("IPopupSet");
			
			returns = ipopupSet.deletePopupSet(dto);
			
			if (returns){
				returns = ipopupSet.deletePopupSet(dto);
			}
			req.setAttribute("returns", Boolean.valueOf(returns));			
			progressMessagePage(req,res,page);
		} catch(Exception e) {
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
        		erorrMessagePage(req,res,e);
		}
	}
}
