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

import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.exception.ConnectionException;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;

import java.awt.GridLayout;

import de.cismet.cids.custom.wrrl_db_mv.commons.WRRLUtil;
import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;
import de.cismet.cids.custom.wrrl_db_mv.util.TimestampConverter;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultCustomObjectEditor;
import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class GupGupEditor extends javax.swing.JPanel implements CidsBeanRenderer, EditorSaveListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            GupGupEditor.class);
    private static final MetaClass MC = ClassCacheMultiple.getMetaClass(
            WRRLUtil.DOMAIN_NAME,
            "gup_gewaesserabschnitt");

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    private boolean readOnly = false;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblBis;
    private javax.swing.JLabel lblErsteller;
    private javax.swing.JLabel lblErstellt;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblHeading1;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblVon;
    private javax.swing.JLabel lblZeitraum;
    private javax.swing.JLabel lblZustaendigkeit;
    private javax.swing.JPanel panGewaesserInner;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo1;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private de.cismet.tools.gui.RoundedPanel panInfo1;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JPanel panInfoContent1;
    private javax.swing.JScrollPane scrollGewaesser;
    private javax.swing.JTextField txtBis;
    private javax.swing.JTextField txtErsteller;
    private javax.swing.JTextField txtErstellt;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtVon;
    private javax.swing.JTextField txtZustaendigkeit;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form GupMassnahmeSohle.
     */
    public GupGupEditor() {
        initComponents();
        scrollGewaesser.getViewport().setOpaque(false);
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
        lblErstellt = new javax.swing.JLabel();
        lblErsteller = new javax.swing.JLabel();
        lblZeitraum = new javax.swing.JLabel();
        lblZustaendigkeit = new javax.swing.JLabel();
        txtErstellt = new javax.swing.JTextField();
        txtErsteller = new javax.swing.JTextField();
        txtVon = new javax.swing.JTextField();
        txtZustaendigkeit = new javax.swing.JTextField();
        lblVon = new javax.swing.JLabel();
        lblBis = new javax.swing.JLabel();
        txtBis = new javax.swing.JTextField();
        lblName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        panInfo1 = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo1 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading1 = new javax.swing.JLabel();
        panInfoContent1 = new javax.swing.JPanel();
        scrollGewaesser = new javax.swing.JScrollPane();
        panGewaesserInner = new javax.swing.JPanel();

        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(994, 400));
        setLayout(new java.awt.GridBagLayout());

        panInfo.setMinimumSize(new java.awt.Dimension(557, 175));
        panInfo.setPreferredSize(new java.awt.Dimension(1130, 164));

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setLayout(new java.awt.FlowLayout());

        lblHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading.setText(org.openide.util.NbBundle.getMessage(GupGupEditor.class, "GupGupEditor.lblHeading.text")); // NOI18N
        panHeadInfo.add(lblHeading);

        panInfo.add(panHeadInfo, java.awt.BorderLayout.NORTH);

        panInfoContent.setMinimumSize(new java.awt.Dimension(1057, 250));
        panInfoContent.setOpaque(false);
        panInfoContent.setLayout(new java.awt.GridBagLayout());

        lblErstellt.setText(org.openide.util.NbBundle.getMessage(GupGupEditor.class, "GupGupEditor.lblErstellt.text")); // NOI18N
        lblErstellt.setMaximumSize(new java.awt.Dimension(170, 17));
        lblErstellt.setMinimumSize(new java.awt.Dimension(170, 17));
        lblErstellt.setPreferredSize(new java.awt.Dimension(170, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        panInfoContent.add(lblErstellt, gridBagConstraints);

        lblErsteller.setText(org.openide.util.NbBundle.getMessage(
                GupGupEditor.class,
                "GupGupEditor.lblErsteller.text")); // NOI18N
        lblErsteller.setMaximumSize(new java.awt.Dimension(170, 17));
        lblErsteller.setMinimumSize(new java.awt.Dimension(170, 17));
        lblErsteller.setPreferredSize(new java.awt.Dimension(170, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        panInfoContent.add(lblErsteller, gridBagConstraints);

        lblZeitraum.setText(org.openide.util.NbBundle.getMessage(GupGupEditor.class, "GupGupEditor.lblZeitraum.text")); // NOI18N
        lblZeitraum.setMaximumSize(new java.awt.Dimension(170, 17));
        lblZeitraum.setMinimumSize(new java.awt.Dimension(170, 17));
        lblZeitraum.setPreferredSize(new java.awt.Dimension(170, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        panInfoContent.add(lblZeitraum, gridBagConstraints);

        lblZustaendigkeit.setText(org.openide.util.NbBundle.getMessage(
                GupGupEditor.class,
                "GupGupEditor.lblZustaendigkeit.text")); // NOI18N
        lblZustaendigkeit.setMaximumSize(new java.awt.Dimension(170, 17));
        lblZustaendigkeit.setMinimumSize(new java.awt.Dimension(170, 17));
        lblZustaendigkeit.setPreferredSize(new java.awt.Dimension(170, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        panInfoContent.add(lblZustaendigkeit, gridBagConstraints);

        txtErstellt.setMaximumSize(new java.awt.Dimension(280, 20));
        txtErstellt.setMinimumSize(new java.awt.Dimension(280, 20));
        txtErstellt.setPreferredSize(new java.awt.Dimension(380, 20));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.datum_e}"),
                txtErstellt,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setConverter(TimestampConverter.getInstance());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 15);
        panInfoContent.add(txtErstellt, gridBagConstraints);

        txtErsteller.setMaximumSize(new java.awt.Dimension(280, 20));
        txtErsteller.setMinimumSize(new java.awt.Dimension(280, 20));
        txtErsteller.setPreferredSize(new java.awt.Dimension(380, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.benutzer}"),
                txtErsteller,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 15);
        panInfoContent.add(txtErsteller, gridBagConstraints);

        txtVon.setMaximumSize(new java.awt.Dimension(280, 20));
        txtVon.setMinimumSize(new java.awt.Dimension(130, 20));
        txtVon.setPreferredSize(new java.awt.Dimension(380, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.startjahr}"),
                txtVon,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 15);
        panInfoContent.add(txtVon, gridBagConstraints);

        txtZustaendigkeit.setMaximumSize(new java.awt.Dimension(280, 20));
        txtZustaendigkeit.setMinimumSize(new java.awt.Dimension(280, 20));
        txtZustaendigkeit.setPreferredSize(new java.awt.Dimension(380, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.zustaendigkeit}"),
                txtZustaendigkeit,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 15);
        panInfoContent.add(txtZustaendigkeit, gridBagConstraints);

        lblVon.setText(org.openide.util.NbBundle.getMessage(GupGupEditor.class, "GupGupEditor.lblVon.text")); // NOI18N
        lblVon.setMaximumSize(new java.awt.Dimension(50, 17));
        lblVon.setMinimumSize(new java.awt.Dimension(50, 17));
        lblVon.setPreferredSize(new java.awt.Dimension(50, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        panInfoContent.add(lblVon, gridBagConstraints);

        lblBis.setText(org.openide.util.NbBundle.getMessage(GupGupEditor.class, "GupGupEditor.lblBis.text")); // NOI18N
        lblBis.setMaximumSize(new java.awt.Dimension(50, 17));
        lblBis.setMinimumSize(new java.awt.Dimension(50, 17));
        lblBis.setPreferredSize(new java.awt.Dimension(50, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        panInfoContent.add(lblBis, gridBagConstraints);

        txtBis.setMaximumSize(new java.awt.Dimension(280, 20));
        txtBis.setMinimumSize(new java.awt.Dimension(130, 20));
        txtBis.setPreferredSize(new java.awt.Dimension(380, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.endjahr}"),
                txtBis,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 15);
        panInfoContent.add(txtBis, gridBagConstraints);

        lblName.setText(org.openide.util.NbBundle.getMessage(GupGupEditor.class, "GupGupEditor.lblName.text")); // NOI18N
        lblName.setMaximumSize(new java.awt.Dimension(170, 17));
        lblName.setMinimumSize(new java.awt.Dimension(170, 17));
        lblName.setPreferredSize(new java.awt.Dimension(170, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        panInfoContent.add(lblName, gridBagConstraints);

        txtName.setMaximumSize(new java.awt.Dimension(280, 20));
        txtName.setMinimumSize(new java.awt.Dimension(280, 20));
        txtName.setPreferredSize(new java.awt.Dimension(380, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.name}"),
                txtName,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 15);
        panInfoContent.add(txtName, gridBagConstraints);

        panInfo.add(panInfoContent, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 0, 15);
        add(panInfo, gridBagConstraints);

        panHeadInfo1.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo1.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo1.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo1.setLayout(new java.awt.FlowLayout());

        lblHeading1.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading1.setText(org.openide.util.NbBundle.getMessage(GupGupEditor.class, "GupGupEditor.lblHeading1.text")); // NOI18N
        panHeadInfo1.add(lblHeading1);

        panInfo1.add(panHeadInfo1, java.awt.BorderLayout.NORTH);

        panInfoContent1.setMinimumSize(new java.awt.Dimension(1057, 250));
        panInfoContent1.setOpaque(false);
        panInfoContent1.setLayout(new java.awt.GridBagLayout());

        scrollGewaesser.setBorder(null);
        scrollGewaesser.setOpaque(false);

        panGewaesserInner.setBorder(null);
        panGewaesserInner.setMinimumSize(new java.awt.Dimension(100, 100));
        panGewaesserInner.setOpaque(false);
        panGewaesserInner.setLayout(new java.awt.GridLayout(1, 0));
        scrollGewaesser.setViewportView(panGewaesserInner);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panInfoContent1.add(scrollGewaesser, gridBagConstraints);

        panInfo1.add(panInfoContent1, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 15, 15, 15);
        add(panInfo1, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    @Override
    public CidsBean getCidsBean() {
        return this.cidsBean;
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
            refreshGewaesser();
        }
    }

    /**
     * DOCUMENT ME!
     */
    public void refreshGewaesser() {
        panGewaesserInner.removeAll();
        try {
            String query = "select " + MC.getID() + ", " + MC.getPrimaryKey() + " from " + MC.getTableName(); // NOI18N
            query += " WHERE gup = " + cidsBean.getProperty("id");                                            // NOI18N

            final MetaObject[] metaObjects = SessionManager.getProxy().getMetaObjectByQuery(query, 0);

            if ((metaObjects != null) && (metaObjects.length > 0)) {
                panGewaesserInner.setLayout(new GridLayout(metaObjects.length, 1));
                for (final MetaObject mo : metaObjects) {
                    final GupGewaesserPreview tmp = new GupGewaesserPreview();
                    tmp.setCidsBean(mo.getBean());
                    panGewaesserInner.add(tmp);
                }
            }
        } catch (final ConnectionException e) {
            LOG.error("Error while trying to receive measurements.", e); // NOI18N
        }
    }

    @Override
    public void dispose() {
        bindingGroup.unbind();
    }

    @Override
    public String getTitle() {
        return "Gewaesser";
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