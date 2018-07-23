package com.wms.beans.dto;
import com.wms.fw.*;
/*결제선을 담은 객체*/
public class AppListDTO extends GeneralDTO 
{
	public String lv;		    //레벨(=단계)
	public String orgCd;        //그룹
	public String orgName;
	public EmpDTO[] empDTOs;    //직원들
};