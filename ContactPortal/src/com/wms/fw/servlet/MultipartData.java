/*************************************************************
*	파 일 명  : MultipartData.java
*	작성일자  : 2004/09/06
*	작 성 자  : 
*	내    용  : Upload된 파일을 추출 , 저장하는 클래스 
*************************************************************/
package com.wms.fw.servlet;
 
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.wms.fw.CharConversion;
import com.wms.fw.servlet.Box;

/**
 * Client 에서 Server 로 파일 Upload 및 Server에서 Client 로 파일 Download 하는 클래스
 */
public class MultipartData {
	
	private static final int DEFAULT_MAX_POST_SIZE = 1024 * 1024;	//default 파일uploading 가능 사이즈	
	private long totalSize = 0;	//upload 전체 사이즈
	private long bytesRead = 0;
	private boolean isEnd  =false;

	private ServletRequest req;
	private File dir;				//서버측 파일저장 경로
	private int maxSize;
	private boolean uniqueFileKey;	//접두어 사용유무
	private String prefixName;
    private boolean isFullName; 	//확장자 사용유무 default true 사용
	private Hashtable parameters;
	private Hashtable files;
	private LinkedList fileSystemNames;
	private LinkedList fileOriginNames;
	//확장자 사용유무 초기화
   	public MultipartData(){
		totalSize=0;
		bytesRead=0;
		isFullName=true;
   	}
	/**
	 * 
	 * @param request  javax.servlet.ServletRequest  
	 * @param saveDirectory   java.lang.String 저장 디렉토리명
	 */
	public void setMultipartData(ServletRequest request, String saveDirectory) throws IOException {
		setMultipartData(request, saveDirectory, DEFAULT_MAX_POST_SIZE);
	}

	/**
	 * 
	 * @param request  javax.servlet.ServletRequest  
	 * @param saveDirectory   java.lang.String 저장 디렉토리명
	 * @param maxPostSize     int 최대 파일의 크기
	 */
	public void setMultipartData(ServletRequest request, String saveDirectory,
		int maxPostSize) throws IOException {

	    parameters = new Hashtable();
	    files = new Hashtable();
        fileSystemNames= new LinkedList();
        fileOriginNames= new LinkedList();
		
		if ( request == null )
			throw new IllegalArgumentException("request cannot be null");
		if ( saveDirectory == null )
		    throw new IllegalArgumentException("saveDirectory cannot be null");
		if ( maxPostSize <= 0 )
		    throw new IllegalArgumentException("maxPostSize must be positive");

		req = request;
		dir = new File(saveDirectory);
		maxSize = maxPostSize;

		if ( !dir.isDirectory() )
			throw new IllegalArgumentException("Not a directory : " + saveDirectory );
			
		if ( !dir.canWrite() )
			throw new IllegalArgumentException("Not writable : " + saveDirectory );
	
		readRequest();
		isEnd=true;
		//totalSize=0;
		//bytesRead=0;		
	}

	/**
	 * 
	 * @return java.lang.String
	 * @param line java.lang.String
	 */
	private String extractBoundary(String line) {
		int index = line.indexOf("boundary=");
		if (index == -1) {
			  return null;
		}
		String boundary = line.substring(index + 9);  // 9 for "boundary="

		// The real boundary is always preceeded by an extra "--"
		boundary = "--" + boundary;

		return boundary;
	}

	/**
	 * 
	 * @return java.lang.String
	 * @param line java.lang.String
	 * @exception java.io.IOException 예외 설명.
	 */
	private String extractContentType(String line) throws IOException {
		String contentType = null;

		// Convert the line to a lowercase string
		String origline = line;
		line = origline.toLowerCase();

		// Get the content type, if any
		if (line.startsWith("content-type")) {
			int start = line.indexOf(" ");
			if (start == -1) {
				throw new IOException("Content type corrupt: " + origline);
		  	}
		  	contentType = line.substring(start + 1);
		}else if (line.length() != 0) {  // no content type, so should be empty
		  throw new IOException("Malformed line after disposition: " + origline);
		}

		return contentType;
	}

