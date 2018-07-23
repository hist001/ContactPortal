/*************************************************************
*	파 일 명  : DayErrDTO.java
*	작성일자  : 2004/06/22
*	작 성 자  : 
*	내    용  : 에러보고 관련 정보를 담는 클래스
*************************************************************/
package com.wms.beans.dto;
import com.wms.fw.*;
public class DayErrDTO extends GeneralDTO 
{
	public String reEmpId; //보고자사번
	public String reportDt;//작성일자
	public String reEmpKName; //보고자이름
	public String apEmpId;//승인자사번
	public String apEmpKName;//승인자성명
	public String apDtm;//승인일시
	public String reWriteDtm;//재작성일시
	public String statusFlag; //상태구분
	public String log;
	public String orgCd;
	public String orgName;
	public String totalMh;
	public String org_no;
	
	public int      totCnt=0; //페이징을 위한 총 레코드 수
	public String statusName;
    	//Flag N 미작성
	//Flag Y 처리
	//Flag R 승인자의 재작성 요구상태

}
