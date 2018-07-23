package com.wms.fw.web;

import java.util.Collection;
import java.util.HashMap;

// J2ee Imports
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;

// BluePrints/WAF imports
//import com.sun.j2ee.blueprints.util.tracer.Debug;
import com.wms.fw.web.util.WebKeys;
import com.wms.fw.web.action.HTMLAction;

/**
 * This is the web tier controller for the sample application.
 *
 * This class is responsible for processing all requests received from
 * the Main.jsp and generating necessary events to modify data which
 * are sent to the WebController.
 *
 */
public class RequestProcessor implements java.io.Serializable {

    private HashMap urlMappings;

    public RequestProcessor() {}


    public void init(ServletContext context) {
        urlMappings = (HashMap)context.getAttribute(WebKeys.URL_MAPPINGS);
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


    /**
    * This method is the core of the RequestProcessor. It receives all requests
    *  and generates the necessary events.
    */
    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        String fullURL = request.getRequestURI();
        // get the screen name
        String selectedURL = null;
        int lastPathSeparator = fullURL.lastIndexOf("/") + 1;
        if (lastPathSeparator != -1) {
            selectedURL = fullURL.substring(lastPathSeparator, fullURL.length());
        }
        URLMapping urlMapping = getURLMapping(selectedURL);
        HTMLAction action = getAction(urlMapping);
        if (action != null) {
            action.setServletContext(request.getSession().getServletContext());
            action.doStart(request,response);
            action.perform(request,response);
            action.doEnd(request,response);
        }
    }

    /**
     * This method load the necessary HTMLAction class necessary to process a the
     * request for the specified URL.
     */

    private HTMLAction getAction(URLMapping urlMapping) {
        HTMLAction handler = null;
        String actionClassString = null;
        if (urlMapping != null) {
            actionClassString = urlMapping.getWebAction();
            if (urlMapping.isAction()) {
                try {
                    handler = (HTMLAction)getClass().getClassLoader().loadClass(actionClassString).newInstance();
                } catch (Exception ex) {
                    System.err.println("RequestProcessor caught loading action: " + ex);
                }
            }
        }
        return handler;
    }
}

