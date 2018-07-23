package com.wms.hafsample.beans.dao;

import java.sql.*;

import com.wms.comPopup.beans.dao.ComPopupQueryHelper;
import com.wms.comPopup.beans.dto.ComPopupDTO;
import com.wms.comPopup.beans.dto.ComPopupSetDTO;
import com.wms.fw.db.DataBaseUtil;
import com.wms.hafsample.beans.dto.*;
import com.wms.hafsample.beans.dao.empSaveQueryHelper;
import com.wms.hafsample.beans.dto.empSaveDTO;
import com.wms.fw.*;

public class empSaveDAO {
	public boolean insert(empSaveDTO dto) throws Exception {
        Connection con = null;
        Statement stmt=null;
        ResultSet rs = null;
		
		boolean returns = false;
		        
		System.out.println("Insert 실행.");
        int result = 0;
        try{
            String sql = empSaveQueryHelper.insert(dto);
            
            System.out.println("SampleInsert :: \n"+sql);
            
            con = DataBaseUtil.getConnection();
            //con.setAutoCommit(true);
            stmt = con.createStatement();
			result = stmt.executeUpdate(sql);

			if(result>0){
				System.out.println("사용자 정보가 등록 되었습니다.");
				returns = true;
			}else{
				throw new Exception("에러가 발생되었습니다. ");
			} 	
            
        }catch(Exception e){
                Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
        }
        finally{
            try{
                if(rs!=null)rs.close();
                if(stmt!=null)stmt.close();
                if(con!=null)con.close();
            }catch(Exception e){
                Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
            }
        }           
        return returns; 
	}

}