	/**
	 * 
	 * @return java.lang.String
	 * @param line java.lang.String
	 * @exception java.io.IOException 예외 설명.
	 */
	private String[] extractDispositionInfo(String line) throws IOException {
		String[] retval = new String[3];

		// Convert the line to a lowercase string without the ending \r\n
		// Keep the original line for error messages and for variable names.
		String origline = line;
		line = origline.toLowerCase();

		// Get the content disposition, should be "form-data"
		int start = line.indexOf("content-disposition: ");
		int end = line.indexOf(";");
		if (start == -1 || end == -1) {
		  throw new IOException("Content disposition corrupt: " + origline);
		}
		String disposition = line.substring(start + 21, end);
		if (!disposition.equals("form-data")) {
		  throw new IOException("Invalid content disposition: " + disposition);
		}

		// Get the field name
		start = line.indexOf("name=\"", end);  // start at last semicolon
		end = line.indexOf("\"", start + 7);   // skip name=\"
		if (start == -1 || end == -1) {
		  throw new IOException("Content disposition corrupt: " + origline);
		}
		String name = origline.substring(start + 6, end);

		// Get the filename, if given
		String filename = null;
		start = line.indexOf("filename=\"", end + 2);  // start after name
		end = line.indexOf("\"", start + 10);          // skip filename=\"
		if (start != -1 && end != -1) {                // note the !=
		  	filename = origline.substring(start + 10, end);
		  	// The filename may contain a full path.  Cut to just the filename.
		  	int slash =
				Math.max(filename.lastIndexOf('/'), filename.lastIndexOf('\\'));
		  	if (slash > -1) {
				filename = filename.substring(slash + 1);  // past last slash
		  	}
		  	if (filename.equals("")) filename = "unknown"; // sanity check
		}

		// Return a String array: disposition, name, filename
		retval[0] = disposition;
		retval[1] = name;
		retval[2] = filename;
		return retval;
	}

	/**
	 * 
	 * @return com.wms.fw.util.Box
	 */
	public Box getBox()throws Exception {
        if(parameters==null){throw new Exception("MultipartData Class의 초기화가 필요합니다.");}
		Box box = new Box("multipartBox");
     
		Enumeration e = parameters.keys();
		while(e.hasMoreElements()) {
			String key = (String)e.nextElement();
			String[] values = (String[])((LinkedList)parameters.get(key)).toArray(new String[1]);
			box.put(key, values);
		}
		if(fileSystemNames.size()>0){
			box.put("fileSystemName",(String[])fileSystemNames.toArray(new String[1]));
			box.put("fileOriginName",(String[])fileOriginNames.toArray(new String[1]));
		}
		return box;
	}

	/**
	 * 
	 * @return java.io.File
	 * @param name java.lang.String
	 */
	public File getFile(String name) {
		try {
			UploadedFile file = (UploadedFile)files.get(name);
			return file.getFile();
		} catch (Exception e) {
			return null;
		}		
	}

