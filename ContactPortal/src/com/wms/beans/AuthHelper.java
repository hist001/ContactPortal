/*************************************************************
*	파 일 명  : AuthHelper.java
*	작성일자  : 2005/02/23
*	작 성 자  : 조 원 호
*	내    용  : 인증/보안처리를 하는 Helper 클래스
*************************************************************/

package com.wms.beans;
import com.wms.beans.dto.OrgCdDTO;

public class AuthHelper
{   //isAuth(request.getParameter("empOrgCd"),dayJob.reEmpId,user.getMgrUnionFlag(),user.empOrgCd,user.empId,user.role,user.orgCds)
	public static boolean isAuth(String orgCd,String empId,String mgrFg,String usrOrgCd,String usrEmpId,String role,OrgCdDTO[] orgCds){
		String teamCd   =orgCd.substring(0,2);
		boolean transKey=true;
		transKey=(teamCd.equals("CA")||teamCd.equals("CD"))?false:true;

		//시작
		if(mgrFg.equals("GT")){mgrFg="T";}else if(mgrFg.equals("TE")){mgrFg="E";}

		if(!transKey){
			if(mgrFg.equals("Y")){//직원인 경우
				if(empId.equals(usrEmpId))
					transKey=true;
			}else if(mgrFg.equals("G")){//그룹장인 경우
				if(usrOrgCd.equals(orgCd))
					transKey=true;
			}
		}else{//영업부서가 아닌 경우
			transKey=false;

			if(mgrFg.equals("G")||mgrFg.equals("Y")){//그룹장과 직원의 권한은 같다.
				if(usrOrgCd.equals(orgCd))
					transKey=true;
			}
		}

		if(mgrFg.equals("T")){//팀장인 경우
			if(usrOrgCd.substring(0,2).equals(teamCd))
				transKey=true;
		}else if(mgrFg.equals("E")||mgrFg.equals("C")){//임원이상인 경우
				transKey=true;
		}
		//System.out.println("user.role::"+user.role);
		//2004.12.07 
		//허대영
		//인사담당자에게 R-POOL 인원 업무보고 조회 가능토록 하기 위하여 권한을 부여함
		if(role.equals("rpoolAdmin")) transKey=true;
		if(role.equals("admin"))transKey=true;
		//지정된 그룹이 존재한다면,
		if(orgCds!=null&&!transKey){
			for(int i=0;i<orgCds.length;i++){
				if(orgCd.equals(orgCds[i].grpOrgCd)){
					transKey=true;
					 break;
				}
			}
		}		

		return  transKey;
	}

}
