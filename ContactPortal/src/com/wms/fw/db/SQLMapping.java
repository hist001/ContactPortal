package com.wms.fw.db;
import java.util.Enumeration;

import com.wms.fw.GeneralDTO;
import com.wms.fw.Utility;
public class SQLMapping extends GeneralDTO {
	public String id;
	public int    cnt;
	public StringBuffer sql;
	public String  paramSql;
	public String  tempSql;
	public String  returnSql;
	public String url;

	int[] idxList    ;
	String[] values  ;
   
	public SQLMapping(){}
	public SQLMapping(String id,String cnt,String url,String sql)throws Exception{
		this.id = id;
		this.cnt = Integer.parseInt(cnt);
		this.url = url;		
		/*
		sql = Utility.replace(sql,  "&amp;"  ,"&");
		sql = Utility.replace(sql,  "&quot;" ,"\"");
		sql = Utility.replace(sql,  "&lt;"   ,"<");
		sql = Utility.replace(sql,  "&gt;"   ,">");
		*/
		this.sql = new StringBuffer(sql);		
		init(sql);
		paramSql 	= sql;
		tempSql  	= sql;
		returnSql  	= sql;
//		System.out.println("SQLMapping sql==>"+sql);
	}

	public void setStringParam(String param, String value)throws Exception{
		setParam(param,value);
	}
	public void setIntParam(String param, String value)throws Exception{
		setParamNum(param,value);
	}
	public void setLongParam(String param, String value)throws Exception{
		setParamNum(param,value);
	}
	public void setDoubleParam(String param, String value)throws Exception{
		setParamNum(param,value);
	}
	public void setFloatParam(String param, String value)throws Exception{
		setParamNum(param,value);
	}

	public void setParam(String param, String value)throws Exception{
		paramSql = Utility.replace(paramSql,  "["+ param + "]" ,"'"+value+"'");
		paramSql = Utility.replace(paramSql,   param + ":" ,"'"+value+"'");
		returnSql=paramSql;
	}
	public void setParamNum(String param, String value)throws Exception{
		paramSql = Utility.replace(paramSql,  "["+ param + "]" ,value);
		paramSql = Utility.replace(paramSql,   param + ":" ,value);
		returnSql=paramSql;
	}
	
	private void init(String sql)throws Exception{
		idxList = new int[cnt];
		values  = new String[cnt];
		int idx=0;
		for(int i=0;i<idxList.length;i++){
			idx=sql.indexOf(63,idx+1);
			idxList[i]=idx;
			values[i]="?";
		}		
	}
	public void set(int idx,String value)throws Exception{
		values[idx-1]=value;
	}
	public void setString(int idx,String value)throws Exception{            			
		set(idx,"'"+value+"'") ;
	}
	public void setInt(int idx,int value)throws Exception{            			
		set(idx,value+"") ;
	}
	public void setLong(int idx,long value)throws Exception{            			
		set(idx,value+"") ;
	}
	public void setDouble(int idx,double value)throws Exception{            			
		set(idx,value+"") ;
	}
	public void setFloat(int idx,float value)throws Exception{            			
		set(idx,value+"") ;
	}
    public String toSql()throws Exception{
		StringBuffer temp= new StringBuffer(sql.toString());
		if(cnt>0){
			int[] lengList   = new int[cnt];
			lengList[0]      = idxList[0];

			for(int i=1;i<idxList.length;i++){
				int idx=0;
				for(int j=0;j<i;j++)idx+=values[j].length()-1;			
				lengList[i]=idxList[i]+idx;
			}
			for(int i=0;i<cnt;i++)
				temp.replace(lengList[i], lengList[i]+1, values[i]) ;					
		}
		return temp.toString();
	}
	
	public String toString(){
		return sql.toString();
	}
	
	public String paramToString(){
		paramSql=tempSql;
		return returnSql.toString();
	}

    //Sql에서 Parameter를 자동 Mapping 처리  : 2008-12 )
    public String makeParamSql(GeneralDTO dto,SQLMapping sm) throws Exception{
    	Enumeration e = dto.getList().keys();
    	while(e.hasMoreElements()) {
    		String key = (String) e.nextElement();
    		Object value =  dto.getList().get(key);
    		sm.setStringParam(dto.getClass().getField(key).getName(), value.toString()); 
    	}    	
    	return sm.paramSql;         
    } 	
}


