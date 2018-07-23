package com.wms.fw;

/**
 * @(#)Utility.java
 */
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Vector;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

//import com.wms.comPopup.beans.dto.ComPopupDTO;
import com.wms.common.beans.dto.DataSet;
import com.wms.common.beans.dto.UploadFilesDTO;
import com.wms.fw.db.DataBaseUtil;
import com.wms.fw.db.SQLMapping;
import com.wms.fw.db.SQLXmlDAO;
import com.wms.fw.Configuration;

public final class Utility {

	/**
	 * Don't let anyone instantiate this class
	 */
	private Utility() {}

	/**
	 * Object[] �� ����. Object�� Array �� ����(clone)�Ͽ�
	 * ���ο� Instance�� ����� �ݴϴ�.
	 *
	 * @param objects java.lang.Object[]
	 * @return java.lang.Object[]
	 *
	 * @see clone(java.lang.Object)
	 * @see clone(java.lang.Vector)
	 */
	public static Object[] clone(Object[] objects)
	{
		int length = objects.length;
		Class c = objects.getClass().getComponentType();
		Object array = Array.newInstance(c, length);

		for(int i=0;i<length;i++){
			Array.set(array, i, Utility.clone(objects[i]));
		}
		return (Object[])array;
	}

	/**
	 * Object �� ����. �Ϲ������� <code>java.lang.Object.clone()</code> �Լ���
	 * �� ����Ͽ� Object�� �����ϸ� Object���� �ִ� Primitive type�� ������ Object
	 * field���� ������ �Ǵ� ���� �ƴ϶� ���� Object�� reference��
	 * ���� �ȴ�.<br>
	 * �׷��� �� Method�� ����ϸ� �� field�� ������ Object�� ���� ����(clone)�Ͽ�
	 * �ش�.
	 *
	 * @param object java.lang.Object
	 * @return java.lang.Object
	 *
	 * @see clone(java.lang.Object[])
	 * @see clone(java.lang.Vector)
	 */
	public static Object clone(Object object)
	{
		Class c = object.getClass();
		Object newObject = null;
		try {
			newObject = c.newInstance();
		}
		catch(Exception e ){
			return null;
		}

		Field[] field = c.getFields();
		for (int i=0 ; i<field.length; i++) {
			try {
				Object f = field[i].get(object);
				field[i].set(newObject, f);
			}
			catch(Exception e){
			}
		}
		return newObject;
	}

	/**
	 * Vector �� ����. �Ϲ������� Vector Object�� clone()�� �ϸ�
	 * Vector���� Element Object�� ���� �����Ǵ� ���� �ƴ϶�
	 * ������ Object�� ���� reference�� ���� �����Ǳ� ������ ���� Element Object��
	 * reference�ϴ� Vector�� �����ϰ� �ȴ�. �׷��� �� method�� ����ϸ�
	 * Vector���� ��� Element Object�� ���� �����Ͽ� �ش�.
	 *
	 * @param objects java.util.Vector
	 * @return java.util.Vector
	 *
	 * @see clone(java.lang.Object)
	 * @see clone(java.lang.Object[])
	 */
	public static Vector clone(Vector objects)
	{
		Vector newObjects = new Vector();
		Enumeration e = objects.elements();
		while(e.hasMoreElements()){
			Object o = e.nextElement();
			newObjects.addElement(Utility.clone(o));
		}
		return newObjects;
	}

	/**
	 * Entity Class�� null string field �ʱ�ȭ.
	 * <p>
	 * Entity class ���� �ִ� java.lang.String���� field�� DB�� Column��
	 * ������ ������ �ִ� ��찡 ����. �̷��� Entity Field�� Ư�� GUI�� Ư��
	 * TextFiled�� ǥ���Ǿ�� �ϴ� ��쵵 ����. ���� �� String Filed�� null��
	 * ��� ������ �˻縦 �Ѵٴ� ���� ������ ����� ���� �ƴ� �� ����.
	 * <p>
	 * �� method�� �������� Object ���� �ִ� ��� java.lang.String���� field ���� ��
	 * null ������ �� field�� ���̰� 0 �� blank string("")���� �ʱ�ȭ �����ش�.
	 * <p>
	 *
	 * <xmp>
	 * Sample Code:
	 * public java.util.Vector selectAll() throws Exception
	 * {
	 *  java.util.Vector list = new Vector();
	 * 	Statement stmt = null;
	 * 	ResultSet rs =null;
	 * 	try{
	 * 		stmt = conn.createStatement();
	 * 		String query = "select " +
	 * 			"id, " +
	 * 			"name, " +
	 * 			"desc " +
	 * 			"from THE10 " +
	 * 			"order by id ";
	 *
	 * 		rs = stmt.executeQuery(query);
	 *
	 * 		while ( rs.next() ) {
	 * 			AdminAuth entity = new AdminAuth();
	 * 			entity.id = rs.getString("id");
	 * 			entity.name = rs.getString("name");
	 * 			entity.desc = rs.getString("desc");
	 * 			Utility.fixNull(entity);
	 * 			list.addElement(entity);
	 * 		}
	 * 	}
	 * 	finally {
	 * 		try{rs.close();}catch(Exception e){}
	 * 		try{stmt.close();}catch(Exception e){}
	 * 	}
	 * 	return list;
	 * }
	 *</xmp
	 *
	 * @param java.lang.Object Object���� public java.lang.String ����
	 *        member variable���� ������ �ش�.
	 *
	 * @see fixNullAll(java.lang.Object)
	 * @see fixNullAndTrim(java.lang.Object)
	 * @see fixNullAndTrimAll(java.lang.Object)
	 * @author WonYoung Lee, wyounglee@lgeds.lg.co.kr
	 */
	public static void fixNull(Object o)
	{
		if ( o == null ) return;

		Class c = o.getClass();
		if ( c.isPrimitive() ) return;

		Field[] fields = c.getFields();
		for (int i=0 ; i<fields.length; i++) {
			try {
				Object f = fields[i].get(o);
				Class fc = fields[i].getType();

				if ( fc.getName().equals("java.lang.String") ) {
					if ( f == null ) fields[i].set(o, "");
					else	fields[i].set(o, f);
				}
			}
			catch(Exception e){
			}
		}
	}

