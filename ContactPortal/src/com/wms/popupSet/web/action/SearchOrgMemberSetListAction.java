/*
 * �ۼ��� ��¥: 2006.08.02
 *
 * TODO ������ ���Ͽ� ���� ���ø�Ʈ�� �����Ϸ��� �������� �̵��Ͻʽÿ�.
 * â - ȯ�� ���� - Java - �ڵ� ��Ÿ�� - �ڵ� ���ø�Ʈ
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
 * ���� �˾� ��ȸ    
 */
public class SearchOrgMemberSetListAction extends WmsActionImpl {

	public void perform(HttpServletRequest req, HttpServletResponse res) {
		// TODO �ڵ� ������ �޼ҵ� ����	
		try {
            UserInfo user =(UserInfo)session.getAttribute("user");
            
			IPopupSet ipopupSet = (IPopupSet)DAOFactory.getDAO("IPopupSet");
            
            //admin ����ó��
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
