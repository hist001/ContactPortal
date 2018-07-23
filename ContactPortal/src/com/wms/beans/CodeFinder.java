package com.wms.beans;
//서버메모리상에 한번등록되 중분류코드로 검색할수 있는 객체..
import java.util.Hashtable;
import com.wms.beans.dto.Code;
public class CodeFinder
{
	private Hashtable storage;
	public CodeFinder(){
		storage=new Hashtable();
	}
	/**
	 * 소분류 Code군을 리턴.
	 * @return Code[] 소분류 Code군
	 * @param key java.lang.String 검색할 중분류코드값 
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
	 * option태그로 구성된 소그룹 Code군 리턴.
	 * @return java.lang.String option태그로 구성된 소분류 Code군
	 * @param key java.lang.String 검색할 중분류코드값 
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
			if(!codeList[i].codeNo.equals("17")){//17는 삭제..
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
	 * 중분류 코드값을 key로 하는 소분류 Code군 등록.
	 * @param code com.wms.beans.dto.Code[] 소분류 Code군
	 * @param key java.lang.String 등록될 key인 중분류코드값 
	 */
	public void set(String key,Code[] code){
		storage.put(key,code);
	}
	/**
	 * 전체 코드군을 등록.
	 * @param code com.wms.beans.dto.Code[] 전체 소분류 Code군
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