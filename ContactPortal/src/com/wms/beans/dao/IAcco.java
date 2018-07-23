/*************************************************************
*	파 일 명  : IAcco
.java
*	작성일자  : 2004/09/03
*	작 성 자  : 
*	내    용  : 계정과목 관련 인터페이스
*************************************************************/
package com.wms.beans.dao;
import com.wms.beans.dto.AccoDTO;
public interface IAcco
{
	public AccoDTO[] searchAccoList(String accoItem, String accoKorName, String prodNo, String prodType, String prcsNo)throws Exception;
}
