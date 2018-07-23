/*************************************************************
*	�� �� ��  : IWkJobPlan.java
*	�ۼ�����  : 2004/06/22
*	�� �� ��  : 
*	��    ��  : �ְ�������ȹ Ȯ�� ���� �������̽�
*************************************************************/
package com.wms.beans.dao;
import com.wms.beans.dto.*;
import java.util.*;

public interface IWkJobPlan
{
	//���� �˻�
    public PrcsDTO wkJobPrcsSearch(String jobNo,String prcsNo) throws Exception;	
	//���� ��ȹ �˻�
	public JobPlanDTO wkJobPlanSearch(String planCd) throws Exception;
	//��ü �ְ� ������ȹ�� �ĺ��� Ȯ�� �˻�
	public ArrayList wkJobSearch(String empId,String orgCd, String startDt, String endDt) throws Exception;
	//�ְ� ������ȹ Ȯ��
	public boolean confirm(WePlanTempDTO[] dto,String flag)throws Exception;
	//�ϰ� Ȯ��
	public String unionConfirm(ArrayList dto)throws Exception;

	//�ӽ� Ȯ�� ��ȹ ����
	public boolean updateTemp(WePlanTempDTO[] dto,WePlanTempDTO[] bHdto)throws Exception;

	//public boolean updateTemp(WePlanTempDTO dto,WePlanTempDTO[] bHdto)throws Exception;
	//�ְ� Ȯ�� ����
	public boolean updateWeekly(WePlanTempDTO dto,WePlanTempDTO[] bHdto)throws Exception;
    //�ӽ� Ȯ�� ��ȹ ��ȸ
	public ArrayList wkJobTempSearch(String plEmpId, String startDt) throws Exception;
	//�ְ� Ȯ�� ��ȹ ��ȸ
	public ArrayList wkJobWeeklySearch(String plEmpId, String startDt) throws Exception;

	//�Ѱ��� �ӽ� Ȯ����ȹ�� �˻�
    public WePlanTempDTO[] wkJobTempDetail(String weStartDt,String jobNo,String prcsNo,String planCd) throws Exception;
	//�Ѱ��� �ְ� Ȯ����ȹ�� �˻�
	public WePlanTempDTO[] wkJobWeeklyDetail(String weekStartDt,String jobNo,String prcsNo,String planCd) throws Exception;


}
