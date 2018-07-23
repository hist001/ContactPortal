/*************************************************************
*	파 일 명  : JobDTO.java
*	작성일자  : 2004/06/22
*	작 성 자  : 
*	내    용  : 일일업무보고 관련 DTO의 상위 클래스
*************************************************************/
package com.wms.beans.dto;
import com.wms.fw.*;

public class JobDTO extends GeneralDTO implements DTO 
{
	public String	jobNo;     //제품코드
	public String	jobName;   //제품명
	public String   prcsNo;    //공정코드
	public String   prcsName;  //공정명
	public String	jobTitle;  //일일보고항목명   
	public String	jobDetail; //일일보고항목내역
	public String	startDt;   //시작일
	public String	endDt;     //종료일    
}
