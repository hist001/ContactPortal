/*************************************************************
*	파 일 명  : AccoDAO.java
*	작성일자  : 2004/09/03
*	작 성 자  : 
*	내    용  : 계정과목 관련 처리(CRUD)
*************************************************************/
package com.wms.beans.dao;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.wms.fw.db.DataBaseUtil;
import com.wms.beans.dto.*;
import com.wms.fw.*;
import com.wms.fw.util.DateUtil;

public class AccoDAO implements IAcco
{
	public AccoDTO[] searchAccoList(String accoItem, String accoKorName, String prodNo, String prodType, String prcsNo)throws Exception
	{
		Connection con=null;
		Statement stmt = null;
		ResultSet rs = null;
		AccoDTO[] returns=null;
		try{
			StringBuffer query = new StringBuffer("select B.* from ");
			query.append("(select accoitem from acco ");
			query.append(" where accoitem like '");
			query.append(accoItem);
			query.append("%' and korname like '%");
			query.append(accoKorName);
			query.append("%' and prcsuseflag='Y'");
			query.append("minus ");
			query.append("select a.accoitem from prcscost a, acco b ");
			query.append("where a.prodno='");
			query.append(prodNo);
			query.append("' and a.prodtype = '");
			query.append(prodType);
			query.append("' and a.prcsno ='");
			query.append(prcsNo);
			query.append("' and a.accoitem like'");
			query.append(accoItem);
			query.append("%' and b.korname like '%");
			query.append(accoKorName);
			query.append("%' and a.accoitem=b.accoitem ) A, acco B ");
			query.append("where A.accoitem=B.accoitem ");
			query.append("order by B.accoitem ");

			System.out.println("searchAccoList :: \n"+query.toString());
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = DataBaseUtil.executeSQL(con,stmt, query.toString());
			returns=(AccoDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.AccoDTO",rs);
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