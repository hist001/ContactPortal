/*************************************************************
*	�� �� ��  : PrcsDTO.java
*	�ۼ�����  : 2004/09/03
*	�� �� ��  : 
*	��    ��  : ���� ���� ���� ����
*************************************************************/
package com.wms.beans.dto;
import com.wms.fw.*;
//����+����
public class PrcsDTO extends GeneralDTO implements DTO 
{
	public String prodNo;		//#��ǰ��ȣ(FK)
	public String prcsNo;		//#������ȣ	
	public String prodType;		//��������(P:��ǰ, T:Task)
	public String prcsName;		//������
	public String prcsDesc;		//��������
	public String prcsEFlag;		//������������
	public String orgCd;			//�ּ�������
	public String orgName;		//�ּ���������
	public String prePrcs;		//�������
	public String prePrcsName;	//���������
	public String plStartDt;		//��ȹ������
	public String plEndDt;		//��ȹ�Ϸ���
	public int 	    plMh;			//��ȹManHour
	public String prcStatus;		//����������
	public String prcsOrgCd;		//����������
	public String prcsOrgName;	//������������
	public String realStartDt;		//���簳����
	public String realEndDt;		//����Ϸ���
	public int      realMh;			//����ManHour
    	public String lastWePlDt;		//�����ְ�����Ȯ������
    	public String chgFlag;		//��������

    //��ȸ�� �ʿ��� �ʵ�
    	public String prodName;		//��ǰ��
	public String plEmpId;		//��ȹ�ڻ��
	public String plEmpKName;	//��ȹ�ڼ���
    	public String jobTitle;		//������

	public PrcsCostDTO[] prcsCostDTOs;	//��������
	public PrcsManCntDTO[]	prcsManCntDTOs;	//�����ο�
}
