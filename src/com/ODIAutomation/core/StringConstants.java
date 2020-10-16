/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ODIAutomation.core;

import java.util.Date;


/**
 *
 * @author info4j
 * String definitions used to provide flexibility to change String definitions at later stage.
 */
public class StringConstants{

    public static final String CONNECTION_NAME = "CONNECTION_NAME";
    public static final String CONN_INFORMATICA = "CONN_INFORMATICA";
    public static final String CONN_ODI = "CONN_ODI";
    
    public static final String BLANK = "";
    
    //Informatica Connection
    public static final String ADMIN_USERNAME = "ADMIN_USERNAME";
    public static final String ADMIN_PASSWORD = "ADMIN_PASSWORD";
    public static final String PC_CLIENT_INSTALL_PATH = "PC_CLIENT_INSTALL_PATH";
    public static final String PMREP_CACHE_FOLDER = "PMREP_CACHE_FOLDER";
    public static final String REPO_SERVER_HOST = "REPO_SERVER_HOST";
    public static final String REPO_SERVER_PORT = "REPO_SERVER_PORT";
    public static final String REPO_SERVER_DOMAIN_NAME = "REPO_SERVER_DOMAIN_NAME";
    public static final String TARGET_REPO_NAME = "TARGET_REPO_NAME";
    
    
    //ODI Connection
    public static final String ODI_USERNAME = "ODI_USERNAME";
    public static final String ODI_PASSWORD = "ODI_PASSWORD";
    public static final String ODI_SCHEMA_USERNAME = "ODI_SCHEMA_USERNAME";
    public static final String ODI_SCHEMA_PASSWORD = "ODI_SCHEMA_PASSWORD";
    public static final String ODI_DRIVER_NAME = "ODI_DRIVER_NAME";
    public static final String ODI_DB_URL = "ODI_DB_URL";
    public static final String ODI_WORKREP = "ODI_WORKREP";
    
    //excel objects
    public static final String XL_FOLDER = "Folder";
    public static final String XL_PACKAGE = "Package";
    public static final String XL_INTERFACE = "Interface";
    public static final String XL_CREATED_BY = "Created by:";
    public static final String XL_CREATED_ON = "Created on:";
    public static final String XL_UPDATED_BY = "Updated by:";
    public static final String XL_UPDATED_ON = "Updated on:";
    public static final String XL_TARGET_TABLE_NAME = "Target Table";
    public static final String XL_TARGET_COLUMN_NAME = "Target Column";
    public static final String XL_DATATYPE = "DataType";
    public static final String XL_EXPRESSION = "Expression";
    public static final String XL_SOURCE_TABLE_NAME = "Source Table";
    public static final String XL_SOURCE_COLUMN_NAME = "Source Column";
    
    private StringConstants(){  
    }
}
