package com.wms.fw;

/**
 * @(#) CharConversion.java
 */
import java.io.UnsupportedEncodingException;

public final class CharConversion {

	/**
	 * Don't let anyone instantiate this class
	 */
	private CharConversion() {
	}

	/**
	 * 8859_1 --> KSC5601.
	 */
	public static String E2K( String english )
	{
		String korean = null;

		if (english == null ) return null;
		//if (english == null ) return "";

		try {
			korean = new String(english.getBytes("8859_1"), "KSC5601");
		}
		catch( UnsupportedEncodingException e ){
			korean = new String(english);
		}
		return korean;
	}

	/**
	 * KSC5601 --> 8859_1.
	 */
	public static String K2E( String korean )
	{
		String english = null;

		if (korean == null ) return null;
		//if (korean == null ) return "";

		english = new String(korean);
		try {
			english = new String(korean.getBytes("KSC5601"), "8859_1");
		}
		catch( UnsupportedEncodingException e ){
			english = new String(korean);
		}
		return english;
	}
}