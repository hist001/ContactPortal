/*************************************************************
*	파 일 명  : IJobPlan.java
*	작성일자  : 2004/06/22
*	작 성 자  : 
*	내    용  : 업무계획 관련 인터페이스
*************************************************************/
package com.wms.beans.dao;
import com.wms.beans.dto.*;
import java.util.ArrayList;

public interface IJobPlan
{
	public boolean save(JobPlanDTO dto)throws Exception;
	public boolean del(String planCD)throws Exception;	
	public boolean mdel(String planCDs)throws Exception;	
	public ArrayList jobPlanSearch(String empId,boolean key )throws Exception;
	public JobPlanDTO jobPlanDetailSearch(String empId,String planCd)throws Exception;

}