package com.wms.beans.dao;
//import com.wms.fw.ejb.JNDIUtility;
//import com.wms.fw.DTO;
//import com.wms.fw.util.DateUtil;
import com.wms.fw.db.DataBaseUtil;
import com.wms.beans.dto.*;
import com.wms.fw.*;

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
//import javax.sql.DataSource;
//import javax.naming.InitialContext;
//import java.io.FileNotFoundException;
//import java.io.File;
//import java.io.Reader;
//import java.io.StringReader;
//import java.lang.reflect.Array;

//biz,bizChag,org �� �˻��� ������ �ʿ� ����
public class AllSearch
{
	public static Hashtable allSearch(String type, String code, String name){
		Connection con=null;
		Statement stmt = null;
		ResultSet rs = null;
		Hashtable returns = new Hashtable();
		ArrayList col1 = new ArrayList();
		ArrayList col2 = new ArrayList();
		ArrayList col3 = null; 
		ArrayList col4 = null;
		
		try{
			StringBuffer query = new StringBuffer();
			if(type.equals("emp")){
				//EmpDTO dto = new EmpDTO();
				//query.append("select /*+INDEX_ASC(EMP XIE1EMP)*/ empid, empkname, to_orgName(empid) empOrgName from EMP ");
				//query.append("where empid like '");query.append(code);query.append("' ");
			    //query.append("and empkname like '");query.append(name);query.append("' ");
			    /*
				query.append("select x.empid, x.empkname, y.orgname ");
				query.append("from emp x, org y ");
				query.append("where x.orgcd = y.orgcd ");
				query.append("and y.orgname like '");query.append(code);query.append("' ");
				query.append("and x.empkname like '");query.append(name);query.append("' ");
				query.append("and x.retFlag = 'N' ");
				query.append("order by y.orgname,x.empkname ");
				*/
				//2005.10.04 �����˻��� ������������ ������ ������� �����ϸ鼭 empall�� ������ ������
				query.append("select x.empid, x.empkname, y.orgname \n");
				query.append("from empall x, org y 					\n");
				query.append("where x.orgcd = to_char(y.org_no) 				\n");
				query.append("and y.orgname like '");query.append(code);query.append("%'\n");
				query.append("and x.empkname like '");query.append(name);query.append("%'\n");
				query.append("and to_char(sysdate,'yyyymmdd') between x.wstartdt and wenddt \n");
				query.append("order by y.orgname,x.empkname \n");
				
			    con = DataBaseUtil.getConnection();
			    stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			    System.out.println(query.toString());
			    rs = stmt.executeQuery(query.toString());
				if(rs.next()){
					col3= new ArrayList();
					do{
						col1.add(rs.getString(1));
						col2.add(rs.getString(2));
						col3.add(rs.getString(3));
					}while(rs.next());
				}
				System.out.println("-----"+col1.size());
			 	returns.put("code",col1);
				returns.put("name",col2);
				returns.put("orgName",col3);
			}else if(type.equals("biz")){
				//BizDTO dto = new BizDTO();
				query.append("select /*+INDEX_ASC(BIZ XPKBIZ)*/ bizno, bizname from BIZ ");
				query.append("where bizno like '");query.append(code);query.append("' ");
				query.append("and bizname like '");query.append(name);query.append("' ");
				con = DataBaseUtil.getConnection();
			    stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			    System.out.println(query.toString());
			    rs = stmt.executeQuery(query.toString());
				int i = 0;
				while(rs.next()){
					col1.add(i,rs.getString(1));
					col2.add(i,rs.getString(2));
					i++;
			    }
				System.out.println("-----"+col1.size());
			 	returns.put("code",col1);
				returns.put("name",col2);
			}else if(type.equals("bizChag")){
				
				//query.append("select /*+INDEX_ASC(BIZCHAG XPKBIZCHAG)*/ bizacqcd, chagname, loc from BIZCHAG ");
				//query.append("where bizacqcd like '");query.append(code);query.append("' ");
				//query.append("and chagname like '");query.append(name);query.append("' ");
                
				//20041108 �ŷ�ó��ȸ ����..�ŷ�ó������ ����				
				query.append("select bizAcqCd , ( ");
				query.append("	CASE substr(bizAcqName,1,3)  ");
				query.append("			WHEN '(��)' THEN replace(bizAcqName,'(��)','') ");  //�����߰��� �߰��� �� �� �ε��� ���Ұ���
				query.append("			WHEN '(��)' THEN replace(bizAcqName,'(��)','') ");
				query.append("			WHEN '(��)' THEN replace(bizAcqName,'(��)','') ");
				query.append("			WHEN '(��)' THEN replace(bizAcqName,'(��)','') ");
				query.append("			ELSE bizAcqName                                      ");
				query.append("	END), ");
				query.append("bizAcqName chagName, bizAcqAddr loc ");
				query.append("from bizAcqCorp ");
				query.append("where bizacqcd like '%");query.append(code);query.append("%' ");				
				query.append("and Upper(bizAcqName) like '%");query.append(Utility.replace(name.toUpperCase(),"%"));query.append("%' ");
				query.append("order by 2 ");

				

				con = DataBaseUtil.getConnection();
			    stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			    System.out.println(query.toString());
			    rs = stmt.executeQuery(query.toString());
				if(rs.next()){
					col3= new ArrayList();
					do{
						col1.add(rs.getString(1));
						col2.add(rs.getString(3));
						col3.add(rs.getString(4));
					}while(rs.next());
				}

				System.out.println("-----"+col1.size());
			 	returns.put("code",col1);
				returns.put("name",col2);
				returns.put("orgName",col3);
			}else if(type.equals("org")){
				//OrgDTO dto = new OrgDTO();
				
//				//���� ���� �˻�(���� ��� ���� ���� ����)
//				query.append("SELECT  A.ORG_NO, \n ");
//				query.append("		A.ORGCD, \n ");
//				query.append("		A.ORGNAME, \n ");
//				query.append("		LEVEL, \n ");
//				query.append("		(SELECT (DECODE(COUNT(ORGCD),0,'',TO_CHAR('['||COUNT(ORGCD)||']'))) \n ");
//				query.append("		FROM ORG \n ");
//				query.append("		WHERE TO_CHAR(A.ORG_NO) = HIGHORGCD) \n ");
//				query.append("		AS LEVELSUBCNT, \n ");
//				query.append("		B.STARTDT \n ");
//				query.append("FROM ORG A , \n ");
//				query.append("	(SELECT ORG_NO, MAX(STARTDT) AS STARTDT \n ");
//				query.append("	FROM ORGHISTORY \n ");
//				query.append("	GROUP BY ORG_NO) B \n ");
//				query.append("WHERE UPPER(ORGCD) LIKE UPPER('%"+ code + "%') \n ");
//				query.append("AND UPPER(ORGNAME) LIKE UPPER('%"+ name+ "%') \n ");
//				query.append("AND A.ORG_NO = B.ORG_NO \n ");
//				query.append("AND SYSDATE >= TO_DATE(B.STARTDT,'YYYYMMDD') \n ");
//				query.append("START WITH A.ORGCD = '9999' \n ");
//				query.append("CONNECT BY PRIOR TO_CHAR(A.ORG_NO) = A.HIGHORGCD \n ");
//				query.append("ORDER SIBLINGS BY A.ORGCD ");
//								
				
				query.append("select /*+INDEX_ASC(ORG XPKORG)*/ org_no, orgcd, orgname from ORG ");
				query.append("where orgcd like '");query.append(code);query.append("%' ");
				query.append("and orgname like '");query.append(name);query.append("%' ");
				//query.append("and sysdate between to_date(startDt,'YYYYMMDD') and to_date(endDt,'YYYYMMDD') ");
				query.append(" AND  ORG_NO IN (SELECT ORG_NO FROM ORGHISTORY WHERE ENDDT >= TO_CHAR(SYSDATE,'YYYYMMDD'))");
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
			}else if(type.equals("histJob")){
				query.append("select jobcd, jobname from HISTJOB ");
				query.append("where jobcd like '");query.append(code);query.append("%' ");
				query.append("and jobname like '%");query.append(name);query.append("%' ");
				query.append("order by jobcd ");

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
				System.out.println("----->"+col1.size());
			 	returns.put("code",col1);
				returns.put("name",col2);			
			}else if(type.equals("coCodeType")){
				query.append("select cdtype, cdtypename \n ");
				query.append("FROM COCODETYPE \n ");
				query.append("where cdtype like '");query.append(code);query.append("' ");
				query.append("and cdtypename like '");query.append(name);query.append("' ");
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
			}else if(type.equals("stdPrcsType")){//cocode ���� cdtype�� 'PR'(ǥ�ذ�������) �� cd ��
				query.append("select cd, cdname, cdds from COCODE ");
				query.append("where cdtype like 'PR' ");
				con = DataBaseUtil.getConnection();
			    stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			    System.out.println(query.toString());
			    rs = stmt.executeQuery(query.toString());
				int i = 0;
				if(rs.next()){
					col3= new ArrayList();
					do{
						col1.add(rs.getString(1));
						col2.add(rs.getString(2));
						col3.add(rs.getString(3));
					}while(rs.next());
				}
				System.out.println("-----"+col1.size());
			 	returns.put("code",col1);
				returns.put("name",col2);
				returns.put("orgName",col3);			
			}else if(type.equals("prod")){
				query.append("select prodtype||prodNo, prodName from PROD ");
				query.append("where prodType||prodNo like '%");query.append(code);query.append("' ");
				query.append("and prodName like '%");query.append(name);query.append("' ");
				query.append("order by 1");
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
			}else if(type.equals("oppt")){
				query.append("select opptno,opptname from OPPT ");
				query.append("where opptNo like '%");query.append(code);query.append("' ");
				query.append("and   opptName like '%");query.append(name);query.append("' ");
				query.append("order by 1 desc");
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
			}else if(type.equals("opptCurEmp")){
				//EmpDTO dto = new EmpDTO();
				//query.append("select /*+INDEX_ASC(EMP XIE1EMP)*/ empid, empkname, to_orgName(empid) empOrgName from EMP ");
				//query.append("where empid like '");query.append(code);query.append("' ");
			    //query.append("and empkname like '");query.append(name);query.append("' ");
			    /*
				query.append("select x.empid,x.empkname,y.orgname, y.orgCd ");
				query.append("from emp x, org y ");
				query.append("where x.orgcd = y.orgcd ");
				query.append("and y.orgname like '");query.append(code);query.append("' ");
				query.append("and x.empkname like '");query.append(name);query.append("' ");
				query.append("order by y.orgname,x.empkname ");
				*/
				//2005.10.04 �����˻��� ������������ ������ ������� �����ϸ鼭 empall�� ������ ������
				query.append("select x.empid,x.empkname,y.orgname, y.orgCd 	\n");
				query.append("from empall x, org y 							\n");
				query.append("where x.orgcd = y.orgcd 						\n");
				query.append("and y.orgname like '");query.append(code);query.append("' 	\n");
				query.append("and x.empkname like '");query.append(name);query.append("' 	\n");
				query.append("and to_char(sysdate,'yyyymmdd') between x.wstartdt and wenddt \n");
				query.append("order by y.orgname,x.empkname 				\n");
				
				
			    con = DataBaseUtil.getConnection();
			    stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			    System.out.println(query.toString());
			    rs = stmt.executeQuery(query.toString());
				if(rs.next()){
					col3 = new ArrayList();
					col4 = new ArrayList();
					do{
						col1.add(rs.getString(1));
						col2.add(rs.getString(2));
						col3.add(rs.getString(3));
						col4.add(rs.getString(4));
					}while(rs.next());
				}
				System.out.println("-----"+col1.size());
			 	returns.put("code",col1);
				returns.put("name",col2);
				returns.put("orgName",col3);
				returns.put("orgCd",col4);
			}
	  }catch(Exception e){e.printStackTrace();}
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