package com.wms.beans.dto;
import com.wms.fw.*;
public class HJobDTO extends GeneralDTO implements DTO
{
	public String empId;			//��� (����α����� ���)
	public String empKName;			//�α����� ��� �̸�
	public String orgName;
	public String revisionNo;		//����ǥ Revision
	public String jobDs;			//��ǥ���� emp table�� jobDs �� ������
	public String jobAgency;
	public String jobAgencyName;
	public String mkEmpId;		//�ۼ��ڻ��
	public String mkEmpKName;		//�ۼ����̸�
	public String lastDtm;			//���������Ͻ�
	public String apEmpId;			//�����ڻ��
	public String apEmpKName;		//�������̸�
	public String confirmDtm;		//�����Ͻ�
	public String statusFlag;			//��������ǥ���� JA0:�ۼ��� JB0:�ۼ��Ϸ� JC0:���οϷ�

	//view ó���� ����
	public String statusName;       //���°��̸�
	
	//public String coWorkEmpYN;		//�յ�������

	public HJobCdDTO[] hJobCdDTOs;	//���������ڵ�LISTs

	public CoJobDTO[] coJobDTOs;   //�յ������ڵ�

	//public String calls;
}