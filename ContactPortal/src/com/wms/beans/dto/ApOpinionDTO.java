/*************************************************************
*	�� �� ��  : ApOpinionDTO.java
*	�ۼ�����  : 2004/09/15
*	�� �� ��  : 
*	��    ��  : ���� ���� �ǰ� ������ ��� Ŭ����
*************************************************************/
package com.wms.beans.dto;
import com.wms.fw.*;
public class ApOpinionDTO extends GeneralDTO 
{

	public String reEmpId;    //�����ڻ��
	public String reportDt;   //�ۼ�����
	public String sn;         //����
	public String apEmpId;    //�����ڻ��
	public String createDtm;  //�ۼ��Ͻ�
	public String apFlag;     
	//apFlag=> �����ǰ�,����� ���а�
	//A==�����ǰ�
	//E==�����

	public String apContents; //�ǰ� ����

    //view�� ó���ϱ� ���� ����
	public String reEmpKName; //�������̸�
	public String apEmpKName; //�����ڼ���
}
