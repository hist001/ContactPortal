/*************************************************************
*	�� �� ��  : OrgDAO.java
*	�ۼ�����  : 2004/06/22
*	�� �� ��  : 
*	��    ��  : ���� ��ȸ ������ Data Access�ϴ� Ŭ����
*************************************************************/
package com.wms.beans.dao;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.wms.fw.db.DataBaseUtil;
import com.wms.beans.dto.*;
import com.wms.fw.*;
public class OrgDAO implements IOrg
{
	public OrgDTO[] searchTeamOrg(String managerFlag, String userOrgCd)throws Exception
	{
		Connection con=null;
		Statement stmt = null;
		ResultSet rs = null;
		OrgDTO[] returns=null;
		try{
		
			String subQry = "";
			if(managerFlag.trim().equals("C") ||managerFlag.trim().equals("E")){
				subQry = "and orgcd like '%'";
			}else{
				subQry = "and orgcd = '"+userOrgCd.substring(0,2)+"'||'00'";
			}
			StringBuffer query = new StringBuffer();
	
			query.append("select * from org ");
			query.append("where substr(orgcd,2,1) != '0'  ");
			query.append("and substr(orgcd,3,2)= '00' ");
			query.append(subQry);
			query.append("order by orgcd ");			

			System.out.println("OrgDAO.searchTeamOrg :: "+query.toString());

			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			rs = DataBaseUtil.executeSQL(con,stmt,query.toString());
			returns=(OrgDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.OrgDTO",rs);
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
	
	public OrgDTO[] searchGroupOrg(String teamCd, String managerFlag, String userOrgCd)throws Exception
	{
		Connection con=null;
		Statement stmt = null;
		ResultSet rs = null;
		OrgDTO[] returns=null;
		try{
			String subQry = "";
			//PM, �׷���,������ ���
			if(managerFlag.trim().equals("G") ||managerFlag.trim().equals("F")
			   || managerFlag.trim().equals("P") ||managerFlag.trim().equals("Y")){
				subQry = userOrgCd.substring(0,3)+"'||'0";
			}else if(managerFlag.trim().equals("T") ){//�����ϰ��
				subQry = userOrgCd.substring(0,2)+"'||'%";
			}else{//�ӿ�, �ι���
				subQry = teamCd;
			}
			StringBuffer query = new StringBuffer();
			query.append("select * from org ");
			query.append("where substr(orgcd,3,1) != '0' ");
			query.append("and substr(orgcd,4,1)= '0' ");
			query.append("and orgcd like '");
			query.append(subQry);
			query.append("' order by orgcd ");			

			System.out.println("OrgDAO.searchGroupOrg :: "+query.toString());

			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			rs = DataBaseUtil.executeSQL(con,stmt,query.toString());
			returns=(OrgDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.OrgDTO",rs);
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

	public OrgDTO[] searchOrg(String orgCd, String orgName)throws Exception{
		Connection con=null;
		Statement stmt = null;
		ResultSet rs = null;
		OrgDTO[] returns=null;
		try{
			StringBuffer query = new StringBuffer();
			query.append("select * from org ");
			query.append("where orgcd like '");
			query.append(orgCd);
			query.append("%' and orgname like '%");
			query.append(orgName);
			query.append("%' ");
			query.append("order by orgcd ");			

			System.out.println("OrgDAO.searchOrg :: "+query.toString());

			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			rs = DataBaseUtil.executeSQL(con,stmt,query.toString());
			returns=(OrgDTO[])DataBaseUtil.moveToEntities("com.wms.beans.dto.OrgDTO",rs);
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