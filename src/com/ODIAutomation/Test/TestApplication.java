/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ODIAutomation.Test;

import com.ODIAutomation.channel.odi.OdiInstanceHandle;
import com.ODIAutomation.channel.odi.parser.ParseMapping;
import com.ODIAutomation.commons.config.ConfigReader;
import com.ODIAutomation.core.StringConstants;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import oracle.odi.core.OdiInstance;
import oracle.odi.domain.adapter.AdapterException;
import oracle.odi.domain.mapping.IMapComponent;
import oracle.odi.domain.mapping.MapAttribute;
import oracle.odi.domain.mapping.Mapping;
import oracle.odi.domain.mapping.exception.MappingException;
import oracle.odi.domain.mapping.finder.IMappingFinder;

/**
 *
 * @author info4j
 */
public class TestApplication {
    OdiInstance odiInstance;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new TestApplication();
    }

    public TestApplication() {
        instance("Local_ODI12C_RTB");
        parse();
        //parseString();
    }
    
    private void parseString(){
        String str = "[SIL_WFM_RateCardDimension_SQ_WFM_RATE_CARD_D (SIL_WFM_RateCardDimension_SQ_WFM_RATE_CARD_D)]";
        System.out.println(str.matches("\\[.*\\(.*\\)\\]\\.\\[.*\\]"));
        String str1 = str.substring(str.indexOf("(")+1, str.indexOf(")"));
        String str2 = str.substring(str.lastIndexOf("[") + 1, str.lastIndexOf("]"));
        System.out.println(str1);
        System.out.println(str2);
    }
    private void parse(){
        Collection<Mapping> odiMappings = ((IMappingFinder)odiInstance.getTransactionalEntityManager().getFinder(Mapping.class)).findByProject("BI_PROJECT", "SIL_WFM_DemandTrackerDimension");
        for (Mapping odiMapping : odiMappings){
          getTarget(odiMapping);
      }
    }
    
    private void getTarget(Mapping odiMapping){
        try {
            List<IMapComponent> targets = odiMapping.getTargets();
            for (IMapComponent target : targets){
                parseTargetTable(target);
            }          
        } catch (MappingException ex) {
            ex.printStackTrace();
        }
    }
    
    private void parseTargetTable(IMapComponent target){
        try {
            MapAttribute ma = target.getAttributes().get(0);
            System.out.println("Target bound Name: " + target.getBoundObjectName());
            System.out.println("Target Name: " + target.getName());
            System.out.println("Target Column: " + ma.getName());
            System.out.println("first source attribute Column: " + ma.getFirstSourceAttribute().getBoundObjectName());
            System.out.println("first source attribute Column: " + ma.getFirstSourceAttribute().getName());
            System.out.println("sql access name: " + ma.getLeafAttribute().getBoundObjectName());
            System.out.println("technical desc: " + ma.getChildAttributes().toString());
            
        } catch (MappingException ex) {
            Logger.getLogger(TestApplication.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AdapterException ex) {
            Logger.getLogger(TestApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void instance(String connectionName) {
        if (OdiInstanceHandle.getInstance().getOdiInstance() == null || !OdiInstanceHandle.getInstance().getConnectionName().equals(connectionName)) {
            try {
                OdiInstanceHandle.getInstance().create(connectionName, ConfigReader.getInstance().getOdiProperty(connectionName, StringConstants.ODI_DB_URL),
                        ConfigReader.getInstance().getOdiProperty(connectionName, StringConstants.ODI_DRIVER_NAME),
                        ConfigReader.getInstance().getOdiProperty(connectionName, StringConstants.ODI_SCHEMA_USERNAME),
                        ConfigReader.getInstance().getOdiProperty(connectionName, StringConstants.ODI_SCHEMA_PASSWORD),
                        ConfigReader.getInstance().getOdiProperty(connectionName, StringConstants.ODI_WORKREP),
                        ConfigReader.getInstance().getOdiProperty(connectionName, StringConstants.ODI_USERNAME),
                        ConfigReader.getInstance().getOdiProperty(connectionName, StringConstants.ODI_PASSWORD)).getOdiInstance();
                System.out.println("Connection Successful");
                odiInstance = OdiInstanceHandle.getInstance().getOdiInstance();
            } catch (Exception exp) {
                JOptionPane.showMessageDialog(null, "Error in Connection: " + exp.getMessage(), "SEVERE", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
