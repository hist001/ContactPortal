/*************************************************************
*	�� �� ��  : PrcsCostDTO.java
*	�ۼ�����  : 2004/09/03
*	�� �� ��  : 
*	��    ��  : �������� ���� ���� ����
*************************************************************/
package com.wms.beans.dto;
import com.wms.fw.*;
//��������
public class PrcsCostDTO extends GeneralDTO implements DTO 
{
	public String prodNo;		//#��ǰ��ȣ(FK)
	public String prodType;		//#��ǰ��ȣ(FK)
	public String prcsNo;		//#������ȣ(FK)
	public String accoItem;		//#��������(FK)
	public String accoKorName;     //���������
	public int contAmt;			//���ݾ�
	public int goalAmt;			//��ǥ�ݾ�
	public int rsltAmt;			//������
	public String consultNo;	//ǰ�ǹ�ȣ
}
