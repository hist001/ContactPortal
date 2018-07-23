/*************************************************************
*	파 일 명  : StatusDTO.java
*	작성일자  : 2004/06/22
*	작 성 자  : 
*	내    용  : 상태관련 정보를 담은 클래스
*************************************************************/
package com.wms.beans.dto;
import java.util.Hashtable;
import javax.servlet.http.HttpServletRequest;
import com.wms.fw.jsp.HtmlUtility;

public class StatusDTO extends Hashtable
{
	public String statusCd = "";  //상태코드
	public String statusNm = "";  //상태명
};