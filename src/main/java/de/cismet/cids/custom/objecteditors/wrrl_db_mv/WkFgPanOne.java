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

import Sirius.server.middleware.types.MetaClass;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.server.search.WkFgWkkSearch;
import de.cismet.cids.custom.wrrl_db_mv.server.search.WkkUniqueSearch;
import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;
import de.cismet.cids.custom.wrrl_db_mv.util.RendererTools;
import de.cismet.cids.custom.wrrl_db_mv.util.ScrollableComboBox;
import de.cismet.cids.custom.wrrl_db_mv.util.UIUtil;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.DisposableCidsBeanStore;

import de.cismet.cids.editors.DefaultBindableReferenceCombo;
import de.cismet.cids.editors.DefaultCustomObjectEditor;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.server.search.CidsServerSearch;

import de.cismet.tools.gui.StaticSwingTools;

/**
 * DOCUMENT ME!
 *
 * @author   stefan
 * @version  $Revision$, $Date$
 */
public class WkFgPanOne extends javax.swing.JPanel implements DisposableCidsBeanStore {

    //~ Static fields/initializers ---------------------------------------------

    private static final Logger LOG = Logger.getLogger(WkFgPanOne.class);
    private static final MetaClass IMPACT_MC;
    private static final MetaClass IMPACT_SRC_MC;
    private static String[] wkk;

