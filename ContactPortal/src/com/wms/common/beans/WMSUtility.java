package com.wms.common.beans;
/**
 * @(#)WMSUtility.java
 */
import com.wms.beans.dto.EmpDTO;
import java.lang.reflect.*;
import java.util.Enumeration;
import java.util.Vector;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.Properties;

import com.wms.beans.dto.AppListDTO;

public final class WMSUtility {

	/**
	 * Don't let anyone instantiate this class
	 */
	private WMSUtility() {}

	/**
    *	조직별로 직원을 분류한다.
    */

	public static ArrayList classify(EmpDTO[] empList,String orgCd)throws Exception{
		if(empList==null)return null;

		int index=0;
		int defOrgIdx=-1;//default OrgCd의 값
		String key=empList[index].empOrgCd;
		if(key==orgCd)
			defOrgIdx=0;
	    ArrayList returnList=new ArrayList();
		for(int i=0;i<empList.length;i++){
			if(!key.equals(empList[i].empOrgCd)){
				EmpDTO[] returns=new EmpDTO[i-index];
				System.arraycopy(empList,index,returns,0,returns.length);
				returnList.add(returns);
				key=empList[i].empOrgCd;
				if(key==orgCd)
					defOrgIdx++;
				index=i;				
			}	
		}
		EmpDTO[] returns=new EmpDTO[empList.length-index];
		System.arraycopy(empList,index,returns,0,returns.length);
		returnList.add(returns);
		if(defOrgIdx==-1)
				defOrgIdx=returnList.size()-1;
        EmpDTO[] test = (EmpDTO[])returnList.get(0);//default값을 맨처음으로 셋팅한다.
        returns = (EmpDTO[])returnList.remove(defOrgIdx);
		returnList.add(0,returns);
        test = (EmpDTO[])returnList.get(0);
       
		return returnList;
	}
	public static ArrayList getOrgNameList(ArrayList empList)throws Exception{
		ArrayList returns=new ArrayList();
		EmpDTO key=null;
		AppListDTO  aLdto=null;
		
		for(int i=0;i<empList.size();i++){
			key=((EmpDTO[])empList.get(i))[0];	
			returns.add(key);
		}
		/*2005/02/07조직개편이후
		for(int i=0;i<empList.size();i++){
			aLdto=(AppListDTO)empList.get(i);	
			if(Integer.parseInt(aLdto.lv)<3){
				returns.add(aLdto.empDTOs);
			}else{break;}
		}
		*/
		return returns;
	}

}