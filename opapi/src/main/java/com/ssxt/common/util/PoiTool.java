package com.ssxt.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.ssxt.web.bean.BasStinfoB;

public class PoiTool {

	// 读取excel文件，创建表格实例
	public static Sheet loadExcel(String filePath) {
		FileInputStream inStream = null;
		Sheet sheet = null;
		try {
			inStream = new FileInputStream(new File(filePath));
			Workbook workBook = WorkbookFactory.create(inStream);

			sheet = workBook.getSheetAt(0);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (inStream != null) {
					inStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return sheet;
	}

	// 获取单元格的值
	public static String getCellValue(Cell cell) {
		String cellValue = "";
		DataFormatter formatter = new DataFormatter();
		if (cell != null) {
			// 判断单元格数据的类型，不同类型调用不同的方法
			switch (cell.getCellType()) {
			// 数值类型
			case Cell.CELL_TYPE_NUMERIC:
				// 进一步判断 ，单元格格式是日期格式
				if (DateUtil.isCellDateFormatted(cell)) {
					cellValue = formatter.formatCellValue(cell);
				} else {
					// 数值
					double value = cell.getNumericCellValue();
					int intValue = (int) value;
					cellValue = value - intValue == 0 ? String.valueOf(intValue) : String.valueOf(value);
				}
				break;
			case Cell.CELL_TYPE_STRING:
				cellValue = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				cellValue = String.valueOf(cell.getBooleanCellValue());
				break;
			// 判断单元格是公式格式，需要做一种特殊处理来得到相应的值
			case Cell.CELL_TYPE_FORMULA: {
				try {
					cellValue = String.valueOf(cell.getNumericCellValue());
				} catch (IllegalStateException e) {
					cellValue = String.valueOf(cell.getRichStringCellValue());
				}

			}
				break;
			case Cell.CELL_TYPE_BLANK:
				cellValue = "";
				break;
			case Cell.CELL_TYPE_ERROR:
				cellValue = "";
				break;
			default:
				cellValue = cell.toString().trim();
				break;
			}
		}
		return cellValue.trim();
	}

	public static String setServiceType(String name) {
		if (name == null)
			return null;
		if (name.contains("地下水")) {
			return "1";
		}
		if (name.contains("中小河流站")) {
			return "2";
		}
		if (name.contains("山洪")) {
			return "3";
		}
		if (name.contains(" 雨量基本站")) {
			return "4";
		}
		return null;
	}

	// // 初始化表格中的每一行，并得到每一个单元格的值
	// public void init() {
	// List<Integer, String> list = new ArrayList<Integer, String>();
	// int rowNum = sheet.getLastRowNum() + 1;
	//
	// for (int i = 1; i < rowNum; i++) {
	// Row row = sheet.getRow(i);
	// // 每有新的一行，创建一个新的LinkedList对象
	//
	// for (int j = 0; j < row.getLastCellNum(); j++) {
	// Cell cell = row.getCell(j);
	// // 获取单元格的值
	// String str = getCellValue(cell);
	// // 将得到的值放入链表中
	// System.out.println(str);
	// }
	// }
	// }

	public static void main(String[] args) {
		PoiTool p = new PoiTool();
		Sheet sheet = p.loadExcel("C:\\Users\\zzb\\Downloads\\测站信息 (3).xls");
		int rowNum = sheet.getLastRowNum() + 1;

		for (int i = 1; i < rowNum; i++) {
			Row row = sheet.getRow(i);

			if (row.getRowNum() > 0) {
				BasStinfoB bean = new BasStinfoB();
				bean.setStcd(PoiTool.getCellValue(row.getCell(0)));
				bean.setStnm(PoiTool.getCellValue(row.getCell(1)));
				bean.setStlc(PoiTool.getCellValue(row.getCell(2)));
				bean.setSttp(PoiTool.getCellValue(row.getCell(3)));
				String serviceType = PoiTool.setServiceType((PoiTool.getCellValue(row.getCell(4))));
				bean.setServiceType(serviceType);
				bean.setAddvcd(PoiTool.getCellValue(row.getCell(8)));

			}

		}
	}

}
