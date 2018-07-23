/*************************************************************
*	파 일 명  : StdPrcsDTO.java
*	작성일자  : 2004/09/03
*	작 성 자  : 
*	내    용  : 표준공정 관련 정보 저장
*************************************************************/
package com.wms.beans.dto;
import com.wms.fw.*;
//표준공정
public class StdPrcsDTO extends GeneralDTO implements DTO 
{
	public String prcsCd;		//#공정형태부호
	public String prcsNo;		//#표준공정번호	
	public String prcsName;		//표준공정명
	public int prcsRate;		//일정진척율
	public int reqTerm;			//소요기간
	public int mhRate;			//MH율
	
	public int totCnt=0;  //총 레코드 수 (페이징)
}
