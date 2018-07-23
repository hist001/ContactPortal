/*************************************************************
*	파 일 명  : AccoDTO.java
*	작성일자  : 2004/09/03
*	작 성 자  : 
*	내    용  : 계정과목 관련 정보 저장
*************************************************************/
package com.wms.beans.dto;
import com.wms.fw.*;
//계정과목
public class AccoDTO extends GeneralDTO implements DTO 
{
	public String accoItem;			//#계정과목
	public String korName;			//한글명칭
	public String engname;			//영문명칭
	public String highAccoItem;		//상위계정과목
	public String cdFlag;			//차변대변구분
	public String gAccoItem;			//그룹계정과목
	public String maEnable;			//관리가능관리불능구분
	public String diCostType;			//직접간접구분
	public String labCostType;		//제조GNA영업외구분
	public String consultType;		//회계품의구분
	public String monthlyCalc;		//월할계산
	public String fixVarCost;			//고정변동비

	public PrcsCostDTO[] prcsCostDTOs;		//공정원가
}
