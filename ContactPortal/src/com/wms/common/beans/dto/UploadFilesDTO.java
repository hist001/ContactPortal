/*************************************************************
*	�� �� ��  : UploadFilesDTO.java
*	�ۼ�����  : 2006/01/10
*	�� �� ��  : ����ȣ
*	��    ��  : ���� ���� ���� ����
*************************************************************/
package com.wms.common.beans.dto;
import com.wms.fw.GeneralDTO;
//��ǰ����
public class UploadFilesDTO extends GeneralDTO
{

	public String ui_no;   			//#key
	public String obj_no;          	//������ȣ
	public String dir;              //���
	public String file_no;          //���Ϲ�ȣ
	public String fileType;         //��������
	public String fileSystemName;   //���Ͻý��۸�
	public String fileOriginName;   //���Ͽ�����
	public String delFlag;			//��������	
	public String crEmpId;		
	public String createDtm;
	public String div;
	
	public String CHK;			//��������

	
}
