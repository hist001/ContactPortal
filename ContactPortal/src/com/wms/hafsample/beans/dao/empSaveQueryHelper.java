package com.wms.hafsample.beans.dao;

import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;

import com.wms.fw.Configuration;
import com.wms.fw.Logger;
import com.wms.fw.db.DataBaseUtil;
import com.wms.fw.db.SQLMapping;
import com.wms.fw.db.SQLXmlDAO;
import com.wms.hafsample.beans.dto.*;

public class empSaveQueryHelper {

	static String dir;
	
	public static String insert(empSaveDTO dto) throws Exception{
        if(dir==null) dir = (new Configuration()).get("com.wms.fw.sql.dir");
        HashMap hm    = SQLXmlDAO.loadRequestMappings(dir+"\\SampleQuery.xml");
        SQLMapping sm = (SQLMapping)hm.get("U_002");
        sm.setString(1, dto.empno);
        sm.setString(2, dto.ename);
        sm.setString(3, dto.job);
        sm.setString(4, dto.hiredate);
        sm.setString(5, dto.deptno);
		System.out.println("Query Helper ½ÇÇà(F!).");
        return sm.toSql();
    }
}
