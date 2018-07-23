package com.wms.fw.web.action;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import java.io.IOException;

import com.wms.fw.DAOFactory;
import com.wms.fw.Logger;
import com.wms.fw.servlet.Box;
import com.wms.fw.servlet.HttpUtility;
import com.wms.fw.servlet.WMSBaseServlet;
import com.wms.beans.dto.UserInfo;
import com.wms.beans.dto.EmpDTO;
import com.wms.beans.dao.IGroupEmp;

/**
 * This class is the Wms project implementation of the WebAction
 *
*/
public abstract class WmsActionImpl extends HTMLActionSupport {
    protected Box box;
	protected HttpSession session ;
    public void doStart(HttpServletRequest req,HttpServletResponse res){

		//login처리...
		try{
		   req.setCharacterEncoding("ksc5601");
		   box = HttpUtility.getBox(req);
           session = req.getSession();
		   System.out.println("session_id::"+session.getId());
		   UserInfo user = (UserInfo)session.getAttribute("user");
		   if(user==null){
				IGroupEmp groupEmp =(IGroupEmp)DAOFactory.getDAO("IGroupEmp");
				
				System.out.println("_DATA 를 Display : " + box.get("_DATA"));
				user=groupEmp.empLogin(box.get("_DATA"));
				if(user!=null){ 
				  //user=new UserInfo();
				  //user.setEmp(emp);
				  session.setAttribute("user",user);
				  req.setAttribute("loginFlag","Y");
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
                   session.invalidate();
                   session=req.getSession();
				   IGroupEmp groupEmp =(IGroupEmp)DAOFactory.getDAO("IGroupEmp");
				   user=groupEmp.empLogin(box.get("_DATA"));
				   if(user!=null){ 
					  //user=new UserInfo();
					  //user.setEmp(emp);
					  session.setAttribute("user",user);
					  req.setAttribute("loginFlag","Y");
				   }else{
					  throw new Exception("Login을 실패했습니다.");
				   }

			   }


		   }
		   if(!user.isLogin()){
			   throw new Exception("불법적인 접근입니다.");
		   }
		   
		   user.sabun = box.get("_DATA");
		
		} catch(Exception e) {
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
            erorrMessagePage(req,res,e);
		}

	}


}

