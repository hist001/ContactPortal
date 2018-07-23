/*************************************************************
*	파 일 명  : PrcsManCntDTO.java
*	작성일자  : 2004/09/03
*	작 성 자  : 
*	내    용  : 공정인원 관련 정보 저장
*************************************************************/
package com.wms.beans.dto;
import com.wms.fw.*;
//공정인원
public class PrcsManCntDTO extends GeneralDTO implements DTO 
{
	public String prodNo="";		//#제품번호(FK)
	public String prodName="";		//제품명
	public String prcsNo="";		//#공정번호(FK)
	public String prcsName="";		//정명
	public String prodType="";		//#제품구분
	public String empId="";			//#직원번호
	public String empKName="";   	//#직원이름
	public String empOrgName="";  //직원소속명
	public String jobCd="";			//수행직무
	public String jobName="";		//수행직무명
	public String intoStartDt="";		//투입시작일
	public String intoEndDt="";		//투입완료일	
	public int    intoRate=0;		        //투입률
}
