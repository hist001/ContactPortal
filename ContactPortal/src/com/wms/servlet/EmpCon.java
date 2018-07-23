/*************************************************************
*	�� �� ��  : EmpCon.java
*	�ۼ�����  : 2004/06/22
*	�� �� ��  : 
*	��    ��  : ������������/���ϱ��°��� ����(CRUD)ó�� Ŭ����
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
					throw new Exception("��������� ��ְ� �߻��߽��ϴ�.");
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
					throw new Exception("��������ó������ ��ְ� �߻��Ǿ����ϴ�.<br>��������ó���� ��������� �Ǿ� �ִ°�� <br>����������� ���� �����Ͻñ� �ٶ��ϴ�.");
				}
				req.setAttribute("returns",returns);				
				progressMessagePage(req,res,page);	
			}else if(_ACT.equals("D")){//������ ���� ���� ���� ����ó���� ���� ���� ������
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
				//����ߺ�üũ
				//DupCheck dc = new DupCheck();
				boolean result = DupCheck.dupCheck(box.get("key"),box.get("tableName"),box.get("value"));
				Boolean returns = new Boolean(result);
				req.setAttribute("returns",returns);				
				printJspPage(req,res,page);			
			}else if(_ACT.equals("DLR")){
				String highEmpId = new String();
				String role = new String();
				role = user.role;
				//�����ó��
				if(user.apKey){
					highEmpId = user.aprvEmpId;
				}else{
					highEmpId = user.empId;
				}
				//���氡�ɽð�ó��(040813 ���� 10�ñ�����)
				String nowTime = DateUtil.getShortTimeString(); //HHmmss
				System.out.println("����ð���::"+nowTime);
				if(user.role.equals("admin")){
					//admin1�� ��� �ð��� ������ ���氡����
				}else{
					if(Integer.parseInt(nowTime.substring(0,4)) >= 2345){ //�ð��� ���� ������ ��
						throw new Exception("���������� ���º��� �ð��� �������ϴ�.<br>���º����� ���� 23��45���̳��� �����մϴ�.");
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
					throw new Exception("����������º��� ���忡�� ��ְ� �߻��Ͽ����ϴ�.");
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
