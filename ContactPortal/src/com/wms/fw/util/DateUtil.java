package com.wms.fw.util;

import java.util.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import com.wms.fw.Utility;

public class DateUtil {
    //���� ���ڰ� ���������� �����̸� true.
    public static boolean isAtmUpdate(String atmDay,int term)throws Exception{
		boolean returns=false;
		String lastTermDay= DateUtil.getAdjustDate(atmDay  ,term);
		lastTermDay=Utility.replace(lastTermDay,"-");
		String toDay=getTodayString("");
		if(Integer.parseInt(lastTermDay) >= Integer.parseInt(toDay))
			returns=true;
		return returns;
	}
    //���� �ۼ��� ������ �����̸� true.
    public static boolean isPreWrite()throws Exception{
		boolean returns=false;
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat ("HHmm", java.util.Locale.KOREA);
		int hour = Integer.parseInt(formatter.format(new java.util.Date()));
		com.wms.fw.Config conf = new com.wms.fw.Configuration();
		String temp=conf.get("com.wms.write.hour").trim();
		temp+=conf.get("com.wms.write.min").trim();
		int writeHour=Integer.parseInt(temp.trim());
		if(writeHour > hour)
			returns=true;
		return returns;
	}
	public static int strToIntDay(String day)throws Exception{
		int returns=0;
		if(day.equals("��")){
			returns=1;
		}else if(day.equals("ȭ")){
			returns=2;
		}else if(day.equals("��")){
			returns=3;
		}else if(day.equals("��")){
			returns=4;
		}else if(day.equals("��")){
			returns=5;
		}else if(day.equals("��")){
			returns=6;
		}else if(day.equals("��")){
			returns=7;
		}
		return returns;
	}
    //���� ���ڰ� ���� ���� �̳� �� ��� true.
    public static boolean checkDate(String day)throws Exception{

		boolean returns=false;
		Date date = new Date();
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat ("E", java.util.Locale.KOREA);

		int ori= strToIntDay(formatter.format(date));
		int val= strToIntDay(day);
		if(ori<=val)
			returns=true;
		return returns;
	}
	//���� ���ڰ� ���� ���ϰ� �ð� �̳� �� ��� true. 
	public static boolean checkDate(String day, int time)throws Exception{
		boolean returns=false;
		Date date = new Date();
        String checkDay=null;
		if(checkDate(day)){
			java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat ("E HH", java.util.Locale.KOREA);
			checkDay=formatter.format(date);
			if(day.equals(checkDay.substring(0,1))){ 
				int val=Integer.parseInt(checkDay.substring(checkDay.length()-2));
				if(val<time)
					returns=true;
			}else{
				returns=true;
			}
		}

		return returns;
	}
	//���� �ð���  ���� �ð� �̳� �� ��� true. 
 	public static boolean checkDate( int time)throws Exception{
		boolean returns=false;
		Date date = new Date();
  
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat ("HH", java.util.Locale.KOREA);
		int val=Integer.parseInt(formatter.format(date));
		if(val<time)
			returns=true;
		return returns;
    }
	//���� �ð���  ���� �ð�, �� �̳� �� ��� true. 
 	public static boolean checkDate( int hour,int min)throws Exception{
		boolean returns=false;
		Date date = new Date();
  
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat ("HH mm", java.util.Locale.KOREA);
		String time=formatter.format(date);
		int valHour=Integer.parseInt(time.substring(0,2));
		if(valHour<=hour){
			if(valHour==hour){
				int valMin=Integer.parseInt(time.substring(time.length()-2));
				if(valMin<=min)
					returns=true;
			}else{
				returns=true;
			}

		}			
		return returns;
    }
	//���� ����  ���� �ð�, �� �̳� �� ��� true. 
 	public static boolean checkDate(String day, int hour,int min)throws Exception{
		boolean returns=false;
		if(checkDate(day)){
			if(checkDate(hour,min))
				returns=true;
		}	
		return returns;
    }
    public static String transDate(String day){
		if(day!=null&&day.length()==8)
			return day.substring(0,4)+"-"+day.substring(4,6)+"-"+day.substring(6);
		return day;
	}
	public static String getFirstDate(String dateStr)throws Exception{
		java.sql.Date date=java.sql.Date.valueOf(dateStr);
		SimpleDateFormat formatter = new SimpleDateFormat ("E", java.util.Locale.KOREA);

		String str= formatter.format(date);
		while(!str.equals("��")){
			date.setTime(date.getTime()-(24*60*60*1000));
			str=formatter.format(date);
		}
		return date.toString();
	}
	public static String getDay(String dayName,String todate,int mode){
		java.sql.Date date=java.sql.Date.valueOf(todate);
		SimpleDateFormat formatter = new SimpleDateFormat ("E", java.util.Locale.KOREA);
		String str= formatter.format(date);	
        while(!str.equals(dayName)){
			date.setTime(date.getTime()-(24*60*60*1000));
			str= formatter.format(date);	
		}
		if(mode==0){//�̹���
		}else if(mode==1){//������
			date.setTime(date.getTime()+(24*60*60*1000)*7);
		}else if(mode==-1){//����
			date.setTime(date.getTime()-(24*60*60*1000)*7);
		}else{
			return null;
		}
		return date.toString();

	}
	//�� ������ ����
	public static String getToDay(){
		Date date=new Date();
		SimpleDateFormat formatter = new SimpleDateFormat ("E", java.util.Locale.KOREA);
		return formatter.format(date);
	}
	// ������ ����
	public static String getToDay(String day){
		java.sql.Date date=java.sql.Date.valueOf(day);
		SimpleDateFormat formatter = new SimpleDateFormat ("E", java.util.Locale.KOREA);
		return formatter.format(date);
	}
	public static String[] getWeekDay(String dayName,String todate,int mode){
		String[] returns=null;        
		java.sql.Date date=java.sql.Date.valueOf(getDay( dayName, todate,mode));
		returns=new String[2];
		returns[0]=date.toString();
		date.setTime(date.getTime()+(24*60*60*1000)*6);		
		returns[1]=date.toString();
		return returns;

	}

