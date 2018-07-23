/*************************************************************
*	파 일 명  : IInstall.java
*	작성일자  : 2004/09/03
*	작 성 자  : 
*	내    용  : 설치내역 관련 인터페이스
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