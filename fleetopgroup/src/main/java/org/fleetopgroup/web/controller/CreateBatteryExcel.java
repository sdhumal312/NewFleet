package org.fleetopgroup.web.controller;

import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CreateBatteryExcel {
		public static void main(String[] args) {
			XSSFWorkbook 				hssfWorkbook					= null;
			XSSFSheet 					hssfSheet						= null;
			XSSFRow 					hssfRow							= null; 
			DataValidation 				dataValidation 					= null;
			DataValidationConstraint 	constraint 						= null;
			DataValidationHelper 		validationHelper 				= null;

			try {
				hssfWorkbook				= new XSSFWorkbook();
				hssfSheet					=(XSSFSheet) hssfWorkbook.createSheet("sheet1");
				XSSFRow rowZero 			= hssfSheet.createRow((short) 0);
				hssfRow 					= hssfSheet.createRow((short) 1);
				

				    validationHelper=new XSSFDataValidationHelper(hssfSheet);
				    hssfSheet.addMergedRegion(new CellRangeAddress(0,0,0,10));
				    
				    rowZero.createCell(0).setCellValue("Note : (*) marked filled are mandatory. Please do not add or remove any column and also please do not rearrange any column. Please fill the all mandatory fields. Please do not remove note row. Please fill Invoice date in dd-mm-yyyy format.");
				    
				    hssfRow.createCell(0).setCellValue("Battery Manufacturer (*)");
					hssfRow.createCell(1).setCellValue("Battery Model (*)");
					hssfRow.createCell(2).setCellValue("Capacity (*)");
					hssfRow.createCell(3).setCellValue("Unit Cost (*)");
					hssfRow.createCell(4).setCellValue("Warehouse Location (*)");
					hssfRow.createCell(5).setCellValue("Battery Number (*)");
				    
				    CellRangeAddressList addressList = new  CellRangeAddressList(2,5,0,0);
				    constraint =validationHelper.createExplicitListConstraint(new String[]{"SELECT","10", "20", "30"});
				    dataValidation = validationHelper.createValidation(constraint, addressList);
				    dataValidation.setSuppressDropDownArrow(true);      
				    hssfSheet.addValidationData(dataValidation);
				    
				    for (int i=0; i<10; i++){
				    	hssfSheet.autoSizeColumn(i);
				    }


				    FileOutputStream fileOut = new FileOutputStream("D:\\Battery\\battery.xlsx");
				    hssfWorkbook.write(fileOut);
				    fileOut.close();
				
				    System.err.println("Sheet Creating successfully!");
				    
				
			} catch (Exception e) {
				System.err.println("Exception "+e);
				
			}
		}
}
