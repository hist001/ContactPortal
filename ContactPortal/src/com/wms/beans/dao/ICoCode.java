/*************************************************************
*	�� �� ��  : ICoCode.java
*	�ۼ�����  : 2004/09/03
*	�� �� ��  : 
*	��    ��  : �������� ���� �������̽�
*************************************************************/
package com.wms.beans.dao;
import com.wms.beans.dto.*;

public interface ICoCode
{
	public CoCodeDTO[] coCodeSearch(String cdType, String cd)throws Exception;
	public CoCodeDTO[] coCodeInquiryList(String cdType,String cdTypeName,String cd,String cdName)throws Exception;
}