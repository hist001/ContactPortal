/*************************************************************
*	�� �� ��  : OrgDTO.java
*	�ۼ�����  : 2004/06/22
*	�� �� ��  : 
*	��    ��  : ������ȸ ���� ������ ��� Ŭ����
*************************************************************/
package com.wms.beans.dto;
import com.wms.fw.*;

public class OrgDTO extends GeneralDTO implements DTO 
{
	public String	orgCd;   //������ȣ
	public String	orgName; //������
	public String    startDt;  //�����Ͻ�
	public String    endDt;//�����̽�
	public String	bizNo;//���
	public String	labCostType;//�빫��������
}
