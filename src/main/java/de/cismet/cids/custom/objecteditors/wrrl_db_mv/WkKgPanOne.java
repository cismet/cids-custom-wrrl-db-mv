/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * WkFgPanOne.java
 *
 * Created on 04.08.2010, 13:44:05
 */
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.exception.ConnectionException;

import Sirius.server.middleware.types.MetaClass;

import org.apache.log4j.Logger;

import java.awt.Color;
import java.awt.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.server.search.PressureImpactsProposals;
import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;
import de.cismet.cids.custom.wrrl_db_mv.util.DefaultBindableScrollableComboboxCellEditor;
import de.cismet.cids.custom.wrrl_db_mv.util.ScrollableComboBox;
import de.cismet.cids.custom.wrrl_db_mv.util.UIUtil;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.DisposableCidsBeanStore;

import de.cismet.cids.editors.DefaultBindableReferenceCombo;
import de.cismet.cids.editors.DefaultCustomObjectEditor;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cismap.cids.geometryeditor.DefaultCismapGeometryComboBoxEditor;

import de.cismet.connectioncontext.AbstractConnectionContext;
import de.cismet.connectioncontext.ConnectionContext;

import de.cismet.tools.BrowserLauncher;

import de.cismet.tools.gui.StaticSwingTools;

import static javax.swing.SwingConstants.TOP;

/**
 * DOCUMENT ME!
 *
 * @author   stefan
 * @version  $Revision$, $Date$
 */
public class WkKgPanOne extends javax.swing.JPanel implements DisposableCidsBeanStore {

    //~ Static fields/initializers ---------------------------------------------

    private static final Logger LOG = Logger.getLogger(WkKgPanOne.class);
    private static final MetaClass WHY_HMWB_MC;
    private static final MetaClass IMPACT_SRC_MC;
    private static final MetaClass IMPACT_CODE_MC;
    private static final MetaClass DRIVER_MC;
    private static final MetaClass SUBSTANCE_CODE_MC;
    private static Map<Integer, Set<Integer>> pressureImpactMap = new HashMap<>();
    private static Map<WkFgPanOne.PressureImpact, Set<Integer>> pressureImpactDriverMap = new HashMap<>();
    private static final ConnectionContext CC = ConnectionContext.create(
            AbstractConnectionContext.Category.EDITOR,
            "Wk_KG");

