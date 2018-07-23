package com.wms.fw.db;
import com.wms.beans.MakeQueryHelper;
import com.wms.common.beans.dto.DataSet;
import com.wms.fw.ejb.JNDIUtility;
import com.wms.fw.DTO;
import com.wms.fw.util.DateUtil;
import javax.sql.DataSource;
import java.sql.Connection;
import javax.naming.InitialContext;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Clob;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.sql.SQLException;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Array;
import com.wms.fw.*;
import javax.naming.*;

public class DataBaseUtil
{
	public  static final String COL_NAMES ="COL_NAMES";
	public  static final String COL_VALUE="COL_VALUE";
	public  static final String VALUE_LIST="VALUE_LIST";

	private static String default_datasource_name;
  	private static int min_special_param_lenth;
  	
	private static String xmlDir;  	
	private static Hashtable sequenceList;
	private static DataSet entitySet;  	
	/**
	 * Connection��ü�� �����Ѵ�.
	 * @return java.sql.Connection
	 */
	public static Connection getConnection()throws Exception{
		if(default_datasource_name==null){
			com.wms.fw.Config conf = new com.wms.fw.Configuration();
			default_datasource_name=conf.get("default.server.datasource.name");
			
		}
			
		return getConnection(default_datasource_name);
	} 
	/**
	 * Connection��ü�� �����Ѵ�.
	 * @return java.sql.Connection
	 * @param jndiName java.lang.String  JNDI�� ����� �Ǵ� ���񽺸�
	 */
	public static Connection getConnection(String jndiName)throws Exception{
		// 20180718
		//return ((DataSource)JNDIUtility.lookup(jndiName)).getConnection();
		Context initContext = new InitialContext();
		Context envContext  = (Context)initContext.lookup("java:/comp/env");
		DataSource ds = (DataSource)envContext.lookup(jndiName);
		return ds.getConnection();
	}
	/**
	 * �˻��� ���� , ����� �����Ѵ�.
	 * @return java.sql.ResultSet
	 * @param con java.sql.Connection 
	 * @param stmt java.sql.Statement 
	 * @param strSQL java.lang.String  SELECT�� �˻� QUERY
	 */
	public static ResultSet executeSQL(Connection conn, Statement stmt, String strSQL) throws Exception{
		if(conn==null || stmt==null) return null;	
		ResultSet rs = null;
		try {
		  rs = stmt.executeQuery(strSQL);
		}
		catch ( Exception e ) { e.printStackTrace();  throw e;}
		return rs;
	}                      
	/**
	 * �˻��� ���� , ����� �����Ѵ�.
	 * @return java.sql.ResultSet
	 * @param con java.sql.Connection 
	 * @param pstmt java.sql.PreparedStatement 
	 * @param sql java.lang.String  SELECT�� �˻� QUERY
	 * @param params Enumeration  �˻� ���ǰ���
	 */
    public static ResultSet executeSelect(Connection conn, PreparedStatement pstmt, String sql,
	Enumeration params) throws SQLException {

	if(conn==null || pstmt==null) return null;
	ResultSet rs = null;

	if (params != null) {
      int i=1;
	  while (params.hasMoreElements()) {
		Object obj = params.nextElement();

		if (obj instanceof java.lang.Integer)
		  pstmt.setObject(i, obj, java.sql.Types.INTEGER);
		else if(obj instanceof java.lang.Character) {
		  pstmt.setString(i, obj.toString());
		}
		else if(obj instanceof java.lang.Double)
		  pstmt.setObject(i, obj, java.sql.Types.DOUBLE);
		else if(obj instanceof java.lang.String)
		  pstmt.setString(i, (String) obj);
		else if(obj instanceof java.util.Date)
		  pstmt.setDate(i, (java.sql.Date) obj);
		else
		  throw new SQLException("pstmt�Է��� �����߻�");
		i++;
	  }
	}
	rs = pstmt.executeQuery();
	return rs;
  }                      
	/**
	 * ���� , ����� �����Ѵ�.
	 * @return int ���� ��� , ����� ���ڵ��� ����
	 * @param con java.sql.Connection 
	 * @param sql java.lang.String  ������ QUERY
	 * @param params Enumeration  ����� ����
	 */
  public static int executeUpdate(Connection conn, String sql,
	Enumeration params) throws SQLException, FileNotFoundException,com.wms.fw.ConfigurationException {
	
	if(conn==null) return 0;
	int affected;
	if(min_special_param_lenth==0){
			com.wms.fw.Config conf = new com.wms.fw.Configuration();
			min_special_param_lenth=conf.getInt("min.special.param.lenth");
			
	}
	PreparedStatement pstmt = conn.prepareStatement(sql);
	if (params != null) {
	  int i=1;
	  while (params.hasMoreElements()) {
		Object obj = params.nextElement();

		if (obj instanceof java.lang.Integer)
		  pstmt.setObject(i, obj, java.sql.Types.INTEGER);
		else if(obj instanceof java.lang.Character) {
		  pstmt.setString(i, obj.toString());
		}
		else if(obj instanceof java.lang.Double)
		  pstmt.setObject(i, obj, java.sql.Types.DOUBLE);
		else if(obj instanceof java.lang.String) {
		  String str = (String) obj;
		  if(str.length() > min_special_param_lenth) {
			Reader in = new StringReader(str);
			pstmt.setCharacterStream(i, in, str.length());
		  }
		  else 
			pstmt.setString(i, str);
		}
		else if(obj instanceof java.util.Date)
		  pstmt.setDate(i, new java.sql.Date(((java.util.Date) obj).getTime()));
		else if(obj instanceof java.io.InputStream) {
		   throw new SQLException("pstmt�Է��� �����߻�");
		}
		else if(obj instanceof java.io.File) {
		  File file = (File) obj;
		  java.io.InputStream fin = new java.io.FileInputStream(file);
		  pstmt.setBinaryStream (i, fin, (int) file.length());
		}
		else
		   throw new SQLException("pstmt�Է��� �����߻�");
		i++;
	  }
	}
	affected = pstmt.executeUpdate();
	pstmt.close();
	return affected;
  } 
  public static void prepareSetting(PreparedStatement ps,
	Enumeration params) throws SQLException, FileNotFoundException,com.wms.fw.ConfigurationException {
	if(min_special_param_lenth==0){
			com.wms.fw.Config conf = new com.wms.fw.Configuration();
			min_special_param_lenth=conf.getInt("min.special.param.lenth");
			
	}
	if (params != null) {
	  int i=1;
	  while (params.hasMoreElements()) {
		Object obj = params.nextElement();

		if (obj instanceof java.lang.Integer)
		  ps.setObject(i, obj, java.sql.Types.INTEGER);
		else if(obj instanceof java.lang.Character) {
		  ps.setString(i, obj.toString());
		}
		else if(obj instanceof java.lang.Double)
		  ps.setObject(i, obj, java.sql.Types.DOUBLE);
		else if(obj instanceof java.lang.String) {
		  String str = (String) obj;
		  if(str.length() > min_special_param_lenth) {
			Reader in = new StringReader(str);
			ps.setCharacterStream(i, in, str.length());
		  }
		  else 
			ps.setString(i, str);
		}
		else if(obj instanceof java.util.Date)
		  ps.setDate(i, new java.sql.Date(((java.util.Date) obj).getTime()));
		else if(obj instanceof java.io.InputStream) {
		   throw new SQLException("ps�Է��� �����߻�");
		}
		else if(obj instanceof java.io.File) {
		  File file = (File) obj;
		  java.io.InputStream fin = new java.io.FileInputStream(file);
		  ps.setBinaryStream (i, fin, (int) file.length());
		}
		else
		   throw new SQLException("ps�Է��� �����߻�");
		i++;
	  }
	}
  }      
	/**
	 * ������ ������ 3������ �и� �����Ѵ�.
	 * COL_NAMES �̸����� String���� ������. �����ڴ� ',' 
	 * COL_VALUE �̸��� ���� ��ŭ '?'�� ������. �����ڴ� ','
	 * VALUE_LIST ���� ������ Enumeration���� �����Ѵ�.
	 * @return java.util.Hashtable 
	 * @param dto com.wms.fw.DTO  ���� ���� ���� ��ü
	 */  
  public static Hashtable makeSql(DTO dto)throws Exception{
	Hashtable data = dto.getList();
	Enumeration keys = data.keys();
	Enumeration values = data.elements();
	StringBuffer colNames= new StringBuffer("");
	StringBuffer colValues=new StringBuffer("");
	String preFix="";
	for (int i = 0;  i < data.size(); i++ ) {		
		preFix=(i < data.size() - 1)?",":"";                
		colNames.append(keys.nextElement());
		colNames.append(preFix);
		colValues.append("?");
		colValues.append(preFix);				
	}
	data=new Hashtable();
	data.put(COL_NAMES,colNames.toString());
	data.put(COL_VALUE,colValues.toString());
	data.put(VALUE_LIST,values);
	return data;
  }
	/**
	 * SQLQuery���� where���� �ڵ����� ������ش�.
	 * ��, ���߰˻��� ��� and�� ����ǰ�, '='�� �� �˻��� �� �ִ�.
	 * @return java.lang.String 
	 * @param obj java.lang.Object ���� ���� ���� ��ü
	 */ 
  public static String whereSql(Object obj)throws Exception{
		StringBuffer sql = new StringBuffer();
		String preFix=" where ";
		Class c = obj.getClass();
		java.lang.reflect.Field[] field = c.getFields();
		for (int i=0 ; i<field.length; i++) {										
			String fieldtype = field[i].getType().getName();
			String fieldname = field[i].getName();
			String value=field[i].get(obj)+"";
			if(value!=null&&!value.trim().equals("")&&!value.trim().equals("null")&&!value.trim().equals("0")){
				sql.append(preFix);
				sql.append(" " + fieldname + " = ");
				if ( fieldtype.equals("java.lang.String")) {
					sql.append("'"+value.trim()+"'");
				}
				else {
					sql.append(value.trim());
				}
				preFix=" and ";	
			}

		}
		return sql.toString();
  }
	/**
	 * ResultSet�� ���� ��� ���ڵ���� ��ü�迭�� �ڵ� ġȯ���ش�.
	 * @return java.lang.Object ��ü�� �迭�� ����Ų��. 
	 * @param  className java.lang.String ġȯ�ϰԵ� ��ü�� Ÿ��
	 * @param  rs java.sql.ResultSet  ���� ������ ���� ��ü
	 */ 
  public static Object moveToEntities(String className,ResultSet rs) throws Exception{
		Object returns=null;
		try {
			ArrayList nameList = createNameList(rs);
			Class c = Class.forName(className);
			java.lang.reflect.Field[] field = c.getFields();
			int index=0;
			if(rs.last()){
				int length=rs.getRow();
				returns=java.lang.reflect.Array.newInstance(c, length);
				rs.first();
				do{
					Object entity=c.newInstance();
					for (int i=0 ; i<field.length; i++) {
									
						String fieldtype = field[i].getType().getName();
						String fieldname = field[i].getName();

						if ( nameList.contains( fieldname.toUpperCase()) ) {
							if ( fieldtype.equals("java.lang.String")) {
								
								field[i].set(entity, rs.getString(fieldname));
								
							}
							else if ( fieldtype.equals("int")) {
								field[i].setInt(entity, rs.getInt(fieldname));
							}
							else if ( fieldtype.equals("double")) {
								field[i].setDouble(entity, rs.getDouble(fieldname));
							}
							else if ( fieldtype.equals("long")) {
								field[i].setLong(entity, rs.getLong(fieldname));
							}
							else if ( fieldtype.equals("float")) {
								field[i].set(entity, new Float(rs.getFloat(fieldname)));
							}
							else if ( fieldtype.equals("boolean")) {
								field[i].setBoolean(entity, rs.getBoolean(fieldname));
							}
						}
					
					}//for
					java.lang.reflect.Array.set(returns,index,entity);
			        index++;
				}while(rs.next());
			}//if
		}
		catch(Exception e){
		}
		return returns;
  } // end moveToEntities
	/**
	 * ResultSet�� ���� ��� ���ڵ���� ��ü�迭�� �ڵ� ġȯ���ش�.
	 * @return java.lang.Object ��ü�� �迭�� ����Ų��. 
	 * @param  keyName java.lang.String ���еǾ����� field
	 * @param  className java.lang.String ġȯ�ϰԵ� ��ü�� Ÿ��
	 * @param  rs java.sql.ResultSet  ���� ������ ���� ��ü
	 */ 
  public static Object moveToEntities(String keyName,String className,ResultSet rs) throws Exception{
		Object returns=null;
		try {
			ArrayList nameList = createNameList(rs);
			Class c = Class.forName(className);
			java.lang.reflect.Field[] field = c.getFields();
			if(rs.first()){
				ArrayList values = new ArrayList();
				ArrayList keys   = new ArrayList();
				do{
					if(!keys.contains(rs.getString(keyName))){
						keys.add(rs.getString(keyName));
						Object entity=c.newInstance();
						for (int i=0 ; i<field.length; i++) {										
							String fieldtype = field[i].getType().getName();
							String fieldname = field[i].getName();

							if ( nameList.contains( fieldname.toUpperCase()) ) {
								if ( fieldtype.equals("java.lang.String")) {									
									field[i].set(entity, rs.getString(fieldname));									
								}
								else if ( fieldtype.equals("int")) {
									field[i].setInt(entity, rs.getInt(fieldname));
								}
								else if ( fieldtype.equals("double")) {
									field[i].setDouble(entity, rs.getDouble(fieldname));
								}
								else if ( fieldtype.equals("long")) {
									field[i].setLong(entity, rs.getLong(fieldname));
								}
								else if ( fieldtype.equals("float")) {
									field[i].set(entity, new Float(rs.getFloat(fieldname)));
								}
								else if ( fieldtype.equals("boolean")) {
									field[i].setBoolean(entity, rs.getBoolean(fieldname));
								}
							}
						
						}//for					
						values.add(entity);
					}
				}while(rs.next());
				returns=java.lang.reflect.Array.newInstance(c, values.size());
				for(int i=0;i<values.size();i++){
					java.lang.reflect.Array.set(returns,i,values.get(i));
				}
			}//if
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return returns;
  } // end moveToEntities

  public static Object moveToEntity(String className,ResultSet rs,int row) throws Exception{
		Object entity=null;
		try {

			ArrayList nameList = DataBaseUtil.createNameList(rs);
			Class c = Class.forName(className);
			java.lang.reflect.Field[] field = c.getFields();
			int index=0;
			if(rs.absolute(row)){
				int length=rs.getRow();				
					entity=c.newInstance();
					for (int i=0 ; i<field.length; i++) {
									
						String fieldtype = field[i].getType().getName();
						String fieldname = field[i].getName();

						if ( nameList.contains( fieldname.toUpperCase()) ) {
							if ( fieldtype.equals("java.lang.String")) {
								
								field[i].set(entity, rs.getString(fieldname));
								
							}
							else if ( fieldtype.equals("int")) {
								field[i].setInt(entity, rs.getInt(fieldname));
							}
							else if ( fieldtype.equals("double")) {
								field[i].setDouble(entity, rs.getDouble(fieldname));
							}
							else if ( fieldtype.equals("long")) {
								field[i].setLong(entity, rs.getLong(fieldname));
							}
							else if ( fieldtype.equals("float")) {
								field[i].set(entity, new Float(rs.getFloat(fieldname)));
							}
							else if ( fieldtype.equals("boolean")) {
								field[i].setBoolean(entity, rs.getBoolean(fieldname));
							}

						}
					
					}//for					
			}//if

		}
		catch(Exception e){
					//Debug.warn.println(this, e.getMessage());
		}
		return entity;
  } // end moveToEntity

  public static Object moveToEntity(String className,ResultSet rs) throws Exception{
		Object entity=null;
		try {
			ArrayList nameList = createNameList(rs);
			Class c = Class.forName(className);
			java.lang.reflect.Field[] field = c.getFields();
			int index=0;
			if(rs.first()){
				int length=rs.getRow();				
					entity=c.newInstance();
					for (int i=0 ; i<field.length; i++) {
									
						String fieldtype = field[i].getType().getName();
						String fieldname = field[i].getName();

						if ( nameList.contains( fieldname.toUpperCase()) ) {
							if ( fieldtype.equals("java.lang.String")) {
								
								field[i].set(entity, rs.getString(fieldname));
								
							}
							else if ( fieldtype.equals("int")) {
								field[i].setInt(entity, rs.getInt(fieldname));
							}
							else if ( fieldtype.equals("double")) {
								field[i].setDouble(entity, rs.getDouble(fieldname));
							}
							else if ( fieldtype.equals("long")) {
								field[i].setLong(entity, rs.getLong(fieldname));
							}
							else if ( fieldtype.equals("float")) {
								field[i].set(entity, new Float(rs.getFloat(fieldname)));
							}
							else if ( fieldtype.equals("boolean")) {
								field[i].setBoolean(entity, rs.getBoolean(fieldname));
							}

						}
					
					}//for					
			}//if

		}
		catch(Exception e){
					//Debug.warn.println(this, e.getMessage());
		}
		return entity;
  } // end moveToEntities

	/**
	 * ResultSet�� ���� �÷��� �̸��� �����Ѵ�.
	 * @return java.util.ArrayList �÷��� �̸���. 
	 * @param  rs java.sql.ResultSet  ���� ������ ���� ��ü
	 */ 
  public static ArrayList createNameList(ResultSet rs)throws Exception{
	ResultSetMetaData rsmd = rs.getMetaData();
	ArrayList returns = new ArrayList();
	for(int i=1;i<=rsmd.getColumnCount();i++){
		returns.add(rsmd.getColumnName(i));			
	}
	return returns;
  }//end createNameList
	/**
	 * �������� sub��ü�� ���� �������� super��ü�� �����Ѵ�.
	 * @return java.util.ArrayList super��ü. 
	 * @param  keyName java.lang.String ���еǾ����� field
	 * @param  superName java.lang.String ������ü�� Ŭ����
	 * @param  subName java.lang.String ������ü�� Ŭ����
	 * @param  rs java.sql.ResultSet  ���� ������ ���� ��ü
	 * 
	 */ 
  public static ArrayList moveToPlan(String keyName,String superName,String subName,ResultSet rs) throws Exception{
		ArrayList returnList=null;
		String keyValue=null;
		try {
			Class c = Class.forName(superName);
			Class cSub = Class.forName(subName);

			Object obj = moveToEntities(subName,rs);

			boolean regFlag=false;
			int index=0;
			int endIndex=0;
			if(rs.first()){
				keyValue=rs.getString(keyName);
                returnList=new ArrayList();
				regFlag=true;

				do{ 
					if(!keyValue.equals(rs.getString(keyName))){
						keyValue=rs.getString(keyName);
						regFlag=true;
						endIndex=rs.getRow()-1;
					}
					
					if(regFlag){
						if(endIndex>0){
							int size =endIndex-index;
							Object array =java.lang.reflect.Array.newInstance(cSub, size);
							System.arraycopy(obj,index,array,0,size);
							Object entity=moveToEntity(superName,rs,endIndex);	
							setArrayField(entity,array,subName);						
							returnList.add(entity);
							index=rs.getRow();
						}

						regFlag=false;
					}												
			       					
				}while(rs.next());
				rs.last();
				endIndex=rs.getRow();
				int size =endIndex-index;
				Object array =java.lang.reflect.Array.newInstance(cSub, size);
				System.arraycopy(obj,index,array,0,size);			
				Object entity=moveToEntity(superName,rs,rs.getRow());	
				setArrayField(entity,array,subName);						
				returnList.add(entity);
			}
		}
		catch(Exception e){
				//Debug.warn.println(this, e.getMessage());
		}
		return returnList;
  } // moveToPlan
	/**
	 * ��ü�� array�� ��Ͻ�Ų��.
	 * @param  entity java.lang.Object array�� ���� ��ü
	 * @param  values java.lang.Object array�� ����Ű�� ��ü
	 * 
	 */ 
   public static void setArrayField(Object entity,Object values,String subName) throws Exception{
		try {
			Class c = entity.getClass();
			java.lang.reflect.Field[] field = c.getFields();
			for (int i=0 ; i<field.length; i++) {
							
				Class type = field[i].getType();
				if ( type.isArray()&&type.getName().equals("[L"+subName+";")) {	//�迭�� ��� Ÿ�Ը� �յڿ� '[L', ';'�� ���ڰ� �ٴ´�.		
					field[i].set(entity, values);
					break;
				}
			
			}//for					

		}
		catch(Exception e){
					//Debug.warn.println(this, e.getMessage());
		}
	
  } // setArrayField

	/**
	 * ��� �Һз� �ڵ���� �����Ѵ�..
	 * @return com.wms.beans.dto.Code[] Code���� ����. 
	 */ 
  public static com.wms.beans.dto.Code[] getCode(){
	  Connection con=null;
	  Statement stmt = null;
	  Object returns = null;
	  try{
         String sql="select substr(a.cd,1,1) precodeno," 
		            +" a.cd codeno, a.cdname codename "
					+" from cocode a, cocode b "
                    +" where substr(a.cd,1,1)=b.cd "
					+" and b.cdtype='BL' "
					+" and a.cdtype='BM' "
                    +" union all select cdtype precodeno,"
					+" cd codeno,cdname codename from cocode "
					+" where cdtype='BL'" 
					+" union select substr(bizcd,1,2) precodeno,"
					+" bizcd codeno,bizname codename from biz " 
                    +" union all select substr(a.cd,1,1) precodeno, "
                    +" a.cd codeno, a.cdname codename "
                    +" from cocode a, cocode b  "
                    +" where substr(a.cd,1,1)=b.cd"
                    +" and b.cdtype='JL' "
                    +" and a.cdtype='JM' "
                    +" union  all select cdtype precodeno,"
                    +" cd codeno,cdname codename from cocode "
                    +" where cdtype='JL'"
                    +" union  all select 'BT' precodeno,"
                    +" cd codeno,cdname codename from cocode "
                    +" where cdtype='BT'"
                    +" union  all select cdtype precodeno,"
                    +" cd codeno,cdname codename from cocode "
                    +" where cdtype='BK'"
					+" order by preCodeNo,codeNo ";


		 con  = getConnection();
		 stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		 returns=moveToEntities("com.wms.beans.dto.Code",executeSQL(con,stmt,sql));

	  }catch(Exception e){e.printStackTrace();}
	  finally{
		  try{
			  if(stmt!=null)stmt.close();
			  if(con!=null)con.close();
		  }catch(Exception e){e.printStackTrace();}
	  }
	  return (com.wms.beans.dto.Code[])returns;
  }

  public static com.wms.beans.dto.Code[] getCode(String codeNo){
	  Connection con=null;
	  Statement stmt = null;
	  Object returns = null;
	  try{
         String sql= " select substr(bizno,1,2) precodeno, "
		            +" bizno codeno , bizname codename "
					+" from biz where substr(bizno,1,2)='"+codeNo+"' " 
		            +" order by preCodeNo,codeNo ";

		 con  = getConnection();
		 stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		 returns=moveToEntities("com.wms.beans.dto.Code",executeSQL(con,stmt,sql));

	  }catch(Exception e){e.printStackTrace();}
	  finally{
		  try{
			  if(stmt!=null)stmt.close();
			  if(con!=null)con.close();
		  }catch(Exception e){e.printStackTrace();}
	  }
	  return (com.wms.beans.dto.Code[])returns;
  }
	/**
	 * ���հ˻�..
	 * @return Hashtable �̸��� �ڵ�����. 
	 */ 
  public static Hashtable unionSearch(String searchNo,String tableName){
	  Connection con=null;
	  Statement stmt = null;
	  ResultSet rs = null;
	  Hashtable returns = null;
	  try{
		 String codeNo=tableName+((tableName.equals("biz"))?"NO":"CD");
		 String codeName=tableName+"NAME";

         	String sql=" select "+codeNo+","+codeName
			       +" from "+tableName
			       +" where "+codeNo+"='"+searchNo.toUpperCase()+"'";
			       
		 con  = getConnection();
		 stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		 System.out.println(sql);
         rs =  stmt.executeQuery(sql);
		 if(rs.next()){
			 returns = new Hashtable();
			 returns.put("no",rs.getString(1));
			 returns.put("name",rs.getString(2));
		 }
		 //returns=moveToEntities("com.wms.beans.dto.Code",executeSQL(con,stmt,sql));

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

  public static String holidayCheck(String date){
	  Connection con=null;
	  PreparedStatement ps = null;
	  ResultSet rs=null;
	  String returns = null;
	  try{
         String sql= " select C_DESCRPTION "
		            +" from calendar where c_date=? ";

		 con  = getConnection();
		 ps = con.prepareStatement(sql);
		 ps.setString(1,date);
		 rs=ps.executeQuery();
		 if(rs.next())
			 returns = rs.getString(1);

	  }catch(Exception e){e.printStackTrace();}
	  finally{
		  try{
			  if(rs!=null)rs.close();
			  if(ps!=null)ps.close();
			  if(con!=null)con.close();
		  }catch(Exception e){e.printStackTrace();}
	  }
	  return returns;
  }
  public static String toDate(){
	  Connection con=null;
	  PreparedStatement ps = null;
	  ResultSet rs=null;
	  String returns = null;
	  try{
         String sql= " select to_char(sysdate,'yyyy-mm-dd') "
		            +" from dual ";

		 con  = getConnection();
		 ps = con.prepareStatement(sql);
		 rs=ps.executeQuery();
		 if(rs.next())
			 returns = rs.getString(1);

	  }catch(Exception e){e.printStackTrace();}
	  finally{
		  try{
			  if(rs!=null)rs.close();
			  if(ps!=null)ps.close();
			  if(con!=null)con.close();
		  }catch(Exception e){e.printStackTrace();}
	  }
	  return returns;
  }

	/**
	 * Clob�� String���� �����Ѵ�.
	 */
  public static String ClobToString(Clob clob) throws java.sql.SQLException {
	if(clob == null) return null;
	java.io.BufferedReader bReader = new java.io.BufferedReader(clob.getCharacterStream());
	String strResult = new String("");
	String strBuffer;
	try {
	  while ((strBuffer = bReader.readLine()) != null)
		strResult = strResult + strBuffer + "\n";
	} catch(java.io.IOException ex) {
	  ex.printStackTrace();
	}
	return strResult;
  }      
	/**
	 * planCd ���� ������ �����Ѵ�.
	 */  
  public static String getPlanCd()throws Exception{
	  Connection con=null;
	  PreparedStatement ps = null;
	  ResultSet rs=null;
	  String returns = null;
	  try{
         String sql= "select to_char(sysdate,'yyyymmdd') "
                    +"||trim(to_char(to_number(substr(plancd,9,12))+1,'0000')) "
                    +"from " 
                    +"(select  plancd " 
                    +"    from longrangeplan "
                    +"        where  substr(plancd,1,8)= "
                    +"        to_char(sysdate,'yyyymmdd') "  
                    +"        order by plancd desc) "
                    +"where rownum=1 ";

		 con  = getConnection();
		 ps = con.prepareStatement(sql);
		 rs=ps.executeQuery();
		 if(rs.next())
			 returns = rs.getString(1);

	  }catch(Exception e){e.printStackTrace();}
	  finally{
		  try{
			  if(rs!=null)rs.close();
			  if(ps!=null)ps.close();
			  if(con!=null)con.close();
		  }catch(Exception e){e.printStackTrace();}
	  }
	  if(returns==null)returns= DateUtil.getTodayString()+"0001";
	  return returns;
  }
	/**
	 * Record�� ���� ó�� ���� �ľ�
	 * 1==��ϰ���(�ڷ� ����)
	 * 2==��������(�ڷ� ����)
	 * 3==ó���Ұ�(����(=����ó��) ����)
	 * @param  obj java.lang.Object ���ǰ��� ���� ��ü
	 * @param  tableName java.lang.String �ش����̺��
	 * @param  columnName java.lang.String ����Į����
	 */  
  public static int checkRec(Object obj,String tableName,String columnName )throws Exception{
	  Connection con=null;
	  PreparedStatement ps = null;
	  ResultSet rs=null;
	  String value=null;
	  int mode=1;//����
	  try{
         String sql= "select "
		            +columnName
					+" from "
					+tableName
                    +" "+whereSql(obj);
         System.out.println(sql);
		 con  = getConnection();
		 ps = con.prepareStatement(sql);
		 rs=ps.executeQuery();
		 if(rs.next()){
			 value = rs.getString(1);
			 if(value!=null&&!value.trim().equals("")){
				 mode=3;//����
			 }else{
				 mode=2;//�����ϴ� ���ڵ��� ���ϰ��� ����
			 }
		 }

	  }catch(Exception e){e.printStackTrace();}
	  finally{
		  try{
			  if(rs!=null)rs.close();
			  if(ps!=null)ps.close();
			  if(con!=null)con.close();
		  }catch(Exception e){e.printStackTrace();}
	  }
	  return mode;
  }
	/**
	 * Sequence ���� �������� �����Ѵ�.
	 */  
  public static int getNextSequence(Connection conn, String sequenceID) throws Exception {
	if(conn==null) return 0;
	int retval = 0;
	try {
		String sql = "select " + sequenceID + ".nextval from dual";
		Statement stmt = conn.createStatement();	  
		ResultSet rs = executeSQL(conn, stmt, sql);
		if(rs.next()==true)
			retval = rs.getInt(1);
		stmt.close();
	} catch(Exception e) { e.printStackTrace();  throw e;}
	return retval;
  }                                

 	/**
	 * Sequence ���� ���簪�� �����Ѵ�.
	 */
  public static int getCurSequence(Connection conn, String sequenceID) throws Exception {
	int retval = 0;
	try {
		String sql = "select " + sequenceID + ".curval from dual";
		Statement stmt = conn.createStatement();	  
		ResultSet rs = executeSQL(conn, stmt, sql);
		if(rs.next()==true)
			retval = rs.getInt(1);
		stmt.close();
	} catch(Exception e) { e.printStackTrace();  throw e;}
	return retval;
  }                              

  public static String replace(String src, String oldstr, String newstr)
  {
	  if (src == null)
		  return null;

	  StringBuffer dest = new StringBuffer("");
	  int  len = oldstr.length();
	  int  srclen = src.length();
	  int  pos = 0;
	  int  oldpos = 0;

	  while ((pos = src.indexOf(oldstr, oldpos)) >= 0) {
		  dest.append(src.substring(oldpos, pos));
		  dest.append(newstr);
		  oldpos = pos + len;
	  }

	  if (oldpos < srclen)
		  dest.append(src.substring(oldpos, srclen));

	  return dest.toString();
  }                

  public static String replaceEscape(String src)
  {
	String src1 = replace(src, "\n", "<br>");
	src1 = replace(src1," ", "&nbsp;");
	return src1;
  }
  
	public static Hashtable getStatusName(){
  		Connection con = null;
		Statement stmt=null;
		ResultSet rs = null;
		Hashtable returns = null;
		try{
			StringBuffer query = new StringBuffer();
			query.append("select statuscd,statusnm from status ");			
			System.out.println(query.toString());
			
			con = DataBaseUtil.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = DataBaseUtil.executeSQL(con,stmt,query.toString());
			if(rs!=null){
				returns = new Hashtable();
				while(rs.next()){
					returns.put(rs.getString("statusCd"),rs.getString("statusNm"));	
				}	
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
		return returns;	
	}   

	/**
	 * ResultSet�� ���� ��� ���ڵ���� Set��ü�� �ڵ� ġȯ���ش�.
	 * @return DataSet ��ü�� ����Ų��. 
	 * @param  rs java.sql.ResultSet  ���� ������ ���� ��ü
	 */ 
  public static DataSet moveToSet(ResultSet rs) throws Exception{
	  DataSet returns=null;
	  ResultSetMetaData rsmd=null;
	  int columnCount=0;
		try {
			rsmd=rs.getMetaData();
			columnCount=rsmd.getColumnCount();			
			rs.first();
			if(rs.last()){
				int length=rs.getRow();
				int idx=0;
				returns= new DataSet();
				for(int i=1;i<=columnCount;i++){
					returns.put(rsmd.getColumnName(i),new Object[length]);
				}						
				
				rs.first();					
				
				do{
					for(int i=1;i<=columnCount;i++){						
						if (rsmd.getColumnTypeName(i).equals("CLOB")){
							Array.set(returns.getObj(rsmd.getColumnName(i)), idx, DataBaseUtil.ClobToString(rs.getClob(i))) ;
						}else{
							Array.set(returns.getObj(rsmd.getColumnName(i)), idx, rs.getString(i)) ;
						}
						//System.out.print("::"+rsmd.getColumnName(i)+"::"+returns.get(rsmd.getColumnName(i),idx));						
					}	
					//System.out.println("==================");
					idx++;
				}while(rs.next());												
			}		
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return returns;
  } // end moveToEntities

		/**
		 * ���� ����ϴ� DataBase ��ƼƼ ����� �����Ѵ�.
		 * @return DataSet ��ƼƼ���
	
		 */ 
	public static DataSet getEntitySet()throws Exception{		
			if(entitySet==null)
				entitySet=DataBaseUtil.getEntitySet(null,(new Configuration()).get("com.wms.db.id"));							
			return entitySet;
	}
		/**
		 * ���� ����ϴ� DataBase ��ƼƼ ����� �����Ѵ�.
		 * @return DataSet ��ƼƼ���
		 * @param  user String  ����ϴ� DBUser��
		 */ 
	public static DataSet getEntitySet(String user) throws Exception{
		  return getEntitySet(null,user);
	}
	
	
		/**
		 * DataBase ��ƼƼ ����� �����Ѵ�.
		 * @return DataSet ��ƼƼ���
		 * @param  user String  ����ϴ� DBUser��
		 * @param  jndi String  ����ϴ� DataSource��
		 */ 
	
	/*
	CONTRAINT�� ã�� FUNCTION
	CREATE OR REPLACE function FNC_GET_CONSTRAINT(
	p_TABLE_NAME  IN SYS.USER_CONS_COLUMNS.TABLE_NAME%TYPE,
	p_CONSTRAINT_NAME IN SYS.USER_CONS_COLUMNS.CONSTRAINT_NAME%TYPE
	)
	return VARCHAR2 is
	Result VARCHAR2(1);
	
	begin
	 IF p_TABLE_NAME IS NULL THEN
	    return(NULL) ;
	 END IF ;
	 
	     SELECT CONSTRAINT_TYPE
	      INTO Result
	      FROM user_constraints
	     WHERE TABLE_NAME  = p_TABLE_NAME
	       AND CONSTRAINT_NAME = p_CONSTRAINT_NAME;
	 
	 return(Result) ;
	
	EXCEPTION 
	 WHEN NO_DATA_FOUND THEN
	      return(NULL);
	 WHEN OTHERS THEN
	      return(NULL);  
	end FNC_GET_CONSTRAINT;
	/
	
			//----------DATABASE META SQL---------------S001
			select T.TABLE_NAME,T.COLUMN_NAME,
				   DECODE(PK,1,1,0) ISPK,
					 --java.sql.Types ����
					CASE WHEN T.DATA_TYPE='VARCHAR2' THEN
						 12
						 WHEN T.DATA_TYPE='NUMBER' THEN
						 4
						 WHEN T.DATA_TYPE='DATE' THEN
						 91
						 WHEN T.DATA_TYPE='CLOB' THEN
						 2005
						 WHEN T.DATA_TYPE='CHAR' THEN
						 1
						 WHEN T.DATA_TYPE='LONG' THEN
						 -4
						 ELSE 1111
					END
				   DATA_TYPE,T.DATA_LENGTH
			from sys.user_tab_COLUMNS T,
			( SELECT A.TABLE_NAME,A.COLUMN_NAME,1 PK
				FROM ( select TABLE_NAME,
							  COLUMN_NAME,
							  FNC_GET_CONSTRAINT(TABLE_NAME,CONSTRAINT_NAME) CON_TYPE
							  from sys.user_cons_columns) A
					   WHERE A.CON_TYPE = 'P') C
			WHERE  T.TABLE_NAME = C.TABLE_NAME(+)
			  AND T.COLUMN_NAME = C.COLUMN_NAME(+)
			ORDER BY T.TABLE_NAME,T.COLUMN_NAME
	
	      --function ���� ��밡���� query
			select T.TABLE_NAME,T.COLUMN_NAME,
				   DECODE(PK,1,1,0) ISPK,
					 --java.sql.Types ����
					CASE WHEN T.DATA_TYPE='VARCHAR2' THEN
						 12
						 WHEN T.DATA_TYPE='NUMBER' THEN
						 4
						 WHEN T.DATA_TYPE='DATE' THEN
						 91
						 WHEN T.DATA_TYPE='CLOB' THEN
						 2005
						 WHEN T.DATA_TYPE='CHAR' THEN
						 1
						 WHEN T.DATA_TYPE='LONG' THEN
						 -4
						 ELSE 1111
					END
				   DATA_TYPE,T.DATA_LENGTH
			from sys.user_tab_COLUMNS T,
			( SELECT A.TABLE_NAME,A.COLUMN_NAME,1 PK
				FROM ( select TABLE_NAME,
							  COLUMN_NAME,
							  	(SELECT CONSTRAINT_TYPE
	      							FROM user_constraints
	     							 WHERE TABLE_NAME  = sys.user_cons_columns.TABLE_NAME
	       						 AND CONSTRAINT_NAME = sys.user_cons_columns.CONSTRAINT_NAME)
			  						 CON_TYPE
							  from sys.user_cons_columns) A
					   WHERE A.CON_TYPE = 'P') C
			WHERE  T.TABLE_NAME = C.TABLE_NAME(+)
			  AND T.COLUMN_NAME = C.COLUMN_NAME(+)
			ORDER BY T.TABLE_NAME,T.COLUMN_NAME
	
	
	*/
	public static DataSet getEntitySet(String jndi,String user) throws Exception{
			Connection con=null;
			Statement stmt =null;
			ResultSet rs   =null;
			DataSet tnameTable=null;
			DataSet columnTable=null;		
			try{
		        HashMap hm        = SQLXmlDAO.loadRequestMappings(getXmlDir()+"/Common.xml");
				SQLMapping sm	  = (SQLMapping)hm.get("S_001");
		        String sql        = sm.toSql();            
				con=(jndi==null)?DataBaseUtil.getConnection():DataBaseUtil.getConnection(jndi);
				stmt=con.createStatement();
				rs=stmt.executeQuery(sql);
				
				String table_Name=null;
				String column_Name=null;
				if(rs.next()){
					tnameTable = new DataSet("ENTITY_SET");
					do{
						table_Name=rs.getString("TABLE_NAME");
						column_Name=rs.getString("COLUMN_NAME");
						if(tnameTable.getObj(table_Name)==null){
							tnameTable.put(table_Name,new DataSet(table_Name));
						}					
						columnTable=(DataSet)tnameTable.getObj(table_Name);
						columnTable.put(column_Name,new ColumnDTO(
													   table_Name,
							                           column_Name,
													   rs.getInt("ISPK"),	
													   rs.getInt("DATA_TYPE"),
													   rs.getInt("DATA_LENGTH")));
						
					}while(rs.next());
				}					
			}catch(Exception e){Logger.err.println(com.wms.fw.Utility.getStackTrace(e));}
			finally{
				try{
					if(rs!=null)rs.close();
					if(stmt!=null)stmt.close();
					if(con!=null)con.close();
				}catch(Exception e){Logger.err.println(com.wms.fw.Utility.getStackTrace(e));}
			}
			return tnameTable;
	}
		/**
		 * sql�� �����ϴ� xml��θ� �����Ѵ�.
		 * @return String �������
		 */ 
	public static String getXmlDir() throws Exception{
		  if(xmlDir==null) xmlDir = (new Configuration()).get("com.wms.fw.sql.dir");
		  return xmlDir;
	}
	
		/**
		 * ���ø����̼ǿ����� ���������� �����Ѵ�. 
		 * @return int sequence��
		 * @param  tableName String  ����ϴ� ��ƼƼ��
		 * @param  columnName String  ����ϴ� �Ӽ���
		 * @param  jndi String  ����ϴ� DataSource��
		 */ 
	public static long getSequence(String tableName,String columnName,DataSet ds,String jndi) throws Exception{
			 long returns=-1;
			 Connection con=null;
			 Statement stmt =null;
			 ResultSet rs   =null;
			 try{
				  Object value=null;
				  if(sequenceList==null) sequenceList = new Hashtable();
				  String key=tableName;
				  HashMap hm=null;
				  SQLMapping sm=null;
				  value=sequenceList.get(key);
				  if(value==null){
						hm        = SQLXmlDAO.loadRequestMappings(getXmlDir()+"/Common.xml");
						if(columnName!=null){							
								sm	  = (SQLMapping)hm.get("S_002");
								sm.set(1,columnName);
								sm.set(2,tableName);
						}else{
								sm	  = (SQLMapping)hm.get(key.toUpperCase());
						}
						String sql        = sm.toSql();            
						Logger.dbwrap.println(sql);
						con=(jndi==null)?DataBaseUtil.getConnection():DataBaseUtil.getConnection(jndi);
						stmt=con.createStatement();
	
						rs=stmt.executeQuery(sql);
						
						System.out.println("DataBaseUtil.rs::>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+rs);
						
						if(rs.next()){
							
							value=new Long(rs.getLong(1)+1);

						}else{
							
							new Exception(tableName+"."+columnName+" MAX���� �������� ���߽��ϴ�.");
						}
						rs.close();
						stmt.close();
						con.close();
						sequenceList.put(key,value);
				  }
				  returns=((Long)value).longValue();
				  sequenceList.put(key,new Long(returns+1));
				  
			 }catch(Exception e){
				 Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
			 }
			 finally{
				try{
					if(rs!=null)rs.close();
					if(stmt!=null)stmt.close();
					if(con!=null)con.close();
				}catch(Exception e){Logger.err.println(com.wms.fw.Utility.getStackTrace(e));}
			 }
			 return returns;
	}
		/**
		 * ���ø����̼ǿ����� ���������� �����Ѵ�. 
		 * @return int sequence��
		 * @param  tableName String  ����ϴ� ��ƼƼ��
		 */ 
	public static long getSequence(String tableName) throws Exception{
			 return getSequence( tableName,null,null,null);
	}
		/**
		 * ���ø����̼ǿ����� ���������� �����Ѵ�. 
		 * @return int sequence��
		 * @param  tableName String  ����ϴ� ��ƼƼ��
		 * @param  columnName String  ����ϴ� �Ӽ���
		 */ 
	public static long getSequence(String tableName,String columnName) throws Exception{
			 return getSequence( tableName, columnName,null,null);
	}
		/**
		 * ���ø����̼ǿ����� ���������� �����Ѵ�. 
		 * @return int sequence��
		 * @param  tableName String  ����ϴ� ��ƼƼ��
		 * @param  columnName String  ����ϴ� �Ӽ���
		 * @param  ds DataSet  ������
		 */ 
	public static long getSequence(String tableName,String columnName,DataSet ds) throws Exception{
			 return getSequence( tableName, columnName,ds,null);
	}

	public static String getStr(String SqlID) throws Exception{
		 String  returns="";
		 Connection con=null;
		 Statement stmt =null;
		 ResultSet rs   =null;
		 try{
			    HashMap hm        = SQLXmlDAO.loadRequestMappings(getXmlDir()+"/Common.xml");
				SQLMapping sm	  = (SQLMapping)hm.get(SqlID);
		        String sql        = sm.toSql();  
		        
		     	
				con=DataBaseUtil.getConnection();
				stmt=con.createStatement();
				rs=stmt.executeQuery(sql);
				
				if(rs.next()){
					returns=rs.getString("CDDS");
				}
				
		 }catch(Exception e){
			 Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
		 }
		 finally{
			try{
				if(rs!=null)rs.close();
				if(stmt!=null)stmt.close();
				if(con!=null)con.close();
			}catch(Exception e){Logger.err.println(com.wms.fw.Utility.getStackTrace(e));}
		 }
		 return returns;
}
	
	public static String getStrSeq(String SqlID) throws Exception{
		 String  returns="";
		 Connection con=null;
		 Statement stmt =null;
		 ResultSet rs   =null;
		 try{
//			 System.out.println("getStr returns1111 ::"+ returns);
		        HashMap hm        = SQLXmlDAO.loadRequestMappings(getXmlDir()+"/Common.xml");
				SQLMapping sm	  = (SQLMapping)hm.get(SqlID);
		        String sql        = sm.toSql();  		        
		        
//		        System.out.println("getStr sql  ::"+ sql);
		        
		        Logger.dbwrap.println(sql);
				con=DataBaseUtil.getConnection();
				stmt=con.createStatement();
				rs=stmt.executeQuery(sql);
				
//				System.out.println("getStr rs  ::"+ rs);
				
				if(rs.next()){
					returns=rs.getString(1);
				}
				
//				System.out.println("getStr returns ::"+ returns);
				
		 }catch(Exception e){
			 Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
		 }
		 finally{
			try{
				if(rs!=null)rs.close();
				if(stmt!=null)stmt.close();
				if(con!=null)con.close();
			}catch(Exception e){Logger.err.println(com.wms.fw.Utility.getStackTrace(e));}
		 }
		 return returns;
}
	
};
