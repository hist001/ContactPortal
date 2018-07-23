/*************************************************************
*	파 일 명  : IDayErr.java
*	작성일자  : 2004/06/22
*	작 성 자  : 
*	내    용  : 에러조치 관련 인터페이스
*************************************************************/
package com.wms.beans.dao;
import com.wms.beans.dto.*;
import com.wms.fw.servlet.Box;
public interface IDayErr
{
	//업무보고조회의 상태값 조회
	public StatusDTO[] searchStatus() throws Exception;
	//현황조회의 상태코드 조회
	public StatusDTO[] searchStatusList(String statusCd, String statusName) throws Exception;
	//일일업무보고미작성자 검색
	public DayErrDTO[] dayErrSearch(Box box) throws Exception;
	//페이징-기간별보고조회
	public DayErrDTO[] dayErrSearchPage(Box box,int pageNum,int pageSize) throws Exception;
	public DayErrDTO[] dayErrSearchPage4TL(Box box,int pageNum,int pageSize) throws Exception;
	public DayErrDTO[] dayErrMiSearch(Box box,int pageNum,int pageSize) throws Exception;
	//페이징-일일미작성조회
	public DayErrDTO[] dayErrMiSearch(Box box) throws Exception;
	public DayErrDTO[] dayErrApSearch(Box box,String empId) throws Exception;
	public boolean updateDaily(DayErrDTO dto)throws Exception;
	//일일업무보고미승인
	public DayErrDTO[] delayConfDayReportList(String empId, String role) throws Exception;
}
