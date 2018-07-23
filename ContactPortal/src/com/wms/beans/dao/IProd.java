/*************************************************************
*	파 일 명  : IProd.java
*	작성일자  : 2004/09/03
*	작 성 자  : 
*	내    용  : 제품 관련 인터페이스
*************************************************************/
package com.wms.beans.dao;
import com.wms.beans.dto.ProdDTO;
import com.wms.beans.dto.AccoDTO;
import com.wms.beans.dto.PrcsDTO;
import com.wms.beans.dto.*;
import com.wms.beans.dto.UserInfo;

public interface IProd
{
	public int[] updateProd(ProdDTO dto,String prcsChangeYN)throws Exception;
	public ProdDTO searchProd(String prodNo, String prodType)throws Exception;
	public ProdDTO[] searchProd(ProdDTO dto)throws Exception;
	//제품검색(공정메뉴)
	public ProdDTO[] searchProd(String empId, String prodType, String prodNo, String prodName, UserInfo user)  throws Exception;
	//제품검색--prcsType을 체크한다.
	public ProdDTO[] searchProd(String empId, String prodType, String orgProdNo, String outerProdNo, String prodName, String prcsType, UserInfo user)  throws Exception;
	public PrcsDTO[] searchDetailPrcs(PrcsDTO dto)throws Exception;
	public boolean createProd(ProdDTO dto,String accoItems) throws Exception;
	//작성자로 검색
	public ProdDTO[] searchProdList(String empId, String prodType, String prodName, UserInfo user, String searchType, String bizType, String prodStatus)throws Exception;
	public ProdDTO[] searchProdDetail(String empId, String prodNo, String prodType)throws Exception;
	public boolean deleteProd(ProdDTO dto) throws Exception;
	//제품별 기간별 조회
	public ProdDTO[] prodInquiryList(String prodType, String startDt,String endDt,String bizNo,String prodStatus, String prodNo, String prodName,int pageCnt,int pageSize)  throws Exception;
	//제품별 기간별 조회 - cnt값
	public int prodInquiryListCnt(String prodType, String startDt,String endDt,String bizNo,String prodStatus, String prodNo, String prodName)  throws Exception;	
	//제품별 투입인력 조회
	public PrcsManCntDTO[] prodEmpSearch(String name,String id)throws Exception;
}
