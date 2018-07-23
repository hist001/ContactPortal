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
	 * ���� ���� HTML �ҽ��� ������ ��, xxxxxxx �κ��� ���ڿ� �߿���
	 * �Ʒ��� �ִ� ��� Ư���� ���ڵ��� ��ȯ�Ͽ��� �մϴ�.
	 * ���� JSP ��� �̸� ��ȯ�Ͽ� HTML ��ü TAG�� ����ų�, Ȥ��
	 * �Ʒ�ó�� ����ϼ���.
	 *-
	 * <option type=radio  name=r value="<%= HtmlUtility.translate(s) %>"> yyyyyyy
	 * <input  type=hidden name=n value="<%= HtmlUtility.translate(s) %>">
	 * <input  type=text   name=n value="<%= HtmlUtility.translate(s) %>">
	 * <textarea name=body rows=20 cols=53><%= HtmlUtility.translate(s) %></textarea>
	 *-
	 *-
	 * �� �ʿ��ϴٸ� yyyyyyy �κе�  translate(s)�� �� �ʿ䰡 ���� �̴ϴ�.
	 * �ʿ��� �� ���� ����ϼ���.
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
	 * ���ڿ��� �������� ��ŭ�� �����ְ�
	 * �� ���̿� �ʰ��Ǵ� ���ڿ��� ��� "..."�� ���ٿ� �����ش�.
	 * @return the translated string.
	 * @param	s				String	��ȯ�� ���ڿ�
	 * @param	limitLength		int		���ڿ��� ���� ����
	 */
	public static String fixLength(String s, int limit) {
		return fixLength(s, limit, "...");
	}

	/**
	 * ���ڿ��� �������� ��ŭ�� �����ְ�
	 * �� ���̿� �ʰ��Ǵ� ���ڿ��� ��� Ư�����ڸ� ���ٿ� �����ش�.
	 * @return the translated string.
	 * @param	s			String		��ȯ�� ���ڿ�
	 * @param	limitLength	int			���ڿ��� ���� ����
	 * @param	postfix		String		������ ���ڿ�
	 */
	public static String fixLength(String s, int limit, String postfix) {
		char[] charArray = s.toCharArray();

		if (limit >= charArray.length)
			return s;
		return new String( charArray, 0, limit ).concat( postfix );
	}

	/**
	 * ���ڿ��� �������� ��ŭ�� �����ְ�
	 * �� ���̿� �ʰ��Ǵ� ���ڿ��� ��� Ư�����ڸ� ���ٿ� �����ش�.
	 *
	 * �� fixLength���� ���̴� ���ѱ����� ������ char�� �ƴ϶� byte��
	 * ó���������ؼ� �ѱ۹����� �ذ��Ҽ� �ִ�.
	 *
	 * @return the translated string.
	 * @param	s			String		��ȯ�� ���ڿ�
	 * @param	limitByte	int			���ڿ��� ���� ����(byte)
	 * @param	postfix		String		������ ���ڿ�
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
	 * Desc : �߰��� -1�� ���ִ� ����. 21����Ʈ¥�� �ѱ� ��Ʈ����.
	 *		20���� ©�� String�� �����ϸ�, �����ڸ� ©���°��� �ƴ϶�.
	 *		��Ʈ����ü�� ������ ���� �ʱ� ����. �׷��Ƿ� ���̰� 0 �̸�
	 *		-1 �� ���� �ڿ� ��Ʈ���� �����ϴ� ���̴�.
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
	 * ���ڿ����� Ư�� ���ڿ��� ġȯ�Ѵ�.
	 * ���ڿ� �迭�� ���ʴ�� ġȯ�ϵ�
	 * �� �̻� �迭 ���� ������ space 1ĭ���� ġȯ�Ѵ�.
	 *
	 * @return the translated string.
	 * @param	src		String		��ȯ�� ���ڿ�
	 * @param	oldStr	String		ġȯ ��� ���ڿ�
	 * @param	newStr	String[]	ġȯ�� ���ڿ� �迭
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
	 * @param	date		String		��ȯ�� ���ڿ�
	 * @param	seperator	char		delimiter
	 */
   	public static String printDate(String date, char seperator) {
	    if (date == null)	return "";
	    return date.substring(0,10).replace('-', seperator);
	}

	/**
	 * ��¥�ð� ���ڿ����� delimiter�� ǥ��� ���·� �����ش�.
	 * @return the translated string.
	 * @param	date	String		��ȯ�� ���ڿ�
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