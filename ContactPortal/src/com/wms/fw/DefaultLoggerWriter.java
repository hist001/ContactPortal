package com.wms.fw;

/**
 * @(#) DefaultLoggerWriter.java
 */

public class DefaultLoggerWriter extends LoggerWriter {

	/**
	 * @param mode LoggerWriter.SYS, LoggerWriter.ERR, LoggerWriter.WARN,
	 *                        LoggerWriter.INFO, LoggerWriter.DEBUG.
	 */
	public DefaultLoggerWriter(int mode) {
		super(mode);
	}

	/**
	 * getPrefixInfo The default message prefix.
	 */
	protected String getPrefixInfo(Object o) {
		StringBuffer info = new StringBuffer();
		info.append('[');

		if ( o == null ) {
			info.append("null");
		}
		else if ( o instanceof javax.servlet.http.HttpServletRequest ) {
			javax.servlet.http.HttpServletRequest req =	(javax.servlet.http.HttpServletRequest)o;
			info.append(req.getRequestURI() +",");
			String user = req.getRemoteUser();
			if ( user != null ) info.append(user+",");
			info.append(req.getRemoteAddr());
		}
		else {
			Class c = o.getClass();
			String fullname = c.getName();
			String name = null;
			int index = fullname.lastIndexOf('.');
			if ( index == -1 ) name = fullname;
			else name = fullname.substring(index+1);
			info.append(name);
		}

		if ( o == null )
			info.append("] ");
		else
			info.append(":" + o.hashCode() + "] ");

		return info.toString();
	}
}