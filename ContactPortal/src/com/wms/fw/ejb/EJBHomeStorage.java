package com.wms.fw.ejb;

import java.util.Hashtable;

import javax.ejb.EJBHome;
import javax.naming.NamingException;

import com.wms.fw.ConfigurationException;
/**
 * Caching하기위해  EJBHome에 대한 정보를 JNDI서비스 제공 server별로 관리한다.
 * 
 *	@since   JDK1.3
 */
class EJBHomeStorage {
	private Hashtable storage;
	private String name;
	
	EJBHomeStorage(String name) {
		storage = new	Hashtable();
		this.name = name;
	}
	
	EJBHome create(String jndi,Class clazz) throws NamingException, ConfigurationException{
		
		EJBHome home = null;
		
		if(storage.containsKey(jndi)) {
			home =  (EJBHome)storage.get(jndi);
			if( home != null ) return home;
		}
		
		home = JNDIUtility.findEJBHome(name,jndi,clazz);

		if(home != null) store(jndi,home);
		
		return home;
		
	}
	
	private void store(String jndi,EJBHome home) {
		storage.put(jndi,home);
	}
	
	void remove(String jndi) {
		storage.remove(jndi);
	}
	
	Hashtable getStorage() {
		return storage;
	}
	
	int size() {
		return storage.size();	
	}

	void clear() {
		storage =  new Hashtable();
	}

}