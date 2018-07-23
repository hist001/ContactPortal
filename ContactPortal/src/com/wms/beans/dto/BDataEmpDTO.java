/*************************************************************
*	파 일 명  : BDataEmpDTO.java
*	작성일자  : 2004/06/22
*	작 성 자  : 
*	내    용  : 직원정보관리 클래스
*************************************************************/
package com.wms.beans.dto;
import com.wms.fw.*;

public class BDataEmpDTO extends GeneralDTO implements DTO 
{	
	public String orgCd;		//조직코드
	public String orgName;		//조직명
	public String empKName;		//성명
	public String empId;		//사번
	public String eGrade;		//직급
	public String wStartDt;		//입사일자
	public String retFlag;		//사직여부 포함 
	public String jobDs;		//대표직무설명
	public String jobAgency;	//직무대행자사번
	public String jobAgencyName;//직무대행자성명
	
	public String highEmpId;	//업무승인자
	public String highEmpKName;	//업무승인자성명
	public String dutyOrgCd1;
	public String dutyOrgCd2;
	public String dutyOrgCd3;
	public String dutyOrgCd4;
	public String dutyOrgName1;
	public String dutyOrgName2;
	public String dutyOrgName3;
	public String dutyOrgName4;
	public String mFlag1;
	public String mFlag2;
	public String mFlag3;
	public String mFlag4;
	public String sn1;
	public String sn2;
	public String sn3;
	public String sn4;
	public String useFlag1;
	public String useFlag2;
	public String useFlag3;
	public String useFlag4;
	public String dutyFullName;
}
