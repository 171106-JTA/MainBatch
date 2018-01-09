package com.revature.util.hybrid;

import java.io.*;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PhpTravelsExcelParser {

	public static Sheet readExcel(String path, String fileName, String sheetName) throws IOException {
		File file = new File(path + "/" + fileName);
		Workbook workbook = null;
		
		try(FileInputStream fis = new FileInputStream(file)){
			String ext = fileName.substring(fileName.indexOf("."));
			if(ext.equals(".xlsx"))
				workbook = new XSSFWorkbook(fis);
			if(ext.equals(".xls"))
				workbook = new HSSFWorkbook(fis);			
			return workbook.getSheet(sheetName);
			
		} catch(IOException ex) {
			return null;
		}
	}
}
