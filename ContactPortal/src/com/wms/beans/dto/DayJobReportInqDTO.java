/*************************************************************
*	�� �� ��  : DayJobReportInqDTO.java
*	�ۼ�����  : 2004/06/22
*	�� �� ��  : 
*	��    ��  : ���Ͼ������� ��ȸ�� ���� ������ ���� Ŭ����
*************************************************************/
package com.wms.beans.dto;
import com.wms.fw.*;
public class DayJobReportInqDTO extends GeneralDTO implements DTO 
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
	public String statusName;
	public String managerFlag; //�����ڱ���
	public String flag;//
	public String apFlag;  //�����ǰ� add 200608.03

	//��ȸ 
	public String transStatus;
	public String totalMh;
	public JobReportDTO[] plans;
    public String org_no;
    public String orgName;	

}
