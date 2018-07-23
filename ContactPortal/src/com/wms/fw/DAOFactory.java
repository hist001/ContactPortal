package com.wms.fw;
import java.util.Hashtable;
public class DAOFactory
{
	private static Hashtable storage;
	/**
	 * DAO��ü�� �����Ѵ�.
	 * @param daoName�� DAO��ü�� implements�ϰ� �ִ� Interface��
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
				throw new Exception("�ش� Interface�� �������� �ʽ��ϴ�.");
			}	

			storage.put(daoName,Class.forName(implName.trim()).newInstance());
			
		}
		return storage.get(daoName);

	}
};