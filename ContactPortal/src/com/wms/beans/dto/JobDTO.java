/*************************************************************
*	�� �� ��  : JobDTO.java
*	�ۼ�����  : 2004/06/22
*	�� �� ��  : 
*	��    ��  : ���Ͼ������� ���� DTO�� ���� Ŭ����
*************************************************************/
package com.wms.beans.dto;
import com.wms.fw.*;

public class JobDTO extends GeneralDTO implements DTO 
{
	public String	jobNo;     //��ǰ�ڵ�
	public String	jobName;   //��ǰ��
	public String   prcsNo;    //�����ڵ�
	public String   prcsName;  //������
	public String	jobTitle;  //���Ϻ����׸��   
	public String	jobDetail; //���Ϻ����׸񳻿�
	public String	startDt;   //������
	public String	endDt;     //������    
}
