/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ODIAutomation.channel.excel;

import com.ODIAutomation.channel.io.odiMappingOutputStream;
import com.ODIAutomation.core.StringConstants;
import java.util.Date;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author info4j
 */
public class xlsOdiMappingOutputStream extends odiMappingOutputStream {
    private XSSFWorkbook wb;
    private XSSFSheet sheetMapping;

    public xlsOdiMappingOutputStream(XSSFWorkbook wb, XSSFSheet sheetMapping) {
        this.wb = wb;
        this.sheetMapping = sheetMapping;
    }

    @Override
    public void setInterface(String name) {
        setParameterName(StringConstants.XL_INTERFACE, name);
    }

    @Override
    public void setPackage(String name) {
        setParameterName(StringConstants.XL_PACKAGE, name);
    }

    @Override
    public void setFolder(String name) {
        setParameterName(StringConstants.XL_FOLDER, name);
    }

    @Override
    public void setCreatedByOn(String name, Date date) {
        setParameterName(StringConstants.XL_CREATED_BY, name,StringConstants.XL_CREATED_ON, date);
    }

    @Override
    public void setUpdatedByOn(String name, Date date) {
        setParameterName(StringConstants.XL_UPDATED_BY, name, StringConstants.XL_UPDATED_ON, date);
    }

    @Override
    public void setLineageRow(String targetTableName, String targetColumnName, String targetDatatypeName, String expression, String sourceTable, String sourceColumn) {
        XSSFRow row = sheetMapping.createRow(sheetMapping.getLastRowNum()+1);
        row.createCell(row.getLastCellNum()+1).setCellValue(targetTableName);
        row.createCell(row.getLastCellNum()).setCellValue(targetColumnName);
        row.createCell(row.getLastCellNum()).setCellValue(targetDatatypeName);
        row.createCell(row.getLastCellNum()).setCellValue(expression);
        row.createCell(row.getLastCellNum()).setCellValue(sourceTable);
        row.createCell(row.getLastCellNum()).setCellValue(sourceColumn);
    }    

    @Override
    public void setLineageHeader() {
        XSSFRow row = sheetMapping.createRow(sheetMapping.getLastRowNum()+1);
        highlightCell(row.createCell(row.getLastCellNum()+1)).setCellValue(StringConstants.XL_TARGET_TABLE_NAME);
        highlightCell(row.createCell(row.getLastCellNum())).setCellValue(StringConstants.XL_TARGET_COLUMN_NAME);
        highlightCell(row.createCell(row.getLastCellNum())).setCellValue(StringConstants.XL_DATATYPE);
        highlightCell(row.createCell(row.getLastCellNum())).setCellValue(StringConstants.XL_EXPRESSION);
        highlightCell(row.createCell(row.getLastCellNum())).setCellValue(StringConstants.XL_SOURCE_TABLE_NAME);
        highlightCell(row.createCell(row.getLastCellNum())).setCellValue(StringConstants.XL_SOURCE_COLUMN_NAME);
        for(int i=0; i < 4 ; i++)
            sheetMapping.autoSizeColumn(i);
    }

    @Override
    public void setBlankRow() {
         XSSFRow row = sheetMapping.createRow(sheetMapping.getLastRowNum()+1);
    }
    
    protected void setParameterName(String parameter, String value){
        XSSFRow row = sheetMapping.createRow(sheetMapping.getLastRowNum()+1);
        highlightCell(row.createCell(row.getLastCellNum()+ 1)).setCellValue(parameter);
        row.createCell(row.getLastCellNum()).setCellValue(value);
    }
    
    protected void setParameterName(String parameter1, String value1, String parameter2, Date value2){
        XSSFRow row = sheetMapping.createRow(sheetMapping.getLastRowNum()+1);
        highlightCell(row.createCell(row.getLastCellNum()+1)).setCellValue(parameter1);
        dateCell(row.createCell(row.getLastCellNum())).setCellValue(value1);
        highlightCell(row.createCell(row.getLastCellNum())).setCellValue(parameter2);
        dateCell(row.createCell(row.getLastCellNum())).setCellValue(value2);
    }
    
    private XSSFCell highlightCell(XSSFCell cell){
        CellStyle style = wb.createCellStyle();  
        Font font = wb.createFont();
        //font.setColor(HSSFColor.GREY_40_PERCENT.index);
        font.setBold(true);
        //font.setFontName("Arial");
        //font.setFontHeightInPoints((short) 8);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);
        style.setFont(font);
        cell.setCellStyle(style);
        return cell;
    }
    
    private XSSFCell dateCell(XSSFCell cell){
        CellStyle style = wb.createCellStyle();  
        CreationHelper createHelper = wb.getCreationHelper();
        style.setDataFormat(createHelper.createDataFormat().getFormat("DD-MMM-YYYY HH:MM:SS"));
        cell.setCellStyle(style);
        return cell;
    }
}
