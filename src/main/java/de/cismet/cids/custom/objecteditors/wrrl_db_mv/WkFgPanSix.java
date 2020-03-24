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

import java.awt.Component;

import java.math.BigDecimal;

import java.util.List;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.util.QualityStatusCodeComparator;
import de.cismet.cids.custom.wrrl_db_mv.util.RendererTools;

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
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbGkTemp;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbPhosphor;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbSaeure;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbSalz;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbSauerstoff;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbStickstoff;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.ChemieMstMessungenPanOne chemieMstMessungenPanOne1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton jbVorb;
    private javax.swing.JTable jtMstTab1;
    private javax.swing.JLabel lblGenCond;
    private javax.swing.JLabel lblGenCond1;
    private javax.swing.JLabel lblGenCond2;
    private javax.swing.JLabel lblGenCond3;
    private javax.swing.JLabel lblGenCond4;
    private javax.swing.JLabel lblGenCond5;
    private javax.swing.JLabel lblGenCond6;
    private javax.swing.JLabel lblGenCondBemerkung;
    private javax.swing.JLabel lblGenCondGkQk;
    private javax.swing.JLabel lblGenCondJahr;
    private javax.swing.JLabel lblGenCondMst;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblSpace;
    private javax.swing.JPanel panAllgemein;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JTextField txtJahrPcqk;
    private javax.swing.JTextField txtJahrPhosphor;
    private javax.swing.JTextField txtJahrSaeure;
    private javax.swing.JTextField txtJahrSalz;
    private javax.swing.JTextField txtJahrSauerstoff;
    private javax.swing.JTextField txtJahrStickstoff;
    private javax.swing.JTextField txtJahrTemp;
    private javax.swing.JTextField txtMst;
    private javax.swing.JTextField txtMstPhosphor;
    private javax.swing.JTextField txtMstSaeure;
    private javax.swing.JTextField txtMstSalzgehalt;
    private javax.swing.JTextField txtMstSauerstoff;
    private javax.swing.JTextField txtMstStickstoff;
    private javax.swing.JTextField txtMstTemp;
    private javax.swing.JTextField txtPcQkBemerkung;
    private javax.swing.JTextField txtPhosphorBemerkung;
    private javax.swing.JTextField txtSaeureBemerkung;
    private javax.swing.JTextField txtSalzBemerkung;
    private javax.swing.JTextField txtSauerstoffBemerkung;
    private javax.swing.JTextField txtStickstoffBemerkung;
    private javax.swing.JTextField txtTempBemerkung;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WkFgPanOne.
     */
    public WkFgPanSix() {
        this(false);
    }

    /**
     * Creates a new WkFgPanSix object.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    WkFgPanSix(final boolean readOnly) {
        initComponents();
        jtMstTab1.setDefaultRenderer(String.class, new DefaultTableCellRenderer() {

                @Override
                public Component getTableCellRendererComponent(final JTable table,
                        final Object value,
                        final boolean isSelected,
                        final boolean hasFocus,
                        final int row,
                        final int column) {
                    final Component c = super.getTableCellRendererComponent(
                            table,
                            value,
                            isSelected,
                            hasFocus,
                            row,
                            column); // To change body of generated methods, choose Tools | Templates.

                    if ((c instanceof JLabel)) {
                        if ((value instanceof String) && (((String)value).length() > 10)) {
                            ((JLabel)c).setToolTipText(String.valueOf(value));
                        } else {
                            ((JLabel)c).setToolTipText(null);
                        }
                    }

                    return c;
                }
            });

        chemieMstMessungenPanOne1.setCidsBean(null);
        jScrollPane1.getViewport().setOpaque(false);
        jtMstTab1.getSelectionModel().addListSelectionListener(this);

        if (readOnly) {
            RendererTools.makeReadOnly(cbGkPcQk);
            RendererTools.makeReadOnly(cbGkTemp);
            RendererTools.makeReadOnly(cbPhosphor);
            RendererTools.makeReadOnly(cbSaeure);
            RendererTools.makeReadOnly(cbSalz);
            RendererTools.makeReadOnly(cbSauerstoff);
            RendererTools.makeReadOnly(cbStickstoff);
            RendererTools.makeReadOnly(txtJahrPcqk);
            RendererTools.makeReadOnly(txtJahrPhosphor);
            RendererTools.makeReadOnly(txtJahrSaeure);
            RendererTools.makeReadOnly(txtJahrSalz);
            RendererTools.makeReadOnly(txtJahrSauerstoff);
            RendererTools.makeReadOnly(txtJahrStickstoff);
            RendererTools.makeReadOnly(txtJahrTemp);
            RendererTools.makeReadOnly(txtMst);
            RendererTools.makeReadOnly(txtMstTemp);
            RendererTools.makeReadOnly(txtMstSauerstoff);
            RendererTools.makeReadOnly(txtMstSalzgehalt);
            RendererTools.makeReadOnly(txtMstSaeure);
            RendererTools.makeReadOnly(txtMstStickstoff);
            RendererTools.makeReadOnly(txtMstPhosphor);
            RendererTools.makeReadOnly(txtPcQkBemerkung);
            RendererTools.makeReadOnly(txtPhosphorBemerkung);
            RendererTools.makeReadOnly(txtSaeureBemerkung);
            RendererTools.makeReadOnly(txtSalzBemerkung);
            RendererTools.makeReadOnly(txtSauerstoffBemerkung);
            RendererTools.makeReadOnly(txtStickstoffBemerkung);
            RendererTools.makeReadOnly(txtTempBemerkung);
            jbVorb.setVisible(false);
        }
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
        lblGenCond1 = new javax.swing.JLabel();
        cbGkTemp = new de.cismet.cids.editors.DefaultBindableReferenceCombo(new QualityStatusCodeComparator());
        txtJahrTemp = new javax.swing.JTextField();
        txtTempBemerkung = new javax.swing.JTextField();
        txtMstTemp = new javax.swing.JTextField();
        cbSauerstoff = new de.cismet.cids.editors.DefaultBindableReferenceCombo(new QualityStatusCodeComparator());
        txtJahrSauerstoff = new javax.swing.JTextField();
        lblGenCond2 = new javax.swing.JLabel();
        txtMstSauerstoff = new javax.swing.JTextField();
        txtSauerstoffBemerkung = new javax.swing.JTextField();
        cbSalz = new de.cismet.cids.editors.DefaultBindableReferenceCombo(new QualityStatusCodeComparator());
        txtJahrSalz = new javax.swing.JTextField();
        lblGenCond3 = new javax.swing.JLabel();
        txtMstSalzgehalt = new javax.swing.JTextField();
        txtSalzBemerkung = new javax.swing.JTextField();
        cbSaeure = new de.cismet.cids.editors.DefaultBindableReferenceCombo(new QualityStatusCodeComparator());
        txtJahrSaeure = new javax.swing.JTextField();
        lblGenCond4 = new javax.swing.JLabel();
        txtMstSaeure = new javax.swing.JTextField();
        txtSaeureBemerkung = new javax.swing.JTextField();
        cbStickstoff = new de.cismet.cids.editors.DefaultBindableReferenceCombo(new QualityStatusCodeComparator());
        txtJahrStickstoff = new javax.swing.JTextField();
        lblGenCond5 = new javax.swing.JLabel();
        txtMstStickstoff = new javax.swing.JTextField();
        txtStickstoffBemerkung = new javax.swing.JTextField();
        cbPhosphor = new de.cismet.cids.editors.DefaultBindableReferenceCombo(new QualityStatusCodeComparator());
        txtJahrPhosphor = new javax.swing.JTextField();
        lblGenCond6 = new javax.swing.JLabel();
        txtMstPhosphor = new javax.swing.JTextField();
        txtPhosphorBemerkung = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtMstTab1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        chemieMstMessungenPanOne1 = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.ChemieMstMessungenPanOne(true);

        setMinimumSize(new java.awt.Dimension(910, 550));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(910, 550));
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

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_pc_qk}"),
                cbGkPcQk,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cbGkPcQk.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbGkPcQkActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAllgemein.add(cbGkPcQk, gridBagConstraints);

        lblGenCondJahr.setText(org.openide.util.NbBundle.getMessage(
                WkFgPanSix.class,
                "WkFgPanSix.lblGenCondJahr.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAllgemein.add(lblGenCondJahr, gridBagConstraints);

        lblGenCondBemerkung.setText(org.openide.util.NbBundle.getMessage(
                WkFgPanSix.class,
                "WkFgPanSix.lblGenCondBemerkung.text")); // NOI18N
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

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.jahr_pcqk}"),
                txtJahrPcqk,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAllgemein.add(txtJahrPcqk, gridBagConstraints);

        txtMst.setMinimumSize(new java.awt.Dimension(100, 20));
        txtMst.setPreferredSize(new java.awt.Dimension(100, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.mst}"),
                txtMst,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAllgemein.add(txtMst, gridBagConstraints);

        txtPcQkBemerkung.setMinimumSize(new java.awt.Dimension(200, 20));
        txtPcQkBemerkung.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.pc_qk_bemerkung}"),
                txtPcQkBemerkung,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAllgemein.add(txtPcQkBemerkung, gridBagConstraints);

        lblGenCondGkQk.setText(org.openide.util.NbBundle.getMessage(
                WkFgPanSix.class,
                "WkFgPanSix.lblGenCondGkQk.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAllgemein.add(lblGenCondGkQk, gridBagConstraints);

        jbVorb.setText(org.openide.util.NbBundle.getMessage(WkFgPanSix.class, "WkFgPanSix.jbVorb.text")); // NOI18N
        jbVorb.setMinimumSize(new java.awt.Dimension(85, 20));
        jbVorb.setPreferredSize(new java.awt.Dimension(85, 20));
        jbVorb.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jbVorbActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAllgemein.add(jbVorb, gridBagConstraints);

        lblGenCond1.setText(org.openide.util.NbBundle.getMessage(WkFgPanSix.class, "WkFgPanSix.lblGenCond1.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAllgemein.add(lblGenCond1, gridBagConstraints);

        cbGkTemp.setMaximumSize(new java.awt.Dimension(200, 20));
        cbGkTemp.setMinimumSize(new java.awt.Dimension(200, 20));
        cbGkTemp.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_pc_thermal_wk}"),
                cbGkTemp,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cbGkTemp.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbGkTempActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAllgemein.add(cbGkTemp, gridBagConstraints);

        txtJahrTemp.setMinimumSize(new java.awt.Dimension(100, 20));
        txtJahrTemp.setPreferredSize(new java.awt.Dimension(100, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_pc_thermal_wk_year}"),
                txtJahrTemp,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAllgemein.add(txtJahrTemp, gridBagConstraints);

        txtTempBemerkung.setMinimumSize(new java.awt.Dimension(200, 20));
        txtTempBemerkung.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_pc_thermal_wk_bemerkung}"),
                txtTempBemerkung,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAllgemein.add(txtTempBemerkung, gridBagConstraints);

        txtMstTemp.setMinimumSize(new java.awt.Dimension(100, 20));
        txtMstTemp.setPreferredSize(new java.awt.Dimension(100, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_pc_thermal_wk_mst}"),
                txtMstTemp,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAllgemein.add(txtMstTemp, gridBagConstraints);

        cbSauerstoff.setMaximumSize(new java.awt.Dimension(200, 20));
        cbSauerstoff.setMinimumSize(new java.awt.Dimension(200, 20));
        cbSauerstoff.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_pc_oxygen_wk}"),
                cbSauerstoff,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cbSauerstoff.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbSauerstoffActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAllgemein.add(cbSauerstoff, gridBagConstraints);

        txtJahrSauerstoff.setMinimumSize(new java.awt.Dimension(100, 20));
        txtJahrSauerstoff.setPreferredSize(new java.awt.Dimension(100, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_pc_oxygen_wk_year}"),
                txtJahrSauerstoff,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAllgemein.add(txtJahrSauerstoff, gridBagConstraints);

        lblGenCond2.setText(org.openide.util.NbBundle.getMessage(WkFgPanSix.class, "WkFgPanSix.lblGenCond2.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAllgemein.add(lblGenCond2, gridBagConstraints);

        txtMstSauerstoff.setMinimumSize(new java.awt.Dimension(100, 20));
        txtMstSauerstoff.setPreferredSize(new java.awt.Dimension(100, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_pc_oxygen_wk_mst}"),
                txtMstSauerstoff,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAllgemein.add(txtMstSauerstoff, gridBagConstraints);

        txtSauerstoffBemerkung.setMinimumSize(new java.awt.Dimension(200, 20));
        txtSauerstoffBemerkung.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_pc_oxygen_wk_bemerkung}"),
                txtSauerstoffBemerkung,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAllgemein.add(txtSauerstoffBemerkung, gridBagConstraints);

        cbSalz.setMaximumSize(new java.awt.Dimension(200, 20));
        cbSalz.setMinimumSize(new java.awt.Dimension(200, 20));
        cbSalz.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_pc_salinity_wk}"),
                cbSalz,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cbSalz.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbSalzActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAllgemein.add(cbSalz, gridBagConstraints);

        txtJahrSalz.setMinimumSize(new java.awt.Dimension(100, 20));
        txtJahrSalz.setPreferredSize(new java.awt.Dimension(100, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_pc_salinity_wk_year}"),
                txtJahrSalz,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAllgemein.add(txtJahrSalz, gridBagConstraints);

        lblGenCond3.setText(org.openide.util.NbBundle.getMessage(WkFgPanSix.class, "WkFgPanSix.lblGenCond3.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAllgemein.add(lblGenCond3, gridBagConstraints);

        txtMstSalzgehalt.setMinimumSize(new java.awt.Dimension(100, 20));
        txtMstSalzgehalt.setPreferredSize(new java.awt.Dimension(100, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_pc_salinity_wk_mst}"),
                txtMstSalzgehalt,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAllgemein.add(txtMstSalzgehalt, gridBagConstraints);

        txtSalzBemerkung.setMinimumSize(new java.awt.Dimension(200, 20));
        txtSalzBemerkung.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_pc_salinity_wk_bemerkung}"),
                txtSalzBemerkung,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAllgemein.add(txtSalzBemerkung, gridBagConstraints);

        cbSaeure.setMaximumSize(new java.awt.Dimension(200, 20));
        cbSaeure.setMinimumSize(new java.awt.Dimension(200, 20));
        cbSaeure.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_pc_acid_wk}"),
                cbSaeure,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cbSaeure.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbSaeureActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAllgemein.add(cbSaeure, gridBagConstraints);

        txtJahrSaeure.setMinimumSize(new java.awt.Dimension(100, 20));
        txtJahrSaeure.setPreferredSize(new java.awt.Dimension(100, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_pc_acid_wk_year}"),
                txtJahrSaeure,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAllgemein.add(txtJahrSaeure, gridBagConstraints);

        lblGenCond4.setText(org.openide.util.NbBundle.getMessage(WkFgPanSix.class, "WkFgPanSix.lblGenCond4.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAllgemein.add(lblGenCond4, gridBagConstraints);

        txtMstSaeure.setMinimumSize(new java.awt.Dimension(100, 20));
        txtMstSaeure.setPreferredSize(new java.awt.Dimension(100, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_pc_acid_wk_mst}"),
                txtMstSaeure,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAllgemein.add(txtMstSaeure, gridBagConstraints);

        txtSaeureBemerkung.setMinimumSize(new java.awt.Dimension(200, 20));
        txtSaeureBemerkung.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_pc_acid_wk_bemerkung}"),
                txtSaeureBemerkung,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAllgemein.add(txtSaeureBemerkung, gridBagConstraints);

        cbStickstoff.setMaximumSize(new java.awt.Dimension(200, 20));
        cbStickstoff.setMinimumSize(new java.awt.Dimension(200, 20));
        cbStickstoff.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_pc_nitrogen_wk}"),
                cbStickstoff,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cbStickstoff.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbStickstoffActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAllgemein.add(cbStickstoff, gridBagConstraints);

        txtJahrStickstoff.setMinimumSize(new java.awt.Dimension(100, 20));
        txtJahrStickstoff.setPreferredSize(new java.awt.Dimension(100, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_pc_nitrogen_wk_year}"),
                txtJahrStickstoff,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAllgemein.add(txtJahrStickstoff, gridBagConstraints);

        lblGenCond5.setText(org.openide.util.NbBundle.getMessage(WkFgPanSix.class, "WkFgPanSix.lblGenCond5.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAllgemein.add(lblGenCond5, gridBagConstraints);

        txtMstStickstoff.setMinimumSize(new java.awt.Dimension(100, 20));
        txtMstStickstoff.setPreferredSize(new java.awt.Dimension(100, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_pc_nitrogen_wk_mst}"),
                txtMstStickstoff,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAllgemein.add(txtMstStickstoff, gridBagConstraints);

        txtStickstoffBemerkung.setMinimumSize(new java.awt.Dimension(200, 20));
        txtStickstoffBemerkung.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_pc_nitrogen_wk_bemerkung}"),
                txtStickstoffBemerkung,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAllgemein.add(txtStickstoffBemerkung, gridBagConstraints);

        cbPhosphor.setMaximumSize(new java.awt.Dimension(200, 20));
        cbPhosphor.setMinimumSize(new java.awt.Dimension(200, 20));
        cbPhosphor.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_pc_phosphor_wk}"),
                cbPhosphor,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cbPhosphor.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbPhosphorActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAllgemein.add(cbPhosphor, gridBagConstraints);

        txtJahrPhosphor.setMinimumSize(new java.awt.Dimension(100, 20));
        txtJahrPhosphor.setPreferredSize(new java.awt.Dimension(100, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_pc_phosphor_wk_year}"),
                txtJahrPhosphor,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAllgemein.add(txtJahrPhosphor, gridBagConstraints);

        lblGenCond6.setText(org.openide.util.NbBundle.getMessage(WkFgPanSix.class, "WkFgPanSix.lblGenCond6.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAllgemein.add(lblGenCond6, gridBagConstraints);

        txtMstPhosphor.setMinimumSize(new java.awt.Dimension(100, 20));
        txtMstPhosphor.setPreferredSize(new java.awt.Dimension(100, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_pc_phosphor_wk_mst}"),
                txtMstPhosphor,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAllgemein.add(txtMstPhosphor, gridBagConstraints);

        txtPhosphorBemerkung.setMinimumSize(new java.awt.Dimension(200, 20));
        txtPhosphorBemerkung.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_pc_phosphor_wk_bemerkung}"),
                txtPhosphorBemerkung,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAllgemein.add(txtPhosphorBemerkung, gridBagConstraints);

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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Messwerte"));
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(chemieMstMessungenPanOne1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        panInfoContent.add(jPanel1, gridBagConstraints);

        jScrollPane1.setViewportView(panInfoContent);

        panInfo.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        add(panInfo, java.awt.BorderLayout.CENTER);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbGkPcQkActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbGkPcQkActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_cbGkPcQkActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jbVorbActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jbVorbActionPerformed
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
    } //GEN-LAST:event_jbVorbActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbGkTempActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbGkTempActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_cbGkTempActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbSauerstoffActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbSauerstoffActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_cbSauerstoffActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbSalzActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbSalzActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_cbSalzActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbSaeureActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbSaeureActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_cbSaeureActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbStickstoffActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbStickstoffActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_cbStickstoffActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbPhosphorActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbPhosphorActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_cbPhosphorActionPerformed

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
            if (jtMstTab1.getSelectedRow() > -1) {
                final CidsBean sbean = model.getData().get(jtMstTab1.getSelectedRow());
                chemieMstMessungenPanOne1.setCidsBean(sbean);
            }
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
                WRRLUtil.DOMAIN_NAME,
                "chemie_mst_messungen");

        //~ Instance fields ----------------------------------------------------

        private String[][] header = {
                { "MST", "messstelle.messstelle" },    // NOI18N
                { "WK", "messstelle.wk_fg.wk_k" },     // NOI18N
                { "Jahr", "messjahr" },                // NOI18N
                { "GK PC", "gk_pc_mst" },              // NOI18N
                { "GK Temp", "gk_pc_thermal_mst" },    // NOI18N
                { "GK Oxy", "gk_pc_oxygen_mst" },      // NOI18N
                { "GK Salz", "gk_pc_salinity_mst" },   // NOI18N
                { "GK Säure", "gk_pc_acid_mst" },      // NOI18N
                { "GK N", "gk_pc_nitrogen_mst" },      // NOI18N
                { "GK P", "gk_pc_phosphor_mst" },      // NOI18N
                { "Bemerkung PC-MST", "bemerkung_pc" } // NOI18N
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
            return String.class;
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
            } else {
                return "";                                                            // NOI18N
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
                if (OW[i][0].equals("o2_owert_rakon")) {
                    if ((ow != null) && (val != null) && (ow.compareTo(val) == 1)) {
                        return true;
                    }
                } else {
                    if ((ow != null) && (val != null) && (ow.compareTo(val) == -1)) {
                        return true;
                    }
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
