/*************************************************************
*	�� �� ��  : IBiz.java
*	�ۼ�����  : 2004/09/03
*	�� �� ��  : 
*	��    ��  : ��� ���� �������̽�
*************************************************************/
package com.wms.beans.dao;
import com.wms.beans.dto.*;

public interface IBiz
{
	public boolean save(BizDTO dto)throws Exception;
	public BizDTO[] bizInquiryList(String bizNo, String bizName)throws Exception;
}