/*
 * �ۼ��� ��¥: 2005. 9. 29.
 *
 * TODO ������ ���Ͽ� ���� ���ø�Ʈ�� �����Ϸ��� �������� �̵��Ͻʽÿ�.
 * â - ȯ�� ���� - Java - �ڵ� ��Ÿ�� - �ڵ� ���ø�Ʈ
 */
package com.wms.comPopup.beans.dao;

import com.wms.comPopup.beans.dto.ComPopupDTO;

/**
 * @author WHCHO
 *
 * TODO ������ ���� �ּ��� ���� ���ø�Ʈ�� �����Ϸ��� �������� �̵��Ͻʽÿ�.
 * â - ȯ�� ���� - Java - �ڵ� ��Ÿ�� - �ڵ� ���ø�Ʈ
 */
public interface IComPopup {

    //�ڵ� ��ȸ ����Ʈ
    public ComPopupDTO[] searchCodeList(ComPopupDTO dtos) throws Exception;
    public ComPopupDTO[] searchCodeNList(ComPopupDTO dtos) throws Exception;
    
}
