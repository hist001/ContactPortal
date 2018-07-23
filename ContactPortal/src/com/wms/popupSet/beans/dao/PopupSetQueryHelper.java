/*************************************************************
*	파 일 명  : PopupSetQueryHelper.java
*	작성일자  : 2006/07/26
*	작 성 자  : mailbest
*	내    용  : 팝업 셋팅 정보 Queryhelper
*************************************************************/ 
package com.wms.popupSet.beans.dao;

import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;

import com.wms.fw.Configuration;
import com.wms.fw.Logger;
import com.wms.fw.db.DataBaseUtil;
import com.wms.fw.db.SQLMapping;
import com.wms.fw.db.SQLXmlDAO;
import com.wms.comPopup.beans.dto.ComPopupSetDTO;
import com.wms.popupSet.beans.dto.PopupSetDTO;


public class PopupSetQueryHelper {
	
	static String dir;
	
	public static String insertPopupSet(PopupSetDTO dto) throws Exception{
        if(dir==null) dir = (new Configuration()).get("com.wms.fw.sql.dir");

        HashMap hm    = SQLXmlDAO.loadRequestMappings(dir+"\\ComPopup.xml");

        SQLMapping sm = (SQLMapping)hm.get("insertPopupSet");
        
        sm.setString(1,dto.paramId );
        sm.setString(2,dto.paramType );
        sm.setString(3,dto.param1 );
        sm.setString(4,dto.param2 );
        sm.setString(5,dto.param3 );

        return sm.toSql();
        
    } 

	public static String deletePopupSet(PopupSetDTO dto) throws Exception{
        if(dir==null) dir = (new Configuration()).get("com.wms.fw.sql.dir");

        HashMap hm    = SQLXmlDAO.loadRequestMappings(dir+"\\ComPopup.xml");

        SQLMapping sm = (SQLMapping)hm.get("deletePopupSet");
        
        sm.setString(1,dto.paramId );
        sm.setString(2,dto.param1 );

        return sm.toSql();
        
    } 
	public static String savePopupSet(PopupSetDTO dto) throws Exception{
        if(dir==null) dir = (new Configuration()).get("com.wms.fw.sql.dir");

        HashMap hm    = SQLXmlDAO.loadRequestMappings(dir+"\\ComPopup.xml");

        SQLMapping sm = (SQLMapping)hm.get("savePopupSet");
        
        sm.setString(1,dto.param1 );
        sm.setString(2,dto.param2 );
        sm.setString(3,dto.param3 );
        sm.setString(4,dto.paramId );

        return sm.toSql();        
    } 

