/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ODIAutomation.channel.excel;

import com.ODIAutomation.channel.io.odiOverviewOutputStream;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author info4j
 */
public class xlsOdiOverviewOutputStream extends odiOverviewOutputStream{
    private XSSFWorkbook wb;
    private XSSFSheet sheet;
    
    public xlsOdiOverviewOutputStream(XSSFWorkbook wb, XSSFSheet sheet) {
        this.wb = wb;
        this.sheet = sheet;
    }

    @Override
    public void setProjectName(String name) {
        super.setProjectName(name); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setDocumentName(String name) {
        super.setDocumentName(name); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
