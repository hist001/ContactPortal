package com.wms.beans.dto;
import com.wms.fw.*;
public class HJobCdDTO extends GeneralDTO implements DTO
{
	public String jobCd; //직무코드
	public String jobName; //직무명
	public String jobTitle; //세부직무항목명
	
	public int yearCnt; // 연간발생건수
	public double unitUseHM; // 건당소요시간
	public double yearTotHM; // 연간사용시간
}