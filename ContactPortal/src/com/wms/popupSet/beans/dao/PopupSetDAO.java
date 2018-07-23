/*************************************************************
*	파 일 명  : PopupSetDAO.java
*	작성일자  : 2006/07/26
*	작 성 자  : mailbest
*	내    용  : 팝업 셋팅 관리 클래스 DAO
*************************************************************/
package com.wms.popupSet.beans.dao;

import java.sql.*;

import com.wms.comPopup.beans.dao.ComPopupQueryHelper;
import com.wms.comPopup.beans.dto.ComPopupDTO;
import com.wms.comPopup.beans.dto.ComPopupSetDTO;
import com.wms.fw.db.DataBaseUtil;
import com.wms.popupSet.beans.dto.*;
import com.wms.fw.*;
 
public class PopupSetDAO implements IPopupSet {	
	public boolean insertPopupSet(PopupSetDTO dto) throws Exception{
        Connection con = null;
        Statement stmt=null;
        ResultSet rs = null;
		
		boolean returns = false;
		        
        int result = 0;
        try{
             
            String sql = PopupSetQueryHelper.insertPopupSet(dto);
            
            System.out.println("insertPopupSet :: \n"+sql);
            
            con = DataBaseUtil.getConnection();
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			result = stmt.executeUpdate(sql);

			if(result>0){
				System.out.println("팝업정보가  "+ result +"개 등록 되었습니다.");
				returns = true;
			}else{
				throw new Exception("팝업정보가 등록시 장애가 발생되었습니다. ");
			} 	
            
        }catch(Exception e){
                Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
        }
        finally{
            try{
                if(rs!=null)rs.close();
                if(stmt!=null)stmt.close();
                if(con!=null)con.close();
            }catch(Exception e){
                Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
            }
        }           
        return returns; 
	}
	
	public boolean deletePopupSet(PopupSetDTO dto)throws Exception{
      Connection con = null;
        Statement stmt=null;
        ResultSet rs = null;
		
		boolean returns = false;
		        
        int result = 0;
        try{
             
            String sql = PopupSetQueryHelper.deletePopupSet(dto);
            
            System.out.println("deletePopupSet :: \n"+sql);
            
            con = DataBaseUtil.getConnection();
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			result = stmt.executeUpdate(sql);

			if(result>0){
				System.out.println("팝업정보가  "+ result +"개 삭제 되었습니다.");
				returns = true;
			}else{
				throw new Exception("팝업정보가 삭제시 장애가 발생되었습니다. ");
			} 	
            
        }catch(Exception e){
                Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
        }
        finally{
            try{
                if(rs!=null)rs.close();
                if(stmt!=null)stmt.close();
                if(con!=null)con.close();
            }catch(Exception e){
                Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
            }
        }           
        return returns; 
	}
	

	
	public boolean savePopupSet(PopupSetDTO dto)throws Exception{
     Connection con = null;
        Statement stmt=null;
        ResultSet rs = null;
		
		boolean returns = false;
		        
        int result = 0;
        try{
             
            String sql = PopupSetQueryHelper.savePopupSet(dto);
            
            System.out.println("savePopupSet :: \n"+sql);
            
            con = DataBaseUtil.getConnection();
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			result = stmt.executeUpdate(sql);

			if(result>0){
				System.out.println("팝업정보가  "+ result +"개 수정 되었습니다.");
				returns = true;
			}else{
				throw new Exception("팝업정보가 수정시 장애가 발생되었습니다. ");
			} 	
            
        }catch(Exception e){
                Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
        }
        finally{
            try{
                if(rs!=null)rs.close();
                if(stmt!=null)stmt.close();
                if(con!=null)con.close();
            }catch(Exception e){
                Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
            }
        }           
        return returns; 
	}

