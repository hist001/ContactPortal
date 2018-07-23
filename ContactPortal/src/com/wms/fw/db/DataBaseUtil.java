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
	 * Connection객체를 리턴한다.
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
	 * Connection객체를 리턴한다.
	 * @return java.sql.Connection
	 * @param jndiName java.lang.String  JNDI의 대상이 되는 서비스명
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
	 * 검색을 실행 , 결과를 리턴한다.
	 * @return java.sql.ResultSet
	 * @param con java.sql.Connection 
	 * @param stmt java.sql.Statement 
	 * @param strSQL java.lang.String  SELECT할 검색 QUERY
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
	 * 검색을 실행 , 결과를 리턴한다.
	 * @return java.sql.ResultSet
	 * @param con java.sql.Connection 
	 * @param pstmt java.sql.PreparedStatement 
	 * @param sql java.lang.String  SELECT할 검색 QUERY
	 * @param params Enumeration  검색 조건값들
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
		  throw new SQLException("pstmt입력중 에러발생");
		i++;
	  }
	}
	rs = pstmt.executeQuery();
	return rs;
  }                      
	/**
	 * 실행 , 결과를 리턴한다.
	 * @return int 실행 결과 , 적용된 레코드의 갯수
	 * @param con java.sql.Connection 
	 * @param sql java.lang.String  실행할 QUERY
	 * @param params Enumeration  적용될 값들
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
		   throw new SQLException("pstmt입력중 에러발생");
		}
		else if(obj instanceof java.io.File) {
		  File file = (File) obj;
		  java.io.InputStream fin = new java.io.FileInputStream(file);
		  pstmt.setBinaryStream (i, fin, (int) file.length());
		}
		else
		   throw new SQLException("pstmt입력중 에러발생");
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
		   throw new SQLException("ps입력중 에러발생");
		}
		else if(obj instanceof java.io.File) {
		  File file = (File) obj;
		  java.io.InputStream fin = new java.io.FileInputStream(file);
		  ps.setBinaryStream (i, fin, (int) file.length());
		}
		else
		   throw new SQLException("ps입력중 에러발생");
		i++;
	  }
	}
  }      
	/**
	 * 적용할 값들을 3가지로 분리 리턴한다.
	 * COL_NAMES 이름들을 String으로 가진다. 구분자는 ',' 
	 * COL_VALUE 이름의 갯수 만큼 '?'를 가진다. 구분자는 ','
	 * VALUE_LIST 실제 값들을 Enumeration으로 저장한다.
	 * @return java.util.Hashtable 
	 * @param dto com.wms.fw.DTO  실제 값을 가진 객체
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
	 * SQLQuery문중 where문을 자동으로 만들어준다.
	 * 단, 다중검색인 경우 and로 연결되고, '='로 만 검색할 수 있다.
	 * @return java.lang.String 
	 * @param obj java.lang.Object 실제 값을 가진 객체
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
	 * ResultSet이 가진 모든 레코드들을 객체배열로 자동 치환해준다.
	 * @return java.lang.Object 객체의 배열을 가리킨다. 
	 * @param  className java.lang.String 치환하게될 객체의 타입
	 * @param  rs java.sql.ResultSet  실제 값들을 가진 객체
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
	 * ResultSet이 가진 모든 레코드들을 객체배열로 자동 치환해준다.
	 * @return java.lang.Object 객체의 배열을 가리킨다. 
	 * @param  keyName java.lang.String 구분되어지는 field
	 * @param  className java.lang.String 치환하게될 객체의 타입
	 * @param  rs java.sql.ResultSet  실제 값들을 가진 객체
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
	 * ResultSet이 가진 컬럼의 이름을 리턴한다.
	 * @return java.util.ArrayList 컬럼의 이름들. 
	 * @param  rs java.sql.ResultSet  실제 값들을 가진 객체
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
	 * 여러개의 sub객체를 가진 여러개의 super객체를 리턴한다.
	 * @return java.util.ArrayList super객체. 
	 * @param  keyName java.lang.String 구분되어지는 field
	 * @param  superName java.lang.String 상위객체의 클래스
	 * @param  subName java.lang.String 하위객체의 클래스
	 * @param  rs java.sql.ResultSet  실제 값들을 가진 객체
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
	 * 객체에 array를 등록시킨다.
	 * @param  entity java.lang.Object array를 담을 객체
	 * @param  values java.lang.Object array를 가리키는 객체
	 * 
	 */ 
   public static void setArrayField(Object entity,Object values,String subName) throws Exception{
		try {
			Class c = entity.getClass();
			java.lang.reflect.Field[] field = c.getFields();
			for (int i=0 ; i<field.length; i++) {
							
				Class type = field[i].getType();
				if ( type.isArray()&&type.getName().equals("[L"+subName+";")) {	//배열인 경우 타입명 앞뒤에 '[L', ';'두 문자가 붙는다.		
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
	 * 모든 소분류 코드들을 리턴한다..
	 * @return com.wms.beans.dto.Code[] Code들의 정보. 
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
	 * 통합검색..
	 * @return Hashtable 이름과 코드정보. 
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
	 * Clob을 String으로 리턴한다.
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
	 * planCd 값의 다음값 리턴한다.
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
	 * Record에 대한 처리 여부 파악
	 * 1==등록가능(자료 없음)
	 * 2==수정가능(자료 있음)
	 * 3==처리불가(기준(=승인처리) 있음)
	 * @param  obj java.lang.Object 조건값을 지닌 객체
	 * @param  tableName java.lang.String 해당테이블명
	 * @param  columnName java.lang.String 기준칼럼명
	 */  
  public static int checkRec(Object obj,String tableName,String columnName )throws Exception{
	  Connection con=null;
	  PreparedStatement ps = null;
	  ResultSet rs=null;
	  String value=null;
	  int mode=1;//없음
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
				 mode=3;//정상
			 }else{
				 mode=2;//실제하는 레코드의 리턴값이 없음
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
	 * Sequence 값의 다음값을 리턴한다.
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
	 * Sequence 값의 현재값을 리턴한다.
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
	 * ResultSet이 가진 모든 레코드들을 Set객체로 자동 치환해준다.
	 * @return DataSet 객체를 가리킨다. 
	 * @param  rs java.sql.ResultSet  실제 값들을 가진 객체
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
		 * 현재 사용하는 DataBase 엔티티 목록을 리턴한다.
		 * @return DataSet 엔티티목록
	
		 */ 
	public static DataSet getEntitySet()throws Exception{		
			if(entitySet==null)
				entitySet=DataBaseUtil.getEntitySet(null,(new Configuration()).get("com.wms.db.id"));							
			return entitySet;
	}
		/**
		 * 현재 사용하는 DataBase 엔티티 목록을 리턴한다.
		 * @return DataSet 엔티티목록
		 * @param  user String  사용하는 DBUser명
		 */ 
	public static DataSet getEntitySet(String user) throws Exception{
		  return getEntitySet(null,user);
	}
	
	
		/**
		 * DataBase 엔티티 목록을 리턴한다.
		 * @return DataSet 엔티티목록
		 * @param  user String  사용하는 DBUser명
		 * @param  jndi String  사용하는 DataSource명
		 */ 
	
	/*
	CONTRAINT를 찾는 FUNCTION
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
					 --java.sql.Types 기준
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
	
	      --function 없이 사용가능한 query
			select T.TABLE_NAME,T.COLUMN_NAME,
				   DECODE(PK,1,1,0) ISPK,
					 --java.sql.Types 기준
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
		 * sql를 저장하는 xml경로를 리턴한다.
		 * @return String 경로정보
		 */ 
	public static String getXmlDir() throws Exception{
		  if(xmlDir==null) xmlDir = (new Configuration()).get("com.wms.fw.sql.dir");
		  return xmlDir;
	}
	
		/**
		 * 어플리케이션에서의 시퀀스값을 제공한다. 
		 * @return int sequence값
		 * @param  tableName String  사용하는 엔티티명
		 * @param  columnName String  사용하는 속성명
		 * @param  jndi String  사용하는 DataSource명
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
							
							new Exception(tableName+"."+columnName+" MAX값을 가져오지 못했습니다.");
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
		 * 어플리케이션에서의 시퀀스값을 제공한다. 
		 * @return int sequence값
		 * @param  tableName String  사용하는 엔티티명
		 */ 
	public static long getSequence(String tableName) throws Exception{
			 return getSequence( tableName,null,null,null);
	}
		/**
		 * 어플리케이션에서의 시퀀스값을 제공한다. 
		 * @return int sequence값
		 * @param  tableName String  사용하는 엔티티명
		 * @param  columnName String  사용하는 속성명
		 */ 
	public static long getSequence(String tableName,String columnName) throws Exception{
			 return getSequence( tableName, columnName,null,null);
	}
		/**
		 * 어플리케이션에서의 시퀀스값을 제공한다. 
		 * @return int sequence값
		 * @param  tableName String  사용하는 엔티티명
		 * @param  columnName String  사용하는 속성명
		 * @param  ds DataSet  조건절
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
