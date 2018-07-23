/*************************************************************
*	�� �� ��  : ProdDTO.java
*	�ۼ�����  : 2004/09/03
*	�� �� ��  : 
*	��    ��  : ��ǰ ���� ���� ����
*************************************************************/
package com.wms.beans.dto;
import com.wms.fw.*;
import java.util.HashMap;
//��ǰ
public class ProdDTO extends GeneralDTO implements DTO 
{
	public String prodNo;			//#��ǰ��ȣ
	public String prodType;			//#��ǰ����
	public String prodName;			//��ǰ��
	public String bizNo;			//�����ȣ(FK)
	public String bizName;			//����� -->�߰�
	public String bizAcqCd;			//�ŷ�ó(FK)
	public String chagName;			//�ŷ�ó�� -->�߰�(��)
	public String bizAcqName;		//�ŷ�ó�� -->�߰�(��) 2005.04.22�� �߰���
	public String orgCd;			//�ְ�����
	public String orgName;			//�ְ�������  -->�߰�
	public String contNo;			//����ȣ
	public String contDt;			//�����
	public String deliDt;			//��ǰ��
	public long contAmt;			//����ݾ�
	public long supplyAmt;			//���ް���	--> 2005.08.19 �߰�
	public String contFlag;			//��������	--> 2005.08.19 �߰�
	public int goalProfitRate;		//��ǥ������
	public int contMh;				//���ManHour
	public int goalMh;				//��ǥManHour
	public int rsltMh;				//����ManHour
	public String lasStatusDtm;		//���������Ͻ�
	public String actOrgCd;			//��������
	public String actOrgName;		//���������� -->�߰�
	//public int maTerm;				//���������Ⱓ
	public String maTerm;				//���ں����Ⱓ -->����
	public int penaltyRate;			//��ü�����
	public String agenda;			//��ǰ����
	public String prodSyncDtm;		//��ǰ����ȭ�Ͻ�
	public String crEmpId;			//�ۼ��ڻ��
	public String crEmpKName;		//�ۼ����̸� -->�߰�
	public String crEmpOrgCd;		//�ۼ��ڼҼ��ڵ� -->�߰�
	public String crEmpOrgName;		//�ۼ��ڼҼӸ� -->�߰�
	public String curEmpId;			//���۾����̸�
	public String curEmpKName;		//���۾����̸� -->�߰�
	public String curEmpOrgName;
	public String pmEmpId;			//PM���
	public String pmEmpKName;		//PM�̸� -->�߰�
	public String pmEmpOrgName;
	public String saleEmpId;		//��������ڻ��
	public String saleEmpKName;		//�ۼ����̸� -->�߰�
	public String saleEmpOrgName;
	public String subWrEmpId;		//�Ϻ��ۼ��ڻ��
	public String subWrEmpKName;	//�Ϻ��ۼ����̸� -->�߰�
	public String subWrEmpOrgName;
	public String prodStatus;		//��ǰ����
	public String prcsType;			//�������� -->�߰�
	public String contStartDt;		//����������
	public String contEndDt;		//�����������
	public String plStartDt;		//��ȹ��������
	public String plEndDt;			//��ȹ��������
	public String realStartDt;			//����������
	public String realEndDt;			//����������	-->2005.07.21 �߰�	
	public long expectCost;				//������
	public long expectPnl;				//�������
	public String salesAcctCd;			//���� ���� �ڵ�
	public String proFitType;			//���� ����
	
	public String lastPrcsName;		//����������	 	
	public int plProgDtCnt;                      //��ȹ��ô�ϼ�
	public float plProgDtRate;			//��ȹ��ô��

	public int totCnt=0;				//�ѷ��ڵ��
		
	public BilColDtDTO[] bilColDtDTOs;	//û������
	public InstallDTO[] installDTOs;		//��ġ����
	public PrcsDTO[] prcsDTOs;			//����
	
	public HashMap prodEmps;				//��ǰ�ο�����

}
