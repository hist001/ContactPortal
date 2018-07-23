/*
 * �ۼ��� ��¥: 2006.06.01
 *
 * TODO ������ ���Ͽ� ���� ���ø�Ʈ�� �����Ϸ��� �������� �̵��Ͻʽÿ�.
 * â - ȯ�� ���� - Java - �ڵ� ��Ÿ�� - �ڵ� ���ø�Ʈ
 */
package com.wms.comPopup.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wms.beans.dto.UserInfo;
import com.wms.fw.DAOFactory;
import com.wms.fw.Logger;
import com.wms.fw.web.action.WmsActionImpl;
import com.wms.comPopup.beans.dao.ComPopupDAO;
import com.wms.comPopup.beans.dao.IComPopup;
import com.wms.comPopup.beans.dto.ComPopupDTO;
import com.wms.popupSet.beans.dao.PopupSetDAO;
import com.wms.popupSet.beans.dto.PopupSetDTO;
;

/**
 * @author mailbest
 * ���� �˾� ��ȸ    
 */
public class ComPopupAction extends WmsActionImpl {

	public void perform(HttpServletRequest req, HttpServletResponse res) {
		// TODO �ڵ� ������ �޼ҵ� ����	
		try {
            UserInfo user =(UserInfo)session.getAttribute("user");
            
            String empId = user.empId;
            
            ComPopupDTO dtos= new ComPopupDTO();
            ComPopupDAO dao= new ComPopupDAO();
            PopupSetDAO popUpsetdao= new PopupSetDAO();
                      
            box.copyToEntity(dtos);  

            ComPopupDTO[] returns=null;
            
            System.out.println(box);
            
            System.out.println("* paramId==>"+dtos.paramId);
            System.out.println("* param==>"+dtos.param);
            
            if (dtos.paramId==null || dtos.paramId.equals("")){
            	System.out.println("* searchCode   List==>");
            	 returns = dao.searchCodeList(dtos);            	
            }else{
            	System.out.println("*searchCodeN   List==>");
            	returns = dao.searchCodeNList(dtos);            	
            }
            
            //�˾� �˻����� ���� Ȯ�� 
            String chkParamId[] = dtos.param.split("_");
            if (chkParamId.length==1){
            	dtos.paramId="";
            }else if (chkParamId.length==2){
            	if (!chkParamId[1].equals("new")) dtos.paramId="";
            }
            PopupSetDTO[] returns1= popUpsetdao.searchPopupSetList(dtos.paramId,"");            
            
            req.setAttribute("returns",returns);
            req.setAttribute("returns1",returns1);
            
            printJspPage(req,res, box.get("_SCREEN"));
            
		} catch(Exception e) {
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
			e.printStackTrace();			
		}		
	}

}
