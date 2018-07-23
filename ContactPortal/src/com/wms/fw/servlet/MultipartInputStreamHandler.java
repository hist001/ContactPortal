/*************************************************************
*	파 일 명  : MultipartInputStreamHandler.java
*	작성일자  : 2004/09/09
*	작 성 자  : 
*	내    용  : Stream을 통해 파일을 읽어드리는 기능을
*               제공하는 클래스
*************************************************************/
package com.wms.fw.servlet;
import java.io.*;
import java.util.*;
import javax.servlet.*;
/**
 * Stream을 통해 파일을 읽어들일 수 있는 기능을 제공한다.
 */

public class MultipartInputStreamHandler {
	  ServletInputStream in;
	  String boundary;
	  int totalExpected;
	  int totalRead = 0;
	  byte[] buf = new byte[8 * 1024];
	/**
	 * @param in            javax.servlet.ServletInputStream
	 * @param boundary      java.lang.String
	 * @param totalExpected int 
	 */
	public MultipartInputStreamHandler(ServletInputStream in, String boundary, int totalExpected) {
		this.in = in;
		this.boundary = boundary;
		this.totalExpected = totalExpected;
	}
	/**
	 * 
	 * @return java.lang.String
	 */
	public String readLine() throws IOException{
		StringBuffer sbuf = new StringBuffer();
		int result;
		String line;

		do {
		  result = this.readLine(buf, 0, buf.length);  // this.readLine() does +=
		  if (result != -1) {
			sbuf.append(new String(buf, 0, result, "ISO-8859-1"));
		  }
		} while (result == buf.length);  // loop only if the buffer was filled

		if (sbuf.length() == 0) {
		  return null;  // nothing read, must be at the end of stream
		}

		sbuf.setLength(sbuf.length() - 2);  // cut off the trailing \r\n
		return sbuf.toString();
	}
	/**
	 * @return int
	 * @param b byte[]
	 * @param off int
	 * @param len int
	 * @exception java.io.IOException 예외 설명.
	 */
	public int readLine(byte b[], int off, int len) throws IOException {
		if (totalRead >= totalExpected) {
		  return -1;
		}
		else {
		  int result = in.readLine(b, off, len);
		  if (result > 0) {
			totalRead += result;
		  }
		  return result;
		}
	}
}