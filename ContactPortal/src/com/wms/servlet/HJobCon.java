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
import java.util.Hashtable;

public class HJobCon extends WmsServlet{

	protected void performTask(HttpServletRequest req, HttpServletResponse res)	throws Exception {
		try {
			Box box = HttpUtility.getBox(req);
			String _ACT=box.get("_ACT");
			String _SCREEN=box.get("_SCREEN");
			HttpSession session = req.getSession();
			UserInfo user =(UserInfo)session.getAttribute("user");
			String page=_SCREEN;
			if(_ACT.equals("LR")){
				//직무표LIST 가져오기
				String empId = box.get("empId");
				if(empId==null||empId.equals("")){
					empId = user.empId;
				}
				IHJob ihjob = (IHJob)DAOFactory.getDAO("IHJob");
				HJobDTO[] returns = ihjob.selectHJobList(empId);
				req.setAttribute("returns", returns);
				printJspPage(req,res,page);
			}else if(_ACT.equals("CLR")){
				//직무표 승인 LIST 가져오기
				String empId = box.get("empId");
				if(empId==null||empId.equals("")){
					empId = user.empId;
				}
				IHJob ihjob = (IHJob)DAOFactory.getDAO("IHJob");
				HJobDTO[] returns = ihjob.selectHJobConfList(empId);
				req.setAttribute("returns", returns);				
				printJspPage(req,res,page);
			}else if(_ACT.equals("R")){
				//직무표 한개 가져오기
				String empId = box.get("empId");
				if(empId==null||empId.equals("")){
					empId = user.empId;
				}
				HJobDTO returns = new HJobDTO();
				IHJob ihjob = (IHJob)DAOFactory.getDAO("IHJob");
				returns = ihjob.selectHJob(empId, box.get("revisionNo"), box.get("statusFlag"));
				System.out.println("confirm::"+box.get("confirmYN"));
				req.setAttribute("confirmYN",box.get("confirmYN"));
				req.setAttribute("returns",returns);				
				printJspPage(req,res,page);	
			}else if(_ACT.equals("D")){
				//직무표 한개 삭제하기
				String empId = box.get("empId");
				String revisionNo = box.get("revisionNo");
				String statusFlag = box.get("statusFlag");
				boolean returns = false;
				IHJob ihjob = (IHJob)DAOFactory.getDAO("IHJob");
				if(statusFlag.equals("JB0")||statusFlag.equals("JC0")){
					throw new Exception("작성완료문서와 승인완료 문서는 삭제할 수 없습니다. 상태를 작성중으로 바꾼후 삭제하시기 바랍니다.");
				}
				returns = ihjob.deleteHJob(empId, revisionNo, statusFlag);
				if(!returns){
					throw new Exception("문서삭제중 장애가 발생되었습니다. 상태를 확인하시기 바랍니다.");
				}
				req.setAttribute("returns",new Boolean(returns));				
				progressMessagePage(req,res,page);	
			}else if(_ACT.equals("U")){
				//직무표 변경
				HJobDTO dto = new HJobDTO();
				box.copyToEntity(dto);
				int selectedIndex = Integer.parseInt(box.get("selectedIndex"));
				
				System.out.println("1::"+selectedIndex);
				HJobCdDTO[] hJobCdDTOs=(HJobCdDTO[])box.copyToEntities("com.wms.beans.dto.HJobCdDTO");
				CoJobDTO[] coJobDTOs = (CoJobDTO[])box.copyToEntities("com.wms.beans.dto.CoJobDTO");
				com.wms.fw.Utility.fixNullAndTrimAll(hJobCdDTOs);
				com.wms.fw.Utility.fixNullAndTrimAll(coJobDTOs);
				System.out.println("2::"+hJobCdDTOs.length);
				System.out.println("3::"+coJobDTOs.length);
				dto.hJobCdDTOs = hJobCdDTOs;
				dto.coJobDTOs = coJobDTOs;
				System.out.println(box);
				
				IHJob ihjob = (IHJob)DAOFactory.getDAO("IHJob");
				boolean returns = false;
				returns = ihjob.updateHJob(dto,selectedIndex);
				if(!returns){
					throw new Exception("직무표 수정에서 장애가 발생하였습니다.");
				}
				req.setAttribute("returns",new Boolean(returns));				
				progressMessagePage(req,res,page);	
			}else if(_ACT.equals("I")){
				//직무표 생성
				HJobDTO dto = new HJobDTO();				
				box.copyToEntity(dto);
				int selectedIndex = Integer.parseInt(box.get("selectedIndex"));
				System.out.println("1::"+selectedIndex);
				HJobCdDTO[] hJobCdDTOs=(HJobCdDTO[])box.copyToEntities("com.wms.beans.dto.HJobCdDTO");
				CoJobDTO[] coJobDTOs = (CoJobDTO[])box.copyToEntities("com.wms.beans.dto.CoJobDTO");
				com.wms.fw.Utility.fixNullAndTrimAll(hJobCdDTOs);
				com.wms.fw.Utility.fixNullAndTrimAll(coJobDTOs);
				System.out.println("2::"+hJobCdDTOs.length);
				System.out.println("3::"+coJobDTOs.length);
				dto.hJobCdDTOs = hJobCdDTOs;
				dto.coJobDTOs = coJobDTOs;
				System.out.println(box);

				IHJob ihjob = (IHJob)DAOFactory.getDAO("IHJob");
				boolean returns = false;
				returns = ihjob.createHJob(dto,selectedIndex,user.empId);
				if(!returns){
					throw new Exception("직무표 신규작성에서 장애가 발생하였습니다. 동일한 직무코드가 있는지 확인하시기 바랍니다.");
				}
				req.setAttribute("returns",new Boolean(returns));				
				progressMessagePage(req,res,page);	
			}else if(_ACT.equals("TJLR")){
				//팀별직무 검색
				IHJob ihjob = (IHJob)DAOFactory.getDAO("IHJob");
				Hashtable returns = new Hashtable();
				returns = ihjob.teamJobSearch(box.get("empId"));
				req.setAttribute("returns",returns);
				printJspPage(req,res,page);
			}else if(_ACT.equals("CoJobLR")){
				//합동직무자 검색
				String empId = box.get("empId");
				if(empId==null||empId.equals("")){
					empId = user.empId;
				}
				IHJob ihjob = (IHJob)DAOFactory.getDAO("IHJob");
				EmpDTO[] returns = ihjob.coJobSearch(empId);
				req.setAttribute("returns",returns);
				printJspPage(req,res,page);
			}else if(_ACT.equals("C")){
				//직무표 승인
				HJobDTO dto = new HJobDTO();
				box.copyToEntity(dto);
				int selectedIndex = Integer.parseInt(box.get("selectedIndex"));
				System.out.println("box::"+box);
				System.out.println("1::"+selectedIndex);
				HJobCdDTO[] hJobCdDTOs=(HJobCdDTO[])box.copyToEntities("com.wms.beans.dto.HJobCdDTO");
				CoJobDTO[] coJobDTOs = (CoJobDTO[])box.copyToEntities("com.wms.beans.dto.CoJobDTO");
				com.wms.fw.Utility.fixNullAndTrimAll(hJobCdDTOs);
				com.wms.fw.Utility.fixNullAndTrimAll(coJobDTOs);
				System.out.println("2::"+hJobCdDTOs.length);
				System.out.println("3::"+coJobDTOs.length);
				dto.hJobCdDTOs = hJobCdDTOs;
				dto.coJobDTOs = coJobDTOs;
				System.out.println(box);
				
				IHJob ihjob = (IHJob)DAOFactory.getDAO("IHJob");
				boolean returns = false;
				returns = ihjob.confirmHJob(dto,selectedIndex,user.empId);
				if(!returns){
					throw new Exception("직무표 승인에서 장애가 발생하였습니다.");
				}
				req.setAttribute("returns",new Boolean(returns));				
				progressMessagePage(req,res,page);	
			}
		} catch(Exception e) {
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
            erorrMessagePage(req,res,e);
		}
	}
	
	
}
