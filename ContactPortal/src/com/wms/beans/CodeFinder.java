package com.wms.beans;
//�����޸𸮻� �ѹ���ϵ� �ߺз��ڵ�� �˻��Ҽ� �ִ� ��ü..
import java.util.Hashtable;
import com.wms.beans.dto.Code;
public class CodeFinder
{
	private Hashtable storage;
	public CodeFinder(){
		storage=new Hashtable();
	}
	/**
	 * �Һз� Code���� ����.
	 * @return Code[] �Һз� Code��
	 * @param key java.lang.String �˻��� �ߺз��ڵ尪 
	 */
	public Code[] get(String key){
		Object obj=storage.get(key);
		if (obj==null)		
			return null;		
		return (Code[])obj;
	}
	public Code searchCode(String key){

		Object obj=storage.get(key.substring(0,2));
		if (obj==null)		
			return null;		
		Code returns=null;
		Code[] codes=(Code[])obj;
		for(int i=0;i<codes.length;i++){
			if(codes[i].codeNo.equals(key)){
				returns = codes[i];
				break;
			}
		}
		return returns;
	}
	/**
	 * option�±׷� ������ �ұ׷� Code�� ����.
	 * @return java.lang.String option�±׷� ������ �Һз� Code��
	 * @param key java.lang.String �˻��� �ߺз��ڵ尪 
	 */
	public String getHtml(String key,String field){
		if(key==null)
			return null;
		Object obj=get(key);
		if (obj==null)		
			return null;		
		Code[] codeList=(Code[])obj;
		StringBuffer sb = new StringBuffer("");
		for(int i=0;i<codeList.length;i++){
			if(!codeList[i].codeNo.equals("17")){//17�� ����..
				sb.append(" <option value=");
				sb.append(codeList[i].codeNo);
				if(field!=null&&codeList[i].codeNo.equals(field))
				sb.append(" selected ");
				sb.append(">");
				sb.append(codeList[i].codeNo+" "+codeList[i].codeName);
				sb.append("</option> ");
			}
		}
		return sb.toString();
							
	}
	/**
	 * �ߺз� �ڵ尪�� key�� �ϴ� �Һз� Code�� ���.
	 * @param code com.wms.beans.dto.Code[] �Һз� Code��
	 * @param key java.lang.String ��ϵ� key�� �ߺз��ڵ尪 
	 */
	public void set(String key,Code[] code){
		storage.put(key,code);
	}
	/**
	 * ��ü �ڵ屺�� ���.
	 * @param code com.wms.beans.dto.Code[] ��ü �Һз� Code��
	 */
	public void register(Code[] code){
		int index=0;
		String key=code[index].preCodeNo;
	
		for(int i=0;i<code.length;i++){
			if(!key.equals(code[i].preCodeNo)){
				Code[] returns=new Code[i-index];
				System.arraycopy(code,index,returns,0,returns.length);
				set(key,returns);
				key=code[i].preCodeNo;
				index=i;
			}	
		}
		Code[] returns=new Code[code.length-index];
		System.arraycopy(code,index,returns,0,returns.length);
		set(key,returns);	
	}

}