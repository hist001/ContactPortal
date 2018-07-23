package com.wms.fw.servlet;

/**
 * @(#)SessionBox.java
 */
import java.util.Hashtable;
import java.util.Enumeration;

public class SessionBox extends Box {
	private javax.servlet.http.HttpSession  session = null;
/**
 *
 */
public SessionBox(javax.servlet.http.HttpSession session, String name) {
	super(name);
	this.session = session;
}
/**
 *
 * @param target com.logicsoft.fw.servlet.Box
 */
public Object clone() {

	SessionBox sessionbox = new SessionBox(session, name);

	Hashtable src = (Hashtable)this;
	Hashtable target = (Hashtable)sessionbox;

	Enumeration e = src.keys();
	while(e.hasMoreElements()) {
		String key = (String) e.nextElement();
		Object value =  src.get(key);
		target.put(key,value);
	}
	return sessionbox;
}
/**
 *
 * @return java.lang.String
 */
public String getName() {
	return name;
}
	/**
	 * @return java.lang.Object
	 * @param key java.lang.String
	 */
	public Object getObject(String key) {
		return super.get((Object)key);
	}
	/**
	 * @param key java.lang.String
	 * @param object java.lang.Object
	 */
	public void putObject(String key, Object object) {
		super.put((Object)key, object);
	}
/**
 *
 */
public void release() {
	try {
		session.setAttribute(name,null);
	}
	catch(Exception  e){
	}
	clear();
}
/**
 *
 * @return java.lang.String
 */
public String toString() {
	return "Sessoin" + super.toString();
}
}