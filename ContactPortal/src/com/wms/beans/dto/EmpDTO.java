package com.wms.beans.dto;
import com.wms.fw.*;
public class EmpDTO extends GeneralDTO implements DTO
{
	public String empId ;     //����
	public String empKName ;  //����
	public String empOrgCd;   //������ȣ
	public String empOrgName; //�����̸�
	public String eType ;     //��������
	public String wStartDt ;  //�ٹ�������
	public String wEndDt ;    //�ٹ�������
	public String eGroup;     //����
    public String eGrade;     //����
	public String jobDs;      //��ǥ��������
	public String jobAgency;  //����������
	public String mainJobCd;  //��ǥ������ȣ
	public String dutyFlag;   //��å����
	public String plEmpIdFlag;//��ȹ������
	public String retFlag ;   //��������
	
	public String role;       //����
	public String drType;       
	
	public int totCnt=0;      // �� ���ڵ� �� (����¡)
	
	public ApprovalDTO[] approvals;
	public EmpJobDTO[] empjobs;
	public OrgCdDTO[]     orgCds;//View���� ��ȸ �� �� �ֵ��� ��ϵ� �׷��
}