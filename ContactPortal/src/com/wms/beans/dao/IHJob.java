package com.wms.beans.dao;
import com.wms.beans.dto.*;
import java.util.ArrayList;
import java.util.Hashtable;

public interface IHJob
{
	public HJobDTO[] selectHJobList(String empId) throws Exception;
	public HJobDTO[] selectHJobConfList(String empId) throws Exception;
	public HJobDTO selectHJob(String empId, String revisionNo, String status) throws Exception;
	public boolean deleteHJob(String empId, String revisionNo, String status) throws Exception;
	public boolean updateHJob(HJobDTO dto,int selectedIndex) throws Exception;
	public boolean confirmHJob(HJobDTO dto, int selectedIndex, String userEmpId) throws Exception;
	public boolean createHJob(HJobDTO dto, int selectedIndex, String userEmpId) throws Exception;
	public EmpDTO[] coJobSearch(String empId)throws Exception;
	public Hashtable teamJobSearch(String empId) throws Exception;
}
