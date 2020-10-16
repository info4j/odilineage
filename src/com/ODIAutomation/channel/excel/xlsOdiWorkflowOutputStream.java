/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ODIAutomation.channel.excel;

import com.ODIAutomation.channel.io.odiMappingOutputStream;
import com.ODIAutomation.channel.io.odiWorkflowOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author info4j
 */
public class xlsOdiWorkflowOutputStream extends odiWorkflowOutputStream{
    private XSSFWorkbook wb;
    private FileOutputStream out;
    
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(xlsOdiWorkflowOutputStream.class);
        
    @Override
    public void initialize(String strFile) {
        try {
            wb = new XSSFWorkbook();
            String outFile = strFile + ".xlsx";
            out = new FileOutputStream(outFile, true);
            //sheet = wb.createSheet("Summary");
        } catch (FileNotFoundException ex) {
            logger.error(ex);
        }
    }

    @Override
    public odiMappingOutputStream createPage(String page){
        XSSFSheet sheet = wb.createSheet(page);
        odiMappingOutputStream mos = new xlsOdiMappingOutputStream(wb, sheet);
        return mos;
    }
    
    @Override
    public void flush(){
        try {
            wb.write(out);
        } catch (IOException ex) {
            logger.error(ex);
        }
    }
}
