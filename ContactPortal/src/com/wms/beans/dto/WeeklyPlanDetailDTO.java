/*************************************************************
*	�� �� ��  : WeeklyPlanDetailDTO.java
*	�ۼ�����  : 2004/06/22
*	�� �� ��  : 
*	��    ��  : Ȯ���� �ְ�������ȹ�� ������ȹ �ϳ��� 
*               ������ ��� Ŭ����
*************************************************************/
package com.wms.beans.dto;

public class WeeklyPlanDetailDTO extends JobDTO 
{
	public String   sn       ;     //����
	public String	weekStartDt;   //�ְ���������
	public String	reEmpId;       //�����ڻ��
	public int      mh;            //menhour
	public int      hh;            //�ð�
	public int      mm;            //��
	public String   delFlag;       //��������
	public String   actFlag;       //����������
	public String   planCd;        //�⺻��ȹ��ȣ

	//��ȸ�� �ʿ�
	public String   reEmpKName;    //�ۼ��ڼ���
}