/*************************************************************
*	파 일 명  : SearchPopupSetListAction.java
*	작성일자  : 2005/02/07
*	작 성 자  : mailbest
*	내    용  : 팝업 세부 Setting 정보 조회 
*************************************************************/

package com.wms.popupSet.web.action;

import com.wms.fw.web.action.WmsActionImpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.wms.beans.dto.UserInfo;
import com.wms.fw.DAOFactory;
import com.wms.fw.Logger;
import com.wms.popupSet.beans.dto.*;
import com.wms.popupSet.beans.dao.*;
import com.wms.comPopup.beans.dto.*;
import com.wms.comPopup.beans.dao.*;

public class SearchPopupSetListAction extends WmsActionImpl{

	public void perform(HttpServletRequest req,HttpServletResponse res){
		String page = box.get("_SCREEN");
		UserInfo user =(UserInfo)session.getAttribute("user");
		PopupSetDTO[] returns =null;
		ComPopupDTO[] returns1 =null;
		ComPopupDTO dtos=null;
		
		try {
			String paramId = box.get("paramId");
			String PopupTitle = box.get("PopupTitle");
			
			System.out.println(box);
			
			IPopupSet ipopupSet = (IPopupSet)DAOFactory.getDAO("IPopupSet");
			
			returns = ipopupSet.searchPopupSetList(paramId, PopupTitle );
			
			req.setAttribute("returns", returns);			
			req.setAttribute("paramId", paramId);
			req.setAttribute("PopupTitle", PopupTitle);
			
			printJspPage(req,res,page);
			
		} catch(Exception e) {
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
        		erorrMessagePage(req,res,e);
		}
	}

}
