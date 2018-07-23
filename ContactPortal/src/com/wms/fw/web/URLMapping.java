package com.wms.fw.web;


public class URLMapping implements java.io.Serializable {

    private String url;
    private boolean isAction = false;
    private String webActionClass = null;

    public URLMapping(String url) {
        this.url = url;
    }

    public URLMapping(String url,
                                    boolean isAction,
                                    String webActionClass)
    {
        this.url = url;
        this.webActionClass = webActionClass;
        this.isAction = isAction;
    }

    public boolean isAction() {
        return isAction;
    }

    public String getWebAction() {
        return webActionClass;
    }

    public String toString() {
        return "[URLMapping: url=" + url +
                ", isAction=" + isAction +
                ", webActionClass=" + webActionClass +
                "]";
    }
}


