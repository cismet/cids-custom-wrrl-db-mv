/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import Sirius.server.middleware.types.MetaClass;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JList;
import javax.swing.JOptionPane;

import de.cismet.cids.custom.util.CidsBeanSupport;
import de.cismet.cids.custom.util.RouteWBDropBehavior;
import de.cismet.cids.custom.util.ScrollableComboBox;
import de.cismet.cids.custom.util.StationToMapRegistry;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultCustomObjectEditor;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.navigator.utils.CidsBeanDropListener;
import de.cismet.cids.navigator.utils.CidsBeanDropTarget;
import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.cismap.cids.geometryeditor.DefaultCismapGeometryComboBoxEditor;

import de.cismet.cismap.commons.features.Feature;
import de.cismet.cismap.commons.features.FeatureCollection;
import de.cismet.cismap.commons.gui.MappingComponent;
import de.cismet.cismap.commons.interaction.CismapBroker;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class MassnahmenUmsetzungEditor extends javax.swing.JPanel implements CidsBeanRenderer,
    EditorSaveListener,
    CidsBeanDropListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            MassnahmenUmsetzungEditor.class);
    private static final String[] WB_PROPERTIES = { "wk_fg", "wk_sg", "wk_kg", "wk_gw" }; // NOI18N

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    private ArrayList<CidsBean> beansToDelete = new ArrayList<CidsBean>();
    private ArrayList<CidsBean> beansToSave = new ArrayList<CidsBean>();
    private JList referencedList;
    private RouteWBDropBehavior dropBehaviorListener;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbGeom;
    private de.cismet.cids.editors.DefaultBindableReferenceCombo cbMeasure_type_code;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel lblBeschrDerMa;
    private javax.swing.JLabel lblGeom;
    private javax.swing.JLabel lblHeading;
    private javax.swing.JLabel lblHeading1;
    private javax.swing.JLabel lblMassnahme_nr;
    private javax.swing.JLabel lblMeasure_type_code;
    private javax.swing.JLabel lblValMassnahme_nr;
    private javax.swing.JLabel lblValWk_k;
    private javax.swing.JLabel lblWk_k;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.LinearReferencedLineEditor linearReferencedLineEditor;
    private de.cismet.tools.gui.RoundedPanel panGeo;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo;
    private de.cismet.tools.gui.SemiRoundedPanel panHeadInfo1;
    private de.cismet.tools.gui.RoundedPanel panInfo;
    private javax.swing.JPanel panInfoContent;
    private javax.swing.JPanel panInfoContent1;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.WirkungPan wirkungPan1;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form MassnahmenUmsetzungEditor.
     */
    public MassnahmenUmsetzungEditor() {
        initComponents();
        dropBehaviorListener = new RouteWBDropBehavior(this);
        linearReferencedLineEditor.setMetaClassName("MASSNAHMENUMSETZUNG"); // NOI18N
        linearReferencedLineEditor.setFromStationField("mass_stat_v");      // NOI18N
        linearReferencedLineEditor.setToStationField("mass_stat_b");        // NOI18N
        linearReferencedLineEditor.setRealGeomField("real_geom");           // NOI18N
        linearReferencedLineEditor.setDropBehavior(dropBehaviorListener);
        linearReferencedLineEditor.addLinearReferencedLineEditorListener(new LinearReferencedLineEditorListener() {

                @Override
                public void linearReferencedLineCreated() {
                    zoomToFeature();
                }
            });
        deActivateGUIElements(false);
        try {
            new CidsBeanDropTarget(this);
        } catch (final Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Error while creating CidsBeanDropTarget", ex); // NOI18N
            }
        }
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     */
    private void zoomToFeature() {
        final MappingComponent mappingComponent = CismapBroker.getInstance().getMappingComponent();
        if (!mappingComponent.isFixedMapExtent()) {
            final Collection<Feature> collection = new ArrayList<Feature>();
            final FeatureCollection fCol = mappingComponent.getFeatureCollection();

            for (final Feature feature : fCol.getAllFeatures()) {
                if (!(feature instanceof StationToMapRegistry.RouteFeature)) {
                    collection.add(feature);
                }
            }

            CismapBroker.getInstance()
                    .getMappingComponent()
                    .zoomToAFeatureCollection(collection, true, mappingComponent.isFixedMapScale());
        }
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

        panInfo = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading = new javax.swing.JLabel();
        panInfoContent = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        lblBeschrDerMa = new javax.swing.JLabel();
        lblWk_k = new javax.swing.JLabel();
        lblValWk_k = new javax.swing.JLabel();
        lblMassnahme_nr = new javax.swing.JLabel();
        lblMeasure_type_code = new javax.swing.JLabel();
        cbMeasure_type_code = new ScrollableComboBox();
        wirkungPan1 = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.WirkungPan();
        lblValMassnahme_nr = new javax.swing.JLabel();
        panGeo = new de.cismet.tools.gui.RoundedPanel();
        panHeadInfo1 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblHeading1 = new javax.swing.JLabel();
        panInfoContent1 = new javax.swing.JPanel();
        linearReferencedLineEditor = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.LinearReferencedLineEditor();
        jPanel1 = new javax.swing.JPanel();
        cbGeom = new DefaultCismapGeometryComboBoxEditor();
        lblGeom = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(440, 675));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(440, 675));
        setLayout(new java.awt.GridBagLayout());

        panInfo.setMinimumSize(new java.awt.Dimension(470, 370));
        panInfo.setPreferredSize(new java.awt.Dimension(470, 370));

        panHeadInfo.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo.setLayout(new java.awt.FlowLayout());

        lblHeading.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenUmsetzungEditor.class,
                "MassnahmenUmsetzungEditor.lblHeading.text")); // NOI18N
        panHeadInfo.add(lblHeading);

        panInfo.add(panHeadInfo, java.awt.BorderLayout.NORTH);

        panInfoContent.setOpaque(false);
        panInfoContent.setLayout(new java.awt.GridBagLayout());

        jPanel2.setMinimumSize(new java.awt.Dimension(450, 480));
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(450, 480));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jScrollPane1.setMinimumSize(new java.awt.Dimension(422, 75));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(422, 75));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.mass_beschreibung}"),
                jTextArea1,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jScrollPane1.setViewportView(jTextArea1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 10);
        jPanel2.add(jScrollPane1, gridBagConstraints);

        lblBeschrDerMa.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenUmsetzungEditor.class,
                "MassnahmenUmsetzungEditor.lblBeschrDerMa.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        jPanel2.add(lblBeschrDerMa, gridBagConstraints);

        lblWk_k.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenUmsetzungEditor.class,
                "MassnahmenUmsetzungEditor.lblWk_k.text")); // NOI18N
        lblWk_k.setMinimumSize(new java.awt.Dimension(182, 20));
        lblWk_k.setPreferredSize(new java.awt.Dimension(182, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblWk_k, gridBagConstraints);

        lblValWk_k.setMinimumSize(new java.awt.Dimension(250, 20));
        lblValWk_k.setPreferredSize(new java.awt.Dimension(250, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 10, 5);
        jPanel2.add(lblValWk_k, gridBagConstraints);

        lblMassnahme_nr.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenUmsetzungEditor.class,
                "MassnahmenUmsetzungEditor.lblMassnahme_nr.text")); // NOI18N
        lblMassnahme_nr.setMinimumSize(new java.awt.Dimension(182, 20));
        lblMassnahme_nr.setPreferredSize(new java.awt.Dimension(182, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblMassnahme_nr, gridBagConstraints);

        lblMeasure_type_code.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenUmsetzungEditor.class,
                "MassnahmenUmsetzungEditor.lblMeasure_type_code.text"));        // NOI18N
        lblMeasure_type_code.setToolTipText(org.openide.util.NbBundle.getMessage(
                MassnahmenUmsetzungEditor.class,
                "MassnahmenUmsetzungEditor.lblMeasure_type_code.toolTipText")); // NOI18N
        lblMeasure_type_code.setMinimumSize(new java.awt.Dimension(182, 20));
        lblMeasure_type_code.setPreferredSize(new java.awt.Dimension(182, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel2.add(lblMeasure_type_code, gridBagConstraints);

        cbMeasure_type_code.setMinimumSize(new java.awt.Dimension(200, 20));
        cbMeasure_type_code.setPreferredSize(new java.awt.Dimension(200, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.measure_type_code}"),
                cbMeasure_type_code,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 10, 5);
        jPanel2.add(cbMeasure_type_code, gridBagConstraints);

        wirkungPan1.setMinimumSize(new java.awt.Dimension(380, 115));
        wirkungPan1.setPreferredSize(new java.awt.Dimension(380, 115));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 10);
        jPanel2.add(wirkungPan1, gridBagConstraints);

        lblValMassnahme_nr.setMinimumSize(new java.awt.Dimension(250, 20));
        lblValMassnahme_nr.setPreferredSize(new java.awt.Dimension(250, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 10, 5);
        jPanel2.add(lblValMassnahme_nr, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 20, 5, 20);
        panInfoContent.add(jPanel2, gridBagConstraints);

        panHeadInfo1.setBackground(new java.awt.Color(51, 51, 51));
        panHeadInfo1.setMinimumSize(new java.awt.Dimension(109, 24));
        panHeadInfo1.setPreferredSize(new java.awt.Dimension(109, 24));
        panHeadInfo1.setLayout(new java.awt.FlowLayout());

        lblHeading1.setForeground(new java.awt.Color(255, 255, 255));
        lblHeading1.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenUmsetzungEditor.class,
                "MassnahmenUmsetzungEditor.lblHeading1.text")); // NOI18N
        panHeadInfo1.add(lblHeading1);

        panGeo.add(panHeadInfo1, java.awt.BorderLayout.NORTH);

        panInfoContent1.setOpaque(false);
        panInfoContent1.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        panInfoContent1.add(linearReferencedLineEditor, gridBagConstraints);

        panGeo.add(panInfoContent1, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 10, 20);
        panInfoContent.add(panGeo, gridBagConstraints);

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        cbGeom.setMinimumSize(new java.awt.Dimension(300, 20));
        cbGeom.setPreferredSize(new java.awt.Dimension(300, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.additional_geom}"),
                cbGeom,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setConverter(((DefaultCismapGeometryComboBoxEditor)cbGeom).getConverter());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel1.add(cbGeom, gridBagConstraints);

        lblGeom.setText(org.openide.util.NbBundle.getMessage(
                MassnahmenUmsetzungEditor.class,
                "MassnahmenUmsetzungEditor.lblGeom.text")); // NOI18N
        lblGeom.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        jPanel1.add(lblGeom, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 20);
        panInfoContent.add(jPanel1, gridBagConstraints);

        panInfo.add(panInfoContent, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(panInfo, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
    }

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        dispose();
        this.cidsBean = cidsBean;

        if (dropBehaviorListener.isRouteChanged() && !linearReferencedLineEditor.hasChangedSinceDrop()) {
            final int ans = JOptionPane.showConfirmDialog(
                    this,
                    "Sie haben die Stationen nicht geändert, nachdem Sie eine "
                            + "neue Route ausgewählt haben. Möchten Sie die Stationen ändern?",
                    "Keine Änderung der Stationen",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (ans == JOptionPane.YES_OPTION) {
                return;
            }
        }

        if (cidsBean != null) {
            ((DefaultCismapGeometryComboBoxEditor)cbGeom).setCidsMetaObject(cidsBean.getMetaObject());
            ((DefaultCismapGeometryComboBoxEditor)cbGeom).initForNewBinding();
            DefaultCustomObjectEditor.setMetaClassInformationToMetaClassStoreComponentsInBindingGroup(
                bindingGroup,
                cidsBean);
            dropBehaviorListener.setWkFg((CidsBean)cidsBean.getProperty("wk_fg"));
            bindingGroup.bind();
            deActivateGUIElements(true);
        } else {
            dropBehaviorListener.setWkFg(null);
            deActivateGUIElements(false);
        }

        linearReferencedLineEditor.setCidsBean(cidsBean);
        wirkungPan1.setCidsBean(cidsBean);
        bindReadOnlyFields();
        showOrHideGeometryEditors();
    }

    @Override
    public void dispose() {
        ((DefaultCismapGeometryComboBoxEditor)cbGeom).dispose();
        linearReferencedLineEditor.dispose();
        bindingGroup.unbind();
    }

    @Override
    public String getTitle() {
        return "";
    }

    @Override
    public void setTitle(final String title) {
    }

    /**
     * DOCUMENT ME!
     *
     * @param  referencedList  DOCUMENT ME!
     */
    public void setList(final JList referencedList) {
        this.referencedList = referencedList;
    }

    /**
     * DOCUMENT ME!
     */
    private void bindReadOnlyFields() {
        if (cidsBean == null) {
            lblValWk_k.setText("");
            lblValMassnahme_nr.setText("");
        } else {
            lblValWk_k.setText(getWk_k());
            final CidsBean massnahme = (CidsBean)cidsBean.getProperty("massnahme"); // NOI18N

            if (massnahme == null) {
                lblValMassnahme_nr.setText(CidsBeanSupport.FIELD_NOT_SET);
            } else {
                final Object massn_id = massnahme.getProperty("massn_id"); // NOI18N

                if (massn_id != null) {
                    lblValMassnahme_nr.setText(massn_id.toString());
                } else {
                    lblValMassnahme_nr.setText(CidsBeanSupport.FIELD_NOT_SET);
                }
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  enable  DOCUMENT ME!
     */
    private void deActivateGUIElements(final boolean enable) {
        jTextArea1.setEnabled(enable);
        cbMeasure_type_code.setEnabled(enable);
        cbGeom.setEnabled(enable);
        linearReferencedLineEditor.setEnabled(enable);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private String getWk_k() {
        if (cidsBean.getProperty(WB_PROPERTIES[0]) != null) {
            return String.valueOf(cidsBean.getProperty("wk_fg.wk_k")); // NOI18N
        } else if (cidsBean.getProperty(WB_PROPERTIES[1]) != null) {
            return String.valueOf(cidsBean.getProperty("wk_sg.wk_k")); // NOI18N
        } else if (cidsBean.getProperty(WB_PROPERTIES[2]) != null) {
            // TODO: Gibt es beu KG und gw kein wk_k??
            return String.valueOf(cidsBean.getProperty("wk_kg.name")); // NOI18N
        } else if (cidsBean.getProperty(WB_PROPERTIES[3]) != null) {
            return String.valueOf(cidsBean.getProperty("wk_gw.name")); // NOI18N
        } else {
            return CidsBeanSupport.FIELD_NOT_SET;
        }
    }

    @Override
    public void beansDropped(final ArrayList<CidsBean> beans) {
        if (cidsBean != null) {
            for (final CidsBean bean : beans) {
                if (bean.getClass().getName().equals("de.cismet.cids.dynamics.Wk_fg")) {             // NOI18N
                    bindToWb(WB_PROPERTIES[0], bean);
                } else if (bean.getClass().getName().equals("de.cismet.cids.dynamics.Wk_sg")) {      // NOI18N
                    bindToWb(WB_PROPERTIES[1], bean);
                } else if (bean.getClass().getName().equals("de.cismet.cids.dynamics.Wk_kg")) {      // NOI18N
                    bindToWb(WB_PROPERTIES[2], bean);
                } else if (bean.getClass().getName().equals("de.cismet.cids.dynamics.Wk_gw")) {      // NOI18N
                    bindToWb(WB_PROPERTIES[3], bean);
                } else if (bean.getClass().getName().equals("de.cismet.cids.dynamics.Massnahmen")) { // NOI18N
                    final int conf = JOptionPane.showConfirmDialog(
                            this,
                            "Soll die Maßnahme "
                                    + bean.toString()
                                    + " wirklich als Template für die ausgewählte Umsetzung dienen?",
                            "Maßnahme als Template nutzen",
                            JOptionPane.YES_NO_CANCEL_OPTION);
                    if (conf == JOptionPane.YES_OPTION) {
                        copyActionToImplementation(bean);
                        if (referencedList != null) {
                            referencedList.repaint();
                        }
                    }
                }
            }
            bindReadOnlyFields();
        }
    }

    /**
     * binds the given water body to the current CidsBean object.
     *
     * @param  propertyName   the name of the water body property. (The allowed values are stored in the array
     *                        WB_PROPERTIES)
     * @param  propertyEntry  the water body object
     */
    private void bindToWb(final String propertyName, final CidsBean propertyEntry) {
        try {
            cidsBean.setProperty(propertyName, propertyEntry);

            for (final String propName : WB_PROPERTIES) {
                if (!propName.equals(propertyName)) {
                    cidsBean.setProperty(propName, null);
                }
            }
            showOrHideGeometryEditors();
        } catch (final Exception ex) {
            LOG.error("Error while binding a water body", ex); // NOI18N
        }
    }

    /**
     * copies all relevant properties from the given action to the current CidsBean object.
     *
     * @param  act  DOCUMENT ME!
     */
    public void copyActionToImplementation(final CidsBean act) {
        try {
            final CidsBean additionalGeom = (CidsBean)act.getProperty("additional_geom"); // NOI18N

            CidsBeanSupport.deletePropertyIfExists(cidsBean, "additional_geom", beansToDelete); // NOI18N

            cidsBean.setProperty("massnahme", act);                                                       // NOI18N
            cidsBean.setProperty("additional_geom", CidsBeanSupport.cloneCidsBean(additionalGeom));       // NOI18N
            cidsBean.setProperty("wk_fg", act.getProperty("wk_fg"));                                      // NOI18N
            cidsBean.setProperty("wk_sg", act.getProperty("wk_sg"));                                      // NOI18N
            cidsBean.setProperty("wk_kg", act.getProperty("wk_kg"));                                      // NOI18N
            cidsBean.setProperty("wk_gw", act.getProperty("wk_gw"));                                      // NOI18N
            final List<CidsBean> meas = CidsBeanSupport.getBeanCollectionFromProperty(act, "de_meas_cd"); // NOI18N

            if ((meas != null) && (meas.size() > 0)) {
                cidsBean.setProperty("measure_type_code", meas.get(0)); // NOI18N
            }

            if ((act.getProperty("stat_von") != null) && (act.getProperty("stat_bis") != null)) { // NOI18N
                final CidsBean statFrom = (CidsBean)act.getProperty("stat_von");                  // NOI18N
                final CidsBean statTo = (CidsBean)act.getProperty("stat_bis");                    // NOI18N
                final CidsBean realGeom = (CidsBean)act.getProperty("real_geom");                 // NOI18N

                LOG.error("statFrom " + statFrom.getProperty("wert")); // NOI18N
                LOG.error("statTo " + statTo.getProperty("wert"));     // NOI18N

                CidsBeanSupport.deleteStationIfExists(cidsBean, "mass_stat_v", beansToDelete); // NOI18N
                CidsBeanSupport.deleteStationIfExists(cidsBean, "mass_stat_b", beansToDelete); // NOI18N
                CidsBeanSupport.deletePropertyIfExists(cidsBean, "real_geom", beansToDelete);  // NOI18N

                cidsBean.setProperty("mass_stat_v", CidsBeanSupport.cloneStation(statFrom)); // NOI18N
                cidsBean.setProperty("mass_stat_b", CidsBeanSupport.cloneStation(statTo));   // NOI18N
                cidsBean.setProperty("real_geom", CidsBeanSupport.cloneCidsBean(realGeom));  // NOI18N
                linearReferencedLineEditor.setCidsBean(cidsBean);
            }

            if (act != null) {
                final Object fin = act.getProperty("massn_fin");
                if ((fin == null) || ((fin instanceof Boolean) && !((Boolean)fin).booleanValue())) {
                    try {
                        act.setProperty("massn_fin", Boolean.TRUE);
                        if (!beansToSave.contains(act)) {
                            beansToSave.add(act);
                        }
                    } catch (final Exception e) {
                        LOG.error("Error while set action to fin.", e); // NOI18N
                    }
                }
            }
        } catch (final Exception e) {
            LOG.error("Error during the creation of a new bean of type massnahmen", e); // NOI18N
        }
    }

    @Override
    public void editorClosed(final EditorSaveStatus status) {
    }

    @Override
    public boolean prepareForSave() {
        if (dropBehaviorListener.isRouteChanged() && !linearReferencedLineEditor.hasChangedSinceDrop()) {
            final int ans = JOptionPane.showConfirmDialog(
                    this,
                    "Sie haben die Stationen nicht geändert, nachdem Sie eine "
                            + "neue Route ausgewählt haben. Möchten Sie die Stationen ändern?",
                    "Keine Änderung der Stationen",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (ans == JOptionPane.YES_OPTION) {
                return false;
            }
        }

        for (final CidsBean bean : beansToDelete) {
            try {
                bean.persist();
            } catch (final Exception e) {
                LOG.error("Error while deleting bean", e); // NOI18N
            }
        }

        for (final CidsBean bean : beansToSave) {
            try {
                bean.persist();
            } catch (final Exception e) {
                LOG.error("Error while deleting bean", e); // NOI18N
            }
        }

        return true;
    }

    /**
     * DOCUMENT ME!
     */
    private void showOrHideGeometryEditors() {
        if ((cidsBean != null) && (cidsBean.getProperty(WB_PROPERTIES[1]) != null)) {
            panGeo.setVisible(false);
        } else {
            panGeo.setVisible(true);
        }
    }
}