/*************************************************************
*	파 일 명  : IHistJob.java
*	작성일자  : 2004/06/22
*	작 성 자  : 
*	내    용  : 직무 관련 인터페이스
*************************************************************/
package com.wms.beans.dao;
import com.wms.beans.dto.HistJobDTO;
public interface IHistJob
{   //해당 직원의 직무가 승인완료된 것을 검색한다.
	public HistJobDTO[] searchEmpJob(String empNo)throws Exception;
    //기본 직무를 검색한다.
	public HistJobDTO[] searchHistJob(HistJobDTO dto)throws Exception;
	//전사직무목록을 조회한다
	public HistJobDTO[] histJobInquiryList(String jobCd, String jobName)throws Exception;
	//전사직무목록을 조회한다--페이징
	public HistJobDTO[] histJobInquiryList(String jobCd, String jobName, int pageNum, int pageSize)throws Exception;
}
