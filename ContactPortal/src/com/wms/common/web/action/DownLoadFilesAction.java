/*
 * �ۼ��� ��¥: 2005. 10. 11.
 *
 * TODO ������ ���Ͽ� ���� ���ø�Ʈ�� �����Ϸ��� �������� �̵��Ͻʽÿ�.
 * â - ȯ�� ���� - Java - �ڵ� ��Ÿ�� - �ڵ� ���ø�Ʈ
 */
package com.wms.common.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wms.fw.Logger;
import com.wms.fw.servlet.MultipartData;
import com.wms.fw.web.action.WmsActionImpl;

/**
 * @author ����ȣ
 * file download 
 */
public class DownLoadFilesAction extends WmsActionImpl {

	/* (��Javadoc)
	 * @see com.wms.fw.web.action.HTMLAction#perform(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void perform(HttpServletRequest req, HttpServletResponse res) {
		// TODO �ڵ� ������ �޼ҵ� ����	
		try {         
            
            //admin ����ó��
            //String empId = null;
            /*
            if(user.auths.containsKey("admin")){
                empId = box.get("empId");
            }else{
                empId = user.empId;                
            }
            */
			MultipartData.downLoad(box.get("dir"), box.get("fileSystemName"), box.get("fileOriginName"),  res);

            
		} catch(Exception e) {
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
			e.printStackTrace();			
			erorrMessagePage(req,res,e);
		}		
	}

}
