/*************************************************************
*	�� �� ��  : JobReportDTO.java
*	�ۼ�����  : 2004/06/22
*	�� �� ��  : 
*	��    ��  : ���Ͼ��������� �����׸� ������ ���� Ŭ����
*************************************************************/
package com.wms.beans.dto;

public class JobReportDTO extends JobDTO 
{
	public String	delFlag;
	public String   reportDt;//��������
	public String   reEmpId; //�����ڻ��
	public String   sn;      //���
	public int	    mh;
	public int	    hh;
	public int	    mm;
	public String	weekPlanCd; //�ְ�������ȹ�ڵ�
	public String	actFlag; //���������������� D(����) I(����)
	public String	transFlag;//����� ��������
	public TransDetailDTO transDetailDTO;
	
	public String jobView;
	public String prcsNoView;
}
