/*************************************************************
*	파 일 명  : BizDTO.java
*	작성일자  : 2004/09/03
*	작 성 자  : 
*	내    용  : 사업 관련 정보 저장
*************************************************************/
package com.wms.beans.dto;
import com.wms.fw.*;
//사업
public class BizDTO extends GeneralDTO implements DTO 
{
	public String   bizNo;	 //#사업코드
	public String   bizName;	 //사업명
	public String   bizCdType; //관리구분
	public String   prcsType;	 //공정형태
	public String   salOrgCd;	 //주관영업조직
	public String   techOrgCd; //주관기술지원조직
	public String   actOrgCd;	 //주관수행조직
	
	public int         totCnt=0; 	 //총레코드수 (페이징)

	public ProdDTO[] prodDTOs;	//제품들
}
