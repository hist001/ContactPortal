package com.wms.fw.web;

import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletConfig;

// J2EE Imports
import javax.naming.NamingException;

import com.wms.fw.Config;
import com.wms.fw.Configuration;
import com.wms.fw.Logger;

import com.wms.fw.web.util.WebKeys;

public class MainServlet extends HttpServlet {
	private static String lineSeparator  = System.getProperty("line.separator");
    private ServletContext context;
    private HashMap urlMappings;
    private String errorPage;
	private boolean trace = false;
    private RequestProcessor requestProcessor;


    public void init(ServletConfig config) throws ServletException {

        this.context = config.getServletContext();
        String requestMappingsURL = null;
        try {
			//매핑 화일 추출
            requestMappingsURL = context.getResource("/WEB-INF/mappings.xml").toString();
        } catch (java.net.MalformedURLException ex) {
            System.err.println("MainServlet: initializing RequestProcessor malformed URL exception: " + ex);
        }
		try {
			Config conf = new Configuration();
			trace       = conf.getBoolean("com.wms.fw.servlet.baseservlet.trace");
			errorPage   = conf.getString("com.wms.fw.servlet.errorPage");
		}
		catch(Exception e) {}

		//매핑 URL 파싱
       urlMappings = URLMappingsXmlDAO.loadRequestMappings(requestMappingsURL);
       //apprication에 등록
       context.setAttribute(WebKeys.URL_MAPPINGS, urlMappings);
       getRequestProcessor();
    }

     public  void doGet(HttpServletRequest request, HttpServletResponse  response)
        throws IOException, ServletException {
        doProcess(request, response);
    }

    public  void doPost(HttpServletRequest request, HttpServletResponse  response)
        throws IOException, ServletException {
        doProcess(request, response);

    }

    private void doProcess(HttpServletRequest request, HttpServletResponse response)
                   throws IOException, ServletException {

		String logMsg = null;
		long start = 0, end = 0;
		if ( trace ) {
			logMsg = request.getRequestURI() + ":" + request.getRemoteHost() + "(" + request.getRemoteAddr() + ")";
			String user = request.getRemoteUser();
			if ( user != null ) logMsg += ":" + user;

			start = System.currentTimeMillis();
			Logger.sys.println(this, logMsg + ":calling");
		}

		try {
			 getRequestProcessor().processRequest(request,response);
		}
		catch(Exception e){
			java.io.ByteArrayOutputStream bos = new java.io.ByteArrayOutputStream();
			java.io.PrintWriter writer = new java.io.PrintWriter(bos);
			e.printStackTrace(writer);
			writer.flush();
			String errString = "Programmer's Exception: " +logMsg +  lineSeparator + bos.toString();
			Logger.err.println(this, errString);
			erorrMessagePage(request,response,e);			
		}

		if ( trace ) {
			end = System.currentTimeMillis();
			Logger.sys.println(this, logMsg + ":end(elapsed=" + (end-start) + ")" + lineSeparator);
		}

    }

    private RequestProcessor getRequestProcessor() {
         RequestProcessor rp = (RequestProcessor)context.getAttribute(WebKeys.REQUEST_PROCESSOR);
         if ( rp == null ) {
             rp = new RequestProcessor();
             rp.init(context);
             context.setAttribute(WebKeys.REQUEST_PROCESSOR, rp);
        }
       return rp;
    }


    /**
     * The UrlMapping object contains information that will match
     * a url to a mapping object that contains information about
     * the current screen, the HTMLAction that is needed to
     * process a request, and the HTMLAction that is needed
     * to insure that the propper screen is displayed.
    */

    private URLMapping getURLMapping(String urlPattern) {
        if ((urlMappings != null) && urlMappings.containsKey(urlPattern)) {
            return (URLMapping)urlMappings.get(urlPattern);
        } else {
            return null;
        }
    }

	protected void erorrMessagePage(
		HttpServletRequest req,
		HttpServletResponse res,Exception e) {
		try{
		//Exception처리....
			req.setAttribute("e",e);
			getServletContext().getRequestDispatcher(errorPage).forward(req,res);
		}
		catch (Exception ex) {}
	}

}

