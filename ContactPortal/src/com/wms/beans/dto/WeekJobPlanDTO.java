/*************************************************************
*	�� �� ��  : WeekJobPlanDTO.java
*	�ۼ�����  : 2004/06/22
*	�� �� ��  : 
*	��    ��  : �ְ� ������ȹ�� ���� ������ ���� Ŭ����
*************************************************************/
package com.wms.beans.dto;
import com.wms.fw.*;
public class WeekJobPlanDTO extends GeneralDTO implements DTO 
{
	public String	weekStartDt;         //�ְ���������
	public String	rEmpId;              //�����ڻ��
	public String   empKName;            //�������̸�
	public String   confirmDtm;          //Ȯ���Ͻ�
	public String   coEmpId;             //Ȯ���ڻ��
	public String	coEmpKName;          //Ȯ���ڼ���
	public String	plEmpId;             //��ȹ�ڻ��
	public String	plEmpKName;          //��ȹ�ڼ���
	public String	crEmpId;             //�ۼ��ڻ��
	public String	crEmpKName;          //�ۼ��ڼ���
	public WeekJobPlanDetailDTO[] plans; //�ְ�������ȹȮ������
	
}
