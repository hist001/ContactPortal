/*************************************************************
*	�� �� ��  : PrcsManCntDTO.java
*	�ۼ�����  : 2004/09/03
*	�� �� ��  : 
*	��    ��  : �����ο� ���� ���� ����
*************************************************************/
package com.wms.beans.dto;
import com.wms.fw.*;
//�����ο�
public class PrcsManCntDTO extends GeneralDTO implements DTO 
{
	public String prodNo="";		//#��ǰ��ȣ(FK)
	public String prodName="";		//��ǰ��
	public String prcsNo="";		//#������ȣ(FK)
	public String prcsName="";		//����
	public String prodType="";		//#��ǰ����
	public String empId="";			//#������ȣ
	public String empKName="";   	//#�����̸�
	public String empOrgName="";  //�����ҼӸ�
	public String jobCd="";			//��������
	public String jobName="";		//����������
	public String intoStartDt="";		//���Խ�����
	public String intoEndDt="";		//���ԿϷ���	
	public int    intoRate=0;		        //���Է�
}
