/*************************************************************
*	파 일 명  : PeriodHelper.java
*	작성일자  : 2005/01/03
*	작 성 자  : 
*	내    용  : 목표원가 생성 화면작성을 위한 기간 설정의 helper, 자바에서 날짜연산을 하는 경우 사용할 수 있음
*************************************************************/
package com.wms.common.beans;

import java.util.*;

public class PeriodHelper {
	//시작일(YYYYMMDD), 종료일(YYYYMMDD) 입력시 두날짜 사이의 차를 월단위로 계산해줌
	public static int getBetweenMonth(String startDt, String endDt){
		
		Calendar c1= Calendar.getInstance();
		Calendar c2= Calendar.getInstance();
		
		int s_yy = Integer.parseInt(startDt.substring(0,4));
		int s_mm = Integer.parseInt(startDt.substring(4,6));
		int s_dd = Integer.parseInt(startDt.substring(6,8));
		
		int e_yy = Integer.parseInt(endDt.substring(0,4));
		int e_mm = Integer.parseInt(endDt.substring(4,6));
		int e_dd = Integer.parseInt(endDt.substring(6,8));
		
		c1.set(Calendar.YEAR, s_yy) ;
		c1.set(Calendar.MONTH, s_mm) ;
		c1.set(Calendar.DAY_OF_MONTH, s_dd) ;
		
		c2.set(Calendar.YEAR, e_yy) ;
		c2.set(Calendar.MONTH, e_mm) ;
		c2.set(Calendar.DAY_OF_MONTH, e_dd) ;
		
		int month = (c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR)) *12 + (c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH)) +1;
				
		return month;		
		
	}
	//시작일(YYYYMMDD), 개월을 입력시 시작일부터 해당 개월수를 더한 후의 날짜를 YYYYMM으로 리턴함
	public static String getAfterYYYYMM(String startDt, int period){
		Calendar c= Calendar.getInstance();
		String result = new String();
				
		int s_yy = Integer.parseInt(startDt.substring(0,4));
		int s_mm = Integer.parseInt(startDt.substring(4,6));
		int s_dd = Integer.parseInt(startDt.substring(6,8));
		
		c.set(Calendar.YEAR, s_yy) ;
		c.set(Calendar.MONTH, s_mm-1) ;
		c.set(Calendar.DAY_OF_MONTH, s_dd) ;
		
		c.add(Calendar.MONTH, period);
		
		String str = String.valueOf(c.get(Calendar.MONTH)+1);
		
		result = String.valueOf(c.get(Calendar.YEAR))+((str.length()<2)?"0"+str:str);
		
		return result;		
	}
		
}
