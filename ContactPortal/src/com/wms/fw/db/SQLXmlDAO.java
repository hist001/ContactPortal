package com.wms.fw.db;
import org.xml.sax.InputSource;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.wms.fw.db.SQLMapping;
//import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

//import java.net.URL;
//import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;

public class SQLXmlDAO {

    // constants
    public static final String SQL_MAPPING = "sql-mapping";
    public static final String URL = "url";
	public static final String ID  = "id";
	public static final String VALUE_COUNT="value-count";
    public static final String SQL_CONTENT = "sql-content";
	/*
	<?xml version="1.0" encoding="ISO-8859-1"?>
	<sql>
	   <sql-mapping id="S001" value-count="1" url="realCost-insert" >        
	   <sql_content>select * from board where id=?</sql_content>
	   </sql-mapping>
	</sql>

	*/
   
   /*
	public static void main(String[] args){
		try{
			HashMap hm    = SQLXmlDAO.loadRequestMappings(args[0]);
			System.out.println("RealCostQueryHelper.executeRealCost loadRequestMappings after");
			//String[] keys = {"R_001","R_002","R_003","R_004","R_005","R_006",
			//				 "R_007","R_008","R_009","R_010","R_011","R_012"};
			String[] keys = {"R_001"};

			SQLMapping sm = null;		
			//for(int i=0;i<keys.length;i++){
				//sm = (SQLMapping)hm.get(keys[i]);
				//for(int j=1;j<=sm.cnt;j++){
					//sm.setString(j,"200508");
				//}
				sm = (SQLMapping)hm.get("R_001");
				//sm.setString(1,"hello");
				sm.setInt(2,10);
				System.out.println(sm.toSql());
			//}		
			
		}catch(Exception e){e.printStackTrace();}
	}
	*/
   
	

    public static Element loadDocument(String location) {
        Document doc = null;
        try {
            InputSource xmlInp = new InputSource(new BufferedReader(new FileReader(location)));

            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder parser = docBuilderFactory.newDocumentBuilder();
            doc = parser.parse(xmlInp);
            Element root = doc.getDocumentElement();
            root.normalize();
            return root;
        } catch (SAXParseException err) {
            System.err.println ("SQLXmlDAO ** Parsing error" + ", line " +
                        err.getLineNumber () + ", uri " + err.getSystemId ());
            System.err.println("SQLXmlDAO error: " + err.getMessage ());
        } catch (SAXException e) {
            System.err.println("SQLXmlDAO error: " + e);
        } catch (java.net.MalformedURLException mfx) {
            System.err.println("SQLXmlDAO error: " + mfx);
        } catch (java.io.IOException e) {
            System.err.println("SQLXmlDAO error: " + e);
        } catch (Exception pce) {
            System.err.println("SQLXmlDAO error: " + pce);
        }
        return null;
    }

    public static HashMap loadRequestMappings(String location)throws Exception {
        Element root = loadDocument(location);
        return getRequestMappings(root);
    }

    private static String getSubTagAttribute(Element root, String tagName, String subTagName, String attribute) {
        String returnString = "";
        NodeList list = root.getElementsByTagName(tagName);
        for (int loop = 0; loop < list.getLength(); loop++) {
            Node node = list.item(loop);
            if (node != null) {
                NodeList  children = node.getChildNodes();
                for (int innerLoop =0; innerLoop < children.getLength(); innerLoop++) {
                    Node  child = children.item(innerLoop);
                    if ((child != null) && (child.getNodeName() != null) && child.getNodeName().equals(subTagName) ) {
                        if (child instanceof Element) {
                            return ((Element)child).getAttribute(attribute);
                        }
                    }
                } // end inner loop
            }
        }
        return returnString;
    }


    public static HashMap getRequestMappings(Element root)throws Exception {
        HashMap urlMappings = new HashMap();
        NodeList list = root.getElementsByTagName(SQL_MAPPING);
        for (int loop = 0; loop < list.getLength(); loop++) {
            Node node = list.item(loop);
            if (node != null) {
                String id = "";
				String url= "";
				String count="";
                String sqlContent =null;
                boolean isAction = false;
                // get url mapping attributes
                // need to be a element to get attributes
                if (node instanceof Element) {
                    Element element = ((Element)node);
                    id = element.getAttribute(ID);
					url= element.getAttribute(URL);
					count=element.getAttribute(VALUE_COUNT);
                }
                sqlContent = getSubTagValue(node, SQL_CONTENT, 1);
				
                if (sqlContent != null) isAction= true;
                SQLMapping mapping = new SQLMapping(id, 
										   count,
                                           url,
                                           sqlContent);

                if (!urlMappings.containsKey(id)) {
                    urlMappings.put(id, mapping);
					 
                } else {
                    System.err.println("ÀÌ¹Ì µî·ÏµÇ¾îÀÖ´Â id ÀÔ´Ï´Ù.");
                }
            }
        }
        return urlMappings;
    }

    public static String getSubTagValue(Node node, String subTagName,int idx) {
        String returnString = "";
        if (node != null) {
            NodeList  children = node.getChildNodes();
            for (int innerLoop =0; innerLoop < children.getLength(); innerLoop++) {
                Node  child = children.item(innerLoop);
                if ((child != null) && (child.getNodeName() != null) && child.getNodeName().equals(subTagName) ) {
                    //Node grandChild = child.getFirstChild();
					Node grandChild =  child.getChildNodes().item(idx);
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


