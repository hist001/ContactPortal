/**********************************************************
*	파 일 명  : WeekJobPlanDAO.java
*	작성일자  : 2004/06/22
*	작 성 자  :
*	내    용  : 업무계획에 관련한 Data Access하는 클래스
************************************************************/
package com.wms.beans.dao;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.wms.fw.db.DataBaseUtil;
import com.wms.beans.dto.*;
import com.wms.fw.*;
public class WeekJobPlanDAO implements IWeekJobPlan
{
	public boolean save(WeekJobPlanDTO before,WeekJobPlanDTO after)throws Exception{
		return true;
	}

	public WeekJobPlanDetailDTO weekJobPlanSubSearch(WeekJobPlanDetailDTO dto ,boolean planner)throws Exception{
		Statement stmt=null;
		Connection con = null;
		WeekJobPlanDetailDTO returns = null;		
	
		try{
			String id=(planner)?"plEmpId":"reEmpId";

			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String query = " select a.*,b.*, to_empkname(B.reEmpId) as empKName,to_empkname(B.plempid) as plEmpKName,to_empkname(B.crempid) as crEmpKName "+
					" from weeklyplandetail A, weeklyplan B "+
					" where A.weekstartdt = '"+dto.weekStartDt+"' "+
					" and A.jobno = '"+dto.jobNo+"' "+
					" and A.prcsno = '"+dto.prcsNo+"' "+
					" and B."+id+" = '"+dto.reEmpId+"' "+
					" and A.weekstartdt = B.weekstartdt "+
					" and A.reEmpId = B.reEmpId "+
					" order by b.reEmpId";

			System.out.println("weekJobPlanSubSearch \n"+query);
			returns=(WeekJobPlanDetailDTO)DataBaseUtil.moveToEntity("com.wms.beans.dto.WeekJobPlanDetailDTO",DataBaseUtil.executeSQL(con,stmt,query));
			returns.plans=(WeekJobPlanDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.WeekJobPlanDTO",DataBaseUtil.executeSQL(con,stmt,query));
			
			System.out.println("weekJobPlanSubSearch \n"+query);

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
		com.wms.fw.Utility.fixNullAndTrimAll(returns);
		return returns;
	}

	//업무계획조회 메인 화면 리스트 가져오기
	public ArrayList weekJobPlanListSearch(String empId,String day,boolean planner)throws Exception{
		Statement stmt=null;
		Connection con = null;
		ResultSet rs = null;
		WeekJobPlanDetailDTO[] returns = null;
		EmpDTO[] tmpEmpDTO = null;
		ArrayList returnList = null;
		try{
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			//String id=(planner)?"plEmpId":"reEmpId";
			String query1 ="";
			//if(planner){
				query1 = " select A.jobno||A.weekstartdt||A.prcsno||A.plancd as pk, A.jobno, A.jobname, A.prcsno, A.prcsname,  A.jobtitle, A.jobdetail, "+
				" A.startdt, A.enddt, A.mh,A.reEmpId as empid,to_empKName(a.reEmpId) as empkname "+
				" from weeklyplandetail A, weeklyplan B "+
				" where B.weekstartdt = '"+day+"' "+
				" and B.plEmpId='"+empId+"' "+
				" and A.reEmpId = B.reEmpId "+
				" and A.weekstartdt = B.weekstartdt "+
				" and (A.delFlag is null or A.delFlag != 'Y') "+
				//" order by jobno, prcsno ";
			//}else{
				//query1 = " select A.jobno||A.weekstartdt||A.prcsno as pk, A.jobno, A.jobname, A.prcsno, A.prcsname,  A.jobtitle, "+
				" union "+
			    " select A.jobno||A.weekstartdt||A.prcsno||A.plancd as pk, A.jobno, A.jobname, A.prcsno, A.prcsname,  A.jobtitle, A.jobdetail, "+
				" A.startdt, A.enddt, A.mh,A.reEmpId as empid,to_empKName(a.reEmpId) as empkname "+
				" from weeklyplandetail A, weeklyplandetail B "+
				" where B.weekstartdt = '"+day+"' "+
				" and B.reEmpId='"+empId+"' "+
				" and A.jobno = B.jobno "+
				" and A.prcsno = B.prcsno "+
				" and A.weekstartdt = B.weekstartdt "+
				" and (A.delFlag is null or A.delFlag != 'Y') "+
				" order by jobno, prcsno ";

			//}
			
			System.out.println("weekJobPlanListSearch \n"+query1);
			returnList=DataBaseUtil.moveToPlan("pk","com.wms.beans.dto.WeekJobPlanDetailDTO","com.wms.beans.dto.EmpDTO",DataBaseUtil.executeSQL(con,stmt,query1));

            /*
			String query2 = "";
			if(returns != null){
				for(int i=0; i<returns.length; i++ ){
					query2 = " select B.reEmpId, to_empkname(B.reEmpId) as empkname "+
					" from weeklyplandetail A, weeklyplan B "+
					" where A.weekstartdt = '"+day+"' "+
					" and A.jobno = '"+returns[i].jobNo+"' "+
					" and A.prcsno = '"+returns[i].prcsNo+"' "+
					" and B.plempid = '"+empId+"' "+
					" and A.weekstartdt = B.weekstartdt "+
					" and A.reEmpId = B.reEmpId ";
					System.out.println("weekJobPlanListSearch \n"+query2);
					tmpEmpDTO=(EmpDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.EmpDTO",DataBaseUtil.executeSQL(con,stmt,query2));
					if(tmpEmpDTO != null){
						returns[i].assignedEmps = tmpEmpDTO;
					}
				}
			}				

                        if(returns==null){
                        	System.out.println("자료가 없습니다.");
			}   
			*/
			
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
		com.wms.fw.Utility.fixNullAndTrimAll(returns);
    		return returnList;
	}

	public WeekJobPlanDTO[] weekJobPlanSearch(String empId,String day)throws Exception{

		Statement stmt=null;
		Connection con = null;
		WeekJobPlanDTO[] returns = null;
		try{
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			StringBuffer query1 =new StringBuffer("");
			System.out.println("dayJobSearch1 \n"+query1);
			returns=(WeekJobPlanDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.WeekJobPlanDTO",DataBaseUtil.executeSQL(con,stmt,query1.toString()));

                        if(returns==null){
                        	System.out.println("자료가 없습니다.");
			}                        
			
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
		com.wms.fw.Utility.fixNullAndTrimAll(returns);
    		return returns;		
	}

	public boolean confirm(WeekJobPlanDTO dto, String empId, String empKName, String flag)throws Exception{
    		return false;
	}
}