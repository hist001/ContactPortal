/*************************************************************
*	파 일 명  : UploadFilesDTO.java
*	작성일자  : 2006/01/10
*	작 성 자  : 조원호
*	내    용  : 파일 관련 정보 저장
*************************************************************/
package com.wms.common.beans.dto;
import com.wms.fw.GeneralDTO;
//제품파일
public class UploadFilesDTO extends GeneralDTO
{

	public String ui_no;   			//#key
	public String obj_no;          	//참조번호
	public String dir;              //경로
	public String file_no;          //파일번호
	public String fileType;         //파일종류
	public String fileSystemName;   //파일시스템명
	public String fileOriginName;   //파일원본명
	public String delFlag;			//삭제여부	
	public String crEmpId;		
	public String createDtm;
	public String div;
	
	public String CHK;			//삭제여부

	
}
