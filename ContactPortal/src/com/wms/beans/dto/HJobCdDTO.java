package com.wms.beans.dto;
import com.wms.fw.*;
public class HJobCdDTO extends GeneralDTO implements DTO
{
	public String jobCd; //�����ڵ�
	public String jobName; //������
	public String jobTitle; //���������׸��
	
	public int yearCnt; // �����߻��Ǽ�
	public double unitUseHM; // �Ǵ�ҿ�ð�
	public double yearTotHM; // �������ð�
}