/*************************************************************
*	파 일 명  : DiliDTO.java
*	작성일자  : 2004/06/22
*	작 성 자  : 
*	내    용  : 당일근태관리 정보 클래스
*************************************************************/
package com.wms.beans.dto;
import com.wms.fw.*;

public class DiliDTO extends GeneralDTO implements DTO 
{	
	public String reportDt;	
	public String reEmpId;	
	public String reEmpKName;
	public String apEmpId;		//dailyreport table의 업무승인자
	public String apEmpKName;		
	public String plEmpId;		//dailyreport table의 업무계획자 040819 부로 쓰지 않음
	public String plEmpKName;		
	public String statusFlag;	//근태코드값
	public String cgDtm;		//sysdate, 변경일시
	public String cgDs;			//변경사유

	public String statusName;	//상태코드값이름
}
