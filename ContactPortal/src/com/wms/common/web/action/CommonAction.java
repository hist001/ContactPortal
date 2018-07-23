/*************************************************************
*	파 일 명  : CommonAction.java
*	작성일자  : 2004/10/04
*	작 성 자  : 
*	내    용  : 공통 이벤트를 처리하는 클래스
*************************************************************/
package com.wms.common.web.action;

import com.wms.fw.web.action.WmsActionImpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.wms.fw.DAOFactory;
import com.wms.fw.Logger;

public class CommonAction extends WmsActionImpl
{
    public void perform(HttpServletRequest req,HttpServletResponse res){
		try {
			String _SCREEN=box.get("_SCREEN");			
			String page=_SCREEN;				
			progressMessagePage(req,res,page);

		} catch(Exception e) {
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
        		erorrMessagePage(req,res,e);
		}
	
	}
};