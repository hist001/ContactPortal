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
	 * Object[] 의 복제. Object의 Array 를 복제(clone)하여
	 * 새로운 Instance를 만들어 줍니다.
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
	 * Object 의 복제. 일반적으로 <code>java.lang.Object.clone()</code> 함수를
	 * 를 사용하여 Object를 복제하면 Object내에 있는 Primitive type을 제외한 Object
	 * field들은 복제가 되는 것이 아니라 같은 Object의 reference를
	 * 갖게 된다.<br>
	 * 그러나 이 Method를 사용하면 각 field의 동일한 Object를 새로 복제(clone)하여
	 * 준다.
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
	 * Vector 의 복제. 일반적으로 Vector Object를 clone()을 하면
	 * Vector내의 Element Object는 새로 생성되는 것이 아니라
	 * 동일한 Object에 대한 reference만 새로 생성되기 때문에 같은 Element Object를
	 * reference하는 Vector를 생성하게 된다. 그러나 이 method를 사용하면
	 * Vector내의 모든 Element Object도 새로 복제하여 준다.
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
	 * Entity Class의 null string field 초기화.
	 * <p>
	 * Entity class 내에 있는 java.lang.String형의 field는 DB의 Column과
	 * 밀접한 연관이 있는 경우가 많다. 이러한 Entity Field가 특히 GUI의 특정
	 * TextFiled에 표현되어야 하는 경우도 많다. 만약 그 String Filed가 null일
	 * 경우 일일이 검사를 한다는 것은 참으로 답답한 일이 아닐 수 없다.
	 * <p>
	 * 이 method는 여하한의 Object 내에 있는 모든 java.lang.String형의 field 변수 중
	 * null 값으로 된 field를 길이가 0 인 blank string("")으로 초기화 시켜준다.
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
	 * @param java.lang.Object Object내의 public java.lang.String 형의
	 *        member variable에만 영향을 준다.
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
	 * Entity Class의 재귀적인 null string field 초기화.
	 * <p>
	 * fixNull() 과 유사한 기능을 하는데, java.lang.String field 뿐만 아니라
	 * Member 변수 중 Array, Object 가 있으면 재귀적으로 ?i아 가서 String형을
	 * blank string("")으로 만들어 준다.<br>
	 * 정상적인 String인 경우 trim()을 시켜준다.<br>
	 * 만약 Array나, Vector가 null일 경우 Instance화는 하지 않는다.
	 *
	 * <p>
	 * 재귀적으로 추적되는 만큼, 부모와 자식간에 서로 양방향 reference를 갖고 있으면
	 * 절대 안된다. Stack Overflow를 내며 JVM을 내릴 것이다.
	 *
	 *
	 * @param java.lang.Object Object내의 public String 형뿐만 아니라, Object[], Vector 등과
	 *        같은 public Object형 Member Variable에 영향을 준다.
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
	 * Entity Class의 null string field 초기화 &amp; trim().
	 * <p>
	 * Entity class 내에 있는 java.lang.String형의 field는 DB의 Column과
	 * 밀접한 연관이 있는 경우가 많다. 이러한 Entity Field가 특히 GUI의 특정
	 * TextFiled에 표현되어야 하는 경우도 많다. 만약 그 String Filed가 null일
	 * 경우 일일이 검사를 한다는 것은 참으로 답답한 일이 아닐 수 없다.
	 * <p>
	 * 이 method는 여하한의 Object 내에 있는 모든 java.lang.String형의 field 변수 중
	 * null 값으로 된 field를 길이가 0 인 blank string("")으로 초기화 시켜준다.
	 * 만약 null이 아닌 정상적인 String이 대입되어 있으면 강제적으로 trim()를
	 * 시켜준다.
	 * <p>
	 * 이 때 trim() 함수는 java.lang.String 의 trim()을 사용하지 않았다.
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
	 * @param java.lang.Object Object내의 public java.lang.String 형의
	 *        member variable에만 영향을 준다.
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
	 * Entity Class의 재귀적인 null string field 초기화  &amp; trim().
	 * <p>
	 * fixNull() 과 유사한 기능을 하는데, java.lang.String field 뿐만 아니라
	 * Member 변수 중 Array, Object 가 있으면 재귀적으로 ?i아 가서 String형을
	 * blank string("")으로 만들어 준다.<br>
	 * 정상적인 String인 경우 trim()을 시켜준다.<br>
	 * 만약 Array나, Vector가 null일 경우 Instance화는 하지 않는다.
	 *
	 * <p>
	 * 재귀적으로 추적되는 만큼, 부모와 자식간에 서로 양방향 reference를 갖고 있으면
	 * 절대 안된다. Stack Overflow를 내며 JVM을 내릴 것이다.
	 *
	 *
	 * @param java.lang.Object Object내의 public String 형뿐만 아니라, Object[], Vector 등과
	 *        같은 public Object형 Member Variable에 영향을 준다.
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
	 * java.lang.String의 trim()과 차이점은 일반적인 white space만 짜르는 것이
	 * 아니라 위에서와 같은 특수한 blank도 짤라 준다.<br>
	 * 이 소스는 IBM HOST와 데이타를 주고 받을 때 유용하게 사용했었다.
	 * 일반적으로 많이 쓰이지는 않을 것이다.
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

		while ((st < len) && ((val[st] <= ' ') || (val[st] == '　') ) )   st++;
		while ((st < len) && ((val[len - 1] <= ' ') || (val[len-1] == '　')))  len--;

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
	 * 문자열에서 특정 문자열을 치환한다.
	 * 문자열 배열의 차례대로 치환하되
	 * 더 이상 배열 값이 없으면 space 1칸으로 치환한다.
	 *
	 * @return the translated string.
	 * @param	src		String		변환할 문자열
	 * @param	oldStr	String		치환 대상 문자열
	 * @param	newStr	String[]	치환될 문자열 배열
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
	//구분자로 분리되어있는 값들을 배열로 정렬 재배치.
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
    *	숫자를 금액형식으로 리턴 (000,000,000)
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
	//우측부터 삭제가능
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
	//좌측부터 삭제가능
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
	//문자열을 숫자(int)로 전환
	public static int parseInt(String str)throws Exception{
		if(str==null)return 0;
		if(str.trim().equals(""))return 0;
		return Integer.parseInt(str);
	}
	//문자열을 숫자(double)로 전환
	public static double parseDouble(String str)throws Exception{
		if(str==null)return 0;
		if(str.trim().equals(""))return 0;
		return Double.parseDouble(str);
	}	
	//문자열에 null이거나 공백인 경우 디폴트 0문자로 전환
	public static String fixNullNumber(String str)throws Exception{
		if(str==null)return "0";
		if(str.trim().equals(""))return "0";
		return str.trim();
	}
	

	//설명요 이후, 보고자에게 메일 발송
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
	
	//첨부파일 추가되 메일 발송
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
        
        // 파일첨부
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
// 메일 발송(다중 수신 지정 포함)
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
		
		//다중 수신자/참조자 분리
		String[] toAddr = to.split(";");
		String[] toReceiver = receiver.split(";");
		String[] toCCAddr = bcc.split(";");
		String[] toCCReceiver = bccName.split(";");
		
		//수신자(다중지정 가능)
		try {
			for(int i=0;i<toAddr.length;i++){
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddr[i], toReceiver[i], "EUC-KR"));
//				System.out.println("수신자===>"+toAddr[i]+"<"+toReceiver[i]+">");
			}
		} catch(java.io.UnsupportedEncodingException e) {
			message.setFrom(new InternetAddress(receiver+"<"+to+">"));
		}
		
		//참조자가 null 이 아닌경우
