/*************************************************************
*	�� �� ��  : WePlanTempDTO.java
*	�ۼ�����  : 2004/06/22
*	�� �� ��  : 
*	��    ��  : �ӽ�Ȯ���� ������ȹ ���� ������ ��� Ŭ����
*************************************************************/
package com.wms.beans.dto;

public class WePlanTempDTO extends JobDTO 
{
	public String	weStartDt;   //�ְ���������
	public String	reEmpId;     //�����ڻ��
	public int      mh;          //menhour
	public int      hh;          //�ð�
	public int      mm;          //��
	public String   delFlag;     //��������
	public String   actFlag;     //����������
	public String   planCd;      //�⺻��ȹ��ȣ
	public String   confirmDtm;  //Ȯ���Ͻ�
	public String   coEmpId;     //Ȯ���ڻ��
	public String   plEmpId;     //��ȹ�ڻ��
	public String   crEmpId;     //�ۼ��ڻ��

	//��ȸ�� �ʿ� �ʵ�
	public String   reEmpKName;  //�ۼ��ڼ���
	public String   plEmpKName;  //��ȹ�ڼ���

	
}