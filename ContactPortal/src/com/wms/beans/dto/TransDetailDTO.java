/*************************************************************
*	�� �� ��  : TransDetailDTO.java
*	�ۼ�����  : 2004/06/22
*	�� �� ��  : 
*	��    ��  : ����񳻿� ���� ������ ��� Ŭ����
*************************************************************/
package com.wms.beans.dto;
import com.wms.fw.*;
public class TransDetailDTO extends GeneralDTO implements DTO 
{
	public String sn;          //����
	public String reportDt;    //��������
	public String reEmpId;     //�����ڻ��
	public String bizAcqCd;    //�ŷ�ó
	public String startTm;     //��߽ú�
	public String endTm;       //�����ú�
	public int carSelKm;       //�ڰ��뼭������Ÿ�
	public int carEtcKm;       //�ڰ������������Ÿ�
	public long carEtc;         //�ڰ���������/�����
	public long bus;            //���߱���-������
	public long subway;         //���߱���-����ö��
	public long taxi;           //���߱���-�ýú�
	public long car;           //�ڰ��� �հ�

	public String chagName;    //�ŷ�ó��
	public String loc ;        //����

	
}
