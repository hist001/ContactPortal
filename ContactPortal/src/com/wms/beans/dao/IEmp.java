/*************************************************************
*	�� �� ��  : IEmp.java
*	�ۼ�����  : 2004/09/03
*	�� �� ��  : 
*	��    ��  : �������� ��ȸ ���� �������̽�
*************************************************************/
package com.wms.beans.dao;
import com.wms.beans.dto.*;

public interface IEmp
{
	public EmpDTO[] empInquiryList(String orgCd, String orgName, String empId, String empKName, String jobCd, String jobName)throws Exception;
	//����������ȸ-����¡
	public EmpDTO[] empInquiryList(String orgCd, String orgName, String empId, String empKName, String jobCd, String jobName,int pageNum,int pageSize)throws Exception;
	public EmpDTO empJobInquiryList(String empId)throws Exception;
	public EmpDTO empApprovalInquiryList(String empId)throws Exception;
}