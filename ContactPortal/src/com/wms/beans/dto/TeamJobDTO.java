/*************************************************************
*	�� �� ��  : TeamJobDTO.java
*	�ۼ�����  : 2004/09/03
*	�� �� ��  : 
*	��    ��  : ������ ���� ����
*************************************************************/
package com.wms.beans.dto;
import com.wms.fw.*;
public class TeamJobDTO extends GeneralDTO implements DTO
{
	public String orgCd;   
	public String orgName ;
	public String jobCd; //�����ڵ�
	public String jobName; //������
	
	public String bizNo;
	public String bizName;
	public String actDs;	
	public String useFlag;
}