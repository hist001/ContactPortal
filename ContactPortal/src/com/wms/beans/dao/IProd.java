/*************************************************************
*	�� �� ��  : IProd.java
*	�ۼ�����  : 2004/09/03
*	�� �� ��  : 
*	��    ��  : ��ǰ ���� �������̽�
*************************************************************/
package com.wms.beans.dao;
import com.wms.beans.dto.ProdDTO;
import com.wms.beans.dto.AccoDTO;
import com.wms.beans.dto.PrcsDTO;
import com.wms.beans.dto.*;
import com.wms.beans.dto.UserInfo;

public interface IProd
{
	public int[] updateProd(ProdDTO dto,String prcsChangeYN)throws Exception;
	public ProdDTO searchProd(String prodNo, String prodType)throws Exception;
	public ProdDTO[] searchProd(ProdDTO dto)throws Exception;
	//��ǰ�˻�(�����޴�)
	public ProdDTO[] searchProd(String empId, String prodType, String prodNo, String prodName, UserInfo user)  throws Exception;
	//��ǰ�˻�--prcsType�� üũ�Ѵ�.
	public ProdDTO[] searchProd(String empId, String prodType, String orgProdNo, String outerProdNo, String prodName, String prcsType, UserInfo user)  throws Exception;
	public PrcsDTO[] searchDetailPrcs(PrcsDTO dto)throws Exception;
	public boolean createProd(ProdDTO dto,String accoItems) throws Exception;
	//�ۼ��ڷ� �˻�
	public ProdDTO[] searchProdList(String empId, String prodType, String prodName, UserInfo user, String searchType, String bizType, String prodStatus)throws Exception;
	public ProdDTO[] searchProdDetail(String empId, String prodNo, String prodType)throws Exception;
	public boolean deleteProd(ProdDTO dto) throws Exception;
	//��ǰ�� �Ⱓ�� ��ȸ
	public ProdDTO[] prodInquiryList(String prodType, String startDt,String endDt,String bizNo,String prodStatus, String prodNo, String prodName,int pageCnt,int pageSize)  throws Exception;
	//��ǰ�� �Ⱓ�� ��ȸ - cnt��
	public int prodInquiryListCnt(String prodType, String startDt,String endDt,String bizNo,String prodStatus, String prodNo, String prodName)  throws Exception;	
	//��ǰ�� �����η� ��ȸ
	public PrcsManCntDTO[] prodEmpSearch(String name,String id)throws Exception;
}
