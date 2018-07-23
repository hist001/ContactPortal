package com.wms.fw.jsp;

import com.wms.fw.Utility;
/**
 * @(#) HtmlUtility.java
 */

public final class HtmlUtility {

	/** You can't call the constructor */
	private HtmlUtility() {}

	/**
	 * Translates special strings into special HTML tag format.
	 *
	 * <xmp>
	 *
	 * & --> &
	 * < --> <
	 * > --> >
	 * " --> "
	 * ' --> '
	 *-----------------------------------------------------------------
	 * <option type=radio  name=r value="xxxxxxxx"> yyyyyyy
	 * <input  type=hidden name=h value="xxxxxxxx">
	 * <input  type=text   name=t value="xxxxxxxx">
	 * <textarea name=msg rows=20 cols=53>xxxxxxx</textarea>
	 *-
	 * 위와 같은 HTML 소스를 생성할 때, xxxxxxx 부분의 문자열 중에서
	 * 아래에 있는 몇가지 특별한 문자들을 변환하여야 합니다.
	 * 만약 JSP 라면 미리 변환하여 HTML 전체 TAG를 만들거나, 혹은
	 * 아래처럼 사용하세요.
	 *-
	 * <option type=radio  name=r value="<%= HtmlUtility.translate(s) %>"> yyyyyyy
	 * <input  type=hidden name=n value="<%= HtmlUtility.translate(s) %>">
	 * <input  type=text   name=n value="<%= HtmlUtility.translate(s) %>">
	 * <textarea name=body rows=20 cols=53><%= HtmlUtility.translate(s) %></textarea>
	 *-
	 *-
	 * 또 필요하다면 yyyyyyy 부분도  translate(s)를 할 필요가 있을 겁니다.
	 * 필요할 때 마다 사용하세요.
	 *-
	 *-
	 * </xmp>
	 *
	 * see <a href="http://developer.netscape.com/docs/manuals/htmlguid/tags22.htm#1819476">http://developer.netscape.com/docs/manuals/htmlguid/tags22.htm#1819476</a>
	 *
	 * @return the translated string.
	 * @param	s	java.lang.String
	 */
	public final static String translate(String s) {

		if( s == null )		return null;

		StringBuffer buf = new StringBuffer();
		char[] c = s.toCharArray();
		int len	= c.length;

		for( int i=0 ; i<len ; i++ ) {
			if     ( c[i] == '&' )	buf.append("&amp;");
			else if( c[i] == '<' )	buf.append("&lt;");
			else if( c[i] == '>' )	buf.append("&gt;");
			else if( c[i] == '"' )	buf.append("&quot;");
			else if( c[i] == '\'')	buf.append("&#039;");
			else buf.append(c[i]);
		}
		return buf.toString();
	}


   /**
	 * Convert new line character(\n) into '<br>'
	 * @return	the translated string.
	 * @param	src		String	the string to be changed
	 */
	public static String NL2BR(String src) {
		return translatePostfix(src, "<br>");
	}

	/**
	 * Convert new line character(\n) into '>'
	 * Use in write 'reply'.
	 * @return the translated string.
	 */
	public static String translateReply(String src) {
		return translatePrefix(src, ">");
	}

	/**
	 * Convert new line character(\n) into specified string(prefix).
	 * @return the translated string.
	 * @param	src		String	the source string
	 * @param	prefix	String	the destination string
	 */
	public static String translatePrefix(String src, String prefix) {
		String result = "";
		java.util.StringTokenizer st = new java.util.StringTokenizer(src, "\n");

		while(st.hasMoreTokens())
			result += prefix + st.nextToken();
		return result;
	}

	/**
	 * Convert new line character(\n) into specified string(postfix).
	 * @return the translated string.
	 * @param	src			String		the source string
	 * @param	postfix		String		the destination string
	 */
	public static String translatePostfix(String src, String postfix) {
		String result = "";
		java.util.StringTokenizer st = new java.util.StringTokenizer(src, "\n");

		while(st.hasMoreTokens())
			result += st.nextToken() + postfix;
		return result;
	}

	/**
	 * 문자열을 일정길이 만큼만 보여주고
	 * 그 길이에 초과되는 문자열일 경우 "..."를 덧붙여 보여준다.
	 * @return the translated string.
	 * @param	s				String	변환할 문자열
	 * @param	limitLength		int		문자열의 제한 길이
	 */
	public static String fixLength(String s, int limit) {
		return fixLength(s, limit, "...");
	}

