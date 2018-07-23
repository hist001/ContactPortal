/*************************************************************
*	파 일 명  : ICoCode.java
*	작성일자  : 2004/09/03
*	작 성 자  : 
*	내    용  : 공정형태 관련 인터페이스
*************************************************************/
package com.wms.beans.dao;
import com.wms.beans.dto.*;

public interface ICoCode
{
	public CoCodeDTO[] coCodeSearch(String cdType, String cd)throws Exception;
	public CoCodeDTO[] coCodeInquiryList(String cdType,String cdTypeName,String cd,String cdName)throws Exception;
}