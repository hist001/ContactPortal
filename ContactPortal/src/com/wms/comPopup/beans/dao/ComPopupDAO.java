/*
 * �ۼ��� ��¥: 2005. 12. 2.
 *
 * TODO ������ ���Ͽ� ���� ���ø�Ʈ�� �����Ϸ��� �������� �̵��Ͻʽÿ�.
 * â - ȯ�� ���� - Java - �ڵ� ��Ÿ�� - �ڵ� ���ø�Ʈ
 */
package com.wms.comPopup.beans.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.wms.comPopup.beans.dto.ComPopupDTO;

import com.wms.fw.Logger;
import com.wms.fw.db.DataBaseUtil;

public class ComPopupDAO implements IComPopup { 

	public ComPopupDTO[]  searchCodeList(ComPopupDTO dtos) throws Exception{
        Connection con = null;
        Statement stmt=null;
        ResultSet rs = null;
        ComPopupDTO[] returns=null;
        try{
             
            String sql = ComPopupQueryHelper.searchCodeList(dtos);
            
            System.out.println("ComPopupDAO.searchCodeList :: \n"+sql);
            
            con = DataBaseUtil.getConnection();
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs = DataBaseUtil.executeSQL(con,stmt,sql); 

            returns=(ComPopupDTO[])DataBaseUtil.moveToEntities("com.wms.comPopup.beans.dto.ComPopupDTO",rs);

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

	public ComPopupDTO[]  searchCodeNList(ComPopupDTO dtos) throws Exception{
        Connection con = null;
        Statement stmt=null;
        ResultSet rs = null;
        ComPopupDTO[] returns=null;
        try{
             
      	System.out.println("ComPopupDAO.searchCodeList :: \n");
            String sql = ComPopupQueryHelper.searchCodeNList(dtos);
            
            System.out.println("ComPopupDAO.searchCodeList :: \n"+sql);
            
            con = DataBaseUtil.getConnection();
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs = DataBaseUtil.executeSQL(con,stmt,sql); 
            
            returns=(ComPopupDTO[])DataBaseUtil.moveToEntities("com.wms.comPopup.beans.dto.ComPopupDTO",rs);
            
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
	
}