    static {
        IMPACT_SRC_MC = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "wfd.pressure_type_code_neu");
        WHY_HMWB_MC = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "wfd.de_effection_on_code");
        IMPACT_CODE_MC = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "wfd.impact_code");
        DRIVER_MC = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "wfd.driver_code");
        SUBSTANCE_CODE_MC = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "wfd.codelist_substance_code");

        new Thread("Init proposals") {

                @Override
                public void run() {
                    initProposals();
                }
            }.start();
    }

    //~ Instance fields --------------------------------------------------------

    private final boolean readOnly = false;

    private CidsBean cidsBean;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddImpactSrc;
    private javax.swing.JButton btnAddWhyHmwb;
    private javax.swing.JButton btnMenImpactSrcAbort;
    private javax.swing.JButton btnMenImpactSrcOk;
    private javax.swing.JButton btnMenWhyHmwbAbort;
    private javax.swing.JButton btnMenWhyHmwbOk;
    private javax.swing.JButton btnRemImpactSrc;
    private javax.swing.JButton btnRemWhyHmwb;
    private javax.swing.JComboBox cbImpactSrcCataloge;
    private javax.swing.JComboBox cbWhyHmwbSrcCataloge;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo defaultBindableReferenceCombo1;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo defaultBindableReferenceCombo2;
    private javax.swing.JDialog dlgImpactSrc1Cataloge;
    private javax.swing.JDialog dlgWhyHmwbsCataloge;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel labWebsite;
    private javax.swing.JLabel lblArtificialKey;
    private javax.swing.JLabel lblArtificialValue;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblImpactSrcCataloge;
    private javax.swing.JLabel lblModifiedKey;
    private javax.swing.JLabel lblNameKey;
    private javax.swing.JLabel lblPredecKey;
    private javax.swing.JLabel lblTheGeomKey;
    private javax.swing.JLabel lblTyCdCwKey;
    private javax.swing.JLabel lblTyMvKey;
    private javax.swing.JLabel lblWebsite;
    private javax.swing.JLabel lblWhyHmwbCataloge;
    private javax.swing.JLabel lblWhyHmwbKey;
    private javax.swing.JList lstWhyHmwbs;
    private javax.swing.JPanel panContrImpactSrc;
    private javax.swing.JPanel panContrWhyHmwb;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JPanel panMenButtonsImpactSrc;
    private javax.swing.JPanel panMenButtonsWhyHmwb;
    private javax.swing.JPanel panPressure;
    private javax.swing.JPanel panSpacerBottom;
    private javax.swing.JScrollPane scpWhyHmwbs;
    private org.jdesktop.swingx.JXTable tabPressure;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtTyMv;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WkFgPanOne.
     */
    public WkKgPanOne() {
        initComponents();
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

        dlgWhyHmwbsCataloge = new JDialog(StaticSwingTools.getParentFrame(this));
        lblWhyHmwbCataloge = new javax.swing.JLabel();
        final DefaultBindableReferenceCombo cb2 = new DefaultBindableReferenceCombo(WHY_HMWB_MC, true, true);
        cbWhyHmwbSrcCataloge = cb2;
        panMenButtonsWhyHmwb = new javax.swing.JPanel();
        btnMenWhyHmwbAbort = new javax.swing.JButton();
        btnMenWhyHmwbOk = new javax.swing.JButton();
        dlgImpactSrc1Cataloge = new JDialog(StaticSwingTools.getParentFrame(this));
        lblImpactSrcCataloge = new javax.swing.JLabel();
        final DefaultBindableReferenceCombo cb3 = new ScrollableComboBox(IMPACT_SRC_MC, false, false);
        cbImpactSrcCataloge = cb3;
        panMenButtonsImpactSrc = new javax.swing.JPanel();
        btnMenImpactSrcAbort = new javax.swing.JButton();
        btnMenImpactSrcOk = new javax.swing.JButton();
        panInfo = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading = new javax.swing.JLabel();
        panInfoContent = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblNameKey = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        defaultBindableReferenceCombo1 = new de.cismet.cids.editors.DefaultBindableReferenceCombo();
        lblTyCdCwKey = new javax.swing.JLabel();
        lblModifiedKey = new javax.swing.JLabel();
        defaultBindableReferenceCombo2 = new de.cismet.cids.editors.DefaultBindableReferenceCombo();
        jPanel4 = new javax.swing.JPanel();
        scpWhyHmwbs = new javax.swing.JScrollPane();
        lstWhyHmwbs = new javax.swing.JList();
        lblWhyHmwbKey = new javax.swing.JLabel();
        panContrWhyHmwb = new javax.swing.JPanel();
        btnAddWhyHmwb = new javax.swing.JButton();
        btnRemWhyHmwb = new javax.swing.JButton();
        lblTyMvKey = new javax.swing.JLabel();
        txtTyMv = new javax.swing.JTextField();
        labWebsite = new javax.swing.JLabel();
        lblWebsite = new javax.swing.JLabel();
        panSpacerBottom = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        lblArtificialKey = new javax.swing.JLabel();
        lblPredecKey = new javax.swing.JLabel();
        jComboBox1 = new DefaultCismapGeometryComboBoxEditor();
        jPanel5 = new javax.swing.JPanel();
        lblTheGeomKey = new javax.swing.JLabel();
        lblArtificialValue = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        panPressure = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabPressure = new org.jdesktop.swingx.JXTable();
        panContrImpactSrc = new javax.swing.JPanel();
        btnAddImpactSrc = new javax.swing.JButton();
        btnRemImpactSrc = new javax.swing.JButton();

        dlgWhyHmwbsCataloge.getContentPane().setLayout(new java.awt.GridBagLayout());

        lblWhyHmwbCataloge.setText(org.openide.util.NbBundle.getMessage(
                WkKgPanOne.class,
                "WkKgPanOne.lblWhyHmwbCataloge.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgWhyHmwbsCataloge.getContentPane().add(lblWhyHmwbCataloge, gridBagConstraints);

        cbWhyHmwbSrcCataloge.setMinimumSize(new java.awt.Dimension(250, 18));
        cbWhyHmwbSrcCataloge.setPreferredSize(new java.awt.Dimension(250, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgWhyHmwbsCataloge.getContentPane().add(cbWhyHmwbSrcCataloge, gridBagConstraints);

        panMenButtonsWhyHmwb.setLayout(new java.awt.GridBagLayout());

        btnMenWhyHmwbAbort.setText(org.openide.util.NbBundle.getMessage(
                WkKgPanOne.class,
                "WkKgPanOne.btnMenWhyHmwbAbort.text")); // NOI18N
        btnMenWhyHmwbAbort.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnMenWhyHmwbAbortActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMenButtonsWhyHmwb.add(btnMenWhyHmwbAbort, gridBagConstraints);

        btnMenWhyHmwbOk.setText(org.openide.util.NbBundle.getMessage(
                WkKgPanOne.class,
                "WkKgPanOne.btnMenWhyHmwbOk.text")); // NOI18N
        btnMenWhyHmwbOk.setMaximumSize(new java.awt.Dimension(85, 23));
        btnMenWhyHmwbOk.setMinimumSize(new java.awt.Dimension(85, 23));
        btnMenWhyHmwbOk.setPreferredSize(new java.awt.Dimension(85, 23));
        btnMenWhyHmwbOk.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnMenWhyHmwbOkActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMenButtonsWhyHmwb.add(btnMenWhyHmwbOk, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgWhyHmwbsCataloge.getContentPane().add(panMenButtonsWhyHmwb, gridBagConstraints);

        dlgImpactSrc1Cataloge.getContentPane().setLayout(new java.awt.GridBagLayout());

        lblImpactSrcCataloge.setText(org.openide.util.NbBundle.getMessage(
                WkKgPanOne.class,
                "WkKgPanOne.lblImpactSrcCataloge.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgImpactSrc1Cataloge.getContentPane().add(lblImpactSrcCataloge, gridBagConstraints);

        cbImpactSrcCataloge.setMinimumSize(new java.awt.Dimension(450, 18));
        cbImpactSrcCataloge.setPreferredSize(new java.awt.Dimension(450, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgImpactSrc1Cataloge.getContentPane().add(cbImpactSrcCataloge, gridBagConstraints);

        panMenButtonsImpactSrc.setLayout(new java.awt.GridBagLayout());

        btnMenImpactSrcAbort.setText(org.openide.util.NbBundle.getMessage(
                WkKgPanOne.class,
                "WkKgPanOne.btnMenImpactSrcAbort.text")); // NOI18N
        btnMenImpactSrcAbort.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnMenImpactSrcAbortActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMenButtonsImpactSrc.add(btnMenImpactSrcAbort, gridBagConstraints);

        btnMenImpactSrcOk.setText(org.openide.util.NbBundle.getMessage(
                WkKgPanOne.class,
                "WkKgPanOne.btnMenImpactSrcOk.text")); // NOI18N
        btnMenImpactSrcOk.setMaximumSize(new java.awt.Dimension(85, 23));
        btnMenImpactSrcOk.setMinimumSize(new java.awt.Dimension(85, 23));
        btnMenImpactSrcOk.setPreferredSize(new java.awt.Dimension(85, 23));
        btnMenImpactSrcOk.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnMenImpactSrcOkActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMenButtonsImpactSrc.add(btnMenImpactSrcOk, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgImpactSrc1Cataloge.getContentPane().add(panMenButtonsImpactSrc, gridBagConstraints);

        setOpaque(false);
        setLayout(new java.awt.BorderLayout());

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setLayout(new java.awt.FlowLayout());

        lblHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading.setText(org.openide.util.NbBundle.getMessage(WkKgPanOne.class, "WkKgPanOne.lblHeading.text")); // NOI18N
        panHeadInfo.add(lblHeading);

        panInfo.add(panHeadInfo, java.awt.BorderLayout.NORTH);

        panInfoContent.setOpaque(false);
        panInfoContent.setLayout(new java.awt.GridBagLayout());

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.GridBagLayout());

        lblNameKey.setText(org.openide.util.NbBundle.getMessage(WkKgPanOne.class, "WkKgPanOne.lblNameKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(lblNameKey, gridBagConstraints);

        txtName.setMinimumSize(new java.awt.Dimension(250, 20));
        txtName.setPreferredSize(new java.awt.Dimension(250, 20));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.name}"),
                txtName,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("null");
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtName, gridBagConstraints);

        defaultBindableReferenceCombo1.setMinimumSize(new java.awt.Dimension(250, 20));
        defaultBindableReferenceCombo1.setPreferredSize(new java.awt.Dimension(250, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.ty_cd_cw}"),
                defaultBindableReferenceCombo1,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(defaultBindableReferenceCombo1, gridBagConstraints);

        lblTyCdCwKey.setText(org.openide.util.NbBundle.getMessage(WkKgPanOne.class, "WkKgPanOne.lblTyCdCwKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(lblTyCdCwKey, gridBagConstraints);

        lblModifiedKey.setText(org.openide.util.NbBundle.getMessage(
                WkKgPanOne.class,
                "WkKgPanOne.lblModifiedKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(lblModifiedKey, gridBagConstraints);

        defaultBindableReferenceCombo2.setMinimumSize(new java.awt.Dimension(250, 20));
        defaultBindableReferenceCombo2.setPreferredSize(new java.awt.Dimension(250, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.modified}"),
                defaultBindableReferenceCombo2,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(defaultBindableReferenceCombo2, gridBagConstraints);

        jPanel4.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        jPanel2.add(jPanel4, gridBagConstraints);

        scpWhyHmwbs.setMinimumSize(new java.awt.Dimension(250, 80));
        scpWhyHmwbs.setPreferredSize(new java.awt.Dimension(250, 80));

        lstWhyHmwbs.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        final org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create(
                "${cidsBean.why_hmwbs}");
        final org.jdesktop.swingbinding.JListBinding jListBinding = org.jdesktop.swingbinding.SwingBindings
                    .createJListBinding(
                        org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                        this,
                        eLProperty,
                        lstWhyHmwbs);
        bindingGroup.addBinding(jListBinding);

        scpWhyHmwbs.setViewportView(lstWhyHmwbs);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(scpWhyHmwbs, gridBagConstraints);

        lblWhyHmwbKey.setText(org.openide.util.NbBundle.getMessage(WkKgPanOne.class, "WkKgPanOne.lblWhyHmwbKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(lblWhyHmwbKey, gridBagConstraints);

        panContrWhyHmwb.setOpaque(false);
        panContrWhyHmwb.setLayout(new java.awt.GridBagLayout());

        btnAddWhyHmwb.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_add_mini.png"))); // NOI18N
        btnAddWhyHmwb.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnAddWhyHmwbActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panContrWhyHmwb.add(btnAddWhyHmwb, gridBagConstraints);

        btnRemWhyHmwb.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_remove_mini.png"))); // NOI18N
        btnRemWhyHmwb.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnRemWhyHmwbActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panContrWhyHmwb.add(btnRemWhyHmwb, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        jPanel2.add(panContrWhyHmwb, gridBagConstraints);

        lblTyMvKey.setText(org.openide.util.NbBundle.getMessage(WkKgPanOne.class, "WkKgPanOne.lblTyMvKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(lblTyMvKey, gridBagConstraints);

        txtTyMv.setMinimumSize(new java.awt.Dimension(250, 20));
        txtTyMv.setPreferredSize(new java.awt.Dimension(250, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.ty_mv}"),
                txtTyMv,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtTyMv, gridBagConstraints);

        labWebsite.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        labWebsite.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(final java.awt.event.MouseEvent evt) {
                    labWebsiteMouseClicked(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        jPanel2.add(labWebsite, gridBagConstraints);

        lblWebsite.setText(org.openide.util.NbBundle.getMessage(
                WkKgPanOne.class,
                "WkKgPanOne.lblWebsite.text",
                new Object[] {})); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        jPanel2.add(lblWebsite, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(jPanel2, gridBagConstraints);

        panSpacerBottom.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        panInfoContent.add(panSpacerBottom, gridBagConstraints);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(25, 5, 25, 5);
        panInfoContent.add(jSeparator1, gridBagConstraints);

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        lblArtificialKey.setText(org.openide.util.NbBundle.getMessage(
                WkKgPanOne.class,
                "WkKgPanOne.lblArtificialKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblArtificialKey, gridBagConstraints);

        lblPredecKey.setText(org.openide.util.NbBundle.getMessage(WkKgPanOne.class, "WkKgPanOne.lblPredecKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblPredecKey, gridBagConstraints);

        jComboBox1.setMinimumSize(new java.awt.Dimension(300, 20));
        jComboBox1.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.the_geom}"),
                jComboBox1,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setConverter(((DefaultCismapGeometryComboBoxEditor)jComboBox1).getConverter());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jComboBox1, gridBagConstraints);

        jPanel5.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jPanel5, gridBagConstraints);

        lblTheGeomKey.setText(org.openide.util.NbBundle.getMessage(WkKgPanOne.class, "WkKgPanOne.lblTheGeomKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblTheGeomKey, gridBagConstraints);

        lblArtificialValue.setMinimumSize(new java.awt.Dimension(300, 20));
        lblArtificialValue.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.artificial.name}"),
                lblArtificialValue,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("null");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(lblArtificialValue, gridBagConstraints);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.wb_predecs}"),
                jTextArea1,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jScrollPane1.setViewportView(jTextArea1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jScrollPane1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(jPanel1, gridBagConstraints);

        panPressure.setBorder(javax.swing.BorderFactory.createTitledBorder(
                org.openide.util.NbBundle.getMessage(
                    WkKgPanOne.class,
                    "WkKgPanOne.panPressure.border.title",
                    new Object[] {}))); // NOI18N
        panPressure.setMinimumSize(new java.awt.Dimension(110, 300));
        panPressure.setOpaque(false);
        panPressure.setPreferredSize(new java.awt.Dimension(110, 300));
        panPressure.setLayout(new java.awt.GridBagLayout());

        jScrollPane2.setViewportView(tabPressure);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        panPressure.add(jScrollPane2, gridBagConstraints);

        panContrImpactSrc.setOpaque(false);
        panContrImpactSrc.setLayout(new java.awt.GridBagLayout());

        btnAddImpactSrc.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_add_mini.png"))); // NOI18N
        btnAddImpactSrc.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnAddImpactSrcActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panContrImpactSrc.add(btnAddImpactSrc, gridBagConstraints);

        btnRemImpactSrc.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_remove_mini.png"))); // NOI18N
        btnRemImpactSrc.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnRemImpactSrcActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panContrImpactSrc.add(btnRemImpactSrc, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        panPressure.add(panContrImpactSrc, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(panPressure, gridBagConstraints);

        panInfo.add(panInfoContent, java.awt.BorderLayout.CENTER);

        add(panInfo, java.awt.BorderLayout.CENTER);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnAddWhyHmwbActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnAddWhyHmwbActionPerformed
        dlgWhyHmwbsCataloge.pack();
        StaticSwingTools.showDialog(StaticSwingTools.getParentFrame(this), dlgWhyHmwbsCataloge, true);
    }                                                                                 //GEN-LAST:event_btnAddWhyHmwbActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnRemWhyHmwbActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnRemWhyHmwbActionPerformed
        final Object selection = lstWhyHmwbs.getSelectedValue();
        if (selection != null) {
            final int answer = JOptionPane.showConfirmDialog(
                    StaticSwingTools.getParentFrame(this),
                    "Soll der Eintrag wirklich gelöscht werden?",
                    "Eintrag entfernen",
                    JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                try {
                    final CidsBean beanToDelete = (CidsBean)selection;
                    final Object beanColl = cidsBean.getProperty("why_hmwbs");
                    if (beanColl instanceof Collection) {
                        ((Collection)beanColl).remove(beanToDelete);
                    }
                } catch (Exception e) {
                    UIUtil.showExceptionToUser(e, this);
                }
            }
        }
    }                                                                                 //GEN-LAST:event_btnRemWhyHmwbActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnMenWhyHmwbAbortActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnMenWhyHmwbAbortActionPerformed
        dlgWhyHmwbsCataloge.setVisible(false);
    }                                                                                      //GEN-LAST:event_btnMenWhyHmwbAbortActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnMenWhyHmwbOkActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnMenWhyHmwbOkActionPerformed
        final Object selection = cbWhyHmwbSrcCataloge.getSelectedItem();
        if (selection instanceof CidsBean) {
            final CidsBean selectedBean = (CidsBean)selection;
            final Collection<CidsBean> colToAdd = CidsBeanSupport.getBeanCollectionFromProperty(cidsBean, "why_hmwbs");
            if (colToAdd != null) {
                if (!colToAdd.contains(selectedBean)) {
                    colToAdd.add(selectedBean);
                }
            }
        }
        dlgWhyHmwbsCataloge.setVisible(false);
    }                                                                                   //GEN-LAST:event_btnMenWhyHmwbOkActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void labWebsiteMouseClicked(final java.awt.event.MouseEvent evt) { //GEN-FIRST:event_labWebsiteMouseClicked
        try {
            BrowserLauncher.openURL(
                "https://fis-wasser-mv.de/charts/steckbriefe/cw/cw_wk.php?kg="
                        + String.valueOf(cidsBean.getProperty("wk_k")));
        } catch (Exception ex) {
            LOG.warn(ex, ex);
        }
    }                                                                          //GEN-LAST:event_labWebsiteMouseClicked

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnAddImpactSrcActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnAddImpactSrcActionPerformed
        dlgImpactSrc1Cataloge.setSize(500, 150);
        StaticSwingTools.showDialog(StaticSwingTools.getParentFrame(this), dlgImpactSrc1Cataloge, true);
    }                                                                                   //GEN-LAST:event_btnAddImpactSrcActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnRemImpactSrcActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnRemImpactSrcActionPerformed
        final int selectedRow = tabPressure.getSelectedRow();
        final CidsBean selection = ((WkFgPanOne.PressureTableModel)tabPressure.getModel()).getCidsBean(selectedRow);
        if (selection != null) {
            final int answer = JOptionPane.showConfirmDialog(
                    StaticSwingTools.getParentFrame(this),
                    "Soll die Beslastungsquelle '"
                            + selection.toString()
                            + "' wirklich gelöscht werden?",
                    "Belastungsquelle entfernen",
                    JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                try {
                    final Object beanColl = cidsBean.getProperty("pressure_impact_driver");
                    if (beanColl instanceof Collection) {
                        if (selection != null) {
                            ((Collection)beanColl).remove(selection);
                            setModel();
                        }
                    }
                } catch (Exception e) {
                    UIUtil.showExceptionToUser(e, this);
                }
            }
        }
    }                                                                                   //GEN-LAST:event_btnRemImpactSrcActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnMenImpactSrcAbortActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnMenImpactSrcAbortActionPerformed
        dlgImpactSrc1Cataloge.setVisible(false);
    }                                                                                        //GEN-LAST:event_btnMenImpactSrcAbortActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnMenImpactSrcOkActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnMenImpactSrcOkActionPerformed
        final Object selection = cbImpactSrcCataloge.getSelectedItem();

        try {
            if (selection instanceof CidsBean) {
                final CidsBean selectedBean = (CidsBean)selection;
                final CidsBean newBean = CidsBeanSupport.createNewCidsBeanFromTableName("wk_fg_pressure_impact_driver");
                newBean.setProperty("pressure", selectedBean);
                final Collection<CidsBean> colToAdd = CidsBeanSupport.getBeanCollectionFromProperty(
                        cidsBean,
                        "pressure_impact_driver");
                if (colToAdd != null) {
                    colToAdd.add(newBean);
                    setModel();
                }
            }
            dlgImpactSrc1Cataloge.setVisible(false);
        } catch (Exception e) {
            LOG.error("Cannot create new cids bean", e);
        }
    } //GEN-LAST:event_btnMenImpactSrcOkActionPerformed

    /**
     * DOCUMENT ME!
     */
    private static void initProposals() {
        try {
            final PressureImpactsProposals search = new PressureImpactsProposals();
            search.initWithConnectionContext(CC);
            final Collection res = SessionManager.getProxy()
                        .customServerSearch(SessionManager.getSession().getUser(), search, CC);

            final ArrayList<ArrayList<Integer>> list = (ArrayList<ArrayList<Integer>>)res;

            if (list != null) {
                for (final ArrayList<Integer> row : list) {
                    final Integer pressure = row.get(0);
                    final Integer impact = row.get(1);
                    final Integer driver = row.get(2);
                    if ((pressure == null) || (impact == null)) {
                        continue;
                    }
                    final WkFgPanOne.PressureImpact pressureImpact = new WkFgPanOne.PressureImpact(pressure, impact);
                    Set<Integer> impactDriverList = pressureImpactDriverMap.get(pressureImpact);
                    Set<Integer> impactList = pressureImpactMap.get(pressure);

                    if (impactList == null) {
                        impactList = new HashSet<>();
                        pressureImpactMap.put(pressure, impactList);
                    }

                    if (impactDriverList == null) {
                        impactDriverList = new HashSet<>();
                        pressureImpactDriverMap.put(pressureImpact, impactList);
                    }

                    impactList.add(impact);
                    impactDriverList.add(driver);
                }
            }
        } catch (ConnectionException e) {
            LOG.error("Cannot retrieve Pressure Impacts proposal", e);
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void setModel() {
        if (cidsBean != null) {
            final List<CidsBean> pressures = CidsBeanSupport.getBeanCollectionFromProperty(
                    cidsBean,
                    "pressure_impact_driver");
            tabPressure.setModel(new WkFgPanOne.PressureTableModel(readOnly, pressures));
        } else {
            tabPressure.setModel(new WkFgPanOne.PressureTableModel(readOnly));
        }

        final DefaultBindableScrollableComboboxCellEditor cbImpact = new DefaultBindableScrollableComboboxCellEditor(
                IMPACT_CODE_MC,
                true);
        final DefaultBindableScrollableComboboxCellEditor cbdriver = new DefaultBindableScrollableComboboxCellEditor(
                DRIVER_MC,
                true);
        final DefaultBindableScrollableComboboxCellEditor cbSubstance = new DefaultBindableScrollableComboboxCellEditor(
                SUBSTANCE_CODE_MC,
                true,
                new Comparator<CidsBean>() {

                    @Override
                    public int compare(final CidsBean o1, final CidsBean o2) {
                        final Integer value1 = (Integer)o1.getProperty("value");
                        final Integer value2 = (Integer)o2.getProperty("value");
                        final String name1 = (String)o1.getProperty("name");
                        final String name2 = (String)o2.getProperty("name");

                        if ((value1 == null) && (value2 != null)) {
                            return 1;
                        } else if ((value1 != null) && (value2 == null)) {
                            return -1;
                        } else {
                            return name1.compareTo(name2);
                        }
                    }
                });

        cbImpact.getComboBox().setRenderer(new DefaultListCellRenderer() {

                @Override
                public Component getListCellRendererComponent(final JList<?> list,
                        final Object value,
                        final int index,
                        final boolean isSelected,
                        final boolean cellHasFocus) {
                    final Component result = super.getListCellRendererComponent(
                            list,
                            value,
                            index,
                            isSelected,
                            cellHasFocus);

                    if (value instanceof DefaultBindableReferenceCombo.NullableItem) {
                        ((JLabel)result).setText(" ");
                    }

                    if ((result instanceof JLabel) && (value instanceof CidsBean)) {
                        final CidsBean bean = (CidsBean)value;

                        if (!isImpactProposed(bean)) {
                            ((JLabel)result).setForeground(Color.GRAY);
                        }
                    }

                    return result;
                }
            });

        cbdriver.getComboBox().setRenderer(new DefaultListCellRenderer() {

                @Override
                public Component getListCellRendererComponent(final JList<?> list,
                        final Object value,
                        final int index,
                        final boolean isSelected,
                        final boolean cellHasFocus) {
                    final Component result = super.getListCellRendererComponent(
                            list,
                            value,
                            index,
                            isSelected,
                            cellHasFocus);

                    if (value instanceof DefaultBindableReferenceCombo.NullableItem) {
                        ((JLabel)result).setText(" ");
                    }

                    if ((result instanceof JLabel) && (value instanceof CidsBean)) {
                        final CidsBean bean = (CidsBean)value;

                        if (!isDriverProposed(bean)) {
                            ((JLabel)result).setForeground(Color.GRAY);
                        }
                    }

                    return result;
                }
            });

        tabPressure.getColumn(2).setCellEditor(cbImpact);
        tabPressure.getColumn(3).setCellEditor(cbdriver);
        tabPressure.getColumn(4).setCellEditor(cbSubstance);
        tabPressure.setDefaultRenderer(String.class, new DefaultTableCellRenderer() {

                @Override
                public Component getTableCellRendererComponent(final JTable table,
                        final Object value,
                        final boolean isSelected,
                        final boolean hasFocus,
                        final int row,
                        final int column) {
                    setVerticalAlignment(TOP);
                    final Component c = super.getTableCellRendererComponent(
                            table,
                            value,
                            isSelected,
                            hasFocus,
                            row,
                            column);
                    if (c instanceof JLabel) {
                        ((JLabel)c).setText("<html>" + ((JLabel)c).getText() + "</html>");
                        ((JLabel)c).setToolTipText(String.valueOf(value));
                    }
                    return c;
                }
            });
        tabPressure.setDefaultRenderer(CidsBean.class, new DefaultTableCellRenderer() {

                @Override
                public Component getTableCellRendererComponent(final JTable table,
                        final Object value,
                        final boolean isSelected,
                        final boolean hasFocus,
                        final int row,
                        final int column) {
                    setVerticalAlignment(TOP);
                    final Component c = super.getTableCellRendererComponent(
                            table,
                            value,
                            isSelected,
                            hasFocus,
                            row,
                            column);
                    if (c instanceof JLabel) {
                        ((JLabel)c).setText("<html>" + ((JLabel)c).getText() + "</html>");
                        ((JLabel)c).setToolTipText(String.valueOf(value));
                    }
                    return c;
                }
            });

        tabPressure.setRowHeight(55);
    }

    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
    }

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        bindingGroup.unbind();
        if (cidsBean != null) {
            this.cidsBean = cidsBean;
            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
                bindingGroup,
                this.cidsBean);
            bindingGroup.bind();
            labWebsite.setText("<html><a href=\"https://fis-wasser-mv.de/charts/steckbriefe/cw/cw_wk.php?kg"
                        + String.valueOf(cidsBean.getProperty("wk_k")) + "\">Webseite</a></html>");
        } else {
            labWebsite.setText("");
        }

        setModel();
    }

    @Override
    public void dispose() {
        ((DefaultCismapGeometryComboBoxEditor)jComboBox1).dispose();
        bindingGroup.unbind();
    }

    /**
     * DOCUMENT ME!
     *
     * @param   impact  pressure DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean isImpactProposed(final CidsBean impact) {
        final int selectedRow = tabPressure.getSelectedRow();
        final CidsBean bean = ((WkFgPanOne.PressureTableModel)tabPressure.getModel()).getCidsBean(selectedRow);

        if (bean != null) {
            final Integer pressureId = (Integer)bean.getProperty("pressure.id");
            final Integer impactId = (Integer)impact.getProperty("id");

            if ((pressureId == null) || pressureImpactMap.isEmpty() || (impactId == null)) {
                return true;
            }

            final Set<Integer> proposedImpacts = pressureImpactMap.get(pressureId);

            if (proposedImpacts != null) {
                return proposedImpacts.contains(impactId);
            }
        } else {
            return true;
        }
        return false;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   driver  pressure DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean isDriverProposed(final CidsBean driver) {
        final int selectedRow = tabPressure.getSelectedRow();
        final CidsBean bean = ((WkFgPanOne.PressureTableModel)tabPressure.getModel()).getCidsBean(selectedRow);

        if (bean != null) {
            final Integer pressureId = (Integer)bean.getProperty("pressure.id");
            final Integer impactId = (Integer)bean.getProperty("impact.id");
            final Integer driverId = (Integer)driver.getProperty("id");

            if ((pressureId == null) || pressureImpactDriverMap.isEmpty() || (impactId == null) || (driverId == null)) {
                return true;
            }

            final WkFgPanOne.PressureImpact pressureImpact = new WkFgPanOne.PressureImpact(pressureId, impactId);

            final Set<Integer> proposedDriver = pressureImpactDriverMap.get(pressureImpact);

            if (proposedDriver != null) {
                return proposedDriver.contains(driverId);
            }
        } else {
            return true;
        }
        return false;
    }
}
