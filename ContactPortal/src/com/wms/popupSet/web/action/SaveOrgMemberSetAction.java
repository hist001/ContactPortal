/*************************************************************
*	파 일 명  : SavePopupSetAction
*	작성일자  : 2005/02/07
*	작 성 자  : 허대영
*	내    용  : 조직리스트 클래스, New Version, BasicData 처리에서 분리시킴
*************************************************************/
package com.wms.popupSet.web.action;

import java.sql.Connection;
import java.sql.Statement;
import java.util.LinkedList;

import com.wms.fw.db.DataBaseUtil;
import com.wms.fw.web.action.WmsActionImpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.wms.popupSet.beans.dto.*;
import com.wms.popupSet.beans.dao.*;
import com.wms.beans.dto.UserInfo;
import com.wms.comPopup.beans.dto.ComPopupSetDTO;
import com.wms.fw.DAOFactory;
import com.wms.fw.Logger;

public class SaveOrgMemberSetAction extends WmsActionImpl{

	 public void perform(HttpServletRequest req,HttpServletResponse res){
			try {
				String _SCREEN=box.get("_SCREEN");
				System.out.println("box::"+box);
				System.out.println("_SCREEN::"+_SCREEN);

				String page=_SCREEN;	
				boolean returns = false;
				
				PopupSetDTO[] dto = (PopupSetDTO[])box.copyToEntities("com.wms.popupSet.beans.dto.PopupSetDTO","param1");
								
				IPopupSet reg = (IPopupSet)DAOFactory.getDAO("IPopupSet");
				
				box.copyToEntity(dto);
								
	            System.out.println("box.copyToEntities::"+dto.length);
	            
	            returns = reg.saveOrgMemberSet(dto);
	            	
				if(!returns)
					throw new Exception("등록  실패했습니다.");
					progressMessagePage(req,res,page);
				System.out.println("Action End->::");
				
			} catch(Exception e) {
				Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
	        		erorrMessagePage(req,res,e);
	       		
			}
	}
}
