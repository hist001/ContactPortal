/*************************************************************
*	파 일 명  : EmpCon.java
*	작성일자  : 2004/06/22
*	작 성 자  : 
*	내    용  : 직원정보관리/당일근태관리 제어(CRUD)처리 클래스
*************************************************************/
package com.wms.servlet;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.wms.fw.servlet.*;
import com.wms.fw.*;
import com.wms.beans.dto.*;
import com.wms.beans.dao.*;
import com.wms.beans.dao.AllSearch;
import java.util.*;
import com.wms.fw.util.DateUtil;
public class EmpCon extends WmsServlet{

	protected void performTask(HttpServletRequest req, HttpServletResponse res)	throws Exception {
		try {
			Box box = HttpUtility.getBox(req);
			String _ACT=box.get("_ACT");
			String _SCREEN=box.get("_SCREEN");
			HttpSession session = req.getSession();
			UserInfo user =(UserInfo)session.getAttribute("user");
			String page=_SCREEN;
			
			if(_ACT.equals("I")){
				boolean result = false;
				BDataEmpDTO dto = new BDataEmpDTO();
				box.copyToEntity(dto);
				System.out.println("---"+dto.dutyOrgCd1+dto.dutyOrgName1);
				IBDataEmp ibd = (IBDataEmp)DAOFactory.getDAO("IBDataEmp");
				result = ibd.createBDataEmp(dto);
				if(!result){
					throw new Exception("직원등록중 장애가 발생했습니다.");
				}
				req.setAttribute("returns", new Boolean(result));				
				progressMessagePage(req,res,page);
			}else if(_ACT.equals("U")){
				BDataEmpDTO dto = new BDataEmpDTO();
				boolean result = false;
				box.copyToEntity(dto);
				IBDataEmp ibd = (IBDataEmp)DAOFactory.getDAO("IBDataEmp");
				result = ibd.updateBDataEmp(dto);
				Boolean returns = new Boolean(result);
				if(returns.booleanValue()==false){
					throw new Exception("직원정보처리에서 장애가 발생되었습니다.<br>직원사직처리시 대결자지정 되어 있는경우 <br>대결자지정을 먼저 해제하시기 바랍니다.");
				}
				req.setAttribute("returns",returns);				
				progressMessagePage(req,res,page);	
			}else if(_ACT.equals("D")){//직원은 삭제 안함 단지 사직처리함 따라서 현재 사용안함
				BasicDataDTO dto = new BasicDataDTO();
				int returns = 0;
				dto.set(req);
				String[] pks = new String[1];
				pks[0] = "empId";
				IBasicData ibd = (IBasicData)DAOFactory.getDAO("IBasicData");
				returns = ibd.deleteBasicData(dto,pks);
				req.setAttribute("returns", String.valueOf(returns));
				progressMessagePage(req,res,page);				
			}else if(_ACT.equals("R")){//Not ONLY ONE
				String empId = box.get("empId");
				BDataEmpDTO returns = new BDataEmpDTO();
				IBDataEmp ibd = (IBDataEmp)DAOFactory.getDAO("IBDataEmp");
				returns = ibd.selectBDataEmp(empId);
				req.setAttribute("returns",returns);				
				printJspPage(req,res,page);
			}else if(_ACT.equals("LR")){//LIST
				BDataEmpDTO dto = new BDataEmpDTO();
				BDataEmpDTO[] returns = null ;
				box.copyToEntity(dto);
				IBDataEmp ibd = (IBDataEmp)DAOFactory.getDAO("IBDataEmp");
				returns = ibd.selectBDataEmpList(dto);
				com.wms.fw.Utility.fixNullAndTrimAll(returns);
				req.setAttribute("returns",returns);				
				printJspPage(req,res,page);
			}else if(_ACT.equals("CHK")){
				//사번중복체크
				//DupCheck dc = new DupCheck();
				boolean result = DupCheck.dupCheck(box.get("key"),box.get("tableName"),box.get("value"));
				Boolean returns = new Boolean(result);
				req.setAttribute("returns",returns);				
				printJspPage(req,res,page);			
			}else if(_ACT.equals("DLR")){
				String highEmpId = new String();
				String role = new String();
				role = user.role;
				//대결자처리
				if(user.apKey){
					highEmpId = user.aprvEmpId;
				}else{
					highEmpId = user.empId;
				}
				//변경가능시간처리(040813 현재 10시까지임)
				String nowTime = DateUtil.getShortTimeString(); //HHmmss
				System.out.println("현재시간은::"+nowTime);
				if(user.role.equals("admin")){
					//admin1의 경우 시간이 지나도 변경가능함
				}else{
					if(Integer.parseInt(nowTime.substring(0,4)) >= 2345){ //시간에 따라서 변경할 것
						throw new Exception("업무보고자 근태변경 시간이 지났습니다.<br>근태변경은 당일 23시45분이내만 가능합니다.");
					}  
				}  
				IBDataEmp ibd = (IBDataEmp)DAOFactory.getDAO("IBDataEmp"); 
				DayJobReportDTO[] returns = ibd.selectDiliList(highEmpId,role,box.get("reportDtFr"),box.get("reportDtTo"),box.get("s_crEmpId"),box.get("s_apEmpId"));
				req.setAttribute("returns",returns); 
				printJspPage(req,res,page); 
			}else if(_ACT.equals("DR")){
				DiliDTO returns = new DiliDTO();
				IBDataEmp ibd = (IBDataEmp)DAOFactory.getDAO("IBDataEmp");
				returns = ibd.selectDili(box.get("reportDt"), box.get("reEmpId"));
				req.setAttribute("returns",returns);
				printJspPage(req,res,page);
			}else if(_ACT.equals("DI")){
				boolean returns =false;
				DiliDTO dto = new DiliDTO();
				box.copyToEntity(dto);
				IBDataEmp ibd = (IBDataEmp)DAOFactory.getDAO("IBDataEmp");
				returns = ibd.saveDili(dto, user.empId);
				if(!returns){
					throw new Exception("업무보고상태변경 저장에서 장애가 발생하였습니다.");
				}
				req.setAttribute("returns",new Boolean(returns));
				printJspPage(req,res,page);
			}
		} catch(Exception e) {
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
            erorrMessagePage(req,res,e);
		}
	}
	
	
}
