/*************************************************************
*	�� �� ��  : DayErrDTO.java
*	�ۼ�����  : 2004/06/22
*	�� �� ��  : 
*	��    ��  : �������� ���� ������ ��� Ŭ����
*************************************************************/
package com.wms.beans.dto;
import com.wms.fw.*;
public class DayErrDTO extends GeneralDTO 
{
	public String reEmpId; //�����ڻ��
	public String reportDt;//�ۼ�����
	public String reEmpKName; //�������̸�
	public String apEmpId;//�����ڻ��
	public String apEmpKName;//�����ڼ���
	public String apDtm;//�����Ͻ�
	public String reWriteDtm;//���ۼ��Ͻ�
	public String statusFlag; //���±���
	public String log;
	public String orgCd;
	public String orgName;
	public String totalMh;
	public String org_no;
	
	public int      totCnt=0; //����¡�� ���� �� ���ڵ� ��
	public String statusName;
    	//Flag N ���ۼ�
	//Flag Y ó��
	//Flag R �������� ���ۼ� �䱸����

}
