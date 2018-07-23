/*************************************************************
*	�� �� ��  : IDayErr.java
*	�ۼ�����  : 2004/06/22
*	�� �� ��  : 
*	��    ��  : ������ġ ���� �������̽�
*************************************************************/
package com.wms.beans.dao;
import com.wms.beans.dto.*;
import com.wms.fw.servlet.Box;
public interface IDayErr
{
	//����������ȸ�� ���°� ��ȸ
	public StatusDTO[] searchStatus() throws Exception;
	//��Ȳ��ȸ�� �����ڵ� ��ȸ
	public StatusDTO[] searchStatusList(String statusCd, String statusName) throws Exception;
	//���Ͼ���������ۼ��� �˻�
	public DayErrDTO[] dayErrSearch(Box box) throws Exception;
	//����¡-�Ⱓ��������ȸ
	public DayErrDTO[] dayErrSearchPage(Box box,int pageNum,int pageSize) throws Exception;
	public DayErrDTO[] dayErrSearchPage4TL(Box box,int pageNum,int pageSize) throws Exception;
	public DayErrDTO[] dayErrMiSearch(Box box,int pageNum,int pageSize) throws Exception;
	//����¡-���Ϲ��ۼ���ȸ
	public DayErrDTO[] dayErrMiSearch(Box box) throws Exception;
	public DayErrDTO[] dayErrApSearch(Box box,String empId) throws Exception;
	public boolean updateDaily(DayErrDTO dto)throws Exception;
	//���Ͼ�������̽���
	public DayErrDTO[] delayConfDayReportList(String empId, String role) throws Exception;
}
