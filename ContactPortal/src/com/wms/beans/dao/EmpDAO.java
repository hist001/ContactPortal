/*************************************************************
*	파 일 명  : EmpDAO.java
*	작성일자  : 2004/09/03
*	작 성 자  : 
*	내    용  : 직원 정보 조회
*************************************************************/
package com.wms.beans.dao;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.wms.fw.db.DataBaseUtil;
import com.wms.beans.dto.*;
import com.wms.fw.*;

public class EmpDAO implements IEmp
{
	/**
	 * 	직무별, 소속별, 직원별 직원정보를 조회한다.-페이징
 	 */
	public EmpDTO[] empInquiryList(String orgCd, String orgName, String empId, String empKName, String jobCd, String jobName,int pageNum,int pageSize)throws Exception
	{
		Connection con = null;
		Statement stmt=null;
		ResultSet rs = null;
		EmpDTO[] returns=null;
		try{
			String strJobCd="";
			if(jobCd.trim().equals("")){
				strJobCd="ALL";
			}else{
				strJobCd=jobCd;
			}
			StringBuffer query = new StringBuffer();
			StringBuffer query1 = new StringBuffer();
			query.append("select * from (select rownum rnum, A.* from( ");
			if(jobCd==null || jobCd.trim().equals("")){//직무코드 전체로 했을때(직무코드 조건을 무시한다.)
				query.append("select distinct b.empkname, b.empid, b.egrade grade, c.cdname egrade, a.drType as drType ");
				query.append("b.wstartdt,b.jobds,to_empkname(b.jobagency) jobagency,a.orgCd empOrgCd, a.orgname empOrgName  ");
				query.append("from org a, emp b, cocode c ");
				query.append("where a.orgcd like '"+orgCd+"%'  ");
				query.append("and b.retFlag = 'N' ");		//사직자 조회 막음
				query.append("and a.orgname like '%"+orgName+"%'  ");
				query.append("and b.empid  like'"+empId+"%'  ");
				query.append("and b.empkname  like '%"+empKName+"%'  ");
				query.append("and a.orgcd=b.orgcd ");
				query.append("and c.cdtype='EA'   ");
				query.append("and b.egrade=c.cd ");
				query.append("order by a.orgcd, grade, empid ");

				query1.append("select count(distinct b.empid) cnt  ");
				query1.append("from org a, emp b, cocode c ");
				query1.append("where a.orgcd like '"+orgCd+"%'  ");
				query1.append("and b.retFlag = 'N' ");		//사직자 조회 막음
				query1.append("and a.orgname like '%"+orgName+"%'  ");
				query1.append("and b.empid  like'"+empId+"%'  ");
				query1.append("and b.empkname  like '%"+empKName+"%'  ");
				query1.append("and a.orgcd=b.orgcd ");
				query1.append("and c.cdtype='EA' ");
				query1.append("and b.egrade=c.cd ");
				
			}else{//직무코드 조건이 있을때
				query.append("select distinct b.empkname, b.empid, b.egrade grade, c.cdname egrade, a.drType as drType "); 
				query.append("b.wstartdt,b.jobds,to_empkname(b.jobagency) jobagency, a.orgCd empOrgCd, a.orgname empOrgName "); 
				query.append("from org a, emp b, cocode c, ");
				query.append("	(select B.empid, B.revisionno, B.jobcd ");
				query.append("	from ");
				query.append("	(select empid, max(to_number(revisionno)) revisionno  ");
				query.append("	from empjob  ");
				query.append("	where empid like '"+empId+"%' ");
				query.append("	and statusflag='JC0' ");
				query.append("	group by empid) A, empjoblist B ");
				query.append("	where A.empid = B.empid ");
				query.append("	and A.revisionno = B.revisionno ");
				query.append("	) d   ");
				query.append("where a.orgcd like '"+orgCd+"%'  ");
				query.append("and a.orgname like '%"+orgName+"%'  ");
				query.append("and b.empid  like'"+empId+"%'  ");
				query.append("and b.empkname  like '%"+empKName+"%'  ");
				query.append("and a.orgcd=b.orgcd ");
				query.append("and b.retFlag='N' ");		//사직자 조회 막음
				query.append("and c.cdtype='EA' ");
				query.append("and b.egrade=c.cd ");
				query.append("and d.jobcd like '"+jobCd+"%' ");
				query.append("and b.empid = d.empid(+)  ");
				query.append("order by a.orgcd, grade, empid ");

				query1.append("select count(distinct b.empid) cnt "); 
				query1.append("from org a, emp b, cocode c, ");
				query1.append("	(select B.empid, B.revisionno, B.jobcd ");
				query1.append("	from ");
				query1.append("	(select empid, max(to_number(revisionno)) revisionno  ");
				query1.append("	from empjob  ");
				query1.append("	where empid like '"+empId+"%' ");
				query1.append("	and statusflag='JC0' ");
				query1.append("	group by empid) A, empjoblist B ");
				query1.append("	where A.empid = B.empid ");
				query1.append("	and A.revisionno = B.revisionno ");
				query1.append("	) d   ");
				query1.append("where a.orgcd like '"+orgCd+"%'  ");
				query1.append("and a.orgname like '%"+orgName+"%'  ");
				query1.append("and b.empid  like'"+empId+"%'  ");
				query1.append("and b.empkname  like '%"+empKName+"%'  ");
				query1.append("and a.orgcd=b.orgcd ");
				query1.append("and b.retFlag='N' ");		//사직자 조회 막음
				query1.append("and c.cdtype='EA' ");
				query1.append("and b.egrade=c.cd ");
				query1.append("and d.jobcd like '"+jobCd+"%' ");
				query1.append("and b.empid = d.empid(+)  ");
			}
			query.append(") A ");
			query.append("order by rownum) ");
			query.append("where rnum between ");
			query.append((pageNum-1)*pageSize+1);
			query.append(" and ");
			query.append(pageNum*pageSize);
			System.out.println(query.toString());
			System.out.println(query1.toString());

			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = DataBaseUtil.executeSQL(con,stmt, query.toString());
			returns=(EmpDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.EmpDTO",rs);
			
			rs = DataBaseUtil.executeSQL(con,stmt, query1.toString());
			while(rs.next()){
				returns[0].totCnt=rs.getInt("cnt");
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
	
		com.wms.fw.Utility.fixNullAndTrimAll(returns);
		return returns;	
	}

	/**
	 * 	직무별, 소속별, 직원별 직원정보를 조회한다.
 	 */
	public EmpDTO[] empInquiryList(String orgCd, String orgName, String empId, String empKName, String jobCd, String jobName)throws Exception
	{
		Connection con = null;
		Statement stmt=null;
		ResultSet rs = null;
		EmpDTO[] returns=null;
		try{
			String strJobCd="";
			if(jobCd.trim().equals("")){
				strJobCd="ALL";
			}else{
				strJobCd=jobCd;
			}
			StringBuffer query = new StringBuffer();
			StringBuffer query1 = new StringBuffer();
			if(jobCd==null || jobCd.trim().equals("")){//직무코드 전체로 했을때(직무코드 조건을 무시한다.)
				query.append("select distinct b.empkname, b.empid, b.egrade grade, c.cdname egrade, a.drType as drType ");
				query.append("b.wstartdt,b.jobds,to_empkname(b.jobagency) jobagency,a.orgCd empOrgCd, a.orgname empOrgName  ");
				query.append("from org a, emp b, cocode c ");
				query.append("where a.orgcd like '"+orgCd+"%'  ");
				query.append("and a.orgname like '%"+orgName+"%'  ");
				query.append("and b.empid  like'"+empId+"%'  ");
				query.append("and b.empkname  like '%"+empKName+"%'  ");
				query.append("and a.orgcd=b.orgcd ");
				query.append("and c.cdtype='EA'   ");
				query.append("and b.egrade=c.cd ");
				query.append("order by a.orgcd, grade, empid ");

			}else{//직무코드 조건이 있을때
				query.append("select distinct b.empkname, b.empid, b.egrade grade, c.cdname egrade, a.drType as drType "); 
				query.append("b.wstartdt,b.jobds,to_empkname(b.jobagency) jobagency, a.orgCd empOrgCd, a.orgname empOrgName "); 
				query.append("from org a, emp b, cocode c, ");
				query.append("	(select B.empid, B.revisionno, B.jobcd ");
				query.append("	from ");
				query.append("	(select empid, max(to_number(revisionno)) revisionno  ");
				query.append("	from empjob  ");
				query.append("	where empid like '"+empId+"%' ");
				query.append("	and statusflag='JC0' ");
				query.append("	group by empid) A, empjoblist B ");
				query.append("	where A.empid = B.empid ");
				query.append("	and A.revisionno = B.revisionno ");
				query.append("	) d   ");
				query.append("where a.orgcd like '"+orgCd+"%'  ");
				query.append("and a.orgname like '%"+orgName+"%'  ");
				query.append("and b.empid  like'"+empId+"%'  ");
				query.append("and b.empkname  like '%"+empKName+"%'  ");
				query.append("and a.orgcd=b.orgcd ");
				query.append("and c.cdtype='EA' ");
				query.append("and b.egrade=c.cd ");
				query.append("and d.jobcd like '"+jobCd+"%' ");
				query.append("and b.empid = d.empid(+)  ");
				query.append("order by a.orgcd, grade, empid ");
			}
			System.out.println(query.toString());

			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = DataBaseUtil.executeSQL(con,stmt, query.toString());
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
	 *	개인별 직무 목록을 검색한다.
	 */
	public EmpDTO empJobInquiryList(String empId)throws Exception
	{
		Connection con = null;
		Statement stmt=null;
		ResultSet rs = null;
		EmpDTO returns=null;

		try{
			StringBuffer query = new StringBuffer();
			query.append("select a.*,d.*,a.orgCd empOrgCd, b.jobname, c.bizno, c.bizname, b.actDs ");
			query.append("from emp a, histjob b, biz c, ");
			query.append("	(select B.* ");
			query.append("	from ");
			query.append("	(select empid, max(to_number(revisionno)) revisionno  ");
			query.append("	from empjob  ");
			query.append("	where empid = '"+empId+"' ");
			query.append("	and statusflag='JC0' ");
			query.append("	group by empid) A, empjoblist B ");
			query.append("	where A.empid = B.empid ");
			query.append("	and A.revisionno = B.revisionno) d  ");
			query.append("where a.empid = '"+empId+"'  ");
			query.append("and a.empid=d.empid  ");
			query.append("and d.jobcd=b.jobcd   ");
			query.append("and b.bizno=c.bizno(+) ");
			System.out.println(query.toString());

			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = DataBaseUtil.executeSQL(con,stmt, query.toString());
			returns=(EmpDTO)DataBaseUtil.moveToEntity("com.wms.beans.dto.EmpDTO",rs);
			returns.empjobs = (EmpJobDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.EmpJobDTO",rs);
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
	 *	개인별 직무 목록을 검색시 직원 정보를 검색한다..
	 */
	public EmpDTO empApprovalInquiryList(String empId)throws Exception
	{
		Connection con = null;
		Statement stmt=null;
		ResultSet rs = null;
		EmpDTO returns=null;
		try{
			StringBuffer query = new StringBuffer();
			query.append("select c.cdname egrade, a.*, a.orgCd empOrgCd, to_orgcdname(a.orgcd) empOrgName, ");
			query.append("b.*,to_empkname(b.apempid) apEmpKName,  ");
			query.append("to_empkname(b.highempid) highEmpKName ");
			query.append("from emp a, approval b, cocode c ");
			query.append(" where a.empid='");query.append(empId);query.append("' ");
			query.append(" and a.empid=b.empid ");
			query.append(" and c.cdtype = 'EA' ");
			query.append(" and a.egrade = c.cd ");
			
			System.out.println(query.toString());

			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = DataBaseUtil.executeSQL(con,stmt, query.toString());
			returns=(EmpDTO)DataBaseUtil.moveToEntity("com.wms.beans.dto.EmpDTO",rs);
			returns.approvals = (ApprovalDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.ApprovalDTO",rs);
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