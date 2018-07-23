/*************************************************************
*	파 일 명  : PrcsCostDAO.java
*	작성일자  : 2004/09/03
*	작 성 자  : 
*	내    용  : 공정원가 관련 처리(CRUD)
*************************************************************/
package com.wms.beans.dao;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.wms.fw.db.DataBaseUtil;
import com.wms.beans.dto.*;
import com.wms.fw.*;

public class PrcsCostDAO implements IPrcsCost
{
	public boolean add(String prodNo, String prodType, String prcsNo, String[] accoItems,String[] chkFlags)throws Exception
	{
		Connection con = null;
		Statement stmt=null;
		int[] returns=null;
		try{
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			con.setAutoCommit(false);		

			for(int i=0; i<chkFlags.length;i++){
				int index = Integer.parseInt(chkFlags[i]);
				System.out.println("index = "+index);
				StringBuffer query = new StringBuffer("insert into prcscost values ('");
				query.append(prodNo);query.append("','");
				query.append(prodType);query.append("','");
				query.append(prcsNo);query.append("','");
				query.append(accoItems[index]);query.append("',0,0,0)");
				System.out.println("PrcsCostDAO.add::\n"+query.toString());
				stmt.addBatch(query.toString());
			}	

			StringBuffer query1 = new StringBuffer("update prcs set chgFlag='Y' ");
			query1.append("where prodno='");
			query1.append(prodNo);
			query1.append("' and  prodType='");
			query1.append(prodType);
			query1.append("' and prcsno='");
			query1.append(prcsNo);
			query1.append("'");
			stmt.addBatch(query1.toString());
			System.out.println("....공정테이블 chgFlag \n"+query1.toString());

			returns = stmt.executeBatch();
			con.commit();
			con.setAutoCommit(true);

		}catch(Exception e){
		}
		finally{
			try{
				if(stmt!=null)stmt.close();
				if(con!=null){
					con.setAutoCommit(true);
					con.close();
				}
			}catch(Exception e){
				Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
			}
		}
	
		return (returns==null)?false:true;	
	}
	
	public int update(String prodNo, String prodType, String prcsNo,String[] accoItems,String[] contAmts,String[] goalAmts,String[] chkFlags)throws Exception
	{
		Connection con = null;
		Statement stmt=null;
		int[] returns=null;
		try{
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			con.setAutoCommit(false);		

			for(int i=0; i<chkFlags.length;i++){
				int index = Integer.parseInt(chkFlags[i]);
				StringBuffer query = new StringBuffer("update prcscost set ");
				query.append("contamt = ");
				query.append(contAmts[index]);
				query.append(", goalamt = ");
				query.append(goalAmts[index]);
				query.append(" where prodNo = '");
				query.append(prodNo);
				query.append("' and prodType = '");
				query.append(prodType);
				query.append("' and prcsNo = '");
				query.append(prcsNo);
				query.append("' and accoItem = '");
				query.append(accoItems[index]);
				query.append("'");

				System.out.println("PrcsCostDAO.update::\n"+query.toString());
				stmt.addBatch(query.toString());
			}	
			StringBuffer query1 = new StringBuffer("update prcs set chgFlag='Y' ");
			query1.append("where prodno='");
			query1.append(prodNo);
			query1.append("' and  prodType='");
			query1.append(prodType);
			query1.append("' and prcsno='");
			query1.append(prcsNo);
			query1.append("'");
			stmt.addBatch(query1.toString());
			System.out.println("....공정테이블 chgFlag \n"+query1.toString());

			returns = stmt.executeBatch();
			con.commit();
			con.setAutoCommit(true);
		}catch(Exception e){
		}
		finally{
			try{
				if(stmt!=null)stmt.close();
				if(con!=null){
					con.setAutoCommit(true);
					con.close();
				}
			}catch(Exception e){
				Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
			}
		}
		return (returns==null)?0:returns.length;
	}

	public int delete(String prodNo, String prodType, String prcsNo, String[] accoItems,String[] chkFlags)throws Exception
	{
		Connection con = null;
		Statement stmt=null;
		int[] returns=null;
		try{
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			con.setAutoCommit(false);		

			for(int i=0; i<chkFlags.length;i++){
				int index = Integer.parseInt(chkFlags[i]);
				StringBuffer query = new StringBuffer("delete prcscost ");
				query.append(" where prodNo = '");
				query.append(prodNo);
				query.append("' and prodType = '");
				query.append(prodType);
				query.append("' and prcsNo = '");
				query.append(prcsNo);
				query.append("' and accoItem = '");
				query.append(accoItems[index]);
				query.append("'");

				System.out.println("PrcsCostDAO.update::\n"+query.toString());
				stmt.addBatch(query.toString());
			}	
			StringBuffer query1 = new StringBuffer("update prcs set chgFlag='Y' ");
			query1.append("where prodno='");
			query1.append(prodNo);
			query1.append("' and  prodType='");
			query1.append(prodType);
			query1.append("' and prcsno='");
			query1.append(prcsNo);
			query1.append("'");
			stmt.addBatch(query1.toString());
			System.out.println("....공정테이블 chgFlag \n"+query1.toString());

			returns = stmt.executeBatch();
			con.commit();
			con.setAutoCommit(true);
		}catch(Exception e){
		}
		finally{
			try{
				if(stmt!=null)stmt.close();
				if(con!=null){
					con.setAutoCommit(true);
					con.close();
				}
			}catch(Exception e){
				Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
			}
		}
	
		return (returns==null)?0:returns.length;
	}

	public PrcsCostDTO[] prcsCostSearch(String prodNo, String prodType, String prcsNo)throws Exception
	{
		Connection con = null;
		Statement stmt=null;
		ResultSet rs = null;
		PrcsCostDTO[] returns=null;
		try{
			StringBuffer query = new StringBuffer("select A.*, B.korname accoKorName from prcscost A, acco B ");
			query.append(" where A.prodno='");
			query.append(prodNo);
			query.append("' and A.prodtype='");
			query.append(prodType);
			query.append("' and A.prcsno='");
			query.append(prcsNo);
			query.append("' and A.accoitem=B.accoitem ");
			query.append("order by A.accoitem ");

			System.out.println("PrcsCostDAO.prcsCostSearch::\n"+query.toString());
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = DataBaseUtil.executeSQL(con,stmt,query.toString());
			returns=(PrcsCostDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.PrcsCostDTO",rs);
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
	
	public PrcsCostDTO  prcsCostSubSearch(String prodNo, String prodType, String prcsNo,  String accoItem)throws Exception
	{
		Connection con = null;
		Statement stmt=null;
		ResultSet rs = null;
		PrcsCostDTO returns=null;
		try{
			StringBuffer query = new StringBuffer("select A.*, B.korname accoKorName from prcscost A, acco B ");
			query.append(" where A.prodno='");
			query.append(prodNo);
			query.append("' and A.prodtype='");
			query.append(prodType);
			query.append("' and A.prcsno='");
			query.append(prcsNo);
			query.append("' and A.accoitem='");
			query.append(accoItem);
			query.append("' and A.accoitem=B.accoitem ");
			query.append("order by A.accoitem ");

			System.out.println("PrcsCostDAO.prcsCostSubSearch::\n"+query.toString());
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = DataBaseUtil.executeSQL(con,stmt,query.toString());
			returns=(PrcsCostDTO)DataBaseUtil.moveToEntity("com.wms.beans.dto.PrcsCostDTO",rs);
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