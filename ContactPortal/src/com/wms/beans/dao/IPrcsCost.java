/*************************************************************
*	�� �� ��  : IPrcsCost.java
*	�ۼ�����  : 2004/09/03
*	�� �� ��  : 
*	��    ��  : �������� ���� �������̽�
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