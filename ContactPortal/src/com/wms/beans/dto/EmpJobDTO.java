/*************************************************************
*	파 일 명  : EmpJobDTO.java
*	작성일자  : 2004/09/03
*	작 성 자  : 
*	내    용  : 개인별직무 관련 정보 저장
*************************************************************/
package com.wms.beans.dto;
import com.wms.fw.*;
public class EmpJobDTO extends GeneralDTO implements DTO
{
	public String empId;   	//직번
	public String empKName ;//성명
	public String jobCd; 		//직무코드
	public String revisionNo;	//개정번호
	public String jobName; 	//직무명
	public String jobTitle; 	//세부직무항목명
	
	public int       yearCnt; // 연간발생건수
	public float    unitUseHM; // 건당소요시간
	public float    yearTotHM; // 연간사용시간

	public String bizNo;
	public String bizName;
	public String actDs;	
}