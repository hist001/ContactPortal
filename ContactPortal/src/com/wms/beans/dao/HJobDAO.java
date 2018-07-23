package com.wms.beans.dao;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.wms.fw.db.DataBaseUtil;
import com.wms.beans.dto.*;
import com.wms.fw.*;
import com.wms.fw.util.DateUtil;
import java.util.ArrayList;
import java.util.Hashtable;

public class HJobDAO implements IHJob
{
	//개인직무표 LIST
	public HJobDTO[] selectHJobList(String empId) throws Exception{
		Connection con=null;
		Statement stmt = null;
		ResultSet rs = null;
		HJobDTO[] returns = null;
		try{
			StringBuffer query = new StringBuffer();			
			query.append("select a.empId, to_empkname(a.empId) empKName, a.revisionno, to_orgName(a.empId) orgName, a.jobDs, ");
			query.append("a.mkempid, to_empkname(a.mkempid) mkEmpKName, a.apEmpId, to_empKName(a.apEmpId) apEmpKName, ");
			query.append("to_char(a.confirmDtm,'YYYY-MM-DD HH24:mi:ss') confirmDtm , to_char(a.lastDtm,'YYYY-MM-DD') lastDtm, a.statusflag ");
			query.append("from EMPJOB a ");
			query.append("where a.empId = '");query.append(empId);query.append("' ");
			query.append("order by a.lastdtm desc , a.confirmdtm desc, a.statusFlag desc ");

			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			System.out.println(query.toString());
			rs = DataBaseUtil.executeSQL(con,stmt,query.toString());
			returns = (HJobDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.HJobDTO",rs);

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

	//직무표 승인 LIST
	public HJobDTO[] selectHJobConfList(String empId) throws Exception{
		Connection con=null;
		Statement stmt = null;
		ResultSet rs = null;
		HJobDTO[] returns = null;
		try{
			StringBuffer query = new StringBuffer();			
			query.append("select a.empId, to_empkname(a.empId) empKName, a.revisionno, to_orgName(a.empId) orgName, a.jobDs, ");
			query.append("a.mkempid, to_empkname(a.mkempid) mkEmpKName, '"+empId+"' as apEmpId, to_empKName('"+empId+"') apEmpKName, ");
			query.append("to_char(a.confirmDtm,'YYYY-MM-DD HH24:mi:ss') confirmDtm , to_char(a.lastDtm,'YYYY-MM-DD') lastDtm, a.statusflag,to_statusnm(a.statusflag) as statusname ");
			query.append("from EMPJOB a ");
			query.append("where a.statusflag = 'JB0' ");
			query.append("and a.empId IN (select distinct empId from approval where highEmpId ='");query.append(empId);query.append("') ");			
			query.append("order by a.lastdtm desc , a.statusFlag desc ");

			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			System.out.println(query.toString());
			rs = DataBaseUtil.executeSQL(con,stmt,query.toString());
			returns = (HJobDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.HJobDTO",rs);

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
	
	public HJobDTO selectHJob(String empId, String revisionNo, String status)throws Exception{
		Connection con=null;
		Statement stmt = null;
		ResultSet rs = null;
		HJobDTO returns = new HJobDTO();
		try{
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			//타인의 직무표 복사를 위해 타인의 직무표 한건을 선택할 수 있는 기능을 쿼리에 주기 위한 subQuery
			StringBuffer query0 = new StringBuffer();
			query0.append("select /*+index_desc(a xpkempjob)*/ revisionno from empjob a where empid = '");
			query0.append(empId);
			query0.append("' and statusFlag='JC0' and rownum = 1 ");
			
			//EMPJOB 개인직무표
			StringBuffer query = new StringBuffer();			
			query.append("select a.empId, to_empkname(a.empId) empKName, to_orgname(a.empId) orgName, a.revisionNo, a.mkEmpId, to_empkname(a.mkEmpId) mkEmpKName, ");
			query.append("a.jobDs, to_char(a.lastDtm,'YYYY-MM-DD HH24:mi:ss') lastDtm, a.apEmpId, to_empkname(a.apEmpId) empKName, ");
			query.append("to_char(a.confirmDtm,'YYYY-MM-DD HH24:mi:ss') confirmDtm, a.statusFlag, ");
			query.append("b.jobAgency, to_empKName(b.jobAgency) jobAgencyName ");
			query.append("from EMPJOB a, EMPALL b ");
			query.append("where a.empId = '");query.append(empId);query.append("' ");
			if(revisionNo.equals("")){  //타인직무표 복사기능을 사용하는 경우
				query.append("and a.revisionNo = (");query.append(query0.toString());query.append(") ");
			}else{                         //본인것에서 직무표 하나 선택하는 경우
				query.append("and a.revisionNo = '");query.append(revisionNo);query.append("' ");			
			}
			query.append("and a.empId = b.empId ");
			System.out.println(query.toString());
			rs = DataBaseUtil.executeSQL(con,stmt,query.toString());			
			returns = (HJobDTO)DataBaseUtil.moveToEntity("com.wms.beans.dto.HJobDTO",rs);

			//EMPJOBLIST 개인직무코드들
			StringBuffer query1 = new StringBuffer();
			query1.append("select a.jobCd, to_jobname(a.jobCd) jobName, a.jobTitle, a.yearCnt, a.unitUseHM, a.yearTotHM ");
			query1.append("from EMPJOBLIST a ");
			query1.append("where a.empId = '");query1.append(empId);query1.append("' ");
			if(revisionNo.equals("")){  //타인직무표 복사기능을 사용하는 경우
				query1.append("and a.revisionNo = (");query1.append(query0.toString());query1.append(") ");
			}else{                         //본인것에서 직무표 하나 선택하는 경우
				query1.append("and a.revisionNo = '");query1.append(revisionNo);query1.append("' ");			
			}
			query1.append("order by a.jobCd asc ");
			System.out.println(query1.toString());
			rs = DataBaseUtil.executeSQL(con,stmt,query1.toString());
			returns.hJobCdDTOs= (HJobCdDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.HJobCdDTO",rs);

			//합동직무자들
			StringBuffer query2 = new StringBuffer();
			query2.append("select distinct a.coEmpId, to_empkname(a.coEmpId) coEmpKName ");
			query2.append("from COJOB a, approval b, approval c ");
			query2.append("where a.empId = '");query2.append(empId);query2.append("' ");
			if(revisionNo.equals("")){  //타인직무표 복사기능을 사용하는 경우
				query2.append("and a.revisionNo = (");query2.append(query0.toString());query2.append(") ");
			}else{                         //본인것에서 직무표 하나 선택하는 경우
				query2.append("and a.revisionNo = '");query2.append(revisionNo);query2.append("' ");			
			}
			query2.append("and a.empid = b.empid ");
			query2.append("and a.coempid = c.empid ");
			query2.append("and b.highempid = c.highempid ");
			query2.append("order by coEmpKName desc ");
			System.out.println(query2.toString());	
			rs = DataBaseUtil.executeSQL(con,stmt,query2.toString());
			returns.coJobDTOs = (CoJobDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.CoJobDTO",rs);

		}catch(Exception e){
				throw new Exception("승인된 개인직무표가 존재하지 않습니다. 다른사람을 선택하시기 바랍니다.");
				//Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
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

	public boolean deleteHJob(String empId, String revisionNo, String status) throws Exception{
		Connection con=null;
		Statement stmt = null;
		ResultSet rs = null;
		boolean returns = false;
		try{
			con = DataBaseUtil.getConnection();
			con.setAutoCommit(false);
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

			//합동직무자 삭제
			StringBuffer query1 = new StringBuffer();
			query1.append("delete COJOB where empId = '");query1.append(empId);query1.append("' ");
			query1.append("and revisionNo = '");query1.append(revisionNo);query1.append("' ");
			System.out.println(query1.toString());
			stmt.addBatch(query1.toString());
			//직무코드들 삭제
			StringBuffer query2 = new StringBuffer();
			query2.append("delete EMPJOBLIST where empId = '");query2.append(empId);query2.append("' ");
			query2.append("and revisionNo = '");query2.append(revisionNo);query2.append("' ");
			System.out.println(query2.toString());
			stmt.addBatch(query2.toString());
			//직무표 삭제
			StringBuffer query3 = new StringBuffer();
			query3.append("delete EMPJOB where empId = '");query3.append(empId);query3.append("' ");
			query3.append("and revisionNo = '");query3.append(revisionNo);query3.append("' ");
			System.out.println(query3.toString());
			stmt.addBatch(query3.toString());
			
			int[] result=stmt.executeBatch();
			if(result!=null){
				if(result.length>0){
					returns = true;
				}else{
					throw new Exception("직무표 삭제시 장애가 발생되었습니다. 현재 직무표 상태를 확인하시기 바랍니다.");
				}
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

	public boolean updateHJob(HJobDTO dto, int selectedIndex) throws Exception{
		Connection con=null;
		Statement stmt = null;
		ResultSet rs = null;
		boolean returns = false;
		try{
			con = DataBaseUtil.getConnection();
			con.setAutoCommit(false);
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			//직무표 update
			StringBuffer query1 = new StringBuffer();
			query1.append("update EMPJOB set ");
			//query1.append("mkEmpId = '");query1.append(dto.mkEmpId);query1.append("', ");
			query1.append("jobDs = '");query1.append(dto.jobDs);query1.append("', ");
			query1.append("lastDtm = sysdate , ");
			//query1.append("apEmpId = '");query1.append(dto.jobDs);query1.append("', ");
			//query1.append("confirmDtm = sysdate , ");
			query1.append("statusFlag = '");query1.append(dto.statusFlag);query1.append("' ");
			query1.append("where empId = '");query1.append(dto.empId);query1.append("' " );
			query1.append("and revisionNo = '");query1.append(dto.revisionNo);query1.append("' ");
			System.out.println(query1.toString());
			stmt.addBatch(query1.toString());

			//합동직무자 delete 후 insert
			//delete
			StringBuffer query2 = new StringBuffer();
			query2.append("delete COJOB where empId = '");query2.append(dto.empId);query2.append("' ");
			query2.append("and revisionNo = '");query2.append(dto.revisionNo);query2.append("' ");
			System.out.println(query2.toString());
			stmt.addBatch(query2.toString());
			//insert
			for(int i=0 ; i<dto.coJobDTOs.length ; i++){
				System.out.println("합동직무자 insert "+i+"::"+selectedIndex+"::"+dto.coJobDTOs[i].coEmpId);
				System.out.println("insert into COJOB(empId,revisionNo,coEmpId) values('"+dto.empId+"','"+dto.revisionNo+"','"+dto.coJobDTOs[i].coEmpId+"')" );
				stmt.addBatch("insert into COJOB(empId,revisionNo,coEmpId) values('"+dto.empId+"','"+dto.revisionNo+"','"+dto.coJobDTOs[i].coEmpId+"')" );				
			}

			//직무코드들 delete 후 insert
			//delete
			StringBuffer query3 = new StringBuffer();
			query3.append("delete EMPJOBLIST where empId = '");query3.append(dto.empId);query3.append("' ");
			query3.append("and revisionNo = '");query3.append(dto.revisionNo);query3.append("' ");
			System.out.println(query3.toString());
			stmt.addBatch(query3.toString());
			//insert
			for(int i=0 ; i<=selectedIndex ; i++){
				if(!((dto.hJobCdDTOs[i].jobCd).equals("")||dto.hJobCdDTOs[i].jobCd==null)){
					System.out.println("직무코드들 insert "+i+"::"+selectedIndex);
					System.out.println("insert into EMPJOBLIST values('"+dto.empId+"','"+dto.revisionNo+"','"+dto.hJobCdDTOs[i].jobCd+"','"+dto.hJobCdDTOs[i].jobTitle+"','"+dto.hJobCdDTOs[i].yearCnt+"','"+dto.hJobCdDTOs[i].unitUseHM+"','"+dto.hJobCdDTOs[i].yearTotHM+"') ");
					stmt.addBatch("insert into EMPJOBLIST values('"+dto.empId+"','"+dto.revisionNo+"','"+dto.hJobCdDTOs[i].jobCd+"','"+dto.hJobCdDTOs[i].jobTitle+"','"+dto.hJobCdDTOs[i].yearCnt+"','"+dto.hJobCdDTOs[i].unitUseHM+"','"+dto.hJobCdDTOs[i].yearTotHM+"') ");				
				}
			}

			//직무대행자 저장
			StringBuffer query4 = new StringBuffer();
			query4.append("update EMPALL set ");
			query4.append("jobAgency = '");query4.append(dto.jobAgency);query4.append("' ");
			query4.append("where empId = '");query4.append(dto.empId);query4.append("' ");
			System.out.println(query4.toString());
			stmt.addBatch(query4.toString());
			
			//대표직무 저장
			StringBuffer query5 = new StringBuffer();
			query5.append("update EMPALL set ");
			query5.append("jobDs = '");query5.append(dto.jobDs);query5.append("' ");
			query5.append("where empId = '");query5.append(dto.empId);query5.append("' ");
			System.out.println(query5.toString());
			stmt.addBatch(query5.toString());
			

			int[] result=stmt.executeBatch();
			if(result!=null){
				if(result.length>0){
					System.out.println(result.length);
					returns = true;
				}else{
					throw new Exception("직무표 저장시 장애가 발생되었습니다. 현재 직무표 상태를 확인하시기 바랍니다.");
				} 
			}else{
				throw new Exception("int[] result 에서 에러발생");
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

	public boolean createHJob(HJobDTO dto,int selectedIndex, String userEmpId)throws Exception{
		Connection con=null;
		Statement stmt = null;
		ResultSet rs = null;
		boolean returns = false;
		try{
			con = DataBaseUtil.getConnection();
			con.setAutoCommit(false);
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			//직무표 insert
			StringBuffer subQuery = new StringBuffer();
			subQuery.append("select max(to_number(revisionNo)) as revisionNo from EMPJOB where empId ='");subQuery.append(dto.empId);subQuery.append("' ");
			//subQuery.append("select /*+INDEX_DESC(EMPJOB XPKEMPJOB)*/ revisionno from EMPJOB where empId = '");subQuery.append(dto.empId);subQuery.append("' ");
			//subQuery.append("and rownum = 1 ");
			//상위업무보고자를 찾는 쿼리
			StringBuffer subQuery1 = new StringBuffer();
			subQuery1.append("select distinct highempid  from approval where empid = '");subQuery1.append(dto.empId);subQuery1.append("' ");

			StringBuffer query1 = new StringBuffer();
			query1.append("insert into EMPJOB values('");query1.append(dto.empId);query1.append("', (nvl((");
			query1.append(subQuery);query1.append("),0)+1), '");
			query1.append(userEmpId);query1.append("','"); //신규생성시만 mkEmpId 를 넣어줌
			query1.append(dto.jobDs);query1.append("',sysdate,(");query1.append(subQuery1);query1.append("),'','");
			query1.append(dto.statusFlag);query1.append("') ");
			System.out.println(query1.toString());
			stmt.addBatch(query1.toString());
			//합동직무자 insert 
			StringBuffer query2 = new StringBuffer();
			for(int i=0 ; i<dto.coJobDTOs.length ; i++){
				System.out.println("합동직무자 insert "+i+"::"+selectedIndex+"::"+dto.coJobDTOs[i].coEmpId);
				System.out.println("insert into COJOB(empId,revisionNo,coEmpId) values('"+dto.empId+"',("+subQuery.toString()+"),'"+dto.coJobDTOs[i].coEmpId+"')" );
				stmt.addBatch("insert into COJOB(empId,revisionNo,coEmpId) values('"+dto.empId+"',("+subQuery.toString()+"),'"+dto.coJobDTOs[i].coEmpId+"')" );				
			}

			//직무코드들 insert
			StringBuffer query3 = new StringBuffer();
			for(int i=0 ; i<=selectedIndex ; i++){
				if(!((dto.hJobCdDTOs[i].jobCd).equals("")||dto.hJobCdDTOs[i].jobCd==null)){
					System.out.println("직무코드들 insert "+i+"::"+selectedIndex);
					System.out.println("insert into EMPJOBLIST values('"+dto.empId+"',("+subQuery.toString()+"),'"+dto.hJobCdDTOs[i].jobCd+"','"+dto.hJobCdDTOs[i].jobTitle+"','"+dto.hJobCdDTOs[i].yearCnt+"','"+dto.hJobCdDTOs[i].unitUseHM+"','"+dto.hJobCdDTOs[i].yearTotHM+"') ");
					stmt.addBatch("insert into EMPJOBLIST values('"+dto.empId+"',("+subQuery.toString()+"),'"+dto.hJobCdDTOs[i].jobCd+"','"+dto.hJobCdDTOs[i].jobTitle+"','"+dto.hJobCdDTOs[i].yearCnt+"','"+dto.hJobCdDTOs[i].unitUseHM+"','"+dto.hJobCdDTOs[i].yearTotHM+"') ");				
				}
			}

			//직무대행자 저장
			StringBuffer query4 = new StringBuffer();
			query4.append("update EMPALL set ");
			query4.append("jobAgency = '");query4.append(dto.jobAgency);query4.append("' ");
			query4.append("where empId = '");query4.append(dto.empId);query4.append("' ");
			System.out.println(query4.toString());
			stmt.addBatch(query4.toString());
			
			//대표직무 저장
			StringBuffer query5 = new StringBuffer();
			query5.append("update EMPALL set ");
			query5.append("jobDs = '");query5.append(dto.jobDs);query5.append("' ");
			query5.append("where empId = '");query5.append(dto.empId);query5.append("' ");
			System.out.println(query5.toString());
			stmt.addBatch(query5.toString());
			
			int[] result=stmt.executeBatch();
			if(result!=null){
				if(result.length>0){
					returns = true;
				}else{
					throw new Exception("직무표 저장시 장애가 발생되었습니다. 현재 직무표 상태를 확인하시기 바랍니다.");
				} 
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

	public EmpDTO[] coJobSearch(String empId) throws Exception{
		Connection con=null;
		Statement stmt = null;
		ResultSet rs = null;
		EmpDTO[] returns = null;
		try{
			StringBuffer query = new StringBuffer();			
			query.append("select distinct empid, to_empkname(empid) empkname, to_orgname(empid) empOrgName ");
			query.append("from approval ");
			query.append("where highempid IN( ");
			query.append("select highempid from approval ");
			query.append("where empid='");query.append(empId);query.append("' ) ");
			query.append("minus ");
			query.append("select distinct empid, to_empkname(empid) empkname, to_orgname(empid) empOrgName ");
			query.append("from approval where empid='");query.append(empId);query.append("' ");

			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			System.out.println(query.toString());
			rs = DataBaseUtil.executeSQL(con,stmt,query.toString());
			returns = (EmpDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.EmpDTO",rs);

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

	public Hashtable teamJobSearch(String empId) throws Exception{
		Connection con=null;
		Statement stmt = null;
		ResultSet rs = null;
		Hashtable returns = new Hashtable();
		ArrayList col1 = new ArrayList();
		ArrayList col2 = new ArrayList();

		try{
			StringBuffer query = new StringBuffer();
			query.append("select jobcd, to_jobname(jobcd) jobname from teamjob ");
			query.append("where substr(orgcd,1,2) IN (select substr(orgcd,1,2) from EMPALL where empid='");
			query.append(empId);query.append("') ");

			con = DataBaseUtil.getConnection();
		    stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		    System.out.println(query.toString());
		    rs = stmt.executeQuery(query.toString());
			int i = 0;
			while(rs.next()){
				//System.out.println(i+"::"+rs.getString(1));
				col1.add(i,rs.getString(1));
				col2.add(i,rs.getString(2));
				i++;
		    }
			System.out.println("-----"+col1.size());
		 	returns.put("code",col1);
			returns.put("name",col2);		
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
	//직무표 승인
	public boolean confirmHJob(HJobDTO dto, int selectedIndex, String userEmpId) throws Exception{
		Connection con=null;
		Statement stmt = null;
		ResultSet rs = null;
		boolean returns = false;
		try{
			con = DataBaseUtil.getConnection();
			con.setAutoCommit(false);
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			//해당 개인의 직무표 저장			
			StringBuffer query1 = new StringBuffer();
			query1.append("update EMPJOB set ");
			//query1.append("mkEmpId = '");query1.append(dto.mkEmpId);query1.append("', ");
			query1.append("jobDs = '");query1.append(dto.jobDs);query1.append("', ");
			//query1.append("lastDtm = sysdate , ");
			query1.append("apEmpId = '");query1.append(userEmpId);query1.append("', ");
			query1.append("confirmDtm = sysdate , ");
			query1.append("statusFlag = 'JC0' ");
			query1.append("where empId = '");query1.append(dto.empId);query1.append("' " );
			query1.append("and revisionNo = '");query1.append(dto.revisionNo);query1.append("' ");
			System.out.println(query1.toString());
			stmt.addBatch(query1.toString());

			//합동직무자 delete 후 insert
			//delete
			StringBuffer query2 = new StringBuffer();
			query2.append("delete COJOB where empId = '");query2.append(dto.empId);query2.append("' ");
			query2.append("and revisionNo = '");query2.append(dto.revisionNo);query2.append("' ");
			System.out.println(query2.toString());
			stmt.addBatch(query2.toString());
			//insert
			for(int i=0 ; i<dto.coJobDTOs.length ; i++){
				System.out.println("합동직무자 insert "+i+"::"+selectedIndex+"::"+dto.coJobDTOs[i].coEmpId);
				System.out.println("insert into COJOB(empId,revisionNo,coEmpId) values('"+dto.empId+"','"+dto.revisionNo+"','"+dto.coJobDTOs[i].coEmpId+"')" );
				stmt.addBatch("insert into COJOB(empId,revisionNo,coEmpId) values('"+dto.empId+"','"+dto.revisionNo+"','"+dto.coJobDTOs[i].coEmpId+"')" );				
			}

			//직무코드들 delete 후 insert
			//delete
			StringBuffer query3 = new StringBuffer();
			query3.append("delete EMPJOBLIST where empId = '");query3.append(dto.empId);query3.append("' ");
			query3.append("and revisionNo = '");query3.append(dto.revisionNo);query3.append("' ");
			System.out.println(query3.toString());
			stmt.addBatch(query3.toString());
			//insert
			for(int i=0 ; i<=selectedIndex ; i++){
				if(!((dto.hJobCdDTOs[i].jobCd).equals("")||dto.hJobCdDTOs[i].jobCd==null)){
					System.out.println("직무코드들 insert "+i+"::"+selectedIndex);
					System.out.println("insert into EMPJOBLIST values('"+dto.empId+"','"+dto.revisionNo+"','"+dto.hJobCdDTOs[i].jobCd+"','"+dto.hJobCdDTOs[i].jobTitle+"','"+dto.hJobCdDTOs[i].yearCnt+"','"+dto.hJobCdDTOs[i].unitUseHM+"','"+dto.hJobCdDTOs[i].yearTotHM+"') ");
					stmt.addBatch("insert into EMPJOBLIST values('"+dto.empId+"','"+dto.revisionNo+"','"+dto.hJobCdDTOs[i].jobCd+"','"+dto.hJobCdDTOs[i].jobTitle+"','"+dto.hJobCdDTOs[i].yearCnt+"','"+dto.hJobCdDTOs[i].unitUseHM+"','"+dto.hJobCdDTOs[i].yearTotHM+"') ");				
				}
			}

			//직무대행자 저장
			StringBuffer query4 = new StringBuffer();
			query4.append("update EMPALL set ");
			query4.append("jobAgency = '");query4.append(dto.jobAgency);query4.append("' ");
			query4.append("where empId = '");query4.append(dto.empId);query4.append("' ");
			System.out.println(query4.toString());
			stmt.addBatch(query4.toString());

			//합동직무자들 처리
			if(dto.coJobDTOs.length>=1){
				//합동직무자들의 직무표 생성 1
				/*
				StringBuffer subQuery = new StringBuffer();
				//subQuery.append("select max(revisionNo) as revisionNo from EMPJOB where empId ='");subQuery.append(dto.coJobDTOs[i].coEmpId);subQuery.append("' ");
				for(int i=0 ; i<dto.coJobDTOs.length ; i++){
					System.out.println("합동직무자들 insert "+i+"::"+dto.coJobDTOs.length);
					subQuery = new StringBuffer();
					subQuery.append("select max(revisionNo) as revisionNo from EMPJOB where empId ='");subQuery.append(dto.coJobDTOs[i].coEmpId);subQuery.append("' ");
					System.out.println("insert into EMPJOB values('"+dto.coJobDTOs[i].coEmpId+"',("+subQuery.toString()+")+1,'"
								+dto.empId+"','"+dto.jobDs+"','','"+userEmpId+"',sysdate,'JC0')");
					stmt.addBatch("insert into EMPJOB values('"+dto.coJobDTOs[i].coEmpId+"',("+subQuery.toString()+")+1,'"
								+dto.empId+"','"+dto.jobDs+"','','"+userEmpId+"',sysdate,'JC0')");
				}
				*/

				//합동직무자들의 직무표 생성 2
				StringBuffer aquery1 = new StringBuffer();
				aquery1.append("insert into EMPJOB ( ");
				aquery1.append("select distinct b.coempid empId, nvl(max(to_number(a.revisionno))over(partition by b.coempid)+1,1)  revisionno, '");aquery1.append(dto.empId);aquery1.append("' mkEmpId,'");
				aquery1.append(dto.jobDs);aquery1.append("' jobDs,sysdate lastDtm,'");aquery1.append(userEmpId);aquery1.append("' apEmpId,sysdate confirmDtm,'JC0' statusFlag ");
				aquery1.append("from EMPJOB a, ( ");
				aquery1.append("				select coEmpId, empId from COJOB where empId = '");aquery1.append(dto.empId);aquery1.append("' ");
				aquery1.append("and revisionno='");aquery1.append(dto.revisionNo);aquery1.append("') b ");
				aquery1.append("where a.empId(+) = b.coEmpId ) ");
				System.out.println(aquery1.toString());
				stmt.addBatch(aquery1.toString());

				//합동직무자들의 직무코드 생성 1
				//insert
				/*
				for(int j=0 ; j<=selectedIndex ; j++){
					for(int i=0 ; i<dto.coJobDTOs.length ; i++){
						subQuery = new StringBuffer();
						subQuery.append("select max(revisionNo) as revisionNo from EMPJOB where empId ='");subQuery.append(dto.coJobDTOs[i].coEmpId);subQuery.append("' ");
						System.out.println("직무코드들 insert "+i+"::"+selectedIndex);
						System.out.println("insert into EMPJOBLIST values('"+dto.coJobDTOs[i].coEmpId+"',("+subQuery.toString()+"),'"+dto.hJobCdDTOs[j].jobCd+"','"+dto.hJobCdDTOs[j].jobTitle+"','"+dto.hJobCdDTOs[j].yearCnt+"','"+dto.hJobCdDTOs[j].unitUseHM+"','"+dto.hJobCdDTOs[j].yearTotHM+"') ");
						stmt.addBatch("insert into EMPJOBLIST values('"+dto.coJobDTOs[i].coEmpId+"',("+subQuery.toString()+"),'"+dto.hJobCdDTOs[j].jobCd+"','"+dto.hJobCdDTOs[j].jobTitle+"','"+dto.hJobCdDTOs[j].yearCnt+"','"+dto.hJobCdDTOs[j].unitUseHM+"','"+dto.hJobCdDTOs[j].yearTotHM+"') ");
					}
				}
				*/
				//합동직무자들의 직무코드 생성 2
				StringBuffer aquery2 = new StringBuffer();
				aquery2.append("insert into EMPJOBLIST ( ");
				aquery2.append("select distinct b.coempid empId, nvl(max(to_number(a.revisionno))over(partition by b.coempid),1)  revisionno, ");
				aquery2.append("	   b.jobcd, b.jobtitle, b.yearcnt, b.unitusehm, b.yeartothm  ");
				aquery2.append("from EMPJOB a , ( ");
				aquery2.append("select y.coempid,x.jobcd, x.jobtitle, x.yearcnt, x.unitusehm, x.yeartothm ");
				aquery2.append("from EMPJOBLIST x, COJOB y ");
				aquery2.append("where x.empid=y.empid ");
				aquery2.append("and x.revisionNo = y.revisionNo ");
				aquery2.append("and x.empid='");aquery2.append(dto.empId);aquery2.append("' ");
				aquery2.append("and x.revisionno='");aquery2.append(dto.revisionNo);aquery2.append("' ");
				aquery2.append(") b ");
				aquery2.append("where a.empid(+)=b.coempid )");
				System.out.println(aquery2.toString());
				stmt.addBatch(aquery2.toString());

				/*
				query6.append("insert into EMPJOBLIST ( ");
				query6.append("select b.coempid empId, a.revisionno,a.jobcd, a.jobTitle, a.yearcnt, a.unitusehm, a.yeartothm ");
				query6.append("from EMPJOBLIST a, COJOB b ");
				query6.append("where a.empid='");query6.append(dto.empId);query6.append("' ");
				query6.append("and a.revisionno = '");query6.append(dto.revisionNo);query6.append("' ");
				query6.append("and a.empid=b.empid ");
				query6.append("and a.revisionno=b.revisionno )");
				System.out.println(query6.toString());
				stmt.addBatch(query6.toString());
				*/
				//합동직무자들의 대표직무/직무대행자 처리
				StringBuffer query7 = new StringBuffer();
				query7.append("update EMPALL set jobds='");query7.append(dto.jobDs);query7.append("', ");
				query7.append("jobAgency='");query7.append(dto.empId);query7.append("' ");
				query7.append("where empid IN ( ");
				query7.append("select coempid from COJOB ");
				query7.append("where empid='");query7.append(dto.empId);query7.append("' "); 
				query7.append("and revisionno='");query7.append(dto.revisionNo);query7.append("') ");
				System.out.println(query7.toString());
				stmt.addBatch(query7.toString());
			}

			int[] result=stmt.executeBatch();
			if(result!=null){
				if(result.length>0){
					System.out.println(result.length);
					returns = true;
				}else{
					throw new Exception("직무표 승인시 장애가 발생되었습니다. 현재 직무표 상태를 확인하시기 바랍니다.");
				} 
			}else{
				throw new Exception("int[] result 에서 에러발생");
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