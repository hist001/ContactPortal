package com.wms.common.beans.dto;

import java.util.LinkedList;

public class ACL  
{
	private LinkedList aclList ;
	public ACL(LinkedList aclList){
		this.aclList=aclList;
	}
	public boolean equals(Object o){
		return aclList.contains(o);
	}

}