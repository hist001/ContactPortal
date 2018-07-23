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
	//������������ ��ó�����ִ� �޼���		
	protected void performPreTask(
		HttpServletRequest req,
		HttpServletResponse res)
		throws Exception {
		//loginó��...
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
			   System.out.println("���ǻ���");
				IGroupEmp groupEmp =(IGroupEmp)DAOFactory.getDAO("IGroupEmp");
				System.out.println("......data"+box.get("_DATA"));
				user=groupEmp.empLogin(box.get("_DATA"));
				if(user!=null){ 
				  //user=new UserInfo();
				  //user.setEmp(emp);
				  session.setAttribute("user",user);
				  System.out.println("name::"+user.empKName+"::WmsServlet");
				  req.setAttribute("loginFlag","Y");
				  System.out.println("servlet loginFlag ���");
				}else{
				  throw new Exception("Login�� �����߽��ϴ�.");
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
				   System.out.println("login�� id�� ������ ����Ǿ����ϴ�. ::"+empId+"==>"+_DATA);
                   session.invalidate();
                   session=req.getSession();
				   System.out.println("���ǻ���");
				   IGroupEmp groupEmp =(IGroupEmp)DAOFactory.getDAO("IGroupEmp");
				   System.out.println("......data"+box.get("_DATA"));
				   user=groupEmp.empLogin(box.get("_DATA"));
				   if(user!=null){ 
					  //user=new UserInfo();
					  //user.setEmp(emp);
					  session.setAttribute("user",user);
					  System.out.println("name::"+user.empKName+"::WmsServlet");
					  req.setAttribute("loginFlag","Y");
					  System.out.println("servlet loginFlag ���");
				   }else{
					  throw new Exception("Login�� �����߽��ϴ�.");
				   }

			   }


		   }
		   if(!user.isLogin()){
			   throw new Exception("�ҹ����� �����Դϴ�.");
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
	//������ ó���ϴ� �޼���
	protected void erorrMessagePage(
		HttpServletRequest req,
		HttpServletResponse res,Exception e) {
		//Exceptionó��....
		req.setAttribute("e",e);
		printJspPage(req,res,"/common/error.jsp");
	}
	//������ �����ϴ� �޼���
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
	//���� ������ �����ϴ� �޼���
	protected abstract void performTask (HttpServletRequest req, HttpServletResponse res) throws Exception;
}