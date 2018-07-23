package com.wms.fw.ejb;

import java.util.Hashtable;

import javax.ejb.EJBHome;
/**
 * 한번 얻은 EJBHome에 대해서 Client의 EJBHome request에 대한 response time을 
 * 줄이기 위해 EJBHome caching 기능을 지원한다.
 *<br>
 *<pre>
 * example)
 * EJBHomeFactory factory = EJBHomeFactory.getFactory();
 * try {
 *   //default server에서 제공하는 JNDI 서비스하는 JNDI_NAME으로 mapping된 UserEJBHome을 얻는다.
 *   UserEJBHome home = (UserEJBHome)factory.lookupHome(JNDI_NAME,UserEJBHome.class);
 * 	
 *   //
 *   //serverName의 server에서 제공하는 JNDI 서비스하는 JNDI_NAME으로 mapping된 UserEJBHome을 얻는다.
 *   //UserEJBHome home = (UserEJBHome)factory.lookupHome(serverName,JNDI_NAME,UserEJBHome.class);
 *   //
 * 
 *   // getting EJBObject reference
 *   UserEJB remote = home.create();
 * 
 * } catch ( NamingException ne) {
 *   ...
 * }
 *</pre>
 *
 *	@since   JDK1.3
 */
public class EJBHomeFactory {
	private static final String DEFAULT_SERVER = "default";
	private Hashtable factories;
	private static EJBHomeFactory instance;
	
	EJBHomeFactory() {
		factories = new Hashtable();
	}
	private EJBHomeStorage addStorage(String server) {
		EJBHomeStorage storage = null;
		storage = new EJBHomeStorage(server);
		factories.put( server, storage );
		return storage;
	}
	synchronized void clear() {
		factories = new Hashtable();
	}
	/**
	 * EJBHomeFactory object를 얻는다.
	 * 
	 * @return EJBHomeFactory
	 */
	public static synchronized EJBHomeFactory getFactory() {
		if(instance == null)  instance = new EJBHomeFactory();
		return instance;
	}
	/**
	 * JNDI 서비스를 제공하는 default server에서 파라메터 jndi에 의해 mapping된 
	 * EJBHome을 얻는다.
	 * 
	 * @param String jndi 얻고자 하는 EJBHome의 jndi name.
	 * @param Class clazz class
	 */
	public EJBHome lookupHome(String jndi,Class clazz) throws Exception {
		return lookupHome(DEFAULT_SERVER,jndi,clazz);
	}
	
	/**
	 * JNDI 서비스를 제공하는 server name server에서 파라메터 jndi에 의해 mapping된 
	 * EJBHome을 얻는다.
	 * 
	 * @param String server JNDI 서비스를 제공하는 서버 별명
	 * @param String jndi 얻고자 하는 EJBHome의 jndi name.
	 * @param Class clazz class
	 * @return EJBHome
	 */
	
	public EJBHome lookupHome(String server,String jndi,Class clazz) throws Exception {
		
		EJBHomeStorage storage = null;
		EJBHome home = null;
		
		if( factories.containsKey(server) ) {
			 storage = (EJBHomeStorage) factories.get(server);
			 if( storage == null ) {
			 	storage = addStorage(server);
			 }
		} else { 
			storage = addStorage(server);
		}
		
		try {
			home = storage.create(jndi,clazz);
/*
			System.out.println("============== CREATE FACTORY INFOMATION ==============");
			System.out.println("server : " + server);
			System.out.println("storage : " + storage);
			System.out.println("cache count : " + storage.size());
			System.out.println("=========================================================");
*/
		} catch (Exception ne) {
			throw ne;
		}
		
		return home;
	}
}