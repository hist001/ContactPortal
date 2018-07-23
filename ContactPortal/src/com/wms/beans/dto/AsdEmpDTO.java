/*************************************************************
*	파 일 명  : AsdEmpDTO.java
*	작성일자  : 2004/06/22
*	작 성 자  : 
*	내    용  : 배정되어진 직원 관련 정보를 담는 클래스
*************************************************************/
package com.wms.beans.dto;
import com.wms.fw.*;
public class AsdEmpDTO extends GeneralDTO implements DTO 
{
	public String   asdEmpId;
	public String   planCd;
	public String   asdEmpKName;
}
