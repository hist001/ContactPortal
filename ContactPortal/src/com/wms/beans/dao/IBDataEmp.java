/*************************************************************
*	�� �� ��  : IBDataEmp.java
*	�ۼ�����  : 2004/06/22
*	�� �� ��  : 
*	��    ��  : ������������ �������̽�
*************************************************************/
package com.wms.beans.dao;
import com.wms.beans.dto.*;

public interface IBDataEmp
{
	//�����⺻����
	public BDataEmpDTO[] selectBDataEmpList(BDataEmpDTO dto) throws Exception;
	public BDataEmpDTO selectBDataEmp(String empId) throws Exception;
	public boolean updateBDataEmp(BDataEmpDTO dto)throws Exception;
	public boolean createBDataEmp(BDataEmpDTO dto)throws Exception;
	//������������
	public DayJobReportDTO[] selectDiliList(String highEmpId, String role,String reportDtFr,String reportDtTo,String s_crEmpId, String s_apEmpId) throws Exception;
	public DiliDTO selectDili(String reportDt, String reEmpId)throws Exception;
	public boolean saveDili(DiliDTO dto, String userEmpId)throws Exception;

}
