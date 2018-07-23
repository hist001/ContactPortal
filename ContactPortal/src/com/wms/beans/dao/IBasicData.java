/*************************************************************
*	�� �� ��  : IBasicData.java
*	�ۼ�����  : 2004/06/22
*	�� �� ��  : 
*	��    ��  : �⺻�ڷ���� �������̽�
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
