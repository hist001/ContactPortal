/*************************************************************
*	파 일 명  : WeekJobPlanDTO.java
*	작성일자  : 2004/06/22
*	작 성 자  : 
*	내    용  : 주간 업무계획에 관한 정보를 담은 클래스
*************************************************************/
package com.wms.beans.dto;
import com.wms.fw.*;
public class WeekJobPlanDTO extends GeneralDTO implements DTO 
{
	public String	weekStartDt;         //주간시작일자
	public String	rEmpId;              //배정자사번
	public String   empKName;            //배정사이름
	public String   confirmDtm;          //확정일시
	public String   coEmpId;             //확정자사번
	public String	coEmpKName;          //확정자성명
	public String	plEmpId;             //계획자사번
	public String	plEmpKName;          //계획자성명
	public String	crEmpId;             //작성자사번
	public String	crEmpKName;          //작성자성명
	public WeekJobPlanDetailDTO[] plans; //주간업무계획확정내역
	
}
