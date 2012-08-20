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
package de.cismet.cids.custom.objectrenderer.wrrl_db_mv;

import Sirius.server.middleware.types.MetaClass;

import java.util.Collection;

import javax.swing.JDialog;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.DisposableCidsBeanStore;

import de.cismet.cids.editors.DefaultBindableReferenceCombo;
import de.cismet.cids.editors.DefaultCustomObjectEditor;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;
import de.cismet.tools.gui.StaticSwingTools;
import javax.swing.JDialog;

import de.cismet.tools.gui.StaticSwingTools;

/**
 * DOCUMENT ME!
 *
 * @author   stefan
 * @version  $Revision$, $Date$
 */
public class WkFgPanOne extends javax.swing.JPanel implements DisposableCidsBeanStore {

    //~ Static fields/initializers ---------------------------------------------

    private static final MetaClass IMPACT_MC;
    private static final MetaClass IMPACT_SRC_MC;

    static {
        IMPACT_MC = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "wfd.sw_impact_type_code");
        IMPACT_SRC_MC = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "wfd.pressure_type_code");
    }

    //~ Instance fields --------------------------------------------------------

    private final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(this.getClass());

    private CidsBean cidsBean;
    private final boolean editable = true;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnImpactAbort;
    private javax.swing.JButton btnImpactOk;
    private javax.swing.JButton btnMenImpactSrcAbort;
    private javax.swing.JButton btnMenImpactSrcOk;
    private javax.swing.JComboBox cbImpactCataloge;
    private javax.swing.JComboBox cbImpactSrcCataloge;
    private javax.swing.JDialog dlgImpactCataloge;
    private javax.swing.JDialog dlgImpactSrcCataloge;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblAktTyp;
    private javax.swing.JLabel lblBemerkung;
    private javax.swing.JLabel lblDatenquelle;
    private javax.swing.JLabel lblEuCdRb;
    private javax.swing.JLabel lblEvk;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblImpact;
    private javax.swing.JLabel lblImpactCataloge;
    private javax.swing.JLabel lblImpactSrc;
    private javax.swing.JLabel lblImpactSrcCataloge;
    private javax.swing.JLabel lblKuestenWk;
    private javax.swing.JLabel lblPlanuCd;
    private javax.swing.JLabel lblRbdCd;
    private javax.swing.JLabel lblSpacing;
    private javax.swing.JLabel lblStaeun;
    private javax.swing.JLabel lblTypEvkK;
    private javax.swing.JLabel lblTypK;
    private javax.swing.JLabel lblValDatenquelle;
    private javax.swing.JLabel lblValEuCdRb;
    private javax.swing.JLabel lblValEvK;
    private javax.swing.JLabel lblValKuestenWk;
    private javax.swing.JLabel lblValStaeun;
    private javax.swing.JLabel lblValStaeun1;
    private javax.swing.JLabel lblValStaeun2;
    private javax.swing.JLabel lblValStaeun3;
    private javax.swing.JLabel lblValTypEvkK;
    private javax.swing.JLabel lblValTypK;
    private javax.swing.JLabel lblValTypK1;
    private javax.swing.JLabel lblValWkGroup;
    private javax.swing.JLabel lblValWkGroupAggr;
    private javax.swing.JLabel lblValWkK;
    private javax.swing.JLabel lblValWkN;
    private javax.swing.JLabel lblWaCd;
    private javax.swing.JLabel lblWkGroup;
    private javax.swing.JLabel lblWkGroupAggr;
    private javax.swing.JLabel lblWkK;
    private javax.swing.JLabel lblWkN;
    private javax.swing.JList lstImpact;
    private javax.swing.JList lstImpactSrc;
    private javax.swing.JPanel panContrImpactSrc;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JPanel panMenButtonsImpact;
    private javax.swing.JPanel panMenButtonsImpactSrc;
    private javax.swing.JScrollPane scpBemerkung;
    private javax.swing.JScrollPane scpImpact;
    private javax.swing.JScrollPane scpImpactSrc;
    private javax.swing.JSeparator sepMiddle;
    private javax.swing.JTextArea taBemerkung;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WkFgPanOne.
     */
    public WkFgPanOne() {
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

        dlgImpactCataloge = new JDialog(StaticSwingTools.getParentFrame(this));
        lblImpactCataloge = new javax.swing.JLabel();
        DefaultBindableReferenceCombo cb1 = new DefaultBindableReferenceCombo(IMPACT_MC,true,true);
        cbImpactCataloge = cb1;
        panMenButtonsImpact = new javax.swing.JPanel();
        btnImpactAbort = new javax.swing.JButton();
        btnImpactOk = new javax.swing.JButton();
        dlgImpactSrcCataloge = new JDialog(StaticSwingTools.getParentFrame(this));
        lblImpactSrcCataloge = new javax.swing.JLabel();
        DefaultBindableReferenceCombo cb2 = new DefaultBindableReferenceCombo(IMPACT_SRC_MC,true,true);
        cbImpactSrcCataloge = cb2;
        panMenButtonsImpactSrc = new javax.swing.JPanel();
        btnMenImpactSrcAbort = new javax.swing.JButton();
        btnMenImpactSrcOk = new javax.swing.JButton();
        panInfo = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading = new javax.swing.JLabel();
        panInfoContent = new javax.swing.JPanel();
        lblWkK = new javax.swing.JLabel();
        lblWkN = new javax.swing.JLabel();
        lblTypEvkK = new javax.swing.JLabel();
        lblTypK = new javax.swing.JLabel();
        lblImpactSrc = new javax.swing.JLabel();
        lblDatenquelle = new javax.swing.JLabel();
        lblImpact = new javax.swing.JLabel();
        lblKuestenWk = new javax.swing.JLabel();
        lblWkGroup = new javax.swing.JLabel();
        lblWkGroupAggr = new javax.swing.JLabel();
        lblBemerkung = new javax.swing.JLabel();
        lblStaeun = new javax.swing.JLabel();
        lblEuCdRb = new javax.swing.JLabel();
        scpBemerkung = new javax.swing.JScrollPane();
        taBemerkung = new javax.swing.JTextArea();
        lblSpacing = new javax.swing.JLabel();
        sepMiddle = new javax.swing.JSeparator();
        scpImpact = new javax.swing.JScrollPane();
        lstImpact = new javax.swing.JList();
        scpImpactSrc = new javax.swing.JScrollPane();
        lstImpactSrc = new javax.swing.JList();
        panContrImpactSrc = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lblEvk = new javax.swing.JLabel();
        lblValWkK = new javax.swing.JLabel();
        lblValWkN = new javax.swing.JLabel();
        lblValEvK = new javax.swing.JLabel();
        lblValTypEvkK = new javax.swing.JLabel();
        lblValTypK = new javax.swing.JLabel();
        lblValWkGroup = new javax.swing.JLabel();
        lblValWkGroupAggr = new javax.swing.JLabel();
        lblValStaeun = new javax.swing.JLabel();
        lblValKuestenWk = new javax.swing.JLabel();
        lblValEuCdRb = new javax.swing.JLabel();
        lblValDatenquelle = new javax.swing.JLabel();
        lblPlanuCd = new javax.swing.JLabel();
        lblWaCd = new javax.swing.JLabel();
        lblRbdCd = new javax.swing.JLabel();
        lblValStaeun1 = new javax.swing.JLabel();
        lblValStaeun2 = new javax.swing.JLabel();
        lblValStaeun3 = new javax.swing.JLabel();
        lblAktTyp = new javax.swing.JLabel();
        lblValTypK1 = new javax.swing.JLabel();

        dlgImpactCataloge.getContentPane().setLayout(new java.awt.GridBagLayout());

        lblImpactCataloge.setText(org.openide.util.NbBundle.getMessage(WkFgPanOne.class, "WkFgPanOne.lblImpactCataloge.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgImpactCataloge.getContentPane().add(lblImpactCataloge, gridBagConstraints);

        cbImpactCataloge.setMinimumSize(new java.awt.Dimension(250, 18));
        cbImpactCataloge.setPreferredSize(new java.awt.Dimension(250, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgImpactCataloge.getContentPane().add(cbImpactCataloge, gridBagConstraints);

        panMenButtonsImpact.setLayout(new java.awt.GridBagLayout());

        btnImpactAbort.setText(org.openide.util.NbBundle.getMessage(WkFgPanOne.class, "WkFgPanOne.btnImpactAbort.text")); // NOI18N
        btnImpactAbort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImpactAbortActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMenButtonsImpact.add(btnImpactAbort, gridBagConstraints);

        btnImpactOk.setText(org.openide.util.NbBundle.getMessage(WkFgPanOne.class, "WkFgPanOne.btnImpactOk.text")); // NOI18N
        btnImpactOk.setMaximumSize(new java.awt.Dimension(85, 23));
        btnImpactOk.setMinimumSize(new java.awt.Dimension(85, 23));
        btnImpactOk.setPreferredSize(new java.awt.Dimension(85, 23));
        btnImpactOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImpactOkActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMenButtonsImpact.add(btnImpactOk, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgImpactCataloge.getContentPane().add(panMenButtonsImpact, gridBagConstraints);

        dlgImpactSrcCataloge.getContentPane().setLayout(new java.awt.GridBagLayout());

        lblImpactSrcCataloge.setText(org.openide.util.NbBundle.getMessage(WkFgPanOne.class, "WkFgPanOne.lblImpactSrcCataloge.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgImpactSrcCataloge.getContentPane().add(lblImpactSrcCataloge, gridBagConstraints);

        cbImpactSrcCataloge.setMinimumSize(new java.awt.Dimension(250, 18));
        cbImpactSrcCataloge.setPreferredSize(new java.awt.Dimension(250, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgImpactSrcCataloge.getContentPane().add(cbImpactSrcCataloge, gridBagConstraints);

        panMenButtonsImpactSrc.setLayout(new java.awt.GridBagLayout());

        btnMenImpactSrcAbort.setText(org.openide.util.NbBundle.getMessage(WkFgPanOne.class, "WkFgPanOne.btnMenImpactSrcAbort.text")); // NOI18N
        btnMenImpactSrcAbort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenImpactSrcAbortActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMenButtonsImpactSrc.add(btnMenImpactSrcAbort, gridBagConstraints);

        btnMenImpactSrcOk.setText(org.openide.util.NbBundle.getMessage(WkFgPanOne.class, "WkFgPanOne.btnMenImpactSrcOk.text")); // NOI18N
        btnMenImpactSrcOk.setMaximumSize(new java.awt.Dimension(85, 23));
        btnMenImpactSrcOk.setMinimumSize(new java.awt.Dimension(85, 23));
        btnMenImpactSrcOk.setPreferredSize(new java.awt.Dimension(85, 23));
        btnMenImpactSrcOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
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
        dlgImpactSrcCataloge.getContentPane().add(panMenButtonsImpactSrc, gridBagConstraints);

        setMaximumSize(new java.awt.Dimension(1100, 550));
        setMinimumSize(new java.awt.Dimension(1100, 550));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(1100, 550));
        setLayout(new java.awt.BorderLayout());

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setLayout(new java.awt.FlowLayout());

        lblHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading.setText(org.openide.util.NbBundle.getMessage(WkFgPanOne.class, "WkFgPanOne.lblHeading.text")); // NOI18N
        panHeadInfo.add(lblHeading);

        panInfo.add(panHeadInfo, java.awt.BorderLayout.NORTH);

        panInfoContent.setOpaque(false);
        panInfoContent.setLayout(new java.awt.GridBagLayout());

        lblWkK.setText(org.openide.util.NbBundle.getMessage(WkFgPanOne.class, "WkFgPanOne.lblWkK.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 25, 5, 5);
        panInfoContent.add(lblWkK, gridBagConstraints);

        lblWkN.setText(org.openide.util.NbBundle.getMessage(WkFgPanOne.class, "WkFgPanOne.lblWkN.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 25, 5, 5);
        panInfoContent.add(lblWkN, gridBagConstraints);

        lblTypEvkK.setText(org.openide.util.NbBundle.getMessage(WkFgPanOne.class, "WkFgPanOne.lblTypEvkK.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 25, 5, 5);
        panInfoContent.add(lblTypEvkK, gridBagConstraints);

        lblTypK.setText(org.openide.util.NbBundle.getMessage(WkFgPanOne.class, "WkFgPanOne.lblTypK.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 25, 5, 5);
        panInfoContent.add(lblTypK, gridBagConstraints);

        lblImpactSrc.setText(org.openide.util.NbBundle.getMessage(WkFgPanOne.class, "WkFgPanOne.lblImpactSrc.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 25, 5, 5);
        panInfoContent.add(lblImpactSrc, gridBagConstraints);

        lblDatenquelle.setText(org.openide.util.NbBundle.getMessage(WkFgPanOne.class, "WkFgPanOne.lblDatenquelle.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(lblDatenquelle, gridBagConstraints);

        lblImpact.setText(org.openide.util.NbBundle.getMessage(WkFgPanOne.class, "WkFgPanOne.lblImpact.text")); // NOI18N
        lblImpact.setToolTipText(org.openide.util.NbBundle.getMessage(WkFgPanOne.class, "WkFgPanOne.lblImpact.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 25, 5, 5);
        panInfoContent.add(lblImpact, gridBagConstraints);

        lblKuestenWk.setText(org.openide.util.NbBundle.getMessage(WkFgPanOne.class, "WkFgPanOne.lblKuestenWk.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(lblKuestenWk, gridBagConstraints);

        lblWkGroup.setText(org.openide.util.NbBundle.getMessage(WkFgPanOne.class, "WkFgPanOne.lblWkGroup.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        panInfoContent.add(lblWkGroup, gridBagConstraints);

        lblWkGroupAggr.setText(org.openide.util.NbBundle.getMessage(WkFgPanOne.class, "WkFgPanOne.lblWkGroupAggr.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(lblWkGroupAggr, gridBagConstraints);

        lblBemerkung.setText(org.openide.util.NbBundle.getMessage(WkFgPanOne.class, "WkFgPanOne.lblBemerkung.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(lblBemerkung, gridBagConstraints);

        lblStaeun.setText(org.openide.util.NbBundle.getMessage(WkFgPanOne.class, "WkFgPanOne.lblStaeun.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(lblStaeun, gridBagConstraints);

        lblEuCdRb.setText(org.openide.util.NbBundle.getMessage(WkFgPanOne.class, "WkFgPanOne.lblEuCdRb.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(lblEuCdRb, gridBagConstraints);

        scpBemerkung.setMinimumSize(new java.awt.Dimension(375, 80));
        scpBemerkung.setPreferredSize(new java.awt.Dimension(375, 80));

        taBemerkung.setColumns(20);
        taBemerkung.setEditable(false);
        taBemerkung.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        taBemerkung.setRows(5);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bemerkung}"), taBemerkung, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        scpBemerkung.setViewportView(taBemerkung);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 25);
        panInfoContent.add(scpBemerkung, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.weighty = 1.0;
        panInfoContent.add(lblSpacing, gridBagConstraints);

        sepMiddle.setOrientation(javax.swing.SwingConstants.VERTICAL);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.insets = new java.awt.Insets(25, 5, 25, 15);
        panInfoContent.add(sepMiddle, gridBagConstraints);

        scpImpact.setMinimumSize(new java.awt.Dimension(300, 80));
        scpImpact.setPreferredSize(new java.awt.Dimension(300, 80));

        lstImpact.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${cidsBean.impact}");
        org.jdesktop.swingbinding.JListBinding jListBinding = org.jdesktop.swingbinding.SwingBindings.createJListBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_ONCE, this, eLProperty, lstImpact);
        bindingGroup.addBinding(jListBinding);

        scpImpact.setViewportView(lstImpact);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(scpImpact, gridBagConstraints);

        scpImpactSrc.setMinimumSize(new java.awt.Dimension(375, 80));
        scpImpactSrc.setPreferredSize(new java.awt.Dimension(375, 80));

        lstImpactSrc.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${cidsBean.impact_src}");
        jListBinding = org.jdesktop.swingbinding.SwingBindings.createJListBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_ONCE, this, eLProperty, lstImpactSrc);
        bindingGroup.addBinding(jListBinding);

        scpImpactSrc.setViewportView(lstImpactSrc);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(scpImpactSrc, gridBagConstraints);

        panContrImpactSrc.setOpaque(false);
        panContrImpactSrc.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 11;
        panInfoContent.add(panContrImpactSrc, gridBagConstraints);

        jPanel1.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        panInfoContent.add(jPanel1, gridBagConstraints);

        lblEvk.setText(org.openide.util.NbBundle.getMessage(WkFgPanOne.class, "WkFgPanOne.lblEvk.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 25, 5, 5);
        panInfoContent.add(lblEvk, gridBagConstraints);

        lblValWkK.setMinimumSize(new java.awt.Dimension(300, 20));
        lblValWkK.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.wk_k}"), lblValWkK, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        panInfoContent.add(lblValWkK, gridBagConstraints);

        lblValWkN.setMinimumSize(new java.awt.Dimension(300, 20));
        lblValWkN.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.wk_n}"), lblValWkN, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(lblValWkN, gridBagConstraints);

        lblValEvK.setMinimumSize(new java.awt.Dimension(300, 20));
        lblValEvK.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.evk.name}"), lblValEvK, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(lblValEvK, gridBagConstraints);

        lblValTypEvkK.setMinimumSize(new java.awt.Dimension(300, 20));
        lblValTypEvkK.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.typ_evk_k.name}"), lblValTypEvkK, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(lblValTypEvkK, gridBagConstraints);

        lblValTypK.setMinimumSize(new java.awt.Dimension(300, 20));
        lblValTypK.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.typ_k.name}"), lblValTypK, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(lblValTypK, gridBagConstraints);

        lblValWkGroup.setMinimumSize(new java.awt.Dimension(300, 20));
        lblValWkGroup.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.wk_group.value}"), lblValWkGroup, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        panInfoContent.add(lblValWkGroup, gridBagConstraints);

        lblValWkGroupAggr.setMinimumSize(new java.awt.Dimension(300, 20));
        lblValWkGroupAggr.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.wk_group.wk_group_aggr.value}"), lblValWkGroupAggr, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(lblValWkGroupAggr, gridBagConstraints);

        lblValStaeun.setMinimumSize(new java.awt.Dimension(300, 20));
        lblValStaeun.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.stalu.value}"), lblValStaeun, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(lblValStaeun, gridBagConstraints);

        lblValKuestenWk.setMinimumSize(new java.awt.Dimension(300, 20));
        lblValKuestenWk.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.kuesten_wk.name}"), lblValKuestenWk, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(lblValKuestenWk, gridBagConstraints);

        lblValEuCdRb.setMinimumSize(new java.awt.Dimension(300, 20));
        lblValEuCdRb.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.eu_cd_rb}"), lblValEuCdRb, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(lblValEuCdRb, gridBagConstraints);

        lblValDatenquelle.setMinimumSize(new java.awt.Dimension(300, 20));
        lblValDatenquelle.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.datenquelle}"), lblValDatenquelle, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(lblValDatenquelle, gridBagConstraints);

        lblPlanuCd.setText(org.openide.util.NbBundle.getMessage(WkFgPanOne.class, "WkFgPanOne.lblPlanuCd.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(lblPlanuCd, gridBagConstraints);

        lblWaCd.setText(org.openide.util.NbBundle.getMessage(WkFgPanOne.class, "WkFgPanOne.lblWaCd.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(lblWaCd, gridBagConstraints);

        lblRbdCd.setText(org.openide.util.NbBundle.getMessage(WkFgPanOne.class, "WkFgPanOne.lblRbdCd.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(lblRbdCd, gridBagConstraints);

        lblValStaeun1.setMinimumSize(new java.awt.Dimension(300, 20));
        lblValStaeun1.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.planu_cd.name}"), lblValStaeun1, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(lblValStaeun1, gridBagConstraints);

        lblValStaeun2.setMinimumSize(new java.awt.Dimension(300, 20));
        lblValStaeun2.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.wa_cd.name}"), lblValStaeun2, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(lblValStaeun2, gridBagConstraints);

        lblValStaeun3.setMinimumSize(new java.awt.Dimension(300, 20));
        lblValStaeun3.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.rbd_cd.name}"), lblValStaeun3, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(lblValStaeun3, gridBagConstraints);

        lblAktTyp.setText(org.openide.util.NbBundle.getMessage(WkFgPanOne.class, "WkFgPanOne.lblAktTyp.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 25, 5, 5);
        panInfoContent.add(lblAktTyp, gridBagConstraints);

        lblValTypK1.setMinimumSize(new java.awt.Dimension(300, 20));
        lblValTypK1.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.lawa_type.lawa_nr.description}"), lblValTypK1, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(lblValTypK1, gridBagConstraints);

        panInfo.add(panInfoContent, java.awt.BorderLayout.CENTER);

        add(panInfo, java.awt.BorderLayout.CENTER);

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnImpactAbortActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImpactAbortActionPerformed
        dlgImpactCataloge.setVisible(false);
    }//GEN-LAST:event_btnImpactAbortActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnImpactOkActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImpactOkActionPerformed
        final Object selection = cbImpactCataloge.getSelectedItem();
        if (selection instanceof CidsBean) {
            final CidsBean selectedBean = (CidsBean)selection;
            final Collection<CidsBean> colToAdd = CidsBeanSupport.getBeanCollectionFromProperty(cidsBean, "impact");
            if (colToAdd != null) {
                if (!colToAdd.contains(selectedBean)) {
                    colToAdd.add(selectedBean);
                }
            }
        }
        dlgImpactCataloge.setVisible(false);
    }//GEN-LAST:event_btnImpactOkActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnMenImpactSrcAbortActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenImpactSrcAbortActionPerformed
        dlgImpactSrcCataloge.setVisible(false);
    }//GEN-LAST:event_btnMenImpactSrcAbortActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnMenImpactSrcOkActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenImpactSrcOkActionPerformed
        final Object selection = cbImpactSrcCataloge.getSelectedItem();
        if (selection instanceof CidsBean) {
            final CidsBean selectedBean = (CidsBean)selection;
            final Collection<CidsBean> colToAdd = CidsBeanSupport.getBeanCollectionFromProperty(cidsBean, "impact_src");
            if (colToAdd != null) {
                if (!colToAdd.contains(selectedBean)) {
                    colToAdd.add(selectedBean);
                }
            }
        }
        dlgImpactSrcCataloge.setVisible(false);
    }//GEN-LAST:event_btnMenImpactSrcOkActionPerformed

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
            try {
                bindingGroup.bind();
            } catch (Exception e) {
                log.warn("Eror during bind", e);
            }
        }
    }

    @Override
    public void dispose() {
        bindingGroup.unbind();
        dlgImpactCataloge.dispose();
        dlgImpactSrcCataloge.dispose();
    }
}
