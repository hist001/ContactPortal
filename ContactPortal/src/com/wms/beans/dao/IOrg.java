/*************************************************************
*	�� �� ��  : IOrg.java
*	�ۼ�����  : 2004/06/22
*	�� �� ��  : 
*	��    ��  : ���� ��ȸ ���� �������̽�
*************************************************************/
package com.wms.beans.dao;
import com.wms.beans.dto.OrgDTO;
public interface IOrg
{
	public OrgDTO[] searchTeamOrg(String managerFlag, String userOrgCd)throws Exception;
	public OrgDTO[] searchGroupOrg(String teamCd, String managerFlag, String userOrgCd)throws Exception;
	public OrgDTO[] searchOrg(String orgCd, String orgName)throws Exception;
}
