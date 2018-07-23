/*************************************************************
*	파 일 명  : IWeekJobPlan.java
*	작성일자  : 2004/06/22
*	작 성 자  : 
*	내    용  : 업무계획 조회 관련 인터페이스
*************************************************************/
package com.wms.beans.dao;
import com.wms.beans.dto.*;

public interface IWeekJobPlan
{
	public boolean save(WeekJobPlanDTO before,WeekJobPlanDTO after)throws Exception;
	//자신이 세운 주간업무계획에서 조회한다.
	public WeekJobPlanDTO[] weekJobPlanSearch(String empId,String day )throws Exception;
	//본인이  배정되어있는 주간업무계획을 조회한다.
	public WeekJobPlanDetailDTO weekJobPlanSubSearch(WeekJobPlanDetailDTO dto,boolean planner)throws Exception;
	//수정하기 위한 하위 계획자들의 주간업무계획을 조회한다.(%주간업무계획에 없으면, 장기업무계획에서 조회)
//	public WeekJobPlanDTO[] weekJobPlanUpdateSearch(String empId,String day)throws Exception;
	public boolean confirm(WeekJobPlanDTO dto,String empId,String empKName, String flag)throws Exception;
	//메인화면에 제품별, 공정별 주간 업무계획을 조회한다.
	public java.util.ArrayList weekJobPlanListSearch(String empId,String day,boolean planner)throws Exception;
}