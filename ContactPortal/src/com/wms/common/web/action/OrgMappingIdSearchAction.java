/*************************************************************
*	�� �� ��  : OrgMappingIdSearchAction.java
*	�ۼ�����  : 2005/02/24
*	�� �� ��  : 
*	��    ��  : ��ȸ �׷� ��ȸ �̺�Ʈ�� ó���ϴ� Ŭ����
*************************************************************/
package com.wms.common.web.action;

import com.wms.fw.web.action.WmsActionImpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.wms.beans.dto.OrgCdDTO;
import com.wms.beans.dao.IGroupEmp;
import com.wms.fw.DAOFactory;
import com.wms.fw.Logger;
import com.wms.fw.Configuration;

public class OrgMappingIdSearchAction extends WmsActionImpl
{
     public void perform(HttpServletRequest req,HttpServletResponse res){
		try {

			IGroupEmp groupEmp =(IGroupEmp)DAOFactory.getDAO("IGroupEmp");
			req.setAttribute("emp",groupEmp.searchOrgMappingId(box.get("empId")));
			printJspPage(req,res,box.get("_SCREEN"));
			

		} catch(Exception e) {
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
        		erorrMessagePage(req,res,e);
		}
	}
}