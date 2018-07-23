package com.wms.fw.servlet;
import java.util.LinkedList;
import java.util.Hashtable;
import java.util.Enumeration;
import com.wms.fw.DTO;

public class PageInfo implements java.io.Serializable{

	private Object[] list;
	
	private int pageNum;
	private int pageSize;
	private int totalCnt;
	private int disPageMaxCnt=10;
	private int pageInitNum;

	private DTO key;

	public PageInfo(){
		pageNum=1;
		pageSize=10;
	}

	public PageInfo(Object[] list,int pageNum,int pageSize, int totalCnt,DTO key){
		this.list = list;
		this.pageNum=pageNum;
		this.pageSize=pageSize;
//		this.pageInitNum=pageInitNum;
		this.totalCnt=totalCnt;
		com.wms.fw.Utility.fixNull(key);
		this.key =key;
	}
	public int size(){
		if(list==null)
			return 0;
		return list.length;
	}
   
	public void setPageCnt(int pageNum){
		this.pageNum = pageNum;
	}
	public void setPageSize(int pageSize){
		this.pageSize = pageSize;
	}	
	public void setTotalCnt(int totalCnt){
		this.totalCnt = totalCnt;
	}

	public void setKey(DTO key){
		com.wms.fw.Utility.fixNull(key);
		this.key=key;
	}
	public String toString(){
		Hashtable keyList =key.getList();
		Enumeration e = keyList.keys();
		StringBuffer returns=null;
		String keyName=null;
		String keyValue=null;
		boolean flag=false;
		if(e.hasMoreElements()){
			returns=new StringBuffer();
			do {
					keyName = (String)e.nextElement();
					keyValue  =  (String)keyList.get(keyName);
					if(!keyValue.trim().equals("")){
						returns.append("<br> <font color='green'>�˻�������</font> <font color='red'>"+keyName+"</font>(��)�� <font color='green'>�˻���</font>�� <font color='blue'>"+keyValue+"</font>");
						flag=true;
					}
			}while(e.hasMoreElements());
		}
		if(flag)
			returns.append("<br> (��)�� �˻��߽��ϴ�.");
		return returns.toString();		
	}
	public void setList(Object[] list){
		this.list = list;
	}

	public void setPageInitNum(int i){
		this.pageInitNum = i;
	}

	public Object[] getList(){
		return this.list;
	}

	//�� �������� �����ִ� ���ڵ� ��
	public int getPageSize(){
		return pageSize;
	}

	//��ü ���ڵ� ��
	public int getTotalCnt(){
		return totalCnt;
	}

	//���� ������ ��ȣ
	public int getPageNum(){
		return pageNum;
	}

	//��ü ������ ��
	public int getTotalPage(){
		return (totalCnt%pageSize == 0)?totalCnt/pageSize:totalCnt/pageSize+1;
	}
	
	//�� ȭ�鿡 �����ְ��� �ϴ� �ִ� �������� -10���� setting�Ǿ� ����
	public int getDisPageMaxCnt(){
		return disPageMaxCnt;
	}
	
	//�� ȭ�鿡 ������ �� �ִ� ������ ��
	public int[] getDisPageCnt(){
		int[] returns = new int[2];
		returns[0] = ((this.pageNum-1)/this.disPageMaxCnt)*this.disPageMaxCnt+1;
		if((returns[0]+this.disPageMaxCnt-1)<=this.getTotalPage()){
			returns[1] = returns[0]+this.disPageMaxCnt-1;
		}else{
			returns[1] = this.getTotalPage();
		}
		return returns;
	}

	//i��°�� �ش��ϴ� ��ü
	public Object get(int i){
		return list[i];
	}
	public DTO getDTO(){
		return key;
	}
}