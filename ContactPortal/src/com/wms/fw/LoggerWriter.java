package com.wms.fw;

/**
 * @(#) LoggerWriter.java
 */
import java.io.*;
import com.wms.fw.util.DateUtil;

/**
 * Print formatted representations of objects to a text-file output stream.  This
 * class implements all of the print methods found in PrintStream.  It does not
 * contain methods for writing raw bytes, for which a program should use
 * unencoded byte streams.
 *
 * <p> Unlike the PrintStream class, if automatic flushing is enabled by setting
 * in the configuration file ( org.jsn.jdf.logger.autoflush=true ), it will
 * be done only when one of the println() methods is invoked, rather than
 * whenever a newline character happens to be output.  The println() methods
 * use the platform's own notion of line separator rather than the newline
 * character.
 *
 * <p> Methods in this class never throw I/O exceptions.  The client may
 * inquire as to whether any errors have occurred by invoking checkError().
 *
 */

public abstract class LoggerWriter
{

	/**
	 * The PrintWriter Agent for real file logging.
	 * �� PrintWriter ��ü�� ���� ���Ͽ� ����� ����Ѵ�.
	 * Logger.sys, Logger.err, Logger.warn, Logger.info, Logger.debug ��
	 * ������ Reference�� ���� println() �� ȣ��Ǵ��� ������ �ϳ��� ���Ͽ�
	 * ����� �Ͼ�Ƿ� �������� ���� ����� �ϳ��� PrintWriter�� ����� �� �ۿ� ����.
	 */
	private static PrintWriter writer = null;

	/**
	 * synchronized lock object
	 */
	private final static Object lock = new Object();

	/**
	 *	The name of log file today.
	 */
	private static String today = null;


	/**
	 * The indication of new line character is printed.
	 */
	private static boolean newLined = true;


	/**
	 * Log Mode.
	 * SYS : Java Development Framework�� ���� �α�.
	 *       �����ڴ� �� �κп� ���Ͽ����� ������� �ʾƵ� �ȴ�.
	 *       �׷��� Framework�� �����ϴ� ����� �̷��� ������ �߻��� ��
	 *       �ݵ�� Ȯ���ϰ� ������ ������ ��� �־�� �Ѵ�.
	 */
	public final static int SYS = 0;

	/**
	 * Log Mode.
	 * ERR : ������� ������ �Ͼ�� �ȵ� �ɰ��� ��Ȳ�� �����α�.
	 */
	public final static int ERR = 1;

	/**
	 * Log Mode.
	 * WARN :������� ������ �Ͼ�� �ȵ����� �׸�  �ɰ����� ���� ��Ȳ�� �����α�.
	 *          �׷��� �ݵ�� ���Ŀ� �̷��� ��Ȳ�� ���Ͽ� Ȯ�� �ܰ踦 ���ľ� �� �α�.
	 */
	public final static int WARN = 2;

	/**
	 * Log Mode.
	 * INFO : ������� ������ ����� �Ͼ �� ������, �ʿ信 ���� ���ܾ� �� �α�.
	 */
	public final static int INFO = 3;

	/**
	 * Log Mode.
	 * DEBUG : ���߽ÿ� �����ڰ� ���� ���� ������, Ȥ�� ���� � ������ ������ ��,
	 *         ��� �α� TRACE �� ������ �� �� ���� �� �ִ� ��Ȳ�� �α�.
	 */
	public final static int DEBUG = 4;

	/**
	 * Log Mode.
	 * DBWRAP : DEBUG�� ���������� ��κ��� MIS�� ���ø����̼��� SQL������ �����ϰ� ������
	 *         DB Wrapper�� �̿��� �����ϰ� �Ѵ�. �̶� DBWrapper Ŭ�������� SQL���常 Logging��
	 *         ����� ���� ���� ���� ���̴�.
	 */
	public final static int DBWRAP = 5;



	private int mode;

	/**
	 * Line separator string.  This is the value of the line.separator
	 * property at the moment that the stream was created.
	 */
	private static String lineSeparator = System.getProperty("line.separator");


	/**
	 * Create a new LoggerWriter.
	 * @param mode The one of LoggerWriter.ERR, LoggerWriter.ERR,
	 *                         LoggerWriter.WARN, LoggerWriter.INFO, LoggerWriter.DEBUG
	 */
	public LoggerWriter(int mode) {
		this.mode = mode;
		synchronized ( lock ) {
			checkDate();
		}
	}

