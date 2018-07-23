/*************************************************************
*	파 일 명  : BasicDataDAO.java
*	작성일자  : 2004/06/22
*	작 성 자  : 
*	내    용  : 기본자료관리 클래스
*************************************************************/
package com.wms.beans.dao;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.wms.fw.db.DataBaseUtil;
import com.wms.beans.dto.*;
import com.wms.beans.*;
import com.wms.fw.*;
import com.wms.fw.util.DateUtil;

public class BasicDataDAO implements IBasicData {

	public int createBasicData(BasicDataDTO dto) throws Exception{
		Connection con=null;
		Statement stmt = null;
		ResultSet rs = null;
		int returns = 0;
		try{
			String query = new String();
			MakeQueryHelper mqh = new MakeQueryHelper();
			query = mqh.makeInsertQuery(dto);
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			System.out.println(query);
			returns = stmt.executeUpdate(query);		
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

	public int updateBasicData(BasicDataDTO dto,String[] pks)throws Exception{
		Connection con=null;
		Statement stmt = null;
		ResultSet rs = null;
		int returns=0;
		try{
			String query = new String();
			query = MakeQueryHelper.makeUpdateQuery(dto,pks);			
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			System.out.println(query);		
			returns = stmt.executeUpdate(query);						
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

	public int deleteBasicData(BasicDataDTO dto, String[] pks)throws Exception{
		Connection con=null;
		Statement stmt = null;
		ResultSet rs = null;
		int returns=0;
		try{
			String query = new String();
			query = MakeQueryHelper.makeDeleteQuery(dto,pks);			
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			System.out.println(query);		
			returns = stmt.executeUpdate(query);						
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
	
	public BasicDataDTO selectBasicData(BasicDataDTO dto)throws Exception{
		Connection con=null;
		Statement stmt = null;
		ResultSet rs = null;
		BasicDataDTO returns = new BasicDataDTO();		
		try{
			String query = new String();
			query = MakeQueryHelper.makeSelectQuery(dto);
			System.out.println(query);
			
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = DataBaseUtil.executeSQL(con,stmt,query);
            BasicDataDTO[] result=basicMoveToEntities(rs);
			returns=(result!=null)?result[0]:null;			
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
		com.wms.fw.Utility.fixNullAndTrimAll(returns);
    	return returns;
	}


	public BasicDataDTO[] selectBasicDataList(BasicDataDTO dto)throws Exception{
		Connection con=null;
		Statement stmt = null;
		ResultSet rs = null;
		BasicDataDTO[] returns = null;		
		try{
			String query = new String();
			query = MakeQueryHelper.makeSelectQuery(dto);
			System.out.println(query);
			
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = DataBaseUtil.executeSQL(con,stmt,query);
			returns=basicMoveToEntities(rs);
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
		//com.wms.fw.Utility.fixNullAndTrimAll(returns);
    	return returns;
	} 	
  public BasicDataDTO[] basicMoveToEntities(ResultSet rs) throws Exception{
	    Connection con=null;
		Statement stmt = null;
		//ResultSet rs = null;
		BasicDataDTO[] returns=null;
		try {
			int idx=0;
			if(rs.last()){
				int length=rs.getRow();
				returns=new BasicDataDTO[length];
				rs.first();
				ResultSetMetaData rsma= rs.getMetaData();
				int columnCnt= rsma.getColumnCount();
				do{
					BasicDataDTO entity=new BasicDataDTO();
					for (int i=1 ; i<=columnCnt; i++) {
						String name=rsma.getColumnName(i);
						entity.set(name,(rs.getString(name)==null)?"":rs.getString(name));
					}//for
                    returns[idx]=entity;
					idx++;
				}while(rs.next());
			}//if
		}
		catch(Exception e){
		}
		return returns;
  } // end basicMoveToEntities

}