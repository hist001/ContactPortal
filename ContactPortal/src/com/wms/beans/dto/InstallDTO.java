/*************************************************************
*	�� �� ��  : InstallDTO.java
*	�ۼ�����  : 2004/09/03
*	�� �� ��  : 
*	��    ��  : ��ġ���� ���� ���� ����
*************************************************************/
package com.wms.beans.dto;
import com.wms.fw.*;
//��ġ����
public class InstallDTO extends GeneralDTO implements DTO 
{
	public String prodNo;			//#��ǰ��ȣ(FK)
	public String prodType;			//#��ǰŸ��
	public String prcsNo;			//#������ȣ
	public String sno;				//#����
	public String prodName;			//��ǰ��
	public String prcsName;			//������
	public String instType;			//��ġ����
	public String itemNo;			//ǰ���ȣ
	public String itemName;			//ǰ��
	public int instCnt;				//��ġ����
	public String instSno;			//��ġSN
	public int contUPrice;			//���ܰ�
	public int uPrice;				//�ܰ�
	public String instPlanDt;		//��ġ��ȹ����
	public String instDt;			//��ġ����
	public String itemDesc;			//��ġ�������λ���
}
