/*************************************************************
*	파 일 명  : MakeQueryHelper.java
*	작성일자  : 2004/06/22
*	작 성 자  : 
*	내    용  : 자동으로 Query를 생성하는 Helper 클래스
*************************************************************/
package com.wms.beans;
import com.wms.fw.*;
import com.wms.fw.db.ColumnDTO;
import com.wms.beans.dto.*;
import com.wms.common.beans.dto.DataSet;

import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;
public class MakeQueryHelper
{
	//flag=> 히스토리의 구분값
	//R==개별업무보고등록
	//U==개별업무보고수정
	//T==합동업무보고등록
	//C==업무보고승인시수정
	//W==임시주간업무보고확정시
	//K==임시주간업무보고확정수정시
	//F==확정주간업무수정시
	//Y==일괄임시주간업무보고확정시
	public static final String POINT=";";    //추출할 때의 구분자
    //날자의 값을 판단 거기에 맞는 날자 관련 Query를 생성
	public static String makeDtm(String date,String dateName,int mode){
		String returns="sysdate";
		//mode=1은 insert
		//mode=2는 update
		if(mode==1&&dateName.equals("apDtm")){
			if(date==null||date.trim().equals(""))
			return null;
		}
		if(date==null||date.trim().equals("")||date.trim().equals("null"))
			return returns;

		return " to_date('"+date.trim()+"','yyyy-mm-dd hh24:mi:ss') ";

	}
    //공통의 Insert Query 를 자동으로 생성
	public static String makeInsertQuery(Object obj,String tableName)throws Exception{
		
//		System.out.println(" ==============> 1");

		StringBuffer sql = new StringBuffer(" insert into  "+tableName+"(");
		String preFix=" ";
		StringBuffer values= new StringBuffer(" values(");
		Class c = obj.getClass();
		java.lang.reflect.Field[] field = c.getFields();
		for (int i=0 ; i<field.length; i++) {										
			String fieldtype = field[i].getType().getName();
			String fieldname = field[i].getName();
			
			if (fieldname.equals("jobView")|| fieldname.equals("prcsNoView")){				
				
			}else{
				
				if(field[i].get(obj)!=null){
				    String value=field[i].get(obj)+"";
					if ( fieldtype.equals("java.lang.String")) {
								sql.append(preFix);
								sql.append(fieldname);
							if(fieldname.indexOf("Dtm")>0&&fieldname.indexOf("Dtm")==(fieldname.length()-3)){
								values.append(preFix);
								values.append(makeDtm(value,fieldname,1));
							}else{
								values.append(preFix);
								values.append("'"+value.trim()+"'");
							}
	
					}else if ( !field[i].getType().isArray()&&!(field[i].get(obj) instanceof DTO)) {
	
						sql.append(preFix);
						sql.append(fieldname);
						values.append(preFix);
						values.append(value.trim());
						
					}
					preFix=",";
				}

			}//else end 
		}
		sql.append(")");
		sql.append(values.toString());
		sql.append(")");
		return sql.toString();
	}
    //공통의 Insert Query 를 자동으로 생성
	public static String makeInsertQuery(Object obj,Hashtable columnList,String tableName)throws Exception{
//		System.out.println(" ==============> 2");
		
		StringBuffer sql = new StringBuffer(" insert into  "+tableName+"(");
		String preFix=" ";		
		String unknow_Val="'";
		boolean columnFlag=(columnList==null)?false:true;						
		StringBuffer values= new StringBuffer(" values(");
		Class c = obj.getClass();
		java.lang.reflect.Field[] field = c.getFields();
		for (int i=0 ; i<field.length; i++) {										
			String fieldtype = field[i].getType().getName();
			String fieldname = field[i].getName();
			if(field[i].get(obj)!=null){
			    String value=field[i].get(obj)+"";

				if ( fieldtype.equals("java.lang.String")) {
					sql.append(preFix);
					sql.append(fieldname);														
					values.append(preFix);
					if(columnFlag){
					unknow_Val=(columnList.get(fieldname)!=null)?
								(String)columnList.get(fieldname):"'";							        
					}
					values.append(unknow_Val);
					values.append(value.trim());
					values.append(unknow_Val);
					preFix=",";	

				}else if ( !field[i].getType().isArray()&&!(field[i].get(obj) instanceof DTO)) {
					sql.append(preFix);
					sql.append(fieldname);
					values.append(preFix);
					values.append(value.trim());
					preFix=",";	
				}
				
			}

		}
		sql.append(")");
		sql.append(values.toString());
		sql.append(")");
		return sql.toString();
	}
   /**
	 *공통의 Update Query 문 자동 생성
	 * @return java.lang.String 
	 * @param obj java.lang.Object 실제 값을 가진 객체
	 * @param columnList java.util.Hashtable 실제값의 형식을 지정한객체 기본은 문자형
	 * @param tableName java.lang.String 적용할 table명
	 */
	public static String makeUpdateQuery(Object obj,Hashtable columnList,String tableName)throws Exception{
//		System.out.println(" ==============> 3");
		StringBuffer sql = new StringBuffer(" update  "+tableName+" set ");
		String preFix=" ";
		String unknow_Val="'";
		boolean columnFlag=(columnList==null)?false:true;						

		Class c = obj.getClass();
		java.lang.reflect.Field[] field = c.getFields();
		for (int i=0 ; i<field.length; i++) {										
			String fieldtype = field[i].getType().getName();
			String fieldname = field[i].getName();
			if(field[i].get(obj)!=null){
				String value=field[i].get(obj)+"";			
				if ( fieldtype.equals("java.lang.String")) {
					if(!value.trim().equals("")){
						sql.append(preFix);
						sql.append(fieldname);
						sql.append("=");
						if(columnFlag){
						unknow_Val=(columnList.get(fieldname)!=null)?
									(String)columnList.get(fieldname):"'";							        
						}
						sql.append(unknow_Val);
						sql.append(value.trim());
						sql.append(unknow_Val);
						preFix=",";	
					}

				}else if ( !field[i].getType().isArray()&&!(field[i].get(obj) instanceof DTO)) {					
					sql.append(preFix);
					sql.append(fieldname);
					sql.append("=");
					sql.append(value.trim());
					preFix=",";	
				}
				
			}

		}
		return sql.toString();
	}

