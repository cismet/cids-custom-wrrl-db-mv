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

import Sirius.server.middleware.types.MetaClass;

import java.util.Collection;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;
import de.cismet.cids.custom.wrrl_db_mv.util.UIUtil;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.DisposableCidsBeanStore;

import de.cismet.cids.editors.DefaultBindableReferenceCombo;
import de.cismet.cids.editors.DefaultCustomObjectEditor;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.tools.gui.StaticSwingTools;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public class WkGwPanThree extends javax.swing.JPanel implements DisposableCidsBeanStore {

    //~ Static fields/initializers ---------------------------------------------

    private static final MetaClass POOR_CHEM_MC;
    private static final MetaClass TREND_TYPE_MC;

    static {
        POOR_CHEM_MC = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "wfd.exemption_source_code");
        TREND_TYPE_MC = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "wfd.Pollutant_trend_type_code");
    }

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddPoorChem;
    private javax.swing.JButton btnAddTrendType;
    private javax.swing.JButton btnMenTrendTypeAbort;
    private javax.swing.JButton btnMenTrendTypeOk;
    private javax.swing.JButton btnPoorChemAbort;
    private javax.swing.JButton btnPoorChemOk;
    private javax.swing.JButton btnRemPoorChem;
    private javax.swing.JButton btnRemTrendType;
    private javax.swing.JComboBox cbPoorChemCataloge;
    private javax.swing.JComboBox cbTrendTypeCataloge;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo defaultBindableReferenceCombo10;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo defaultBindableReferenceCombo11;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo defaultBindableReferenceCombo12;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo defaultBindableReferenceCombo14;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo defaultBindableReferenceCombo6;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo defaultBindableReferenceCombo7;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo defaultBindableReferenceCombo8;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo defaultBindableReferenceCombo9;
    private javax.swing.JDialog dlgPoorChemCataloge;
    private javax.swing.JDialog dlgTrendTypeCataloge;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblActsubpestKey;
    private javax.swing.JLabel lblAnnexIiKey;
    private javax.swing.JLabel lblChemStatKey;
    private javax.swing.JLabel lblChemStatValue;
    private javax.swing.JLabel lblConfLevelKey;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblNitratKey;
    private javax.swing.JLabel lblOthplKey;
    private javax.swing.JLabel lblPesticidesKey;
    private javax.swing.JLabel lblPollTrendKey;
    private javax.swing.JLabel lblPoorChemCataloge;
    private javax.swing.JLabel lblPoorChemsKey;
    private javax.swing.JLabel lblReasChemKey;
    private javax.swing.JLabel lblTrendTypeCataloge;
    private javax.swing.JLabel lblTrendTypesKey;
    private javax.swing.JList lstPoorChems;
    private javax.swing.JList lstTrendTypes;
    private javax.swing.JPanel panContrPoorChems;
    private javax.swing.JPanel panContrTrendTypes;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JPanel panMenButtonsPoorChem;
    private javax.swing.JPanel panMenButtonsTrendType;
    private javax.swing.JPanel panSpacingBottom;
    private javax.swing.JPanel panSpacingBottom1;
    private javax.swing.JScrollPane scpPoorChems;
    private javax.swing.JScrollPane scpTrendTypes;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WkFgPanOne.
     */
    public WkGwPanThree() {
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

        dlgPoorChemCataloge = new JDialog(StaticSwingTools.getParentFrame(this));
        lblPoorChemCataloge = new javax.swing.JLabel();
        final DefaultBindableReferenceCombo cb1 = new DefaultBindableReferenceCombo(POOR_CHEM_MC, true, true);
        cbPoorChemCataloge = cb1;
        panMenButtonsPoorChem = new javax.swing.JPanel();
        btnPoorChemAbort = new javax.swing.JButton();
        btnPoorChemOk = new javax.swing.JButton();
        dlgTrendTypeCataloge = new JDialog(StaticSwingTools.getParentFrame(this));
        lblTrendTypeCataloge = new javax.swing.JLabel();
        final DefaultBindableReferenceCombo cb2 = new DefaultBindableReferenceCombo(TREND_TYPE_MC, true, true);
        cbTrendTypeCataloge = cb2;
        panMenButtonsTrendType = new javax.swing.JPanel();
        btnMenTrendTypeAbort = new javax.swing.JButton();
        btnMenTrendTypeOk = new javax.swing.JButton();
        panInfo = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading = new javax.swing.JLabel();
        panInfoContent = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lblChemStatKey = new javax.swing.JLabel();
        lblReasChemKey = new javax.swing.JLabel();
        defaultBindableReferenceCombo6 = new de.cismet.cids.editors.DefaultBindableReferenceCombo();
        lblPoorChemsKey = new javax.swing.JLabel();
        lblNitratKey = new javax.swing.JLabel();
        defaultBindableReferenceCombo7 = new de.cismet.cids.editors.DefaultBindableReferenceCombo();
        lblPesticidesKey = new javax.swing.JLabel();
        defaultBindableReferenceCombo8 = new de.cismet.cids.editors.DefaultBindableReferenceCombo();
        lblActsubpestKey = new javax.swing.JLabel();
        defaultBindableReferenceCombo9 = new de.cismet.cids.editors.DefaultBindableReferenceCombo();
        lblAnnexIiKey = new javax.swing.JLabel();
        defaultBindableReferenceCombo10 = new de.cismet.cids.editors.DefaultBindableReferenceCombo();
        lblOthplKey = new javax.swing.JLabel();
        defaultBindableReferenceCombo11 = new de.cismet.cids.editors.DefaultBindableReferenceCombo();
        lblPollTrendKey = new javax.swing.JLabel();
        defaultBindableReferenceCombo12 = new de.cismet.cids.editors.DefaultBindableReferenceCombo();
        lblConfLevelKey = new javax.swing.JLabel();
        defaultBindableReferenceCombo14 = new de.cismet.cids.editors.DefaultBindableReferenceCombo();
        lblTrendTypesKey = new javax.swing.JLabel();
        panSpacingBottom = new javax.swing.JPanel();
        scpPoorChems = new javax.swing.JScrollPane();
        lstPoorChems = new javax.swing.JList();
        panContrPoorChems = new javax.swing.JPanel();
        btnAddPoorChem = new javax.swing.JButton();
        btnRemPoorChem = new javax.swing.JButton();
        scpTrendTypes = new javax.swing.JScrollPane();
        lstTrendTypes = new javax.swing.JList();
        panContrTrendTypes = new javax.swing.JPanel();
        btnAddTrendType = new javax.swing.JButton();
        btnRemTrendType = new javax.swing.JButton();
        lblChemStatValue = new javax.swing.JLabel();
        panSpacingBottom1 = new javax.swing.JPanel();

        dlgPoorChemCataloge.getContentPane().setLayout(new java.awt.GridBagLayout());

        lblPoorChemCataloge.setText(org.openide.util.NbBundle.getMessage(
                WkGwPanThree.class,
                "WkGwPanThree.lblPoorChemCataloge.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgPoorChemCataloge.getContentPane().add(lblPoorChemCataloge, gridBagConstraints);

        cbPoorChemCataloge.setMinimumSize(new java.awt.Dimension(250, 18));
        cbPoorChemCataloge.setPreferredSize(new java.awt.Dimension(250, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgPoorChemCataloge.getContentPane().add(cbPoorChemCataloge, gridBagConstraints);

        panMenButtonsPoorChem.setLayout(new java.awt.GridBagLayout());

        btnPoorChemAbort.setText(org.openide.util.NbBundle.getMessage(
                WkGwPanThree.class,
                "WkGwPanThree.btnPoorChemAbort.text")); // NOI18N
        btnPoorChemAbort.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnPoorChemAbortActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMenButtonsPoorChem.add(btnPoorChemAbort, gridBagConstraints);

        btnPoorChemOk.setText(org.openide.util.NbBundle.getMessage(
                WkGwPanThree.class,
                "WkGwPanThree.btnPoorChemOk.text")); // NOI18N
        btnPoorChemOk.setMaximumSize(new java.awt.Dimension(85, 23));
        btnPoorChemOk.setMinimumSize(new java.awt.Dimension(85, 23));
        btnPoorChemOk.setPreferredSize(new java.awt.Dimension(85, 23));
        btnPoorChemOk.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnPoorChemOkActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMenButtonsPoorChem.add(btnPoorChemOk, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgPoorChemCataloge.getContentPane().add(panMenButtonsPoorChem, gridBagConstraints);

        dlgTrendTypeCataloge.getContentPane().setLayout(new java.awt.GridBagLayout());

        lblTrendTypeCataloge.setText(org.openide.util.NbBundle.getMessage(
                WkGwPanThree.class,
                "WkGwPanThree.lblTrendTypeCataloge.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgTrendTypeCataloge.getContentPane().add(lblTrendTypeCataloge, gridBagConstraints);

        cbTrendTypeCataloge.setMinimumSize(new java.awt.Dimension(250, 18));
        cbTrendTypeCataloge.setPreferredSize(new java.awt.Dimension(250, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgTrendTypeCataloge.getContentPane().add(cbTrendTypeCataloge, gridBagConstraints);

        panMenButtonsTrendType.setLayout(new java.awt.GridBagLayout());

        btnMenTrendTypeAbort.setText(org.openide.util.NbBundle.getMessage(
                WkGwPanThree.class,
                "WkGwPanThree.btnMenTrendTypeAbort.text")); // NOI18N
        btnMenTrendTypeAbort.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnMenTrendTypeAbortActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMenButtonsTrendType.add(btnMenTrendTypeAbort, gridBagConstraints);

        btnMenTrendTypeOk.setText(org.openide.util.NbBundle.getMessage(
                WkGwPanThree.class,
                "WkGwPanThree.btnMenTrendTypeOk.text")); // NOI18N
        btnMenTrendTypeOk.setMaximumSize(new java.awt.Dimension(85, 23));
        btnMenTrendTypeOk.setMinimumSize(new java.awt.Dimension(85, 23));
        btnMenTrendTypeOk.setPreferredSize(new java.awt.Dimension(85, 23));
        btnMenTrendTypeOk.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnMenTrendTypeOkActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMenButtonsTrendType.add(btnMenTrendTypeOk, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgTrendTypeCataloge.getContentPane().add(panMenButtonsTrendType, gridBagConstraints);

        setOpaque(false);
        setLayout(new java.awt.BorderLayout());

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setLayout(new java.awt.FlowLayout());

        lblHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading.setText(org.openide.util.NbBundle.getMessage(WkGwPanThree.class, "WkGwPanThree.lblHeading.text")); // NOI18N
        panHeadInfo.add(lblHeading);

        panInfo.add(panHeadInfo, java.awt.BorderLayout.NORTH);

        panInfoContent.setOpaque(false);
        panInfoContent.setLayout(new java.awt.GridBagLayout());

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        lblChemStatKey.setText(org.openide.util.NbBundle.getMessage(
                WkGwPanThree.class,
                "WkGwPanThree.lblChemStatKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblChemStatKey, gridBagConstraints);

        lblReasChemKey.setText(org.openide.util.NbBundle.getMessage(
                WkGwPanThree.class,
                "WkGwPanThree.lblReasChemKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblReasChemKey, gridBagConstraints);

        defaultBindableReferenceCombo6.setMinimumSize(new java.awt.Dimension(300, 20));
        defaultBindableReferenceCombo6.setPreferredSize(new java.awt.Dimension(300, 20));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.reas_chem}"),
                defaultBindableReferenceCombo6,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(defaultBindableReferenceCombo6, gridBagConstraints);

        lblPoorChemsKey.setText(org.openide.util.NbBundle.getMessage(
                WkGwPanThree.class,
                "WkGwPanThree.lblPoorChemsKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 5, 5);
        jPanel1.add(lblPoorChemsKey, gridBagConstraints);

        lblNitratKey.setText(org.openide.util.NbBundle.getMessage(
                WkGwPanThree.class,
                "WkGwPanThree.lblNitratKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblNitratKey, gridBagConstraints);

        defaultBindableReferenceCombo7.setMinimumSize(new java.awt.Dimension(300, 20));
        defaultBindableReferenceCombo7.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.nitrat}"),
                defaultBindableReferenceCombo7,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(defaultBindableReferenceCombo7, gridBagConstraints);

        lblPesticidesKey.setText(org.openide.util.NbBundle.getMessage(
                WkGwPanThree.class,
                "WkGwPanThree.lblPesticidesKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblPesticidesKey, gridBagConstraints);

        defaultBindableReferenceCombo8.setMinimumSize(new java.awt.Dimension(300, 20));
        defaultBindableReferenceCombo8.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.pesticides}"),
                defaultBindableReferenceCombo8,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(defaultBindableReferenceCombo8, gridBagConstraints);

        lblActsubpestKey.setText(org.openide.util.NbBundle.getMessage(
                WkGwPanThree.class,
                "WkGwPanThree.lblActsubpestKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblActsubpestKey, gridBagConstraints);

        defaultBindableReferenceCombo9.setMinimumSize(new java.awt.Dimension(300, 20));
        defaultBindableReferenceCombo9.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.actsubpest}"),
                defaultBindableReferenceCombo9,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(defaultBindableReferenceCombo9, gridBagConstraints);

        lblAnnexIiKey.setText(org.openide.util.NbBundle.getMessage(
                WkGwPanThree.class,
                "WkGwPanThree.lblAnnexIiKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblAnnexIiKey, gridBagConstraints);

        defaultBindableReferenceCombo10.setMinimumSize(new java.awt.Dimension(300, 20));
        defaultBindableReferenceCombo10.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.annex_ii}"),
                defaultBindableReferenceCombo10,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(defaultBindableReferenceCombo10, gridBagConstraints);

        lblOthplKey.setText(org.openide.util.NbBundle.getMessage(WkGwPanThree.class, "WkGwPanThree.lblOthplKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblOthplKey, gridBagConstraints);

        defaultBindableReferenceCombo11.setMinimumSize(new java.awt.Dimension(300, 20));
        defaultBindableReferenceCombo11.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.othpl}"),
                defaultBindableReferenceCombo11,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(defaultBindableReferenceCombo11, gridBagConstraints);

        lblPollTrendKey.setText(org.openide.util.NbBundle.getMessage(
                WkGwPanThree.class,
                "WkGwPanThree.lblPollTrendKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblPollTrendKey, gridBagConstraints);

        defaultBindableReferenceCombo12.setMinimumSize(new java.awt.Dimension(300, 20));
        defaultBindableReferenceCombo12.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.poll_trend}"),
                defaultBindableReferenceCombo12,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(defaultBindableReferenceCombo12, gridBagConstraints);

        lblConfLevelKey.setText(org.openide.util.NbBundle.getMessage(
                WkGwPanThree.class,
                "WkGwPanThree.lblConfLevelKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblConfLevelKey, gridBagConstraints);

        defaultBindableReferenceCombo14.setMinimumSize(new java.awt.Dimension(300, 20));
        defaultBindableReferenceCombo14.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.conf_level}"),
                defaultBindableReferenceCombo14,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(defaultBindableReferenceCombo14, gridBagConstraints);

        lblTrendTypesKey.setText(org.openide.util.NbBundle.getMessage(
                WkGwPanThree.class,
                "WkGwPanThree.lblTrendTypesKey.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblTrendTypesKey, gridBagConstraints);

        panSpacingBottom.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(panSpacingBottom, gridBagConstraints);

        scpPoorChems.setMinimumSize(new java.awt.Dimension(300, 80));
        scpPoorChems.setPreferredSize(new java.awt.Dimension(300, 80));

        lstPoorChems.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create(
                "${cidsBean.poor_chems}");
        org.jdesktop.swingbinding.JListBinding jListBinding = org.jdesktop.swingbinding.SwingBindings
                    .createJListBinding(
                        org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                        this,
                        eLProperty,
                        lstPoorChems);
        bindingGroup.addBinding(jListBinding);

        scpPoorChems.setViewportView(lstPoorChems);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(scpPoorChems, gridBagConstraints);

        panContrPoorChems.setOpaque(false);
        panContrPoorChems.setLayout(new java.awt.GridBagLayout());

        btnAddPoorChem.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_add_mini.png"))); // NOI18N
        btnAddPoorChem.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnAddPoorChemActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panContrPoorChems.add(btnAddPoorChem, gridBagConstraints);

        btnRemPoorChem.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_remove_mini.png"))); // NOI18N
        btnRemPoorChem.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnRemPoorChemActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panContrPoorChems.add(btnRemPoorChem, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        jPanel1.add(panContrPoorChems, gridBagConstraints);

        scpTrendTypes.setMinimumSize(new java.awt.Dimension(300, 80));
        scpTrendTypes.setPreferredSize(new java.awt.Dimension(300, 80));
        scpTrendTypes.setRequestFocusEnabled(false);

        lstTrendTypes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${cidsBean.trend_types}");
        jListBinding = org.jdesktop.swingbinding.SwingBindings.createJListBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                eLProperty,
                lstTrendTypes);
        bindingGroup.addBinding(jListBinding);

        scpTrendTypes.setViewportView(lstTrendTypes);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(scpTrendTypes, gridBagConstraints);

        panContrTrendTypes.setOpaque(false);
        panContrTrendTypes.setLayout(new java.awt.GridBagLayout());

        btnAddTrendType.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_add_mini.png"))); // NOI18N
        btnAddTrendType.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnAddTrendTypeActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panContrTrendTypes.add(btnAddTrendType, gridBagConstraints);

        btnRemTrendType.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_remove_mini.png"))); // NOI18N
        btnRemTrendType.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnRemTrendTypeActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panContrTrendTypes.add(btnRemTrendType, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        jPanel1.add(panContrTrendTypes, gridBagConstraints);

        lblChemStatValue.setMinimumSize(new java.awt.Dimension(250, 20));
        lblChemStatValue.setPreferredSize(new java.awt.Dimension(250, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.chem_stat.name}"),
                lblChemStatValue,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue("");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblChemStatValue, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(jPanel1, gridBagConstraints);

        panSpacingBottom1.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        panInfoContent.add(panSpacingBottom1, gridBagConstraints);

        panInfo.add(panInfoContent, java.awt.BorderLayout.CENTER);

        add(panInfo, java.awt.BorderLayout.CENTER);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnPoorChemAbortActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnPoorChemAbortActionPerformed
        dlgPoorChemCataloge.setVisible(false);
    }                                                                                    //GEN-LAST:event_btnPoorChemAbortActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnPoorChemOkActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnPoorChemOkActionPerformed
        final Object selection = cbPoorChemCataloge.getSelectedItem();
        if (selection instanceof CidsBean) {
            final CidsBean selectedBean = (CidsBean)selection;
            final Collection<CidsBean> colToAdd = CidsBeanSupport.getBeanCollectionFromProperty(cidsBean, "poor_chems");
            if (colToAdd != null) {
                if (!colToAdd.contains(selectedBean)) {
                    colToAdd.add(selectedBean);
                }
            }
        }
        dlgPoorChemCataloge.setVisible(false);
    }                                                                                 //GEN-LAST:event_btnPoorChemOkActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnMenTrendTypeAbortActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnMenTrendTypeAbortActionPerformed
        dlgTrendTypeCataloge.setVisible(false);
    }                                                                                        //GEN-LAST:event_btnMenTrendTypeAbortActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnMenTrendTypeOkActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnMenTrendTypeOkActionPerformed
        final Object selection = cbTrendTypeCataloge.getSelectedItem();
        if (selection instanceof CidsBean) {
            final CidsBean selectedBean = (CidsBean)selection;
            final Collection<CidsBean> colToAdd = CidsBeanSupport.getBeanCollectionFromProperty(
                    cidsBean,
                    "trend_types");
            if (colToAdd != null) {
                if (!colToAdd.contains(selectedBean)) {
                    colToAdd.add(selectedBean);
                }
            }
        }
        dlgTrendTypeCataloge.setVisible(false);
    }                                                                                     //GEN-LAST:event_btnMenTrendTypeOkActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnAddTrendTypeActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnAddTrendTypeActionPerformed
        dlgTrendTypeCataloge.pack();
        StaticSwingTools.showDialog(StaticSwingTools.getParentFrame(this), dlgTrendTypeCataloge, true);
    }                                                                                   //GEN-LAST:event_btnAddTrendTypeActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnRemTrendTypeActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnRemTrendTypeActionPerformed
        final Object selection = lstTrendTypes.getSelectedValue();
        if (selection != null) {
            final int answer = JOptionPane.showConfirmDialog(
                    StaticSwingTools.getParentFrame(this),
                    "Soll der Eintrag wirklich gelöscht werden?",
                    "Eintrag entfernen",
                    JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                try {
                    final CidsBean beanToDelete = (CidsBean)selection;
                    final Object beanColl = cidsBean.getProperty("trend_types");
                    if (beanColl instanceof Collection) {
                        ((Collection)beanColl).remove(beanToDelete);
                    }
                } catch (Exception e) {
                    UIUtil.showExceptionToUser(e, this);
                }
            }
        }
    }                                                                                   //GEN-LAST:event_btnRemTrendTypeActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnAddPoorChemActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnAddPoorChemActionPerformed
        dlgPoorChemCataloge.pack();
        StaticSwingTools.showDialog(StaticSwingTools.getParentFrame(this), dlgPoorChemCataloge, true);
    }                                                                                  //GEN-LAST:event_btnAddPoorChemActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnRemPoorChemActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnRemPoorChemActionPerformed
        final Object selection = lstPoorChems.getSelectedValue();
        if (selection != null) {
            final int answer = JOptionPane.showConfirmDialog(
                    StaticSwingTools.getParentFrame(this),
                    "Soll der Eintrag wirklich gelöscht werden?",
                    "Eintrag entfernen",
                    JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                try {
                    final CidsBean beanToDelete = (CidsBean)selection;
                    final Object beanColl = cidsBean.getProperty("poor_chems");
                    if (beanColl instanceof Collection) {
                        ((Collection)beanColl).remove(beanToDelete);
                    }
                } catch (Exception e) {
                    UIUtil.showExceptionToUser(e, this);
                }
            }
        }
    }                                                                                  //GEN-LAST:event_btnRemPoorChemActionPerformed

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
        }
    }

    @Override
    public void dispose() {
        bindingGroup.unbind();
    }
}