	/**
	 * Entity Class�� ������� null string field �ʱ�ȭ.
	 * <p>
	 * fixNull() �� ������ ����� �ϴµ�, java.lang.String field �Ӹ� �ƴ϶�
	 * Member ���� �� Array, Object �� ������ ��������� ?i�� ���� String����
	 * blank string("")���� ����� �ش�.<br>
	 * �������� String�� ��� trim()�� �����ش�.<br>
	 * ���� Array��, Vector�� null�� ��� Instanceȭ�� ���� �ʴ´�.
	 *
	 * <p>
	 * ��������� �����Ǵ� ��ŭ, �θ�� �ڽİ��� ���� ����� reference�� ���� ������
	 * ���� �ȵȴ�. Stack Overflow�� ���� JVM�� ���� ���̴�.
	 *
	 *
	 * @param java.lang.Object Object���� public String ���Ӹ� �ƴ϶�, Object[], Vector ���
	 *        ���� public Object�� Member Variable�� ������ �ش�.
	 *
	 * @see fixNull(java.lang.Object)
	 * @see fixNullAndTrim(java.lang.Object)
	 * @see fixNullAndTrimAll(java.lang.Object)
	 *
	 */
	public static void fixNullAll(Object o)
	{
		if ( o == null ) return;

		Class c = o.getClass();
		if ( c.isPrimitive() ) return;

		if( c.isArray() ) {
			int length = Array.getLength(o);
			for(int i=0; i<length ;i++){
				Object element = Array.get(o, i);
				Utility.fixNullAll(element);
			}
		}
		else {
			Field[] fields = c.getFields();
			for (int i=0 ; i<fields.length; i++) {
				try {
					Object f = fields[i].get(o);
					Class fc = fields[i].getType();
					if ( fc.isPrimitive() ) continue;
					if ( fc.getName().equals("java.lang.String") ) {
						if ( f == null ) fields[i].set(o, "");
						else	fields[i].set(o, f);
					}
					else if ( f != null ) {
						Utility.fixNullAll(f);
					}
					else {} // Some Object, but it's null.
				}
				catch(Exception e) {
				}
			}
		}
	}

	/**
	 * Entity Class�� null string field �ʱ�ȭ &amp; trim().
	 * <p>
	 * Entity class ���� �ִ� java.lang.String���� field�� DB�� Column��
	 * ������ ������ �ִ� ��찡 ����. �̷��� Entity Field�� Ư�� GUI�� Ư��
	 * TextFiled�� ǥ���Ǿ�� �ϴ� ��쵵 ����. ���� �� String Filed�� null��
	 * ��� ������ �˻縦 �Ѵٴ� ���� ������ ����� ���� �ƴ� �� ����.
	 * <p>
	 * �� method�� �������� Object ���� �ִ� ��� java.lang.String���� field ���� ��
	 * null ������ �� field�� ���̰� 0 �� blank string("")���� �ʱ�ȭ �����ش�.
	 * ���� null�� �ƴ� �������� String�� ���ԵǾ� ������ ���������� trim()��
	 * �����ش�.
	 * <p>
	 * �� �� trim() �Լ��� java.lang.String �� trim()�� ������� �ʾҴ�.
	 *
	 * <xmp>
	 * Sample Code:
	 * public java.util.Vector selectAll() throws Exception
	 * {
	 *  java.util.Vector list = new Vector();
	 * 	Statement stmt = null;
	 * 	ResultSet rs =null;
	 * 	try{
	 * 		stmt = conn.createStatement();
	 * 		String query = "select " +
	 * 			"id, " +
	 * 			"name, " +
	 * 			"desc " +
	 * 			"from THE10 " +
	 * 			"order by id ";
	 *
	 * 		rs = stmt.executeQuery(query);
	 *
	 * 		while ( rs.next() ) {
	 * 			AdminAuth entity = new AdminAuth();
	 * 			entity.id = rs.getString("id");
	 * 			entity.name = rs.getString("name");
	 * 			entity.desc = rs.getString("desc");
	 * 			Utility.fixNull(entity);
	 * 			list.addElement(entity);
	 * 		}
	 * 	}
	 * 	finally {
	 * 		try{rs.close();}catch(Exception e){}
	 * 		try{stmt.close();}catch(Exception e){}
	 * 	}
	 * 	return list;
	 * }
	 *</xmp
	 *
	 * @param java.lang.Object Object���� public java.lang.String ����
	 *        member variable���� ������ �ش�.
	 *
	 * @see fixNull(java.lang.Object)
	 * @see fixNullAll(java.lang.Object)
	 * @see fixNullAndTrimAll(java.lang.Object)
	 * @see trim(String)
	 */
	public static void fixNullAndTrim(Object o)
	{
		if ( o == null ) return;

		Class c = o.getClass();
		if ( c.isPrimitive() ) return;

		Field[] fields = c.getFields();
		for (int i=0 ; i<fields.length; i++) {
			try {
				Object f = fields[i].get(o);
				Class fc = fields[i].getType();
				if ( fc.getName().equals("java.lang.String") ) {
					if ( f == null) fields[i].set(o, "");
					else {
						String item = Utility.trim( (String)f );
						if(item.equals("null"))item="";
						fields[i].set(o, item);
					}
				}
			}
			catch(Exception e){
			}
		}
	}