	//추가 2006-06-26
    //공통의 Insert Query 를 자동으로 생성
	public static String makeDeclaredInsertQuery(Object obj,Hashtable columnList,String tableName)throws Exception{
		
//		System.out.println("==============> 4");
		
		return makeDeclaredInsertQuery(obj, columnList, tableName, null);		
	}
	public static String makeDeclaredInsertQuery(Object obj,Hashtable columnList,String tableName,String objectName)throws Exception{

		
		StringBuffer sql = new StringBuffer(" insert into  "+tableName+"(");
		String preFix=" ";		
		String unknow_Val="'";
		boolean columnFlag=(columnList==null)?false:true;						
		StringBuffer values= new StringBuffer(" values(");
		Class c = null;
		if(objectName==null)c=obj.getClass();
		else c=Class.forName(objectName);
		java.lang.reflect.Field[] field = c.getDeclaredFields();
		for (int i=0 ; i<field.length; i++) {										
			String fieldtype = field[i].getType().getName();
			String fieldname = field[i].getName();
			if(field[i].get(obj)!=null){
			    String value=field[i].get(obj)+"";

				if ( fieldtype.equals("java.lang.String")) {
					sql.append(preFix);
					sql.append(fieldname);														
					values.append(preFix);
					if(columnFlag){
					unknow_Val=(columnList.get(fieldname)!=null)?
								(String)columnList.get(fieldname):"'";							        
					}
					values.append(unknow_Val);
					values.append(value.trim());
					values.append(unknow_Val);
					preFix=",";	

				}else if ( !field[i].getType().isArray()&&!(field[i].get(obj) instanceof DTO)) {
					sql.append(preFix);
					sql.append(fieldname);
					values.append(preFix);
					values.append(value.trim());
					preFix=",";	
				}
				
			}

		}
		sql.append(")");
		sql.append(values.toString());
		sql.append(")");
		return sql.toString();
	}
   /**
	 *공통의 Update Query 문 자동 생성
	 * @return java.lang.String 
	 * @param obj java.lang.Object 실제 값을 가진 객체
	 * @param columnList java.util.Hashtable 실제값의 형식을 지정한객체 기본은 문자형
	 * @param tableName java.lang.String 적용할 table명
	 */
	public static String makeDeclaredUpdateQuery(Object obj,Hashtable columnList,String tableName)throws Exception{
//		System.out.println(" ==============> 5");
		return makeDeclaredUpdateQuery(obj,columnList,tableName,null);
	}
	public static String makeDeclaredUpdateQuery(Object obj,Hashtable columnList,String tableName,String objectName)throws Exception{
//		System.out.println(" ==============> 6");
		StringBuffer sql = new StringBuffer(" update  "+tableName+" set ");
		String preFix=" ";
		String unknow_Val="'";
		boolean columnFlag=(columnList==null)?false:true;						

		Class c = null;
		if(objectName==null)c=obj.getClass();
		else c=Class.forName(objectName);
			
		java.lang.reflect.Field[] field = c.getDeclaredFields();
		for (int i=0 ; i<field.length; i++) {										
			String fieldtype = field[i].getType().getName();
			String fieldname = field[i].getName();
			if(field[i].get(obj)!=null){
				String value=field[i].get(obj)+"";			
				if ( fieldtype.equals("java.lang.String")) {
					if(!value.trim().equals("")){
						sql.append(preFix);
						sql.append(fieldname);
						sql.append("=");
						if(columnFlag){
						unknow_Val=(columnList.get(fieldname)!=null)?
									(String)columnList.get(fieldname):"'";							        
						}
						sql.append(unknow_Val);
						sql.append(value.trim());
						sql.append(unknow_Val);
					}

				}else if ( !field[i].getType().isArray()&&!(field[i].get(obj) instanceof DTO)) {					
					sql.append(preFix);
					sql.append(fieldname);
					sql.append("=");
					sql.append(value.trim());
				}
				preFix=",";	
			}

		}
		return sql.toString();
	}

	
	
