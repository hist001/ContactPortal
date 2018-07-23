package com.wms.xinternet.beans.dao;

import java.io.*;
import java.util.LinkedList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import com.wms.xinternet.beans.dto.ProtocolXmlDTO;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import com.wms.fw.Utility;
import com.wms.fw.DTO;
import com.wms.fw.Logger;

public class ProtocolXmlDAO{

    private String className;                //dto클래스명
	private java.lang.reflect.Field[] field; //field명들
    private String primaryKeyName;           //pk명
    private StringBuffer xmlSubDoc;          //레코드별 xml
    private LinkedList recList;              //ProtocolXmlDTO 집합
	private LinkedList creatList;
	private LinkedList updatList;
	private LinkedList deletList;
	int     pkIdx = -1;                          //primaryKey의 칼럼 index

	public ProtocolXmlDAO(String className){
		this(className,null);    
	}

	public ProtocolXmlDAO(String className,String primaryKeyName){ 
		try{
			this.className      =  className;
			this.primaryKeyName =  primaryKeyName;
			recList             =  new LinkedList();
			creatList           =  new LinkedList();
			updatList           =  new LinkedList();
			deletList           =  new LinkedList();
			xmlSubDoc           =  new StringBuffer("");
			field				= (Class.forName(className)).getFields();		
		}catch(Exception e){
			Logger.err.println(Utility.getStackTrace(e));
		}
    }

    public void setPrimaryKeyName(String primaryKeyName)//pk설정
    {
        this.primaryKeyName = primaryKeyName;
    }

    public String getPrimaryKeyName()//pk리턴
    {
        return primaryKeyName;
    }

    private String getIdxToChar(int idx)//영문자로 전환 getIdxToChar
    {
        String returns = null;
        if(idx > 25)
        {
            char character[] = {
                (char)(int)(Math.floor(idx / 26) + 96D), (char)(idx % 26 + 97)
            };
            returns = new String(character);
        } else{
            char character[] = {(char)(idx + 97)};
            returns = new String(character);
        }
        return returns;
    }

    private String rplEncoding(String str)//치환 rplEncoding
    {
        String returns = str;
        if(str != null && str != "")
        {
            returns = Utility.replace(returns, "&", "&amp;");
            returns = Utility.replace(returns, "\"", "&quot;");
            returns = Utility.replace(returns, "<", "&lt;");
            returns = Utility.replace(returns, ">", "&gt;");
            char carriageReturn[] = {'\r'};
            char newLine[]        = {'\n'};
            char tab[]            = {'\t'};
            char space[]          = {' '};
            returns = Utility.replace(returns, new String(newLine), "&#10;");
            returns = Utility.replace(returns, new String(carriageReturn), "&#13;");
            returns = Utility.replace(returns, new String(tab), "&#9;");
            returns = Utility.replace(returns, new String(space), "&#32;");
        }
        return returns;
    }

    public void add(DTO dto)//레코드추가
    {
        add("e", dto);
    }

    public void add(String type, DTO dto)//레코드추가xml에 저장
    {
		try{
			String fieldname=null;
			StringBuffer xmlTxt = new StringBuffer("<");
			xmlTxt.append(type);
			xmlTxt.append(" ");
			int idx			= 0;
			String keyName  = null;
			//boolean isFind  = false;
			for (int i=0 ; i<field.length; i++) {
				fieldname = field[i].getName();
				/*
				keyName   = getIdxToChar((isFind)?idx-1:idx);
				if(fieldname.equals(primaryKeyName)){keyName="xk";isFind=true;}
				xmlTxt.append(keyName);
				xmlTxt.append("=\"");
				xmlTxt.append(rplEncoding((String)field[i].get(dto)));
				xmlTxt.append("\" ");
				*/
				keyName   = getIdxToChar(idx);
				if(fieldname.equals(primaryKeyName)){
					xmlTxt.append("xk=\"");
					xmlTxt.append(rplEncoding((String)field[i].get(dto)));
					xmlTxt.append("\" ");				
				}
				xmlTxt.append(keyName);
				xmlTxt.append("=\"");
				xmlTxt.append(rplEncoding((String)field[i].get(dto)));
				xmlTxt.append("\" ");

				idx++;
			}
			xmlTxt.append(" />");
			xmlSubDoc.append(xmlTxt.toString());
		}catch(Exception e){
			Logger.err.println(Utility.getStackTrace(e));
		}
    }

    public ProtocolXmlDTO get(int idx)//ProtocolXmlDTO 리턴
    {	
        return (ProtocolXmlDTO)recList.get(idx);
    }

