/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.objectrenderer.wrrl_db_mv;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.DisposableCidsBeanStore;

import de.cismet.cids.editors.DefaultCustomObjectEditor;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class WkSgPanTwo extends javax.swing.JPanel implements DisposableCidsBeanStore {

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblChemStat;
    private javax.swing.JLabel lblConfidence;
    private javax.swing.JLabel lblEcoPot;
    private javax.swing.JLabel lblEcoStat;
    private javax.swing.JLabel lblFwstat_dat;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblNon_comp;
    private javax.swing.JLabel lblSpace;
    private javax.swing.JLabel lblValChem_stat;
    private javax.swing.JLabel lblValConfidence;
    private javax.swing.JLabel lblValEco_pot;
    private javax.swing.JLabel lblValEco_stat;
    private javax.swing.JLabel lblValFwstat_dat;
    private javax.swing.JLabel lblValNon_comp;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadQuality;
    private de.cismet.tools.gui.RoundedPanel panQuality;
    private javax.swing.JPanel panQualityContent;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WkFgPanOne.
     */
    public WkSgPanTwo() {
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

        panQuality = new de.cismet.tools.gui.RoundedPanel();
        panHeadQuality = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading = new javax.swing.JLabel();
        panQualityContent = new javax.swing.JPanel();
        lblEcoStat = new javax.swing.JLabel();
        lblEcoPot = new javax.swing.JLabel();
        lblChemStat = new javax.swing.JLabel();
        lblFwstat_dat = new javax.swing.JLabel();
        lblConfidence = new javax.swing.JLabel();
        lblNon_comp = new javax.swing.JLabel();
        lblSpace = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblValEco_stat = new javax.swing.JLabel();
        lblValEco_pot = new javax.swing.JLabel();
        lblValChem_stat = new javax.swing.JLabel();
        lblValFwstat_dat = new javax.swing.JLabel();
        lblValConfidence = new javax.swing.JLabel();
        lblValNon_comp = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(520, 290));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(520, 290));
        setLayout(new java.awt.BorderLayout());

        panHeadQuality.setBackground(new java.awt.Color(51, 51, 51));
        panHeadQuality.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadQuality.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadQuality.setLayout(new java.awt.FlowLayout());

        lblHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading.setText("Überblicksinformationen Qualität");
        panHeadQuality.add(lblHeading);

        panQuality.add(panHeadQuality, java.awt.BorderLayout.NORTH);

        panQualityContent.setMinimumSize(new java.awt.Dimension(450, 260));
        panQualityContent.setOpaque(false);
        panQualityContent.setPreferredSize(new java.awt.Dimension(450, 260));
        panQualityContent.setLayout(new java.awt.GridBagLayout());

        lblEcoStat.setText("Ökologischer Zustand");
        lblEcoStat.setMaximumSize(new java.awt.Dimension(170, 17));
        lblEcoStat.setMinimumSize(new java.awt.Dimension(170, 17));
        lblEcoStat.setPreferredSize(new java.awt.Dimension(170, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 5, 5);
        panQualityContent.add(lblEcoStat, gridBagConstraints);

        lblEcoPot.setText("Ökologisches Potential");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panQualityContent.add(lblEcoPot, gridBagConstraints);

        lblChemStat.setText("Chemischer Zustand");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panQualityContent.add(lblChemStat, gridBagConstraints);

        lblFwstat_dat.setText("Gültigkeitsdatum der QK");
        lblFwstat_dat.setToolTipText("Gültigkeitsdatum der Statusmeldung der QK");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panQualityContent.add(lblFwstat_dat, gridBagConstraints);

        lblConfidence.setText("Verlässlichk. d. ök. Bew.");
        lblConfidence.setToolTipText("Verlässlichkeit der Ökologischen Bewertung");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panQualityContent.add(lblConfidence, gridBagConstraints);

        lblNon_comp.setText("Einhalt. UQN Schadst.");
        lblNon_comp.setToolTipText("Einhaltung UQN Spezifische Schadstoffe");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panQualityContent.add(lblNon_comp, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.weighty = 1.0;
        panQualityContent.add(lblSpace, gridBagConstraints);

        jPanel1.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 8;
        panQualityContent.add(jPanel1, gridBagConstraints);

        lblValEco_stat.setMaximumSize(new java.awt.Dimension(300, 20));
        lblValEco_stat.setMinimumSize(new java.awt.Dimension(300, 20));
        lblValEco_stat.setPreferredSize(new java.awt.Dimension(300, 20));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.eco_stat.name}"),
                lblValEco_stat,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        panQualityContent.add(lblValEco_stat, gridBagConstraints);

        lblValEco_pot.setMaximumSize(new java.awt.Dimension(300, 20));
        lblValEco_pot.setMinimumSize(new java.awt.Dimension(300, 20));
        lblValEco_pot.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.eco_pot.name}"),
                lblValEco_pot,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panQualityContent.add(lblValEco_pot, gridBagConstraints);

        lblValChem_stat.setMaximumSize(new java.awt.Dimension(300, 20));
        lblValChem_stat.setMinimumSize(new java.awt.Dimension(300, 20));
        lblValChem_stat.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.chem_stat.name}"),
                lblValChem_stat,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panQualityContent.add(lblValChem_stat, gridBagConstraints);

        lblValFwstat_dat.setMaximumSize(new java.awt.Dimension(300, 20));
        lblValFwstat_dat.setMinimumSize(new java.awt.Dimension(300, 20));
        lblValFwstat_dat.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.fwstat_dat}"),
                lblValFwstat_dat,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panQualityContent.add(lblValFwstat_dat, gridBagConstraints);

        lblValConfidence.setMaximumSize(new java.awt.Dimension(300, 20));
        lblValConfidence.setMinimumSize(new java.awt.Dimension(300, 20));
        lblValConfidence.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.confidence.name}"),
                lblValConfidence,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panQualityContent.add(lblValConfidence, gridBagConstraints);

        lblValNon_comp.setMaximumSize(new java.awt.Dimension(300, 20));
        lblValNon_comp.setMinimumSize(new java.awt.Dimension(300, 20));
        lblValNon_comp.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.non_comp.name}"),
                lblValNon_comp,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("<nicht gesetzt>");
        binding.setSourceUnreadableValue("<nicht gesetzt>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panQualityContent.add(lblValNon_comp, gridBagConstraints);

        panQuality.add(panQualityContent, java.awt.BorderLayout.CENTER);

        add(panQuality, java.awt.BorderLayout.CENTER);

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
