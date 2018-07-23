/*************************************************************
*	파 일 명  : SearchOrgMemberListAction.java
*	작성일자  : 2005/02/07
*	작 성 자  : mailbest
*	내    용  : 조직_사원리스트 클래스
*************************************************************/

package com.wms.popupSet.web.action;

import com.wms.fw.web.action.WmsActionImpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.wms.beans.dto.UserInfo;
import com.wms.fw.DAOFactory;
import com.wms.fw.Logger;
import com.wms.popupSet.beans.dao.*;
import com.wms.comPopup.beans.dto.*;

public class SearchOrgMemberListAction extends WmsActionImpl{

	public void perform(HttpServletRequest req,HttpServletResponse res){
		String page = box.get("_SCREEN");
		UserInfo user =(UserInfo)session.getAttribute("user");
		ComPopupSetDTO[] returns =null;
		ComPopupDTO dtos = null;
		
		try {
			
			String paramId = box.getString("paramId");
			System.out.println("paramId=======:" +paramId);
			
			IPopupSet ipopupSet = (IPopupSet)DAOFactory.getDAO("IPopupSet");
			
			returns = ipopupSet.searchOrgMemberList(paramId);
			
			System.out.println("returns=======:");
			
			req.setAttribute("returns", returns);
			req.setAttribute("paramId", paramId);
			
			printJspPage(req,res,page);
			
		} catch(Exception e) {
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
        		erorrMessagePage(req,res,e);
		}
	}

}
