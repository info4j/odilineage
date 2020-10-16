/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ODIAutomation.channel.odi.parser;

import com.ODIAutomation.channel.io.odiMappingOutputStream;
import com.ODIAutomation.channel.odi.OdiInstanceHandle;
import com.ODIAutomation.core.StringConstants;
import static com.sun.javafx.scene.CameraHelper.project;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import oracle.odi.core.OdiInstance;
import oracle.odi.core.persistence.transaction.ITransactionDefinition;
import oracle.odi.core.persistence.transaction.ITransactionManager;
import oracle.odi.core.persistence.transaction.ITransactionStatus;
import oracle.odi.core.persistence.transaction.support.DefaultTransactionDefinition;
import oracle.odi.domain.adapter.AdapterException;
import oracle.odi.domain.mapping.IMapComponent;
import oracle.odi.domain.mapping.MapAttribute;
import oracle.odi.domain.mapping.Mapping;
import oracle.odi.domain.mapping.ReusableMapping;
import oracle.odi.domain.mapping.exception.MappingException;
import oracle.odi.domain.mapping.expression.MapExpression;
import oracle.odi.domain.mapping.finder.IMappingFinder;
import oracle.odi.domain.mapping.finder.IReusableMappingFinder;
import oracle.odi.domain.project.OdiFolder;
import oracle.odi.domain.project.OdiInterface;
import oracle.odi.domain.project.finder.IOdiFolderFinder;
import oracle.odi.mapping.generation.StringConstant;
import org.apache.log4j.Logger;

/**
 *
 * @author info4j (parikshit)
 * 
 * This is a core file and have logics to parse odi mapping and write it to excel
 */
public class ParseMapping {

    private static final Logger logger = Logger.getLogger(ParseMapping.class.getName());
    
    odiMappingOutputStream mos;
    Mapping odiMapping;
    /**
     *
     * @param mapping
     */
    public ParseMapping(odiMappingOutputStream mos, Mapping odiMapping) {
        this.mos = mos;
        this.odiMapping = odiMapping;
        
        writeOverview();
        getTarget();
        
    }

    //Returns the target table and calls parseTargetTable for columns
    private void getTarget(){
        try {
            List<IMapComponent> targets = odiMapping.getTargets();
            for (IMapComponent target : targets){
                logger.info("Target:" + target.getName());
                parseTargetTable(target);
            }           
        } catch (MappingException ex) {
            logger.error(ex);
        }
    }
    
    // parse the target table columns for name, expression, and datatype
    private void parseTargetTable(IMapComponent target){
        mos.setLineageHeader();
        try {
            List<MapAttribute> mas = target.getAttributes();
            for (MapAttribute ma : mas){
                if(ma.getExpression() != null){
                    logger.info("Attribute: " + ma.getName() + 
                            " Expression: " + ma.getExpression().getText() + 
                            " Datatype: " + ma.getDataType().getName() + (ma.isSizeSet()? "(" + ma.getSize() +  (ma.isScaleSet() ? "," + ma.getScale() : "") + ")" : ""));
                    String str = ma.getTechnicalDescription();
                    String str1 = StringConstants.BLANK, str2 = StringConstants.BLANK;
                    if (str != null && !str.isEmpty() && str.matches("\\[.*\\(.*\\)\\]\\.\\[.*\\]")){
                        str1 = (str.substring(str.indexOf("(")+1, str.indexOf(")")));
                        str2 = str.substring(str.lastIndexOf("[") + 1, str.lastIndexOf("]"));
                    }
                    
                    mos.setLineageRow(target.getBoundObjectName(), 
                            ma.getName(), 
                            ma.getDataType().getName() +  (ma.isSizeSet()? "(" + ma.getSize() +  (ma.isScaleSet() ? "," + ma.getScale() : "") + ")" : ""), 
                            ma.getExpression().getText(), str1, str2);
                    
                }else{
                    logger.info("Attribute: " + ma.getName() + 
                            " Datatype: " + ma.getDataType().getName() + (ma.isSizeSet()? "(" + ma.getSize() +  (ma.isScaleSet() ? "," + ma.getScale() : "") + ")" : ""));
                    mos.setLineageRow(target.getName(), 
                            ma.getName(), 
                            ma.getDataType().getName() + (ma.isSizeSet()? "(" + ma.getSize() +  (ma.isScaleSet() ? "," + ma.getScale() : "") + ")" : ""), 
                            StringConstants.BLANK, StringConstants.BLANK, StringConstants.BLANK);
                }
            }           
        } catch (MappingException ex) {
            logger.error(ex);
        } catch (AdapterException ex) {
            logger.error(ex);
        }
    }
    
    //Writes header information
    private void writeOverview(){
        logger.info("Mapping: " + odiMapping.getName());
        logger.info("Created by: " + odiMapping.getFirstUser() + " Created on: " + odiMapping.getFirstDate());
        logger.info("Updated by: " + odiMapping.getLastUser() + " Updated on: " + odiMapping.getLastDate());
        
        mos.setInterface(odiMapping.getName());
        mos.setCreatedByOn(odiMapping.getFirstUser(),odiMapping.getFirstDate());
        mos.setUpdatedByOn(odiMapping.getLastUser(),odiMapping.getLastDate());
    }
    
    

}
