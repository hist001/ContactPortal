/*************************************************************
*	파 일 명  : BizRegDTO.java
*	작성일자  : 2004/09/03
*	작 성 자  : 
*	내    용  : 사업자 관련 정보 저장
*************************************************************/
package com.wms.beans.dto;
import com.wms.fw.*;
//사업자번호
public class BizRegDTO extends GeneralDTO implements DTO 
{
	public String bizRegNo;		//#사업자번호
	public String bizAcqCd;		//거래처
	public String postCd;		//우편번호
	public String city;			//시도
	public String addr;			//주소
	public String ceo;			//대표자
}
