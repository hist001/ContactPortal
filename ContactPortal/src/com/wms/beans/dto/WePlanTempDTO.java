/*************************************************************
*	파 일 명  : WePlanTempDTO.java
*	작성일자  : 2004/06/22
*	작 성 자  : 
*	내    용  : 임시확정된 업무계획 관련 정보를 담는 클래스
*************************************************************/
package com.wms.beans.dto;

public class WePlanTempDTO extends JobDTO 
{
	public String	weStartDt;   //주간시작일자
	public String	reEmpId;     //배정자사번
	public int      mh;          //menhour
	public int      hh;          //시간
	public int      mm;          //분
	public String   delFlag;     //삭제유무
	public String   actFlag;     //직간접유무
	public String   planCd;      //기본계획번호
	public String   confirmDtm;  //확정일시
	public String   coEmpId;     //확정자사번
	public String   plEmpId;     //계획자사번
	public String   crEmpId;     //작성자사번

	//조회시 필요 필드
	public String   reEmpKName;  //작성자성명
	public String   plEmpKName;  //계획자성명

	
}