/*************************************************************
*	�� �� ��  : DayJobReportDTO.java
*	�ۼ�����  : 2004/06/22
*	�� �� ��  : 
*	��    ��  : ���Ͼ������� ���� ������ ���� Ŭ����
*************************************************************/
package com.wms.beans.dto;
import com.wms.fw.*;
public class DayJobReportDTO extends GeneralDTO implements DTO 
{
	public String reportDt;//�ۼ�����
	public String reEmpId; //�����ڻ��
	public String reEmpKName; //�������̸�
	public String createDtm;//�ʱ��ۼ��Ͻ�
	public String lastDtm;  //�����ۼ��Ͻ�
	public String crEmpId;  //�ۼ��ڻ��
	public String crEmpKName; //�ۼ����̸�
	public String plEmpId;   //��ȹ�ڻ�� 
	public String plEmpKName; //��ȹ���̸�
	public String apEmpId;//�����ڻ��
	public String apEmpKName;//�����ڼ���
	public String apDtm;//�����Ͻ�
	public String statusFlag; //���±���
    public String apFlag;     //�ǰ��� ��ϵǾ��ִ����� ���� ����

	//��ȸ 
	public String transStatus;
	public String totalMh;
	public String statusName;
	public JobReportDTO[] plans;
    public String reportDtFr;
    public String reportDtTo;
    public String s_crEmpId;
    public String s_apEmpId;
    
    public String REORGNAME;
	
}
