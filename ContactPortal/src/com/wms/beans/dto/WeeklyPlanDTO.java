/*************************************************************
*	�� �� ��  : WeeklyPlanDTO.java
*	�ۼ�����  : 2004/06/22
*	�� �� ��  : 
*	��    ��  : Ȯ���� �ְ�������ȹ�� ������ ��� Ŭ����
*************************************************************/
package com.wms.beans.dto;
import com.wms.fw.*;

public class WeeklyPlanDTO extends GeneralDTO 
{
	public String	weekStartDt;    //�ְ���������
	public String	reEmpId;        //�����ڻ��

	public String   confirmDtm;     //Ȯ���Ͻ�
	public String   coEmpId;        //Ȯ���ڻ��
	public String   plEmpId;        //��ȹ�ڻ��
	public String   crEmpId;        //�ۼ��ڻ��

    //��ȸ�� �ʿ� �ʵ�
	public String plEmpKName;       //��ȹ�ڼ���

	public WeeklyPlanDetailDTO[] weeklyPlanDetailDTOs;//������ȹ��

	
}