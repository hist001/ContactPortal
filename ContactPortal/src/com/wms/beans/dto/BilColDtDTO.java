/*************************************************************
*	�� �� ��  : BilColDtDTO.java
*	�ۼ�����  : 2004/09/03
*	�� �� ��  : 
*	��    ��  : û������ ���� ���� ����
*************************************************************/
package com.wms.beans.dto;
import com.wms.fw.*;
//û��������
public class BilColDtDTO extends GeneralDTO implements DTO 
{
	public String prodNo;		//#��ǰ��ȣ(FK)	
	public String sn;			//#����
	public String bilColName;	//û�����ݸ�
	public String planDt;		//��ȹ����
	public String bilDt;		//û������
	public String receiptDt;	//�Ա�����
	public int pressCnt;		//����Ƚ��
	public String pressDesc;	//���˻󼼳���
}
