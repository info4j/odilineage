package com.ODIAutomation.channel.io;

import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author info4j
 */
public class odiMappingOutputStream {
    
    public void setFolder(String name){};
    public void setPackage(String name){};
    public void setInterface(String name){};
    public void setCreatedByOn(String name, Date date) {};
    public void setUpdatedByOn(String name, Date date) {};
    
    public void setLineageHeader(){};
    public void setLineageRow(String targetTableName, String targetColumnName, String targetDatatypeName,
            String expression, String sourceTable, String sourceColumn){};
    public void setBlankRow(){};
}
