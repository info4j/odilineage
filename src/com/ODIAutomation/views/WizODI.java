/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ODIAutomation.views;


import com.ODIAutomation.channel.odi.OdiInstanceHandle;
import com.ODIAutomation.core.StringConstants;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

/**
 *
 * @author info4j
 */
public class WizODI extends javax.swing.JPanel {
    JFrame parentFrame;
    int page=1; //btn clicked
 
    public WizODI() {
        initComponents();
    }
    
    public WizODI(JFrame parent) {
        parentFrame=parent;
        initComponents();
        //wiz1ConnectionSelection.setConnectionMode(StringConstants.CONN_ODI);
        //wiz2OdiImport.setVisible(false);
        wiz2InterfaceSelection.setVisible(false);
        wiz1ConnectionSelection.setVisible(true);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnNext = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        wiz1ConnectionSelection = new com.ODIAutomation.views.Wiz1ConnectionSelection(parentFrame, StringConstants.CONN_ODI);
        wiz2InterfaceSelection = new com.ODIAutomation.views.Wiz2InterfaceSelection(parentFrame);
        jButton1 = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(734, 375));
        setRequestFocusEnabled(false);

        btnNext.setText("Next");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnBack.setText("Back");
        btnBack.setEnabled(false);
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        jLayeredPane1.add(wiz1ConnectionSelection);
        wiz1ConnectionSelection.setBounds(-5, -4, 730, 330);

        wiz2InterfaceSelection.setMaximumSize(new java.awt.Dimension(725, 326));
        wiz2InterfaceSelection.setMinimumSize(new java.awt.Dimension(725, 326));
        wiz2InterfaceSelection.setPreferredSize(new java.awt.Dimension(725, 326));
        jLayeredPane1.add(wiz2InterfaceSelection);
        wiz2InterfaceSelection.setBounds(0, 0, 700, 310);

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/info4j_50.png"))); // NOI18N
        jButton1.setText("<html><h3>Info4J</h3></html>");
        jButton1.setBorder(null);
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 436, Short.MAX_VALUE)
                        .addComponent(btnBack)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnNext)
                        .addGap(26, 26, 26))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLayeredPane1)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnBack, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnNext, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(12, 12, 12))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        btnBack.setEnabled(true);
        switch(page){
            case 1:
                page++;
                showStep(page);
                btnNext.setText("Execute");
                break;
            case 2:
                if (!btnNext.getText().equals("Execute")){
                    showStep(page);
                }
                btnNext.setText("Execute");

                try {
                    wiz2InterfaceSelection.getFinalList();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(WizODI.class.getName()).log(Level.SEVERE, null, ex);
                }
                //wiz2OdiImport.createInterface();
                //new Wiz2OdiValidation(parentFrame, true, wiz2OdiImport.selMappings); //create and validate interfaces
                break;
            default:
                btnNext.setText("Next");
        }

    }//GEN-LAST:event_btnNextActionPerformed

    
    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        btnNext.setEnabled(true);
        btnNext.setText("Next");
        switch(page){
            case 2:
                page--;
                showStep(page);
                break;
            case 1:
                showStep(page);
                btnBack.setEnabled(false);
                break;
        }
    }//GEN-LAST:event_btnBackActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       if (Desktop.isDesktopSupported()) {
            try {
              Desktop.getDesktop().browse(new URI("www.info4j.com"));
              } catch (IOException | URISyntaxException ex) {
               Logger.getLogger(WizODI.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
    }//GEN-LAST:event_jButton1ActionPerformed


    public void showStep(int index) {
        switch (index) {
            case 1:
                wiz1ConnectionSelection.setVisible(true);
                wiz2InterfaceSelection.setVisible(false);
                wiz2InterfaceSelection.showLoadscreen(true);
                //OdiInstanceHandle.getInstance().release();
                //wiz2OdiImport.setVisible(false);
                break;

            case 2:
                btnBack.setEnabled(false);
                btnNext.setEnabled(false);
                wiz1ConnectionSelection.setVisible(false);
                wiz2InterfaceSelection.setVisible(true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        wiz2InterfaceSelection.initialize(wiz1ConnectionSelection.getSelectedConnection());
                        btnNext.setEnabled(true);
                        btnBack.setEnabled(true);
                    }
                }).start();

                break;
            }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton jButton1;
    private javax.swing.JLayeredPane jLayeredPane1;
    private com.ODIAutomation.views.Wiz1ConnectionSelection wiz1ConnectionSelection;
    private com.ODIAutomation.views.Wiz2InterfaceSelection wiz2InterfaceSelection;
    // End of variables declaration//GEN-END:variables
}
