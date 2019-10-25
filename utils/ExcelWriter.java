/**
 * @author ZXL
 * @date 2019/7/23 10:37
 */
package com.xuebei.crm.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @author Rong Weicheng
 */
public class ExcelWriter<T> {
    private List<ColumnExtractor<T>> extractors;

    public ExcelWriter(List<ColumnExtractor<T>> extractors) {
        this.extractors = extractors;
    }

    public void write(OutputStream out, String sheetName, List<T> data) throws IOException {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(sheetName);
        buildBody(sheet, wb, data);
        wb.write(out);
    }

    public void write(OutputStream out, String sheetName, List<T> data, List<String> finalLine) throws IOException {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(sheetName);
        int rowNo = buildBody(sheet, wb, data);
        buildTail(sheet, finalLine, rowNo);
        wb.write(out);
    }

    private int buildBody(HSSFSheet sheet, HSSFWorkbook wb, List<T> data) {
        int rowNo = 0;
        HSSFRow headRow = sheet.createRow(rowNo++);
        int cellNo = 0;
        CellStyle headCellStyle = buildHeadCellStyle(wb);
        for (ColumnExtractor<T> extractor : extractors) {
            HSSFCell cell = headRow.createCell(cellNo++);
            cell.setCellStyle(headCellStyle);
            cell.setCellValue(extractor.getColumnName());
        }

        for (T t : data) {
            HSSFRow row = sheet.createRow(rowNo++);
            cellNo = 0;
            for (ColumnExtractor<T> extractor : extractors) {
                row.createCell(cellNo++).setCellValue(extractor.extractValue(t));
            }
        }
        return rowNo;
    }

    private int buildTail(HSSFSheet sheet, List<String> finalLine, int rowNo) {
        HSSFRow row = sheet.createRow(rowNo++);
        int cellNo = 0;
        for (String item : finalLine) {
            row.createCell(cellNo++).setCellValue(item);
        }
        return rowNo;
    }

    public CellStyle buildHeadCellStyle(HSSFWorkbook wb) {
        CellStyle headCellStyle = wb.createCellStyle();
        headCellStyle.setLocked(true);
        Font font = wb.createFont();
        font.setBold(true);
        font.setFontHeight((short)260);
        headCellStyle.setFont(font);
        return headCellStyle;
    }
}

