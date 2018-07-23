package com.wms.beans.dto;

import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Enumeration;
import com.wms.beans.dao.IGroupEmp;
import com.wms.fw.DAOFactory;
import com.wms.fw.*;

public class UserInfo  extends GeneralDTO   
{
	/*
	직책 코드
	G =그룹장
	F =task장
	T =팀장
	E =임원
	C =CEO
	Y =직원
	P =P.M
	*/
	public String empId ;     //직번
	public String empKName ;  //성명
	public String empOrgCd;   //조직부호
	public String empOrgNo;   //조직부호
	public String empOrgName;
	public String eType ;     //직종(정규직,계약직,파견직,촉탁)
	public String eTypeName;
	public String wStartDt ;  //근무개시일
	public String wEndDt ;    //근무만료일
	public String eGroup;     //직군(기술직 , 관리직)
	public String eGroupName;
    public String eGrade;     //직급(대표 , 임원,부장,차장,과장,대리,직원)
	public String eGradeName;
	public String jobDs;      //대표직무설명
	public String jobAgency;  //직무대행자
	public String jobAgencyName;
	public String mainJobCd;  //대표직무번호
	public String mainJobCdName;
	//public String dutyFlag;   //직책유무
	//public String plEmpIdFlag;//계획자유무
	
	public String role;       //역할
	public String drType;       

	public String techGrade;  //기술등급(초,중,고,특급)
	public String techGradeName;
	public String aprvEmpId;  //상위자(=highEmpId)
    public String highEmpId;  //상위자사번
	public String highEmpKName;//상위자명

	public String reportYn;   //보고유무
	public String subEmpId;   //대결자(=apEmpId)
	public String empMemo;    //비고
    public String eDuty;      //직책(PM,본부장,팀원등)
	public String eDutyName;

    public String apEmpId;    //대결자 사번
    public String apEmpKName; //대결자 이름
	public String apEDuty;    //대결자 직책
	public String apEDutyName;
	public boolean apKey;      //대결유무

    public boolean isApEmp;    //승인자유무

	public Hashtable auths;          //통합된 권한 정보 객체

	public String ORG_NO_DIV_D; //조직 부문조회


    public OrgCdDTO[] orgCds; //view Group 


	//public Hashtable approvalList;//approval를 저장하는 객체

    public Object temp;       //임시 저장 변수
    public String CSRTEAM;       //CSR TEAM
    public String sabun;		//한웨이에서 보내준 사번을 저장

	//public UserInfo apUser;   //대결자의 정보를 담은 객체


	/** 
	 * 사용자가 로그인했는지를 알려준다.
	 * @return boolean 로그인했으면 true, 로그인을 않했으면 false
	 */    
	public boolean isLogin(){
		return (empId!=null&&!empId.trim().equals(""))?true:false;
	}
	/**
	 * 사용자가 계획자인지를 알려준다.
	 * @return boolean 계획자이면 true, 계획자가 아니면 false
	 */
	/* 
	public boolean isPlanner(){
		//return (plEmpIdFlag!=null&&plEmpIdFlag.trim().equals("Y"))?true:false;
		//임시...계획자는 그룹장으로 수정
		return (getManagerFlag().trim().equals("G"))?true:false;
	}
	*/
	/**
	 * 사용자가 (=팀장)인지를 알려준다.
	 * @return boolean 팀장이면 true, 팀장이 아니면 false
	 */
    /*	 
	public boolean isManager(){

		return (getManagerFlag().trim().equals("T"))?true:false;
	}
	*/
	/**
	 * 직책이 있는지를 알려준다.
	 * @return boolean 직책이 있으면 true, 아니면 false
	 */  
	public String getManagerFlag(){//본인의 직책,그룹장,팀장
	 /*
		String returns=null;
		ApprovalDTO dto=(ApprovalDTO)approvalList.get(empOrgCd);
		if(dto!=null){
			 returns=dto.managerFlag;
			 if(returns.equals("F")||returns.equals("P"))
				 returns="G";
		}
		return returns;
     */
		 String returns=this.eDuty;
		 if(returns.equals("F")||returns.equals("P"))
			 returns="G";
		 return returns;
	}
	
	public String getApManagerFlag(){//대결자의 직책
		String returns="";
		if(apKey){
			returns=apEDuty;
		}
		return returns;

	}
	
	public String getMgrUnionFlag(){
		String mgrFg=getManagerFlag()+getApManagerFlag();
		if(mgrFg.equals("YG")){
			mgrFg="G";
		}else if(mgrFg.equals("YT")){
			mgrFg="T";
		}
		return mgrFg;
	}
	/*
	public boolean setOrg(String orgCd){
		boolean returns=false;
		ApprovalDTO dto=(ApprovalDTO)approvalList.get(orgCd);
		if(dto!=null){
			this.empOrgCd = orgCd;
			this.empOrgName= dto.orgName;
			returns=true;
		}
		return returns;
	}
	*/
	/**
	 * 로그인된 직원의 정보를 등록한다.
	 * @param dto EmpDTO 직원 정보를 담은 객체
	 */  
	/*
	public void setEmp(EmpDTO dto)throws Exception{

		this.empId = dto.empId ;
		this.empKName = dto.empKName; 
		this.empOrgCd = dto.empOrgCd;
		this.empOrgName = dto.empOrgName;
		this.eType = dto.eType;
		this.wStartDt = dto.wStartDt;
		this.wEndDt = dto.wEndDt;
		this.eGroup = dto.eGroup;
		this.eGrade = dto.eGrade;
		this.jobDs = dto.jobDs;
		this.jobAgency = dto.jobAgency;
		this.mainJobCd = dto.mainJobCd;
		//this.dutyFlag = dto.dutyFlag ;
		//this.plEmpIdFlag = dto.plEmpIdFlag; 
        this.role        = dto.role;
		this.orgCds      = dto.orgCds;
		//this.approvals=dto.approvals;
		this.approvalList = new Hashtable();
		try{
			for(int i=0;i<dto.approvals.length;i++){
				approvalList.put(dto.approvals[i].orgCd,dto.approvals[i]);
			}

		}catch(Exception e){
			throw new Exception("결제테이블에 정보가 없습니다.");
		}

		ApprovalDTO apDto =(ApprovalDTO)approvalList.get(empOrgCd);
		if(apDto!=null){
			 this.apKey = (apDto.apEmpId!=null&&
				          !apDto.apEmpId.trim().equals(""))?true:false;  
			 if(apKey){
				 this.apEmpId = apDto.apEmpId;
				 this.apEmpKName= apDto.apEmpKName;				 
				 IGroupEmp groupEmp =(IGroupEmp)DAOFactory.getDAO("IGroupEmp");		
				 EmpDTO emp=groupEmp.empLogin(apDto.apEmpId.trim());
				 if(emp!=null){ 
					this.apUser=new UserInfo();				  
					apUser.setEmp(emp);
				 }

			 }
		}
		
	}
	
	public ArrayList getOrgNameList()throws Exception{
		ArrayList returns=new ArrayList();
		returns.add(empOrgCd);//default OrgCd값을 셋팅한다.
		Enumeration
        keys= approvalList.keys();
		String key="";
		while(keys.hasMoreElements()){
			key=(String)keys.nextElement();	
			if(!key.equals(empOrgCd))
				returns.add(key);
		}
		return returns;
	}
	*/
	
}