    static {
        IMPACT_MC = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "wfd.sw_impact_type_code");
        IMPACT_SRC_MC = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "wfd.pressure_type_code");

        try {
            final CidsServerSearch search = new WkFgWkkSearch();
            final Collection res = SessionManager.getProxy()
                        .customServerSearch(SessionManager.getSession().getUser(), search);
            final ArrayList<ArrayList> resArray = (ArrayList<ArrayList>)res;

            if (resArray != null) {
                wkk = new String[resArray.size() + 1];
                wkk[0] = null;

                for (int i = 0; i < resArray.size(); ++i) {
                    if ((resArray.get(i) != null) && (resArray.get(i).get(0) != null)) {
                        wkk[i + 1] = resArray.get(i).get(0).toString();
                    }
                }
            } else {
                wkk = new String[0];
            }
            Arrays.sort(wkk, 1, wkk.length);
        } catch (Exception e) {
            LOG.error("Error while retrieving wkk", e);
        }
    }

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    private final boolean editable = true;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddImpact;
    private javax.swing.JButton btnAddImpactSrc;
    private javax.swing.JButton btnImpactAbort;
    private javax.swing.JButton btnImpactOk;
    private javax.swing.JButton btnMenImpactSrcAbort;
    private javax.swing.JButton btnMenImpactSrcOk;
    private javax.swing.JButton btnRemImpact;
    private javax.swing.JButton btnRemImpactSrc;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbEvk;
    private javax.swing.JComboBox cbGrpBio;
    private javax.swing.JComboBox cbGrpChem;
    private javax.swing.JComboBox cbImpactCataloge;
    private javax.swing.JComboBox cbImpactSrcCataloge;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbPlanuCd;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbRbdCd;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbSchutzgut;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbStalu;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbTypEvkK;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbTypK;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbWaCd;
    private javax.swing.JDialog dlgImpactCataloge;
    private javax.swing.JDialog dlgImpactSrcCataloge;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblBemerkung;
    private javax.swing.JLabel lblEvk;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblImpact;
    private javax.swing.JLabel lblImpactCataloge;
    private javax.swing.JLabel lblImpactSrc;
    private javax.swing.JLabel lblImpactSrcCataloge;
    private javax.swing.JLabel lblPlanuCd;
    private javax.swing.JLabel lblRbdCd;
    private javax.swing.JLabel lblSchutzgut;
    private javax.swing.JLabel lblSpacing;
    private javax.swing.JLabel lblStaeun;
    private javax.swing.JLabel lblTypEvkK;
    private javax.swing.JLabel lblTypK;
    private javax.swing.JLabel lblWaCd;
    private javax.swing.JLabel lblWkGroup;
    private javax.swing.JLabel lblWkGroupAggr;
    private javax.swing.JLabel lblWkK;
    private javax.swing.JLabel lblWkKPre;
    private javax.swing.JLabel lblWkN;
    private javax.swing.JList lstImpact;
    private javax.swing.JList lstImpactSrc;
    private javax.swing.JPanel panContrImpact;
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
    private javax.swing.JTextField txtWkK;
    private javax.swing.JTextField txtWkKPre;
    private javax.swing.JTextField txtWkN;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WkFgPanOne.
     */
    public WkFgPanOne() {
        initComponents();
        RendererTools.makeReadOnly(cbWaCd);
        cbGrpBio.setModel(new DefaultComboBoxModel(wkk));
        cbGrpChem.setModel(new DefaultComboBoxModel(wkk));
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
        final DefaultBindableReferenceCombo cb1 = new ScrollableComboBox(IMPACT_MC, true, true);
        cbImpactCataloge = cb1;
        panMenButtonsImpact = new javax.swing.JPanel();
        btnImpactAbort = new javax.swing.JButton();
        btnImpactOk = new javax.swing.JButton();
        dlgImpactSrcCataloge = new JDialog(StaticSwingTools.getParentFrame(this));
        lblImpactSrcCataloge = new javax.swing.JLabel();
        final DefaultBindableReferenceCombo cb2 = new ScrollableComboBox(IMPACT_SRC_MC, true, true);
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
        lblImpact = new javax.swing.JLabel();
        lblWkGroup = new javax.swing.JLabel();
        lblWkGroupAggr = new javax.swing.JLabel();
        lblBemerkung = new javax.swing.JLabel();
        lblStaeun = new javax.swing.JLabel();
        lblPlanuCd = new javax.swing.JLabel();
        lblWaCd = new javax.swing.JLabel();
        lblRbdCd = new javax.swing.JLabel();
        txtWkK = new javax.swing.JTextField();
        txtWkN = new javax.swing.JTextField();
        scpBemerkung = new javax.swing.JScrollPane();
        taBemerkung = new javax.swing.JTextArea();
        cbTypK = new ScrollableComboBox();
        cbRbdCd = new ScrollableComboBox();
        cbWaCd = new ScrollableComboBox();
        cbPlanuCd = new ScrollableComboBox();
        lblSpacing = new javax.swing.JLabel();
        sepMiddle = new javax.swing.JSeparator();
        scpImpact = new javax.swing.JScrollPane();
        lstImpact = new javax.swing.JList();
        scpImpactSrc = new javax.swing.JScrollPane();
        lstImpactSrc = new javax.swing.JList();
        panContrImpact = new javax.swing.JPanel();
        btnAddImpact = new javax.swing.JButton();
        btnRemImpact = new javax.swing.JButton();
        panContrImpactSrc = new javax.swing.JPanel();
        btnAddImpactSrc = new javax.swing.JButton();
        btnRemImpactSrc = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lblEvk = new javax.swing.JLabel();
        cbEvk = new ScrollableComboBox();
        cbTypEvkK = new ScrollableComboBox();
        cbStalu = new ScrollableComboBox();
        lblWkKPre = new javax.swing.JLabel();
        txtWkKPre = new javax.swing.JTextField();
        cbSchutzgut = new ScrollableComboBox();
        lblSchutzgut = new javax.swing.JLabel();
        cbGrpChem = new javax.swing.JComboBox();
        cbGrpBio = new javax.swing.JComboBox();

        dlgImpactCataloge.getContentPane().setLayout(new java.awt.GridBagLayout());

        lblImpactCataloge.setText(org.openide.util.NbBundle.getMessage(
                WkFgPanOne.class,
                "WkFgPanOne.lblImpactCataloge.text")); // NOI18N
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

        btnImpactAbort.setText(org.openide.util.NbBundle.getMessage(
                WkFgPanOne.class,
                "WkFgPanOne.btnImpactAbort.text")); // NOI18N
        btnImpactAbort.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
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

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
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

        lblImpactSrcCataloge.setText(org.openide.util.NbBundle.getMessage(
                WkFgPanOne.class,
                "WkFgPanOne.lblImpactSrcCataloge.text")); // NOI18N
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

        btnMenImpactSrcAbort.setText(org.openide.util.NbBundle.getMessage(
                WkFgPanOne.class,
                "WkFgPanOne.btnMenImpactSrcAbort.text")); // NOI18N
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
                WkFgPanOne.class,
                "WkFgPanOne.btnMenImpactSrcOk.text")); // NOI18N
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

        panInfoContent.setMaximumSize(new java.awt.Dimension(1150, 490));
        panInfoContent.setMinimumSize(new java.awt.Dimension(1150, 490));
        panInfoContent.setOpaque(false);
        panInfoContent.setPreferredSize(new java.awt.Dimension(1150, 490));
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
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 25, 5, 5);
        panInfoContent.add(lblWkN, gridBagConstraints);

        lblTypEvkK.setText(org.openide.util.NbBundle.getMessage(WkFgPanOne.class, "WkFgPanOne.lblTypEvkK.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
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
        lblImpactSrc.setToolTipText(org.openide.util.NbBundle.getMessage(
                WkFgPanOne.class,
                "WkFgPanOne.lblImpactSrc.toolTipText"));                                                              // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 25, 5, 5);
        panInfoContent.add(lblImpactSrc, gridBagConstraints);

        lblImpact.setText(org.openide.util.NbBundle.getMessage(WkFgPanOne.class, "WkFgPanOne.lblImpact.text")); // NOI18N
        lblImpact.setToolTipText(org.openide.util.NbBundle.getMessage(
                WkFgPanOne.class,
                "WkFgPanOne.lblImpact.toolTipText"));                                                           // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 25, 5, 5);
        panInfoContent.add(lblImpact, gridBagConstraints);

        lblWkGroup.setText(org.openide.util.NbBundle.getMessage(WkFgPanOne.class, "WkFgPanOne.lblWkGroup.text_1")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        panInfoContent.add(lblWkGroup, gridBagConstraints);

        lblWkGroupAggr.setText(org.openide.util.NbBundle.getMessage(
                WkFgPanOne.class,
                "WkFgPanOne.lblWkGroupAggr.text")); // NOI18N
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
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridheight = 3;
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

        txtWkK.setMaximumSize(new java.awt.Dimension(375, 20));
        txtWkK.setMinimumSize(new java.awt.Dimension(375, 20));
        txtWkK.setPreferredSize(new java.awt.Dimension(375, 20));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.wk_k}"),
                txtWkK,
                org.jdesktop.beansbinding.BeanProperty.create("text"),
                "");
        binding.setSourceNullValue("null");
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        panInfoContent.add(txtWkK, gridBagConstraints);

        txtWkN.setMinimumSize(new java.awt.Dimension(300, 20));
        txtWkN.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.wk_n}"),
                txtWkN,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("null");
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(txtWkN, gridBagConstraints);

        scpBemerkung.setMinimumSize(new java.awt.Dimension(300, 20));
        scpBemerkung.setPreferredSize(new java.awt.Dimension(300, 20));

        taBemerkung.setColumns(20);
        taBemerkung.setRows(5);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bemerkung}"),
                taBemerkung,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("");
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        scpBemerkung.setViewportView(taBemerkung);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridheight = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 25);
        panInfoContent.add(scpBemerkung, gridBagConstraints);

        cbTypK.setMinimumSize(new java.awt.Dimension(300, 20));
        cbTypK.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.lawa_type}"),
                cbTypK,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(cbTypK, gridBagConstraints);

        cbRbdCd.setMinimumSize(new java.awt.Dimension(300, 20));
        cbRbdCd.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.rbd_cd}"),
                cbRbdCd,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 25);
        panInfoContent.add(cbRbdCd, gridBagConstraints);

        cbWaCd.setMinimumSize(new java.awt.Dimension(300, 20));
        cbWaCd.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.wa_cd}"),
                cbWaCd,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 25);
        panInfoContent.add(cbWaCd, gridBagConstraints);

        cbPlanuCd.setMinimumSize(new java.awt.Dimension(375, 20));
        cbPlanuCd.setPreferredSize(new java.awt.Dimension(375, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.planu_cd}"),
                cbPlanuCd,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 25);
        panInfoContent.add(cbPlanuCd, gridBagConstraints);
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

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create(
                "${cidsBean.impact}");
        org.jdesktop.swingbinding.JListBinding jListBinding = org.jdesktop.swingbinding.SwingBindings
                    .createJListBinding(
                        org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                        this,
                        eLProperty,
                        lstImpact);
        bindingGroup.addBinding(jListBinding);

        scpImpact.setViewportView(lstImpact);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(scpImpact, gridBagConstraints);

        scpImpactSrc.setMinimumSize(new java.awt.Dimension(300, 80));
        scpImpactSrc.setPreferredSize(new java.awt.Dimension(300, 80));

        lstImpactSrc.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${cidsBean.impact_src}");
        jListBinding = org.jdesktop.swingbinding.SwingBindings.createJListBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                eLProperty,
                lstImpactSrc);
        bindingGroup.addBinding(jListBinding);

        scpImpactSrc.setViewportView(lstImpactSrc);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(scpImpactSrc, gridBagConstraints);

        panContrImpact.setOpaque(false);
        panContrImpact.setLayout(new java.awt.GridBagLayout());

        btnAddImpact.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_add_mini.png"))); // NOI18N
        btnAddImpact.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnAddImpactActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panContrImpact.add(btnAddImpact, gridBagConstraints);

        btnRemImpact.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_remove_mini.png"))); // NOI18N
        btnRemImpact.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnRemImpactActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panContrImpact.add(btnRemImpact, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridheight = 3;
        panInfoContent.add(panContrImpact, gridBagConstraints);

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
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridheight = 3;
        panInfoContent.add(panContrImpactSrc, gridBagConstraints);

        jPanel1.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        panInfoContent.add(jPanel1, gridBagConstraints);

        lblEvk.setText(org.openide.util.NbBundle.getMessage(WkFgPanOne.class, "WkFgPanOne.lblEvk.text"));               // NOI18N
        lblEvk.setToolTipText(org.openide.util.NbBundle.getMessage(WkFgPanOne.class, "WkFgPanOne.lblEvk.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 25, 5, 5);
        panInfoContent.add(lblEvk, gridBagConstraints);

        cbEvk.setMinimumSize(new java.awt.Dimension(300, 20));
        cbEvk.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.evk}"),
                cbEvk,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(cbEvk, gridBagConstraints);

        cbTypEvkK.setMinimumSize(new java.awt.Dimension(300, 20));
        cbTypEvkK.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.typ_evk_k}"),
                cbTypEvkK,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(cbTypEvkK, gridBagConstraints);

        cbStalu.setMinimumSize(new java.awt.Dimension(300, 20));
        cbStalu.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.stalu}"),
                cbStalu,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 25);
        panInfoContent.add(cbStalu, gridBagConstraints);

        lblWkKPre.setText(org.openide.util.NbBundle.getMessage(WkFgPanOne.class, "WkFgPanOne.lblWkKPre.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 25, 5, 5);
        panInfoContent.add(lblWkKPre, gridBagConstraints);

        txtWkKPre.setMaximumSize(new java.awt.Dimension(375, 20));
        txtWkKPre.setMinimumSize(new java.awt.Dimension(375, 20));
        txtWkKPre.setPreferredSize(new java.awt.Dimension(375, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.wb_predecs}"),
                txtWkKPre,
                org.jdesktop.beansbinding.BeanProperty.create("text"),
                "wb_pre");
        binding.setSourceNullValue("");
        binding.setSourceUnreadableValue("<Errror>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(txtWkKPre, gridBagConstraints);

        cbSchutzgut.setMinimumSize(new java.awt.Dimension(300, 20));
        cbSchutzgut.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.schutzgut}"),
                cbSchutzgut,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(cbSchutzgut, gridBagConstraints);

        lblSchutzgut.setText(org.openide.util.NbBundle.getMessage(WkFgPanOne.class, "WkFgPanOne.lblSchutzgut.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 25, 5, 5);
        panInfoContent.add(lblSchutzgut, gridBagConstraints);

        cbGrpChem.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "wird geladen" }));
        cbGrpChem.setMinimumSize(new java.awt.Dimension(375, 20));
        cbGrpChem.setPreferredSize(new java.awt.Dimension(375, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.grpchem}"),
                cbGrpChem,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setSourceNullValue("");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 25);
        panInfoContent.add(cbGrpChem, gridBagConstraints);

        cbGrpBio.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "wird geladen" }));
        cbGrpBio.setMinimumSize(new java.awt.Dimension(375, 20));
        cbGrpBio.setPreferredSize(new java.awt.Dimension(375, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.grpbio}"),
                cbGrpBio,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setSourceNullValue("");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 25);
        panInfoContent.add(cbGrpBio, gridBagConstraints);

        panInfo.add(panInfoContent, java.awt.BorderLayout.CENTER);

        add(panInfo, java.awt.BorderLayout.CENTER);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnAddImpactSrcActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnAddImpactSrcActionPerformed
        dlgImpactSrcCataloge.setSize(300, 150);
        StaticSwingTools.showDialog(StaticSwingTools.getParentFrame(this), dlgImpactSrcCataloge, true);
    }                                                                                   //GEN-LAST:event_btnAddImpactSrcActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnRemImpactSrcActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnRemImpactSrcActionPerformed
        final Object selection = lstImpactSrc.getSelectedValue();
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
                    final CidsBean beanToDelete = (CidsBean)selection;
                    final Object beanColl = cidsBean.getProperty("impact_src");
                    if (beanColl instanceof Collection) {
                        ((Collection)beanColl).remove(beanToDelete);
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
    private void btnRemImpactActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnRemImpactActionPerformed
        final Object selection = lstImpact.getSelectedValue();
        if (selection != null) {
            final int answer = JOptionPane.showConfirmDialog(
                    StaticSwingTools.getParentFrame(this),
                    "Soll die Auswirkung '"
                            + selection.toString()
                            + "' wirklich gelöscht werden?",
                    "Auswirkung entfernen",
                    JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                try {
                    final CidsBean beanToDelete = (CidsBean)selection;
                    final Object beanColl = cidsBean.getProperty("impact");
                    if (beanColl instanceof Collection) {
                        ((Collection)beanColl).remove(beanToDelete);
                    }
                } catch (Exception e) {
                    UIUtil.showExceptionToUser(e, this);
                }
            }
        }
    }                                                                                //GEN-LAST:event_btnRemImpactActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnAddImpactActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnAddImpactActionPerformed
        dlgImpactCataloge.setSize(300, 150);
        StaticSwingTools.showDialog(StaticSwingTools.getParentFrame(this), dlgImpactCataloge, true);
    }                                                                                //GEN-LAST:event_btnAddImpactActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnImpactAbortActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnImpactAbortActionPerformed
        dlgImpactCataloge.setVisible(false);
    }                                                                                  //GEN-LAST:event_btnImpactAbortActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnImpactOkActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnImpactOkActionPerformed
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
    }                                                                               //GEN-LAST:event_btnImpactOkActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnMenImpactSrcAbortActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnMenImpactSrcAbortActionPerformed
        dlgImpactSrcCataloge.setVisible(false);
    }                                                                                        //GEN-LAST:event_btnMenImpactSrcAbortActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnMenImpactSrcOkActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnMenImpactSrcOkActionPerformed
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
    }                                                                                     //GEN-LAST:event_btnMenImpactSrcOkActionPerformed

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
        dlgImpactCataloge.dispose();
        dlgImpactSrcCataloge.dispose();
    }
}
