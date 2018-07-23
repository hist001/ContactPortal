/*************************************************************
*	파 일 명  : IPrcs.java
*	작성일자  : 2004/09/03
*	작 성 자  : 
*	내    용  : 공정 관련 인터페이스
*************************************************************/
package com.wms.beans.dao;
import com.wms.beans.dto.*;

public interface IPrcs
{
	public int add(PrcsDTO dto)throws Exception;
	public int addCopyPrcs(String prodNo, String prcsNo, String prodType, String nPrcsNo, String nPrcsName) throws Exception;
//	public int addCopyPrcs2(String prodNo, String prodType,String copyPrcsNo, String[] copiedPrcsNo, String[] chkFlags)throws Exception;
	public int updatePrcs(PrcsDTO dto)throws Exception;
	public int updateCopyPrcs2(String prodNo, String prodType,String copyPrcsNo, String[] copiedPrcsNo, String[] chkFlags)throws Exception;
	public int deletePrcs(String prodNo, String prcsNo, String prodType)throws Exception;	
	public int deleteCopyPrcs2(String prodNo, String prodType, String[] copiedPrcsNo, String[] chkFlags)throws Exception;
	public boolean prcsCdSearch(String prodNo, String prodType, String prcsNo)throws Exception;
	public PrcsDTO prcsSubSearch(String prodNo, String prcsNo, String prodType)throws Exception;
	//admin 기능 추가에 따른 role 변수 추가
	public PrcsDTO[] prcsSearch(String empId, String prodNo,String prodType, String role)throws Exception;
	public PrcsDTO[] copiedPrcsSearch(String prodNo,String prodType)throws Exception;
	public PrcsDTO[] copyPrcsSearch(String prodNo,String prodType, String prcsNo)throws Exception;
	public int copyOuterPrcs(String copiedProdNo, String prodNo, String prodType,String[] copyPrcsNo, String[] chkFlags, String empOrgCd)throws Exception;
}