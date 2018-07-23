/*
 * 작성된 날짜: 2006.06.01
 *
 * TODO 생성된 파일에 대한 템플리트를 변경하려면 다음으로 이동하십시오.
 * 창 - 환경 설정 - Java - 코드 스타일 - 코드 템플리트
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
        
        sm.makeParamSql(dtos, sm); // 파라미터 매핑       
		
        return sm.paramToString();
    }    
}
