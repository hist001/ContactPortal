/*
 * 작성된 날짜: 2006.08.02
 *
 * TODO 생성된 파일에 대한 템플리트를 변경하려면 다음으로 이동하십시오.
 * 창 - 환경 설정 - Java - 코드 스타일 - 코드 템플리트
 */
package com.wms.popupSet.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wms.beans.dto.UserInfo;
import com.wms.fw.DAOFactory;
import com.wms.fw.Logger;
import com.wms.fw.web.action.WmsActionImpl;
import com.wms.comPopup.beans.dao.IComPopup;
import com.wms.popupSet.beans.dao.IPopupSet;
import com.wms.comPopup.beans.dto.ComPopupDTO;
import com.wms.comPopup.beans.dto.ComPopupSetDTO;
import com.wms.popupSet.beans.dto.PopupSetDTO;
;

/**
 * @author mailbest
 * 공통 팝업 조회    
 */
public class SearchOrgMemberSetListAction extends WmsActionImpl {

	public void perform(HttpServletRequest req, HttpServletResponse res) {
		// TODO 자동 생성된 메소드 스텁	
		try {
            UserInfo user =(UserInfo)session.getAttribute("user");
            
			IPopupSet ipopupSet = (IPopupSet)DAOFactory.getDAO("IPopupSet");
            
            //admin 권한처리
			ComPopupSetDTO dtos= new ComPopupSetDTO();
           
            dtos.param		= box.get("param");
            dtos.paramId	= box.get("paramId");
            dtos.code		= box.get("code");
            dtos.codeName	= box.get("codeName");
            dtos.empId		= box.get("empId");
            
//            System.out.println(box);
            
            System.out.println("* paramId==>"+dtos.paramId);
            System.out.println("* param==>"+dtos.param);
            
            ComPopupSetDTO[] returns = ipopupSet.searchOrgMemberSetList(dtos);            
            PopupSetDTO[] returns1= ipopupSet.searchPopupSetList(dtos.paramId,"");            
            
            req.setAttribute("returns",returns);
            req.setAttribute("returns1",returns1);
            
            printJspPage(req,res, box.get("_SCREEN"));
            
		} catch(Exception e) {
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
			e.printStackTrace();			
		}		
	}

}
