/*
 * �ۼ��� ��¥: 2006.08.08
 *
 * TODO ������ ���Ͽ� ���� ���ø�Ʈ�� �����Ϸ��� �������� �̵��Ͻʽÿ�.
 * â - ȯ�� ���� - Java - �ڵ� ��Ÿ�� - �ڵ� ���ø�Ʈ
 */
package com.wms.comPopup.beans.dto;

import com.wms.fw.GeneralDTO;
 
/**
 * @author mailbest
 *
 * TODO ������ ���� �ּ��� ���� ���ø�Ʈ�� �����Ϸ��� �������� �̵��Ͻʽÿ�.
 * â - ȯ�� ���� - Java - �ڵ� ��Ÿ�� - �ڵ� ���ø�Ʈ
 */
public class ComPopupSetDTO extends GeneralDTO{

	public String codeNo;     		//�ý��� �ڵ�
	public String code;   	  		//�ڵ�
	public String codeName;   		//�ڵ��
	public String code2;   	  		//�ڵ�
	public String codeName2;   		//�ڵ��
	public String highCode;   		//�����ڵ�
	public String highCodeName;   	//�����ڵ��
	public int    level;   			//����
	
	public String param;  			//��ȸ param
	public String paramId;  		//��ȸ paramId
	public String selFlag;  		//���� ��ư ����
	public String empId;  		    //�˻� ��� EMPID

	public String ObjCodeNo1;  		//�ڵ��ȣ1 ���� ���� Object��
	public String ObjCodeName1;		//�ڵ��1 ���� ���� Object�� 
	public String ObjCodeNo2;  		//�ڵ��ȣ2 ���� ���� Object��
	public String ObjCodeName2;		//�ڵ��2 ���� ���� Object��
	
	public String selectFlag;		//�˾� ��ȸ ����- ���� ����
	public String delFlag;			//�˾� ��ȸ ����- ���� ����
	public String rearrangeFlag;	//�˾� ��ȸ ����- ���� ����
	
}