	/**
	 * Entity Class�� ������� null string field �ʱ�ȭ  &amp; trim().
	 * <p>
	 * fixNull() �� ������ ����� �ϴµ�, java.lang.String field �Ӹ� �ƴ϶�
	 * Member ���� �� Array, Object �� ������ ��������� ?i�� ���� String����
	 * blank string("")���� ����� �ش�.<br>
	 * �������� String�� ��� trim()�� �����ش�.<br>
	 * ���� Array��, Vector�� null�� ��� Instanceȭ�� ���� �ʴ´�.
	 *
	 * <p>
	 * ��������� �����Ǵ� ��ŭ, �θ�� �ڽİ��� ���� ����� reference�� ���� ������
	 * ���� �ȵȴ�. Stack Overflow�� ���� JVM�� ���� ���̴�.
	 *
	 *
	 * @param java.lang.Object Object���� public String ���Ӹ� �ƴ϶�, Object[], Vector ���
	 *        ���� public Object�� Member Variable�� ������ �ش�.
	 *
	 * @see fixNull(java.lang.Object)
	 * @see fixNullAll(java.lang.Object)
	 * @see fixNullAndTrim(java.lang.Object)
	 * @see trim(String)
	 *
	 */
	public static void fixNullAndTrimAll(Object o)
	{
		if ( o == null ) return;

		Class c = o.getClass();
		if ( c.isPrimitive() ) return;

		if( c.isArray() ) {
			int length = Array.getLength(o);
			for(int i=0; i<length ;i++){
				Object element = Array.get(o, i);
				Utility.fixNullAndTrimAll(element);
			}
		}
		else {
			Field[] fields = c.getFields();
			for (int i=0 ; i<fields.length; i++) {
				try {
					Object f = fields[i].get(o);
					Class fc = fields[i].getType();
					if ( fc.isPrimitive() ) continue;
					if ( fc.getName().equals("java.lang.String") ) {
						if ( f == null ) fields[i].set(o, "");
						else {
							String item = Utility.trim( (String)f );
							if(item.equals("null"))item="";
							fields[i].set(o, item);
						}
					}
					else if ( f != null ) {
						Utility.fixNullAndTrimAll(f);
					}
					else {} // Some Object, but it's null.
				}
				catch(Exception e) {
				}
			}
		}
	}

	/**
	 *
	 * @param e java.lang.Throwable
	 */
	public static String getStackTrace(Throwable e) {
		java.io.ByteArrayOutputStream bos = new java.io.ByteArrayOutputStream();
		java.io.PrintWriter writer = new java.io.PrintWriter(bos);
		e.printStackTrace(writer);
		writer.flush();
		return bos.toString();
	}

	/**
	 * Remove special white space from both ends of this string.
	 * <p>
	 * All characters that have codes less than or equal to
	 * <code>'&#92;u0020'</code> (the space character) are considered to be
	 * white space.
	 * <p>
	 * java.lang.String�� trim()�� �������� �Ϲ����� white space�� ¥���� ����
	 * �ƴ϶� �������� ���� Ư���� blank�� ©�� �ش�.<br>
	 * �� �ҽ��� IBM HOST�� ����Ÿ�� �ְ� ���� �� �����ϰ� ����߾���.
	 * �Ϲ������� ���� �������� ���� ���̴�.
	 *
	 * @param  java.lang.String
	 * @return trimed string with white space removed
	 *         from the front and end.
	 */
	public static String trim(String s) {
		int st = 0;
		char[] val = s.toCharArray();
		int count = val.length;
		int len = count;

		while ((st < len) && ((val[st] <= ' ') || (val[st] == '��') ) )   st++;
		while ((st < len) && ((val[len - 1] <= ' ') || (val[len-1] == '��')))  len--;

		return ((st > 0) || (len < count)) ? s.substring(st, len) : s;
	}
	/**
	 * Replace specified String with new String.
	 *
	 * @return the translated string.
	 * @param src		String		the source string
	 * @param oldStr	String		the old string to be changed
	 * @param newStr	String		the new string
	 */
	public static String replace(String src, String oldStr, String newStr) {
		int idx = 0;
	    int curIdx = 0;
	    StringBuffer result = new StringBuffer();

		curIdx = src.indexOf(oldStr, idx);
	    while ( curIdx >= 0 ) {
			// Replace string and append string.
			result.append(src.substring(idx, curIdx));
			result.append(newStr);
			// Increment search the string index.
			idx = curIdx + oldStr.length();
			curIdx = src.indexOf(oldStr, idx);	// Add this line, this is the fixed point.
	    }

		// After replace all of the oldStr, then if the string remains...
		// append it to result string.
		if (idx <= src.length())
	    	result.append(src.substring(idx, src.length()));	// this..

	    return result.toString();
	}