    public LinkedList getList()//ProtocolXmlDTO들을 리턴
    {
        return recList;
    }

    public String getXmlDoc()//xml문서 리턴
    {
		String preFix="";
		StringBuffer xmlTxt=new StringBuffer("<?xml version='1.0' encoding='UTF-8'?>");
		//StringBuffer xmlTxt=new StringBuffer("<?xml version='1.0' encoding='EUC-KR'?>");
		xmlTxt.append("<root fields=\"");
		StringBuffer xmlField=new StringBuffer("");
		String fieldname=null;
		for (int i=0 ; i<field.length; i++) {
			fieldname = field[i].getName();
			//if(!fieldname.equals(primaryKeyName)){
				xmlField.append(preFix);
				xmlField.append(fieldname);
				preFix = "|";
			//}
		}
		xmlTxt.append(rplEncoding(xmlField.toString()));
		xmlTxt.append("\">");
		xmlTxt.append(xmlSubDoc.toString());
		xmlTxt.append("</root>");

		return xmlTxt.toString();

    }

    public void setXmlDoc(Document doc){
        try
        {
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            
            if(nodeList != null)
            {
				Node node          = null;
				ProtocolXmlDTO dto = null;
				Element element    = null;
				
				StringWriter writer = new StringWriter();
				StreamResult result = new StreamResult(writer);
				DOMSource ds		= null;
				TransformerFactory factory = TransformerFactory.newInstance();
				Transformer transformer = factory.newTransformer();
				transformer.setOutputProperty("omit-xml-declaration", "yes");
				
				LinkedList valueList = null;
				String value         = null;
                for(int i = 0; i < nodeList.getLength(); i++)
                {
                    node = doc.getDocumentElement().getChildNodes().item(i);
                    element = (Element)node;                    
                    ds = new DOMSource(node);
	
                    transformer.transform(ds, result);
			
					dto = new ProtocolXmlDTO();
                    dto.xmlSubDoc=writer.toString();					
                    dto.primaryKey=element.getAttribute("xk");
                    dto.type=element.getTagName();
					xmlSubDoc.append(dto.xmlSubDoc); 

                    valueList = new LinkedList();
					value	  = null;
                    for(int j=0; element.hasAttribute(getIdxToChar(j)); j++)
                    {
                        value = element.getAttribute(getIdxToChar(j));
                        valueList.add(value);
                    }                  
                    dto.valueList=valueList;
                    recList.add(dto);

					if(dto.type.equals("insert")){
						creatList.add(getDTO(dto));
					}else if(dto.type.equals("update")){
						updatList.add(getDTO(dto));
					}else if(dto.type.equals("delete")){
						deletList.add(getDTO(dto));				
					}
                }
            }
        }
        catch(Exception e)
        {
			Logger.err.println(Utility.getStackTrace(e));
        }
    }

    public void setXmlDoc(String doc){
        try
        {                        
            setXmlDoc(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(doc))));
        }
        catch(Exception e){
			Logger.err.println(Utility.getStackTrace(e));
        }
    }

    public void setXmlDoc(Reader reader){
        try
        {
            setXmlDoc(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new BufferedReader(reader))));
        }
        catch(Exception e){
			Logger.err.println(Utility.getStackTrace(e));
		}
    }

	private DTO getDTO(ProtocolXmlDTO dto){
		DTO returns=null;
		try{
			if(dto!=null){
				returns=(DTO)Class.forName(className).newInstance();
				if(dto.valueList.size()==0){
					if(primaryKeyName!=null){
						if(pkIdx==-1){
							for(int i=0;i<field.length;i++){
								if(field[i].getName().equals(primaryKeyName)){							
									pkIdx=i;break;
								}
							}
						}
						field[pkIdx].set(returns,dto.primaryKey);
					}

				}else{
					for(int i=0;i<dto.valueList.size();i++){
						field[i].set(returns,(String)dto.valueList.get(i));
						if(primaryKeyName!=null){
							if(field[i].getName().equals(primaryKeyName)){							
								field[i].set(returns,dto.primaryKey);
							}
						}
					}
				}
				
			}
		}
		catch(Exception e){
			Logger.err.println(Utility.getStackTrace(e));
		}
		return returns;
	}
	//등록자료 리턴
	public LinkedList getCreatList(){
		return creatList;
	}
	//수정자료 리턴
	public LinkedList getUpdatList(){
		return updatList;
	}
	//삭제자료 리턴
	public LinkedList getDeletList(){
		return deletList;
	}

}