 	public static String searchPopupSetList(String paramId,String PopupTitle) throws Exception{
    
    	if(dir==null) dir = (new Configuration()).get("com.wms.fw.sql.dir");
        
        HashMap hm    = SQLXmlDAO.loadRequestMappings(dir+"\\ComPopup.xml");
        
        SQLMapping sm = (SQLMapping)hm.get("popupList");        
        
        sm.setString(1,paramId);
        sm.setString(2,PopupTitle);       
		
        return sm.toSql();        
    }  
 	public static String searchOrgMemberList(String paramId) throws Exception{
 		
 		if(dir==null) dir = (new Configuration()).get("com.wms.fw.sql.dir");
 		
 		HashMap hm    = SQLXmlDAO.loadRequestMappings(dir+"\\ComPopup.xml");
 		
 		SQLMapping sm = (SQLMapping)hm.get("orgMemberList");        
 		
 		sm.setString(1,paramId);
 		
// 		System.out.println("searchOrgMemberList Query==>\n" + sm);
 		
 		return sm.toSql();        
 	}  
 	public static String searchOrgMemberSetList(ComPopupSetDTO dtos) throws Exception{
 		
 		if(dir==null) dir = (new Configuration()).get("com.wms.fw.sql.dir");
 		
 		HashMap hm    = SQLXmlDAO.loadRequestMappings(dir+"\\ComPopup.xml");
 		
//        System.out.println("param========>" +dtos.param);
        SQLMapping sm = (SQLMapping)hm.get(dtos.param);        
        
        sm.setString(1,dtos.paramId);
        sm.setString(2,dtos.paramId);
        sm.setString(3,dtos.empId);
		
//        System.out.println("searchSetCodeList==>\n" + sm);
        
        return sm.toSql();              
 	}   	
	public static boolean saveOrgMemberSet(PopupSetDTO[] dto) throws Exception{
        if(dir==null) dir = (new Configuration()).get("com.wms.fw.sql.dir");
		Connection con = null;
        String sql= "";
        Statement stmt=null;
        HashMap hm    = SQLXmlDAO.loadRequestMappings(dir+"\\ComPopup.xml");
        
        try{
        con = DataBaseUtil.getConnection();
		con.setAutoCommit(false);
		stmt=con.createStatement();
        
        SQLMapping sm1 = (SQLMapping)hm.get("deletePopupSetDetail");
        SQLMapping sm2 = (SQLMapping)hm.get("insertPopupSet");
        
//        	System.out.println("sm1 sql: " + sm1.toSql());
//        	System.out.println("sm2 sql: " + sm2.toSql());
        
        	//삭제
         	sm1.setString(1,dto[0].paramId );        	
         	sm1.setString(2,dto[0].param2 );        	
         	
//         	System.out.println("sm1.toSql() -> " + sm1.toSql());
         	stmt.addBatch(sm1.toSql());
//         	System.out.println("sm1.addBatch() -> " + stmt);

         	//등록
        for (int i=0;i<dto.length ;i++){;
        	if (dto[i].delFlag.equals("Y")){
//        		System.out.println("dto["+i+"].delFlag -> " + dto[i].delFlag);
		        sm2.setString(1,dto[i].paramId );
		        sm2.setString(2,"D");	
		        sm2.setString(3,dto[i].param1 );
		        sm2.setString(4,dto[i].param2 );
		        if (dto[i].rearrangeFlag.equals("Y")){		        
		        	sm2.setString(5,"Y" );
		        }else{
		        	sm2.setString(5,dto[i].param3 );
		        }
		        
		        stmt.addBatch(sm2.toSql());
        	}
        	
        	if(dto[i].selectFlag.equals("Y")){
//        		System.out.println("dto["+i+"].selectFlag -> " + dto[i].selectFlag);
		        sm2.setString(1,dto[i].paramId );
		        sm2.setString(2,"S");	
		        sm2.setString(3,dto[i].param1 );
		        sm2.setString(4,dto[i].param2 );
		        if (dto[i].rearrangeFlag.equals("Y")){		        
		        	sm2.setString(5,"Y" );
		        }else{
		        	sm2.setString(5,dto[i].param3 );
		        }
		        
		        System.out.println("--saveOrgMemberSet-----------------> " +sm2.toSql());
		        stmt.addBatch(sm2.toSql());
	        }
        	
        	if(!dto[i].rearrangeFlag.equals("")&!dto[i].rearrangeFlag.equals("Y")){
//        		System.out.println("dto["+i+"].rearrangeFlag -> " + dto[i].rearrangeFlag);
	        	sm2.setString(1,dto[i].paramId );
		        sm2.setString(2,"R");	
	        	sm2.setString(3,dto[i].param1 );
	        	sm2.setString(4,dto[i].param2 );
	        	sm2.setString(5,dto[i].rearrangeFlag );
	        	
	            stmt.addBatch(sm2.toSql());
		        }
	        }
     
			stmt.executeBatch();
			con.commit();
		    	return true; 		        	
    		
		}catch(Exception e){
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
		}
		finally{
			try{
				if(stmt!=null)stmt.close();
				if(con!=null)con.close();
			}catch(Exception e){
				Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
			}
		}
		return false;
	}
}
