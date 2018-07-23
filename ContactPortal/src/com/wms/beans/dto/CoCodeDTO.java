/*************************************************************
*	파 일 명  : CoCodeDTO.java
*	작성일자  : 2004/09/03
*	작 성 자  : 
*	내    용  : 공정형태 관련 정보 저장
*************************************************************/
package com.wms.beans.dto;
import com.wms.fw.*;

public class CoCodeDTO extends GeneralDTO implements DTO 
{
	public String cdType;		//#코드종류
	public String cd;			//#코드	
	public String cdName;		//코드명
	public String cdDs;			//코드설명
	
	public int       totCnt=0;              //총레코드 수(페이징)
	
	public String cdTypeName;
}