	//기본자료 관련 Insert Query를 자동으로 생성
	public static String makeInsertQuery(BasicDataDTO dto)throws Exception{
//		System.out.println(" ==============> 7");
		String tableName=dto.get("tableName");
		dto.remove("tableName");
		StringBuffer sql = new StringBuffer(" insert into  "+tableName+"(");
		String preFix=" ";
		StringBuffer values= new StringBuffer(" values(");
		Enumeration keys = dto.keys();
		while (keys.hasMoreElements()) {										
			//String fieldtype = field[i].getType().getName();
			String fieldName = (String)keys.nextElement();
			if((fieldName.substring(0,1)).equals("_")){
				dto.remove(fieldName);
			}
			String value=dto.get(fieldName);
			if(!value.trim().equals("")){
				sql.append(preFix);
				sql.append(fieldName);
				values.append(preFix);
				if(fieldName.indexOf("Dtm")>0&&fieldName.indexOf("Dtm")==(fieldName.length()-3)){
						values.append(makeDtm(value,fieldName,1));
				}else{
						values.append("'"+value.trim()+"'");
				}

				preFix=",";	
			}

		}
		sql.append(")");
		sql.append(values.toString());
		sql.append(")");
		return sql.toString();
	}
    //공통의 Update Query 문 자동 생성
	public static String makeUpdateQuery(Object obj,String tableName)throws Exception{
//		System.out.println(" ==============> 8");
		StringBuffer sql = new StringBuffer(" update  "+tableName+" set ");
		String preFix=" ";
		Class c = obj.getClass();
		java.lang.reflect.Field[] field = c.getFields();
		for (int i=0 ; i<field.length; i++) {										
			String fieldtype = field[i].getType().getName();
			String fieldname = field[i].getName();
			if(field[i].get(obj)!=null){
				String value=field[i].get(obj)+"";			
				boolean flag=false;
				if ( fieldtype.equals("java.lang.String")) {
                    flag=true;
					value="'"+value.trim()+"'";

				}else if ( !field[i].getType().isArray()&&!(field[i].get(obj) instanceof DTO)) {
                    flag=true;
				}
				if(flag){
					sql.append(preFix);
					sql.append(fieldname);
					sql.append("=");
					sql.append(value.trim());
					preFix=",";	
				}
			}

		}
		return sql.toString();
	}
    //기본자료 관련 Update Query 자동 생성
	public static String makeUpdateQuery(BasicDataDTO dto,String[] pks)throws Exception{
//		System.out.println(" ==============> 9");
		String tableName = dto.get("tableName");
		dto.remove("tableName");
		StringBuffer sql = new StringBuffer("update  "+tableName+" set ");
		Enumeration keys = dto.keys();
		System.out.println(dto);
		String preFix1 = " ";
		while(keys.hasMoreElements()){
			/*
			if((fieldName.substring(0,1)).equals("_")){
				dto.remove(fieldName);
			}*/
			String fieldName = (String)keys.nextElement();
			String value=dto.get(fieldName);		
			if(!value.trim().equals("")){
				sql.append(preFix1);
				sql.append(fieldName);
				sql.append("=");
				if(fieldName.indexOf("Dtm")>0&&fieldName.indexOf("Dtm")==(fieldName.length()-3)){
						sql.append("sysdate");
				}else{
						sql.append("'");sql.append(value);sql.append("'");
				}
				preFix1 = ", ";
			}
		}
		String preFix2=" where ";
		if(pks.length>0){
			for(int i=0 ; i<pks.length ; i++){
				sql.append(preFix2);sql.append(pks[i]);sql.append("='");sql.append(dto.get(pks[i]));sql.append("' ");
				preFix2 = " and ";
			}			
		}else{
			throw new Exception("update 절에 where 구문이 없습니다."); 
		}
		return sql.toString();
	}
    //기본 자료 관련 Delete Query 자동 생성
	public static String makeDeleteQuery(BasicDataDTO dto,String[] pks)throws Exception{
//		System.out.println(" ==============> 10");
		String tableName = dto.get("tableName");
		dto.remove("tableName");
		StringBuffer sql = new StringBuffer("delete "+tableName+" ");
		String preFix = "where ";
		if(pks.length>0){
			for(int i=0 ; i<pks.length ; i++){
				sql.append(preFix);sql.append(pks[i]);sql.append("='");sql.append(dto.get(pks[i]));sql.append("' ");
				preFix =" and ";
			}
		}else{
			throw new Exception("delete 절에 where 구문이 없습니다.");
		}
		return sql.toString();
	}
	//기본 자료 관련 Select Query 자동 생성					 
	public static String makeSelectQuery(BasicDataDTO dto)throws Exception{
//		System.out.println(" ==============> 11");
		String tableName = dto.get("tableName");
		dto.remove("tableName");
		Enumeration keys = dto.keys();
		String fieldName=null;
        String value=null;
		String preFix=" where ";
		String sql="";
		while(keys.hasMoreElements()){
			fieldName = (String)keys.nextElement(); 
			/*
			if((fieldName.substring(0,1)).equals("_")){
				dto.remove(fieldName);
			}*/
			value=dto.get(fieldName);
			if(!value.trim().equals("")){
                sql+=preFix+fieldName+" like '"+value+"' ";
				preFix=" and ";
			}
		}
		sql="select * from "+tableName+" "+sql+" order by 1,2";
		return sql;
	}

