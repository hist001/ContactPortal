/*************************************************************
*	파 일 명  : HistJobDAO.java
*	작성일자  : 2004/06/22
*	작 성 자  : 
*	내    용  : 직무 관련 Data Access하는 클래스
*************************************************************/
package com.wms.beans.dao;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.wms.fw.db.DataBaseUtil;
import com.wms.beans.dto.*;
import com.wms.fw.*;
public class HistJobDAO implements IHistJob
{
	/**
	 *	기본 직무를 검색한다.
	 */
	public HistJobDTO[] searchHistJob(HistJobDTO dto)throws Exception{
		Connection con=null;
		Statement stmt = null;
		ResultSet rs = null;
		HistJobDTO[] returns=null;
		try{
		
			Hashtable ht = dto.getList();
			Enumeration nameList = ht.keys();
			String name="";
			while(nameList.hasMoreElements()){
				name=(String)nameList.nextElement();
			}
			//System.out.println("HistJobDTO::"+dto);
			String sql = "select jobcd,jobname,a.bizno,bizname,useflag from HistJob a, biz b ";
			String preFix="where";
			if(dto.useFlag!=null&&dto.useFlag.equals("Y")){				
				sql+=preFix+" substr(jobCd,1,2)='"+dto.jobCd+"' and  a.useFlag='Y' ";
				preFix="and";
			}else{			
				
				if(dto.jobCd!=null&&!dto.jobCd.trim().equals("")){
					sql+=preFix+" jobCd like '"+dto.jobCd+"%' ";
					preFix="and";
				}
//				if(dto.superJobCd!=null&&!dto.superJobCd.trim().equals("")){
//					sql+=preFix+" a.superJobCd like '"+dto.superJobCd.toUpperCase()+"%' ";
//					preFix="and";
					
					if(dto.bizNo!=null&&!dto.bizNo.trim().equals("")){
						sql+=preFix+" a.bizNo like '"+dto.bizNo.toUpperCase()+"%' ";
						preFix="and";
					
				}
			}
			
			sql+=preFix+" b.bizno (+)= a.bizno order by 1";
			System.out.println(sql);
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = DataBaseUtil.executeSQL(con,stmt, sql);
			returns=(HistJobDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.HistJobDTO",rs);
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
	
	/**
	 *	해당 직원의 직무가 승인완료된 것을 검색한다.
	 */
	public HistJobDTO[] searchEmpJob(String empNo)throws Exception{
		Connection con=null;
		Statement stmt = null;
		ResultSet rs = null;
		HistJobDTO[] returns=null;
		try{
			String sql = "select b.jobcd,b.jobname,b.bizno,c.bizname, "
                        +"a.jobTitle "
                        +"from  empjoblist a ,histjob b,biz c, "
                        +"(select   a.*,rownum rn from "
                        +"(select empid,confirmdtm,revisionno  from empjob where empid='"+empNo+"' and statusflag='JC0' order by confirmdtm desc )  a "
                        +" )  d  "
                        +"where "
                        +"d.rn=1 "
                        +"and "
                        +"a.empid=d.empid "
                        +"and  "
                        +"a.revisionno=d.revisionno "
                        +"and  "
                        +"a.jobcd=b.jobcd "
		                +"and b.useflag='Y' "
                        +"and c.bizno(+)=b.bizno "
                        +"order by 1  ";
						
			System.out.println(sql);
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = DataBaseUtil.executeSQL(con,stmt, sql);
			returns=(HistJobDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.HistJobDTO",rs);
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
	
	/**
	 *	전사직무목록을 조회한다.
	 */
	public HistJobDTO[] histJobInquiryList(String jobCd, String jobName)throws Exception{
		Connection con=null;
		Statement stmt = null;
		ResultSet rs = null;
		HistJobDTO[] returns=null;
		try{
			StringBuffer query = new StringBuffer();
			query.append("select a.*,b.bizname bizName ");
			query.append("from histjob a, biz b ");
			query.append("where jobcd like '");query.append(jobCd);
			query.append("%' and jobname like '%");query.append(jobName);
			query.append("%' ");
			query.append("and a.bizno = b.bizno(+) order by a.jobcd");
			
			System.out.println(query.toString());
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = DataBaseUtil.executeSQL(con,stmt, query.toString());
			returns=(HistJobDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.HistJobDTO",rs);
			
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

	/**
	 *	전사직무목록을 조회한다.--페이징
	 */
	public HistJobDTO[] histJobInquiryList(String jobCd, String jobName,  int pageNum, int pageSize)throws Exception{
		Connection con=null;
		Statement stmt = null;
		ResultSet rs = null;
		HistJobDTO[] returns=null;
		try{
			StringBuffer query = new StringBuffer();
			query.append("select * from (select rownum rnum, A.* from( ");
			query.append("select a.*,b.bizname bizName ");
			query.append("from histjob a, biz b ");
			query.append("where jobcd like '");query.append(jobCd);
			query.append("%' and jobname like '%");query.append(jobName);
			query.append("%' ");
			query.append("and a.bizno = b.bizno(+) order by a.jobcd");
			query.append(") A ");
			query.append("order by rownum) ");
			query.append("where rnum between ");
			query.append((pageNum-1)*pageSize+1);
			query.append(" and ");
			query.append(pageNum*pageSize);

			StringBuffer query1 = new StringBuffer();
			query1.append("select count(*) cnt ");
			query1.append("from histjob a, biz b ");
			query1.append("where jobcd like '");query1.append(jobCd);
			query1.append("%' and jobname like '%");query1.append(jobName);
			query1.append("%' ");
			query1.append("and a.bizno = b.bizno(+)");

			
			System.out.println(query.toString());
			System.out.println(query1.toString());
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = DataBaseUtil.executeSQL(con,stmt, query.toString());
			returns=(HistJobDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.HistJobDTO",rs);
			
			rs = DataBaseUtil.executeSQL(con,stmt, query1.toString());
			while(rs.next()){
				returns[0].totCnt = rs.getInt("cnt");
			}
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