/*************************************************************
*	파 일 명  : InstallDAO.java
*	작성일자  : 2004/09/03
*	작 성 자  : 
*	내    용  : 설치내역 관련 처리(CRUD)
*************************************************************/
package com.wms.beans.dao;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.wms.fw.db.DataBaseUtil;
import com.wms.beans.dto.*;
import com.wms.fw.*;
import com.wms.fw.util.DateUtil;

public class InstallDAO implements IInstall
{
	public boolean createInstall(InstallDTO dto)throws Exception
	{
		Connection con = null;
		Statement stmt=null;
		ResultSet rs=null;
		int[] returns=null;
		try{
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			con.setAutoCommit(false);		

			StringBuffer query = new StringBuffer();
			StringBuffer subQuery = new StringBuffer();
			//주어진 제품코드내에서 sno의 max 값을 찾는 subquery
			subQuery.append("select nvl(max(sno)+1,1) sno from INSTALL ");
			subQuery.append("where prodno='");subQuery.append(dto.prodNo);subQuery.append("' ");
			subQuery.append("and prodType='");subQuery.append(dto.prodType);subQuery.append("' ");
			subQuery.append("and prcsNo='");subQuery.append(dto.prcsNo);subQuery.append("' ");

			query.append("insert into INSTALL values('");
			query.append(dto.prodNo);query.append("', '");
			query.append(dto.prodType);query.append("', '");
			query.append(dto.prcsNo);query.append("', (");
			query.append(subQuery.toString());query.append(") , '");
			query.append(dto.instType);query.append("', '");
			query.append(dto.itemNo);query.append("', '");
			query.append(dto.itemName);query.append("', '");
			query.append(dto.instCnt);query.append("', '");
			query.append(dto.instSno);query.append("', '");
			query.append(dto.contUPrice);query.append("', '");
			query.append(dto.uPrice);query.append("', '");
			query.append(Utility.replace((dto.instPlanDt),"-"));query.append("', '");
			query.append(Utility.replace((dto.instDt),"-"));query.append("', '");
			query.append(dto.itemDesc);query.append("') ");
			stmt.addBatch(query.toString());
			System.out.println(query.toString());

			StringBuffer query1 = new StringBuffer("update prcs set chgFlag='Y' ");
			query1.append("where prodno='");
			query1.append(dto.prodNo);
			query1.append("' and  prodType='");
			query1.append(dto.prodType);
			query1.append("' and prcsno='");
			query1.append(dto.prcsNo);
			query1.append("'");
			stmt.addBatch(query1.toString());
			System.out.println("....공정테이블 chgFlag \n"+query1.toString());

			returns = stmt.executeBatch();
			con.commit();
			con.setAutoCommit(true);

		}catch(Exception e){
			throw new Exception("오류가 발생했습니다. 발생위치:InstallDAO.");
		}
		finally{
			try{
				if(rs!=null)rs.close();
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

	public InstallDTO[] searchInstallList(String prodType, String prodNo, String prcsNo)throws Exception
	{
		Connection con = null;
		Statement stmt=null;
		ResultSet rs = null;
		InstallDTO[] returns=null;
		try{
			StringBuffer query = new StringBuffer();
			query.append("select x.* ,y.prodname , z.prcsname from INSTALL x, PROD y, PRCS z ");
			query.append("where x.prodno=y.prodno ");
			query.append("and y.prodno=z.prodno ");
			query.append("and z.prodno=x.prodno ");
			query.append("and x.prodtype=y.prodtype ");
			query.append("and y.prodtype=z.prodtype ");
			query.append("and z.prodtype=x.prodtype ");
			query.append("and z.prcsno=x.prcsno ");
			query.append("and x.prodno='");query.append(prodNo);query.append("' ");
			query.append("and x.prodType='");query.append(prodType);query.append("' ");
			query.append("and x.prcsNo='");query.append(prcsNo);query.append("' ");
			System.out.println(query.toString());

			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(query.toString());
			returns=(InstallDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.InstallDTO",rs);
		}catch(Exception e){
			throw new Exception("오류가 발생했습니다. 발생위치:InstallDAO.");
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

	public InstallDTO searchInstall(InstallDTO dto)throws Exception
	{
		Connection con = null;
		Statement stmt=null;
		ResultSet rs = null;
		InstallDTO returns = new InstallDTO();
		StringBuffer query = new StringBuffer();
		try{
			query.append("select x.* ,y.prodname , z.prcsname from INSTALL x, PROD y, PRCS z ");
			query.append("where x.prodno=y.prodno ");
			query.append("and y.prodno=z.prodno ");
			query.append("and z.prodno=x.prodno ");
			query.append("and x.prodtype=y.prodtype ");
			query.append("and y.prodtype=z.prodtype ");
			query.append("and z.prodtype=x.prodtype ");
			query.append("and z.prcsno=x.prcsno ");
			query.append("and x.prodno='");query.append(dto.prodNo);query.append("' ");
			query.append("and x.prodType='");query.append(dto.prodType);query.append("' ");
			query.append("and x.prcsNo='");query.append(dto.prcsNo);query.append("' ");
			query.append("and x.sno like '");query.append(dto.sno);query.append("' ");

			/*
			query.append("select * from INSTALL where prodNo ='");query.append(dto.prodNo);query.append("' ");
			query.append("and prodtype ='");query.append(dto.prodType);query.append("' ");
			query.append("and prcsNo ='");query.append(dto.prcsNo);query.append("' ");
			query.append("and sno like '");query.append(dto.sno);query.append("' ");
			*/
			System.out.println(query.toString());
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(query.toString());
			returns = (InstallDTO)DataBaseUtil.moveToEntity("com.wms.beans.dto.InstallDTO",rs);			
		}catch(Exception e){
			throw new Exception("오류가 발생했습니다. 발생위치:InstallDAO.");
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

	public boolean updateInstall(InstallDTO dto) throws Exception{
		Connection con = null;
		Statement stmt=null;
		ResultSet rs = null;
		int[] returns = null;
		try{
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			con.setAutoCommit(false);		

			StringBuffer query = new StringBuffer();
			query.append("update INSTALL set ");
			query.append("insttype='");query.append(dto.instType);query.append("', ");
			query.append("itemno='");query.append(dto.itemNo);query.append("', ");
			query.append("itemname='");query.append(dto.itemName);query.append("', ");
			query.append("instcnt='");query.append(dto.instCnt);query.append("', ");
			query.append("instsno='");query.append(dto.instSno);query.append("', ");
			query.append("contuprice='");query.append(dto.contUPrice);query.append("', ");
			query.append("uprice='");query.append(dto.uPrice);query.append("', ");
			query.append("instplandt='");query.append(Utility.replace((dto.instPlanDt),"-"));query.append("', ");
			query.append("instdt='");query.append(Utility.replace((dto.instDt),"-"));query.append("', ");
			query.append("itemdesc='");query.append(dto.itemDesc);query.append("' ");
			query.append("where prodno='");query.append(dto.prodNo);query.append("' ");
			query.append("and prodtype='");query.append(dto.prodType);query.append("' ");
			query.append("and prcsno='");query.append(dto.prcsNo);query.append("' ");
			query.append("and sno='");query.append(dto.sno);query.append("' ");
			stmt.addBatch(query.toString());
			System.out.println(query.toString());

			StringBuffer query1 = new StringBuffer("update prcs set chgFlag='Y' ");
			query1.append("where prodno='");
			query1.append(dto.prodNo);
			query1.append("' and  prodType='");
			query1.append(dto.prodType);
			query1.append("' and prcsno='");
			query1.append(dto.prcsNo);
			query1.append("'");
			stmt.addBatch(query1.toString());
			System.out.println("....공정테이블 chgFlag \n"+query1.toString());

			returns = stmt.executeBatch();
			con.commit();
			con.setAutoCommit(true);

		}catch(Exception e){
			throw new Exception("오류가 발생했습니다. 발생위치:InstallDAO");
		}
		finally{
			try{
				if(rs!=null)rs.close();
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

	public boolean deleteInstall(InstallDTO dto) throws Exception{
		Connection con = null;
		Statement stmt=null;
		ResultSet rs = null;
		int[] returns = null;
		try{
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			con.setAutoCommit(false);		

			StringBuffer query = new StringBuffer();
			query.append("delete INSTALL ");
			query.append("where prodno='");query.append(dto.prodNo);query.append("' ");
			query.append("and prodtype='");query.append(dto.prodType);query.append("' ");
			query.append("and prcsno='");query.append(dto.prcsNo);query.append("' ");
			query.append("and sno='");query.append(dto.sno);query.append("' ");
			stmt.addBatch(query.toString());
			System.out.println(query.toString());

			StringBuffer query1 = new StringBuffer("update prcs set chgFlag='Y' ");
			query1.append("where prodno='");
			query1.append(dto.prodNo);
			query1.append("' and  prodType='");
			query1.append(dto.prodType);
			query1.append("' and prcsno='");
			query1.append(dto.prcsNo);
			query1.append("'");
			stmt.addBatch(query1.toString());
			System.out.println("....공정테이블 chgFlag \n"+query1.toString());

			returns = stmt.executeBatch();
			con.commit();
			con.setAutoCommit(true);
		}catch(Exception e){
			throw new Exception("오류가 발생했습니다. 발생위치:InstallDAO");
		}
		finally{
			try{
				if(rs!=null)rs.close();
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
}