	public static String getAdjustDate(String dateStr ,int num)throws Exception{
		java.sql.Date date=java.sql.Date.valueOf(dateStr);
		date.setTime(date.getTime()+((24*60*60*1000)*(num-1)));
		return date.toString(); 
	}
	public static String[] getCal(String dateStr, int calNum)throws Exception{
        String firstDate=getFirstDate(dateStr.substring(0,8)+"01");
		String[] returns=new String[calNum];
		java.sql.Date date=java.sql.Date.valueOf(firstDate);
		long time=date.getTime();
		long dd =24*60*60*1000;
		for(int i=0;i<calNum;i++){
			date.setTime(time+(dd*i));
			returns[i]=date.toString();
		}
		return returns;
	}
  public static Date toDate(String strVal) {
	char delimeter = ' ';
	for (int i = 0; i < strVal.length(); i++) {
	  if (Character.getType(strVal.charAt(i)) != Character.DECIMAL_DIGIT_NUMBER) {
		delimeter = strVal.charAt(i);
		break;
	  }
	}

	SimpleDateFormat df = null;
	if (delimeter == ' ')
	  df = new SimpleDateFormat("yyyyMMdd");
	else
	  df = new SimpleDateFormat("yyyy" + delimeter + "MM" + delimeter + "dd");

	try {
	  return df.parse(strVal);
	} catch(java.text.ParseException e) {
	  return null;
	}
  }    

  public static Timestamp toTimestamp(String strVal) {
	if (toDate(strVal) == null) return null;
	return new Timestamp(toDate(strVal).getTime());
  }    

  public static Timestamp toTimestamp(int year, int month, int day) {
	if (toDate(year, month, day) == null) return null;
	return new Timestamp(toDate(year, month, day).getTime());
  }    

  public static Date toDate(int year, int month, int day) {
	Calendar resultCal = Calendar.getInstance();
	resultCal.set(year, month - 1, day);
	return resultCal.getTime();
  }    

