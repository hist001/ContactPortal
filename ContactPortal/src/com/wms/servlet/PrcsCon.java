/*************************************************************
*	파 일 명  : PrcsCon.java
*	작성일자  : 2004/09/03
*	작 성 자  : 
*	내    용  : 공정 관련 제어 처리
*************************************************************/
package com.wms.servlet;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.StringTokenizer;
import com.wms.fw.servlet.*;
import com.wms.fw.*;
import com.wms.beans.dto.*;
import com.wms.beans.dao.*;
import com.wms.fw.util.*;

public class PrcsCon extends WmsServlet{

	protected void performTask(HttpServletRequest req, HttpServletResponse res)
		throws Exception {
		try {
			//공정 검색
			//공정 인원 검색
			//공정 원가 검색
			//
			Box box = HttpUtility.getBox(req);
			String _ACT=box.get("_ACT");
			String _SCREEN=box.get("_SCREEN");			
			HttpSession session = req.getSession();
			UserInfo user =(UserInfo)session.getAttribute("user");			
			if(_ACT.equals("I")){//공정 생성
				PrcsDTO insertDto = new PrcsDTO();
				box.copyToEntity(insertDto);
				IPrcs iprcs = (IPrcs)DAOFactory.getDAO("IPrcs");
				com.wms.fw.Utility.fixNullAndTrimAll(insertDto);
				int prcsdto = iprcs.add(insertDto);
			        
			        System.out.println("공정 insert 건수 = "+prcsdto);
				progressMessagePage(req,res,_SCREEN);
			}else if(_ACT.equals("ICP")){//공정코드를 생성하면서 내용까지 복사
				String prodNo = box.get("prodNo");
				String prcsNo = box.get("prcsNo");
				String prodType = box.get("prodType");
				String nPrcsNo = box.get("nPrcsNo");
				String nPrcsName = box.get("nPrcsName");
				
				IPrcs iprcs = (IPrcs)DAOFactory.getDAO("IPrcs");
				int prcsdto = iprcs.addCopyPrcs(prodNo, prcsNo, prodType, nPrcsNo, nPrcsName);
			        
			        System.out.println("공정 insert 건수 = "+prcsdto);
				progressMessagePage(req,res,_SCREEN);
			}else if(_ACT.equals("ICP2")){//공정코드는 변하지 않고 내용만 복사
				String prodNo = box.get("prodNo");
				String prodType = box.get("prodType");
				String copyPrcsNo = box.get("prcsNo");
				String[] copiedPrcsNo = req.getParameterValues("copiedPrcsNo");
				String[] chkFlags = req.getParameterValues("chkFlag");

				IPrcs iprcs = (IPrcs)DAOFactory.getDAO("IPrcs");
				int prcsdto = iprcs.updateCopyPrcs2(prodNo, prodType, copyPrcsNo, copiedPrcsNo, chkFlags);
				//int prcsdto = iprcs.deleteCopyPrcs2(prodNo, prodType, copiedPrcsNo, chkFlags);
				//int prcsdto = iprcs.addCopyPrcs2(prodNo, prodType, copyPrcsNo, copiedPrcsNo, chkFlags);

				progressMessagePage(req,res,_SCREEN);
			}else if(_ACT.equals("IUCOP")){//공정코드는 변하지 않고 내용만 복사
				String copiedProdNo = box.get("prodNo");//copiedProdNo, 공정이 복사될 제품(당제품)
				String prodNo = box.get("prodNo2");//복사될 공정이 있는 제품(타제품)
				String prodType = box.get("prodType");
				String[] copyPrcsNos = req.getParameterValues("copyPrcsNo");
				String[] chkFlags = req.getParameterValues("chkFlag");
				String empOrgCd = user.empOrgCd;

				IPrcs iprcs = (IPrcs)DAOFactory.getDAO("IPrcs");
				int prcsdto = iprcs.copyOuterPrcs(copiedProdNo,prodNo, prodType, copyPrcsNos, chkFlags,empOrgCd);

				progressMessagePage(req,res,_SCREEN);
			}else if(_ACT.equals("U")){//save
				System.out.println("prcscon 갱신....******");
				PrcsDTO updatedDto = new PrcsDTO();
				box.copyToEntity(updatedDto);
				IPrcs iprcs = (IPrcs)DAOFactory.getDAO("IPrcs");
				com.wms.fw.Utility.fixNullAndTrimAll(updatedDto);
				int prcsdto = iprcs.updatePrcs(updatedDto);
			        
			        System.out.println("공정 update 건수 = "+prcsdto);
				progressMessagePage(req,res,_SCREEN);
			}else if(_ACT.equals("D")){//save
				String prodNo = box.get("prodNo");
				String prcsNo = box.get("prcsNo");
				String prodType = box.get("prodType");
				IPrcs iprcs = (IPrcs)DAOFactory.getDAO("IPrcs");
				int prcsdto = iprcs.deletePrcs(prodNo, prcsNo, prodType);
			    if(prcsdto==0){
					throw new Exception("삭제된 공정이 없습니다. 일일업무보고에 실적이 있는경우 공정을 삭제할 수 없습니다.");
				}    
		        System.out.println("공정 delete 건수 = "+prcsdto);
				progressMessagePage(req,res,_SCREEN);
			}else if(_ACT.equals("LR")){//해당 제품의 공정 List
				String empId = box.get("empId"); 
				String prodNo = box.get("prodNo");
				String prodType = box.get("prodType");
				String prodNo2 = box.get("prodNo2");//공정복사 화면의 공정번호
				System.out.println("prodNo2.....="+prodNo2);
				if(prodNo2!=null & !prodNo2.trim().equals("")){
					prodNo = prodNo2;
				}

				IPrcs iprcs = (IPrcs)DAOFactory.getDAO("IPrcs");
				//admin 기능 추가에 따른 user.empId 추가
				PrcsDTO[] prcsdto = iprcs.prcsSearch(empId, prodNo, prodType, user.role);
				req.setAttribute("prcsSearchList", prcsdto);	

				printJspPage(req,res,_SCREEN);		
			}else if(_ACT.equals("R")){//하나의 공정을 검색한다.
				//공정조회
				String empId = box.get("empId");
				String prodNo = box.get("prodNo");
				String prcsNo = box.get("prcsNo");
				String prodType = box.get("prodType");
				IPrcs iprcs = (IPrcs)DAOFactory.getDAO("IPrcs");
				PrcsDTO prcsdto = iprcs.prcsSubSearch(prodNo, prcsNo, prodType);
			    req.setAttribute("prcsSubSearch", prcsdto);	
			        
				//공정인원 조회
				IPrcsManCnt iprcsmancnt = (IPrcsManCnt)DAOFactory.getDAO("IPrcsManCnt");
				PrcsManCntDTO[] prcsmancntdto = iprcsmancnt.prcsManCntSearchList(prodNo, prcsNo, prodType);
			    req.setAttribute("prcsManCntSearchList", prcsmancntdto);	

				//제품 기본정보 조회--공정한건 검색시 제품정보를 사용하는데 이때 admin인 경우만 보이고 curempid만 보임
				IProd iprod = (IProd)DAOFactory.getDAO("IProd");
				ProdDTO[] proddto = iprod.searchProd(empId, prodType, prodNo, "%", user);
			    req.setAttribute("searchProd", (proddto==null)?null:proddto[0]);	

				//전체공정코드 조회
				//admin 기능추가로 인한 user.empId 추가
				PrcsDTO[] prcsdtos = iprcs.prcsSearch(empId, prodNo, prodType, user.role);
			    req.setAttribute("prcsSearchList", prcsdtos);	
			    
			    //배정자 추가삭제시 상태유지를 위한 처리
			    if(box.get("reloadingFlag").equals("Y")){
			    	prcsdto.prcsName = box.get("prcsName");
			    	prcsdto.plStartDt = box.get("plStartDt");
			    	prcsdto.plEndDt = box.get("plEndDt");
			    	prcsdto.plMh = Integer.parseInt(box.get("plMh"));
			    	prcsdto.prcStatus = box.get("prcStatus");
			    	prcsdto.prcsDesc = box.get("prcsDesc");
			    	req.setAttribute("prcsSubSearch", prcsdto);	
			    }

			    printJspPage(req,res,_SCREEN);		
			}else if(_ACT.equals("CR")){//공정생성복사 화면에서 필요한 자료
				//공정조회
				String empId = box.get("empId");
				String prodNo = box.get("prodNo");
				String prcsNo = box.get("prcsNo");
				String prodType = box.get("prodType");
				String prcsCd = box.get("prcsCd");
				IStdPrcs istdprcs = (IStdPrcs)DAOFactory.getDAO("IStdPrcs");
				StdPrcsDTO[] stdprcsdto = istdprcs.searchStdPrcs(prcsCd, prcsNo);
			        req.setAttribute("searchStdPrcs", stdprcsdto);	
			        
			        printJspPage(req,res,_SCREEN);		
			}else if(_ACT.equals("CLR")){//공정복사 화면에서 필요한 자료
				//공정조회
				String prodNo = box.get("prodNo");
				String prodType = box.get("prodType");
				String prcsNo = box.get("prcsNo");
				
				IPrcs iprcs = (IPrcs)DAOFactory.getDAO("IPrcs");
				PrcsDTO[] prcsdto1 = iprcs.copyPrcsSearch(prodNo, prodType, prcsNo);
				PrcsDTO[] prcsdto2 = iprcs.copiedPrcsSearch(prodNo, prodType);

			        req.setAttribute("copyPrcsSearch", prcsdto1);
			        req.setAttribute("copiedPrcsSearch", prcsdto2);	
			        
			        printJspPage(req,res,_SCREEN);		
			}else if(_ACT.equals("VD")){//공정코드가 중복되는지를 체크한다.
				String prodNo = box.get("prodNo");
				String prcsNo = box.get("prcsNo");
				String prodType = box.get("prodType");
				IPrcs iprcs = (IPrcs)DAOFactory.getDAO("IPrcs");
				boolean chekPrcsCd = iprcs.prcsCdSearch(prodNo,prodType,prcsNo);

			        req.setAttribute("prcsCdSearch", new Boolean(chekPrcsCd));
			        printJspPage(req,res,_SCREEN);
			}
		} catch(Exception e) {
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
            		erorrMessagePage(req,res,e);
		}
	}
}
