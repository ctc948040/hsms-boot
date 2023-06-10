package com.hsms.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class ExcelReader {

//	public static void main(String[] args) {
//		System.out.println(ExcelReader.getKey("최태철[aaaa]"));
//	}
	
	public static String getKey(String key) {
		String ret = "";
		if(key == null) return key;
		
		if(key.indexOf('[') == -1 && key.indexOf(']') == -1) {
			return key;
		}
		
		String[] arr = StringUtils.split(key, "[");
		
		String temp = arr[1];
		int lastIdx = temp.indexOf(']');
		
		ret = temp.substring(0, lastIdx);
		
		return ret;
	}
	
	public static List<Map<String, String>> read(MultipartFile file) {
		XSSFWorkbook workbook = null;
		InputStream in = null;
		try {
			in = file.getInputStream();
			workbook = new XSSFWorkbook(in);
			XSSFSheet worksheet = workbook.getSheetAt(0);

			List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
			List<String> keyList = new ArrayList<String>();
			Map<String, String> data = null;

			Row row = worksheet.getRow(0);

			for (int i = 0; i < 20; i++) {
				try {
					String key = row.getCell(i).getStringCellValue();
					if (!StringUtils.hasLength(key))
						break;
					
					keyList.add(getKey(key));
				} catch (NullPointerException e) {
					break;
				}
			}

			for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {

				row = worksheet.getRow(i);
				data = new HashMap<String, String>();
				int j = 0;
				for (String key : keyList) {
					String value = "";
					try {
						 value = row.getCell(j).getStringCellValue();
						
					} catch (Exception e) {
						if(row.getCell(j) != null) { 
							
							double dv = row.getCell(j).getNumericCellValue();
							value = String.valueOf((int) Math.round(dv));
						}
					}
					j +=1;
					if (!StringUtils.hasLength(value)) {
						value = null;
					}
					
					data.put(key, value);
				}
				dataList.add(data);
			}
			return dataList;
		} catch (IOException e) {
			throw new RuntimeException("Could not read the file. Error: " + e.getMessage());
		} finally {

			try {
				if (workbook != null)
					workbook.close();
				if (in != null)
					in.close();
			} catch (IOException e) {
			}
		}
	}
	
	public static List<Map<String, String>> read(Resource file) {
		XSSFWorkbook workbook = null;
		InputStream in = null;
		try {
			in = file.getInputStream();
			workbook = new XSSFWorkbook(in);
			XSSFSheet worksheet = workbook.getSheetAt(0);

			List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
			List<String> keyList = new ArrayList<String>();
			Map<String, String> data = null;

			Row row = worksheet.getRow(0);

			for (int i = 0; i < 20; i++) {
				try {
					String key = row.getCell(i).getStringCellValue();
					if (!StringUtils.hasLength(key))
						break;
					
					keyList.add(getKey(key));
				} catch (NullPointerException e) {
					break;
				}
			}

			for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {

				row = worksheet.getRow(i);
				data = new HashMap<String, String>();
				int j = 0;
				for (String key : keyList) {
					String value = "";
					try {
						 value = row.getCell(j).getStringCellValue();
						
					} catch (Exception e) {
						if(row.getCell(j) != null) { 
							
							double dv = row.getCell(j).getNumericCellValue();
							value = String.valueOf((int) Math.round(dv));
						}
					}
					j +=1;
					if (!StringUtils.hasLength(value)) {
						value = null;
					}
					
					data.put(key, value);
				}
				dataList.add(data);
			}
			return dataList;
		} catch (IOException e) {
			throw new RuntimeException("Could not read the file. Error: " + e.getMessage());
		} finally {

			try {
				if (workbook != null)
					workbook.close();
				if (in != null)
					in.close();
			} catch (IOException e) {
			}
		}

	}
}
