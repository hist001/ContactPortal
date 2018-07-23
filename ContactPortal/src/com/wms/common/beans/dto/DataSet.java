package com.wms.common.beans.dto;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Vector;

import java.lang.reflect.Array;
import com.wms.fw.jsp.HtmlUtility;
import com.wms.fw.*;

public class DataSet extends java.util.Hashtable {
	protected String name = null;

	public DataSet(){
		this("DataSet");
	}
	public DataSet(String name) {
		super();
		this.name = name;
	}
	public synchronized Object clone() {
	
		DataSet newSet = new DataSet(name);
	
		Hashtable src = (Hashtable)this;
		Hashtable target = (Hashtable)newSet;
	
		Enumeration e = src.keys();
		while(e.hasMoreElements()) {
			String key = (String) e.nextElement();
			Object value =  src.get(key);
			target.put(key,value);
		}
		return newSet;
	}

	/**
	 * String을 리턴한다.
	 * @return java.lang.String
	 * @param key java.lang.String
	 */

	public String get(String key) {
		return getString(key);
	}
	public String getCash(String key){
		return getCash(key,0);
	}
	public String getCash(String key,int idx){
		return Utility.cashReturn(getString(key,idx));
	}	
	public String getKdt(String key){
		return getKdt(key,0);
	}
	public String getKdt(String key,int idx){
		String tmp=getString(key,idx);
		if(tmp!=null&&tmp.length()==8){
			tmp=tmp.substring(0,4)+"-"+
				tmp.substring(4,6)+"-"+
				tmp.substring(6,8);
		}else{
			tmp="";
		}
		return tmp;
	}	
	public String get(String key,int idx) {
		return getString(key,idx);
	}

	public Object getObj(String key) {
		return super.get(key);
	}	
	/**
	 * @return boolean
	 * @param key java.lang.String
	 */
	public boolean getBoolean(String key) {
		return getBoolean(key,0);
	}
	/**
	 * @return double
	 * @param key java.lang.String
	 */
	public double getDouble(String key) {
		return getDouble(key,0);
	}

	/**
	 * @return float
	 * @param key java.lang.String
	 */
	public float getFloat(String key) {
		return (float)getDouble(key);
	}
	/**
	 * @return int
	 * @param key java.lang.String
	 */
	public int getInt(String key) {
		double value = getDouble(key);
		return (int)value;
	}
	/**
	 * @return long
	 * @param key java.lang.String
	 */
	public long getLong(String key) {
		return getLong(key,0);
	}
	/**
	 * @return java.lang.String
	 * @param key java.lang.String
	 */
	public String getString(String key) {
		return getString(key,0);
	}

	public Object copyToEntity(String className) {
		Object returns=copyToEntities(className,null);
		return (returns==null)?null:Array.get(returns, 0); 
		
	} // end copyToEntity
	
	/**
	 * className타입의 배열로 값을 리턴한다.
	 * @return java.lang.Object
	 * @param  className java.lang.String  복사할 객체의 타입
	 */
	public Object copyToEntities(String className) {
		return copyToEntities(className,null);
		
	} // end copyToEntities
	/**keyName의 크기만큼 복사..*/
	public Object copyToEntities(String className,String keyName) {
		return copyToEntities( className, keyName, null);
	} // end copyToEntities
	
