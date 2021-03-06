/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.DisposableCidsBeanStore;

import de.cismet.cids.editors.DefaultCustomObjectEditor;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class WkSgPanFive extends javax.swing.JPanel implements DisposableCidsBeanStore {

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbEqsHm;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbEqsOnatpl;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbEqsOthpl;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbEqsPestic;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbNitrat;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cblEqsIndpol;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblEqsHm;
    private javax.swing.JLabel lblEqsIndpol;
    private javax.swing.JLabel lblEqsOnatpl;
    private javax.swing.JLabel lblEqsOthpl;
    private javax.swing.JLabel lblEqsPestic;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblNitrat;
    private javax.swing.JLabel lblSpace;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private javax.swing.JPanel panInfoContent;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WkFgPanOne.
     */
    public WkSgPanFive() {
        initComponents();
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
        lblEqsHm = new javax.swing.JLabel();
        lblEqsPestic = new javax.swing.JLabel();
        lblEqsIndpol = new javax.swing.JLabel();
        lblEqsOthpl = new javax.swing.JLabel();
        lblEqsOnatpl = new javax.swing.JLabel();
        lblNitrat = new javax.swing.JLabel();
        cbEqsHm = new de.cismet.cids.editors.DefaultBindableReferenceCombo();
        cbEqsPestic = new de.cismet.cids.editors.DefaultBindableReferenceCombo();
        cblEqsIndpol = new de.cismet.cids.editors.DefaultBindableReferenceCombo();
        cbEqsOthpl = new de.cismet.cids.editors.DefaultBindableReferenceCombo();
        cbEqsOnatpl = new de.cismet.cids.editors.DefaultBindableReferenceCombo();
        cbNitrat = new de.cismet.cids.editors.DefaultBindableReferenceCombo();
        lblSpace = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();

        setMinimumSize(new java.awt.Dimension(580, 260));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(580, 260));
        setLayout(new java.awt.BorderLayout());

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setLayout(new java.awt.FlowLayout());

        lblHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading.setText("Chemische Qualitätskomponenten");
        panHeadInfo.add(lblHeading);

        panInfo.add(panHeadInfo, java.awt.BorderLayout.NORTH);

        panInfoContent.setOpaque(false);
        panInfoContent.setLayout(new java.awt.GridBagLayout());

        lblEqsHm.setText("Schwermetalle");
        lblEqsHm.setToolTipText("Einhaltung UQN Schwermetalle");
        lblEqsHm.setMaximumSize(new java.awt.Dimension(170, 17));
        lblEqsHm.setMinimumSize(new java.awt.Dimension(170, 17));
        lblEqsHm.setPreferredSize(new java.awt.Dimension(170, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent.add(lblEqsHm, gridBagConstraints);

        lblEqsPestic.setText("Pestizide");
        lblEqsPestic.setToolTipText("Einhaltung UQN Pestizide");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent.add(lblEqsPestic, gridBagConstraints);

        lblEqsIndpol.setText("Industrielle Stoffe");
        lblEqsIndpol.setToolTipText("Einhaltung UQN Industrielle Stoffe");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent.add(lblEqsIndpol, gridBagConstraints);

        lblEqsOthpl.setText("Andere prio. Stoffe");
        lblEqsOthpl.setToolTipText("Einhaltung UQN Andere prioritäre Stoffe");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent.add(lblEqsOthpl, gridBagConstraints);

        lblEqsOnatpl.setText("Andere nationale Stoffe");
        lblEqsOnatpl.setToolTipText("Einhaltung UQN Andere nationale Stoffe");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent.add(lblEqsOnatpl, gridBagConstraints);

        lblNitrat.setText("Nitrat");
        lblNitrat.setToolTipText("Einhaltung UQN Chemischer Zustand Nitrat");
        lblNitrat.setMaximumSize(new java.awt.Dimension(170, 17));
        lblNitrat.setMinimumSize(new java.awt.Dimension(170, 17));
        lblNitrat.setPreferredSize(new java.awt.Dimension(170, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 5, 5);
        panInfoContent.add(lblNitrat, gridBagConstraints);

        cbEqsHm.setMinimumSize(new java.awt.Dimension(300, 20));
        cbEqsHm.setPreferredSize(new java.awt.Dimension(300, 20));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.eqs_hm}"),
                cbEqsHm,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(cbEqsHm, gridBagConstraints);

        cbEqsPestic.setMinimumSize(new java.awt.Dimension(300, 20));
        cbEqsPestic.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.eqs_pestic}"),
                cbEqsPestic,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(cbEqsPestic, gridBagConstraints);

        cblEqsIndpol.setMinimumSize(new java.awt.Dimension(300, 20));
        cblEqsIndpol.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.eqs_indpol}"),
                cblEqsIndpol,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(cblEqsIndpol, gridBagConstraints);

        cbEqsOthpl.setMinimumSize(new java.awt.Dimension(300, 20));
        cbEqsOthpl.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.eqs_othpl}"),
                cbEqsOthpl,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(cbEqsOthpl, gridBagConstraints);

        cbEqsOnatpl.setMinimumSize(new java.awt.Dimension(300, 20));
        cbEqsOnatpl.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.eqs_onatpl}"),
                cbEqsOnatpl,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(cbEqsOnatpl, gridBagConstraints);

        cbNitrat.setMinimumSize(new java.awt.Dimension(300, 20));
        cbNitrat.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.nitrat}"),
                cbNitrat,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        panInfoContent.add(cbNitrat, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.weighty = 1.0;
        panInfoContent.add(lblSpace, gridBagConstraints);

        jPanel1.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 6;
        panInfoContent.add(jPanel1, gridBagConstraints);

        panInfo.add(panInfoContent, java.awt.BorderLayout.CENTER);

        add(panInfo, java.awt.BorderLayout.CENTER);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

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
    }
}
