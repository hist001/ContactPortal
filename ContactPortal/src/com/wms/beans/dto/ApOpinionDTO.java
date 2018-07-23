/*************************************************************
*	파 일 명  : ApOpinionDTO.java
*	작성일자  : 2004/09/15
*	작 성 자  : 
*	내    용  : 승인 관련 의견 정보를 담는 클래스
*************************************************************/
package com.wms.beans.dto;
import com.wms.fw.*;
public class ApOpinionDTO extends GeneralDTO 
{

	public String reEmpId;    //보고자사번
	public String reportDt;   //작성일자
	public String sn;         //순번
	public String apEmpId;    //승인자사번
	public String createDtm;  //작성일시
	public String apFlag;     
	//apFlag=> 승인의견,설명요 구분값
	//A==승인의견
	//E==설명요

	public String apContents; //의견 내용

    //view를 처리하기 위한 변수
	public String reEmpKName; //보고자이름
	public String apEmpKName; //승인자성명
}
