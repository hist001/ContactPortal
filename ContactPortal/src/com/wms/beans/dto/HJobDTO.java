package com.wms.beans.dto;
import com.wms.fw.*;
public class HJobDTO extends GeneralDTO implements DTO
{
	public String empId;			//사번 (현재로그인한 사람)
	public String empKName;			//로그인한 사람 이름
	public String orgName;
	public String revisionNo;		//직무표 Revision
	public String jobDs;			//대표직무 emp table의 jobDs 와 동일함
	public String jobAgency;
	public String jobAgencyName;
	public String mkEmpId;		//작성자사번
	public String mkEmpKName;		//작성자이름
	public String lastDtm;			//최종저장일시
	public String apEmpId;			//승인자사번
	public String apEmpKName;		//승인자이름
	public String confirmDtm;		//승인일시
	public String statusFlag;			//현재직무표상태 JA0:작성중 JB0:작성완료 JC0:승인완료

	//view 처리를 위해
	public String statusName;       //상태값이름
	
	//public String coWorkEmpYN;		//합동보고여부

	public HJobCdDTO[] hJobCdDTOs;	//개인직무코드LISTs

	public CoJobDTO[] coJobDTOs;   //합동직무자들

	//public String calls;
}