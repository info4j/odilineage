/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ODIAutomation.commons.config;

import com.ODIAutomation.core.StringConstants;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.resolver.DefaultEntityResolver;
import org.apache.commons.configuration2.tree.ImmutableNode;
import org.apache.commons.configuration2.tree.xpath.XPathExpressionEngine;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author info4j
 */
public class ConfigReader {
    private XMLConfiguration config;
    private FileBasedConfigurationBuilder<XMLConfiguration> builder;
   // private String connectionName;


    
	private static final Logger logger = Logger.getLogger(ConfigReader.class.getName());

	private ConfigReader() {
            Parameters params = new Parameters();
            loadSystemProperty();
            try { 
                builder = new FileBasedConfigurationBuilder<XMLConfiguration>(XMLConfiguration.class)
                              .configure(params.xml()
                              .setFile(getFile())
                              .setSchemaValidation(false)
                              .setExpressionEngine(new XPathExpressionEngine()));
                config = builder.getConfiguration();
            } catch (ConfigurationException ex) {
                logger.fatal(ex);
            }
	}
        
        private void loadSystemProperty(){
              System.setProperty("javax.xml.accessExternalDTD", "all");
        }
//        private void compositeConfigurations() throws ConfigurationException{
//            CompositeConfiguration config = new CompositeConfiguration();
//            config.addConfiguration(new SystemConfiguration());
//            config.addConfiguration(new PropertiesConfiguration().s);
//
//            System.out.println(config.getString("configuration.first"));
//            System.out.println(config.getString("java.home"));
//        }
//        
//        private void automaticReloadingOfConfigurations() throws Exception {
//            PropertiesConfiguration configs = new  PropertiesConfiguration("configurations.properties");
//            configs.setReloadingStrategy(new FileChangedReloadingStrategy());
//
//            System.out.println(configs.getString("configuration.first"));
//            Thread.sleep(10000);
//            //change file on disk
//            System.out.println(configs.getString("configuration.first"));	
//        }
      
        private File getFile(){
           // final String property = System.getProperty("CONFIG_PATH");
		/*if (StringUtils.isEmpty(property)) {
			logger.warning("ERROR: Config path not entered. Use -DCONFIG_PATH to pass as VM argument.");
		} */
           // logger.info("Reading property file: " + "conf/config.xml");
            File file = new File("res/conf/config.xml");
            System.out.println(file.getAbsolutePath());
            return file;
        }
        
        public static void main(String args[]){
             ConfigReader.getInstance().config.addProperty("connections[@type='informatica'] connection/" + "CONNECTION_NAME", "PARIKSHIT");
        }
        
        public static ConfigReader getInstance() {
            return ConfigReaderHolder.INSTANCE;
        }
    
        private static class ConfigReaderHolder {

            private static final ConfigReader INSTANCE = new ConfigReader();
        }
        
