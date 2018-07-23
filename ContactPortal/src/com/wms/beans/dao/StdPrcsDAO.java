/*************************************************************
*	파 일 명  : StdPrcsDAO.java
*	작성일자  : 2004/09/03
*	작 성 자  : 
*	내    용  : 표준공정코드 검색
*************************************************************/
package com.wms.beans.dao;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.wms.fw.db.DataBaseUtil;
import com.wms.beans.dto.*;
import com.wms.fw.*;
import com.wms.fw.util.DateUtil;

public class StdPrcsDAO implements IStdPrcs
{
	public StdPrcsDTO[] searchStdPrcs(String prcsCd, String prcsNo)throws Exception
	{
		Connection con=null;
		Statement stmt = null;
		ResultSet rs = null;
		StdPrcsDTO[] returns=null;
		try{
			StringBuffer query = new StringBuffer("select * from stdprcs ");
			query.append("where prcscd like '");
			query.append(prcsCd);
			query.append("%' order by prcscd, prcsno ");

			System.out.println(query.toString());
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = DataBaseUtil.executeSQL(con,stmt, query.toString());
			returns=(StdPrcsDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.StdPrcsDTO",rs);
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
	
	public StdPrcsDTO[] stdPrcsInquiryList(String prcsType, String prcsName)throws Exception
	{
		Connection con=null;
		Statement stmt = null;
		ResultSet rs = null;
		StdPrcsDTO[] returns=null;
		try{
			StringBuffer query = new StringBuffer();
			query.append("select b.cdname, a.*  ");
			query.append("from stdprcs a, cocode b ");
			query.append("where b.cdtype='PR' ");
			query.append("and substr(a.prcscd,1,1) like'");
			query.append(prcsType);
			query.append("%' and a.prcsname like '%");
			query.append(prcsName);
			query.append("%' and substr(a.prcscd,1,1) = b.cd ");
			query.append("order by prcscd, prcsno, cdname  ");
			System.out.println(query.toString());

			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = DataBaseUtil.executeSQL(con,stmt, query.toString());
			returns=(StdPrcsDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.StdPrcsDTO",rs);

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

}