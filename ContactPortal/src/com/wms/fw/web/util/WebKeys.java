package com.wms.fw.web.util;

/**
 * This interface contains all the keys that are used to
 * store data in the different scopes of web-tier. These
 * values are the same as those used in the JSP
 * pages (useBean tags).
 */
public class WebKeys {

    protected WebKeys() {} // prevent instanciation

    public static final String COMPONENT_MANAGER = "com.hist.haf.web.COMPONENT_MANAGER";
    public static final String SCREEN_FLOW_MANAGER = "com.hist.haf.web.SCREEN_FLOW_MANAGER";
    public static final String REQUEST_PROCESSOR = "com.hist.haf.web.REQUEST_PROCESSOR";
    public static final String CURRENT_SCREEN = "com.hist.haf.web.CURRENT_SCREEN";
    public static final String PREVIOUS_SCREEN = "com.hist.haf.web.PREVIOUS_SCREEN";
    public static final String CURRENT_URL = "com.hist.haf.web.CURRENT_URL";
    public static final String PREVIOUS_URL = "com.hist.haf.web.PREVIOUS_URL";
    public static final String PREVIOUS_REQUEST_PARAMETERS = "com.hist.haf.web.PREVIOUS_REQUEST_PARAMETERS";
    public static final String PREVIOUS_REQUEST_ATTRIBUTES = "com.hist.haf.web.PREVIOUS_REQUEST_ATTRIBUTES";
    public static final String URL_MAPPINGS = "com.hist.haf.web.URL_MAPPINGS";
    public static final String EVENT_MAPPINGS = "com.hist.haf.web.EVENT_MAPPINGS";
    public static final String MISSING_FORM_DATA = "com.hist.haf.web.MISSING_FORM_DATA";
    public static final String SERVER_TYPE = "com.hist.haf.web.SERVER_TYPE";
    public static final String LOCALE = "com.hist.haf.web.LOCALE";
    public static final String WEB_CONTROLLER = "com.hist.haf.web.WEB_CLIENT_CONTROLLER";
    public static final String EJB_CONTROLLER = "com.hist.haf.web.EJB_CLIENT_CONTROLLER ";
}

