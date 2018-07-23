/*************************************************************
*	파 일 명  : PrcsManCntDAO.java
*	작성일자  : 2004/09/03
*	작 성 자  : 
*	내    용  : 공정인원 관련 처리(CRUD)
*************************************************************/
package com.wms.beans.dao;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.wms.fw.db.DataBaseUtil;
import com.wms.beans.dto.*;
import com.wms.fw.*;

public class PrcsManCntDAO implements IPrcsManCnt
{
	/**
	* 	공정인원 저장
	*/
	public boolean add(String prodNo, String prodType, String prcsNo, String[] prcsEmpIds, String[] chkFlags)throws Exception
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
				StringBuffer query = new StringBuffer();
				query.append("insert into prcsmancnt (prodno, prodtype, prcsno, empid) values ('");
				query.append(prodNo);query.append("','");
				query.append(prodType);query.append("','");
				query.append(prcsNo);query.append("','");
				query.append(prcsEmpIds[index]);query.append("')");

				System.out.println("prcsEmpIds["+index+"]="+prcsEmpIds[index]);
				System.out.println("PrcsManCntDAO.add::\n"+query.toString());
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
			System.out.println("....공정테이블 chgFlag \n"+query1.toString());
			stmt.addBatch(query1.toString());

			returns = stmt.executeBatch();
			con.commit();
			con.setAutoCommit(true);
		}catch(Exception e){
			throw new Exception("이미 등록된 자료입니다."+e.getMessage());
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
	
	/**
	*	공정인원 갱신
	*/
	public int update(PrcsManCntDTO dto)throws Exception
	{
		Connection con = null;
		Statement stmt=null;
		int[] returns=null;
		try{
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			con.setAutoCommit(false);		

			StringBuffer query = new StringBuffer("update prcsmancnt set ");
			query.append("jobcd='");
			query.append(dto.jobCd);
			query.append("', intostartdt='");
			query.append(Utility.replace(dto.intoStartDt,"-"));
			query.append("', intoenddt='");
			query.append(Utility.replace(dto.intoEndDt,"-"));
			query.append("', intorate=");
			query.append(dto.intoRate);
			query.append(" where prodno='");
			query.append(dto.prodNo);
			query.append("' and prodtype='");
			query.append(dto.prodType);
			query.append("' and prcsno='");
			query.append(dto.prcsNo);
			query.append("' and empid='");
			query.append(dto.empId);
			query.append("'");
			stmt.addBatch(query.toString());
			System.out.println("PrcsManCntDAO.update"+query.toString());

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

	/**
	*	공정인원 삭제
	*/
	public int delete(String prodNo, String prodType, String prcsNo, String[] prcsEmpIds, String[] chkFlags)throws Exception
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
				StringBuffer query = new StringBuffer("delete prcsmancnt  ");
				query.append(" where prodno='");
				query.append(prodNo);
				query.append("' and prodtype='");
				query.append(prodType);
				query.append("' and prcsno='");
				query.append(prcsNo);
				query.append("' and empid='");
				query.append(prcsEmpIds[index]);
				query.append("'");
				System.out.println("PrcsManCntDAO.delete::\n"+query.toString());
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
			System.out.println("....공정테이블 chgFlag \n"+query1.toString());
			stmt.addBatch(query1.toString());

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

	/**
	*	공정인원 검색
	*/
	public PrcsManCntDTO[] prcsManCntSearchList(String prodNo, String prcsNo, String prodType)throws Exception
	{
		Connection con = null;
		Statement stmt=null;
		ResultSet rs = null;
		PrcsManCntDTO[] returns=null;
		try{
			StringBuffer query = new StringBuffer("select A.*, to_empkname(A.empid) empKName  from prcsmancnt A ");
			query.append("where A.prodno='");			
			query.append(prodNo);			
			query.append("' and A.prodtype='");			
			query.append(prodType);			
			query.append("' and A.prcsno='");
			query.append(prcsNo);			
			query.append("'");
			System.out.println("prcsManCntSearchList :: \n"+query.toString());
			 
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = DataBaseUtil.executeSQL(con,stmt,query.toString());
			returns=(PrcsManCntDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.PrcsManCntDTO",rs);
			
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
	
	/**
	*	공정인원 검색
	*/
	public PrcsManCntDTO prcsManCntSubSearch(String prodNo, String prcsNo, String prodType, String empId)throws Exception
	{
		Connection con = null;
		Statement stmt=null;
		ResultSet rs = null;
		PrcsManCntDTO returns=null;
		try{
			StringBuffer query = new StringBuffer("select A.*, to_empkname(A.empid) empKName  from prcsmancnt A ");
			query.append("where A.prodno='");			
			query.append(prodNo);			
			query.append("' and A.prodtype='");			
			query.append(prodType);			
			query.append("' and A.prcsno='");
			query.append(prcsNo);			
			query.append("' and A.empid='");
			query.append(empId);			
			query.append("'");
			
			System.out.println("prcsManCntSubSearch :: \n"+query.toString());
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = DataBaseUtil.executeSQL(con,stmt,query.toString());
			returns=(PrcsManCntDTO)DataBaseUtil.moveToEntity("com.wms.beans.dto.PrcsManCntDTO",rs);
		}catch(Exception e){
			e.printStackTrace();
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
	
	/**
	*	같은 제품의 다른 공정 직원 검색
	*/
	public EmpDTO[] otherPrcsEmpSearch(String prodNo, String prcsNo, String prodType, String empId, String empKName, String orgName)throws Exception
	{
		Connection con = null;
		Statement stmt=null;
		ResultSet rs = null;
		EmpDTO[] returns=null;
		try{			
			StringBuffer query = new StringBuffer();			
			query.append("select A.*, to_empkname(A.empId) empKName, to_orgName(A.empId) empOrgName ");
			query.append("from ");			
			query.append("( ");			
			query.append("select distinct b.* from prodemp a, emp b, org c ");			
			query.append("where a.prodno = '");	
			query.append(prodNo);			
			query.append("' and a.prodtype = '");			
			query.append(prodType);
			/*						
			query.append("' and a.prcsno != '");
			query.append(prcsNo);	
			*/
			query.append("' and a.empId like '");
			query.append(empId);			
			query.append("%' and b.empkname like '%");
			query.append(empKName);			
			query.append("%' and c.orgname like '%");
			query.append(orgName);			
			query.append("%' and a.empId=b.empId ");
			query.append("and b.orgcd = c.orgcd ");
			query.append("minus ");
			query.append("select b.* from prcsmancnt a, emp b , org c ");
			query.append("where a.prodno= '");	
			query.append(prodNo);			
			query.append("' and a.prodtype= '");			
			query.append(prodType);			
			query.append("' and a.prcsno = '");
			query.append(prcsNo);			
			query.append("' and a.empId like '");
			query.append(empId);			
			query.append("%' and b.empkname like '%");
			query.append(empKName);			
			query.append("%' and c.orgname like '%");
			query.append(orgName);			
			query.append("%' and a.empId=b.empId ");
			query.append("and b.orgcd = c.orgcd ");
			query.append(") A ");
			query.append("order by empOrgName, empid ");			
			
			System.out.println("prcsManCntSearchList.otherPrcsEmpSearch :: \n"+query.toString());
			
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = DataBaseUtil.executeSQL(con,stmt,query.toString());
			returns=(EmpDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.EmpDTO",rs);
			
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

	/**
	*	제품에 속하지 않은 다른 직원 검색
	*/
	public EmpDTO[] empSearch(String prodNo, String prcsNo, String prodType, String empId, String empKName, String orgName, String prodEmpCallYN)throws Exception
	{
		Connection con = null;
		Statement stmt=null;
		ResultSet rs = null;
		EmpDTO[] returns=null;
		try{
			StringBuffer query = null;
			if(prodEmpCallYN.equals("N")){
				query = new StringBuffer("select empId, to_empkname(empId) empKName, to_orgName(empId) empOrgName ");
				query.append("from ");			
				query.append("( ");			
				query.append("select a.empId from emp a, org b ");			
				query.append("where a.empid like '");	
				query.append(empId);			
				query.append("%' and a.empkname like '%");			
				query.append(empKName);			
				query.append("%' and b.orgname like '%");
				query.append(orgName);			
				query.append("%' and a.orgcd = b.orgcd ");
				query.append("and retflag='N' "); 			//퇴직자 검색되지 않도록 처리함 2005.08.11
				query.append(" minus ");
				query.append("select a.empId from prcsmancnt a, emp b, org c ");
				query.append("where a.prodno= '");	
				query.append(prodNo);			
				query.append("' and a.prodtype= '");			
				query.append(prodType);			
				query.append("' and a.prcsno = '");
				query.append(prcsNo);			
				query.append("' and a.empId like '");
				query.append(empId);			
				query.append("%' and b.empkname like '%");
				query.append(empKName);			
				query.append("%' and c.orgname like '%");
				query.append(orgName);			
				query.append("%' and a.empId=b.empId ");
				query.append("and b.orgcd = c.orgcd ");
				query.append(") order by empOrgName, empid ");
			}else if(prodEmpCallYN.equals("Y")){
				System.out.println("prodEmpCallYN 이 Call 됨");
				query = new StringBuffer();
				query.append("select b.*, to_orgname(b.empid) empOrgName ");
				query.append("from prodemp a, emp b ");
				query.append("where a.empid = b.empid ");
				query.append("and	a.prodtype = '");query.append(prodType);query.append("' ");
				query.append("and	a.prodNo = '");query.append(prodNo);query.append("' ");
			}
			
			System.out.println("prcsManCntSearchList.empSearch :: \n"+query.toString());
			
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = DataBaseUtil.executeSQL(con,stmt,query.toString());
			returns=(EmpDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.EmpDTO",rs);
			
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
	
	/**
	*	당 공정에 속하는 직원 검색(공정인원 검색)
	*/
	public EmpDTO[] prcsEmpSearch(String prodNo, String prcsNo, String prodType)throws Exception{
		Connection con = null;
		Statement stmt=null;
		ResultSet rs = null;
		EmpDTO[] returns=null;
		try{
			StringBuffer query = new StringBuffer();
			query.append("select b.*, to_empkname(b.empId) empKName, to_orgName(b.empId) empOrgName ");
			query.append("from prcsmancnt a, emp b ");
			query.append("where a.prodtype = '");			
			query.append(prodType);			
			query.append("' and a.prodno= '");			
			query.append(prodNo);			
			query.append("' and a.prcsno = '");
			query.append(prcsNo);			
			query.append("' and a.empid = b.empid ");
			
			System.out.println("prcsManCntSearchList.prcsEmpSearch :: \n"+query.toString());
			
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = DataBaseUtil.executeSQL(con,stmt,query.toString());
			returns=(EmpDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.EmpDTO",rs);
			
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