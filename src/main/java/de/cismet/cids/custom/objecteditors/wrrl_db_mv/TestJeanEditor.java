/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultCustomObjectEditor;
import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cismap.commons.gui.MappingComponent;
import de.cismet.cismap.commons.interaction.CismapBroker;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public class TestJeanEditor extends DefaultCustomObjectEditor implements EditorSaveListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(TestJeanEditor.class);

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.StationEditor stationEditor;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.StationArrayEditor stationenEditor;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.LinearReferencedLineArrayEditor teileEditor;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkTeilEditor wkTeilEditor;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form TestJeanEditor.
     */
    public TestJeanEditor() {
        initComponents();

        teileEditor.setFields(
            WkTeilEditor.MC_WKTEIL,
            "wk_teile",
            WkTeilEditor.PROP_WKTEIL_FROM,
            WkTeilEditor.PROP_WKTEIL_TO,
            WkTeilEditor.PROP_WKTEIL_GEOM);

        stationenEditor.setFields("stationen");

        stationEditor.addStationEditorListener(new StationEditorListener() {

                @Override
                public void stationCreated() {
                    zoomToFeatures();
                    try {
                        cidsBean.setProperty("station", stationEditor.getCidsBean());
                    } catch (Exception ex) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("error while setting new cidsbean for station", ex);
                        }
                    }
                }
            });

        wkTeilEditor.getWrappedEditor()
                .addLinearReferencedLineEditorListener(new LinearReferencedLineEditorListener() {

                        @Override
                        public void linearReferencedLineCreated() {
                            zoomToFeatures();
                            try {
                                cidsBean.setProperty("wk_teil", wkTeilEditor.getCidsBean());
                            } catch (Exception ex) {
                                if (LOG.isDebugEnabled()) {
                                    LOG.debug("error while setting new cidsbean for wk_teil", ex);
                                }
                            }
                        }
                    });

        teileEditor.addLinearReferencedLineArrayEditorListener(new LinearReferencedLineArrayEditorListener() {

                @Override
                public void editorAdded(final LinearReferencedLineEditor source) {
                    zoomToFeatures();
                }

                @Override
                public void editorRemoved(final LinearReferencedLineEditor source) {
                    zoomToFeatures();
                }
            });

        stationenEditor.addStationEditorListener(new StationArrayEditorListener() {

                @Override
                public void editorAdded(final StationEditor source) {
                    zoomToFeatures();
                }

                @Override
                public void editorRemoved(final StationEditor source) {
                    zoomToFeatures();
                }
            });

        // stationEditor1.setImageIcon(new
        // javax.swing.ImageIcon(getClass().getResource("/de/cismet/cids/custom/objecteditors/wrrl_db_mv/ende.png")));
        // // NOI18N
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     */
    private void zoomToFeatures() {
        final MappingComponent mappingComponent = CismapBroker.getInstance().getMappingComponent();
        if (!mappingComponent.isFixedMapExtent()) {
            CismapBroker.getInstance()
                    .getMappingComponent()
                    .zoomToFeatureCollection(mappingComponent.isFixedMapScale());
        }
    }

    @Override
    public void dispose() {
        try {
            stationEditor.dispose();
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("error while dispose", ex);
            }
        }
        try {
            stationenEditor.dispose();
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("error while dispose", ex);
            }
        }
        try {
            wkTeilEditor.dispose();
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("error while dispose", ex);
            }
        }
        try {
            teileEditor.dispose();
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("error while dispose", ex);
            }
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

        wkTeilEditor = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.WkTeilEditor();
        stationEditor = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.StationEditor();
        jPanel1 = new javax.swing.JPanel();
        stationenEditor = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.StationArrayEditor();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        teileEditor = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.LinearReferencedLineArrayEditor();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();

        setLayout(new java.awt.GridBagLayout());

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.wk_teil}"),
                wkTeilEditor,
                org.jdesktop.beansbinding.BeanProperty.create("cidsBean"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(wkTeilEditor, gridBagConstraints);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.station}"),
                stationEditor,
                org.jdesktop.beansbinding.BeanProperty.create("cidsBean"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(stationEditor, gridBagConstraints);

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean}"),
                stationenEditor,
                org.jdesktop.beansbinding.BeanProperty.create("cidsBean"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(stationenEditor, gridBagConstraints);

        jPanel3.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jPanel3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        add(jPanel1, gridBagConstraints);

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.GridBagLayout());

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean}"),
                teileEditor,
                org.jdesktop.beansbinding.BeanProperty.create("cidsBean"));
        bindingGroup.addBinding(binding);

        teileEditor.setLayout(new java.awt.GridLayout(1, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(teileEditor, gridBagConstraints);

        jPanel4.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        jPanel2.add(jPanel4, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        add(jPanel2, gridBagConstraints);

        jPanel5.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        add(jPanel5, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    @Override
    public void editorClosed(final EditorClosedEvent event) {
        final CidsBean savedBean = event.getSavedBean();
        final CidsBean savedTeilBean = (savedBean == null) ? null : (CidsBean)savedBean.getProperty("wk_teil");
        wkTeilEditor.editorClosed(new EditorClosedEvent(event.getStatus(), savedTeilBean));
        teileEditor.editorClosed(event);
    }

    @Override
    public boolean prepareForSave() {
        boolean save = true;
        save &= wkTeilEditor.prepareForSave();
        save &= teileEditor.prepareForSave();
        return save;
    }
}
