/*************************************************************
*	파 일 명  : BizDAO.java
*	작성일자  : 2004/09/03
*	작 성 자  : 
*	내    용  : 사업 관련 처리(CRUD)
*************************************************************/
package com.wms.beans.dao;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.wms.fw.db.DataBaseUtil;
import com.wms.beans.dto.*;
import com.wms.fw.*;

public class BizDAO implements IBiz
{
	public boolean save(BizDTO dto)throws Exception
	{
		Connection con=null;
		Statement stmt=null;
		boolean returns=false;
		try{
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
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
	
		return returns;	
	}
	
	public BizDTO[] bizInquiryList(String bizNo, String bizName)throws Exception
	{
		Connection con = null;
		Statement stmt=null;
		ResultSet rs=null;
		BizDTO[] returns=null;
		try{
			StringBuffer query = new StringBuffer();
			query.append("select a.* ");
			query.append(" from biz a ");
			query.append("where a.bizno like '");
			query.append(bizNo);
			query.append("%' and a.bizName like '%");
			query.append(bizName);
			query.append("%' order by a.bizNo ");
			
			System.out.println("BizDAO.bizInquiryList :: \n"+query.toString());
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = DataBaseUtil.executeSQL(con,stmt, query.toString());
			returns = (BizDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.BizDTO",rs);
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