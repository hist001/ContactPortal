/*************************************************************
*	�� �� ��     : JobPlanDTO.java
*	�ۼ�����     : 2004/06/22
*   ������������ : 2005/04/06 �����ֱ� ���� �߰�
*	�� �� ��     : ����ȣ
*	��    ��     : ������ȹ ���� ������ ��� Ŭ����
*************************************************************/
package com.wms.beans.dto;

import java.util.ArrayList;

public class JobPlanDTO extends JobDTO  
{
	public String   planCd;			//��ȹ��ȣ
	public int      planMh;			//��ȹ�ð�
	public String   plEmpId;		//��ȹ�ڻ��
	public String   plEmpKName;		//��ȹ�ڼ���
	public String   orgCd;			//������ȣ
	public String   lastCrEmpId;	//�����ۼ��ڻ��
	public String   lastCrEmpKName; //�����ۼ��ڼ���
	public String   lastWePlDt;		//�������������
	public String   actFlag;		//����(D),����(I)����
	public String   prodSyncDtm;
    public String   divFlag;        //�����ֱⱸ��(����==D,����==W,�ſ�=M) 2005/04/06
	//public ArrayList values;
	public AsdEmpDTO[] values;      //������ ������
}
