/*************************************************************
*	파 일 명  : TransDetailDTO.java
*	작성일자  : 2004/06/22
*	작 성 자  : 
*	내    용  : 교통비내역 관련 정보를 담는 클래스
*************************************************************/
package com.wms.beans.dto;
import com.wms.fw.*;
public class TransDetailDTO extends GeneralDTO implements DTO 
{
	public String sn;          //순번
	public String reportDt;    //보고일자
	public String reEmpId;     //보고자사번
	public String bizAcqCd;    //거래처
	public String startTm;     //출발시분
	public String endTm;       //도착시분
	public int carSelKm;       //자가용서울주행거리
	public int carEtcKm;       //자가용수도권주행거리
	public long carEtc;         //자가용주차비/통행료
	public long bus;            //대중교통-버스비
	public long subway;         //대중교통-지하철비
	public long taxi;           //대중교통-택시비
	public long car;           //자가용 합계

	public String chagName;    //거래처명
	public String loc ;        //지역

	
}
