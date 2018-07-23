/*************************************************************
*	파 일 명  : WeeklyPlanDetailDTO.java
*	작성일자  : 2004/06/22
*	작 성 자  : 
*	내    용  : 확정된 주간업무계획중 업무계획 하나의 
*               정보를 담는 클래스
*************************************************************/
package com.wms.beans.dto;

public class WeeklyPlanDetailDTO extends JobDTO 
{
	public String   sn       ;     //순번
	public String	weekStartDt;   //주간시작일자
	public String	reEmpId;       //배정자사번
	public int      mh;            //menhour
	public int      hh;            //시간
	public int      mm;            //분
	public String   delFlag;       //삭제유무
	public String   actFlag;       //직간접유무
	public String   planCd;        //기본계획번호

	//조회시 필요
	public String   reEmpKName;    //작성자성명
}