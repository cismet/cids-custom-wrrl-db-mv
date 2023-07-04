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

import javax.swing.JTextField;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.CidsBeanStore;

import de.cismet.cids.editors.DefaultCustomObjectEditor;

import static de.cismet.cids.custom.objecteditors.wrrl_db_mv.ChemieMstMessungenEditor.calcColor;
import static de.cismet.cids.custom.objecteditors.wrrl_db_mv.ChemieMstMessungenEditor.calcColorReverse;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class WkKgMstBioMessungenPanOne extends javax.swing.JPanel implements CidsBeanStore {

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JLabel lbSwEinheit;
    private javax.swing.JLabel lblJmwTn;
    private javax.swing.JLabel lblO2;
    private javax.swing.JLabel lblPhyChem;
    private javax.swing.JLabel lblPhyChem1;
    private javax.swing.JLabel lblPhyChem2;
    private javax.swing.JLabel lblSichtTiefe;
    private javax.swing.JTextField txtJmwTnSw;
    private javax.swing.JTextField txtJmwTnWert;
    private javax.swing.JTextField txtO2Sw;
    private javax.swing.JTextField txtO2Wert;
    private javax.swing.JTextField txtSichtTiefeSw;
    private javax.swing.JTextField txtSichtTiefeWert;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ChemieMstMessungenPanOne object.
     */
    public WkKgMstBioMessungenPanOne() {
        this(false);
    }

    /**
     * Creates new form ChemieMstMessungenPanOne.
     *
     * @param  readOnly  DOCUMENT ME!
     */
    public WkKgMstBioMessungenPanOne(final boolean readOnly) {
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
                        txtJmwTnSw.setBackground(new Color(245, 246, 247));
                        txtJmwTnWert.setBackground(new Color(245, 246, 247));
                        txtO2Sw.setBackground(new Color(245, 246, 247));
                        txtO2Wert.setBackground(new Color(245, 246, 247));
                        txtSichtTiefeSw.setBackground(new Color(245, 246, 247));
                        txtSichtTiefeWert.setBackground(new Color(245, 246, 247));
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
        txtJmwTnSw.setEnabled(false);
        txtJmwTnWert.setEnabled(false);
        txtO2Sw.setEnabled(false);
        txtO2Wert.setEnabled(false);
        txtSichtTiefeSw.setEnabled(false);
        txtSichtTiefeWert.setEnabled(false);

        txtJmwTnSw.setDisabledTextColor(new Color(0, 0, 0));
        txtJmwTnWert.setDisabledTextColor(new Color(0, 0, 0));
        txtO2Sw.setDisabledTextColor(new Color(0, 0, 0));
        txtO2Wert.setDisabledTextColor(new Color(0, 0, 0));
        txtSichtTiefeSw.setDisabledTextColor(new Color(0, 0, 0));
        txtSichtTiefeWert.setDisabledTextColor(new Color(0, 0, 0));
    }

    /**
     * DOCUMENT ME!
     */
    private void clearForm() {
        txtJmwTnSw.setText("");
        txtJmwTnWert.setText("");
        txtO2Sw.setText("");
        txtO2Wert.setText("");
        txtSichtTiefeSw.setText("");
        txtSichtTiefeWert.setText("");
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
        jPanel2 = new javax.swing.JPanel();
        lblSichtTiefe = new javax.swing.JLabel();
        txtSichtTiefeWert = new javax.swing.JTextField();
        txtSichtTiefeSw = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        lblO2 = new javax.swing.JLabel();
        txtO2Wert = new javax.swing.JTextField();
        txtO2Sw = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        lblJmwTn = new javax.swing.JLabel();
        txtJmwTnWert = new javax.swing.JTextField();
        txtJmwTnSw = new javax.swing.JTextField();

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
                WkKgMstBioMessungenPanOne.class,
                "WkKgMstBioMessungenPanOne.lblPhyChem1.text_1")); // NOI18N
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
                WkKgMstBioMessungenPanOne.class,
                "WkKgMstBioMessungenPanOne.lblPhyChem.text")); // NOI18N
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
                WkKgMstBioMessungenPanOne.class,
                "WkKgMstBioMessungenPanOne.lblPhyChem2.text")); // NOI18N
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
                WkKgMstBioMessungenPanOne.class,
                "WkKgMstBioMessungenPanOne.lbSwEinheit.text")); // NOI18N
        lbSwEinheit.setMaximumSize(new java.awt.Dimension(160, 17));
        lbSwEinheit.setMinimumSize(new java.awt.Dimension(160, 17));
        lbSwEinheit.setPreferredSize(new java.awt.Dimension(160, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel8.add(lbSwEinheit, gridBagConstraints);

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
                WkKgMstBioMessungenPanOne.class,
                "WkKgMstBioMessungenPanOne.lblSichtTiefe.text")); // NOI18N
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
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.ppt_eqr}"),
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
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.ppt_class}"),
                txtSichtTiefeSw,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtSichtTiefeSw, gridBagConstraints);

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
            org.openide.util.NbBundle.getMessage(
                WkKgMstBioMessungenPanOne.class,
                "WkKgMstBioMessungenPanOne.lblO2.text")); // NOI18N
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
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.phyb_eqr}"),
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
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.phyb_class}"),
                txtO2Sw,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txtO2Sw, gridBagConstraints);

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
                WkKgMstBioMessungenPanOne.class,
                "WkKgMstBioMessungenPanOne.lblJmwTn.text",
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
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.mzb_eqr}"),
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
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.mzb_class}"),
                txtJmwTnSw,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(txtJmwTnSw, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(jPanel4, gridBagConstraints);

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
        setColorOfField(txtSichtTiefeSw);
        setColorOfField(txtO2Sw);
        setColorOfField(txtJmwTnSw);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  mit  DOCUMENT ME!
     */
    public static void setColorOfField(final JTextField mit) {
        mit.setDisabledTextColor(new Color(139, 142, 143));
        mit.setBackground(new Color(245, 246, 247));

        if ((mit.getText() == null) || mit.getText().equals("") || mit.getText().equals("<nicht gesetzt>")) {
            mit.setBackground(new Color(245, 246, 247));
            return;
        }

        try {
            final int mitD = Integer.parseInt(mit.getText());

            mit.setBackground(calcColor(mitD));

            if (mit.getBackground().equals(Color.RED)) {
                mit.setDisabledTextColor(new Color(255, 255, 255));
            }
        } catch (NumberFormatException e) {
            mit.setBackground(new Color(245, 246, 247));
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   classNumber  mittel DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static Color calcColor(final int classNumber) {
        switch (classNumber) {
            case 1: {
                return Color.BLUE;
            }
            case 2: {
                return Color.GREEN;
            }
            case 3: {
                return Color.YELLOW;
            }
            case 4: {
                return Color.ORANGE;
            }
            case 5: {
                return Color.RED;
            }
            default: {
                return Color.WHITE;
            }
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
