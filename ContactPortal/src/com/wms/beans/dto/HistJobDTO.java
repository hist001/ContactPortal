package com.wms.beans.dto;
import com.wms.fw.*;

public class HistJobDTO extends GeneralDTO 
{
	public String jobCd;		//�����ڵ�
	public String jobName;    //������
	public String jobTitle;       //
	public String actDs;        //��������
	public String bizNo;	      //�����ȣ
	public String superJobCd;	      //���������ڵ� 2006.06.14 add
	public String bizName;   //�����
	public String useFlag;    //�������
	
	public int      totCnt=0;//�� ���ڵ� �� (����¡)
		
	public TeamJobDTO[] teamjobs; //�����������
	public EmpJobDTO[] empjobs; //�����������
};