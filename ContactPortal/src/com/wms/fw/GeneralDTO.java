package com.wms.fw;
import java.util.Hashtable;
import java.util.Enumeration;
import java.lang.reflect.Array;

public abstract class GeneralDTO implements DTO,java.io.Serializable
{
	/**
	 * DTO가 가진 모든 값을 리턴한다.
	 * 
	 * @return java.util.Hashtable
	 */	
	public Hashtable getList(){
		Hashtable returns= new Hashtable();
		Class c = this.getClass();
		java.lang.reflect.Field[] field = c.getFields();
		String fieldname=null;
		String fieldtype=null; 
		for (int i=0 ; i<field.length; i++) {
			try {					
			
				fieldname = field[i].getName();
				fieldtype = field[i].getType().getName();
				if ( fieldtype.equals("java.lang.String")) {
					returns.put(fieldname,(String)field[i].get(this));
				}
				else if ( fieldtype.equals("int")) {
					if(field[i].getInt(this)!=0)
					returns.put(fieldname,new Integer(field[i].getInt(this)));
				}
				else if ( fieldtype.equals("double")) {
					if(field[i].getDouble(this)!=0)
					returns.put(fieldname,new Double(field[i].getDouble(this)));
				}
				else if ( fieldtype.equals("long")) {
					if(field[i].getLong(this)!=0)
					returns.put(fieldname,new Long(field[i].getLong(this)));
				}
				else if ( fieldtype.equals("float")) {
					if(field[i].getFloat(this)!=0)
					returns.put(fieldname,new Float(field[i].getFloat(this)));
				}
				else if ( fieldtype.equals("boolean")) {
					returns.put(fieldname,new Boolean(field[i].getBoolean(this)));
				}
		
				
			}catch(Exception e){				
			}
		}

	return returns;
	}
	/**
	 * DTO가 가진 모든값을 String으로 리턴한다.
	 * @return java.lang.String
	 */
	public synchronized String toString() {		
		StringBuffer buf = new StringBuffer();
		Hashtable list=getList();
		int max = list.size();
		Enumeration keys = list.keys();
		Enumeration objects = list.elements();
		buf.append("{");

		for (int i = 0; i < max; i++) {
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

		return "DTO =" + buf.toString();

	}

};