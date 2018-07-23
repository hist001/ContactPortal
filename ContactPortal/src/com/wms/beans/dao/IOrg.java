/*************************************************************
*	파 일 명  : IOrg.java
*	작성일자  : 2004/06/22
*	작 성 자  : 
*	내    용  : 조직 조회 관련 인터페이스
*************************************************************/
package com.wms.beans.dao;
import com.wms.beans.dto.OrgDTO;
public interface IOrg
{
	public OrgDTO[] searchTeamOrg(String managerFlag, String userOrgCd)throws Exception;
	public OrgDTO[] searchGroupOrg(String teamCd, String managerFlag, String userOrgCd)throws Exception;
	public OrgDTO[] searchOrg(String orgCd, String orgName)throws Exception;
}
