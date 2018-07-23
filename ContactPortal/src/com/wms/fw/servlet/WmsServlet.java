package com.wms.fw.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.wms.beans.dao.IGroupEmp;
import com.wms.fw.DAOFactory;
import com.wms.fw.servlet.Box;
import com.wms.fw.servlet.WMSBaseServlet;
import com.wms.beans.dto.UserInfo;
import com.wms.beans.dto.EmpDTO;
import com.wms.fw.Logger;

public abstract class WmsServlet extends WMSBaseServlet {
	//본업무수행전 전처리해주는 메서드		
	protected void performPreTask(
		HttpServletRequest req,
		HttpServletResponse res)
		throws Exception {
		//login처리...
		try{
		   Box box = HttpUtility.getBox(req);
           HttpSession session = req.getSession();
		   System.out.println("session_id::"+session.getId());
		   System.out.println("_DATA     ::"+req.getParameter("_DATA"));
		   System.out.println("_SCREEN   ::"+req.getParameter("_SCREEN"));
		   System.out.println("_ACT      ::"+req.getParameter("_ACT"));
           System.out.println("preformPreTask start");
		   UserInfo user = (UserInfo)session.getAttribute("user");
		   if(user==null){
			   System.out.println("세션생성");
				IGroupEmp groupEmp =(IGroupEmp)DAOFactory.getDAO("IGroupEmp");
				System.out.println("......data"+box.get("_DATA"));
				user=groupEmp.empLogin(box.get("_DATA"));
				if(user!=null){ 
				  //user=new UserInfo();
				  //user.setEmp(emp);
				  session.setAttribute("user",user);
				  System.out.println("name::"+user.empKName+"::WmsServlet");
				  req.setAttribute("loginFlag","Y");
				  System.out.println("servlet loginFlag 등록");
				}else{
				  throw new Exception("Login을 실패했습니다.");
				}
		   }else{
			   String empId = user.empId;
			   String _DATA = box.get("_DATA");
			   boolean updateKey=false;
			   if(empId!=null){
				   if(!empId.equals(_DATA.trim())&&!_DATA.trim().equals(""))
					   updateKey=true;
			   }else{
				   if(!_DATA.trim().equals(""))
					   updateKey=true;
			   }
			   if(updateKey){
				   System.out.println("login된 id의 정보가 변경되었습니다. ::"+empId+"==>"+_DATA);
                   session.invalidate();
                   session=req.getSession();
				   System.out.println("세션생성");
				   IGroupEmp groupEmp =(IGroupEmp)DAOFactory.getDAO("IGroupEmp");
				   System.out.println("......data"+box.get("_DATA"));
				   user=groupEmp.empLogin(box.get("_DATA"));
				   if(user!=null){ 
					  //user=new UserInfo();
					  //user.setEmp(emp);
					  session.setAttribute("user",user);
					  System.out.println("name::"+user.empKName+"::WmsServlet");
					  req.setAttribute("loginFlag","Y");
					  System.out.println("servlet loginFlag 등록");
				   }else{
					  throw new Exception("Login을 실패했습니다.");
				   }

			   }


		   }
		   if(!user.isLogin()){
			   throw new Exception("불법적인 접근입니다.");
		   }

		} catch(Exception e) {
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
            erorrMessagePage(req,res,e);
		}
		req.setCharacterEncoding("ksc5601");
		performTask(req,res);
	}

	protected void printMessagePage(
		HttpServletRequest req,
		HttpServletResponse res,
		String user_msg,
		String debug_msg) {
	}
	//에러를 처리하는 메서드
	protected void erorrMessagePage(
		HttpServletRequest req,
		HttpServletResponse res,Exception e) {
		//Exception처리....
		req.setAttribute("e",e);
		printJspPage(req,res,"/common/error.jsp");
	}
	//진행을 수행하는 메서드
	protected void progressMessagePage(
		HttpServletRequest req,
		HttpServletResponse res,
		String page){
		req.setAttribute("movePage",page);
		printJspPage(req,res,"/common/progress.jsp");
	}
	protected void printPopupMessagePage(
		HttpServletRequest req,
		HttpServletResponse res,
		String user_msg,
		String debug_msg) {

	}
	//실제 업무를 수행하는 메서드
	protected abstract void performTask (HttpServletRequest req, HttpServletResponse res) throws Exception;
}