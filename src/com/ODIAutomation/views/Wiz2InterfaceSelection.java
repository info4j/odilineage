/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ODIAutomation.views;

import com.ODIAutomation.channel.odi.OdiInstanceHandle;
import com.ODIAutomation.channel.odi.parser.Parser;
import com.ODIAutomation.commons.config.ConfigReader;
import com.ODIAutomation.core.StringConstants;
import com.ODIAutomation.library.JCheckBoxTree;
import java.awt.BorderLayout;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import oracle.odi.domain.mapping.Mapping;
import oracle.odi.domain.project.OdiFolder;
import oracle.odi.domain.project.OdiProject;

/**
 *
 * @author info4j
 */
public class Wiz2InterfaceSelection extends javax.swing.JPanel {
    
    TreeModel treeModel;
    JFrame parentFrame;
    
    private static final Logger logger = Logger.getLogger(Wiz2InterfaceSelection.class.getName());
    /**
     * Creates new form Wiz2FoldersSelection
     */
    public Wiz2InterfaceSelection() {
        
    }
    
    public Wiz2InterfaceSelection(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        initComponents();
        setMinimumSize(new java.awt.Dimension(750, 380));
        //setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        //add(lblIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 700, 200));
        
        lblImport = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jScrollPane = new javax.swing.JScrollPane();
        checkboxTree = new JCheckBoxTree();
        checkboxTree.setRootVisible(false);
	jScrollPane.setViewportView(checkboxTree);
        jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    }

    public void initialize(String connectionName){    
        if (OdiInstanceHandle.getInstance().getOdiInstance() == null || !OdiInstanceHandle.getInstance().getConnectionName().equals(connectionName)){
            try{
                OdiInstanceHandle.getInstance().create(connectionName, ConfigReader.getInstance().getOdiProperty(connectionName, StringConstants.ODI_DB_URL),
                        ConfigReader.getInstance().getOdiProperty(connectionName, StringConstants.ODI_DRIVER_NAME),
                        ConfigReader.getInstance().getOdiProperty(connectionName, StringConstants.ODI_SCHEMA_USERNAME),
                        ConfigReader.getInstance().getOdiProperty(connectionName, StringConstants.ODI_SCHEMA_PASSWORD),
                        ConfigReader.getInstance().getOdiProperty(connectionName, StringConstants.ODI_WORKREP),
                        ConfigReader.getInstance().getOdiProperty(connectionName, StringConstants.ODI_USERNAME),
                        ConfigReader.getInstance().getOdiProperty(connectionName, StringConstants.ODI_PASSWORD)).getOdiInstance() ;
                System.out.println("Connection Successful");
                new JOptionPane("Connection successful");
            }catch(Exception exp){
                JOptionPane.showMessageDialog(this, "Error in Connection: " + exp.getMessage(), "SEVERE", JOptionPane.ERROR_MESSAGE);
            }
            }
        
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                initComponents2();
            }
        });

    }
    
    public void showLoadscreen(boolean loadIcon){
        lblImport.setVisible(!loadIcon);
        jSeparator2.setVisible(!loadIcon);
        jScrollPane.setVisible(!loadIcon);
        checkboxTree.setVisible(!loadIcon);
        lblIcon.setVisible(loadIcon);
        
    }
    
    private void initComponents2(){  
        showLoadscreen(false);      
        lblImport.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblImport.setText("Select Mapping(s) to export");
//        add(lblImport, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 9, -1, -1));
//        add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 31, 693, 11));
//        add(jScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 50, 700, 300));
        
        treeModel = buildModel();
        checkboxTree.setModel(treeModel);
        //updateUI();
    }  

    public void getFinalList() throws FileNotFoundException{
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        logger.info("File chooser");
        chooser.setDialogTitle("Specify a file to save");
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("Excel File", "xlsx"));
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            final String exportDirectory = chooser.getSelectedFile().getAbsolutePath();
            progressDialog(exportDirectory);
            JOptionPane.showMessageDialog(this, "File created successfully", "Status", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void progressDialog(String exportDirectory) {
        JDialog dlgProgress = new JDialog(parentFrame, "Please wait...", true);//true means that the dialog created is modal
        JLabel lblStatus = new JLabel("Working..."); // this is just a label in which you can indicate the state of the processing

        JProgressBar pbProgress = new JProgressBar(0, 100);
        pbProgress.setIndeterminate(true); //we'll use an indeterminate progress bar

        dlgProgress.add(BorderLayout.NORTH, lblStatus);
        dlgProgress.add(BorderLayout.CENTER, pbProgress);
        dlgProgress.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE); // prevent the user from closing the dialog
        dlgProgress.setSize(300, 90);

        SwingWorker<Void, Void> sw = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                Parser parser = new Parser(exportDirectory);
                TreePath[] paths = checkboxTree.getCheckedPaths();
                for (TreePath tp : paths) {
                    for (Object pathPart : tp.getPath()) {
                        DefaultMutableTreeNode node = (DefaultMutableTreeNode) pathPart;
                        if (node.isLeaf()) {
                            ODITreeNode nodeObject = (ODITreeNode) node.getUserObject();
                            System.out.println(nodeObject.getInstance().toString() + " " + pathPart);
                            if (nodeObject.instanceOf(OdiFolder.class)) {
                                parser.parseFolder((OdiFolder) nodeObject.data);
                            }

                        }
                    }
                }
                parser.flush();
                return null;
            }

            @Override
            protected void done() {
                dlgProgress.dispose();//close the modal dialog
            }
        };

        sw.execute(); // this will start the processing on a separate thread
        dlgProgress.setVisible(true); //this will block user input as long as the processing task is working

    }
       
    //====================== Your Domain specific stuff goes here

    protected TreeModel buildModel() {
        DefaultMutableTreeNode topNode = new DefaultMutableTreeNode("Root");
        DefaultMutableTreeNode node;
        
        Collection<OdiProject> projects = OdiInstanceHandle.getInstance().getOdiInstance().getTransactionalEntityManager().findAll(OdiProject.class);
        for(OdiProject project : projects){
            ODITreeNode d = new ODITreeNode(project);
            node = new DefaultMutableTreeNode(d);
            topNode.add(node);
            Collection<OdiFolder> folders = project.getFolders();
            for(OdiFolder folder : folders){  
                DefaultMutableTreeNode subnode = new DefaultMutableTreeNode(new ODITreeNode(folder));
                node.add(subnode);
                iterateFolder(subnode, folder);
            }
        }
        
        return new DefaultTreeModel(topNode);
    }

    
    private void iterateFolder(DefaultMutableTreeNode node, OdiFolder folder){
        if (folder.getSubFolders() != null){
             for(OdiFolder subfolder : folder.getSubFolders()){
                 DefaultMutableTreeNode subnode = new DefaultMutableTreeNode(new ODITreeNode(subfolder));
                 node.add(subnode);
                 iterateFolder(subnode, subfolder);
             }
        } else{
            for(Mapping mapping : folder.getMappings()){
                DefaultMutableTreeNode subnode = new DefaultMutableTreeNode(new ODITreeNode(mapping));
                node.add(new DefaultMutableTreeNode(subnode));
            }
        }

     }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblIcon = new javax.swing.JLabel();

        lblIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ajax-loader.gif"))); // NOI18N

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblIcon;
    // End of variables declaration//GEN-END:variables
    private javax.swing.JLabel lblImport;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JScrollPane jScrollPane;
    private JCheckBoxTree checkboxTree;
}
