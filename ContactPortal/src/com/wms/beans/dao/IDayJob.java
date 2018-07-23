/*************************************************************
*	�� �� ��  : IDayJob.java
*	�ۼ�����  : 2004/06/22
*	�� �� ��  : 
*	��    ��  : �������� ���� �������̽�
*************************************************************/
package com.wms.beans.dao;
import com.wms.beans.dto.*;

public interface IDayJob
{
	//���� ���� ���� 
	public boolean save(DayJobReportDTO before,DayJobReportDTO after,String flag, String foodFlag, String foodFlag2, String orgNo)throws Exception;
	//���,���� �������� ���� ���� ��ȸ
	public DayJobReportDTO dayJobSearch(String empId,String day )throws Exception;
    //�������� ��ȸ
	public DayJobReportDTO[] dayJobSubSearch(String empId,String day,String orgCd,int mode)throws Exception;
	//���� ó��
	public boolean confirm(DayJobReportDTO dto)throws Exception;
	//�յ� ���� ���� ����
	public String unionSave(DayJobReportDTO dto,AsdEmpDTO[] empList)throws Exception;
	//�������� ��ȸ
	public DayJobReportDTO[] dayJobSubSearch(String empId,String startDt,String endDt,String orgCd,int mode)throws Exception;	
	//�ڰ��� ó���� ���� ��ȸ
	public String searchCarPrice(String empId)throws Exception;
	//����������ν� 2004/08/12 	
	public DayJobReportDTO[] confirmSearch(String empId,String orgCd,String s_crempkname, String s_dr_status)throws Exception;
	//���������������ȸ  2004/08/19 		
	public DayJobReportDTO[] updateSearch(String empId,String day,String orgCd)throws Exception;
	//����񳻿���ȸ
	public TransDetailDTO transSearch(String empId,String reportDt,String sn)throws Exception;
	//���� �������� ���ۼ���ȸ 20040826
	public String[] preReportDtSearch(String empId)throws Exception;
	public boolean updateLoc(String bizAcqCd,String loc)throws Exception;
	//���ΰ��� �ǰߵ��
	public boolean apOpinionRegist(ApOpinionDTO dto)throws Exception;
	//�ǰ� ��ȸ 
	public ApOpinionDTO[] opinionSearch(String empId,String reportDt)throws Exception;	
	//���� ���縦 ���� �����ǿ� ���� �˻�
	public DayJobReportDTO nextRepSearch(String empId,String statusFlag)throws Exception;
	//���Ϻ��� ���� 2005/02/24
	public boolean deleteDailyReport(String empId,String reportDt)throws Exception;
	//���Ϻ��� ��Ƽ ���� 2008/05/26
	public boolean mdeleteDailyReport(String dailyReportPKs)throws Exception;	
	//���� ��ȸ 2005/02/24 
	//������ȸ(TEST)
	public DayJobReportInqDTO[] dayJobSearch2(String empId,String startDt,String endDt,String orgCd,String managerFlag)throws Exception;
	public DayJobReportInqDTO[] dayJobSearch3(String empId,String startDt,String endDt,String orgCd,String managerFlag)throws Exception;
	public DayJobReportInqDTO[] dayJobSearch4(String empId,String startDt,String endDt,String orgCd,String managerFlag,String keyword)throws Exception;

}