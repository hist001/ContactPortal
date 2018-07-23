/*************************************************************
*	파 일 명  : BDataEmpDAO.java
*	작성일자  : 2004/06/22
*	작 성 자  : 
*	내    용  : 직원정보관리 클래스
*************************************************************/
package com.wms.beans.dao;
import java.io.*;
import java.util.*; 
import java.sql.*;
import com.wms.fw.db.DataBaseUtil;
import com.wms.beans.dto.*;
import com.wms.fw.*;
import com.wms.fw.util.DateUtil;

public class BDataEmpDAO implements IBDataEmp
{
	public BDataEmpDTO[] selectBDataEmpList(BDataEmpDTO dto) throws Exception{
		Connection con=null;
		Statement stmt = null;
		ResultSet rs = null;
		BDataEmpDTO[] returns = null;
		//BDataEmpDutyDTO[] returns2 = null;
		try{
			StringBuffer query = new StringBuffer();
			query.append("select orgCd,orgName,empKName,empId,eGrade,wStartDt,retFlag,jobDs,jobAgency,jobAgencyName, highEmpId,highEmpKName,  ");
			query.append("dutyOrgCd1,dutyOrgCd2,dutyOrgCd3,dutyOrgCd4,mFlag1,mFlag2,mFlag3,mFlag4,dutyOrgName1,dutyOrgName2,dutyOrgName3,dutyOrgName4, ");
			query.append("dutyOrgName1||mFlagName1||'  '||dutyOrgName2||mFlagName2||'  '||dutyOrgName3||mFlagName3||'  '||dutyOrgName4||mFlagName4 dutyFullName ");
			query.append("from(  ");
			query.append("select orgCd,orgName,empKName,empId,eGrade,wStartDt,retFlag,jobDs,jobAgency,jobAgencyName, highEmpId,highEmpKName,  ");
			query.append("       max(decode(cnt,'1',dutyOrgCd)) dutyOrgCd1, max(decode(cnt,'2',dutyOrgCd)) dutyOrgCd2, ");
			query.append("       max(decode(cnt,'3',dutyOrgCd)) dutyOrgCd3, max(decode(cnt,'4',dutyOrgCd)) dutyOrgCd4, ");
			query.append("       max(decode(cnt,'1',managerFlag)) mFlag1, max(decode(cnt,'2',managerFlag)) mFlag2, ");
			query.append("       max(decode(cnt,'3',managerFlag)) mFlag3, max(decode(cnt,'4',managerFlag)) mFlag4, ");
			query.append("       max(decode(cnt,'1',dutyOrgName)) dutyOrgName1, max(decode(cnt,'2',dutyOrgName)) dutyOrgName2, ");
			query.append("       max(decode(cnt,'3',dutyOrgName)) dutyOrgName3, max(decode(cnt,'4',dutyOrgName)) dutyOrgName4, ");
			query.append("       max(decode(cnt,'1',mFlagName)) mFlagName1, max(decode(cnt,'2',mFlagName)) mFlagName2, ");
			query.append("       max(decode(cnt,'3',mFlagName)) mFlagName3, max(decode(cnt,'4',mFlagName)) mFlagName4 ");
			query.append("from ( ");
			query.append("     select x.orgcd,to_orgcdname(x.orgcd) orgName,x.empid,to_empKName(x.empid) empKName,x.egrade,x.wstartdt,x.retflag,x.jobds,x.jobagency, ");
			query.append("            to_empKName(x.jobagency) jobAgencyName, y.highempid,to_empKName(y.highempid) highEmpKName,  ");
			query.append("            y.orgcd dutyOrgCd ,y.managerflag, to_orgcdname(y.orgcd) dutyOrgName, ");
			query.append("            (case  ");
			query.append("             when y.managerflag='E' then '임원' when y.managerflag='T' then '장'  ");
			query.append("             when y.managerflag='P' then 'PM' when y.managerflag='F' then '팀장'  ");
			query.append("             when y.managerflag='G' then '장' when y.managerflag='Y' then '직원' ");
			query.append("             end) mFlagName, ");
			query.append("             rank()over(partition by y.empid order by y.orgcd) cnt  ");
			query.append("     from emp x, approval y  ");
			query.append("     where x.empid = y.empid(+) "); //나중에 outer join 지울것
			query.append("     and y.useFlag = 'Y' ");
			query.append("     and x.retFlag = 'N' ");
			query.append(")  ");
			query.append("where empKName like '");query.append(dto.empKName);query.append("' ");
			query.append("and   orgName like '");query.append(dto.orgName);query.append("' ");
			query.append("group by orgCd,orgName,empKName,empId,eGrade,wStartDt,retFlag,jobDs,jobAgency,jobAgencyName, highEmpId,highEmpKName ");
			query.append(") ");


			
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			System.out.println(query.toString());	
			rs = DataBaseUtil.executeSQL(con,stmt,query.toString());
			returns=(BDataEmpDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.BDataEmpDTO",rs);						
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


	public BDataEmpDTO selectBDataEmp(String empId)throws Exception{
		Connection con=null;
		Statement stmt = null;
		ResultSet rs = null;
		BDataEmpDTO returns = new BDataEmpDTO();
		try{
			StringBuffer query = new StringBuffer();
			query.append("select orgCd,orgName,empKName,empId,eGrade,wStartDt,retFlag,jobDs,jobAgency,jobAgencyName, highEmpId,highEmpKName, ");
			query.append("dutyOrgCd1,dutyOrgCd2,dutyOrgCd3,dutyOrgCd4,mFlag1,mFlag2,mFlag3,mFlag4,dutyOrgName1,dutyOrgName2,dutyOrgName3,dutyOrgName4, ");
			query.append("dutyOrgName1||mFlagName1||'  '||dutyOrgName2||mFlagName2||'  '||dutyOrgName3||mFlagName3||'  '||dutyOrgName4||mFlagName4 dutyFullName,");
			query.append("sn1,sn2,sn3,sn4,useflag1,useflag2,useflag3,useflag4 ");
			query.append("from(  ");
			query.append("select orgCd,orgName,empKName,empId,eGrade,wStartDt,retFlag,jobDs,jobAgency,jobAgencyName, highEmpId,highEmpKName, ");
			query.append("max(decode(cnt,'1',dutyOrgCd)) dutyOrgCd1, max(decode(cnt,'2',dutyOrgCd)) dutyOrgCd2, ");
			query.append("max(decode(cnt,'3',dutyOrgCd)) dutyOrgCd3, max(decode(cnt,'4',dutyOrgCd)) dutyOrgCd4, ");
			query.append("max(decode(cnt,'1',managerFlag)) mFlag1, max(decode(cnt,'2',managerFlag)) mFlag2, ");
			query.append("max(decode(cnt,'3',managerFlag)) mFlag3, max(decode(cnt,'4',managerFlag)) mFlag4, ");
			query.append("max(decode(cnt,'1',dutyOrgName)) dutyOrgName1, max(decode(cnt,'2',dutyOrgName)) dutyOrgName2, ");
			query.append("max(decode(cnt,'3',dutyOrgName)) dutyOrgName3, max(decode(cnt,'4',dutyOrgName)) dutyOrgName4, ");
			query.append("max(decode(cnt,'1',mFlagName)) mFlagName1, max(decode(cnt,'2',mFlagName)) mFlagName2, ");
			query.append("max(decode(cnt,'3',mFlagName)) mFlagName3, max(decode(cnt,'4',mFlagName)) mFlagName4, ");
			query.append("max(decode(cnt,'1',sn)) sn1, max(decode(cnt,'2',sn)) sn2, ");
			query.append("max(decode(cnt,'3',sn)) sn3, max(decode(cnt,'4',sn)) sn4, ");
			query.append("max(decode(cnt,'1',useFlag)) useFlag1, max(decode(cnt,'2',useFlag)) useFlag2, ");
			query.append("max(decode(cnt,'3',useFlag)) useFlag3, max(decode(cnt,'4',useFlag)) useFlag4 ");
			query.append("from (  ");
			query.append("select x.orgcd,to_orgcdname(x.orgcd) orgName,x.empid,to_empKName(x.empid) empKName,x.egrade,x.wstartdt,x.retflag,x.jobds,x.jobagency, ");
			query.append("to_empKName(x.jobagency) jobAgencyName, y.highempid,to_empKName(y.highempid) highEmpKName, ");
			query.append("y.orgcd dutyOrgCd ,y.managerflag, to_orgcdname(y.orgcd) dutyOrgName,y.sn,y.useflag, ");
			query.append("(case ");
			query.append("when y.managerflag='E' then '임원' when y.managerflag='T' then '팀장' ");
			query.append("when y.managerflag='P' then 'PM' when y.managerflag='F' then 'TF팀장' ");
			query.append("when y.managerflag='G' then '그룹장' when y.managerflag='Y' then '직원' ");
			query.append("end) mFlagName, ");
			query.append("rank()over(partition by y.empid order by y.orgcd) cnt ");
			query.append("from emp x, approval y ");
			query.append("where x.empid = y.empid(+) ");	//나중에 outer join 지울것
			query.append("and y.useFlag = 'Y' ");
			query.append("and x.retFlag = 'N' ");
			query.append(") ");
			query.append("where empId = '");query.append(empId);query.append("' ");
			query.append("group by orgCd,orgName,empKName,empId,eGrade,wStartDt,retFlag,jobDs,jobAgency,jobAgencyName, highEmpId,highEmpKName ");
			query.append(") ");
			
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			System.out.println(query.toString());	
			rs = DataBaseUtil.executeSQL(con,stmt,query.toString());
			returns=(BDataEmpDTO)DataBaseUtil.moveToEntity("com.wms.beans.dto.BDataEmpDTO",rs);
						
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

	
	public boolean updateBDataEmp(BDataEmpDTO dto)throws Exception{
		Connection con=null;
		Statement stmt = null;
		ResultSet rs = null;
		int[] result = null;
		boolean returns = false;
		//BDataEmpDTO dto = new BDataEmpDTO();
		try{
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			//2004.12.08
			//허대영
			//사직처리시 대결자 지정여부 사전 확인을 위한 쿼리
			if(dto.retFlag.equals("Y")){
				StringBuffer query7 = new StringBuffer();
				query7.append("select apempid from approval where empid = '");
				query7.append(dto.empId);
				query7.append("' ");
				System.out.println(query7.toString());
				rs = stmt.executeQuery(query7.toString());
				while(rs.next()){
					if(rs.getString(1)!=null)
						throw new Exception("대결자가 지정되어 있어서 사직처리할 수 없습니다.");
				}
			}					
			
			con.setAutoCommit(false);

			StringBuffer query1 = new StringBuffer();
			query1.append("update emp set ");
			query1.append("orgcd = '");query1.append(dto.orgCd);query1.append("', ");
			query1.append("egrade = '");query1.append(dto.eGrade);query1.append("', ");
			query1.append("wstartdt = '");query1.append(dto.wStartDt);query1.append("', ");
			query1.append("jobagency = '");query1.append(dto.jobAgency);query1.append("', ");
			query1.append("jobDs = '");query1.append(dto.jobDs);query1.append("', ");
			query1.append("retFlag = '");query1.append(dto.retFlag);query1.append("' ");
			query1.append("where empid = '");query1.append(dto.empId);query1.append("' ");
			System.out.println(query1.toString());
			stmt.addBatch(query1.toString());
			
			if(dto.retFlag.equals("Y")){
				StringBuffer query6 = new StringBuffer();
				query6.append("update approval set useFlag = 'N' where empId = '");query6.append(dto.empId);query6.append("' ");
				System.out.println(query6.toString());
				stmt.addBatch(query6.toString());
			}else{
				StringBuffer subQuery1 = new StringBuffer();
				subQuery1.append("(select /*+INDEX_DESC(APPROVAL XPKAPPROVAL)*/ sn from approval where rownum=1)+1");

				if(dto.useFlag1.equals("U")){
					StringBuffer query2 = new StringBuffer();
					query2.append("update approval set ");
					query2.append("highempid = '");query2.append(dto.highEmpId);query2.append("', ");
					query2.append("orgCd = '");query2.append(dto.dutyOrgCd1);query2.append("', ");
					query2.append("orgName = '");query2.append(dto.dutyOrgName1);query2.append("', ");
					query2.append("managerFlag = '");query2.append(dto.mFlag1);query2.append("', ");
					query2.append("useFlag = 'Y' ");
					query2.append("where sn = '");query2.append(dto.sn1);query2.append("' ");
					query2.append("and empid = '");query2.append(dto.empId);query2.append("' ");
					System.out.println(query2.toString());
					stmt.addBatch(query2.toString());
				}else if(dto.useFlag1.equals("D")){
					StringBuffer query2 = new StringBuffer();
					query2.append("update approval set ");
					query2.append("highempid = '");query2.append(dto.highEmpId);query2.append("', ");
					query2.append("orgCd = '");query2.append(dto.dutyOrgCd1);query2.append("', ");
					query2.append("orgName = '");query2.append(dto.dutyOrgName1);query2.append("', ");
					query2.append("managerFlag = '");query2.append(dto.mFlag1);query2.append("', ");
					query2.append("useFlag = 'N' ");
					query2.append("where sn = '");query2.append(dto.sn1);query2.append("' ");
					query2.append("and empid = '");query2.append(dto.empId);query2.append("' ");
					System.out.println(query2.toString());
					stmt.addBatch(query2.toString());
				}else if(dto.useFlag1.equals("C")){
					StringBuffer query2 = new StringBuffer();
					query2.append("insert into APPROVAL(sn,empid,highempid,orgcd,orgname,managerflag,useFlag) values(");
					query2.append(subQuery1.toString());query2.append(",'");
					query2.append(dto.empId);query2.append("','");query2.append(dto.highEmpId);query2.append("' ,'");
					query2.append(dto.dutyOrgCd1);query2.append("','");
					query2.append(dto.dutyOrgName1);query2.append("', '");
					query2.append(dto.mFlag1);query2.append("', 'Y')");
					System.out.println(query2.toString());
					stmt.addBatch(query2.toString());
				}else if(dto.useFlag1.equals("R")){
				}

				if(dto.useFlag2.equals("U")){
					StringBuffer query3 = new StringBuffer();
					query3.append("update approval set ");
					query3.append("highempid = '");query3.append(dto.highEmpId);query3.append("', ");
					query3.append("orgCd = '");query3.append(dto.dutyOrgCd2);query3.append("', ");
					query3.append("orgName = '");query3.append(dto.dutyOrgName2);query3.append("', ");
					query3.append("managerFlag = '");query3.append(dto.mFlag2);query3.append("', ");
					query3.append("useFlag = 'Y' ");
					query3.append("where sn = '");query3.append(dto.sn2);query3.append("' ");
					query3.append("and empid = '");query3.append(dto.empId);query3.append("' ");
					System.out.println(query3.toString());
					stmt.addBatch(query3.toString());
				}else if(dto.useFlag2.equals("D")){
					StringBuffer query3 = new StringBuffer();
					query3.append("update approval set ");
					query3.append("highempid = '");query3.append(dto.highEmpId);query3.append("', ");
					query3.append("orgCd = '");query3.append(dto.dutyOrgCd2);query3.append("', ");
					query3.append("orgName = '");query3.append(dto.dutyOrgName2);query3.append("', ");
					query3.append("managerFlag = '");query3.append(dto.mFlag2);query3.append("', ");
					query3.append("useFlag = 'N' ");
					query3.append("where sn = '");query3.append(dto.sn2);query3.append("' ");
					query3.append("and empid = '");query3.append(dto.empId);query3.append("' ");
					System.out.println(query3.toString());
					stmt.addBatch(query3.toString());
				}else if(dto.useFlag2.equals("C")){
					StringBuffer query3 = new StringBuffer();
					query3.append("insert into APPROVAL(sn,empid,highempid,orgcd,orgname,managerflag,useFlag) values(");
					query3.append(subQuery1.toString());query3.append(",'");
					query3.append(dto.empId);query3.append("','");query3.append(dto.highEmpId);query3.append("' ,'");
					query3.append(dto.dutyOrgCd2);query3.append("','");
					query3.append(dto.dutyOrgName2);query3.append("', '");
					query3.append(dto.mFlag2);query3.append("', 'Y')");
					System.out.println(query3.toString());
					stmt.addBatch(query3.toString());
				}else if(dto.useFlag2.equals("R")){
				}

				if(dto.useFlag3.equals("U")){
					StringBuffer query4 = new StringBuffer();
					query4.append("update approval set ");
					query4.append("highempid = '");query4.append(dto.highEmpId);query4.append("', ");
					query4.append("orgCd = '");query4.append(dto.dutyOrgCd3);query4.append("', ");
					query4.append("orgName = '");query4.append(dto.dutyOrgName3);query4.append("', ");
					query4.append("managerFlag = '");query4.append(dto.mFlag3);query4.append("', ");
					query4.append("useFlag = 'Y' ");
					query4.append("where sn = '");query4.append(dto.sn3);query4.append("' ");
					query4.append("and empid = '");query4.append(dto.empId);query4.append("' ");
					System.out.println(query4.toString());
					stmt.addBatch(query4.toString());
				}else if(dto.useFlag3.equals("D")){
					StringBuffer query4 = new StringBuffer();
					query4.append("update approval set ");
					query4.append("highempid = '");query4.append(dto.highEmpId);query4.append("', ");
					query4.append("orgCd = '");query4.append(dto.dutyOrgCd3);query4.append("', ");
					query4.append("orgName = '");query4.append(dto.dutyOrgName3);query4.append("', ");
					query4.append("managerFlag = '");query4.append(dto.mFlag3);query4.append("', ");
					query4.append("useFlag = 'N' ");
					query4.append("where sn = '");query4.append(dto.sn3);query4.append("' ");
					query4.append("and empid = '");query4.append(dto.empId);query4.append("' ");
					System.out.println(query4.toString());
					stmt.addBatch(query4.toString());
				}else if(dto.useFlag3.equals("C")){
					StringBuffer query4 = new StringBuffer();
					query4.append("insert into APPROVAL(sn,empid,highempid,orgcd,orgname,managerflag,useFlag) values(");
					query4.append(subQuery1.toString());query4.append(",'");
					query4.append(dto.empId);query4.append("','");query4.append(dto.highEmpId);query4.append("' ,'");
					query4.append(dto.dutyOrgCd3);query4.append("','");
					query4.append(dto.dutyOrgName3);query4.append("', '");
					query4.append(dto.mFlag3);query4.append("', 'Y')");
					System.out.println(query4.toString());
					stmt.addBatch(query4.toString());
				}else if(dto.useFlag3.equals("R")){
				}

				if(dto.useFlag4.equals("U")){
					StringBuffer query5 = new StringBuffer();
					query5.append("update approval set ");
					query5.append("highempid = '");query5.append(dto.highEmpId);query5.append("', ");
					query5.append("orgCd = '");query5.append(dto.dutyOrgCd4);query5.append("', ");
					query5.append("orgName = '");query5.append(dto.dutyOrgName4);query5.append("', ");
					query5.append("managerFlag = '");query5.append(dto.mFlag4);query5.append("', ");
					query5.append("useFlag = 'Y' ");
					query5.append("where sn = '");query5.append(dto.sn4);query5.append("' ");
					query5.append("and empid = '");query5.append(dto.empId);query5.append("' ");
					System.out.println(query5.toString());
					stmt.addBatch(query5.toString());
				}else if(dto.useFlag4.equals("D")){
					StringBuffer query5 = new StringBuffer();
					query5.append("update approval set ");
					query5.append("highempid = '");query5.append(dto.highEmpId);query5.append("', ");
					query5.append("orgCd = '");query5.append(dto.dutyOrgCd4);query5.append("', ");
					query5.append("orgName = '");query5.append(dto.dutyOrgName4);query5.append("', ");
					query5.append("managerFlag = '");query5.append(dto.mFlag4);query5.append("', ");
					query5.append("useFlag = 'N' ");
					query5.append("where sn = '");query5.append(dto.sn4);query5.append("' ");
					query5.append("and empid = '");query5.append(dto.empId);query5.append("' ");
					System.out.println(query5.toString());
					stmt.addBatch(query5.toString());
				}else if(dto.useFlag4.equals("C")){
					StringBuffer query5 = new StringBuffer();
					query5.append("insert into APPROVAL(sn,empid,highempid,orgcd,orgname,managerflag,useFlag) values(");
					query5.append(subQuery1.toString());query5.append(",'");
					query5.append(dto.empId);query5.append("','");query5.append(dto.highEmpId);query5.append("' ,'");
					query5.append(dto.dutyOrgCd4);query5.append("','");
					query5.append(dto.dutyOrgName4);query5.append("', '");
					query5.append(dto.mFlag4);query5.append("', 'Y')");
					System.out.println(query5.toString());
					stmt.addBatch(query5.toString());
				}else if(dto.useFlag4.equals("R")){
				}
			}

			StringBuffer query0 = new StringBuffer();
			query0.append("update APPROVAL set ");
			query0.append("highempid = '");query0.append(dto.highEmpId);query0.append("' ");
			query0.append("where empId = '");query0.append(dto.empId);query0.append("' ");
			System.out.println(query0.toString());
			stmt.addBatch(query0.toString());
			/*
			if(dto.retFlag.equals("Y")){
				StringBuffer query6 = new StringBuffer();
				query6.append("update approval set useFlag = 'N' where empId = '");query6.append(dto.empId);query6.append("' ");
				System.out.println(query6.toString());
				stmt.addBatch(query6.toString());
			}
			*/

			result=stmt.executeBatch();
			if(result.length>0){
				returns = true;
			}else{
				throw new Exception("insert후 rs값이 0 이상이 아닙니다.");
			} 
			con.commit();
			
		}catch(Exception e){
				Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
		}
		finally{
			con.setAutoCommit(true);
			try{
				if(rs!=null)rs.close();
				if(stmt!=null)stmt.close();
				if(con!=null)con.close();
			}catch(Exception e){
				Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
			}

		}		
		//com.wms.fw.Utility.fixNullAndTrimAll(returns);
    	return returns;
	}

	public boolean createBDataEmp(BDataEmpDTO dto)throws Exception{
		Connection con=null;
		Statement stmt = null;
		ResultSet rs = null;
		int[] result = null;
		boolean returns = false;
		//BDataEmpDTO dto = new BDataEmpDTO();
		try{
			con = DataBaseUtil.getConnection();
			con.setAutoCommit(false);
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			//EMP table 신규직원등록
			StringBuffer query1 = new StringBuffer();
			query1.append("insert into EMP(empid,empkname,orgcd,wstartdt,egrade,jobds,jobagency,retflag) ");
			query1.append("values('");query1.append(dto.empId);
			query1.append("','");query1.append(dto.empKName);
			query1.append("','");query1.append(dto.orgCd);
			query1.append("','");query1.append(dto.wStartDt);
			query1.append("','");query1.append(dto.eGrade);
			query1.append("','");query1.append(dto.jobDs);
			query1.append("','");query1.append(dto.jobAgency);
			query1.append("','N') ");

			System.out.println(query1.toString());
			stmt.addBatch(query1.toString());

			StringBuffer subQuery1 = new StringBuffer();
			subQuery1.append("(select /*+INDEX_DESC(APPROVAL XPKAPPROVAL)*/ sn from approval where rownum=1)+1");
			//APPROVAL table 업무승인자등 등록
			if(!((dto.dutyOrgCd1.trim()).equals(""))&&!(dto.dutyOrgCd1==null)){
				StringBuffer query2 = new StringBuffer();
				query2.append("insert into APPROVAL(sn,empid,highempid,orgcd,orgname,managerflag,useFlag) values(");
				query2.append(subQuery1.toString());query2.append(",'");
				query2.append(dto.empId);query2.append("','");query2.append(dto.highEmpId);query2.append("' ,'");
				query2.append(dto.dutyOrgCd1);query2.append("','");
				query2.append(dto.dutyOrgName1);query2.append("', '");
				query2.append(dto.mFlag1);query2.append("', 'Y')");
				System.out.println(query2.toString());
				stmt.addBatch(query2.toString());
			}else if(!((dto.dutyOrgCd2.trim()).equals(""))&&!(dto.dutyOrgCd2==null)){
				StringBuffer query3 = new StringBuffer();
				query3.append("insert into APPROVAL(sn,empid,highempid,orgcd,orgname,managerflag,useFlag) values(");
				query3.append(subQuery1.toString());query3.append(",'");
				query3.append(dto.empId);query3.append("','");query3.append(dto.highEmpId);query3.append("' ,'");
				query3.append(dto.dutyOrgCd2);query3.append("','");
				query3.append(dto.dutyOrgName2);query3.append("', '");
				query3.append(dto.mFlag2);query3.append("', 'Y')");
				System.out.println(query3.toString());
				stmt.addBatch(query3.toString());
			}else if(!((dto.dutyOrgCd3.trim()).equals(""))&&!(dto.dutyOrgCd3==null)){
				StringBuffer query4 = new StringBuffer();
				query4.append("insert into APPROVAL(sn,empid,highempid,orgcd,orgname,managerflag,useFlag) values(");
				query4.append(subQuery1.toString());query4.append(",'");
				query4.append(dto.empId);query4.append("','");query4.append(dto.highEmpId);query4.append("' ,'");
				query4.append(dto.dutyOrgCd3);query4.append("','");
				query4.append(dto.dutyOrgName3);query4.append("', '");
				query4.append(dto.mFlag3);query4.append("', 'Y')");
				System.out.println(query4.toString());
				stmt.addBatch(query4.toString());
			}else if(!((dto.dutyOrgCd4.trim()).equals(""))&&!(dto.dutyOrgCd4==null)){
				StringBuffer query5 = new StringBuffer();
				query5.append("insert into APPROVAL(sn,empid,highempid,orgcd,orgname,managerflag,useFlag) values(");
				query5.append(subQuery1.toString());query5.append(",'");
				query5.append(dto.empId);query5.append("','");query5.append(dto.highEmpId);query5.append("' ,'");
				query5.append(dto.dutyOrgCd4);query5.append("','");
				query5.append(dto.dutyOrgName4);query5.append("', '");
				query5.append(dto.mFlag4);query5.append("', 'Y')");
				System.out.println(query5.toString());
				stmt.addBatch(query5.toString());
			}

			result=stmt.executeBatch();
			if(result.length>0){
				returns = true;
			}else{
				throw new Exception("insert후 rs값이 0 이상이 아닙니다.");
			} 
			con.commit();
			
		}catch(Exception e){
				Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
		}
		finally{
			con.setAutoCommit(true);
			try{
				if(rs!=null)rs.close();
				if(stmt!=null)stmt.close();
				if(con!=null)con.close();
			}catch(Exception e){
				Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
			}

		}		
		//com.wms.fw.Utility.fixNullAndTrimAll(returns);
    	return returns;
	}

	/*업무보고상태변경 기능 */
	public DayJobReportDTO[] selectDiliList(String highEmpId, String role, String reportDtFr,String reportDtTo,String s_crEmpId,String s_apEmpId) throws Exception{
		Connection con=null;
		Statement stmt = null;
		ResultSet rs = null;
		DayJobReportDTO[] returns = null;
		try{
			StringBuffer query = new StringBuffer();
			/*approval table 에서 업무승인자 변경시 즉시 하위직원의 업무보고상태를 변경할 수 있는 쿼리
			query.append("select reportDt, reEmpId ,to_empkname(reEmpId) reEmpKName , plEmpId , to_empkname(plEmpId) plEmpKName,statusFlag ");
			query.append("from dailyreport ");
			query.append("where reportdt = to_char(sysdate,'YYYYMMDD') ");
			query.append("and   reempid IN (select distinct empid ");
			query.append("                  from approval ");
			query.append("                  where highempid = '");query.append(highEmpId);query.append("') ");
			*/
			//approval table 에서 업무승인자 변경시 즉시 하위직원의 업무보고상태를 변경할 수 없는 쿼리
			query.append("select reportDt, reEmpId ,to_empkname(reEmpId) reEmpKName , apEmpId , to_empkname(apEmpId) apEmpKName,statusFlag, to_statusnm(statusFlag) statusName ");
			query.append("from dailyreport ");
			query.append("where reportdt between replace('");
			query.append(reportDtFr); 
			query.append("','-','') and replace('");
			query.append(reportDtTo);			
			query.append("','-','') "); 
			query.append(" and crempid like '");
			query.append(s_crEmpId); 
			query.append("' and apEmpid like '");
			query.append(s_apEmpId);
			query.append("'");
			//query.append("where reportdt = to_char(sysdate,'YYYYMMDD') ");
			//검색대상에 대한 admin 권한처리
			/* admin 기능 삭제, 노츠메뉴에서 권한처리 대신함
			if(role.equals("admin")){
				//admin인경우 과거 데이터 변경가능
				query.append("where reportdt = '");query.append(reportDt);query.append("' ");
			}else{
				query.append("where reportdt = to_char(sysdate,'YYYYMMDD') ");
				query.append("and   apEmpId ='");query.append(highEmpId);query.append("' ");				
				query.append("union all ");
				query.append("select reportDt, reEmpId ,to_empkname(reEmpId) reEmpKName , apEmpId , to_empkname(apEmpId) apEmpKName,statusFlag, to_statusnm(statusFlag) statusName ");
				query.append("from dailyreport ");
				query.append("where reportdt = to_char(sysdate,'YYYYMMDD') ");
				query.append("and   reempid ='");query.append(highEmpId);query.append("' ");
				//해당인원이 팀장인 경우만 본인의 업무보고상태를 변경하는 것이 가능함
				query.append("and 'OK' IN (select distinct 'OK' from approval where empid='");
				query.append(highEmpId);query.append("' ");
				query.append("and managerflag='T') ");			
			}
			*/
			query.append("order by reEmpKName ");
			
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			System.out.println(query.toString());	
			rs = DataBaseUtil.executeSQL(con,stmt,query.toString());
			returns=(DayJobReportDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.DayJobReportDTO",rs);						
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
	
	public DiliDTO selectDili( String reportDt, String reEmpId)throws Exception{
		Connection con=null;
		Statement stmt = null; 
		ResultSet rs = null;
		DiliDTO returns = new DiliDTO();
		try{ 
			StringBuffer query = new StringBuffer();
			query.append("select reportDt, reEmpId, to_empkname(reEmpId) reEmpKName, apEmpId, to_empkname(apEmpId) apEmpKName, statusFlag, to_statusnm(statusFlag) statusName ");
			query.append("from dailyreport ");
			query.append("where reportdt = '");query.append(reportDt);query.append("' ");
			query.append("and reEmpId = '");query.append(reEmpId);query.append("' ");
			 
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			System.out.println(query.toString());	
			rs = DataBaseUtil.executeSQL(con,stmt,query.toString());
			returns=(DiliDTO)DataBaseUtil.moveToEntity("com.wms.beans.dto.DiliDTO",rs);
						
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

	public boolean saveDili(DiliDTO dto, String userEmpId)throws Exception{
		Connection con=null;
		Statement stmt = null;
		ResultSet rs = null;
		boolean returns = false;

		try{
			con = DataBaseUtil.getConnection();
			con.setAutoCommit(false);
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

			StringBuffer query1 = new StringBuffer();
			query1.append("update DAILYREPORT set ");
			query1.append("statusFlag ='");query1.append(dto.statusFlag);query1.append("' ");
			query1.append("where reportDt = '");query1.append(dto.reportDt);query1.append("' ");
			query1.append("and reempid ='");query1.append(dto.reEmpId);query1.append("' ");
			stmt.addBatch(query1.toString());
			System.out.println(query1.toString());

			StringBuffer query2 = new StringBuffer();
			query2.append("insert into STATUSLOG values(statuslog_sn.nextval,'");
			query2.append(dto.reportDt);query2.append("','");
			query2.append(dto.reEmpId);query2.append("','");
			query2.append(dto.apEmpId);query2.append("','");
			query2.append(userEmpId);query2.append("',sysdate,'");
			query2.append(dto.statusFlag);query2.append("','");
			query2.append(dto.cgDs);query2.append("') ");
			stmt.addBatch(query2.toString());
			System.out.println(query2.toString());
            //추후작성인경우 DailyReportLog Table에 로그 등록
            if(dto.statusFlag.equals("WDS") ){
				String dailyRptLog="insert into dailyreportlog(reportdt,reempid,rewritedtm,completeflag) values("
			                   +"'"+dto.reportDt+"','"+dto.reEmpId+"',sysdate+1,'"+dto.statusFlag+"')";
				System.out.println(dailyRptLog);
                stmt.addBatch(dailyRptLog);
			}else if(dto.statusFlag.substring(0,1).equals("E")){   //상태를 근태코드로 변경하는 경우 dailyreportlog를 지움
				String dailyRptLog="delete dailyreportlog where reportdt = '" + dto.reportDt + "'" + " and reempid = '" + dto.reEmpId + "'" ;
				System.out.println(dailyRptLog);
                stmt.addBatch(dailyRptLog);
			}

			int[] result=stmt.executeBatch();
			if(result!=null){
				if(result.length>0){
					returns = true;
				}
			}else{
				throw new Exception("BDataEmpDAO 의 saveDili() 에서 에러가 발생하였습니다.");
			}
			con.commit();
		}catch(Exception e){
				Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
		}
		finally{
			con.setAutoCommit(true);
			try{
				if(rs!=null)rs.close();
				if(stmt!=null)stmt.close();
				if(con!=null)con.close();
			}catch(Exception e){
				Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
			}
		}
		//com.wms.fw.Utility.fixNullAndTrimAll(returns);
	    return returns;
	}
	
}