/*************************************************************
*	파 일 명  : IBasicData.java
*	작성일자  : 2004/06/22
*	작 성 자  : 
*	내    용  : 기본자료관리 인터페이스
*************************************************************/
package com.wms.beans.dao;
import com.wms.beans.dto.*;

public interface IBasicData
{
	public int createBasicData(BasicDataDTO dto) throws Exception;
	public int updateBasicData(BasicDataDTO dto, String[] pks) throws Exception;
	public int deleteBasicData(BasicDataDTO dto, String[] pks) throws Exception;
	public BasicDataDTO selectBasicData(BasicDataDTO dto) throws Exception;
	public BasicDataDTO[] selectBasicDataList(BasicDataDTO dto) throws Exception;
}
