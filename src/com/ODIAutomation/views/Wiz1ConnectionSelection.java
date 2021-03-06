/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ODIAutomation.views;

import com.ODIAutomation.commons.config.ConfigReader;
import com.ODIAutomation.core.StringConstants;
import java.util.Iterator;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;

/**
 *
 * @author info4j
 */
public class Wiz1ConnectionSelection extends javax.swing.JPanel {
    DefaultListModel listInformaticaModelConnections;
    DefaultListModel listOdiModelConnections;
    JFrame parentFrame;
    String connectionMode = StringConstants.CONN_ODI;

    /**
     * Creates new form Wiz1ConnectionSelection
     */
    public Wiz1ConnectionSelection() {
        initComponents();
    }
    
    public Wiz1ConnectionSelection(JFrame parent, String connectionMode) {
        parentFrame=parent;
        this.connectionMode = connectionMode;
        initComponents();
        if (connectionMode.equals(StringConstants.CONN_ODI))
            initializeODIInstance();
        
    }
    
   
   
   public String getSelectedConnection(){
       System.out.println("sel value: " + lstConnections.getSelectedValue().toString());
       return lstConnections.getSelectedValue().toString();
   }
   
    
   void initializeODIInstance(){
       System.out.println("odi instnace");
       jLabel2.setText("1. ODI Instance");
        Iterator iter = ConfigReader.getInstance().getConnectionNames(StringConstants.CONN_ODI);
        listOdiModelConnections = new DefaultListModel();
        while(iter.hasNext())
            listOdiModelConnections.addElement(iter.next());
        lstConnections.setModel(listOdiModelConnections);
        lstConnections.setSelectedIndex(0);
    }
   
    void showDesc(String selectedVal){
        Map<String, String> conn = ConfigReader.getInstance().getOdiConnectionDetails(selectedVal);
        lblDesc.setText("<html><body>" +
            "<b>" + StringConstants.CONNECTION_NAME + "</b>: " + conn.get(StringConstants.CONNECTION_NAME) + "<br /><br />" +
            "<b>" + StringConstants.ODI_USERNAME + "</b>: " + conn.get(StringConstants.ODI_USERNAME) + "<br /><br />" +
            "<b>" + StringConstants.ODI_PASSWORD + "</b>: " + conn.get(StringConstants.ODI_PASSWORD) + "<br /><br />" +
            "<b>" + StringConstants.ODI_SCHEMA_USERNAME + "</b>: " + conn.get(StringConstants.ODI_SCHEMA_USERNAME) + "<br /><br />" +
            "<b>" + StringConstants.ODI_SCHEMA_PASSWORD + "</b>: " + conn.get(StringConstants.ODI_SCHEMA_PASSWORD) + "<br /><br />" +
            "<b>" + StringConstants.ODI_DRIVER_NAME + "</b>: " + conn.get(StringConstants.ODI_DRIVER_NAME) + "<br /><br />" +
            "<b>" + StringConstants.ODI_DB_URL + "</b>: " + conn.get(StringConstants.ODI_DB_URL) + "<br /><br />" +
            "<b>" + StringConstants.ODI_WORKREP + "</b>: " + conn.get(StringConstants.ODI_WORKREP) + "<br />" 
            + "</body></html>");
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstConnections = new javax.swing.JList();
        lblDesc = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("1. Informatica Instance");

        lstConnections.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        lstConnections.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstConnections.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstConnectionsValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(lstConnections);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/application_edit.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 649, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblDesc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void lstConnectionsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstConnectionsValueChanged
        if (lstConnections.getSelectedValue() == null)
            lstConnections.setSelectedIndex(0);
        showDesc(lstConnections.getSelectedValue().toString());
    }//GEN-LAST:event_lstConnectionsValueChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        ConnectionODI newConnection = new ConnectionODI(parentFrame, true, null);  
        newConnection.setVisible(true);
        System.out.println(newConnection.getReturnStatus());
        if(newConnection.getReturnStatus()==1){
            listOdiModelConnections.addElement(newConnection.getConnectionName());
            lstConnections.setSelectedIndex(lstConnections.getLastVisibleIndex());
            showDesc(newConnection.getConnectionName());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        System.out.println("connectionMOde: " + connectionMode + lstConnections.getSelectedValue().toString());
            ConnectionODI editConnection = new ConnectionODI(parentFrame, true, lstConnections.getSelectedValue().toString());
            editConnection.setVisible(true);
            if(editConnection.getReturnStatus()==1){
                if(!lstConnections.getSelectedValue().equals(editConnection.getConnectionName()))
                    listOdiModelConnections.set(lstConnections.getSelectedIndex(), editConnection.getConnectionName());
                showDesc(editConnection.getConnectionName());
            }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String val = lstConnections.getSelectedValue().toString();
        lstConnections.setSelectedIndex(lstConnections.getSelectedIndex() - 1);
        ConfigReader.getInstance().deleteOdiNode(val);
        listOdiModelConnections.removeElement(val);
    }//GEN-LAST:event_jButton3ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDesc;
    private javax.swing.JList lstConnections;
    // End of variables declaration//GEN-END:variables
}
