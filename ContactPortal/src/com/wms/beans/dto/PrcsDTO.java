/*************************************************************
*	파 일 명  : PrcsDTO.java
*	작성일자  : 2004/09/03
*	작 성 자  : 
*	내    용  : 공정 관련 정보 저장
*************************************************************/
package com.wms.beans.dto;
import com.wms.fw.*;
//공정+일정
public class PrcsDTO extends GeneralDTO implements DTO 
{
	public String prodNo;		//#제품번호(FK)
	public String prcsNo;		//#공정번호	
	public String prodType;		//공정형태(P:제품, T:Task)
	public String prcsName;		//공정명
	public String prcsDesc;		//공정설명
	public String prcsEFlag;		//공정마감구분
	public String orgCd;			//주수행조직
	public String orgName;		//주수행조직명
	public String prePrcs;		//선행공정
	public String prePrcsName;	//선행공정명
	public String plStartDt;		//계획개시일
	public String plEndDt;		//계획완료일
	public int 	    plMh;			//계획ManHour
	public String prcStatus;		//현공정상태
	public String prcsOrgCd;		//현수행조직
	public String prcsOrgName;	//현수행조직명
	public String realStartDt;		//실재개시일
	public String realEndDt;		//실재완료일
	public int      realMh;			//실재ManHour
    	public String lastWePlDt;		//최종주간업무확정일자
    	public String chgFlag;		//변경유무

    //조회시 필요한 필드
    	public String prodName;		//제품명
	public String plEmpId;		//계획자사번
	public String plEmpKName;	//계획자성명
    	public String jobTitle;		//직무명

	public PrcsCostDTO[] prcsCostDTOs;	//공정원가
	public PrcsManCntDTO[]	prcsManCntDTOs;	//공정인원
}
