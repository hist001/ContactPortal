package com.wms.beans.dto;
import com.wms.fw.*;
/*�������� ���� ��ü*/
public class AppListDTO extends GeneralDTO 
{
	public String lv;		    //����(=�ܰ�)
	public String orgCd;        //�׷�
	public String orgName;
	public EmpDTO[] empDTOs;    //������
};