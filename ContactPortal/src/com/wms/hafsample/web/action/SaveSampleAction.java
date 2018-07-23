package com.wms.hafsample.web.action;

import com.wms.beans.dto.UserInfo;
import com.wms.fw.web.action.WmsActionImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wms.fw.DAOFactory;
import com.wms.fw.Logger;
import com.wms.hafsample.beans.dao.empSaveDAO;
import com.wms.hafsample.beans.dto.empSaveDTO;

public class SaveSampleAction extends WmsActionImpl {

	public void perform(HttpServletRequest req, HttpServletResponse res) {
		try {
			System.out.println("SaveSampleAction==>실행 !!!!!!!!!");
			String page = box.get("_SCREEN");
			//UserInfo user = (UserInfo) session.getAttribute("user");

			boolean returns = false;

			empSaveDTO dto = new empSaveDTO();
			box.copyToEntity(dto);
			
			//System.out.println(dto.empno + " / " + dto.ename + " / "+ dto.job + " / "+ dto.hiredate + " / "+box.size());
			//
			empSaveDAO dao = new empSaveDAO();  // (empSaveDAO)DAOFactory.getDAO("empSaveDAO");
			returns = dao.insert(dto);

			if (returns) {
				System.out.println("등록 성공했습니다.");
			} else {
				System.out.println("실패함!>>> " + box.get("_SCREEN"));
			}

			printJspPage(req, res, page);

		} catch (Exception e) {
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
			erorrMessagePage(req, res, e);

		}
	}
}
