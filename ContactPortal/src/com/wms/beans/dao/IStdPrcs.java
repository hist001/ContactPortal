/*************************************************************
*	�� �� ��  : IStdPrcs.java
*	�ۼ�����  : 2004/09/03
*	�� �� ��  : 
*	��    ��  : ǥ�ذ��� ��ȸ ���� �������̽�
*************************************************************/
package com.wms.beans.dao;
import com.wms.beans.dto.StdPrcsDTO;

public interface IStdPrcs
{
	public StdPrcsDTO[] searchStdPrcs(String prcsCd, String prcsNo)throws Exception;
	public StdPrcsDTO[] stdPrcsInquiryList(String prcsType, String prcsName)throws Exception;
}
