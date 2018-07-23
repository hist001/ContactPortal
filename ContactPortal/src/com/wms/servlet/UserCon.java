/*************************************************************
*	파 일 명  : UserCon.java
*	작성일자  : 2004/06/22
*	작 성 자  : 
*	내    용  : 사용자 로그인관련 제어 하는 클래스
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
	//본업무수행전 전처리해주는 메서드		
	protected void performPreTask(
		HttpServletRequest req,
		HttpServletResponse res)
		throws Exception {
		//login처리...
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
			if(_ACT.equals("T")){//kms에서 wms으로 이관할 경우....
			    //String remoteAddr=req.getRemoteAddr();
				//if(!(new Configuration()).get("com.wms.remoteAddr").trim().equals(remoteAddr.trim()))
					//throw new Exception("등록된 서버에서의 접근이 아닙니다.");
		
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

					throw new Exception("Data전송을 실패했습니다.");
				}
			}
			//화면을 진행시켜주는 메서드
			progressMessagePage(req,res,page);
		} catch(Exception e) {
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
            erorrMessagePage(req,res,e);
		}
	}
	
	
}
