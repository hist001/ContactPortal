/*************************************************************
*	파 일 명  : TeamJobDTO.java
*	작성일자  : 2004/09/03
*	작 성 자  : 
*	내    용  : 팀직무 정보 저장
*************************************************************/
package com.wms.beans.dto;
import com.wms.fw.*;
public class TeamJobDTO extends GeneralDTO implements DTO
{
	public String orgCd;   
	public String orgName ;
	public String jobCd; //직무코드
	public String jobName; //직무명
	
	public String bizNo;
	public String bizName;
	public String actDs;	
	public String useFlag;
}