  public static String toString(Date currentDate) {
	if (currentDate == null) return null;
	SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
	return df.format(currentDate);
  }    

  public static String toString(Timestamp currentDate) {
	if (currentDate == null) return null;
	SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
	return df.format(new Date(currentDate.getTime()));
  }    

  public static Date getToday() {
	return new java.util.Date();
  }    
  public static String getTodayString(String pre){
	if(pre==null)
		return null;
    String format="yyyy"+pre+"MM"+pre+"dd";
	SimpleDateFormat df = new SimpleDateFormat(format);
	return df.format(new Date());
  }
  public static String getTodayString(Date currentDate) {
	if (currentDate == null) return null;
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	return df.format(currentDate);
  }    

  public static String getTodayString(){
	return getTodayString("");
  }
  public static Timestamp getTodayTimestamp() {
	return new Timestamp(new java.util.Date().getTime());
  }    

  public static Date add(Date currentDate, int year, int month, int day) {
	if(currentDate==null) return null;
	Calendar resultCal = Calendar.getInstance();
	resultCal.setTime(currentDate);
	resultCal.set(resultCal.get(Calendar.YEAR) + year, resultCal.get(Calendar.MONTH) + month, resultCal.get(Calendar.DATE) + day);
	return resultCal.getTime();
  }      

  public static int getYear(Date currentDate) {
	if(currentDate==null) return 0;
	Calendar resultCal = Calendar.getInstance();
	resultCal.setTime(currentDate);
	return resultCal.get(Calendar.YEAR);
  }      

  public static int getMonth(Date currentDate) {
	if(currentDate==null) return 0;	
	Calendar resultCal = Calendar.getInstance();
	resultCal.setTime(currentDate);
	return (resultCal.get(Calendar.MONTH) + 1);
  }      

  public static int getDay(Date currentDate) {
	if(currentDate==null) return 0;
	Calendar resultCal = Calendar.getInstance();
	resultCal.setTime(currentDate);
	return resultCal.get(Calendar.DATE);
  }        

  public static int getYear(Timestamp currentDate) {
	if(currentDate==null) return 0;
	Calendar resultCal = Calendar.getInstance();
	resultCal.setTime(new Date(currentDate.getTime()));
	return resultCal.get(Calendar.YEAR);
  }      

  public static int getMonth(Timestamp currentDate) {
	if(currentDate==null) return 0;
	Calendar resultCal = Calendar.getInstance();
	resultCal.setTime(new Date(currentDate.getTime()));
	return (resultCal.get(Calendar.MONTH) + 1);
  }      

  public static int getDay(Timestamp currentDate) {
	if(currentDate==null) return 0;
	Calendar resultCal = Calendar.getInstance();
	resultCal.setTime(new Date(currentDate.getTime()));
	return resultCal.get(Calendar.DATE);
  } 
  
	/**
	 * @return formatted string representation of current day with  "yyyyMMdd".
	 */
	public static String getShortDateString() {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat ("yyyyMMdd", java.util.Locale.KOREA);
		return formatter.format(new java.util.Date());
	}

	/**
	 * @return formatted string representation of current time with  "HHmmss".
	 */
	public static String getShortTimeString() {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat ("HHmmss", java.util.Locale.KOREA);
		return formatter.format(new java.util.Date());
	}

	/**
	 * @return formatted string representation of current time with  "yyyy-MM-dd HH:mm:ss".
	 */
	public static String getTimeStampString() {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat ("yyyy-MM-dd HH:mm:ss", java.util.Locale.KOREA);
		return formatter.format(new java.util.Date());
	}
	
	public static String getTimeStampStringTrim() {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat ("yyyyMMddHHmmss", java.util.Locale.KOREA);
		return formatter.format(new java.util.Date());
	}