	public Object copyToEntities(String className,String keyName,LinkedList prefixList) {
		Object returns=null;
		LinkedList tmpList=null;//2006-07-06 추가
		try {
			Class c = Class.forName(className);
			java.lang.reflect.Field[] field = c.getFields();
			if(keyName==null)keyName=field[0].getName();
			
			int length=getLength(keyName);
			//returns=Array.newInstance(c, length);  
			
			int idx=0;
			/*접두사 처리*/
			boolean prefix_div=false;//접두사 구분 		
			String prefix=null;
			if(prefixList!=null){
				prefix_div=true;
				prefix=className.substring((className.lastIndexOf(".")==-1)?0:className.lastIndexOf(".")+1,(className.lastIndexOf("DTO")==-1)?className.length():className.lastIndexOf("DTO"));
			}
					
			for(int j=0;j<length;j++){		
				if(getString(keyName,j).equals(""))continue;//key값이 null이면 자료 추출 제외
				//System.out.println(keyName+":"+getString(keyName,j));
				if(tmpList==null)tmpList=new LinkedList();//2006-07-06 추가
				
				Object entity=c.newInstance();
				idx=j;
				for (int i=0 ; i<field.length; i++) {
								
					String fieldtype = field[i].getType().getName();
					String fieldname = field[i].getName();
					if(prefix_div){
						if(prefixList.contains(fieldname))
							fieldname=prefix+"."+fieldname;
					}
					if ( containsKey( fieldname ) ) {
						if ( fieldtype.equals("java.lang.String")) {
							field[i].set(entity, getString(fieldname,idx));						
						}
						else if ( fieldtype.equals("int")) {
							field[i].setInt(entity, getInt(fieldname,idx));
						}
						else if ( fieldtype.equals("double")) {
							field[i].setDouble(entity, getDouble(fieldname,idx));
						}
						else if ( fieldtype.equals("long")) {
							field[i].setLong(entity, getLong(fieldname,idx));
						}
						else if ( fieldtype.equals("float")) {
							field[i].set(entity, new Float(getDouble(fieldname,idx)));
						}
						else if ( fieldtype.equals("boolean")) {
							field[i].setBoolean(entity, getBoolean(fieldname,idx));
						}
					}
				
				}//for
				tmpList.add(entity);
		    }//for
			if(tmpList!=null){
				returns=Array.newInstance(c, tmpList.size());
				for(int i=0;i<tmpList.size();i++)
					Array.set(returns,i,tmpList.get(i));
				
			}		
		}
		catch(Exception e){
			
					Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
		}
		return returns;
	} // end copyToEntities

	public String getString(String key,int index) {

		String value = null;
		try {
			Object o = (Object)super.get(key);
			Class c = o.getClass();
			if ( o == null ) value = "";
			else if( c.isArray() ) {
				int length = Array.getLength(o);
				if ( length == 0 ) value = "";
				else {
					if(length<=index)index=0;
					Object item = Array.get(o, index);
					if ( item == null ) value = "";
					else value = item.toString();
				}
			}
			else 	value = o.toString();
		}
		catch(Exception e) {
			System.out.println(key+" 컬럼이 없습니다.");
			value = "";
		}		
		//return HtmlUtility.translate(value);
		return value;

	}//getString(String key,int index)

	public boolean getBoolean(String key,int index) {
		String value = getString(key,index);
		boolean isTrue = false;
		try {
			isTrue = (new Boolean(value)).booleanValue();
		}
		catch(Exception e){}
		return isTrue;
	}

	public double getDouble(String key,int index) {
		String value = removeComma(getString(key,index));
		if ( value.equals("") ) return 0;
		double num = 0;
		try {
			num = Double.valueOf(value).doubleValue();
		}
		catch(Exception e) {
			num = 0;
		}
		return num;
	}

	public double getFloat(String key,int index) {
		return (float)getDouble(key,index);
	}
	public int getInt(String key,int index) {
		double value = getDouble(key,index);
		return (int)value;
	}
	public long getLong(String key,int index) {
		String value = removeComma(getString(key,index));
		if ( value.equals("") ) return 0L;

		long lvalue = 0L;
		try {
			lvalue = Long.valueOf(value).longValue();
		}
		catch(Exception e) {
			lvalue = 0L;
		}

		return lvalue;
	}
	public int getLength() {
		return getLength(null);
	}
	public int getLength(String key) {
		int returns = 0;
		
		try {
			if(key==null){
				key=keys().nextElement().toString();
			}
			Object o = (Object)super.get(key);
			System.out.println(key+":lengthL:"+o);
			Class c = o.getClass();
			 if( o!=null){				 
				 if(c.isArray() ){				 
					 returns = Array.getLength(o);
				 }else{
					 returns=1;
				 }
			 }
			 
		}
		catch(Exception e) {	
			e.printStackTrace();
		}
		return returns;
	}//getLength(String key)
	/**
	 * check box 와 같이 같은 name에 대해 여러 value들이 String의 Vector로 넘겨준다.
	 * @return Vector
	 * @param key java.lang.String
	 */
	public synchronized Vector getVector(String key) {
		Vector vector = new Vector();
		try {
			Object o = (Object)super.get(key);
			Class c = o.getClass();
			if ( o != null ) {
				if( c.isArray() ) {
					int length = Array.getLength(o);
					if ( length != 0 ) {
						for(int i=0; i<length;i++) {
							Object tiem = Array.get(o, i);
							if (tiem == null ) vector.addElement("");
							else vector.addElement(tiem.toString());
						}
					}
				}
				else
					vector.addElement(o.toString());
			}
		}
		catch(Exception e) {}
		return vector;
	}
	/**
	 * check box 와 같이 같은 name에 대해 여러 value들이 String의 Vector로 넘겨준다.
	 * @return Vector
	 * @param key java.lang.String
	 */
	public synchronized ArrayList getArrayList(String key) {
		ArrayList list = new ArrayList();
		try {
			Object o = (Object)super.get(key);
			Class c = o.getClass();
			if ( o != null ) {
				if( c.isArray() ) {
					int length = Array.getLength(o);
					if ( length != 0 ) {
						for(int i=0; i<length;i++) {
							Object tiem = Array.get(o, i);
							if (tiem == null )list.add("");
							else list.add(tiem.toString());
						}
					}
				}
				else
					list.add(o.toString());
			}
		}
		catch(Exception e) {}
		return list;
	}

