/*************************************************************
*	파 일 명  : IDayJob.java
*	작성일자  : 2004/06/22
*	작 성 자  : 
*	내    용  : 업무보고 관련 인터페이스
*************************************************************/
package com.wms.beans.dao;
import com.wms.beans.dto.*;

public interface IDayJob
{
	//업무 보고 저장 
	public boolean save(DayJobReportDTO before,DayJobReportDTO after,String flag, String foodFlag, String foodFlag2, String orgNo)throws Exception;
	//사번,날자 조건으로 업무 보고 조회
	public DayJobReportDTO dayJobSearch(String empId,String day )throws Exception;
    //업무보고 조회
	public DayJobReportDTO[] dayJobSubSearch(String empId,String day,String orgCd,int mode)throws Exception;
	//승인 처리
	public boolean confirm(DayJobReportDTO dto)throws Exception;
	//합동 업무 보고 저장
	public String unionSave(DayJobReportDTO dto,AsdEmpDTO[] empList)throws Exception;
	//업무보고 조회
	public DayJobReportDTO[] dayJobSubSearch(String empId,String startDt,String endDt,String orgCd,int mode)throws Exception;	
	//자가용 처리를 위한 조회
	public String searchCarPrice(String empId)throws Exception;
	//업무보고승인시 2004/08/12 	
	public DayJobReportDTO[] confirmSearch(String empId,String orgCd,String s_crempkname, String s_dr_status)throws Exception;
	//업무보고수정시조회  2004/08/19 		
	public DayJobReportDTO[] updateSearch(String empId,String day,String orgCd)throws Exception;
	//교통비내역조회
	public TransDetailDTO transSearch(String empId,String reportDt,String sn)throws Exception;
	//전일 업무보고 미작성조회 20040826
	public String[] preReportDtSearch(String empId)throws Exception;
	public boolean updateLoc(String bizAcqCd,String loc)throws Exception;
	//승인관련 의견등록
	public boolean apOpinionRegist(ApOpinionDTO dto)throws Exception;
	//의견 조회 
	public ApOpinionDTO[] opinionSearch(String empId,String reportDt)throws Exception;	
	//연속 결재를 위한 다음건에 대한 검색
	public DayJobReportDTO nextRepSearch(String empId,String statusFlag)throws Exception;
	//일일보고 삭제 2005/02/24
	public boolean deleteDailyReport(String empId,String reportDt)throws Exception;
	//일일보고 멀티 삭제 2008/05/26
	public boolean mdeleteDailyReport(String dailyReportPKs)throws Exception;	
	//공정 조회 2005/02/24 
	//업무조회(TEST)
	public DayJobReportInqDTO[] dayJobSearch2(String empId,String startDt,String endDt,String orgCd,String managerFlag)throws Exception;
	public DayJobReportInqDTO[] dayJobSearch3(String empId,String startDt,String endDt,String orgCd,String managerFlag)throws Exception;
	public DayJobReportInqDTO[] dayJobSearch4(String empId,String startDt,String endDt,String orgCd,String managerFlag,String keyword)throws Exception;

}