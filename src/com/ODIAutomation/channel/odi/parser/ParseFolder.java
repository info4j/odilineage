/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ODIAutomation.channel.odi.parser;

import com.ODIAutomation.channel.io.odiMappingOutputStream;
import com.ODIAutomation.channel.odi.OdiInstanceHandle;
import static com.sun.javafx.scene.CameraHelper.project;
import java.util.Collection;
import oracle.odi.core.OdiInstance;
import oracle.odi.core.persistence.transaction.ITransactionDefinition;
import oracle.odi.core.persistence.transaction.ITransactionManager;
import oracle.odi.core.persistence.transaction.ITransactionStatus;
import oracle.odi.core.persistence.transaction.support.DefaultTransactionDefinition;
import oracle.odi.domain.mapping.Mapping;
import oracle.odi.domain.mapping.ReusableMapping;
import oracle.odi.domain.mapping.finder.IMappingFinder;
import oracle.odi.domain.mapping.finder.IReusableMappingFinder;
import oracle.odi.domain.project.OdiFolder;
import oracle.odi.domain.project.OdiInterface;
import oracle.odi.domain.project.finder.IOdiFolderFinder;
import org.apache.log4j.Logger;

/**
 *
 * @author info4j (parikshit)
 * 
 * This is a core file and have logics to parse informatica workflow and read objects and trasformations in a defined logic and then export the workflow objects in the sheet.
 */
public class ParseFolder {

    private static final Logger logger = Logger.getLogger(ParseFolder.class.getName());
    
    odiMappingOutputStream mos;
    OdiInstance odiInstance;
    OdiFolder odiFolder;

    /**
     *
     * @param mapping
     */
    public ParseFolder(odiMappingOutputStream mos, OdiFolder odiFolder) {
        this.mos = mos;
        this.odiFolder = odiFolder;
        
        odiInstance = OdiInstanceHandle.getInstance().getOdiInstance();
        ITransactionDefinition txnDef = new DefaultTransactionDefinition();
        ITransactionManager tm = odiInstance.getTransactionManager();
        ITransactionStatus txnStatus = tm.getTransaction(txnDef);
        
        parseFolder();
        
        tm.commit(txnStatus);
    }

    private void parseFolder(){
      
//      //Temporary-----------------------------------
//      Collection<OdiFolder> odiFolders = ((IOdiFolderFinder)odiInstance.getTransactionalEntityManager().getFinder(OdiFolder.class)).findByName("First Folder","INFA2ODI");
//      if (odiFolders.size() == 0) {System.err.println("Error: cannot find folder in project "); return; }
//      odiFolder = (OdiFolder) (odiFolders.toArray()[0]);
//      //------------------------------------------
      logger.info("Folder: " + odiFolder.getName());
      mos.setFolder(odiFolder.getName());   //write folder name
      
      Collection<Mapping> odiMappings = ((IMappingFinder)odiInstance.getTransactionalEntityManager().getFinder(Mapping.class)).findByProject(odiFolder.getProject().getCode(), odiFolder.getName());
      //if (odiMappings.size() == 0) {System.err.println("Error: cannot find folder "+folder+" in project "+project); return; }
      
      //Collection<ReusableMapping> odiReusableMappings = ((IReusableMappingFinder)odiInstance.getTransactionalEntityManager().getFinder(ReusableMapping.class)).findByProject(odiFolder.getProject().getCode(), odiFolder.getName());
      //if (odiMappings.size() == 0) {System.err.println("Error: cannot find folder "+folder+" in project "+project); return; }
      
      for (Mapping odiMapping : odiMappings){
          new ParseMapping(mos, odiMapping);
          mos.setBlankRow();
      }
      
//      for (ReusableMapping odiReusableMapping : odiReusableMappings)
//          parseReusableMapping(odiReusableMapping);
    }
    
    
}