	//history query의 이력 내용 추가
	public static String makeHistory(Object obj,String str)throws Exception{
//		System.out.println(" ==============> 12");
		StringBuffer returns = new StringBuffer();
		String preFix=" ";
		Class c = obj.getClass();
		java.lang.reflect.Field[] field = c.getFields();
		for (int i=0 ; i<field.length; i++) {	
			if(!field[i].getType().isArray()&&!(field[i].get(obj) instanceof DTO) ){
				String fieldtype = field[i].getType().getName();
				String fieldname = field[i].getName();
				String value=field[i].get(obj)+"";
				returns.append(preFix);
				returns.append(value.trim());
					
				preFix=str;
			}

		}
		returns.append("\n");
		return returns.toString();
	}
	//history query의 이력 생성
	public static String makeHistoryDTO(Object obj,Object[] list,String flag)throws Exception{
//		System.out.println(" ==============> 13");
		if(obj==null&&list==null)
			return null;
		String returns="";
		if(obj!=null)
			returns="H"+POINT+makeHistory(obj,POINT);
        if(list!=null){
			for(int i=0;i<list.length;i++){
					returns+="D"+POINT+makeHistory(list[i],POINT);
					if(flag.equals("S")){//영업업무보고이면..
						if(((JobReportDTO)list[i]).transFlag.equals("Y")){
						    TransDetailDTO tran=((JobReportDTO)list[i]).transDetailDTO;
							returns+=POINT+makeHistory(tran,POINT);
						}
					}
			}
		}
		return returns;
	}
	//history query 생성 
	public static String makeHistoryQuery(Object before,Object[] beforeList,Object after,Object[] afterList,String flag)throws Exception{
//		System.out.println(" ==============> 14");
		String aCrEmpId=null;
		String bCrEmpId=null;
		if(after!=null){
			aCrEmpId=(String)((DTO)after).getList().get("crEmpId");
		}else if(afterList!=null){
			aCrEmpId=(String)((DTO)afterList[0]).getList().get("crEmpId");
		}
		if(before!=null){
			bCrEmpId=(String)((DTO)before).getList().get("crEmpId");
		}else if(beforeList!=null){
			bCrEmpId=(String)((DTO)beforeList[0]).getList().get("crEmpId");
		}

        String sql ="insert into history(sn,dtm,crempid,edempid,plreflag,edbefore,edafter) "
		           +" values( "
				   +" history_sn.nextval ,"
				   +" sysdate ,"
				   +"'"+bCrEmpId+"'"
				   +","
				   +"'"+aCrEmpId+"'"
				   +","
				   +"'"+flag+"'"
				   +","
				   +"'"+makeHistoryDTO(before,beforeList,flag)+"'"
				   +","
				   +"'"+makeHistoryDTO(after,afterList,flag)+"'"
				   +")";
		return sql;
	}
	/*2007-08-27 추가 조원호*/
	//품의서 관련 MakeQueryHelper
	public static String makeInsertQuery(String tableName,DTO obj,Hashtable columnList,DataSet entitySet,boolean declaredKey,String objName)throws Exception
	{
		System.out.println("makeInsertQuery!!!!:"+tableName);
//		System.out.println("==============> 15");
		DataSet columnSet=(DataSet)entitySet.getObj(tableName);
		
//		System.out.println("==============> 15 columnSet"+columnSet);
		
		if(columnSet==null)return null;
		columnList = tabletColumn(tableName,columnList);
						
		Class c = null;		 
		Field[] fields=null;
		
		if(declaredKey){
			c = Class.forName(objName);
			fields=c.getDeclaredFields();
		}else{
			c = obj.getClass();
			fields=c.getFields();
		}
		
		LinkedList tmp       = null;
		LinkedList pkFinder  = null;
		ColumnDTO  columnDTO = null;
		
       // int keyInx=-1;
        String columnName=null;       
        
		for(int i=0;i<fields.length;i++){			
			columnName=fields[i].getName().toUpperCase();
			columnDTO=(ColumnDTO)columnSet.getObj(columnName);
			
			System.out.println("columnDTO 15:: \n"+columnDTO);

			if(columnDTO!=null){
				
				if(tmp==null)tmp= new LinkedList();
				tmp.add(i+"");
				if(columnDTO.isPk==1){
					if(pkFinder==null)pkFinder= new LinkedList();
					pkFinder.add(i+"");
				}
			}
		}
		if(tmp==null)return null;

		//(5). 추출할 칼럼 확정(칼럼 index 필요)
		int[] arrInx = new int[tmp.size()];
		int[] pkInx  = new int[pkFinder.size()];
		for(int i=0;i<tmp.size();i++)arrInx[i]=Integer.parseInt((String)tmp.get(i));
		for(int i=0;i<pkFinder.size();i++)pkInx[i]=Integer.parseInt((String)pkFinder.get(i));		

		StringBuffer sql     = null;
		StringBuffer values = null;
        String preFix        = null;
		String value         = null;
		String unknow_Val  = null;
		String fieldtype     = null;

		boolean columnFlag=(columnList==null)?false:true;

		sql          = new StringBuffer(" insert into  "+tableName+"(");
		values      = new StringBuffer(" values(");
		preFix      = " ";		
		unknow_Val= "'";
		value       = null;

		for(int j=0;j<arrInx.length;j++){
			columnName=fields[arrInx[j]].getName().toUpperCase();
			if(fields[arrInx[j]].get(obj)!=null){
				value=fields[arrInx[j]].get(obj)+"";	
				fieldtype = fields[arrInx[j]].getType().getName();
					
				if ( fieldtype.equals("java.lang.String")) {
					sql.append(preFix);
					sql.append(columnName);														
					values.append(preFix);
					if(columnFlag){
					unknow_Val=(columnList.get(columnName)!=null)?
								(String)columnList.get(columnName):"'";							        
					}
					values.append(unknow_Val);
					values.append(value.trim());
					values.append(unknow_Val);
					preFix=",";	
	
				}else if ( !fields[arrInx[j]].getType().isArray() &&
						   !(fields[arrInx[j]].get(obj) instanceof DTO)) {
					sql.append(preFix);
					sql.append(columnName);
					values.append(preFix);
					values.append(value.trim());
					preFix=",";	
				}
			}
			
		}
		sql.append(")");
		sql.append(values.toString());
		sql.append(")");

		System.out.println("MAKE QUERY HELPER INSERT:: \n"+sql.toString());
		return sql.toString();

	}//end method  

	
	//품의서 관련 MakeQueryHelper
	public static String makeUpdateQuery(String tableName,DTO obj,Hashtable columnList,DataSet entitySet,boolean declaredKey,String objName)throws Exception
	{
		System.out.println("makeUpdateQuery!!!!:"+tableName);
//		System.out.println("==============> 16");
	    DataSet columnSet=(DataSet)entitySet.getObj(tableName);

		if(columnSet==null)return null;
		columnList = tabletColumn(tableName,columnList);

		Class c = null;		 
		Field[] fields=null;
		
		if(declaredKey){
			c = Class.forName(objName);
			fields=c.getDeclaredFields();
		}else{
			//System.out.println("obj::"+obj);
			c = obj.getClass();
			fields=c.getFields();
		}
		
		LinkedList tmp       = null;
		LinkedList pkFinder  = null;
		ColumnDTO  columnDTO = null;
		
       // int keyInx=-1;
        String columnName=null;

		for(int i=0;i<fields.length;i++){			
			columnName=fields[i].getName().toUpperCase();
			columnDTO=(ColumnDTO)columnSet.getObj(columnName);
			if(columnDTO!=null){
				if(tmp==null)tmp= new LinkedList();
				tmp.add(i+"");
				if(columnDTO.isPk==1){
					if(pkFinder==null)pkFinder= new LinkedList();
					pkFinder.add(i+"");
				}
			}
		}
		if(tmp==null)return null;

		//(5). 추출할 칼럼 확정(칼럼 index 필요)
		int[] arrInx = new int[tmp.size()];
		int[] pkInx  = new int[pkFinder.size()];
		for(int i=0;i<tmp.size();i++)arrInx[i]=Integer.parseInt((String)tmp.get(i));
		for(int i=0;i<pkFinder.size();i++)pkInx[i]=Integer.parseInt((String)pkFinder.get(i));		

		StringBuffer sql     = null;
        String preFix        = null;
		String value         = null;
		String unknow_Val  = null;
		String fieldtype     = null;

		boolean columnFlag=(columnList==null)?false:true;

		sql        = new StringBuffer(" update  "+tableName+" set ");
		preFix     = " ";
		unknow_Val = "'";				
		value      = null;	

		for(int j=0;j<arrInx.length;j++){
			columnName=fields[arrInx[j]].getName().toUpperCase();
			if(fields[arrInx[j]].get(obj)!=null){
				value=fields[arrInx[j]].get(obj)+"";	
				fieldtype = fields[arrInx[j]].getType().getName();
				
				if ( fieldtype.equals("java.lang.String")) {
					sql.append(preFix);
					sql.append(columnName);														
					sql.append("=");
					if(columnFlag){
					unknow_Val=(columnList.get(columnName)!=null)?
								(String)columnList.get(columnName):"'";							        
					}
					sql.append(unknow_Val);
					sql.append(value.trim());
					sql.append(unknow_Val);
					preFix=",";	
	
				}else if ( !fields[arrInx[j]].getType().isArray() &&
						   !(fields[arrInx[j]].get(obj) instanceof DTO)) {
					sql.append(preFix);
					sql.append(columnName);
					sql.append("=");
					sql.append(value.trim());
					preFix=",";	
				}
			}
			
		}

		sql.append(makeWhere(columnFlag, columnList, pkInx, fields, obj));
		System.out.println("MAKE QUERY HELPER UPDATE:: \n"+sql.toString());
		return sql.toString();
		

	}//end method  
	
