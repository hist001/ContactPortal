/*
 * 작성된 날짜: 2006.08.08
 *
 * TODO 생성된 파일에 대한 템플리트를 변경하려면 다음으로 이동하십시오.
 * 창 - 환경 설정 - Java - 코드 스타일 - 코드 템플리트
 */
package com.wms.comPopup.beans.dto;

import com.wms.fw.GeneralDTO;
 
/**
 * @author mailbest
 *
 * TODO 생성된 유형 주석에 대한 템플리트를 변경하려면 다음으로 이동하십시오.
 * 창 - 환경 설정 - Java - 코드 스타일 - 코드 템플리트
 */
public class ComPopupSetDTO extends GeneralDTO{

	public String codeNo;     		//시스템 코드
	public String code;   	  		//코드
	public String codeName;   		//코드명
	public String code2;   	  		//코드
	public String codeName2;   		//코드명
	public String highCode;   		//상위코드
	public String highCodeName;   	//상위코드명
	public int    level;   			//레벨
	
	public String param;  			//조회 param
	public String paramId;  		//조회 paramId
	public String selFlag;  		//선택 버튼 유무
	public String empId;  		    //검색 사원 EMPID

	public String ObjCodeNo1;  		//코드번호1 값을 받을 Object명
	public String ObjCodeName1;		//코드명1 값을 받을 Object명 
	public String ObjCodeNo2;  		//코드번호2 값을 받을 Object명
	public String ObjCodeName2;		//코드명2 값을 받을 Object명
	
	public String selectFlag;		//팝업 조회 조건- 선택 여부
	public String delFlag;			//팝업 조회 조건- 삭제 여부
	public String rearrangeFlag;	//팝업 조회 조건- 정렬 여부
	
}
