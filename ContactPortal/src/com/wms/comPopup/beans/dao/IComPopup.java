/*
 * 작성된 날짜: 2005. 9. 29.
 *
 * TODO 생성된 파일에 대한 템플리트를 변경하려면 다음으로 이동하십시오.
 * 창 - 환경 설정 - Java - 코드 스타일 - 코드 템플리트
 */
package com.wms.comPopup.beans.dao;

import com.wms.comPopup.beans.dto.ComPopupDTO;

/**
 * @author WHCHO
 *
 * TODO 생성된 유형 주석에 대한 템플리트를 변경하려면 다음으로 이동하십시오.
 * 창 - 환경 설정 - Java - 코드 스타일 - 코드 템플리트
 */
public interface IComPopup {

    //코드 조회 리스트
    public ComPopupDTO[] searchCodeList(ComPopupDTO dtos) throws Exception;
    public ComPopupDTO[] searchCodeNList(ComPopupDTO dtos) throws Exception;
    
}