	public static String makeDeleteQuery(String tableName,DTO obj,Hashtable columnList,DataSet entitySet)throws Exception
	{
//		System.out.println("==============> 17");
		DataSet columnSet=(DataSet)entitySet.getObj(tableName);
	    
		if(columnSet==null)return null;
		columnList = tabletColumn(tableName,columnList);

		Class c = obj.getClass();
		Field[] fields = c.getFields();
		
		LinkedList tmp       = null;
		LinkedList pkFinder  = null;
		ColumnDTO  columnDTO = null;
		
       // int keyInx=-1;
        String columnName=null;

		for(int i=0;i<fields.length;i++){			
			columnName=fields[i].getName().toUpperCase();
			columnDTO=(ColumnDTO)columnSet.getObj(columnName);
			if(columnDTO!=null){
				if(tmp==null)tmp= new LinkedList();
				tmp.add(i+"");
				if(columnDTO.isPk==1){
					if(pkFinder==null)pkFinder= new LinkedList();
					pkFinder.add(i+"");
				}
			}
		}
		if(tmp==null)return null;

		//(5). 추출할 칼럼 확정(칼럼 index 필요)
		int[] arrInx = new int[tmp.size()];
		int[] pkInx  = new int[pkFinder.size()];
		for(int i=0;i<tmp.size();i++)arrInx[i]=Integer.parseInt((String)tmp.get(i));
		for(int i=0;i<pkFinder.size();i++)pkInx[i]=Integer.parseInt((String)pkFinder.get(i));		

		StringBuffer sql     = null;

		boolean columnFlag=(columnList==null)?false:true;

		sql = new StringBuffer(" delete from  "+tableName+" ");

		sql.append(makeWhere( columnFlag, columnList, pkInx, fields, obj));
		return sql.toString();

	}//end method  
	