	/**
	 * 
	 * @return java.lang.String
	 * @param name java.lang.String
	 */
	public String getFileContentType(String name) {
		try {
			UploadedFile file = (UploadedFile)files.get(name);
			return file.getContentType();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 
	 * @return java.util.Enumeration
	 */
	public Enumeration getFileNames() {
		return files.keys();
	}

	/**
	 * 
	 * @return java.lang.String
	 * @param name java.lang.String
	 */
	public String getFileSystemName(String name) {
		String filename=null;
		try {
			
			UploadedFile file = (UploadedFile)files.get(name);
			
			return file.getFileSystemName();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 
	 * @return java.lang.String
	 * @param name java.lang.String
	 */
	public String getFileOriginName(String name) {
		try {
			UploadedFile file = (UploadedFile)files.get(name);			
			return file.getFileOriginName();
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * 
	 * @return boolean
	 */
	public static boolean isMultipart(ServletRequest req) {
		String type = req.getContentType();

		if ( type == null || !type.toLowerCase().startsWith("multipart/form-data")) {
				return false;
		}
		return true;
	}

	/**
	 * 
	 * @param in MultipartInputStreamHandler
	 * @param boundary java.lang.String
	 * @param filename java.lang.String
	 * @exception java.io.IOException 예외 설명.
	 */
	protected void readAndSaveFile(MultipartInputStreamHandler in, String boundary, String filename) throws IOException {
		if(filename.equals("unknown"))return;
		filename=(!isFullName)?filename.substring(0,filename.lastIndexOf(".")):filename;		
        filename=(uniqueFileKey)?prefixName+"!"+filename:filename;
		File f = new File(dir + File.separator + filename);

		FileOutputStream fos = new FileOutputStream(f);
		BufferedOutputStream out = new BufferedOutputStream(fos, 8 * 1024); // 8K

		byte[] bbuf = new byte[100 * 1024];  // 100K
		int result;
		String line;

		// ServletInputStream.readLine() has the annoying habit of 
		// adding a \r\n to the end of the last line.  
		// Since we want a byte-for-byte transfer, we have to cut those chars.
		boolean rnflag = false;

		while ((result = in.readLine(bbuf, 0, bbuf.length)) != -1) {
		  // Check for boundary
		  
		  if (result > 2 && bbuf[0] == '-' && bbuf[1] == '-') { // quick pre-check
			line = new String(bbuf, 0, result, "ISO-8859-1");
			if (line.startsWith(boundary)) break;
		  }
		  // Are we supposed to write \r\n for the last iteration?
		  if (rnflag) {
			out.write('\r'); out.write('\n');
			rnflag = false;
		  }
		  // Write the buffer, postpone any ending \r\n
		  if (result >= 2 && 
			  bbuf[result - 2] == '\r' && 
			  bbuf[result - 1] == '\n') {
			out.write(bbuf, 0, result - 2);  // skip the last 2 chars
			rnflag = true;  // make a note to write them on the next iteration
		  }
		  else {
			out.write(bbuf, 0, result);
		  }
		  bytesRead+=result;
		  //System.out.println("진행률:"+totalSize+"/"+bytesRead);
		}

		out.flush();
		out.close();
		fos.close();
	}

	/**
	 * 
	 * @return boolean
	 * @param in MultipartInputStreamHandler
	 * @param boundary java.lang.String
	 * @exception java.io.IOException 예외 설명.
	 */
	protected boolean readNextPart(MultipartInputStreamHandler in, String boundary) throws IOException {

		String line = in.readLine();
       
		if ( line == null) {
			return true;
		}
		if ( line.trim().equals("")) {
			in.readLine();
			return false;
		}

		String[] dispInfo = extractDispositionInfo(line);
		String disposition = dispInfo[0];
		String name = dispInfo[1];
		String filename = CharConversion.E2K(dispInfo[2]);

		line = in.readLine();

		if ( line == null ) {
			return true;
		}

		String contentType = extractContentType(line);

		if ( contentType != null ) {
			line = in.readLine();

			if ( line == null || line.length() > 0 ) {
				throw new IOException("Malformed line after content type : " + line );
			}
		}
		else {
			contentType = "application/octet-stream";
		}

		if ( filename == null ) {
			String value = readParameter(in, boundary);
			LinkedList values=(LinkedList)parameters.get(name);
			if(values==null){
				values=new LinkedList();
				parameters.put(name, values);
			}
			values.add(value);
		}
		else {
			readAndSaveFile(in, boundary,filename );

			if ( filename.equals("unknown") ) {
				//files.put(name, new UploadedFile(null, null, null));
			}
			else {		
				//files.put(name, new UploadedFile(dir.toString(), CharConversion.E2K(filename), contentType));
				String fileOriginname=(!isFullName)?filename.substring(0,filename.lastIndexOf(".")):filename;		
				fileOriginNames.add(fileOriginname);
				filename=(uniqueFileKey)?prefixName+"!"+fileOriginname:fileOriginname;
				fileSystemNames.add(filename);
				files.put(name, new UploadedFile(dir.toString(), filename,fileOriginname, contentType));

			}
		}  
		
		return false;
	}

	/**
	 * 
	 * @return java.lang.String
	 * @param in MultipartInputStreamHandler
	 * @param boundary java.lang.String
	 * @exception java.io.IOException 예외 설명.
	 */
	protected String readParameter(MultipartInputStreamHandler in, String boundary) throws IOException {
		StringBuffer sbuf = new StringBuffer();
		String line;
		String result = null;

		while ( ( line = in.readLine() ) != null ) {
			if ( line.startsWith(boundary) ) break;

			sbuf.append(line + "\r\n");
		}

		if ( sbuf.length() == 0 ) {
			return null;
		}
		bytesRead+=sbuf.length();
	 	sbuf.setLength(sbuf.length() - 2);
		//System.out.println("진행률:"+totalSize+"/"+bytesRead);
		return CharConversion.E2K(sbuf.toString());
	 }    

	/**
	 * @exception java.io.IOException 예외 설명.
	 */
	protected void readRequest() throws IOException {

		String type = req.getContentType();

		if ( type == null || 
			!type.toLowerCase().startsWith("multipart/form-data")) {
				throw new IOException("Posted content type isn't multipart/form-data");
		}

		int length = req.getContentLength();
		totalSize = length;

		if ( length > maxSize ) {
			throw new IOException("Posted content length of " + length +
				" exceeds limit of " + maxSize );
		}

		String boundary = extractBoundary(type);

		if (boundary == null ) {
			throw new IOException("Separation boundary was not specified");
		}
		
		MultipartInputStreamHandler in =
			new MultipartInputStreamHandler(req.getInputStream(), boundary, length);

		String line = in.readLine();



		if ( line == null ) {
			throw new IOException("Corrupt form data : premature ending");
		}
		
		if ( !line.startsWith(boundary) ) {
			throw new IOException("Corrupt form data : no leading boundary");
		}
		

		boolean done = false;

		while ( !done ) {
			done = readNextPart(in, boundary);
		}
	}
	
	//접두어 사용유무 getter
	public boolean getUniqueFileKey(){
		return uniqueFileKey;
	}
	
	//접두어 사용유무 setter
	public void setUniqueFileKey(boolean uniqueFileKey){
		this.uniqueFileKey=uniqueFileKey;
	}
	
	//접두어 setter
	public void setPrefixName(String prefixName){
		this.prefixName=prefixName;
		uniqueFileKey=true;
	}
	
	//접두어 getter
	public String getPrefixName(){
		return prefixName;
	}
	
	//파일 확장자 사용유무 setter
	public void setFullName(boolean flag){
		isFullName=flag;
	}
	
	//파일 확장자 사용유무 getter
	public boolean getFullName(){
		return isFullName;
	}
	
	//파일 download를 처리하는 method
	/**	 
	 * @param dir java.lang.String	server측 파일저장경로
	 * @param fileSystemName java.lang.String	server에 저장된 파일명
	 * @param fileOriginName java.lang.String	Client에서 올린 파일명(
	 * @param response javax.servlet.ServletResponse  	 
	 */
	public static void downLoad(String dir, String fileSystemName, String fileOriginName, HttpServletResponse response){
		InputStream in = null;
		OutputStream os = null;
		 
		try{
			File file = new File(dir+"//"+fileSystemName );
		 	in = new FileInputStream(file);
		 	response.reset() ;
		 	response.setContentType("application/smnet");
		 	response.setHeader ("Content-Disposition", "attachment; filename=\"" + new String(fileOriginName.getBytes(),"ISO8859_1")+"\"" );
		 	response.setHeader ("Content-Length", ""+file.length() );
		 	os = response.getOutputStream();
		 	//byte b[] = new byte[(int)file.length()];//주의 시스템down의 가능성있음.
						
            byte[] b = new byte[4096]; //buffer size 4K.
            int count = 0;
            while ((count = in.read(b)) != -1) {
                os.write(b,0,count);
                os.flush();
            }
			
			/*
		 	int leng = 0;
		 	while( (leng = in.read(b)) > 0 ){
		 		os.write(b,0,leng);
		  	}
			*/
		} catch(Exception e) {
					e.printStackTrace();
		}finally{
		 	if(in !=null){
		 		try{
		 			in.close();
		 		}catch(Exception e){
					e.printStackTrace();
		 		}
		 	}
		 	if(os !=null){
		 		try{
		 			os.close();
		 		}catch(Exception e){
					e.printStackTrace();
		 		}
			}
		}
	}

	public long getTotalSize(){
		return totalSize;
	}
	public long getBytesRead(){
		return bytesRead;
	}
	public boolean isEnd(){
		return isEnd;
	}	

}