/*************************************************************
*	�� �� ��  : ColumnDTO.java
*	�ۼ�����  : 2006/01/13
*	�� �� ��  : ����ȣ
*	��    ��  : �÷��� ������ �����ϴ� Ŭ����
*************************************************************/
package com.wms.fw.db;

import com.wms.fw.GeneralDTO;

/**
 * �÷��� ������ �����ϴ� Ŭ����
 * table_Name - ��ƼƼ��
 * column_Name - �Ӽ���
 * isPk - primary key �̸�, 1 �׿� 0
 * data_Type - �ڷ��� type ���� , java.sql.Types ���ذ� ����
 * data_Length - �ڷ��� ����   
 */
public class  ColumnDTO extends GeneralDTO
{
	public String table_Name;
	public String column_Name;
	public int isPk;
	public int data_Type;
	public int data_Length;

	public ColumnDTO(){}
	public ColumnDTO(String table_Name,String column_Name,int isPk,int data_Type,int data_Length){
		this.table_Name=table_Name;
		this.column_Name=column_Name;
		this.isPk=isPk;
		this.data_Type=data_Type;
		this.data_Length=data_Length;
	}
}
