/*************************************************************
*	�� �� ��  : EmpJobDTO.java
*	�ۼ�����  : 2004/09/03
*	�� �� ��  : 
*	��    ��  : ���κ����� ���� ���� ����
*************************************************************/
package com.wms.beans.dto;
import com.wms.fw.*;
public class EmpJobDTO extends GeneralDTO implements DTO
{
	public String empId;   	//����
	public String empKName ;//����
	public String jobCd; 		//�����ڵ�
	public String revisionNo;	//������ȣ
	public String jobName; 	//������
	public String jobTitle; 	//���������׸��
	
	public int       yearCnt; // �����߻��Ǽ�
	public float    unitUseHM; // �Ǵ�ҿ�ð�
	public float    yearTotHM; // �������ð�

	public String bizNo;
	public String bizName;
	public String actDs;	
}