        public void storeProperty(){
            try {
                ConfigReader.getInstance().builder.save();
            } catch (ConfigurationException ex) {
                java.util.logging.Logger.getLogger(ConfigReader.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        }
        

//        public String getConnectionName() {
//            return connectionName;
//        }
//
//        public void setConnectionName(String connectionName) {
//            this.connectionName = connectionName;
//        }
        
        public Iterator getConnectionNames(String instanceType){
            if (instanceType.equals(StringConstants.CONN_INFORMATICA))
                return ConfigReader.getInstance().config.getList("connections[@type='informatica']/connection/CONNECTION_NAME").listIterator();
            else{
                logger.debug("hi in odi");
                for(ListIterator i = ConfigReader.getInstance().config.getList("connections[@type='odi']/connection/CONNECTION_NAME").listIterator() ; i.hasNext();){
                    System.out.println(i.next());  
                }
                return ConfigReader.getInstance().config.getList("connections[@type='odi']/connection/CONNECTION_NAME").listIterator();
            }
        }
        
        public Map<String, String> getInformaticaConnectionDetails(String connectionName){
            int index = config.getList("connections[@type='informatica']/connection/CONNECTION_NAME").indexOf(connectionName)+1;
//            System.out.println(index);
//            List<HierarchicalConfiguration<ImmutableNode>> fields = config.configurationsAt("connections[@type='informatica']/connection[" + index + "]");
//            
//            for(HierarchicalConfiguration sub : fields)
//            {
                // sub contains all data about a single field
                Map<String, String> conn = new HashMap<>();
                conn.put(StringConstants.CONNECTION_NAME, connectionName);
                conn.put(StringConstants.ADMIN_USERNAME, config.getString("connections[@type='informatica']/connection[" + index + "]/ADMIN_USERNAME"));
                conn.put(StringConstants.ADMIN_PASSWORD, config.getString("connections[@type='informatica']/connection[" + index + "]/ADMIN_PASSWORD"));
                conn.put(StringConstants.TARGET_REPO_NAME, config.getString("connections[@type='informatica']/connection[" + index + "]/TARGET_REPO_NAME"));
                conn.put(StringConstants.REPO_SERVER_HOST, config.getString("connections[@type='informatica']/connection[" + index + "]/REPO_SERVER_HOST"));
                conn.put(StringConstants.REPO_SERVER_DOMAIN_NAME, config.getString("connections[@type='informatica']/connection[" + index + "]/REPO_SERVER_DOMAIN_NAME"));
                conn.put(StringConstants.REPO_SERVER_PORT, config.getString("connections[@type='informatica']/connection[" + index + "]/REPO_SERVER_PORT"));
         //   }
                return conn;
        }
        
        public String getInformaticaProperty(String connectionName, String property){
            if (property.equals(StringConstants.PC_CLIENT_INSTALL_PATH))
                return ConfigReader.getInstance().config.getString("connections[@type='informatica']/client/" + StringConstants.PC_CLIENT_INSTALL_PATH);
            else  if (property.equals(StringConstants.PMREP_CACHE_FOLDER))
                return ConfigReader.getInstance().config.getString("connections[@type='informatica']/client/" + StringConstants.PMREP_CACHE_FOLDER);        
            else{
                int index = config.getList("connections[@type='informatica']/connection/CONNECTION_NAME").indexOf(connectionName)+1;          
                return ConfigReader.getInstance().config.getString("connections[@type='informatica']/connection[" + index + "]/" + property);
            }
        }
        
        public void setInformaticaProperty(String connectionName, String key, String value){
            int index=config.getList("connections[@type='informatica']/connection/CONNECTION_NAME").indexOf(connectionName);
            System.out.println("ConfigReader-> connectionName: " + connectionName + " index: " + index + " key: " + key + " value: " + value);
            if(index==-1)
                ConfigReader.getInstance().config.addProperty("connections[@type='informatica'] connection/" + key, value);
            else if(key.equals(StringConstants.PC_CLIENT_INSTALL_PATH))
                ConfigReader.getInstance().config.setProperty("connections[@type='informatica']/client/" + StringConstants.PC_CLIENT_INSTALL_PATH, value);
            else if(key.equals(StringConstants.CONNECTION_NAME)){
                ConfigReader.getInstance().config.setProperty("connections[@type='informatica']/connection[" + (index + 1)+ "]/" + key, value);
                storeProperty();
            }
            else
                ConfigReader.getInstance().config.setProperty("connections[@type='informatica']/connection[" + (index + 1)+ "]/" + key, value);
        }
        
        public void deleteInformaticaNode(String connectionName){
            int index = config.getList("connections[@type='informatica']/connection/CONNECTION_NAME").indexOf(connectionName)+1;
            ConfigReader.getInstance().config.clearTree("connections[@type='informatica']/connection[" + index + "]");
            ConfigReader.getInstance().storeProperty();
        }
        
        public Map<String, String> getOdiConnectionDetails(String connectionName){
            int index = config.getList("connections[@type='odi']/connection/CONNECTION_NAME").indexOf(connectionName)+1;
//            System.out.println(index);
//            List<HierarchicalConfiguration<ImmutableNode>> fields = config.configurationsAt("connections[@type='informatica']/connection[" + index + "]");
//            
//            for(HierarchicalConfiguration sub : fields)
//            {
                // sub contains all data about a single field
                Map<String, String> conn = new HashMap<>();
                conn.put(StringConstants.CONNECTION_NAME, connectionName);
                conn.put(StringConstants.ODI_USERNAME, config.getString("connections[@type='odi']/connection[" + index + "]/ODI_USERNAME"));
                conn.put(StringConstants.ODI_PASSWORD, config.getString("connections[@type='odi']/connection[" + index + "]/ODI_PASSWORD"));
                conn.put(StringConstants.ODI_SCHEMA_USERNAME, config.getString("connections[@type='odi']/connection[" + index + "]/ODI_SCHEMA_USERNAME"));
                conn.put(StringConstants.ODI_SCHEMA_PASSWORD, config.getString("connections[@type='odi']/connection[" + index + "]/ODI_SCHEMA_PASSWORD"));
                conn.put(StringConstants.ODI_DRIVER_NAME, config.getString("connections[@type='odi']/connection[" + index + "]/ODI_DRIVER_NAME"));
                conn.put(StringConstants.ODI_DB_URL, config.getString("connections[@type='odi']/connection[" + index + "]/ODI_DB_URL"));
                conn.put(StringConstants.ODI_WORKREP, config.getString("connections[@type='odi']/connection[" + index + "]/ODI_WORKREP"));
         //   }
                return conn;
        }
        
        public String getOdiProperty(String connectionName, String property){
            int index = config.getList("connections[@type='odi']/connection/CONNECTION_NAME").indexOf(connectionName)+1;          
            return ConfigReader.getInstance().config.getString("connections[@type='odi']/connection[" + index + "]/" + property);
        }
        
        public void setOdiProperty(String connectionName, String key, String value){
            int index=config.getList("connections[@type='odi']/connection/CONNECTION_NAME").indexOf(connectionName);
            System.out.println("ConfigReader-> connectionName: " + connectionName + " index: " + index + " key: " + key + " value: " + value);
            if(index==-1)
                ConfigReader.getInstance().config.addProperty("connections[@type='odi'] connection/" + key, value);
            else if(key.equals(StringConstants.CONNECTION_NAME)){
                ConfigReader.getInstance().config.setProperty("connections[@type='odi']/connection[" + (index + 1)+ "]/" + key, value);
                storeProperty();
            }
            else
                ConfigReader.getInstance().config.setProperty("connections[@type='odi']/connection[" + (index + 1)+ "]/" + key, value);
        }
        
        public void deleteOdiNode(String connectionName){
            int index = config.getList("connections[@type='odi']/connection/CONNECTION_NAME").indexOf(connectionName)+1;
            ConfigReader.getInstance().config.clearTree("connections[@type='odi']/connection[" + index + "]");
            ConfigReader.getInstance().storeProperty();
        }
        
}
