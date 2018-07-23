/*
 * �ۼ��� ��¥: 2006.06.01
 *
 * TODO ������ ���Ͽ� ���� ���ø�Ʈ�� �����Ϸ��� �������� �̵��Ͻʽÿ�.
 * â - ȯ�� ���� - Java - �ڵ� ��Ÿ�� - �ڵ� ���ø�Ʈ
 */
package com.wms.comPopup.beans.dao;

import java.util.HashMap;

import com.wms.comPopup.beans.dto.ComPopupDTO;
import com.wms.fw.Configuration;
import com.wms.fw.db.SQLMapping;
import com.wms.fw.db.SQLXmlDAO;

public class ComPopupQueryHelper {
	
	static String dir;
	
    public static String searchCodeNList(ComPopupDTO dtos) throws Exception{
    
    	if(dir==null) dir = (new Configuration()).get("com.wms.fw.sql.dir");
        
        HashMap hm    = SQLXmlDAO.loadRequestMappings(dir+"\\ComPopup.xml");
        
        SQLMapping sm = (SQLMapping)hm.get(dtos.param);  
        
        sm.setStringParam("paramId",dtos.paramId);
        sm.setStringParam("codeName",dtos.codeName);
        sm.setStringParam("code",dtos.code);
        sm.setStringParam("empId",dtos.empId);
        
        return sm.paramToString();
      
    }    

    public static String searchCodeList(ComPopupDTO dtos) throws Exception{
        
    	if(dir==null) dir = (new Configuration()).get("com.wms.fw.sql.dir");
        HashMap hm    = SQLXmlDAO.loadRequestMappings(dir+"\\ComPopup.xml");
        
        SQLMapping sm = (SQLMapping)hm.get(dtos.param);        
        
        sm.makeParamSql(dtos, sm); // �Ķ���� ����       
		
        return sm.paramToString();
    }    
}
