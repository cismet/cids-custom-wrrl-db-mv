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

import javax.swing.JOptionPane;

import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;
import de.cismet.cids.custom.wrrl_db_mv.util.NumberConverter;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.DisposableCidsBeanStore;

import de.cismet.cids.editors.DefaultCustomObjectEditor;

import de.cismet.tools.gui.StaticSwingTools;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class KartierabschnittBesSohlen extends javax.swing.JPanel implements DisposableCidsBeanStore {

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox cbHinweiseKolmation;
    private javax.swing.JCheckBox cbNe;
    private javax.swing.JLabel lblAbwasser;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblHinweiseKolmation;
    private javax.swing.JLabel lblMuell;
    private javax.swing.JLabel lblNe;
    private javax.swing.JLabel lblSandtreiben;
    private javax.swing.JLabel lblSchutt;
    private javax.swing.JLabel lblSonstige;
    private javax.swing.JLabel lblVerockerung;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JTextField tfAbwasser;
    private javax.swing.JTextField tfMuell;
    private javax.swing.JTextField tfSandtreiben;
    private javax.swing.JTextField tfSchutt;
    private javax.swing.JTextField tfSonstige;
    private javax.swing.JTextField tfVerockerung;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WkFgPanOne.
     */
    public KartierabschnittBesSohlen() {
        initComponents();
        final FocusListener lis = new FocusListener() {

                @Override
                public void focusGained(final FocusEvent e) {
                }

                @Override
                public void focusLost(final FocusEvent e) {
                    fillNE();
                }
            };

        tfAbwasser.addFocusListener(lis);
        tfMuell.addFocusListener(lis);
        tfSandtreiben.addFocusListener(lis);
        tfSchutt.addFocusListener(lis);
        tfSonstige.addFocusListener(lis);
        tfVerockerung.addFocusListener(lis);
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
        lblMuell = new javax.swing.JLabel();
        lblSchutt = new javax.swing.JLabel();
        lblAbwasser = new javax.swing.JLabel();
        tfMuell = new javax.swing.JTextField();
        tfSchutt = new javax.swing.JTextField();
        tfAbwasser = new javax.swing.JTextField();
        lblVerockerung = new javax.swing.JLabel();
        tfVerockerung = new javax.swing.JTextField();
        lblSandtreiben = new javax.swing.JLabel();
        tfSandtreiben = new javax.swing.JTextField();
        lblSonstige = new javax.swing.JLabel();
        tfSonstige = new javax.swing.JTextField();
        cbNe = new javax.swing.JCheckBox();
        lblNe = new javax.swing.JLabel();
        lblHinweiseKolmation = new javax.swing.JLabel();
        cbHinweiseKolmation = new javax.swing.JCheckBox();

        setMinimumSize(new java.awt.Dimension(1100, 250));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(1100, 250));
        setLayout(new java.awt.BorderLayout());

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setLayout(new java.awt.FlowLayout());

        lblHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading.setText(org.openide.util.NbBundle.getMessage(
                KartierabschnittBesSohlen.class,
                "KartierabschnittBesSohlen.lblHeading.text_1")); // NOI18N
        panHeadInfo.add(lblHeading);

        panInfo.add(panHeadInfo, java.awt.BorderLayout.NORTH);

        panInfoContent.setMinimumSize(new java.awt.Dimension(1057, 250));
        panInfoContent.setOpaque(false);
        panInfoContent.setLayout(new java.awt.GridBagLayout());

        lblMuell.setText(org.openide.util.NbBundle.getMessage(
                KartierabschnittBesSohlen.class,
                "KartierabschnittBesSohlen.lblMuell.text")); // NOI18N
        lblMuell.setMaximumSize(new java.awt.Dimension(120, 17));
        lblMuell.setMinimumSize(new java.awt.Dimension(140, 17));
        lblMuell.setPreferredSize(new java.awt.Dimension(140, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 5, 5);
        panInfoContent.add(lblMuell, gridBagConstraints);

        lblSchutt.setText(org.openide.util.NbBundle.getMessage(
                KartierabschnittBesSohlen.class,
                "KartierabschnittBesSohlen.lblSchutt.text")); // NOI18N
        lblSchutt.setMaximumSize(new java.awt.Dimension(120, 17));
        lblSchutt.setMinimumSize(new java.awt.Dimension(140, 17));
        lblSchutt.setPreferredSize(new java.awt.Dimension(140, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent.add(lblSchutt, gridBagConstraints);

        lblAbwasser.setText(org.openide.util.NbBundle.getMessage(
                KartierabschnittBesSohlen.class,
                "KartierabschnittBesSohlen.lblAbwasser.text")); // NOI18N
        lblAbwasser.setMaximumSize(new java.awt.Dimension(120, 17));
        lblAbwasser.setMinimumSize(new java.awt.Dimension(140, 17));
        lblAbwasser.setPreferredSize(new java.awt.Dimension(140, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent.add(lblAbwasser, gridBagConstraints);

        tfMuell.setMinimumSize(new java.awt.Dimension(50, 20));
        tfMuell.setPreferredSize(new java.awt.Dimension(50, 20));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.belastung_sohle_mue}"),
                tfMuell,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setConverter(NumberConverter.getInstance());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 10);
        panInfoContent.add(tfMuell, gridBagConstraints);

        tfSchutt.setMinimumSize(new java.awt.Dimension(50, 20));
        tfSchutt.setPreferredSize(new java.awt.Dimension(50, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.belastung_sohle_st}"),
                tfSchutt,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setConverter(NumberConverter.getInstance());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent.add(tfSchutt, gridBagConstraints);

        tfAbwasser.setMinimumSize(new java.awt.Dimension(50, 20));
        tfAbwasser.setPreferredSize(new java.awt.Dimension(50, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.belastung_sohle_abw}"),
                tfAbwasser,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setConverter(NumberConverter.getInstance());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent.add(tfAbwasser, gridBagConstraints);

        lblVerockerung.setText(org.openide.util.NbBundle.getMessage(
                KartierabschnittBesSohlen.class,
                "KartierabschnittBesSohlen.lblVerockerung.text")); // NOI18N
        lblVerockerung.setMaximumSize(new java.awt.Dimension(120, 17));
        lblVerockerung.setMinimumSize(new java.awt.Dimension(140, 17));
        lblVerockerung.setPreferredSize(new java.awt.Dimension(140, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent.add(lblVerockerung, gridBagConstraints);

        tfVerockerung.setMinimumSize(new java.awt.Dimension(50, 20));
        tfVerockerung.setPreferredSize(new java.awt.Dimension(50, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.belastung_sohle_vo}"),
                tfVerockerung,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setConverter(NumberConverter.getInstance());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent.add(tfVerockerung, gridBagConstraints);

        lblSandtreiben.setText(org.openide.util.NbBundle.getMessage(
                KartierabschnittBesSohlen.class,
                "KartierabschnittBesSohlen.lblSandtreiben.text")); // NOI18N
        lblSandtreiben.setMaximumSize(new java.awt.Dimension(120, 17));
        lblSandtreiben.setMinimumSize(new java.awt.Dimension(140, 17));
        lblSandtreiben.setPreferredSize(new java.awt.Dimension(140, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent.add(lblSandtreiben, gridBagConstraints);

        tfSandtreiben.setMinimumSize(new java.awt.Dimension(50, 20));
        tfSandtreiben.setPreferredSize(new java.awt.Dimension(50, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.belastung_sohle_sa}"),
                tfSandtreiben,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setConverter(NumberConverter.getInstance());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent.add(tfSandtreiben, gridBagConstraints);

        lblSonstige.setText(org.openide.util.NbBundle.getMessage(
                KartierabschnittBesSohlen.class,
                "KartierabschnittBesSohlen.lblSonstige.text")); // NOI18N
        lblSonstige.setMaximumSize(new java.awt.Dimension(120, 17));
        lblSonstige.setMinimumSize(new java.awt.Dimension(140, 17));
        lblSonstige.setPreferredSize(new java.awt.Dimension(140, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent.add(lblSonstige, gridBagConstraints);

        tfSonstige.setMinimumSize(new java.awt.Dimension(50, 20));
        tfSonstige.setPreferredSize(new java.awt.Dimension(50, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.belastung_sohle_so}"),
                tfSonstige,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setConverter(NumberConverter.getInstance());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panInfoContent.add(tfSonstige, gridBagConstraints);

        cbNe.setText(org.openide.util.NbBundle.getMessage(
                KartierabschnittBesSohlen.class,
                "KartierabschnittBesSohlen.cbNe.text")); // NOI18N
        cbNe.setContentAreaFilled(false);
        cbNe.addChangeListener(new javax.swing.event.ChangeListener() {

                @Override
                public void stateChanged(final javax.swing.event.ChangeEvent evt) {
                    cbNeStateChanged(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 3, 5, 10);
        panInfoContent.add(cbNe, gridBagConstraints);

        lblNe.setText(org.openide.util.NbBundle.getMessage(
                KartierabschnittBesSohlen.class,
                "KartierabschnittBesSohlen.lblNe.text")); // NOI18N
        lblNe.setMaximumSize(new java.awt.Dimension(120, 17));
        lblNe.setMinimumSize(new java.awt.Dimension(140, 17));
        lblNe.setPreferredSize(new java.awt.Dimension(140, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent.add(lblNe, gridBagConstraints);

        lblHinweiseKolmation.setText(org.openide.util.NbBundle.getMessage(
                KartierabschnittBesSohlen.class,
                "KartierabschnittBesSohlen.lblHinweiseKolmation.text")); // NOI18N
        lblHinweiseKolmation.setMaximumSize(new java.awt.Dimension(120, 17));
        lblHinweiseKolmation.setMinimumSize(new java.awt.Dimension(140, 17));
        lblHinweiseKolmation.setPreferredSize(new java.awt.Dimension(215, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent.add(lblHinweiseKolmation, gridBagConstraints);

        cbHinweiseKolmation.setText(org.openide.util.NbBundle.getMessage(
                KartierabschnittBesSohlen.class,
                "KartierabschnittBesSohlen.cbHinweiseKolmation.text")); // NOI18N
        cbHinweiseKolmation.setContentAreaFilled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.aeussere_kolmation}"),
                cbHinweiseKolmation,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        binding.setSourceUnreadableValue(false);
        bindingGroup.addBinding(binding);

        cbHinweiseKolmation.addChangeListener(new javax.swing.event.ChangeListener() {

                @Override
                public void stateChanged(final javax.swing.event.ChangeEvent evt) {
                    cbHinweiseKolmationStateChanged(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 3, 5, 10);
        panInfoContent.add(cbHinweiseKolmation, gridBagConstraints);

        panInfo.add(panInfoContent, java.awt.BorderLayout.CENTER);

        add(panInfo, java.awt.BorderLayout.CENTER);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbNeStateChanged(final javax.swing.event.ChangeEvent evt) { //GEN-FIRST:event_cbNeStateChanged
        if (cbNe.isSelected()) {
            boolean nothing = true;
            nothing &= (CidsBeanSupport.textToDouble(tfAbwasser, 0.0) == 0.0);
            nothing &= (CidsBeanSupport.textToDouble(tfMuell, 0.0) == 0.0);
            nothing &= (CidsBeanSupport.textToDouble(tfSandtreiben, 0.0) == 0.0);
            nothing &= (CidsBeanSupport.textToDouble(tfSchutt, 0.0) == 0.0);
            nothing &= (CidsBeanSupport.textToDouble(tfSonstige, 0.0) == 0.0);
            nothing &= (CidsBeanSupport.textToDouble(tfVerockerung, 0.0) == 0.0);

            if (nothing) {
                tfAbwasser.setText("0.0");
                tfMuell.setText("0.0");
                tfSandtreiben.setText("0.0");
                tfSchutt.setText("0.0");
                tfSonstige.setText("0.0");
                tfVerockerung.setText("0.0");
            } else {
                JOptionPane.showMessageDialog(
                    StaticSwingTools.getParentFrame(this),
                    "Es sind bereits Felder auf einen Wert ungleich Null gesetzt.",
                    "Felder gesetzt",
                    JOptionPane.INFORMATION_MESSAGE);
                cbNe.setSelected(false);
            }
        }
    } //GEN-LAST:event_cbNeStateChanged

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbHinweiseKolmationStateChanged(final javax.swing.event.ChangeEvent evt) { //GEN-FIRST:event_cbHinweiseKolmationStateChanged
        // TODO add your handling code here:
    } //GEN-LAST:event_cbHinweiseKolmationStateChanged

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
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
            fillNE();
        }
    }

    @Override
    public void dispose() {
        bindingGroup.unbind();
    }

    /**
     * DOCUMENT ME!
     */
    private void fillNE() {
        boolean nothing = true;
        nothing &= (CidsBeanSupport.textToDouble(tfAbwasser, 1.0) == 0.0);
        nothing &= (CidsBeanSupport.textToDouble(tfMuell, 1.0) == 0.0);
        nothing &= (CidsBeanSupport.textToDouble(tfSandtreiben, 1.0) == 0.0);
        nothing &= (CidsBeanSupport.textToDouble(tfSchutt, 1.0) == 0.0);
        nothing &= (CidsBeanSupport.textToDouble(tfSonstige, 1.0) == 0.0);
        nothing &= (CidsBeanSupport.textToDouble(tfVerockerung, 1.0) == 0.0);

        cbNe.setSelected(nothing);
    }
}