	/**
	 * 문자열을 일정길이 만큼만 보여주고
	 * 그 길이에 초과되는 문자열일 경우 특정문자를 덧붙여 보여준다.
	 * @return the translated string.
	 * @param	s			String		변환할 문자열
	 * @param	limitLength	int			문자열의 제한 길이
	 * @param	postfix		String		덧붙일 문자열
	 */
	public static String fixLength(String s, int limit, String postfix) {
		char[] charArray = s.toCharArray();

		if (limit >= charArray.length)
			return s;
		return new String( charArray, 0, limit ).concat( postfix );
	}

	/**
	 * 문자열을 일정길이 만큼만 보여주고
	 * 그 길이에 초과되는 문자열일 경우 특정문자를 덧붙여 보여준다.
	 *
	 * 단 fixLength와의 차이는 제한길이의 기준이 char가 아니라 byte로
	 * 처리함으로해서 한글문제를 해결할수 있다.
	 *
	 * @return the translated string.
	 * @param	s			String		변환할 문자열
	 * @param	limitByte	int			문자열의 제한 길이(byte)
	 * @param	postfix		String		덧붙일 문자열
	 */
	public static String fixUnicodeLength(String s, int limitByte) {
		return fixUnicodeLength(s, limitByte, "...");
	}

	/**
	 * Retrict String length up to limitByte.
	 * 		cut excess String and append postfix String.
	 *
	 * @return constructed new String
	 * @param	s			String		source string
	 * @param	limitByte	int			limit length in byte
	 * @param	postfix		String		to be appended string
	 *
	 * Desc : 중간에 -1을 해주는 것은. 21바이트짜리 한글 스트링을.
	 *		20으로 짤라서 String을 생성하면, 끝글자만 짤리는것이 아니라.
	 *		스트링자체가 생성이 되지 않기 때문. 그러므로 길이가 0 이면
	 *		-1 을 해준 뒤에 스트링을 생성하는 것이다.
	 */
	public static String fixUnicodeLength(String s, int limitByte, String postfix) {

		// Cut empty string
		s = s.trim();

		byte[] outputBytes = s.getBytes();
		String output = null;

		if (outputBytes.length <= limitByte) {
			output = s;
		}else {
			output = new String( outputBytes, 0, limitByte );

			if(output.length() == 0)
				output = new String( outputBytes, 0, limitByte-1 );
			//output.concat( postfix );
			//Minkoo : upper code do not work. I don't know exatly why...
			output += postfix;
		}
		return output;
	}

	/**
	 * Replace specified String with new String.
	 *
	 * @return the translated string.
	 * @param src		String		the source string
	 * @param oldStr	String		the old string to be changed
	 * @param newStr	String		the new string
	 * 2001.01.27	Bug fixed, Minkoo Kim (fire34@hananet.net).
	 */
	public static String replaceStr(String src, String oldStr, String newStr) {
		return Utility.replace( src, oldStr,newStr);
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
	 * 2001.01.27	Bug fixed, Minkoo Kim (fire34@hananet.net).
	 */
	public static String replaceStr(String src, String oldStr, String[] newStr) {
		return Utility.replace( src, oldStr,newStr);	
	}

	public static String replaceEscape(String src)
	{
		if(src==null) return null;
		String src1 = replaceStr(src, "\n", "<br>");
		src1 = replaceStr(src1," ", "&nbsp;");
		//src1 = replaceStr(src1,"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;", "&nbsp;");
		
		return src1;
	}
	/**
	 * Print 'date' after translate '-' into '/'.
	 * ex) "2001-01-01 00:00:00" -> "2001/01/01"
	 * @return the translated string.
	 * @param	date	String		The string to be changed
	 */
   	public static String printDate(String date) {
	    if (date == null) return "";
	    return date.substring(0, 10).replace('-','/');
	}

	/**
	 * Print 'date' after translate '-' into 'seperator' character.
	 * @return the translated string.
	 * @param	date		String		변환할 문자열
	 * @param	seperator	char		delimiter
	 */
   	public static String printDate(String date, char seperator) {
	    if (date == null)	return "";
	    return date.substring(0,10).replace('-', seperator);
	}

	/**
	 * 날짜시간 문자열에서 delimiter가 표기된 형태로 보여준다.
	 * @return the translated string.
	 * @param	date	String		변환할 문자열
	 */
   	public static String printDateTime(String date) {
	    if (date == null) return "";
		return date.substring(0, 16).replace('-', '/');
	}

	/**
	 * Calculate number of page
	 * @return number of page in the list
	 * @param	int		total		Total number of contents
	 * @param	int		listCnt		Number of content in a page
	 */
	public static int getPageCnt(int total, int listCnt) {
		int pageCnt = total / listCnt;
		int remain =  total % listCnt;

		if(remain > 0)		pageCnt++;

		return pageCnt;
	}

}