	/**
	 * @param key java.lang.String
	 * @param value java.lang.String
	 */
	public synchronized void put(String key, String value) {
		super.put(key, value);
	}

	/**
	 * remove "," in string.
	 * @return java.lang.String
	 * @param s java.lang.String
	 */
	private static String removeComma(String s) {
		if ( s == null ) return null;
		if ( s.indexOf(",") != -1 ) {
			StringBuffer buf = new StringBuffer();
			for(int i=0;i<s.length();i++){
				char c = s.charAt(i);
				if ( c != ',') buf.append(c);
			}
			return buf.toString();
		}
		return s;
	}
	/**
	 * DataSet가 가진 모든값을 리턴한다.
	 * @return java.lang.String
	 */
	public synchronized String toString() {
		int max = size() - 1;
		StringBuffer buf = new StringBuffer();
		Enumeration keys = keys();
		Enumeration objects = elements();
		buf.append("{");
	
		for (int i = 0; i <= max; i++) {
			String key = keys.nextElement().toString();
			String value = null;
			Object o = objects.nextElement();
			if ( o == null ) value = "";
			else {
				Class  c = o.getClass();
				if( c.isArray() ) {
					int length = Array.getLength(o);
					if ( length == 0 ) 	value = "";
					else if ( length == 1 ) {
						Object item = Array.get(o, 0);
						if ( item == null ) value = "";
						else value = item.toString();
					}
					else {
						StringBuffer valueBuf = new StringBuffer();
						valueBuf.append("[");
						for ( int j=0;j<length;j++) {
							Object item = Array.get(o, j);
							if ( item != null ) valueBuf.append(item.toString());
							if ( j<length-1) valueBuf.append(",");
						}
						valueBuf.append("]");
						value = valueBuf.toString();
					}
				}
				else
					value = o.toString();
			}
		    buf.append(key + "=" + value);
		    if (i < max) buf.append(", ");
		}
		buf.append("}");
	
		return "DataSet["+name+"]=" + buf.toString();
	
	}
	/**
	 * DataSet가 가진 모든값을 xml형태로 리턴한다.
	 * @return java.lang.String
	 */
	public synchronized String toXml() {
		return toXml(null);
	}	
	public synchronized String toXml(String dsName) {
		
		StringBuffer buf=null;		
		try{
			if(dsName==null)dsName="set";
			int max = size() ;
			int leng = getLength();			
			buf = new StringBuffer();
			Enumeration keys = keys();
			Enumeration objects = elements();
			
			//buf.append("<"+dsName+">");
		    
			String[] keyArr = new String[max];
			String value = null;
			boolean isExit=false;
			Object o=null;
			for(int i=0;i<max;i++){
				keyArr[i]=keys.nextElement().toString();
			}		
			for(int i=0;i<leng;i++){
				
				buf.append("<"+dsName+">");		
				for(int j=0;j<max;j++){
					buf.append("<"+keyArr[j]+">");						
					buf.append("<![CDATA["+getString(keyArr[j],i)+"]]>");					
					buf.append("</"+keyArr[j]+">");				
				}
				buf.append("</"+dsName+">");			
			}	    
			//buf.append("</"+dsName+">");
		}catch(Exception e1){e1.printStackTrace();}
	
		return buf.toString();
	
	}
	
}