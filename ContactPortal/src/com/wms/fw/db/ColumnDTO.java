/*************************************************************
*	파 일 명  : ColumnDTO.java
*	작성일자  : 2006/01/13
*	작 성 자  : 조원호
*	내    용  : 컬럼의 정보를 저장하는 클래스
*************************************************************/
package com.wms.fw.db;

import com.wms.fw.GeneralDTO;

/**
 * 컬럼의 정보를 저장하는 클래스
 * table_Name - 엔티티명
 * column_Name - 속성명
 * isPk - primary key 이면, 1 그외 0
 * data_Type - 자료의 type 정보 , java.sql.Types 기준과 동일
 * data_Length - 자료의 길이   
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
