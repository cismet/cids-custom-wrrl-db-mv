/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * GupMassnahmeSohle.java
 *
 * Created on 19.10.2011, 11:49:19
 */
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import de.cismet.cids.custom.wrrl_db_mv.util.ScrollableComboBox;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultCustomObjectEditor;
import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class GupAbstimmungsvermerk extends javax.swing.JPanel implements CidsBeanRenderer, EditorSaveListener {

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbStatusNa;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbStatusWa;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JLabel lblBemerkungDritte;
    private javax.swing.JLabel lblBemerkungNa;
    private javax.swing.JLabel lblBemerkungWa;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblHeading1;
    private javax.swing.JLabel lblHeading2;
    private javax.swing.JLabel lblStatusNa;
    private javax.swing.JLabel lblStatusWa;
    private javax.swing.JLabel lblZustDritte;
    private javax.swing.JLabel lblZustNatBehoerde;
    private javax.swing.JLabel lblZustWaBehoerde;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo1;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo2;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private de.cismet.tools.gui.RoundedPanel panInfo1;
    private de.cismet.tools.gui.RoundedPanel panInfo2;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JPanel panInfoContent1;
    private javax.swing.JPanel panInfoContent2;
    private javax.swing.JScrollPane spBemerkungDritte;
    private javax.swing.JScrollPane spBemerkungNa;
    private javax.swing.JScrollPane spBemerkungWa;
    private javax.swing.JTextField txtZustDritteBehoerde;
    private javax.swing.JTextField txtZustNaBehoerde;
    private javax.swing.JTextField txtZustWaBehoerde;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form GupMassnahmeSohle.
     */
    public GupAbstimmungsvermerk() {
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
        lblZustWaBehoerde = new javax.swing.JLabel();
        txtZustWaBehoerde = new javax.swing.JTextField();
        lblStatusWa = new javax.swing.JLabel();
        cbStatusWa = new ScrollableComboBox();
        lblBemerkungWa = new javax.swing.JLabel();
        spBemerkungWa = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        panInfo1 = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo1 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading1 = new javax.swing.JLabel();
        panInfoContent1 = new javax.swing.JPanel();
        lblZustNatBehoerde = new javax.swing.JLabel();
        cbStatusNa = new ScrollableComboBox();
        lblStatusNa = new javax.swing.JLabel();
        lblBemerkungNa = new javax.swing.JLabel();
        txtZustNaBehoerde = new javax.swing.JTextField();
        spBemerkungNa = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        panInfo2 = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo2 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading2 = new javax.swing.JLabel();
        panInfoContent2 = new javax.swing.JPanel();
        lblZustDritte = new javax.swing.JLabel();
        lblBemerkungDritte = new javax.swing.JLabel();
        txtZustDritteBehoerde = new javax.swing.JTextField();
        spBemerkungDritte = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();

        setPreferredSize(new java.awt.Dimension(994, 500));
        setLayout(new java.awt.GridBagLayout());

        panInfo.setMinimumSize(new java.awt.Dimension(557, 200));
        panInfo.setPreferredSize(new java.awt.Dimension(1130, 200));

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setLayout(new java.awt.FlowLayout());

        lblHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading.setText(org.openide.util.NbBundle.getMessage(
                GupAbstimmungsvermerk.class,
                "GupAbstimmungsvermerk.lblHeading.text")); // NOI18N
        panHeadInfo.add(lblHeading);

        panInfo.add(panHeadInfo, java.awt.BorderLayout.NORTH);

        panInfoContent.setMinimumSize(new java.awt.Dimension(1057, 250));
        panInfoContent.setOpaque(false);
        panInfoContent.setLayout(new java.awt.GridBagLayout());

        lblZustWaBehoerde.setText(org.openide.util.NbBundle.getMessage(
                GupAbstimmungsvermerk.class,
                "GupAbstimmungsvermerk.lblZustWaBehoerde.text")); // NOI18N
        lblZustWaBehoerde.setMaximumSize(new java.awt.Dimension(170, 34));
        lblZustWaBehoerde.setMinimumSize(new java.awt.Dimension(170, 34));
        lblZustWaBehoerde.setPreferredSize(new java.awt.Dimension(170, 34));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent.add(lblZustWaBehoerde, gridBagConstraints);

        txtZustWaBehoerde.setMaximumSize(new java.awt.Dimension(280, 20));
        txtZustWaBehoerde.setMinimumSize(new java.awt.Dimension(280, 20));
        txtZustWaBehoerde.setPreferredSize(new java.awt.Dimension(280, 20));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.wasserbehoerde}"),
                txtZustWaBehoerde,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(txtZustWaBehoerde, gridBagConstraints);

        lblStatusWa.setText(org.openide.util.NbBundle.getMessage(
                GupAbstimmungsvermerk.class,
                "GupAbstimmungsvermerk.lblStatusWa.text")); // NOI18N
        lblStatusWa.setMaximumSize(new java.awt.Dimension(170, 17));
        lblStatusWa.setMinimumSize(new java.awt.Dimension(170, 17));
        lblStatusWa.setPreferredSize(new java.awt.Dimension(170, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent.add(lblStatusWa, gridBagConstraints);

        cbStatusWa.setMaximumSize(new java.awt.Dimension(280, 20));
        cbStatusWa.setMinimumSize(new java.awt.Dimension(280, 20));
        cbStatusWa.setPreferredSize(new java.awt.Dimension(280, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.status_wasser}"),
                cbStatusWa,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(cbStatusWa, gridBagConstraints);

        lblBemerkungWa.setText(org.openide.util.NbBundle.getMessage(
                GupAbstimmungsvermerk.class,
                "GupAbstimmungsvermerk.lblBemerkungWa.text")); // NOI18N
        lblBemerkungWa.setMaximumSize(new java.awt.Dimension(170, 17));
        lblBemerkungWa.setMinimumSize(new java.awt.Dimension(170, 17));
        lblBemerkungWa.setPreferredSize(new java.awt.Dimension(170, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent.add(lblBemerkungWa, gridBagConstraints);

        spBemerkungWa.setMaximumSize(new java.awt.Dimension(280, 90));
        spBemerkungWa.setMinimumSize(new java.awt.Dimension(280, 90));
        spBemerkungWa.setPreferredSize(new java.awt.Dimension(280, 90));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.kommentar_wasser}"),
                jTextArea1,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        spBemerkungWa.setViewportView(jTextArea1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent.add(spBemerkungWa, gridBagConstraints);

        panInfo.add(panInfoContent, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        add(panInfo, gridBagConstraints);

        panInfo1.setMinimumSize(new java.awt.Dimension(557, 200));
        panInfo1.setPreferredSize(new java.awt.Dimension(1130, 200));

        panHeadInfo1.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo1.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo1.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo1.setLayout(new java.awt.FlowLayout());

        lblHeading1.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading1.setText(org.openide.util.NbBundle.getMessage(
                GupAbstimmungsvermerk.class,
                "GupAbstimmungsvermerk.lblHeading1.text")); // NOI18N
        panHeadInfo1.add(lblHeading1);

        panInfo1.add(panHeadInfo1, java.awt.BorderLayout.NORTH);

        panInfoContent1.setMinimumSize(new java.awt.Dimension(1057, 250));
        panInfoContent1.setOpaque(false);
        panInfoContent1.setLayout(new java.awt.GridBagLayout());

        lblZustNatBehoerde.setText(org.openide.util.NbBundle.getMessage(
                GupAbstimmungsvermerk.class,
                "GupAbstimmungsvermerk.lblZustNatBehoerde.text")); // NOI18N
        lblZustNatBehoerde.setMaximumSize(new java.awt.Dimension(170, 34));
        lblZustNatBehoerde.setMinimumSize(new java.awt.Dimension(170, 34));
        lblZustNatBehoerde.setPreferredSize(new java.awt.Dimension(170, 34));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent1.add(lblZustNatBehoerde, gridBagConstraints);

        cbStatusNa.setMaximumSize(new java.awt.Dimension(280, 20));
        cbStatusNa.setMinimumSize(new java.awt.Dimension(280, 20));
        cbStatusNa.setPreferredSize(new java.awt.Dimension(280, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.status_naturschutz}"),
                cbStatusNa,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent1.add(cbStatusNa, gridBagConstraints);

        lblStatusNa.setText(org.openide.util.NbBundle.getMessage(
                GupAbstimmungsvermerk.class,
                "GupAbstimmungsvermerk.lblStatusNa.text")); // NOI18N
        lblStatusNa.setMaximumSize(new java.awt.Dimension(170, 17));
        lblStatusNa.setMinimumSize(new java.awt.Dimension(170, 17));
        lblStatusNa.setPreferredSize(new java.awt.Dimension(170, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent1.add(lblStatusNa, gridBagConstraints);

        lblBemerkungNa.setText(org.openide.util.NbBundle.getMessage(
                GupAbstimmungsvermerk.class,
                "GupAbstimmungsvermerk.lblBemerkungNa.text")); // NOI18N
        lblBemerkungNa.setMaximumSize(new java.awt.Dimension(170, 17));
        lblBemerkungNa.setMinimumSize(new java.awt.Dimension(170, 17));
        lblBemerkungNa.setPreferredSize(new java.awt.Dimension(170, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent1.add(lblBemerkungNa, gridBagConstraints);

        txtZustNaBehoerde.setMaximumSize(new java.awt.Dimension(280, 20));
        txtZustNaBehoerde.setMinimumSize(new java.awt.Dimension(280, 20));
        txtZustNaBehoerde.setPreferredSize(new java.awt.Dimension(280, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.naturschutzbehoerde}"),
                txtZustNaBehoerde,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent1.add(txtZustNaBehoerde, gridBagConstraints);

        spBemerkungNa.setMaximumSize(new java.awt.Dimension(280, 90));
        spBemerkungNa.setMinimumSize(new java.awt.Dimension(280, 90));
        spBemerkungNa.setPreferredSize(new java.awt.Dimension(280, 90));

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.kommentar_naturschutz}"),
                jTextArea2,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        spBemerkungNa.setViewportView(jTextArea2);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent1.add(spBemerkungNa, gridBagConstraints);

        panInfo1.add(panInfoContent1, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        add(panInfo1, gridBagConstraints);

        panInfo2.setMinimumSize(new java.awt.Dimension(557, 200));
        panInfo2.setPreferredSize(new java.awt.Dimension(1130, 200));

        panHeadInfo2.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo2.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo2.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo2.setLayout(new java.awt.FlowLayout());

        lblHeading2.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading2.setText(org.openide.util.NbBundle.getMessage(
                GupAbstimmungsvermerk.class,
                "GupAbstimmungsvermerk.lblHeading2.text")); // NOI18N
        panHeadInfo2.add(lblHeading2);

        panInfo2.add(panHeadInfo2, java.awt.BorderLayout.NORTH);

        panInfoContent2.setMinimumSize(new java.awt.Dimension(1057, 250));
        panInfoContent2.setOpaque(false);
        panInfoContent2.setLayout(new java.awt.GridBagLayout());

        lblZustDritte.setText(org.openide.util.NbBundle.getMessage(
                GupAbstimmungsvermerk.class,
                "GupAbstimmungsvermerk.lblZustDritte.text")); // NOI18N
        lblZustDritte.setMaximumSize(new java.awt.Dimension(170, 34));
        lblZustDritte.setMinimumSize(new java.awt.Dimension(170, 34));
        lblZustDritte.setPreferredSize(new java.awt.Dimension(170, 34));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent2.add(lblZustDritte, gridBagConstraints);

        lblBemerkungDritte.setText(org.openide.util.NbBundle.getMessage(
                GupAbstimmungsvermerk.class,
                "GupAbstimmungsvermerk.lblBemerkungDritte.text")); // NOI18N
        lblBemerkungDritte.setMaximumSize(new java.awt.Dimension(170, 17));
        lblBemerkungDritte.setMinimumSize(new java.awt.Dimension(170, 17));
        lblBemerkungDritte.setPreferredSize(new java.awt.Dimension(170, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panInfoContent2.add(lblBemerkungDritte, gridBagConstraints);

        txtZustDritteBehoerde.setMaximumSize(new java.awt.Dimension(280, 20));
        txtZustDritteBehoerde.setMinimumSize(new java.awt.Dimension(280, 20));
        txtZustDritteBehoerde.setPreferredSize(new java.awt.Dimension(280, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.dritter_beteiligter}"),
                txtZustDritteBehoerde,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent2.add(txtZustDritteBehoerde, gridBagConstraints);

        spBemerkungDritte.setMaximumSize(new java.awt.Dimension(280, 90));
        spBemerkungDritte.setMinimumSize(new java.awt.Dimension(280, 90));
        spBemerkungDritte.setPreferredSize(new java.awt.Dimension(280, 90));

        jTextArea3.setColumns(20);
        jTextArea3.setRows(5);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.kommentar_dritte}"),
                jTextArea3,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        spBemerkungDritte.setViewportView(jTextArea3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent2.add(spBemerkungDritte, gridBagConstraints);

        panInfo2.add(panInfoContent2, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 5, 5);
        add(panInfo2, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
    }

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        bindingGroup.unbind();
        this.cidsBean = cidsBean;

        if (cidsBean != null) {
            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
                bindingGroup,
                cidsBean);
            bindingGroup.bind();
        }
    }

    @Override
    public void dispose() {
        bindingGroup.unbind();
    }

    @Override
    public String getTitle() {
        return "";
    }

    @Override
    public void setTitle(final String title) {
    }

    @Override
    public void editorClosed(final EditorClosedEvent event) {
    }

    @Override
    public boolean prepareForSave() {
        return true;
    }
}