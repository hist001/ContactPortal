/****************************************** 
FILENAME : JxlRead.java 
DATE : 2008.10.27
PROGRAMER : 
COMMENT : 엑셀 파일 로드(sheet 별로 구분)
******************************************/
package com.wms.fw.servlet;

import java.io.*;  
import java.util.Vector;

import com.wms.fw.Logger;
 
import jxl.*;	  
  
public class JxlRead  {     
  
	public  String[][][] JxlRead(String fileName) throws FileNotFoundException, IOException,jxl.read.biff.BiffException{
		try {
			
		Workbook myWorkbook = Workbook.getWorkbook(new File(fileName));  
		
		//처리 Sheet, Cols, Rows, Cell 생성
		int sheetCnt= myWorkbook.getNumberOfSheets();
		Sheet[] mySheet=new Sheet[sheetCnt] ;
		int[] sheetCols= new int[sheetCnt];
		int[] sheetRows= new int[sheetCnt];		
		
		//총Cell수
		int Cells = 0;
		int CurrCell=0;
		int Rows=0;
		
		//처리 Sheet, Cols, Rows 기본값 설정
		for(int i=0;i<sheetCnt;i++){
			mySheet[i]= myWorkbook.getSheet(i);
			sheetCols[i]=mySheet[i].getColumns();
			sheetRows[i]=mySheet[i].getRows();
			Cells = Cells +  sheetCols[i];
			if (Rows<sheetRows[i]){
				Rows = sheetRows[i];
			}
		}
		//myCells선언
		String[][][] myCells = new String[sheetCnt][Cells+1][Rows+1];
			
//		System.out.println("sheetCnt ::::::::> "+ sheetCnt);
//		System.out.println("Cells ::::::::> "+ Cells);
		
		//처리 Sheet, Cols, Rows 기본값 설정
		for(int i=0;i<sheetCnt;i++){
//			System.out.println("sheet ::::::::> "+ i);
			for(int j=0;j<mySheet[i].getColumns();j++){	
//				System.out.println("cols ::::::::> "+ j);
//				System.out.println("CurrCell ::::::::> "+ CurrCell);
				for(int k=0;k<mySheet[i].getRows();k++){
//					System.out.println("rows ::::::::> "+ k);
						Cell myCell= mySheet[i].getCell(j,k);
						if (k==0) {
							//sheet정보	
							myCells[i][j][k]=i+","+mySheet[i].getColumns() +","+ mySheet[i].getRows();
							myCells[i][j][k+1] = myCell.getContents();
						}else {
							//셀값 할당
							myCells[i][j][k+1] = myCell.getContents();
						}
//						System.out.println(i+","+j+","+k) ;
//						System.out.println("->"+myCells[i][j][k].toString());
				}
			}
		}
		
//			System.out.println("myCells[1][2].length()1 ::::::::> " + myCells.length);
//			System.out.println("myCells[1][2].length()2 ::::::::> " + myCells[1].length);
//			System.out.println("myCells[1][2].length()3 ::::::::> " + myCells[1][1].length );
		
		return myCells;
		}
		catch(Exception e){
			Logger.err.println(com.wms.fw.Utility.getStackTrace(e));
		}
		return null;
	} 
}
