/*************************************************************
*	�� �� ��  : IHistJob.java
*	�ۼ�����  : 2004/06/22
*	�� �� ��  : 
*	��    ��  : ���� ���� �������̽�
*************************************************************/
package com.wms.beans.dao;
import com.wms.beans.dto.HistJobDTO;
public interface IHistJob
{   //�ش� ������ ������ ���οϷ�� ���� �˻��Ѵ�.
	public HistJobDTO[] searchEmpJob(String empNo)throws Exception;
    //�⺻ ������ �˻��Ѵ�.
	public HistJobDTO[] searchHistJob(HistJobDTO dto)throws Exception;
	//������������� ��ȸ�Ѵ�
	public HistJobDTO[] histJobInquiryList(String jobCd, String jobName)throws Exception;
	//������������� ��ȸ�Ѵ�--����¡
	public HistJobDTO[] histJobInquiryList(String jobCd, String jobName, int pageNum, int pageSize)throws Exception;
}
