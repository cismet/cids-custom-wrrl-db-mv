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

import org.openide.util.NbBundle;

import java.awt.Color;
import java.awt.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import de.cismet.cids.client.tools.DevelopmentTools;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.server.search.PressureImpactsProposals;
import de.cismet.cids.custom.wrrl_db_mv.server.search.WkFgWkkSearch;
import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;
import de.cismet.cids.custom.wrrl_db_mv.util.DefaultBindableScrollableComboboxCellEditor;
import de.cismet.cids.custom.wrrl_db_mv.util.RendererTools;
import de.cismet.cids.custom.wrrl_db_mv.util.ScrollableComboBox;
import de.cismet.cids.custom.wrrl_db_mv.util.UIUtil;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.DisposableCidsBeanStore;

import de.cismet.cids.editors.DefaultBindableReferenceCombo;
import de.cismet.cids.editors.DefaultCustomObjectEditor;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.server.search.CidsServerSearch;

import de.cismet.connectioncontext.AbstractConnectionContext;
import de.cismet.connectioncontext.ConnectionContext;

import de.cismet.tools.BrowserLauncher;

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
    private static final MetaClass IMPACT_CODE_MC;
    private static final MetaClass DRIVER_MC;
    private static final MetaClass SUBSTANCE_CODE_MC;
    private static String[] wkk;
    private static Map<Integer, Set<Integer>> pressureImpactMap = new HashMap<>();
    private static Map<PressureImpact, Set<Integer>> pressureImpactDriverMap = new HashMap<>();
    private static final ConnectionContext CC = ConnectionContext.create(
            AbstractConnectionContext.Category.EDITOR,
            "Wk_FG");

    static {
        IMPACT_MC = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "wfd.sw_impact_type_code");
        IMPACT_SRC_MC = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "wfd.pressure_type_code_neu");
        IMPACT_CODE_MC = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "wfd.impact_code");
        DRIVER_MC = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "wfd.driver_code");
        SUBSTANCE_CODE_MC = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "wfd.codelist_substance_code");

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
        new Thread("Init proposals") {

                @Override
                public void run() {
                    initProposals();
                }
            }.start();
    }

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    private boolean readOnly = false;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddImpactSrc;
    private javax.swing.JButton btnImpactAbort;
    private javax.swing.JButton btnImpactOk;
    private javax.swing.JButton btnMenImpactSrcAbort;
    private javax.swing.JButton btnMenImpactSrcOk;
    private javax.swing.JButton btnRemImpactSrc;
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
    private javax.swing.JDialog dlgImpactCataloge;
    private javax.swing.JDialog dlgImpactSrcCataloge;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labSteckbrief;
    private javax.swing.JLabel labSteckbriefVal;
    private javax.swing.JLabel lblBemerkung;
    private javax.swing.JLabel lblEvk;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblImpactCataloge;
    private javax.swing.JLabel lblImpactSrcCataloge;
    private javax.swing.JLabel lblPlanuCd;
    private javax.swing.JLabel lblRbdCd;
    private javax.swing.JLabel lblSchutzgut;
    private javax.swing.JLabel lblSpacing;
    private javax.swing.JLabel lblStaeun;
    private javax.swing.JLabel lblTypEvkK;
    private javax.swing.JLabel lblTypK;
    private javax.swing.JLabel lblValStaeun2;
    private javax.swing.JLabel lblWaCd;
    private javax.swing.JLabel lblWkGroup;
    private javax.swing.JLabel lblWkGroupAggr;
    private javax.swing.JLabel lblWkK;
    private javax.swing.JLabel lblWkKPre;
    private javax.swing.JLabel lblWkN;
    private javax.swing.JPanel panContrImpact;
    private javax.swing.JPanel panContrImpactSrc;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JPanel panMenButtonsImpact;
    private javax.swing.JPanel panMenButtonsImpactSrc;
    private javax.swing.JPanel panPressure;
    private javax.swing.JScrollPane scpBemerkung;
    private javax.swing.JSeparator sepMiddle;
    private javax.swing.JTextArea taBemerkung;
    private org.jdesktop.swingx.JXTable tabPressure;
    private javax.swing.JTextField txtB9ausw;
    private javax.swing.JTextField txtWkK;
    private javax.swing.JTextField txtWkKPre;
    private javax.swing.JTextField txtWkN;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new WkFgPanOne object.
     */
    public WkFgPanOne() {
        this(false);
    }

    /**
     * Creates new form WkFgPanOne.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public WkFgPanOne(final boolean readOnly) {
        this.readOnly = readOnly;
        initComponents();

        panPressure.setVisible(true);
        lblTypEvkK.setVisible(false);
        cbTypEvkK.setVisible(false);

        // this fiele should always be read only
        RendererTools.makeReadOnly(txtB9ausw);

        if (readOnly) {
            RendererTools.makeReadOnly(txtWkK);
            RendererTools.makeReadOnly(txtWkKPre);
            RendererTools.makeReadOnly(txtWkN);
            RendererTools.makeReadOnly(cbGrpBio);
            RendererTools.makeReadOnly(cbGrpChem);
            RendererTools.makeReadOnly(cbImpactCataloge);
            RendererTools.makeReadOnly(cbImpactSrcCataloge);
            RendererTools.makeReadOnly(cbPlanuCd);
            RendererTools.makeReadOnly(cbRbdCd);
            RendererTools.makeReadOnly(cbSchutzgut);
            RendererTools.makeReadOnly(cbStalu);
            RendererTools.makeReadOnly(cbTypEvkK);
            RendererTools.makeReadOnly(cbTypK);
            RendererTools.makeReadOnly(taBemerkung);
            panContrImpact.setVisible(false);
            panContrImpactSrc.setVisible(false);
        }

        if (wkk != null) {
            cbGrpBio.setModel(new DefaultComboBoxModel(wkk));
            cbGrpChem.setModel(new DefaultComboBoxModel(wkk));
        }
    }

    //~ Methods ----------------------------------------------------------------

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
                    final PressureImpact pressureImpact = new PressureImpact(pressure, impact);
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
        final DefaultBindableReferenceCombo cb2 = new ScrollableComboBox(IMPACT_SRC_MC, false, false);
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
        cbPlanuCd = new ScrollableComboBox();
        lblSpacing = new javax.swing.JLabel();
        sepMiddle = new javax.swing.JSeparator();
        panContrImpact = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lblEvk = new javax.swing.JLabel();
        cbTypEvkK = new ScrollableComboBox();
        cbStalu = new ScrollableComboBox();
        lblWkKPre = new javax.swing.JLabel();
        txtWkKPre = new javax.swing.JTextField();
        cbSchutzgut = new ScrollableComboBox();
        lblSchutzgut = new javax.swing.JLabel();
        cbGrpChem = new javax.swing.JComboBox();
        cbGrpBio = new javax.swing.JComboBox();
        lblValStaeun2 = new javax.swing.JLabel();
        panPressure = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabPressure = new org.jdesktop.swingx.JXTable();
        panContrImpactSrc = new javax.swing.JPanel();
        btnAddImpactSrc = new javax.swing.JButton();
        btnRemImpactSrc = new javax.swing.JButton();
        txtB9ausw = new javax.swing.JTextField();
        labSteckbrief = new javax.swing.JLabel();
        labSteckbriefVal = new javax.swing.JLabel();

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

        cbImpactSrcCataloge.setMinimumSize(new java.awt.Dimension(450, 18));
        cbImpactSrcCataloge.setPreferredSize(new java.awt.Dimension(450, 18));
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

        panInfoContent.setMaximumSize(new java.awt.Dimension(1550, 490));
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
        gridBagConstraints.weightx = 1.0;
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

        scpBemerkung.setMinimumSize(new java.awt.Dimension(300, 100));
        scpBemerkung.setPreferredSize(new java.awt.Dimension(300, 100));

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
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
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
        gridBagConstraints.gridheight = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.insets = new java.awt.Insets(25, 5, 25, 15);
        panInfoContent.add(sepMiddle, gridBagConstraints);

        panContrImpact.setOpaque(false);
        panContrImpact.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridheight = 3;
        panInfoContent.add(panContrImpact, gridBagConstraints);

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

        cbTypEvkK.setMinimumSize(new java.awt.Dimension(300, 20));
        cbTypEvkK.setPreferredSize(new java.awt.Dimension(300, 20));
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
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(cbSchutzgut, gridBagConstraints);

        lblSchutzgut.setText(org.openide.util.NbBundle.getMessage(WkFgPanOne.class, "WkFgPanOne.lblSchutzgut.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
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
        gridBagConstraints.weightx = 1.0;
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

        lblValStaeun2.setMinimumSize(new java.awt.Dimension(300, 20));
        lblValStaeun2.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.planu_cd.wa_cd}"),
                lblValStaeun2,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(lblValStaeun2, gridBagConstraints);

        panPressure.setBorder(javax.swing.BorderFactory.createTitledBorder(
                org.openide.util.NbBundle.getMessage(
                    WkFgPanOne.class,
                    "WkFgPanOne.panPressure.border.title",
                    new Object[] {}))); // NOI18N
        panPressure.setMinimumSize(new java.awt.Dimension(110, 300));
        panPressure.setOpaque(false);
        panPressure.setPreferredSize(new java.awt.Dimension(110, 300));
        panPressure.setLayout(new java.awt.GridBagLayout());

        jScrollPane1.setViewportView(tabPressure);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        panPressure.add(jScrollPane1, gridBagConstraints);

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
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 25, 5, 25);
        panInfoContent.add(panPressure, gridBagConstraints);

        txtB9ausw.setMinimumSize(new java.awt.Dimension(300, 20));
        txtB9ausw.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.b9ausw}"),
                txtB9ausw,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(txtB9ausw, gridBagConstraints);

        labSteckbrief.setText(org.openide.util.NbBundle.getMessage(
                WkFgPanOne.class,
                "WkFgPanOne.labSteckbrief.text",
                new Object[] {})); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 25, 5, 5);
        panInfoContent.add(labSteckbrief, gridBagConstraints);

        labSteckbriefVal.setText(org.openide.util.NbBundle.getMessage(
                WkFgPanOne.class,
                "WkFgPanOne.labSteckbriefVal.text",
                new Object[] {})); // NOI18N
        labSteckbriefVal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        labSteckbriefVal.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(final java.awt.event.MouseEvent evt) {
                    labSteckbriefValMouseClicked(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(labSteckbriefVal, gridBagConstraints);

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
        dlgImpactSrcCataloge.setSize(500, 150);
        StaticSwingTools.showDialog(StaticSwingTools.getParentFrame(this), dlgImpactSrcCataloge, true);
    }                                                                                   //GEN-LAST:event_btnAddImpactSrcActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnRemImpactSrcActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnRemImpactSrcActionPerformed
        final int selectedRow = tabPressure.getSelectedRow();
        final CidsBean selection = ((PressureTableModel)tabPressure.getModel()).getCidsBean(selectedRow);
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
            dlgImpactSrcCataloge.setVisible(false);
        } catch (Exception e) {
            LOG.error("Cannot create new cids bean", e);
        }
    } //GEN-LAST:event_btnMenImpactSrcOkActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void labSteckbriefValMouseClicked(final java.awt.event.MouseEvent evt) { //GEN-FIRST:event_labSteckbriefValMouseClicked
        try {
            BrowserLauncher.openURL("https://fis-wasser-mv.de/charts/steckbriefe/rw/rw_wk.php?fg="
                        + String.valueOf(cidsBean.getProperty("wk_k")));
        } catch (Exception ex) {
            LOG.warn(ex, ex);
        }
    }                                                                                //GEN-LAST:event_labSteckbriefValMouseClicked

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
            labSteckbriefVal.setText("<html><a href=\"https://fis-wasser-mv.de/charts/steckbriefe/rw/rw_wk.php?fg="
                        + String.valueOf(cidsBean.getProperty("wk_k")) + "\">Webseite</a></html>");
        } else {
            labSteckbriefVal.setText("");
        }

        setModel();
    }

    /**
     * DOCUMENT ME!
     */
    private void setModel() {
        if (cidsBean != null) {
            final List<CidsBean> pressures = CidsBeanSupport.getBeanCollectionFromProperty(
                    cidsBean,
                    "pressure_impact_driver");
            tabPressure.setModel(new PressureTableModel(readOnly, pressures));
        } else {
            tabPressure.setModel(new PressureTableModel(readOnly));
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

    /**
     * DOCUMENT ME!
     *
     * @param   impact  pressure DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean isImpactProposed(final CidsBean impact) {
        final int selectedRow = tabPressure.getSelectedRow();
        final CidsBean bean = ((PressureTableModel)tabPressure.getModel()).getCidsBean(selectedRow);

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
        final CidsBean bean = ((PressureTableModel)tabPressure.getModel()).getCidsBean(selectedRow);

        if (bean != null) {
            final Integer pressureId = (Integer)bean.getProperty("pressure.id");
            final Integer impactId = (Integer)bean.getProperty("impact.id");
            final Integer driverId = (Integer)driver.getProperty("id");

            if ((pressureId == null) || pressureImpactDriverMap.isEmpty() || (impactId == null) || (driverId == null)) {
                return true;
            }

            final PressureImpact pressureImpact = new PressureImpact(pressureId, impactId);

            final Set<Integer> proposedDriver = pressureImpactDriverMap.get(pressureImpact);

            if (proposedDriver != null) {
                return proposedDriver.contains(driverId);
            }
        } else {
            return true;
        }
        return false;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   args  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public static void main(final String[] args) throws Exception {
        DevelopmentTools.createEditorInFrameFromRMIConnectionOnLocalhost(
            "WRRL_DB_MV",
            "Administratoren",
            "admin",
            "n!emal$99",
            "wk_fg",
            1,
            1280,
            1024);
    }

    @Override
    public void dispose() {
        bindingGroup.unbind();
        dlgImpactCataloge.dispose();
        dlgImpactSrcCataloge.dispose();
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public static class PressureTableModel implements TableModel {

        //~ Static fields/initializers -----------------------------------------

        private static final String[] COLUMN_NAMES = {
                "Belastungskategorie",
                "Signifikante Belastung am WK",
                "Belastungsauswirkungen",
                "Verursacher",
                "Belastung mit Substance-Code",
                "Kommentar"
            };
        private static final String[] PROPERTY_NAMES = {
                "",
                "pressure.name",
                "impact",
                "driver",
                "substance_code",
                "bemerkung"
            };

        //~ Instance fields ----------------------------------------------------

        private List<TableModelListener> listener = new ArrayList<TableModelListener>();
        private List<CidsBean> data = new ArrayList<CidsBean>();
        private boolean readOnly;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new PressureTableModel object.
         *
         * @param  readOnly  DOCUMENT ME!
         */
        public PressureTableModel(final boolean readOnly) {
            this.readOnly = readOnly;
            this.data = new ArrayList<CidsBean>();
        }

        /**
         * Creates a new PressureTableModel object.
         *
         * @param  readOnly  DOCUMENT ME!
         * @param  beans     DOCUMENT ME!
         */
        public PressureTableModel(final boolean readOnly, final List<CidsBean> beans) {
            this.readOnly = readOnly;
            this.data = new ArrayList<CidsBean>(beans);
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public int getColumnCount() {
            return COLUMN_NAMES.length;
        }

        @Override
        public String getColumnName(final int columnIndex) {
            return COLUMN_NAMES[columnIndex];
        }

        @Override
        public Class<?> getColumnClass(final int columnIndex) {
            if ((columnIndex < 2) || (columnIndex == 5)) {
                return String.class;
            } else {
                return CidsBean.class;
            }
        }

        @Override
        public boolean isCellEditable(final int rowIndex, final int columnIndex) {
            return !readOnly && (columnIndex != 0) && (columnIndex != 1);
        }

        @Override
        public Object getValueAt(final int rowIndex, final int columnIndex) {
            if (columnIndex == 0) {
                final Object substanceCode = data.get(rowIndex).getProperty("substance_code");

                if (substanceCode == null) {
                    return NbBundle.getMessage(
                            WkFgPanOne.class,
                            "WkFgPanOne.refillCategory.anthropog");
                } else {
                    return NbBundle.getMessage(
                            WkFgPanOne.class,
                            "WkFgPanOne.refillCategory.material");
                }
            } else {
                return data.get(rowIndex).getProperty(PROPERTY_NAMES[columnIndex]);
            }
        }

        /**
         * DOCUMENT ME!
         *
         * @param   row  DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public CidsBean getCidsBean(final int row) {
            if (row >= 0) {
                return data.get(row);
            } else {
                return null;
            }
        }

        @Override
        public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
            try {
                data.get(rowIndex).setProperty(PROPERTY_NAMES[columnIndex], aValue);
            } catch (Exception e) {
                LOG.error("cannot set property", e);
            }
        }

        @Override
        public void addTableModelListener(final TableModelListener l) {
            listener.add(l);
        }

        @Override
        public void removeTableModelListener(final TableModelListener l) {
            listener.remove(l);
        }

        /**
         * DOCUMENT ME!
         */
        public void fireTableChanged() {
            final TableModelEvent e = new TableModelEvent(this);
            for (final TableModelListener tmp : listener) {
                tmp.tableChanged(e);
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public static class PressureImpact implements Comparable<PressureImpact> {

        //~ Instance fields ----------------------------------------------------

        private final Integer pressure;
        private final Integer impact;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new PressureImpact object.
         *
         * @param  pressure  DOCUMENT ME!
         * @param  impact    DOCUMENT ME!
         */
        public PressureImpact(final Integer pressure, final Integer impact) {
            this.pressure = pressure;
            this.impact = impact;
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public boolean equals(final Object obj) {
            if (obj instanceof PressureImpact) {
                final PressureImpact other = (PressureImpact)obj;

                return pressure.equals(other.pressure) && impact.equals(other.impact);
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = (67 * hash) + Objects.hashCode(this.pressure);
            hash = (67 * hash) + Objects.hashCode(this.impact);
            return hash;
        }

        @Override
        public int compareTo(final PressureImpact o) {
            if (o.pressure.equals(pressure)) {
                return o.impact.compareTo(impact);
            } else {
                return o.pressure.compareTo(pressure);
            }
        }
    }
}
