package com.wms.beans;
import com.wms.fw.db.*;
import com.wms.fw.*;
import com.wms.beans.dto.*;
import java.util.Hashtable;
import javax.servlet.*;

public class WMSServletContextListener implements ServletContextListener
{
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("call contextDestroyed");
	}
           
	public void contextInitialized(ServletContextEvent sce){
		try{			
			System.out.println("call contextInitialized");
			CodeFinder cf = new CodeFinder();
			cf.register(DataBaseUtil.getCode());
			ServletContext sc=sce.getServletContext();
			sc.setAttribute("codeFinder",cf);
			Hashtable statusName = DataBaseUtil.getStatusName();
			sc.setAttribute("statusName",statusName);
		}catch(Exception e){
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
		}
	} 
 

}
