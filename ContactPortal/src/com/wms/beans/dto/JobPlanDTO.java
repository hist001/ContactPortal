/*************************************************************
*	파 일 명     : JobPlanDTO.java
*	작성일자     : 2004/06/22
*   최종수정일자 : 2005/04/06 업무주기 정보 추가
*	작 성 자     : 조원호
*	내    용     : 업무계획 관련 정보를 담는 클래스
*************************************************************/
package com.wms.beans.dto;

import java.util.ArrayList;

public class JobPlanDTO extends JobDTO  
{
	public String   planCd;			//계획번호
	public int      planMh;			//계획시간
	public String   plEmpId;		//계획자사번
	public String   plEmpKName;		//계획자성명
	public String   orgCd;			//조직부호
	public String   lastCrEmpId;	//최종작성자사번
	public String   lastCrEmpKName; //최종작성자성명
	public String   lastWePlDt;		//최종적용된일자
	public String   actFlag;		//직접(D),간접(I)구분
	public String   prodSyncDtm;
    public String   divFlag;        //업무주기구분(매일==D,매주==W,매월=M) 2005/04/06
	//public ArrayList values;
	public AsdEmpDTO[] values;      //배정된 직원들
}
