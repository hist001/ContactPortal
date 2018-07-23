package com.wms.fw.web.action;

import javax.ejb.EJBHome;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import java.io.IOException;

import com.wms.fw.Config;
import com.wms.fw.Configuration;
import com.wms.fw.Logger;
import com.wms.fw.ejb.EJBHomeFactory;
/**
 * This class is the default implementation of the WebAction
 *
*/
public abstract class HTMLActionSupport implements HTMLAction {
    private static String errorPage;
	private static String progressPage;
    protected ServletContext context;
    
    public void setServletContext(ServletContext context) {
        this.context  = context;
    }
	protected EJBHome lookup(String jndi,Class clazz) throws Exception {

		EJBHomeFactory factory = EJBHomeFactory.getFactory();
		return factory.lookupHome(jndi,clazz);				
	}
	protected EJBHome lookup(String server,String jndi,Class clazz) throws Exception {

		EJBHomeFactory factory = EJBHomeFactory.getFactory();
		return factory.lookupHome(server,jndi,clazz);				
	}
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
			erorrMessagePage(req,res, e);
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

			// Servlet 2.1
			RequestDispatcher dispatcher = context.getRequestDispatcher(jspfile);
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
			erorrMessagePage(req,res, e);
		}
	}
	protected void erorrMessagePage(
		HttpServletRequest req,
		HttpServletResponse res,Exception e) {
		//Exception처리....
		if(errorPage==null){
			try{
				errorPage   = (new Configuration()).getString("com.wms.fw.servlet.errorPage");
			}catch(Exception ex){				
			}
		}
		req.setAttribute("e",e);
		printJspPage(req,res,errorPage);
	}
	protected void progressMessagePage(
		HttpServletRequest req,
		HttpServletResponse res,
		String page) {
		//browser의 버퍼 비워줌
		if(progressPage==null){
			try{
				progressPage   = (new Configuration()).getString("com.wms.fw.servlet.progressPage");
			}catch(Exception e){}
		}
		req.setAttribute("movePage",page);
		printJspPage(req,res,progressPage);
	}
    public void doStart(HttpServletRequest request,HttpServletResponse response){}
    public void doEnd(HttpServletRequest request,HttpServletResponse response){}
}

