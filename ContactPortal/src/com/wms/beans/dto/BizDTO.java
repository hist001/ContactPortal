/*************************************************************
*	�� �� ��  : BizDTO.java
*	�ۼ�����  : 2004/09/03
*	�� �� ��  : 
*	��    ��  : ��� ���� ���� ����
*************************************************************/
package com.wms.beans.dto;
import com.wms.fw.*;
//���
public class BizDTO extends GeneralDTO implements DTO 
{
	public String   bizNo;	 //#����ڵ�
	public String   bizName;	 //�����
	public String   bizCdType; //��������
	public String   prcsType;	 //��������
	public String   salOrgCd;	 //�ְ���������
	public String   techOrgCd; //�ְ������������
	public String   actOrgCd;	 //�ְ���������
	
	public int         totCnt=0; 	 //�ѷ��ڵ�� (����¡)

	public ProdDTO[] prodDTOs;	//��ǰ��
}
