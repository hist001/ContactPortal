/*************************************************************
*	�� �� ��  : IWeekJobPlan.java
*	�ۼ�����  : 2004/06/22
*	�� �� ��  : 
*	��    ��  : ������ȹ ��ȸ ���� �������̽�
*************************************************************/
package com.wms.beans.dao;
import com.wms.beans.dto.*;

public interface IWeekJobPlan
{
	public boolean save(WeekJobPlanDTO before,WeekJobPlanDTO after)throws Exception;
	//�ڽ��� ���� �ְ�������ȹ���� ��ȸ�Ѵ�.
	public WeekJobPlanDTO[] weekJobPlanSearch(String empId,String day )throws Exception;
	//������  �����Ǿ��ִ� �ְ�������ȹ�� ��ȸ�Ѵ�.
	public WeekJobPlanDetailDTO weekJobPlanSubSearch(WeekJobPlanDetailDTO dto,boolean planner)throws Exception;
	//�����ϱ� ���� ���� ��ȹ�ڵ��� �ְ�������ȹ�� ��ȸ�Ѵ�.(%�ְ�������ȹ�� ������, ��������ȹ���� ��ȸ)
//	public WeekJobPlanDTO[] weekJobPlanUpdateSearch(String empId,String day)throws Exception;
	public boolean confirm(WeekJobPlanDTO dto,String empId,String empKName, String flag)throws Exception;
	//����ȭ�鿡 ��ǰ��, ������ �ְ� ������ȹ�� ��ȸ�Ѵ�.
	public java.util.ArrayList weekJobPlanListSearch(String empId,String day,boolean planner)throws Exception;
}