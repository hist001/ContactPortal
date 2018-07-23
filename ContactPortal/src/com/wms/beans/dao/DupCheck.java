/*************************************************************
*	파 일 명  : DupCheck.java
*	작성일자  : 2004/06/22
*	작 성 자  : 
*	내    용  : 중복체크 유틸 클래스
*************************************************************/
package com.wms.beans.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.ArrayList;
import java.sql.SQLException;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.wms.fw.db.DataBaseUtil;
import com.wms.beans.dto.*;

public class DupCheck{
	public static boolean dupCheck(String key, String tableName, String value){
		Connection con=null;
		Statement stmt = null;
		ResultSet rs = null;
		boolean returns = false;
	
		try{
			StringBuffer query = new StringBuffer();
			query.append("select ");query.append(key);
			query.append(" from ");query.append(tableName);
			query.append(" where ");query.append(key);query.append("='");query.append(value);query.append("' ");
			
		    System.out.println(query.toString());
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(query.toString());
			if(rs.next()){
				//중복발생
				returns = true;
			}else{
				//중복미발생
				returns = false;
			}		
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				if(rs!=null)rs.close();
				if(stmt!=null)stmt.close();
				if(con!=null)con.close();
		    }catch(Exception e){e.printStackTrace();}
        }
	    return returns;
	}
	//CoCodeCon 중복체크
	public static boolean dupCheck2(String key1, String key2, String tableName, String value1, String value2){
		Connection con=null;
		Statement stmt = null;
		ResultSet rs = null;
		boolean returns = false;
	
		try{
			StringBuffer query = new StringBuffer();
			query.append("select ");query.append(key1);query.append(",");query.append(key2);
			query.append(" from ");query.append(tableName);
			query.append(" where ");query.append(key1);query.append("='");query.append(value1);query.append("' ");
			query.append(" and ");query.append(key2);query.append("='");query.append(value2);query.append("' ");
			
		    System.out.println(query.toString());
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(query.toString());
			if(rs.next()){
				//중복발생
				returns = true;
			}else{
				//중복미발생
				returns = false;
			}		
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				if(rs!=null)rs.close();
				if(stmt!=null)stmt.close();
				if(con!=null)con.close();
		    }catch(Exception e){e.printStackTrace();}
        }
	    return returns;
	}

	//제품 중복 검색
	public static ProdDTO[] dupCheck3(String key1, String value1, String tableName){
		//key1 은 범용성을 위해서 만듦
		Connection con=null;
		Statement stmt = null;
		ResultSet rs = null;
		ProdDTO[] returns = null;
	
		try{
			StringBuffer query = new StringBuffer();
			//query.append("select prodType||prodNo, ");query.append(key1);query.append(" ");
			query.append("select * ");
			query.append(" from ");query.append(tableName);
			query.append(" where ");query.append(key1);query.append(" like '%");query.append(value1);query.append("%' ");
			
		    System.out.println(query.toString());
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = DataBaseUtil.executeSQL(con,stmt, query.toString());
			returns=(ProdDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.ProdDTO",rs);		
			
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				if(rs!=null)rs.close();
				if(stmt!=null)stmt.close();
				if(con!=null)con.close();
		    }catch(Exception e){e.printStackTrace();}
        }
	    return returns;
	}	
	//조직이력 중복 검색
	public static boolean dupCheck4(String value1,String value2,String value3 ){
		//key1 은 범용성을 위해서 만듦
		Connection con=null;
		Statement stmt = null;
		ResultSet rs = null;
		boolean returns = false;
	
		try{
			StringBuffer query = new StringBuffer();
			query.append(" select ORG_NO  \n");
			query.append(" from  ORGHISTORY \n");
			query.append(" where  ORG_NO = '" + value1 +"' \n");
			query.append(" AND    ORGCD = '" + value2 +"' \n");
			query.append(" AND    HIGHORGCD = '" + value3 +"' \n");
			
		    System.out.println(query.toString());
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(query.toString());
			
			if(rs.next()){
				//중복발생
				returns = true;
			}else{
				//중복미발생
				returns = false;
			}	
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				if(rs!=null)rs.close();
				if(stmt!=null)stmt.close();
				if(con!=null)con.close();
		    }catch(Exception e){e.printStackTrace();}
        }
	    return returns;
	}
	
}