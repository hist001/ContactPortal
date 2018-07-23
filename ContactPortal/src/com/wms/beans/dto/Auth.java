package com.wms.beans.dto;

import com.wms.fw.GeneralDTO;
public class Auth extends GeneralDTO
{
	public String authType;
	public String empId;
    public String authStartDt;
	public String authEndDt;
	public String isAcl;
}
