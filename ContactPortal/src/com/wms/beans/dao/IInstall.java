/*************************************************************
*	�� �� ��  : IInstall.java
*	�ۼ�����  : 2004/09/03
*	�� �� ��  : 
*	��    ��  : ��ġ���� ���� �������̽�
*************************************************************/
package com.wms.beans.dao;
import com.wms.beans.dto.*;

public interface IInstall
{
	public boolean createInstall(InstallDTO dto)throws Exception;
	public boolean updateInstall(InstallDTO dto)throws Exception;
	public boolean deleteInstall(InstallDTO dto)throws Exception;
	public InstallDTO searchInstall(InstallDTO dto)throws Exception;
	public InstallDTO[] searchInstallList(String prodType, String prodNo, String prcsNo)throws Exception;	
}