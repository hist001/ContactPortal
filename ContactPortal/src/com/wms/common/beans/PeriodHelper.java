/*************************************************************
*	�� �� ��  : PeriodHelper.java
*	�ۼ�����  : 2005/01/03
*	�� �� ��  : 
*	��    ��  : ��ǥ���� ���� ȭ���ۼ��� ���� �Ⱓ ������ helper, �ڹٿ��� ��¥������ �ϴ� ��� ����� �� ����
*************************************************************/
package com.wms.common.beans;

import java.util.*;

public class PeriodHelper {
	//������(YYYYMMDD), ������(YYYYMMDD) �Է½� �γ�¥ ������ ���� �������� �������
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
	//������(YYYYMMDD), ������ �Է½� �����Ϻ��� �ش� �������� ���� ���� ��¥�� YYYYMM���� ������
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
