package com.wms.beans.dto;

import com.wms.fw.*;
public class ErrProdDTO extends GeneralDTO implements DTO
{
	public String prodNo;			//#제품번호
	public String prodType;			//#제품구분
	public String prodName;			//제품명
	public String bizNo;			//사업번호(FK)
	public String bizName;			//사업명 -->추가
	public String bizAcqCd;			//거래처(FK)
	public String chagName;			//거래처명 -->추가(구)
	public String bizAcqName;		//거래처명 -->추가(신) 2005.04.22일 추가함
	public String orgCd;			//주관조직
	public String orgName;			//주관조직명  -->추가
	public String contNo;			//계약번호
	public String contDt;			//계약일
	public String deliDt;			//납품일
	public long contAmt;				//계약금액
	public int goalProfitRate;		//목표수익율
	public int contMh;				//계약ManHour
	public int goalMh;				//목표ManHour
	public int rsltMh;				//실적ManHour
	public String lasStatusDtm;		//최종수정일시
	public String actOrgCd;			//수행조직
	public String actOrgName;		//수행조직명 -->추가
	public int maTerm;				//유지보수기간
	public int penaltyRate;			//지체상금율
	public String agenda;			//제품개요
	public String prodSyncDtm;		//제품동기화일시
	public String crEmpId;			//작성자사번
	public String crEmpKName;		//작성자이름 -->추가
	public String crEmpOrgCd;		//작성자소속코드 -->추가
	public String crEmpOrgName;		//작성자소속명 -->추가
	public String curEmpId;			//현작업자이름
	public String curEmpKName;		//현작업자이름 -->추가
	public String curEmpOrgName;
	public String pmEmpId;			//PM사번
	public String pmEmpKName;		//PM이름 -->추가
	public String pmEmpOrgName;
	public String saleEmpId;		//영업담당자사번
	public String saleEmpKName;		//작성자이름 -->추가
	public String saleEmpOrgName;
	public String subWrEmpId;		//하부작성자사번
	public String subWrEmpKName;	//하부작성자이름 -->추가
	public String subWrEmpOrgName;
	public String prodStatus;		//제품상태
	public String prcsType;			//공정형태 -->추가
	public String contStartDt;		//계약시작일자
	public String contEndDt;		//계약종료일자
	public String plStartDt;		//계획시작일자
	public String plEndDt;			//계획종료일자
	public String realStartDt;			//실제개시일
	public String realEndDt;			//실제종료일	-->2005.07.21 추가	
	public long expectCost;				//예상비용
	public long expectPnl;				//예상손익
	public String salesAcctCd;			//매출 계정 코드
	public String proFitType;			//수익 형태
	
	public String lastPrcsName;		//최종공정명	 	
	public int plProgDtCnt;                      //계획진척일수
	public float plProgDtRate;			//계획진척율

	public int totCnt=0;				//총레코드수

	public String errCode;						// 오류코드
		
}