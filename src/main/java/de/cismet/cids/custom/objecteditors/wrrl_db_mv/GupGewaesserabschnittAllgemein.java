/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * GupMassnahmeSohle.java
 *
 * Created on 19.10.2011, 11:49:19
 */
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import java.awt.EventQueue;

import java.io.File;

import java.util.Arrays;

import javax.swing.JFileChooser;

import de.cismet.cids.client.tools.DevelopmentTools;

import de.cismet.cids.custom.wrrl_db_mv.util.RendererTools;
import de.cismet.cids.custom.wrrl_db_mv.util.gup.DocumentDropList;
import de.cismet.cids.custom.wrrl_db_mv.util.gup.GupHelper;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultCustomObjectEditor;
import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class GupGewaesserabschnittAllgemein extends javax.swing.JPanel implements CidsBeanRenderer, EditorSaveListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            GupGewaesserabschnittAllgemein.class);

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    private boolean readOnly = false;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.cismet.tools.gui.RoundedPanel glassPanel;
    private javax.swing.JButton jbAdd;
    private javax.swing.JButton jbDownload;
    private javax.swing.JList jlObjectList;
    private javax.swing.JPanel jpControl;
    private javax.swing.JButton jpDelete;
    private javax.swing.JScrollPane jsObjectList;
    private javax.swing.JLabel lblGewaessername;
    private javax.swing.JLabel lblGwk;
    private javax.swing.JLabel lblObjectList;
    private javax.swing.JTextField txtGewaessername;
    private javax.swing.JTextField txtGwk;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form GupMassnahmeSohle.
     */
    public GupGewaesserabschnittAllgemein() {
        this(false);
    }

    /**
     * Creates new form GupMassnahmeSohle.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public GupGewaesserabschnittAllgemein(final boolean readOnly) {
        this.readOnly = readOnly;
        initComponents();

        if (readOnly) {
            RendererTools.makeReadOnly(txtGewaessername);
        }
        jpDelete.setEnabled(!readOnly);
        jbAdd.setEnabled(!readOnly);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        lblGewaessername = new javax.swing.JLabel();
        lblGwk = new javax.swing.JLabel();
        lblObjectList = new javax.swing.JLabel();
        txtGewaessername = new javax.swing.JTextField();
        txtGwk = new javax.swing.JTextField();
        jsObjectList = new javax.swing.JScrollPane();
        jlObjectList = new DocumentDropList(readOnly, "dokumente");
        jpControl = new javax.swing.JPanel();
        jbDownload = new javax.swing.JButton();
        jpDelete = new javax.swing.JButton();
        jbAdd = new javax.swing.JButton();
        glassPanel = new de.cismet.tools.gui.RoundedPanel();

        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(994, 300));
        setLayout(new java.awt.GridBagLayout());

        lblGewaessername.setText(org.openide.util.NbBundle.getMessage(
                GupGewaesserabschnittAllgemein.class,
                "GupGewaesserabschnittAllgemein.lblGewaessername.text")); // NOI18N
        lblGewaessername.setMaximumSize(new java.awt.Dimension(170, 17));
        lblGewaessername.setMinimumSize(new java.awt.Dimension(170, 17));
        lblGewaessername.setPreferredSize(new java.awt.Dimension(170, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(25, 15, 5, 5);
        add(lblGewaessername, gridBagConstraints);

        lblGwk.setText(org.openide.util.NbBundle.getMessage(
                GupGewaesserabschnittAllgemein.class,
                "GupGewaesserabschnittAllgemein.lblGwk.text")); // NOI18N
        lblGwk.setMaximumSize(new java.awt.Dimension(170, 17));
        lblGwk.setMinimumSize(new java.awt.Dimension(170, 17));
        lblGwk.setPreferredSize(new java.awt.Dimension(170, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        add(lblGwk, gridBagConstraints);

        lblObjectList.setText(org.openide.util.NbBundle.getMessage(
                GupGewaesserabschnittAllgemein.class,
                "GupGewaesserabschnittAllgemein.lblObjectList.text")); // NOI18N
        lblObjectList.setMaximumSize(new java.awt.Dimension(170, 17));
        lblObjectList.setMinimumSize(new java.awt.Dimension(170, 17));
        lblObjectList.setPreferredSize(new java.awt.Dimension(170, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        add(lblObjectList, gridBagConstraints);

        txtGewaessername.setMaximumSize(new java.awt.Dimension(280, 20));
        txtGewaessername.setMinimumSize(new java.awt.Dimension(280, 20));
        txtGewaessername.setPreferredSize(new java.awt.Dimension(380, 20));

        final org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.name}"),
                txtGewaessername,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(25, 5, 5, 15);
        add(txtGewaessername, gridBagConstraints);

        txtGwk.setEnabled(false);
        txtGwk.setMaximumSize(new java.awt.Dimension(280, 20));
        txtGwk.setMinimumSize(new java.awt.Dimension(280, 20));
        txtGwk.setPreferredSize(new java.awt.Dimension(380, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 15);
        add(txtGwk, gridBagConstraints);

        final org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create(
                "${cidsBean.dokumente}");
        final org.jdesktop.swingbinding.JListBinding jListBinding = org.jdesktop.swingbinding.SwingBindings
                    .createJListBinding(
                        org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                        this,
                        eLProperty,
                        jlObjectList);
        jListBinding.setSourceNullValue(null);
        jListBinding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(jListBinding);

        jsObjectList.setViewportView(jlObjectList);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 15);
        add(jsObjectList, gridBagConstraints);

        jpControl.setOpaque(false);
        jpControl.setLayout(new java.awt.GridBagLayout());

        jbDownload.setText(org.openide.util.NbBundle.getMessage(
                GupGewaesserabschnittAllgemein.class,
                "GupGewaesserabschnittAllgemein.jbDownload.text")); // NOI18N
        jbDownload.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jbDownloadActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 15, 15);
        jpControl.add(jbDownload, gridBagConstraints);

        jpDelete.setText(org.openide.util.NbBundle.getMessage(
                GupGewaesserabschnittAllgemein.class,
                "GupGewaesserabschnittAllgemein.jpDelete.text")); // NOI18N
        jpDelete.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jpDeleteActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 15, 0);
        jpControl.add(jpDelete, gridBagConstraints);

        jbAdd.setText(org.openide.util.NbBundle.getMessage(
                GupGewaesserabschnittAllgemein.class,
                "GupGewaesserabschnittAllgemein.jbAdd.text")); // NOI18N
        jbAdd.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jbAddActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 15, 15);
        jpControl.add(jbAdd, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 15);
        add(jpControl, gridBagConstraints);

        glassPanel.setAlpha(0);
        glassPanel.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        add(glassPanel, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jpDeleteActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jpDeleteActionPerformed
        if (readOnly) {
            return;
        }
        final int[] selection = jlObjectList.getSelectedIndices();
        int count = 0;

        for (final int index : selection) {
            ((DocumentDropList)jlObjectList).removeObject(index - (count++));
        }
    } //GEN-LAST:event_jpDeleteActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jbDownloadActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jbDownloadActionPerformed
        ((DocumentDropList)jlObjectList).downloadSelectedDocs();
    }                                                                              //GEN-LAST:event_jbDownloadActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jbAddActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jbAddActionPerformed
        if (!readOnly) {
            final JFileChooser chooser = new JFileChooser();
            chooser.setMultiSelectionEnabled(true);
            chooser.showDialog(this, "Hinzufügen");
            final File[] files = chooser.getSelectedFiles();

            ((DocumentDropList)jlObjectList).addFiles(Arrays.asList(files));
        }
    } //GEN-LAST:event_jbAddActionPerformed

    @Override
    public CidsBean getCidsBean() {
        return this.cidsBean;
    }

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        bindingGroup.unbind();
        this.cidsBean = cidsBean;

        if (cidsBean != null) {
            ((DocumentDropList)jlObjectList).setCidsBean(cidsBean);
            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
                bindingGroup,
                cidsBean);
            bindingGroup.bind();

            refreshLabels();
        }
    }

    /**
     * DOCUMENT ME!
     */
    public void refreshLabels() {
        String gwk = "";
        String gewName = "";

        try {
            final CidsBean statLine = GupHelper.getStationLinie(cidsBean);
            if (statLine != null) {
                final CidsBean statVon = (CidsBean)statLine.getProperty("von");
                if (statVon != null) {
                    final CidsBean route = (CidsBean)statVon.getProperty("route");
                    if (route != null) {
                        gwk = route.getProperty("gwk").toString();
                        gewName = route.getProperty("routenname").toString();
                    }
                }
            }
        } catch (final Exception e) {
            LOG.error("Error while determining the water body", e);
        }

        final String gwkV = gwk;
        final String gewNameV = gewName;

        EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    txtGwk.setText(gwkV);
                }
            });
    }

    @Override
    public void dispose() {
        bindingGroup.unbind();
    }

    @Override
    public String getTitle() {
        return "Allgemeine Informationen";
    }

    @Override
    public void setTitle(final String title) {
    }

    @Override
    public void editorClosed(final EditorClosedEvent event) {
        ((DocumentDropList)jlObjectList).editorClosed(event);
    }

    @Override
    public boolean prepareForSave() {
        return ((DocumentDropList)jlObjectList).prepareForSave();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  args  DOCUMENT ME!
     */
    public static void main(final String[] args) {
        try {
            DevelopmentTools.createEditorInFrameFromRMIConnectionOnLocalhost(
                "WRRL_DB_MV",
                "Administratoren",
                "admin",
                "x",
                "gup_gewaesserabschnitt",
                1,
                1280,
                1024);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
