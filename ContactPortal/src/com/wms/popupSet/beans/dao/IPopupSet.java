/*************************************************************
*	파 일 명  : IPopupSet.java
*	작성일자  : 2006/07/26
*	작 성 자  : mailbest
*	내    용  : 팝업 셋팅 정보 인터페이스
*************************************************************/
package com.wms.popupSet.beans.dao;

import com.wms.comPopup.beans.dto.ComPopupDTO;
import com.wms.comPopup.beans.dto.ComPopupSetDTO;
import com.wms.popupSet.beans.dto.PopupSetDTO;

public interface IPopupSet
{
	public PopupSetDTO[] searchPopupSetList(String paramId,String PopupTitle) throws Exception;
	public ComPopupSetDTO[] searchOrgMemberList(String paramId) throws Exception;
	public ComPopupSetDTO[] searchOrgMemberSetList(ComPopupSetDTO dtos) throws Exception;
	public boolean deletePopupSet(PopupSetDTO dto)throws Exception;
	public boolean insertPopupSet(PopupSetDTO dto) throws Exception;
	public boolean savePopupSet(PopupSetDTO dto) throws Exception;
	public boolean saveOrgMemberSet(PopupSetDTO[] dto) throws Exception;
}