	/**
	 * ���ڿ����� Ư�� ���ڿ��� ġȯ�Ѵ�.
	 * ���ڿ� �迭�� ���ʴ�� ġȯ�ϵ�
	 * �� �̻� �迭 ���� ������ space 1ĭ���� ġȯ�Ѵ�.
	 *
	 * @return the translated string.
	 * @param	src		String		��ȯ�� ���ڿ�
	 * @param	oldStr	String		ġȯ ��� ���ڿ�
	 * @param	newStr	String[]	ġȯ�� ���ڿ� �迭
	 */
	public static String replace(String src, String oldStr, String[] newStr) {
	    int idx		= 0;
		int startIdx= 0;
	    int curIdx	= 0;

	    StringBuffer result = new StringBuffer();
		String dest = null;

		curIdx = src.indexOf(oldStr, idx);
	    while( curIdx >= 0 ) {
			// Cage : Assign string to be appended.
		    if( idx < newStr.length )	dest = newStr[idx++];
		    else						dest = " ";
			// Replace string and append string.
			result.append(src.substring(startIdx, curIdx))
	    	      .append(dest);
			// Increment search the string index.
			startIdx = curIdx + oldStr.length();
			curIdx = src.indexOf(oldStr, idx);	// Add this line, this is the fixed point.
	    }

		// After replace all of the oldStr, then if the string remains...
		// append it to result string.
		if (startIdx <= src.length())
	    	result.append(src.substring(startIdx, src.length()));

	    return result.toString();
	}

	public static String replace(String str,String point){
		if(str==null)return null;
		StringBuffer returns = new StringBuffer();
		while(str.indexOf(point)>-1){
			returns.append(str.substring(0,str.indexOf(point)));
			str=str.substring(str.indexOf(point)+1);
		}
		returns.append(str);
		return returns.toString();

	}
	public static String remove(String str,String point){
		return replace(str,point);
	}
	//�����ڷ� �и��Ǿ��ִ� ������ �迭�� ���� ���ġ.
	public static String[] toArray(String value,String delim)throws Exception{
		if(value==null)return null;
		if(delim==null)return null;
		StringTokenizer st=new StringTokenizer(value, delim); 		
		int cnt = st.countTokens() ;
		if(cnt==0)return null;
		String[] values= new String[cnt];
		int i=0;
		while(st.hasMoreTokens()){
			values[i]=st.nextToken();
			i++;
		}
		return values;

	}
	/**
    *	���ڸ� �ݾ��������� ���� (000,000,000)
    */
	public static String cashReturn(String num)
	{
		String cashReturn = "";
		String sigValue   = "";
		String preValue   = "";
		String postValue  = "";
		String strPoint   = "";
		if(num.indexOf("-")>-1){
			sigValue="-";
			num=num.substring(num.indexOf("-")+1);
		}
		int pointIndex = num.indexOf(".");
		if(pointIndex<0){
			preValue=num;			
		}else{
			preValue = num.substring(0,num.indexOf("."));
			postValue = num.substring(num.indexOf(".")+1,num.length());
			strPoint=".";
		}
		for (int i = preValue.length()-1; i >= 0; i--){
			cashReturn = preValue.charAt(i) + cashReturn;
			if (i != 0 && i%3 == preValue.length()%3) cashReturn = "," + cashReturn;
		}
		return sigValue+cashReturn+strPoint+postValue;
	}
	//�������� ��������
	public static String rDelete(String str,char del)throws Exception{
		if(str==null)return null;
		if(str.trim().equals("")) return "";
		StringBuffer orgCd=new StringBuffer(str);
		char returns=orgCd.charAt(orgCd.length()-1);		
		while(returns==del){
			orgCd=orgCd.deleteCharAt(orgCd.length()-1);
			if(orgCd.length()==0)break;
			returns=orgCd.charAt(orgCd.length()-1);

		}
		return orgCd.toString();
	}
	//�������� ��������
	public static String lDelete(String str,char del)throws Exception{
		if(str==null)return null;
		if(str.trim().equals("")) return "";
		StringBuffer orgCd=new StringBuffer(str);
		char returns=orgCd.charAt(0);		
		while(returns==del){
			orgCd=orgCd.deleteCharAt(0);
			if(orgCd.length()==0)break;
			returns=orgCd.charAt(0);
		}
		return orgCd.toString();
	}
	//���ڿ��� ����(int)�� ��ȯ
	public static int parseInt(String str)throws Exception{
		if(str==null)return 0;
		if(str.trim().equals(""))return 0;
		return Integer.parseInt(str);
	}
	//���ڿ��� ����(double)�� ��ȯ
	public static double parseDouble(String str)throws Exception{
		if(str==null)return 0;
		if(str.trim().equals(""))return 0;
		return Double.parseDouble(str);
	}	
	//���ڿ��� null�̰ų� ������ ��� ����Ʈ 0���ڷ� ��ȯ
	public static String fixNullNumber(String str)throws Exception{
		if(str==null)return "0";
		if(str.trim().equals(""))return "0";
		return str.trim();
	}
	

