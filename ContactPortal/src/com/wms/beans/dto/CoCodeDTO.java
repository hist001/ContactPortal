/*************************************************************
*	�� �� ��  : CoCodeDTO.java
*	�ۼ�����  : 2004/09/03
*	�� �� ��  : 
*	��    ��  : �������� ���� ���� ����
*************************************************************/
package com.wms.beans.dto;
import com.wms.fw.*;

public class CoCodeDTO extends GeneralDTO implements DTO 
{
	public String cdType;		//#�ڵ�����
	public String cd;			//#�ڵ�	
	public String cdName;		//�ڵ��
	public String cdDs;			//�ڵ弳��
	
	public int       totCnt=0;              //�ѷ��ڵ� ��(����¡)
	
	public String cdTypeName;
}
