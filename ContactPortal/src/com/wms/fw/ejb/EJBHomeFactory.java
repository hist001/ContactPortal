package com.wms.fw.ejb;

import java.util.Hashtable;

import javax.ejb.EJBHome;
/**
 * �ѹ� ���� EJBHome�� ���ؼ� Client�� EJBHome request�� ���� response time�� 
 * ���̱� ���� EJBHome caching ����� �����Ѵ�.
 *<br>
 *<pre>
 * example)
 * EJBHomeFactory factory = EJBHomeFactory.getFactory();
 * try {
 *   //default server���� �����ϴ� JNDI �����ϴ� JNDI_NAME���� mapping�� UserEJBHome�� ��´�.
 *   UserEJBHome home = (UserEJBHome)factory.lookupHome(JNDI_NAME,UserEJBHome.class);
 * 	
 *   //
 *   //serverName�� server���� �����ϴ� JNDI �����ϴ� JNDI_NAME���� mapping�� UserEJBHome�� ��´�.
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
	 * EJBHomeFactory object�� ��´�.
	 * 
	 * @return EJBHomeFactory
	 */
	public static synchronized EJBHomeFactory getFactory() {
		if(instance == null)  instance = new EJBHomeFactory();
		return instance;
	}
	/**
	 * JNDI ���񽺸� �����ϴ� default server���� �Ķ���� jndi�� ���� mapping�� 
	 * EJBHome�� ��´�.
	 * 
	 * @param String jndi ����� �ϴ� EJBHome�� jndi name.
	 * @param Class clazz class
	 */
	public EJBHome lookupHome(String jndi,Class clazz) throws Exception {
		return lookupHome(DEFAULT_SERVER,jndi,clazz);
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