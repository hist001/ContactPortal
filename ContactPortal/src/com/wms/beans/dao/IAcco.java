/*************************************************************
*	�� �� ��  : IAcco
.java
*	�ۼ�����  : 2004/09/03
*	�� �� ��  : 
*	��    ��  : �������� ���� �������̽�
*************************************************************/
package com.wms.beans.dao;
import com.wms.beans.dto.AccoDTO;
public interface IAcco
{
	public AccoDTO[] searchAccoList(String accoItem, String accoKorName, String prodNo, String prodType, String prcsNo)throws Exception;
}
