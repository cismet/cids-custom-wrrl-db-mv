/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
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
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class WkSgPanOne extends javax.swing.JPanel implements DisposableCidsBeanStore {

    //~ Static fields/initializers ---------------------------------------------

    private static final Logger LOG = Logger.getLogger(WkSgPanOne.class);
    private static final String EU_CD_LW_PREFIX = "DE_LS_DEMV_LW_";
    private static final String MS_CD_LW_PREFIX = "DEMV_LW_";
    private static final MetaClass IMPACT_MC;
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
        IMPACT_MC = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "wfd.sw_impact_type_code");
        IMPACT_SRC_MC = ClassCacheMultiple.getMetaClass(WRRLUtil.DOMAIN_NAME, "wfd.pressure_type_code_neu");
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
    private final boolean editable = true;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddImpact;
    private javax.swing.JButton btnAddImpactSrc;
    private javax.swing.JButton btnAddImpactSrc1;
    private javax.swing.JButton btnImpactAbort;
    private javax.swing.JButton btnImpactOk;
    private javax.swing.JButton btnMenImpactSrcAbort;
    private javax.swing.JButton btnMenImpactSrcAbort1;
    private javax.swing.JButton btnMenImpactSrcOk;
    private javax.swing.JButton btnMenImpactSrcOk1;
    private javax.swing.JButton btnRemImpact;
    private javax.swing.JButton btnRemImpactSrc;
    private javax.swing.JButton btnRemImpactSrc1;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbArtificial;
    private javax.swing.JComboBox cbGeom;
    private javax.swing.JComboBox cbImpactCataloge;
    private javax.swing.JComboBox cbImpactSrcCataloge;
    private javax.swing.JComboBox cbImpactSrcCataloge1;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbModified;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbPlanuCd;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbPlanu_cd;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbRbdCd;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbTy_cd_lw;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbWaCd;
    private javax.swing.JDialog dlgImpactCataloge;
    private javax.swing.JDialog dlgImpactSrc1Cataloge;
    private javax.swing.JDialog dlgImpactSrcCataloge;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labWebsite;
    private javax.swing.JLabel lblArtificial;
    private javax.swing.JLabel lblEuCdRb;
    private javax.swing.JLabel lblEu_cd_lw;
    private javax.swing.JLabel lblGeom;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblImpact;
    private javax.swing.JLabel lblImpactCataloge;
    private javax.swing.JLabel lblImpactSrc;
    private javax.swing.JLabel lblImpactSrcCataloge;
    private javax.swing.JLabel lblImpactSrcCataloge1;
    private javax.swing.JLabel lblKuestenWk;
    private javax.swing.JLabel lblLw_name;
    private javax.swing.JLabel lblModified;
    private javax.swing.JLabel lblMs_cd_lw;
    private javax.swing.JLabel lblPlanu_cd;
    private javax.swing.JLabel lblRbdCd;
    private javax.swing.JLabel lblSee_id;
    private javax.swing.JLabel lblSpacing;
    private javax.swing.JLabel lblTy_cd_lw;
    private javax.swing.JLabel lblValEuCdLw;
    private javax.swing.JLabel lblValMsCdLw;
    private javax.swing.JLabel lblWaCd;
    private javax.swing.JLabel lblWebsite;
    private javax.swing.JLabel lblWhy_hmwb;
    private javax.swing.JLabel lblWkK;
    private javax.swing.JList lstImpact;
    private javax.swing.JList lstImpactSrc;
    private javax.swing.JPanel panContrImpact;
    private javax.swing.JPanel panContrImpactSrc;
    private javax.swing.JPanel panContrImpactSrc1;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JPanel panMenButtonsImpact;
    private javax.swing.JPanel panMenButtonsImpactSrc;
    private javax.swing.JPanel panMenButtonsImpactSrc1;
    private javax.swing.JPanel panPressure;
    private javax.swing.JScrollPane scpImpact;
    private javax.swing.JScrollPane scpImpactSrc;
    private javax.swing.JSeparator sepMiddle;
    private org.jdesktop.swingx.JXTable tabPressure;
    private javax.swing.JTextField txtEuCdRb;
    private javax.swing.JTextField txtKuestenWk;
    private javax.swing.JTextField txtLw_name;
    private javax.swing.JTextField txtSee_id;
    private javax.swing.JTextField txtWkK;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WkFgPanOne.
     */
    public WkSgPanOne() {
        initComponents();
        lblImpact.setVisible(false);
        lblImpactSrc.setVisible(false);
        scpImpact.setVisible(false);
        scpImpactSrc.setVisible(false);
        panContrImpact.setVisible(false);
        panContrImpactSrc.setVisible(false);
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
        final DefaultBindableReferenceCombo cb1 = new DefaultBindableReferenceCombo(IMPACT_MC, true, true);
        cbImpactCataloge = cb1;
        panMenButtonsImpact = new javax.swing.JPanel();
        btnImpactAbort = new javax.swing.JButton();
        btnImpactOk = new javax.swing.JButton();
        dlgImpactSrcCataloge = new JDialog(StaticSwingTools.getParentFrame(this));
        lblImpactSrcCataloge = new javax.swing.JLabel();
        final DefaultBindableReferenceCombo cb2 = new DefaultBindableReferenceCombo(IMPACT_SRC_MC, true, true);
        cbImpactSrcCataloge = cb2;
        panMenButtonsImpactSrc = new javax.swing.JPanel();
        btnMenImpactSrcAbort = new javax.swing.JButton();
        btnMenImpactSrcOk = new javax.swing.JButton();
        dlgImpactSrc1Cataloge = new JDialog(StaticSwingTools.getParentFrame(this));
        lblImpactSrcCataloge1 = new javax.swing.JLabel();
        final DefaultBindableReferenceCombo cb3 = new ScrollableComboBox(IMPACT_SRC_MC, false, false);
        cbImpactSrcCataloge1 = cb3;
        panMenButtonsImpactSrc1 = new javax.swing.JPanel();
        btnMenImpactSrcAbort1 = new javax.swing.JButton();
        btnMenImpactSrcOk1 = new javax.swing.JButton();
        panInfo = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading = new javax.swing.JLabel();
        panInfoContent = new javax.swing.JPanel();
        lblImpactSrc = new javax.swing.JLabel();
        lblImpact = new javax.swing.JLabel();
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
        lblWkK = new javax.swing.JLabel();
        txtWkK = new javax.swing.JTextField();
        lblLw_name = new javax.swing.JLabel();
        txtLw_name = new javax.swing.JTextField();
        lblSee_id = new javax.swing.JLabel();
        txtSee_id = new javax.swing.JTextField();
        lblTy_cd_lw = new javax.swing.JLabel();
        cbTy_cd_lw = new de.cismet.cids.editors.DefaultBindableReferenceCombo();
        lblArtificial = new javax.swing.JLabel();
        cbArtificial = new de.cismet.cids.editors.DefaultBindableReferenceCombo();
        lblModified = new javax.swing.JLabel();
        lblWhy_hmwb = new javax.swing.JLabel();
        cbModified = new de.cismet.cids.editors.DefaultBindableReferenceCombo();
        cbPlanuCd = new de.cismet.cids.editors.DefaultBindableReferenceCombo();
        cbPlanu_cd = new de.cismet.cids.editors.DefaultBindableReferenceCombo();
        cbWaCd = new de.cismet.cids.editors.DefaultBindableReferenceCombo();
        cbRbdCd = new de.cismet.cids.editors.DefaultBindableReferenceCombo();
        lblPlanu_cd = new javax.swing.JLabel();
        lblWaCd = new javax.swing.JLabel();
        lblRbdCd = new javax.swing.JLabel();
        lblKuestenWk = new javax.swing.JLabel();
        txtKuestenWk = new javax.swing.JTextField();
        lblEu_cd_lw = new javax.swing.JLabel();
        txtEuCdRb = new javax.swing.JTextField();
        lblEuCdRb = new javax.swing.JLabel();
        lblMs_cd_lw = new javax.swing.JLabel();
        lblGeom = new javax.swing.JLabel();
        cbGeom = new DefaultCismapGeometryComboBoxEditor();
        lblValEuCdLw = new javax.swing.JLabel();
        lblValMsCdLw = new javax.swing.JLabel();
        labWebsite = new javax.swing.JLabel();
        lblWebsite = new javax.swing.JLabel();
        panPressure = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabPressure = new org.jdesktop.swingx.JXTable();
        panContrImpactSrc1 = new javax.swing.JPanel();
        btnAddImpactSrc1 = new javax.swing.JButton();
        btnRemImpactSrc1 = new javax.swing.JButton();

        dlgImpactCataloge.getContentPane().setLayout(new java.awt.GridBagLayout());

        lblImpactCataloge.setText(org.openide.util.NbBundle.getMessage(
                WkSgPanOne.class,
                "WkSgPanOne.lblImpactCataloge.text")); // NOI18N
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
                WkSgPanOne.class,
                "WkSgPanOne.btnImpactAbort.text")); // NOI18N
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

        btnImpactOk.setText(org.openide.util.NbBundle.getMessage(WkSgPanOne.class, "WkSgPanOne.btnImpactOk.text")); // NOI18N
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
                WkSgPanOne.class,
                "WkSgPanOne.lblImpactSrcCataloge.text")); // NOI18N
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
                WkSgPanOne.class,
                "WkSgPanOne.btnMenImpactSrcAbort.text")); // NOI18N
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
                WkSgPanOne.class,
                "WkSgPanOne.btnMenImpactSrcOk.text")); // NOI18N
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

        dlgImpactSrc1Cataloge.getContentPane().setLayout(new java.awt.GridBagLayout());

        lblImpactSrcCataloge1.setText(org.openide.util.NbBundle.getMessage(
                WkSgPanOne.class,
                "WkSgPanOne.lblImpactSrcCataloge1.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgImpactSrc1Cataloge.getContentPane().add(lblImpactSrcCataloge1, gridBagConstraints);

        cbImpactSrcCataloge1.setMinimumSize(new java.awt.Dimension(450, 18));
        cbImpactSrcCataloge1.setPreferredSize(new java.awt.Dimension(450, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgImpactSrc1Cataloge.getContentPane().add(cbImpactSrcCataloge1, gridBagConstraints);

        panMenButtonsImpactSrc1.setLayout(new java.awt.GridBagLayout());

        btnMenImpactSrcAbort1.setText(org.openide.util.NbBundle.getMessage(
                WkSgPanOne.class,
                "WkSgPanOne.btnMenImpactSrcAbort1.text")); // NOI18N
        btnMenImpactSrcAbort1.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnMenImpactSrcAbort1ActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMenButtonsImpactSrc1.add(btnMenImpactSrcAbort1, gridBagConstraints);

        btnMenImpactSrcOk1.setText(org.openide.util.NbBundle.getMessage(
                WkSgPanOne.class,
                "WkSgPanOne.btnMenImpactSrcOk1.text")); // NOI18N
        btnMenImpactSrcOk1.setMaximumSize(new java.awt.Dimension(85, 23));
        btnMenImpactSrcOk1.setMinimumSize(new java.awt.Dimension(85, 23));
        btnMenImpactSrcOk1.setPreferredSize(new java.awt.Dimension(85, 23));
        btnMenImpactSrcOk1.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnMenImpactSrcOk1ActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMenButtonsImpactSrc1.add(btnMenImpactSrcOk1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        dlgImpactSrc1Cataloge.getContentPane().add(panMenButtonsImpactSrc1, gridBagConstraints);

        setMinimumSize(new java.awt.Dimension(1100, 550));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(1100, 550));
        setLayout(new java.awt.BorderLayout());

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setLayout(new java.awt.FlowLayout());

        lblHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading.setText("Allgemeine Informationen");
        panHeadInfo.add(lblHeading);

        panInfo.add(panHeadInfo, java.awt.BorderLayout.NORTH);

        panInfoContent.setOpaque(false);
        panInfoContent.setLayout(new java.awt.GridBagLayout());

        lblImpactSrc.setText("Sign. Belastungsquellen");
        lblImpactSrc.setMaximumSize(new java.awt.Dimension(180, 17));
        lblImpactSrc.setMinimumSize(new java.awt.Dimension(180, 17));
        lblImpactSrc.setPreferredSize(new java.awt.Dimension(180, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent.add(lblImpactSrc, gridBagConstraints);

        lblImpact.setText("Auswirkungen der Bel.");
        lblImpact.setToolTipText("Auswirkungen der Belastungen");
        lblImpact.setMaximumSize(new java.awt.Dimension(180, 17));
        lblImpact.setMinimumSize(new java.awt.Dimension(180, 17));
        lblImpact.setPreferredSize(new java.awt.Dimension(180, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent.add(lblImpact, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.weighty = 1.0;
        panInfoContent.add(lblSpacing, gridBagConstraints);

        sepMiddle.setOrientation(javax.swing.SwingConstants.VERTICAL);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 15);
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
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(scpImpact, gridBagConstraints);

        scpImpactSrc.setMaximumSize(new java.awt.Dimension(300, 80));
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
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
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

        lblWkK.setText(org.openide.util.NbBundle.getMessage(WkSgPanOne.class, "WkSgPanOne.lblWkK.text")); // NOI18N
        lblWkK.setToolTipText("Wasserkörper-Kürzel");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 5, 5);
        panInfoContent.add(lblWkK, gridBagConstraints);

        txtWkK.setMinimumSize(new java.awt.Dimension(300, 20));
        txtWkK.setPreferredSize(new java.awt.Dimension(300, 20));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.wk_k}"),
                txtWkK,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("");
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

        lblLw_name.setText("Wasserkörpername");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent.add(lblLw_name, gridBagConstraints);

        txtLw_name.setMinimumSize(new java.awt.Dimension(300, 20));
        txtLw_name.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.lw_name}"),
                txtLw_name,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("");
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(txtLw_name, gridBagConstraints);

        lblSee_id.setText("ID des einzelnen Sees");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent.add(lblSee_id, gridBagConstraints);

        txtSee_id.setMinimumSize(new java.awt.Dimension(300, 20));
        txtSee_id.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.see_id}"),
                txtSee_id,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("");
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(txtSee_id, gridBagConstraints);

        lblTy_cd_lw.setText(org.openide.util.NbBundle.getMessage(WkSgPanOne.class, "WkSgPanOne.lblTy_cd_lw.text")); // NOI18N
        lblTy_cd_lw.setToolTipText("Typ d. See-WK - Code");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent.add(lblTy_cd_lw, gridBagConstraints);

        cbTy_cd_lw.setMinimumSize(new java.awt.Dimension(300, 20));
        cbTy_cd_lw.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.ty_cd_lw}"),
                cbTy_cd_lw,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(cbTy_cd_lw, gridBagConstraints);

        lblArtificial.setText("Künstlich?");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent.add(lblArtificial, gridBagConstraints);

        cbArtificial.setMinimumSize(new java.awt.Dimension(300, 20));
        cbArtificial.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.artificial}"),
                cbArtificial,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(cbArtificial, gridBagConstraints);

        lblModified.setText("Erheblich verändert?");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent.add(lblModified, gridBagConstraints);

        lblWhy_hmwb.setText("Schutzgut");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent.add(lblWhy_hmwb, gridBagConstraints);

        cbModified.setMinimumSize(new java.awt.Dimension(300, 20));
        cbModified.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.modified}"),
                cbModified,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(cbModified, gridBagConstraints);

        cbPlanuCd.setMinimumSize(new java.awt.Dimension(300, 20));
        cbPlanuCd.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.WHY_HMWB}"),
                cbPlanuCd,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(cbPlanuCd, gridBagConstraints);

        cbPlanu_cd.setMinimumSize(new java.awt.Dimension(300, 20));
        cbPlanu_cd.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.planu_cd}"),
                cbPlanu_cd,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(cbPlanu_cd, gridBagConstraints);

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
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(cbWaCd, gridBagConstraints);

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
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(cbRbdCd, gridBagConstraints);

        lblPlanu_cd.setText("Planungseinheit");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(lblPlanu_cd, gridBagConstraints);

        lblWaCd.setText("Bearbeitungsgebiet");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(lblWaCd, gridBagConstraints);

        lblRbdCd.setText("Flussgebietseinheit");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(lblRbdCd, gridBagConstraints);

        lblKuestenWk.setText("Ehemalige WK-Bezeichnung");
        lblKuestenWk.setMaximumSize(new java.awt.Dimension(180, 17));
        lblKuestenWk.setMinimumSize(new java.awt.Dimension(180, 17));
        lblKuestenWk.setPreferredSize(new java.awt.Dimension(180, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(lblKuestenWk, gridBagConstraints);

        txtKuestenWk.setMinimumSize(new java.awt.Dimension(300, 20));
        txtKuestenWk.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.wb_predec}"),
                txtKuestenWk,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("");
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(txtKuestenWk, gridBagConstraints);

        lblEu_cd_lw.setText("Int. See-WK-Code");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(lblEu_cd_lw, gridBagConstraints);

        txtEuCdRb.setMinimumSize(new java.awt.Dimension(300, 20));
        txtEuCdRb.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.eu_cd_rb}"),
                txtEuCdRb,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("");
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(txtEuCdRb, gridBagConstraints);

        lblEuCdRb.setText("River Basin");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(lblEuCdRb, gridBagConstraints);

        lblMs_cd_lw.setText("Nat. See-WK-Code");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(lblMs_cd_lw, gridBagConstraints);

        lblGeom.setText(org.openide.util.NbBundle.getMessage(WkSgPanOne.class, "WkSgPanOne.lblGeom.text")); // NOI18N
        lblGeom.setMaximumSize(new java.awt.Dimension(350, 20));
        lblGeom.setMinimumSize(new java.awt.Dimension(250, 20));
        lblGeom.setPreferredSize(new java.awt.Dimension(180, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(lblGeom, gridBagConstraints);

        cbGeom.setMinimumSize(new java.awt.Dimension(300, 20));
        cbGeom.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.geom}"),
                cbGeom,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setConverter(((DefaultCismapGeometryComboBoxEditor)cbGeom).getConverter());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(cbGeom, gridBagConstraints);

        lblValEuCdLw.setMinimumSize(new java.awt.Dimension(300, 20));
        lblValEuCdLw.setPreferredSize(new java.awt.Dimension(300, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(lblValEuCdLw, gridBagConstraints);

        lblValMsCdLw.setMinimumSize(new java.awt.Dimension(300, 20));
        lblValMsCdLw.setPreferredSize(new java.awt.Dimension(300, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(lblValMsCdLw, gridBagConstraints);

        labWebsite.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        labWebsite.addMouseListener(new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(final java.awt.event.MouseEvent evt) {
                    labWebsiteMouseClicked(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        panInfoContent.add(labWebsite, gridBagConstraints);

        lblWebsite.setText(org.openide.util.NbBundle.getMessage(
                WkSgPanOne.class,
                "WkSgPanOne.lblWebsite.text",
                new Object[] {})); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);
        panInfoContent.add(lblWebsite, gridBagConstraints);

        panPressure.setBorder(javax.swing.BorderFactory.createTitledBorder(
                org.openide.util.NbBundle.getMessage(
                    WkSgPanOne.class,
                    "WkSgPanOne.panPressure.border.title",
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

        panContrImpactSrc1.setOpaque(false);
        panContrImpactSrc1.setLayout(new java.awt.GridBagLayout());

        btnAddImpactSrc1.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_add_mini.png"))); // NOI18N
        btnAddImpactSrc1.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnAddImpactSrc1ActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panContrImpactSrc1.add(btnAddImpactSrc1, gridBagConstraints);

        btnRemImpactSrc1.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/edit_remove_mini.png"))); // NOI18N
        btnRemImpactSrc1.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnRemImpactSrc1ActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panContrImpactSrc1.add(btnRemImpactSrc1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        panPressure.add(panContrImpactSrc1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
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
    private void btnAddImpactSrcActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnAddImpactSrcActionPerformed
//        UIUtil.findOptimalPositionOnScreen(dlgImpactSrcCataloge);
        dlgImpactSrcCataloge.setSize(300, 150);
        StaticSwingTools.showDialog(StaticSwingTools.getParentFrame(this), dlgImpactSrcCataloge, true);
    } //GEN-LAST:event_btnAddImpactSrcActionPerformed

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
                    "Soll die Belastungsquelle '"
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
//        UIUtil.findOptimalPositionOnScreen(dlgImpactCataloge);
        dlgImpactCataloge.setSize(300, 150);
        StaticSwingTools.showDialog(StaticSwingTools.getParentFrame(this), dlgImpactCataloge, true);
    } //GEN-LAST:event_btnAddImpactActionPerformed

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

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void labWebsiteMouseClicked(final java.awt.event.MouseEvent evt) { //GEN-FIRST:event_labWebsiteMouseClicked
        try {
            BrowserLauncher.openURL(
                "https://fis-wasser-mv.de/charts/steckbriefe/lw/lw_wk.php?sg="
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
    private void btnMenImpactSrcAbort1ActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnMenImpactSrcAbort1ActionPerformed
        dlgImpactSrc1Cataloge.setVisible(false);
    }                                                                                         //GEN-LAST:event_btnMenImpactSrcAbort1ActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnMenImpactSrcOk1ActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnMenImpactSrcOk1ActionPerformed
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
    } //GEN-LAST:event_btnMenImpactSrcOk1ActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnAddImpactSrc1ActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnAddImpactSrc1ActionPerformed
        dlgImpactSrc1Cataloge.setSize(500, 150);
        StaticSwingTools.showDialog(StaticSwingTools.getParentFrame(this), dlgImpactSrc1Cataloge, true);
    }                                                                                    //GEN-LAST:event_btnAddImpactSrc1ActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnRemImpactSrc1ActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnRemImpactSrc1ActionPerformed
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
    }                                                                                    //GEN-LAST:event_btnRemImpactSrc1ActionPerformed

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
            lblValEuCdLw.setText(EU_CD_LW_PREFIX + String.valueOf(cidsBean.getProperty("wk_k")));
            lblValMsCdLw.setText(MS_CD_LW_PREFIX + String.valueOf(cidsBean.getProperty("wk_k")));
            labWebsite.setText("<html><a href=\"https://fis-wasser-mv.de/charts/steckbriefe/lw/lw_wk.php?sg="
                        + String.valueOf(cidsBean.getProperty("wk_k")) + "\">"
                        + String.valueOf(cidsBean.getProperty("wk_k")) + "</a></html>");
        } else {
            lblValEuCdLw.setText("");
            lblValMsCdLw.setText("");
            labWebsite.setText("");
        }

        setModel();
    }

    @Override
    public void dispose() {
        dlgImpactCataloge.dispose();
        dlgImpactSrcCataloge.dispose();
        ((DefaultCismapGeometryComboBoxEditor)cbGeom).dispose();
        bindingGroup.unbind();
    }

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
