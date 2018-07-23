package com.wms.fw.web;

import org.xml.sax.InputSource;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class provides the data bindings for the screendefinitions.xml
 * and the requestmappings.xml file.
 * The data obtained is maintained by the ScreenFlowManager
 */

public class URLMappingsXmlDAO {

    // constants
    public static final String URL_MAPPING = "url-mapping";
    public static final String URL = "url";
    public static final String PROCESSS_ACTION = "isAction";
    public static final String WEB_ACTION_CLASS = "web-action-class";


    public static Element loadDocument(String location) {
        Document doc = null;
        try {
            URL url = new URL(location);
            InputSource xmlInp = new InputSource(url.openStream());

            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder parser = docBuilderFactory.newDocumentBuilder();
            doc = parser.parse(xmlInp);
            Element root = doc.getDocumentElement();
            root.normalize();
            return root;
        } catch (SAXParseException err) {
            System.err.println ("URLMappingsXmlDAO ** Parsing error" + ", line " +
                        err.getLineNumber () + ", uri " + err.getSystemId ());
            System.err.println("URLMappingsXmlDAO error: " + err.getMessage ());
        } catch (SAXException e) {
            System.err.println("URLMappingsXmlDAO error: " + e);
        } catch (java.net.MalformedURLException mfx) {
            System.err.println("URLMappingsXmlDAO error: " + mfx);
        } catch (java.io.IOException e) {
            System.err.println("URLMappingsXmlDAO error: " + e);
        } catch (Exception pce) {
            System.err.println("URLMappingsXmlDAO error: " + pce);
        }
        return null;
    }




    public static HashMap loadRequestMappings(String location) {
        Element root = loadDocument(location);
        return getRequestMappings(root);
    }




//    private static String getSubTagAttribute(Element root, String tagName, String subTagName, String attribute) {
//        String returnString = "";
//        NodeList list = root.getElementsByTagName(tagName);
//        for (int loop = 0; loop < list.getLength(); loop++) {
//            Node node = list.item(loop);
//            if (node != null) {
//                NodeList  children = node.getChildNodes();
//                for (int innerLoop =0; innerLoop < children.getLength(); innerLoop++) {
//                    Node  child = children.item(innerLoop);
//                    if ((child != null) && (child.getNodeName() != null) && child.getNodeName().equals(subTagName) ) {
//                        if (child instanceof Element) {
//                            return ((Element)child).getAttribute(attribute);
//                        }
//                    }
//                } // end inner loop
//            }
//        }
//        return returnString;
//    }


    public static HashMap getRequestMappings(Element root) {
        HashMap urlMappings = new HashMap();
        NodeList list = root.getElementsByTagName(URL_MAPPING);
        for (int loop = 0; loop < list.getLength(); loop++) {
            Node node = list.item(loop);
            if (node != null) {
                String url = "";
                String webActionClass =null;
                boolean isAction = false;
                // get url mapping attributes
                // need to be a element to get attributes
                if (node instanceof Element) {
                    Element element = ((Element)node);
                    url = element.getAttribute(URL);
                }
                webActionClass = getSubTagValue(node, WEB_ACTION_CLASS);
                if (webActionClass != null) isAction= true;
                URLMapping mapping = new URLMapping(url, 
                                             isAction,
                                             webActionClass);

                if (!urlMappings.containsKey(url)) {
                    urlMappings.put(url, mapping);
                } else {
                    System.err.println("*** Non Fatal errror: Screen " + url +
                                       " defined more than once in screen definitions file");
                }
            }
        }
        return urlMappings;
    }

    public static String getSubTagValue(Node node, String subTagName) {
        String returnString = "";
        if (node != null) {
            NodeList  children = node.getChildNodes();
            for (int innerLoop =0; innerLoop < children.getLength(); innerLoop++) {
                Node  child = children.item(innerLoop);
                if ((child != null) && (child.getNodeName() != null) && child.getNodeName().equals(subTagName) ) {
                    Node grandChild = child.getFirstChild();
                    if (grandChild.getNodeValue() != null) return grandChild.getNodeValue();
                }
            } // end inner loop
        }
        return returnString;
    }

    public static String getSubTagValue(Element root, String tagName, String subTagName) {
        String returnString = "";
        NodeList list = root.getElementsByTagName(tagName);
        for (int loop = 0; loop < list.getLength(); loop++) {
            Node node = list.item(loop);
            if (node != null) {
                NodeList  children = node.getChildNodes();
                for (int innerLoop =0; innerLoop < children.getLength(); innerLoop++) {
                    Node  child = children.item(innerLoop);
                    if ((child != null) && (child.getNodeName() != null) && child.getNodeName().equals(subTagName) ) {
                        Node grandChild = child.getFirstChild();
                        if (grandChild.getNodeValue() != null) return grandChild.getNodeValue();
                    }
                } // end inner loop
            }
        }
        return returnString;
    }

    public static String getTagValue(Element root, String tagName) {
        String returnString = "";
        NodeList list = root.getElementsByTagName(tagName);
        for (int loop = 0; loop < list.getLength(); loop++) {
            Node node = list.item(loop);
            if (node != null) {
                Node child = node.getFirstChild();
                if ((child != null) && child.getNodeValue() != null) return child.getNodeValue();
            }
        }
        return returnString;
    }
}


