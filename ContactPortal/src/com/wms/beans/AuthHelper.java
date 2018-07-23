/*************************************************************
*	�� �� ��  : AuthHelper.java
*	�ۼ�����  : 2005/02/23
*	�� �� ��  : �� �� ȣ
*	��    ��  : ����/����ó���� �ϴ� Helper Ŭ����
*************************************************************/

package com.wms.beans;
import com.wms.beans.dto.OrgCdDTO;

public class AuthHelper
{   //isAuth(request.getParameter("empOrgCd"),dayJob.reEmpId,user.getMgrUnionFlag(),user.empOrgCd,user.empId,user.role,user.orgCds)
	public static boolean isAuth(String orgCd,String empId,String mgrFg,String usrOrgCd,String usrEmpId,String role,OrgCdDTO[] orgCds){
		String teamCd   =orgCd.substring(0,2);
		boolean transKey=true;
		transKey=(teamCd.equals("CA")||teamCd.equals("CD"))?false:true;

		//����
		if(mgrFg.equals("GT")){mgrFg="T";}else if(mgrFg.equals("TE")){mgrFg="E";}

		if(!transKey){
			if(mgrFg.equals("Y")){//������ ���
				if(empId.equals(usrEmpId))
					transKey=true;
			}else if(mgrFg.equals("G")){//�׷����� ���
				if(usrOrgCd.equals(orgCd))
					transKey=true;
			}
		}else{//�����μ��� �ƴ� ���
			transKey=false;

			if(mgrFg.equals("G")||mgrFg.equals("Y")){//�׷���� ������ ������ ����.
				if(usrOrgCd.equals(orgCd))
					transKey=true;
			}
		}

		if(mgrFg.equals("T")){//������ ���
			if(usrOrgCd.substring(0,2).equals(teamCd))
				transKey=true;
		}else if(mgrFg.equals("E")||mgrFg.equals("C")){//�ӿ��̻��� ���
				transKey=true;
		}
		//System.out.println("user.role::"+user.role);
		//2004.12.07 
		//��뿵
		//�λ����ڿ��� R-POOL �ο� �������� ��ȸ ������� �ϱ� ���Ͽ� ������ �ο���
		if(role.equals("rpoolAdmin")) transKey=true;
		if(role.equals("admin"))transKey=true;
		//������ �׷��� �����Ѵٸ�,
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
