package com.sun.utils;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;


/**
 * Datatable class for fetching value from the excel sheet
 *
 */
public class DataTable {
	protected Hashtable<String, String> testData;
	private String testName;
	
	public DataTable(String testName) {
		this.testName=testName;
		loadTestData();		
	}

	private void loadTestData() {
		HSSFWorkbook hssfWorkbook = getWorkBook(testName);	
		HSSFSheet dataSheet = getSheet(hssfWorkbook);
		ArrayList<String> columNames= getColumnNames(dataSheet);
		Row testDataRow= getTestDataRowForTestCase(dataSheet);
		if(testDataRow==null){
			throw new RuntimeException("Unable to find testdata row for " + testName);
		}
		testData=prepareTestDataRowHashTable(columNames,testDataRow);
	}
	
	/**
	 * Get value from the data sheet
	 * @param key column name in the data sheet
	 * @return 
	 */
	public String getValue(String key){
		return testData.get(key);
	}

	private HSSFSheet getSheet(HSSFWorkbook hssfWorkbook) {
		
		TestSettings testSetting= new TestSettings();
		String sheetName=testSetting.getSheetName();		
		HSSFSheet sheet = hssfWorkbook.getSheet(sheetName); 	
		return sheet;
	}

	private HSSFWorkbook getWorkBook(String testName) {		
		String path=TestUtils.getRelativePath() + "//src//test//resources//IllinoisTestData.xls";
		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream(path);
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(fileInputStream);
			fileInputStream.close();
			return hssfWorkbook;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	private Hashtable<String, String> prepareTestDataRowHashTable(
			ArrayList<String> columNames, Row testDataRow) {
		Hashtable<String, String> testDataRowHashTable= new Hashtable<String,String>();		
		for (Cell cell : testDataRow) {
			String columnName=columNames.get(cell.getColumnIndex());
			String columnValue=cell.getStringCellValue();
			testDataRowHashTable.put(columnName, columnValue);
		}
		return testDataRowHashTable;
	}

	private ArrayList<String> getColumnNames(Sheet testDataSheet) {
		ArrayList<String> columnNameList= new ArrayList<String>();
		Row row = testDataSheet.getRow(0);
		for (Cell cell : row) {
			columnNameList.add(cell.getStringCellValue());
		}
		return columnNameList;
	}

		
	private Row getTestDataRowForTestCase(Sheet testDataSheet) {
		for (Row row : testDataSheet) {
			if (IsRequiredTestCaseRow(row,this.testName)) {
				return row;
			} 
		}		
		return null;
	}

	private boolean IsRequiredTestCaseRow(Row row, String testCaseName) {
		Cell testCaseIdCell= row.getCell(0);
		String testCaseId=testCaseIdCell.getStringCellValue();
		if (testCaseId.trim().equals(testCaseName)) {
			return true;
		}
		return false;
	}
}
