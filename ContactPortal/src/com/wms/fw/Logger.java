package com.wms.fw;

/**
 * @(#) Logger.java
 */

public final class Logger
{

	/**
	 * The "critical system level error" log stream. This stream is already
	 * open and ready to accept output data.
	 * <p>
	 * Typically this stream corresponds to display output or another
	 * output destination specified by the host environment or user. By
	 * convention, this output stream is used to display error messages
	 * or other information that should come to the immediate attention
	 * of a user even if the principal output stream, the value of the
	 * variable <code>out</code>, has been redirected to a file or other
	 * destination that is typically not continuously monitored.
	 *
	 */
	public final static LoggerWriter sys = getLoggerWriter(LoggerWriter.SYS);


	/**
	 * The "critical business error" log stream. This stream is already
	 * open and ready to accept output data.
	 * <p>
	 * Typically this stream corresponds to display output or another
	 * output destination specified by the host environment or user. By
	 * convention, this output stream is used to display error messages
	 * or other information that should come to the immediate attention
	 * of a user even if the principal output stream, the value of the
	 * variable <code>out</code>, has been redirected to a file or other
	 * destination that is typically not continuously monitored.
	 *
	 */
	public final static LoggerWriter err = getLoggerWriter(LoggerWriter.ERR);


	/**
	 * The "business warnning" log stream. This stream is already
	 * open and ready to accept output data. Typically this stream
	 * corresponds to display output or another output destination
	 * specified by the host environment or user.
	 * <p>
	 * For simple stand-alone Java applications, a typical way to write
	 * a line of output data is:
	 * <ul><code>Logger.out.println(data)</code></ul>
	 * <p>
	 * See the <code>println</code> methods in class <code>LoggerWriter</code>.
	 *
	 * @see     com.logicsoft.fw.LoggerWriter#println()
	 * @see     com.logicsoft.fw.LoggerWriter#println(boolean)
	 * @see     com.logicsoft.fw.LoggerWriter#println(char)
	 * @see     com.logicsoft.fw.LoggerWriter#println(char[])
	 * @see     com.logicsoft.fw.LoggerWriter#println(double)
	 * @see     com.logicsoft.fw.LoggerWriter#println(float)
	 * @see     com.logicsoft.fw.LoggerWriter#println(int)
	 * @see     com.logicsoft.fw.LoggerWriter#println(long)
	 * @see     com.logicsoft.fw.LoggerWriter#println(java.lang.Object)
	 * @see     com.logicsoft.fw.LoggerWriter#println(java.lang.String)
	 */
	public final static LoggerWriter warn = getLoggerWriter(LoggerWriter.WARN);

	/**
	 * The "business infomation" log stream. This stream is already
	 * open and ready to accept output data. Typically this stream
	 * corresponds to display output or another output destination
	 * specified by the host environment or user.
	 * <p>
	 * For simple stand-alone Java applications, a typical way to write
	 * a line of output data is:
	 * <ul><code>Logger.out.println(data)</code></ul>
	 * <p>
	 * See the <code>println</code> methods in class <code>LoggerWriter</code>.
	 *
	 * @see     com.logicsoft.fw.LoggerWriter#println()
	 * @see     com.logicsoft.fw.LoggerWriter#println(boolean)
	 * @see     com.logicsoft.fw.LoggerWriter#println(char)
	 * @see     com.logicsoft.fw.LoggerWriter#println(char[])
	 * @see     com.logicsoft.fw.LoggerWriter#println(double)
	 * @see     com.logicsoft.fw.LoggerWriter#println(float)
	 * @see     com.logicsoft.fw.LoggerWriter#println(int)
	 * @see     com.logicsoft.fw.LoggerWriter#println(long)
	 * @see     com.logicsoft.fw.LoggerWriter#println(java.lang.Object)
	 * @see     com.logicsoft.fw.LoggerWriter#println(java.lang.String)
	 */
	public final static LoggerWriter info = getLoggerWriter(LoggerWriter.INFO);


	/**
	 * The "debug & tracing" log stream. This stream is already
	 * open and ready to accept output data.
	 *
	 */
	public final static LoggerWriter debug = getLoggerWriter(LoggerWriter.DEBUG);


	/**
	 * The special log stream for DBWrapper. This stream is already
	 * open and ready to accept output data.
	 *
	 */
	public final static LoggerWriter dbwrap = getLoggerWriter(LoggerWriter.DBWRAP);


	/**
	 * Don't let anyone instantiate this class
	 */
	private Logger() {	}

	private static LoggerWriter getLoggerWriter(int serverty) {
		LoggerWriter  logger = null;
		try {
			Config conf = new Configuration();
			Class loggerClass = Class.forName(conf.get("com.wms.fw.logger.driver"));
			Class[] argsTypes = new Class[1];
			argsTypes[0] = int.class;
			java.lang.reflect.Constructor cons = loggerClass.getConstructor(argsTypes);
			Object[] args = new Object[1];
			args[0] = new Integer(serverty);
			logger = (LoggerWriter)cons.newInstance(args);
		}
		catch(Exception e) {
			logger = new DefaultLoggerWriter(serverty);
			if ( serverty == LoggerWriter.SYS ) {
				logger.println("LoggerWriter initialization fail : " + e.getMessage());
				logger.println("LoggerWriter is reinitialized with DefaultLoggerWriter");
				logger.flush();
			}
		}
		return logger;
	}
}