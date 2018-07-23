/*************************************************************
*	파 일 명  : IWkJobPlan.java
*	작성일자  : 2004/06/22
*	작 성 자  : 
*	내    용  : 주간업무계획 확정 관련 인터페이스
*************************************************************/
package com.wms.beans.dao;
import com.wms.beans.dto.*;
import java.util.*;

public interface IWkJobPlan
{
	//공정 검색
    public PrcsDTO wkJobPrcsSearch(String jobNo,String prcsNo) throws Exception;	
	//업무 계획 검색
	public JobPlanDTO wkJobPlanSearch(String planCd) throws Exception;
	//전체 주간 업무계획의 후보및 확정 검색
	public ArrayList wkJobSearch(String empId,String orgCd, String startDt, String endDt) throws Exception;
	//주간 업무계획 확정
	public boolean confirm(WePlanTempDTO[] dto,String flag)throws Exception;
	//일괄 확정
	public String unionConfirm(ArrayList dto)throws Exception;

	//임시 확정 계획 수정
	public boolean updateTemp(WePlanTempDTO[] dto,WePlanTempDTO[] bHdto)throws Exception;

	//public boolean updateTemp(WePlanTempDTO dto,WePlanTempDTO[] bHdto)throws Exception;
	//주간 확정 수정
	public boolean updateWeekly(WePlanTempDTO dto,WePlanTempDTO[] bHdto)throws Exception;
    //임시 확정 계획 조회
	public ArrayList wkJobTempSearch(String plEmpId, String startDt) throws Exception;
	//주간 확정 계획 조회
	public ArrayList wkJobWeeklySearch(String plEmpId, String startDt) throws Exception;

	//한건의 임시 확정계획을 검색
    public WePlanTempDTO[] wkJobTempDetail(String weStartDt,String jobNo,String prcsNo,String planCd) throws Exception;
	//한건의 주간 확정계획을 검색
	public WePlanTempDTO[] wkJobWeeklyDetail(String weekStartDt,String jobNo,String prcsNo,String planCd) throws Exception;


}
