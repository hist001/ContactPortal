/*************************************************************
*	파 일 명  : PrcsCostDTO.java
*	작성일자  : 2004/09/03
*	작 성 자  : 
*	내    용  : 공정원가 관련 정보 저장
*************************************************************/
package com.wms.beans.dto;
import com.wms.fw.*;
//공정원가
public class PrcsCostDTO extends GeneralDTO implements DTO 
{
	public String prodNo;		//#제품번호(FK)
	public String prodType;		//#제품번호(FK)
	public String prcsNo;		//#공정번호(FK)
	public String accoItem;		//#계정과목(FK)
	public String accoKorName;     //계정과목명
	public int contAmt;			//계약금액
	public int goalAmt;			//목표금액
	public int rsltAmt;			//실적액
	public String consultNo;	//품의번호
}
