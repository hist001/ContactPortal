/*************************************************************
*	파 일 명  : BilColDtDTO.java
*	작성일자  : 2004/09/03
*	작 성 자  : 
*	내    용  : 청구수금 관련 정보 저장
*************************************************************/
package com.wms.beans.dto;
import com.wms.fw.*;
//청구수금일
public class BilColDtDTO extends GeneralDTO implements DTO 
{
	public String prodNo;		//#제품번호(FK)	
	public String sn;			//#순번
	public String bilColName;	//청구수금명
	public String planDt;		//계획일자
	public String bilDt;		//청구일자
	public String receiptDt;	//입금일자
	public int pressCnt;		//독촉횟수
	public String pressDesc;	//독촉상세내역
}