	 /**
	 * 엔티티에서 이용할 Attribute를 정제한다.
	 * @return java.util.Hashtable columnList  테이블에서 사용할 Column prefix명들
	 * @param tableName java.lang.String 실제 적용되는 테이블명
	 * @param columnList java.util.Hashtable Column별 preFix정보
	 */
	public static Hashtable tabletColumn(String name, Hashtable columnList)throws Exception{
//		System.out.println("==============> 18");
		if(columnList==null) return null; 
		String tmp =null;
		String prefix=null;
		String subfix=null;
		int prefix_idx=-1;
		
		System.out.println("columnList::========> 18"+columnList);
		
		Enumeration tmpLst = columnList.keys();
        Hashtable returns  = new Hashtable();
		while(tmpLst.hasMoreElements()){
			tmp = (String)tmpLst.nextElement(); 
			prefix_idx=tmp.indexOf(".");
			if(prefix_idx==-1){
				returns.put(tmp,"");
			}else{
				prefix=tmp.substring(0,prefix_idx);
				subfix=tmp.substring(prefix_idx+1,tmp.length());				
				if(prefix.equals(name)){
					returns.put(subfix,"");
				}
			}
		}
		
		System.out.println("tabletColumn Returns::"+returns);
		return returns;

	}

	   /**
	 * GauceDataSet의 정보를 이용, 자동으로 where 조건문을 작성한다.
	 * @return String sql문장
	 * @param sql        StringBuffer  실제 적용되는 sql문
	 * @param columnFlag boolean preFix 사용유무
	 * @param columnList java.util.Hashtable Column별 preFix 정보
	 * @param pkInx      int[]   primary key들의 정보
	 * @param Field[]     fields  Column 정보
	 * @param DTO       obj     row 정보
	 */	
	public static String makeWhere(boolean columnFlag,Hashtable columnList,int[] pkInx,Field[] fields,DTO obj)throws Exception{
//		System.out.println("==============> 19");
		String unknow_Val = "'";				
		String value      = null;								
		String preFix     = " where ";
		String columnName = null;
		StringBuffer sql = new StringBuffer();
		//LinkedList returns= new LinkedList();
		for(int j=0;j<pkInx.length;j++){			
			columnName=fields[pkInx[j]].getName().toUpperCase();
			value=fields[pkInx[j]].get(obj)+"";	
			sql.append(preFix);
			sql.append(columnName);
			sql.append("=");
			sql.append(unknow_Val);
			sql.append(value.trim());
			sql.append(unknow_Val);
			preFix=" and ";	
		}				
		return sql.toString();
	}	
	
};
