/*************************************************************
*	파 일 명  : BasicDataDTO.java
*	작성일자  : 2004/06/22
*	작 성 자  : 
*	내    용  : 기본자료관리 정보 클래스
*************************************************************/
package com.wms.beans.dto;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;
import java.util.StringTokenizer;
import java.lang.reflect.Array;
import javax.servlet.http.HttpServletRequest;
import com.wms.fw.jsp.HtmlUtility;

public class BasicDataDTO extends Hashtable
{
	public BasicDataDTO(){
		super();
	}
	/**
	 * @param req javax.servlet.http.HttpServletRequest
	 * 
	 */
	public void set(HttpServletRequest req)  {

		Enumeration e = req.getParameterNames();
		while(e.hasMoreElements()){
			String key = (String)e.nextElement();
			if(!(key.substring(0,1)).equals("_")){
				set(key, req.getParameter(key));
			}			
		}
	}
	public void set(String key,Object value){
		super.put(key,value);
	}
	/**
	 * DTO가 가진 값들을 entity에 복사한다.
	 * @param entity java.lang.Object
	 */
	public void copyToEntity(Object entity) {
		copyToEntity(entity,0); // 0 is not conversion, 1 is K2E conversion, 2 is E2K conversion
	}
	/**
	 * DTO가 가진 값들을 entity에 복사한다.
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
			}
			catch(Exception e){
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
	 * DTO가 가진 모든값을 리턴한다.
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

		return "DTO=" + buf.toString();

	}

};