	//����� ����, �����ڿ��� ���� �߼�
	public static void sendResponseMail(String from,String sender,String to,String receiver,String subject,String content,String host)throws Exception{
		
		
//		Session session = Session.getInstance(System.getProperties(), null);
//		
//		MimeMessage message = new MimeMessage(session);
		
		Properties properties = new Properties();
		  properties.put("mail.transport.protocol", "smtp");
		  properties.put("mail.smtp.host", "inbound.hanjingroup.net");
		  properties.put("mail.smtp.port", "25");
		  
		  Session mailSession = Session.getInstance(properties);
		  Message message = new MimeMessage(mailSession);
		try {
			message.setFrom(new InternetAddress(from, sender, "EUC-KR"));
		} catch(java.io.UnsupportedEncodingException e) {
			message.setFrom(new InternetAddress(sender+"<"+from+">"));
		}
		try {
			message.addRecipient(Message.RecipientType.TO, 
			new InternetAddress(to, receiver, "EUC-KR"));
		} catch(java.io.UnsupportedEncodingException e) {
			message.setFrom(new InternetAddress(receiver+"<"+to+">"));
		}

		//message.addHeader("content-type", "text/html; charset=utf-8");
		message.addHeader("content-type", "text/html");
		message.addHeader("charset", "EUC-KR");
		//message.addHeader("Content-Transfer-Encoding", "8bit");

		//message.setSubject(subject, "EUC-KR");
		message.setSubject(subject);
		message.setSentDate(new java.util.Date());
		//message.setText(content);
		message.setContent(content,"text/html; charset=euc-kr");
		
		Transport.send(message);
		
//		Transport transport = session.getTransport("smtp");
//		transport.connect(host, "", "");
//		
//		transport.sendMessage(message, message.getAllRecipients());
//		transport.close();
	}
	
	//÷������ �߰��� ���� �߼�
public static void sendResponseMailAddFiles(String from,String sender,String to,String receiver,String subject,String content,String host,UploadFilesDTO[] files)throws Exception{
		
		//Session session = Session.getInstance(System.getProperties(), null);
		
		Multipart mp = new MimeMultipart();
//		MimeMessage message = new MimeMessage(session);
		
		Properties properties = new Properties();
	  properties.put("mail.transport.protocol", "smtp");
	  properties.put("mail.smtp.host", "inbound.hanjingroup.net");
	  properties.put("mail.smtp.port", "25");
	  
	  Session mailSession = Session.getInstance(properties);
	  Message message = new MimeMessage(mailSession);
		
		try {
			message.setFrom(new InternetAddress(from, sender, "EUC-KR"));
		} catch(java.io.UnsupportedEncodingException e) {
			message.setFrom(new InternetAddress(sender+"<"+from+">"));
		}
		try {
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to, receiver, "EUC-KR"));
		} catch(java.io.UnsupportedEncodingException e) {
			message.setFrom(new InternetAddress(receiver+"<"+to+">"));
		}
		
		message.setSubject(subject);

//		message.setSubject(subject, "EUC-KR");

		MimeBodyPart mbp1 = new MimeBodyPart();
		mbp1.setContent(content, "text/html; charset=euc-kr");
        mp.addBodyPart(mbp1);
        
        // ����÷��
        if(files !=null){
        	
        	String flname ="";
        	for (int i = 0; i < files.length; i++) {
        		
        		flname = "F:/upload/mail/" +files[i].fileSystemName;
        		MimeBodyPart mbp2 = new MimeBodyPart();

                FileDataSource fds = new FileDataSource(flname);
                mbp2.setDataHandler(new DataHandler(fds));
                mbp2.setFileName(iso8859(files[i].fileOriginName));

                mp.addBodyPart(mbp2);
        	}
        }
       
        message.setContent(mp);
		message.setSentDate(new java.util.Date());

//		Transport transport = session.getTransport("smtp");
		//transport.connect(host, "", "");
		//Transport.send(message);
		
		Transport.send(message);
		
//		transport.sendMessage(message, message.getAllRecipients());
//		transport.close();
	

	}
// ���� �߼�(���� ���� ���� ����)
	public static void sendResponseMailAddFiles(String from,String sender,String to,String receiver, String bcc, String bccName, String subject,String content,String host,UploadFilesDTO[] files)throws Exception{
		
		//Session session = Session.getInstance(System.getProperties(), null);
		
		Multipart mp = new MimeMultipart();
	//	MimeMessage message = new MimeMessage(session);
		
		Properties properties = new Properties();
	  properties.put("mail.transport.protocol", "smtp");
	  properties.put("mail.smtp.host", "smtp.hanjingroup.net");
	  properties.put("mail.smtp.port", "25");
	  
	  Session mailSession = Session.getInstance(properties);
	  Message message = new MimeMessage(mailSession);
		
		try {
			message.setFrom(new InternetAddress(from, sender, "EUC-KR"));
		} catch(java.io.UnsupportedEncodingException e) {
			message.setFrom(new InternetAddress(sender+"<"+from+">"));
		}
		
		//���� ������/������ �и�
		String[] toAddr = to.split(";");
		String[] toReceiver = receiver.split(";");
		String[] toCCAddr = bcc.split(";");
		String[] toCCReceiver = bccName.split(";");
		
		//������(�������� ����)
		try {
			for(int i=0;i<toAddr.length;i++){
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddr[i], toReceiver[i], "EUC-KR"));
//				System.out.println("������===>"+toAddr[i]+"<"+toReceiver[i]+">");
			}
		} catch(java.io.UnsupportedEncodingException e) {
			message.setFrom(new InternetAddress(receiver+"<"+to+">"));
		}
		
		//�����ڰ� null �� �ƴѰ��
