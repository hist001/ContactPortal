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
	��å �ڵ�
	G =�׷���
	F =task��
	T =����
	E =�ӿ�
	C =CEO
	Y =����
	P =P.M
	*/
	public String empId ;     //����
	public String empKName ;  //����
	public String empOrgCd;   //������ȣ
	public String empOrgNo;   //������ȣ
	public String empOrgName;
	public String eType ;     //����(������,�����,�İ���,��Ź)
	public String eTypeName;
	public String wStartDt ;  //�ٹ�������
	public String wEndDt ;    //�ٹ�������
	public String eGroup;     //����(����� , ������)
	public String eGroupName;
    public String eGrade;     //����(��ǥ , �ӿ�,����,����,����,�븮,����)
	public String eGradeName;
	public String jobDs;      //��ǥ��������
	public String jobAgency;  //����������
	public String jobAgencyName;
	public String mainJobCd;  //��ǥ������ȣ
	public String mainJobCdName;
	//public String dutyFlag;   //��å����
	//public String plEmpIdFlag;//��ȹ������
	
	public String role;       //����
	public String drType;       

	public String techGrade;  //������(��,��,��,Ư��)
	public String techGradeName;
	public String aprvEmpId;  //������(=highEmpId)
    public String highEmpId;  //�����ڻ��
	public String highEmpKName;//�����ڸ�

	public String reportYn;   //��������
	public String subEmpId;   //�����(=apEmpId)
	public String empMemo;    //���
    public String eDuty;      //��å(PM,������,������)
	public String eDutyName;

    public String apEmpId;    //����� ���
    public String apEmpKName; //����� �̸�
	public String apEDuty;    //����� ��å
	public String apEDutyName;
	public boolean apKey;      //�������

    public boolean isApEmp;    //����������

	public Hashtable auths;          //���յ� ���� ���� ��ü

	public String ORG_NO_DIV_D; //���� �ι���ȸ


    public OrgCdDTO[] orgCds; //view Group 


	//public Hashtable approvalList;//approval�� �����ϴ� ��ü

    public Object temp;       //�ӽ� ���� ����
    public String CSRTEAM;       //CSR TEAM
    public String sabun;		//�ѿ��̿��� ������ ����� ����

	//public UserInfo apUser;   //������� ������ ���� ��ü


	/** 
	 * ����ڰ� �α����ߴ����� �˷��ش�.
	 * @return boolean �α��������� true, �α����� �������� false
	 */    
	public boolean isLogin(){
		return (empId!=null&&!empId.trim().equals(""))?true:false;
	}
	/**
	 * ����ڰ� ��ȹ�������� �˷��ش�.
	 * @return boolean ��ȹ���̸� true, ��ȹ�ڰ� �ƴϸ� false
	 */
	/* 
	public boolean isPlanner(){
		//return (plEmpIdFlag!=null&&plEmpIdFlag.trim().equals("Y"))?true:false;
		//�ӽ�...��ȹ�ڴ� �׷������� ����
		return (getManagerFlag().trim().equals("G"))?true:false;
	}
	*/
	/**
	 * ����ڰ� (=����)������ �˷��ش�.
	 * @return boolean �����̸� true, ������ �ƴϸ� false
	 */
    /*	 
	public boolean isManager(){

		return (getManagerFlag().trim().equals("T"))?true:false;
	}
	*/
	/**
	 * ��å�� �ִ����� �˷��ش�.
	 * @return boolean ��å�� ������ true, �ƴϸ� false
	 */  
	public String getManagerFlag(){//������ ��å,�׷���,����
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
	
	public String getApManagerFlag(){//������� ��å
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
	 * �α��ε� ������ ������ ����Ѵ�.
	 * @param dto EmpDTO ���� ������ ���� ��ü
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
			throw new Exception("�������̺� ������ �����ϴ�.");
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
		returns.add(empOrgCd);//default OrgCd���� �����Ѵ�.
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