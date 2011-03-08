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
import Sirius.server.middleware.types.MetaObject;

import java.math.BigDecimal;

import java.util.List;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import de.cismet.cids.custom.util.CidsBeanSupport;
import de.cismet.cids.custom.util.QualityStatusCodeComparator;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.DisposableCidsBeanStore;

import de.cismet.cids.editors.DefaultCustomObjectEditor;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

/**
 * DOCUMENT ME!
 *
 * @author   stefan
 * @version  $Revision$, $Date$
 */
public class WkFgPanSix extends javax.swing.JPanel implements DisposableCidsBeanStore, ListSelectionListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(WkFgPanSix.class);

    //~ Instance fields --------------------------------------------------------

    private final MstTableModel model = new MstTableModel();
    private CidsBean cidsBean;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbGkPcQk;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton jbVorb;
    private javax.swing.JTable jtMstTab1;
    private javax.swing.JLabel lbl90PerzentilAmm;
    private javax.swing.JLabel lbl90PerzentilChlor;
    private javax.swing.JLabel lbl90PerzentilGesN;
    private javax.swing.JLabel lbl90PerzentilNit;
    private javax.swing.JLabel lbl90PerzentilOrth;
    private javax.swing.JLabel lbl90PerzentilPhos;
    private javax.swing.JLabel lblBemerkungPc;
    private javax.swing.JLabel lblGenCond;
    private javax.swing.JLabel lblGenCondBemerkung;
    private javax.swing.JLabel lblGenCondGkQk;
    private javax.swing.JLabel lblGenCondJahr;
    private javax.swing.JLabel lblGenCondMst;
    private javax.swing.JLabel lblGkLawaAmm;
    private javax.swing.JLabel lblGkLawaChlor;
    private javax.swing.JLabel lblGkLawaGesN;
    private javax.swing.JLabel lblGkLawaNit;
    private javax.swing.JLabel lblGkLawaOrth;
    private javax.swing.JLabel lblGkLawaPhos;
    private javax.swing.JLabel lblGkPcMst;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblJahr;
    private javax.swing.JLabel lblLawa;
    private javax.swing.JLabel lblLawa1;
    private javax.swing.JLabel lblLawa2;
    private javax.swing.JLabel lblLawa3;
    private javax.swing.JLabel lblLawa4;
    private javax.swing.JLabel lblLawa5;
    private javax.swing.JLabel lblMittelAmm;
    private javax.swing.JLabel lblMittelChlor;
    private javax.swing.JLabel lblMittelGesN;
    private javax.swing.JLabel lblMittelNit;
    private javax.swing.JLabel lblMittelO2;
    private javax.swing.JLabel lblMittelOrth;
    private javax.swing.JLabel lblMittelPhos;
    private javax.swing.JLabel lblOWertAmm;
    private javax.swing.JLabel lblOWertChlor;
    private javax.swing.JLabel lblOWertO2;
    private javax.swing.JLabel lblOWertOrth;
    private javax.swing.JLabel lblOWertPhos;
    private javax.swing.JLabel lblPhyChem;
    private javax.swing.JLabel lblRakon;
    private javax.swing.JLabel lblRakon1;
    private javax.swing.JLabel lblRakon3;
    private javax.swing.JLabel lblRakon4;
    private javax.swing.JLabel lblRakon5;
    private javax.swing.JLabel lblSpace;
    private javax.swing.JPanel panAllgemein;
    private javax.swing.JPanel panAmm;
    private javax.swing.JPanel panChlor;
    private javax.swing.JPanel panGesN;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JPanel panNit;
    private javax.swing.JPanel panO2;
    private javax.swing.JPanel panOrtho;
    private javax.swing.JPanel panPhos;
    private javax.swing.JTextField txtJahrPcqk;
    private javax.swing.JTextField txtMst;
    private javax.swing.JTextField txtPcQkBemerkung;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WkFgPanOne.
     */
    public WkFgPanSix() {
        initComponents();
        jScrollPane1.getViewport().setOpaque(false);
        jtMstTab1.getSelectionModel().addListSelectionListener(this);
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

        panInfo = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        panInfoContent = new javax.swing.JPanel();
        lblSpace = new javax.swing.JLabel();
        panAllgemein = new javax.swing.JPanel();
        lblGenCond = new javax.swing.JLabel();
        cbGkPcQk = new de.cismet.cids.editors.DefaultBindableReferenceCombo(new QualityStatusCodeComparator());
        lblGenCondJahr = new javax.swing.JLabel();
        lblGenCondBemerkung = new javax.swing.JLabel();
        lblGenCondMst = new javax.swing.JLabel();
        txtJahrPcqk = new javax.swing.JTextField();
        txtMst = new javax.swing.JTextField();
        txtPcQkBemerkung = new javax.swing.JTextField();
        lblGenCondGkQk = new javax.swing.JLabel();
        jbVorb = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtMstTab1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        panOrtho = new javax.swing.JPanel();
        lblLawa = new javax.swing.JLabel();
        lblRakon = new javax.swing.JLabel();
        lbl90PerzentilOrth = new javax.swing.JLabel();
        lblGkLawaOrth = new javax.swing.JLabel();
        lblOWertOrth = new javax.swing.JLabel();
        lblMittelOrth = new javax.swing.JLabel();
        panAmm = new javax.swing.JPanel();
        lblLawa1 = new javax.swing.JLabel();
        lblRakon1 = new javax.swing.JLabel();
        lbl90PerzentilAmm = new javax.swing.JLabel();
        lblGkLawaAmm = new javax.swing.JLabel();
        lblOWertAmm = new javax.swing.JLabel();
        lblMittelAmm = new javax.swing.JLabel();
        panGesN = new javax.swing.JPanel();
        lblLawa2 = new javax.swing.JLabel();
        lbl90PerzentilGesN = new javax.swing.JLabel();
        lblGkLawaGesN = new javax.swing.JLabel();
        lblMittelGesN = new javax.swing.JLabel();
        panPhos = new javax.swing.JPanel();
        lblLawa3 = new javax.swing.JLabel();
        lblRakon3 = new javax.swing.JLabel();
        lbl90PerzentilPhos = new javax.swing.JLabel();
        lblGkLawaPhos = new javax.swing.JLabel();
        lblOWertPhos = new javax.swing.JLabel();
        lblMittelPhos = new javax.swing.JLabel();
        panNit = new javax.swing.JPanel();
        lblLawa4 = new javax.swing.JLabel();
        lbl90PerzentilNit = new javax.swing.JLabel();
        lblGkLawaNit = new javax.swing.JLabel();
        lblMittelNit = new javax.swing.JLabel();
        panChlor = new javax.swing.JPanel();
        lblLawa5 = new javax.swing.JLabel();
        lblRakon5 = new javax.swing.JLabel();
        lbl90PerzentilChlor = new javax.swing.JLabel();
        lblGkLawaChlor = new javax.swing.JLabel();
        lblOWertChlor = new javax.swing.JLabel();
        lblMittelChlor = new javax.swing.JLabel();
        panO2 = new javax.swing.JPanel();
        lblRakon4 = new javax.swing.JLabel();
        lblOWertO2 = new javax.swing.JLabel();
        lblMittelO2 = new javax.swing.JLabel();
        lblPhyChem = new javax.swing.JLabel();
        lblGkPcMst = new javax.swing.JLabel();
        lblBemerkungPc = new javax.swing.JLabel();
        lblJahr = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(910, 650));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(910, 650));
        setLayout(new java.awt.BorderLayout());

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setLayout(new java.awt.FlowLayout());

        lblHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading.setText("Allgemeine Physikalisch-chemische Qualitätskomponenten");
        panHeadInfo.add(lblHeading);

        panInfo.add(panHeadInfo, java.awt.BorderLayout.NORTH);

        jScrollPane1.setBorder(null);
        jScrollPane1.setOpaque(false);

        panInfoContent.setMaximumSize(new java.awt.Dimension(777, 400));
        panInfoContent.setMinimumSize(new java.awt.Dimension(777, 400));
        panInfoContent.setOpaque(false);
        panInfoContent.setPreferredSize(new java.awt.Dimension(777, 400));
        panInfoContent.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.weighty = 1.0;
        panInfoContent.add(lblSpace, gridBagConstraints);

        panAllgemein.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        panAllgemein.setOpaque(false);
        panAllgemein.setLayout(new java.awt.GridBagLayout());

        lblGenCond.setText(org.openide.util.NbBundle.getMessage(WkFgPanSix.class, "WkFgPanSix.lblGenCond.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAllgemein.add(lblGenCond, gridBagConstraints);

        cbGkPcQk.setMaximumSize(new java.awt.Dimension(200, 20));
        cbGkPcQk.setMinimumSize(new java.awt.Dimension(200, 20));
        cbGkPcQk.setPreferredSize(new java.awt.Dimension(200, 20));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_pc_qk}"), cbGkPcQk, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cbGkPcQk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbGkPcQkActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAllgemein.add(cbGkPcQk, gridBagConstraints);

        lblGenCondJahr.setText(org.openide.util.NbBundle.getMessage(WkFgPanSix.class, "WkFgPanSix.lblGenCondJahr.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAllgemein.add(lblGenCondJahr, gridBagConstraints);

        lblGenCondBemerkung.setText(org.openide.util.NbBundle.getMessage(WkFgPanSix.class, "WkFgPanSix.lblGenCondBemerkung.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAllgemein.add(lblGenCondBemerkung, gridBagConstraints);

        lblGenCondMst.setText(org.openide.util.NbBundle.getMessage(WkFgPanSix.class, "WkFgPanSix.lblGenCondMst.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAllgemein.add(lblGenCondMst, gridBagConstraints);

        txtJahrPcqk.setMinimumSize(new java.awt.Dimension(100, 20));
        txtJahrPcqk.setPreferredSize(new java.awt.Dimension(100, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.jahr_pcqk}"), txtJahrPcqk, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAllgemein.add(txtJahrPcqk, gridBagConstraints);

        txtMst.setMinimumSize(new java.awt.Dimension(100, 20));
        txtMst.setPreferredSize(new java.awt.Dimension(100, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.mst}"), txtMst, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAllgemein.add(txtMst, gridBagConstraints);

        txtPcQkBemerkung.setMinimumSize(new java.awt.Dimension(200, 20));
        txtPcQkBemerkung.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.pc_qk_bemerkung}"), txtPcQkBemerkung, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAllgemein.add(txtPcQkBemerkung, gridBagConstraints);

        lblGenCondGkQk.setText(org.openide.util.NbBundle.getMessage(WkFgPanSix.class, "WkFgPanSix.lblGenCondGkQk.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAllgemein.add(lblGenCondGkQk, gridBagConstraints);

        jbVorb.setText(org.openide.util.NbBundle.getMessage(WkFgPanSix.class, "WkFgPanSix.jbVorb.text")); // NOI18N
        jbVorb.setMinimumSize(new java.awt.Dimension(85, 20));
        jbVorb.setPreferredSize(new java.awt.Dimension(85, 20));
        jbVorb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbVorbActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAllgemein.add(jbVorb, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 5, 5);
        panInfoContent.add(panAllgemein, gridBagConstraints);

        jScrollPane3.setMinimumSize(new java.awt.Dimension(800, 100));
        jScrollPane3.setOpaque(false);
        jScrollPane3.setPreferredSize(new java.awt.Dimension(800, 100));

        jtMstTab1.setModel(model);
        jScrollPane3.setViewportView(jtMstTab1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(25, 10, 15, 10);
        panInfoContent.add(jScrollPane3, gridBagConstraints);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        panOrtho.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Orthophosphat"));
        panOrtho.setOpaque(false);
        panOrtho.setLayout(new java.awt.GridBagLayout());

        lblLawa.setText(org.openide.util.NbBundle.getMessage(WkFgPanSix.class, "WkFgPanSix.lblLawa.text")); // NOI18N
        lblLawa.setMinimumSize(new java.awt.Dimension(45, 20));
        lblLawa.setPreferredSize(new java.awt.Dimension(45, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panOrtho.add(lblLawa, gridBagConstraints);

        lblRakon.setText(org.openide.util.NbBundle.getMessage(WkFgPanSix.class, "WkFgPanSix.lblRakon.text")); // NOI18N
        lblRakon.setMinimumSize(new java.awt.Dimension(45, 20));
        lblRakon.setPreferredSize(new java.awt.Dimension(45, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panOrtho.add(lblRakon, gridBagConstraints);

        lbl90PerzentilOrth.setToolTipText(org.openide.util.NbBundle.getMessage(WkFgPanSix.class, "WkFgPanSix.lbl90Perzentil.toolTipText")); // NOI18N
        lbl90PerzentilOrth.setMinimumSize(new java.awt.Dimension(110, 20));
        lbl90PerzentilOrth.setPreferredSize(new java.awt.Dimension(110, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panOrtho.add(lbl90PerzentilOrth, gridBagConstraints);

        lblGkLawaOrth.setToolTipText(org.openide.util.NbBundle.getMessage(WkFgPanSix.class, "WkFgPanSix.lblGkLawa.textTipText")); // NOI18N
        lblGkLawaOrth.setMinimumSize(new java.awt.Dimension(110, 20));
        lblGkLawaOrth.setPreferredSize(new java.awt.Dimension(110, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panOrtho.add(lblGkLawaOrth, gridBagConstraints);

        lblOWertOrth.setToolTipText(org.openide.util.NbBundle.getMessage(WkFgPanSix.class, "WkFgPanSix.lblOWert.toolTipText")); // NOI18N
        lblOWertOrth.setMinimumSize(new java.awt.Dimension(110, 20));
        lblOWertOrth.setPreferredSize(new java.awt.Dimension(110, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panOrtho.add(lblOWertOrth, gridBagConstraints);

        lblMittelOrth.setToolTipText(org.openide.util.NbBundle.getMessage(WkFgPanSix.class, "WkFgPanSix.lblMittel.toolTipText")); // NOI18N
        lblMittelOrth.setMinimumSize(new java.awt.Dimension(110, 20));
        lblMittelOrth.setPreferredSize(new java.awt.Dimension(110, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panOrtho.add(lblMittelOrth, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel1.add(panOrtho, gridBagConstraints);

        panAmm.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Ammonium-N"));
        panAmm.setOpaque(false);
        panAmm.setLayout(new java.awt.GridBagLayout());

        lblLawa1.setText(org.openide.util.NbBundle.getMessage(WkFgPanSix.class, "WkFgPanSix.lblLawa.text")); // NOI18N
        lblLawa1.setMinimumSize(new java.awt.Dimension(45, 20));
        lblLawa1.setPreferredSize(new java.awt.Dimension(45, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAmm.add(lblLawa1, gridBagConstraints);

        lblRakon1.setText(org.openide.util.NbBundle.getMessage(WkFgPanSix.class, "WkFgPanSix.lblRakon.text")); // NOI18N
        lblRakon1.setMinimumSize(new java.awt.Dimension(45, 20));
        lblRakon1.setPreferredSize(new java.awt.Dimension(45, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAmm.add(lblRakon1, gridBagConstraints);

        lbl90PerzentilAmm.setToolTipText(org.openide.util.NbBundle.getMessage(WkFgPanSix.class, "WkFgPanSix.lbl90Perzentil.toolTipText")); // NOI18N
        lbl90PerzentilAmm.setMinimumSize(new java.awt.Dimension(110, 20));
        lbl90PerzentilAmm.setPreferredSize(new java.awt.Dimension(110, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAmm.add(lbl90PerzentilAmm, gridBagConstraints);

        lblGkLawaAmm.setToolTipText(org.openide.util.NbBundle.getMessage(WkFgPanSix.class, "WkFgPanSix.lblGkLawa.textTipText")); // NOI18N
        lblGkLawaAmm.setMinimumSize(new java.awt.Dimension(110, 20));
        lblGkLawaAmm.setPreferredSize(new java.awt.Dimension(110, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAmm.add(lblGkLawaAmm, gridBagConstraints);

        lblOWertAmm.setToolTipText(org.openide.util.NbBundle.getMessage(WkFgPanSix.class, "WkFgPanSix.lblOWert.toolTipText")); // NOI18N
        lblOWertAmm.setMinimumSize(new java.awt.Dimension(110, 20));
        lblOWertAmm.setPreferredSize(new java.awt.Dimension(110, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAmm.add(lblOWertAmm, gridBagConstraints);

        lblMittelAmm.setToolTipText(org.openide.util.NbBundle.getMessage(WkFgPanSix.class, "WkFgPanSix.lblMittel.toolTipText")); // NOI18N
        lblMittelAmm.setMinimumSize(new java.awt.Dimension(110, 20));
        lblMittelAmm.setPreferredSize(new java.awt.Dimension(110, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAmm.add(lblMittelAmm, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel1.add(panAmm, gridBagConstraints);

        panGesN.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Gesamt-N"));
        panGesN.setOpaque(false);
        panGesN.setLayout(new java.awt.GridBagLayout());

        lblLawa2.setText(org.openide.util.NbBundle.getMessage(WkFgPanSix.class, "WkFgPanSix.lblLawa.text")); // NOI18N
        lblLawa2.setMinimumSize(new java.awt.Dimension(45, 20));
        lblLawa2.setPreferredSize(new java.awt.Dimension(45, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panGesN.add(lblLawa2, gridBagConstraints);

        lbl90PerzentilGesN.setToolTipText(org.openide.util.NbBundle.getMessage(WkFgPanSix.class, "WkFgPanSix.lbl90Perzentil.toolTipText")); // NOI18N
        lbl90PerzentilGesN.setMinimumSize(new java.awt.Dimension(110, 20));
        lbl90PerzentilGesN.setPreferredSize(new java.awt.Dimension(110, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panGesN.add(lbl90PerzentilGesN, gridBagConstraints);

        lblGkLawaGesN.setToolTipText(org.openide.util.NbBundle.getMessage(WkFgPanSix.class, "WkFgPanSix.lblGkLawa.textTipText")); // NOI18N
        lblGkLawaGesN.setMinimumSize(new java.awt.Dimension(110, 20));
        lblGkLawaGesN.setPreferredSize(new java.awt.Dimension(110, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panGesN.add(lblGkLawaGesN, gridBagConstraints);

        lblMittelGesN.setToolTipText(org.openide.util.NbBundle.getMessage(WkFgPanSix.class, "WkFgPanSix.lblMittel.toolTipText")); // NOI18N
        lblMittelGesN.setMinimumSize(new java.awt.Dimension(120, 20));
        lblMittelGesN.setPreferredSize(new java.awt.Dimension(120, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panGesN.add(lblMittelGesN, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel1.add(panGesN, gridBagConstraints);

        panPhos.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Gesamtphosphat"));
        panPhos.setOpaque(false);
        panPhos.setLayout(new java.awt.GridBagLayout());

        lblLawa3.setText(org.openide.util.NbBundle.getMessage(WkFgPanSix.class, "WkFgPanSix.lblLawa.text")); // NOI18N
        lblLawa3.setMinimumSize(new java.awt.Dimension(45, 20));
        lblLawa3.setPreferredSize(new java.awt.Dimension(45, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panPhos.add(lblLawa3, gridBagConstraints);

        lblRakon3.setText(org.openide.util.NbBundle.getMessage(WkFgPanSix.class, "WkFgPanSix.lblRakon.text")); // NOI18N
        lblRakon3.setMinimumSize(new java.awt.Dimension(45, 20));
        lblRakon3.setPreferredSize(new java.awt.Dimension(45, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panPhos.add(lblRakon3, gridBagConstraints);

        lbl90PerzentilPhos.setToolTipText(org.openide.util.NbBundle.getMessage(WkFgPanSix.class, "WkFgPanSix.lbl90Perzentil.toolTipText")); // NOI18N
        lbl90PerzentilPhos.setMinimumSize(new java.awt.Dimension(110, 20));
        lbl90PerzentilPhos.setPreferredSize(new java.awt.Dimension(110, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panPhos.add(lbl90PerzentilPhos, gridBagConstraints);

        lblGkLawaPhos.setToolTipText(org.openide.util.NbBundle.getMessage(WkFgPanSix.class, "WkFgPanSix.lblGkLawa.textTipText")); // NOI18N
        lblGkLawaPhos.setMinimumSize(new java.awt.Dimension(110, 20));
        lblGkLawaPhos.setPreferredSize(new java.awt.Dimension(110, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panPhos.add(lblGkLawaPhos, gridBagConstraints);

        lblOWertPhos.setToolTipText(org.openide.util.NbBundle.getMessage(WkFgPanSix.class, "WkFgPanSix.lblOWert.toolTipText")); // NOI18N
        lblOWertPhos.setMinimumSize(new java.awt.Dimension(110, 20));
        lblOWertPhos.setPreferredSize(new java.awt.Dimension(110, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panPhos.add(lblOWertPhos, gridBagConstraints);

        lblMittelPhos.setToolTipText(org.openide.util.NbBundle.getMessage(WkFgPanSix.class, "WkFgPanSix.lblMittel.toolTipText")); // NOI18N
        lblMittelPhos.setMinimumSize(new java.awt.Dimension(110, 20));
        lblMittelPhos.setPreferredSize(new java.awt.Dimension(110, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panPhos.add(lblMittelPhos, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel1.add(panPhos, gridBagConstraints);

        panNit.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Nitrat-N"));
        panNit.setOpaque(false);
        panNit.setLayout(new java.awt.GridBagLayout());

        lblLawa4.setText(org.openide.util.NbBundle.getMessage(WkFgPanSix.class, "WkFgPanSix.lblLawa.text")); // NOI18N
        lblLawa4.setMinimumSize(new java.awt.Dimension(45, 20));
        lblLawa4.setPreferredSize(new java.awt.Dimension(45, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panNit.add(lblLawa4, gridBagConstraints);

        lbl90PerzentilNit.setToolTipText(org.openide.util.NbBundle.getMessage(WkFgPanSix.class, "WkFgPanSix.lbl90Perzentil.toolTipText")); // NOI18N
        lbl90PerzentilNit.setMinimumSize(new java.awt.Dimension(110, 20));
        lbl90PerzentilNit.setPreferredSize(new java.awt.Dimension(110, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panNit.add(lbl90PerzentilNit, gridBagConstraints);

        lblGkLawaNit.setToolTipText(org.openide.util.NbBundle.getMessage(WkFgPanSix.class, "WkFgPanSix.lblGkLawa.textTipText")); // NOI18N
        lblGkLawaNit.setMinimumSize(new java.awt.Dimension(110, 20));
        lblGkLawaNit.setPreferredSize(new java.awt.Dimension(110, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panNit.add(lblGkLawaNit, gridBagConstraints);

        lblMittelNit.setToolTipText(org.openide.util.NbBundle.getMessage(WkFgPanSix.class, "WkFgPanSix.lblMittel.toolTipText")); // NOI18N
        lblMittelNit.setMinimumSize(new java.awt.Dimension(110, 20));
        lblMittelNit.setPreferredSize(new java.awt.Dimension(110, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panNit.add(lblMittelNit, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel1.add(panNit, gridBagConstraints);

        panChlor.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Chlorid"));
        panChlor.setOpaque(false);
        panChlor.setLayout(new java.awt.GridBagLayout());

        lblLawa5.setText(org.openide.util.NbBundle.getMessage(WkFgPanSix.class, "WkFgPanSix.lblLawa.text")); // NOI18N
        lblLawa5.setMinimumSize(new java.awt.Dimension(45, 20));
        lblLawa5.setPreferredSize(new java.awt.Dimension(45, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panChlor.add(lblLawa5, gridBagConstraints);

        lblRakon5.setText(org.openide.util.NbBundle.getMessage(WkFgPanSix.class, "WkFgPanSix.lblRakon.text")); // NOI18N
        lblRakon5.setMinimumSize(new java.awt.Dimension(45, 20));
        lblRakon5.setPreferredSize(new java.awt.Dimension(45, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panChlor.add(lblRakon5, gridBagConstraints);

        lbl90PerzentilChlor.setToolTipText(org.openide.util.NbBundle.getMessage(WkFgPanSix.class, "WkFgPanSix.lbl90Perzentil.toolTipText")); // NOI18N
        lbl90PerzentilChlor.setMinimumSize(new java.awt.Dimension(110, 20));
        lbl90PerzentilChlor.setPreferredSize(new java.awt.Dimension(110, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panChlor.add(lbl90PerzentilChlor, gridBagConstraints);

        lblGkLawaChlor.setToolTipText(org.openide.util.NbBundle.getMessage(WkFgPanSix.class, "WkFgPanSix.lblGkLawa.textTipText")); // NOI18N
        lblGkLawaChlor.setMinimumSize(new java.awt.Dimension(110, 20));
        lblGkLawaChlor.setPreferredSize(new java.awt.Dimension(110, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panChlor.add(lblGkLawaChlor, gridBagConstraints);

        lblOWertChlor.setToolTipText(org.openide.util.NbBundle.getMessage(WkFgPanSix.class, "WkFgPanSix.lblOWert.toolTipText")); // NOI18N
        lblOWertChlor.setMinimumSize(new java.awt.Dimension(110, 20));
        lblOWertChlor.setPreferredSize(new java.awt.Dimension(110, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panChlor.add(lblOWertChlor, gridBagConstraints);

        lblMittelChlor.setToolTipText(org.openide.util.NbBundle.getMessage(WkFgPanSix.class, "WkFgPanSix.lblMittel.toolTipText")); // NOI18N
        lblMittelChlor.setMinimumSize(new java.awt.Dimension(110, 20));
        lblMittelChlor.setPreferredSize(new java.awt.Dimension(110, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panChlor.add(lblMittelChlor, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel1.add(panChlor, gridBagConstraints);

        panO2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Sauerstoff"));
        panO2.setOpaque(false);
        panO2.setLayout(new java.awt.GridBagLayout());

        lblRakon4.setText(org.openide.util.NbBundle.getMessage(WkFgPanSix.class, "WkFgPanSix.lblRakon.text")); // NOI18N
        lblRakon4.setMinimumSize(new java.awt.Dimension(45, 20));
        lblRakon4.setPreferredSize(new java.awt.Dimension(45, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panO2.add(lblRakon4, gridBagConstraints);

        lblOWertO2.setToolTipText(org.openide.util.NbBundle.getMessage(WkFgPanSix.class, "WkFgPanSix.lblOWert.toolTipText")); // NOI18N
        lblOWertO2.setMinimumSize(new java.awt.Dimension(110, 20));
        lblOWertO2.setPreferredSize(new java.awt.Dimension(110, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panO2.add(lblOWertO2, gridBagConstraints);

        lblMittelO2.setToolTipText(org.openide.util.NbBundle.getMessage(WkFgPanSix.class, "WkFgPanSix.lblMittel.toolTipText")); // NOI18N
        lblMittelO2.setMinimumSize(new java.awt.Dimension(110, 20));
        lblMittelO2.setPreferredSize(new java.awt.Dimension(110, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panO2.add(lblMittelO2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel1.add(panO2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 5, 10);
        panInfoContent.add(jPanel1, gridBagConstraints);

        lblPhyChem.setText(org.openide.util.NbBundle.getMessage(WkFgPanSix.class, "WkFgPanSix.lblPhyChem.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent.add(lblPhyChem, gridBagConstraints);

        lblGkPcMst.setMinimumSize(new java.awt.Dimension(100, 20));
        lblGkPcMst.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(lblGkPcMst, gridBagConstraints);

        lblBemerkungPc.setMinimumSize(new java.awt.Dimension(250, 20));
        lblBemerkungPc.setPreferredSize(new java.awt.Dimension(250, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(lblBemerkungPc, gridBagConstraints);

        lblJahr.setMinimumSize(new java.awt.Dimension(100, 20));
        lblJahr.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent.add(lblJahr, gridBagConstraints);

        jScrollPane1.setViewportView(panInfoContent);

        panInfo.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        add(panInfo, java.awt.BorderLayout.CENTER);

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbGkPcQkActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbGkPcQkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbGkPcQkActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jbVorbActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbVorbActionPerformed
        final CidsBean measure = getTheCurrentlyWorstMeasure();

        if (measure != null) {
            Object o = measure.getProperty("gk_pc_mst");

            if (o != null) {
                cbGkPcQk.setSelectedItem(o);
            } else {
                cbGkPcQk.setSelectedIndex(-1);
            }

            o = measure.getProperty("messjahr");

            if (o != null) {
                txtJahrPcqk.setText(o.toString());
            } else {
                txtJahrPcqk.setText("");
            }

            o = measure.getProperty("bemerkung_pc");
            if (o != null) {
                txtPcQkBemerkung.setText(o.toString());
            } else {
                txtPcQkBemerkung.setText("");
            }

            o = measure.getProperty("messstelle.messstelle");
            if (o != null) {
                txtMst.setText(o.toString());
            } else {
                txtMst.setText("");
            }
        }
    }//GEN-LAST:event_jbVorbActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private CidsBean getTheCurrentlyWorstMeasure() {
        final List<CidsBean> beans = model.getData();
        int latestYear = 0;
        int worstValue = 0;
        CidsBean measure = null;

        for (final CidsBean cbean : beans) {
            final Integer year = (Integer)cbean.getProperty("messjahr");
            if (year != null) {
                if (latestYear == 0) {
                    latestYear = year;
                    measure = cbean;
                    final CidsBean tmp = (CidsBean)cbean.getProperty("gk_pc_mst");
                    if (cidsBean != null) {
                        try {
                            worstValue = Integer.parseInt((String)tmp.getProperty("value"));
                        } catch (final NumberFormatException e) {
                            LOG.error("Field value does not contain a number", e);
                        }
                    }
                }
            } else {
                if (latestYear == year) {
                    if (cidsBean != null) {
                        try {
                            final CidsBean qualityTmp = (CidsBean)cbean.getProperty("gk_pc_mst");
                            if (qualityTmp != null) {
                                final int valTmp = Integer.parseInt((String)qualityTmp.getProperty("value"));

                                if (valTmp > worstValue) {
                                    worstValue = valTmp;
                                    measure = cbean;
                                }
                            }
                        } catch (final NumberFormatException e) {
                            LOG.error("Field value does not contain a number", e);
                        }
                    }
                } else {
                    break;
                }
            }
        }

        return measure;
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
            model.fireTableDataChanged();

            new Thread(new Runnable() {

                    @Override
                    public void run() {
                        model.refreshData(cidsBean);
                    }
                }).start();
        }
    }

    @Override
    public void dispose() {
        bindingGroup.unbind();
    }

    @Override
    public void valueChanged(final ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            if (e.getFirstIndex() > -1) {
                final CidsBean sbean = model.getData().get(e.getFirstIndex());
                setField(sbean, lblGkPcMst, "gk_pc_mst");
                setField(sbean, lblBemerkungPc, "bemerkung_pc");
                setField(sbean, lblJahr, "messjahr");
                setField(sbean, lbl90PerzentilAmm, "nh4_90_perzentil");
                setField(sbean, lbl90PerzentilChlor, "cl_90_perzentil");
                setField(sbean, lbl90PerzentilGesN, "ges_n_90_perzentil");
                setField(sbean, lbl90PerzentilNit, "no3_n_90_perzentil");
                setField(sbean, lbl90PerzentilOrth, "opo4_90_perzentil");
                setField(sbean, lbl90PerzentilPhos, "ges_p_90_perzentil");
                setField(sbean, lblGkLawaAmm, "nh4_gk_lawa");
                setField(sbean, lblGkLawaChlor, "cl_gk_lawa");
                setField(sbean, lblGkLawaGesN, "ges_n_gk_lawa");
                setField(sbean, lblGkLawaNit, "no3_n_gk_lawa");
                setField(sbean, lblGkLawaOrth, "opo4_gk_lawa");
                setField(sbean, lblGkLawaPhos, "ges_p_gk_lawa");
                setField(sbean, lblOWertAmm, "nh4_owert_rakon");
                setField(sbean, lblOWertChlor, "cl_owert_rakon");
                setField(sbean, lblOWertOrth, "opo4_owert_rakon");
                setField(sbean, lblOWertPhos, "ges_p_owert_rakon");
                setField(sbean, lblOWertO2, "o2_owert_rakon");
                setField(sbean, lblMittelAmm, "nh4_mittelwert");
                setField(sbean, lblMittelChlor, "cl_mittelwert");
                setField(sbean, lblMittelGesN, "ges_n_mittelwert");
                setField(sbean, lblMittelNit, "no3_n_mittelwert");
                setField(sbean, lblMittelOrth, "opo4_mittelwert");
                setField(sbean, lblMittelPhos, "ges_p_mittelwert");
                setField(sbean, lblMittelO2, "o2_mittelwert");
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  sbean     DOCUMENT ME!
     * @param  lab       DOCUMENT ME!
     * @param  property  DOCUMENT ME!
     */
    private void setField(final CidsBean sbean, final JLabel lab, final String property) {
        final Object val = sbean.getProperty(property);

        if (val != null) {
            lab.setText(val.toString());
        } else {
            lab.setText("");
        }
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public static class MstTableModel extends AbstractTableModel {

        //~ Static fields/initializers -----------------------------------------

        private static final MetaClass MC = ClassCacheMultiple.getMetaClass(
                CidsBeanSupport.DOMAIN_NAME,
                "chemie_mst_messungen");

        //~ Instance fields ----------------------------------------------------

        private String[][] header = {
                { "MST", "messstelle.messstelle" },    // NOI18N
                { "WK", "messstelle.wk_fg.wk_k" },     // NOI18N
                { "Jahr", "messjahr" },                // NOI18N
                { "GK PC MST", "gk_pc_mst" },          // NOI18N
                { "GK GW-Überschreitung?", "" },       // NOI18N
                { "Bemerkung PC Mst", "bemerkung_pc" } // NOI18N
            };
        private List<CidsBean> data = new Vector<CidsBean>();
        private boolean isInitialised = false;

        //~ Methods ------------------------------------------------------------

        @Override
        public int getRowCount() {
            if (!isInitialised) {
                return 1;
            } else {
                return data.size();
            }
        }

        @Override
        public int getColumnCount() {
            return header.length;
        }

        @Override
        public String getColumnName(final int columnIndex) {
            if (columnIndex < header.length) {
                return header[columnIndex][0];
            } else {
                return "";
            }
        }

        @Override
        public Class<?> getColumnClass(final int columnIndex) {
            return Object.class;
        }

        @Override
        public boolean isCellEditable(final int rowIndex, final int columnIndex) {
            return false;
        }

        @Override
        public Object getValueAt(final int rowIndex, final int columnIndex) {
            if (!isInitialised) {
                return "lade ...";
            } else if ((rowIndex < data.size()) && (columnIndex < header.length)) {
                if (columnIndex == 4) {
                    // column OW-Überschreitung?
                    return (isLimitExceeded(data.get(rowIndex)) ? "ja" : "nein");
                } else {
                    final Object value = data.get(rowIndex).getProperty(header[columnIndex][1]);
                    if (value != null) {
                        if (value instanceof CidsBean) {
                            return String.valueOf(((CidsBean)value).getProperty("name")); // NOI18N
                        } else {
                            return String.valueOf(value);
                        }
                    } else {
                        return "-";                                                       // NOI18N
                    }
                }
            } else {
                return "";                                                                // NOI18N
            }
        }

        /**
         * DOCUMENT ME!
         *
         * @param   cbean  DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        private boolean isLimitExceeded(final CidsBean cbean) {
            final String[][] OW = {
                    { "opo4_owert_rakon", "opo4_mittelwert" },
                    { "ges_p_owert_rakon", "ges_p_mittelwert" },
                    { "nh4_owert_rakon", "nh4_mittelwert" },
                    { "no3_n_owert_rakon", "no3_n_mittelwert" },
                    { "ges_n_owert_rakon", "ges_n_mittelwert" },
                    { "cl_owert_rakon", "cl_mittelwert" },
                    { "o2_owert_rakon", "o2_mittelwert" }
                };

            for (int i = 0; i < OW.length; ++i) {
                final BigDecimal ow = (BigDecimal)cbean.getProperty(OW[i][0]);
                final BigDecimal val = (BigDecimal)cbean.getProperty(OW[i][1]);
                if ((ow != null) && (val != null) && (ow.compareTo(val) == -1)) {
                    return true;
                }
            }

            return false;
        }

        @Override
        public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
            // nothing to do, because it is not allowed to modify columns
        }

        /**
         * DOCUMENT ME!
         *
         * @param  cidsBean  DOCUMENT ME!
         */
        public void refreshData(final CidsBean cidsBean) {
            try {
                data.clear();
                String query = "select " + MC.getID() + ", m." + MC.getPrimaryKey() + " from " + MC.getTableName(); // NOI18N
                query += " m, chemie_mst_stammdaten s";                                                             // NOI18N
                query += " WHERE m.messstelle = s.id AND s.wk_fg = " + cidsBean.getProperty("id");                  // NOI18N
                query += " order by messjahr desc";                                                                 // NOI18N

                final MetaObject[] metaObjects = SessionManager.getProxy().getMetaObjectByQuery(query, 0);

                for (final MetaObject mo : metaObjects) {
                    data.add(mo.getBean());
                }
                isInitialised = true;
                fireTableDataChanged();
            } catch (final ConnectionException e) {
                LOG.error("Error while trying to receive measurements.", e); // NOI18N
            }
        }

        /**
         * DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        public List<CidsBean> getData() {
            return data;
        }

        /**
         * DOCUMENT ME!
         */
        public void clearModel() {
            data.clear();
        }
    }
}