//		System.out.println("�����ڰ� null ===>"+bcc);
	    if(!bcc.equals("")){
	    	//������(�������� ����)
			try {
				for(int i=0;i<toCCAddr.length;i++){
					message.addRecipient(Message.RecipientType.CC, new InternetAddress(toCCAddr[i], toCCReceiver[i], "EUC-KR"));
//					System.out.println("������===>"+toCCAddr[i]+"<"+toCCReceiver[i]+">");
				}
			} catch(java.io.UnsupportedEncodingException e) {
				message.setFrom(new InternetAddress(bccName+"<"+bcc+">"));
			}
	    }
		
		message.setSubject(subject);
	
	//	message.setSubject(subject, "EUC-KR");
	
		MimeBodyPart mbp1 = new MimeBodyPart();
		mbp1.setContent(content, "text/html; charset=euc-kr");
	    mp.addBodyPart(mbp1);
	    
	    // ����÷��
	    if(files !=null){
	    	
	    	String flname ="";
	    	for (int i = 0; i < files.length; i++) {
	    		
	    		flname = "F:/upload/mail/" +files[i].fileSystemName;
	    		MimeBodyPart mbp2 = new MimeBodyPart();
	
	            FileDataSource fds = new FileDataSource(flname);
	            mbp2.setDataHandler(new DataHandler(fds));
	            mbp2.setFileName(iso8859(files[i].fileOriginName));
	
	            mp.addBodyPart(mbp2);
	    	}
	    }
	   
	    message.setContent(mp);
		message.setSentDate(new java.util.Date());
	
	//	Transport transport = session.getTransport("smtp");
		//transport.connect(host, "", "");
		//Transport.send(message);
		
		Transport.send(message);
		
	//	transport.sendMessage(message, message.getAllRecipients());
	//	transport.close();
	
	
	}
	
	 public static String iso8859(String strStr) throws java.io.UnsupportedEncodingException
     {
             if (strStr == null)
             {
                     return  null;
             }
             else
             {
                     return new String(strStr.getBytes("KSC5601"), "8859_1");
             }
     }

    /*	
	public static void sendResponseMail(String from,String sender,String to,String receiver,String subject,String content,String host)throws Exception{
		
		
		Session session = Session.getInstance(System.getProperties(), null);
		
		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(from, sender, "EUC-KR"));
		} catch(java.io.UnsupportedEncodingException e) {
			message.setFrom(new InternetAddress(sender+"<"+from+">"));
		}
		try {
			message.addRecipient(Message.RecipientType.TO, 
			new InternetAddress(to, receiver, "EUC-KR"));
		} catch(java.io.UnsupportedEncodingException e) {
			message.setFrom(new InternetAddress(receiver+"<"+to+">"));
		}

		
		message.setSubject(subject, "EUC-KR");
		message.setSentDate(new java.util.Date());
		message.setText(content);
		
		Transport transport = session.getTransport("smtp");
		transport.connect(host, "", "");
		
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
	}
	*/
    //�ݿø��� ó���ϴ� �޼���
	public static String round(String resource,int idx){
		String returns=resource;
		int initIdx   =resource.indexOf('.');
		if(initIdx==-1){resource+=".0";}
		try{			
			boolean ver=true;
			String plu="";
			if(idx<0){
				ver=false;
				idx=java.lang.Math.abs(idx);
				for(int i=0;i<idx;i++){plu+="0";}			
			}else if(idx==0){
				return (resource.substring(0,resource.indexOf('.')));
			}
			String maSrc =      resource.substring(0,resource.indexOf('.'));
			String miSrc =      resource.substring(resource.indexOf('.')+1);
			if(ver){
				if(miSrc.length()<idx)return returns;

			}else{
				if(maSrc.length()-1<idx)return resource;
			}
			int value =0;
			int target=0; 
			value =     Integer.parseInt((ver)?
										 (
										  (idx==1)?miSrc.substring(0,1):
												   miSrc.substring(idx-1,idx)
										  )
										 :
										  maSrc.substring(maSrc.length()-idx,maSrc.length()-idx+1));

			target=     Integer.parseInt((ver)?
										 (
										  (idx==1)?maSrc:
												   miSrc.substring(0,idx-1)
										  )
										 :
										  maSrc.substring(0,maSrc.length()-idx)); 

			if(value>=5)target+=1;
			returns= (ver)?(
						   (idx==1)?target+"":
									maSrc+"."+target
						  )
						  :target+plu;

		}catch(Exception e){e.printStackTrace();}
        return returns;
	}
	/**
	 * fieldName�� ������ �����Ѵ�.
	 *
	 * @return Object[].
	 * @param	objArr	    Object[]    ���Ĵ��
	 * @param	fieldName	String		���ı����׸�
	 * @param	isDeSending	boolean	    desending = true,asending = false
	 */
	public static Object[] sort(Object[] objArr,String fieldName,boolean isDeSending)throws Exception{
		if(objArr==null||objArr.length==0)
			throw new Exception("������ �ڷᰡ �����ϴ�.");
		Class c     = objArr[0].getClass(); 
		Field field = c.getField(fieldName);  
        if(field==null)throw new Exception("������ �Ӽ��� �����ϴ�.");
		String fieldType =field.getType().getName();	
		Object temp  ="";
		boolean isSort=false;
		if(isDeSending){
			for(int i=0;i<objArr.length-1;i++){		   
			   for(int j=i+1;j<objArr.length;j++){
				   if ( fieldType.equals("java.lang.String")) {
						if( (field.get(objArr[j])+"").compareTo(field.get(objArr[i])+"")>0 )
							isSort=true;
				   }else if ( fieldType.equals("int")) {
						if( field.getInt(objArr[j])> field.getInt(objArr[i]) )
							isSort=true;
				   }else if ( fieldType.equals("double")) {
						if( field.getDouble(objArr[j])> field.getDouble(objArr[i]) )
							isSort=true;
				   }else if ( fieldType.equals("long")) {
						if( field.getLong(objArr[j])> field.getLong(objArr[i]) )
							isSort=true;				
				   }else if ( fieldType.equals("float")) {
						if( field.getFloat(objArr[j])> field.getFloat(objArr[i]) )
							isSort=true;				
				   }
				   if(isSort){
						temp=objArr[i];
						objArr[i]=objArr[j];
						objArr[j]=temp;
						isSort=false;
				   }			   

			   }
			}
		}else{
			for(int i=0;i<objArr.length-1;i++){		   
			   for(int j=i+1;j<objArr.length;j++){
				   if ( fieldType.equals("java.lang.String")) {
						if( (field.get(objArr[j])+"").compareTo(field.get(objArr[i])+"")<0 )
							isSort=true;
				   }else if ( fieldType.equals("int")) {
						if( field.getInt(objArr[j])< field.getInt(objArr[i]) )
							isSort=true;
				   }else if ( fieldType.equals("double")) {
						if( field.getDouble(objArr[j])< field.getDouble(objArr[i]) )
							isSort=true;
				   }else if ( fieldType.equals("long")) {
						if( field.getLong(objArr[j])< field.getLong(objArr[i]) )
							isSort=true;				
				   }else if ( fieldType.equals("float")) {
						if( field.getFloat(objArr[j])< field.getFloat(objArr[i]) )
							isSort=true;				
				   }
				   if(isSort){
						temp=objArr[i];
						objArr[i]=objArr[j];
						objArr[j]=temp;
						isSort=false;
				   }			   

			   }
			}
		}


		return objArr;
	}

	//���� ���� �̻� ���� �ڸ��� + ���ӹ���
    public static String truncStr(String inputData, int maxLength ){
    	//inputData ����ڿ�
    	//maxLength ���ڿ� ����
    	//inStr ���ӹ��� (..., ~)
    	if (inputData.length()> maxLength ){    		
    	    inputData =  inputData.substring(0,maxLength) + " ..";	
    	}
    	
    	return inputData;
    	
    }
    //���� ���� �̻� ���� �ڸ��� + ���ӹ���
    public static String truncStr(String inputData, int maxLength ,String inStr){
    	//inputData ����ڿ�
    	//maxLength ���ڿ� ����
    	//inStr ���ӹ��� (..., ~)
    	if (inputData.length()> maxLength ){    		
    		inputData =  inputData.substring(0,maxLength) + inStr;	
    	}
    	
    	return inputData;
    	
    }
    //byte�� ����Ͽ� ���ڿ� �ڸ���(�ѱ�ó��)
    public static String truncStrByte(String str, int sLoc, int eLoc,String inStr){
    	byte[] bystStr;
    	String rltStr = str;
    	String CHKrltStr = str;
    	try
    	{
    		bystStr =str.getBytes();
    		int bytelen =bystStr.length;
    		int len = str.length();
    		int chklen =0;
    		if(bytelen > eLoc){
    			rltStr =new String (bystStr, sLoc, eLoc);
    			int rltlen =rltStr.length()-1;

    			//���������ڰ� �ѱ����� ���� Ȯ��
    			for(int i=0;i<rltlen;i++){
    				if(rltStr.charAt(i)<127){
    					chklen++; 
    				}else{
    					chklen+=2;
    				}
    				//System.out.println(rltStr+":"+i+":"+rltStr.charAt(i)+": chklen ===>" + chklen + "==>"+ (eLoc-1) );
    			}
    			
    			if(chklen>=eLoc-1){
    				rltStr =new String (bystStr, sLoc, eLoc-1);
    			}else{
    				rltStr =new String (bystStr, sLoc, eLoc);
    			}
    			rltStr=rltStr+inStr;
    			
    		}
    	}catch(Exception e){
    		return rltStr;
    	}
    	return rltStr;
    }    
    //Treeview �����
