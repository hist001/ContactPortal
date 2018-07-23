package com.wms.fw.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;

/**
 * This class is the base interface to request handlers on the
 * web tier.
 *
*/
public interface HTMLAction extends java.io.Serializable {

    public void setServletContext(ServletContext context);
    public void doStart(HttpServletRequest request,HttpServletResponse response);
    public void perform(HttpServletRequest request,HttpServletResponse response);
    public void doEnd(HttpServletRequest request,HttpServletResponse response);
}