//		System.out.println("참조자가 null ===>"+bcc);
	    if(!bcc.equals("")){
	    	//참조자(다중지정 가능)
			try {
				for(int i=0;i<toCCAddr.length;i++){
					message.addRecipient(Message.RecipientType.CC, new InternetAddress(toCCAddr[i], toCCReceiver[i], "EUC-KR"));
//					System.out.println("참조자===>"+toCCAddr[i]+"<"+toCCReceiver[i]+">");
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
	    
	    // 파일첨부
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
    //반올림을 처리하는 메서드
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
	 * fieldName의 값으로 정렬한다.
	 *
	 * @return Object[].
	 * @param	objArr	    Object[]    정렬대상
	 * @param	fieldName	String		정렬기준항목
	 * @param	isDeSending	boolean	    desending = true,asending = false
	 */
	public static Object[] sort(Object[] objArr,String fieldName,boolean isDeSending)throws Exception{
		if(objArr==null||objArr.length==0)
			throw new Exception("정렬할 자료가 없습니다.");
		Class c     = objArr[0].getClass(); 
		Field field = c.getField(fieldName);  
        if(field==null)throw new Exception("정렬할 속성이 없습니다.");
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

	//기준 길이 이상 문자 자르기 + 줄임문자
    public static String truncStr(String inputData, int maxLength ){
    	//inputData 대상문자열
    	//maxLength 문자열 길이
    	//inStr 줄임문자 (..., ~)
    	if (inputData.length()> maxLength ){    		
    	    inputData =  inputData.substring(0,maxLength) + " ..";	
    	}
    	
    	return inputData;
    	
    }
    //기준 길이 이상 문자 자르기 + 줄임문자
    public static String truncStr(String inputData, int maxLength ,String inStr){
    	//inputData 대상문자열
    	//maxLength 문자열 길이
    	//inStr 줄임문자 (..., ~)
    	if (inputData.length()> maxLength ){    		
    		inputData =  inputData.substring(0,maxLength) + inStr;	
    	}
    	
    	return inputData;
    	
    }
    //byte로 계산하여 문자열 자르기(한글처리)
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

    			//마지막문자가 한글인지 여부 확인
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
    //Treeview 만들기
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
//				if(dtos[i].level==1){ //최상위 Level						
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
//				if(dtos[i].level==1){ //최상위 Level						
//					treeString= treeString + "<img src='../images/treeImage/0_.gif'>";																	
//				}else{							
//					treeString= treeString + "<img src='../images/treeImage/1_.gif'>";																	
//				}
//			}
//		} 
//    	
//		return treeString;
//    }
    //Treeview 만들기
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
				if(set.getInt("LEVEL",i)==1){ //최상위 Level						
					treeString= treeString + "<img src='../images/treeImage/0_.gif'>";																	
				}else{							
					treeString= treeString + "<img src='../images/treeImage/1_.gif'>";																	
				}
			}
		} 
    	
		return treeString;
    }    
    //날짜 형식으로 바꾸기
    public static String toDtFormat(String inputData, String dv){
    	
    	String returnStr="";
    	String yyyy ="";
    	String mm ="";
    	String dd ="";
    	
    	if(inputData.length()>=4)  yyyy =inputData.substring(0,4);
    	if(inputData.length()>=6)  mm =inputData.substring(4,6); 
    	if(inputData.length()>=8)  dd =inputData.substring(6,8); 

    	//yyyy-mm-dd 형식
    	if(inputData.length()<=4)  returnStr = yyyy; 
    	if(inputData.length()> 4)  returnStr = yyyy + dv + mm;
    	if(inputData.length()> 6)  returnStr = yyyy + dv + mm+ dv + dd;
    	
    	return returnStr;
    }
    
    //특수문자 변경
    public static String transXStr(String sql){
    	    	
    	sql = Utility.replace(sql,  "&amp;"  ,"&");
		sql = Utility.replace(sql,  "&quot;" ,"\"");
		sql = Utility.replace(sql,  "&lt;"   ,"<");
		sql = Utility.replace(sql,  "&gt;"   ,">");
    	
    	return sql;
    }
    
    //Query 기초 정보 생성
    public static SQLMapping setSQLMapping(String xml,String Id) throws Exception{
    	String dir = (new Configuration()).get("com.wms.fw.sql.dir");
		HashMap hm=SQLXmlDAO.loadRequestMappings(dir+"\\"+xml);
		SQLMapping sm=(SQLMapping)hm.get(Id); 

    	return sm;
    }    
    
    //Data Commit 공통처리
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
	 
	//5.1	크로스사이트 스크립트 필터 함수
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

		// 5.2	경로 조작(Path Manipulation) 필터 함수
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

		  // 5.3	Header Manipulation 필터 함수
	  public static String removeCRLF(String parameter) {
	    return parameter.replaceAll("\r", "").replaceAll("\n", "");
	  }
}