	/**
	 * Check log file name.
	 */
	private static void checkDate() {
		String day = DateUtil.getShortDateString();
		if ( day.equals(today) ) return;

		try {
			if ( writer != null ) {
				try { writer.close();} catch(Exception e){}
				writer = null;
			}
			today = day;
			String logname = today + ".log" ;
			Config conf = new Configuration();
			String directory = conf.get("com.wms.fw.logger.dir");
			java.io.File file = new java.io.File(directory, logname);
			String filename = file.getAbsolutePath();
			java.io.FileWriter fw =  new java.io.FileWriter(filename, true);// APPEND MODE
			writer = new PrintWriter(
				new java.io.BufferedWriter(fw),
				conf.getBoolean("com.wms.fw.logger.autoflush")  // AUTO Flush
			);
		}
		catch(Exception e){
			writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)), true);
			writer.println("Can't open log file : " + e.getMessage());
			writer.println("Log will be printed into System.out");
		}
	}

	public void finalize() {
		try {
			if ( writer != null ) writer.flush();
		}
		catch(Exception e){}
	}

	/** Flush the stream. */
	public void flush() {
		if ( isPrintMode() ) writer.flush();
	}
	/**
	 * Get the pre-defined Object's information.
	 * You must be implement this method.
	 * @return java.lang.String
	 * @param o java.lang.Object
	 */
	protected abstract String getPrefixInfo(Object o);

	/**
	 * @return boolean
	 */
	private boolean isPrintMode() {
		boolean isPrintable = true;
		try {
			Config conf = new Configuration();
			switch ( mode ) {
				case SYS:
					isPrintable = conf.getBoolean("com.wms.fw.logger.sys.trace");
					break;
				case ERR:
					isPrintable = conf.getBoolean("com.wms.fw.logger.err.trace");
					break;
				case WARN:
					isPrintable = conf.getBoolean("com.wms.fw.logger.warn.trace");
					break;
				case INFO:
					isPrintable = conf.getBoolean("com.wms.fw.logger.info.trace");
					break;
				case DEBUG:
					isPrintable = conf.getBoolean("com.wms.fw.logger.debug.trace");
					break;
				case DBWRAP:
					isPrintable = conf.getBoolean("com.wms.fw.logger.dbwrap.trace");
					break;
			}
		}
		catch(Exception e) {
		}
		return isPrintable;
	}

	/** Print an array of chracters. */
	public void print(char x[]) {
		if ( ! isPrintMode() ) return;
	    synchronized (lock) {
			if ( newLined ) printTime();
			writer.print(x);
			newLined = false;
	    }
	}

	/** Print a character. */
	public void print(char x) {
		if ( ! isPrintMode() ) return;
	    synchronized (lock) {
			if ( newLined ) printTime();
			writer.print(x);
			newLined = false;
	    }
	}

	/** Print a double. */
	public void print(double x) {
		if ( ! isPrintMode() ) return;
	    synchronized (lock) {
			if ( newLined ) printTime();
			writer.print(x);
			newLined = false;
	    }

	}

	/** Print a float. */
	public void print(float x) {
		if ( ! isPrintMode() ) return;
	    synchronized (lock) {
			if ( newLined ) printTime();
			writer.print(x);
			newLined = false;
	    }
	}

	/** Print an integer. */
	public void print(int x) {
		if ( ! isPrintMode() ) return;
	    synchronized (lock) {
			if ( newLined ) printTime();
			writer.print(x);
			newLined = false;
	    }
	}

	/** Print a long. */
	public void print(long x) {
		if ( ! isPrintMode() ) return;
	    synchronized (lock) {
			if ( newLined ) printTime();
			writer.print(x);
			newLined = false;
	    }
	}

	/** Print an object. */
	public void print(Object x) {
		if ( ! isPrintMode() ) return;
	    synchronized (lock) {
			if ( newLined ) printTime();
			writer.print(x);
			newLined = false;
	    }
	}

	/**
	 * Print predefined information.
	 * Print the predefined information of Object p,
	 * and then the string of message Object p.
	 */
	public void print(Object p, Object x) {
		if ( ! isPrintMode() ) return;
	    synchronized (lock) {
			if ( newLined ) printTime();
			writer.print(getPrefixInfo(p));
			writer.print(x);
			newLined = false;
	    }
	}

	/** Print a String. */
	public void print(String x) {
		if ( ! isPrintMode() ) return;
	    synchronized (lock) {
			if ( newLined ) printTime();
			writer.print(x);
			newLined = false;
	    }
	}

	/** Print a boolean. */
	public void print(boolean x) {
		if ( ! isPrintMode() ) return;
	    synchronized (lock) {
			if ( newLined ) printTime();
			writer.print(x);
			newLined = false;
	    }
	}

	/** Finish the line. */
	public void println() {
		if ( ! isPrintMode() ) return;
	    synchronized (lock) {
			if ( newLined ) printTime();
			writer.println();
			newLined = true;
	    }
	}

	/** Print an array of characters, and then finish the line. */
	public void println(char x[]) {
		if ( ! isPrintMode() ) return;
	    synchronized (lock) {
			if ( newLined ) printTime();
			writer.println(x);
			newLined = true;
	    }
	}

	/** Print a character, and then finish the line. */
	public void println(char x) {
		if ( ! isPrintMode() ) return;
	    synchronized (lock) {
			if ( newLined ) printTime();
			writer.println(x);
			newLined = true;
	    }
	}

	/** Print a double, and then finish the line. */
	public void println(double x) {
		if ( ! isPrintMode() ) return;
	    synchronized (lock) {
			if ( newLined ) printTime();
			writer.println(x);
			newLined = true;
	    }
	}

	/** Print a float, and then finish the line. */
	public void println(float x) {
		if ( ! isPrintMode() ) return;
	    synchronized (lock) {
			if ( newLined ) printTime();
			writer.println(x);
			newLined = true;
	    }
	}

	/** Print an integer, and then finish the line. */
	public void println(int x) {
		if ( ! isPrintMode() ) return;
	    synchronized (lock) {
			if ( newLined ) printTime();
			writer.println(x);
			newLined = true;
	    }
	}

	/** Print a long, and then finish the line. */
	public void println(long x) {
		if ( ! isPrintMode() ) return;
	    synchronized (lock) {
			if ( newLined ) printTime();
			writer.println(x);
			newLined = true;
	    }
	}

	/** Print an Object, and then finish the line. */
	public void println(Object x) {
		if ( ! isPrintMode() ) return;
	    synchronized (lock) {
			if ( newLined ) printTime();
			writer.println(x);
			newLined = true;
	    }
	}

	/**
	 * Print predefined information.
	 * Print the predefined information of Object p,
	 * and then the string of message Object p.
	 */
	public void println(Object p, Object x) {
		if ( ! isPrintMode() ) return;
		synchronized ( lock ) {
			if ( newLined ) printTime();
			writer.print(getPrefixInfo(p));
			writer.println(x);
			newLined = true;
		}
	}

	/** Print a String, and then finish the line. */
	public void println(String x) {
		if ( ! isPrintMode() ) return;
	    synchronized (lock) {
			if ( newLined ) printTime();
			writer.println(x);
			newLined = true;
	    }
	}

	/** Print a boolean, and then finish the line. */
	public void println(boolean x) {
		if ( ! isPrintMode() ) return;
	    synchronized (lock) {
			if ( newLined ) printTime();
			writer.println(x);
			newLined = true;
	    }
	}

	/**
	 * print current time
	 */
	private void printTime() {
		checkDate();
		char serverty = ' ';
		switch ( mode ) {
			case SYS:
				serverty = 'S';
				break;
			case ERR:
				serverty = 'E';
				break;
			case WARN:
				serverty = 'W';
				break;
			case INFO:
				serverty = 'I';
				break;
			case DEBUG:
				serverty = 'D';
				break;
			case DBWRAP:
				serverty = 'Q';
				break;
		}

		writer.write(serverty + DateUtil.getShortTimeString()+' ') ;
	}

	/**
	 * Write an array of characters.  This method cannot be inherited from the
	 * Writer class because it must suppress I/O exceptions.
	 */
	public void write(char buf[]) {
	write(buf, 0, buf.length);
	}

	/** Write a portion of an array of characters. */
	public void write(char buf[], int off, int len) {
		if ( ! isPrintMode() ) return;
	    synchronized (lock) {
			if ( newLined ) printTime();
			writer.write(buf, off, len);
			newLined = false;
	    }
	}

	/*
	 * Exception-catching, synchronized output operations,
	 * which also implement the write() methods of Writer
	 */

	/** Write a single character. */
	public void write(int c) {
		if ( ! isPrintMode() ) return;
	    synchronized (lock) {
			if ( newLined ) printTime();
			writer.write(c);
			newLined = false;
	    }
	}

	/**
	 * Write a string.  This method cannot be inherited from the Writer class
	 * because it must suppress I/O exceptions.
	 */
	public void write(String s) {
	write(s, 0, s.length());
	}

	/** Write a portion of a string. */
	public void write(String s, int off, int len) {
		if ( ! isPrintMode() ) return;
	    synchronized (lock) {
			if ( newLined ) printTime();
			writer.write(s, off, len);
			newLined = false;
	    }
	}
}