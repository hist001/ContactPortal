/*************************************************************
*	파 일 명  : DTO.java
*	작성일자  : 2004/09/06
*	작 성 자  : 
*	내    용  : 모든 DTO의 인터페이스
*************************************************************/
package com.wms.fw;
import java.util.Hashtable;
/**
 * 모든 DTO의 인터페이스
 * 핵심 기능은 DTO가 가진 모든 값을 리턴해주는 getList(),         
 */
public interface DTO{
	/**
	 * DTO가 가진 모든 값을 리턴한다.
	 * 
	 * @return java.util.Hashtable
	 */	
	public Hashtable getList();
}