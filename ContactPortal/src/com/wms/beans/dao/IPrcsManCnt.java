/*************************************************************
*	�� �� ��  : IPrcsManCnt.java
*	�ۼ�����  : 2004/09/03
*	�� �� ��  : 
*	��    ��  : �����ο� ���� �������̽�
*************************************************************/
package com.wms.beans.dao;
import com.wms.beans.dto.*;

public interface IPrcsManCnt
{
	public boolean add(String prodNo, String prodType, String prcsNo, String[] prcsEmpIds, String[] chkFlags)throws Exception;
	public int update(PrcsManCntDTO dto)throws Exception;
	public int delete(String prodNo, String prodType, String prcsNo, String[] prcsEmpIds, String[] chkFlags)throws Exception;
	public PrcsManCntDTO[] prcsManCntSearchList(String prodNo, String prcsNo, String prodType)throws Exception;
	public PrcsManCntDTO prcsManCntSubSearch(String prodNo, String prcsNo, String prodType, String empId)throws Exception;
	public EmpDTO[] otherPrcsEmpSearch(String prodNo, String prcsNo, String prodType, String empId, String empKName, String orgName)throws Exception;
	public EmpDTO[] empSearch(String prodNo, String prcsNo, String prodType, String empId, String empKName, String orgName, String prodEmpCallYN)throws Exception;	
	public EmpDTO[] prcsEmpSearch(String prodNo, String prcsNo, String prodType)throws Exception;	
}