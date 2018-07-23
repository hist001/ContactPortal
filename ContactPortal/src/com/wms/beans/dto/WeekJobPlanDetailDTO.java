/*************************************************************
*	�� �� ��  : WeekJobPlanDetailDTO.java
*	�ۼ�����  : 2004/06/22
*	�� �� ��  : 
*	��    ��  : �ְ�������ȹ�� �ϳ��� ������ȹ��
*               ���� ������ ���� Ŭ����
*************************************************************/
package com.wms.beans.dto;
import com.wms.fw.*;
public class WeekJobPlanDetailDTO extends GeneralDTO implements DTO 
{
	public String	weekStartDt;        //�ְ���������
	public String	reEmpId;            //���
	public String    sn;                //����
	public String	jobNo;              //��ǰ�ڵ� �Ǵ� �����ڵ�
	public String	jobName;            //��ǰ�� �Ǵ� ������
	public String    prcsNo;            //�����ڵ� �Ǵ� ����ڵ�
	public String    prcsName;          //������ �Ǵ� �����
	public String	startDt;            //��������
	public String	endDt;              //��������
	public String	jobTitle;           //���Ϻ����׸��
	public String	jobDetail;          //���Ϻ����׸񳻿�
	public int	        mh;             //ManHour
	public int	        hh;             //�۾��ð���
	public int	        mm;             //�۾��ð��� �з�
	public String	delFlag;            //������������
	public String	actFlag;            //����������������
	public String    planCd;            //��������ȹ�ڵ�
	public EmpDTO[] assignedEmps;       //������ �迭
	public WeekJobPlanDTO[] plans;      
}
