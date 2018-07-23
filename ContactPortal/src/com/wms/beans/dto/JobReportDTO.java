/*************************************************************
*	파 일 명  : JobReportDTO.java
*	작성일자  : 2004/06/22
*	작 성 자  : 
*	내    용  : 일일업무보고의 업무항목 정보를 담은 클래스
*************************************************************/
package com.wms.beans.dto;

public class JobReportDTO extends JobDTO 
{
	public String	delFlag;
	public String   reportDt;//보고일자
	public String   reEmpId; //보고자사번
	public String   sn;      //사번
	public int	    mh;
	public int	    hh;
	public int	    mm;
	public String	weekPlanCd; //주간업무계획코드
	public String	actFlag; //직접간접업무구분 D(직접) I(간접)
	public String	transFlag;//교통비 내역유무
	public TransDetailDTO transDetailDTO;
	
	public String jobView;
	public String prcsNoView;
}
