/*************************************************************
*	파 일 명  : UploadedFile.java
*	작성일자  : 2004/09/09
*	작 성 자  : 
*	내    용  : Upload된 파일의 정보를 담은 클래스 
*************************************************************/
package com.wms.fw.servlet;


import java.io.*;
import java.util.*;
import javax.servlet.*;
 /**
 * Upload된 파일의 정보를 제공해 준다.
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