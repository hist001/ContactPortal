/*
 * 작성된 날짜: 2005. 10. 11.
 *
 * TODO 생성된 파일에 대한 템플리트를 변경하려면 다음으로 이동하십시오.
 * 창 - 환경 설정 - Java - 코드 스타일 - 코드 템플리트
 */
package com.wms.common.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wms.fw.Logger;
import com.wms.fw.servlet.MultipartData;
import com.wms.fw.web.action.WmsActionImpl;

/**
 * @author 조원호
 * file download 
 */
public class DownLoadFilesAction extends WmsActionImpl {

	/* (비Javadoc)
	 * @see com.wms.fw.web.action.HTMLAction#perform(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void perform(HttpServletRequest req, HttpServletResponse res) {
		// TODO 자동 생성된 메소드 스텁	
		try {         
            
            //admin 권한처리
            //String empId = null;
            /*
            if(user.auths.containsKey("admin")){
                empId = box.get("empId");
            }else{
                empId = user.empId;                
            }
            */
			MultipartData.downLoad(box.get("dir"), box.get("fileSystemName"), box.get("fileOriginName"),  res);

            
		} catch(Exception e) {
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
			e.printStackTrace();			
			erorrMessagePage(req,res,e);
		}		
	}

}
