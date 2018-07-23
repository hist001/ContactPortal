/*************************************************************
*	파 일 명  : AuthCheck.java
*	작성일자  : 2004/06/22
*	작 성 자  : 
*	내    용  : 화면권한 제어(CRUD)처리 클래스
*************************************************************/
package com.wms.beans.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.ArrayList;
import java.sql.SQLException;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.wms.fw.db.DataBaseUtil;
import com.wms.beans.dto.*;

public class AuthCheck{
	public static boolean authCheck(String empId, String scrName){
		Connection con=null;
		Statement stmt = null;
		ResultSet rs = null;
		boolean returns = false;
	
		try{
			StringBuffer query = new StringBuffer();
			query.append("select empId,scrName from SCREENAUTH where empId = '");query.append(empId);query.append("' ");
			query.append("and scrName = '");query.append(scrName);query.append("' ");
			if(scrName.equals("emp")){
				//직원정보관리(emp)는 SCREEAUTH table 에 없어도 그룹장이상은 ACCESS 할 수 있음
				query.append("union all ");
				query.append("select empid, managerflag from approval ");
				query.append("where empid = '");query.append(empId);query.append("' ");
				query.append("and   managerflag IN ('C','E','T','P','F','G') ");
			}
		    System.out.println(query.toString());
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(query.toString());
			if(rs.next()){
				returns = true;
			}else{
				returns = false;
			}		
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				if(rs!=null)rs.close();
				if(stmt!=null)stmt.close();
				if(con!=null)con.close();
		    }catch(Exception e){e.printStackTrace();}
        }
	    return returns;
	}
}