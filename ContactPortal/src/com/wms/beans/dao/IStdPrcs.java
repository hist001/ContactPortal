/*************************************************************
*	파 일 명  : IStdPrcs.java
*	작성일자  : 2004/09/03
*	작 성 자  : 
*	내    용  : 표준공정 조회 관련 인터페이스
*************************************************************/
package com.wms.beans.dao;
import com.wms.beans.dto.StdPrcsDTO;

public interface IStdPrcs
{
	public StdPrcsDTO[] searchStdPrcs(String prcsCd, String prcsNo)throws Exception;
	public StdPrcsDTO[] stdPrcsInquiryList(String prcsType, String prcsName)throws Exception;
}
