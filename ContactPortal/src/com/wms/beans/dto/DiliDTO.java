/*************************************************************
*	�� �� ��  : DiliDTO.java
*	�ۼ�����  : 2004/06/22
*	�� �� ��  : 
*	��    ��  : ���ϱ��°��� ���� Ŭ����
*************************************************************/
package com.wms.beans.dto;
import com.wms.fw.*;

public class DiliDTO extends GeneralDTO implements DTO 
{	
	public String reportDt;	
	public String reEmpId;	
	public String reEmpKName;
	public String apEmpId;		//dailyreport table�� ����������
	public String apEmpKName;		
	public String plEmpId;		//dailyreport table�� ������ȹ�� 040819 �η� ���� ����
	public String plEmpKName;		
	public String statusFlag;	//�����ڵ尪
	public String cgDtm;		//sysdate, �����Ͻ�
	public String cgDs;			//�������

	public String statusName;	//�����ڵ尪�̸�
}
