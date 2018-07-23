package com.wms.fw.servlet;

/**
 * @(#) HttpUtility.java
 */
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;


public class HttpUtility {
	/**
	 * You can't call the constructor.
	 */
	private HttpUtility() {}

	
	/**
	 * @param req javax.servlet.http.HttpServletRequest
	 * @param name box name for this SessionBox
	 */
	public static Box getBox(HttpServletRequest req)  {
		Box box = new Box("requestbox");

		Enumeration e = req.getParameterNames();
		while(e.hasMoreElements()){
			String key = (String)e.nextElement();
			box.put(key, req.getParameterValues(key));
		}
		return box;
	}

	/**
	 * @param req javax.servlet.http.HttpServletRequest
	 * @param name box name for this SessionBox
	 */
	public static Box getBoxFromCookie(HttpServletRequest req)  {
		Box cookiebox = new Box("cookiebox");
		javax.servlet.http.Cookie[] cookies = req.getCookies();
		if ( cookies == null ) return cookiebox;

		for(int i=0; cookies != null && i< cookies.length; i++) {
			String key = cookies[i].getName();
			String value = cookies[i].getValue();
			if ( value == null ) value = "";
			String[] values = new String[1];
			values[0] = value;
			cookiebox.put(key,values);
		}
		return cookiebox;
	}

	/**
	 * @param req javax.servlet.http.HttpServletRequest
	 * @param name box name for this SessionBox
	 */
	public static SessionBox getNewSessionBox(HttpServletRequest req)  {
		return getNewSessionBox(req, "shared session box");
	}

	/**
	 * @param req javax.servlet.http.HttpServletRequest
	 * @param name box name for this SessionBox
	 */
	public static SessionBox getNewSessionBox(HttpServletRequest req, String name)  {

		javax.servlet.http.HttpSession  session = req.getSession(true);

		SessionBox sessionbox = null;

		if (! session.isNew()) {
			Object o = session.getAttribute(name);
			if ( o != null ) {
				session.setAttribute(name,null);
			}
		}

		if ( sessionbox == null ) {
			sessionbox = new SessionBox(session, name);
			session.setAttribute(name, sessionbox);
		}

		Enumeration e = req.getParameterNames();
		while(e.hasMoreElements()){
			String key = (String)e.nextElement();
			sessionbox.put(key, req.getParameterValues(key));
		}
		return sessionbox;
	}

	/**
	 * @param req javax.servlet.http.HttpServletRequest
	 * @param name box name for this SessionBox
	 */
	public static SessionBox getSessionBox(HttpServletRequest req)  {
		return getSessionBox(req, "shared session box");
	}

	/**
	 * @param req javax.servlet.http.HttpServletRequest
	 * @param name box name for this SessionBox
	 */
	public static SessionBox getSessionBox(HttpServletRequest req, String name)  {

		javax.servlet.http.HttpSession  session = req.getSession(true);

		SessionBox sessionbox = null;

		Object o = session.getAttribute(name);
		if ( o != null ) {
			if ( o instanceof SessionBox ) {
				sessionbox = (SessionBox)o;
			}
			else {
				session.setAttribute(name,null);
			}
		}

		if ( sessionbox == null ) {
			sessionbox = new SessionBox(session, name);
			session.setAttribute(name, sessionbox);
		}

		Enumeration e = req.getParameterNames();
		while(e.hasMoreElements()){
			String key = (String)e.nextElement();
			sessionbox.put(key, req.getParameterValues(key));
		}
		return sessionbox;
	}

	/**
	 *
	 * @return boolean
	 * @param req HttpServletRequest
	 */
	public static boolean isOverIE50(HttpServletRequest req) {
		String user_agent = (String) req.getHeader("user-agent");

		if ( user_agent == null ) 	return false;

		int index = user_agent.indexOf("MSIE");
		if ( index == -1 ) return false;

		int version = 0;
		try {
			version = Integer.parseInt(user_agent.substring(index+5, index+5+1));
		}
		catch(Exception e){}
		if ( version < 5 ) return false;

		return true;
	}
}