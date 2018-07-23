/*************************************************************
*	파 일 명  : IGroupEmp.java
*	작성일자  : 2004/06/22
*	작 성 자  : 
*	내    용  : 직원 관리 관련 인터페이스
*************************************************************/
package com.wms.beans.dao;
import com.wms.beans.dto.*;
import java.util.ArrayList;
public interface IGroupEmp 
{
	public static final int SUB_PLANNER_SEARCH=1;  //계획자
	public static final int SUB_EMP_SEARCH    =2;  //일반직원
	public static final int SUB_ALL_SEARCH    =3;  //계획자+일반직원

    //직원 저장
	public boolean   save(EmpDTO[] dto)throws Exception;
	//조직에 속한 직원 조회 
	public EmpDTO[]  empSearch(String orgCd)throws Exception;
	//직원 조회
	public EmpDTO[]  empSearch(EmpDTO dto)throws Exception;
	//사번,조직,계획자유무 조건에 대한 직원 조회
	public EmpDTO[]  empSubSearch(String empId,String orgCd, int mode)throws Exception;
	//사번,계획자유무 조건에 대한 직원 조회
	public EmpDTO[]  empSubSearch(String empId, int mode)throws Exception;
	//업무보고 하위직원조회
	public EmpDTO[] groupSubSearch(String empId, int mode)throws Exception;
	//직원에 상세 조회
	public EmpDTO    empDetailSearch(String empId)throws Exception;
	//같은 상위자에 속한 동료 직원 조회
	public EmpDTO[]  empComradeSearch(String empId,String orgCd)throws Exception;
	//합동 업무 보고를 위한 직원 조회
	public EmpDTO[]  empUnionSearch(String empId,String orgCd,String reportDt,boolean managerKey)throws Exception;
	//로그인을 위한 직원 조회
	public UserInfo  empLogin(String empId)throws Exception;
	//승인자 조회
	public EmpDTO    empApSearch(String empId)throws Exception;
	//같은 승인자에 속한 직원 조회
	public EmpDTO[]  empSelectSearch(String empId)throws Exception;
	//대결자지정하기위한 승인자 수정
	public boolean   apUpdate(String empId,String agentId,String agentKName)throws Exception;
	//승인자 일괄 처리를 위한 승인자들 조회
	public EmpDTO[]  orgSearch(String orgCd,String orgName)throws Exception;
	//승인자 일괄 처리를 위한 직원 검색
	public EmpDTO[]  orgEmpSearch(String empId,String empKName)throws Exception;
	//승인자 일괄 처리를 위한 승인자 수정
	public boolean   highUpdate(String oldEmpId,String newEmpId)throws Exception;
    //결제선에 있는 임직원들을 조회, 
	//flag==true 자신보다 상위결재선 조회, flag==false 자신보다 하위의 결재선 조회
	public ArrayList appList(String empId,boolean flag)throws Exception;
    //결제선에 있는 임직원들을 조회,
	//flag==true 자신보다 상위결재선 조회, flag==false 자신보다 하위의 결재선 조회
	//level==0 전체,그외 level은 그 수준에 맞는 level를 조회
	public ArrayList appList(String empId,boolean flag,String level)throws Exception;
	//2005/02/24 조원호
    //사람별 View Group 조회 기능
	public OrgCdDTO[] searchOrgMappingId(String empId)throws Exception;
    //사람별 View Group 등록
	public boolean registOrgMappingId(String empId,String grpOrgCd)throws Exception ;
    //사람별 View Group 삭제
	public boolean deleteOrgMappingId(String empId,String grpOrgCd)throws Exception ;
}