/*************************************************************
*	�� �� ��  : StdPrcsDTO.java
*	�ۼ�����  : 2004/09/03
*	�� �� ��  : 
*	��    ��  : ǥ�ذ��� ���� ���� ����
*************************************************************/
package com.wms.beans.dto;
import com.wms.fw.*;
//ǥ�ذ���
public class StdPrcsDTO extends GeneralDTO implements DTO 
{
	public String prcsCd;		//#�������º�ȣ
	public String prcsNo;		//#ǥ�ذ�����ȣ	
	public String prcsName;		//ǥ�ذ�����
	public int prcsRate;		//������ô��
	public int reqTerm;			//�ҿ�Ⱓ
	public int mhRate;			//MH��
	
	public int totCnt=0;  //�� ���ڵ� �� (����¡)
}
