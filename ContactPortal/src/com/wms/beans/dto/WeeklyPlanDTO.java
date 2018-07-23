/*************************************************************
*	파 일 명  : WeeklyPlanDTO.java
*	작성일자  : 2004/06/22
*	작 성 자  : 
*	내    용  : 확정된 주간업무계획의 정보를 담는 클래스
*************************************************************/
package com.wms.beans.dto;
import com.wms.fw.*;

public class WeeklyPlanDTO extends GeneralDTO 
{
	public String	weekStartDt;    //주간시작일자
	public String	reEmpId;        //배정자사번

	public String   confirmDtm;     //확정일시
	public String   coEmpId;        //확정자사번
	public String   plEmpId;        //계획자사번
	public String   crEmpId;        //작성자사번

    //조회시 필요 필드
	public String plEmpKName;       //계획자성명

	public WeeklyPlanDetailDTO[] weeklyPlanDetailDTOs;//업무계획들

	
}