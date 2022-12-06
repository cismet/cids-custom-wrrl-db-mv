/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import org.jdesktop.beansbinding.Converter;

import java.awt.Color;
import java.awt.EventQueue;

import java.math.BigDecimal;

import java.text.DecimalFormat;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.CidsBeanStore;

import de.cismet.cids.editors.DefaultCustomObjectEditor;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class WkKgMstMessungenPanOne extends javax.swing.JPanel implements CidsBeanStore {

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JLabel lbSwEinheit;
    private javax.swing.JLabel lblGk;
    private javax.swing.JLabel lblGk1;
    private javax.swing.JLabel lblJmw;
    private javax.swing.JLabel lblJmwTn;
    private javax.swing.JLabel lblO2;
    private javax.swing.JLabel lblPhyChem;
    private javax.swing.JLabel lblPhyChem1;
    private javax.swing.JLabel lblPhyChem2;
    private javax.swing.JLabel lblSichtTiefe;
    private javax.swing.JTextField txtJmwEi;
    private javax.swing.JTextField txtJmwGk;
    private javax.swing.JTextField txtJmwSw;
    private javax.swing.JTextField txtJmwTnEi;
    private javax.swing.JTextField txtJmwTnGk;
    private javax.swing.JTextField txtJmwTnSw;
    private javax.swing.JTextField txtJmwTnWert;
    private javax.swing.JTextField txtJmwWert;
    private javax.swing.JTextField txtO2Ei;
    private javax.swing.JTextField txtO2Gk;
    private javax.swing.JTextField txtO2Sw;
    private javax.swing.JTextField txtO2Wert;
    private javax.swing.JTextField txtSichtTiefeEi;
    private javax.swing.JTextField txtSichtTiefeGk;
    private javax.swing.JTextField txtSichtTiefeSw;
    private javax.swing.JTextField txtSichtTiefeWert;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ChemieMstMessungenPanOne object.
     */
    public WkKgMstMessungenPanOne() {
        this(false);
    }

    /**
     * Creates new form ChemieMstMessungenPanOne.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public WkKgMstMessungenPanOne(final boolean readOnly) {
        initComponents();

        setEnable(!readOnly);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  cidsBean  DOCUMENT ME!
     */
    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        bindingGroup.unbind();

        this.cidsBean = cidsBean;

        if (cidsBean != null) {
            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
                bindingGroup,
                cidsBean);
            bindingGroup.bind();
            EventQueue.invokeLater(new Thread("setColors") {

                    @Override
                    public void run() {
                        setColors();
                    }
                });
        } else {
            EventQueue.invokeLater(new Thread("clear colors") {

                    @Override
                    public void run() {
                        clearForm();
                        txtJmwGk.setBackground(new Color(245, 246, 247));
                        txtJmwSw.setBackground(new Color(245, 246, 247));
                        txtJmwTnGk.setBackground(new Color(245, 246, 247));
                        txtJmwTnSw.setBackground(new Color(245, 246, 247));
                        txtJmwTnWert.setBackground(new Color(245, 246, 247));
                        txtJmwWert.setBackground(new Color(245, 246, 247));
                        txtO2Gk.setBackground(new Color(245, 246, 247));
                        txtO2Sw.setBackground(new Color(245, 246, 247));
                        txtO2Wert.setBackground(new Color(245, 246, 247));
                        txtSichtTiefeGk.setBackground(new Color(245, 246, 247));
                        txtSichtTiefeSw.setBackground(new Color(245, 246, 247));
                        txtSichtTiefeWert.setBackground(new Color(245, 246, 247));
                        txtJmwEi.setBackground(new Color(245, 246, 247));
                        txtJmwTnEi.setBackground(new Color(245, 246, 247));
                        txtO2Ei.setBackground(new Color(245, 246, 247));
                        txtSichtTiefeEi.setBackground(new Color(245, 246, 247));
                    }
                });
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  enable  DOCUMENT ME!
     */
    public void setEnable(final boolean enable) {
        txtJmwGk.setEnabled(false);
        txtJmwSw.setEnabled(false);
        txtJmwTnGk.setEnabled(false);
        txtJmwTnSw.setEnabled(false);
        txtJmwTnWert.setEnabled(false);
        txtJmwWert.setEnabled(false);
        txtO2Gk.setEnabled(false);
        txtO2Sw.setEnabled(false);
        txtO2Wert.setEnabled(false);
        txtSichtTiefeGk.setEnabled(false);
        txtSichtTiefeSw.setEnabled(false);
        txtSichtTiefeWert.setEnabled(false);
        txtJmwEi.setEnabled(false);
        txtJmwTnEi.setEnabled(false);
        txtO2Ei.setEnabled(false);
        txtSichtTiefeEi.setEnabled(false);

        txtJmwGk.setDisabledTextColor(new Color(0, 0, 0));
        txtJmwSw.setDisabledTextColor(new Color(0, 0, 0));
        txtJmwTnGk.setDisabledTextColor(new Color(0, 0, 0));
        txtJmwTnSw.setDisabledTextColor(new Color(0, 0, 0));
        txtJmwTnWert.setDisabledTextColor(new Color(0, 0, 0));
        txtJmwWert.setDisabledTextColor(new Color(0, 0, 0));
        txtO2Gk.setDisabledTextColor(new Color(0, 0, 0));
        txtO2Sw.setDisabledTextColor(new Color(0, 0, 0));
        txtO2Wert.setDisabledTextColor(new Color(0, 0, 0));
        txtSichtTiefeGk.setDisabledTextColor(new Color(0, 0, 0));
        txtSichtTiefeSw.setDisabledTextColor(new Color(0, 0, 0));
        txtSichtTiefeWert.setDisabledTextColor(new Color(0, 0, 0));
        txtJmwEi.setDisabledTextColor(new Color(0, 0, 0));
        txtJmwTnEi.setDisabledTextColor(new Color(0, 0, 0));
        txtO2Ei.setDisabledTextColor(new Color(0, 0, 0));
        txtSichtTiefeEi.setDisabledTextColor(new Color(0, 0, 0));
    }

    /**
     * DOCUMENT ME!
     */
    private void clearForm() {
        txtJmwGk.setText("");
        txtJmwSw.setText("");
        txtJmwTnGk.setText("");
        txtJmwTnSw.setText("");
        txtJmwTnWert.setText("");
        txtJmwWert.setText("");
        txtO2Gk.setText("");
        txtO2Sw.setText("");
        txtO2Wert.setText("");
        txtSichtTiefeGk.setText("");
        txtSichtTiefeSw.setText("");
        txtSichtTiefeWert.setText("");
        txtJmwEi.setText("");
        txtJmwTnEi.setText("");
        txtO2Ei.setText("");
        txtSichtTiefeEi.setText("");
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

        jPanel1 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        lblPhyChem1 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        lblPhyChem = new javax.swing.JLabel();
        lblPhyChem2 = new javax.swing.JLabel();
        lbSwEinheit = new javax.swing.JLabel();
        lblGk = new javax.swing.JLabel();
        lblGk1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblSichtTiefe = new javax.swing.JLabel();
        txtSichtTiefeWert = new javax.swing.JTextField();
        txtSichtTiefeSw = new javax.swing.JTextField();
        txtSichtTiefeGk = new javax.swing.JTextField();
        txtSichtTiefeEi = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        lblO2 = new javax.swing.JLabel();
        txtO2Wert = new javax.swing.JTextField();
        txtO2Sw = new javax.swing.JTextField();
        txtO2Gk = new javax.swing.JTextField();
        txtO2Ei = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        lblJmwTn = new javax.swing.JLabel();
        txtJmwTnWert = new javax.swing.JTextField();
        txtJmwTnSw = new javax.swing.JTextField();
        txtJmwTnGk = new javax.swing.JTextField();
        txtJmwTnEi = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        lblJmw = new javax.swing.JLabel();
        txtJmwWert = new javax.swing.JTextField();
        txtJmwSw = new javax.swing.JTextField();
        txtJmwGk = new javax.swing.JTextField();
        txtJmwEi = new javax.swing.JTextField();

        setOpaque(false);
        setLayout(new java.awt.GridBagLayout());

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel9.setOpaque(false);
        jPanel9.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(
            lblPhyChem1,
            org.openide.util.NbBundle.getMessage(
                WkKgMstMessungenPanOne.class,
                "WkKgMstMessungenPanOne.lblPhyChem1.text_1")); // NOI18N
        lblPhyChem1.setMaximumSize(new java.awt.Dimension(277, 35));
        lblPhyChem1.setMinimumSize(new java.awt.Dimension(277, 35));
        lblPhyChem1.setPreferredSize(new java.awt.Dimension(277, 35));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel9.add(lblPhyChem1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jPanel9, gridBagConstraints);

        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel8.setOpaque(false);
        jPanel8.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(
            lblPhyChem,
            org.openide.util.NbBundle.getMessage(
                WkKgMstMessungenPanOne.class,
                "WkKgMstMessungenPanOne.lblPhyChem.text")); // NOI18N
        lblPhyChem.setMaximumSize(new java.awt.Dimension(280, 17));
        lblPhyChem.setMinimumSize(new java.awt.Dimension(280, 17));
        lblPhyChem.setPreferredSize(new java.awt.Dimension(280, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(lblPhyChem, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            lblPhyChem2,
            org.openide.util.NbBundle.getMessage(
                WkKgMstMessungenPanOne.class,
                "WkKgMstMessungenPanOne.lblPhyChem2.text")); // NOI18N
        lblPhyChem2.setMaximumSize(new java.awt.Dimension(220, 17));
        lblPhyChem2.setMinimumSize(new java.awt.Dimension(220, 17));
        lblPhyChem2.setPreferredSize(new java.awt.Dimension(220, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(lblPhyChem2, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            lbSwEinheit,
            org.openide.util.NbBundle.getMessage(
                WkKgMstMessungenPanOne.class,
                "WkKgMstMessungenPanOne.lbSwEinheit.text")); // NOI18N
        lbSwEinheit.setMaximumSize(new java.awt.Dimension(160, 17));
        lbSwEinheit.setMinimumSize(new java.awt.Dimension(160, 17));
        lbSwEinheit.setPreferredSize(new java.awt.Dimension(160, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(lbSwEinheit, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            lblGk,
            org.openide.util.NbBundle.getMessage(WkKgMstMessungenPanOne.class, "WkKgMstMessungenPanOne.lblGk.text")); // NOI18N
        lblGk.setMaximumSize(new java.awt.Dimension(110, 35));
        lblGk.setMinimumSize(new java.awt.Dimension(110, 35));
        lblGk.setPreferredSize(new java.awt.Dimension(110, 35));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(lblGk, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            lblGk1,
            org.openide.util.NbBundle.getMessage(WkKgMstMessungenPanOne.class, "WkKgMstMessungenPanOne.lblGk1.text")); // NOI18N
        lblGk1.setMaximumSize(new java.awt.Dimension(60, 17));
        lblGk1.setMinimumSize(new java.awt.Dimension(60, 17));
        lblGk1.setPreferredSize(new java.awt.Dimension(60, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(lblGk1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jPanel8, gridBagConstraints);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(
            lblSichtTiefe,
            org.openide.util.NbBundle.getMessage(
                WkKgMstMessungenPanOne.class,
                "WkKgMstMessungenPanOne.lblSichtTiefe.text")); // NOI18N
        lblSichtTiefe.setMaximumSize(new java.awt.Dimension(280, 17));
        lblSichtTiefe.setMinimumSize(new java.awt.Dimension(280, 17));
        lblSichtTiefe.setPreferredSize(new java.awt.Dimension(280, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(lblSichtTiefe, gridBagConstraints);

        txtSichtTiefeWert.setMaximumSize(new java.awt.Dimension(220, 20));
        txtSichtTiefeWert.setMinimumSize(new java.awt.Dimension(220, 20));
        txtSichtTiefeWert.setPreferredSize(new java.awt.Dimension(220, 20));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.jmwsecci}"),
                txtSichtTiefeWert,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtSichtTiefeWert, gridBagConstraints);

        txtSichtTiefeSw.setMaximumSize(new java.awt.Dimension(160, 20));
        txtSichtTiefeSw.setMinimumSize(new java.awt.Dimension(160, 20));
        txtSichtTiefeSw.setPreferredSize(new java.awt.Dimension(160, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.zielw_secci}"),
                txtSichtTiefeSw,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtSichtTiefeSw, gridBagConstraints);

        txtSichtTiefeGk.setMaximumSize(new java.awt.Dimension(110, 20));
        txtSichtTiefeGk.setMinimumSize(new java.awt.Dimension(110, 20));
        txtSichtTiefeGk.setPreferredSize(new java.awt.Dimension(110, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.wrrl_gk_secci}"),
                txtSichtTiefeGk,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtSichtTiefeGk, gridBagConstraints);

        txtSichtTiefeEi.setMaximumSize(new java.awt.Dimension(60, 20));
        txtSichtTiefeEi.setMinimumSize(new java.awt.Dimension(60, 20));
        txtSichtTiefeEi.setPreferredSize(new java.awt.Dimension(60, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.einh_secci}"),
                txtSichtTiefeEi,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtSichtTiefeEi, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(jPanel2, gridBagConstraints);

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setOpaque(false);
        jPanel3.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(
            lblO2,
            org.openide.util.NbBundle.getMessage(WkKgMstMessungenPanOne.class, "WkKgMstMessungenPanOne.lblO2.text")); // NOI18N
        lblO2.setToolTipText(org.openide.util.NbBundle.getMessage(
                WkKgMstMessungenPanOne.class,
                "WkKgMstMessungenPanOne.lblO2.toolTipText",
                new Object[] {}));                                                                                    // NOI18N
        lblO2.setMaximumSize(new java.awt.Dimension(280, 17));
        lblO2.setMinimumSize(new java.awt.Dimension(280, 17));
        lblO2.setPreferredSize(new java.awt.Dimension(280, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lblO2, gridBagConstraints);

        txtO2Wert.setMaximumSize(new java.awt.Dimension(220, 20));
        txtO2Wert.setMinimumSize(new java.awt.Dimension(220, 20));
        txtO2Wert.setPreferredSize(new java.awt.Dimension(220, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.minimum_o2}"),
                txtO2Wert,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txtO2Wert, gridBagConstraints);

        txtO2Sw.setMaximumSize(new java.awt.Dimension(160, 20));
        txtO2Sw.setMinimumSize(new java.awt.Dimension(160, 20));
        txtO2Sw.setPreferredSize(new java.awt.Dimension(160, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.zielw_o2}"),
                txtO2Sw,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txtO2Sw, gridBagConstraints);

        txtO2Gk.setMaximumSize(new java.awt.Dimension(110, 20));
        txtO2Gk.setMinimumSize(new java.awt.Dimension(110, 20));
        txtO2Gk.setPreferredSize(new java.awt.Dimension(110, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.wrrl_gk_o2}"),
                txtO2Gk,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txtO2Gk, gridBagConstraints);

        txtO2Ei.setMaximumSize(new java.awt.Dimension(60, 20));
        txtO2Ei.setMinimumSize(new java.awt.Dimension(60, 20));
        txtO2Ei.setPreferredSize(new java.awt.Dimension(60, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.einh_o2}"),
                txtO2Ei,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txtO2Ei, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(jPanel3, gridBagConstraints);

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.setOpaque(false);
        jPanel4.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(
            lblJmwTn,
            org.openide.util.NbBundle.getMessage(
                WkKgMstMessungenPanOne.class,
                "WkKgMstMessungenPanOne.lblJmwTn.text",
                new Object[] {})); // NOI18N
        lblJmwTn.setMaximumSize(new java.awt.Dimension(280, 17));
        lblJmwTn.setMinimumSize(new java.awt.Dimension(280, 17));
        lblJmwTn.setPreferredSize(new java.awt.Dimension(280, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblJmwTn, gridBagConstraints);

        txtJmwTnWert.setMaximumSize(new java.awt.Dimension(220, 20));
        txtJmwTnWert.setMinimumSize(new java.awt.Dimension(220, 20));
        txtJmwTnWert.setPreferredSize(new java.awt.Dimension(220, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.jmwtn}"),
                txtJmwTnWert,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtJmwTnWert, gridBagConstraints);

        txtJmwTnSw.setMaximumSize(new java.awt.Dimension(160, 20));
        txtJmwTnSw.setMinimumSize(new java.awt.Dimension(160, 20));
        txtJmwTnSw.setPreferredSize(new java.awt.Dimension(160, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.zielw_tn}"),
                txtJmwTnSw,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtJmwTnSw, gridBagConstraints);

        txtJmwTnGk.setMaximumSize(new java.awt.Dimension(110, 20));
        txtJmwTnGk.setMinimumSize(new java.awt.Dimension(110, 20));
        txtJmwTnGk.setPreferredSize(new java.awt.Dimension(110, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.wrrl_gk_tn}"),
                txtJmwTnGk,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtJmwTnGk, gridBagConstraints);

        txtJmwTnEi.setMaximumSize(new java.awt.Dimension(60, 20));
        txtJmwTnEi.setMinimumSize(new java.awt.Dimension(60, 20));
        txtJmwTnEi.setPreferredSize(new java.awt.Dimension(60, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.einh_tn}"),
                txtJmwTnEi,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtJmwTnEi, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(jPanel4, gridBagConstraints);

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.setOpaque(false);
        jPanel5.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(
            lblJmw,
            org.openide.util.NbBundle.getMessage(WkKgMstMessungenPanOne.class, "WkKgMstMessungenPanOne.lblJmw.text")); // NOI18N
        lblJmw.setMaximumSize(new java.awt.Dimension(280, 17));
        lblJmw.setMinimumSize(new java.awt.Dimension(280, 17));
        lblJmw.setPreferredSize(new java.awt.Dimension(280, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(lblJmw, gridBagConstraints);

        txtJmwWert.setMaximumSize(new java.awt.Dimension(220, 20));
        txtJmwWert.setMinimumSize(new java.awt.Dimension(220, 20));
        txtJmwWert.setPreferredSize(new java.awt.Dimension(220, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.jmw}"),
                txtJmwWert,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtJmwWert, gridBagConstraints);

        txtJmwSw.setMaximumSize(new java.awt.Dimension(160, 20));
        txtJmwSw.setMinimumSize(new java.awt.Dimension(160, 20));
        txtJmwSw.setPreferredSize(new java.awt.Dimension(160, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.zielw_tp}"),
                txtJmwSw,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtJmwSw, gridBagConstraints);

        txtJmwGk.setMaximumSize(new java.awt.Dimension(110, 20));
        txtJmwGk.setMinimumSize(new java.awt.Dimension(110, 20));
        txtJmwGk.setPreferredSize(new java.awt.Dimension(110, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.wrrl_gk_tp}"),
                txtJmwGk,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtJmwGk, gridBagConstraints);

        txtJmwEi.setMaximumSize(new java.awt.Dimension(60, 20));
        txtJmwEi.setMinimumSize(new java.awt.Dimension(60, 20));
        txtJmwEi.setPreferredSize(new java.awt.Dimension(60, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.einh_tp}"),
                txtJmwEi,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtJmwEi, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(jPanel5, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jPanel1, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    @Override
    public CidsBean getCidsBean() {
        return this.cidsBean;
    }

    /**
     * DOCUMENT ME!
     */
    private void setColors() {
        txtSichtTiefeGk.setBackground(getColor(txtSichtTiefeGk.getText()));
        txtJmwGk.setBackground(getColor(txtJmwGk.getText()));
        txtO2Gk.setBackground(getColor(txtO2Gk.getText()));
        txtJmwTnGk.setBackground(getColor(txtJmwTnGk.getText()));
    }

    /**
     * DOCUMENT ME!
     *
     * @param   text  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private Color getColor(final String text) {
        if (text.equalsIgnoreCase("ja")) {
            return Color.GREEN;
        } else if (text.equalsIgnoreCase("nein")) {
            return Color.RED;
        } else {
            return new Color(245, 246, 247);
        }
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private static class DoubleConverter extends Converter<BigDecimal, String> {

        //~ Instance fields ----------------------------------------------------

        private int digits;
        private DecimalFormat formatter;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new DoubleConverter object.
         *
         * @param  digits  DOCUMENT ME!
         */
        public DoubleConverter(final int digits) {
            this.digits = digits;
            formatter = new DecimalFormat("0.########");
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public String convertForward(final BigDecimal value) {
            // log.fatal("forward:"+value);
            if (value != null) {
                final double factor = Math.pow(10, digits);
                return formatter.format(Math.round(value.doubleValue() * factor) / factor);
            } else {
                return null;
            }
        }

        @Override
        public BigDecimal convertReverse(final String value) {
            if (value != null) {
                try {
                    return new BigDecimal(value);
                } catch (NumberFormatException e) {
                    return null;
                }
            } else {
                return null;
            }
        }
    }
}
