package com.wms.beans.dto;
import com.wms.fw.*;
public class ApprovalDTO extends GeneralDTO implements DTO
{
	public String sn ;   //직번
	public String EmpId ;//성명
	public String apEmpId; //대결자ID
	public String apEmpKName; //대결자성명
	public String highEmpId ;//상위자ID
	public String highEmpKName;//상위자명
	public String dutyName;//직책명
	public String orgCd;//조직부호
	public String orgName;//조직명
	public String managerFlag;//관리자유무
	public String apEmpFlag ;//승인자유무
	public String useFlag;//사용유무

}