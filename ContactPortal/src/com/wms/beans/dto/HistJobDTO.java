package com.wms.beans.dto;
import com.wms.fw.*;

public class HistJobDTO extends GeneralDTO 
{
	public String jobCd;		//직무코드
	public String jobName;    //직무명
	public String jobTitle;       //
	public String actDs;        //수행형태
	public String bizNo;	      //사업번호
	public String superJobCd;	      //상위직무코드 2006.06.14 add
	public String bizName;   //사업명
	public String useFlag;    //사용유무
	
	public int      totCnt=0;//총 레코드 수 (페이징)
		
	public TeamJobDTO[] teamjobs; //팀별직무목록
	public EmpJobDTO[] empjobs; //팀별직무목록
};