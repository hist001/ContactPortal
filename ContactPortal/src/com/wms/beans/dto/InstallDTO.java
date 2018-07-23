/*************************************************************
*	파 일 명  : InstallDTO.java
*	작성일자  : 2004/09/03
*	작 성 자  : 
*	내    용  : 설치내역 관련 정보 저장
*************************************************************/
package com.wms.beans.dto;
import com.wms.fw.*;
//설치내역
public class InstallDTO extends GeneralDTO implements DTO 
{
	public String prodNo;			//#제품번호(FK)
	public String prodType;			//#제품타입
	public String prcsNo;			//#공정번호
	public String sno;				//#순번
	public String prodName;			//제품명
	public String prcsName;			//공정명
	public String instType;			//설치종류
	public String itemNo;			//품목번호
	public String itemName;			//품명
	public int instCnt;				//설치수량
	public String instSno;			//설치SN
	public int contUPrice;			//계약단가
	public int uPrice;				//단가
	public String instPlanDt;		//설치계획일자
	public String instDt;			//설치일자
	public String itemDesc;			//설치내역세부사항
}
