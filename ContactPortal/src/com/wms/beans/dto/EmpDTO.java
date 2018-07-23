package com.wms.beans.dto;
import com.wms.fw.*;
public class EmpDTO extends GeneralDTO implements DTO
{
	public String empId ;     //직번
	public String empKName ;  //성명
	public String empOrgCd;   //조직부호
	public String empOrgName; //조직이름
	public String eType ;     //직원구분
	public String wStartDt ;  //근무개시일
	public String wEndDt ;    //근무만료일
	public String eGroup;     //직군
    public String eGrade;     //직급
	public String jobDs;      //대표직무설명
	public String jobAgency;  //직무대행자
	public String mainJobCd;  //대표직무번호
	public String dutyFlag;   //직책유무
	public String plEmpIdFlag;//계획자유무
	public String retFlag ;   //사직유무
	
	public String role;       //역할
	public String drType;       
	
	public int totCnt=0;      // 총 레코드 수 (페이징)
	
	public ApprovalDTO[] approvals;
	public EmpJobDTO[] empjobs;
	public OrgCdDTO[]     orgCds;//View에서 조회 할 수 있도록 등록된 그룹들
}