//    public static String makeTree(ComPopupDTO[] dtos, int i){
//    	
//    	String treeString ="";
//    	
//		for (int j=1;j<=dtos[i].level ;j++) {
//			if (dtos[i].level!=j & dtos[i].level>=1 ) {
//				treeString= treeString + "<img src='../images/treeImage/0.gif'>";
//			}else if (dtos[i].level ==j  & dtos[i].level>1 & i+1<dtos.length){
//				if(dtos[i+1].level>dtos[i].level) {
//					treeString= treeString + "<img src='../images/treeImage/1.gif'>";
//				}else{
//					treeString= treeString + "<img src='../images/treeImage/1_.gif'>";
//				}
//			}else{
//				if(dtos[i].level==1){ //�ֻ��� Level						
//					treeString= treeString + "<img src='../images/treeImage/0_.gif'>";																	
//				}else{							
//					treeString= treeString + "<img src='../images/treeImage/1_.gif'>";																	
//				}
//			}
//		} 
//    	
//		return treeString;
//    }
//    public static String makeTreeMo(ComPopupDTO[] dtos, int i){
//    	
//    	String treeString ="";
//    	
//		for (int j=1;j<=dtos[i].level ;j++) {
//			if (dtos[i].level!=j & dtos[i].level>=1 ) {
//				treeString= treeString + "&nbsp;&nbsp;&nbsp;";
//			}else if (dtos[i].level ==j  & dtos[i].level>1 & i+1<dtos.length){
//				if(dtos[i+1].level>dtos[i].level) {
//					treeString= treeString + "<img src='../images/treeImage/1.gif'>";
//				}else{
//					treeString= treeString + "<img src='../images/treeImage/1_.gif'>";
//				}
//			}else{
//				if(dtos[i].level==1){ //�ֻ��� Level						
//					treeString= treeString + "<img src='../images/treeImage/0_.gif'>";																	
//				}else{							
//					treeString= treeString + "<img src='../images/treeImage/1_.gif'>";																	
//				}
//			}
//		} 
//    	
//		return treeString;
//    }
    //Treeview �����
    public static String makeSetTree(DataSet set, int i){
    	
    	String treeString ="";
    	
		for (int j=1;j<=set.getInt("LEVEL",i) ;j++) {
			if (set.getInt("LEVEL",i)!=j & set.getInt("LEVEL",i)>=1 ) {
				treeString= treeString + "<img src='../images/treeImage/0.gif'>";
			}else if (set.getInt("LEVEL",i) ==j  & set.getInt("LEVEL",i)>1 & i+1<set.getLength("LEVEL")){
				if(set.getInt("LEVEL",i+1)>set.getInt("LEVEL",i)) {
					treeString= treeString + "<img src='../images/treeImage/1.gif'>";
				}else{
					treeString= treeString + "<img src='../images/treeImage/1_.gif'>";
				}
			}else{
				if(set.getInt("LEVEL",i)==1){ //�ֻ��� Level						
					treeString= treeString + "<img src='../images/treeImage/0_.gif'>";																	
				}else{							
					treeString= treeString + "<img src='../images/treeImage/1_.gif'>";																	
				}
			}
		} 
    	
		return treeString;
    }    
    //��¥ �������� �ٲٱ�
    public static String toDtFormat(String inputData, String dv){
    	
    	String returnStr="";
    	String yyyy ="";
    	String mm ="";
    	String dd ="";
    	
    	if(inputData.length()>=4)  yyyy =inputData.substring(0,4);
    	if(inputData.length()>=6)  mm =inputData.substring(4,6); 
    	if(inputData.length()>=8)  dd =inputData.substring(6,8); 

    	//yyyy-mm-dd ����
    	if(inputData.length()<=4)  returnStr = yyyy; 
    	if(inputData.length()> 4)  returnStr = yyyy + dv + mm;
    	if(inputData.length()> 6)  returnStr = yyyy + dv + mm+ dv + dd;
    	
    	return returnStr;
    }
    
    //Ư������ ����
    public static String transXStr(String sql){
    	    	
    	sql = Utility.replace(sql,  "&amp;"  ,"&");
		sql = Utility.replace(sql,  "&quot;" ,"\"");
		sql = Utility.replace(sql,  "&lt;"   ,"<");
		sql = Utility.replace(sql,  "&gt;"   ,">");
    	
    	return sql;
    }
    
    //Query ���� ���� ����
    public static SQLMapping setSQLMapping(String xml,String Id) throws Exception{
    	String dir = (new Configuration()).get("com.wms.fw.sql.dir");
		HashMap hm=SQLXmlDAO.loadRequestMappings(dir+"\\"+xml);
		SQLMapping sm=(SQLMapping)hm.get(Id); 

    	return sm;
    }    
    
    //Data Commit ����ó��
	public static boolean DataCommit(LinkedList query) throws Exception{
        Connection con = null;
		Statement stmt = null;
        boolean returns= false;
        
        try{
			con = DataBaseUtil.getConnection();
            con.setAutoCommit(false);
            stmt=con.createStatement();        	
			
			LinkedList tmpList = query;
            String[] sqlList = (String[])tmpList.toArray(new String[0]);
            
			for(int i=0;i<sqlList.length;i++){
				stmt.addBatch(sqlList[i]);
				System.out.println(sqlList[i]);
			}			
			
			stmt.executeBatch();	        	
			con.commit();
			
			returns=true;

        }catch(Exception e){
                Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
        }
        finally{
			if(stmt != null){try{stmt.close();}catch(SQLException e){Logger.err.println(com.wms.fw.Utility.getStackTrace(e));}}
			if(con != null){try{con.close();}catch(SQLException e){Logger.err.println(com.wms.fw.Utility.getStackTrace(e));}}
        }           
        return returns; 

	}
	 
	//5.1	ũ�ν�����Ʈ ��ũ��Ʈ ���� �Լ�
		public static String clearXSSMinimum(String value) {
			if (value == null || value.trim().equals("")) {
				return "";
			}
			
			String returnValue = value;

			returnValue = returnValue.replaceAll("&", "&amp;");
			returnValue = returnValue.replaceAll("<", "&lt;");
			returnValue = returnValue.replaceAll(">", "&gt;");
			returnValue = returnValue.replaceAll("\"", "&#34;");
			returnValue = returnValue.replaceAll("\'", "&#39;");
			return returnValue;
		}

		// 5.2	��� ����(Path Manipulation) ���� �Լ�
		public static String filePathReplaceAll(String value) {
			String returnValue = value;
			if (returnValue == null || returnValue.trim().equals("")) {
				return "";
			}
			returnValue = returnValue.replaceAll("/", "");
			returnValue = returnValue.replaceAll("\\", "");
			returnValue = returnValue.replaceAll("\\.\\.", ""); // ..
			returnValue = returnValue.replaceAll("&", "");

			return returnValue;
		}

		  // 5.3	Header Manipulation ���� �Լ�
	  public static String removeCRLF(String parameter) {
	    return parameter.replaceAll("\r", "").replaceAll("\n", "");
	  }
}