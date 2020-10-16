/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ODIAutomation.views;

import oracle.odi.domain.mapping.Mapping;
import oracle.odi.domain.project.OdiFolder;
import oracle.odi.domain.project.OdiProject;

/**
 *
 * @author info4j
 */
public class ODITreeNode {
    protected Object data;

    public ODITreeNode(Object obj) {
        data = obj;
    }
    
    public String toString() {
        String name;
        if (data !=null){
            if (data instanceof OdiProject)
                name = ((OdiProject)data).getName();
            else if (data instanceof OdiFolder)
                name = ((OdiFolder)data).getName();
            else if (data instanceof Mapping)
                name = ((OdiFolder)data).getName();
            else
                name = data.toString();
        }else 
            name = "(EMPTY)";
        return name;
    }

    public boolean instanceOf(Class obj){
        if(obj.isInstance(data)) {
            return true;
        }
        else
            return false;
    }

    public Class getInstance(){
        return data.getClass();
    }
}
