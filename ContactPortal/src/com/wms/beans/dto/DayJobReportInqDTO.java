/*************************************************************
*	파 일 명  : DayJobReportInqDTO.java
*	작성일자  : 2004/06/22
*	작 성 자  : 
*	내    용  : 일일업무보고 조회에 관한 정보를 담은 클래스
*************************************************************/
package com.wms.beans.dto;
import com.wms.fw.*;
public class DayJobReportInqDTO extends GeneralDTO implements DTO 
{
	public String reportDt;//작성일자
	public String reEmpId; //보고자사번
	public String reEmpKName; //보고자이름
	public String createDtm;//초기작성일시
	public String lastDtm;  //최종작성일시
	public String crEmpId;  //작성자사번
	public String crEmpKName; //작성자이름
	public String plEmpId;   //계획자사번
	public String plEmpKName; //계획자이름
	public String apEmpId;//승인자사번
	public String apEmpKName;//승인자성명
	public String apDtm;//승인일시
	public String statusFlag; //상태구분
	public String statusName;
	public String managerFlag; //관리자구분
	public String flag;//
	public String apFlag;  //승인의견 add 200608.03

	//조회 
	public String transStatus;
	public String totalMh;
	public JobReportDTO[] plans;
    public String org_no;
    public String orgName;	

}