	public PopupSetDTO[]  searchPopupSetList(String paramId,String  PopupTitle) throws Exception{
        Connection con = null;
        Statement stmt=null;
        ResultSet rs = null;
        PopupSetDTO[] returns=null;
        try{
             
            String sql = PopupSetQueryHelper.searchPopupSetList(paramId, PopupTitle);
            
            con = DataBaseUtil.getConnection();
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs = DataBaseUtil.executeSQL(con,stmt,sql); 
            
            returns=(PopupSetDTO[])DataBaseUtil.moveToEntities("com.wms.popupSet.beans.dto.PopupSetDTO",rs);
            
            com.wms.fw.Utility.fixNullAndTrimAll(returns);
            
        }catch(Exception e){
                Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
        }
        finally{
            try{
                if(rs!=null)rs.close();
                if(stmt!=null)stmt.close();
                if(con!=null)con.close();
            }catch(Exception e){
                Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
            }
        }           
        return returns; 

	}		 
	public ComPopupSetDTO[] searchOrgMemberList(String paramId) throws Exception{
		Connection con = null;
		Statement stmt=null;
		ResultSet rs = null;
		ComPopupSetDTO[] returns=null;
		try{
			
			String sql = PopupSetQueryHelper.searchOrgMemberList(paramId);
			
			System.out.println("ComPopupDAO.searchOrgMemberList :: \n"+sql);
			
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = DataBaseUtil.executeSQL(con,stmt,sql); 
			
			returns=(ComPopupSetDTO[])DataBaseUtil.moveToEntities("com.wms.comPopup.beans.dto.ComPopupSetDTO",rs);
			
			com.wms.fw.Utility.fixNullAndTrimAll(returns);
			
		}catch(Exception e){
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
		}
		finally{
			try{
				if(rs!=null)rs.close();
				if(stmt!=null)stmt.close();
				if(con!=null)con.close();
			}catch(Exception e){
				Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
			}
		}           
		return returns; 
		
	}
	public ComPopupSetDTO[]  searchOrgMemberSetList(ComPopupSetDTO dtos) throws Exception{
        Connection con = null;
        Statement stmt=null;
        ResultSet rs = null;
        ComPopupSetDTO[] returns=null;
        try{
             
            String sql = PopupSetQueryHelper.searchOrgMemberSetList(dtos);
            
            System.out.println("searchOrgMemberSetList :: \n"+sql);
            
            con = DataBaseUtil.getConnection();
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs = DataBaseUtil.executeSQL(con,stmt,sql); 
            
            returns=(ComPopupSetDTO[])DataBaseUtil.moveToEntities("com.wms.comPopup.beans.dto.ComPopupSetDTO",rs);

            com.wms.fw.Utility.fixNullAndTrimAll(returns);
            
        }catch(Exception e){
                Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
        }
        finally{
            try{ 
                if(rs!=null)rs.close();
                if(stmt!=null)stmt.close();
                if(con!=null)con.close();
            }catch(Exception e){
                Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
            }
        }           
        return returns; 

	}

	public boolean saveOrgMemberSet(PopupSetDTO[] dtos) throws Exception{
        Connection con = null;
        Statement stmt=null;
        ResultSet rs = null;
        boolean returns= false;
        try{
            int result = 0;
          
//          con = DataBaseUtil.getConnection();
//          con.setAutoCommit(false);
//          stmt=con.createStatement();
            
//          returns = PopupSetQueryHelper.saveOrgMemberSet(dtos);            
//            System.out.println("savePopupSet :: \n"+stmt.toString());
            
//			stmt.executeBatch();
//			con.commit();
//
//			returns = true;
			
//			if(>0){
//				System.out.println("팝업정보가  "+ stmt.getMaxRows() +"개 수정 되었습니다.");
//			returns = true;
//			}else{
//				throw new Exception("팝업정보가 수정시 장애가 발생되었습니다. ");
//			} 	
            
        }catch(Exception e){
                Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
        }
        finally{
            try{
                if(rs!=null)rs.close();
                if(stmt!=null)stmt.close();
                if(con!=null)con.close();
            }catch(Exception e){
                Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
            }
        }           
        return returns; 

	}		
	
}