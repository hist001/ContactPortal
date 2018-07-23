/*************************************************************
*	파 일 명  : IBiz.java
*	작성일자  : 2004/09/03
*	작 성 자  : 
*	내    용  : 사업 관련 인터페이스
*************************************************************/
package com.wms.beans.dao;
import com.wms.beans.dto.*;

public interface IBiz
{
	public boolean save(BizDTO dto)throws Exception;
	public BizDTO[] bizInquiryList(String bizNo, String bizName)throws Exception;
}