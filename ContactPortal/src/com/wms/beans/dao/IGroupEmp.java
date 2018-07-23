/*************************************************************
*	�� �� ��  : IGroupEmp.java
*	�ۼ�����  : 2004/06/22
*	�� �� ��  : 
*	��    ��  : ���� ���� ���� �������̽�
*************************************************************/
package com.wms.beans.dao;
import com.wms.beans.dto.*;
import java.util.ArrayList;
public interface IGroupEmp 
{
	public static final int SUB_PLANNER_SEARCH=1;  //��ȹ��
	public static final int SUB_EMP_SEARCH    =2;  //�Ϲ�����
	public static final int SUB_ALL_SEARCH    =3;  //��ȹ��+�Ϲ�����

    //���� ����
	public boolean   save(EmpDTO[] dto)throws Exception;
	//������ ���� ���� ��ȸ 
	public EmpDTO[]  empSearch(String orgCd)throws Exception;
	//���� ��ȸ
	public EmpDTO[]  empSearch(EmpDTO dto)throws Exception;
	//���,����,��ȹ������ ���ǿ� ���� ���� ��ȸ
	public EmpDTO[]  empSubSearch(String empId,String orgCd, int mode)throws Exception;
	//���,��ȹ������ ���ǿ� ���� ���� ��ȸ
	public EmpDTO[]  empSubSearch(String empId, int mode)throws Exception;
	//�������� ����������ȸ
	public EmpDTO[] groupSubSearch(String empId, int mode)throws Exception;
	//������ �� ��ȸ
	public EmpDTO    empDetailSearch(String empId)throws Exception;
	//���� �����ڿ� ���� ���� ���� ��ȸ
	public EmpDTO[]  empComradeSearch(String empId,String orgCd)throws Exception;
	//�յ� ���� ���� ���� ���� ��ȸ
	public EmpDTO[]  empUnionSearch(String empId,String orgCd,String reportDt,boolean managerKey)throws Exception;
	//�α����� ���� ���� ��ȸ
	public UserInfo  empLogin(String empId)throws Exception;
	//������ ��ȸ
	public EmpDTO    empApSearch(String empId)throws Exception;
	//���� �����ڿ� ���� ���� ��ȸ
	public EmpDTO[]  empSelectSearch(String empId)throws Exception;
	//����������ϱ����� ������ ����
	public boolean   apUpdate(String empId,String agentId,String agentKName)throws Exception;
	//������ �ϰ� ó���� ���� �����ڵ� ��ȸ
	public EmpDTO[]  orgSearch(String orgCd,String orgName)throws Exception;
	//������ �ϰ� ó���� ���� ���� �˻�
	public EmpDTO[]  orgEmpSearch(String empId,String empKName)throws Exception;
	//������ �ϰ� ó���� ���� ������ ����
	public boolean   highUpdate(String oldEmpId,String newEmpId)throws Exception;
    //�������� �ִ� ���������� ��ȸ, 
	//flag==true �ڽź��� �������缱 ��ȸ, flag==false �ڽź��� ������ ���缱 ��ȸ
	public ArrayList appList(String empId,boolean flag)throws Exception;
    //�������� �ִ� ���������� ��ȸ,
	//flag==true �ڽź��� �������缱 ��ȸ, flag==false �ڽź��� ������ ���缱 ��ȸ
	//level==0 ��ü,�׿� level�� �� ���ؿ� �´� level�� ��ȸ
	public ArrayList appList(String empId,boolean flag,String level)throws Exception;
	//2005/02/24 ����ȣ
    //����� View Group ��ȸ ���
	public OrgCdDTO[] searchOrgMappingId(String empId)throws Exception;
    //����� View Group ���
	public boolean registOrgMappingId(String empId,String grpOrgCd)throws Exception ;
    //����� View Group ����
	public boolean deleteOrgMappingId(String empId,String grpOrgCd)throws Exception ;
}