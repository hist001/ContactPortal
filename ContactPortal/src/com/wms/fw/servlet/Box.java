package com.wms.fw.servlet;

/**
 * @(#)Box.java
 */

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Vector;
import java.util.StringTokenizer;
import java.lang.reflect.Array;
import com.wms.fw.jsp.HtmlUtility;
import com.wms.fw.*;

public class Box extends java.util.Hashtable {
	protected String name = null;
	/**
	 *
	 */
	public Box(String name) {
		super();
		this.name = name;
	}
/**
 *
 * @param target com.wms.fw.servlet.Box
 */
public synchronized Object clone() {

	Box newbox = new Box(name);

	Hashtable src = (Hashtable)this;
	Hashtable target = (Hashtable)newbox;

	Enumeration e = src.keys();
	while(e.hasMoreElements()) {
		String key = (String) e.nextElement();
		Object value =  src.get(key);
		target.put(key,value);
	}
	return newbox;
}
/**
 * Box가 가진 값들을 entity에 복사한다.
 * @param entity java.lang.Object
 */
public void copyToEntity(Object entity) {
	copyToEntity(entity,0); // 0 is not conversion, 1 is K2E conversion, 2 is E2K conversion
}
/**
 * Box가 가진 값들을 entity에 복사한다.
 * @param entity java.lang.Object
 * @param Conversion int, 1 is ISO-8859-1 to EUC-KR conversion(ex.AS/400), 2 is EUC-KR to ISO-8859-1 conversion(ex.MSSQL) and other values are no conversion.
 */
public void copyToEntity(Object entity, int Conversion) {
	if ( entity == null )
		throw new NullPointerException("trying to copy from box to null entity class");

	Class c = entity.getClass();
	java.lang.reflect.Field[] field = c.getFields();
	for (int i=0 ; i<field.length; i++) {
		try {
			String fieldtype = field[i].getType().getName();
			String fieldname = field[i].getName();

			if ( containsKey( fieldname ) ) {
				if ( fieldtype.equals("java.lang.String")) {
				    if(Conversion == 1){
						field[i].set(entity, new String(getString(fieldname).getBytes("8859_1"), "EUC-KR"));
				    } else if(Conversion == 2){
						field[i].set(entity, new String(getString(fieldname).getBytes("EUC-KR"), "8859_1"));
				    } else {
						field[i].set(entity, getString(fieldname));
					}
				}
				else if ( fieldtype.equals("int")) {
					field[i].setInt(entity, getInt(fieldname));
				}
				else if ( fieldtype.equals("double")) {
					field[i].setDouble(entity, getDouble(fieldname));
				}
				else if ( fieldtype.equals("long")) {
					field[i].setLong(entity, getLong(fieldname));
				}
				else if ( fieldtype.equals("float")) {
					field[i].set(entity, new Float(getDouble(fieldname)));
				}
				else if ( fieldtype.equals("boolean")) {
					field[i].setBoolean(entity, getBoolean(fieldname));
				}
			}
			//com.wms.fw.Utility.fixNullAndTrimAll(entity);
		}catch(Exception e){
			//Debug.warn.println(this, e.getMessage());
		}
	}
} // end copyToEntity

