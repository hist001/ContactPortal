/*************************************************************
*	�� �� ��  : AccoDTO.java
*	�ۼ�����  : 2004/09/03
*	�� �� ��  : 
*	��    ��  : �������� ���� ���� ����
*************************************************************/
package com.wms.beans.dto;
import com.wms.fw.*;
//��������
public class AccoDTO extends GeneralDTO implements DTO 
{
	public String accoItem;			//#��������
	public String korName;			//�ѱ۸�Ī
	public String engname;			//������Ī
	public String highAccoItem;		//������������
	public String cdFlag;			//�����뺯����
	public String gAccoItem;			//�׷��������
	public String maEnable;			//�������ɰ����Ҵɱ���
	public String diCostType;			//������������
	public String labCostType;		//����GNA�����ܱ���
	public String consultType;		//ȸ��ǰ�Ǳ���
	public String monthlyCalc;		//���Ұ��
	public String fixVarCost;			//����������

	public PrcsCostDTO[] prcsCostDTOs;		//��������
}
