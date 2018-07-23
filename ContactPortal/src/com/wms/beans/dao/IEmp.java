/*************************************************************
*	파 일 명  : IEmp.java
*	작성일자  : 2004/09/03
*	작 성 자  : 
*	내    용  : 직원정보 조회 관련 인터페이스
*************************************************************/
package com.wms.beans.dao;
import com.wms.beans.dto.*;

public interface IEmp
{
	public EmpDTO[] empInquiryList(String orgCd, String orgName, String empId, String empKName, String jobCd, String jobName)throws Exception;
	//직원정보조회-페이징
	public EmpDTO[] empInquiryList(String orgCd, String orgName, String empId, String empKName, String jobCd, String jobName,int pageNum,int pageSize)throws Exception;
	public EmpDTO empJobInquiryList(String empId)throws Exception;
	public EmpDTO empApprovalInquiryList(String empId)throws Exception;
}