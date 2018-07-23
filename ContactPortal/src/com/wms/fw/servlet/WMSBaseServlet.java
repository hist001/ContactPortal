package com.wms.fw.servlet;

/**
 * @(#) WMSBaseServlet.java
 */
import javax.ejb.EJBHome;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;

import com.wms.fw.Config;
import com.wms.fw.Configuration;
import com.wms.fw.Logger;
import com.wms.fw.ejb.EJBHomeFactory;

public abstract class WMSBaseServlet extends javax.servlet.http.HttpServlet {
	protected static String lineSeparator  = System.getProperty("line.separator");

	/**
	 * BaseServlet constructor comment.
	 */
	public WMSBaseServlet() {
		super();
	}

	/**
	 * Performs the HTTP GET operation; the default implementation
	 * reports an HTTP BAD_REQUEST error.  Overriding this method to
	 * support the GET operation also automatically supports the HEAD
	 * operation.  (HEAD is a GET that returns no body in the response;
	 * it just returns the request HEADer fields.)
	 *
	 * <p>Servlet writers who override this method should read any data
	 * from the request, set entity headers in the response, access the
	 * writer or output stream, and, finally, write any response data.
	 * The headers that are set should include content type, and
	 * encoding.  If a writer is to be used to write response data, the
	 * content type must be set before the writer is accessed.  In
	 * general, the servlet implementor must write the headers before
	 * the response data because the headers can be flushed at any time
	 * after the data starts to be written.
	 *
	 * <p>Setting content length allows the servlet to take advantage
	 * of HTTP "connection keep alive".  If content length can not be
	 * set in advance, the performance penalties associated with not
	 * using keep alives will sometimes be avoided if the response
	 * entity fits in an internal buffer.
	 *
	 * <p>Entity data written for a HEAD request is ignored.  Servlet
	 * writers can, as a simple performance optimization, omit writing
	 * response data for HEAD methods.  If no response data is to be
	 * written, then the content length field must be set explicitly.
	 *
	 * <P>The GET operation is expected to be safe: without any side
	 * effects for which users might be held responsible.  For example,
	 * most form queries have no side effects.  Requests intended to
	 * change stored data should use some other HTTP method.  (There
	 * have been cases of significant security breaches reported
	 * because web-based applications used GET inappropriately.)
	 *
	 * <P> The GET operation is also expected to be idempotent: it can
	 * safely be repeated.  This is not quite the same as being safe,
	 * but in some common examples the requirements have the same
	 * result.  For example, repeating queries is both safe and
	 * idempotent (unless payment is required!), but buying something
	 * or modifying data is neither safe nor idempotent.
	 *
	 * @param req HttpServletRequest that encapsulates the request to
	 * the servlet
	 * @param resp HttpServletResponse that encapsulates the response
	 * from the servlet
	 *
	 * @exception IOException if detected when handling the request
	 * @exception ServletException if the request could not be handled
	 *
	 * @see javax.servlet.ServletResponse#setContentType
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException
	{
		performBasePreTask(req, res);
	}

	/**
	 *
	 * Performs the HTTP POST operation; the default implementation
	 * reports an HTTP BAD_REQUEST error.  Servlet writers who override
	 * this method should read any data from the request (for example,
	 * form parameters), set entity headers in the response, access the
	 * writer or output stream and, finally, write any response data
	 * using the servlet output stream.  The headers that are set
	 * should include content type, and encoding.  If a writer is to be
	 * used to write response data, the content type must be set before
	 * the writer is accessed.  In general, the servlet implementor
	 * must write the headers before the response data because the
	 * headers can be flushed at any time after the data starts to be
	 * written.
	 *
	 * <p>If HTTP/1.1 chunked encoding is used (that is, if the
	 * transfer-encoding header is present), then the content-length
	 * header should not be set.  For HTTP/1.1 communications that do
	 * not use chunked encoding and HTTP 1.0 communications, setting
	 * content length allows the servlet to take advantage of HTTP
	 * "connection keep alive".  For just such communications, if
	 * content length can not be set, the performance penalties
	 * associated with not using keep alives will sometimes be avoided
	 * if the response entity fits in an internal buffer.
	 *
	 * <P> This method does not need to be either "safe" or
	 * "idempotent".  Operations requested through POST can have side
	 * effects for which the user can be held accountable.  Specific
	 * examples including updating stored data or buying things online.
	 *
	 * @param req HttpServletRequest that encapsulates the request to
	 * the servlet
	 * @param resp HttpServletResponse that encapsulates the response
	 * from the servlet
	 *
	 * @exception IOException if detected when handling the request
	 * @exception ServletException if the request could not be handled
	 *
	 * @see javax.servlet.ServletResponse#setContentType
	 */
	protected void doPost (HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException
	{
		performBasePreTask(req, res);
	}

	/**
	 *
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 */
	protected void performBasePreTask (HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException
	{
		boolean trace = false;
		try {
			Config conf = new Configuration();
			trace = conf.getBoolean("com.wms.fw.servlet.baseservlet.trace");
		}
		catch(Exception e) {}

		String logMsg = null;
		long start = 0, end = 0;
		if ( trace ) {
			logMsg = req.getRequestURI() + ":" + req.getRemoteHost() + "(" + req.getRemoteAddr() + ")";
			String user = req.getRemoteUser();
			if ( user != null ) logMsg += ":" + user;

			start = System.currentTimeMillis();
			Logger.sys.println(this, logMsg + ":calling");
		}

		try {
			performPreTask(req, res);
		}
		catch(Exception e){
			java.io.ByteArrayOutputStream bos = new java.io.ByteArrayOutputStream();
			java.io.PrintWriter writer = new java.io.PrintWriter(bos);
			e.printStackTrace(writer);
			writer.flush();
			String errString = "Programmer's Exception: " +logMsg +  lineSeparator + bos.toString();
			Logger.err.println(this, errString);
			printMessagePage(req,res, errString);
		}

		if ( trace ) {
			end = System.currentTimeMillis();
			Logger.sys.println(this, logMsg + ":end(elapsed=" + (end-start) + ")" + lineSeparator);
		}
	}

	/**
	 *
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 */
	protected abstract void performPreTask (HttpServletRequest req, HttpServletResponse res) throws Exception;

	/**
	* Sends a temporary redirect response to the client using the
	* specified redirect location URL.  The URL must be absolute (for
	* example, <code><em>https://hostname/path/file.html</em></code>).
	* Relative URLs are not permitted here.
	*
	* @param req javax.servlet.http.HttpServletRequest
	* @param res javax.servlet.http.HttpServletResponse
	* @param location the redirect location URL
	* @exception IOException If an I/O error has occurred.
	*/
	protected void printHtmlPage (HttpServletRequest req, HttpServletResponse res, String location) {
		try {
			res.sendRedirect(location);
		}
		catch (IOException e) {
			String err = e.getMessage();
			if ( err == null ) err = "";
			else err = " (" + err + ")";
			printMessagePage(req,res, "File Not Found : " + location + err );
		}
	}

	/**
	* Sends a temporary redirect response to the client using the
	* specified redirect location of jsp file.  The URL must be absolute (for
	* example, <code><em>/example/result.jsp</em></code>).
	* Relative URLs are not permitted here.
	*
	* @param req javax.servlet.http.HttpServletRequest
	* @param res javax.servlet.http.HttpServletResponse
	* @param jspfile the redirect location URL of jsp file.
	*/
	protected void printJspPage (HttpServletRequest req, HttpServletResponse res, String jspfile) {
		try {

			// IBM WebSphere 2.0.x
			//((com.sun.server.http.HttpServiceResponse) res).callPage(jspfile, req);

			// Servlet 2.1
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(jspfile);
			dispatcher.forward(req, res);
		}
		catch (IllegalStateException e) {
			try{
				java.io.PrintWriter out = res.getWriter();
				java.io.ByteArrayOutputStream bos = new java.io.ByteArrayOutputStream();
				java.io.PrintWriter writer = new java.io.PrintWriter(bos);
				writer.println("JSP Call Error: " + this.getClass().getName() );
				writer.println("Request URI: " + req.getRequestURI() );
				String user = req.getRemoteUser();
				if ( user != null ) writer.println("User : " + user );
				writer.println("User Location  : " + req.getRemoteHost() + "(" + req.getRemoteAddr() + ")");
				e.printStackTrace(writer);
				writer.flush();
				res.setContentType("text/html;charset=euc-kr");
				out.println("<html><head><title>Errort</title></head><body bgcolor=white><xmp>");
				out.println(bos.toString());
				out.println("</xmp></body></html>");
				out.close();
			}
			catch(Exception ex){}
		}
		catch (Exception e) {
			java.io.ByteArrayOutputStream bos = new java.io.ByteArrayOutputStream();
			java.io.PrintWriter writer = new java.io.PrintWriter(bos);
			writer.println("JSP Call Error: " + this.getClass().getName() );
			writer.println("Request URI: " + req.getRequestURI() );
			String user = req.getRemoteUser();
			if ( user != null ) writer.println("User : " + user );
			writer.println("User Location  : " + req.getRemoteHost() + "(" + req.getRemoteAddr() + ")");
			e.printStackTrace(writer);
			writer.flush();
			Logger.sys.println(bos.toString());
			printMessagePage(req,res, bos.toString());
		}
	}

	

	
	

	/**
	 *
	 * @param req javax.servlet.http.HttpServletRequest
	 * @param res javax.servlet.http.HttpServletResponse
	 * @param msg 최종사용자에게 보여질 메세지
	 */
	protected void printMessagePage (HttpServletRequest req, HttpServletResponse res, String msg)
	{
		printMessagePage(req,res, msg, (String)null);
	}

	/**
	 *
	 * @param req javax.servlet.http.HttpServletRequest
	 * @param res javax.servlet.http.HttpServletResponse
	 * @param user_msg 최종 사용자에게 보여질 메세지
	 * @param e Exception: 개발시점에서 개발자가 Debugging을 위해 보는 메세지,
	 *                                  통상 운영시는 보이지 않도록 함.
	 */
	protected void printMessagePage (HttpServletRequest req, HttpServletResponse res,
		String user_msg, Exception e)
	{
		printMessagePage(req, res, user_msg, com.wms.fw.Utility.getStackTrace(e));
	}

	/**
	 * 이 Method는 반드시 프로젝트에서 구현해서 사용해야 함. 왜냐면, 프로젝트마다
	 * Message를 보여주는 화면이 다를 수 있기 때문.
	 *
	 * @param req javax.servlet.http.HttpServletRequest
	 * @param res javax.servlet.http.HttpServletResponse
	 * @param user_msg 최종 사용자에게 보여질 메세지
	 * @param debug_msg 개발시점에서 개발자가 Debugging을 위해 보는 메세지,
	 *                                  통상 운영시는 보이지 않도록 함.
	 */
	protected abstract void printMessagePage (HttpServletRequest req, HttpServletResponse res, String user_msg, String debug_msg);

	
	/**
	 *
	 * @param req javax.servlet.http.HttpServletRequest
	 * @param res javax.servlet.http.HttpServletResponse
	 * @param msg 최종사용자에게 보여질 메세지
	 */
	protected void printPopupMessagePage (HttpServletRequest req, HttpServletResponse res, String msg)
	{
		printPopupMessagePage(req,res, msg, (String)null);
	}

	/**
	 *
	 * @param req javax.servlet.http.HttpServletRequest
	 * @param res javax.servlet.http.HttpServletResponse
	 * @param user_msg 최종 사용자에게 보여질 메세지
	 * @param e Exception: 개발시점에서 개발자가 Debugging을 위해 보는 메세지,
	 *                                  통상 운영시는 보이지 않도록 함.
	 */
	protected void printPopupMessagePage (HttpServletRequest req, HttpServletResponse res,
		String user_msg, Exception e)
	{
		printPopupMessagePage(req, res, user_msg, com.wms.fw.Utility.getStackTrace(e));
	}

	/**
	 * 이 Method는 반드시 프로젝트에서 구현해서 사용해야 함. 왜냐면, 프로젝트마다
	 * Message를 보여주는 화면이 다를 수 있기 때문.
	 *
	 * @param req javax.servlet.http.HttpServletRequest
	 * @param res javax.servlet.http.HttpServletResponse
	 * @param user_msg 최종 사용자에게 보여질 메세지
	 * @param debug_msg 개발시점에서 개발자가 Debugging을 위해 보는 메세지,
	 *                                  통상 운영시는 보이지 않도록 함.
	 */
	protected abstract void printPopupMessagePage (HttpServletRequest req, HttpServletResponse res, String user_msg, String debug_msg);
	
	protected EJBHome lookup(String jndi,Class clazz) throws Exception {

		EJBHomeFactory factory = EJBHomeFactory.getFactory();
		return factory.lookupHome(jndi,clazz);				
	}
	protected EJBHome lookup(String server,String jndi,Class clazz) throws Exception {

		EJBHomeFactory factory = EJBHomeFactory.getFactory();
		return factory.lookupHome(server,jndi,clazz);				
	}

}