/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ODIAutomation.channel.odi.parser;

import com.ODIAutomation.channel.excel.xlsOdiWorkflowOutputStream;
import com.ODIAutomation.channel.io.odiMappingOutputStream;
import com.ODIAutomation.channel.io.odiWorkflowOutputStream;
import java.io.FileNotFoundException;
import oracle.odi.domain.project.OdiFolder;
import org.apache.log4j.Logger;

/**
 *
 * @author info4j
 */
public class Parser {
    odiWorkflowOutputStream wfos;
    odiMappingOutputStream mos;
    
    private static final Logger logger = Logger.getLogger(Parser.class);
    
    public Parser(String file) throws FileNotFoundException {
        wfos = new xlsOdiWorkflowOutputStream();
        wfos.initialize(file);
        mos = wfos.createPage("Data Lineage");
        //new ParseFolder(mos,null);
    }
    
    public void parseFolder(OdiFolder folder){
        new ParseFolder(mos, folder);
    }
    
    public void flush(){
        wfos.flush();
    }
//
//    private void writeOverview() {
//        overviewOS = wfos.createPage("Overview")
//        logger.info("Mapping: " + odiMapping.getName());
//        logger.info("Created by: " + odiMapping.getFirstUser() + " Created on: " + odiMapping.getFirstDate());
//        logger.info("Updated by: " + odiMapping.getLastUser() + " Updated on: " + odiMapping.getLastDate());
//
//        overviewOS.setInterface(odiMapping.getName());
//        overviewOS.setCreatedByOn(odiMapping.getFirstUser(), odiMapping.getFirstDate());
//        overviewOS.setUpdatedByOn(odiMapping.getLastUser(), odiMapping.getLastDate());
//    }
}
