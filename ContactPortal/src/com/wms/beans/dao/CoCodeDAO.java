/*************************************************************
*	파 일 명  : CoCodeDAO.java
*	작성일자  : 2004/09/03
*	작 성 자  : 
*	내    용  : 공정형태 검색
*************************************************************/
package com.wms.beans.dao;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.wms.fw.db.DataBaseUtil;
import com.wms.beans.dto.*;
import com.wms.fw.*;

public class CoCodeDAO implements ICoCode
{
	public CoCodeDTO[] coCodeSearch(String cdType, String cd)throws Exception
	{
		Connection con = null;
		Statement stmt=null;
		ResultSet rs = null;
		CoCodeDTO[] returns=null;
		try{
			StringBuffer query = new StringBuffer("select * from cocode ");
			query.append(" where cdtype='");
			query.append(cdType);
			query.append("' order by cd ");
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = DataBaseUtil.executeSQL(con,stmt, query.toString());
			System.out.println(query.toString());
			returns=(CoCodeDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.CoCodeDTO",rs);
		}catch(Exception e){
		}
		finally{
			try{
				if(stmt!=null)stmt.close();
				if(con!=null)con.close();
			}catch(Exception e){
				Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
			}
		}
	
		com.wms.fw.Utility.fixNullAndTrimAll(returns);
		return returns;	
	}
	
	public CoCodeDTO[] coCodeInquiryList(String cdType,String cdTypeName,String cd,String cdName)throws Exception
	{
		Connection con = null;
		Statement stmt=null;
		ResultSet rs = null;
		CoCodeDTO[] returns=null;
		try{
			StringBuffer query = new StringBuffer();
			query.append("select a.cdtype, b.cdtypename, a.cd, a.cdname, a.cdds ");
			query.append("from cocode a, cocodetype b ");
			query.append("where a.cdtype like '");
			query.append(cdType);
			query.append("%' and b.cdtypename like '%");
			query.append(cdTypeName);
			query.append("%' and a.cd like '");
			query.append(cd);
			query.append("%' and a.cdname like '%");
			query.append(cdName);
			query.append("%' and a.cdtype = b.cdtype ");
			query.append("order by cdtype, cd ");
			
			System.out.println(query.toString());
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = DataBaseUtil.executeSQL(con,stmt, query.toString());
			returns=(CoCodeDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.CoCodeDTO",rs);
			
		}catch(Exception e){
		}
		finally{
			try{
				if(stmt!=null)stmt.close();
				if(con!=null)con.close();
			}catch(Exception e){
				Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
			}
		}
	
		com.wms.fw.Utility.fixNullAndTrimAll(returns);
		return returns;	
	}
}