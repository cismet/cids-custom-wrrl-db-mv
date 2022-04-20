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

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.math.BigDecimal;

import java.text.DecimalFormat;

import de.cismet.cids.custom.wrrl_db_mv.util.QualityStatusCodeSupportingComparator;
import de.cismet.cids.custom.wrrl_db_mv.util.RendererTools;
import de.cismet.cids.custom.wrrl_db_mv.util.ScrollableComboBox;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.CidsBeanStore;

import de.cismet.cids.editors.DefaultCustomObjectEditor;

import de.cismet.tools.BrowserLauncher;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class ChemieMstMessungenPanOne extends javax.swing.JPanel implements CidsBeanStore {

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbGkChlorid;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbGkGesN;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbGkGesamtP;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbGkPHMin;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbGkPhysChem;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbGkSauerstoffMin;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbGkTemp;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JLabel lblGenCond;
    private javax.swing.JLabel lblGenCond1;
    private javax.swing.JLabel lblGenCond10;
    private javax.swing.JLabel lblGenCond12;
    private javax.swing.JLabel lblGenCond13;
    private javax.swing.JLabel lblGenCond14;
    private javax.swing.JLabel lblGenCond15;
    private javax.swing.JLabel lblGenCond16;
    private javax.swing.JLabel lblGenCond17;
    private javax.swing.JLabel lblGenCond18;
    private javax.swing.JLabel lblGenCond19;
    private javax.swing.JLabel lblGenCond2;
    private javax.swing.JLabel lblGenCond20;
    private javax.swing.JLabel lblGenCond3;
    private javax.swing.JLabel lblGenCond4;
    private javax.swing.JLabel lblGenCond5;
    private javax.swing.JLabel lblGenCond6;
    private javax.swing.JLabel lblGenCond7;
    private javax.swing.JLabel lblGenCond8;
    private javax.swing.JLabel lblGenCond9;
    private javax.swing.JLabel lblPhyChem;
    private javax.swing.JTextField txtAmmoniak;
    private javax.swing.JTextField txtAmmonium;
    private javax.swing.JTextField txtBSB5;
    private javax.swing.JTextField txtChlorid;
    private javax.swing.JTextField txtGesN;
    private javax.swing.JTextField txtGesP;
    private javax.swing.JTextField txtNitratN;
    private javax.swing.JTextField txtNitritN;
    private javax.swing.JTextField txtOpo4;
    private javax.swing.JTextField txtPHMax;
    private javax.swing.JTextField txtPHMin;
    private javax.swing.JTextField txtPhosphor;
    private javax.swing.JTextField txtPhysChemBem;
    private javax.swing.JTextField txtSaeure;
    private javax.swing.JTextField txtSalzgehalt;
    private javax.swing.JTextField txtSauerstoff;
    private javax.swing.JTextField txtSauerstoffMin;
    private javax.swing.JTextField txtStickstoff;
    private javax.swing.JTextField txtSulfat;
    private javax.swing.JTextField txtTempGk;
    private javax.swing.JTextField txtTempMax;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ChemieMstMessungenPanOne object.
     */
    public ChemieMstMessungenPanOne() {
        this(false);
    }

    /**
     * Creates new form ChemieMstMessungenPanOne.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public ChemieMstMessungenPanOne(final boolean readOnly) {
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
        } else {
            clearForm();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  enable  DOCUMENT ME!
     */
    public void setEnable(final boolean enable) {
//        cbGkPhysChem.setEnabled(enable);
        txtPhysChemBem.setEnabled(enable);
        txtTempGk.setEnabled(enable);
        txtSauerstoff.setEnabled(enable);
        txtSalzgehalt.setEnabled(enable);
        txtSaeure.setEnabled(enable);
        txtStickstoff.setEnabled(enable);
        txtPhosphor.setEnabled(enable);

        txtTempMax.setEnabled(false);
        txtSauerstoffMin.setEnabled(false);
        txtBSB5.setEnabled(false);
        txtPHMin.setEnabled(false);
        txtChlorid.setEnabled(false);
        txtSulfat.setEnabled(false);
        txtPHMax.setEnabled(false);
        txtGesN.setEnabled(false);
        txtAmmonium.setEnabled(false);
        txtAmmoniak.setEnabled(false);
        txtNitratN.setEnabled(false);
        txtNitritN.setEnabled(false);
        txtOpo4.setEnabled(false);
        txtGesP.setEnabled(false);

        if (!enable) {
            RendererTools.makeReadOnly(cbGkPhysChem);
            RendererTools.makeReadOnly(cbGkTemp);
            RendererTools.makeReadOnly(cbGkSauerstoffMin);
            RendererTools.makeReadOnly(cbGkChlorid);
            RendererTools.makeReadOnly(cbGkPHMin);
            RendererTools.makeReadOnly(cbGkGesN);
            RendererTools.makeReadOnly(cbGkGesamtP);
        } else {
            RendererTools.makeWritable(cbGkTemp);
            RendererTools.makeWritable(cbGkSauerstoffMin);
            RendererTools.makeWritable(cbGkChlorid);
            RendererTools.makeWritable(cbGkPHMin);
            RendererTools.makeWritable(cbGkGesN);
            RendererTools.makeWritable(cbGkGesamtP);
//            RendererTools.makeWritable(txtTempMax);
//            RendererTools.makeWritable(txtTempGk);
//            RendererTools.makeWritable(txtSauerstoffMin);
//            RendererTools.makeWritable(txtSauerstoff);
//            RendererTools.makeWritable(txtBSB5);
//            RendererTools.makeWritable(txtPHMin);
//            RendererTools.makeWritable(txtChlorid);
//            RendererTools.makeWritable(txtSalzgehalt);
//            RendererTools.makeWritable(txtSulfat);
//            RendererTools.makeWritable(txtSaeure);
//            RendererTools.makeWritable(txtPHMax);
//            RendererTools.makeWritable(txtGesN);
//            RendererTools.makeWritable(txtStickstoff);
//            RendererTools.makeWritable(txtAmmonium);
//            RendererTools.makeWritable(txtAmmoniak);
//            RendererTools.makeWritable(txtNitratN);
//            RendererTools.makeWritable(txtNitritN);
//            RendererTools.makeWritable(txtOpo4);
//            RendererTools.makeWritable(txtGesP);
//            RendererTools.makeWritable(txtPhosphor);
//            RendererTools.makeWritable(txtPhysChemBem);
            RendererTools.makeWritable(cbGkPhysChem);
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void clearForm() {
        txtAmmoniak.setText("");
        txtAmmonium.setText("");
        txtBSB5.setText("");
        txtChlorid.setText("");
        txtGesN.setText("");
        txtGesP.setText("");
        txtNitratN.setText("");
        txtNitritN.setText("");
        txtOpo4.setText("");
        txtPHMax.setText("");
        txtPHMin.setText("");
        txtPhosphor.setText("");
        txtPhysChemBem.setText("");
        txtSaeure.setText("");
        txtSalzgehalt.setText("");
        txtSauerstoff.setText("");
        txtSauerstoffMin.setText("");
        txtStickstoff.setText("");
        txtSulfat.setText("");
        txtTempGk.setText("");
        txtTempMax.setText("");
        cbGkPhysChem.setSelectedIndex(-1);
        cbGkChlorid.setSelectedIndex(-1);
        cbGkGesN.setSelectedIndex(-1);
        cbGkGesamtP.setSelectedIndex(-1);
        cbGkPHMin.setSelectedIndex(-1);
        cbGkPhysChem.setSelectedIndex(-1);
        cbGkSauerstoffMin.setSelectedIndex(-1);
        cbGkTemp.setSelectedIndex(-1);
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
        jPanel8 = new javax.swing.JPanel();
        txtPhysChemBem = new javax.swing.JTextField();
        cbGkPhysChem = new ScrollableComboBox(null, false, false, new QualityStatusCodeSupportingComparator());
        lblPhyChem = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblGenCond = new javax.swing.JLabel();
        txtTempMax = new javax.swing.JTextField();
        cbGkTemp = new ScrollableComboBox(null, false, false, new QualityStatusCodeSupportingComparator());
        lblGenCond1 = new javax.swing.JLabel();
        txtTempGk = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        lblGenCond2 = new javax.swing.JLabel();
        lblGenCond4 = new javax.swing.JLabel();
        txtSauerstoffMin = new javax.swing.JTextField();
        txtBSB5 = new javax.swing.JTextField();
        cbGkSauerstoffMin = new ScrollableComboBox(null, false, false, new QualityStatusCodeSupportingComparator());
        lblGenCond3 = new javax.swing.JLabel();
        txtSauerstoff = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        lblGenCond6 = new javax.swing.JLabel();
        lblGenCond8 = new javax.swing.JLabel();
        txtChlorid = new javax.swing.JTextField();
        txtSulfat = new javax.swing.JTextField();
        cbGkChlorid = new ScrollableComboBox(null, false, false, new QualityStatusCodeSupportingComparator());
        lblGenCond7 = new javax.swing.JLabel();
        txtSalzgehalt = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        lblGenCond5 = new javax.swing.JLabel();
        lblGenCond10 = new javax.swing.JLabel();
        txtPHMax = new javax.swing.JTextField();
        txtPHMin = new javax.swing.JTextField();
        cbGkPHMin = new ScrollableComboBox(null, false, false, new QualityStatusCodeSupportingComparator());
        lblGenCond9 = new javax.swing.JLabel();
        txtSaeure = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        lblGenCond12 = new javax.swing.JLabel();
        lblGenCond14 = new javax.swing.JLabel();
        lblGenCond15 = new javax.swing.JLabel();
        lblGenCond16 = new javax.swing.JLabel();
        lblGenCond17 = new javax.swing.JLabel();
        txtGesN = new javax.swing.JTextField();
        cbGkGesN = new ScrollableComboBox(null, false, false, new QualityStatusCodeSupportingComparator());
        lblGenCond13 = new javax.swing.JLabel();
        txtStickstoff = new javax.swing.JTextField();
        txtAmmonium = new javax.swing.JTextField();
        txtAmmoniak = new javax.swing.JTextField();
        txtNitratN = new javax.swing.JTextField();
        txtNitritN = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        lblGenCond19 = new javax.swing.JLabel();
        lblGenCond18 = new javax.swing.JLabel();
        txtOpo4 = new javax.swing.JTextField();
        txtGesP = new javax.swing.JTextField();
        cbGkGesamtP = new ScrollableComboBox(null, false, false, new QualityStatusCodeSupportingComparator());
        lblGenCond20 = new javax.swing.JLabel();
        txtPhosphor = new javax.swing.JTextField();

        setOpaque(false);
        setLayout(new java.awt.GridBagLayout());

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel8.setOpaque(false);
        jPanel8.setLayout(new java.awt.GridBagLayout());

        txtPhysChemBem.setMinimumSize(new java.awt.Dimension(200, 20));
        txtPhysChemBem.setPreferredSize(new java.awt.Dimension(200, 20));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bemerkung_pc}"),
                txtPhysChemBem,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bemerkung_pc}"),
                txtPhysChemBem,
                org.jdesktop.beansbinding.BeanProperty.create("toolTipText"));
        binding.setSourceNullValue("null");
        binding.setSourceUnreadableValue("null");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(txtPhysChemBem, gridBagConstraints);

        cbGkPhysChem.setMaximumSize(new java.awt.Dimension(225, 20));
        cbGkPhysChem.setMinimumSize(new java.awt.Dimension(225, 20));
        cbGkPhysChem.setPreferredSize(new java.awt.Dimension(225, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_pc_mst}"),
                cbGkPhysChem,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cbGkPhysChem.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbGkPhysChemActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(cbGkPhysChem, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            lblPhyChem,
            org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenPanOne.class,
                "ChemieMstMessungenPanOne.lblPhyChem.text")); // NOI18N
        lblPhyChem.setMaximumSize(new java.awt.Dimension(277, 17));
        lblPhyChem.setMinimumSize(new java.awt.Dimension(277, 17));
        lblPhyChem.setPreferredSize(new java.awt.Dimension(277, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(lblPhyChem, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jPanel8, gridBagConstraints);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(
            lblGenCond,
            org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenPanOne.class,
                "ChemieMstMessungenPanOne.lblGenCond.text")); // NOI18N
        lblGenCond.setMaximumSize(new java.awt.Dimension(167, 17));
        lblGenCond.setMinimumSize(new java.awt.Dimension(167, 17));
        lblGenCond.setPreferredSize(new java.awt.Dimension(167, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(lblGenCond, gridBagConstraints);

        txtTempMax.setMinimumSize(new java.awt.Dimension(100, 20));
        txtTempMax.setPreferredSize(new java.awt.Dimension(100, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.t_max}"),
                txtTempMax,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setConverter(new DoubleConverter(1));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtTempMax, gridBagConstraints);

        cbGkTemp.setMaximumSize(new java.awt.Dimension(225, 20));
        cbGkTemp.setMinimumSize(new java.awt.Dimension(225, 20));
        cbGkTemp.setPreferredSize(new java.awt.Dimension(225, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_pc_thermal_mst}"),
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
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(cbGkTemp, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            lblGenCond1,
            org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenPanOne.class,
                "ChemieMstMessungenPanOne.lblGenCond1.text",
                new Object[] {})); // NOI18N
        lblGenCond1.setMaximumSize(new java.awt.Dimension(107, 17));
        lblGenCond1.setMinimumSize(new java.awt.Dimension(107, 17));
        lblGenCond1.setPreferredSize(new java.awt.Dimension(107, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(lblGenCond1, gridBagConstraints);

        txtTempGk.setMinimumSize(new java.awt.Dimension(100, 20));
        txtTempGk.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_pc_thermal_bemerkung}"),
                txtTempGk,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_pc_thermal_bemerkung}"),
                txtTempGk,
                org.jdesktop.beansbinding.BeanProperty.create("toolTipText"));
        binding.setSourceNullValue("null");
        binding.setSourceUnreadableValue("null");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtTempGk, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(jPanel2, gridBagConstraints);

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setOpaque(false);
        jPanel3.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(
            lblGenCond2,
            org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenPanOne.class,
                "ChemieMstMessungenPanOne.lblGenCond2.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lblGenCond2, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            lblGenCond4,
            org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenPanOne.class,
                "ChemieMstMessungenPanOne.lblGenCond4.text",
                new Object[] {})); // NOI18N
        lblGenCond4.setMaximumSize(new java.awt.Dimension(167, 17));
        lblGenCond4.setMinimumSize(new java.awt.Dimension(167, 17));
        lblGenCond4.setPreferredSize(new java.awt.Dimension(167, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lblGenCond4, gridBagConstraints);

        txtSauerstoffMin.setMinimumSize(new java.awt.Dimension(100, 20));
        txtSauerstoffMin.setPreferredSize(new java.awt.Dimension(100, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.o2_mittelwert}"),
                txtSauerstoffMin,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setConverter(new DoubleConverter(2));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txtSauerstoffMin, gridBagConstraints);

        txtBSB5.setMinimumSize(new java.awt.Dimension(100, 20));
        txtBSB5.setPreferredSize(new java.awt.Dimension(100, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.bsb5_mittelwert}"),
                txtBSB5,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setConverter(new DoubleConverter(2));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txtBSB5, gridBagConstraints);

        cbGkSauerstoffMin.setMaximumSize(new java.awt.Dimension(225, 20));
        cbGkSauerstoffMin.setMinimumSize(new java.awt.Dimension(225, 20));
        cbGkSauerstoffMin.setPreferredSize(new java.awt.Dimension(225, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_pc_oxygen_mst}"),
                cbGkSauerstoffMin,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cbGkSauerstoffMin.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbGkSauerstoffMinActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(cbGkSauerstoffMin, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            lblGenCond3,
            org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenPanOne.class,
                "ChemieMstMessungenPanOne.lblGenCond3.text")); // NOI18N
        lblGenCond3.setMaximumSize(new java.awt.Dimension(107, 17));
        lblGenCond3.setMinimumSize(new java.awt.Dimension(107, 17));
        lblGenCond3.setPreferredSize(new java.awt.Dimension(107, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lblGenCond3, gridBagConstraints);

        txtSauerstoff.setMinimumSize(new java.awt.Dimension(100, 20));
        txtSauerstoff.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_pc_oxygen_bemerkung}"),
                txtSauerstoff,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_pc_oxygen_bemerkung}"),
                txtSauerstoff,
                org.jdesktop.beansbinding.BeanProperty.create("toolTipText"));
        binding.setSourceNullValue("null");
        binding.setSourceUnreadableValue("null");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txtSauerstoff, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(jPanel3, gridBagConstraints);

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.setOpaque(false);
        jPanel4.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(
            lblGenCond6,
            org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenPanOne.class,
                "ChemieMstMessungenPanOne.lblGenCond6.text",
                new Object[] {})); // NOI18N
        lblGenCond6.setMaximumSize(new java.awt.Dimension(167, 17));
        lblGenCond6.setMinimumSize(new java.awt.Dimension(167, 17));
        lblGenCond6.setPreferredSize(new java.awt.Dimension(167, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblGenCond6, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            lblGenCond8,
            org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenPanOne.class,
                "ChemieMstMessungenPanOne.lblGenCond8.text")); // NOI18N
        lblGenCond8.setMaximumSize(new java.awt.Dimension(167, 17));
        lblGenCond8.setMinimumSize(new java.awt.Dimension(167, 17));
        lblGenCond8.setPreferredSize(new java.awt.Dimension(167, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblGenCond8, gridBagConstraints);

        txtChlorid.setMinimumSize(new java.awt.Dimension(100, 20));
        txtChlorid.setPreferredSize(new java.awt.Dimension(100, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.cl_mittelwert}"),
                txtChlorid,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setConverter(new DoubleConverter(2));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtChlorid, gridBagConstraints);

        txtSulfat.setMinimumSize(new java.awt.Dimension(100, 20));
        txtSulfat.setPreferredSize(new java.awt.Dimension(100, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.so4_mittelwert}"),
                txtSulfat,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setConverter(new DoubleConverter(2));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtSulfat, gridBagConstraints);

        cbGkChlorid.setMaximumSize(new java.awt.Dimension(225, 20));
        cbGkChlorid.setMinimumSize(new java.awt.Dimension(225, 20));
        cbGkChlorid.setPreferredSize(new java.awt.Dimension(225, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_pc_salinity_mst}"),
                cbGkChlorid,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cbGkChlorid.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbGkChloridActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(cbGkChlorid, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            lblGenCond7,
            org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenPanOne.class,
                "ChemieMstMessungenPanOne.lblGenCond7.text")); // NOI18N
        lblGenCond7.setMaximumSize(new java.awt.Dimension(107, 17));
        lblGenCond7.setMinimumSize(new java.awt.Dimension(107, 17));
        lblGenCond7.setPreferredSize(new java.awt.Dimension(107, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(lblGenCond7, gridBagConstraints);

        txtSalzgehalt.setMinimumSize(new java.awt.Dimension(100, 20));
        txtSalzgehalt.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_pc_salinity_bemerkung}"),
                txtSalzgehalt,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_pc_salinity_bemerkung}"),
                txtSalzgehalt,
                org.jdesktop.beansbinding.BeanProperty.create("toolTipText"));
        binding.setSourceNullValue("null");
        binding.setSourceUnreadableValue("null");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtSalzgehalt, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(jPanel4, gridBagConstraints);

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.setOpaque(false);
        jPanel5.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(
            lblGenCond5,
            org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenPanOne.class,
                "ChemieMstMessungenPanOne.lblGenCond5.text")); // NOI18N
        lblGenCond5.setMaximumSize(new java.awt.Dimension(167, 17));
        lblGenCond5.setMinimumSize(new java.awt.Dimension(167, 17));
        lblGenCond5.setPreferredSize(new java.awt.Dimension(167, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(lblGenCond5, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            lblGenCond10,
            org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenPanOne.class,
                "ChemieMstMessungenPanOne.lblGenCond10.text")); // NOI18N
        lblGenCond10.setMaximumSize(new java.awt.Dimension(167, 17));
        lblGenCond10.setMinimumSize(new java.awt.Dimension(167, 17));
        lblGenCond10.setPreferredSize(new java.awt.Dimension(167, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(lblGenCond10, gridBagConstraints);

        txtPHMax.setMinimumSize(new java.awt.Dimension(100, 20));
        txtPHMax.setPreferredSize(new java.awt.Dimension(100, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.ph_max}"),
                txtPHMax,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setConverter(new DoubleConverter(2));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtPHMax, gridBagConstraints);

        txtPHMin.setMinimumSize(new java.awt.Dimension(100, 20));
        txtPHMin.setPreferredSize(new java.awt.Dimension(100, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.ph_min}"),
                txtPHMin,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setConverter(new DoubleConverter(2));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtPHMin, gridBagConstraints);

        cbGkPHMin.setMaximumSize(new java.awt.Dimension(225, 20));
        cbGkPHMin.setMinimumSize(new java.awt.Dimension(225, 20));
        cbGkPHMin.setPreferredSize(new java.awt.Dimension(225, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_pc_acid_mst}"),
                cbGkPHMin,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cbGkPHMin.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbGkPHMinActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(cbGkPHMin, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            lblGenCond9,
            org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenPanOne.class,
                "ChemieMstMessungenPanOne.lblGenCond9.text")); // NOI18N
        lblGenCond9.setMaximumSize(new java.awt.Dimension(107, 17));
        lblGenCond9.setMinimumSize(new java.awt.Dimension(107, 17));
        lblGenCond9.setPreferredSize(new java.awt.Dimension(107, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(lblGenCond9, gridBagConstraints);

        txtSaeure.setMinimumSize(new java.awt.Dimension(100, 20));
        txtSaeure.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_pc_acid_bemerkung}"),
                txtSaeure,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_pc_acid_bemerkung}"),
                txtSaeure,
                org.jdesktop.beansbinding.BeanProperty.create("toolTipText"));
        binding.setSourceNullValue("null");
        binding.setSourceUnreadableValue("null");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(txtSaeure, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(jPanel5, gridBagConstraints);

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel6.setOpaque(false);
        jPanel6.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(
            lblGenCond12,
            org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenPanOne.class,
                "ChemieMstMessungenPanOne.lblGenCond12.text")); // NOI18N
        lblGenCond12.setMaximumSize(new java.awt.Dimension(167, 17));
        lblGenCond12.setMinimumSize(new java.awt.Dimension(167, 17));
        lblGenCond12.setPreferredSize(new java.awt.Dimension(167, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(lblGenCond12, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            lblGenCond14,
            org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenPanOne.class,
                "ChemieMstMessungenPanOne.lblGenCond14.text")); // NOI18N
        lblGenCond14.setMaximumSize(new java.awt.Dimension(167, 17));
        lblGenCond14.setMinimumSize(new java.awt.Dimension(167, 17));
        lblGenCond14.setPreferredSize(new java.awt.Dimension(167, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(lblGenCond14, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            lblGenCond15,
            org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenPanOne.class,
                "ChemieMstMessungenPanOne.lblGenCond15.text")); // NOI18N
        lblGenCond15.setMaximumSize(new java.awt.Dimension(167, 17));
        lblGenCond15.setMinimumSize(new java.awt.Dimension(167, 17));
        lblGenCond15.setPreferredSize(new java.awt.Dimension(167, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(lblGenCond15, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            lblGenCond16,
            org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenPanOne.class,
                "ChemieMstMessungenPanOne.lblGenCond16.text")); // NOI18N
        lblGenCond16.setMaximumSize(new java.awt.Dimension(167, 17));
        lblGenCond16.setMinimumSize(new java.awt.Dimension(167, 17));
        lblGenCond16.setPreferredSize(new java.awt.Dimension(167, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(lblGenCond16, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            lblGenCond17,
            org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenPanOne.class,
                "ChemieMstMessungenPanOne.lblGenCond17.text")); // NOI18N
        lblGenCond17.setMaximumSize(new java.awt.Dimension(167, 17));
        lblGenCond17.setMinimumSize(new java.awt.Dimension(167, 17));
        lblGenCond17.setPreferredSize(new java.awt.Dimension(167, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(lblGenCond17, gridBagConstraints);

        txtGesN.setMinimumSize(new java.awt.Dimension(100, 20));
        txtGesN.setPreferredSize(new java.awt.Dimension(100, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.ges_n_mittelwert}"),
                txtGesN,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setConverter(new DoubleConverter(2));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(txtGesN, gridBagConstraints);

        cbGkGesN.setMaximumSize(new java.awt.Dimension(225, 20));
        cbGkGesN.setMinimumSize(new java.awt.Dimension(225, 20));
        cbGkGesN.setPreferredSize(new java.awt.Dimension(225, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_pc_nitrogen_mst}"),
                cbGkGesN,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cbGkGesN.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbGkGesNActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridheight = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(cbGkGesN, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            lblGenCond13,
            org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenPanOne.class,
                "ChemieMstMessungenPanOne.lblGenCond13.text")); // NOI18N
        lblGenCond13.setMaximumSize(new java.awt.Dimension(107, 17));
        lblGenCond13.setMinimumSize(new java.awt.Dimension(107, 17));
        lblGenCond13.setPreferredSize(new java.awt.Dimension(107, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridheight = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(lblGenCond13, gridBagConstraints);

        txtStickstoff.setMinimumSize(new java.awt.Dimension(100, 20));
        txtStickstoff.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_pc_nitrogen_bemerkung}"),
                txtStickstoff,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_pc_nitrogen_bemerkung}"),
                txtStickstoff,
                org.jdesktop.beansbinding.BeanProperty.create("toolTipText"));
        binding.setSourceNullValue("null");
        binding.setSourceUnreadableValue("null");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridheight = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(txtStickstoff, gridBagConstraints);

        txtAmmonium.setMinimumSize(new java.awt.Dimension(100, 20));
        txtAmmonium.setPreferredSize(new java.awt.Dimension(100, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.nh4_mittelwert}"),
                txtAmmonium,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setConverter(new DoubleConverter(4));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(txtAmmonium, gridBagConstraints);

        txtAmmoniak.setMinimumSize(new java.awt.Dimension(100, 20));
        txtAmmoniak.setPreferredSize(new java.awt.Dimension(100, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.nh3_n_mittelwert}"),
                txtAmmoniak,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setConverter(new DoubleConverter(7));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(txtAmmoniak, gridBagConstraints);

        txtNitratN.setMinimumSize(new java.awt.Dimension(100, 20));
        txtNitratN.setPreferredSize(new java.awt.Dimension(100, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.no3_n_mittelwert}"),
                txtNitratN,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setConverter(new DoubleConverter(3));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(txtNitratN, gridBagConstraints);

        txtNitritN.setMinimumSize(new java.awt.Dimension(100, 20));
        txtNitritN.setPreferredSize(new java.awt.Dimension(100, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.no2_mittelwert}"),
                txtNitritN,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setConverter(new DoubleConverter(3));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(txtNitritN, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(jPanel6, gridBagConstraints);

        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel7.setOpaque(false);
        jPanel7.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(
            lblGenCond19,
            org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenPanOne.class,
                "ChemieMstMessungenPanOne.lblGenCond19.text")); // NOI18N
        lblGenCond19.setMaximumSize(new java.awt.Dimension(167, 17));
        lblGenCond19.setMinimumSize(new java.awt.Dimension(167, 17));
        lblGenCond19.setPreferredSize(new java.awt.Dimension(167, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(lblGenCond19, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            lblGenCond18,
            org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenPanOne.class,
                "ChemieMstMessungenPanOne.lblGenCond18.text")); // NOI18N
        lblGenCond18.setMaximumSize(new java.awt.Dimension(167, 17));
        lblGenCond18.setMinimumSize(new java.awt.Dimension(167, 17));
        lblGenCond18.setPreferredSize(new java.awt.Dimension(167, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(lblGenCond18, gridBagConstraints);

        txtOpo4.setMinimumSize(new java.awt.Dimension(100, 20));
        txtOpo4.setPreferredSize(new java.awt.Dimension(100, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.opo4_mittelwert}"),
                txtOpo4,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setConverter(new DoubleConverter(3));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(txtOpo4, gridBagConstraints);

        txtGesP.setMinimumSize(new java.awt.Dimension(100, 20));
        txtGesP.setPreferredSize(new java.awt.Dimension(100, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.ges_p_mittelwert}"),
                txtGesP,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setConverter(new DoubleConverter(3));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(txtGesP, gridBagConstraints);

        cbGkGesamtP.setMaximumSize(new java.awt.Dimension(225, 20));
        cbGkGesamtP.setMinimumSize(new java.awt.Dimension(225, 20));
        cbGkGesamtP.setPreferredSize(new java.awt.Dimension(225, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_pc_phosphor_mst}"),
                cbGkGesamtP,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cbGkGesamtP.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbGkGesamtPActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(cbGkGesamtP, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            lblGenCond20,
            org.openide.util.NbBundle.getMessage(
                ChemieMstMessungenPanOne.class,
                "ChemieMstMessungenPanOne.lblGenCond20.text")); // NOI18N
        lblGenCond20.setMaximumSize(new java.awt.Dimension(107, 17));
        lblGenCond20.setMinimumSize(new java.awt.Dimension(107, 17));
        lblGenCond20.setPreferredSize(new java.awt.Dimension(107, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(lblGenCond20, gridBagConstraints);

        txtPhosphor.setMinimumSize(new java.awt.Dimension(100, 20));
        txtPhosphor.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_pc_phosphor_bemerkung}"),
                txtPhosphor,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gk_pc_phosphor_bemerkung}"),
                txtPhosphor,
                org.jdesktop.beansbinding.BeanProperty.create("toolTipText"));
        binding.setSourceNullValue("null");
        binding.setSourceUnreadableValue("null");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel7.add(txtPhosphor, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jPanel7, gridBagConstraints);

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
    private void cbGkSauerstoffMinActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbGkSauerstoffMinActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_cbGkSauerstoffMinActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbGkChloridActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbGkChloridActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_cbGkChloridActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbGkPHMinActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbGkPHMinActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_cbGkPHMinActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbGkGesNActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbGkGesNActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_cbGkGesNActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbGkGesamtPActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbGkGesamtPActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_cbGkGesamtPActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbGkPhysChemActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbGkPhysChemActionPerformed
        // TODO add your handling code here:
    } //GEN-LAST:event_cbGkPhysChemActionPerformed

    @Override
    public CidsBean getCidsBean() {
        return this.cidsBean;
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
