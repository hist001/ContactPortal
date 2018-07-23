/*************************************************************
*	파 일 명  : OrgDTO.java
*	작성일자  : 2004/06/22
*	작 성 자  : 
*	내    용  : 조직조회 관련 정보를 담는 클래스
*************************************************************/
package com.wms.beans.dto;
import com.wms.fw.*;

public class OrgDTO extends GeneralDTO implements DTO 
{
	public String	orgCd;   //조직부호
	public String	orgName; //조직명
	public String    startDt;  //개시일시
	public String    endDt;//종료이시
	public String	bizNo;//사업
	public String	labCostType;//노무원가구분
}
