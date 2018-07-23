/*************************************************************
*	파 일 명  : IBDataEmp.java
*	작성일자  : 2004/06/22
*	작 성 자  : 
*	내    용  : 직원정보관리 인터페이스
*************************************************************/
package com.wms.beans.dao;
import com.wms.beans.dto.*;

public interface IBDataEmp
{
	//직원기본정보
	public BDataEmpDTO[] selectBDataEmpList(BDataEmpDTO dto) throws Exception;
	public BDataEmpDTO selectBDataEmp(String empId) throws Exception;
	public boolean updateBDataEmp(BDataEmpDTO dto)throws Exception;
	public boolean createBDataEmp(BDataEmpDTO dto)throws Exception;
	//직원근태정보
	public DayJobReportDTO[] selectDiliList(String highEmpId, String role,String reportDtFr,String reportDtTo,String s_crEmpId, String s_apEmpId) throws Exception;
	public DiliDTO selectDili(String reportDt, String reEmpId)throws Exception;
	public boolean saveDili(DiliDTO dto, String userEmpId)throws Exception;

}
