package com.wms.fw;
import java.util.Hashtable;
public class DAOFactory
{
	private static Hashtable storage;
	/**
	 * DAO객체를 리턴한다.
	 * @param daoName은 DAO객체가 implements하고 있는 Interface명
	 * @return java.lang.Object
	 */	
	public static Object getDAO(String daoName)throws Exception{
		if(storage==null){		
			storage=new Hashtable();
		}	
		if(!storage.containsKey(daoName)){	
			com.wms.fw.Config conf = new com.wms.fw.Configuration();
			String implName=conf.get(daoName);
			if(implName==null){
				throw new Exception("해당 Interface는 존재하지 않습니다.");
			}	

			storage.put(daoName,Class.forName(implName.trim()).newInstance());
			
		}
		return storage.get(daoName);

	}
};