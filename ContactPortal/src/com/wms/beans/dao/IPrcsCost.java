/*************************************************************
*	파 일 명  : IPrcsCost.java
*	작성일자  : 2004/09/03
*	작 성 자  : 
*	내    용  : 공정원가 관련 인터페이스
*************************************************************/
package com.wms.beans.dao;
import com.wms.beans.dto.*;

public interface IPrcsCost
{
	public boolean add(String prodNo, String prodType, String prcsNo, String[] accoItems,String[] chkFlags)throws Exception;
	public int update(String prodNo, String prodType, String prcsNo,String[] accoItems,String[] contAmts,String[] goalAmts,String[] chkFlags)throws Exception;
	public int delete(String prodNo, String prodType, String prcsNo, String[] accoItems,String[] chkFlags)throws Exception;
	public PrcsCostDTO[] prcsCostSearch(String prodNo, String prodType, String prcsNo)throws Exception;
	public PrcsCostDTO prcsCostSubSearch(String prodNo,  String prodType, String prcsNo, String accoItem)throws Exception;
}