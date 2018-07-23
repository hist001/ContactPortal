/*************************************************************
*	�� �� ��  : IJobPlan.java
*	�ۼ�����  : 2004/06/22
*	�� �� ��  : 
*	��    ��  : ������ȹ ���� �������̽�
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