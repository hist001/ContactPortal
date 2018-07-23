/*************************************************************
*	파 일 명  : GroupEmpDAO.java
*	작성일자  : 2004/06/22
*	작 성 자  : 
*	내    용  : 직원 관리 관련 Data Access를 하는 클래스
*************************************************************/
package com.wms.beans.dao;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.wms.fw.db.DataBaseUtil;
import com.wms.beans.dto.*;
import com.wms.fw.*;
public class GroupEmpDAO implements IGroupEmp
{   //직원 저장
	public boolean save(EmpDTO[] dto)throws Exception{
		return true;
	}
	//조직에 속한 직원 조회 
	public EmpDTO[] empSearch(String orgCd)throws Exception{
		Statement stmt=null;
		Connection con = null;
		ResultSet rs = null;
		EmpDTO[] returns = null;		
		String appendSql="";
	
		try{

			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			String query ="select a.orgcd as emporgcd,to_orgname(b.empid) as emporgname,b.* "
                         +"from approval a, emp b "
                         +"where  a.orgcd='"+orgCd+"'  " 
                         +"and a.useflag='Y' "
                         +"and a.empid=b.empid "
                         +"and b.retflag='N'  "
                         +"union  select a.orgcd as emporgcd,to_orgname(b.empid) as emporgname,b.* "
                         +"from approval a, emp b "
                         +"where  a.orgcd='"+orgCd+"'  " 
						 +"and a.useflag='Y' " 
                         +"and a.managerflag='G' "
                         +"and a.highempid=b.empid " 
                         +"and b.retflag='N' ";  
		
			System.out.println(query);
			rs=DataBaseUtil.executeSQL(con,stmt,query);
			returns=(EmpDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.EmpDTO",rs);
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
    //직원 조회
	public EmpDTO[]  empSearch(EmpDTO dto)throws Exception{
		Statement stmt=null;
		Connection con = null;
		ResultSet rs = null;
		EmpDTO[] returns = null;				
		String orgCd=Utility.rDelete(dto.empOrgCd.trim(),'0');	
		try{
			String sql="select A.*, to_orgName(A.empId) empOrgName from emp A ";
			String pre=" where ";
			if(dto.empId!=null&&!dto.empId.trim().equals("")){
				sql+=pre+" empId='"+dto.empId.trim()+"' ";
			    pre=" and ";
			}
			if(dto.empKName!=null&&!dto.empKName.trim().equals("")){
				//sql+=pre+" empKName like '%"+dto.empKName.trim()+"%' ";
			    //pre=" and ";
			}
			if(dto.empOrgCd!=null&&!dto.empOrgCd.trim().equals("")){
				//sql+=pre+" empOrgCd like '"+orgCd.trim()+"%'";
			    //pre=" and ";
			}
			if(dto.retFlag!=null&&!dto.retFlag.trim().equals("")){
				sql+=pre+" retFlag='"+dto.retFlag.trim()+"'";
			}
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
	
			System.out.println(sql);
			rs=DataBaseUtil.executeSQL(con,stmt,sql);
			returns=(EmpDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.EmpDTO",rs);
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
	//role 설정을 위한 login 처리
	//040804. 허대영
	public UserInfo  empLogin(String empId)throws Exception{
		Statement stmt=null;
		Connection con = null;
		ResultSet  rs  = null;
		UserInfo returns = null;		
		
		String strEmpid[] =empId.split(",");
		String strPw ="";
		
		if(strEmpid.length==2){
			strPw =strEmpid[1];
		}

		try{ 
			StringBuffer query = new StringBuffer();
			query.append("select c.drType, ");
			query.append("a.EMPID,a.EMPKNAME, ");
			query.append("a.ORGCD empOrgNo, ");
			query.append("c.ORGCD empOrgCd, ");
			query.append("to_orgnoname(a.orgcd)empOrgName, ");
			query.append("ETYPE,to_cdname('ET',etype) eTypeName, ");
			query.append("WSTARTDT,WENDDT, ");
			query.append("EDUTY,to_cdname('ED',eDuty) eDutyName, ");
			query.append("EGRADE,to_cdname('EA',eGrade) eGradeName,JOBDS, ");
			query.append("JOBAGENCY,to_empkname(JOBAGENCY) JOBAGENCYName, ");
			query.append("MAINJOBCD,TECHGRADE,");
			//query.append("eGroup, to_cdname('EG',eGroup) eGroupName,");
			query.append("APRVEMPID,APRVEMPID highEmpId, ");
			query.append("to_empkname(APRVEMPID) highEmpKName, ");
			query.append("REPORTYN, ");
			query.append("SUBEMPID,SUBEMPID apEmpId, ");
			query.append("to_empkname(subEmpId) apEmpKName, ");
			query.append("GET_EMPDUTY(subEmpId) apEDuty, to_cdname('ED',GET_EMPDUTY(subEmpId)) adEDutyName,");
			query.append("EMPMEMO, ");
			//부문조회 
//			query.append("(SELECT G.ORG_NO FROM ORG G ");
//			query.append("WHERE G.ORGDIV='D' ");
//			query.append("START WITH  G.ORG_NO=a.ORGCD ");
//			query.append("CONNECT BY PRIOR G.HIGHORGCD=G.ORG_NO)  AS ORG_NO_DIV_D,");
			query.append(" GET_ORGDIV(a.ORGCD,'D') AS ORG_NO_DIV_D,");
			query.append("aclrole role ");
			query.append("from  ");
			query.append("empAll a,  acl b, org c ");
			query.append("where ");
			query.append("a.empid = b.empid(+) ");
			query.append("and a.orgcd = c.org_no ");
			query.append("and (a.empid='");
			query.append(empId);
			query.append("' 	  or (a.id='");
			query.append(strEmpid[0]);
			query.append("' and a.password='");
			query.append(strPw);
			query.append("'))");

			System.out.println("role::0 \n "+query.toString());
			
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs=DataBaseUtil.executeSQL(con,stmt,query.toString());
			System.out.println("role:1: \n "+rs);

			//ApprovalDTO[] dtos=(ApprovalDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.ApprovalDTO",rs);
			returns=(UserInfo)DataBaseUtil.moveToEntity("com.wms.beans.dto.UserInfo",rs);
			System.out.println("role:2: \n "+returns);
			if(returns==null)
				return null;
			rs.close();
			//USER 에게 등록된 VIEW GROUP 검색
			StringBuffer viewSql=new StringBuffer(" select grpOrgCd from orgMappingId where empid= '");
			viewSql.append(empId.trim()+"'");
			
			System.out.println(viewSql.toString());
			rs=DataBaseUtil.executeSQL(con,stmt,viewSql.toString());
			//returns.approvals=dtos;
			returns.orgCds = (OrgCdDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.OrgCdDTO",rs);
			if(returns.apEmpId!=null&&!returns.apEmpId.trim().equals("")){
				returns.apKey=true;
			}
			rs.close();

			StringBuffer authSql=new StringBuffer(" select * from auth where (empId= '");
			authSql.append(empId.trim()+"'");
			authSql.append("OR empid =GET_EMPID('"+ strEmpid[0] +"','"+ strPw +"'))");
			authSql.append(" and to_char(sysdate,'yyyymmdd') between authstartDt and authEndDt ");
			System.out.println(authSql.toString());
			rs=DataBaseUtil.executeSQL(con,stmt,authSql.toString());
			Auth[] authArr = (Auth[])DataBaseUtil.moveToEntities("com.wms.beans.dto.Auth",rs);
			Hashtable auths = new Hashtable();
			returns.auths=auths;
			if(authArr!=null){				
				for(int i=0;i<authArr.length;i++){
					auths.put(authArr[i].authType,authArr[i]);
					//System.out.println("authArr["+i+"]"+authArr[i]);
				}
			}

			StringBuffer apEmpSql=new StringBuffer(" select * from empall where aprvempid = '");
			apEmpSql.append(empId.trim());
			apEmpSql.append("' and  rownum =1 ");
			System.out.println(apEmpSql.toString());
			rs=DataBaseUtil.executeSQL(con,stmt,apEmpSql.toString());
			returns.isApEmp=(rs.next())?true:false;
            rs.close();


			//System.out.println("empLogin = "+returns);
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

	//직원에 상세 조회
	public EmpDTO  empDetailSearch(String empId)throws Exception{
		Statement stmt=null;
		Connection con = null;
		ResultSet  rs  = null;
		EmpDTO returns = null;		
	
		try{
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String query ="select distinct a.empid,a.empkname,a.etype, "
						+"a.wstartdt,a.wenddt,a.egroup,a.egrade,"
						+"a.jobds,a.jobagency,a.mainjobcd,  "
						+"a.dutyflag,a.plempidflag,a.retflag, a.orgcd as emporgcd, to_orgcdname(a.orgcd) as emporgname , " 
						+"b.apempid,to_empkname(b.apempid) apempkname,b.highempid,b.dutyname,to_empKname(b.highempid) as highempkname , "
                        +"b.orgcd,b.orgname,  b.managerflag,b.apempflag,b.useflag "
						+" from emp a,approval b "
						+" where   b.empid=a.empid "
						+" and a.empid='"+empId.trim()+"' ";
						//+" order by b.orgcd desc ";
						 
			System.out.println(query);
			rs=DataBaseUtil.executeSQL(con,stmt,query);
			ApprovalDTO[] dtos=(ApprovalDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.ApprovalDTO",rs);
			returns=(EmpDTO)DataBaseUtil.moveToEntity("com.wms.beans.dto.EmpDTO",rs);
			if(returns==null)
				return null;
			returns.approvals=dtos;
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
	//사번,조직,계획자유무 조건에 대한 직원 조회
	public EmpDTO[] empSubSearch(String empId,String orgCd, int mode)throws Exception{
		Statement stmt=null;
		Connection con = null;
		ResultSet rs = null;
		EmpDTO[] returns = null;		
		String appendSql="";
		//orgCd=Utility.rDelete(orgCd,'0');
		System.out.println("GroupEmpDAO.empSubSearch("+empId+","+orgCd+","+mode+")");
		try{
			if(mode==this.SUB_PLANNER_SEARCH){//하위계획자검색요청
				appendSql=" and MANAGERFLAG in ('T','G','P','F' ) ";
			}else if(mode==this.SUB_EMP_SEARCH){//하위직원검색요청
				appendSql=" and managerFlag='Y' ";
			}
			/*2005/02/17 조직개편이후*/
			//String appendSql2=" and a.orgcd like'"+orgCd+"%'";
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String query =" select a.orgcd as emporgcd,a.orgname as emporgname, \n"
						 + "B.EMPID,B.EMPKNAME,B.ORGCD,B.ETYPE,B.EGRADE,B.WSTARTDT, \n"
						 + "B.WENDDT,B.EGROUP,B.JOBDS,B.JOBAGENCY,B.MAINJOBCD,B.DUTYFLAG, \n"
						 + "B.PLEMPIDFLAG,B.RETFLAG,B.REPORTYN \n"
						 +" from approval a, emp b where  HIGHEMPID='"+empId+"'  \n"
			             +appendSql
						 +" and useflag='Y' and a.empid=b.empid and b.retflag='N'  \n"
						 //+appendSql2
						 +" union  \n"
						 +" select a.orgcd as emporgcd,a.orgname as emporgname,  \n"
						 + "B.EMPID,B.EMPKNAME,B.ORGCD,B.ETYPE,B.EGRADE,B.WSTARTDT, \n"
						 + "B.WENDDT,B.EGROUP,B.JOBDS,B.JOBAGENCY,B.MAINJOBCD,B.DUTYFLAG, \n"
						 + "B.PLEMPIDFLAG,B.RETFLAG,B.REPORTYN  \n"
						 +" from approval a, emp b where  b.EMPID='"+empId+"'  \n"
						 +" and a.empid=b.empid and a.useflag='Y' and b.retflag='N'   \n";
			if(mode==100){
				query=" select substr(a.orgcd,0,2)||'00' as emporgcd, \n"
					 +" to_orgcdname(substr(a.orgcd,0,2)||'00') as emporgname,  \n"
					 + "B.EMPID,B.EMPKNAME,B.ORGCD,B.ETYPE,B.EGRADE,B.WSTARTDT, \n"
					 + "B.WENDDT,B.EGROUP,B.JOBDS,B.JOBAGENCY,B.MAINJOBCD,B.DUTYFLAG, \n"
					 + "B.PLEMPIDFLAG,B.RETFLAG,B.REPORTYN  \n"
					 +" from approval a, emp b  \n"
					 +"where  MANAGERFLAG in ('T','G' )  and useflag= 'Y'  \n"
					 +"and a.empid=b.empid and b.retflag='N'  \n"
					 +" union  \n"
					 +" select a.orgcd as emporgcd,a.orgname as emporgname,  \n"
					 + "B.EMPID,B.EMPKNAME,B.ORGCD,B.ETYPE,B.EGRADE,B.WSTARTDT, \n"
					 + "B.WENDDT,B.EGROUP,B.JOBDS,B.JOBAGENCY,B.MAINJOBCD,B.DUTYFLAG, \n"
					 + "B.PLEMPIDFLAG,B.RETFLAG,B.REPORTYN  \n"
					 +" from approval a, emp b where  b.EMPID='"+empId+"'  \n"
					 +" and a.empid=b.empid  and a.useflag='Y' and b.retflag='N'  \n"
					 +"order by emporgcd ";
			}	 
			System.out.println(query);
			rs=DataBaseUtil.executeSQL(con,stmt,query);
			returns=(EmpDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.EmpDTO",rs);
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
	//사번,계획자유무 조건에 대한 직원 조회
	public EmpDTO[] empSubSearch(String empId, int mode)throws Exception{
		Statement stmt=null;
		Connection con = null;
		ResultSet rs = null;
		EmpDTO[] returns = null;		
		String appendSql="";
	
		try{
			if(mode==this.SUB_PLANNER_SEARCH){//하위계획자검색요청
				appendSql=" and MANAGERFLAG in ('T','G','P','F' ) ";
			}else if(mode==this.SUB_EMP_SEARCH){//하위직원검색요청
				appendSql=" and managerFlag='Y' ";
			}
			//String appendSql2=" and a.orgcd='"+orgCd+"'";
			
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String query =" select a.orgcd as emporgcd,a.orgname as emporgname, b.* from approval a, emp b where  HIGHEMPID='"+empId+"' "
			             +appendSql
						 +" and useflag='Y' and a.empid=b.empid and b.retflag='N' "
						 +" union "
						 +" select a.orgcd  as emporgcd, a.orgname as emporgname, b.* from approval a, emp b where  b.EMPID='"+empId+"' "
						 +" and a.empid=b.empid  and a.useflag='Y'  and b.retflag='N' ";
			System.out.println(query);
			rs=DataBaseUtil.executeSQL(con,stmt,query);
			returns=(EmpDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.EmpDTO",rs);
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
	//업무보고 하위 직원 조회
	public EmpDTO[] groupSubSearch(String empId, int mode)throws Exception{
		Statement stmt=null;
		Connection con = null;
		ResultSet rs = null;
		EmpDTO[] returns = null;		
		String appendSql="";
	
		try{
			if(mode==this.SUB_PLANNER_SEARCH){//하위계획자검색요청
				appendSql=" and MANAGERFLAG in ('T','G','P','F' ) ";
			}else if(mode==this.SUB_EMP_SEARCH){//하위직원검색요청
				appendSql=" and managerFlag='Y' ";
			}
			//String appendSql2=" and a.orgcd='"+orgCd+"'";
			
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String query =" select  b.* from approval a, emp b where  HIGHEMPID='"+empId+"' "
			             +appendSql
						 +" and useflag='Y' and a.empid=b.empid and b.retflag='N' "
						 +" union "
						 +" select  b.* from approval a, emp b where  b.EMPID='"+empId+"' "
						 +" and a.empid=b.empid  and a.useflag='Y'  and b.retflag='N' order by 2";
			System.out.println(query);
			rs=DataBaseUtil.executeSQL(con,stmt,query);
			returns=(EmpDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.EmpDTO",rs);
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

	//같은 상위자에 속한 동료 직원 조회
	public EmpDTO[] empComradeSearch(String empId,String orgCd)throws Exception{
		Statement stmt=null;
		Connection con = null;
		ResultSet rs = null;
		EmpDTO[] returns = null;		
		String appendSql="";
	    orgCd=Utility.rDelete(orgCd,'0');
		orgCd=orgCd.trim();		
		try{

			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String query ="select distinct  c.*,a.orgcd as emporgcd,a.orgname as emporgname from approval a, approval b, emp c "
                         +"where   b.empid=c.empid "
                         +"and b.useflag='Y' "
                         +"and b.orgcd like '"+orgCd+"%' "
                         +"and a.highempid=b.highempid "						
                         +"and a.empid='"+empId+"' "
                         +"and a.orgcd like '"+orgCd+"%' "
						 +"and c.retflag='N'"; 			
			System.out.println(query);
			rs=DataBaseUtil.executeSQL(con,stmt,query);
			returns=(EmpDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.EmpDTO",rs);
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
	//합동 업무 보고를 위한 직원 조회
	public EmpDTO[] empUnionSearch(String empId,String orgCd,String reportDt,boolean managerKey)throws Exception{
		Statement stmt=null;
		Connection con = null;
		ResultSet rs = null;
		EmpDTO[] returns = null;		
		String appendSql="";
	    //orgCd=Utility.rDelete(orgCd,'0');
		orgCd=orgCd.trim();		
		try{
			
			System.out.println("empUnionSearch::::");
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String query="";
			
			System.out.println("managerKey::::"+ managerKey);
			if(managerKey){
			query =" select TO_ORGNOCD(a.orgcd) as emporgcd,TO_ORGNONAME(a.orgcd) as emporgname, b.* from approval a, emp b ,dailyreport c where  HIGHEMPID='"+empId+"' "
	
						 +" and useflag='Y' and a.empid=b.empid and b.retflag='N' "
						 +" and c.reportdt='"+reportDt+"' and a.empid=c.reempid "
						 //+" and c.statusflag in ('WNO','EHO','WNT','E13') "//그룹내 모든 사람들 2004/09/21
						 +" and c.statusflag not in  ('WES','WEO','WET','WCO','WCC','WCD','WCN') "//작성중,작성완료,전일작성중,승인완료,승인지연,승인완료(지연),설명요 제외
						 +" union "
						 +" select a.orgcd  as emporgcd, a.orgname as emporgname, b.* from approval a, emp b,dailyreport c where  b.EMPID='"+empId+"' "
						 +" and a.empid=b.empid and b.retflag='N'  and  a.USEFLAG='Y'  "
						 +" and c.reportdt='"+reportDt+"' and a.empid=c.reempid "
                         //+" and c.statusflag in ('WNO','EHO','WNT','E13') ";//그룹내 모든 사람들 2004/09/21
						 +" and c.statusflag not in  ('WES','WEO','WET','WCO','WCC','WCD','WCN') ";//작성중,작성완료,전일작성중,승인완료,승인지연,승인완료(지연),설명요 제외
            }else{
			query =" SELECT A.*, TO_ORGNOCD(A.ORGCD) AS EMPORGCD, " +
		     " TO_ORGNONAME(A.ORGCD) AS EMPORGNAME" +
		     " FROM EMP A, APPROVAL B, ORGHISTORY C " +
		     " WHERE A.EMPID=B.EMPID" +
		     " AND A.RETFLAG='N' "+
		     " AND B.USEFLAG='Y' "+ 
			 " AND C.ORGCD='"+orgCd+"' "+ 
			 " AND A.ORGCD=C.ORG_NO "+
			 " AND EXISTS (SELECT B.HIGHEMPID  FROM APPROVAL C WHERE B.HIGHEMPID=C.HIGHEMPID AND EMPID='"+empId+"') "+
  			 " AND TO_CHAR(SYSDATE,'YYYYMM') BETWEEN STARTDT AND ENDDT	 "; 
			}
			System.out.println(query);
			rs=DataBaseUtil.executeSQL(con,stmt,query);
			returns=(EmpDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.EmpDTO",rs);
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
	
	//승인자 조회
	public EmpDTO empApSearch(String empId)throws Exception{
		Statement stmt=null;
		Connection con = null;
		ResultSet rs = null;
		EmpDTO returns = null;		

		try{

			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String query ="select  c.*,a.orgcd as emporgcd,a.orgname as emporgname from approval a, emp c "
                         +"where   a.empid=c.empid "
                         +"and     a.apempid='"+empId+"' "
						 +"and     c.retflag='N'"; 			
			System.out.println(query);
			rs=DataBaseUtil.executeSQL(con,stmt,query);
			returns=(EmpDTO)DataBaseUtil.moveToEntity("com.wms.beans.dto.EmpDTO",rs);
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
	//같은 승인자에 속한 직원 조회
	public EmpDTO[] empSelectSearch(String empId)throws Exception{
		Statement stmt=null;
		Connection con = null;
		ResultSet rs = null;
		EmpDTO[] returns = null;		
	
		try{

			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String query ="select  c.*,a.orgcd as emporgcd,a.orgname as emporgname from approval a, emp c "
                         +"where   a.empid=c.empid "
                         +"and     a.highempid='"+empId+"' "
						 +"and     c.retflag='N'"; 			
			System.out.println(query);
			rs=DataBaseUtil.executeSQL(con,stmt,query);
			returns=(EmpDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.EmpDTO",rs);
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
	//대결자지정하기위한 승인자 수정
	public boolean apUpdate(String empId,String agentId,String agentKName)throws Exception{
		Statement stmt=null;
		Connection con = null;
		boolean returns = false;		
	
		try{
            if(agentKName==null||agentKName.trim().equals("")){
				empId=null;
			}else{
				empId="'"+empId+"'";
			}
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

			//String query ="update approval set apempid="+empId+" where empid='"+agentId+"'"; 
			String query ="update empall set subempid="+empId+" where empid='"+agentId+"'"; 
			System.out.println(query);
			if(stmt.executeUpdate(query)==1)
				returns=true;
			
		}catch(Exception e){
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));

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
	//승인자 일괄 처리를 위한 승인자들 조회
	public EmpDTO[] orgSearch(String empId,String empKName)throws Exception{
		Statement stmt=null;
		Connection con = null;
		ResultSet rs = null;
		EmpDTO[] returns = null;		
	
		try{

			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			 	
            String append="";
			if(empId!=null&&!empId.trim().equals("")){
				append=" and a.empId ='"+empId.trim()+"' ";
			}
            String query ="select distinct a.empid, a.empKName,c.orgName||' '||c.dutyname  as empOrgName from emp a,approval b ,approval c "
			             +"where a.empid=b.highempid and a.empKName like '%"+empKName.trim()+"%' and b.useflag='Y' and a.retflag='N'  "
						 +"and a.empid=c.empid "
						 +append
						 +"order by 1 , empOrgName desc";
			System.out.println(query);
			rs=DataBaseUtil.executeSQL(con,stmt,query);
			returns=(EmpDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.EmpDTO",rs);
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
	//승인자 일괄 처리를 위한 직원 검색
	public EmpDTO[] orgEmpSearch(String empId,String empKName)throws Exception{
		Statement stmt=null;
		Connection con = null;
		ResultSet rs = null;
		EmpDTO[] returns = null;		
	
		try{

			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			 	
            String append="";
			if(empId!=null&&!empId.trim().equals("")){
				append=" and a.empId ='"+empId.trim()+"' ";
			}
            String query ="select  a.empid, a.empKName from emp a where a.empKName like '%"+empKName.trim()+"%' and a.retflag='N' "			     
						 +append
						 +"order by 1 "	;					 
			System.out.println(query);
			rs=DataBaseUtil.executeSQL(con,stmt,query);
			returns=(EmpDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.EmpDTO",rs);
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
	//승인자 일괄 처리를 위한 승인자 수정
	public boolean highUpdate(String oldEmpId,String newEmpId)throws Exception{
		Statement stmt=null;
		Connection con = null;
		boolean returns = false;		
	
		try{

			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			//update empall set APRVEMPID='09301106' where APRVEMPID='20039022' and not
			//( empid ='09301106')
			String query ="update empall set APRVEMPID='"+newEmpId.trim()+"' where APRVEMPID='"+oldEmpId.trim()+"' and not ( empid ='"+newEmpId.trim()+"')"; 
			System.out.println(query);
			if(stmt.executeUpdate(query)!=0)
				returns=true;
			
		}catch(Exception e){
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));

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
    //결제선에 있는 임직원들을 조회, 
	//flag==true 자신보다 상위결재선 조회, flag==false 자신보다 하위의 결재선 조회
	public ArrayList appList(String empId,boolean flag)throws Exception{
		return appList(empId,flag,"0");
	}
	//2005/02/02 조원호 수정
    //결제선에 있는 임직원들을 조회,
	//flag==true 자신보다 상위결재선 조회, flag==false 자신보다 하위의 결재선 조회
	//level==0 전체,그외 level은 그 수준에 맞는 level를 조회
	public ArrayList appList(String empId,boolean flag,String level)throws Exception{
		Statement stmt=null;
		Connection con = null;
		ResultSet rs = null;
		ArrayList returns = null;		
	
		try{

			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			StringBuffer query = new StringBuffer();
			query.append("select a.*,a.lv||a.empOrgCd key,a.empOrgName from (");
			query.append("select distinct level lv, empid, to_empkname(empid) empkname,orgCd empOrgCd,orgCd,to_orgcdname(orgcd) empOrgName ");
			query.append("from approval a ");
			query.append("where useflag = 'Y' ");                   
			query.append("connect by ");
			if(flag)query.append(" prior ");
			query.append("a.highempid = ");
			if(!flag)query.append(" prior ");
			query.append(" a.empid ");
			query.append("start with a.empid = ");
			query.append(empId);
			query.append(" order by level desc,orgCd ");	
			query.append(") a ");
			if(!level.equals("0"))
			{query.append("where lv in (");query.append(level);query.append(")");}
			System.out.println(query.toString());
			rs=DataBaseUtil.executeSQL(con,stmt,query.toString());
	        returns=DataBaseUtil.moveToPlan("key","com.wms.beans.dto.AppListDTO","com.wms.beans.dto.EmpDTO",rs);
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
	//2005/02/24 조원호
    //사람별 View Group 조회 기능
	public OrgCdDTO[] searchOrgMappingId(String empId)throws Exception{
		Statement stmt=null;
		Connection con = null;
		ResultSet rs = null;
		OrgCdDTO[] returns = null;		
	
		try{

			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			 	

            String query ="select  grpOrgCd,to_orgCdName(grpOrgCd) grpOrgName from orgMappingId "
			             +"where empId="+empId
                         +" order by 1 ";
					
			System.out.println(query);
			rs=DataBaseUtil.executeSQL(con,stmt,query);
			returns=(OrgCdDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.OrgCdDTO",rs);
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
	public boolean registOrgMappingId(String empId,String grpOrgCd)throws Exception {
		Statement stmt=null;
		Connection con = null;
		boolean returns = false;		
	
		try{

			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

			String query ="insert into orgmappingid(empid,grporgcd) values('"+empId+"','"+grpOrgCd+"')";
			System.out.println(query);
			if(stmt.executeUpdate(query)!=0)
				returns=true;
			
		}catch(Exception e){
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));

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
	public boolean deleteOrgMappingId(String empId,String grpOrgCd)throws Exception {
		Statement stmt=null;
		Connection con = null;
		boolean returns = false;		
	
		try{

			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

			String query ="delete from orgmappingid where empid='"+empId+"' and grpOrgCd='"+grpOrgCd+"'";
			System.out.println(query);
			if(stmt.executeUpdate(query)!=0)
				returns=true;
			
		}catch(Exception e){
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));

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

}