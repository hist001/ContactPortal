/*************************************************************
*	파 일 명  : WeekJobPlanDetailDTO.java
*	작성일자  : 2004/06/22
*	작 성 자  : 
*	내    용  : 주간업무계획중 하나의 업무계획에
*               관한 정보를 담은 클래스
*************************************************************/
package com.wms.beans.dto;
import com.wms.fw.*;
public class WeekJobPlanDetailDTO extends GeneralDTO implements DTO 
{
	public String	weekStartDt;        //주간시작일자
	public String	reEmpId;            //사번
	public String    sn;                //순번
	public String	jobNo;              //제품코드 또는 직무코드
	public String	jobName;            //제품명 또는 직무명
	public String    prcsNo;            //공정코드 또는 사업코드
	public String    prcsName;          //공정명 또는 사업명
	public String	startDt;            //시작일자
	public String	endDt;              //종료일자
	public String	jobTitle;           //일일보고항목명
	public String	jobDetail;          //일일보고항목내역
	public int	        mh;             //ManHour
	public int	        hh;             //작업시간량
	public int	        mm;             //작업시간의 분량
	public String	delFlag;            //삭제유무구분
	public String	actFlag;            //직접간접업무구분
	public String    planCd;            //장기업무계획코드
	public EmpDTO[] assignedEmps;       //배정자 배열
	public WeekJobPlanDTO[] plans;      
}
