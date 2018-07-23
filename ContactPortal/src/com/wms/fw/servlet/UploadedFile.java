/*************************************************************
*	�� �� ��  : UploadedFile.java
*	�ۼ�����  : 2004/09/09
*	�� �� ��  : 
*	��    ��  : Upload�� ������ ������ ���� Ŭ���� 
*************************************************************/
package com.wms.fw.servlet;


import java.io.*;
import java.util.*;
import javax.servlet.*;
 /**
 * Upload�� ������ ������ ������ �ش�.
 */
public class UploadedFile {
	private String dir;
	private String filename;
	private String fileOriginname;
	private String type;
	/**
	 * @param dir           java.lang.String
	 * @param filename      java.lang.String
	 * @param fileOriginName      java.lang.String
	 * @param type          java.lang.String
	 */
	public UploadedFile(String dir, String filename,String fileOriginname, String type) {
		this.dir = dir;
		this.filename = filename;
		this.fileOriginname=fileOriginname;
		this.type = type;
	}
	/**
	 * 
	 * @return java.lang.String
	 */
	public String getContentType() {
		return type;
	}
	/**
	 * 
	 * @return java.io.File
	 */
	public File getFile() {
		if (dir == null || filename == null) {
		  return null;
		}
		else {
		  return new File(dir + File.separator + filename);
		}
	}
	/**
	 * 
	 * @return java.lang.String
	 */
	public String getFileSystemName() {
		return filename;
	}
	/**
	 * 
	 * @return java.lang.String
	 */
	public String getFileOriginName() {
		return fileOriginname;
	}
}