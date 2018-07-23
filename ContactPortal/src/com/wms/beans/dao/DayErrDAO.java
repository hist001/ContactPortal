/*************************************************************
*	파 일 명  : DayErrDAO.java
*	작성일자  : 2004/06/22
*	작 성 자  : 
*	내    용  : 에러보고 관련 Data Access하는 클래스
*************************************************************/
package com.wms.beans.dao;
import java.io.*;
import java.util.*; 
import java.sql.*;
import com.wms.fw.db.DataBaseUtil;
import com.wms.beans.dto.*;
import com.wms.beans.*;
import com.wms.fw.*;
import com.wms.fw.servlet.Box;
import com.wms.fw.Utility;
import com.wms.fw.util.DateUtil;

public class DayErrDAO implements IDayErr
{
	//일일보고 미승인 검색
	public DayErrDTO[] delayConfDayReportList(String empId, String role) throws Exception{
		Connection con = null;
	    Statement stmt = null;
	    ResultSet rs   = null;
		DayErrDTO[] returns= null;
		try{
			StringBuffer query = new StringBuffer();
			if(!role.equals("admin1")){
				query.append("select x.reempid, x.reportdt, to_empkname(x.reempid) reEmpKName, x.plempid apEmpId, to_empkname(x.plempid) apEmpKName, ");
				query.append("       x.apdtm, x.statusflag,to_statusnm(x.statusflag) AS statusname ,get_sum_mh(x.reportDt,x.reempid) as totalMh ");
				query.append("from dailyreport x ");
				query.append("where x.reportdt < to_char(sysdate,'YYYYMMDD') ");
				//query.append("and   X.statusFlag IN ('미승인','추후작성승인대기','미작성승인대기') ");
				query.append("and   X.statusFlag IN ('WCC') ");//승인지연
				query.append("and   x.plempid = '");query.append(empId);query.append("' ");
				query.append("order by x.reportdt desc ");
			}else if(role.equals("admin1")){
				query.append("select x.reempid, x.reportdt, to_empkname(x.reempid) reEmpKName, x.plempid apEmpId, to_empkname(x.plempid) apEmpKName, ");
				query.append("       x.apdtm, x.statusflag,to_statusnm(x.statusflag) AS statusname ,get_sum_mh(x.reportDt,x.reempid) as totalMh ");
				query.append("from dailyreport x ");
				query.append("where x.reportdt < to_char(sysdate,'YYYYMMDD') ");
				//query.append("and   X.statusFlag IN ('미승인','추후작성승인대기','미작성승인대기') ");
				query.append("and   X.statusFlag IN ('WCC') ");//미보고,작성완료,승인지연
				query.append("order by x.reportdt desc ");				
			}
		
			System.out.println(query.toString());
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs=DataBaseUtil.executeSQL(con,stmt,query.toString());

			returns = (DayErrDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.DayErrDTO",rs);
			
		}catch(Exception e){
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
		}
		finally{
			try{
				if(stmt!=null)stmt.close();
				if(rs!=null)rs.close();
				if(con!=null)con.close();
			}catch(Exception e){
				Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
			}
		}
		com.wms.fw.Utility.fixNullAndTrimAll(returns);
		return returns;
	}

	//일일보고 미작성 한건 선택
	/*
	public DayErrDTO delayConfDayReport(String reportDt, String reEmpId) throws Exception{
		Connection con = null;
	    Statement stmt = null;
	    ResultSet rs   = null;
		DayErrDTO returns = new DayErrDTO();;
		try{
			StringBuffer query = new StringBuffer();
			query.append("select x.reempid, x.reportdt, to_empkname(x.reempid) reEmpKName, x.plempid apEmpId, to_empkname(x.plempid) apEmpKName, ");
			query.append("       x.apdtm, x.statusflag ");
			query.append("from dailyreport x ");
			query.append("where x.reportdt < to_char(sysdate,'YYYYMMDD') ");
			query.append("and   X.statusFlag IN ('미승인','추후작성승인대기','미작성승인대기') ");
			//query.append("and   X.statusFlag IN ('승인완료','작성중') ");
			query.append("and   x.plempid = '");query.append(reEmpId);query.append("' ");
			query.append("order by x.reportdt desc ");
					
			System.out.println(query.toString());
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs=DataBaseUtil.executeSQL(con,stmt,query.toString());

			returns = (DayErrDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.DayErrDTO",rs);
			
		}catch(Exception e){
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
		}
		finally{
			try{
				if(stmt!=null)stmt.close();
				if(rs!=null)rs.close();
				if(con!=null)con.close();
			}catch(Exception e){
				Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
			}
		}
		com.wms.fw.Utility.fixNullAndTrimAll(returns);
		return returns;
	}
	*/

	//전체 미작성 검색
	public DayErrDTO[] dayErrSearch(Box box) throws Exception{
		Connection con = null;
	    Statement stmt = null;
	    ResultSet rs   = null;
		DayErrDTO[] returns= null;	
		try{
			/*
			String orgCd=Utility.rDelete(box.get("orgCd").trim(),'0');
			StringBuffer query = new StringBuffer();
			query.append("select a.reportDt, a.reempid,to_empKname(a.reempid) as reempkname , ");
			query.append("a.apempid , to_empkname(a.apempid ) as apempkname , ");			
			query.append("to_char(a.apdtm,'yyyy-mm-dd hh24:mi:ss') apdtm , a.statusflag,to_statusnm(a.statusflag) AS statusname,b.orgcd , to_orgcdname(b.orgcd) orgname  ");
			query.append("from   dailyreport a, emp b ");
			query.append("where b.orgcd like '"+orgCd+"%' ");
			query.append("and b.empKName like '%"+box.get("empKName").trim()+"%' ");
			query.append("and b.empid=a.reempid ");
			if(!box.get("plEmpId").equals(""))
			query.append("and a.plEmpId='"+box.get("plEmpId").trim()+"'");
			if(!box.get("startDt").equals("")){
				query.append("and a.reportdt >='"+Utility.replace(box.get("startDt").trim(),"-")+"' ");
				query.append("and a.reportdt <='"+Utility.replace(box.get("endDt").trim(),"-")+"'  ");
			}else{
				query.append("and a.reportdt >='"+DateUtil.getTodayString("")+"' ");
				query.append("and a.reportdt <='"+DateUtil.getTodayString("")+"'  ");
			}
			String statusFlag = (box.get("statusFlag").equals("") || box.get("statusFlag").equals("%"))?"ALL":box.get("statusFlag").trim();
			if(statusFlag.equals("WNO")){
				query.append("and a.statusflag in ('WNT' ,'WNO') ");
			}else if(statusFlag.equals("WES")){
				query.append("and a.statusflag in ('WET' , 'WES') ");
			}else{
				query.append("and a.statusflag like decode('"+statusFlag+"','ALL','%','"+statusFlag+"') ");
			}
			query.append("order by a.reportDt desc , b.orgcd, reempkname asc");
			*/
			String orgCd=Utility.rDelete(box.get("orgCd").trim(),'0');
			String startDt = (box.get("startDt")==null||box.get("startDt").equals(""))?Utility.replace(DateUtil.getTodayString(""),"-"):Utility.replace(box.get("startDt").trim(),"-");
			String endDt = (box.get("endDt")==null||box.get("endDt").equals(""))?Utility.replace(DateUtil.getTodayString(""),"-"):Utility.replace(box.get("endDt").trim(),"-");
			String statusFlag = (box.get("statusFlag").equals("") || box.get("statusFlag").equals("%"))?"ALL":box.get("statusFlag").trim();
			StringBuffer query = new StringBuffer();
			query.append("select a.reportDt, a.reempid, to_orgname(a.reempid) orgname,b.orgcd as orgcd , to_empKname(a.reempid) as reempkname , ");
			query.append("a.apempid , to_empkname(a.apempid ) as apempkname , ");			
			query.append("to_char(a.apdtm,'yyyy-mm-dd hh24:mi:ss') apdtm , a.statusflag,to_statusnm(a.statusflag) AS statusname,b.orgcd , to_orgcdname(b.orgcd) orgname,  ");
			query.append(" rank() over(partition by 0 order by reportdt desc,b.orgcd,reempid )no ");
 			query.append("from   dailyreport a, emp b ");
			query.append("where b.empid=a.reempid ");
			if(!orgCd.equals(""))
				query.append("and b.orgcd like '"+orgCd+"%' ");
			if(!box.get("empKName").trim().equals(""))
				query.append("and b.empKName like '%"+box.get("empKName").trim()+"%' ");
			query.append("and a.reportdt >='"+startDt+"' ");
			query.append("and a.reportdt <='"+endDt+"'  ");
			if(statusFlag.equals("WNO")){
				query.append("and a.statusflag in ('WNT' ,'WNO') ");
			}else if(statusFlag.equals("WES")){
				query.append("and a.statusflag in ('WET' , 'WES') ");
			}			
			/*Query 성능을 위해 수정 2005/03/03
			else{
				query.append("and a.statusflag like decode('"+statusFlag+"','ALL','%','"+statusFlag+"') ");
			}
			*/
			else if(!statusFlag.equals("ALL")){
				query.append("and a.statusflag in ('"+statusFlag+"') ");
			}
			//query.append("order by a.reportDt desc , b.orgcd, reempkname asc");
		
			System.out.println(query.toString());
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs=DataBaseUtil.executeSQL(con,stmt,query.toString());

			returns = (DayErrDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.DayErrDTO",rs);
			
		}catch(Exception e){
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
		}
		finally{
			try{
				if(stmt!=null)stmt.close();
				if(rs!=null)rs.close();
				if(con!=null)con.close();
			}catch(Exception e){
				Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
			}
		}
		return returns;
	}

	//기간별 list
	public DayErrDTO[] dayErrSearchPage(Box box,int pageNum,int pageSize) throws Exception{

		Connection con = null; 
	    Statement stmt = null;
	    ResultSet rs   = null;
		DayErrDTO[] returns= null;	
		try{
			System.out.println("dayErrSearchPage");
			//String orgCd=Utility.rDelete(box.get("orgCd").trim(),'0');
			String orgCd= box.get("orgCd");
			String startDt = (box.get("startDt")==null||box.get("startDt").equals(""))?Utility.replace(DateUtil.addDay(DateUtil.getTodayString(),-1),"-"):Utility.replace(box.get("startDt").trim(),"-");
			String endDt = (box.get("endDt")==null||box.get("endDt").equals(""))?Utility.replace(DateUtil.addDay(DateUtil.getTodayString(),-1),"-"):Utility.replace(box.get("endDt").trim(),"-");
			String statusFlag = (box.get("statusFlag").equals("") || box.get("statusFlag").equals("%"))?"ALL":box.get("statusFlag").trim();

			StringBuffer query = new StringBuffer();
			//자료량의 증가에 따른, rank() 로 수정, order by 삭제
			query.append("select * from(  \n");
			query.append("select REPORTDT, REEMPID,  REEMPKNAME,  APEMPID, APEMPKNAME,   \n");
			query.append("APDTM,  STATUSFLAG,   STATUSNAME,  ORGCD, ORGNAME,   ROWNUM RN  \n");
			query.append("from( \n"); 
			query.append("select a.reportDt, a.reempid,to_empKname(a.reempid) as reempkname , \n");
			query.append("a.apempid , to_empkname(a.apempid ) as apempkname ,  \n");			
			query.append("to_char(a.apdtm,'yyyy-mm-dd hh24:mi:ss') apdtm , a.statusflag,to_statusnm(a.statusflag) AS statusname, \n");
			query.append("to_orgnocd(b.orgcd)as orgcd , ");
			//query.append("to_orgcdname(b.orgcd) orgname,  \n");
			query.append("to_orgnoname(b.orgcd) orgname  \n");
			//query.append("rank() over(partition by 0 order by reportdt desc,b.orgcd,reempid )no \n");
 			query.append("from   dailyreport a, emp b 	\n");
			query.append("where b.empid=a.reempid 	\n");
			if(!orgCd.equals(""))
				query.append("and b.orgcd like '"+orgCd+"%' ");
			if(!box.get("empKName").trim().equals(""))
				query.append("and b.empKName like '%"+box.get("empKName").trim()+"%' ");
			query.append("and a.reportdt >='"+startDt+"' ");
			query.append("and a.reportdt <='"+endDt+"'  ");
			if(statusFlag.equals("WNO")){
				query.append("and a.statusflag in ('WNT' ,'WNO') ");
			}else if(statusFlag.equals("WES")){
				query.append("and a.statusflag in ('WET' , 'WES') ");
			}
			/*Query 성능을 위해 수정 2005/03/03
			else{
				query.append("and a.statusflag like decode('"+statusFlag+"','ALL','%','"+statusFlag+"') ");
			}
			*/
			else if(statusFlag.equals("E%")){
				query.append("and a.statusflag like 'E%' ");
			}
			else if(!statusFlag.equals("ALL")){
				query.append("and a.statusflag in ('"+statusFlag+"') ");
			}
			query.append("order by  reportdt desc, orgcd, reempid )A  \n ");
			query.append(") \n  ");
			query.append("where RN between ");
			query.append((pageNum-1)*pageSize+1);
			query.append(" and ");
			query.append(pageNum*pageSize);		
			

			System.out.println(query.toString());

			StringBuffer query1 = new StringBuffer();
 			query1.append(" select count(*) cnt  ");
			query1.append("from   dailyreport a, emp b ");

			query1.append("where b.empid=a.reempid ");
			if(!orgCd.equals(""))
				query1.append("and b.orgcd like '"+orgCd+"%' ");
			if(!box.get("empKName").trim().equals(""))
				query1.append("and b.empKName like '%"+box.get("empKName").trim()+"%' ");

			query1.append("and a.reportdt >='"+startDt+"' ");
			query1.append("and a.reportdt <='"+endDt+"'  ");
			if(statusFlag.equals("WNO")){
				query1.append("and (a.statusflag = 'WNT' or a.statusflag='WNO') ");
			}else if(statusFlag.equals("WES")){
				query1.append("and (a.statusflag = 'WET' or a.statusflag='WES') ");
			}			
			/*Query 성능을 위해 수정 2005/03/03
			else{
				query1.append("and a.statusflag like decode('"+statusFlag+"','ALL','%','"+statusFlag+"') ");
			}
			*/
			else if(statusFlag.equals("E%")){
				query1.append("and a.statusflag like 'E%' ");
			}
			else if(!statusFlag.equals("ALL")){
				query1.append("and a.statusflag in ('"+statusFlag+"') ");
			}
			System.out.println(query1.toString());

			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

			rs=DataBaseUtil.executeSQL(con,stmt,query.toString());
			returns = (DayErrDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.DayErrDTO",rs);
			if(returns!=null){
				rs=DataBaseUtil.executeSQL(con,stmt,query1.toString());
				if(rs.next()){
					returns[0].totCnt = rs.getInt("cnt");
				}
			}

		}catch(Exception e){
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
		}
		finally{
			try{
				if(stmt!=null)stmt.close();
				if(rs!=null)rs.close();
				if(con!=null)con.close();
			}catch(Exception e){
				Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
			}
		}
		return returns;
	}

	public DayErrDTO[] dayErrSearchPage4TL(Box box,int pageNum,int pageSize) throws Exception{

		Connection con = null;  
	    Statement stmt = null;
	    ResultSet rs   = null;
		DayErrDTO[] returns= null;	
		try{ 
			System.out.println("dayErrSearchPage4TL");
			//String orgCd=Utility.rDelete(box.get("orgCd").trim(),'0');
			String orgCd= box.get("orgCd"); 
			String startDt = (box.get("startDt")==null||box.get("startDt").equals(""))?Utility.replace(DateUtil.addDay(DateUtil.getTodayString(),-8),"-"):Utility.replace(box.get("startDt").trim(),"-");
			String endDt = (box.get("endDt")==null||box.get("endDt").equals(""))?Utility.replace(DateUtil.addDay(DateUtil.getTodayString(),-1),"-"):Utility.replace(box.get("endDt").trim(),"-");
			String statusFlag = (box.get("statusFlag").equals("") || box.get("statusFlag").equals("%"))?"ALL":box.get("statusFlag").trim();

			StringBuffer query = new StringBuffer();
			//자료량의 증가에 따른, rank() 로 수정, order by 삭제
			query.append("select * from(  \n");
			query.append("select REPORTDT, REEMPID,  REEMPKNAME,  APEMPID, APEMPKNAME,   \n");
			query.append(" (SELECT TRUNC((SUM(HH)*60+SUM(MM))/60)||'시간'||MOD(SUM(HH)*60+SUM(MM),60)||'분' FROM DAILYREPORTDETAIL WHERE REPORTDT=A.REPORTDT AND REEMPID=A.REEMPID) totalMh , ");
			query.append("APDTM,  STATUSFLAG,   STATUSNAME,  ORGCD,  ORGNAME,   ROWNUM RN  \n");
			query.append("from( \n");
			query.append("select a.reportDt, a.reempid,to_empKname(a.reempid) as reempkname , \n");
			query.append("a.apempid , to_empkname(a.apempid ) as apempkname , \n");			
			query.append("to_char(a.apdtm,'yyyy-mm-dd hh24:mi:ss') apdtm , a.statusflag,to_statusnm(a.statusflag) AS statusname, \n");
			query.append("to_orgnocd(b.orgcd)as orgcd , ");
			//query.append("to_orgcdname(b.orgcd) orgname,  \n");
			query.append("to_orgnoname(b.orgcd) orgname  \n");
			//query.append("rank() over(partition by 0 order by reportdt desc,b.orgcd,reempid )no \n");
 			query.append("from   dailyreport a, emp b 	\n");
			query.append("where b.empid=a.reempid 	\n");
//			query.append(" and b.empid in (select empid from empall where GET_ORGDIV(ORGCD,'T') =(select orgcd from empall where empid='"+box.get("_DATA")+"' and eduty='T') and wenddt > to_char(sysdate,'yyyymmdd') union all select empid from empall where orgcd=(select orgcd from empall where empid ='"+box.get("_DATA")+"' and eduty='E') and wenddt >to_char(sysdate,'yyyymmdd') ");
			query.append("and b.empid in (	\n");
			query.append("SELECT EMPID 	\n");
			query.append("   FROM EMPALL	\n");
			query.append("   WHERE ORGCD IN	\n");
			query.append("	(SELECT ORG_NO	\n");
			query.append("	FROM ORGHISTORY	\n");
			query.append("	WHERE '"+endDt+"' BETWEEN STARTDT AND ENDDT	\n");
			query.append("	START WITH   ORG_NO IN (SELECT ORGCD	\n");
			query.append("							FROM EMPALL	\n");
			query.append("							WHERE EMPID ='"+box.get("_DATA")+"'	\n");
			query.append("							UNION	\n");
			query.append("							SELECT ORGCD	\n");
			query.append("							FROM   EMPALL	\n");
			query.append("							WHERE APRVEMPID='"+box.get("_DATA")+"'	\n");
			query.append("	)	\n");
			query.append("	CONNECT BY PRIOR TO_CHAR(ORG_NO)=HIGHORGCD	\n");
			query.append("	)				\n");
//			query.append("				 )	\n");
			if(box.get("_DATA").equals("20020042")){ // 김재훈 예외처리 
				query.append(" union all select empid from empall where empid in ('20000089','20000082','20000091','20000112','20000110','20000106','20000119','20000120','20000154','20000155','20000114','20000118','20000123','20000239') ");
			}else if(box.get("_DATA").equals("20000108")){			
				query.append(" union all select empid from empall where empid in ('20000057','20000116')");
				query.append(" union all SELECT EMPID FROM EMPALL WHERE ORGCD =(SELECT ORGCD FROM EMPALL WHERE EMPID='20000108') AND EMPID !='20000108' AND WENDDT >= TO_CHAR(SYSDATE,'YYYYMMDD') ");
			}else if(box.get("_DATA").equals("20000046")){// 신영철부장
				query.append(" union select empid from empall where empid in ('20000076','20000058','20020008','20000052','20000036','20000084','20000100','20000064','20020037','20020042') ");
				query.append(" union all select empid from empall where empid in ('20000089','20000082','20000091','20000112','20000110','20000106','20000119','20000120','20000154','20000155','20000114','20000118','20000123','20000239') ");
			} 
			query.append(")");
			query.append(" and b.empid !='"+box.get("_DATA")+"'" );		
			if(!orgCd.equals("")) 
				query.append("and b.orgcd like '"+orgCd+"%' ");
			if(!box.get("empKName").trim().equals(""))
				query.append("and b.empKName like '%"+box.get("empKName").trim()+"%' ");
			query.append("and a.reportdt >='"+startDt+"' ");
			query.append("and a.reportdt <='"+endDt+"'  ");
			if(statusFlag.equals("WNO")){
				query.append("and a.statusflag in ('WNT' ,'WNO') ");
			}else if(statusFlag.equals("WES")){
				query.append("and a.statusflag in ('WET' , 'WES') ");
			}
			/*Query 성능을 위해 수정 2005/03/03
			else{
				query.append("and a.statusflag like decode('"+statusFlag+"','ALL','%','"+statusFlag+"') ");
			}
			*/ 
			else if(statusFlag.equals("E%")){
				query.append("and a.statusflag like 'E%' ");
			}
			else if(!statusFlag.equals("ALL")){
				query.append("and a.statusflag in ('"+statusFlag+"') ");
			}
			query.append("order by  reportdt desc, orgcd, reempkname )A  \n "); 
			query.append(") \n  ");
			query.append("where RN between ");
			query.append((pageNum-1)*pageSize+1);
			query.append(" and ");
			query.append(pageNum*pageSize);		
			

			System.out.println(query.toString());

			StringBuffer query1 = new StringBuffer();
 			query1.append(" select count(*) cnt  ");
			query1.append("from   dailyreport a, emp b ");

			query1.append("where b.empid=a.reempid ");
			
//			query1.append(" and b.empid in (select empid from empall where GET_ORGDIV(ORGCD,'T') =(select orgcd from empall where empid='"+box.get("_DATA")+"' and eduty='T') and wenddt > to_char(sysdate,'yyyymmdd') union all select empid from empall where orgcd=(select orgcd from empall where empid ='"+box.get("_DATA")+"' and eduty='E') and wenddt >to_char(sysdate,'yyyymmdd') ");
			query1.append("and b.empid in (	\n");
			query1.append("SELECT EMPID 	\n");
			query1.append("   FROM EMPALL	\n");
			query1.append("   WHERE ORGCD IN	\n");
			query1.append("	(SELECT ORG_NO	\n");
			query1.append("	FROM ORGHISTORY	\n");
			query1.append("	WHERE '"+endDt+"' BETWEEN STARTDT AND ENDDT	\n");
			query1.append("	START WITH   ORG_NO IN (SELECT ORGCD	\n");
			query1.append("							FROM EMPALL	\n");
			query1.append("							WHERE EMPID ='"+box.get("_DATA")+"'	\n");
			query1.append("							UNION	\n");
			query1.append("							SELECT ORGCD	\n");
			query1.append("							FROM   EMPALL	\n");
			query1.append("							WHERE APRVEMPID='"+box.get("_DATA")+"'	\n");
			query1.append("	)	\n");
			query1.append("	CONNECT BY PRIOR TO_CHAR(ORG_NO)=HIGHORGCD	\n");
			query1.append("	)				\n");
			
			if(box.get("_DATA").equals("20020042")){ // 김재훈 예외처리 
				query1.append(" union all select empid from empall where empid in ('20000089','20000082','20000091','20000112','20000110','20000106','20000119','20000120','20000154','20000155','20000114','20000118','20000123','20000239') ");
			}else if(box.get("_DATA").equals("20000108")){  
				query1.append(" union all select empid from empall where empid in ('20000057','20000116') ");
				query1.append(" union all SELECT EMPID FROM EMPALL WHERE ORGCD =(SELECT ORGCD FROM EMPALL WHERE EMPID='20000108') AND EMPID !='20000108' AND WENDDT >= TO_CHAR(SYSDATE,'YYYYMMDD') ");
			}else if(box.get("_DATA").equals("20000046")){//신영철부장
				query1.append(" union select empid from empall where empid in ('20000076','20000058','20020008','20000052','20000036','20000084','20000100','20000064','20020037','20020042') ");
				query1.append(" union all select empid from empall where empid in ('20000089','20000082','20000091','20000112','20000110','20000106','20000119','20000120','20000154','20000155','20000114','20000118','20000123','20000239') ");
			}    
			query1.append(")");	
			query1.append(" and b.empid !='"+box.get("_DATA")+"'" );			
			if(!orgCd.equals(""))
				query1.append("and b.orgcd like '"+orgCd+"%' ");
			if(!box.get("empKName").trim().equals(""))
				query1.append("and b.empKName like '%"+box.get("empKName").trim()+"%' ");

			query1.append("and a.reportdt >='"+startDt+"' ");
			query1.append("and a.reportdt <='"+endDt+"'  ");
			if(statusFlag.equals("WNO")){
				query1.append("and (a.statusflag = 'WNT' or a.statusflag='WNO') ");
			}else if(statusFlag.equals("WES")){
				query1.append("and (a.statusflag = 'WET' or a.statusflag='WES') ");
			}			
			/*Query 성능을 위해 수정 2005/03/03
			else{
				query1.append("and a.statusflag like decode('"+statusFlag+"','ALL','%','"+statusFlag+"') ");
			}
			*/
			else if(statusFlag.equals("E%")){
				query1.append("and a.statusflag like 'E%' ");
			}
			else if(!statusFlag.equals("ALL")){
				query1.append("and a.statusflag in ('"+statusFlag+"') ");
			}
			System.out.println(query1.toString());

			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

			rs=DataBaseUtil.executeSQL(con,stmt,query.toString());
			returns = (DayErrDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.DayErrDTO",rs);
			if(returns!=null){
				rs=DataBaseUtil.executeSQL(con,stmt,query1.toString());
				if(rs.next()){
					returns[0].totCnt = rs.getInt("cnt");
				}
			} 

		}catch(Exception e){
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
		}
		finally{
			try{
				if(stmt!=null)stmt.close();
				if(rs!=null)rs.close();
				if(con!=null)con.close();
			}catch(Exception e){
				Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
			}
		}
		return returns;
	}

	
	//기간별
	public DayErrDTO[] dayErrMiSearch(Box box) throws Exception{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs   = null;
		DayErrDTO[] returns= null;	
		try{
			System.out.println("dayErrMiSearch");
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

			String orgCd=Utility.rDelete(box.get("orgCd").trim(),'0');
			//기본값으로 하루전을 setting...
			String startDt = (box.get("startDt")==null||box.get("startDt").equals(""))?Utility.replace(DateUtil.getTodayString(DateUtil.add(DateUtil.getToday(),0,0,-1)),"-"):Utility.replace(box.get("startDt").trim(),"-");
			String endDt = (box.get("endDt")==null||box.get("endDt").equals(""))?Utility.replace(DateUtil.getTodayString(DateUtil.add(DateUtil.getToday(),0,0,-1)),"-"):Utility.replace(box.get("endDt").trim(),"-");
			String statusFlag = (box.get("statusFlag").equals("") || box.get("statusFlag").equals("%"))?"and (a.statusflag = 'WNN' or a.statusflag='WDS')":"and a.statusflag='"+box.get("statusFlag").trim()+"'";
			
			StringBuffer query = new StringBuffer();
			query.append("select a.reportDt, a.reempid,to_empKname(a.reempid) as reempkname , ");
			query.append("a.apempid , to_empkname(a.apempid ) as apempkname , ");		
			query.append("to_char(a.apdtm,'yyyy-mm-dd hh24:mi:ss') apdtm , a.statusflag,to_statusnm(a.statusflag) AS statusname,b.orgcd , ");
			//query.append("to_orgcdname(b.orgcd) orgname, ");
			query.append("to_orgnoname(b.orgcd) orgname, ");
			query.append(" rank() over(partition by 0 order by reportdt desc,b.orgcd,reempid )no ");
			query.append("from dailyreport a, emp b ");
			query.append("where b.empid=a.reempid ");
			if(!orgCd.equals(""))
				query.append("and b.orgcd like '"+orgCd+"%' ");
			if(!box.get("empKName").trim().equals(""))
				query.append("and b.empKName like '%"+box.get("empKName").trim()+"%' ");
			query.append("and a.reportdt >='"+startDt+"' ");
			query.append("and a.reportdt <='"+endDt+"'  ");
			query.append(statusFlag);
			//query.append(" order by a.reportDt desc, b.orgcd, reempkname asc ");
			System.out.println("기간별::\n"+query.toString());

			rs=DataBaseUtil.executeSQL(con,stmt,query.toString());
			returns = (DayErrDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.DayErrDTO",rs);

		}catch(Exception e){
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
		}
		finally{
			try{
				if(stmt!=null)stmt.close();
				if(rs!=null)rs.close();
				if(con!=null)con.close();
			}catch(Exception e){
				Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
			}
		}
		com.wms.fw.Utility.fixNullAndTrimAll(returns);
		return returns;
	}

	//일일미작성조회-페이징
	public DayErrDTO[] dayErrMiSearch(Box box,int pageNum,int pageSize) throws Exception{
		Connection con = null;
	    Statement stmt = null;
	    ResultSet rs   = null;
		DayErrDTO[] returns= null;	
		try{
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

			String orgCd=Utility.rDelete(box.get("orgCd").trim(),'0');
			//기본값으로 하루전을 setting...
			String startDt = (box.get("startDt")==null||box.get("startDt").equals(""))?Utility.replace(DateUtil.getTodayString(DateUtil.add(DateUtil.getToday(),0,0,-1)),"-"):Utility.replace(box.get("startDt").trim(),"-");
			String endDt = (box.get("endDt")==null||box.get("endDt").equals(""))?Utility.replace(DateUtil.getTodayString(DateUtil.add(DateUtil.getToday(),0,0,-1)),"-"):Utility.replace(box.get("endDt").trim(),"-");
			String statusFlag = (box.get("statusFlag").equals("") || box.get("statusFlag").equals("%"))?"and (a.statusflag = 'WNN' or a.statusflag='WDS')":"and a.statusflag='"+box.get("statusFlag").trim()+"'";
			
			StringBuffer query = new StringBuffer();
			query.append("select * from ( ");
			query.append("select a.reportDt, a.reempid,to_empKname(a.reempid) as reempkname , ");
			query.append("a.apempid , to_empkname(a.apempid ) as apempkname , ");		
			query.append("to_char(a.apdtm,'yyyy-mm-dd hh24:mi:ss') apdtm , a.statusflag,to_statusnm(a.statusflag) AS statusname, ");
			//query.append("b.orgcd , to_orgcdname(b.orgcd) orgname, ");
			query.append(" to_orgnocd(b.orgcd) as orgcd, to_orgnoname(b.orgcd) as orgname, ");
			query.append(" rank() over(partition by 0 order by reportdt desc,b.orgcd,reempid )no ");
			query.append("from   dailyreport a, emp b ");
			query.append("where b.empid=a.reempid ");
			if(!orgCd.equals(""))
				query.append("and b.orgcd like '"+orgCd+"%' ");
			if(!box.get("empKName").trim().equals(""))
				query.append("and b.empKName like '%"+box.get("empKName").trim()+"%' ");
			query.append("and a.reportdt >='"+startDt+"' ");
			query.append("and a.reportdt <='"+endDt+"'  ");
			query.append(statusFlag);
			//query.append(" order by a.reportDt desc, b.orgcd, reempkname asc ");
			query.append(")  ");
			query.append("where no between ");
			query.append((pageNum-1)*pageSize+1);
			query.append(" and ");
			query.append(pageNum*pageSize);		
			System.out.println("일일미작성조회-페이징 ::\n"+query.toString());

			rs=DataBaseUtil.executeSQL(con,stmt,query.toString());
			returns = (DayErrDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.DayErrDTO",rs);

			StringBuffer query1 = new StringBuffer();
 			query1.append(" select count(*) cnt  ");
			query1.append("from   dailyreport a, emp b ");
			query1.append("where b.empid=a.reempid ");
			if(!orgCd.equals(""))
				query1.append("and b.orgcd like '"+orgCd+"%' ");
			if(!box.get("empKName").trim().equals(""))
				query1.append("and b.empKName like '%"+box.get("empKName").trim()+"%' ");
			query1.append("and a.reportdt >='"+startDt+"' ");
			query1.append("and a.reportdt <='"+endDt+"'  ");
			query1.append(statusFlag);
			System.out.println(query1.toString());
			if(returns!=null){
				rs=DataBaseUtil.executeSQL(con,stmt,query1.toString());
				if(rs.next()){
					returns[0].totCnt=rs.getInt("cnt");
				}
			}
		
		}catch(Exception e){
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
		}
		finally{
			try{
				if(stmt!=null)stmt.close();
				if(rs!=null)rs.close();
				if(con!=null)con.close();
			}catch(Exception e){
				Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
			}
		}
		com.wms.fw.Utility.fixNullAndTrimAll(returns);
		return returns;
	}
    //미작성 등록을 위한 조회
	public DayErrDTO[] dayErrApSearch(Box box,String empId) throws Exception{
		Connection con = null;
	    Statement stmt = null;
	    ResultSet rs   = null;
		DayErrDTO[] returns= null;	
		try{
			StringBuffer query = new StringBuffer();

			query.append("select a.reportDt, a.reempid,to_empKname(a.reempid) as reempkname , ");
			query.append("a.apempid , to_empkname(a.apempid ) as apempkname , ");
			query.append("to_char(a.apdtm,'yyyy-mm-dd hh24:mi:ss') apdtm , a.statusflag,to_statusnm(a.statusflag) AS statusname ,b.orgcd , to_orgcdname(b.orgcd) orgname    ");
			query.append("from   dailyreport a, emp b  ");
			query.append("where b.empid=a.reempid ");
			query.append("and a.reempid='"+empId+"' and a.reempid=b.empid ");
			/*기간에 관계없는 검색으로 인한 기능삭제
			if(!box.get("startDt").equals("")){
				query.append("and a.reportdt >='"+Utility.replace(box.get("startDt").trim(),"-")+"' ");
				query.append("and a.reportdt <='"+Utility.replace(box.get("endDt").trim(),"-")+"'  ");
			}else{
				query.append("and a.reportdt >='"+Utility.replace(DateUtil.getTodayString(DateUtil.add(DateUtil.getToday(),0,0,-1)),"-")+"' ");
				query.append("and a.reportdt <='"+DateUtil.getTodayString("")+"'  ");
			}
			*/
			query.append("and a.reportdt <to_char(sysdate,'yyyymmdd')  ");
			query.append("and a.statusflag in ('WNN','WDS','WEO','WES','E13','WCN') ");//미보고,추후작성,작성완료,작성중,국내출장

			query.append("union  ");
			query.append("select a.reportDt, a.reempid,to_empKname(a.reempid) as reempkname , ");
			query.append("a.apempid , to_empkname(a.apempid) as apempkname , ");
			query.append("to_char(a.apdtm,'yyyy-mm-dd hh24:mi:ss') apdtm , a.statusflag,to_statusnm(a.statusflag) AS statusname ,b.orgcd , to_orgcdname(b.orgcd) orgname   ");
			query.append("from   dailyreport a, emp b  ");
			query.append("where a.plempid='"+empId+"' and b.empid=a.reempid ");
			/*
			if(!box.get("startDt").equals("")){
				query.append("and a.reportdt >='"+Utility.replace(box.get("startDt").trim(),"-")+"' ");
				query.append("and a.reportdt <='"+Utility.replace(box.get("endDt").trim(),"-")+"'  ");
			}else{
				query.append("and a.reportdt >='"+Utility.replace(DateUtil.getTodayString(DateUtil.add(DateUtil.getToday(),0,0,-1)),"-")+"' ");
				query.append("and a.reportdt <='"+DateUtil.getTodayString("")+"'  ");
			}
			*/
			query.append("and a.reportdt <to_char(sysdate,'yyyymmdd')  ");
			query.append("and a.statusflag in ('WNN','WDS','WEO','WES','E13','WCN') ");//미보고,추후작성,작성완료,작성중,국내출장


			query.append("order by 1 desc  ");

			System.out.println(query.toString());
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs=DataBaseUtil.executeSQL(con,stmt,query.toString());

			returns = (DayErrDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.DayErrDTO",rs);

			
		}catch(Exception e){
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
		}
		finally{
			try{
				if(stmt!=null)stmt.close();
				if(rs!=null)rs.close();
				if(con!=null)con.close();
			}catch(Exception e){
				Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
			}
		}
		com.wms.fw.Utility.fixNullAndTrimAll(returns);
		return returns;
	}

	public boolean updateDaily(DayErrDTO dto)throws Exception{
		Connection con = null;
		Statement stmt=null;
		boolean returns=false;//결과값 저장하는 변수
		try{
		
			con = DataBaseUtil.getConnection();
			con.setAutoCommit(false);
			stmt=con.createStatement();
	
			String term="0";
			if(dto.statusFlag.equals("WDS"))
                 term="1" ;
			String dailyRpt="update dailyreport set statusflag='"+dto.statusFlag+"' where reportDt='"+Utility.replace(dto.reportDt.trim(),"-")+"' and reempid='"+dto.reEmpId+"' ";
            String dailyRptLog="insert into dailyreportlog(reportdt,reempid,rewritedtm,completeflag) values("
			                   +"'"+Utility.replace(dto.reportDt.trim(),"-")+"','"+dto.reEmpId+"',sysdate+"+term+",'"+dto.statusFlag+"')";

			stmt.addBatch(dailyRpt);			
			stmt.addBatch(dailyRptLog);
			System.out.println(dailyRpt);
			System.out.println(dailyRptLog);

			int[] result=stmt.executeBatch();		
            con.commit();  
			returns= true;
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
	
		return returns;
	}

	public StatusDTO[] searchStatus() throws Exception{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs   = null;
		StatusDTO[] returns= null;
		try{
			StringBuffer query = new StringBuffer();
			query.append("select * from status ");
			query.append("where statuscd like 'W%' ");
			query.append("and statuscd not in ('WNT','WET')  ");
			query.append("order by statuscd  ");
		
			System.out.println(query.toString());
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs=DataBaseUtil.executeSQL(con,stmt,query.toString());
		
			returns = (StatusDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.StatusDTO",rs);			
		}catch(Exception e){
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
		}
		finally{
			try{
				if(stmt!=null)stmt.close();
				if(rs!=null)rs.close();
				if(con!=null)con.close();
			}catch(Exception e){
				Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
			}
		}
		com.wms.fw.Utility.fixNullAndTrimAll(returns);
		return returns;
	}

	public StatusDTO[] searchStatusList(String statusCd, String statusName) throws Exception{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs   = null;
		StatusDTO[] returns= null;
		try{
			StringBuffer query = new StringBuffer();
			query.append("select * from status ");
			query.append("where statuscd like '");
			query.append(statusCd);
			query.append("%' and statusNm like '%");
			query.append(statusName);
			query.append("%' order by statuscd  ");
			System.out.println(query.toString());

			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs=DataBaseUtil.executeSQL(con,stmt,query.toString());
		
			returns = (StatusDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.StatusDTO",rs);			
		}catch(Exception e){
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
		}
		finally{
			try{
				if(stmt!=null)stmt.close();
				if(rs!=null)rs.close();
				if(con!=null)con.close();
			}catch(Exception e){
				Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
			}
		}
		com.wms.fw.Utility.fixNullAndTrimAll(returns);
		return returns;
	}
}
