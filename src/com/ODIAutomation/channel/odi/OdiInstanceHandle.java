package com.ODIAutomation.channel.odi;

import oracle.odi.core.OdiInstance;
import oracle.odi.core.config.MasterRepositoryDbInfo;
import oracle.odi.core.config.OdiConfigurationException;
import oracle.odi.core.config.OdiInstanceConfig;
import oracle.odi.core.config.PoolingAttributes;
import oracle.odi.core.config.WorkRepositoryDbInfo;
import oracle.odi.core.security.Authentication;
import oracle.odi.core.security.AuthenticationException;
import org.apache.log4j.Logger;

/**
 * This class is used in samples to help in OdiInstance and Authentication
 * management. It can also be used as sample on how 3rd party ODI repository consumers 
 * can build and manage an OdiInstance for use cases where a single user and a 
 * single thread is involved.
 * 
 */
public class OdiInstanceHandle {
	
        private static final Logger logger = Logger.getLogger(OdiInstanceHandle.class.getName());
        
	private OdiInstance mOdiInstance;
        private String connectionName;
        private String odiUsername, odiPassword;
		
	public OdiInstanceHandle create(String connectionName, String pMasterReposUrl, String pMasterReposDriver, 
			String pMasterReposUser, String pMasterReposPassword, String pWorkReposName, String pOdiUsername, 
			String pOdiPassword) throws OdiConfigurationException, AuthenticationException {
		
                logger.debug(pMasterReposUrl + "///" + pMasterReposDriver + "///" + pMasterReposUser + "///" + pMasterReposPassword + "///" + pWorkReposName + "///" + pOdiUsername + "///" + pOdiPassword);
                
                odiUsername = pOdiUsername;
                odiPassword = pOdiPassword;
                this.connectionName = connectionName;
               // try{
                    MasterRepositoryDbInfo masterInfo = new MasterRepositoryDbInfo(pMasterReposUrl, pMasterReposDriver, 
                                    pMasterReposUser, pMasterReposPassword.toCharArray(), new PoolingAttributes());
                    WorkRepositoryDbInfo workInfo = null;
                    if (pWorkReposName != null)
                            workInfo = new WorkRepositoryDbInfo(pWorkReposName, new PoolingAttributes());

                    OdiInstanceConfig odiInstanceConfig = new OdiInstanceConfig(masterInfo, workInfo);
                    mOdiInstance = OdiInstance.createInstance(odiInstanceConfig);
                    try {
                            Authentication auth = mOdiInstance.getSecurityManager().createAuthentication(pOdiUsername, pOdiPassword.toCharArray());
                            mOdiInstance.getSecurityManager().setGlobalAuthentication(auth);
                            return new OdiInstanceHandle(mOdiInstance);
                    } catch (RuntimeException e) {
                            mOdiInstance.close();
                            throw e;
                    }
//                } catch(Exception E){
//                    E.printStackTrace();
//                    throw E;
//                }
	}
	
        public OdiInstance createAuthentication(){
            if (!mOdiInstance.getSecurityManager().hasCurrentThreadAuthentication()){
                Authentication auth = mOdiInstance.getSecurityManager().createAuthentication(odiUsername, odiPassword.toCharArray());
                mOdiInstance.getSecurityManager().setCurrentThreadAuthentication(auth);
                return mOdiInstance;
            }
            return mOdiInstance;
        }
        
        public String getConnectionName(){
            return connectionName;
        }
	public OdiInstanceHandle create(String connectionName, String pMasterReposUrl, String pMasterReposDriver, 
			String pMasterReposUser, String pMasterReposPassword, String pOdiUsername, 
			String pOdiPassword) throws OdiConfigurationException, AuthenticationException {
		
		return create(connectionName, pMasterReposUrl, pMasterReposDriver, pMasterReposUser, pMasterReposPassword, null, pOdiUsername, pOdiPassword);
	}
		
	private OdiInstanceHandle(OdiInstance pOdiInstance) {
		mOdiInstance = pOdiInstance;
	}

        private OdiInstanceHandle() {
        }
	
	public OdiInstance getOdiInstance() {
		return mOdiInstance;
	}
			
	public void release() {
		try {
			Authentication auth = mOdiInstance.getSecurityManager().getCurrentAuthentication();
			mOdiInstance.getSecurityManager().clearCurrentThreadAuthentication();
			auth.close();
		}
		finally {
			mOdiInstance.close();
		}
	}
        
        public static OdiInstanceHandle getInstance() {
            return InstanceHandler.INSTANCE;
        }
    
        private static class InstanceHandler {
            private static final OdiInstanceHandle INSTANCE = new OdiInstanceHandle();
        }
}
