/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

import de.cismet.cids.custom.util.FgskHelper;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.DisposableCidsBeanStore;

import de.cismet.cids.editors.DefaultCustomObjectEditor;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class KartierabschnittBesUferstrukturen extends javax.swing.JPanel implements DisposableCidsBeanStore {

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    private JTextField[] left;
    private JTextField[] right;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox cbKeineL;
    private javax.swing.JCheckBox cbKeineR;
    private javax.swing.JLabel lblBu;
    private javax.swing.JLabel lblHa;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblL;
    private javax.swing.JLabel lblNboe;
    private javax.swing.JLabel lblPb;
    private javax.swing.JLabel lblR;
    private javax.swing.JLabel lblSb;
    private javax.swing.JLabel lblSo;
    private javax.swing.JLabel lblSpacing;
    private javax.swing.JLabel lblUs;
    private javax.swing.JLabel lblkeine;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JTextField txtBul;
    private javax.swing.JTextField txtBur;
    private javax.swing.JTextField txtHal;
    private javax.swing.JTextField txtHar;
    private javax.swing.JTextField txtNbl;
    private javax.swing.JTextField txtPbl;
    private javax.swing.JTextField txtPbr;
    private javax.swing.JTextField txtSbl;
    private javax.swing.JTextField txtSbr;
    private javax.swing.JTextField txtSol;
    private javax.swing.JTextField txtSor;
    private javax.swing.JTextField txtUsl;
    private javax.swing.JTextField txtUsr;
    private javax.swing.JTextField txtnbr;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WkFgPanOne.
     */
    public KartierabschnittBesUferstrukturen() {
        initComponents();
        left = new JTextField[7];
        left[0] = txtBul;
        left[1] = txtHal;
        left[2] = txtNbl;
        left[3] = txtPbl;
        left[4] = txtSbl;
        left[5] = txtSol;
        left[6] = txtUsl;

        right = new JTextField[7];
        right[0] = txtBur;
        right[1] = txtHar;
        right[2] = txtnbr;
        right[3] = txtPbr;
        right[4] = txtSbr;
        right[5] = txtSor;
        right[6] = txtUsr;

        final FocusListener lisl = new FocusListener() {

                @Override
                public void focusGained(final FocusEvent e) {
                }

                @Override
                public void focusLost(final FocusEvent e) {
                    FgskHelper.fillNvCheckbox(cbKeineL, left);
                }
            };

        final FocusListener lisr = new FocusListener() {

                @Override
                public void focusGained(final FocusEvent e) {
                }

                @Override
                public void focusLost(final FocusEvent e) {
                    FgskHelper.fillNvCheckbox(cbKeineR, right);
                }
            };

        FgskHelper.addListenerForNvCheck(lisl, left);
        FgskHelper.addListenerForNvCheck(lisr, right);
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
        panInfoContent = new javax.swing.JPanel();
        lblL = new javax.swing.JLabel();
        lblSpacing = new javax.swing.JLabel();
        lblR = new javax.swing.JLabel();
        lblBu = new javax.swing.JLabel();
        lblPb = new javax.swing.JLabel();
        lblUs = new javax.swing.JLabel();
        lblSb = new javax.swing.JLabel();
        lblHa = new javax.swing.JLabel();
        lblNboe = new javax.swing.JLabel();
        lblSo = new javax.swing.JLabel();
        txtBul = new javax.swing.JTextField();
        txtPbl = new javax.swing.JTextField();
        txtUsl = new javax.swing.JTextField();
        txtSbl = new javax.swing.JTextField();
        txtHal = new javax.swing.JTextField();
        txtNbl = new javax.swing.JTextField();
        txtSol = new javax.swing.JTextField();
        txtBur = new javax.swing.JTextField();
        txtPbr = new javax.swing.JTextField();
        txtUsr = new javax.swing.JTextField();
        txtSbr = new javax.swing.JTextField();
        txtHar = new javax.swing.JTextField();
        txtnbr = new javax.swing.JTextField();
        txtSor = new javax.swing.JTextField();
        lblkeine = new javax.swing.JLabel();
        cbKeineL = new javax.swing.JCheckBox();
        cbKeineR = new javax.swing.JCheckBox();

        setMinimumSize(new java.awt.Dimension(1100, 250));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(1100, 250));
        setLayout(new java.awt.BorderLayout());

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setLayout(new java.awt.FlowLayout());

        lblHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading.setText(org.openide.util.NbBundle.getMessage(KartierabschnittBesUferstrukturen.class, "KartierabschnittBesUferstrukturen.lblHeading.text")); // NOI18N
        panHeadInfo.add(lblHeading);

        panInfo.add(panHeadInfo, java.awt.BorderLayout.NORTH);

        panInfoContent.setMinimumSize(new java.awt.Dimension(1057, 250));
        panInfoContent.setOpaque(false);
        panInfoContent.setLayout(new java.awt.GridBagLayout());

        lblL.setText(org.openide.util.NbBundle.getMessage(KartierabschnittBesUferstrukturen.class, "KartierabschnittUferstrukturenEditor.lblL.text")); // NOI18N
        lblL.setMaximumSize(new java.awt.Dimension(120, 17));
        lblL.setMinimumSize(new java.awt.Dimension(20, 17));
        lblL.setPreferredSize(new java.awt.Dimension(20, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent.add(lblL, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.weighty = 1.0;
        panInfoContent.add(lblSpacing, gridBagConstraints);

        lblR.setText(org.openide.util.NbBundle.getMessage(KartierabschnittBesUferstrukturen.class, "KartierabschnittBesUferstrukturen.lblR.text")); // NOI18N
        lblR.setMaximumSize(new java.awt.Dimension(120, 17));
        lblR.setMinimumSize(new java.awt.Dimension(20, 17));
        lblR.setPreferredSize(new java.awt.Dimension(20, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent.add(lblR, gridBagConstraints);

        lblBu.setText(org.openide.util.NbBundle.getMessage(KartierabschnittBesUferstrukturen.class, "KartierabschnittBesUferstrukturen.lblBu.text")); // NOI18N
        lblBu.setToolTipText(org.openide.util.NbBundle.getMessage(KartierabschnittBesUferstrukturen.class, "KartierabschnittBesUferstrukturen.lblBu.toolTipText")); // NOI18N
        lblBu.setMaximumSize(new java.awt.Dimension(120, 17));
        lblBu.setMinimumSize(new java.awt.Dimension(40, 17));
        lblBu.setPreferredSize(new java.awt.Dimension(40, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 5, 5);
        panInfoContent.add(lblBu, gridBagConstraints);

        lblPb.setText(org.openide.util.NbBundle.getMessage(KartierabschnittBesUferstrukturen.class, "KartierabschnittBesUferstrukturen.lblPb.text")); // NOI18N
        lblPb.setToolTipText(org.openide.util.NbBundle.getMessage(KartierabschnittBesUferstrukturen.class, "KartierabschnittBesUferstrukturen.lblPb.toolTipText")); // NOI18N
        lblPb.setMaximumSize(new java.awt.Dimension(120, 17));
        lblPb.setMinimumSize(new java.awt.Dimension(40, 17));
        lblPb.setPreferredSize(new java.awt.Dimension(40, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 5, 5);
        panInfoContent.add(lblPb, gridBagConstraints);

        lblUs.setText(org.openide.util.NbBundle.getMessage(KartierabschnittBesUferstrukturen.class, "KartierabschnittBesUferstrukturen.lblUs.text")); // NOI18N
        lblUs.setToolTipText(org.openide.util.NbBundle.getMessage(KartierabschnittBesUferstrukturen.class, "KartierabschnittBesUferstrukturen.lblUs.toolTipText")); // NOI18N
        lblUs.setMaximumSize(new java.awt.Dimension(120, 17));
        lblUs.setMinimumSize(new java.awt.Dimension(40, 17));
        lblUs.setPreferredSize(new java.awt.Dimension(40, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 5, 5);
        panInfoContent.add(lblUs, gridBagConstraints);

        lblSb.setText(org.openide.util.NbBundle.getMessage(KartierabschnittBesUferstrukturen.class, "KartierabschnittBesUferstrukturen.lblSb.text")); // NOI18N
        lblSb.setToolTipText(org.openide.util.NbBundle.getMessage(KartierabschnittBesUferstrukturen.class, "KartierabschnittBesUferstrukturen.lblSb.toolTipText")); // NOI18N
        lblSb.setMaximumSize(new java.awt.Dimension(120, 17));
        lblSb.setMinimumSize(new java.awt.Dimension(40, 17));
        lblSb.setPreferredSize(new java.awt.Dimension(40, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 5, 5);
        panInfoContent.add(lblSb, gridBagConstraints);

        lblHa.setText(org.openide.util.NbBundle.getMessage(KartierabschnittBesUferstrukturen.class, "KartierabschnittBesUferstrukturen.lblHa.text")); // NOI18N
        lblHa.setToolTipText(org.openide.util.NbBundle.getMessage(KartierabschnittBesUferstrukturen.class, "KartierabschnittBesUferstrukturen.lblHa.toolTipText")); // NOI18N
        lblHa.setMaximumSize(new java.awt.Dimension(120, 17));
        lblHa.setMinimumSize(new java.awt.Dimension(40, 17));
        lblHa.setPreferredSize(new java.awt.Dimension(40, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 5, 5);
        panInfoContent.add(lblHa, gridBagConstraints);

        lblNboe.setText(org.openide.util.NbBundle.getMessage(KartierabschnittBesUferstrukturen.class, "KartierabschnittBesUferstrukturen.lblNboe.text")); // NOI18N
        lblNboe.setToolTipText(org.openide.util.NbBundle.getMessage(KartierabschnittBesUferstrukturen.class, "KartierabschnittBesUferstrukturen.lblnboe.toolTipText")); // NOI18N
        lblNboe.setMaximumSize(new java.awt.Dimension(120, 17));
        lblNboe.setMinimumSize(new java.awt.Dimension(40, 17));
        lblNboe.setPreferredSize(new java.awt.Dimension(40, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 5, 5);
        panInfoContent.add(lblNboe, gridBagConstraints);

        lblSo.setText(org.openide.util.NbBundle.getMessage(KartierabschnittBesUferstrukturen.class, "KartierabschnittBesUferstrukturen.lblSo.text")); // NOI18N
        lblSo.setToolTipText(org.openide.util.NbBundle.getMessage(KartierabschnittBesUferstrukturen.class, "KartierabschnittBesUferstrukturen.lblSo.toolTipText")); // NOI18N
        lblSo.setMaximumSize(new java.awt.Dimension(120, 17));
        lblSo.setMinimumSize(new java.awt.Dimension(40, 17));
        lblSo.setPreferredSize(new java.awt.Dimension(40, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 5, 5);
        panInfoContent.add(lblSo, gridBagConstraints);

        txtBul.setMinimumSize(new java.awt.Dimension(40, 20));
        txtBul.setPreferredSize(new java.awt.Dimension(40, 20));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.uferstruktur_bu_links}"), txtBul, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(txtBul, gridBagConstraints);

        txtPbl.setMinimumSize(new java.awt.Dimension(40, 20));
        txtPbl.setPreferredSize(new java.awt.Dimension(40, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.uferstruktur_pb_links}"), txtPbl, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(txtPbl, gridBagConstraints);

        txtUsl.setMinimumSize(new java.awt.Dimension(40, 20));
        txtUsl.setPreferredSize(new java.awt.Dimension(40, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.uferstruktur_us_links}"), txtUsl, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(txtUsl, gridBagConstraints);

        txtSbl.setMinimumSize(new java.awt.Dimension(40, 20));
        txtSbl.setPreferredSize(new java.awt.Dimension(40, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.uferstruktur_sb_links}"), txtSbl, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(txtSbl, gridBagConstraints);

        txtHal.setMinimumSize(new java.awt.Dimension(40, 20));
        txtHal.setPreferredSize(new java.awt.Dimension(40, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.uferstruktur_ha_links}"), txtHal, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(txtHal, gridBagConstraints);

        txtNbl.setMinimumSize(new java.awt.Dimension(40, 20));
        txtNbl.setPreferredSize(new java.awt.Dimension(40, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.uferstruktur_nboe_links}"), txtNbl, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(txtNbl, gridBagConstraints);

        txtSol.setMinimumSize(new java.awt.Dimension(40, 20));
        txtSol.setPreferredSize(new java.awt.Dimension(40, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.uferstruktur_so_links}"), txtSol, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(txtSol, gridBagConstraints);

        txtBur.setMinimumSize(new java.awt.Dimension(40, 20));
        txtBur.setPreferredSize(new java.awt.Dimension(40, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.uferstruktur_bu_rechts}"), txtBur, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(txtBur, gridBagConstraints);

        txtPbr.setMinimumSize(new java.awt.Dimension(40, 20));
        txtPbr.setPreferredSize(new java.awt.Dimension(40, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.uferstruktur_pb_rechts}"), txtPbr, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(txtPbr, gridBagConstraints);

        txtUsr.setMinimumSize(new java.awt.Dimension(40, 20));
        txtUsr.setPreferredSize(new java.awt.Dimension(40, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.uferstruktur_us_rechts}"), txtUsr, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(txtUsr, gridBagConstraints);

        txtSbr.setMinimumSize(new java.awt.Dimension(40, 20));
        txtSbr.setPreferredSize(new java.awt.Dimension(40, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.uferstruktur_sb_rechts}"), txtSbr, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(txtSbr, gridBagConstraints);

        txtHar.setMinimumSize(new java.awt.Dimension(40, 20));
        txtHar.setPreferredSize(new java.awt.Dimension(40, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.uferstruktur_ha_rechts}"), txtHar, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(txtHar, gridBagConstraints);

        txtnbr.setMinimumSize(new java.awt.Dimension(40, 20));
        txtnbr.setPreferredSize(new java.awt.Dimension(40, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.uferstruktur_nboe_rechts}"), txtnbr, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(txtnbr, gridBagConstraints);

        txtSor.setMinimumSize(new java.awt.Dimension(40, 20));
        txtSor.setPreferredSize(new java.awt.Dimension(40, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.uferstruktur_so_rechts}"), txtSor, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(txtSor, gridBagConstraints);

        lblkeine.setText(org.openide.util.NbBundle.getMessage(KartierabschnittBesUferstrukturen.class, "KartierabschnittBesUferstrukturen.lblkeine.text")); // NOI18N
        lblkeine.setMaximumSize(new java.awt.Dimension(120, 17));
        lblkeine.setMinimumSize(new java.awt.Dimension(40, 17));
        lblkeine.setPreferredSize(new java.awt.Dimension(40, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 5, 5);
        panInfoContent.add(lblkeine, gridBagConstraints);

        cbKeineL.setContentAreaFilled(false);
        cbKeineL.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                cbKeineLStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent.add(cbKeineL, gridBagConstraints);

        cbKeineR.setContentAreaFilled(false);
        cbKeineR.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                cbKeineRStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent.add(cbKeineR, gridBagConstraints);

        panInfo.add(panInfoContent, java.awt.BorderLayout.CENTER);

        add(panInfo, java.awt.BorderLayout.CENTER);

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbKeineLStateChanged(final javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_cbKeineLStateChanged
        FgskHelper.nvCheckBoxStateChange(this, cbKeineL, left);
    }//GEN-LAST:event_cbKeineLStateChanged

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbKeineRStateChanged(final javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_cbKeineRStateChanged
        FgskHelper.nvCheckBoxStateChange(this, cbKeineR, right);
    }//GEN-LAST:event_cbKeineRStateChanged

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
            FgskHelper.fillNvCheckbox(cbKeineL, left);
            FgskHelper.fillNvCheckbox(cbKeineR, right);
        }
    }

    @Override
    public void dispose() {
        bindingGroup.unbind();
    }
}