	/**
	 * String을 리턴한다.
	 * @return java.lang.String
	 * @param key java.lang.String
	 */
	public String get(String key) {
		return getString(key);
	}
	/**
	 * @return boolean
	 * @param key java.lang.String
	 */
	public boolean getBoolean(String key) {
		String value = getString(key);
		boolean isTrue = false;
		try {
			isTrue = (new Boolean(value)).booleanValue();
		}
		catch(Exception e){}
		return isTrue;
	}
	/**
	 * @return double
	 * @param key java.lang.String
	 */
	public double getDouble(String key) {
		String value = removeComma(getString(key));
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
		String value = removeComma(getString(key));
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
	/**
	 * @return java.lang.String
	 * @param key java.lang.String
	 */
	public String getString(String key) {
		String value = null;
		try {
			Object o = (Object)super.get(key);
			Class c = o.getClass();
			if ( o == null ) value = "";
			else if( c.isArray() ) {
				int length = Array.getLength(o);
				if ( length == 0 ) value = "";
				else {
					Object item = Array.get(o, 0);
					if ( item == null ) value = "";
					else value = item.toString();
				}
			}
			else 	value = o.toString();
		}
		catch(Exception e) {
			value = "";
		}
		return HtmlUtility.translate(value);
	}
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
 * Box가 가진 모든값을 리턴한다.
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

	return "Box["+name+"]=" + buf.toString();

}
/**
 * className타입의 배열로 값을 리턴한다.
 * @return java.lang.Object
 * @param  className java.lang.String  복사할 객체의 타입
 */
public Object copyToEntities(String className) {
	/*
	Object returns=null;
	try {
		Class c = Class.forName(className);
		java.lang.reflect.Field[] field = c.getFields();
		int length=getLength(field[0].getName());
		returns=Array.newInstance(c, length); 
		for(int j=0;j<length;j++){
			Object entity=c.newInstance();
			for (int i=0 ; i<field.length; i++) {
							
				String fieldtype = field[i].getType().getName();
				String fieldname = field[i].getName();

				if ( containsKey( fieldname ) ) {
					if ( fieldtype.equals("java.lang.String")) {
						
						field[i].set(entity, getString(fieldname,j));
						
					}
					else if ( fieldtype.equals("int")) {
						field[i].setInt(entity, getInt(fieldname,j));
					}
					else if ( fieldtype.equals("double")) {
						field[i].setDouble(entity, getDouble(fieldname,j));
					}
					else if ( fieldtype.equals("long")) {
						field[i].setLong(entity, getLong(fieldname,j));
					}
					else if ( fieldtype.equals("float")) {
						field[i].set(entity, new Float(getDouble(fieldname,j)));
					}
					else if ( fieldtype.equals("boolean")) {
						field[i].setBoolean(entity, getBoolean(fieldname,j));
					}
				}
			
			}//for
			//com.wms.fw.Utility.fixNullAndTrimAll(entity);
			Array.set(returns,j,entity);
	    }//for
	}
	catch(Exception e){
		
				//Debug.warn.println(this, e.getMessage());
	}
	return returns;
	*/
	return copyToEntities(className,null);
	
} // end copyToEntities
/**keyName의 크기만큼 복사..
*/
public Object copyToEntities(String className,String keyName) {
	/*
	Object returns=null;
	try {
		Class c = Class.forName(className);
		java.lang.reflect.Field[] field = c.getFields();
		if(keyName==null)keyName=field[0].getName();
		int length=getLength(keyName);
		returns=Array.newInstance(c, length); 
		int idx=0;
		for(int j=0;j<length;j++){
			Object entity=c.newInstance();
			idx=j;
			for (int i=0 ; i<field.length; i++) {
							
				String fieldtype = field[i].getType().getName();
				String fieldname = field[i].getName();
				if ( containsKey( fieldname ) ) {
					if ( fieldtype.equals("java.lang.String")) {
						//fieldname이 2번 반복되더라도 keyName보다 이전이면, 값을 인식못함.
						//if(keyName.equals(fieldname))idx=j;
						field[i].set(entity, getString(fieldname,idx));						
					}
					else if ( fieldtype.equals("int")) {
						//if(keyName.equals(fieldname))idx=j;						
						field[i].setInt(entity, getInt(fieldname,idx));
					}
					else if ( fieldtype.equals("double")) {
						//if(keyName.equals(fieldname))idx=j;							
						field[i].setDouble(entity, getDouble(fieldname,idx));
					}
					else if ( fieldtype.equals("long")) {
						//if(keyName.equals(fieldname))idx=j;							
						field[i].setLong(entity, getLong(fieldname,idx));
					}
					else if ( fieldtype.equals("float")) {
						//if(keyName.equals(fieldname))idx=j;							
						field[i].set(entity, new Float(getDouble(fieldname,idx)));
					}
					else if ( fieldtype.equals("boolean")) {
						//if(keyName.equals(fieldname))idx=j;				
						field[i].setBoolean(entity, getBoolean(fieldname,idx));
					}
				}
			
			}//for
			Array.set(returns,j,entity);
	    }//for
	}
	catch(Exception e){
		
				Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
	}
	return returns;
	*/
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
						//fieldname이 2번 반복되더라도 keyName보다 이전이면, 값을 인식못함.
						//if(keyName.equals(fieldname))idx=j;
						//System.out.println(className+":field:"+fieldname+":idx:"+idx+":value:"+getString(fieldname,idx));
						field[i].set(entity, getString(fieldname,idx));						
					}
					else if ( fieldtype.equals("int")) {
						//if(keyName.equals(fieldname))idx=j;						
						field[i].setInt(entity, getInt(fieldname,idx));
					}
					else if ( fieldtype.equals("double")) {
						//if(keyName.equals(fieldname))idx=j;							
						field[i].setDouble(entity, getDouble(fieldname,idx));
					}
					else if ( fieldtype.equals("long")) {
						//if(keyName.equals(fieldname))idx=j;							
						field[i].setLong(entity, getLong(fieldname,idx));
					}
					else if ( fieldtype.equals("float")) {
						//if(keyName.equals(fieldname))idx=j;							
						field[i].set(entity, new Float(getDouble(fieldname,idx)));
					}
					else if ( fieldtype.equals("boolean")) {
						//if(keyName.equals(fieldname))idx=j;				
						field[i].setBoolean(entity, getBoolean(fieldname,idx));
					}
				}
			
			}//for
			//Array.set(returns,j,entity);
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
			e.printStackTrace();
			value = "";
		}		
		return HtmlUtility.translate(value);

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

	public int getLength(String key) {
		int returns = 0;
		try {
			Object o = (Object)super.get(key);
			Class c = o.getClass();
			 if( o!=null&&c.isArray() ) 
				returns = Array.getLength(o);							
		}
		catch(Exception e) {			
		}
		return returns;
	}//getLength(String key)
}