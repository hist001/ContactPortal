/*************************************************************
*	파 일 명  : PrcsDAO.java
*	작성일자  : 2004/09/03
*	작 성 자  : 
*	내    용  : 공정관련 처리(CRUD)
*************************************************************/
package com.wms.beans.dao;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.wms.fw.db.DataBaseUtil;
import com.wms.beans.dto.*;
import com.wms.fw.*;

public class PrcsDAO implements IPrcs
{
	public int add(PrcsDTO dto) throws Exception
	{
		Connection con = null;
		Statement stmt=null;
		int returns=0;
		try{
			StringBuffer query = new StringBuffer("insert into prcs values ('");
			query.append(dto.prodNo);query.append("', '");
			query.append(dto.prodType);query.append("', upper('");
			query.append(dto.prcsNo);query.append("'), '");
			query.append(dto.prcsName);query.append("', '");
			query.append(dto.prcsDesc);query.append("', '");
			query.append(dto.prcsEFlag);query.append("', '");
			query.append(dto.orgCd);query.append("', '");
			query.append(dto.prePrcs);query.append("', '");
			query.append(Utility.replace(dto.plStartDt,"-"));query.append("', '");
			query.append(Utility.replace(dto.plEndDt,"-"));query.append("', '");
			query.append(dto.plMh);query.append("', '");
			//query.append(dto.prcStatus);
			query.append("준비', '");
			query.append(dto.prcsOrgCd);query.append("', '99991231', '99991231', '0','99991231','Y',0) ");
			
			System.out.println("prcs add ::\n"+query.toString());
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			returns = stmt.executeUpdate(query.toString());
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
	
	/*
		공정을 복사하여 저장한다.
		공정인원, 공정원가, 설치내역 모두 복사하여야 한다.
	*/	
	public int addCopyPrcs(String prodNo, String prcsNo, String prodType, String nPrcsNo, String nPrcsName) throws Exception
	{
		Connection con = null;
		Statement stmt=null;
		int[] returns=null;
		try{
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			con.setAutoCommit(false);

			//공정테이블 복사
			StringBuffer query = new StringBuffer("insert into prcs ");
			query.append("select prodno, prodtype, upper('");
			query.append(nPrcsNo);
			query.append("'), '");
			query.append(nPrcsName);
			query.append("', prcsdesc, prcseflag, orgcd, preprcs, plstartdt, ");
			query.append("plenddt, plmh, prcstatus, prcsorgcd, '19000101', ' ', 0, lastwepldt , 'Y', 0 ");
			query.append("from prcs ");
			query.append("where prodno='");
			query.append(prodNo);
			query.append("' and prodtype='");
			query.append(prodType);
			query.append("' and prcsno=upper('");
			query.append(prcsNo);
			query.append("')");
			System.out.println("PrcsDAO.addCopyPrcs :: "+query.toString());
			stmt.addBatch(query.toString());

			//공정인원 테이블 복사
			StringBuffer query1 = new StringBuffer("insert into prcsmancnt ");
			query1.append("select prodno, prodtype, upper('");
			query1.append(nPrcsNo);
			query1.append("'), empid, jobcd, intostartdt, intoenddt, intorate ");
			query1.append("from prcsmancnt ");
			query1.append("where prodno='");
			query1.append(prodNo);
			query1.append("' and prodtype='");
			query1.append(prodType);
			query1.append("' and prcsno=upper('");
			query1.append(prcsNo);
			query1.append("')");
			System.out.println("PrcsDAO addCopyPrcsManCnt :: "+query1.toString());
			stmt.addBatch(query1.toString());

			//공정원가 테이블 복사
			StringBuffer query2 = new StringBuffer("insert into prcscost ");
			query2.append("select prodno, prodtype, upper('");
			query2.append(nPrcsNo);
			query2.append("'), accoitem, contamt, goalamt, rsltamt ");
			query2.append("from prcscost ");
			query2.append("where prodno='");
			query2.append(prodNo);
			query2.append("' and prodtype='");
			query2.append(prodType);
			query2.append("' and prcsno=upper('");
			query2.append(prcsNo);
			query2.append("')");
			System.out.println("PrcsDAO addCopyPrcsCost :: "+query2.toString());
			stmt.addBatch(query2.toString());

			//설치내역 테이블 복사
			StringBuffer query3 = new StringBuffer("insert into install ");
			query3.append("select prodno, prodtype, upper('");
			query3.append(nPrcsNo);
			query3.append("'), sno, insttype, itemno, itemname, instcnt, instsno, contuprice, uprice, instplandt, instdt, itemdesc ");
			query3.append("from install ");
			query3.append("where prodno='");
			query3.append(prodNo);
			query3.append("' and prodtype='");
			query3.append(prodType);
			query3.append("' and prcsno=upper('");
			query3.append(prcsNo);
			query3.append("')");
			System.out.println("PrcsDAO addCopyInstall :: "+query3.toString());
			stmt.addBatch(query3.toString());

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

	//제품내공정복사
	public int updateCopyPrcs2(String prodNo, String prodType,String copyPrcsNo, String[] copiedPrcsNo, String[] chkFlags)throws Exception
	{
		Connection con = null;
		Statement stmt=null;
		int[] returns=null;
		try{
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			con.setAutoCommit(false);		
/*
			for(int i=0; i<chkFlags.length;i++){				
				int index = Integer.parseInt(chkFlags[i]);
				StringBuffer query = new StringBuffer("update prcs set   ");
				query.append("(prcsdesc, prcseflag, orgcd, preprcs, plstartdt, plenddt, plmh, prcstatus, ");
				query.append("prcsorgcd, realstartdt, realenddt, realmh, lastwepldt, chgflag) ");
				query.append("=(select prcsdesc, prcseflag, orgcd, preprcs, plstartdt, plenddt, plmh, prcstatus, ");
				query.append("prcsorgcd, '19000101', ' ', 0, lastwepldt, chgflag ");
				query.append("from prcs  ");
				query.append("where prodtype='");
				query.append(prodType);
				query.append("' and prodno ='");
				query.append(prodNo);
				query.append("' and prcsno = '");
				query.append(copyPrcsNo);
				query.append("')  where prodtype='");
				query.append(prodType);
				query.append("' and prodno ='");
				query.append(prodNo);
				query.append("' and prcsno = '");
				query.append(copiedPrcsNo[index]);
				query.append("'");
				System.out.println("PrcsManCntDAO.delete::\n"+query.toString());
				stmt.addBatch(query.toString());

				StringBuffer query1 = new StringBuffer("delete prcsmancnt ");
				query1.append("where prodtype='");
				query1.append(prodType);
				query1.append("' and prodno ='");
				query1.append(prodNo);
				query1.append("' and prcsno = '");
				query1.append(copiedPrcsNo[index]);
				query1.append("'");
				System.out.println("PrcsManCntDAO.deleteCopyPrcs2::\n"+query1.toString());
				stmt.addBatch(query1.toString());

				StringBuffer query2 = new StringBuffer("insert into prcsmancnt ");
				query2.append("select prodno, prodtype, '");
				query2.append(copiedPrcsNo[index]);
				query2.append("', empid, jobcd, intostartdt, intoenddt, intorate ");
				query2.append("from prcsmancnt ");
				query2.append("where prodno='");
				query2.append(prodNo);
				query2.append("' and prodtype='");
				query2.append(prodType);
				query2.append("' and prcsno=upper('");
				query2.append(copyPrcsNo);
				query2.append("')");
				System.out.println("PrcsDAO addCopyPrcsManCnt :: "+query2.toString());
				stmt.addBatch(query2.toString());
				
				StringBuffer query3 = new StringBuffer("update prcs set chgFlag='Y' ");
				query3.append("where prodno='");
				query3.append(prodNo);
				query3.append("' and  prodType='");
				query3.append(prodType);
				query3.append("' and prcsno='");
				query3.append(copiedPrcsNo[index]);
				query3.append("'");
				System.out.println("....공정테이블 chgFlag \n"+query3.toString());
				stmt.addBatch(query3.toString());
			}
				*/
				
				
			//2005.03.30
			//공정복사시 배정자가 복사되지 않음으로 인한 코드수정
			//허대영
			
			// 체크된 공정들에 대한 배정자 삭제처리
			for(int i=0; i<chkFlags.length;i++){	
				int index = Integer.parseInt(chkFlags[i]);
				StringBuffer query1 = new StringBuffer();
				query1.append("delete prcsmancnt ");
				query1.append("where prodno = '");query1.append(prodNo);query1.append("' ");
				query1.append("and	prodtype = '");query1.append(prodType);query1.append("' ");
				query1.append("and	prcsno = '");query1.append(copiedPrcsNo[index]);query1.append("' ");
				System.out.println(query1.toString());
				stmt.addBatch(query1.toString());				
			}			
			
			// 체크된 공정들 삭제처리
			for(int i=0; i<chkFlags.length;i++){	
				int index = Integer.parseInt(chkFlags[i]);
				StringBuffer query2 = new StringBuffer();
				query2.append("delete prcs ");
				query2.append("where prodno = '");query2.append(prodNo);query2.append("' ");
				query2.append("and	prodtype = '");query2.append(prodType);query2.append("' ");
				query2.append("and	prcsno = '");query2.append(copiedPrcsNo[index]);query2.append("' ");
				System.out.println(query2.toString());
				stmt.addBatch(query2.toString());				
			}			
			
			//해당 공정 copy 후 insert 			
			for(int i=0; i<chkFlags.length;i++){	
				int index = Integer.parseInt(chkFlags[i]);
				StringBuffer query3 = new StringBuffer();
				query3.append("insert into PRCS(prodno,prodtype,prcsno,prcsname,prcsdesc,plstartdt,plenddt,prcsorgcd,realstartdt,lastwepldt) ");
				query3.append("select a.prodno,a.prodtype,'");query3.append(copiedPrcsNo[index]);query3.append("' prcsno,b.prcsname, prcsdesc, plstartdt,plenddt,prcsorgcd,'19000101','99991231' ");
				query3.append("from prcs a, stdprcs b ");
				query3.append("where prodno = '");query3.append(prodNo);query3.append("' ");
				query3.append("and	  prodtype = '");query3.append(prodType);query3.append("' ");
				query3.append("and	  a.prcsno = '");query3.append(copyPrcsNo);query3.append("' ");
				query3.append("and	  b.prcscd = substr('");query3.append(copiedPrcsNo[index]);query3.append("',1,2) ");
				query3.append("and	  b.prcsno = substr('");query3.append(copiedPrcsNo[index]);query3.append("',3,1) ");
				System.out.println(query3.toString());
				stmt.addBatch(query3.toString());
			}
			
			//해당 공정의 배정자들 insert
			for(int i=0; i<chkFlags.length;i++){	
				int index = Integer.parseInt(chkFlags[i]);
				StringBuffer query4 = new StringBuffer();
				query4.append("insert into PRCSMANCNT(prodno,prodtype,prcsno,empid,jobcd,intostartdt,intoenddt,intorate) ");
				query4.append("select a.prodno, a.prodtype, '");query4.append(copiedPrcsNo[index]);query4.append("' prcsNo, a.empId, a.jobCd, a.intoStartDt, a.intoEndDt, a.intoRate ");
				query4.append("from prcsmancnt a ");
				query4.append("where a.prodno = '");query4.append(prodNo);query4.append("' ");
				query4.append("and	  a.prodtype = '");query4.append(prodType);query4.append("' ");
				query4.append("and	  a.prcsno = '");query4.append(copyPrcsNo);query4.append("' ");
				System.out.println(query4.toString());
				stmt.addBatch(query4.toString());
			}	
	
			returns = stmt.executeBatch();
			con.commit();			
		}catch(Exception e){
			if(con!=null)
				con.rollback();
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
			throw e;
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

	//타제품의 공정을 복사--공정이 있으면 update, 공정이 없으면 insert
	//prodNo제품(타제품)의 공정을 copiedProdNo제품(당제품)의 공정으로 복사
	public int copyOuterPrcs(String copiedProdNo, String prodNo, String prodType,String[] copyPrcsNo, String[] chkFlags, String empOrgCd)throws Exception
	{
		Connection con = null;
		Statement stmt=null;
	        ResultSet rs = null;
		int[] returns=null;
		try{
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

			for(int i=0; i<chkFlags.length;i++){
				int index = Integer.parseInt(chkFlags[i]);
				StringBuffer query = new StringBuffer();
				query.append("select * from prcs ");
				query.append("where prodno='");
				query.append(copiedProdNo);
				query.append("' and prodtype='");
				query.append(prodType);
				query.append("' and prcsno='");
				query.append(copyPrcsNo[index]);
				query.append("'");

				rs = DataBaseUtil.executeSQL(con,stmt,query.toString());
				PrcsDTO tmp=(PrcsDTO)DataBaseUtil.moveToEntity("com.wms.beans.dto.PrcsDTO",rs);

				if(tmp==null){//추후 공정원가,설치내역 추가할 수 있음.
					//공정테이블 복사
					StringBuffer query1 = new StringBuffer("insert into prcs ");
					query1.append("select '"+copiedProdNo+"', prodtype, prcsno, prcsname,");
					query1.append("prcsdesc, prcseflag, '', '', plstartdt, ");
					query1.append("plenddt, plmh, '', '"+empOrgCd+"', '19000101', '', 0, '99991231','Y',0 ");
					query1.append("from prcs ");
					query1.append("where prodno='");
					query1.append(prodNo);
					query1.append("' and prodtype='");
					query1.append(prodType);
					query1.append("' and prcsno=upper('");
					query1.append(copyPrcsNo[index]);
					query1.append("')");
					System.out.println("PrcsDAO.copyOuterPrcs :: "+query1.toString());
					stmt.executeUpdate(query1.toString());
		
					/*	
					//공정인원 테이블 복사
					StringBuffer query2 = new StringBuffer("insert into prcsmancnt ");
					query2.append("select prodno, prodtype, prcsno, ");
					query2.append("empid, jobcd, intostartdt, intoenddt, intorate ");
					query2.append("from prcsmancnt ");
					query2.append("where prodno='");
					query2.append(prodNo);
					query2.append("' and prodtype='");
					query2.append(prodType);
					query2.append("' and prcsno=upper('");
					query2.append(copyPrcsNo[index]);
					query2.append("')");
					System.out.println("PrcsDAO copyOuterPrcs :: "+query2.toString());
					stmt.addBatch(query2.toString());				
					*/
				}else{
                    			StringBuffer query1 = new StringBuffer();
					query1.append("update prcs set   ");
					query1.append("(prcsdesc, plstartdt, plenddt, plmh, ");
					query1.append("prcsorgcd, realstartdt, realenddt, realmh, chgflag) ");
					query1.append("=(select prcsdesc, plstartdt, plenddt, plmh, ");
					query1.append("'"+empOrgCd+"', '19000101', ' ', 0, 'Y' ");
					query1.append("from prcs  ");
					query1.append("where prodtype='");
					query1.append(prodType);
					query1.append("' and prodno ='");
					query1.append(prodNo);
					query1.append("' and prcsno = '");
					query1.append(copyPrcsNo[index]);
					query1.append("')  where prodtype='");
					query1.append(prodType);
					query1.append("' and prodno ='");
					query1.append(copiedProdNo);
					query1.append("' and prcsno = '");
					query1.append(copyPrcsNo[index]);
					query1.append("'");
					System.out.println("PrcsDAO copyOuterPrcs ::\n"+query1.toString());
					stmt.executeUpdate(query1.toString());
	
					/*
					StringBuffer query2 = new StringBuffer();
					query2.append("delete prcsmancnt ");
					query2.append("where prodtype='");
					query2.append(prodType);
					query2.append("' and prodno ='");
					query2.append(copiedProdNo);
					query2.append("' and prcsno = '");
					query2.append(copiedPrcsNo[index]);
					query2.append("'");
					System.out.println("PrcsDAO copyOuterPrcs ::\n"+query2.toString());
					stmt.addBatch(query2.toString());
	
					StringBuffer query3 = new StringBuffer();
					query3.append("insert into prcsmancnt ");
					query3.append("select prodno, prodtype, '");
					query3.append(copiedPrcsNo[index]);
					query3.append("', empid, jobcd, intostartdt, intoenddt, intorate ");
					query3.append("from prcsmancnt ");
					query3.append("where prodno='");
					query3.append(prodNo);
					query3.append("' and prodtype='");
					query3.append(prodType);
					query3.append("' and prcsno=upper('");
					query3.append(copiedPrcsNo[index]);
					query3.append("')");
					System.out.println("PrcsDAO addCopyPrcsManCnt :: "+query3.toString());
					stmt.addBatch(query3.toString());
					*/
				}
			}	
	/*
			returns = stmt1.executeBatch();
			con.commit();
			con.setAutoCommit(true);
			*/

		}catch(Exception e){
		}
		finally{
			try{
				if(stmt!=null)stmt.close();
//				if(stmt1!=null)stmt1.close();
				if(con!=null){
//					con.setAutoCommit(true);
					con.close();
				}
			}catch(Exception e){
				Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
			}
		}
	
		return (returns==null)?0:returns.length;	
	}

	public int updatePrcs(PrcsDTO dto)throws Exception
	{
		Connection con = null;
		Statement stmt=null;
		int returns=0;
		try{
			StringBuffer query = new StringBuffer("update prcs set ");
			query.append("prcsname='");
			query.append(dto.prcsName);
			query.append("', prcsdesc='");
			query.append(dto.prcsDesc);
			query.append("', prcseflag='");
			query.append(dto.prcsEFlag);
			query.append("', preprcs='");
			query.append(dto.prePrcs);
			query.append("', plstartdt='");
			query.append(Utility.replace(dto.plStartDt,"-"));
			query.append("', plenddt='");
			query.append(Utility.replace(dto.plEndDt,"-"));
			query.append("', plmh='");
			query.append(dto.plMh);
			query.append("', prcstatus='");
			query.append(dto.prcStatus);
			query.append("', prcsorgcd='");
			query.append(dto.prcsOrgCd);
			query.append("', chgFlag='Y', ");
			query.append("realenddt=decode('");
			query.append(dto.prcStatus);
			query.append("','완료',to_char(sysdate,'yyyymmdd'),'') ");
			query.append(" where prodno='");
			query.append(dto.prodNo);
			query.append("' and prcsno=upper('");
			query.append(dto.prcsNo);
			query.append("') and prodtype='");
			query.append(dto.prodType);
			query.append("'");

			System.out.println("updatePrcs :: "+query.toString());
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			returns = stmt.executeUpdate(query.toString());
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

	public int deletePrcs(String prodNo, String prcsNo, String prodType)throws Exception
	{
		Connection con = null;
		Statement stmt=null;
		ResultSet rs = null;
		int[] returns=null;
		try{
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			
			//2004.12.02
			//실제 DailyReportDetail 을 읽어서 공정에 실적이 있으면 삭제 못하게 하는 로직 추가함
			//허대영
			StringBuffer confirmQuery = new StringBuffer();
			confirmQuery.append("	select reportdt, reempid, jobno, prcsno, hh, mm from dailyreportdetail ");
			confirmQuery.append("where jobno = '");confirmQuery.append(prodType+prodNo);confirmQuery.append("' ");
			confirmQuery.append("and	  Prcsno = '");confirmQuery.append(prcsNo);confirmQuery.append("' ");
			confirmQuery.append("and	  delflag = 'N' ");
			confirmQuery.append("and	  HH||MM != 00 ");
			confirmQuery.append("and rownum = 1 ");
			System.out.println(confirmQuery.toString());
			rs = stmt.executeQuery(confirmQuery.toString());
			if(rs.next()){
				throw new Exception("일일업무보고에 해당공정에 대한 실적이 있으므로 제품을 삭제할 수 없습니다.");
			}

			con.setAutoCommit(false);
			
			StringBuffer query = new StringBuffer("delete prcsmancnt ");
			query.append(" where prodno='");
			query.append(prodNo);
			query.append("' and prcsno=upper('");
			query.append(prcsNo);
			query.append("') and prodtype='");
			query.append(prodType);
			query.append("'");
			System.out.println("PrcsDAO delete prcsmancnt:: "+query.toString());
			stmt.addBatch(query.toString());
			/* 테스트 DB에 해당 테이블 이 없어서 주석처리 함 
			//2005.03.30
			// 허대영
			StringBuffer query1 = new StringBuffer("delete prcscost ");
			query1.append(" where prodno='");
			query1.append(prodNo);
			query1.append("' and prcsno=upper('");
			query1.append(prcsNo);
			query1.append("') and prodtype='");
			query1.append(prodType);
			query1.append("'");
			System.out.println("PrcsDAO delete prcscost  :: "+query1.toString());
			stmt.addBatch(query1.toString());

			StringBuffer query2 = new StringBuffer("delete install ");
			query2.append(" where prodno='");
			query2.append(prodNo);
			query2.append("' and prcsno=upper('");
			query2.append(prcsNo);
			query2.append("') and prodtype='");
			query2.append(prodType);
			query2.append("'");
			System.out.println("PrcsDAO delete install  :: "+query2.toString());
			stmt.addBatch(query2.toString());
			*/
			StringBuffer query3 = new StringBuffer("delete prcs ");
			query3.append(" where prodno='");
			query3.append(prodNo);
			query3.append("' and prcsno=upper('");
			query3.append(prcsNo);
			query3.append("') and prodtype='");
			query3.append(prodType);
			query3.append("'");
			System.out.println("PrcsDAO deletePrcs :: "+query3.toString());
			stmt.addBatch(query3.toString());
			
			returns = stmt.executeBatch();
			con.commit();			
		}catch(Exception e){
			if(con!=null)
				con.rollback();
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
			throw e;
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
	
		return (returns==null)?0:returns.length;	
	}

	public int deleteCopyPrcs2(String prodNo, String prodType, String[] copiedPrcsNo, String[] chkFlags)throws Exception{
		Connection con = null;
		Statement stmt=null;
		int[] returns=null;
		try{
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			con.setAutoCommit(false);		

			for(int i=0; i<chkFlags.length;i++){
				int index = Integer.parseInt(chkFlags[i]);
				StringBuffer query = new StringBuffer("delete prcsmancnt ");
				query.append("where prodtype='");
				query.append(prodType);
				query.append("' and prodno ='");
				query.append(prodNo);
				query.append("' and prcsno = '");
				query.append(copiedPrcsNo[index]);
				query.append("'");
				System.out.println("PrcsManCntDAO.deleteCopyPrcs2::\n"+query.toString());
				stmt.addBatch(query.toString());

				StringBuffer query1 = new StringBuffer("update prcs set chgFlag='Y' ");
				query1.append("where prodno='");
				query1.append(prodNo);
				query1.append("' and  prodType='");
				query1.append(prodType);
				query1.append("' and prcsno='");
				query1.append(copiedPrcsNo[index]);
				query1.append("'");
				System.out.println("....공정테이블 chgFlag \n"+query1.toString());
				stmt.addBatch(query1.toString());
			}			

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

	public boolean prcsCdSearch(String prodNo, String prodType, String prcsNo)throws Exception{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		boolean returns = false;
		try{
			StringBuffer query = new StringBuffer("select prcsno from prcs  ");
			query.append("where prodno='");
			query.append(prodNo);
			query.append("' and prodtype='");
			query.append(prodType);
			query.append("' and  prcsno=upper('");
			query.append(prcsNo);
			query.append("')");	
					
			System.out.println("prcsCdSearch :: "+query.toString());
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			rs = DataBaseUtil.executeSQL(con,stmt,query.toString());
			while(rs.next()){
				returns=true;
			}
			
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

	public PrcsDTO[] prcsSearch(String empId, String prodNo, String prodType, String role)throws Exception
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PrcsDTO[] returns = null;
		try{
			StringBuffer query = new StringBuffer();
			if(!role.equals("admin")){
				query.append("select b.*, to_orgcdname(b.orgcd) orgName, to_orgcdname(b.prcsorgcd) prcsOrgName, c.prcsname prePrcsName,b.prcsdesc ");
				query.append("from prod a, prcs b, prcs c ");
				query.append("where a.curempid like'");
				query.append(empId);
				query.append("%' and a.prodno='");			
				query.append(prodNo);
				query.append("' and a.prodtype='");			
				query.append(prodType);
				query.append("' and a.prodno=b.prodno ");		
				query.append(" and a.prodType=b.prodType ");		
				query.append(" and b.preprcs=c.prcsno(+) ");		
				query.append(" order by b.prcsno ");	
			}else if(role.equals("admin")){
				query.append("select b.*, to_orgcdname(b.orgcd) orgName, to_orgcdname(b.prcsorgcd) prcsOrgName, c.prcsname prePrcsName,b.prcsdesc ");
				query.append("from prod a, prcs b, prcs c ");
				query.append("where a.curempid like'");
				query.append("");	//admin 기능 추가로 인한 empId 기능 삭제
				query.append("%' and a.prodno='");			
				query.append(prodNo);
				query.append("' and a.prodtype='");			
				query.append(prodType);
				query.append("' and a.prodno=b.prodno ");		
				query.append(" and a.prodType=b.prodType ");		
				query.append(" and b.preprcs=c.prcsno(+) ");		
				query.append(" order by b.prcsno ");
			}

			System.out.println("prcsSearch :: "+query.toString());

			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			rs = DataBaseUtil.executeSQL(con,stmt,query.toString());
			returns=(PrcsDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.PrcsDTO",rs);
			
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
	
	public PrcsDTO prcsSubSearch(String prodNo, String prcsNo, String prodType)throws Exception
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PrcsDTO returns = null;
		try{
			StringBuffer query = new StringBuffer("select A.*, to_orgcdname(A.orgcd) orgName, to_orgcdname(A.prcsorgcd) prcsOrgName ");
			query.append("from prcs A ");
			query.append("where prodno= '");
			query.append(prodNo);
			query.append("' and prcsno = upper('");			
			query.append(prcsNo);
			query.append("') and prodtype like '");			
			query.append(prodType);
			query.append("%' ");		
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			System.out.println("prcsSubSearch :: "+query.toString());
			rs = DataBaseUtil.executeSQL(con,stmt,query.toString());
			returns=(PrcsDTO)DataBaseUtil.moveToEntity("com.wms.beans.dto.PrcsDTO",rs);
			
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
	
	public PrcsDTO[] copiedPrcsSearch(String prodNo,String prodType)throws Exception{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PrcsDTO[] returns = null;
		try{
			StringBuffer query = new StringBuffer("select * from prcs ");
			query.append("where prodtype = '");
			query.append(prodType);
			query.append("' and prodno = '");			
			query.append(prodNo);
			query.append("' and (prcstatus!='' or prcstatus is not null) ");			
			query.append("order by prcsno");		
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			System.out.println("copiedPrcsSearch :: "+query.toString());
			rs = DataBaseUtil.executeSQL(con,stmt,query.toString());
			returns=(PrcsDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.PrcsDTO",rs);
			
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
	
	public PrcsDTO[] copyPrcsSearch(String prodNo,String prodType, String prcsNo)throws Exception{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PrcsDTO[] returns = null;
		try{
			StringBuffer query = new StringBuffer();
			if(prodType.equals("P")){
				query.append("select * from prcs ");
				query.append("where prodtype = '");query.append(prodType);query.append("' ");
				query.append("and prodno = '");query.append(prodNo);query.append("' ");
				query.append("and prcsno <> '");query.append(prcsNo);query.append("' ");	//자기자신은 복제할 수 없음
				query.append("and prcsno <> '");query.append(prcsNo.substring(0,1)+"B0");query.append("' ");	//수주공정 3개는 복사할 수 없음
				query.append("and prcsno <> '");query.append(prcsNo.substring(0,1)+"D0");query.append("' ");
				query.append("and prcsno <> '");query.append(prcsNo.substring(0,1)+"F0");query.append("' ");			
				//query.append("and (prcstatus = '' or prcstatus is null) ");			
				query.append("order by prcsno");		
			}else{
				query.append("select * from prcs ");
				query.append("where prodtype = '");query.append(prodType);query.append("' ");
				query.append("and prodno = '");query.append(prodNo);query.append("' ");
				query.append("and prcsno <> '");query.append(prcsNo);query.append("' ");	//자기자신은 복제할 수 없음
				query.append("order by prcsno");				
			}

			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			System.out.println("copyPrcsSearch :: "+query.toString());
			rs = DataBaseUtil.executeSQL(con,stmt,query.toString());
			returns=(PrcsDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.PrcsDTO",rs);
			
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