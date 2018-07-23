package com.wms.fw;

/**
 * @(#) Configuration.java
 */
import java.util.Properties;
import java.io.File;
import java.io.FileInputStream;
import java.net.*;
import java.io.*;

public class Configuration extends GeneralConfiguration
{
	private static long jdf_last_modified = 0;
	private static long pbf_last_modified = 0;

	private static String jdf_file_name = null; 
	static {
		File default_file = new File(System.getProperty("user.home"), "wms_OJT.properties");
		jdf_file_name = System.getProperty("com.wms.fw.config.file", default_file.getAbsolutePath());
	}

	/**
	 *
	 */
	public Configuration() throws ConfigurationException {
		super();
		initialize();
	}

	/**
	 * 
	 */
	protected void initialize() throws ConfigurationException {
		synchronized(lock){	
			try{
				boolean needUpdate = false;

			// Java Development Framework Configuration File
				File jdf_file = new File(jdf_file_name);
				if ( ! jdf_file.canRead() ) 
					throw new ConfigurationException( this.getClass().getName() + " - Can't open lsf configuration file: " + jdf_file_name );
				
				if ( (jdf_last_modified != jdf_file.lastModified() ) || props == null ) {
					needUpdate = true;
				}
				else {
					String pbf_file_name = props.getProperty("com.wms.fw.config.pbf.file");
					if ( pbf_file_name != null ) {
						File pbf_file = new File(pbf_file_name);
						if ( ! pbf_file.canRead() ) 
							throw new ConfigurationException( this.getClass().getName() + " - Can't open pbf configuration file: " + pbf_file_name );
						if ( pbf_last_modified != pbf_file.lastModified() ) {
							needUpdate = true;
						}
					}
				}// end if

				if ( needUpdate ) {
					props = new java.util.Properties();
					FileInputStream jdf_fin = new FileInputStream(jdf_file);
					props.load(new java.io.BufferedInputStream(jdf_fin));
					jdf_fin.close();
					jdf_last_modified = jdf_file.lastModified();

					String pbf_file_name = props.getProperty("com.wms.fw.config.pbf.file");
					if ( pbf_file_name != null ) {
						File pbf_file = new File(pbf_file_name);
						if ( ! pbf_file.canRead() ) 
							throw new ConfigurationException( this.getClass().getName() + " - Can't open pbf configuration file: " + pbf_file_name );
						FileInputStream pbf_fin = new FileInputStream(pbf_file);
						props.load(new java.io.BufferedInputStream(pbf_fin));
						pbf_fin.close();
						pbf_last_modified = pbf_file.lastModified();
					}
					super.lastModified = System.currentTimeMillis();
					
				} // end if
			}
			catch(ConfigurationException e) {
				super.lastModified = 0;
				jdf_last_modified = 0;
				pbf_last_modified = 0;
				throw e;
			}
			catch(Exception e){
				super.lastModified = 0;
				jdf_last_modified = 0;
				pbf_last_modified = 0;
				throw new ConfigurationException( this.getClass().getName() + " - Can't load configuration file: " + e.getMessage());
			}
		} // end of sunchronized(lock);
	}
}