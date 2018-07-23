package com.wms.fw.ejb;

import javax.naming.*;
import javax.rmi.PortableRemoteObject;
import javax.ejb.*;

import com.wms.fw.*;
import com.wms.fw.ConfigurationException;
/**
 * JNDI���񽺸� �����ϴ� multi server�� ���� Context���� �� EJBHome�� ã�� ���� 
 * interface�� �����Ѵ�.
 * 
 *	@since   JDK1.3
 */
public class JNDIUtility {
	
	private static final String DEFAULT_SERVER   = "default";
	private static final String PROVIDER_URL       = ".server.provider.url";
	private static final String INITIAL_CONTEXT    = ".server.initial.context";
	
	
	/**
	 * default server���� �����Ǵ� JNDI���񽺿� JNDI�̸��� jndi�� EJBHome Object�� ��´�.
	 * 
	 * @param String jndi jndi name 
	 * @param Class clazz class
	 * @return EJBHome
	 */
	public static EJBHome findEJBHome(String jndi,Class clazz) throws NamingException, ConfigurationException{
		return findEJBHome(DEFAULT_SERVER,jndi,clazz);
	}
	/**
	 * JNDI ���񽺸� �����ϴ� server name server���� �Ķ���� jndi�� ���� mapping�� 
	 * EJBHome�� ��´�.
	 * 
	 * @param String server JNDI ���񽺸� �����ϴ� ���� ����
	 * @param String jndi ����� �ϴ� EJBHome�� jndi name.
	 * @param Class clazz class
	 * @return EJBHome
	 */	
	public static EJBHome findEJBHome(String server,String jndi,Class clazz) throws NamingException, ConfigurationException {
		EJBHome home = null;
			
		Object ref = lookup(server,jndi);
			
		if(DEFAULT_SERVER.equals(server))
			home = (EJBHome)ref;
		else 
			home = (EJBHome)PortableRemoteObject.narrow(ref,clazz);
			
		return home;
	}

	/**
	 * jndi�� ���� mapping�� EJBLocalHome�� ��´�.
	 * 
	 * @param String jndi ����� �ϴ� EJBHome�� jndi name.
	 * @param Class clazz class
	 * @return EJBHome
	 */	
	public static EJBLocalHome findEJBLocalHome(String jndi,Class clazz) throws NamingException, ConfigurationException {
		EJBLocalHome home = null;
			
		Object ref = lookup(jndi);
		home = (EJBLocalHome)ref;
		return home;
		
	}	
	/**
	 * default server���� �����Ǵ� JNDI���񽺿� JNDI�̸��� jndi�� Object�� ��´�.
	 * 
	 * @param String jndi jndi name
	 * @return Object 
	 */	
	public static Object lookup(String jndi) throws NamingException, ConfigurationException{
		return lookup(DEFAULT_SERVER,jndi);
	}
	/**
	 * JNDI ���񽺸� �����ϴ� server name server���� �Ķ���� jndi�� ���� mapping�� 
	 * EJBHome�� ��´�.
	 * 
	 * @param String jndi jndi name
	 * @return Object 
	 */		
	public static Object lookup(String server,String jndi) throws NamingException, ConfigurationException{

		Object ref = null;
		Context ctx = null;
		
		try {
			
			if(DEFAULT_SERVER.equals(server))
				ctx = getInitialContext();
			else
				ctx = getInitialContext(server);
				
			ref = ctx.lookup(jndi);
			
		} catch ( NamingException ne ) {
			Logger.sys.println(ne.toString());
			throw ne;
		}	
		
		return ref;
		
	}
	/**
	 * default server�� ���� IntialContext������ ��´�.
	 * 
	 * @return Context
	 */	
	public static Context getInitialContext() throws NamingException {
		return new InitialContext();
	}
	/**
	 * server name server�� ���� IntialContext������ ��´�.
	 * 
	 * @return Context
	 */		
	public static Context getInitialContext(String server) throws NamingException, ConfigurationException {
		
		Context ctx = null;		
		String provider_url = null;
		String context_factory = null;
		java.util.Hashtable env = new java.util.Hashtable();
		
		try {
			
			Config conf = new Configuration();
			provider_url = conf.get(server + PROVIDER_URL);
			context_factory = conf.get( server + INITIAL_CONTEXT);
			
			if(provider_url.trim().length()==0 ||  
			    context_factory.trim().length()==0 ) 
			{
				throw new NamingException("Not found JNDI SVR information in base.properties\nserver name : " + server + "\nprovider url : " + provider_url + "\ncontext factory : " + context_factory);
			}
			
			env.put(Context.INITIAL_CONTEXT_FACTORY,context_factory);
			env.put(Context.PROVIDER_URL,provider_url);				
			
			ctx = new InitialContext(env); 
			
		} catch (NamingException ne) {
			Logger.sys.println(ne.toString());
			throw ne;
		}	
		
		return ctx;
		
	}
}