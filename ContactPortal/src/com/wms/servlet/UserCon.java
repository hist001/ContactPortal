/*************************************************************
*	�� �� ��  : UserCon.java
*	�ۼ�����  : 2004/06/22
*	�� �� ��  : 
*	��    ��  : ����� �α��ΰ��� ���� �ϴ� Ŭ����
*************************************************************/
package com.wms.servlet;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.wms.fw.servlet.*;
import com.wms.fw.*;
import com.wms.beans.dto.*;
import com.wms.beans.dao.*;
public class UserCon extends WmsServlet{
	//������������ ��ó�����ִ� �޼���		
	protected void performPreTask(
		HttpServletRequest req,
		HttpServletResponse res)
		throws Exception {
		//loginó��...
		req.setCharacterEncoding("ksc5601");
		performTask(req,res);
	}
	protected void performTask(HttpServletRequest req, HttpServletResponse res)
		throws Exception {
		try {
			Box box = HttpUtility.getBox(req);
			String _ACT=box.get("_ACT");
			String _SCREEN=box.get("_SCREEN");			
			HttpSession session = req.getSession();
			String page=_SCREEN;
			if(_ACT.equals("T")){//kms���� wms���� �̰��� ���....
			    //String remoteAddr=req.getRemoteAddr();
				//if(!(new Configuration()).get("com.wms.remoteAddr").trim().equals(remoteAddr.trim()))
					//throw new Exception("��ϵ� ���������� ������ �ƴմϴ�.");
		
				IGroupEmp groupEmp =(IGroupEmp)DAOFactory.getDAO("IGroupEmp");
				UserInfo user=groupEmp.empLogin(box.get("_DATA"));
				System.out.println("session_id::"+session.getId());
		   		if(user!=null){ 
				  //UserInfo user=new UserInfo();
				  //com.wms.fw.Utility.fixNullAndTrimAll(emp);
				  //user.setEmp(emp);

				  session.setAttribute("user",user);
				  System.out.println("name::"+user.empKName);
				  System.out.println(page);

				}else{
				  System.out.println("login_end");	

					throw new Exception("Data������ �����߽��ϴ�.");
				}
			}
			//ȭ���� ��������ִ� �޼���
			progressMessagePage(req,res,page);
		} catch(Exception e) {
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
            erorrMessagePage(req,res,e);
		}
	}
	
	
}