	/**
	 * @return formatted string representation of current time with  "HH:mm:ss".
	 */
	public static String getTimeString() {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat ("HH:mm:ss", java.util.Locale.KOREA);
		return formatter.format(new java.util.Date());
	}
	//�⵵�� ����ó����
	public static String addYear(String inputDate, int period){
		String currentdate = Utility.replace(inputDate,"-");
		Calendar c = Calendar.getInstance();
		String resultDate = new String();
				
		int s_yy = Integer.parseInt(currentdate.substring(0,4));
		int s_mm = Integer.parseInt(currentdate.substring(4,6));
		int s_dd = Integer.parseInt(currentdate.substring(6,8));
		c.set(Calendar.YEAR, s_yy);
		c.set(Calendar.MONTH, s_mm-1);
		c.set(Calendar.DAY_OF_MONTH, s_dd);
		
		c.add(Calendar.YEAR, period);							
		String monthTemp = String.valueOf(c.get(Calendar.MONTH)+1);
		String dayTemp = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
		resultDate = String.valueOf(c.get(Calendar.YEAR))+((monthTemp.length()==1)?0+monthTemp:monthTemp)+((dayTemp.length()==1)?0+dayTemp:dayTemp);
		
		return resultDate;		
	}
	//�������� ����ó����
	public static String addMonth(String inputDate, int period){
		String currentdate = Utility.replace(inputDate,"-");
		Calendar c = Calendar.getInstance();
		String resultDate = new String();
				
		int s_yy = Integer.parseInt(currentdate.substring(0,4));
		int s_mm = Integer.parseInt(currentdate.substring(4,6));
		int s_dd = Integer.parseInt(currentdate.substring(6,8));
		c.set(Calendar.YEAR, s_yy);
		c.set(Calendar.MONTH, s_mm-1);
		c.set(Calendar.DAY_OF_MONTH, s_dd);
		
		c.add(Calendar.MONTH, period);							//month�� 0~11 �̹Ƿ� -1 +1 ���ϸ� "0"���� ���´�
		String monthTemp = String.valueOf(c.get(Calendar.MONTH)+1);
		String dayTemp = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
		resultDate = String.valueOf(c.get(Calendar.YEAR))+((monthTemp.length()==1)?0+monthTemp:monthTemp)+((dayTemp.length()==1)?0+dayTemp:dayTemp);
		//System.out.println("resultDate_addMOnth"+resultDate);
		return resultDate;		
	}
	//�� ������ ����ó����
	public static String addDay(String inputDate, int period){
		String currentdate = Utility.replace(inputDate,"-");
		Calendar c = Calendar.getInstance();
		String resultDate = new String();
				
		int s_yy = Integer.parseInt(currentdate.substring(0,4));
		int s_mm = Integer.parseInt(currentdate.substring(4,6));
		int s_dd = Integer.parseInt(currentdate.substring(6,8));
		c.set(Calendar.YEAR, s_yy);
		c.set(Calendar.MONTH, s_mm-1);
		c.set(Calendar.DAY_OF_MONTH, s_dd);
		
		c.add(Calendar.DAY_OF_MONTH, period);							
		String monthTemp = String.valueOf(c.get(Calendar.MONTH)+1);
		String dayTemp = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
		resultDate = String.valueOf(c.get(Calendar.YEAR))+((monthTemp.length()==1)?0+monthTemp:monthTemp)+((dayTemp.length()==1)?0+dayTemp:dayTemp);
		
		return resultDate;		
	}
	//�ش���� �������ڸ� ������ (��:5��->31��, 2��->28 or 29��)
	public static String getEndDay(String inputDate){
		String currentdate = Utility.replace(inputDate,"-");
		Calendar c = Calendar.getInstance();
		String resultDate = new String();
		
		int s_yy = Integer.parseInt(currentdate.substring(0,4));
		int s_mm = Integer.parseInt(currentdate.substring(4,6));
		int s_dd = Integer.parseInt(currentdate.substring(6,8));
		//System.out.println("s_mm"+s_mm);
		//System.out.println("s_dd"+s_dd);
		c.set(Calendar.YEAR, s_yy);
		c.set(Calendar.MONTH, s_mm-1);
		c.set(Calendar.DAY_OF_MONTH, s_dd);
		
		resultDate = String.valueOf(c.getActualMaximum(Calendar.DAY_OF_MONTH));
		//System.out.println("resultDate"+resultDate);		
		return resultDate;
	}
	//�ش���� 1���� ��ġ�ϴ� �������� ���� (��:�ش���� 1���� �������ΰ�� 4�� ������)
	public static String getFirstDayOfMonth(String inputDate){
		String currentdate = Utility.replace(inputDate,"-");
		Calendar c = Calendar.getInstance();
		String resultDate = new String();
		
		int s_yy = Integer.parseInt(currentdate.substring(0,4));
		int s_mm = Integer.parseInt(currentdate.substring(4,6));
		int s_dd = 1;
		
		c.set(Calendar.YEAR, s_yy);
		c.set(Calendar.MONTH, s_mm-1);
		c.set(Calendar.DAY_OF_MONTH, s_dd);
		
		resultDate = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
		
		return resultDate;		
	}
	//���������� yyyymmdd �� ������
	public static String getCurrentDate(){
		Calendar c = Calendar.getInstance();
		String resultDate = new String();
		
		String monthTemp = String.valueOf(c.get(Calendar.MONTH)+1);
		String dayTemp = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
		
		resultDate = String.valueOf(c.get(Calendar.YEAR))+((monthTemp.length()==1)?0+monthTemp:monthTemp)+((dayTemp.length()==1)?0+dayTemp:dayTemp);
		
		return resultDate;
	}
	//��¥������ ���� ������ ���ڸ��� �ΰ�� �տ� "0"�� ���� (��: 1 => 01 )
	public static String addFirstZero(int inputNumber){
		String resultDate = new String();
		resultDate = String.valueOf(inputNumber);
		if(resultDate.length()==1){
			resultDate = "0"+resultDate;
		}
		return resultDate;
	}
	//�ش���� �������� ������
	public static int getWeekCnt(String inputDate){
		String currentdate = Utility.replace(inputDate,"-");
		Calendar c=Calendar.getInstance() ;

		int s_yy = Integer.parseInt(currentdate.substring(0,4));
		int s_mm = Integer.parseInt(currentdate.substring(4,6));
		int s_dd = Integer.parseInt(currentdate.substring(6,8));
		c.set(Calendar.YEAR, s_yy);
		c.set(Calendar.MONTH, s_mm-1);
		c.set(Calendar.DAY_OF_MONTH, s_dd);
		return c.getActualMaximum(Calendar.WEEK_OF_MONTH);
	}
	//�ش���� ù°���� ������ ���� 1���� ù°���̴�....-,.-; (��:yyyymmdd �� ������)
	public static String getFirstDateInMonth(String inputDate){
		String currentdate = Utility.replace(inputDate,"-");
		String resultDate = new String();
		resultDate = currentdate.substring(0,6)+"01";
		return resultDate;
	}
	//�ش���� ���������� ������ yyymmdd �������...
	public static String getEndDateInMonth(String inputDate){
		String currentdate = Utility.replace(inputDate,"-");
		Calendar c = Calendar.getInstance();
		String resultDate = new String();
				
		int s_yy = Integer.parseInt(currentdate.substring(0,4));
		int s_mm = Integer.parseInt(currentdate.substring(4,6));
		int s_dd = Integer.parseInt(currentdate.substring(6,8));
		c.set(Calendar.YEAR, s_yy);
		c.set(Calendar.MONTH, s_mm-1);
		c.set(Calendar.DAY_OF_MONTH, s_dd);
		
		String monthTemp = String.valueOf(c.get(Calendar.MONTH)+1);
		String dayTemp = String.valueOf(c.getActualMaximum(Calendar.DAY_OF_MONTH));
		resultDate = String.valueOf(c.get(Calendar.YEAR))+((monthTemp.length()==1)?0+monthTemp:monthTemp)+dayTemp;
		
		return resultDate;
	}  
    
}